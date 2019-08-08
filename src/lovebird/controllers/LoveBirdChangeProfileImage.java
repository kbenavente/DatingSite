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

@WebServlet("/LoveBirdChangeProfileImage")
public class LoveBirdChangeProfileImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoveBirdChangeProfileImage() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("username") == null) {
			
			response.sendRedirect("LoveBirdSignUp");
			
		} else {
			
			request.getRequestDispatcher("WEB-INF/LoveBirdChangeProfileImage.jsp").forward(request, response);
			
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String userID = Utilities.getUserID((String) request.getSession().getAttribute("username"));
		int user_id = Integer.parseInt(userID);
		
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
					
					//Place each file in chosen folder
					String file_name = items.get(i).getName();
					int dot_index = file_name.lastIndexOf('.');
					file_name =  String.format("profile-%s%s", user_id, file_name.substring(dot_index));
					System.out.println(file_name);
					Utilities.updateProfileImage((String) session.getAttribute("username"), file_name);
					File file = new File(folder_path, file_name);
					
					String file_to_delete = getServletContext().getRealPath("WEB-INF/files/" + (String) session.getAttribute("profile_image"));
					
					File delete_file = new File(file_to_delete);	
					delete_file.delete();
					
					//Write uploaded file content into this file
					items.get(i).write(file);
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String referer = request.getHeader("Referer");
//		response.sendRedirect(referer);
			
		response.sendRedirect("LoveBirdProfile");
		
		
		
	}

}
