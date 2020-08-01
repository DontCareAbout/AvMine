package us.dontcareabout.avMine.client.component.player;

import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.sencha.gxt.widget.core.client.container.SimpleContainer;

/**
 * 提供一個有 {@link Video} element 以及自訂控制界面 / 行為（{@link PlayerCore}）的 GXT component。
 */
//實際的功能都在 PlayerCore 裡頭， 這裡只負責 layout 的 wrapper
//所以重點會在 onResize() 以及提供各種 delegate method
public class VideoPlayer extends SimpleContainer {
	private final AbsolutePanel absolute = new AbsolutePanel();
	private final PlayerCore controller = new PlayerCore();

	public VideoPlayer() {
		controller.video.setControls(false);
		add(absolute);
		absolute.add(controller.video, 0, 0);
		absolute.add(controller, 0, 0);
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
