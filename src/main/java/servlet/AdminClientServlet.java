package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.ClientDao;
import dao.ClientDaoImp;
import dao.Factory;
import model.Client;

/**
 * Servlet implementation class AdminClientServlet
 */
@WebServlet("/admin/clients")
public class AdminClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminClientServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

        try (Connection conn = Factory.getConnection()) {

            ClientDao dao = new ClientDaoImp(conn);

            if ("add".equals(action)) {
                request.getRequestDispatcher("/admin/ajouterClient.jsp")
                       .forward(request, response);
                return;
            }

            if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Client c = dao.getClienttById(id);

                request.setAttribute("client", c);
                request.getRequestDispatcher("/admin/modifierClient.jsp")
                       .forward(request, response);
                return;
            }

            
            request.setAttribute("liste", dao.getAll());
            request.getRequestDispatcher("/admin/clients.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");

        try (Connection conn = Factory.getConnection()) {

            ClientDao dao = new ClientDaoImp(conn);

            if ("add".equals(action)) {

                Client c = new Client();
                c.setNom(request.getParameter("nom"));
                c.setPrenom(request.getParameter("prenom"));
                c.setEmail(request.getParameter("email"));
                c.setTelephone(request.getParameter("telephone"));
                c.setMotDePasse(request.getParameter("mot_de_passe"));

                dao.add(c);
            }

            if ("update".equals(action)) {

                Client c = new Client();
                c.setId(Integer.parseInt(request.getParameter("id")));
                c.setNom(request.getParameter("nom"));
                c.setPrenom(request.getParameter("prenom"));
                c.setEmail(request.getParameter("email"));
                c.setTelephone(request.getParameter("telephone"));

                dao.update(c);
            }

            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);
            }

            response.sendRedirect(request.getContextPath() + "/admin/clients");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
	}

}
