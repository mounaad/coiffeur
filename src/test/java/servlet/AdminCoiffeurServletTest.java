package servlet;

import static org.mockito.Mockito.*;

import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import dao.Factory;

class AdminCoiffeurServletTest {

    @Test
    void doGet_shouldForwardList() throws Exception {
        AdminCoiffeurServlet servlet = new AdminCoiffeurServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        Connection conn = mock(Connection.class);

        when(request.getParameter("action")).thenReturn(null);
        when(request.getRequestDispatcher("/admin/coiffeurs.jsp"))
                .thenReturn(dispatcher);

        try (MockedStatic<Factory> mocked = mockStatic(Factory.class)) {
            mocked.when(Factory::getConnection).thenReturn(conn);

            servlet.doGet(request, response);
        }

        verify(request).setAttribute(eq("liste"), any());
        verify(dispatcher).forward(request, response);
    }

    @Test
    void doPost_shouldDeleteAndRedirect() throws Exception {
        AdminCoiffeurServlet servlet = new AdminCoiffeurServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Connection conn = mock(Connection.class);

        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getContextPath()).thenReturn("");

        try (MockedStatic<Factory> mocked = mockStatic(Factory.class)) {
            mocked.when(Factory::getConnection).thenReturn(conn);

            servlet.doPost(request, response);
        }

        verify(response).sendRedirect("/admin/coiffeurs");
    }
}