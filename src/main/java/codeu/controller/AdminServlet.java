package codeu.controller;
import javax.swing.*;
import java.awt.*;
import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for the login page. */
public class AdminServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  private ConversationStore conversationStore;

  /**
   * Set up state for handling login-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  void setConversationStore(ConversationStore conversationStore) {
    this.conversationStore = conversationStore;
  }
  /**
   * This function fires when a user requests the /profile URL. It simply forwards the request to
   * admin.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
    String username = (String) request.getSession().getAttribute("user");
        if (username == null) {
  //User is not logged in
          response.sendRedirect("/login");
          return;
        }

    User user = userStore.getUser(username);
        if (user == null) {
  //User was not found
          System.out.println("User not found: " + username);
          response.sendRedirect("/login");
          return;
        }
  //Users in this list can access the admin page
        if(user.getName().matches("maria") || user.getName().matches("nieszka") ||
           user.getName().matches("gregory")){
          request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
          return;
        }
  }

}
