package com.ex03;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "")
public class Controller extends HttpServlet {

    /**
     * This function initializes the global parameters in the program
     * @param config The configuration of the program
     * @throws ServletException If there is problem in initialization of the servlet
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = getServletContext();
        // this param will be available to all other servlets
        context.setAttribute("id", new int[]{-1});
        //context.setAttribute("id", new Integer(-1));
        context.setAttribute("db", new DataBase());
    }

    /**
     * This function handles the request of get
     * @param request The request of the user
     * @param response The response of the user
     * @throws ServletException if there is problems in the servlet
     * @throws IOException If there is problems in reading the file
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException , ServletException {
        response.setContentType("text/html");
        request.getRequestDispatcher("index.html").include(request, response);
    }

    /**
     * This function handles the request of post
     * @param request The request of the user
     * @param response The response of the user
     * @throws ServletException if there is problems in the servlet
     * @throws IOException If there is problems in reading the file
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String url = request.getParameter("enterURL");
        if(!checkValidUrl(url) || !validation(url))
            request.getRequestDispatcher("error.html").include(request, response);
        ServletContext context = getServletContext();
        // this param will be available to all other servlets
        WebCrawlerWithDepth wc = new WebCrawlerWithDepth(url);
        //Integer id = (Integer) context.getAttribute("id");
        synchronized(this) {
            int id[] = (int[]) context.getAttribute("id");
            id[0]++;
            //id++;
            DataBase db = (DataBase) context.getAttribute("db");
            db.add(wc);
            //wc.insertData(id[0], db, url);
            //db.setImgCount(id[0]);
            wc.start();
            //wc.getPageLinks(url, 0);
            Cookie cookie = new Cookie("url",url);
            response.addCookie(cookie);
            Cookie idCookie = new Cookie("id", String.valueOf(id[0]));
            response.addCookie(idCookie);
        }

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

    /*public void destroy() {
    }*/

    /**
     * This function checks the integrity of the URL
     * @param Url This is the URL that needs to be checked
     * @return true if the url is valid and false it's invalid
     */
    private boolean checkValidUrl(String Url){
        try  {
            URL url = new URL(Url);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("HEAD");
            if(!connect.getContentType().contains("html"))
                return false;
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * This function checks if responseCode valid or not
     * @param Url This is the URL that needs to be checked
     * @return true if the url is valid and false it's invalid
     * @throws IOException if there is problem in page of the link
     */
    private boolean validation(String  Url) throws IOException {
        URL url = new URL(Url);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();

        int responseCode = huc.getResponseCode();
        if(responseCode != 200)
            return false;
        return true;
    }
}