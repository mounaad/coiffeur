<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
        body { font-family: Arial, sans-serif; text-align: center; margin-top: 100px; }
        .error { color: red; font-size: 20px; }
        a { text-decoration: none; color: #007BFF; }
    </style>
</head>
<body>
 <h1 class="error">Oups ! Une erreur est survenue.</h1>
    <p>${errorMessage}</p>
    <p><a href="login.jsp">Retour à la page de connexion</a></p>
</body>
</html>