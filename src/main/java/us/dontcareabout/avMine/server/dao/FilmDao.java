package us.dontcareabout.avMine.server.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import us.dontcareabout.avMine.server.HibernateUtil;
import us.dontcareabout.avMine.shared.vo.Film;

//Refactory 抽出 BaseDao
public class FilmDao {
	private static int BATCH_SIZE = 100;

	public List<Film> fetchAll() {
		Session session = HibernateUtil.session();
		List<Film> result = session.createQuery("from film", Film.class).list();
		session.close();
		return result;
	}

	public void save(Film entity) {
		Session session = HibernateUtil.session();
		Transaction transaction = session.beginTransaction();
		session.save(entity);
		transaction.commit();
		session.close();
	}

	public void save(List<Film> entityList) {
		Session session = HibernateUtil.session();
		Transaction transaction = session.beginTransaction();

		for (int i = 1; i <= entityList.size(); i++) {
			session.save(entityList.get(i - 1));

			if (i % BATCH_SIZE == 0) {
				transaction.commit();
			}
		}

		session.close();	//close 的時候會做 commit
	}
}
