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
<%     //IDao dao = (IDao) getServletContext().getAttribute("sharedDao");
      IDao dao = new Dao();

 %>
    <jsp:include page="navBar.jsp"></jsp:include>
    

<main class="main-content position-relative border-radius-lg ">
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl " id="navbarBlur" data-scroll="false">
      <div class="container-fluid py-1 px-3">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-white" href="javascript:;">Pages</a></li>
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Présence</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Etudiants</h6>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
          <div class="ms-md-auto pe-md-3 d-flex align-items-center">
            
          </div>
          <ul class="navbar-nav  justify-content-end">
            <li class="nav-item d-flex align-items-center">
              <a href="../logoutCotroller" class="nav-link text-white font-weight-bold px-0">
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
                <div class="mb-3">
                <% int idGroup = Integer.parseInt(request.getParameter("idGroupe"));
                List<Seance> seancesOfGroupes = dao.getAllSeancesByGroupe(idGroup) ;%>
                  <select class="form-select" id="sessionSelect">
                  
                    <% for(Seance s : seancesOfGroupes){ %>
						<option value="<%=s.getIdSeance() %>" data-date="<%= s.getDateSeance().format(java.time.format.DateTimeFormatter.ofPattern("d MMMM uuuu")) %>">
                            <a href="#" class="session-link" data-idseance="<%=s.getIdSeance()%>"><small><%= s.getDateSeance().format(java.time.format.DateTimeFormatter.ofPattern("d MMMM uuuu")) + "  " +s.getHeureDebut() +" - "+ s.getHeureFin()%></small></a>
                        </option>
                    <% } %>
                  </select>
                </div>
            </div>
        </div>
       
        <div class="row">
          <div class="col-md-5 mt-4">
           <div class="card h-100 mb-4">
            <div class="card-header pb-0 px-3">

              
              <div class="row">
       
                <div class="col-md-6">
                  <h6 class="mb-0">Séance</h6>
                </div>
                <div class="col-md-6 d-flex justify-content-end align-items-center">
                  <i class="far fa-calendar-alt me-2"></i>
                   <small id="sessionDate"></small>
               </div>
             </div>
            </div>
            <div class="card-body pt-4 p-3">
              <h6 class="text-uppercase text-body text-xs font-weight-bolder mb-3">éleves</h6>
            <%   
            
                 List<Utilisateur> eleves = dao.getAllElevesByIdGroup(idGroup) ;%>
              <ul class="list-group">
                <% for(Utilisateur e : eleves){ %>
                <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                  <div class="d-flex align-items-center">
                    <div class="d-flex flex-column">
                      <h6 class="mb-1 text-dark text-sm"><%=e.getNom() + " " + e.getPrenom()%></h6>
                    </div>
                  </div>
                  <div class="d-flex align-items-center text-danger text-gradient text-sm font-weight-bold">
                   <!-- <a href="../registerAbsenceController?idStudent=<%=e.getIdUtilisateur()%>&idSeance=" class=" btn-sm ms-3">absent</a> -->
                          <a href="#" class="btn-sm ms-3 absence-link" data-idstudent="<%= e.getIdUtilisateur() %>">absent</a>
                </div>

                </li>
                <% } %>
                
              </ul>
            </div>
          </div>
        </div>
      </div>
      
    </div>
  </main>


<jsp:include page="configurations.jsp"></jsp:include>
 <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>

 

<script>
    // Function to handle click on the absence link
    function handleAbsenceLink(event) {
        event.preventDefault(); 
        var idStudent = this.getAttribute("data-idstudent");
        var idSeance = document.getElementById("sessionSelect").value; 
        var idGroup = <%= request.getParameter("idGroupe") %>; 
        
        // Check if the student is already marked as absent for the selected session
        isAbsent(idStudent, idSeance, idGroup,this);
    }

    // Function to check if the student is absent
    function isAbsent(idStudent, idSeance, idGroup, linkElement) {
        $.ajax({
            url: "../atendance/checkAbsentController",
            type: "GET",
            data: {idStudent: idStudent, idSeance: idSeance},
            success: function(result) {
                if (result == true) {
                    console.log("Student is already marked as absent."+ result);
                 // Disable the link
                    linkElement.classList.add("disabled");
                } else {
                    window.location.href = "../atendance/registerAbsenceController?idStudent=" + idStudent + "&idSeance=" + idSeance + "&idGroup=" + idGroup;
                }
            },
            error: function() {
                console.log("Error occurred while checking absence.");
            }
        });
    }
    
    

    // Add event listeners to each absence link
    var absenceLinks = document.querySelectorAll(".absence-link");
    absenceLinks.forEach(function(link) {
        link.addEventListener("click", handleAbsenceLink);
    });
</script>
<!--  
<script>
    function handleAbsenceLink(event) {
        event.preventDefault(); 
        var idStudent = this.getAttribute("data-idstudent");
        var idSeance = document.getElementById("sessionSelect").value; 
        var idGroup = <%= request.getParameter("idGroupe") %>; 
        
        isAbsent(idStudent, idSeance, idGroup);
        window.location.href = "../registerAbsenceController?idStudent=" + idStudent + "&idSeance=" + idSeance + "&idGroup=" + idGroup;    }

    var absenceLinks = document.querySelectorAll(".absence-link");
    absenceLinks.forEach(function(link) {
        link.addEventListener("click", handleAbsenceLink);
    });

    function handleSessionLink(event) {
        event.preventDefault();
        var idSeance = this.getAttribute("data-idseance");
        var sessionDate = this.getAttribute("data-sessiondate");
        document.getElementById("sessionDate").innerText = sessionDate; // Update session date
    }

    // Add event listeners to each session link
    var sessionLinks = document.querySelectorAll(".session-link");
    sessionLinks.forEach(function(link) {
        link.addEventListener("click", handleSessionLink);
    });
</script> -->