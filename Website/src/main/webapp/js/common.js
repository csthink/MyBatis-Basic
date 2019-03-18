var $regPage1 = $("#regPage1"); // 手机号输入模块
var $regPage2 = $("#regPage2"); // 手机号输入模块
var $phoneNumModule = $("#phoneNumModule"); // 手机号发短信提示模块
var $rightNotice = $("#rightNotice"); // 手机号校验正确提示框
var $errorNotice = $("#errorNotice"); // 手机号校验错误提示框
var $phone = $("input[name=phone]"); // 手机号文本框
var $imageVerifyCode = $("input[name=imageVerifyCode]"); // 图片验证码文本框
var $smsVerifyCode = $("input[name=smsVerifyCode]"); // 短信验证码文本框
var $sendMsgBtn = $("#sendMsgBtn"); // 发送短信按钮
var $nextBtn = $("#nextBtn"); // 下一步按钮
var $registerBtn = $("#registerBtn"); // 注册提交按钮

var countdownInit = 5; // 倒计时初始化时间
var countToZeroTime = 5; // 倒计时秒数

/**
 * 获取图片验证码
 */
function changeVerifyImage() {
    $("#verifyImage").attr("src", "/captcha.do?s=" + Math.random());
    $imageVerifyCode.val("").focus();
    $imageVerifyCode.parent().find(".error-tips").addClass("invisible").removeClass("visible"); // 关闭错误提示
    $imageVerifyCode.parent().find(".normal-tips").hide(); // 关闭正常提示
    $sendMsgBtn.attr("disabled", true); // 禁用发送短信按钮
    $smsVerifyCode.val("").attr("disabled", true).parent().find(".error-tips").addClass("invisible").removeClass("visible"); // 禁用短信文本框
}

/**
 * 手机号有效的反馈
 */
function validPhoneFeedback() {
    $phone.parent().find(".normal-tips").show(); // 打开正常提示
    $phone.parent().find(".error-tips").hide(); // 关闭错误提示
    var tempPhone = $phone.val(); // 获取用户输入的手机号
    tempPhone = tempPhone.substr(0, 3) + " " + tempPhone.substr(3, 4) + " " + tempPhone.substr(7, 4); // 格式化手机号输出
    $phoneNumModule.find(".phoneNum").html(tempPhone); // 给手机号发短信模块的手机号填充值
    $rightNotice.fadeIn(1500);
    $errorNotice.hide(1500);
    setTimeout(function () {
        $rightNotice.fadeOut(2500);
        $nextBtn.attr("disabled", false); // 启用下一步
    }, 100);
}

/**
 * 手机号无效的反馈
 */
function invalidPhoneFeedback() {
    $phone.parent().find(".normal-tips").hide(); // 关闭正常提示
    $phone.parent().find(".error-tips").show(); // 打开错误提示
    $nextBtn.attr("disabled", true); // 禁用下一步
    $rightNotice.hide();
}

/**
 * 图形验证码有效的反馈
 */
function validImageVerifyCodeFeedback(msg) {
    $imageVerifyCode.parent().find(".error-tips").addClass("invisible").removeClass("visible"); // 关闭错误提示
    $imageVerifyCode.parent().find(".normal-tips").show().text(msg); // 开启正常提示
    $sendMsgBtn.attr("disabled", false); // 启用发送短信按钮
    $smsVerifyCode.attr("disabled", false).focus(); // 启用短信文本框
}

/**
 * 图形验证码输入错误的反馈
 */
function invalidImageVerifyCodeFeedback(msg) {
    $imageVerifyCode.parent().find(".error-tips").addClass("visible").removeClass("invisible").text(msg); // 打开错误提示
    $imageVerifyCode.parent().find(".normal-tips").hide(); // 关闭正常提示
    $sendMsgBtn.attr("disabled", true); // 禁用发送短信按钮
    $smsVerifyCode.val("").attr("disabled", true).parent().find(".error-tips").addClass("invisible").removeClass("visible"); // 禁用短信文本框
}

/**
 * 短信验证码输入正确的反馈
 */
function validSMSVerifyCodeFeedback(msg) {
    $registerBtn.attr("disabled", false).Shake(1, 6); // 启用注册按钮
    $smsVerifyCode.parent().find(".error-tips").addClass("invisible").removeClass("visible"); // 关闭错误提示
    $smsVerifyCode.parent().find(".normal-tips").show().text(msg); // 打开正常提示
}

/**
 * 短信验证码输入错误的反馈
 */
function invalidSMSVerifyCodeFeedback(msg) {
    $registerBtn.attr("disabled", true); // 禁用注册按钮
    $smsVerifyCode.parent().find(".error-tips").addClass("visible").removeClass("invisible").text(msg); // 打开错误提示
    $smsVerifyCode.parent().find(".normal-tips").hide(); // 关闭正常提示
}

