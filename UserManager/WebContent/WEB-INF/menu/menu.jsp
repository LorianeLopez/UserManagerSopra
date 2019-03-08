<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Menu</title>
</head>
<body>
	<fieldset>
		<a href="<c:url value="/Login"/>">Connexion à mon espace</a><br>
		<a href="<c:url value="/Register"/>">Créer un nouvel utilisateur</a><br>
		<a href="<c:url value="/users"/>">Afficher tous les utilisateurs</a><br>
		<a href="<c:url value="/Index"/>">Accueil</a>
	</fieldset>
</body>
</html>