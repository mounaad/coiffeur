package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.ClientDao;
import dao.ClientDaoImp;
import dao.CoiffeurDao;
import dao.CoiffeurDaoImpl;
import dao.Factory;
import model.User;

/**
 * Servlet implementation class AdminDashboardServlet
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDashboardServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

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

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace(); // ou mieux : logger
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
