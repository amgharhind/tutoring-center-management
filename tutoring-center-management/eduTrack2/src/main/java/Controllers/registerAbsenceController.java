package Controllers;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import dao.IDao;

import java.io.IOException;
import java.io.PrintWriter;

import entities.*;
@WebServlet("/atendance/*")

public class registerAbsenceController extends HttpServlet {

	 
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String pathRequest = request.getPathInfo();
    	if(pathRequest != null && pathRequest.equals("/registerAbsenceController")) {
    		 int studentId = Integer.parseInt(request.getParameter("idStudent"));
    	        int seanceId = Integer.parseInt(request.getParameter("idSeance"));
    	        //String justification = request.getParameter("justification");
    	        int idGroup = Integer.parseInt(request.getParameter("idGroup"));
    	        IDao dao = new Dao();
    	        Absence absence = new Absence();
    	        //absence.setJustification(justification);

    	        Seance seance = new Seance();
    	        seance.setIdSeance(seanceId);
    	        absence.setSeance(seance);

    	        Eleve eleve = new Eleve();
    	        eleve.setIdEleve(studentId);
    	        absence.setEleve(eleve);

    	        
    	        int result = dao.saveAbsence(absence);

    	        if (result > 0) {
    	        	//int idGroup =(int) request.getAttribute("idGroup");
    	        	System.out.println("i am here  "+idGroup );
    	        	
    	            response.sendRedirect( "http://localhost:8080/eduTrack2/Views/presenceStudents.jsp?idGroupe=" + idGroup);
    	        } else {
    	            response.sendRedirect("../Views/error.jsp");
    	        }
    		
    	}else if(pathRequest != null && pathRequest.equals("/checkAbsentController")) {
    	    response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            
            int idStudent = Integer.parseInt(request.getParameter("idStudent"));
            int idSeance = Integer.parseInt(request.getParameter("idSeance"));
            
            IDao dao = new Dao();
            
            boolean isAbsent = dao.isAbsent(idStudent, idSeance);
            System.out.println(" i am herree" + isAbsent);
            out.print(isAbsent);
        }
    		
    	}
       
    }

