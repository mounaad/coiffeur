<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion - Coiffure Élégance</title>
<link rel="stylesheet" href="css/login.css">
</head>
<body>
    <!-- En-tête -->
    <header>
        <div class="container">
            <div class="header-content">
                <div class="logo">Coiffure<span>Élégance</span></div>
                <nav>
                    <ul>
                        <li><a href="index.html">Accueil</a></li>
                        <li><a href="#">Services</a></li>
                        <li><a href="#">À propos</a></li>
                        <li><a href="#">Contact</a></li>
                    </ul>
                </nav>
                <div class="auth-buttons">
                    <a href="#">Inscription</a>
                </div>
            </div>
        </div>
    </header>

    <!-- Section de connexion -->
    <section class="login-section">
        <div class="container" >
            <div class="login-container">
                <div class="login-header">
                    <h1>Connexion</h1>
                    <p>Accédez à votre compte pour gérer vos rendez-vous</p>
                </div>
                
                <form class="login-form" action="${pageContext.request.contextPath}/login" method="post">
                    <div class="form-group">
                        <label for="username">Email ou nom d'utilisateur</label>
                        <input type="text" id="username" name="username" placeholder="Entrez votre email ou nom d'utilisateur" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="password">Mot de passe</label>
                        <input type="password" id="password" name="password" placeholder="Entrez votre mot de passe" required>
                    </div>
                    
                    <button type="submit" class="login-button">Se connecter</button>
                    
                    <p style="color:red">${error}</p>
                </form>
                
                <div class="register-link">
                    <p>Pas encore de compte ? <a href="#">Inscrivez-vous ici</a></p>
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
                <p>&copy; 2023 Coiffure Élégance. Tous droits réservés.</p>
            </div>
        </div>
    </footer>
</body>
</html>