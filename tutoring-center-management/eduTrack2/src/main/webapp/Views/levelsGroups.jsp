<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:include page="navBar.jsp"></jsp:include>
 <main class="main-content position-relative border-radius-lg ">
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl " id="navbarBlur" data-scroll="false">
      <div class="container-fluid py-1 px-3">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-white" href="javascript:;">Pages</a></li>
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Niveaux Matieres Groupes</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Niveaux Matieres Groupes</h6>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
          <div class="ms-md-auto pe-md-3 d-flex align-items-center">
            <div class="input-group">
              
            </div>
          </div>
          <ul class="navbar-nav  justify-content-end">
            <li class="nav-item d-flex align-items-center">
              <a href="../logoutController?id=" class="nav-link text-white font-weight-bold px-0">
                <i class="fas fa-sign-out-alt me-sm-1"></i>
                <span class="d-sm-inline d-none">Se déconnecter</span>
              </a>
            </li>
           
            
            
          </ul>
        </div>
      </div>
    </nav>
    <!-- End Navbar -->
    <!-- this is the part-->
    <div class="container-fluid py-4">
      <div class="row">
        <div class="col-lg-12">
          <div class="row">
            
            
            <!-- the col of buttons -->
            <div class="col-md-12 mb-lg-0 mb-4">
              <div class="card mt-4">
                
                <div class="card-body p-3">
                  <div class="row">
                    <div class="col-4 md-6">
                      <a class="btn bg-gradient-dark mb-0" href="#" data-bs-toggle="modal" data-bs-target="#niveauModal"><i class="fas fa-plus"></i>&nbsp;&nbsp;Ajouter un niveau</a>
                    </div>
                    <div class="col-4 md-6">
                      
                      <a class="btn bg-gradient-dark mb-0" href="#" data-bs-toggle="modal" data-bs-target="#subjectModal"><i class="fas fa-plus"></i>&nbsp;&nbsp;Ajouter une matiere</a>
                    </div>
                    <div class="col-4 md-6">
                      <a class="btn bg-gradient-dark mb-0" href="#" data-bs-toggle="modal" data-bs-target="#groupModal"><i class="fas fa-plus"></i>&nbsp;&nbsp;Ajouter un groupe</a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
             <!-- end the table of buttons -->
          </div>
        </div>
         <!-- the table of pdfs -->
       
      </div>

      <!--my tables of this -->
      <div class="row">
              <!--start of col -->

        <div class="col-md-6 mt-4">
          <div class="card">
            <div class="card-header pb-0 px-3">
              <h6 class="mb-0">Niveau</h6>
            </div>
            <!--- start of matiere-->
            <div class="card-body pt-4 p-3">
              <ul class="list-group">
                            <!--- start of group-->

                <li class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
                  <div class="d-flex flex-column">
                    <h6 class="mb-3 text-sm">Matiere 1</h6>
                   <span> <a href="google.com"  class="text-dark ms-sm-2 font-weight-bold">group 1 :</a> <span class="mb-3 text-sm">nom  du prof</span></span>
                    
                  </div>
                  <div class="ms-auto text-end">
                    <a class="btn btn-link text-danger text-gradient px-3 mb-0" href="deleteSubjectController?idSubject="><i class="far fa-trash-alt me-2"></i>Delete</a>
                   <!--<a class="btn btn-link text-dark px-3 mb-0" href="javascript:;"><i class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i>Edit</a>--> 
                  </div>
                </li>
                
                 <!--- end of group-->
              </ul>
            </div>
            <!--- end of matiere-->
          </div>
        </div>
              <!--end of col -->
              <div class="col-md-6 mt-4">
                <div class="card">
                  <div class="card-header pb-0 px-3">
                    <h6 class="mb-0">Niveau</h6>
                  </div>
                  <!--- start of matiere-->
                  <div class="card-body pt-4 p-3">
                    <ul class="list-group">
                                  <!--- start of group-->
      
                      <li class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
                        <div class="d-flex flex-column">
                          <h6 class="mb-3 text-sm">Matiere 1</h6>
                         <span> <a href="google.com"  class="text-dark ms-sm-2 font-weight-bold">group 1 :</a> <span class="mb-3 text-sm">nom  du prof</span></span>
                          
                        </div>
                        <div class="ms-auto text-end">
                          <a class="btn btn-link text-danger text-gradient px-3 mb-0" href="javascript:;"><i class="far fa-trash-alt me-2"></i>Delete</a>
                         <!--<a class="btn btn-link text-dark px-3 mb-0" href="javascript:;"><i class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i>Edit</a>--> 
                        </div>
                      </li>
                      
                       <!--- end of group-->
                    </ul>
                  </div>
                  <!--- end of matiere-->
                </div>
              </div>
                    <!--end of col -->

                    <div class="col-md-6 mt-4">
                        <div class="card">
                          <div class="card-header pb-0 px-3">
                            <h6 class="mb-0">Niveau</h6>
                          </div>
                          <!--- start of matiere-->
                          <div class="card-body pt-4 p-3">
                            <ul class="list-group">
                                          <!--- start of group-->
              
                              <li class="list-group-item border-0 d-flex p-4 mb-2 bg-gray-100 border-radius-lg">
                                <div class="d-flex flex-column">
                                  <h6 class="mb-3 text-sm">Matiere 1</h6>
                                 <span> <a href="google.com"  class="text-dark ms-sm-2 font-weight-bold">group 1 :</a> <span class="mb-3 text-sm">nom  du prof</span></span>
                                  
                                </div>
                                <div class="ms-auto text-end">
                                  <a class="btn btn-link text-danger text-gradient px-3 mb-0" href="javascript:;"><i class="far fa-trash-alt me-2"></i>Delete</a>
                                 <!--<a class="btn btn-link text-dark px-3 mb-0" href="javascript:;"><i class="fas fa-pencil-alt text-dark me-2" aria-hidden="true"></i>Edit</a>--> 
                                </div>
                              </li>
                              
                               <!--- end of group-->
                            </ul>
                          </div>
                          <!--- end of matiere-->
                        </div>
                      </div>
                            <!--end of col -->

        
      </div>
     
    </div>
        <!-- this is the part-->
        <!-- forms -->
                <!-- ajouter un niveau-->

        <div class="modal fade" id="niveauModal" tabindex="-1" aria-labelledby="niveauModalLabel" aria-hidden="true">
          <div class="modal-dialog">
              <div class="modal-content">
                  <div class="modal-header">
                      <h5 class="modal-title" id="niveauModalLabel">Ajouter un niveau</h5>
                      <button type="button" class="btn-close bg-blue-midium " data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">
                      <!-- Your form content goes here -->
                      <form method="post" action="../addlevelController">
                          <div class="mb-3">
                              <label for="niveauName" class="form-label">Nom du niveau</label>
                              <input type="text" class="form-control" id="niveauName" required>
                          </div>
                          <button type="submit" class="btn btn-midium-blue">ajouter</button>
                      </form>
                  </div>
              </div>
          </div>
      </div>
       <!-- end ajouter un niveau-->

      <!-- ajouter une matiere-->
      <div class="modal fade" id="subjectModal" tabindex="-1" aria-labelledby="subjectModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="subjectModalLabel">Ajouter une matière</h5>
                    <button type="button" class="btn-close bg-blue-midium" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Your form content goes here -->
                    <form method="post" action="../addSubjectController">
                        <div class="mb-3">
                            <label for="subjectName" class="form-label">Nom du matière</label>
                            <input type="text" class="form-control" id="subjectName" name="subject" required>
                        </div>
                        <div class="mb-3">
                          <label for="niveauSelect" class="form-label">Choisir un niveau</label>
                          <select class="form-select" id="niveauSelect" name="niveau" required>
                              <!-- Populate the options dynamically or hardcode them based on your data -->
                              <option value="niveau1">Niveau 1</option>
                              <option value="niveau2">Niveau 2</option>
                              <option value="niveau3">Niveau 3</option>
                          </select>
                      </div>
                        <button type="submit" class="btn btn-midium-blue">ajouter</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
      <!-- end  ajouter une matiere-->
            <!-- ajouter un group-->
            <div class="modal fade" id="groupModal" tabindex="-1" aria-labelledby="groupModalLabel" aria-hidden="true">
              <div class="modal-dialog">
                  <div class="modal-content">
                      <div class="modal-header">
                          <h5 class="modal-title" id="groupModalLabel">Ajouter un groupe</h5>
                          <button type="button" class="btn-close bg-blue-midium" data-bs-dismiss="modal" aria-label="Close"></button>
                      </div>
                      <div class="modal-body">
                          <!-- Your form content goes here -->
                          <form method="post" action="../addGroupController">
                              <div class="mb-3">
                                  <label for="groupName" class="form-label">Nom du groupe</label>
                                  <input type="text" class="form-control" id="groupName" name="group" required>
                              </div>
                              <div class="mb-3">
                                <label for="niveauSelect" class="form-label">Choisir un niveau</label>
                                <select class="form-select" id="niveauSelect" name="niveau" required>
                                    <!-- Populate the options dynamically or hardcode them based on your data -->
                                    <option value="niveau1">Niveau 1</option>
                                    <option value="niveau2">Niveau 2</option>
                                    <option value="niveau3">Niveau 3</option>
                                </select>
                            </div>
                            <div class="mb-3">
                              <label for="niveauSelect" class="form-label">Choisir une matiere</label>
                              <select class="form-select" id="niveauSelect" name="matiere" required>
                                  <!-- Populate the options dynamically or hardcode them based on your data -->
                                  <option value="niveau1">Matiere 1</option>
                                  <option value="niveau2">Matiere 2</option>
                                  <option value="niveau3">Matiere 3</option>
                              </select>
                          </div>
                              <button type="submit" class="btn btn-midium-blue">ajouter</button>
                          </form>
                      </div>
                  </div>
              </div>
          </div>
      <!-- end  ajouter un group-->
        <!-- end forms -->

  </main>
<jsp:include page="configurations.jsp"></jsp:include>
