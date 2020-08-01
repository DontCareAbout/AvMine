package us.dontcareabout.avMine.client.component.player;

import com.google.gwt.event.dom.client.CanPlayThroughHandler;
import com.google.gwt.event.dom.client.LoadedMetadataHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

public class VideoPlayer extends SimpleContainer {
	private final AbsolutePanel absolute = new AbsolutePanel();
	private final Controller controller = new Controller();

	public VideoPlayer() {
		controller.video.setControls(false);
		add(absolute);
		absolute.add(controller.video, 0, 0);
		absolute.add(controller, 0, 0);
	}

	public void addLoadedMetadataHandler(LoadedMetadataHandler handler) {
		controller.video.addLoadedMetadataHandler(handler);
	}

	public void addCanPlayThroughHandler(CanPlayThroughHandler handler) {
		controller.video.addCanPlayThroughHandler(handler);
	}

	public void play() {
		controller.video.play();
	}

	public void pause() {
		controller.video.pause();
	}

	public void setCurrentTime(Integer value) {
		controller.video.setCurrentTime(value);
	}

	public void setSrc(String url) {
		controller.video.setSrc(url);
	}

	@Override
	protected void onResize(int width, int height) {
		controller.video.setPixelSize(width, height);
		controller.setPixelSize(width, height);
		super.onResize(width, height);
	}
}
