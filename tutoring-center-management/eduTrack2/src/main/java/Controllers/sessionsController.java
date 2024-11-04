package Controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.Dao;
import dao.IDao;
import entities.Groupe;
import entities.Salle;
import entities.Seance;
import entities.Utilisateur;
@WebServlet("/sessions/*")
public class sessionsController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 String pathInfo = req.getPathInfo();
		    IDao dao = new Dao();
		    if(pathInfo != null && pathInfo.equals("/closeSessionProfController"))
		    {
		    	int idSession = Integer.parseInt(req.getParameter("idSession"));
		    	String reason = req.getParameter("reason");
		    	if(reason.equals("autre")) reason = req.getParameter("customReason");
		    	HttpSession session = req.getSession();
		    	Utilisateur utilisateur = (Utilisateur)session.getAttribute("user");
		    	//System.out.println(idSession + reason  );
		    	int result = dao.closeSeance(idSession, reason, utilisateur.getIdUtilisateur());
		    	resp.sendRedirect("../Views/scheduler.jsp");
		    }
		    
		    if(pathInfo != null && pathInfo.equals("/deleteSessionAgentController"))
		    {
		    	int idSession = Integer.parseInt(req.getParameter("idSession"));
		    	
		       int result = dao.deleteSeance(idSession);
		    	resp.sendRedirect("../Views/scheduler.jsp");
		    	
		    }
		    
		   
		    
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		    String pathInfo = req.getPathInfo();
		    
		    IDao dao = new Dao();
		    if (pathInfo != null && pathInfo.equals("/addSessionAgentController")) {
		    	    String startDate = req.getParameter("startDate");
			        String startTime = req.getParameter("startTime");
			        String endTime = req.getParameter("endTime");
			        int groupId = Integer.parseInt(req.getParameter("group"));
			        int roomId = Integer.parseInt(req.getParameter("room"));
			        Seance seance = new Seance();
			        seance.setDateSeance(LocalDateTime.parse(startDate + "T" + startTime));
			        seance.setHeureDebut(LocalTime.parse(startTime));
			        seance.setHeureFin(LocalTime.parse(endTime));
			        Groupe group = dao.getGroupeById(groupId);
			        Salle salle =dao.getSalleById(roomId);
			        Map<Integer, Object> result = dao.addSeance(seance, group, salle);
			        if (result.containsKey(1)) {
			           
			            resp.sendRedirect("../Views/scheduler.jsp");
			            return;
			        } else {
			            String errorMessage = (String) result.get(0);
			            System.out.println(" error "+errorMessage );
			            resp.setContentType("application/json");
			            resp.setCharacterEncoding("UTF-8");
			            resp.getWriter().write(new Gson().toJson(result));
			            return;
			        }

		    }
		    if(pathInfo != null && pathInfo.equals("/closeDayController")) {
		    	HttpSession session = req.getSession();
		    	int result = 0;
		    	Utilisateur utilisateur = (Utilisateur)session.getAttribute("user");
		    	int idProf = utilisateur.getIdUtilisateur();
		    	System.out.println(idProf + " i am in the controller" + utilisateur.getTypeUtil());
		    	String day = req.getParameter("closeDayDate");
		    	System.out.println(day);

		    	if(utilisateur.getTypeUtil().equals("professor")) {
			    	System.out.println(idProf + " i am in if " + utilisateur.getTypeUtil());

		    		result = dao.closeDayOrHours(idProf, day, null, null, true);
		    	}else {
		    		result = dao.closeDayOrHours( day, null, null);
		    	}
		    	if(result != 0) {
		    		resp.sendRedirect("../Views/scheduler.jsp");
		    		return;
		    	}
		    	//resp.sendRedirect("../Views/error.jsp");
		    	
		    }
			if(pathInfo != null && pathInfo.equals("/closeHoursController")) {
				HttpSession session = req.getSession();
		    	int result = 0;
		    	Utilisateur utilisateur = (Utilisateur)session.getAttribute("user");
		    	String day = req.getParameter("closeHoursDay");
		    	String startTime = req.getParameter("startTime");
		    	String endTime = req.getParameter("endTime");

		    	if(utilisateur.getTypeUtil().equals("professor")) {
		    		result = dao.closeDayOrHours(utilisateur.getIdUtilisateur(), day, startTime, endTime, false);
		    	}else if(utilisateur.getTypeUtil().equals("agent")) {
		    		result = dao.closeDayOrHours( day, startTime, endTime);
		    	}
		    	if(result != 0) {
		    		resp.sendRedirect("../Views/scheduler.jsp");
		    		return;
		    	}
		    	
				
					    	
			 }
			System.out.println(pathInfo);
	    	resp.sendRedirect("../Views/error.jsp");

	}

}
