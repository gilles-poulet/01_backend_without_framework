package ue1104.iramps.be;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

public class Testing extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter pw = res.getWriter();
        pw.print(new StringBuilder("Get API demandée").append(req.getContextPath()) );
        pw.flush();
        pw.close();
    }

    public void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter pw = res.getWriter();
        pw.print(new StringBuilder("Put API demandée").append(req.getContextPath()) );
        pw.flush();
        pw.close();
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter pw = res.getWriter();
        pw.print(new StringBuilder("Delete API demandée").append(req.getContextPath()) );
        pw.flush();
        pw.close();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        res.setContentType("text/plain");
        PrintWriter pw = res.getWriter();
        pw.print(new StringBuilder("Post API demandée").append(req.getContextPath()) );
        pw.flush();
        pw.close();
    }
}