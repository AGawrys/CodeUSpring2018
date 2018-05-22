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
      <a href="/profile">Profile</a>
       <% if (request.getSession().getAttribute("user") != null) { %>
      <a href="/admin"> Admin page </a>
       <% } else { %>
        <a href="/login">Login as Admin</a>
      <% } %>
    </nav>
</head>
  <body>
      <div style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;" id="container">
        <h1><%= request.getSession().getAttribute("user")%></h1>
        <h1> ABOUT ME:</h1>
          <div class="aboutMe">
          <textarea name="description" id="description" class="form-control">About me</textarea>
          </div>
          <button type="submit">Submit</button>
      </div>
  </body>
</html>
