<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>



<!DOCTYPE html>

<html>
  <head>
    <title><%= request.getSession().getAttribute("user") %></title>
    <link rel="stylesheet" href="/css/main.css">
    <link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/css/main.css">
  </head>

   <nav class="navbar navbar-default" role="navigation">
     <!-- Brand and toggle get grouped for better mobile display -->
     <div class="navbar-header">
       <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
         <span class="sr-only">Toggle navigation</span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
       </button>
       <a class="navbar-brand" href="/">MURMUR</a>
     </div>

     <!-- Collect the nav links, forms, and other content for toggling -->
     <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
       <ul class="nav navbar-nav navbar-right">
         <% if(request.getSession().getAttribute("user") != null){ %>
        <li> <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
       <% } else{ %>
         <a href="/login">Login</a></li>
       <% } %>
         <li><a href="/about.jsp">About</a></li>
         <% if(request.getSession().getAttribute("user") != null){ %>
         <li><a href="/activityfeed">Feed</a></li>
         <% }  %>
         <% if(request.getSession().getAttribute("user") != null){ %>
         <li><a href="/conversations">Conversations</a></li>
          <% }  %>
         <% if(request.getSession().getAttribute("user") != null){ %>
         <li><a href="/mentions" > Notifications</a></li>
          <% }  %>
         <li><a href="/user/<%=request.getSession().getAttribute("user")%>">Profile</a></li>
         <% if(request.getSession().getAttribute("user") != null){ %>
         <li><a href="/logout" > Logout</a></li>
          <% }  %>
       </ul>
     </div><!-- /.navbar-collapse -->
   </nav>
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
