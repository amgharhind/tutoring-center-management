<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import= "entities.*" %>
    <%@ page import="dao.*" %>
<%! IDao dao = new Dao();%>
<!DOCTYPE html>
<html lang="fr">

<head>

 
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="apple-touch-icon" sizes="76x76" href="../assets/img/hat.png">
  <link rel="icon" type="image/png" href="../assets/img/hat.png">
  <title>
    EduTrack
  </title>
  
  <!--     Fonts and icons     -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700" rel="stylesheet" />
  <!-- Nucleo Icons -->
  <link href="../assets/css/nucleo-icons.css" rel="stylesheet" />
  <link href="../assets/css/nucleo-svg.css" rel="stylesheet" />
  <!-- Font Awesome Icons -->
  
  <script src="https://kit.fontawesome.com/42d5adcbca.js" crossorigin="anonymous"></script>
  <link href="../assets/css/nucleo-svg.css" rel="stylesheet" />
  
  <!-- CSS Files -->
  <link id="pagestyle" href="../assets/css/argon-dashboard.css?v=2.0.4" rel="stylesheet" />
  <link id="pagestyle" href="../assets/scss/argon-dashboard.scss" rel="stylesheet" />
  
  
  
   <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
  <link href="../assets/css/agenda.css" rel="stylesheet" />
  
<style>
        /* Your custom styles */
        .nav-item {
            list-style: none;
            margin: 0;
            padding: 0;
        }

        .nav-link {
            text-decoration: none;
            color: #333;
            padding: 10px;
            display: block;
            cursor: pointer;
        }

        .nav-link:hover {
            background-color: #f0f0f0;
        }

        .children-list {
            display: none;
        }
    </style>
</head>

