
<!DOCTYPE html>
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
    <% if(request.getSession().getAttribute("user") != null){ %>
    <a href="/user/<%=request.getSession().getAttribute("user")%>">Profile</a>
    <% } else{ %>
    <% } %>
     </nav>
</head>
  <body>
      <h1>this is what the admin page looks like</h1>
  </body>
</html>
