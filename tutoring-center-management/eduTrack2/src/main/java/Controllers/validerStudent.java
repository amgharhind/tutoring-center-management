package Controllers;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import dao.IDao;

@WebServlet("/validerStudent")
public class validerStudent extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String studentIdParam = request.getParameter("idStudent");
        IDao dao = new Dao();
        // Validate if the parameter is not null and is a valid integer
        if (studentIdParam != null && studentIdParam.matches("\\d+")) {
            int studentId = Integer.parseInt(studentIdParam);

            boolean validationStatus =dao.validerEleve(studentId);
         // Redirect back to the referring page
            if (validationStatus ) {
                response.sendRedirect("Views/students.jsp");
            } else {
                response.sendRedirect( "Views/error.jsp");
            }
          
        } else {
            response.sendRedirect( "Views/error.jsp");
        }
}
}