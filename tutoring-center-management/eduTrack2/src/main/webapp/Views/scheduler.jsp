<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="dao.*"%>
 <%@ page import="java.util.*" %>
<%@ page import="entities.*" %>
    
    <%
Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
   if (utilisateur == null) {
%>
<jsp:forward page="signIn.jsp"></jsp:forward>
<% }%>
<%     IDao dao = (IDao) getServletContext().getAttribute("sharedDao");

 %>
<jsp:include page="navBar.jsp"></jsp:include>


<div class="main-content position-relative max-height-vh-100 h-100">
    <!-- Navbar -->
    
    <!-- End Navbar -->
    <div class="card shadow-lg mx-4 card-profile-bottom">
      <div class="card-body p-3">
        <div class="row gx-4">
          <div class="col-auto">
          
          </div>
          
          <div class="col-auto my-auto">
            <div class="h-100">
              <h5 class="mb-1">
                Mon emplois du temps
              </h5>
             <!-- <p class="mb-0 font-weight-bold text-sm">
                Public Relations
              </p>-->
            </div>
          </div>
          <!-- corriger -->
          
          <div class="col-lg-8 col-md-6 my-sm-auto ms-sm-auto me-sm-0 mx-auto mt-3">
            <div class="nav-wrapper position-relative end-0">
         <%   if(utilisateur.getTypeUtil().equals("agent") || utilisateur.getTypeUtil().equals("professor")) {%> 
              <ul class="nav nav-pills nav-fill p-3" role="tablist">
             <%  if(utilisateur.getTypeUtil().equals("agent")){ %> 
                                                                     
                <li class="nav-item">
                  <a class="nav-link mb-0 px-0 py-1 active d-flex align-items-center justify-content-left btn-add-session " data-bs-toggle="tab" href="javascript:;" role="tab" aria-selected="true">
                    <i class="ni ni-fat-add"></i>
                    <span class="ms-2">Ajouter une session</span>
                  </a>
                </li>
                <%} %>
                
                <li class="nav-item">
                  <a class="nav-link mb-0 px-0 py-1 d-flex align-items-center justify-content-left btn-close-a-day  " data-bs-toggle="tab" href="javascript:;" role="tab" aria-selected="false" data-bs-target="#closeDayFormModal">
                    <i class="ni ni-calendar-grid-58"></i>
                    <span class="ms-2">Fermer un jour</span>
                  </a>
                </li>
                <li class="nav-item">
                  <a class="nav-link mb-0 px-0 py-1 d-flex align-items-center justify-content-left btn-close-hours" data-bs-toggle="tab" href="javascript:;" role="tab" aria-selected="false" data-bs-target="#closeHoursFormModal" >
                    <i class="ni ni-time-alarm"></i>

                    <span class="ms-2">Fermer quelques heures</span>
                  </a>
                </li>
              </ul>
              <%} %>
            </div>
          </div><!-- end corriger -->
        </div>
      </div>
    </div>
    <div class="container-fluid py-4">
      <div class="row">
        <div class="col-md-12">
          <div class="card">
            <div class="card-header pb-0">
              <div class="d-flex align-items-center">
                <p class="mb-0"></p>
              </div>
            </div>
            <div class="card-body">
             <!-- calendrier-->
                <div class="idance">
                    <div class="schedule content-block">
                        <div class="container">
                            <h2 data-aos="zoom-in-up" class="aos-init aos-animate"></h2>
                            
                          
            
                            <div class="timetable">
                           <nav class="nav nav-tabs" id="dayTabs">
						            <a class="nav-link active" onclick="showSessions('MONDAY')">Mon</a>
								    <a class="nav-link" onclick="showSessions('TUESDAY')">Tue</a>
								    <a class="nav-link" onclick="showSessions('WEDNESDAY')">Wed</a>
								    <a class="nav-link" onclick="showSessions('THURSDAY')">Thu</a>
								    <a class="nav-link" onclick="showSessions('FRIDAY')">Fri</a>
								    <a class="nav-link" onclick="showSessions('SATURDAY')">Sat</a>
								    <a class="nav-link" onclick="showSessions('SUNDAY')">Sun</a>
                           
                            </nav>
                            
                            <!--  <div class="tab-content" id="dayTabsContent">-->
                     <!-- The content will be dynamically updated here -->
                      <% Map<String, List<Seance>> seancesByDay =  new HashMap();
                      
                      seancesByDay = utilisateur.getTypeUtil().equals("agent") ? dao.getAllSeanceByCurrentWeekMap() :( utilisateur.getTypeUtil().equals("professor")? dao.getAllSeanceByCurrentWeekMapByIdProf(utilisateur.getIdUtilisateur()) : dao.getAllSeanceByCurrentWeekMapByIdStudent(utilisateur.getIdUtilisateur()));
                      //Map<String, List<Seance>> seancesByDay = dao.getAllSeanceByCurrentWeekMap();
                     
                      
                       for (Map.Entry<String, List<Seance>> entry : seancesByDay.entrySet()) {
                                        String dayOfWeek = entry.getKey();
                                        List<Seance> seances = entry.getValue();
                                        boolean hasSessions = !seances.isEmpty();
                                        %>
                            
                              <div class="tab-content">
                                    <div class="tab-pane show "  id="<%= dayOfWeek %>">
                                    
							    <% if (seances.isEmpty()) { %>
						        <div class="row">
						            <div class="col-md-12">
						                <p>Pas de seances</p>
						            </div>
						        </div>
						          <% } else { %>
										        
                                    
                                        <div class="row  ">
                                         <% for (Seance seance : seances) { %>
                                            <div class="col-md-6  ">
                                                 <div class="timetable-item <% if (seance.isAnnule()) { %>bg-danger text-white<% }  %>">
                                                   <div class="timetable-item-main">
                                                        <div class="timetable-item-time">  <%= seance.getHeureDebut() %> - <%= seance.getHeureFin() %></div>
                                                        <div class="timetable-item-name"><%= seance.getGroupe().getDescriptionGroupe()+seance.getGroupe().getIdGroupe()+" " + dao.getMatiereByGroupeId(seance.getGroupe().getIdGroupe()).getDescriptionMatiere()+ " "
                                                        +seance.getGroupe().getClasse().getNiveau().getDescriptionNiveau() %> </div>
                                                         <div class="timetable-item-name">salle <%= seance.getSalle().getIdSalle()%> </div>
                                                       <!-- 
                                                        <a href="#" class="btn btn-circle  btn-circle-orange  btn-update">
                                                          <i class=" fas fa-pencil-alt "></i>
                                                        </a> --> 
                                                        <%  if(utilisateur.getTypeUtil().equals("agent")){ %> 
                                                        <a href="../sessions/deleteSessionAgentController?idSession=<%= seance.getIdSeance() %>" class="btn btn-circle">
                                                          <i class="fas fa-trash "></i>
                                                        </a>
                                                        <%} %>
                                                       <%  if(utilisateur.getTypeUtil().equals("professor")&& !seance.isAnnule()){ %> 
                                                        <!--  this is for prof if he wanna close a seance -->
                                                        <a href="#" class="btn btn-circle  btn-delete" data-session-id="<%= seance.getIdSeance() %>">
                                                          <i class="fas fa-times "></i>
                                                        </a>
                                                          <%} %>
                                                                
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                           <% } %>
                                   
                                    </div>
                                   <% } %>
                                </div>
                                 <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            
              
              
              
            <!-- end calendrier-->
            <!-- Form for adding a session -->
    <div class="modal" tabindex="-1" role="dialog" id="addSessionFormModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Ajouter une séance</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="addSessionForm" method="post" action="../sessions/addSessionAgentController">
                        <div class="form-group">
                            <label for="startDate">Date </label>
                            <input type="date" class="form-control" id="startDate" name="startDate" required>
                        </div>
                      <!--  <div class="form-group">
                            <label for="endDate">Date de fin</label>
                            <input type="date" class="form-control" id="endDate" name="endDate" required>
                        </div>  -->
                        <div class="form-group">
                            <label for="startTime">Heure de début</label>
                            <input type="time" class="form-control" id="startTime" name="startTime" required>
                        </div>
                        <div class="form-group">
                            <label for="endTime">Heure de fin</label>
                            <input type="time" class="form-control" id="endTime" name="endTime" required>
                        </div>

                        <div class="form-group">
                            <label for="group">Groupe</label>
                            <select class="form-control" id="group" name="group" required>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="room">Salle</label>
                            <select class="form-control" id="room" name="room" required>
                            </select>
                        </div>

                        <button type="submit" class="btn bg-blue-midium">Confirmer</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
<!-- End of the add session form -->

<!-- form for update a session  -->
<div class="modal" tabindex="-1" role="dialog" id="modifierFormModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Modifier la séance</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Your form inputs here -->
                <form id="modifierForm" method="post" action="../updateSessionAgentController">
                    <div class="form-group">
                        <label for="startDate">Date </label>
                        <input type="date" class="form-control" id="startDate" name="startDate" required>
                    </div>
                    <!--  
                    <div class="form-group">
                        <label for="endDate">Date de fin</label>
                        <input type="date" class="form-control" id="endDate" name="endDate" required>
                    </div>-->
                    <div class="form-group">
                        <label for="startTime">Heure de début</label>
                        <input type="time" class="form-control" id="startTime" name="startTime" required>
                    </div>
                    <div class="form-group">
                        <label for="endTime">Heure de fin</label>
                        <input type="time" class="form-control" id="endTime" name="endTime" required>
                    </div>
                    <button type="submit" class="btn bg-blue-midium" >Confirmer</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!--end form for update session-->

<!-- form for close  a session  -->
<div class="modal" tabindex="-1" role="dialog" id="closeFormModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Férmer une séance</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Your form inputs here -->
                <form id="modifierForm" method="get" action="../sessions/closeSessionProfController">
                    <div class="form-group">
                        <label for="reason">Choisir une raison :</label>
                                        <select class="form-control" id="reason" name="reason" required>
                                            <option value="malade">Malade</option>
                                            <option value="urgence">Urgence personnelle</option>
                                            <option value="rendezvous">Rendez-vous imprévu</option>
                                            <option value="autre">Autre raisons</option>
                                            <!-- Add more options as needed -->
                                        </select>
                    </div>
                                            <!-- Input field for custom reason (initially hidden) -->

                    <div class="form-group" id="customReasonField" style="display: none;">
                        <label for="customReason">Autre raison :</label>
                        <input type="text" class="form-control" id="customReason" name="customReason">
                    </div>

                     <input type="hidden" id="idSession" name="idSession" value="">
                     
                    
                    <button type="submit" class="btn bg-blue-midium" >Confirmer</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!--end form for close the session-->

<div class="modal" tabindex="-1" role="dialog" id="closeDayFormModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Fermer un jour</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Your form inputs here -->
                <form id="closeDayForm" method="post" action="../sessions/closeDayController">
                    <div class="form-group">
                        <label for="closeDayDate">Date du jour à fermer</label>
                        <input type="date" class="form-control" id="closeDayDate" name="closeDayDate" required>
                    </div>
                    <button type="submit" class="btn bg-blue-midium">Confirmer</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- End of the close day form -->
<!-- Form for closing hours -->
<div class="modal" tabindex="-1" role="dialog" id="closeHoursFormModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Fermer quelques heures</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Your form inputs here -->
                <form id="closeHoursForm" method="post" action="../sessions/closeHoursController">
                 <div class="form-group">
                        <label for="closeHoursDay">Jour</label>
                        <input type="date" class="form-control" id="closeHoursDay" name="closeHoursDay" required>
                    </div>
                    <div class="form-group">
                        <label for="startTime">Heure de début</label>
                        <input type="time" class="form-control" id="startTime" name="startTime" required>
                    </div>
                    <div class="form-group">
                        <label for="endTime">Heure de fin</label>
                        <input type="time" class="form-control" id="endTime" name="endTime" required>
                    </div>
                   
                    <button type="submit" class="btn bg-blue-midium">Confirmer</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- End of the close hours form -->
            </div>
          </div>
        </div>
       
    </div>
  </div>
</div>
<jsp:include page="configurations.jsp"></jsp:include>
<!--js for script forms -->
  
       <!--this script is for when i choose others reasons-->
    <script>
        $(document).ready(function () {
            // Show/hide custom reason input based on selected option
            $('#reason').change(function () {
                if ($(this).val() === 'autre') {
                    $('#customReasonField').show();
                } else {
                    $('#customReasonField').hide();
                }
            });
        });
    </script>
      
    <!--this script is for update button-->

    <script>
        $(document).ready(function () {
            // Close the modal when the close button is clicked
        $(".close").click(function () {
            $("#modifierFormModal").modal("hide");
        });
            // Show the modal when the "Modifier" button is clicked
            $(".btn-update").click(function () {
                $('#modifierFormModal').modal('show');
            });
        });
    </script>
        <!--this script is for close a session-->
        <script>
            $(document).ready(function () {
                $(".close").click(function () {
            $("#closeFormModal").modal("hide");
        });
                // Show the modal when the "Modifier" button is clicked
                $(".btn-delete").click(function () {
                    $('#closeFormModal').modal('show');
                    var sessionId = $(this).data('session-id');
                    $('#idSession').val(sessionId);
                    $('#closeForm').submit();
                });
            });
        </script>
                <!--this script is for add a session-->



<!--  this script is for close a day button -->
<script >
$(document).ready(function () {
    // Close the modal when the close button is clicked
$(".close").click(function () {
    $("#closeDayFormModal").modal("hide");
});
    $(".btn-close-a-day").click(function () {
        $('#closeDayFormModal').modal('show');
    });
});

</script>
 <!-- Script for closing  hours -->
<script>
    $(document).ready(function () {
        $(".close").click(function () {
            $("#closeHoursFormModal").modal("hide");
        });

        $(".btn-close-hours").click(function () {
            $('#closeHoursFormModal').modal('show');
        });
    });
</script>

 
<!--  this is for the sessions  -->
 <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
 <script >
 // Function to fetch group data from servlet and populate the dropdown list
 function populateGroups() {
     var xhr = new XMLHttpRequest();
     xhr.open("GET", "../getAllGroupesServlet", true);
     xhr.onreadystatechange = function() {
         if (xhr.readyState === 4 && xhr.status === 200) {
             var groupSelect = document.getElementById("group");
             var groups = JSON.parse(xhr.responseText);
             groups.forEach(function(group) {
                 var option = document.createElement("option");
                 option.value = group.idGroupe;
                 option.text = group.descriptionGroupe + " " +  group.idGroupe + " - " + group.classe.niveau.descriptionNiveau; 
                 
                 groupSelect.appendChild(option);
             });
         }
     };
     xhr.send();
 }

 </script>
 
 
  <script >
 function populateRooms() {
     var xhr = new XMLHttpRequest();
     xhr.open("GET", "../salles/getAllSalles", true);
     xhr.onreadystatechange = function() {
         if (xhr.readyState === 4 && xhr.status === 200) {
             var roomSelect = document.getElementById("room");
             var rooms = JSON.parse(xhr.responseText);
             rooms.forEach(function(room) {
                 var option = document.createElement("option");
                 option.value = room.idSalle;
                 option.text =  " salle " +  room.idSalle; 
                 
                 roomSelect.appendChild(option);
             });
         }
     };
     xhr.send();
 }

 </script>
 <script>
   


    $(document).ready(function () {
        // Close the modal when the close button is clicked
        $(".close").click(function () {
            $("#addSessionFormModal").modal("hide");
        });
        // Show the modal when the "Ajouter une séance" button is clicked
        $(".btn-add-session").click(function () {
            $('#addSessionFormModal').modal('show');
            // Populate the select options dynamically when the modal is shown
            
            populateGroups();
            populateRooms();
        });

      
    });
</script>

<script>
    $(document).ready(function () {
        $("#addSessionForm").submit(function(event) {
            event.preventDefault(); // Prevent the form from submitting normally
            
            $.ajax({
                url: $(this).attr('action'),
                type: $(this).attr('method'),
                data: $(this).serialize(),
                dataType: 'json',
                success: function (result) {
                    if (result.hasOwnProperty('0')) {
                        var errorMessage = result['0'];
                        alert(errorMessage); // Display error message as an alert
                    } else {
                        location.reload(); // Reload the page if there are no errors
                    }
                },
                error: function (error) {
                    console.error('Error:', error);
                    location.reload(); // Reload the page in case of AJAX error
                }
            });
        });
    });
</script>

<script>
function showSessions(day) {
    // Convert day to uppercase to match the format in your navigation links
    var uppercaseDay = day.toUpperCase();

    // Hide all session content divs
    var sessionDivs = document.querySelectorAll('.tab-pane');
    sessionDivs.forEach(function(div) {
        div.classList.remove('show', 'active');
    });

    // Show the session content div for the selected day
    var selectedDayDiv = document.getElementById(uppercaseDay);
    if (selectedDayDiv) {
        selectedDayDiv.classList.add('show', 'active');
       
    }
}
</script>

 