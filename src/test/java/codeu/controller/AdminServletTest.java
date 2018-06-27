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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;

public class AdminServletTest {

  private AdminServlet adminServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private HttpSession mockSession;
  private UserStore mockUserStore;
  private ConversationStore mockConversationStore;
  private MessageStore mockMessageStore;


  @Before
  public void setup() {
    adminServlet = new AdminServlet();

    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);

    mockConversationStore = Mockito.mock(ConversationStore.class);
    adminServlet.setConversationStore(mockConversationStore);

    mockMessageStore = Mockito.mock(MessageStore.class);
    adminServlet.setMessageStore(mockMessageStore);

    mockUserStore = Mockito.mock(UserStore.class);
    adminServlet.setUserStore(mockUserStore);

    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/admin.jsp"))
        .thenReturn(mockRequestDispatcher);
  }

  @Test
   public void testDoGet_CantAccess() throws IOException, ServletException {
     Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

     adminServlet.doGet(mockRequest, mockResponse);

     Mockito.verify(mockRequest).setAttribute("isRegistered", false);
     Mockito.verify(mockRequest).setAttribute("isAdmin", false);
     Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
   }

   @Test
   public void testDoGet_NonAdminUserNoAccess() throws IOException, ServletException {
     Mockito.when(mockSession.getAttribute("user")).thenReturn("testUsername");

     adminServlet.doGet(mockRequest, mockResponse);

     Mockito.verify(mockRequest).setAttribute("isRegistered", true);
     Mockito.verify(mockRequest).setAttribute("isAdmin", false);
     Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
   }

   @Test
   public void testDoGet_AdminUserCanAccess() throws IOException, ServletException {
     Mockito.when(mockSession.getAttribute("user")).thenReturn("mayfnessAdmin");

     Mockito.when(mockUserStore.getUserCount()).thenReturn(0);
     Mockito.when(mockConversationStore.getConversationCount()).thenReturn(0);
     Mockito.when(mockMessageStore.getMessagesCount()).thenReturn(0);

     adminServlet.doGet(mockRequest, mockResponse);

     Mockito.verify(mockRequest).setAttribute("isRegistered", true);
     Mockito.verify(mockRequest).setAttribute("isAdmin", true);
     Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
   }

   @Test
   public void testDoGet_NoData() throws IOException, ServletException {
     Mockito.when(mockSession.getAttribute("user")).thenReturn("mayfnessAdmin");

     Mockito.when(mockUserStore.getUserCount()).thenReturn(0);
     Mockito.when(mockConversationStore.getConversationCount()).thenReturn(0);
     Mockito.when(mockMessageStore.getMessagesCount()).thenReturn(0);

     adminServlet.doGet(mockRequest, mockResponse);

     Mockito.verify(mockRequest).setAttribute("totalUsers", 0);
     Mockito.verify(mockRequest).setAttribute("totalConvos", 0);
     Mockito.verify(mockRequest).setAttribute("totalMessages", 0);
     Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
   }
 }
