package us.dontcareabout.avMine.client.component.player;

import com.google.gwt.event.dom.client.CanPlayThroughHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.LoadedMetadataHandler;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;
import com.sencha.gxt.widget.core.client.toolbar.LabelToolItem;

public class VideoPlayer extends SimpleContainer {
	private final Video v = Video.createIfSupported();
	private final AbsolutePanel absolute = new AbsolutePanel();
	private final LabelToolItem blockDiv = new LabelToolItem();

	public VideoPlayer() {
		v.setControls(false);
		add(absolute);
		absolute.add(v, 0, 0);
		//在 video 上頭蓋一層看不到的 div 來阻攔所有 mouse event
		absolute.add(blockDiv, 0, 0);
	}

	//blockDiv 的 DOM event handler
	public void addClickHandler(ClickHandler handler) {
		blockDiv.addDomHandler(handler, ClickEvent.getType());
	}

	public void addDoubleClickHandler(DoubleClickHandler handler) {
		blockDiv.addDomHandler(handler, DoubleClickEvent.getType());
	}
	////

	public void addLoadedMetadataHandler(LoadedMetadataHandler handler) {
		v.addLoadedMetadataHandler(handler);
	}

	public void addCanPlayThroughHandler(CanPlayThroughHandler handler) {
		v.addCanPlayThroughHandler(handler);
	}

	public void play() {
		v.play();
	}

	public void pause() {
		v.pause();
	}

	public void setCurrentTime(Integer value) {
		v.setCurrentTime(value);
	}

	public void setSrc(String url) {
		v.setSrc(url);
	}

	@Override
	protected void onResize(int width, int height) {
		super.onResize(width, height);
		v.setPixelSize(width, height);
		blockDiv.setPixelSize(width, height);
	}
}