/**
 * 抖动特效
 * @param shakeNum 抖动次数
 * @param shakeDistance
 * @returns {jQuery.fn.Shake}
 * @constructor
 */
jQuery.fn.Shake = function (shakeNum, shakeDistance) {
    this.each(function () {
        var jSelf = $(this);
        jSelf.css({position: 'relative'});
        for (var x = 1; x <= shakeNum; x++) {
            jSelf.animate({left: (-shakeDistance)}, 300)
                .animate({left: shakeDistance}, 300)
                .animate({left: 0}, 50);
        }
    });

    return this;
};

/**
 * 倒计时
 * @param obj
 */
function countdown(obj) {
    if (countToZeroTime === 0) {
        obj.attr("disabled", false);
        obj.html("发送验证码");
        countToZeroTime = countdownInit;
    } else {
        obj.attr("disabled", true);
        obj.html("重新发送(" + countToZeroTime + ")");
        countToZeroTime--;
        setTimeout(function () {
            countdown(obj)
        }, 1000)
    }
}

/**
 * 手机号校验
 */
function checkPhone() {
    var pattern = /^1[34578]\d{9}$/;
    // 判断手机号是否有效
    if (!pattern.test($phone.val())) {
        // 手机号不合法
        invalidPhoneFeedback();
    } else {
        // 手机号合法,验证手机号是否已注册
        $.post("/checkPhone.do",
            {
                phone: $phone.val()
            },
            function (result) {
                var flag = result.flag;
                if (flag === true) {
                    validPhoneFeedback();
                } else {
                    invalidPhoneFeedback();
                    $errorNotice.slideDown("fast");
                    $rightNotice.hide();
                }
            }, "json");
    }
}

/**
 * 图像验证码校验
 */
function checkImageCode() {
    // 判断验证码输入是否合法
    if ($imageVerifyCode.val().length === 4) {
        // 校验验证码
        $.post("/checkCaptcha.do",
            {code: $imageVerifyCode.val()},
            function (result) {
                console.log("img: " + result);
                var flag = result.flag;
                var msg = result.msg;
                if (flag === true) {
                    validImageVerifyCodeFeedback(msg);
                } else {
                    invalidImageVerifyCodeFeedback(msg);
                }
            }, "json");
    } else {
        invalidImageVerifyCodeFeedback();
    }
}

/**
 * 短信验证码校验
 */
function checkSMSCode() {
    if ($smsVerifyCode.val().length === 4) {
        // 发送短信验证码
        $.post("/checkSMS.do",
            {
                code: $smsVerifyCode.val()
            },
            function (result) {
                console.log("sms: " + result);
                var flag = result.flag;
                var msg = result.msg;
                if (flag === true) {
                    validSMSVerifyCodeFeedback(msg);
                } else {
                    console.log("sms fail");
                    invalidSMSVerifyCodeFeedback(msg);
                }
            }, "json");
    } else {
        invalidSMSVerifyCodeFeedback();
    }
}

$(function () {
    // 手机号文本框
    $phone.keyup(function () {
        checkPhone();
    }).focus(function () {
        $errorNotice.hide();
        $rightNotice.hide();
    });

    // 下一步按钮
    $nextBtn.click(function () {
        $nextBtn.hide();
        $rightNotice.hide();
        $errorNotice.hide();
        $regPage1.hide();
        $regPage2.show();
        $imageVerifyCode.focus();
    });

    // 图片验证码文本框
    $imageVerifyCode.keyup(function () {
        checkImageCode();
    });

    // 发送短信按钮
    $sendMsgBtn.click(function () {
        countdown($(this));
        // 发送短信验证码
        $.post("/sendSMS.do",
            {
                phone: $phone.val()
            },
            function (result) {
                console.log("sendSMS: " + result);
                var flag = result.flag;
                var msg = result.msg;
                if (flag === true) {
                    validSMSVerifyCodeFeedback(msg);
                } else {
                    console.log("sms fail");
                    invalidSMSVerifyCodeFeedback(msg);
                }
            }, "json");
    });

    // 短信验证码文本框
    $smsVerifyCode.keyup(function () {
        checkSMSCode();
    });

    // 注册操作
    $registerBtn.click(function () {
        $.post("/register.do",
            {
                phone: $phone.val(),
                smsCode: $smsVerifyCode.val()
            },
            function (result) {
                console.log("注册操作: " + result);
                var flag = result.flag;
                var msg = result.msg;
                if (flag === true) {
                    window.location.href="/message/list.do";
                } else {
                    window.location.href="/register_prompt.do";
                }
            }, "json");
    });
});