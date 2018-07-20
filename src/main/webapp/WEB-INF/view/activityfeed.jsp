<%--
    Document   : activity
    Created on : May 18, 2018, 1:21:58 AM
    Author     : Agnieszka
--%>


<%@page import="codeu.model.data.Type"%>
<%@page import="codeu.model.data.Activity"%>
<%@page import="codeu.model.store.basic.UserStore"%>
<%@page import="java.util.List"%>
<%@page import="codeu.model.data.Message"%>

<%
List<Activity> activities = (List<Activity>) request.getAttribute("activities");
%>
<%@include file = "activityfeed-helper.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>Activity</title>
  <link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="/css/main.css">
  <style>
    #feed {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var feedDiv = document.getElementById('feed');
      feedDiv.scrollTop = feedDiv.scrollHeight;
    };
  </script>
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
 
<body onload="scrollChat()">
  <div id="container">

    <h1><%= "Here's what happening on murmur" %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="feed">
      <p>
        Updates will appear here.
      </p>
      <%
        String result;
        for (Activity activity : activities) {
            if (activity.getType() == Type.MESSAGESENT) {
                result = messageSent(activity);
            } else if (activity.getType() == Type.CONVERSATIONSTART) {
                result = conversationStarted(activity);
            } else {
                result = userJoined(activity);
            }
    %>
        <li><%= result %></li>
    <%
        }
    %>
        </div>
    </body>
</html>
