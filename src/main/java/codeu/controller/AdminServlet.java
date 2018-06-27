package codeu.controller;
import javax.swing.*;
import java.awt.*;
import codeu.model.data.User;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for the login page. */
public class AdminServlet extends HttpServlet {

  /** Store class that gives access to Users. */
  private UserStore userStore;

  private ConversationStore conversationStore;

 private MessageStore messageStore;
  /**
   * Set up state for handling login-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setConversationStore(ConversationStore.getInstance());
    setMessageStore(MessageStore.getInstance());
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

  void setMessageStore(MessageStore messageStore) {
    this.messageStore = messageStore;
  }
  /**
   * This function fires when a user requests the /profile URL. It simply forwards the request to
   * admin.jsp.
   */
   public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
      String username = (String) request.getSession().getAttribute("user");
      boolean isRegistered = (username != null);
      boolean isAdmin = isAdmin(username);

      if (isAdmin) {
        request.setAttribute("totalUsers", userStore.getUserCount());
        request.setAttribute("totalConvos", conversationStore.getConversationCount());
        request.setAttribute("totalMessages", messageStore.getMessagesCount());
      }

      request.setAttribute("isRegistered", isRegistered);
      request.setAttribute("isAdmin", isAdmin);

      request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
      return;
    }

    private boolean isAdmin(String username) {
      ArrayList<String> admins = new ArrayList<String>();
      admins.add("mayfnessAdmin");
      admins.add("nizeskaAdmin");
      admins.add("gregoryAdmin");

      return username != null && admins.contains(username);
    }

}
