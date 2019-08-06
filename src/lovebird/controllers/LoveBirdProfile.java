package lovebird.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lovebird.utils.*;

@WebServlet("/LoveBirdProfile")
public class LoveBirdProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdProfile() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") == null) { // Session exists and is valid
			
			response.sendRedirect("LoveBirdSignUp"); // Redirect user to the Sign Up page
			
			
		} else if(request.getParameter("otherUserID") == null) { // The current user's profile page
			
			session.setAttribute("otherUserID", "");
			
		
			
			ArrayList<String> imageIDs = Utilities.getImagesID(session, Integer.parseInt(Utilities.getUserID((String) session.getAttribute("username"))));
			System.out.println(imageIDs.size());
			session.setAttribute("imageIDs", imageIDs);
			
			Utilities.saveUserInfoToSession(session, (String) session.getAttribute("username")); 
			request.getRequestDispatcher("WEB-INF/LoveBirdProfile.jsp").forward(request, response); // Forward request and response and display specific JSP
			
		} else {
			
			Utilities.saveOtherUserInfoToApplicationScope(session, Integer.parseInt(request.getParameter("otherUserID"))); // Session scope information has been populated with the other user's information
			
			session.setAttribute("otherUserID", request.getParameter("otherUserID")); 
			
			ArrayList<String> imageIDs = Utilities.getImagesID(session, Integer.parseInt(request.getParameter("otherUserID")));
			
			for(int i = 0; i < imageIDs.size(); i++)
				System.out.println("VALUE IN LIST: " + imageIDs.get(i));
			
			session.setAttribute("imageIDs", imageIDs);
			
			
			request.getRequestDispatcher("WEB-INF/LoveBirdProfile.jsp").forward(request, response);
			
		}
		
	}

}
