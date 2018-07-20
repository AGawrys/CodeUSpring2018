package codeu.controller;

import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

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
/**
   **
      * Gets the correct stats from datastore and stores them in a map, simulating a json file
      */
     public void addStats(Map<String, String> map) {
       // Retrieve sizes of each Datastore
       Integer totalUsers = userStore.getUserCount();
       Integer totalMessages = messageStore.getMessagesCount();
       Integer totalConvos = conversationStore.getConversationCount();

       // Adds them to the map
       map.put("totalUsers", totalUsers.toString());
       map.put("totalMessages", totalMessages.toString());
       map.put("totalConvos", totalConvos.toString());
     }

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
      Map<String, String> adminStatsMap = new HashMap<>();
      String username = (String) request.getSession().getAttribute("user");
      User user = userStore.getUser(username);
      if (user == null) {
        // user was not found, don't let them access admin
        System.out.println("User not found: " + username);
        response.sendRedirect("/login");
        return;
      }

      if(user.isAdmin()){
      // user is in admin list, give access to admin site, and send map as attribute
      addStats(adminStatsMap);
      request.setAttribute("adminStatsMap", adminStatsMap);
      adminStatsMap.forEach((key,value) -> System.out.println(key + " = " + value));
      request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
      return;
    }

      // user isn't in admin list, redirect him to root
      response.sendRedirect("/");

    }
    /**
      *  Gets an entry made by an admin username, cleans it and if conditions are method
      *  that entry (username) acquires admin privileges.
      */
     @Override
     public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {

         //Get username submitted & clean user entry
         String toBeAdmin = request.getParameter("toBeAdminUser");
         toBeAdmin = Jsoup.clean(toBeAdmin, Whitelist.none());

         User user = userStore.getUser(toBeAdmin);

         if(user == null) {
           //If user doesn't exist send error message
           request.setAttribute("error", "User " + toBeAdmin + " doesn't exist.");
           request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
           return;
         } else if(user.isAdmin()) {
           //If user is already an admin, send error message
           request.setAttribute("error", "User " + toBeAdmin + " is already an admin.");
           request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
           return;
         } else {
           //User exists and it's not an admin, set admin -> true, send sucess message
           user.setAdmin(true);
           userStore.updateUser(user);
           request.setAttribute("success", toBeAdmin + " is now an admin!");
           request.getRequestDispatcher("/WEB-INF/view/admin.jsp").forward(request, response);
           return;
         }

     }
   }
