package servlet;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoiffeurFideliteServletTest {

    private CoiffeurFideliteServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setup() {
        servlet = new CoiffeurFideliteServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    void redirectionSiUserNull() throws Exception {
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendRedirect(anyString());
    }

    @Test
    void redirectionSiUserNonCoiffeur() throws Exception {
        User user = new User();
        user.setRole("admin");

        when(session.getAttribute("user")).thenReturn(user);

        servlet.doGet(request, response);

        verify(response).sendRedirect(anyString());
    }
}