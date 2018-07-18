package codeu.controller;

import codeu.model.data.User;
import java.util.UUID;
import java.time.Instant;

import codeu.model.store.basic.UserStore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServletTest {

    private ProfileServlet profileServlet;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private RequestDispatcher mockRequestDispatcher;

    @Before
    public void setup() {
        profileServlet = new ProfileServlet();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp")).thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGetValidUser() throws IOException, ServletException {
        User userJohn = new User(
                        UUID.randomUUID(), "john", "johnpassword", Instant.now(), false);
        UserStore mockUserStore = Mockito.mock(UserStore.class);

        Mockito.when(mockRequest.getRequestURI()).thenReturn("/users/john");
        Mockito.when(mockUserStore.getUser("john")).thenReturn(userJohn);

        profileServlet.setUserStore(mockUserStore);
        profileServlet.doGet(mockRequest, mockResponse);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp")).thenReturn(mockRequestDispatcher);

    }

@Test
    public void testDoGetNotValidUser() throws IOException, ServletException {
        UserStore mockUserStore = Mockito.mock(UserStore.class);

        Mockito.when(mockRequest.getRequestURI()).thenReturn("/users/john");
        Mockito.when(mockUserStore.getUser("john")).thenReturn(null);

        profileServlet.setUserStore(mockUserStore);
        profileServlet.doGet(mockRequest, mockResponse);
        Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profile.jsp")).thenReturn(mockRequestDispatcher);
    }

}
