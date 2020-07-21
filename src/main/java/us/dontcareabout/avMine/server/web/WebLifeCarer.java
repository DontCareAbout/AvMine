package us.dontcareabout.avMine.server.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import us.dontcareabout.avMine.server.Folder;
import us.dontcareabout.avMine.server.HibernateUtil;
import us.dontcareabout.avMine.server.Setting;
import us.dontcareabout.avMine.server.util.FilmImporter;
import us.dontcareabout.java.common.Paths;

@WebListener
public class WebLifeCarer implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ensureFolder();
		FilmImporter.process(Setting.workspace());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		HibernateUtil.sessionFactoryDown();
	}

	private void ensureFolder() {
		String workspace = Setting.workspace();

		for (Folder f : Folder.values()) {
			new Paths(workspace).append(f.toString()).existFolder().toFile();
		}
	}
}
