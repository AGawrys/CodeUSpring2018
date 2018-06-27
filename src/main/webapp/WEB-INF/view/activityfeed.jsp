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
  <link rel="stylesheet" href="/css/main.css" type="text/css">

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
<body onload="scrollChat()">
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
