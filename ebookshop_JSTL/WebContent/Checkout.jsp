<%@page language="java" contentType="text/html"%>
<%@page session="true" import="java.util.Vector, ebookshop.Book" %>
<html>
<head>
	<title>E-Bookshop Checkout</title>
	<style type="text/css">
		body 
		{
			background-color:gray; 
			font-size=10pt;
		}
		H1 
		{
			font-size:20pt;
		}
		table 
		{
			background-color:white;
		}
  	</style>
  	</head>
<body>
  <H1>TIENDA EN LA RED</H1>
  <hr/>
  <table border="1" cellpadding="2">
	<tr>
	      <td align="center">TITULO</td>
	      <td align="center">PRECIO</td>
	      <td align="center">CANTIDAD</td>
	</tr>
<%
    Vector<Book> shoplist = (Vector<Book>)session.getAttribute("ebookshop.cart");
    for (Book anOrder : shoplist) 
    {
 %>
      <tr>
        <td><%=anOrder.getTitle()%></td>
        <td align="right">$<%=anOrder.getPrice()%></td>
        <td align="right"><%=anOrder.getQuantity()%></td>
      </tr>
<%
    }
    //Comentar en caso de que no queramos cerrar la sesión al darle a seguir comprando
    session.invalidate(); 
  %>
    <tr>
      <td>TOTALS</td>
      <td align="right">$<%=(String)request.getAttribute("dollars")%></td>
      <td align="right"><%=(String)request.getAttribute("books")%></td>
      </tr>
  </table>
  <br/>
  <a href="/ebookshop_JSTL/eshop">SEGUIR COMPRANDO!</a>
</body>
</html>
