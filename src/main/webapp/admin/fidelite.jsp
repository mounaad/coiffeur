<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Points de Fidélité - Admin</title>
    <style>
        /* Reset complet */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            background-color: #f8f9fa;
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
            min-height: 100vh;
        }
        
        /* Variables de couleurs */
        :root {
            --dark-brown: #220D0C;
            --light-beige: #D6C8BD;
            --burgundy: #541409;
            --medium-brown: #9B8174;
        }
        
        /* Layout avec sidebar */
        .dashboard-layout {
            display: flex;
            min-height: 100vh;
        }
        
        /* Sidebar */
        .sidebar {
            width: 280px;
            background: var(--dark-brown);
            color: var(--light-beige);
            padding: 0;
        }
        
        .sidebar-header {
            padding: 30px 20px;
            background: var(--dark-brown);
            text-align: center;
            border-bottom: 2px solid var(--medium-brown);
        }
        
        .sidebar-header h3 {
            color: var(--light-beige);
            margin: 0;
            font-size: 1.4em;
            font-weight: 600;
            font-family: 'Georgia', 'Times New Roman', serif;
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
            padding: 18px 20px;
            color: var(--light-beige);
            text-decoration: none;
            transition: all 0.3s ease;
            border-left: 4px solid transparent;
            font-size: 1em;
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
        }
        
        .sidebar-menu a:hover {
            background: var(--burgundy);
            border-left-color: var(--light-beige);
            padding-left: 25px;
        }
        
        .sidebar-menu a.active {
            background: var(--burgundy);
            border-left-color: var(--light-beige);
            font-weight: 600;
        }
        
        /* Contenu principal */
        .main-content {
            flex: 1;
            padding: 40px;
            background: #f8f9fa;
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
        }
        
        .page-header {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid var(--light-beige);
        }
        
        .page-header h1 {
            color: var(--dark-brown);
            margin: 0;
            font-size: 1.8em;
            font-weight: 600;
            font-family: 'Georgia', 'Times New Roman', serif;
        }
        
        /* Section fidélité */
        .fidelite-section {
            background: white;
            border-radius: 8px;
            padding: 0;
            box-shadow: 0 2px 15px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        
        .section-title {
            background: var(--dark-brown);
            color: var(--light-beige);
            padding: 20px 25px;
            margin: 0;
            font-size: 1.2em;
            font-weight: 500;
            font-family: 'Georgia', 'Times New Roman', serif;
        }
        
        /* Tableau fidélité */
        .fidelite-table {
            width: 100%;
            border-collapse: collapse;
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
        }
        
        .fidelite-table th {
            background-color: #f8f9fa;
            color: var(--dark-brown);
            padding: 16px 20px;
            text-align: left;
            font-weight: 600;
            border-bottom: 2px solid var(--light-beige);
            font-size: 0.9em;
        }
        
        .fidelite-table td {
            padding: 16px 20px;
            border-bottom: 1px solid #e9ecef;
            color: var(--dark-brown);
            font-size: 0.9em;
        }
        
        .fidelite-table tr:last-child td {
            border-bottom: none;
        }
        
        .fidelite-table tr:hover {
            background-color: rgba(214, 200, 189, 0.08);
        }
        
        /* Points */
        .points-badge {
            padding: 6px 12px;
            border-radius: 20px;
            font-size: 0.85em;
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
            padding: 50px 20px;
            color: var(--medium-brown);
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
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

    <!-- Dashboard avec sidebar -->
    <div class="dashboard-layout">
        <!-- Sidebar -->
        <aside class="sidebar">
            <div class="sidebar-header">
                <h3>Coiffure Élégance</h3>
            </div>
            <ul class="sidebar-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/dashboard">
                        Tableau de Bord
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/clients">
                        Gestion des Clients
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/coiffeurs">
                        Gestion des Coiffeurs
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/fidelite" class="active">
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
                                    <th>ID Client</th>
                                    <th>Nom</th>
                                    <th>Prénom</th>
                                    <th>Points</th>
                                    <th>Réduction applicable</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="c" items="${listeFidelite}">
                                    <tr>
                                        <td>${c.id}</td>
                                        <td><strong>${c.nom}</strong></td>
                                        <td><strong>${c.prenom}</strong></td>
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