package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.RendezVous;
import model.User;
import model.Coiffeur;
import dao.CoiffeurDaoImpl;
import dao.RendezVousDao;
import dao.RendezVousDaoImpl;
import dao.Factory;
import dao.FideliteDao;
import dao.FideliteDaoImpl;

import java.sql.Connection;
import java.util.List;

/**
 * Servlet implementation class CoiffeurDashboardServlet
 */
@WebServlet("/coiffeur/dashboard")
public class CoiffeurDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CoiffeurDashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 HttpSession session = request.getSession();
	        User user = (User) session.getAttribute("user");

	        if (user == null || !"coiffeur".equals(user.getRole())) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return;
	        }

	        int coiffeurId = user.getId();

	        try (Connection conn = Factory.getConnection()) {
	            RendezVousDao rdvDao = new RendezVousDaoImpl(conn);
	            CoiffeurDaoImpl coiffDao = new CoiffeurDaoImpl(conn);
 
	            List<RendezVous> rdvs = rdvDao.getRendezVousByCoiffeur(coiffeurId);
	            Coiffeur coiffeur = coiffDao.getCoiffeurById(coiffeurId);

	            request.setAttribute("rdvs", rdvs);
	            request.setAttribute("coiffeur", coiffeur);

	            request.getRequestDispatcher("/coiffeur/dashboard.jsp").forward(request, response);

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendError(500, "Erreur serveur");
	        }

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.getRole().equals("coiffeur")) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int idRdv = Integer.parseInt(request.getParameter("idRdv"));
        String action = request.getParameter("action");

        try (Connection conn = Factory.getConnection()) {
            RendezVousDao rdvDao = new RendezVousDaoImpl(conn);

            switch(action) {
                case "confirme":
                    rdvDao.confirmerRdv(idRdv);
                    
                    FideliteDao fDao = new FideliteDaoImpl(conn);
                    int idClient = Integer.parseInt(request.getParameter("idClient"));
                    fDao.ajouterPoints(idClient, 10);
                    
                    break;
                case "annule":
                    rdvDao.annulerRdv(idRdv);
                    break;
                default:
                    response.sendError(400, "Action invalide");
                    return;
            }
            
            // Redirige vers le dashboard pour recharger la liste
            response.sendRedirect(request.getContextPath() + "/coiffeur/dashboard");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur serveur");
        }
    }
	

}
