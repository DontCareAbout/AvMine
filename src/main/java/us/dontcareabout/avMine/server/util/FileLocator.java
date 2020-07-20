package us.dontcareabout.avMine.server.util;

import java.io.File;

import us.dontcareabout.avMine.server.Folder;
import us.dontcareabout.avMine.server.Setting;
import us.dontcareabout.java.common.Paths;

public class FileLocator {
	public static File getFilm(long id) {
		return getPaths(Long.toString(id), Folder.film).toFile();
	}

	public static File getAccident(String name) {
		return getPaths(name, Folder.accident).toFile();
	}

	private static Paths getPaths(String name, Folder type) {
		return new Paths(Setting.workspace()).append(type.toString())
			.append(name);
	}
}
