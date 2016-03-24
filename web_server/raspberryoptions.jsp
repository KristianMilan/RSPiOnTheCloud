<%@page import="com.raspberrypiapp.utility.DBUtility"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.raspberrypiapp.utility.*" %>
<%@ page import="com.raspberrypiapp.action.*" %>

<html:html lang="true">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="raspberry.css">
        <title><bean:message key="welcome.title"/></title>
        <html:base/>
        
        <script>
            function myFunction() 
            {
                     document.getElementById("myForm").submit();
            }
</script>
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
<%  DBUtility db = new DBUtility();
    ArrayList<Rspi> rspi = db.getRaspberryPi(1);
              

for(int i=0;i<rspi.size();i++)
{
            %>
<fieldset>
    
    <legend>
        Raspberry PI Options: <%if(rspi.get(i).getOnlineStatus()==1){out.println("ONLINE");}else out.println("OFFINE");%>
    </legend>
<table>
    
    <tr>
        <td> PIN Number</td>
        <td> Action ON/OFF</td>
        <td> Current State </td>
    </tr>
    <html:form  action="/commServer">
    <tr>
        <td> 1</td>
        <td> <input type="submit" name="option1" value="ON/OFF"/></td>
        <td><% if(rspi.get(i).getPin1()==1){out.println("ON");}else out.println("OFF");%> </td>
    <input type="hidden" name="raspberryId" value="<%out.println(rspi.get(i).getIdRSPi());%>"/>
    <input type="hidden" name="pin" value="1"/>
    <input type="hidden" name="state" value="<%out.println(rspi.get(i).getPin1());%>"/>
    </tr>
    </html:form>
    <html:form  action="/commServer">
    <tr>
        <td> 2</td>
        <td> <input type="submit" name="option2" value="ON/OFF"/></td>
        <td> <% if(rspi.get(i).getPin2()==1){out.println("ON");}else out.println("OFF");%> </td>
        <input type="hidden" name="raspberryId" value="<%out.println(rspi.get(i).getIdRSPi());%>"/>
    <input type="hidden" name="pin" value="2"/>
    <input type="hidden" name="state" value="<%out.println(rspi.get(i).getPin1());%>"/>
    </tr>
    </html:form>
    <html:form  action="/commServer">
    <tr>
        <td> 3</td>
        <td> <input type="submit" name="option3" value="ON/OFF"  /></td>
        <td> <%if(rspi.get(i).getPin3()==1){out.println("ON");}else out.println("OFF");%> </td>
        <input type="hidden" name="raspberryId" value="<%out.println(rspi.get(i).getIdRSPi());%>"/>
        <input type="hidden" name="pin" value="3"/>
        <input type="hidden" name="state" value="<%out.println(rspi.get(i).getPin1());%>"/>
    </tr>
    </html:form>
</table>
</fieldset>
    
    <%
        }
    %>
    
</div>

        
        

      
        
    </body>
</html:html>
