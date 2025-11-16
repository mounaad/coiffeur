package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ClientDao;
import dao.ClientDaoImp;
import model.Client;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ClientDaoImp clientDao = new ClientDaoImp();

    
    public RegisterServlet() {
        super();
        
    }
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String motDePasse = request.getParameter("mot_de_passe");
        String confirm = request.getParameter("confirm_password");

        if (!motDePasse.equals(confirm)) {
            request.setAttribute("error", "Les mots de passe ne correspondent pas !");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (clientDao.emailExiste(email)) {
            request.setAttribute("error", "Un compte avec cet email existe déjà !");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        Client client = new Client(prenom, nom, email, telephone, motDePasse);
        
        boolean inserted = clientDao.ajouterClient(client);


        if (inserted) {
        	HttpSession session = request.getSession();
        	session.setAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
        	response.sendRedirect("login.jsp");
        } else {
            request.setAttribute("error", "Erreur lors de la création du compte !");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }

        
    }

}
