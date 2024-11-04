package Controllers;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import dao.IDao;
import entities.Utilisateur;
@WebServlet("/loginController")

public class loginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	IDao dao = new Dao();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Utilisateur utilisateur = dao.authentifier(email, password);

        if (utilisateur != null) {
            request.getSession().setAttribute("user", utilisateur);

            response.sendRedirect("Views/profile.jsp");
        } else {
            response.sendRedirect("Views/signIn.jsp");
        }
    }
}
