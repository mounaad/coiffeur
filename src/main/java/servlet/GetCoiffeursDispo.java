package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CoiffeurDaoImpl;
import dao.Factory;
import model.Coiffeur;
import com.google.gson.Gson;

@WebServlet("/GetCoiffeursDispo")
public class GetCoiffeursDispo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GetCoiffeursDispo() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Configuration JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Récupération des paramètres
            String idServiceParam = request.getParameter("id_service");
            String date = request.getParameter("date");
            String heure = request.getParameter("heure");
         
            // Validation
            if (idServiceParam == null || date == null || heure == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("[]");
                return;
            }
            
            int id_service = Integer.parseInt(idServiceParam);
            
            // Connexion BDD
            Connection conn = Factory.getConnection();
            
            if (conn == null) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("[]");
                return;
            }
            // Récupération des coiffeurs
            CoiffeurDaoImpl dao = new CoiffeurDaoImpl(conn);
            List<Coiffeur> list = dao.getCoiffeursDisponibles(id_service, date, heure);
            
            // Afficher chaque coiffeur
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Coiffeur c = list.get(i);
                    System.out.println("   " + (i+1) + ". ID=" + c.getIdCoiffeur() + 
                                     " | Nom=" + c.getNom());
                }
            } else {
                System.out.println("⚠️ AUCUN COIFFEUR TROUVÉ!");
            }
            
            // Conversion JSON
            Gson gson = new Gson();
            String json = gson.toJson(list);
           
            response.getWriter().write(json);
            
        } catch (NumberFormatException e) {
            System.out.println("❌ ERREUR: Format nombre invalide");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("[]");
        } catch (Exception e) {
            System.out.println("   Message: " + e.getMessage());
            System.out.println("   Type: " + e.getClass().getName());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("[]");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}