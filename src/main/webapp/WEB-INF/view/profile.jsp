
<%@page import="codeu.model.data.User"%>
<%@ page import="codeu.model.data.About" %>
<%@ page import="codeu.model.store.basic.AboutMeStore" %>
<%@ page import="java.util.List" %>

<%About about = (About) request.getAttribute("AboutMe");%>
<!DOCTYPE html>
<html>
<head>
  <title><%= request.getSession().getAttribute("user") %></title>
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
<body>
    <div style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;" id="container">

        <form method="POST" action="upload" enctype="multipart/form-data" >
            File:
            <input type="file" name="file" id="file" /> <br/>
            <input type="submit" value="Upload" name="upload" id="upload" />
        </form>
        <%
            User currentUser = (User) request.getAttribute("user");
            if (currentUser.getImageString() != null) {
        %>
        <img src="data:image/jpeg;base64,<%=request.getAttribute("image")%>" width="200" height="200" />
        <%
        } else {
        %>
        <img src="/images/default_pfp.jpg" width="200" height="200" />
        <%
            }
        %>

        <h1><%= request.getSession().getAttribute("user")%></h1>
        <h1> ABOUT ME:</h1>
        <% if (request.getParameter("AboutMe") == null) {
            } else {%>
        <h1><%= request.getParameter("AboutMe") /*about.getTitle()*/%></h1>
        <%}%>
        <div class="aboutMe">
            <form action="/user/<%=request.getSession().getAttribute("user")%>" method="GET">
                <textarea name="AboutMe" rows="10" cols="30">
                What about you?
                </textarea>
                <br/><br/>
                <input type="submit" value="Submit">
            </form>    
        </div>
    </div>
</body>
</html>
