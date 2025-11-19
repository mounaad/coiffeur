<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="model.RendezVous" %>
<%@ page import="dao.CoiffeurDaoImpl" %>
<%@ page import="model.Coiffeur" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard Coiffeur</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style>
    /* Layout avec sidebar */
    .dashboard-layout {
        display: flex;
        min-height: calc(100vh - 80px);
    }
    
    /* Sidebar */
    .sidebar {
        width: 280px;
        background: var(--dark-brown);
        color: var(--light-beige);
        padding: 0;
    }
    
  
    
    .sidebar-menu {
        list-style: none;
        padding: 0;
        margin: 0;
    }
    
    .sidebar-menu li {
        border-bottom: 1px solid rgba(214, 200, 189, 0.1);
    }
    
    .sidebar-menu a {
        display: block;
        padding: 20px;
        color: var(--light-beige);
        text-decoration: none;
        transition: all 0.3s ease;
        border-left: 4px solid transparent;
        font-size: 1.1em;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    .sidebar-menu a:hover {
        background: var(--burgundy);
        border-left-color: var(--light-beige);
        padding-left: 25px;
    }
    
    .sidebar-menu a.active {
        background: var(--burgundy);
        border-left-color: var(--light-beige);
        font-weight: bold;
    }
    
    .menu-icon {
        margin-right: 12px;
        width: 20px;
        display: inline-block;
        text-align: center;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    /* Contenu principal */
    .main-content {
        flex: 1;
        padding: 40px;
        background: #f8f9fa;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    .page-header {
        margin-bottom: 30px;
        padding-bottom: 15px;
        border-bottom: 2px solid var(--light-beige);
    }
    
    .page-header h1 {
        color: var(--dark-brown);
        margin: 0;
        font-size: 2em;
        font-weight: 600;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    /* Section des rendez-vous */
    .rdv-section {
        background: white;
        border-radius: 8px;
        padding: 0;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        overflow: hidden;
    }
    
    .section-title {
        background: var(--dark-brown);
        color: var(--light-beige);
        padding: 20px 25px;
        margin: 0;
        font-size: 1.3em;
        font-weight: 500;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    /* Tableau des rendez-vous */
    .rdv-table {
        width: 100%;
        border-collapse: collapse;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    .rdv-table th {
        background-color: #f8f9fa;
        color: var(--dark-brown);
        padding: 18px 25px;
        text-align: left;
        font-weight: 600;
        border-bottom: 2px solid var(--light-beige);
        font-size: 0.95em;
    }
    
    .rdv-table td {
        padding: 18px 25px;
        border-bottom: 1px solid #e9ecef;
        color: var(--dark-brown);
        font-size: 0.95em;
    }
    
    .rdv-table tr:last-child td {
        border-bottom: none;
    }
    
    .rdv-table tr:hover {
        background-color: rgba(214, 200, 189, 0.08);
    }
    
    /* Statuts */
    .statut {
        padding: 8px 16px;
        border-radius: 4px;
        font-size: 0.9em;
        font-weight: 500;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }
    
    .statut-en_attente {
        background: #fff3cd;
        color: #856404;
        border: 1px solid #ffeaa7;
    }
    
    .statut-confirme {
        background: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
    }
    
    .statut-annule {
        background: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
    }
    
    /* Actions */
    .action-buttons {
        display: flex;
        gap: 10px;
    }
    
    .btn {
        padding: 8px 16px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 0.85em;
        font-weight: 500;
        transition: all 0.3s ease;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    .btn-confirm {
        background: #28a745;
        color: white;
    }
    
    .btn-cancel {
        background: #dc3545;
        color: white;
    }
    
    .btn-confirm:hover {
        background: #218838;
    }
    
    .btn-cancel:hover {
        background: #c82333;
    }
    
    .no-action {
        color: #6c757d;
        font-style: italic;
        font-size: 0.9em;
    }
    
    /* Message vide */
    .no-data {
        text-align: center;
        padding: 60px 20px;
        color: var(--medium-brown);
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    .no-data h3 {
        margin-bottom: 10px;
        color: var(--dark-brown);
        font-weight: 500;
    }
    
    /* Footer simple */
    .simple-footer {
        background: var(--dark-brown);
        color: var(--light-beige);
        text-align: center;
        padding: 20px;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    /* Responsive */
    @media (max-width: 768px) {
        .dashboard-layout {
            flex-direction: column;
        }
        
        .sidebar {
            width: 100%;
        }
        
        .main-content {
            padding: 20px;
        }
        
        .action-buttons {
            flex-direction: column;
        }
        
        .rdv-table {
            display: block;
            overflow-x: auto;
        }
    }
</style>
</head>

<body>
<%
    User user = (User) session.getAttribute("user");  
%>
    <!-- header simple -->
    <header>
        <div class="container">
            <div class="header-content">
                <div class="logo">Coiffure Élégance</div>
            </div>
        </div>
    </header>

    <!-- Dashboard avec sidebar -->
    <div class="dashboard-layout">
        <!-- Sidebar -->
        <aside class="sidebar">

            <ul class="sidebar-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/coiffeur/dashboard" class="active">
                        Rendez-vous
                    </a>
                </li>
                <li>
                    <a href="#">
                        Gestion de Planning
                    </a>
                </li>
                <li>
                    <a href="#">
                        Points de Fidélité
                    </a>
                </li>
                <li>
                    <a href="#">
                        Se Déconnecter
                    </a>
                </li>
            </ul>
        </aside>

        <!-- Contenu principal -->
        <main class="main-content">
            <!-- En-tête de page -->
            <div class="page-header">
                <h1>Liste des Rendez-vous</h1>
            </div>

            <!-- Liste des rendez-vous -->
            <div class="rdv-section">
                <h3 class="section-title">Rendez-vous</h3>
                
                <c:choose>
                    <c:when test="${not empty rdvs}">
                        <table class="rdv-table">
                            <thead>
                                <tr>
                                    <th>Client</th>
                                    <th>Date</th>
                                    <th>Heure</th>
                                    <th>Service</th>
                                    <th>Prix</th>
                                    <th>Statut</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="rdv" items="${rdvs}">
                                    <tr>
                                        <td>
                                            <strong>${rdv.nomClient} ${rdv.prenomClient}</strong>
                                        </td>
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
                                        <td>
                                            <c:choose>
                                                <c:when test="${rdv.statut == 'en_attente'}">
                                                    <div class="action-buttons">
                                                        <form method="post" style="display:inline">
                                                            <input type="hidden" name="idRdv" value="${rdv.idRdv}"/>
                                                            <input type="hidden" name="idClient" value="${rdv.idClient}">
                                                            <button type="submit" name="action" value="confirme" class="btn btn-confirm">
                                                                Confirmer
                                                            </button>
                                                        </form>
                                                        <form method="post" style="display:inline">
                                                            <input type="hidden" name="idRdv" value="${rdv.idRdv}"/>
                                                            <button type="submit" name="action" value="annule" class="btn btn-cancel">
                                                                Annuler
                                                            </button>
                                                        </form>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="no-action">Aucune action</span>
                                                </c:otherwise>
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
        </main>
    </div>

    
</body>
</html>