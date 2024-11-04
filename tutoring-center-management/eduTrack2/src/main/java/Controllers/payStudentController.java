package Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.*;
@WebServlet("/payStudentController")
public class payStudentController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentIdString = request.getParameter("idStudent");
        String matiereIdString = request.getParameter("idMatiere");
        System.out.println(studentIdString   + "  matiere " + matiereIdString);
        if (studentIdString != null && matiereIdString != null) {
            try {
                int studentId = Integer.parseInt(studentIdString);
                int matiereId = Integer.parseInt(matiereIdString);

                IDao dao = new Dao();
                int result = dao.payMatiere(matiereId, studentId);

                if (result > 0) {
                    response.sendRedirect("Views/students.jsp");
                } else {
                	System.out.println("i am in else");
                    response.sendRedirect("Views/students.jsp");
                }
            } catch (NumberFormatException e) {
            	System.out.println("number format ");

                response.sendRedirect("Views/error.jsp");
            }
        } else {
        	System.out.println("null params ");

            response.sendRedirect( "Views/error.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
