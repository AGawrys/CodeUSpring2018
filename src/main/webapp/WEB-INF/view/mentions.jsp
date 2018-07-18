

<%@page import="codeu.model.data.Type"%>
<%@page import="codeu.model.data.Mention"%>
<%@page import="codeu.model.store.basic.UserStore"%>
<%@page import="java.util.List"%>
<%@page import="codeu.model.data.Message"%>

<%
List<Mention> mentions = (List<Mention>) request.getAttribute("mentions");
%>
<%@include file = "mention-helper.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>Mention</title>
  <link rel="stylesheet" href="/css/main.css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
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
<body onload="scrollChat()">
  <div id="container">
    <h1><%= "You been tagged!" %>
    <a href="" style="float: right">&#8635;</a></h1>
    <hr/>
    <div id="MentionFeed">
    <p>Here are your mentions!</p>
    <%
        String result = "";
        for (Mention mention : mentions) {
          if( getMentionMessage(mention.getObjectId()).contains("@" + request.getSession().getAttribute("user"))){
            if (mention.getType() == Type.MESSAGESENT) {
                result = messageSent(mention);
            } else if (mention.getType() == Type.CONVERSATIONSTART) {
                result = conversationStarted(mention);
            } else {
                result = userJoined(mention);
            }
          }
    %>
        <li><%= result %></li>
    <%
        }
    %>
        </div>

    </body>
</html>
