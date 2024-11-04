<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.ArrayList"%>
<%@page import="java.time.ZoneId"%>
<%@page import="dao.*"%>
 <%@ page import="java.util.*" %>
<%@ page import="entities.*" %>


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

 


<main class="main-content position-relative border-radius-lg ">
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl " id="navbarBlur" data-scroll="false">
      <div class="container-fluid py-1 px-3">
        
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-white" href="javascript:;">Pages</a></li>
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Professeurs</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Professeurs</h6>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
          <div class="col-md-6 ms-md-auto pe-md-3 d-flex align-items-center">
            <form action="servlet.php" method="post">
            <div class="input-group">
             
              <button type="submit" class="input-group-text text-body">
                <i class="fas fa-search" aria-hidden="true"></i>
              </button>
              <!--<span class="input-group-text text-body"><i class="fas fa-search" aria-hidden="true"></i></span>-->
              <input type="text" class="form-control" placeholder="Type here...">
            </div>
          </form>
          </div>
          
          <ul class="navbar-nav  justify-content-end">
            <li class="nav-item d-flex align-items-center">
              <a href="../logoutCotroller" class="nav-link text-white font-weight-bold px-0">
                <i class="fas fa-sign-out-alt me-sm-1"></i>
                <span class="d-sm-inline d-none">se déconnecter</span>
              </a>
              
            </li>
            <li class="nav-item d-xl-none ps-3 d-flex align-items-center">
              <a href="javascript:;" class="nav-link text-white p-0" id="iconNavbarSidenav">
                <div class="sidenav-toggler-inner">
                  <i class="sidenav-toggler-line bg-white"></i>
                  <i class="sidenav-toggler-line bg-white"></i>
                  <i class="sidenav-toggler-line bg-white"></i>
                </div>
              </a>
            </li>
           
          </ul>
        </div>
      </div>
    </nav>
    <!-- End Navbar -->
    <div class="container-fluid py-4">
        <!--  table of  new inscriptions profs-->

      <div class="row">
        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">
              <h6>Nouvelle Inscriptions</h6>
            </div>
            <div class="card-body px-0 pt-0 pb-2">
              <div class="table-responsive p-0">
                <table class="table align-items-center mb-0">
                  <thead>
                    <tr>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Nom</th>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Spécialités</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Avec expérience</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Date d'inscription</th>
                      <th class="text-secondary opacity-7"></th>
                    </tr>
                  </thead>
                  <% List<Enseignant> profs = dao.getAllProfs();%>
                  
       <% List<Enseignant> profsNew = new ArrayList();
       List<Enseignant> profsInscrits = new ArrayList();
       
       for (Enseignant e : profs) {
           if (e.getDateValidation() == null) {
               profsNew.add(e);
           }else {
        	   profsInscrits.add(e);
        	   
           }
           
       }
       
       %>
                  <tbody>
                    <!-- les profs avec experiences -->
                 <% for(Enseignant e : profsNew){ %>
                 
                 
                    
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                         
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm"><%=e.getNom() + " " +e.getPrenom()  %></h6>
                            <p class="text-xs text-secondary mb-0"><%=e.getEmail() %></p>
                          </div>
                        </div>
                      </td>
                      <td>
                      <% for(EnseignantSpecialite es:dao.getEnseignantSpecialitesByEnseignantId(e.getIdEnseignant())){ %>
                         
                        <p class="text-xs <%= es.isMain() ? " font-weight-bold" : "text-secondary"%>  mb-0"><%= es.getSpecialite().getDescriptionMatiere() %></p>
                     <%} %>
                     
                      </td>
                      <td class="align-middle text-center text-sm">
                      
                        <span class="badge badge-sm <%=e.isAvecExperience() ? "bg-gradient-success" : "bg-gradient-danger" %>"><%=e.isAvecExperience() ? "oui" : "non" %></span>
                      </td>
                      <td class="align-middle text-center">
                        <span class="text-secondary text-xs font-weight-bold"><%=e.getDateCreation() %></span>
                      </td>
                      <td class="align-middle">
                        <a href="#" class="text-secondary font-weight-bold text-xs" onclick="validerProf(<%= e.getIdEnseignant() %>)">
                          accepter
                        </a>
                      </td>
                      
                    </tr>
                   
                        <%} %>                    <!-- end les profs avec experiences -->
                                            <!-- les profs sans experiences -->

              
               
   
                   
                                       
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
             <!-- end table of new inscriptions-->
                     <!--  table of  profs-->
                     <div class="row">
                        <div class="col-12">
                          <div class="card mb-4">
                            <div class="card-header pb-0">
                              <h6>Profs Inscrits</h6>
                            </div>
                            <div class="card-body px-0 pt-0 pb-2">
                              <div class="table-responsive p-0">
                                <table class="table align-items-center mb-0">
                                  <thead>
                                    <tr>
                                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Nom</th>
                                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Spécialités</th>
                                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7"> groupes</th>

                                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Date de validation</th>

                                      <th class="text-secondary opacity-7"></th>

                                    </tr>
                                  </thead>
                                  <tbody>
                                      <% for(Enseignant e : profsInscrits){ %>
                                  
                                    <tr>
                                      <td>
                                        <div class="d-flex px-2 py-1">
                                        
                                          <div class="d-flex flex-column justify-content-center">
                                            <h6 class="mb-0 text-sm"><%=e.getNom() + " " +e.getPrenom()  %></h6>
                                            <p class="text-xs text-secondary mb-0"><%=e.getEmail()%></p>
                                          </div>
                                        </div>
                                      </td>
                                      <td>
                                         <% for(EnseignantSpecialite es:dao.getEnseignantSpecialitesByEnseignantId(e.getIdEnseignant())){ %>
                         
                                        <p class="text-xs <%= es.isMain() ? " font-weight-bold" : "text-secondary"%>  mb-0"><%= es.getSpecialite().getDescriptionMatiere() %></p>
                                   <%} %>
                                      </td>
                                      
                                      <td class="align-middle text-center text-sm">
                                      <% List<Groupe> groupes = dao.getAllGroupesOfProf(e.getIdEnseignant()) ;
                                      if(groupes!= null && !groupes.isEmpty()){
                                      for(Groupe g : groupes ){
                                      %>
                                        <p class="text-secondary text-xs font-weight-bold"><%=g.getDescriptionGroupe() + " " +g.getIdGroupe()%></p>
                                       <%} }%>
                                      </td>
                                     
                                  
                                      <td class="align-middle text-center">
                                        <span class="text-secondary text-xs font-weight-bold"><%=e.getDateValidation() %></span>
                                      </td>
                                   
                                      <td class="align-middle">
                                        <a href="deleteProfController?idProf=<%=e.getIdEnseignant() %>" class="text-secondary font-weight-bold text-xs" >
                                          supprimer
                                        </a>
                                      </td>
                                    </tr>
                                    <%} %>
                                                     <!-- end tr -->

                                    
                                 
                                  </tbody>
                                </table>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <!--end of inscriptions profs -->

      
    </div>
  </main>

<jsp:include page="configurations.jsp"></jsp:include>


 <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
              
  <script >
    function validerProf(idProf) {
        jQuery.ajax({
            url: '../validerProf?idProf=' + idProf,
            type: 'GET',
            dataType: 'json',
            success: function (result) {
                handleResult(result);
            },
            error: function (error) {
                console.error('Error:', error);
                location.reload();

            }
        });
    }

    function handleResult(result) {
        if (result.hasOwnProperty('0')) {
            var errorMessage = result['0'];
            alert('' + errorMessage);
        } 
            location.reload();

        
    }
</script>