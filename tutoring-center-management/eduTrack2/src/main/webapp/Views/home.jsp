<%@page import="dao.Dao"%>
<%@page import="dao.IDao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="entities.Utilisateur"%>
     <%@ page import="java.util.Map" %>
<!doctype html>
<html lang="fr">
<head>
    <meta charset="utf-8" />
    <title>Home page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:wght@300;400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../assets/css/homecss/bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="../assets/css/homecss/all.min.css" type="text/css" /> 
    <link rel="stylesheet" href="../assets/css/homecss/slick.css" type="text/css" />   
    <link rel="stylesheet" href="../assets/css/homecss/style.css" type="text/css" />

</head>

<body>
<% Utilisateur utilisateur = (Utilisateur) session.getAttribute("user");

IDao dao = new Dao();%>
    <div id="outer">
        <header class="header order-last" id="tm-header">
            <nav class="navbar">
                <div class="collapse navbar-collapse single-page-nav">
                    <ul class="navbar-nav">
                        <li class="nav-item" style="background-image: url(../assets/img/menu-item-bg.png);">
                            <a class="nav-link" href="#section-1"><span class="icn"><i class="fas fa-2x fa-home"></i></span> Notre Centre</a>
                        </li>
                        <li class="nav-item" style="background-image: url(../assets/img/menu-item-bg.png);">
                            <a class="nav-link" href="#section-2"><span class="icn"><i class="fas fa-2x fa-tools"></i></span> Fonctionnalités</a>
                        </li>
                        <li class="nav-item" style="background-image: url(../assets/img/menu-item-bg.png);">
                            <a class="nav-link" href="#section-3"><span class="icn"><i class="fas fa-2x fa-chart-bar"></i></span> Statistiques</a>
                        </li>
                        <li class="nav-item" style="background-image: url(../assets/img/menu-item-bg.png);">
                            <a class="nav-link" href="#section-4"><span class="icn"><i class="fas fa-info-circle"></i></span> à propos de nous</a>
                        </li>
                        <% if(utilisateur == null){ %>
                        <li class="nav-item" style="background-image: url(../assets/img/menu-item-bg.png);">
                            <a class="nav-link" href="#section-5"><span class="icn"><i class="fas fa-2x fa-user"></i></span> Se connecter</a>
                        </li>
                        <%}else{ %>
                        <li class="nav-item" style="background-image: url(../assets/img/menu-item-bg.png);">
                            <a class="nav-link" href="#section-5"><span class="icn"><i class="fas fa-2x fa-user"></i></span> Mon compte</a>
                        </li>
                        <% } %>
                    </ul>
                </div>
            </nav>
        </header>
        
        <button class="navbar-button collapsed" type="button">
            <span class="menu_icon">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </span>
        </button>
        
        <main id="content-box" class="order-first">
            <div class="banner-section section parallax-window" data-parallax="scroll" data-image-src="../assets/img/bg1.jpg" id="section-1">
                <div class="container">
                    <div class="item">
                        <div class="bg-blue-transparent logo-fa"><span><i class="fas fa-2x fa-graduation-cap"></i></span> EduTrack</div>
                        <div class="bg-blue-transparent simple"><p>"L'éducation est l'arme la plus puissante que l'on puisse utiliser pour changer le monde."- Nelson Mandela</p></div>
                    </div>
                </div>
            </div>
        
            <section class="work-section section" id="section-2">
                <div class="container">
                    <div class="row">
                        <div class="item col-md-4">
                            <div class="tm-work-item-inner">
                                <div class="icn"><i class="fas fa-2x fa-user-graduate"></i></div>
                                <h3> Pour les étudiants</h3>
                                <p> Consulter l'emploi du temps, télécharger vos cours et devoirs facilement.</p>
                            </div>                        
                        </div>
                        <div class="item col-md-4 one">
                            <div class="tm-work-item-inner">
                                <div class="icn"><i class="fas fa-2x fa-users"></i></div>
                                <h3> Pour les parents</h3>
                                <p> Les parents peuvent consulter les absences et présences de leurs enfants facilement.</p>
                            </div>
                        </div>
                        <div class="item col-md-4 two">
                            <div class="tm-work-item-inner">
                                <div class="icn"><i class="fas fa-2x fa-chalkboard-teacher"></i></div>
                                <h3> Pour les enseignants</h3>
                                <p> Consulter votre emploi du temps et déposer des documents.</p>
                            </div>
                        </div>
                    </div>
                    <div class="title">
                        <h2>Fonctionnalités</h2>
                    </div>
                </div>
            </section>


             <section class="gallery-section section parallax-window" data-parallax="scroll" id="section-3">
                <div class="container">
                    <div class="title text-right">
                        <h2>Statistiques</h2>
                    </div>
                    <div class="row">
                        <div class="col-md-4">
                            <div class="dashboard-item">
                                <p class="display-7 font-weight-bold ">nombre des inscriptions de cette année </p>
                                 <%
    Map<String, Long> inscriptionsByType = dao.inscriptionsOfcurrentYear();
    long totalInscriptions = 0;

    for (long count : inscriptionsByType.values()) {
        totalInscriptions += count;
    }
