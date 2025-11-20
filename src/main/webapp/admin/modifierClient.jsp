<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Client</title>
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
        
        /* Section formulaire */
        .form-section {
            background: white;
            border-radius: 8px;
            padding: 0;
            box-shadow: 0 2px 15px rgba(0,0,0,0.08);
            overflow: hidden;
            width: 100%;
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
        
        /* Formulaire */
        .form-container {
            padding: 40px;
        }
        
        .form-single-column {
            max-width: 100%;
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: var(--dark-brown);
            font-weight: 500;
            font-size: 0.95em;
        }
        
        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 0.95em;
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
            transition: border-color 0.3s ease;
        }
        
        .form-control:focus {
            outline: none;
            border-color: var(--burgundy);
            box-shadow: 0 0 0 2px rgba(84, 20, 9, 0.1);
        }
        
        /* Boutons */
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.95em;
            font-weight: 500;
            transition: all 0.3s ease;
            font-family: 'Arial', 'Helvetica Neue', Helvetica, sans-serif;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }
        
        .btn-primary {
            background: #17a2b8;
            color: white;
        }
        
        .btn-secondary {
            background: #6c757d;
            color: white;
            margin-right: 10px;
        }
        
        .btn-primary:hover {
            background: #138496;
        }
        
        .btn-secondary:hover {
            background: #5a6268;
        }
        
        .button-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
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
            
            .form-container {
                padding: 25px;
            }
            
            .button-group {
                flex-direction: column;
            }
            
            .btn-secondary {
                margin-right: 0;
                margin-bottom: 10px;
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
                <h1>Modifier Client</h1>
            </div>

            <!-- Section formulaire -->
            <div class="form-section">
                <h3 class="section-title">Modification du Client</h3>
                
                <div class="form-container">
                    <!-- Formulaire en une seule colonne -->
                    <form method="post" action="${pageContext.request.contextPath}/admin/clients" class="form-single-column">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${client.id}">
                        
                        <!-- Champs du formulaire -->
                        <div class="form-group">
                            <label for="nom">Nom :</label>
                            <input type="text" id="nom" name="nom" class="form-control" value="${client.nom}" required placeholder="Nom du client">
                        </div>
                        
                        <div class="form-group">
                            <label for="prenom">Prénom :</label>
                            <input type="text" id="prenom" name="prenom" class="form-control" value="${client.prenom}" required placeholder="Prénom du client">
                        </div>
                        
                        <div class="form-group">
                            <label for="email">Email :</label>
                            <input type="email" id="email" name="email" class="form-control" value="${client.email}" required placeholder="email@exemple.com">
                        </div>
                        
                        <div class="form-group">
                            <label for="telephone">Téléphone :</label>
                            <input type="text" id="telephone" name="telephone" class="form-control" value="${client.telephone}" required placeholder="Numéro de téléphone">
                        </div>
                        
                        <!-- Boutons -->
                        <div class="button-group">
                            <a href="${pageContext.request.contextPath}/admin/clients" class="btn btn-secondary">
                                ← Retour à la liste
                            </a>
                            <button type="submit" class="btn btn-primary">
                                Mettre à jour
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</body>
</html>