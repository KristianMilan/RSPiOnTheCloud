<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="raspberry.css">
        <title><bean:message key="welcome.title"/></title>
        <html:base/>
    </head>
    <body style="background-color: white">
        
        <logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
            <div  style="color: red">
                ERROR:  Application resources not loaded -- check servlet container
                logs for error messages.
            </div>
        </logic:notPresent>
        
        
<div id="header">
    <h1>Raspberry PI</h1>
</div>



<div id="section">


<!--h1>Log In</h1-->
<html:form action="login">
    <fieldset>
    <legend>Login Details:</legend>
  User Id
  <br>
  <input type="text" name="userId"><br>
  <br/>
  Password:
  <br>
  <input type="password" name="password" /><br><br>
  <input type="submit" value="Submit"/>
    </fieldset>
</html:form>
<logic:present name="unauthorized" scope="session">
            <div  style="color: red">
                ERROR:  User id or password is incorrect
            </div>
</logic:present>
<a a href ="/newUser.jsp">Don't have account? create New</a>



</div>

        
        
<!--div id="footer"></div-->
      
        
    </body>
</html:html>
