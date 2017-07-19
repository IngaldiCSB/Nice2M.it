/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.csbeng.nice2mit.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Luca
 */
public class SaveImage extends HttpServlet {
private static final String UPLOAD_DIRECTORY = "img";
private static final int THRESHOLD_SIZE     = 1024 * 1024 * 3;  // 3MB
private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException, Exception{
   
 if (!ServletFileUpload.isMultipartContent(request)) {
    PrintWriter writer = response.getWriter();
    writer.println("Request does not contain upload data");
    writer.flush();
    return;
}
 
DiskFileItemFactory factory = new DiskFileItemFactory();
factory.setSizeThreshold(THRESHOLD_SIZE);
factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setFileSizeMax(MAX_FILE_SIZE);
upload.setSizeMax(MAX_REQUEST_SIZE); 


 String uploadPath = getServletContext().getRealPath("resources/imgs/")
            + File.separator + UPLOAD_DIRECTORY;
        // creates the directory if it does not exist
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

List formItems = upload.parseRequest(request);
Iterator iter = formItems.iterator();
 
// iterates over form's fields
while (iter.hasNext()) {
    FileItem item = (FileItem) iter.next();
    // processes only fields that are not form fields
    if (!item.isFormField()) {
        String fileName = new File(item.getName()).getName();
        String filePath = uploadDir + File.separator + fileName;
        File storeFile = new File(filePath);
 
        // saves the file on disk
        item.write(storeFile);
    }
}


    }
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        processRequest(request, response);
    } catch (FileUploadException ex) {
        Logger.getLogger(SaveImage.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(SaveImage.class.getName()).log(Level.SEVERE, null, ex);
    }
      }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    try {
        processRequest(request, response);
    } catch (FileUploadException ex) {
        Logger.getLogger(SaveImage.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(SaveImage.class.getName()).log(Level.SEVERE, null, ex);
    }
       }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
