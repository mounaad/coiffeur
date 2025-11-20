<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des Clients</title>
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
            display: flex;
            justify-content: space-between;
            align-items: center;
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
        
        /* Section des clients */
        .clients-section {
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
        
        /* Tableau des clients */
        .clients-table {
            width: 100%;
            border-collapse: collapse;
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
        }
        
        .clients-table th {
            background-color: #f8f9fa;
            color: var(--dark-brown);
            padding: 16px 20px;
            text-align: left;
            font-weight: 600;
            border-bottom: 2px solid var(--light-beige);
            font-size: 0.9em;
        }
        
        .clients-table td {
            padding: 16px 20px;
            border-bottom: 1px solid #e9ecef;
            color: var(--dark-brown);
            font-size: 0.9em;
        }
        
        .clients-table tr:last-child td {
            border-bottom: none;
        }
        
        .clients-table tr:hover {
            background-color: rgba(214, 200, 189, 0.08);
        }
        
        /* Actions */
        .action-buttons {
            display: flex;
            gap: 8px;
        }
        
        .btn {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.8em;
            font-weight: 500;
            transition: all 0.3s ease;
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }
        
        .btn-add {
            background: var(--burgundy);
            color: white;
            padding: 10px 20px;
            font-size: 0.9em;
        }
        
        .btn-edit {
            background: #17a2b8;
            color: white;
        }
        
        .btn-delete {
            background: #dc3545;
            color: white;
        }
        
        .btn-add:hover {
            background: var(--dark-brown);
        }
        
        .btn-edit:hover {
            background: #138496;
        }
        
        .btn-delete:hover {
            background: #c82333;
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
            
            .page-header {
                flex-direction: column;
                gap: 15px;
                align-items: flex-start;
            }
            
            .action-buttons {
                flex-direction: column;
            }
            
            .clients-table {
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
                    <a href="${pageContext.request.contextPath}/admin/clients" class="active">
                        Gestion des Clients
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/coiffeurs">
                        Gestion des Coiffeurs
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/fidelite">
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
                <h1>Gestion des Clients</h1>
                <a href="${pageContext.request.contextPath}/admin/clients?action=add" class="btn btn-add">+ Ajouter un Client</a>
            </div>

            <!-- Liste des clients -->
            <div class="clients-section">
                <h3 class="section-title">Liste des Clients</h3>
                
                <c:choose>
                    <c:when test="${not empty liste}">
                        <table class="clients-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nom</th>
                                    <th>Prénom</th>
                                    <th>Email</th>
                                    <th>Téléphone</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="c" items="${liste}">
                                    <tr>
                                        <td>${c.id}</td>
                                        <td><strong>${c.nom}</strong></td>
                                        <td><strong>${c.prenom}</strong></td>
                                        <td>${c.email}</td>
                                        <td>${c.telephone}</td>
                                        <td>
                                            <div class="action-buttons">
                                                <!-- Modifier -->
                                                <form action="${pageContext.request.contextPath}/admin/clients" method="get" style="display:inline">
                                                    <input type="hidden" name="action" value="update">
                                                    <input type="hidden" name="id" value="${c.id}">
                                                    <button type="submit" class="btn btn-edit">Modifier</button>
                                                </form>

                                                <!-- Supprimer -->
                                                <form action="${pageContext.request.contextPath}/admin/clients" method="post" style="display:inline"
                                                      onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer ce client ?');">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="id" value="${c.id}">
                                                    <button type="submit" class="btn btn-delete">Supprimer</button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="no-data">
                            <h3>Aucun client enregistré</h3>
                            <p>Commencez par ajouter votre premier client.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>
</body>
</html>