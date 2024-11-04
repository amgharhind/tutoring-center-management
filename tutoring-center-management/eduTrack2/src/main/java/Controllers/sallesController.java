package Controllers;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.Dao;
import dao.IDao;
import entities.Salle;
@WebServlet("/salles/*")
public class sallesController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        IDao dao = new Dao();
		if (pathInfo != null && pathInfo.equals("/getAllSalles")) {
            List<Salle> salles = dao.getAllSalles();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(new Gson().toJson(salles));
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
	}

}
