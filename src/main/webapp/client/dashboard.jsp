<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>

<%@ page import="model.Client" %>
<%@ page import="dao.ClientDaoImp" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ page import="dao.ServiceDaoImp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User user = (User) session.getAttribute("user");

    if(user == null || !"client".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    ClientDaoImp clientDao = new ClientDaoImp();
    Client client = clientDao.getClientById(user.getId());
    
    
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Tableau de Bord - Coiffure Élégance</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <!-- En-tête -->
    <header>
        <div class="container">
            <div class="header-content">
                <div class="logo">Coiffure<span>Élégance</span></div>
                <nav>
                    <ul>
                        
                        
                        <li><a href="${pageContext.request.contextPath}/dashboard" class="active">Mon Compte</a></li>
                    </ul>
                </nav>
                <div class="auth-buttons">
                    
                    <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
                </div>
            </div>
        </div>
    </header>

    <!-- Tableau de bord -->
    <section class="dashboard">
        <div class="container">
            <!-- En-tête du dashboard -->
            <div class="dashboard-header">
                <h1 class="welcome-message">Bonjour, <%= client.getPrenom() %> !</h1>
                <p class="user-info">Bienvenue dans votre espace personnel</p>
            </div>

            <!-- Gros bouton Prendre RDV -->
            <a href="<%= request.getContextPath() %>/ReservationServlet" class="big-cta-button">
                <i class="fas fa-calendar-plus"></i> PRENDRE UN RENDEZ-VOUS
            </a>
            <a href="<%= request.getContextPath() %>/Services" class="big-cta-button">
 		   VOIR LES SERVICES 
			</a>

            <!-- Grille principale -->
            
                <!-- Programme de fidélité -->
<div class="dashboard-card fidelite-card">
    <div class="card-header">
        <div class="card-icon"><i class="fas fa-gift"></i></div>
        <h2 class="card-title">Programme de Fidélité</h2>
    </div>
    
    <div class="fidelite-content">
        <div class="points-display">
            <div class="points-number">Points : ${pointsFidelite}</div>
            
        </div>
        
        <div class="progress-section">
            <div class="progress-info">
                <span>Progression vers la prochaine réduction</span>
                <span class="progress-text">${pointsFidelite % 100}/100 points</span>
            </div>
            <div class="progress-bar">
                <div class="progress-fill" style="width: ${pourcentagePoints}%"></div>
            </div>
        </div>
        
        <c:if test="${reductionsDisponibles > 0}">
            <div class="reductions-disponibles">
                <div class="reduction-badge">
                    <i class="fas fa-ticket-alt"></i>
                    <span>${reductionsDisponibles} réduction(s) de 10% disponible(s)</span>
                </div>
                <p class="reduction-info">
                    <i class="fas fa-info-circle"></i>
                    Vos réductions seront automatiquement appliquées lors de votre prochain rendez-vous
                </p>
            </div>
        </c:if>
       
        
        <div class="fidelite-avantages">
            <h3>Comment ça marche ?</h3>
            
                <h4><i class="fas fa-check-circle"></i> 10 points par rendez-vous confirmé</h4>
                <h4><i class="fas fa-check-circle"></i> 100 points = 10% de réduction</h4>
                <h4><i class="fas fa-check-circle"></i> Réductions cumulables</h4>
                <h4><i class="fas fa-check-circle"></i> Pas de limite de temps</h4>
            
        </div>
    </div>
</div>
               <div class="dashboard-grid">
                <!-- Rendez-vous à venir -->
                <div class="dashboard-card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-clock"></i></div>
                        <h2 class="card-title">Mes rendez-vous à venir</h2>
                    </div>
                    <c:choose>
    <c:when test="${not empty rdvs}">
        <table class="data-table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Heure</th>
                    <th>Service</th>
                    <th>Prix</th>
                    <th>Statut</th>
                    
                </tr>
            </thead>

            <tbody>
                <c:forEach var="rdv" items="${rdvs}">
                    <tr>
                        <td>${rdv.dateRdv}</td>
                        <td>${rdv.heureRdv}</td>
                        <td>${rdv.nomService}</td>
                        <td>${rdv.prix} MAD</td>

                        <td>
                            <c:choose>
                                <c:when test="${rdv.statut == 'en_attente'}">
                                    <span class="statut statut-en_attente">En attente</span>
                                </c:when>
                                <c:when test="${rdv.statut == 'confirme'}">
                                    <span class="statut statut-confirme">Confirmé</span>
                                </c:when>
                                <c:when test="${rdv.statut == 'annule'}">
                                    <span class="statut statut-annule">Annulé</span>
                                </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>

    <c:otherwise>
        <div class="no-data">
            <h3>Aucun rendez-vous pour le moment</h3>
        </div>
    </c:otherwise>
</c:choose>
</div>
            </div>
            </div>
    </section>

    <!-- Pied de page -->
    <footer>
        <div class="container">
            <div class="footer-content">
                <div class="footer-column">
                    <h3>Coiffure Élégance</h3>
                    <p>Simplifiez votre prise de rendez-vous chez le coiffeur avec notre plateforme intuitive.</p>
                </div>
                
                <div class="footer-column">
                    <h3>Contact</h3>
                   <ul>
                        <li>Email: contact@example.com</li>
                        <li>Téléphone: 00 00 00 00 00 </li>
                        <li>Adresse: 123 Avenue de la Beauté, Rabat</li>
                    </ul>
                </div>
            </div>
            <div class="copyright">
                <p>&copy; 2025 Coiffure Élégance. Tous droits réservés.</p>
            </div>
        </div>
    </footer>
    

</body>
</html>

