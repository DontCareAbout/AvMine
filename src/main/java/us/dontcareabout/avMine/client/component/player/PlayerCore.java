package us.dontcareabout.avMine.client.component.player;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Range;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.LoadedMetadataEvent;
import com.google.gwt.event.dom.client.LoadedMetadataHandler;
import com.google.gwt.media.client.Video;
import com.sencha.gxt.chart.client.draw.RGB;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent;
import com.sencha.gxt.chart.client.draw.sprite.SpriteSelectionEvent.SpriteSelectionHandler;

import us.dontcareabout.avMine.client.util.TimeUtil;
import us.dontcareabout.gxt.client.draw.LRectangleSprite;
import us.dontcareabout.gxt.client.draw.LTextSprite;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;

//把 Video 變成 PlayerCore 的 field 純粹是因為控制起來比較方便 XD
//暫時不考慮抽 interface 讓 VideoPlayer 可以自行決定 implementation... [逃]
class PlayerCore extends LayerContainer {
	private static int timeFontSize = 14;
	private static int timeHieght = timeFontSize + 2;
	private static int timelineHeight = 12;

	Video video = Video.createIfSupported();

	private TimelineLayer timeLine = new TimelineLayer();
	private TimeLayer time = new TimeLayer();

	/** 若值為 -1 則代表 metadata 還沒 ready。 */
	private double duration = -1;

	private boolean isHotMode;
	private List<Range<Integer>> hotSlice;
	private int hotIndex;

	PlayerCore() {
		bindEvents(video.getMediaElement());
		addLayer(time);
		addLayer(timeLine);

		video.addLoadedMetadataHandler(new LoadedMetadataHandler() {
			@Override
			public void onLoadedMetadata(LoadedMetadataEvent event) {
				duration = video.getDuration();
				onTimeupdate();
			}
		});
	}

	void setSrc(String url) {
		duration = -1;
		video.setSrc(url);
	}

	void setHotMode(boolean isHot) {
		isHotMode = isHot;
	}

	void setHotSlice(List<Range<Integer>> hotList) {
		hotSlice = hotList;
		hotIndex = 0;
		timeLine.genHotSlice();
	}

	@Override
	protected void adjustMember(int width, int height) {
		timeLine.setLX(0);
		timeLine.setLY(height - timeHieght - timelineHeight);
		timeLine.resize(width, timelineHeight);

		time.setLX(0);
		time.setLY(height - timeHieght);
		time.resize(timeFontSize * 9, timeHieght);
	}

	private void onTimeupdate() {
		//會炸 timeupdate event 的時候一定就有 duration 了
		timeLine.setTime(video.getCurrentTime(), duration);
		time.setTime(video.getCurrentTime(), duration);

		if (!isHotMode) { return; }
		if (hotSlice.size() == 0) { return; }

		Range<Integer> hot = hotSlice.get(hotIndex);
		double current = video.getCurrentTime();

		//在熱區中，沒事
		if (hot.contains((int)current)) { return; }

		//目前時間小於熱區的起始時間，跳到熱區的起始時間
		if (current < hot.lowerEndpoint()) { video.setCurrentTime(hot.lowerEndpoint()); };

		//目前時間大於熱區的結束時間，要看狀況
		if (current > hot.upperEndpoint()) {
			//不是最後一個熱區，就往下個熱區去
			if (hotIndex < hotSlice.size() - 1) {
				hotIndex++;
				video.setCurrentTime(hotSlice.get(hotIndex).lowerEndpoint());
			} else {	//已經是最後一個熱區
				video.setCurrentTime(hotSlice.get(hotIndex).upperEndpoint());
				video.pause();
				//TODO 有播放清單 + 連續播放的功能後，這裡要炸 event 換下一片
			}
		}
	}

	private native void bindEvents(MediaElement mediaElement) /*-{
		var self = this;
		mediaElement.addEventListener("timeupdate", function(e) {
			self.@us.dontcareabout.avMine.client.component.player.PlayerCore::onTimeupdate()();
		});
	}-*/;

