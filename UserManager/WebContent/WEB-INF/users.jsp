<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Users</title>
<link type="text/css" rel="stylesheet" href="css/css.css" />
</head>
<body>
	<c:import url="menu/menu.jsp" />

	<br />
	<br />
	<c:choose>
		<c:when test="${sessionScope.users != null }">
			<table>
				<thead>
					<tr>
						<th>Email</th>
						<th>Nom</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${sessionScope.users}" var="user">
						<tr>
							<td>${user.key}</td>
							<td><c:out value="${user.value.name}"></c:out></td>
							<c:url value="/deleteUser" var="myUrl">
								<c:param name="email" value="${user.key}"></c:param>
							</c:url>
							<td><a href='${myUrl}'> <input type="button"
									name="Supprimer" value="Supprimer" />
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
	</c:choose>
</body>
</html>