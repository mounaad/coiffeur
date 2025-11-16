

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription - Coiffure Élégance</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
<style>
    
    .register-section {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-grow: 1;
        padding: 40px 0;
        background: linear-gradient(rgba(214, 200, 189, 0.9), rgba(214, 200, 189, 0.9)), 
                    url('https://images.unsplash.com/photo-1560066984-138dadb4c035?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
        background-size: cover;
        background-position: center;
    }
    
    .register-container {
    margin: 0 auto;
        background-color: rgba(255, 255, 255, 0.5);
        border-radius: 8px;
        box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        padding: 40px;
        width: 100%;
        max-width: 450px;
    }
    
    .register-header {
        text-align: center;
        margin-bottom: 30px;
    }
    
    .register-header h1 {
        color: var(--dark-brown);
        margin-bottom: 10px;
    }
    
    .register-header p {
        color: var(--medium-brown);
    }
    
    .register-form {
        display: flex;
        flex-direction: column;
    }
    
    .form-group {
        margin-bottom: 20px;
    }
    
    .form-group label {
        display: block;
        margin-bottom: 8px;
        color: var(--dark-brown);
        font-weight: 500;
    }
    
    .form-group input {
        width: 100%;
        padding: 12px 15px;
        border: 1px solid #ddd;
        border-radius: 4px;
        font-size: 16px;
        transition: border-color 0.3s;
    }
    
    .form-group input:focus {
        border-color: var(--burgundy);
        outline: none;
    }
    
    .form-row {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 15px;
    }
    
    .register-button {
        background-color: var(--burgundy);
        color: white;
        border: none;
        padding: 12px;
        border-radius: 4px;
        font-size: 16px;
        font-weight: bold;
        cursor: pointer;
        transition: background-color 0.3s;
        margin-top: 10px;
    }
    
    .register-button:hover {
        background-color: var(--medium-brown);
    }
    
    .error-message {
        color: #d9534f;
        text-align: center;
        margin-top: 15px;
        padding: 10px;
        background-color: rgba(217, 83, 79, 0.1);
        border-radius: 4px;
    }
    
    .login-link {
        text-align: center;
        margin-top: 20px;
    }
    
    .login-link a {
        color: var(--burgundy);
        text-decoration: none;
        font-weight: 500;
    }
    
    .login-link a:hover {
        text-decoration: underline;
    }
    
    .terms-checkbox {
        display: flex;
        align-items: flex-start;
        gap: 10px;
        margin-top: 15px;
    }
    
    .terms-checkbox input {
        margin-top: 3px;
    }
    
    .terms-checkbox label {
        font-size: 14px;
        color: var(--medium-brown);
    }
    
    .terms-checkbox a {
        color: var(--burgundy);
        text-decoration: none;
    }
    
    .terms-checkbox a:hover {
        text-decoration: underline;
    }
</style>
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
                        <li><a href="${pageContext.request.contextPath}/about">À propos</a></li>
                    </ul>
                </nav>
                <div class="auth-buttons">
                    <a href="${pageContext.request.contextPath}/login.jsp">Connexion</a>
                </div>
            </div>
        </div>
    </header>

    
    <section class="register-section">
        <div class="container">
            <div class="register-container">
                <div class="register-header">
                    <h1>Créer un compte</h1>
                    <p>Rejoignez-nous en quelques secondes</p>
                </div>
                
				<form class="register-form" action="${pageContext.request.contextPath}/RegisterServlet" method="post">
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="prenom">Prénom *</label>
                            <input type="text" id="prenom" name="prenom" 
                                   placeholder="Votre prénom" required
                                   value="${param.prenom}">
                        </div>
                        
                        <div class="form-group">
                            <label for="nom">Nom *</label>
                            <input type="text" id="nom" name="nom" 
                                   placeholder="Votre nom" required
                                   value="${param.nom}">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email *</label>
                        <input type="email" id="email" name="email" 
                               placeholder="votre@email.com" required
                               value="${param.email}">
                    </div>
                    
                    <div class="form-group">
                        <label for="telephone">Téléphone</label>
                        <input type="tel" id="telephone" name="telephone" 
                               placeholder="06 00 00 00 00"
                               value="${param.telephone}">
                    </div>
                    
                    <div class="form-group">
                        <label for="mot_de_passe">Mot de passe *</label>
                        <input type="password" id="mot_de_passe" name="mot_de_passe" 
                               placeholder="Créez un mot de passe" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="confirm_password">Confirmer le mot de passe *</label>
                        <input type="password" id="confirm_password" name="confirm_password" 
                               placeholder="Confirmez le mot de passe" required>
                    </div>
                    
                    
					
					    <button type="submit" class="register-button">Créer mon compte</button>
					                   
                    
                   <p style="color:red">${error}</p>
                   <p style="color:green">${succes}</p>
                    
                    
                      
                    
                    
                </form>
                
                <div class="login-link">
                    <p>Déjà un compte ? <a href="${pageContext.request.contextPath}/login.jsp">Connectez-vous ici</a></p>
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