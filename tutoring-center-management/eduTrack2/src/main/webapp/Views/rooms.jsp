<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<%@ page import="entities.*" %>
<%@ page import="dao.*" %>

      <jsp:include page="navBar.jsp"></jsp:include>
     <%
Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
    if (utilisateur == null) {
%>
<jsp:forward page="signIn.jsp"></jsp:forward>
<%} %>
<%     //IDao dao = (IDao) getServletContext().getAttribute("sharedDao");
    IDao dao = new Dao();

 %>
<main class="main-content position-relative border-radius-lg ">
    <!-- Navbar -->
    <nav class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl " id="navbarBlur" data-scroll="false">
      <div class="container-fluid py-1 px-3">
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
            <li class="breadcrumb-item text-sm"><a class="opacity-5 text-white" href="javascript:;">Pages</a></li>
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Salles</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Salles</h6>
        </nav>
        <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
          <div class="ms-md-auto pe-md-3 d-flex align-items-center">
            <div class="input-group"> 
              
            </div>
          </div>
          <ul class="navbar-nav  justify-content-end">
            <li class="nav-item d-flex align-items-center">
              <a href="../logoutCotroller" class="nav-link text-white font-weight-bold px-0">
                <i class="fa fa-sign-out-alt me-sm-1"></i>
                <span class="d-sm-inline d-none">Se déconnecter</span>
              </a>
            </li>
         
         
          </ul>
        </div>
      </div>
    </nav>
    <!-- End Navbar -->
    <div class="container-fluid py-4">
      <!--  add ajouter une salle  button -->
      <div class="row">
        <div class="col-12">
            <button type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#addSalleModal">
                Ajouter une salle
            </button>
        </div>
    </div>
      <div class="row">

        <div class="col-12">
          <div class="card mb-4">
            <div class="card-header pb-0">
              <h6>Les salles</h6>
            </div>
            <div class="card-body px-0 pt-0 pb-2">
              <div class="table-responsive p-0">
                <table class="table align-items-center justify-content-center mb-0">
                  <thead>
                    <tr>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">salle</th>
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">capacité</th>
                    <!--   <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 ps-2">Occuper</th> -->
                      <th class="text-uppercase text-secondary text-xxs font-weight-bolder text-center opacity-7 ps-2">disponible</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody>
                  <%  List<Salle> salles = dao.getAllSalles();
                                    if (salles != null && !salles.isEmpty()) {
                                        for (Salle salle : salles) { %>
                    <tr>
                      <td>
                        <div class="d-flex px-2">
                         
                          <div class="my-auto">
                            <h6 class="mb-0 text-sm">salle <%= salle.getIdSalle() %></h6>
                          </div>
                        </div>
                      </td>
                      <td>
                        <p class="text-sm font-weight-bold mb-0"><%= salle.getCapacite() %></p>
                      </td>
                    <!-- <td>
                        <span class="text-xs font-weight-bold"><%= salle.isOccuper() ? "Oui" : "Non" %></span>
                      </td>--> 
                      <td class="align-middle text-center">
                        
                            
                    <span class="badge badge-sm <%= salle.isDisponible() ? "bg-gradient-success" : "bg-gradient-danger" %>">
                               <%= salle.isDisponible() ? "Oui" : "Non" %></span> 
                          
                        
                      </td>
                      <td class="align-middle text-center">
                       
                          <a href="../noAvailableSalleController?id=<%=salle.getIdSalle() %>&opt=<%=salle.isDisponible() %>" class=" text-xs"> <%= salle.isDisponible() ? "en traine de  réformer" : " disponible " %>  </a>
                        
                      </td>
                    </tr>
                     <%
                                        }
                                    }
                                    %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    
    </div>
    <div class="modal fade" id="addSalleModal" tabindex="-1" aria-labelledby="addSalleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addSalleModalLabel">Ajouter une salle</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="../addSalleController" method="post">
                        <div class="mb-3">
                            <label for="capacite" class="form-label">Capacité</label>
                            <input type="text" class="form-control" id="capacite" name="capacite">
                        </div>
                        <div class="mb-3">
                            <label for="occuper" class="form-label">disponible</label>
                            <div class="form-check">
                                <input class="form-check-input " type="radio" name="dispo" id="occuperOui" value="oui" checked>
                                <label class="form-check-label" for="occuperOui">Oui</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="dispo" id="occuperNon" value="non">
                                <label class="form-check-label" for="occuperNon">Non</label>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-midium-blue">Ajouter</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  </main>
  <jsp:include page="configurations.jsp"></jsp:include>