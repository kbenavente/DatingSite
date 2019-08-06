package lovebird.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoveBirdLogout")
public class LoveBirdLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdLogout() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") != null) {
			
			session.invalidate();
			response.sendRedirect("LoveBirdSignIn");
			
		} else {
			
			response.sendRedirect("LoveBirdSignUp");
			
		}
		
	}

}
