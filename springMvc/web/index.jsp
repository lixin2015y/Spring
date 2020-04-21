<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2020/4/19
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="/testPojo">
    <input type="text" name="userName" value="12312312">
    <input type="text" name="age" value="12312312">
    <input type="submit" value="测试pojo">
</form>
<a href="/testCookieValue">测试cookieValue</a><br/>

<a href="/testRequestHeader">测试requestHeader</a><br/>

<a href="/hello">hello</a><br/>
<a href="/testPathVariable/1">testPathVariable</a><br/>
<a href="/testHeadersAndParams?username=tom&password=123">测试headers和params</a><br/>

<h1>测试rest</h1>
<form action="/order/1" method="get"><input type="submit" value="get"></form>
<br/>
<form action="/order/1" method="post"><input type="submit" value="post"></form>
<br/>
<form action="/order/1" method="post"><input type="hidden" name="_method" value="DELETE"> <input type="submit"
                                                                                                 value="delete"></form>
<br/>
<form action="/order/1" method="post"><input type="hidden" name="_method" value="PUT"> <input type="submit" value="put">
</form>
<br/>
<a href="/testModalAndView?userName=李新"> 测试modalAndView</a><br/>
<a href="/getAllUser">拿到所有的User</a><br/>
<a href="/download">下载私密图片</a><br/>


</body>
</html>
