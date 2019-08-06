package lovebird.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoveBirdEditProfile")
public class LoveBirdEditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdEditProfile() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") == null) {
			
			response.sendRedirect("LoveBirdSignUp");
			
		} else {
			
			request.getRequestDispatcher("WEB-INF/LoveBirdEditProfile.jsp").forward(request, response);
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
