package us.dontcareabout.avMine.server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;

import us.dontcareabout.avMine.shared.vo.Film;

//Refactory GF
public class HibernateUtil {
	private static SessionFactory sessionF;

	public static SessionFactory sessionFactory() {
		if (sessionF != null) { return sessionF; }

		StandardServiceRegistry registry;
		String workspace = Setting.workspace().replace('\\', '/');

		registry = new StandardServiceRegistryBuilder().configure()
			.applySetting(AvailableSettings.URL, "jdbc:h2:file:" + workspace + "/database/h2")
			.build();

		MetadataSources sources = new MetadataSources(registry);
		//TODO 改一次掃一整個 package
		sources.addAnnotatedClass(Film.class);

		sessionF = sources.getMetadataBuilder().build().getSessionFactoryBuilder().build();
		return sessionF;
	}

	public static Session session() {
		return sessionFactory().openSession();
	}
}
