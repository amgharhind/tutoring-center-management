package Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dao;
import dao.IDao;
import entities.Utilisateur;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/updateProfile")
public class updateProfileController  extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Utilisateur utilisateur = (Utilisateur)session.getAttribute("user");
		IDao dao = new Dao();
		
		String age = req.getParameter("age");
		System.out.println(age);

		System.out.println(req.getParameter("adr"));
		System.out.println(req.getParameter("phone"));
        System.out.println(req.getParameter("password"));
        System.out.println(utilisateur.getIdUtilisateur());
		 Map<String, Object> values = new HashMap<>();
	        values.put("email", req.getParameter("email"));
	        if(age != null) values.put("age",Integer.parseInt(req.getParameter("age")) );

	        values.put("adr", req.getParameter("adr"));
	        values.put("phone", req.getParameter("phone"));
	        values.put("password", req.getParameter("password"));
		    values.put("type_utile", utilisateur.getTypeUtil());
		    values.put("id_utile", utilisateur.getIdUtilisateur());
		
	    int result = dao.updateProfile(values);
	    if(result>0) {
	    	utilisateur = dao.getUtilisateurById(utilisateur.getIdUtilisateur());
	    	session.setAttribute("user", utilisateur);
	    	resp.sendRedirect("Views/profile.jsp");
	    	return ;
	    }
	    resp.sendRedirect("Views/error.jsp");
	
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
