<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 内容头部 -->
    <section class="content-header">
        <h1>
            货运管理
            <small>财务管理</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">货运管理</a></li>
            <li class="active">货物添加及列表</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">当前未付款财务报运单</div>
            <%--
                文件上传三要素
                1.表单必须post提交
                2.必须是多部分表单提交格式  enctype="multipart/form-data"
                3.必须有type=file组件
                使request对象失效 后台拿不到数据 必须需要配置文件上传解析
            --%>
            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList1" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <th class="" style="padding-right:0px;">

                            </th>
                            <th class="sorting">制单人</th>
                            <th class="sorting">制单日期</th>
                            <th class="sorting">总金额</th>
                            <th class="sorting">状态</th>
<%--                            <th class="sorting">操作</th>--%>
                        </tr>
                        </thead>
                        <tbody class="tableBody" >
                        ${links}
                                <td><input type="checkbox" name="id" value="${finance.financeId}"/></td>
                                <td>${finance.createBy}</td>
                                <td>${finance.createTime}</td>
                                <td>${finance.invoiceValue}</td>
                                <td><c:if test="${finance.state==0}"><font color="green">unpaid</font></c:if></td>
                                <td><c:if test="${finance.state==1}"><font color="green">paid</font></c:if></td>
<%--                                <td>--%>
<%--                                    <c:if test="${finance.state==0}">--%>
<%--                                        <a href="/cargo/finance/factoryPrintExcel.do?financeId=${finance.financeId}">[下载]</a>--%>
<%--                                    </c:if>--%>
<%--                                </td>--%>
                        </tbody>
                    </table>
                    <!--数据列表/-->
                    <!--工具栏/-->
                </div>
                <!-- 数据表格 /-->
            </div>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

    <section class="content">

        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">ContractIds</h3>
            </div>
            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="ContractList" class="table table-bordered table-striped table-hover dataTable">
                        <tbody class="tableBody" >
                        <c:forEach items="${contractId}" varStatus="status">
                            <tr>
                                <td>${contractId[status.index]}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

                        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">厂家列表</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">
                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">

                        <thead>
                        <tr>
                            <td class="tableHeader">序号</td>
                            <td class="tableHeader">厂家名称</td>
                            <td class="tableHeader">厂家全名</td>
                            <td class="tableHeader">联系人</td>
                            <td class="tableHeader">电话</td>
                            <td class="tableHeader">地址</td>
                        </tr>
                        </thead>
                        <tbody class="tableBody" >
                        <c:forEach items="${factories}" var="factory" varStatus="status">
                            <tr>
                                <td>${status.index+1}</td>
                                <td>${factory.factoryName}</td>
                                <td>${factory.fullName}</td>
                                <td>${factory.contacts}</td>
                                <td>${factory.phone}</td>
                                <td>${factory.address}</td>
                            </tr>
                        </c:forEach>


                        </tbody>
                    </table>
                    <!--数据列表/-->
                    <!--工具栏/-->
                </div>
                <!-- 数据表格 /-->
            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
<%--            <div class="box-footer">--%>
<%--                <jsp:include page="../common/page.jsp">--%>
<%--                    <jsp:param value="/cargo/contractProduct/list.do?contractId=${contractId}" name="pageUrl"/>--%>
<%--                </jsp:include>--%>
<%--            </div>--%>
            <!-- /.box-footer-->
        </div>

    </section>

</div>
<!-- 内容区域 /-->
</body>

</html>