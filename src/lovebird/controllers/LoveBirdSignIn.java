package lovebird.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lovebird.utils.Utilities;

@WebServlet("/LoveBirdSignIn")
public class LoveBirdSignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdSignIn() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") != null) { // Session exists and is valid
			
			response.sendRedirect("LoveBirdProfile"); // Redirect user to their profile dash board
			
		} else {
			
			request.getRequestDispatcher("WEB-INF/LoveBirdSignIn.jsp").forward(request, response); // Forward request and response and display specific JSP
			
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Clear Errors
		
		getServletContext().setAttribute("errorsForInputsSignIn", "");
		getServletContext().setAttribute("validUserCredentials", "");
		
		
		// Get request parameters
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		// Check all forms for valid input and also if the username is already taken
		
		ArrayList<String> errorsForInputsSignIn = Utilities.checkEmptyInputs(username, "username", password, "password");
		String validUserCredentials = Utilities.validUserCredentials(username, password);
		
		if(errorsForInputsSignIn.isEmpty() && validUserCredentials.isEmpty()) {
			
			// Create the user's session
			
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			Utilities.saveUserInfoToSession(session, username);
			session.setMaxInactiveInterval(20 * 60); // Session Timeout set to 20 minutes
			
			// Redirect to the specific user's profile page
			
			response.sendRedirect("LoveBirdProfile");
			
		} else { // Error found, redirect to Sign In page with the errors to be shown
			
			if(!errorsForInputsSignIn.isEmpty()) {
				
				getServletContext().setAttribute("errorsForInputsSignIn", errorsForInputsSignIn);
				
			} else {
			
				getServletContext().setAttribute("errorsForInputsSignIn", errorsForInputsSignIn);
				getServletContext().setAttribute("validUserCredentials", validUserCredentials);
			
			}
			
			response.sendRedirect("LoveBirdSignIn");
			
		}
		
	}

}
