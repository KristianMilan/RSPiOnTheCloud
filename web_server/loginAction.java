package com.raspberrypiapp.action;

import com.raspberrypiapp.utility.DBUtility;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class loginAction extends Action{
	
   @Override
   public ActionForward execute(ActionMapping mapping, ActionForm form,
     HttpServletRequest request, HttpServletResponse response)
     throws Exception {
	
     DBUtility db = new DBUtility();
     DynaActionForm myForm = (DynaActionForm) form;

     response.setContentType("application/octet-stream");
     
        
     try 
     {
         
       log("Login Action");
       request.getSession().removeAttribute("unauthorized");
       String userId = (String) myForm.get( "userId" );
       String pwd = (String) myForm.get( "password" );
       
       
       if( (userId!=null && pwd!=null)&&
          (db.verifyPassword(userId, pwd)))
       {
           log("User Verifired ---------------");
          return ( mapping.findForward( "success" ) );
       
       } 
       else
       {
           request.getSession().setAttribute("unauthorized", userId);
           log("unauthorized cant verify user");
           return ( mapping.findForward( "failure" ) );
       }
     }catch(Exception e){
    	e.printStackTrace();
   }

   return ( mapping.findForward( "failure" ) );
  }
   void log(String n)
   {
       System.out.println(n);
   }
}