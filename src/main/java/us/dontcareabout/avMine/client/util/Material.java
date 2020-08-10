package us.dontcareabout.avMine.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.impl.ImageResourcePrototype;

import us.dontcareabout.avMine.client.util.image.Icon;

public class Material {
	private static final Icon icon = GWT.create(Icon.class);

	public static ImageResource icon_AngleLeft() {
		return from(icon.angleLeft(), 30, 30);
	}

	public static ImageResource icon_AngleRight() {
		return from(icon.angleRight(), 30, 30);
	}

	private static ImageResource from(DataResource dr, int width, int height) {
		return new ImageResourcePrototype(
			"", dr.getSafeUri(),
			0, 0, width, height,
			false, false
		);
	}
}
