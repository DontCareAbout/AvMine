package us.dontcareabout.avMine.client.component.player;

import com.sencha.gxt.chart.client.draw.RGB;

import us.dontcareabout.avMine.client.util.TimeUtil;
import us.dontcareabout.gxt.client.draw.LTextSprite;
import us.dontcareabout.gxt.client.draw.LayerContainer;
import us.dontcareabout.gxt.client.draw.LayerSprite;

public class Controller extends LayerContainer {
	private static int timeFontSize = 14;

	private TimeLayer time = new TimeLayer();

	public Controller() {
		addLayer(time);
	}

	public void setTime(double now, double total) {
		time.setTime(now, total);
	}

	@Override
	protected void adjustMember(int width, int height) {
		time.setLX(0);
		time.setLY(height - (timeFontSize + 2));
		time.resize(timeFontSize * 9, (timeFontSize + 2));
	}

	private class TimeLayer extends LayerSprite {

		private LTextSprite text = new LTextSprite("8:88:88 / 8:88:88");

		TimeLayer() {
			add(text);
			text.setFill(RGB.WHITE);
			text.setFontSize(timeFontSize);
			setBgColor(RGB.BLACK);
			setBgOpacity(0.6);
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
}
