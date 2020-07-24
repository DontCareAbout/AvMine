package us.dontcareabout.avMine.client.data;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import us.dontcareabout.avMine.client.data.FilmReadyEvent.FilmReadyHandler;
import us.dontcareabout.avMine.shared.vo.Film;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();


	private static ArrayList<Film> filmList;

	public static List<Film> getFilm() {
		return filmList;
	}

	public static void wantFilm() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, "film");

		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					filmList = MapperSet.film.read(response.getText());
					eventBus.fireEvent(new FilmReadyEvent());
				}

				@Override
				public void onError(Request request, Throwable exception) {}
			});
		} catch(RequestException e) {}
	}

	public static HandlerRegistration addFilmReady(FilmReadyHandler handler) {
		return eventBus.addHandler(FilmReadyEvent.TYPE, handler);
	}
}
