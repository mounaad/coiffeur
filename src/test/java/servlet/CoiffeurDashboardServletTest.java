package servlet;

import static org.mockito.Mockito.*;

import javax.servlet.http.*;

import org.junit.jupiter.api.Test;

class CoiffeurDashboardServletTest {
    @Test
    void testDoGetSansUtilisateur() throws Exception {
        CoiffeurDashboardServlet servlet = new CoiffeurDashboardServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendRedirect(contains("login"));
    }
    @Test
void testDoGetRedirectIfNotLoggedIn() throws Exception {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse resp = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);

    when(req.getSession()).thenReturn(session);
    when(session.getAttribute("user")).thenReturn(null);

    new CoiffeurDashboardServlet().doGet(req, resp);

    verify(resp).sendRedirect(anyString());
}

}