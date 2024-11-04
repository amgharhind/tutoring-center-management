package Controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
@WebServlet("/matiereStudentController")
public class matiereStudentController extends javax.servlet.http.HttpServlet{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Simulate getting matieres from the database
        List<String> matieres = new ArrayList<>();
        matieres.add("Matiere 1");
        matieres.add("Matiere 2");
        matieres.add("Matiere 3");

        // Send the matieres as JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print("[\"" + String.join("\",\"", matieres) + "\"]");
        out.flush();
    }
}
