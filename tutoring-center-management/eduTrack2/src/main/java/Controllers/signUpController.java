package Controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dao;
import dao.IDao;
import entities.Utilisateur;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@WebServlet("/signUpController")

public class signUpController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        	int userId = 0;
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String prenom = request.getParameter("prenom");

            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            System.out.println("prenom" + prenom);

            Map<String, Object> userAttributes = new HashMap<>();
            userAttributes.put("nom", name);
            userAttributes.put("prenom", prenom);  
            userAttributes.put("email", email);
            userAttributes.put("password", password);
   
            String userType = request.getParameter("userType");
            System.out.println("User Type: " + userType);

            IDao dao = new Dao();

            if ("student".equals(userType)) {
                userAttributes.put("sexe", request.getParameter("childGender"));    
                 userAttributes.put("phone", request.getParameter("phone"));
                int age = Integer.parseInt(request.getParameter("age"));
                System.out.println("Age: " + age);

                userAttributes.put("age", age);
                userAttributes.put("parentName", request.getParameter("parentName"));
                userAttributes.put("parentPrenome", request.getParameter("parentPrenome"));
                userAttributes.put("parentEmail", request.getParameter("parentEmail"));
                userAttributes.put("parentAddress", request.getParameter("parentAddress"));
                userAttributes.put("parentGender", request.getParameter("parentGender"));
                userAttributes.put("Level", Integer.parseInt(request.getParameter("Level")));
                String[] selectedMatieres = request.getParameterValues("matiere[]");
                System.out.println(selectedMatieres[0]);
                System.out.println(request.getParameter("Level"));

                if (selectedMatieres != null && selectedMatieres.length > 0) {
                    List<Integer> matiereList = Arrays.stream(selectedMatieres)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    userAttributes.put("matieres", matiereList);
                }

                 userId = dao.addEleve(userAttributes);

                if (userId > 0) {
                    System.out.println("Student added successfully");
                    Utilisateur utilisateur = dao.getUtilisateurById(userId);
                    HttpSession session = request.getSession();
                    session.setAttribute("user", utilisateur);
                   
                    response.sendRedirect("Views/profile.jsp");
                } else {
                    System.out.println("Error adding student");
                    response.sendRedirect("error.jsp");
                }
            } else if ("prof".equals(userType)) {
                // Handle professor-specific attributes
            	String[] selectedSpecialities = request.getParameterValues("speciality[]");
            	if(selectedSpecialities!= null && selectedSpecialities.length>0) {
            		List<Integer> specialities = Arrays.stream(selectedSpecialities).
            				map(Integer::parseInt).toList();
            		userAttributes.put("specialities", specialities);
            	}
            	boolean avecExperience = "1".equals(request.getParameter("experience"));

                System.out.println("Avec Experience: " + avecExperience);

                int mainSpecialty = Integer.parseInt( request.getParameter("mainSpecialty"));
                userAttributes.put("mainSpecialty", mainSpecialty);
                userAttributes.put("phoneProf", request.getParameter("phoneProf"));
                userAttributes.put("ageProf",Integer.parseInt(request.getParameter("ageProf")));
                userAttributes.put("profGender",request.getParameter("profGender"));
                userAttributes.put("avecExperience", avecExperience);
           	    userAttributes.put("adresse", request.getParameter("adr"));  

                userId = dao.addProf(userAttributes);

                if (userId > 0) {
                    System.out.println("Professor added successfully");
                    Utilisateur utilisateur = dao.getUtilisateurById(userId);
                    HttpSession session = request.getSession();
                    
                    session.setAttribute("user", utilisateur);
                    
                    response.sendRedirect("Views/profile.jsp");
                } else {
                    System.out.println("Error adding professor");
                    response.sendRedirect("Views/signUp.jsp");
                }
            } else {
                System.out.println("Invalid user type");
                response.sendRedirect("Views/error.jsp");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Number format exception. Please check your input.");
            response.sendRedirect("Views/error.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An unexpected error occurred.");
            response.sendRedirect("Views/error.jsp");
        }
    }}
