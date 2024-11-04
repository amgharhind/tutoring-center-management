package Controllers;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.Dao;
import dao.IDao;
import entities.Salle;
@WebServlet("/addSalleController")

public class addSalleController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	IDao dao = new Dao();
        int capacite = Integer.parseInt(request.getParameter("capacite"));
        boolean dispo= "oui".equals(request.getParameter("dispo")); 
        Salle salle = new Salle();
        salle.setCapacite(capacite);
        salle.setDisponible(dispo);
        int result = dao.addSalle(salle);

        if (result > 0) {
            response.sendRedirect("Views/rooms.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}