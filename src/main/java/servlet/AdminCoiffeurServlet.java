package servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CoiffeurDao;
import dao.CoiffeurDaoImpl;
import dao.Factory;
import model.Coiffeur;

/**
 * Servlet implementation class AdminCoiffeurServlet
 */
@WebServlet("/admin/coiffeurs")
public class AdminCoiffeurServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminCoiffeurServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		try (Connection conn = Factory.getConnection()) {

            CoiffeurDao dao = new CoiffeurDaoImpl(conn);
            if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Coiffeur c = dao.getCoiffeurById(id);

                request.setAttribute("coiffeur", c);
                request.getRequestDispatcher("/admin/modifierCoiffeur.jsp").forward(request, response);
                return;
            }

            request.setAttribute("liste", dao.getAll());

            request.getRequestDispatcher("/admin/coiffeurs.jsp").forward(request, response);

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

	            CoiffeurDao dao = new CoiffeurDaoImpl(conn);

	            if ("add".equals(action)) {

	                Coiffeur c = new Coiffeur();
	                c.setNom(request.getParameter("nom"));
	                c.setAdresse(request.getParameter("adresse"));
	                c.setEmail(request.getParameter("email"));
	                c.setTelephone(request.getParameter("telephone"));
	                c.setMotDePasse(request.getParameter("mot_de_passe"));

	                dao.add(c);
	            }

	            if ("update".equals(action)) {
	                Coiffeur c = new Coiffeur();

	                c.setIdCoiffeur(Integer.parseInt(request.getParameter("id")));
	                c.setNom(request.getParameter("nom"));
	                c.setAdresse(request.getParameter("adresse"));
	                c.setEmail(request.getParameter("email"));
	                c.setTelephone(request.getParameter("telephone"));

	                dao.update(c);
	            }

	            if ("delete".equals(action)) {
	                int id = Integer.parseInt(request.getParameter("id"));
	                dao.delete(id);
	            }

	            response.sendRedirect(request.getContextPath() + "/admin/coiffeurs");

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendError(500);
	        }
	}

}
