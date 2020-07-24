package us.dontcareabout.avMine.client.data;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.avMine.client.data.FilmReadyEvent.FilmReadyHandler;

public class FilmReadyEvent extends GwtEvent<FilmReadyHandler> {
	public static final Type<FilmReadyHandler> TYPE = new Type<FilmReadyHandler>();

	@Override
	public Type<FilmReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FilmReadyHandler handler) {
		handler.onFilmReady(this);
	}

	public interface FilmReadyHandler extends EventHandler{
		public void onFilmReady(FilmReadyEvent event);
	}
}
