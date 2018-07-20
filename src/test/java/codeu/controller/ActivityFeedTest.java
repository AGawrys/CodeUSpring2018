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
    private MessageStore mockMessageStore;
    private ConversationStore mockConversationStore;
    private UserStore mockUserStore;

    @Before
    public void setup() {
        activityFeedServlet = new ActivityFeedServlet();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        mockMessageStore = Mockito.mock(MessageStore.class);
        mockConversationStore = Mockito.mock(ConversationStore.class);
        mockUserStore = Mockito.mock(UserStore.class);
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
    public void testGetMessageActivities(){
        ActivityStore activityStore = ActivityStore.getInstance();
        List<Message> message = new ArrayList<Message>();
        List<User> fakeUsers = new ArrayList<User>();
        List<Conversation> fakeConversations = new ArrayList<Conversation>();
        Instant older = Instant.now();
        Instant newest = older.plusSeconds(3);
        message.add(new Message(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Test1", older));
        message.add(new Message(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Test2", newest));
        message.add(new Message(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), "Test2", older.plusSeconds(1)));
        Mockito.when(mockMessageStore.getAll()).thenReturn(message);
        Mockito.when(mockUserStore.getAll()).thenReturn(fakeUsers);
        Mockito.when(mockConversationStore.getAllConversations()).thenReturn(fakeConversations);
        activityStore.setMessageStore(mockMessageStore);
        activityStore.setConversationStore(mockConversationStore);
        activityStore.setUserStore(mockUserStore);
        
        List<Activity> act = activityStore.getAllActivities();
        
        Mockito.verify(mockMessageStore).getAll();
        Assert.assertEquals(3, act.size());
        Assert.assertEquals(newest, act.get(0).creationTime);
        Assert.assertEquals(older, act.get(2).creationTime);
    }
    
    @Test
    public void testGetConversationActivities(){
        ActivityStore activityStore = ActivityStore.getInstance();
        List<User> fakeUsers = new ArrayList<User>();
        List<Message> fakeMessages = new ArrayList<Message>();
        List<Conversation> conversations = new ArrayList<Conversation>();
        Instant oldest = Instant.now();
        conversations.add(new Conversation(UUID.randomUUID(), UUID.randomUUID(), "Test1",  oldest));
        conversations.add(new Conversation(UUID.randomUUID(), UUID.randomUUID(), "Test2",  oldest.plusSeconds(1)));
        conversations.add(new Conversation(UUID.randomUUID(), UUID.randomUUID(), "Test3",  oldest.plusSeconds(2)));
        Mockito.when(mockConversationStore.getAllConversations()).thenReturn(conversations);
        Mockito.when(mockUserStore.getAll()).thenReturn(fakeUsers);
        Mockito.when(mockMessageStore.getAll()).thenReturn(fakeMessages);
        activityStore.setMessageStore(mockMessageStore);
        activityStore.setConversationStore(mockConversationStore);
        activityStore.setUserStore(mockUserStore);
           
        List<Activity> act = activityStore.getAllActivities();
        
        Mockito.verify(mockConversationStore).getAllConversations();
        Assert.assertEquals(3, act.size());
        Assert.assertEquals(oldest, act.get(2).creationTime); //the oldest should be last
    }
    
    @Test
    public void testGetUserActivities(){
        ActivityStore activityStore = ActivityStore.getInstance();
        List<User> users = new ArrayList<User>();
        List<Message> fakeMessages = new ArrayList<Message>();
        List<Conversation> fakeConversations = new ArrayList<Conversation>();
        Instant oldest = Instant.now();
        Instant younger = oldest.plusSeconds(1);
        users.add(new User(UUID.randomUUID(), "Test4", "fakePassword", oldest, true));
        users.add(new User(UUID.randomUUID(), "Test5", "fakePassword", younger, true));        
        Mockito.when(mockUserStore.getAll()).thenReturn(users);
        Mockito.when(mockMessageStore.getAll()).thenReturn(fakeMessages);
        Mockito.when(mockConversationStore.getAllConversations()).thenReturn(fakeConversations); 
        activityStore.setMessageStore(mockMessageStore);
        activityStore.setConversationStore(mockConversationStore);
        activityStore.setUserStore(mockUserStore);
    
        List<Activity> act = activityStore.getAllActivities();
        
        Mockito.verify(mockUserStore).getAll();
        Assert.assertEquals(2, act.size());
        Assert.assertEquals(oldest, act.get(1).creationTime); //the oldest should be last
    }
}
