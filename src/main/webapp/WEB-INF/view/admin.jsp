<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>



<!DOCTYPE html>

<html>
<head>
  <title>AdminPage</title>
   <link rel="stylesheet" href="/css/main.css">
   <link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
   <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
     <nav>
       <a id="navTitle" href="/">MURMUR</a>
       <% if(request.getSession().getAttribute("user") != null){ %>
         <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
       <% } else{ %>
         <a href="/login">Login</a>
       <% } %>
       <a href="/about.jsp">About</a>
       <a href="/activityfeed">Feed</a>
       <% if(request.getSession().getAttribute("user") != null){ %>
       <a href="/conversations">Conversations</a>
        <% }  %>
       <% if(request.getSession().getAttribute("user") != null){ %>
       <a href="/mentions" > Notifications</a>
        <% }  %>
       <a href="/user/<%=request.getSession().getAttribute("user")%>">Profile</a>
       <% if(request.getSession().getAttribute("user") != null){ %>
       <a href="/logout" > Logout</a>
        <% }  %>
      </nav>
</head>
<div id="container">
    <h1>Admin</h1>

    <form action="/admin" method="POST">
        <p> <b>Make someone admin:<b> </p>
        <input type="text" name="toBeAdminUser" value="" placeholder="username" >
        <br/>
        <button type="submit">Submit</button>
    </form>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } else if(request.getAttribute("success") != null){%>
        <h2 style="color:green"><%= request.getAttribute("success") %></h2>
    <% } %>
  </body>
</html>
