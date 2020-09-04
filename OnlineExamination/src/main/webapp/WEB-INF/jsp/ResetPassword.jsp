<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Online Examination</title>
<style>
body {background-color: powderblue;}
.icons {height: 100px;width: 100px;}
table {
font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  width: 100%;
}

td {
 padding: 15px;
  height: 50px;
  background-color: #f5f5f5;
}

.button {
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
}

.button:hover {
  background-color: #4CAF50; /* Green */
  color: white;
}
</style>

<script type="text/javascript">
</script>
</head>

<body onload="hideOptionAnswer()">
<jsp:include page="header.jsp"></jsp:include>
    <h3>Reset Password</h3>
    
    <h4 style="color:red;">${message}</h4>
    
    <div align="center" style="margin-top:50">
    <form:form method="POST" id="userForm"
          action="/updatePassword" modelAttribute="user">
             <table>

                <tr>
                     <td><form:label path="username">User Name</form:label></td>
                    <td>${user.username}<form:hidden path="username" /></td>
                </tr>
  
                <tr>
                    <td><form:label path="password">Enter Existing Password</form:label></td>
                    <td><form:input path="password" /></td>
                </tr>
                
                <tr>
                     <td><form:label path="newPassword">Enter New Password</form:label></td>
                    <td><form:input path="newPassword"/></td>
                </tr>
                
                <tr>
                     <td><form:label path="confirmNewPassword">Confirm New Password</form:label></td>
                    <td><form:input path="confirmNewPassword"/></td>
                </tr>
                
                <tr>
               	    <td><input type="reset" value="Reset"/></td>
                    <td><input type="submit" value="Submit" /></td>
                </tr>
            </table>
        </form:form>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>



</html>