<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="fr">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="icon" type="image/png" href="../assets/img/hat.png">
  <title>
  Sign Up
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
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
  
  <style>
        .form-step {
            display: none;
        }

        .form-step.active {
            display: block;
        }
        .hidden {
            display: none;
        }
    </style>
</head>

<body class="">
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg position-absolute top-0 z-index-3 w-100 shadow-none my-3 navbar-transparent mt-4">
    <div class="container">
      <a class="navbar-brand font-weight-bolder ms-lg-0 ms-3 text-white" href="home.jsp">EduTrack      </a>
      <button class="navbar-toggler shadow-none ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#navigation" aria-controls="navigation" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon mt-2">
          <span class="navbar-toggler-bar bar1"></span>
          <span class="navbar-toggler-bar bar2"></span>
          <span class="navbar-toggler-bar bar3"></span>
        </span>
      </button>
      <div class="collapse navbar-collapse" id="navigation">
        
       
      </div>
    </div>
  </nav>
  <!-- End Navbar -->
  <main class="main-content  mt-0">
    <div class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg" style="background-image: url('../assets/img/graduation.jpg'); background-position: top;">
      <span class="mask bg-gradient-dark opacity-6"></span>
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-5 text-center mx-auto">
            <h1 class="text-white mb-2 mt-5">EduTrack</h1>
            <p class="text-lead text-white">"L'éducation est l'arme la plus puissante que l'on puisse utiliser pour changer le monde."- Nelson Mandela.</p>
          </div>
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row mt-lg-n10 mt-md-n11 mt-n10 justify-content-center">
        <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
          <div class="card z-index-0">
            <div class="card-header text-center pt-4">
              <h5>S'inscrire</h5>
            </div>
          
            <div class="card-body">
                 <form id="signupForm"  action="../signUpController" method="post">
        <!-- Step 1: Name, Email, Password -->
        <div id="step1" class="form-step active">
            <div class="mb-3">
                <label for="name">Nom:</label>
                <input type="text" id="name" name ="name" class="form-control" placeholder="Name" aria-label="Name" required>
            </div>
            <div class="mb-3">
                <label for="name">Prenom:</label>
                <input type="text" id="name" name ="prenom" class="form-control" placeholder="Name" aria-label="Name" required>
            </div>
            <div class="mb-3">
                <label for="email">Email:</label>
                <input type="email" id="email" name ="email" class="form-control" placeholder="Email" aria-label="Email" required>
            </div>
            <div class="mb-3">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" aria-label="Password" required>
            </div>
            <div class="text-center">
                <button type="button" class="btn bg-gradient-dark w-100 my-4 mb-2 nextBtn"  >Suivant</button>
            </div>
        </div>

        <!-- Step 2: Student/Prof Selection -->
        <div id="step2" class="form-step">
            <label>User Type:</label>
            <div class="mb-3">
                <input type="radio" name="userType" value="student" id="studentRadio"  required>
                <label for="studentRadio" class="form-check-label">Étudiant</label>
            </div>
            <div class="mb-3">
                <input type="radio" name="userType" value="prof" id="profRadio"  required>
                <label for="profRadio" class="form-check-label">Professeur</label>
            </div>
            <button type="button" class="btn btn-midium-blue w-100 my-4 mb-2 prevBtn"  >Précédent</button>
            <button type="button" class="btn bg-gradient-dark w-100 my-4 mb-2 nextBtn" >Suivant</button>
        </div>

        <!-- Step 3: Additional Information -->
        <div id="step3" class="form-step">
            <!-- Student Fields -->
            <div id="studentFields" class="hidden">
     <div class="mb-3">
            <label for="age">Age:</label>
            <input type="text" id="age" class="form-control" name="age">
        </div>
          <div class="mb-3">
    <label for="childGender" class="form-label">Genre:</label>
    <div class="d-flex">
        <div class="form-check">
            <input class="form-check-input" type="radio" id="childGenderMale" name="childGender" value="M">
            <label class="form-check-label" for="childGenderMale">M</label>
        </div>
        <div class="form-check ms-3">
            <input class="form-check-input" type="radio" id="childGenderFemale" name="childGender" value="F">
            <label class="form-check-label" for="childGenderFemale">F</label>
        </div>
    </div>
