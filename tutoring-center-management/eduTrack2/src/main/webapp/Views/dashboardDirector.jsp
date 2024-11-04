<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="dao.*"%>
 <%@ page import="java.util.*" %>
<%@ page import="entities.*" %>
    <% Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");
   if (utilisateur == null) {
%>
<jsp:forward page="signIn.jsp"></jsp:forward>
<% }%>
<%    // IDao dao = (IDao) getServletContext().getAttribute("sharedDao");
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
            <li class="breadcrumb-item text-sm text-white active" aria-current="page">Dashboard</li>
          </ol>
          <h6 class="font-weight-bolder text-white mb-0">Dashboard</h6>
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
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-uppercase font-weight-bold">nombre des inscriptions du l'année current</p>
                    <h5 class="font-weight-bolder">
                    
                     <%
    Map<String, Long> inscriptionsByType = dao.inscriptionsOfcurrentYear();
    long totalInscriptions = 0;

    for (long count : inscriptionsByType.values()) {
        totalInscriptions += count;
    }
%>
                    <%=totalInscriptions%>
                    </h5>
                    <p class="mb-0">
                        <span class="text-success text-sm font-weight-bolder"></span>
                      </p>
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div class="icon icon-shape bg-gradient-dark shadow-primary text-center rounded-circle">
                    <i class="ni ni-single-02 text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-uppercase font-weight-bold">nombre des enseignants validées</p>
                    <h5 class="font-weight-bolder">
                     <%=dao.numberOfValidateProfs() %>
                    </h5>
                    <p class="mb-0">
                      <span class="text-success text-sm font-weight-bolder"></span>
                    </p>
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div class="icon icon-shape bg-gradient-myred shadow-danger text-center rounded-circle">
                    <i class="ni ni-archive-2 text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-uppercase font-weight-bold">nombre des éleves validées</p>
                    <h5 class="font-weight-bolder">
                      <%=dao.numberOfValidateStudent() %>
                    </h5>
                  <!--   <p class="mb-0">
                      <span class="text-danger text-sm font-weight-bolder">-2%</span>
                      depuis le mois dernier
                    </p> -->
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div class="icon icon-shape bg-gradient-info-blue shadow-success text-center rounded-circle">
                    <i class="ni ni-single-02 text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="col-xl-3 col-sm-6">
          <div class="card">
            <div class="card-body p-3">
            
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-uppercase font-weight-bold">revenus du l'année current</p>
                    <h5 class="font-weight-bolder">
                      <%=dao.incomeOfCurrentYear() %> Dh
                    </h5>
                   
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div class="icon icon-shape bg-gradient-dark shadow-warning text-center rounded-circle">
                    <i class="ni ni-money-coins text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- the graphe is here-->
      <div class="row mt-4">
      
        <div class="col-lg-7 mb-lg-0 mb-4">
          <div class="card z-index-2 h-100">
            <div class="card-header pb-0 pt-3 bg-transparent">
              <h6 class="text-capitalize">Inscriptions des utilisateurs </h6>
              <!-- <p class="text-sm mb-0">
                <i class="fa fa-arrow-up text-success"></i>
                <span class="font-weight-bold"></span> in current year
              </p>-->
            </div>
            <div class="card-body p-3">
              <div class="chart">
                <canvas id="chart-line" class="chart-canvas" height="300"></canvas>
              </div>
            </div>
          </div>
        </div>
              <!-- end of  the graphe is here-->
          <div class="col-xl-3 col-sm-6 mb-xl-0 mb-4">
          <div class="card">
            <div class="card-body p-3">
              <div class="row">
                <div class="col-8">
                  <div class="numbers">
                    <p class="text-sm mb-0 text-uppercase font-weight-bold">nombre des éleves inscrits</p>
                    <h5 class="font-weight-bolder">
                      <%=inscriptionsByType.get("student") %>
                    </h5>
                  <!--   <p class="mb-0">
                      <span class="text-danger text-sm font-weight-bolder">-2%</span>
                      depuis le mois dernier
                    </p> -->
                  </div>
                </div>
                <div class="col-4 text-end">
                  <div class="icon icon-shape bg-gradient-faded-secondary shadow-success text-center rounded-circle">
                    <i class="ni ni-chart-pie-35 text-lg opacity-10" aria-hidden="true"></i>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
           
      
        <!--  here i delete the images -->
      </div>
      <div class="row mt-4">
        <div class="col-lg-8 mb-lg-0 mb-4">
          <div class="card ">
            <div class="card-header pb-0 p-3">
              <div class="d-flex justify-content-between">
                <h6 class="mb-2">Par Niveau</h6>
              </div>
            </div>
            <div class="table-responsive">
            <% Map<String, Long> inscriptionsByLevel = dao.inscriptionsByLevel();
			 Map<String, Object> incomeByLevel = dao.incomeByLevel(); %>
              <table class="table align-items-center ">
              
                <tbody>
                 <% for (Map.Entry<String, Long> entry : inscriptionsByLevel.entrySet()) { %>
                  <tr>
                    <td class="w-30">
                      <div class="d-flex px-2 py-1 align-items-center">
                        
                        <div class="ms-4">
                          <p class="text-xs font-weight-bold mb-0">Niveau:</p>
                          <h6 class="text-sm mb-0"><%= entry.getKey() %></h6>
                        </div>
                      </div>
                    </td>
                    <td>
                      <div class="text-center">
                        <p class="text-xs font-weight-bold mb-0">Nombre des éleves:</p>
                        <h6 class="text-sm mb-0"><%= entry.getValue() %></h6>
                      </div>
                    </td>
                    
                                    <td class="align-middle text-sm">
                                        <div class="col text-center">
                                            <p class="text-xs font-weight-bold mb-0">Revenu:</p>
                                            <h6 class="text-sm mb-0">   <%= incomeByLevel.containsKey(entry.getKey())? incomeByLevel.get(entry.getKey())  : 0 %> Dh</h6>
                                        </div>
                                    </td>
                               
                                   
                  </tr>
                 
                 <%} %>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        
      </div>
  
    </div>
  </main>
  
  
  
    <jsp:include page="configurations.jsp"></jsp:include>
    
    
    <!--  the script of dashboard-->
    
   <script>
    document.addEventListener("DOMContentLoaded", function() {
        // Fetch chart data
        fetch('../chartData')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // Render chart with fetched data
                renderChart(data);
            })
            .catch(error => {
                console.error('Fetch Error:', error);
            });
    });

    function renderChart(data) {
        var ctx = document.getElementById('chart-line').getContext('2d');
        var gradientStroke1 = ctx.createLinearGradient(0, 230, 0, 50);

        gradientStroke1.addColorStop(1, 'rgba(156, 39, 176, 0.2)');
        gradientStroke1.addColorStop(0.2, 'rgba(156, 39, 176, 0.0)');
        gradientStroke1.addColorStop(0, 'rgba(156, 39, 176, 0)');

        new Chart(ctx, {
            type: 'line',
            data: {
                labels: Object.keys(data),
                datasets: [{
                    label: 'Inscriptions',
                    tension: 0.4,
                    borderWidth: 0,
                    pointRadius: 0,
                    borderColor: "#9c27b0 ",
                    backgroundColor: gradientStroke1,
                    borderWidth: 3,
                    fill: true,
                    data: Object.values(data),
                    maxBarThickness: 6
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                interaction: {
                    intersect: false,
                    mode: 'index'
                },
                scales: {
                    y: {
                        grid: {
                            drawBorder: false,
                            display: true,
                            drawOnChartArea: true,
                            drawTicks: false,
                            borderDash: [5, 5]
                        },
                        ticks: {
                            display: true,
                            padding: 10,
                            color: '#F6F4EB',
                            font: {
                                size: 11,
                                family: "Open Sans",
                                style: 'normal',
                                lineHeight: 2
                            }
                        }
                    },
                    x: {
                        grid: {
                            drawBorder: false,
                            display: false,
                            drawOnChartArea: false,
                            drawTicks: false,
                            borderDash: [5, 5]
                        },
                        ticks: {
                            display: true,
                            color: '#ccc',
                            padding: 20,
                            font: {
                                size: 11,
                                family: "Open Sans",
                                style: 'normal',
                                lineHeight: 2
                            }
                        }
                    }
                }
            }
        });
    }
</script>
    

  
  
  
    