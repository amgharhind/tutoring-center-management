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
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Documents</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Poser un Document</h6>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
          <div class="ms-md-auto pe-md-3 d-flex align-items-center">
           
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
        <% for (Matiere matiere : dao.getAllMatieresOfProf(utilisateur.getIdUtilisateur())) { %>
      
       <!-- start of the docs of group-->
        <div class="col-lg-6">
          <div class="card h-100">
            <div class="card-header pb-0 p-3">
              <div class="row">
                <div class="col-6 d-flex align-items-center">
                  <h6 class="mb-0"><%= matiere.getDescriptionMatiere() %></h6>
                </div>
                <div class="col-6 text-end">
                    <button class="btn btn-outline-primary-violet btn-sm mb-0" data-bs-toggle="modal" data-bs-target="#addDocumentModal" data-matiere-id="<%= matiere.getIdMatiere() %>" onclick="setMatiereId(this)">ajouter un document</button>
                </div>
              </div>
            </div>
            <div class="card-body p-3 pb-0">
              <ul class="list-group">
                    <% List<Document> documentsForMatiere = dao.getDocumentsByMatiereIdAndProfId(matiere.getIdMatiere(), utilisateur.getIdUtilisateur()); %>
                    <% for (Document document : documentsForMatiere) { %>
                        <li class="list-group-item border-0 d-flex justify-content-between ps-0 mb-2 border-radius-lg">
                            <div class="d-flex flex-column">
                                <h6 class="mb-1 text-dark font-weight-bold text-sm"><%= document.getNomDoc() %></h6>
                                <span class="text-xs"><%= document.getDate_de_creation().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd   HH:mm:ss"))%></span>
                            </div>
                            <div class="d-flex align-items-center text-sm">
                                <%= document.getTypeDoc() %>
                                <a href="../documents/<%=document.getNomDoc() %>" target="_blank" class="btn btn-link text-dark text-sm mb-0 px-0 ms-4"><i class="fas fa-file-pdf text-lg me-1"></i> PDF</a>
                            </div>
                        </li>
                    <% } %>
                </ul>
            </div>
          </div>
        </div>
        <!-- end of the col -->
         <% } %>
      </div>
    </div>
    <!-- Modal for adding a document -->
<div class="modal fade" id="addDocumentModal" tabindex="-1" aria-labelledby="addDocumentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="addDocumentModalLabel">Ajouter un document</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <!-- Form for adding a document -->
          <form method="post" action="../addDocumentController" enctype="multipart/form-data" onsubmit="setMatiereIdBeforeSubmit(this);">
           
            <div class="mb-3">
              <label class="form-label">Type</label>
              <div class="form-check">
                <input class="form-check-input" type="radio" name="documentType" id="typeCour" value="cour" checked>
                <label class="form-check-label" for="typeCour">Cour</label>
              </div>
              <div class="form-check">
                <input class="form-check-input" type="radio" name="documentType" id="typeDevoir" value="devoir">
                <label class="form-check-label" for="typeDevoir">Devoir</label>
              </div>
            </div>
            <div class="mb-3">
              <label for="pdfFile" class="form-label">Fichier PDF</label>
              <input type="file" class="form-control" id="pdfFile" name="pdfFile" accept=".pdf" required>
            </div>
                    <input type="hidden" id="matiereId" name="matiereId" value="">            
            <button type="submit" class="btn btn-midium-blue">Ajouter</button>
          </form>
        </div>
      </div>
    </div>
  </div>
  </main>
 <!--  <script>
  function setMatiereId(button) {
    var matiereId = button.getAttribute('data-matiere-id');
    document.getElementById('matiereId').value = matiereId;
  }
</script> -->
<script>
   

    function setMatiereIdBeforeSubmit(form) {
        var button = document.querySelector('[data-bs-target="#addDocumentModal"]');
        var matiereId = button.getAttribute('data-matiere-id');
        document.getElementById('matiereId').value = matiereId;
        return true; // Allow form submission to proceed
    }
</script>
 <jsp:include page="configurations.jsp"></jsp:include>
    