// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.data.About;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.AboutMeStore;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/** Servlet class responsible for the login page. */
public class ProfileServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  private AboutMeStore aboutMeStore;

  /**
   * Set up state for handling login-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setAboutMeStore(AboutMeStore.getInstance());

  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  void setAboutMeStore(AboutMeStore aboutMeStore) {
    this.aboutMeStore = aboutMeStore;
  }

  /**
   * This function fires when a user requests the /profile URL. It simply forwards the request to
   * profile.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {


              String aboutString = request.getParameter("AboutMe");

              //About about = aboutMeStore.getaboutWithTitle(aboutString);
              String requestUrl = request.getRequestURI();
              String userProfile = requestUrl.substring("/user/".length());
              User user = userStore.getUser(userProfile);
              if (user == null) {
                // couldn't find conversation, redirect to conversation list
                System.out.println("Couldn't find " + userProfile);
                response.sendRedirect("/login");
                return;
  }
              request.setAttribute("userProfile", userProfile);
              //request.setAttribute("AboutMe", about);
              request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
  }

  /**
   * This function fires when a user submits the login form. It gets the username and password from
   * the submitted form data, checks for validity and if correct adds the username to the session so
   * we know the user is logged in.
   */
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
       throws IOException, ServletException {

	   String username = (String) request.getSession().getAttribute("user");
	    if (username == null) {

	      response.sendRedirect("/login");
	      return;
	    }
            String requestUrl = request.getRequestURI();

            String aboutString = request.getParameter("AboutMe");
            About about = aboutMeStore.getaboutWithTitle(aboutString);

            String userProfile = requestUrl.substring("/user/".length());

            User user = userStore.getUser(userProfile);
            if (user == null) {
              response.sendRedirect("/login");
              return;
            }


        response.sendRedirect("/user/" + userProfile);
  }
}
