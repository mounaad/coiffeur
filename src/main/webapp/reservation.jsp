<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Service" %>
<%@ page import="dao.ServiceDaoImp" %>
<%@ page import="dao.Factory" %>
<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
User user = (User) session.getAttribute("user");
if(user == null || !"client".equals(user.getRole())) {
    response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
}

// Liste des services
ServiceDaoImp serviceDao = new ServiceDaoImp(Factory.getConnection());
List<Service> services = serviceDao.getAllServices();

// Date du jour pour le minimum
String dateAujourdhui = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prendre un rendez-vous - Coiffure Elégance</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/reservation.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <!-- En-tête -->
    <header>
        <div class="container">
            <div class="header-content">
                <div class="logo">Coiffure<span>Elégance</span></div>
                <nav>
                    <ul>
                      
                        <li><a href="${pageContext.request.contextPath}/client/dashboard.jsp">Mon Compte</a></li>
                    </ul>
                </nav>
                <div class="auth-buttons">
                    <span style="color: var(--light-beige); margin-right: 15px;">
                        Bonjour, <%= user.getNom() %>
                    </span>
                    <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
                </div>
            </div>
        </div>
    </header>

    <!-- Section réservation -->
    <section class="reservation-section">
        <div class="container">
            <div class="reservation-container">
                <div class="reservation-header">
                    <h1><i class="fas fa-calendar-plus"></i> Prendre un rendez-vous</h1>
                    <p>Choisissez votre service et la date qui vous convient</p>
                </div>

                <!-- Messages -->
                <c:if test="${not empty message}">
                    <div class="message success">
                        <i class="fas fa-check-circle"></i> ${message}
                    </div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="message error">
                        <i class="fas fa-exclamation-circle"></i> ${error}
                    </div>
                </c:if>

                <!-- Formulaire de réservation -->
                <form class="reservation-form" method="post" action="${pageContext.request.contextPath}/ReservationServlet">
                    
                    <!-- SERVICE -->
                    <div class="form-group">
                        <label for="id_service">
                             Service :
                        </label>
                        <select id="id_service" name="id_service" required>
                            <option value="">Sélectionnez un service</option>
                            <% for(Service s : services) { %>
                            <option value="<%= s.getId() %>">
                                <%= s.getNom() %> - <%= s.getPrix() %> DH (<%= s.getDuree() %> min)
                            </option>
                            <% } %>
                        </select>
                    </div>

                    <!-- DATE ET HEURE -->
                    
                        <div class="form-group">
                            <label for="date">
                                <i class="fas fa-calendar-alt"></i> Date :
                            </label>
                            <input type="date" id="date" name="date" required min="<%= dateAujourdhui %>">
                        
                            <label for="heure">
                                <i class="fas fa-clock"></i> Heure (entre 09:00 et 23:00) :
                            </label>
                            <input type="time" id="heure" name="heure" required 
                                   min="09:00" max="18:00"
                                   title="Veuillez entrer une heure entre 09:00 et 18:00">
                            <small style="color: #666; font-size: 13px; display: block; margin-top: 5px;">
                                Format : HH:MM (exemple : 14:30)
                            </small>
                        </div>
                    

                    <!-- COIFFEUR (caché par défaut, apparaît après sélection) -->
                    <div class="form-group" id="coiffeur-container" style="display:none;">
                        <label for="id_coiffeur">
                            <i class="fas fa-user"></i> Coiffeur disponible :
                        </label>
                        <select id="id_coiffeur" name="id_coiffeur" required>
                            <option value="">Choisissez un coiffeur</option>
                        </select>
                    </div>

                    

                    <!-- BOUTON SUBMIT -->
                    <button type="submit" class="reservation-button">
                        <i class="fas fa-calendar-check"></i> Confirmer le rendez-vous
                    </button>
                </form>

                <!-- Lien retour -->
                <div style="text-align: center; margin-top: 20px;">
                    <a href="${pageContext.request.contextPath}/client/dashboard" 
                       style="color: var(--burgundy); text-decoration: none;">
                        <i class="fas fa-arrow-left"></i> Retour au tableau de bord
                    </a>
                </div>
            </div>
        </div>
    </section>

    
    
    <!-- SCRIPT JAVASCRIPT -->
    <script>
        
        // Context Path
        const contextPath = "<%= request.getContextPath() %>";
        console.log("📍 Context Path:", contextPath);
        
        // Éléments du DOM
        const serviceSelect = document.getElementById("id_service");
        const dateInput = document.getElementById("date");
        const heureInput = document.getElementById("heure");
        const coiffeurContainer = document.getElementById("coiffeur-container");
        const coiffeurSelect = document.getElementById("id_coiffeur");
        
        // Vérification des éléments
        if (!serviceSelect || !dateInput || !heureInput || !coiffeurContainer || !coiffeurSelect) {
            console.error("❌ Éléments du formulaire introuvables!");
        } else {
            console.log("✅ Tous les éléments trouvés");
            
            // Événements
            serviceSelect.addEventListener("change", checkAvailability);
            dateInput.addEventListener("change", checkAvailability);
            heureInput.addEventListener("change", checkAvailability);
        }
        
        // Fonction principale
        function checkAvailability() {
            
            const id_service = serviceSelect.value;
            const date = dateInput.value;
            const heure = heureInput.value;

            console.log("📋 Valeurs:");
            console.log("   Service:", id_service);
            console.log("   Date:", date);
            console.log("   Heure:", heure);

            // Validation
            if (!id_service || !date || !heure) {
                console.log("⚠️ Valeurs incomplètes - masquage");
                coiffeurContainer.style.display = "none";
                return;
            }

            console.log("✅ Toutes les valeurs OK");

            // Construction URL
            const url = contextPath + "/GetCoiffeursDispo" +
                "?id_service=" + encodeURIComponent(id_service) +
                "&date=" + encodeURIComponent(date) +
                "&heure=" + encodeURIComponent(heure);
            
            console.log("📡 URL:", url);

            // Loader
            coiffeurSelect.innerHTML = '<option value="">⏳ Chargement...</option>';
            coiffeurContainer.style.display = "block";

            // Requête AJAX
            fetch(url)
                .then(response => {
                    console.log("📥 Réponse reçue");
                    console.log("   Status:", response.status);
                    
                    if (!response.ok) {
                        throw new Error('Erreur serveur (HTTP ' + response.status + ')');
                    }
                    
                    return response.text();
                })
                .then(text => {
                    console.log("📄 Réponse brute:", text);
                    
                    // Parse JSON
                    let data;
                    try {
                        data = JSON.parse(text);
                    } catch (e) {
                        throw new Error("Format JSON invalide");
                    }
                    
                    // Réinitialisation
                    coiffeurSelect.innerHTML = '<option value="">Choisissez un coiffeur</option>';

                    // Remplissage
                    if (!data || data.length === 0) {
                        coiffeurSelect.innerHTML += '<option value="" disabled>Aucun coiffeur disponible</option>';
                    } else {
                        console.log("✅ " + data.length + " coiffeur(s) trouvé(s):");
                        
                        data.forEach((coiffeur, index) => {
                            const id = coiffeur.idCoiffeur || coiffeur.id || coiffeur.id_coiffeur;
                            const nom = coiffeur.nom || "";
                            
                            
                            
                            console.log("   " + (index + 1) + ". ID=" + id + " | " + nom);
                            
                            const option = document.createElement('option');
                            option.value = id;
                            option.textContent = nom;
                            coiffeurSelect.appendChild(option);
                        });
                    }

                    // Toujours afficher le container
                    coiffeurContainer.style.display = "block";
                })
                .catch(err => {
                    console.error("❌ ERREUR:", err);
                    coiffeurSelect.innerHTML = '<option value="">❌ Erreur de chargement</option>';
                    alert("Erreur: " + err.message + "\n\nVérifiez:\n1. Les logs Tomcat/Eclipse\n2. La console (F12)");
                });
        }
        
    </script>
    
</body>
</html>