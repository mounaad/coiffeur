<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Service" %>
<%@ page import="dao.ServiceDaoImp" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.Factory" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    List<Service> services = (List<Service>) request.getAttribute("services");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body>
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
                </div>
            </div>
        </div>
    </header>
    <section class="dashboard">
        <div class="container">
<!-- Services disponibles -->
            <div class="dashboard-card" id="services">
                <div class="card-header">
                    <div class="card-icon"><i class="fas fa-scissors"></i></div>
                    <h2 class="card-title">Nos services disponibles</h2>
                </div>
                <div class="services-grid">
    <c:forEach var="s" items="${services}">
        <div class="service-card">
            <img src="${s.getPhoto()}" alt="image service" style="width:120px;border-radius:10px;">

            <h3 class="service-name">${s.getNom()}</h3>

            <p class="service-duration">
                <i class="fas fa-clock"></i> ${s.getDuree()} minutes
            </p>

            <div class="service-price">${s.getPrix()} DH</div>

            <p style="color: var(--medium-brown); font-size: 14px; margin-bottom: 15px;">
                ${s.getDescription()}
            </p>

            <a href="reservation.jsp?serviceId=${s.getId()}" class="btn btn-primary">
                <i class="fas fa-calendar-check"></i> Réserver
            </a>
        </div>
    </c:forEach>
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