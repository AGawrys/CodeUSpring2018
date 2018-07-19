<%--
  Copyright 2017 Google Inc.
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  \
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><%= conversation.getTitle() %></title>
  <link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link rel="stylesheet" href="/css/main.css">
  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
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
    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>
    <hr/>
    <div id="chat">
      <ul>
        <%
         String currentUser= "@" + request.getSession().getAttribute("user");
          for (Message message : messages) {
          String author = UserStore.getInstance().getUser(message.getAuthorId()).getName();
         %>
         <li><strong><a href="/user/<%=author%>"><%= author %></a></strong></li>
         <%  if(message.getContent().contains(currentUser)){ %>
              <strong><%= message.getContent() %></strong>
           <%} else{%>
            <%= message.getContent() %>
         <%
          }
      } %>
      </li>
      </ul>
    </div>
    <hr/>
    <% if (request.getSession().getAttribute("user") != null) { %>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input type="text" name="message">
        <br/>
        <button type="submit">Send</button>
    </form>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>
    <hr/>
  </div>
</body>
</html>
