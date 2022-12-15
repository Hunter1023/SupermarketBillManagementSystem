var oldPassword = null;
var newPassword = null;
var renewPassword = null;
var saveBtn = null;

$(function () {
    oldPassword = $("#oldPassword");
    newPassword = $("#newPassword");
    renewPassword = $("#reNewPassword");
    saveBtn = $("#save");

    oldPassword.next().html("*");
    newPassword.next().html("*");
    renewPassword.next().html("*");

    oldPassword.on("blur", function () {
        $.ajax({
            type: "GET",
            url: path + "/jsp/user.do",
            // Ajax传递的参数
            data: {method: "verifyOldPwd", oldPassword: oldPassword.val()},
            // 目前主流开发，都是用json实现前后端交互
            dataType: "json",
            success: function (data) {
                if (data.result === "true") {//旧密码正确
                    validateTip(oldPassword.next(), {"color": "green"}, imgYes, true);
                } else if (data.result === "false") {//旧密码输入不正确
                    validateTip(oldPassword.next(), {"color": "red"}, imgNo + " 原密码输入不正确", false);
                } else if (data.result === "sessionError") {//当前用户session过期，请重新登录
                    validateTip(oldPassword.next(), {"color": "red"}, imgNo + " 当前用户session过期，请重新登录", false);
                } else if (data.result === "error") {//旧密码输入为空
                    validateTip(oldPassword.next(), {"color": "red"}, imgNo + " 请输入旧密码", false);
                }
            },
            error: function (data) {
                //请求出错
                validateTip(oldPassword.next(), {"color": "red"}, imgNo + " 请求错误", false);
            }
        });


    }).on("focus", function () {
        validateTip(oldPassword.next(), {"color": "#666666"}, "* 请输入原密码", false);
    });

    newPassword.on("focus", function () {
        validateTip(newPassword.next(), {"color": "#666666"}, "* 密码长度必须是大于6小于20", false);
    }).on("blur", function () {
        if (newPassword.val() != null && newPassword.val().length >= 6
            && newPassword.val().length < 20) {
            validateTip(newPassword.next(), {"color": "green"}, imgYes, true);
        } else {
            validateTip(newPassword.next(), {"color": "red"}, imgNo + " 密码输入不符合规范，请重新输入", false);
        }
    });


    renewPassword.on("focus", function () {
        validateTip(renewPassword.next(), {"color": "#666666"}, "* 请输入与上面一致的密码", false);
    }).on("blur", function () {
        if (renewPassword.val() != null && renewPassword.val().length >= 6
            && renewPassword.val().length < 20 && newPassword.val() === renewPassword.val()) {
            validateTip(renewPassword.next(), {"color": "green"}, imgYes, true);
        } else {
            validateTip(renewPassword.next(), {"color": "red"}, imgNo + " 两次密码输入不一致，请重新输入", false);
        }
    });


    saveBtn.on("click", function () {
        oldPassword.blur();
        newPassword.blur();
        renewPassword.blur();
        if (oldPassword.attr("validateStatus") === "true"
            && newPassword.attr("validateStatus") === "true"
            && renewPassword.attr("validateStatus") === "true") {
            if (confirm("确定要修改密码？")) {
                $("#userForm").submit();
            }
        }

    });
});