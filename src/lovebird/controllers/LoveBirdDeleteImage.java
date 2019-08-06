package lovebird.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lovebird.utils.Utilities;

@WebServlet("/LoveBirdDeleteImage")
public class LoveBirdDeleteImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdDeleteImage() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") == null) {
			
			response.sendRedirect("LoveBirdSignUp");
			
		} else {
			
			// Get the image ID
			
			int image_id = Integer.parseInt(request.getParameter("imageID"));
			
			// Get userID
			
			String userID = Utilities.getUserID((String) session.getAttribute("username"));
			int user_id = Integer.parseInt(userID);
			
			// Get file name 
			
			String fileName = Utilities.getImageName(image_id, user_id);
			
			// Path to file 
			
			String file_path = getServletContext().getRealPath("WEB-INF/files/" + fileName);
			
			// Delete the file
			
			File file = new File(file_path);
			
			if(file.delete()) { // Successful file delete
				
				Utilities.upateImageRecord(userID, image_id, null);
				
				response.sendRedirect("LoveBirdProfile");
				
			} else { // Unsuccessful file delete
				
				response.sendRedirect("LoveBirdProfile");
				
			}
			
			
			
			
		}
		
	}

}
