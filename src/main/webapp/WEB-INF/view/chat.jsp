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
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
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
<body onload="scrollChat()">
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
          %>
          </li>
         <%
           }
         %>
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

  <div>
    <p align="left" margin = "auto">This is some text in a paragraph.</p>
  <div>

</body>
</html>
