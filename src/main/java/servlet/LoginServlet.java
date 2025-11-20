 package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AuthDao;
import dao.ClientDaoImp;
import dao.Factory;
import model.Client;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 String usernameOrEmail = request.getParameter("username");
	     String password = request.getParameter("password");

	     try {  
	            AuthDao authDao = new AuthDao(Factory.getInstance());
	            User user = authDao.login(usernameOrEmail, password);
	            if(user != null) { 
	                request.getSession().setAttribute("user", user);

	                // Redirection selon rôle
	                switch(user.getRole()) {
	                    case "admin":
	                        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
	                        break;  
	                    case "coiffeur":
	                        response.sendRedirect(request.getContextPath() + "/coiffeur/dashboard");
	                        break;
	                    case "client":

	                        response.sendRedirect(request.getContextPath() + "/client/dashboard");

	                        break;
	                }
	            } else {
	                request.setAttribute("error", "Identifiants invalides");
	                request.getRequestDispatcher("login.jsp").forward(request, response);
	            }
	        } catch(SQLException e) {
	            e.printStackTrace();
	        }
	    }
	

}
