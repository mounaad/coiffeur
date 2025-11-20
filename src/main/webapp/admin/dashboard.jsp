<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard Admin</title>
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
    
    /* Message de bienvenue */
    .welcome-section {
        background: linear-gradient(135deg, var(--burgundy), var(--dark-brown));
        color: var(--light-beige);
        padding: 40px;
        border-radius: 10px;
        margin-bottom: 40px;
        text-align: center;
    }
    
    .welcome-section h2 {
        font-size: 2em;
        margin-bottom: 10px;
    }
    
    .welcome-section p {
        font-size: 1.2em;
        opacity: 0.9;
    }
    
    /* Cartes de statistiques */
    .stats-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 25px;
        margin-bottom: 40px;
    }
    
    .stat-card {
        background: white;
        border-radius: 8px;
        padding: 25px;
        box-shadow: 0 2px 15px rgba(0,0,0,0.08);
        text-align: center;
        transition: all 0.3s ease;
        border-top: 4px solid var(--burgundy);
    }
    
    .stat-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 5px 20px rgba(0,0,0,0.12);
    }
    
    .stat-icon {
        font-size: 2.5em;
        margin-bottom: 15px;
        color: var(--burgundy);
    }
    
    .stat-number {
        font-size: 2em;
        font-weight: bold;
        color: var(--dark-brown);
        margin-bottom: 5px;
    }
    
    .stat-label {
        color: var(--medium-brown);
        font-size: 0.9em;
        font-weight: 500;
    }
    
    /* Cartes d'ajout */
    .add-cards-grid {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
        gap: 25px;
        margin-bottom: 40px;
    }
    
    .add-card {
        background: white;
        border-radius: 8px;
        padding: 30px;
        box-shadow: 0 2px 15px rgba(0,0,0,0.08);
        text-align: center;
        transition: all 0.3s ease;
        border-top: 4px solid var(--burgundy);
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        min-height: 180px;
    }
    
    .add-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 5px 20px rgba(0,0,0,0.12);
    }
    
    .add-card-icon {
        font-size: 3em;
        margin-bottom: 15px;
        color: var(--burgundy);
    }
    
    .add-card-title {
        font-size: 1.3em;
        font-weight: 600;
        color: var(--dark-brown);
        margin-bottom: 10px;
        font-family: 'Georgia', 'Times New Roman', serif;
    }
    
    .add-card-description {
        color: var(--medium-brown);
        font-size: 0.9em;
        margin-bottom: 20px;
        line-height: 1.5;
    }
    
    .add-card-btn {
        display: inline-block;
        padding: 10px 20px;
        background: var(--burgundy);
        color: var(--light-beige);
        text-decoration: none;
        border-radius: 6px;
        font-weight: 500;
        transition: all 0.3s ease;
        font-size: 0.95em;
        border: 2px solid var(--burgundy);
    }
    
    .add-card-btn:hover {
        background: var(--dark-brown);
        border-color: var(--dark-brown);
        color: white;
    }
    
    /* Section des tableaux */
    .section {
        background: white;
        border-radius: 8px;
        padding: 0;
        box-shadow: 0 2px 15px rgba(0,0,0,0.08);
        overflow: hidden;
        margin-bottom: 30px;
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
    
    /* Tableaux */
    .data-table {
        width: 100%;
        border-collapse: collapse;
        font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
    }
    
    .data-table th {
        background-color: #f8f9fa;
        color: var(--dark-brown);
        padding: 16px 20px;
        text-align: left;
        font-weight: 600;
        border-bottom: 2px solid var(--light-beige);
        font-size: 0.9em;
    }
    
    .data-table td {
        padding: 16px 20px;
        border-bottom: 1px solid #e9ecef;
        color: var(--dark-brown);
        font-size: 0.9em;
    }
    
    .data-table tr:last-child td {
        border-bottom: none;
    }
    
    .data-table tr:hover {
        background-color: rgba(214, 200, 189, 0.08);
    }
    
    /* Badges de statut */
    .status-badge {
        padding: 6px 12px;
        border-radius: 4px;
        font-size: 0.85em;
        font-weight: 500;
    }
    
    .status-active {
        background: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
    }
    
    .status-pending {
        background: #fff3cd;
        color: #856404;
        border: 1px solid #ffeaa7;
    }
    
    .status-inactive {
        background: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
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
    }
    
    .btn-edit {
        background: #17a2b8;
        color: white;
    }
    
    .btn-delete {
        background: #dc3545;
        color: white;
    }
    
    .btn-validate {
        background: #28a745;
        color: white;
    }
    
    .btn-edit:hover {
        background: #138496;
    }
    
    .btn-delete:hover {
        background: #c82333;
    }
    
    .btn-validate:hover {
        background: #218838;
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
        
        .stats-grid {
            grid-template-columns: 1fr;
        }
        
        .add-cards-grid {
            grid-template-columns: 1fr;
        }
        
        .action-buttons {
            flex-direction: column;
        }
        
        .data-table {
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
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">
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
                <h1>Tableau de Bord Administrateur</h1>
            </div>

            <!-- Message de bienvenue -->
            <div class="welcome-section">
                <h2>Bienvenue, <%= user.getNom() %> !</h2>
                <p>Gérez l'ensemble de votre salon de coiffure </p>
            </div>

            <!-- Cartes de statistiques -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-icon">👥</div>
                    <div class="stat-number">${totalClients}</div>
                    <div class="stat-label">Clients Inscrits</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">💇</div>
                    <div class="stat-number">${totalCoiffeurs}</div>
                    <div class="stat-label">Coiffeurs</div>
                </div>
            </div>
            
            <!-- Cartes d'ajout -->
            <div class="add-cards-grid">
                <div class="add-card">
                    <div class="add-card-icon">👤</div>
                    <h3 class="add-card-title">Ajouter un Client</h3>
                    <p class="add-card-description">Créez un nouveau profil client dans votre système</p>
                    <a href="ajouterClient.jsp" class="add-card-btn">
                        Ajouter un Client
                    </a>
                </div>
                
                <div class="add-card">
                    <div class="add-card-icon">✂️</div>
                    <h3 class="add-card-title">Ajouter un Coiffeur</h3>
                    <p class="add-card-description">Intégrez un nouveau coiffeur à votre équipe</p>
                    <a href="ajouterCoiffeur.jsp" class="add-card-btn">
                        Ajouter un Coiffeur
                    </a>
                </div>
            </div>

        </main>
    </div>
</body>
</html>