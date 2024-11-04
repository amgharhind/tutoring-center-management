<%@page import="java.util.ArrayList"%>
<%@page import="java.time.ZoneId"%>
<%@page import="dao.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<%@ page import="entities.*" %>
<%@ page import="tools.*" %>


  <%
Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
    if (utilisateur == null) {
%>
<jsp:forward page="signIn.jsp"></jsp:forward>
<%} %>
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
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Etudiants</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Etudiants</h6>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
          <div class="col-md-6 ms-md-auto pe-md-3 d-flex align-items-center">
            <form action="servlet.php" method="post">
            <div class="input-group">
             
              <button type="submit" class="input-group-text text-body">
                <i class="fas fa-search" aria-hidden="true"></i>
              </button>
              <input type="text" class="form-control" placeholder="Chercher...">
            </div>
          </form>
          </div>
          
          <ul class="navbar-nav  justify-content-end">
            <li class="nav-item d-flex align-items-center">
              <a href="../logoutCotroller" class="nav-link text-white font-weight-bold px-0">
                <i class="fa fa-sign-out-alt me-sm-1"></i>
                <span class="d-sm-inline d-none">Se déconnecter</span>
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
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Nom et Prenom</th>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Matières</th>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Parent</th>
                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Niveau</th>

                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Date d'inscription</th>
                      <th class="text-secondary opacity-7"></th>
                    </tr>
                  </thead>
                  <% List<Eleve> eleves = dao.getAllEleves();%>
                  
       <% List<Eleve> elevesNew = new ArrayList();
       List<Eleve> elevesInscrits = new ArrayList();
       
       for (Eleve e : eleves) {
           if (e.getDateValidation() == null) {
               elevesNew.add(e);
           }else {
        	   elevesInscrits.add(e);
        	   
           }
           
       }
       
       %>
                  
                  <tbody>
                    <% for( Eleve e : elevesNew){ %>
                    <%  List<Matiere> matieres = dao.getAllMatieresOfEleve(e.getIdEleve());
                    String niveauName = "noMatieres";

                    if (!matieres.isEmpty()) {
                        Matiere firstMatiere = matieres.get(0);
                        if (firstMatiere.getNiveau() != null) {
                            niveauName = firstMatiere.getNiveau().getDescriptionNiveau();
                        }
                    }                    %>
                    <tr>
                      <td>
                        <div class="d-flex px-2 py-1">
                          <div class="d-flex flex-column justify-content-center">
                            <h6 class="mb-0 text-sm"><%=e.getNom()+ " "+ e.getPrenom() %> </h6>
                            <p class="text-xs text-secondary mb-0"><%= e.getEmail() %></p>

                          </div>
                        </div>
                      </td>
                      
                      <td>
                      <% for(Matiere matiere : matieres ){ %>
                        <p class="text-xs font-weight-bold mb-0"><%= matiere.getDescriptionMatiere() %> </p>
                      <%} %>
                      </td>
                      <td>
                        <p class="text-xs font-weight-bold mb-0"><%= e.getParent().getNom()+ " " + e.getParent().getPrenom() %></p>
                        <p class="text-xs text-secondary mb-0"><%=e.getParent().getEmail()%></p>
                      </td>
                      <td class="align-middle text-center text-sm">
                        <span class="badge badge-sm bg-gradient-success"><%=niveauName %></span>
                      </td>
                      
                      <td class="align-middle text-center">
                        <span class="text-secondary text-xs font-weight-bold"><%= e.getDateCreation() %></span>
                      </td>
                      <td class="align-middle">
                        <a href="../validerStudent?idStudent=<%= e.getIdEleve() %>" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
                          valider
                        </a>
                      </td>
                    </tr>
                                                                <!-- end of row -->

                    <tr>
                       
                 <%} %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>

      
       

             <!-- end table of new inscriptions-->
                     <!--  table of  students-->
                     <div class="row">
                     
                        <div class="col-12">
                          <div class="card mb-4">
                            <!-- filtrer-->

                            <div class="col-12">
                              <div class="card mb-4">
                                  <div class="card-header pb-0">
                                      <div class="d-flex justify-content-between">                
                                          <div class="dropdown">
                                             <!-- <button class="btn btn-dark dropdown-toggle" type="button" id="filterDropdown" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                  Filtrer
                                              </button>-->
                                             <!--<div class="dropdown-menu" aria-labelledby="filterDropdown">
                                                  <form class="px-4 py-3" action="filterController" method="post">
                                                      <div class="mb-3">
                                                          <label for="niveau" class="form-label">Niveau:</label>
                                                          <select class="form-select" id="niveau" name="niveau" onchange="populateMatiere()">
                                                              <option value="">Choisir un niveau</option>
                                                              <option value="niveau1">Niveau 1</option>
                                                              <option value="niveau2">Niveau 2</option>
                                                              <option value="niveau3">Niveau 3</option>
                                                          </select>
                                                      </div>
                      
                                                      <div class="mb-3">
                                                          <label for="matiere" class="form-label">Matiere:</label>
                                                          <select class="form-select" id="matiere" name="matiere" onchange="populateGroup()">
                                                              <option value="">Choisir une Matiere</option>
                                                          </select>
                                                      </div>
                      
                                                      <div class="mb-3">
                                                          <label for="group" class="form-label">Group:</label>
                                                          <select class="form-select" id="group" name="group">
                                                              <option value="">Choisir Group</option>
                                                          </select>
                                                      </div>
                      
                                                      
                      
                                                      <button type="button" class="btn btn-dark">chercher</button>
                                                  </form>
                                              </div>  --> 
                                          </div>
                                      </div>
                                  </div>
                            <div class="card-header pb-0">
                              <h6>Etudiants inscrits</h6>
                            </div>
                            <div class="card-body px-0 pt-0 pb-2">
                              <div class="table-responsive p-0">
                                <table class="table align-items-center mb-0">
                                  <thead>
                                    <tr>
                                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Nom et Prenom</th>
                                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Matières</th>
                                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Parent</th>
                                     <!--   <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">nombre séances assistés</th>

                                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">nombre d'absences </th>-->
                                      <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">Date de validation</th>

                                      <th class="text-secondary opacity-7"></th>
                                      <th class="text-secondary opacity-7"></th>
                                      <!-- <th class="text-secondary opacity-7"></th>  -->


                                    </tr>
                                  </thead>
                
                                  <tbody>
                                  <% for(Eleve e:elevesInscrits){ %>
                                    <tr>
                                      <td>
                                        <div class="d-flex px-2 py-1">
                                          
                                          <div class="d-flex flex-column justify-content-center">
                                            <h6 class="mb-0 text-sm"><%=e.getNom() + " " +e.getPrenom()%> </h6>
                                            <p class="text-xs text-secondary mb-0"><%=e.getEmail()%> </p>
                                          </div>
                                        </div>
                                      </td>
                                      <% List<Matiere> matieres = dao.getAllMatieresOfEleve(e.getIdEleve());%>
                                      <td>
                                      <%for(Matiere m: matieres){ %>
                                        <p class="text-xs font-weight-bold mb-0"><%=m.getDescriptionMatiere() %> </p>
                                         <% if (dao.isPayThisMonth(e.getIdEleve(), m.getIdMatiere())==0) { %>
                                        <a href="../payStudentController?idStudent=<%=e.getIdEleve() %>&idMatiere=<%=m.getIdMatiere() %>" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" >
                                          payer
                                        </a>
                                        <% } else { %>
									        <span class="text-secondary font-weight-bold text-xs">Non dû pour le paiement</span>
									    <% } %>
									    
                                        <%} %>
                                      </td>
                                      
                                      
                                      <td>
                                        <p class="text-xs font-weight-bold mb-0"><%=e.getParent().getNom() + " " +e.getParent().getPrenom()%> </p>
                                        <p class="text-xs text-secondary mb-0"><%=e.getParent().getEmail() %> </p>
                                      </td>
                                     <!--  <td class="align-middle text-center text-sm">
                                        <span class="text-secondary text-xs font-weight-bold">2</span>
                                      </td>
                                      <td class="align-middle text-center text-sm">
                                        <span class="text-secondary text-xs font-weight-bold">1</span>
                                      </td> -->
                                      <td class="align-middle text-center">
                                      <% dateUtils util = new dateUtils() ;%>
                                        <span class="text-secondary text-xs font-weight-bold"><%= e.getDateValidation().getDayOfMonth() +"/"+  e.getDateValidation().getMonthValue() +"/"+e.getDateValidation().getYear() + "  " + e.getDateValidation().getHour() +":"+
                                      e.getDateValidation().getMinute()  %>
                                        
                                        </span>
                                      </td>
                                      <!--
                                      <td class="align-middle">
                                        <a href="../payStudentController?idStudent=<%=e.getIdEleve() %>" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" >
                                          payer
                                        </a>
                                      </td>
                                        -->
                                      <td class="align-middle">
                                         <a href="#" class="text-secondary font-weight-bold text-xs ajouterMatiereLink" data-toggle="tooltip" data-target="#ajouterMatiereModal" data-student-id="<%= e.getIdEleve() %>">
                                           ajouter matière </a>
                  
                                      </td>
                                      <td class="align-middle">
                                        <a href="../deleteStudent?idStudent=<%=e.getIdEleve() %>" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip">
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
<!--  start   the second tables -->
<!-- -->
      <!--  end of  the second table -->

      
    </div>
                          
                                        <!-- Modal for "Ajouter Matiere" -->
  <div class="modal fade" id="ajouterMatiereModal" tabindex="-1" role="dialog" aria-labelledby="ajouterMatiereModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="ajouterMatiereModalLabel">Ajouter Matiere</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
				    <div class="form-group">
				        <label for="matiereSelect">Choisir une Matiere:</label>
				        <select class="form-control" id="matiereSelect"></select>
				    </div>
				    <button type="button" class="btn btn-primary" id="ajouterMatiereBtn">Ajouter</button>
              </form>

            </div>
            
        </div>
    </div>
</div>
  </main>
  
    <script>
    // Sample data for demonstration purposes
    const matiereData = {
        niveau1: ['Matiere 1A', 'Matiere 1B'],
        niveau2: ['Matiere 2A', 'Matiere 2B'],
        niveau3: ['Matiere 3A', 'Matiere 3B']
    };

    const groupData = {
        'Matiere 1A': ['Group 1A1', 'Group 1A2'],
        'Matiere 1B': ['Group 1B1', 'Group 1B2'],
        'Matiere 2A': ['Group 2A1', 'Group 2A2'],
        'Matiere 2B': ['Group 2B1', 'Group 2B2'],
        'Matiere 3A': ['Group 3A1', 'Group 3A2'],
        'Matiere 3B': ['Group 3B1', 'Group 3B2']
    };

    function populateMatiere() {
        const niveauSelect = document.getElementById('niveau');
        const matiereSelect = document.getElementById('matiere');
        const selectedNiveau = niveauSelect.value;

        // Clear previous options
        matiereSelect.innerHTML = '<option value="">Choisir une Matiere</option>';

        if (selectedNiveau && matiereData[selectedNiveau]) {
            matiereData[selectedNiveau].forEach(matiere => {
                const option = document.createElement('option');
                option.value = matiere;
                option.textContent = matiere;
                matiereSelect.appendChild(option);
            });
        }
    }

    function populateGroup() {
        const matiereSelect = document.getElementById('matiere');
        const groupSelect = document.getElementById('group');
        const selectedMatiere = matiereSelect.value;

        // Clear previous options
        groupSelect.innerHTML = '<option value="">Choisir un Groupe</option>';

        if (selectedMatiere && groupData[selectedMatiere]) {
            groupData[selectedMatiere].forEach(group => {
                const option = document.createElement('option');
                option.value = group;
                option.textContent = group;
                groupSelect.appendChild(option);
            });
        }
    }
</script>

<script>

    document.addEventListener("DOMContentLoaded", function() {
        $("#ajouterMatiereLink").click(function() {
            var selectHtml = '<select class="form-select">';
            selectHtml += '<option value="">Select Matiere</option>';
            for (var i = 0; i < matieresArray.length; i++) {
                selectHtml += '<option value="' + matieresArray[i] + '">' + matieresArray[i] + '</option>';
            }
            selectHtml += '</select>';

            // Display the form with the select element
            $("#matiereSelect").html(selectHtml);
            $("#ajouterMatiereModal").modal("show");  // Show the modal
        });
    });
</script>

  <!-- end of the filter script-->
  
  
 <script>
    document.addEventListener('DOMContentLoaded', function() {
        // When "ajouter matière" is clicked, populate and show the modal
        document.querySelectorAll('.ajouterMatiereLink').forEach(function(link) {
            link.addEventListener('click', function() {
                var studentId = this.getAttribute('data-student-id');

                //var studentId = this.dataset.studentId;

                // Fetch matières data
                fetch('../addMatiere/fetchAllMatieres?studentId=' + studentId)
                    .then(function(response) {
                        if (!response.ok) {
                            throw new Error('Network response was not ok');
                        }
                        return response.json();
                    })
                    .then(function(data) {
                        // Clear previous options
                        var matiereSelect = document.getElementById('matiereSelect');
                        matiereSelect.innerHTML = '';

                        // Populate the dropdown list with fetched matières data
                        data.forEach(function(matiere) {
                            var option = document.createElement('option');
                            option.value = matiere.id;
                            option.text = matiere.nom;
                            matiereSelect.appendChild(option);
                        });

                        // Show the modal after fetching matières
                        var modal = document.getElementById('ajouterMatiereModal');
                        var bootstrapModal = new bootstrap.Modal(modal);
                        bootstrapModal.show();
                    })
                    .catch(function(error) {
                        console.error('Error fetching matières:', error);
                    });
            });
        });

        // Event listener for "Ajouter" button
        document.getElementById('ajouterMatiereBtn').addEventListener('click', function() {
            // Handle adding matière to the student here
            // You can retrieve selected matière ID using document.getElementById('matiereSelect').value
            // and send it to the server via another fetch call
        });
    });
</script>
<jsp:include page="configurations.jsp"></jsp:include>