<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/jsp/common/head.jsp"%>
    <div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>密码修改页面</span>
        </div>
        <div class="providerAdd">
            <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath }/jsp/user.do">
                <input type="hidden" name="method" value="savePwd"><%--提交给后端的时候对应方法处理--%>
                <!--div的class 为error是验证错误，ok是验证成功-->
                <div class="info">${message}</div>
                <div class="">
                    <label for="oldPassword">旧密码：</label>
                    <input type="password" name="oldPassword" id="oldPassword" value="">
                    <span style="color: red; "></span>
                </div>
                <div>
                    <label for="newPassword">新密码：</label>
                    <input type="password" name="newPassword" id="newPassword" value="">
                    <span style="color: red; "></span>
                </div>
                <div>
                    <label for="renewPassword">确认新密码：</label>
                    <input type="password" name="renewPassword" id="renewPassword" value="">
                    <span style="color: red; "></span>
                </div>
                <div class="providerAddBtn">
                    <%--<a href="#">保存</a>--%>
                    <input type="button" name="save" id="save" value="保存" class="input-button">
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/pwdmodify.js"></script>