<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>CodeU Chat App</title>
   <link rel="stylesheet" href="/css/main.css">


     <nav>
       <a id="navTitle" href="/">CodeU Chat App</a>
       <a href="/conversations">Conversations</a>
       <% if(request.getSession().getAttribute("user") != null){ %>
         <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
       <% } else{ %>
         <a href="/login">Login</a>
       <% } %>
       <a href="/about.jsp">About</a>
       <a href="/profile">Profile</a>
        <% if (request.getSession().getAttribute("user") != null) { %>
       <a href="/admin"> Admin page </a>
        <% } else { %>
         <a href="/login">Login as Admin</a>
       <% } %>
     </nav>
  </nav>
</head>
<body>
 <% if (request.getSession().getAttribute("user") != null) { %>
    <h1>this is what the admin page looks like</h1>
     <% } else { %>
      <h1>you need to be an admin to see this</h1>
       <% } %>
</body>
</html>
