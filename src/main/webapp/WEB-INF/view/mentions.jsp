
<%@page import="codeu.model.data.Type"%>
<%@page import="codeu.model.data.Mention"%>
<%@page import="codeu.model.store.basic.UserStore"%>
<%@page import="java.util.List"%>
<%@page import="codeu.model.data.Message"%>

<%
List<Mention> mentions = (List<Mention>) request.getAttribute("mentions");
%>
<%@include file = "mentions-helper.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>Mentions</title>
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
    <a href="/user/<%=request.getSession().getAttribute("user")%>">Profile</a>
  </nav>

  <div id="container">

    <h1><%= "Here are your mentions" %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="feed">
      <p>
        These are your mentions:
      </p>

    <%
        String result;
        for (Mention mention : mentions) {
            if (mention.getType() == Type.MESSAGESENT) {
                result = messageSent(mention);
            } else if (mention.getType() == Type.CONVERSATIONSTART) {
                result = conversationStarted(mention);
            } else {
                result = userJoined(mention);
            }
    %>
        <li><%= result %></li>
    <%
        }
    %>
        </div>

    </body>
</html>
