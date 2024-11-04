<%@page import="entities.Parent"%>
<%@page import="dao.*"%>
<%@page import="entities.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <%
Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
    if (utilisateur == null) {
%>
<jsp:forward page="signIn.jsp"></jsp:forward>
<% }%>


<%     //IDao dao = (IDao) getServletContext().getAttribute("sharedDao");
    IDao dao = new Dao();

 %>
 <jsp:include page="navBar.jsp"></jsp:include>
 <div class="main-content position-relative max-height-vh-100 h-100">
 
    <!-- Navbar -->
    
    <!-- End Navbar -->
    <div class="card shadow-lg mx-test card-profile-bottom">
      <div class="card-body p-3">
        <div class="row gx-4">
          <div class="col-auto">
        
          </div>
          <div class="col-auto my-auto">
            <div class="h-100">
              <h5 class="mb-1">
                <%=utilisateur.getNom() + " " + utilisateur.getPrenom() %>
              </h5>
              <p class="mb-0 font-weight-bold text-sm">
				          <%
				    String userType = utilisateur.getTypeUtil();
				    String userTypeText;
				
				    if ("student".equals(userType)) {
				        userTypeText = "étudiant";
				    } else if ("professeur".equals(userType)) {
				        userTypeText = "professeur";
				    } else if ("parent".equals(userType)) {
				        userTypeText = "parent";
				   
				    }else if("agent".equals(userType)){
				        userTypeText = "admin";
				    }else if("directeur".equals(userType)){
				    	userTypeText = "directeur";
				    } else {
				        userTypeText = "unknown"; 
				    }
				%>

				<%= userTypeText %>

               </p>
            </div>
          </div>
         
        </div>
      </div>
    </div>
    <div class="container-fluid py-4">
      <div class="row">
        <div class="col-md-8">
          <form action="../updateProfile" method="post">
          <div class="card">
            <div class="card-header pb-0">
              <div class="d-flex align-items-center">
                <p class="mb-0">Editer le profil</p>
                <input class="btn btn-blue btn-sm ms-auto" type="submit" value="modifier "></input>
              </div>
            </div>
            <div class="card-body">
              <p class="text-uppercase text-sm">informations de l'utilisateur</p>
              <div class="row">
              
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Adresse e-mail</label>
                    <input class="form-control" type="email" value="<%=utilisateur.getEmail() %>" name="email" required>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Prénom</label>
                    <input class="form-control" type="text" value="<%=utilisateur.getPrenom() %>" name="firstName" disabled="disabled">
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Nom de famille</label>
                    <input class="form-control" type="text" value="<%=utilisateur.getNom() %>" name="lastName" disabled="disabled" >
                  </div>
                </div>
                <% if(utilisateur.getAge() != 0) {%>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Age</label>
                    <input class="form-control" type="text" value="<%=utilisateur.getAge()%>" name="age" >
                  </div>
                </div>
               <% } %>
                <div class="col-md-6">
                  <div class="form-group">
                      <label class="form-control-label"></label>
                      <div class="form-check form-check-inline">
                          <input class="form-check-input" type="radio" name="gender" id="male" value="1" <%= utilisateur.getSexe().equals("M") ? "checked" : "" %> disabled>
                          <label class="form-check-label" for="male" >Homme</label>
                      </div>
                      <div class="form-check form-check-inline">
                          <input class="form-check-input" type="radio" name="gender" id="female" value="1"<%= utilisateur.getSexe().equals("F") ? "checked" : "" %> disabled>
                          <label class="form-check-label" for="female" >Femme</label>
                      </div>
                  </div>
              </div>
              
              </div>
              <hr class="horizontal dark">
              <p class="text-uppercase text-sm">Coordonnées</p>
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Address</label>
                    <input class="form-control" type="text" value="<%=utilisateur.getAdresse() != null ? utilisateur.getAdresse():null %>" name="adr">
                  </div>
                </div>
                <!--<div class="col-md-4">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Ville</label>
                    <input class="form-control" type="text" value="New York" name="city">
                  </div>
                </div>-->
             
                
              </div>
              
              <hr class="horizontal dark">
              <p class="text-uppercase text-sm">Contacts</p>
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Numéro de téléphone</label>
                    <input class="form-control" type="text" value="<%=utilisateur.getPhone()!= null ? utilisateur.getPhone(): "undefined" %>" name ="phone">
                  </div>
                </div>
              </div>
              <hr class="horizontal dark">
              <p class="text-uppercase text-sm">Changer le mot passe</p>
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Mot de passe</label>
                    <input class="form-control" type="password" value="<%=utilisateur.getPassword()%>" name="password" required="required">
                  </div>
                  
                </div>
               <!--  <div class="col-md-12">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Valider Mot de passe</label>
                    <input class="form-control" type="password" value="" name="password"  required="required">
                  </div>
                  
                </div>  -->
              </div>
              <% if(utilisateur.getTypeUtil().equals("student")){ 
              Parent p = dao.getParentByEleve(utilisateur.getIdUtilisateur());
              
              
              %>
              <hr class="horizontal dark">
              <p class="text-uppercase text-sm">Informations sur les parents</p>
              <div class="row">
                <div class="col-md-12">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Adresse e-mail du père/mère</label>
                    <input class="form-control" type="email" value="<%= p.getAdresse() %>" name="emailP" disabled>
                  </div>
                  
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Nom de famille du père/mère</label>
                    <input class="form-control" type="text" value="<%= p.getNom() %>" name="lastNameP" disabled>
                  </div>
                  
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label for="example-text-input" class="form-control-label">Prénom du père/mère</label>
                    <input class="form-control" type="text" value="<%= p.getPrenom() %>" name="firstNameP" disabled>
                  </div>
                  
                </div>
                 
              </div>
             <%} %>
            </form>
            </div>
          </div>
        </div>


       </div>
  
<jsp:include page="configurations.jsp"></jsp:include>
