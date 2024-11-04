package Controllers;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dao.Dao;
import dao.IDao;
import entities.Matiere;
@WebServlet("/matieresServletController/*")
public class matieresController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	  IDao dao = new Dao();
      String pathInfo = req.getPathInfo();
      
      if (pathInfo != null) {
          String[] pathSegments = pathInfo.split("/");
          
          if (pathSegments.length > 1) {
              String requestType = pathSegments[1];

              if ("specialities".equals(requestType)) {
                  // Handle the specialities request
                  List<Matiere> specialities = dao.getAllMatieres();
                  List<Map<String, Object>> specialitiesData = specialities.stream()
                          .map(specialitie -> {
                              Map<String, Object> data = new HashMap<>();
                               data.put("id",specialitie.getIdMatiere());
                              data.put("nom", specialitie.getDescriptionMatiere());
                              return data;
                          }) .toList();

                  resp.setContentType("application/json");
                  resp.setCharacterEncoding("UTF-8");
                  resp.getWriter().write(new Gson().toJson(specialitiesData));
                  return;
              } else if ("matieres".equals(requestType)) {
            	  String niveau = req.getParameter("niveau");
                  // Handle the matieres request
                  List<Matiere> matieres = dao.getMatieresByNiveauId(Integer.parseInt(niveau));
                  // Convert Matiere objects to a list of simplified objects with id and nom
                  List<Map<String, Object>> matieresData = matieres.stream()
                          .map(matiere -> {
                              Map<String, Object> data = new HashMap<>();
                               data.put("id", matiere.getIdMatiere());
                              data.put("nom", matiere.getDescriptionMatiere());
                              return data;
                          })
                          .toList();

                  resp.setContentType("application/json");
                  resp.setCharacterEncoding("UTF-8");
                  resp.getWriter().write(new Gson().toJson(matieresData));
                  return;
              }
          }
      }

      // If no valid request type is found, return an error
      resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      resp.getWriter().write("Invalid request");
  }
	  
	

}
