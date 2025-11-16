<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<<<<<<< HEAD
<%@ page import="model.Client" %>
<%@ page import="dao.ClientDaoImp" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ page import="dao.ServiceDaoImp" %>

<%
    User user = (User) session.getAttribute("user");

    if(user == null || !"client".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    ClientDaoImp clientDao = new ClientDaoImp();
    Client client = clientDao.getClientById(user.getId());
    
    
%>
=======
>>>>>>> 2892b5ab0a89df4c7d8078003cb7f72e1d4378b2
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<<<<<<< HEAD
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
                        <li><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
                        <li><a href="${pageContext.request.contextPath}/salons">Salons</a></li>
                        <li><a href="${pageContext.request.contextPath}/services">Services</a></li>
                        <li><a href="${pageContext.request.contextPath}/dashboard" class="active">Mon Compte</a></li>
                    </ul>
                </nav>
                <div class="auth-buttons">
                    <span style="color: var(--light-beige); margin-right: 15px;">
                        <i class="fas fa-bell"></i> <span class="notification-badge">2</span>
                    </span>
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
            <a href="#services" class="big-cta-button">
                <i class="fas fa-calendar-plus"></i> PRENDRE UN RENDEZ-VOUS
            </a>

            <!-- Grille principale -->
            <div class="dashboard-grid">
                
                <!-- Rendez-vous à venir -->
                <div class="dashboard-card">
                    <div class="card-header">
                        <div class="card-icon"><i class="fas fa-clock"></i></div>
                        <h2 class="card-title">Mes rendez-vous à venir</h2>
                    </div>
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Heure</th>
                                <th>Service</th>
                                <th>Statut</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td colspan="5" class="empty-state">
                                    <i class="fas fa-calendar-times"></i>
                                    <p>Aucun rendez-vous pour le moment</p>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <div style="text-align: center; margin-top: 15px;">
                        <a href="#services" class="btn btn-primary">Prendre un rendez-vous</a>
                    </div>
                </div>

                
            </div>
            <div class="dashboard-card">
			<a href="services.jsp" class="btn btn-primary">
			    Voir tous les services
			</a></div>
			            
            <!-- Historique des rendez-vous -->
            <div class="dashboard-card">
                <div class="card-header">
                    <div class="card-icon"><i class="fas fa-history"></i></div>
                    <h2 class="card-title">Historique des rendez-vous</h2>
                </div>
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Service</th>
                            <th>Coiffeur</th>
                            <th>Prix</th>
                            <th>Statut</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td colspan="5" class="empty-state">
                                <i class="fas fa-history"></i>
                                <p>Aucun rendez-vous dans l'historique</p>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Actions rapides -->
            <div class="dashboard-card">
                <div class="card-header">
                    <div class="card-icon"><i class="fas fa-bolt"></i></div>
                    <h2 class="card-title">Actions rapides</h2>
                </div>
                <div class="quick-actions">
                    <a href="#services" class="action-btn">
                        <i class="fas fa-calendar-plus"></i> Prendre rendez-vous
                    </a>
                    <a href="${pageContext.request.contextPath}/salons" class="action-btn">
                        <i class="fas fa-store"></i> Explorer les salons
                    </a>
                    <a href="ModifierProfilServlet" class="action-btn">
                        <i class="fas fa-user-edit"></i> Modifier mon profil
                    </a>
                    <a href="${pageContext.request.contextPath}/notifications" class="action-btn">
                        <i class="fas fa-bell"></i> Mes notifications
                    </a>
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
                    <h3>Liens rapides</h3>
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
                        <li><a href="${pageContext.request.contextPath}/salons">Salons</a></li>
                        <li><a href="${pageContext.request.contextPath}/services">Services</a></li>
                        <li><a href="${pageContext.request.contextPath}/about">À propos</a></li>
                    </ul>
                </div>
                <div class="footer-column">
                    <h3>Contact</h3>
                    <ul>
                        <li>Email: contact@coiffure-elegance.fr</li>
                        <li>Téléphone: 01 23 45 67 89</li>
                        <li>Adresse: 123 Avenue de la Beauté, Paris</li>
                    </ul>
                </div>
            </div>
            <div class="copyright">
                <p>&copy; 2024 Coiffure Élégance. Tous droits réservés.</p>
            </div>
        </div>
    </footer>

</body>
</html>

