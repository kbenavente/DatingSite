package lovebird.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lovebird.utils.Utilities;

@WebServlet("/ProfileImage")
public class ProfileImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProfileImage() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") == null) {
			
			response.sendRedirect("LoveBirdSignUp");
			
		} else {
			
			// Retrieve profile image for the username using the userID which is the primary key
			
			response.setHeader("Content-Disposition", "inline");
			
			String file_path = getServletContext().getRealPath("WEB-INF/files/" + (String) session.getAttribute("profile_image"));
			
			FileInputStream in = new FileInputStream(file_path);
			
			byte[] buffer = new byte[4000];
			int bytes_read;
			
			while((bytes_read = in.read(buffer)) > -1) {
				
				response.getOutputStream().write(buffer, 0, bytes_read);
				
			}
			
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
