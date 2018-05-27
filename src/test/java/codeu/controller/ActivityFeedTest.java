/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codeu.controller;

import codeu.model.data.Activity;
import codeu.model.data.Conversation;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.basic.ActivityStore;
import codeu.model.store.basic.ConversationStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author Agnieszka
 */
public class ActivityFeedTest {
    
    private ActivityFeedServlet activityFeedServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse; 
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setup() {
        activityFeedServlet = new ActivityFeedServlet();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/activityfeed.jsp"))
        .thenReturn(mockRequestDispatcher);
    }
    
    @Test
    public void testDoGet() throws IOException, ServletException {
        ActivityStore mockActivityStore = Mockito.mock(ActivityStore.class);
        List<Activity> act = new ArrayList<Activity>();
        Mockito.when(mockActivityStore.getAllActivities()).thenReturn(act);
        activityFeedServlet.setActivityStore(mockActivityStore);
        activityFeedServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }
    
    @Test
    public void testGetMultipleActivities(){
        ActivityStore activityStore = ActivityStore.getInstance();
        List<Message> message = new ArrayList<Message>();
        Instant older = Instant.now();
        Instant newest = older.plusSeconds(3);
        message.add(new Message(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Test1", older));
        message.add(new Message(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Test2", newest));
        MessageStore mockMessageStore = Mockito.mock(MessageStore.class);
        Mockito.when(mockMessageStore.getAll()).thenReturn(message);
        activityStore.setMessageStore(mockMessageStore);
        
        List<Conversation> conversations = new ArrayList<Conversation>();
        Instant middle = older.plusSeconds(1);
        conversations.add(new Conversation(UUID.randomUUID(), UUID.randomUUID(), "Test3",  middle));
        ConversationStore mockConversationStore = Mockito.mock(ConversationStore.class);
        Mockito.when(mockConversationStore.getAllConversations()).thenReturn(conversations);
        activityStore.setConversationStore(mockConversationStore);
        
        List<User> users = new ArrayList<User>();
        Instant middle2 = older.plusSeconds(2);
        users.add(new User(UUID.randomUUID(), "Test4", "fakePassword", middle2));
        UserStore mockUserStore = Mockito.mock(UserStore.class);
        Mockito.when(mockUserStore.getAll()).thenReturn(users);
        activityStore.setUserStore(mockUserStore);
        
        List<Activity> act = activityStore.getAllActivities();
        
        Mockito.verify(mockMessageStore).getAll();
        Assert.assertEquals(4, act.size());
        Assert.assertEquals(newest, act.get(0).creationTime);
        Assert.assertEquals(older, act.get(3).creationTime);
    }

}
