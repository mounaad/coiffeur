<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ page import="dao.FideliteDaoImpl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Points de Fidélité</title>
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
    
    /* Section fidélité */
    .fidelite-section {
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
    
    /* Tableau fidélité */
    .fidelite-table {
        width: 100%;
        border-collapse: collapse;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    
    .fidelite-table th {
        background-color: #f8f9fa;
        color: var(--dark-brown);
        padding: 18px 25px;
        text-align: left;
        font-weight: 600;
        border-bottom: 2px solid var(--light-beige);
        font-size: 0.95em;
    }
    
    .fidelite-table td {
        padding: 18px 25px;
        border-bottom: 1px solid #e9ecef;
        color: var(--dark-brown);
        font-size: 0.95em;
    }
    
    .fidelite-table tr:last-child td {
        border-bottom: none;
    }
    
    .fidelite-table tr:hover {
        background-color: rgba(214, 200, 189, 0.08);
    }
    
    /* Points */
    .points-badge {
        padding: 8px 16px;
        border-radius: 20px;
        font-size: 0.9em;
        font-weight: 600;
        display: inline-block;
    }
    
    .points-faible {
        background: #fff3cd;
        color: #856404;
        border: 1px solid #ffeaa7;
    }
    
    .points-moyen {
        background: #d1edff;
        color: #0c5460;
        border: 1px solid #bee5eb;
    }
    
    .points-eleve {
        background: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
    }
    
    /* Statut réduction */
    .reduction-badge {
        padding: 6px 12px;
        border-radius: 4px;
        font-size: 0.85em;
        font-weight: 500;
        text-transform: uppercase;
        letter-spacing: 0.5px;
    }
    
    .reduction-oui {
        background: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
    }
    
    .reduction-non {
        background: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
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
        
        .fidelite-table {
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
                    <a href="${pageContext.request.contextPath}/coiffeur/dashboard">
                        Rendez-vous
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/coiffeur/fidelite" class="active">
                        Points de Fidélité
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/logout">
                        Se Déconnecter
                    </a>
                </li>
            </ul>
        </aside>

        <!-- Contenu principal -->
        <main class="main-content">
            <!-- En-tête de page -->
            <div class="page-header">
                <h1>Points de Fidélité des Clients</h1>
            </div>

            <!-- Section fidélité -->
            <div class="fidelite-section">
                <h3 class="section-title">Programme de Fidélité</h3>
                
                <c:choose>
                    <c:when test="${not empty listeFidelite}">
                        <table class="fidelite-table">
                            <thead>
                                <tr>
                                    <th>Client</th>
                                    <th>Points</th>
                                    <th>Réduction active</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="c" items="${listeFidelite}">
                                    <tr>
                                        <td>
                                            <strong>${c.nom} ${c.prenom}</strong>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${c.pointsFidelite >= 100}">
                                                    <span class="points-badge points-eleve">${c.pointsFidelite} pts</span>
                                                </c:when>
                                                <c:when test="${c.pointsFidelite >= 50}">
                                                    <span class="points-badge points-moyen">${c.pointsFidelite} pts</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="points-badge points-faible">${c.pointsFidelite} pts</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${c.reduction > 0}">
                                                    <span class="reduction-badge reduction-oui">Oui</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="reduction-badge reduction-non">Non</span>
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
                            <h3>Aucun client dans le programme de fidélité</h3>
                            <p>Les points de fidélité de vos clients apparaîtront ici.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>
</body>
</html>