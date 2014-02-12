<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page language="java" contentType="text/html"%>
<%@page trimDirectiveWhitespaces="true"%>
<%@page session="true" import="java.util.Vector, ebookshop.Book"%>
<html>
<head>
<title>E-bookshop</title>
	<style type="text/css">
		body 
		{
			background-color: gray;
			font-size:10pt;
		}
		
		H1 
		{
			font-size: 20pt;
		}
		
		table 
		{
			background-color: white;
		}
	</style>
</head>
<body>
	<H1>TIENDA EN LA RED</H1>
	<hr />
	<%
		Vector booklist = (Vector<ebookshop.Book>) session.getAttribute("ebookshop.list");
		session.setAttribute("booklistx", booklist);

		if (booklist == null) 
		{
			response.sendRedirect("/ebookshop_JSTL/eshop");
		} 
		else 
		{
	%>

	<form name="addForm" action="eshop" method="POST">
		<input type="hidden" name="do_this" value="add"> LIBRO: <select
			name=book>

			<c:forEach var="reg" items="${booklistx}">
				<option>${reg}</option>
			</c:forEach>
		</select> CANTIDAD: <input type="text" name="qty" size="3" value="1"> 
							<input type="submit" value="Añadir al carrito">
	</form>
	<%
		Vector<ebookshop.Book> shoplist = (Vector<ebookshop.Book>) session.getAttribute("ebookshop.cart");
		if (shoplist != null && shoplist.size() > 0) 
		{
			session.setAttribute("carrito", shoplist);
			int contador=0;
	%>
	<table border="1">
		<tr>
			<td align="center">TITULO</td>
			<td align="center">PRECIO</td>
			<td align="center">CANTIDAD</td>
			<td align="center">ELIMINAR</td>
		</tr>
		<tr>
				
		<c:forEach var="libro" items="${carrito}">
		<tr>
			<form name="updateForm" action="eshop" method="POST">
				<input type="hidden" name="position" value="<%=contador%>" />
				<input type="hidden" name="do_this" value="update" />
				<td><c:out value="${libro.title}"></c:out></td>
				<td align="right">$<c:out value="${libro.price}"></c:out></td>
				<td align="right">
					<input type="text" size="2" name="quantity" value="${libro.quantity}">
					<input type="submit" value="Actualizar"/></td>      
			</form>
			<form name="removeForm" action="eshop" method="POST">
				<input type="hidden" name="position" value="<%=contador++%>" />
				<input type="hidden" name="do_this" value="remove" />
				<td><input type="submit" value="Eliminar del carrito"></td>
			</form>
		</tr>
    	</c:forEach>
			
		</tr>
	</table>
	<br/>
	<form name="checkoutForm" action="eshop" method="POST">
		<input type="hidden" name="do_this" value="checkout"> 
		<input type="submit" value="Checkout">
	</form>
	<%
		}
		}
	%>
</body>
</html>
