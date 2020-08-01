package us.dontcareabout.avMine.client.component.player;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.event.dom.client.CanPlayThroughHandler;
import com.google.gwt.event.dom.client.LoadedMetadataHandler;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class VideoPlayer extends SimpleContainer {
	private final Video v = Video.createIfSupported();
	private final AbsolutePanel absolute = new AbsolutePanel();
	private final Controller controller = new Controller(v);

	public VideoPlayer() {
		bindEvents(v.getMediaElement());
		v.setControls(false);
		add(absolute);
		absolute.add(v, 0, 0);
		absolute.add(controller, 0, 0);
	}

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
		v.setPixelSize(width, height);
		controller.setPixelSize(width, height);
		super.onResize(width, height);
	}

	private native void bindEvents(MediaElement mediaElement) /*-{
		var self = this;
		mediaElement.addEventListener("timeupdate", function(e) {
			self.@us.dontcareabout.avMine.client.component.player.VideoPlayer::onTimeupdate()();
		});
	}-*/;

	private void onTimeupdate() {
		controller.setTime(v.getCurrentTime(), v.getDuration());
	}
}
