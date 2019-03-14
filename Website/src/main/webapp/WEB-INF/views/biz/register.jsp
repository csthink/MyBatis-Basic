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
    <title>小不点注册页面</title>
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
            <li class="nav-item active">
                <a class="nav-link" href="/register_prompt.do">注册 <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/login_prompt.do">登录 </a>
            </li>
        </ul>
    </div>
</nav>

<div class="container" style="margin-top: 25px;">

    <div class="col-6 offset-4">
        <div class="row" style="margin-top: 1rem; display: none;" id="rightNotice">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong>恭喜您!</strong> 该手机号可以用于注册.
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>

        <div class="row" style="margin-top: 1rem; display:none;" id="errorNotice">
            <div class="alert alert-warning alert-dismissible fade show" role="alert">
                <strong>该手机号已被注册!</strong> 请直接 <a href="/login_prompt.do">登录</a>
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
    </div>

    <div class="row" style="margin-top: 2rem">
        <div class="col-8 offset-2 jumbotron">
            <form>
                <div class="form-group row" id="phoneInputModule">
                    <label for="phone" class="col-sm-3 col-form-label text-right">手机</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="phone" name="phone" placeholder="请输入手机号" autofocus>
                        <small class="normal-tips text-dark">此手机将作为登录用户名，请谨慎填写</small>
                        <small class="error-tips text-danger" style="display: none;">手机号无效</small>
                    </div>
                </div>

                <div class="form-group row" style="margin-top: 4rem;">
                    <div class="col-sm-2 offset-5">
                        <button type="button" class="btn btn-dark" id="nextBtn" disabled>下一步</button>
                    </div>
                </div>

                <div class="form-group row" id="imageVerifyModule" style="display: none;">
                    <label for="imageVerifyCode" class="col-sm-3 col-form-label text-right">图形验证码</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="imageVerifyCode" placeholder="请输入图形验证码" name="imageVerifyCode">
                        <small class="normal-tips text-dark">完成验证才能继续操作哦</small>
                        <small class="error-tips text-danger" style="display: none;">图形验证码错误</small>
                    </div>
                    <div class="col-sm-3">
                        <img src="/image_verify_code.do" id="verifyImage" alt="图形验证码" width="100%" height="40px" onclick="javascript:changeVerifyImage()" style="cursor: pointer;">
                    </div>
                </div>

                <div class="form-group row" id="msgVerifyModule" style="display: none">
                    <label for="msgVerifyCode" class="col-sm-3 col-form-label text-right">手机验证码</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="msgVerifyCode" placeholder="请输入短信验证码" name="msgVerifyCode" disabled>
                        <small class="normal-tips text-dark">小不点已发送一条验证短信到你的手机</small>
                        <small class="error-tips text-danger" style="display: none;">短信校验失败</small>
                    </div>
                    <div class="col-sm-3">
                        <button type="button" class="btn btn-dark active" id="sendMsgBtn" data-toggle="button" aria-pressed="false" autocomplete="off" style="width: 100%;" disabled>发送验证码</button>
                    </div>
                </div>
                
                <div class="form-group row" style="margin-top: 4rem; display: none;" id="submitModule">
                    <div class="col-sm-2 offset-5">
                        <button type="submit" class="btn btn-dark" id="registerBtn" disabled>注册</button>
                    </div>
                </div>
            </form>
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
<script>
    $(function () {
        $("form").submit(function () {

           return false;
        });
    })


    // Example starter JavaScript for disabling form submissions if there are invalid fields
    // (function() {
    //     'use strict';
    //     window.addEventListener('load', function() {
    //         // Fetch all the forms we want to apply custom Bootstrap validation styles to
    //         var forms = document.getElementsByClassName('needs-validation');
    //         // Loop over them and prevent submission
    //         var validation = Array.prototype.filter.call(forms, function(form) {
    //             form.addEventListener('submit', function(event) {
    //                 if (form.checkValidity() === false) {
    //                     event.preventDefault();
    //                     event.stopPropagation();
    //                 }
    //                 form.classList.add('was-validated');
    //             }, false);
    //         });
    //     }, false);
    // })();
</script>
</body>
</html>