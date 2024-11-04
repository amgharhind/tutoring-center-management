package Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import dao.IDao;
import entities.Niveau;
import com.google.gson.Gson;

@WebServlet("/niveauController")
public class niveauController  extends HttpServlet{
 
	private static final long serialVersionUID = 1L;

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	IDao dao = new Dao();
	List<Niveau> niveaux = dao.getAllNiveaus();
	List<Map<String, Object>> niveauData = niveaux.stream().map(niveau ->{
		Map<String, Object> data = new HashMap<>();
        data.put("id",niveau.getIdNiveau());
        data.put("descriptionNiveau", niveau.getDescriptionNiveau());
        return data;
	}).toList();
	//List<String> niveauxNames = niveaux.stream().map(n->n.getDescriptionNiveau()).toList();
	 resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    resp.getWriter().write(new Gson().toJson(niveauData));
	    
	
}
}