	private class TimelineLayer extends BaseLS {
		final int barYMargin = 2;
		final int barHeight = timelineHeight - (barYMargin * 2);
		private LRectangleSprite totalBar = new LRectangleSprite();
		private LRectangleSprite nowBar = new LRectangleSprite();
		private ArrayList<HotBlock> hotBlockList = new ArrayList<>();
		private double nowRatio;

		TimelineLayer() {
			add(totalBar);
			totalBar.setFill(RGB.LIGHTGRAY);
			add(nowBar);
			nowBar.setFill(RGB.RED);
			nowBar.setLZIndex(10);	//hotBlockList 後面才會加入，所以要給 nowBar 指定 z-index 才不會被蓋住

			addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					double newTime = video.getDuration() * eventLayerX(event.getBrowserEvent()) / getWidth();
					video.setCurrentTime(newTime);

					if (!isHotMode) { return; }

					//重新調整為合理的 hotIndex
					int newHotIndex = hotSlice.size() - 1;

					for (; newHotIndex > 0; newHotIndex--) {
						//只用 lower bound 來考慮
						//不用判斷 hotSlice[0]，比 hotSlice[1] 小的都判定為 0
						if (newTime > hotSlice.get(newHotIndex).lowerEndpoint()) {
							break;
						}
					}

					hotIndex = newHotIndex;
				}
			});
		}

		private void genHotSlice() {
			for (HotBlock rs : hotBlockList) {
				remove(rs);
			}

			for (Range<Integer> r : hotSlice) {
				HotBlock hot = new HotBlock(r);
				hotBlockList.add(hot);
				add(hot);
				redeploy();
			}

			//adjustMember() 的由 setTime() 觸發就好
			//理論上 genHostSlice() 一定會比 load metadata event 來得快
		}

		private void setTime(double now, double total) {
			nowRatio = now / total;
			adjustMember();
			redraw();
		}

		@Override
		protected void adjustMember() {
			totalBar.setLX(0);
			totalBar.setLY(barYMargin);
			totalBar.setWidth(getWidth());
			totalBar.setHeight(barHeight);

			nowBar.setLX(0);
			nowBar.setLY(barYMargin);
			nowBar.setWidth(getWidth() * nowRatio);
			nowBar.setHeight(barHeight);

			//正常狀況應該不會出現沒 duration 但是卻要調整 hotBlockList
			//不過預防萬一還是擋一下......
			if (duration == -1) { return; }

			double wUnit = getWidth() / duration;
			for (HotBlock hot : hotBlockList) {
				hot.setLX(hot.range.lowerEndpoint() * wUnit);
				hot.setLY(barYMargin);
				hot.setWidth((hot.range.upperEndpoint() - hot.range.lowerEndpoint()) * wUnit);
				hot.setHeight(barHeight);
			}
		}

		private class HotBlock extends LRectangleSprite {
			private Range<Integer> range;

			HotBlock(Range<Integer> r) {
				range = r;
				setFill(RGB.DARKGRAY);
			}
		}
	}

	private class TimeLayer extends BaseLS {
		private LTextSprite text = new LTextSprite();

		TimeLayer() {
			add(text);
			text.setFill(RGB.WHITE);
			text.setFontSize(timeFontSize);
		}

		void setTime(double now, double total) {
			text.setText(TimeUtil.format(now) + " / " + TimeUtil.format(total));
			text.redraw();
		}

		@Override
		protected void adjustMember() {
			text.setLX(2);
			text.setLY(0);
		}
	}

	private class BaseLS extends LayerSprite {
		BaseLS() {
			setBgColor(RGB.BLACK);
			setBgOpacity(0.6);
		}
	}

	//Refactory GF
	private static native double eventLayerX(NativeEvent evt) /*-{
		return evt.layerX || 0;
	}-*/;

	private static native double eventLayerY(NativeEvent evt) /*-{
		return evt.layerY || 0;
	}-*/;
}
