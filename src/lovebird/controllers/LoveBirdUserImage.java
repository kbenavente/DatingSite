package lovebird.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lovebird.utils.Utilities;

@WebServlet("/LoveBirdUserImage")
public class LoveBirdUserImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public LoveBirdUserImage() {
        super();
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") == null) {
			
			response.sendRedirect("LoveBirdSignUp");
			
		} else {
			
			String imageID = request.getParameter("imageID");
			System.out.println("User Image recieved this image ID: " + imageID);
			
			if(imageID == null || imageID.isEmpty()) {
				
				response.sendRedirect("LoveBirdProfile");
				
			} else {
				
				// Retrieve profile image for the username using the userID which is the primary key
				
				int image_id = Integer.parseInt(request.getParameter("imageID"));
				String otherUserID = request.getParameter("otherUserID");
				
				 
				if(otherUserID.isEmpty()) { // This means that the person that wants their image is the logged in user
					
					int user_id = Integer.parseInt(Utilities.getUserID((String) session.getAttribute("username")));
					
					response.setHeader("Content-Disposition", "inline");
					
					String file_path = getServletContext().getRealPath("WEB-INF/files/" + Utilities.getImageName(image_id, user_id));
					System.out.println("IMAGE NAME: " + Utilities.getImageName(image_id, user_id));
					try {
					
						FileInputStream in = new FileInputStream(file_path);
						
					System.out.println();
					
						byte[] buffer = new byte[4000];
						int bytes_read;
						
						while((bytes_read = in.read(buffer)) > -1) {
							
							response.getOutputStream().write(buffer, 0, bytes_read);
							
						}
						
						in.close();
					
					} catch(FileNotFoundException ex) {
						
						// File was not found (Protects against manual parameter inputs.

					}
					
				} else {
					
					
					int other_user_id = Integer.parseInt(otherUserID);
					
					response.setHeader("Content-Disposition", "inline");
					
					String file_path = getServletContext().getRealPath("WEB-INF/files/" + Utilities.getImageName(image_id, other_user_id));
					
					try {
					
						FileInputStream in = new FileInputStream(file_path);
						
					
					
						byte[] buffer = new byte[4000];
						int bytes_read;
						
						while((bytes_read = in.read(buffer)) > -1) {
							
							response.getOutputStream().write(buffer, 0, bytes_read);
							
						}
						
						in.close();
					
					} catch(FileNotFoundException ex) {
						
						// File was not found (Protects against manual parameter inputs.
						
					}
						
					
				}
				
			}
			
		}
	
	}


}