</div>
        <div class="mb-3">
            <label for="Level" class="form-label"> Niveau:</label>
            <select id="Level" name="Level" class="form-select">
                
            </select>
        </div>
        <div class="mb-3">
            <label>Matière:</label>
                <div class="d-flex matiere-checkboxes">
            
            <div class="form-check">
            
            </div>
        
           
              </div>
        </div>
        <!-- Add other student fields -->
          <div class="mb-3">
            <label for="phone" class="form-label">Numero du telephone:</label>
            <input type="text" id="phone" class="form-control" name="phone">
        </div>
         <div class="mb-3">
            <label for="parentName" class="form-label">Nom de votre parent:</label>
            <input type="text" id="parentName" class="form-control" name="parentName">
        </div>
         <div class="mb-3">
            <label for="parentPrenome" class="form-label">Prenom de votre parent:</label>
            <input type="text" id="parentPrenome" class="form-control" name="parentPrenome">
        </div>
        
        <div class="mb-3">
            <label for="parentEmail" class="form-label">Email de votre parent:</label>
            <input type="email" id="parentEmail" class="form-control" name="parentEmail">
        </div>
        
        <div class="mb-3">
            <label for="parentAddress" class="form-label">Adresse de votre parent:</label>
            <input type="text" id="parentAddress" class="form-control" name="parentAddress">
        </div>
        
        
        
        <div class="mb-3">
    <label for="parentGender" class="form-label">Sexe du parent:</label>
    <div class="d-flex">
        <div class="form-check">
            <input class="form-check-input" type="radio" id="parentGenderMale" name="parentGender" value="M">
            <label class="form-check-label" for="parentGenderMale">M</label>
        </div>
        <div class="form-check ms-3">
            <input class="form-check-input" type="radio" id="parentGenderFemale" name="parentGender" value="F">
            <label class="form-check-label" for="parentGenderFemale">F</label>
        </div>
    </div>
</div>
    </div>
    <!-- Professor Fields -->
    <div id="profFields" class="hidden">
    
     <div class="mb-3">
        <label for="mainSpecialty" class="form-label">Spécialité principale:</label>
        <select id="mainSpecialty" class="form-select" name="mainSpecialty">
            <!-- Options will be dynamically added here -->
        </select>
    </div>
          <div class="mb-3 specialite-checkboxes">
        <label for="specialities" class="form-label">Spécialités:</label>
        <div class="form-check" id="specialitiesContainer">
            <!-- Specialities checkboxes will be dynamically added here -->
        </div>
    </div>
        
        <!-- Additional Professor Fields -->
      
        <div class="mb-3">
            <label for="adr" class="form-label">Adresse:</label>
            <input type="text" id="adr" class="form-control" name="adr">
        </div>
        
         <div class="mb-3">
            <label for="phone" class="form-label">Phone:</label>
            <input type="text" id="phone" class="form-control" name="phoneProf">
        </div>
        
        <div class="mb-3">
            <label for="ageProf">Age:</label>
            <input type="text" id="ageProf" class="form-control" name="ageProf">
        </div>
  <div class="mb-3">
    <label for="profGender" class="form-label">Sexe :</label>
    <div class="d-flex">
        <div class="form-check">
            <input class="form-check-input" type="radio" id="profGenderMale" name="profGender" value="M">
            <label class="form-check-label" for="profGenderMale">M</label>
        </div>
        <div class="form-check ms-3">
            <input class="form-check-input" type="radio" id="profGenderFemale" name="profGender" value="F">
            <label class="form-check-label" for="profGenderFemale">F</label>
        </div>
    </div>
</div>
        <!-- Add other prof fields -->
        
        
   <div class="mb-3">
    <label for="experience" class="form-label">Avec Expérience  :</label>
    <div class="d-flex">
        <div class="form-check">
            <input class="form-check-input" type="radio" id="experienceOui" name="experience" value="1">
            <label class="form-check-label" for="profGenderMale">Oui</label>
        </div>
        <div class="form-check ms-3">
            <input class="form-check-input" type="radio" id="experienceNo" name="experience" value="0">
            <label class="form-check-label" for="profGenderFemale">Non</label>
        </div>
    </div>
</div>
    </div>
    

            <button type="button" class="btn btn-midium-blue w-100 my-4 mb-2 prevBtn ">Précédent</button>
            <button type="button" class="btn bg-gradient-dark w-100 my-4 mb-2 nextBtn" >Suivant</button>
        </div>
        

        <!-- Step 4: Final Step -->
        <div id="stepFinal" >
        
           <input type="submit" class="btn bg-gradient-dark w-100 my-4 mb-2" value="Confirmer">
           
           
        </div>
                    <!--  <input type="submit" class="btn " value="Confirmer2">-->
        
          </form>
                              <p class="text-sm mt-3 mb-0">Vous avez déja  un compte? <a href="signIn.jsp" class="text-dark font-weight-bolder">Sign in</a></p>
              
            </div>
          </div>
        </div>
      </div>
    </div>
    
    
  </main>
 

