<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta content="initial-scale=1, shrink-to-fit=no, width=device-width" name="viewport">
    <title>小不点登录页面</title>
    <!-- Add Material font (Roboto) and Material icon as needed -->
    <link rel="stylesheet" href="/css/font.css">
    <!-- Add Material CSS, replace Bootstrap CSS -->
    <link href="/css/material.min.css" rel="stylesheet">
</head>
<body style="padding-top: 3.5rem">
<nav class="navbar fixed-top navbar-light bg-light">
    <div class="col-3 offset-2">
        <a class="navbar-brand" href="/message/list.do">小不点</a>
    </div>
    <div class="col-3 offset-4">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="/register_prompt.do">注册 </a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/login_prompt.do">登录 <span class="sr-only">(current)</span></a>
            </li>
        </ul>
    </div>
</nav>

<div class="container" style="margin-top: 25px;">
    <div class="col-4 offset-4" style="height: 3rem;">
        <%
            String errorMsg = "";
            if (null != request.getAttribute("errorMsg")) {
                errorMsg = request.getAttribute("errorMsg").toString();
            }
        %>
        <%
            if (null != request.getAttribute("errorMsg")) {
        %>
            <div class="row justify-content-center" style="margin-top: 1rem;" id="errorMsg">
        <%
        } else {
        %>
        <div class="row justify-content-center" style="margin-top: 1rem; display:none;" id="errorMsg">
        <%
            }
        %>
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong><%=errorMsg%></strong>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
    </div>
    <div class="col-4 offset-4" style="height: 3rem;">
        <div class="row justify-content-center" style="margin-top: 1rem; display: none;" id="rightNotice">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong>恭喜您!</strong> 该手机号可以用于登录.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
        <div class="row justify-content-center" style="margin-top: 1rem; display:none;" id="errorNotice">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong>该手机号尚未注册!</strong> 请先 <a href="/register_prompt.do">注册</a>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
    </div>

    <div class="row" style="margin-top: 2rem">
        <div class="col-8 offset-2 jumbotron">
            <div id="loginPage1">
                <form action="/login.do" method="post">
                    <input type="hidden" name="type" value="sms">
                    <div class="form-group row">
                        <label for="phone" class="col-sm-3 col-form-label text-right">手机</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号"
                                   autofocus>
                            <small class="normal-tips text-dark"></small>
                            <small class="error-tips text-danger" style="display: none;">手机号无效</small>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="smsVerifyCode" class="col-sm-3 col-form-label text-right">手机验证码</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="smsVerifyCode" placeholder="请输入短信验证码" name="smsVerifyCode" disabled>
                            <small class="normal-tips text-dark"></small>
                            <small class="error-tips text-danger invisible">短信校验失败</small>
                        </div>
                        <div class="col-sm-3">
                            <button type="button" class="btn btn-dark active" id="sendMsgBtn" data-toggle="button" aria-pressed="false" autocomplete="off" style="width: 100%;" disabled>发送验证码</button>
                        </div>
                    </div>

                    <div class="form-group row" style="margin-top: 4rem;">
                        <div class="col-sm-2 offset-5">
                            <button type="submit" class="btn btn-dark submit" id="submit1" disabled>登录</button>
                        </div>
                    </div>
                </form>
                <a href="javascript:void(0)" id="changeToPage2">
                    <small>使用账号密码登录</small>
                </a>
            </div>

            <div id="loginPage2" style="display: none;">
                <form action="/login.do" method="post">
                    <input type="hidden" name="type" value="pwd">
                    <div class="form-group row">
                        <label for="phone2" class="col-sm-3 col-form-label text-right">手机</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="phone2" name="phone" placeholder="请输入手机号"
                                   autofocus>
                            <small class="normal-tips text-dark"></small>
                            <small class="error-tips text-danger" style="display: none;">手机尚未注册，请先<a href="/register_prompt.do">注册</a></small>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="password" class="col-sm-3 col-form-label text-right">密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="password" placeholder="请输入密码"
                                   name="password">
                            <small class="normal-tips text-dark"></small>
                            <small class="error-tips text-danger invisible">密码无效</small>
                        </div>
                        <div class="col-sm-3">
                        </div>
                    </div>
                    <div class="form-group row" style="margin-top: 4rem;">
                        <div class="col-sm-2 offset-5">
                            <button type="submit" class="btn btn-dark submit" id="submit2">登录</button>
                        </div>
                    </div>
                </form>
                <a href="javascript:void(0)" id="changeToPage1">
                    <small>使用短信快捷登录</small>
                </a>
            </div>
        </div>
    </div>
</div><!-- /container -->
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="/js/jquery.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap4.1.1.min.js"></script>
<!-- Then Material JavaScript on top of Bootstrap JavaScript -->
<script src="/js/material.min.js"></script>
<script src="/js/common.js"></script>
</body>
</html>