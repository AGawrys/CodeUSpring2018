<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<html>
  <head>
    <title>Login</title>
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
         <li><a href="/about.jsp">About</a></li>
         <li><a href="/activityfeed">Feed</a></li>
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
  <div id="container">
    <h1>Login</h1>

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

    <form action="/login" method="POST">
      <label for="username">Username: </label>
      <br/>
      <input type="text" name="username" id="username">
      <br/>
      <label for="password">Password: </label>
      <br/>
      <input type="password" name="password" id="password">
      <br/><br/>
      <button type="submit">Login</button>
    </form>

    <p>New users can register <a href="/register">here</a>.</p>
  </div>
</body>
</html>
