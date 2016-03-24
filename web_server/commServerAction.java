package com.raspberrypiapp.action;


import com.raspberrypiapp.utility.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 *
 * @author awaisazeem
 */

public class commServerAction extends Action{
    DBUtility db = new DBUtility();
	
   @Override
   public ActionForward execute(ActionMapping mapping, ActionForm form,
     HttpServletRequest request, HttpServletResponse response)
     throws Exception 
   {
	
     
     commServConnector csa = new commServConnector();
    // DynaActionForm myForm = (DynaActionForm) form;

     response.setContentType("application/octet-stream");
    
     
     log("Rspi ID : "+request.getParameter("raspberryId"));  
     log("Pin No !!!!!!!!"+request.getParameter("pin"));
     log("State !!!!!!!!"+request.getParameter("state"));
     
     //request.getParameter("state")
     if(request.getParameter("state").equals(1))
        csa.setPin(request.getParameter("raspberryId"), Integer.parseInt(request.getParameter("pin")), false);
     else
        csa.setPin(request.getParameter("raspberryId"), Integer.parseInt(request.getParameter("pin")), true);
         
     
     
   return ( mapping.findForward( "failure" ) );
  }
   void log(String n)
   {
       System.out.println(n);
   }
   
}