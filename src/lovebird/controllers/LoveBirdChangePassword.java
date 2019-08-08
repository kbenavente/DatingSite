package lovebird.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lovebird.utils.Utilities;

@WebServlet("/LoveBirdChangePassword")
public class LoveBirdChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdChangePassword() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") == null) {
			
			response.sendRedirect("LoveBirdSignUp");
			
		} else {
			
			request.getRequestDispatcher("WEB-INF/LoveBirdChangePassword.jsp").forward(request, response);
			
		}
	 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("username");
		
		String oldPassword = request.getParameter("oldPassword");
		String newPassword1 = request.getParameter("newPassword1");
		String newPassword2 = request.getParameter("newPassword2");
		
		ArrayList<String> changePasswordError = Utilities.checkEmptyInputs(oldPassword, "Old Password", newPassword1, "New Password 1", newPassword2, "New Password 2");
		
		String newPasswordError = Utilities.checkPasswordMatch(newPassword1, newPassword2);
		
		String oldPasswordError = Utilities.validateUserPassword(username, oldPassword);
		
		if(changePasswordError.isEmpty() && newPasswordError.isEmpty() && oldPasswordError.isEmpty()) {
			
			session.setAttribute("newPasswordError", "");
			session.setAttribute("changePasswordError", "");
			session.setAttribute("oldPasswordError", "");
			
			Utilities.changePassword(username, newPassword1);
			
			response.sendRedirect("LoveBirdProfile");
			
		} else {
			
			session.setAttribute("newPasswordError", newPasswordError);
			session.setAttribute("changePasswordError", changePasswordError);
			session.setAttribute("oldPasswordError", oldPasswordError);
			
			response.sendRedirect("LoveBirdChangePassword");
			
		}
		
	}

}
