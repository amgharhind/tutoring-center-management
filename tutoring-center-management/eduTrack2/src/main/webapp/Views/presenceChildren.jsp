<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "entities.*" %>
    <%@ page import="dao.*" %>
    <% Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
   if (utilisateur == null) {
%>
<jsp:forward page="signIn.jsp"></jsp:forward>
<% }%>
<%    // IDao dao = (IDao) getServletContext().getAttribute("sharedDao");
    IDao dao = new Dao();

 %>
<jsp:include page="navBar.jsp"></jsp:include>
 <main class="main-content position-relative border-radius-lg " >
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl " id="navbarBlur" data-scroll="false">
      <div class="container-fluid py-1 px-3">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-white" href="javascript:;">Pages</a></li>
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Enfants</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Présence</h6>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
          <div class="ms-md-auto pe-md-3 d-flex align-items-center">
            
          </div>
          <ul class="navbar-nav  justify-content-end">
            <li class="nav-item d-flex align-items-center">
              <a href="../logoutCotroller?id=" class="nav-link text-white font-weight-bold px-0">
                <i class="fas fa-sign-out-alt me-sm-1"></i>
                <span class="d-sm-inline d-none">Se déconnecter</span>
              </a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
    <!-- End Navbar -->
    <div class="container-fluid py-4">
         <div class="row">
            <div class="col-md-5 mt-4">
                <!-- Dropdown to select a session -->
               
        </div>
       
        <div class="row">
          <div class="col-md-5 mt-4">
          <div class="card h-100 mb-4">
            <div class="card-header pb-0 px-3">

              
              <div class="row">
       <%  
       int idStudent  = Integer.parseInt(request.getParameter("idChild"));
       int idGroup = Integer.parseInt(request.getParameter("idGroup"));
       
       List<Seance> seances = dao.getAllSeancesDepuisInscriptionOfAstudent(idGroup,idStudent) ;%>
                <div class="col-md-6">
                  <h6 class="mb-0">Séances</h6>
                </div>
                <div class="col-md-6 d-flex justify-content-end align-items-center">
                  
                </div>
              </div>
            </div>
            <div class="card-body pt-4 p-3">
            <% if( seances.isEmpty()) { %> pas de séances <% } else { %>
              <ul class="list-group">
  <% for(Seance s : seances) {%>
               <% Boolean isAbsent  = dao.isAbsent(idStudent, s.getIdSeance()); %>
                <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                  <div class="d-flex align-items-center">
                    <div class="d-flex  justify-content-end align-items-center">
                        <i class="far fa-calendar-alt me-2"></i><small><%= s.getDateSeance().format(java.time.format.DateTimeFormatter.ofPattern("d MMMM uuuu")) + "  " +s.getHeureDebut() +" - "+ s.getHeureFin()%></small>

                    </div>
                  </div>
                <div class="d-flex align-items-center   <%= isAbsent ? " text-danger" : "text-success" %> text-gradient text-sm font-weight-bold">
                    <span class=" btn-sm ms-3"> <%= isAbsent ? " absent" : "present" %>   </span>
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
  </main>

<jsp:include page="configurations.jsp"></jsp:include>