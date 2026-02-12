package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.ClientDao;
import dao.ClientDaoImp;
import dao.CoiffeurDao;
import dao.CoiffeurDaoImpl;
import dao.Factory;
import model.User;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(AdminDashboardServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try (Connection conn = Factory.getConnection()) {

            ClientDao clientDao = new ClientDaoImp(conn);
            CoiffeurDao coiffeurDao = new CoiffeurDaoImpl(conn);

            int totalClients = clientDao.countClients();
            int totalCoiffeurs = coiffeurDao.countCoiffeurs();

            request.setAttribute("totalClients", totalClients);
            request.setAttribute("totalCoiffeurs", totalCoiffeurs);

            request.getRequestDispatcher("/admin/dashboard.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            logger.error("Database error while loading admin dashboard", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
