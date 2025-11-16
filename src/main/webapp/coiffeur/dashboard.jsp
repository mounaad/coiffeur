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
</head>
<body>
<%
    User user = (User) session.getAttribute("user");
    if(user == null || !"coiffeur".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    
%>

<h2>Bonjour <%= user.getNom() %></h2>

<h3>Rendez-vous</h3>
<table border="1">
    <tr>
        <th>Client</th>
        <th>Date</th>
        <th>Heure</th>
        <th>Statut</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="rdv" items="${rdvs}">
        <tr>
            <td>${rdv.nomClient} ${rdv.prenomClient}</td>
            <td>${rdv.dateRdv}</td>
            <td>${rdv.heureRdv}</td>
            <td>${rdv.statut}</td>
            <td>
                <c:if test="${rdv.statut == 'en_attente'}">
                    <form method="post" style="display:inline">
                        <input type="hidden" name="idRdv" value="${rdv.idRdv}"/>
                        <button type="submit" name="action" value="confirme">Confirmer</button>
                    </form>
                    <form method="post" style="display:inline">
                        <input type="hidden" name="idRdv" value="${rdv.idRdv}"/>
                        <button type="submit" name="action" value="annule">Annuler</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>