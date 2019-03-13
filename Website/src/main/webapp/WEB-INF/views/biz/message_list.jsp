<%@ page import="com.csthink.bbs.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta content="initial-scale=1, shrink-to-fit=no, width=device-width" name="viewport">
    <title>message页面</title>
    <!-- Add Material font (Roboto) and Material icon as needed -->
    <link rel="stylesheet" href="/css/font.css">
    <!-- Add Material CSS, replace Bootstrap CSS -->
    <link href="/css/material.min.css" rel="stylesheet">
</head>
<body style="padding-top: 3.5rem">
<nav class="navbar fixed-top navbar-light bg-light">
    <div class="col-3 offset-2">
        <a class="navbar-brand" href="#">小不点</a>
    </div>
    <div class="col-3 offset-4">
        <%
            if (null != session.getAttribute("user")) {
                User user = (User)session.getAttribute("user");
        %>
            <div class="btn-group">
                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Admin
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="#">我的信息</a>
                    <a class="dropdown-item" href="#">我的留言</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="#">退出</a>
                </div>
            </div>
        <%
            } else {
        %>
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="#">注册</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="#">登录 <span class="sr-only">(current)</span></a>
                </li>
            </ul>
        <%
            }
        %>

    </div>
</nav>

<div class="container" style="margin-top: 25px;">
    <div class="jumbotron">
        <h1 class="display-4">Hello, MyBatis!</h1>
        <p class="lead">这是一个使用 MyBatis 实现 Java 操作数据库的简单样例.</p>
        <hr class="my-4">
        <p>致所有热爱编程的我们.</p>
        <a class="btn btn-success btn-lg" href="https://www.csthink.com" target="_blank" role="button">了解更多</a>
    </div>

    <div class="row" style="margin-top: 20px;">
        <div class="col">
            <button type="button" class="btn btn-dark">
                记录总数 <span class="badge badge-light">${total}</span>
                <span class="sr-only">unread messages</span>
            </button>
        </div>
    </div>

    <div class="row" style="margin-top: 20px;">

        <div class="col">
            <c:forEach var="message" items="${messageList}">
                <div class="list-group">
                    <a href="#" class="list-group-item list-group-item-action flex-column align-items-start">
                        <div class="d-flex w-100 justify-content-between">
                            <h5 class="mb-1">${message.title}</h5>
                            <small><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${message.createTime}"/></small>
                        </div>
                        <p class="mb-1">${message.content}</p>
                        <small>${message.username}</small>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>



    <nav aria-label="...">
        <ul class="pagination justify-content-center">
            <c:choose>
                <c:when test="${page != 1}">
                    <li class="page-item">
                </c:when>
                <c:otherwise>
                <li class="page-item disabled">
                </c:otherwise>
            </c:choose>
                <a class="page-link" href="/message/list.do?page=${page - 1}" tabindex="-1">Previous</a>
            </li>

            <c:forEach items="${pageList}" var="pageStr">
                <c:choose>
                    <c:when test="${pageStr == page}">
                        <li class="page-item active">
                            <a class="page-link" href="/message/list.do?page=${page}">${pageStr} <span class="sr-only">(current)</span></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="/message/list.do?page=${pageStr}">${pageStr}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:choose>
                <c:when test="${page != lastPage}">
                    <li class="page-item">
                </c:when>
                <c:otherwise>
                    <li class="page-item disabled">
                </c:otherwise>
            </c:choose>
                <a class="page-link" href="/message/list.do?page=${page + 1}">Next</a>
            </li>
        </ul>
    </nav>


</div><!-- /container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="/js/jquery.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap4.1.1.min.js"></script>
<!-- Then Material JavaScript on top of Bootstrap JavaScript -->
<script src="/js/material.min.js"></script>
</body>
</html>