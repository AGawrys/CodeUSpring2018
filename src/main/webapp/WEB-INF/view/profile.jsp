<%@ page import="codeu.model.data.About" %>
<%@ page import="codeu.model.store.basic.AboutMeStore" %>
<%@ page import="java.util.List" %>

<%
About about = (About) request.getAttribute("AboutMe");
%>
<!DOCTYPE html>
<html>
<head>
  <title><%= request.getSession().getAttribute("user") %></title>
  <link rel="stylesheet" href="/css/main.css">
  <link href="https://fonts.googleapis.com/css?family=Montserrat:700" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
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
  <body>
      <div style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;" id="container">
        <h1><%= request.getSession().getAttribute("user")%></h1>
        <h1> ABOUT ME:</h1>
        <% if(request.getParameter("AboutMe") == null){
        }else{%>
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