%>
                   
                                <p class="display-2 font-weight-bold "> <%=totalInscriptions%> </p> 
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="dashboard-item">
                                <div class="dashboard-content">
                                    <p class="display-7 font-weight-bold ">Professeurs</p>
                                   <p class="display-2 font-weight-bold " ><%=dao.numberOfValidateProfs() %> </p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="dashboard-item">
                                <p class="display-7 font-weight-bold ">Cours postés</p>
                                <p class="display-2 font-weight-bold "> <%=dao.numberOfDocuments() %></p>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="contact-section section" id="section-4">
                <div class="container">
                    <div class="title">
                        <h3>À propos de nous</h3>
                    </div>
                    <div class="row">
                        
               

            
                               

       
                           
                            
                  
           
                    
                        <div class="col-lg-3 col-md-12 map">
                            <!-- Map -->
                            <div class="map-outer tm-mb-40">
                                <div class="gmap-canvas">
                                    <iframe
                                 width="65%" height="400"  id="gmap-canvas" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3305.1557919607896!2d-7.541483347509364!3d33.56615419313835!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x0!2zMywzwrAyJzM4LjYiTiA3wrAyJzQ2LjYiRQ!5e0!3m2!1sen!2s!4v1637984462461!5m2!1sen!2s" 
                                 frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>

                                </div>
                            </div>
                        </div>
                    </div> 
                                   
                </div>
                
                 <!-- Footer Section -->
            <footer class="footer container container-fluid">
                <div class="row justify-content-center"> 
                    <div class="col-lg-6 text-center">
                    <div class="tm-contact-item-inner-2 text-center">
                        <p>Rejoignez notre centre de cours de soutien pour une expérience éducative enrichissante. Nos enseignants qualifiés sont là pour vous aider à atteindre vos objectifs académiques. Inscrivez-vous dès aujourd'hui et donnez un coup de pouce à votre réussite scolaire!</p>
                        <ul class="list-unstyled">
                            <li>
                                <span class="icn"><i class="fas fa-mobile-alt"></i></span>
                                <span class="lbl">Tel:</span> <span>010-020-0340</span>
                            </li>
                            <li>
                                <span class="icn"><i class="fas fa-at"></i></span>
                                <span class="lbl">Email:</span> <span >info@company.com</span>
                            </li>
                        </ul>
                    </div>
                </div> 
                </div>
            </footer>
        </div>
            <!-- End Footer Section -->
            </section>
           <% if(utilisateur == null) {%>
            
            <section class="signup" data-parallax="scroll" id="section-5">
                <div class="container">
                    <div class="title text-right">
                        <h2>Vous connecter / vous inscrire</h2>
                    </div>
                    <div class="row">
                        <div class="col-lg-6 mb-4 contact-form" id="signInForm">
                            <!-- Sign In Button -->
                            <div class="form tm-contact-item-inner">
                                <button onclick="window.location.href='signIn.jsp'" class="btn btn-primary  beautiful-button">Sign In</button>
                                <div>
                                    <p>Vous n'avez pas de compte ? <a href="#" id="showSignUp">S'inscrire</a></p>
                                </div>
                            </div>
                        </div>
            
                        <div class="col-lg-6 mb-4 contact-form" style="display: none;" id="signUpForm">
                            <!-- Sign Up Button -->
                            <div class="form tm-contact-item-inner">
                                <button onclick="window.location.href='signUp.jsp'" class="btn btn-primary  beautiful-button">Sign Up</button>
                                <div>
                                    <p>Vous avez déjà un compte? <a href="#" id="showSignIn">Se Connecter</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

           <% } else {%>
            
   <section class="signup" data-parallax="scroll" id="section-5">
                <div class="container">
                    <div class="title text-right">
                        <h2>Mon compte</h2>
                    </div>
                    <div class="row">
                        <div class="col-lg-6 mb-4 contact-form" id="signInForm">
                            <!-- Sign In Button -->
                            <div class="form tm-contact-item-inner">
                                <button onclick="window.location.href='profile.jsp'" class="btn btn-primary  beautiful-button">Mon profile</button>
                                <div>
                                  <!--    <p>Vous n'avez pas de compte ? <a href="#" id="showSignUp">S'inscrire</a></p>-->
                                </div>
                            </div>
                        </div>
            
                       
                    </div>
                </div>
            </section>
       <% } %>
        </main>
    </div>
    <script src="../assets/js/homejs/jquery-3.3.1.min.js"></script>
    <script src="../assets/js/core/bootstrap.bundle.min.js"></script>
    <script src="../assets/js/homejs/jquery.singlePageNav.min.js"></script>
    <script src="../assets/js/homejs/slick.js"></script>
    <script src="../assets/js/homejs/parallax.min.js"></script>
    <script src="../assets/js/homejs/templatemo-script.js"></script>
<script>
    $(document).ready(function () {
        $("#showSignUp").click(function (e) {
            e.preventDefault();
            $("#signUpForm").show();
            $("#signInForm").hide();
        });

        $("#showSignIn").click(function (e) {
            e.preventDefault();
            $("#signInForm").show();
            $("#signUpForm").hide();
        });
    });
</script>
</body>
</html>