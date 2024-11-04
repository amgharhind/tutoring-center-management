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

@WebServlet("/addMatiere/fetchAllMatieres")
public class addMatiereToStudentController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int studentId = Integer.parseInt(req.getParameter("studentId")); 
         IDao dao = new Dao();
        List<Matiere> matieres = dao.getAllMatieresNotAssignedToAstudent(studentId);
        Map<Integer, Matiere> matieresMap = new HashMap<>();
        for (int i = 0; i < matieres.size(); i++) {
            matieresMap.put(i, matieres.get(i));
        }
        System.out.println(matieresMap);
        Gson gson = new Gson();
        String json = gson.toJson(matieresMap);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
        
	}
	

}
