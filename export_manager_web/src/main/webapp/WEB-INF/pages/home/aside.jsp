<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../base.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
<%--                <img src="../img/user2-160x160.jpg" class="img-circle" alt="User Image">--%>
                <c:if test="${not empty sessionScope.loginUser.img && !(sessionScope.loginUser.img eq null) }">
                        <img src="${sessionScope.loginUser.img}" class="img-circle" alt="User Image">
                </c:if>

                <c:if test="${empty sessionScope.loginUser.img || (sessionScope.loginUser.img eq null) }">
                        <img src="../../../img/avatar5.png" class="img-circle" alt="User Image">
                </c:if>
            </div>
            <div class="pull-left info">
                <p> ${sessionScope.loginUser.userName}-<a href="#">${sessionScope.loginUser.companyName}</a></p>
            </div>
        </div>

        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单</li>
                <c:forEach items="${sessionScope.modules}" var="item">
                    <%--`ctype` decimal(11,0)'   0 主菜单/1 左侧菜单/2按钮/3 链接/4 状态',                       --%>
                    <c:if test="${item.ctype==0}">
                        <li class="treeview">
                            <a href="#">
                                <i class="fa fa-cube"></i> <span>${item.nameEn}</span>
                                <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                            </a>
                            <ul class="treeview-menu">
                                <c:forEach items="${sessionScope.modules}" var="item2">
                                    <c:if test="${item2.ctype==1 && item2.parentId == item.id}">
                                        <li id="${item2.id}">
                                            <a onclick="setSidebarActive(this)" href="${ctx}/${item2.curl}" target="iframe">
                                                <i class="fa fa-circle-o"></i>${item2.nameEn}
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                </c:forEach>




       </ul>

   </section>
               <!-- /.sidebar -->
</aside>
