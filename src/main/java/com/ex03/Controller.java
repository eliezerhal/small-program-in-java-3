package com.ex03;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "")
public class Controller extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        // this param will be available to all other servlets
        context.setAttribute("id", new int[]{0});
        context.setAttribute("db", new DataBase());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException , ServletException {
        response.setContentType("text/html");
        request.getRequestDispatcher("index.html").include(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String url = request.getParameter("enterURL");
        ServletContext context = getServletContext();
        // this param will be available to all other servlets
        WebCrawlerWithDepth wc = new WebCrawlerWithDepth();
        int id[] = (int[]) context.getAttribute("id");
        id[0]++;
        DataBase db = (DataBase) context.getAttribute("db");
        wc.insertData(id[0], db, url);
        db.setImgCount(id[0]);
        wc.start();
        //wc.getPageLinks(url, 0);
        Cookie cookie = new Cookie("url",url);
        response.addCookie(cookie);
        Cookie idCookie = new Cookie("id", String.valueOf(id[0]));
        response.addCookie(idCookie);
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>ImgPage</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>Crawling of <b>" + url + "</b> started...</p>\n");
        out.println("<p>visit in <a href=\"ThreadCounter\">this page</a> to track results</p>" +
                "</body>\n" +
                "</html>");


    }

    public void destroy() {
    }
}