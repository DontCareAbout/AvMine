package us.dontcareabout.avMine.client.data;

import java.util.ArrayList;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.google.gwt.core.client.GWT;

import us.dontcareabout.avMine.shared.vo.Film;

public class MapperSet {
	interface FilmMapper extends ObjectMapper<ArrayList<Film>> {}
	public final static FilmMapper film = GWT.create(FilmMapper.class);
}
