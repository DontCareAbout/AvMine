package us.dontcareabout.avMine.server.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import us.dontcareabout.avMine.server.dao.FilmDao;
import us.dontcareabout.avMine.shared.vo.Film;

public class FilmImporter {
	private static final FileFilter filter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) { return false; }

			return true;
		}
	};

	public static void process(String path) {
		File folder = new File(path);

		if (folder.isFile()) { throw new IllegalArgumentException("不是目錄"); }


		FilmDao dao = new FilmDao();

		for (File file : folder.listFiles(filter)) {
			try {
				BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				Film film = new Film();
				film.setName(file.getName());
				film.setCreateTime(new Date(attr.lastModifiedTime().toMillis()));
				film.setSize(attr.size());
				dao.save(film);
				//移動到 film 目錄下
				Files.move(
					file.toPath(),
					FileLocator.getFilm(film.getId()).toPath(),
					StandardCopyOption.ATOMIC_MOVE
				);
			} catch(IOException e) {
				e.printStackTrace();
				//移動到 accident 區
				try {
					Files.move(
						file.toPath(),
						FileLocator.getAccident(file.getName()).toPath(),
						StandardCopyOption.ATOMIC_MOVE
					);
				} catch(IOException e1) {
					e1.printStackTrace();
					//不想管了 XD
				}
			}
		}
	}
}
