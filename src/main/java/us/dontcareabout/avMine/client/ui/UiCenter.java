package us.dontcareabout.avMine.client.ui;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Window;

import us.dontcareabout.avMine.client.component.RwdRootPanel;

public class UiCenter {
	private final static MainView mainView = new MainView();

	public static void start() {
		RwdRootPanel.setComponent(mainView);
	}

	//////////////////////////////////////////////////////////////////

	public static void mask(String message) {
		RwdRootPanel.block(message);
	}

	public static void unmask() {
		RwdRootPanel.unblock();
	}

	private final static Window dialog = new Window();
	static {
		dialog.setModal(true);
		dialog.setResizable(false);
	}

	public static void closeDialog() {
		dialog.hide();
	}

	private static void dialog(Widget widget, int width, int height) {
		dialog.clear();
		dialog.add(widget);
		dialog.show();
		dialog.setPixelSize(width, height);
		dialog.center();
	}
}
