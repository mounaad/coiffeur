package servlet;

import static org.mockito.Mockito.*;

import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dao.Factory;
import model.User;

class AdminDashboardServletTest {

    @Test
    void doGet_shouldRedirect_ifUserNotAdmin() throws Exception {
        AdminDashboardServlet servlet = new AdminDashboardServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendRedirect(contains("/login.jsp"));
    }

    @Test
    void doGet_shouldForwardDashboard_ifAdmin() throws Exception {
        AdminDashboardServlet servlet = new AdminDashboardServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        Connection conn = mock(Connection.class);

        User admin = new User();
        admin.setRole("admin");

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(admin);
        when(request.getRequestDispatcher("/admin/dashboard.jsp"))
                .thenReturn(dispatcher);

        try (MockedStatic<Factory> mocked = mockStatic(Factory.class)) {
            mocked.when(Factory::getConnection).thenReturn(conn);

            servlet.doGet(request, response);
        }

        verify(request).setAttribute(eq("totalClients"), anyInt());
        verify(request).setAttribute(eq("totalCoiffeurs"), anyInt());
        verify(dispatcher).forward(request, response);
    }
}