/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeu.controller;

import codeu.model.data.Activity;
import codeu.model.data.Message;
import codeu.model.store.basic.ActivityStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Agnieszka
 */
public class ActivityFeedServlet extends HttpServlet {
     /** Store class that gives access to Activities. */
     private ActivityStore activityStore;

    @Override
    public void init() throws ServletException{
        super.init();
        setActivityStore(ActivityStore.getInstance());
    }
    /**
    * Sets the ActivityStore used by this servlet. This function provides a common setup method
    * for use by the test framework or the servlet's init() function.
    */
   void setActivityStore(ActivityStore activityStore) {
     this.activityStore = activityStore;
   }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{        
        List<Activity> activities = activityStore.getAllActivities();
        request.setAttribute("activities", activities);
        request.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp").forward(request, response);   
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.sendRedirect("/activityfeed");
    }

}
