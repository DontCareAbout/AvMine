package us.dontcareabout.avMine.client.util.image;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.DataResource.MimeType;

public interface Icon extends ClientBundle {
	@Source("angle-left.svg")
	@MimeType("image/svg+xml")
	DataResource angleLeft();

	@Source("angle-right.svg")
	@MimeType("image/svg+xml")
	DataResource angleRight();
}
