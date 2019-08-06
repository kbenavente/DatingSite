package lovebird.controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import lovebird.accounts.*;
import lovebird.utils.*;

@WebServlet("/LoveBirdSignUp")
public class LoveBirdSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdSignUp() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") != null) { // Session exists and is valid
			
			response.sendRedirect("MyProfile"); // Redirect user to their profile dash board
			
		} else {
			
			request.getRequestDispatcher("WEB-INF/LoveBirdSignUp.jsp").forward(request, response); // Forward request and response and display specific JSP
			
		}
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		// Clear all error messages if any
		
		getServletContext().setAttribute("errorsForInputsSignUp", "");
		getServletContext().setAttribute("errorsForPasswordsSignUp", "");
		getServletContext().setAttribute("isUsernameTaken", "");
		
		// Get input values
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String age = request.getParameter("age");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String gender = request.getParameter("gender");
		String bio = "I am a LoveBird user and I chose to make the first move.";
		
		// Check all forms for valid input and also if the username is already taken
		
		ArrayList<String> errorsForInputsSignUp = Utilities.checkEmptyInputs(firstName, "First Name", lastName, "Last Name", email, "email", username, "username", password1, "password", password2, "password - again", age, "age", city, "city", state, "state", gender, "gender");
		String errorsForPasswordsSignUp = Utilities.checkPasswordMatch(password1, password2);
		String isUsernameTaken = Utilities.takenUsername(username);
		System.out.println(isUsernameTaken);
		
		if(errorsForInputsSignUp.isEmpty() && errorsForPasswordsSignUp.isEmpty() && isUsernameTaken.isEmpty()) {
			
			// Create user (more DB work)
			
			Utilities.insertNewUserToDB(username, password1, firstName, lastName, email, age, city, state, gender, "I am a LoveBird user and I chose to make the first move."); // Bio default value is inputed at account creation
			
			// Create the user's session
			
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("firstName", firstName);
			session.setAttribute("lastName", lastName);
			session.setAttribute("age", age);
			session.setAttribute("city", city);
			session.setAttribute("state", state);
			session.setAttribute("gender", gender);
			session.setAttribute("bio", "I am a LoveBird user and I chose to make the first move.");
			session.setMaxInactiveInterval(20 * 60); // Session Timeout set to 20 minutes
			
			// Create default profile image
			
			File defaultProfileImage = new File(getServletContext().getRealPath("WEB-INF/files/default-profile.png"));
			
			FileUtils.copyFile(defaultProfileImage, new File(getServletContext().getRealPath("WEB-INF/files/profile_" + Utilities.getUserID(username) + ".png")));
			
			// Redirect to the specific user's profile page
			
			response.sendRedirect("LoveBirdProfile");
			
		} else { // Error found, redirect to Sign Up page with the errors to be shown
			
			getServletContext().setAttribute("errorsForInputsSignUp", errorsForInputsSignUp);
			getServletContext().setAttribute("errorsForPasswordsSignUp", errorsForPasswordsSignUp);
			getServletContext().setAttribute("usernameIsTaken", isUsernameTaken);
			
			response.sendRedirect("LoveBirdSignUp");
			
		}
		
	}

}
