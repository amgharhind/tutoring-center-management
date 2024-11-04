package Controllers;

import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import dao.Dao;
import dao.IDao;
import entities.Document;
import entities.Utilisateur;

@WebServlet("/addDocumentController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50) 
public class addDocumentController extends HttpServlet {
  
   private static final long serialVersionUID = 1L;
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
   IDao dao = new Dao();
   HttpSession session = request.getSession();
   Utilisateur utilisateur = (Utilisateur)session.getAttribute("user");

   int profId = utilisateur.getIdUtilisateur();
   System.out.println("profId : "+profId);
   int matiereId = Integer.parseInt(request.getParameter("matiereId"));
   System.out.println("matiere id : "+matiereId);

   String documentType = request.getParameter("documentType");
   System.out.println("doc type : "+documentType);

   Part filePart = request.getPart("pdfFile");
   String contentDisposition = filePart.getHeader("Content-Disposition");
   String[] tokens = contentDisposition.split(";");
   String fileName = null;
   for (String token : tokens) {
       if (token.trim().startsWith("filename")) {
           fileName = token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
           break;
       }
   }

   if (fileName == null || fileName.isEmpty()) {
       response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
       return;
   }

   String uploadFolder ="D://DSBD//Jee//exercices/eduTrack2/src/main/webapp/documents";
   String filePath = uploadFolder + File.separator + fileName;

   System.out.println("File Name: " + fileName);
   System.out.println("File Path: " + filePath);

   try (InputStream fileContent = filePart.getInputStream();
        OutputStream outputStream = new FileOutputStream(filePath)) {

       byte[] buffer = new byte[4096];
       int bytesRead;
       while ((bytesRead = fileContent.read(buffer)) != -1) {
           outputStream.write(buffer, 0, bytesRead);
       }

       Document document = new Document();
       document.setNomDoc(fileName);
       document.setTaille(filePart.getSize());
       document.setTypeDoc(documentType);

       int result = dao.insertDocument(document, matiereId, profId);

       if (result > 0) {
           response.sendRedirect(request.getHeader("referer"));
       } else {
           response.sendRedirect(request.getContextPath() + "/error1.jsp");
       }

   } catch (IOException  e) {
       e.printStackTrace();
       response.sendRedirect(request.getContextPath() + "/error2.jsp");
   }
}
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
