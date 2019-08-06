package lovebird.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import lovebird.utils.Utilities;

@WebServlet("/LoveBirdUploadImage")
public class LoveBirdUploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdUploadImage() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") !=  null) {
			
			request.getRequestDispatcher("WEB-INF/LoveBirdUploadImage.jsp").forward(request, response);
			
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userID = Utilities.getUserID((String) request.getSession().getAttribute("username"));
		int user_id = Integer.parseInt(userID);
		
		String maxImageError = "";
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		String folder_path = getServletContext().getRealPath("WEB-INF/files");
		
		// Parse the request
		try {
			
			List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
			
			for (int i = 0; i < items.size(); i++) {
				
				//If not a file
				if (items.get(i).isFormField()) {

					
					
				} else {
					
					int next_image_id = Utilities.getNextImageID(user_id);
					
					// Check for max image upload
					
					if(next_image_id == 0) {
						
						maxImageError = "You can only upload 6 images";
						break;
						
					}
					
					//Place each file in chosen folder
					String file_name = items.get(i).getName();
					int dot_index = file_name.lastIndexOf('.');
					file_name =  String.format("%s-%s%s", user_id, next_image_id, file_name.substring(dot_index));
					System.out.println(file_name);
					File file = new File(folder_path, file_name);
					//Write uploaded file content into this file
					items.get(i).write(file);
					
					Utilities.upateImageRecord(userID, next_image_id, file_name);
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String referer = request.getHeader("Referer");
//		response.sendRedirect(referer);
		
		if(maxImageError.isEmpty()) {
			
			HttpSession session = request.getSession();
			
			session.setAttribute("maxImageError", maxImageError);
			response.sendRedirect("LoveBirdProfile");
			
		} else {
		
		response.sendRedirect("LoveBirdProfile");
		
		}
		
	}

}
