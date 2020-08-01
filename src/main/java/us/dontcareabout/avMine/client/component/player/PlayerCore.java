package us.dontcareabout.avMine.client.component.player;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.dom.client.NativeEvent;
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

	PlayerCore() {
		bindEvents(video.getMediaElement());
		addLayer(time);
		addLayer(timeLine);
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
		timeLine.setTime(video.getCurrentTime(), video.getDuration());
		time.setTime(video.getCurrentTime(), video.getDuration());
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
		private double nowRatio;

		TimelineLayer() {
			add(totalBar);
			totalBar.setFill(RGB.LIGHTGRAY);
			add(nowBar);
			nowBar.setFill(RGB.RED);

			addSpriteSelectionHandler(new SpriteSelectionHandler() {
				@Override
				public void onSpriteSelect(SpriteSelectionEvent event) {
					video.setCurrentTime(video.getDuration() * eventLayerX(event.getBrowserEvent()) / getWidth());
				}
			});
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
