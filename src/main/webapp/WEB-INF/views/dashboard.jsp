<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 2rem auto; padding: 0 1rem }
        .card { background: #fff8e1; border-left: 4px solid #f4a000;
            padding: 1rem 1.5rem; border-radius: 4px; margin-top: 1rem }
        a { color: #f4a000 }
    </style>
</head>
<body>
<h1>Dashboard</h1>
<div class="card">
    <p>¡Bienvenido! Has iniciado sesion como:</p>
    <strong><c:out value="${email}"/></strong>
</div>
<p style="margin-top: 1rem">
    <a href="${pageContext.request.contextPath}/logout">Cerrar sesion</a>
</p>
</body>
</html>