/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package it.csbeng.nice2mit.servlet.filter;


import it.csbeng.nice2mit.infrastructure.database.HibernateSessionManager;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Carmine Ingaldi
 */

public class RestFilter implements Filter
{


    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        System.out.println("init");
        HibernateSessionManager.INSTANCE.openSession();

        //System.out.println( filterConfig.getServletContext().getRealPath("/resources/imgs"));



    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        chain.doFilter(request, response);
        
        
    }

    @Override
    public void destroy()
    {
        System.out.println("destroy");
        HibernateSessionManager.INSTANCE.closeSession();


    }

}
 