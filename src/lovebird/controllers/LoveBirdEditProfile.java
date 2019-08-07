package lovebird.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lovebird.utils.Utilities;

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
		
		String bio = request.getParameter("bio");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String preference = request.getParameter("preference");
		
		String fieldsCannotBeEmptyError = "";
		
		if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || preference.isEmpty()) {
			
			fieldsCannotBeEmptyError = "Note: First Name, Last Name, Email,  and Gender Preference cannot be negative";
			getServletContext().setAttribute("fieldsCannotBeEmptyError", fieldsCannotBeEmptyError);
			response.sendRedirect("LoveBirdEditProfile");
			
		} else {
			
			HttpSession session = request.getSession();
			
			Utilities.updateUserProfile((String)session.getAttribute("username"), bio, firstName, lastName, email, preference);
			Utilities.saveUserInfoToSession(session, (String)session.getAttribute("username"));
			response.sendRedirect("LoveBirdProfile");
			
		}
		
	}

}