<body class="g-sidenav-show bg-gray-100" >



    <%
    getServletContext().setAttribute("sharedDao", dao);
   Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
   if(utilisateur==null){
%>

<jsp:forward page="signIn.jsp"></jsp:forward>
<%} %>
  <div class="position-absolute w-100 min-height-300 top-0" style="background-image: url(''); background-position-y: 50%;">
    <!-- this is the span where the color blue -->
    <span class="mask bg-blue-midium opacity-6"></span>
  </div>
  <aside class="sidenav bg-white navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-4 " id="sidenav-main">
    <div class="sidenav-header">
      <i class="fas fa-times p-3 cursor-pointer text-secondary opacity-5 position-absolute end-0 top-0 d-none d-xl-none" aria-hidden="true" id="iconSidenav"></i>
      <a class="navbar-brand m-0" href=" https://demos.creative-tim.com/argon-dashboard/pages/dashboard.html " target="_blank">
        <img src="../assets/img/hat.png" class="navbar-brand-img h-100" alt="main_logo">
        <span class="ms-1 font-weight-bold">EduTrack Profil</span>
      </a>
    </div>
    <hr class="horizontal dark mt-0">
    <div class="collapse navbar-collapse  w-auto " id="sidenav-collapse-main">
      <ul class="navbar-nav">
      <% if(utilisateur.getRolee().equals("admin")|| utilisateur.getTypeUtil().equals("directeur")){ %>
    	 
      
        <li class="nav-item">
          <a class="nav-link " href="dashboardDirector.jsp">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-tv-2 text-primary text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Dashboard</span>
          </a>
        </li>
        <% } %>
        <% if(utilisateur.getTypeUtil().equals("student")||utilisateur.getTypeUtil().equals("professor")||utilisateur.getTypeUtil().equals("agent")){ %>
        <li class="nav-item">
          <a class="nav-link " href="scheduler.jsp?UserType=<%=utilisateur.getTypeUtil()%>">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-calendar-grid-58 text-warning text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Mon emploi du temps</span>
          </a>
        </li>
         <% } %>
          <% if(utilisateur.getTypeUtil().equals("professor")||utilisateur.getTypeUtil().equals("parent")){ %>
     <li class="nav-item">
                <div class="nav-link" onclick="toggleChildrenList()">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                <i class="ni ni-bold-down text-success text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">présences / absences</span>
        </div>
         
        <% if(utilisateur.getTypeUtil().equals("parent")){ 
         List<Eleve> childrens = dao.getElevesByIdParent(utilisateur.getIdUtilisateur());
         if(!childrens.isEmpty()){
         
         %>
                <!-- List of children -->
                <ul class="children-list">
                <%  for(Eleve e : childrens){ 
                if(e.getDateValidation() != null){%>
                
                <%  Map<Integer , Matiere> map = dao.getAllMatieresAndGroupsIdOfEleve(e.getIdUtilisateur()); 
                Set<Integer> keys = map.keySet();
                String childName = e.getNom() +" " + e.getPrenom() ;
               for(Integer key : keys) {
                
                %>
                    <li><a class="nav-link" href="presenceChildren.jsp?idChild=<%=e.getIdUtilisateur()%>&idGroup=<%=key%>"><%= childName + "  - " + map.get(key).getDescriptionMatiere() %></a></li> 
                <% }  } }%>
                </ul>
                <% } %>
                <% }%>
        
       <% if(utilisateur.getTypeUtil().equals("professor")){
        List<Groupe> groupes = dao.getAllGroupesOfProf(utilisateur.getIdUtilisateur());
        	if(groupes!=null && !groupes.isEmpty()){%>
        		 <ul class="children-list">
                <%  for(Groupe g : groupes){ %>
                    <li><a class="nav-link" href="presenceStudents.jsp?idGroupe=<%=g.getIdGroupe()%>"><%=g.getDescriptionGroupe() + " " +g.getIdGroupe() %></a></li> 
                <% } %>
                </ul> 
        		 
        	<% }%>
       <% } %>
       
        	
   
            </li>
            <% } %>
    <% if(utilisateur.getTypeUtil().equals("professor")){ %>
        <li class="nav-item">
          <a class="nav-link " href="documentsProfs.jsp">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-cloud-upload-96 text-success text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Poser un document</span>
          </a>
        </li>
        <%} %>
        <%if(utilisateur.getTypeUtil().equals("student")){ %>
        <li class="nav-item">
         <div class="nav-link" onclick="toggleChildrenList()">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
                <i class="ni ni-books text-info text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Mes Documents</span>
        </div>
          
          <% List<Matiere> matieres = dao.getAllMatieresOfEleve(utilisateur.getIdUtilisateur()) ;%>
          <ul class="children-list">
                <%  for(Matiere m : matieres){ %>
                    <li><a class="nav-link" href="documentStudents.jsp?matiereId=<%=m.getIdMatiere()%>"><%=m.getDescriptionMatiere()+ " " + m.getNiveau().getDescriptionNiveau()%></a></li> 
                <% } %>
                </ul> 
        </li>
        <%} %>
         <%if(utilisateur.getRolee().equals("admin")){ %>
          <li class="nav-item">
          <a class="nav-link " href="profs.jsp">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-diamond text-success text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Professeurs</span>
          </a>
        </li>
         <li class="nav-item">
          <a class="nav-link " href="students.jsp">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-hat-3 text-primary text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Élèves</span>
          </a>
        </li>
        
        
          <li class="nav-item">
          <a class="nav-link " href="rooms.jsp">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-settings text-info text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Salles</span>
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link " href="levelsGroups.jsp">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-settings-gear-65 text-info text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Niveaux/Matières/Classes</span>
          </a>
        </li>
      <%} %>
        <li class="nav-item mt-3">
          <h6 class="ps-4 ms-2 text-uppercase text-xs font-weight-bolder opacity-6"></h6>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="profile.jsp">
            <div class="icon icon-shape icon-sm border-radius-md text-center me-2 d-flex align-items-center justify-content-center">
              <i class="ni ni-single-02 text-dark text-sm opacity-10"></i>
            </div>
            <span class="nav-link-text ms-1">Mon compte</span>
          </a>
        </li>
       
      </ul>
    </div>
    <div class="sidenav-footer mx-3 ">
      <div class="card card-plain shadow-none" id="sidenavCard">
        <div class="w-50 mx-auto" style="height: 100px;"></div>
      
      </div>
      <a href="home.jsp" target="_blank" class="btn btn-midium-blue btn-sm w-100 mb-3">Home</a>
      <a class="btn btn-blue btn-sm mb-0 w-100" href="../logoutCotroller" type="button">Se déconnecter</a>
    </div>
  </aside>
  <!--  -->

