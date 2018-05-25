/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeu.controller;

import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Agnieszka
 */
public class ActivityFeedServlet extends HttpServlet {
    
    private UserStore userStore; 
    
    @Override
    public void init() throws ServletException{
        super.init();
        setUserStore(UserStore.getInstance());
    }
    
    void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{        
        request.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp").forward(request, response);   
    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{  
        response.sendRedirect("/activityfeed");   
    }
    
}
