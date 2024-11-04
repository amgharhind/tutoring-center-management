<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
     <%@ page import = "entities.*" %>
    <%@ page import="dao.*" %>
     <%
Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
   if (utilisateur == null) {
%>
<jsp:forward page="signIn.jsp"></jsp:forward>
<% }%>
<%     IDao dao = (IDao) getServletContext().getAttribute("sharedDao");

 %>
    <jsp:include page="navBar.jsp"></jsp:include>
     <% 
   
    
     int matiereId = Integer.parseInt(request.getParameter("matiereId"));%>
    
     <main class="main-content position-relative border-radius-lg ">
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl " id="navbarBlur" data-scroll="false">
      <div class="container-fluid py-1 px-3">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-white" href="javascript:;">Pages</a></li>
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Documents</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Mes Documents</h6>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
          <div class="ms-md-auto pe-md-3 d-flex align-items-center">
            <div class="input-group">
             
            </div>
          </div>
                <ul class="navbar-nav  justify-content-end">
            <li class="nav-item d-flex align-items-center">
              <a href="../logoutCotroller" class="nav-link text-white font-weight-bold px-0">
                <i class="fas fa-sign-out-alt me-sm-1"></i>
                <span class="d-sm-inline d-none">se déconnecter</span>
              </a>
            </li>
            
          </ul>
        </div>
      </div>
    </nav>
    <!-- End Navbar -->
    <div class="container-fluid py-4">
    
      <div class="row">
       <!-- start of the docs cours-->
        <div class="col-lg-6">
          <div class="card h-100">
            <div class="card-header pb-0 p-3">
              <div class="row">
                <div class="col-6 d-flex align-items-center">
                  <h6 class="mb-0">Cours</h6>
                </div>
                
              </div>
            </div>
            <div class="card-body p-3 pb-0">
            <% List<Document> cours = dao.getCoursDocumentsByMatiereIdAndStudent(matiereId, utilisateur.getIdUtilisateur()); 
              
              if (cours.isEmpty()) { %>
                <p class="text-muted">Pas de documents disponibles.</p>
            <% } else { %>
              <ul class="list-group">
              <% for (Document document : cours) { %>
                <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                  <div class="d-flex flex-column">
                    <h6 class="mb-1 text-dark font-weight-bold text-sm"><%= document.getNomDoc() %></h6>
                      <span class="text-xs"><%= document.getDate_de_creation().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss")) %></span> 
                  </div>
                  <div class="d-flex align-items-center text-sm">
                    <a href="../documents/<%= document.getNomDoc() %>" target="_blank" class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><i class="fas fa-file-pdf text-lg me-1"></i> PDF</a>
                  </div>
                </li>
                <% } %>
              </ul>
               <% } %>
            </div>
          </div>
        </div>
        <!-- end of the col -->
        <!-- start of the docs devoir-->
        <div class="col-lg-6">
            <div class="card h-100">
              <div class="card-header pb-0 p-3">
                <div class="row">
                  <div class="col-6 d-flex align-items-center">
                    <h6 class="mb-0">Devoirs</h6>
                  </div>
                  
                </div>
              </div>
              <div class="card-body p-3 pb-0">
             <% List<Document> devoirs = dao.getDevoirsDocumentsByMatiereIdAndStudent(matiereId, utilisateur.getIdUtilisateur()) ;%>
              <% if (devoirs.isEmpty()) { %>
                <p class="text-muted">Pas de documents disponibles.</p>
            <% } else { %>
                <ul class="list-group">
                <%  for (Document doc : devoirs ) {%>
                  <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                    <div class="d-flex flex-column">
                      <h6 class="mb-1 text-dark font-weight-bold text-sm"><%= doc.getNomDoc() %></h6>
                       <span class="text-xs"><%= doc.getDate_de_creation().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss")) %></span> 
                    </div>
                    <div class="d-flex align-items-center text-sm">
                      
                    <a href="../documents/<%= doc.getNomDoc() %>" target="_blank" class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><i class="fas fa-file-pdf text-lg me-1"></i> PDF</a>
                    </div>
                  </li>
                  <% } %>
                </ul>
                 <% } %>
              </div>
            </div>
          </div>
      </div>
    </div>
    <!-- fin -->

  </main>
    <jsp:include page="configurations.jsp"></jsp:include>
    