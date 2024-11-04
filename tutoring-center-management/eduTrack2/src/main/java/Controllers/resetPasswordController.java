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
@WebServlet("/resetPassword/*")
public class resetPasswordController extends HttpServlet{
	
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 String pathInfo = req.getPathInfo();
	 IDao dao = new Dao();
	    if(pathInfo != null && pathInfo.equals("/step1")) {
	    	String email = req.getParameter("email");
            Utilisateur user = dao.getUtilisateurByEmait(email);
            if (user != null) {
                resp.sendRedirect("../Views/resetPasswordStep2.jsp?id=" + user.getIdUtilisateur());
            } else {
                resp.sendRedirect("../Views/resetPasswordStep2.jsp");
            }
	    	
	    	
	    }
		if(pathInfo != null && pathInfo.equals("/step2")) {
			 String newPassword = req.getParameter("password");

		        int userId = Integer.parseInt(req.getParameter("userId"));
		        Utilisateur user = dao.updatePassword(userId, newPassword);
                
		        if (user != null) {
                   HttpSession session = req.getSession();
                    user = dao.getUtilisateurById(userId);
                    session.setAttribute("user", user);
                    
                    resp.sendRedirect("../Views/profile.jsp");
                    return ;
		        } else {
		            resp.sendRedirect( "../Views/resetPasswordStep2.jsp");
		            return ;
		        }
			    	
			    }
}
}
