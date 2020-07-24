package us.dontcareabout.avMine.server.web.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import us.dontcareabout.avMine.server.dao.FilmDao;

@WebServlet(name="film", urlPatterns = {"/film"})
public class FilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").create();
	private final FilmDao filmDao = new FilmDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println(gson.toJson(filmDao.fetchAll()));
	}
}
