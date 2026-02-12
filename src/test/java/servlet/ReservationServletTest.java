
package servlet;

import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import dao.Factory;
import model.User;

class ReservationServletTest {

    private ReservationServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setup() {
        servlet = new ReservationServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
    }

    @Test
    void doPost_shouldRedirectToLogin_whenNoSession() throws Exception {
        when(request.getSession(false)).thenReturn(null);
        when(request.getContextPath()).thenReturn("");

        servlet.doPost(request, response);

        verify(response).sendRedirect("/login.jsp");
    }

    @Test
    void doPost_shouldForward_whenMissingParameters() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(new User());

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("/reservation.jsp")).thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_shouldInsertReservationAndRedirect() throws Exception {
        User user = new User();
        user.setId(1);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        when(request.getParameter("id_service")).thenReturn("2");
        when(request.getParameter("id_coiffeur")).thenReturn("3");
        when(request.getParameter("date")).thenReturn("2026-02-15");
        when(request.getParameter("heure")).thenReturn("10:00");
        when(request.getContextPath()).thenReturn("");

        Connection conn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(conn.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        try (MockedStatic<Factory> factoryMock = Mockito.mockStatic(Factory.class)) {
            factoryMock.when(Factory::getConnection).thenReturn(conn);

            servlet.doPost(request, response);
        }

        verify(ps).executeUpdate();
        verify(response).sendRedirect("/client/dashboard");
    }

    @Test
    void doPost_shouldHandleNumberFormatException() throws Exception {
        User user = new User();
        user.setId(1);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        when(request.getParameter("id_service")).thenReturn("abc"); // ❌
        when(request.getParameter("id_coiffeur")).thenReturn("1");
        when(request.getParameter("date")).thenReturn("2026-02-15");
        when(request.getParameter("heure")).thenReturn("10:00");
        when(request.getContextPath()).thenReturn("");

        servlet.doPost(request, response);

        verify(response).sendRedirect("/client/dashboard");
    }
}
