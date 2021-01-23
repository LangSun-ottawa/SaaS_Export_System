<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<header class="main-header">
    <a href="/feedback/toMain.do" class="logo">
        <span class="logo-mini"><img src="../img/logo.png"></span>
        <span class="logo-lg">
                    <img src="../img/export.png">
                    <i> SaaS外贸进出口平台</i>
                </span>
    </a>

    <nav class="navbar navbar-static-top">
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <li class="dropdown messages-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-envelope-o"></i>
                        <span class="label label-success numberUnread"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li class="header">You have <span class="numberUnread"></span> unread message</li>
                        <li>
                            <ul class="menu" id="nbhuaile">

                            </ul>
                        </li>
                        <li class="footer" id="footer"></li>
                    </ul>
                </li>

                <script>
                    /*========================加载消息列表=========================*/
                    function refresh() {
                        var url = "${pageContext.request.contextPath}/feedback/unread.do";
                        $.post(url, function (data) {
                            var count = data.length;
                            $(".numberUnread").text(count);
                            var $nbhuaile = $("#nbhuaile");
                            $nbhuaile.empty();
                            for (var i = 0; i < count; i++) {
                                $nbhuaile.append("" +
                                    "<li><a onclick=\"refresh2(1000)\" target=\"iframe\" href=\"${pageContext.request.contextPath}/feedback/messageChecked.do?feedbackId=" + data[i].feedbackId + "\">\n" +
                                    "<i class=\"fa fa-users text-aqua\"></i>" + data[i].content +
                                    "</a></li>")
                            }
                            $("#footer").html("<a onclick=\"refresh2(1000)\" target=\"iframe\" href=\"${pageContext.request.contextPath}/feedback/checkHistory.do\">History</a>")
                        }, "json")
                    }

                    function refresh2(time) {
                        setTimeout('refresh()', time);
                    }

                    $(function () {
                        refresh();
                    })
                    /*========================加载消息列表=========================*/

                </script>

                <!-- Notifications: style can be found in dropdown.less -->

                <li class="header">
                    <a href="/chat/findAll.do" onclick="setSidebarActive(this)"  target="iframe">
                        <i class="fa fa-flag-o"></i>
                    </a>
                </li>

                <!-- User Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">

                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="${sessionScope.loginUser.img}" class="user-image" alt="No Image">
                        <span class="hidden-xs"> ${sessionScope.loginUser.userName}</span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <img src="${sessionScope.loginUser.img}" class="img-circle" alt="User Image">

                            <p>
                                ${sessionScope.loginUser.userName}
                            </p>
                        </li>

                        <!-- Menu Footer-->
                        <li class="user-footer">
                            <div class="pull-left">
                                <a href="/system/user/personal.do" class="btn btn-default btn-flat">Change psw</a>
                            </div>
                            <div class="pull-right">
                                <a href="/logout.do" class="btn btn-default btn-flat">logout</a>
                            </div>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>
    </nav>
</header>
<!-- 页面头部 /-->

