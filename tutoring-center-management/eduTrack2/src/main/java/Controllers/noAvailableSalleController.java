package Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import dao.IDao;
@WebServlet("/noAvailableSalleController")
public class noAvailableSalleController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String salleIdString = request.getParameter("id");
        String optString = request.getParameter("opt");
       
        IDao dao = new Dao();

        if (salleIdString != null && !salleIdString.isEmpty()) {
            try {
                int salleId = Integer.parseInt(salleIdString);
                boolean currentAvailability = Boolean.parseBoolean(optString);
                boolean newAvailability = !currentAvailability; 
                int result = dao.notAvailableSalle(salleId,newAvailability);
               System.out.println(request.getContextPath() );
                if (result > 0) {
                    response.sendRedirect("Views/rooms.jsp");
                } else {
                    response.sendRedirect( "Views/error.jsp");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect( "Views/error.jsp");
            }
        } else {
            response.sendRedirect( "/Views/error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
