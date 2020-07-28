package us.dontcareabout.avMine.server.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import us.dontcareabout.avMine.server.util.FileLocator;

@WebServlet(name="video206", urlPatterns="/video/*")
public class VideoServlet extends Http206Servlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected File resource(HttpServletRequest request) {
		try {
			String pathInfo = request.getPathInfo();
			//一開始有一個斜線 Orz
			pathInfo = URLDecoder.decode(pathInfo.substring(1), "UTF-8");
			return FileLocator.getFilm(Long.valueOf(pathInfo));
		} catch(UnsupportedEncodingException e) {
			return null;
		}
	}
}