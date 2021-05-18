package com.ex03;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ThreadCounter", value = "/ThreadCounter")
public class ThreadCounter extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "";
        Integer id = -1;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
            response.sendRedirect("/");
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals("url")) {
                url = cookies[i].getValue();
            }
            if (cookies[i].getName().equals("id")) {
                id = Integer.parseInt(cookies[i].getValue());
            }
        }
        if(url == "" || id == -1)
            response.sendRedirect("/");

        ServletContext context = getServletContext();
        DataBase db = (DataBase) context.getAttribute("db");
        out.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Index</title>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "<body>" +
                "<p>Crawling URL " + url +" Current results: " + db.getImgCount(id) + " images found.</p>" +
                " press   <a href=\"/ThreadCounter\">here</a> to reload\n" +
                "</body>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}