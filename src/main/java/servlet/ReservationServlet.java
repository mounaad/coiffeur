package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Factory;
import model.User;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
                
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        int id_client = user.getId();
        
        // Récupération des paramètres
        String serviceIdParam = request.getParameter("id_service");
        String date = request.getParameter("date");
        String heure = request.getParameter("heure");
        String coiffeurIdParam = request.getParameter("id_coiffeur");

        
        // Validation
        if (serviceIdParam == null || date == null || heure == null || coiffeurIdParam == null) {
            request.setAttribute("error", "Tous les champs sont obligatoires");
            request.getRequestDispatcher("/reservation.jsp").forward(request, response);
            return;
        }
        
        try {
            int id_service = Integer.parseInt(serviceIdParam);
            int id_coiffeur = Integer.parseInt(coiffeurIdParam);
            
            
            Connection conn = Factory.getConnection();
            
            String sql = "INSERT INTO rendezvous (id_client, id_coiffeur, id_service, date_rdv, heure_rdv, statut) " +
                         "VALUES (?, ?, ?, ?, ?, 'en_attente')";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id_client);
            ps.setInt(2, id_coiffeur);
            ps.setInt(3, id_service);
            ps.setString(4, date);
            ps.setString(5, heure);
            
            int result = ps.executeUpdate();
            ps.close();
            
            if (result > 0) {
                request.setAttribute("message", "Votre rendez-vous a été réservé avec succès !");
                
            } else {
                request.setAttribute("error", "Erreur lors de la réservation");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Format de données invalide");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la réservation: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur système");
        }
        
        
        response.sendRedirect(request.getContextPath() + "/client/dashboard");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/reservation.jsp");
    }
}