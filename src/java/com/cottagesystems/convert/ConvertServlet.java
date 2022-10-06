
package com.cottagesystems.convert;

import japa.parser.ParseException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jbf
 */
@WebServlet(name = "ConvertServlet", urlPatterns = {"/ConvertServlet"})
public class ConvertServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String code= request.getParameter("code");
        
        boolean onlyStatic = "true".equals( request.getParameter("onlyStatic") );
        
        String pythonVersion = request.getParameter("pythonTarget");
        if ( pythonVersion==null ) pythonVersion= PythonTarget.jython_2_2.toString();
                
        response.setContentType("text/html;charset=UTF-8");

        Convert convert= new Convert();
        convert.setOnlyStatic(onlyStatic);
        convert.setPythonTarget(PythonTarget.valueOf(pythonVersion));
        
        String jythonCode;
        try {
            if (code!=null ) {
                jythonCode = convert.doConvert(code);
            } else {
                jythonCode = "";
                code= "";
            }
        } catch (ParseException ex) {
            jythonCode = "*** "+ex.getMessage()+" ***";
        }
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Jython Result</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ConvertServlet at " + request.getContextPath() + "</h1>");
            out.println("<form action=\"ConvertServlet\" method=\"post\">");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td>Java Code:<br>");
            out.println("<textarea rows=\"40\" cols=\"80\" name=\"code\">"+code+"</textarea>");            
            out.println("</td>");
            out.println("<td>Jython Code:<br>");
            out.println("<textarea rows=\"40\" cols=\"132\">"+jythonCode+"</textarea>");
            out.println("</td>");
            out.println("</tr></table>");
            out.println( String.format( "<input type=\"checkbox\" id=\"onlyStatic\" name=\"onlyStatic\" value=\"true\" %s>Only Static Parts</input>",
                    convert.isOnlyStatic() ? "checked" : "" ) );
            //out.println("<select name=\"jythonVersion\" id=\"jythonVersion\">");
            //out.println(String.format( "    <option value=\"jython_2_2\" %s>Jython 2.2</option>", 
            //        convert.getPythonTarget()==Convert.PythonTarget.jython_2_2 ? "selected=1" : ""  ) );
            //out.println(String.format( "    <option value=\"python_3_6\" %s>Python 3.6</option>", 
            //        convert.getPythonTarget()==Convert.PythonTarget.python_3_6 ? "selected=1" : ""  ) );
            //out.println("</select>");
            out.println("<input type=\"submit\" value=\"submit\"></input>");
            out.println("</form action=\"ConvertServlet\" method=\"post\">");            
            out.println("<small>Version 20221006b</small>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
