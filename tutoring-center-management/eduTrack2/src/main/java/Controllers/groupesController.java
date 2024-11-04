package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dao.Dao;
import dao.IDao;
import entities.Groupe;

@WebServlet("/getAllGroupesServlet")
public class groupesController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  IDao dao = new Dao();

		 resp.setContentType("application/json");
	        resp.setCharacterEncoding("UTF-8");
	        PrintWriter out = resp.getWriter();
	        List<Groupe> groupes = dao.getAllGroupes();
	        // Convert the list of groups to JSON format
	        Gson gson = new Gson();
	        String json = gson.toJson(groupes);
	        out.print(json);
	        out.flush();
	}

}
