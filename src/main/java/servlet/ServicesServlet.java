package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Factory;
import dao.ServiceDaoImp;
import model.Service;

@WebServlet("/Services")
public class ServicesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServicesServlet() {
        super();
    }
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Connection conn = null;
        try {
            
            conn = Factory.getConnection();
            
            ServiceDaoImp dao = new ServiceDaoImp(conn);
            
            List<Service> services = dao.getAllServices();
            
            request.setAttribute("services", services);
            request.getRequestDispatcher("/services.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("errorType", e.getClass().getName());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connexion fermée");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}