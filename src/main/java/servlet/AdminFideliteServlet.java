package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import dao.Factory;
import model.Client;
import model.User;
/**
 * Servlet implementation class AdminFideliteServlet
 */


@WebServlet("/admin/fidelite")
public class AdminFideliteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFideliteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try (Connection conn = Factory.getConnection()) {

            String sql = "SELECT c.id_client, c.nom, c.prenom, " +
                         "COALESCE(f.points, 0) AS points, " +
                         "COALESCE(f.reduction_applicable, 0.00) AS reduction " +
                         "FROM client c LEFT JOIN fidelite f ON c.id_client = f.id_client";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Client> liste = new ArrayList<>();

            while (rs.next()) {
                Client c = new Client();
                c.setId(rs.getInt("id_client"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setPointsFidelite(rs.getInt("points"));
                c.setReduction(rs.getDouble("reduction"));

                liste.add(c);
            }

            request.setAttribute("listeFidelite", liste);
            request.getRequestDispatcher("/admin/fidelite.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur serveur");
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
