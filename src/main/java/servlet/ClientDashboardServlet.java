package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClientDaoImp;
import dao.Factory;
import dao.FideliteDao;
import dao.FideliteDaoImpl;
import dao.RendezVousDao;
import dao.RendezVousDaoImpl;
import model.Client;
import model.RendezVous;
import model.User;

/**
 * Servlet implementation class ClientDashboardServlet
 */
@WebServlet("/client/dashboard")
public class ClientDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClientDashboardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"client".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        int idClient = user.getId();
        
        
        try (Connection conn = Factory.getConnection()) { // connexion via Factory
            RendezVousDao rdvDao = new RendezVousDaoImpl(conn);
            ClientDaoImp clientDao = new ClientDaoImp();
            FideliteDao fideliteDao = new FideliteDaoImpl(conn);

            List<RendezVous> rdvs = rdvDao.getRendezVousByClient(idClient);
            Client client = clientDao.getClientById(idClient);

            request.setAttribute("rdvs", rdvs);
            request.setAttribute("client", client);
            
         //  RÉCUPÉRER LES POINTS DE FIDÉLITÉ
            int pointsFidelite = fideliteDao.getPoints(idClient);
            boolean hasReduction = fideliteDao.hasReduction(idClient);
            
            // Calculer le pourcentage pour la barre de progression
            int pourcentagePoints = Math.min((pointsFidelite % 100) * 100 / 100, 100);
            int reductionsDisponibles = pointsFidelite / 100;
            
            // Passer les données à la JSP
            request.setAttribute("rdvs", rdvs);
            request.setAttribute("client", client);
            request.setAttribute("pointsFidelite", pointsFidelite);
            request.setAttribute("hasReduction", hasReduction);
            request.setAttribute("pourcentagePoints", pourcentagePoints);
            request.setAttribute("reductionsDisponibles", reductionsDisponibles);
            
            request.getRequestDispatcher("/client/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur serveur: " + e.getMessage());
        }

            

       
        }

           
    
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
