package Controllers;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.*;
import com.google.gson.Gson;

@WebServlet("/validerProf")
public class validerProfServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idProf = Integer.parseInt(request.getParameter("idProf"));

        IDao dao = new Dao();
        Map<Integer, String> result = dao.validerProf(idProf);
     // Display the result for each step
        for (Map.Entry<Integer, String> entry : result.entrySet()) {
            System.out.println("Step " + entry.getKey() + ": " + entry.getValue());
        }
        
       
        
        if (!result.containsKey(0)) {
        	
            System.out.println("redirected " );

            response.sendRedirect("Views/profs.jsp");
        } else {
            System.out.println("Error occurred: " + result.get(0));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new Gson().toJson(result));

            
        }
        
    }
}
