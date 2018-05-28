<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.ConversationStore" %>
<%@ page import="codeu.model.store.basic.MessageStore" %>
<%@ page import="codeu.model.store.basic.UserStore" %>


   <%
ConversationStore conversationStore = ConversationStore.getInstance();
UserStore userStore = UserStore.getInstance();
MessageStore messageStore = MessageStore.getInstance();
/**Gets the total numbers from datastore */
Integer totalConvos = conversationStore.getConversationCount();
Integer totalUsers = userStore.getUserCount();
Integer totalMessages = messageStore.getMessagesCount();

%>
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
    <% if(request.getSession().getAttribute("user") != null){ %>
    <a href="/user/<%=request.getSession().getAttribute("user")%>">Profile</a>
    <% } else{ %>
    <% } %>
     </nav>
</head>

  <body>

      <h1>this is what the admin page looks like with some hard coded data:</h1>
      <ul>
        <li><b>Total Messages: <%= totalMessages %></b></li>
        <li><b>Total Users:<%= totalUsers %></b></li>
        <li><b>Total Conversations:<%= totalConvos %> </b> </li>
      </ul>

  </body>
</html>
