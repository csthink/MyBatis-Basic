var $phoneInputModule = $("#phoneInputModule"); // 手机号输入模块
var $imageVerifyModule = $("#imageVerifyModule"); // 图形验证模块
var $phoneNumModule = $("#phoneNumModule"); // 手机号发短信提示模块
var $msgVerifyModule = $("#msgVerifyModule"); // 短信验证模块
var $submitModule = $("#submitModule"); // 提交模块

var $rightNotice = $("#rightNotice");
var $errorNotice = $("#errorNotice");

var $phone = $("input[name=phone]"); // 手机号文本框
var $imageVerifyCode = $("input[name=imageVerifyCode]"); // 图片验证码文本框
var $msgVerifyCode = $("input[name=msgVerifyCode]"); // 短信验证码文本框
var $sendMsgBtn = $("#sendMsgBtn"); // 发送短信按钮
var $nextBtn = $("#nextBtn"); // 下一步按钮


var countdownInit = 5; // 倒计时初始化时间
var countToZeroTime = 5; // 倒计时秒数

/**
 * 获取图片验证码
 */
function changeVerifyImage() {
    $("#verifyImage").attr("src","/image_verify_code.do?s=" + Math.random());
    $imageVerifyCode.val('').parent().find(".error-tips").addClass("invisible").removeClass("visible"); // 图形文本框置空,关闭错误提示
}

/**
 * 手机号有效的反馈
 */
function validPhoneFeedback() {
    $phone.parent().find(".normal-tips").show(); // 打开正常提示
    $phone.parent().find(".error-tips").hide(); // 关闭错误提示
    var tempPhone = $phone.val(); // 获取用户输入的手机号
    tempPhone = tempPhone.substr(0,3) + " " + tempPhone.substr(3,4) + " " + tempPhone.substr(7,4); // 格式化手机号输出
    $phoneNumModule.find(".phoneNum").html(tempPhone); // 给手机号发短信模块的手机号填充值
    $nextBtn.attr("disabled", false); // 启用下一步
    $rightNotice.show();
    $errorNotice.hide();
    setTimeout(function() {
        $rightNotice.hide("slow");
    },3000)
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
function validImageVerifyCodeFeedback() {
    $imageVerifyCode.parent().find(".error-tips").hide(); // 关闭错误提示
    $imageVerifyCode.parent().find(".normal-tips").hide(); // 关闭正常提示
    $sendMsgBtn.attr("disabled", false); // 启用发送短信按钮
    $msgVerifyCode.attr("disabled", false); // 启用短信文本框
}

/**
 * 图形验证码输入错误的反馈
 */
function invalidImageVerifyCodeFeedback() {
    $imageVerifyCode.parent().find(".error-tips").show(); // 打开错误提示
    $imageVerifyCode.parent().find(".normal-tips").hide(); // 关闭正常提示
    $sendMsgBtn.attr("disabled", true); // 禁用发送短信按钮
    $msgVerifyCode.val("").attr("disabled", true); // 禁用短信文本框
}

/**
 * 短信验证码输入正确的反馈
 */
function validSMSVerifyCodeFeedback() {
    $("#registerBtn").attr("disabled", false).Shake(2, 10); // 启用注册按钮
    $msgVerifyCode.parent().find(".error-tips").hide(); // 关闭错误提示
    $msgVerifyCode.parent().find(".normal-tips").hide(); // 打开正常提示
}

/**
 * 短信验证码输入错误的反馈
 */
function invalidSMSVerifyCodeFeedback() {
    $("#registerBtn").attr("disabled", true); // 禁用注册按钮
    $msgVerifyCode.parent().find(".error-tips").show(); // 打开错误提示
    $msgVerifyCode.parent().find(".normal-tips").hide(); // 关闭正常提示
}

/**
 * 抖动特效
 * @param shakeNum 抖动次数
 * @param shakeDistance
 * @returns {jQuery.fn.Shake}
 * @constructor
 */
jQuery.fn.Shake = function (shakeNum , shakeDistance) {
    this.each(function () {
        var jSelf = $(this);
        jSelf.css({ position: 'relative' });
        for (var x = 1; x <= shakeNum; x++) {
            jSelf.animate({ left: (-shakeDistance) }, 50)
                .animate({ left: shakeDistance }, 50)
                .animate({ left: 0 }, 50);
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
        countToZeroTime --;
        setTimeout(function() {
            countdown(obj)
        },1000)
    }
}

function checkPhone() {
    var pattern = /^1[34578]\d{9}$/;
    // 判断手机号是否有效
    if (!pattern.test($phone.val())) {
        // 手机号无效
        console.log("手机号无效");
        invalidPhoneFeedback();
        return false;
    } else {
        // 手机号有效
        // TEMP
        console.log("手机号有效");
        // todo : ajax 验证手机号是否已注册
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
                    $errorNotice.show();
                    $rightNotice.hide();
                }
            }, "json");
    }
}

function checkSMSCode() {
    if ($msgVerifyCode.val() === "" || $msgVerifyCode.val().length !== 4) {
        invalidSMSVerifyCodeFeedback();
    } else {
        // 发送短信验证码
        $.post("/checkSMS.do",
            {
                code: $msgVerifyCode.val()
            },
            function (result) {
                console.log(result);
                var flag = result.flag;
                if (flag === true) {
                    validSMSVerifyCodeFeedback();
                } else {
                    invalidSMSVerifyCodeFeedback()
                }
            }, "json");
    }
}

function checkImageCode() {
    // 判断验证码输入是否合法
    if ($imageVerifyCode.val() === '' || $imageVerifyCode.val().length !== 4) {
        // 验证码不合法
        console.log("验证码不合法");
        invalidImageVerifyCodeFeedback();
    } else {
        console.log("验证码ajax");
        // 验证码合法
        // 校验验证码
        $.post("/checkImageVerifyCode.do",
            {code: $imageVerifyCode.val()},
            function (result) {
                var flag = result.flag;
                if (flag === true) { // 验证码输入正确
                    validImageVerifyCodeFeedback();
                } else { // 验证码输入错误
                    console.log("验证码不合法");
                    invalidImageVerifyCodeFeedback();
                }
            }, "json");
    }
}

$(function () {
    // 手机号文本框
    $phone.blur(function () {
        checkPhone();
    }).focus(function () {
        $errorNotice.hide();
        $rightNotice.hide();
    });

    $nextBtn.click(function () {
        $(this).hide();
        $rightNotice.hide();
        $errorNotice.hide();
        $phoneInputModule.hide();
        $imageVerifyModule.show();
        $phoneNumModule.show();
        $msgVerifyModule.show();
        $submitModule.show();
    });

    // 图片验证码文本框
    $imageVerifyCode.blur(function () {
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
                console.log(result);
            }, "json");
    });

    // 短信验证码文本框
    $msgVerifyCode.blur(function () {
        checkSMSCode();
    }).focus(function () {
        checkImageCode();
    })
});