<script>
$(document).ready(function () {
    var currentStep = 1;

    function showStep(step) {
        $('.form-step').removeClass('active');
        $('#step' + step).addClass('active');

        if (step === 2) {
            showStudentProfFields();
        }

        // Show/hide the "Confirmer" button in the final step
        var finalStep = 4;
        $('#stepFinal').toggleClass('hidden',step!==finalStep);

        console.log('Current Step:', step);
    }

    function showStudentProfFields() {
        var userType = $('input[name="userType"]:checked').val();
        console.log('User Type:', userType);

        var studentFields = $('#studentFields');
        var profFields = $('#profFields');

        if (userType === 'student') {
            studentFields.show();
            profFields.hide();
        } else if (userType === 'prof') {
            studentFields.hide();
            profFields.show();
        } else {
            studentFields.hide();
            profFields.hide();
        }

        console.log('Student Fields Hidden:', studentFields.is(':hidden'));
        console.log('Prof Fields Hidden:', profFields.is(':hidden'));
    }

    $('input[name="userType"]').on('change', function () {
        showStudentProfFields();
        console.log('User Type Changed. Current Step:', currentStep);
    });

    $('.nextBtn').on('click', function () {
        console.log(' Clicked. next:', currentStep);

        if (currentStep < 4) {  // Limit to the number of steps
            currentStep++;
            showStep(currentStep);
            console.log('Next Button Clicked. Current Step:', currentStep);
        }
    });

    $('.prevBtn').on('click', function () {
        if (currentStep > 1) {
            currentStep--;
            showStep(currentStep);
            console.log('Previous Button Clicked. Current Step:', currentStep);
        }
    });

    // Initial setup
    showStep(currentStep);
    showStudentProfFields();  
});

</script>

  <script>
    var win = navigator.platform.indexOf('Win') > -1;
    if (win && document.querySelector('#sidenav-scrollbar')) {
      var options = {
        damping: '0.5'
      }
      Scrollbar.init(document.querySelector('#sidenav-scrollbar'), options);
    }
  </script>
 <script>
    $(document).ready(function () {
        $.ajax({
            url: "../niveauController",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var niveauDropdown = $("#Level");
                niveauDropdown.empty();
                $.each(data, function (index, value) {
                    niveauDropdown.append($("<option>").text(value.descriptionNiveau).val(value.id));
                });
            }
        });
    });
</script>
<script>
    $(document).ready(function () {
        $("#Level").change(function () {
            var selectedNiveau = $(this).val();
            $.ajax({
                url: "../matieresServletController/matieres?niveau=" + selectedNiveau,
                type: "GET",
                dataType: "json",
                success: function (data) {
                    var matiereCheckboxes = $("#step3").find(".matiere-checkboxes");
                    // Clear existing checkboxes
                    matiereCheckboxes.empty();
                    $.each(data, function (index, value) {
                        matiereCheckboxes.append(
                            $("<div>").addClass("form-check").append(
                                $("<input>").addClass("form-check-input").attr({
                                    type: "checkbox",
                                    id: "matiere" + index,
                                    name: "matiere[]",
                                    value: value.id
                                }),
                                $("<label>").addClass("form-check-label").attr("for", "matiere" + index).text(value.nom)
                            )
                        );
                    });
                }
            });
        });
    });
</script>

<script>
    $(document).ready(function () {
        // Fetch specialities from the server
        $.ajax({
            url: "../matieresServletController/specialities",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var mainSpecialtyDropdown = $("#mainSpecialty");
                var specialitiesContainer = $("#specialitiesContainer");

               
            $.each(data, function (index, value) {
                mainSpecialtyDropdown.append(
                    $("<option>").attr({
                        value: value.id,
                        class: "main-speciality-option"
                    }).text(value.nom)
                );
            });


                $.each(data, function (index, value) {
                    specialitiesContainer.append(
                        $("<div>").addClass("form-check").append(
                            $("<input>").addClass("form-check-input speciality-checkbox").attr({
                                type: "checkbox",
                                id: "speciality" + index,
                                name: "speciality[]",
                                value: value.id
                            }),
                            $("<label>").addClass("form-check-label").attr("for", "speciality" + index).text(value.nom)
                        )
                    );
                });

                // Add change event listener to main speciality dropdown
                $("#mainSpecialty").change(function () {
                    var selectedMainSpecialty = $(this).val();
                 // Enable the previously selected main speciality checkbox
                    $(".speciality-checkbox[disabled]").prop("disabled", false);
                    // Disable the corresponding checkbox
                    $(".speciality-checkbox[value='" + selectedMainSpecialty + "']").prop("disabled", true);
                    // Uncheck all other checkboxes
                    $(".speciality-checkbox").not("[value='" + selectedMainSpecialty + "']").prop("checked", false);
                });
            },
            error: function (error) {
                console.error("Error fetching specialities:", error);
            }
        });
    });
    
    
    function getSelectedSpecialityIds() {
        var selectedSpecialityIds = [];
        $(".speciality-checkbox:checked").each(function () {
            var specialityId = $(this).val();
            selectedSpecialityIds.push(specialityId);
        });
        return selectedSpecialityIds;
    }
</script>
  <!--   Core JS Files   -->
  <script src="../assets/js/core/popper.min.js"></script>
  <script src="../assets/js/core/bootstrap.min.js"></script>
  
  
</body>

</html>    