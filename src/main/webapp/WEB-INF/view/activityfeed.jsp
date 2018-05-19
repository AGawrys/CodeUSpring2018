<%-- 
    Document   : activity
    Created on : May 18, 2018, 1:21:58 AM
    Author     : Agnieszka
--%>


<!DOCTYPE html>
<html>
<head>
  <title>Activity</title>
  <link rel="stylesheet" href="/css/main.css">
  
</head>
<body>
    <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else{ %>
      <a href="/login">Login</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/activityfeed">Feed</a>
  </nav>

  <div id="container">
      <h1>Here is what is happening on murmur!</h1>
      </div>
    
    </body>
</html>