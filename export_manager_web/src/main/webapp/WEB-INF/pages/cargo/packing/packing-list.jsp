<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ include file="../../base.jsp"%>
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
    <script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
</head>
<script>
    function deleteById() {
        var id = getCheckId();
        $.get("/cargo/packing/findById.do", "id="+id, function (resp) {
            if (resp.state == 0) {
                if (id) {
                    if (confirm("你确认要删除此条记录吗？")) {
                        location.href = "${ctx}/cargo/packing/delete.do?id=" + id;
                    }
                } else {
                    alert("请勾选待处理的记录，且每次只能勾选一个")
                }
            } else {
                alert("装箱单状态不为草稿状态,无法删除");

            }
        });

    }

    function submit() {
        var id = getCheckId();
        $.get("/cargo/packing/findById.do", "id=" + id, function (resp) {
            if (resp.state == 0) {
                if(id) {
                    location.href="${ctx}/cargo/packing/submit.do?id="+id;
                }else{
                    alert("请勾选待处理的记录，且每次只能勾选一个")
                }
            } else {
                alert("装箱单状态不为草稿状态,无法提交");
            }
        });
    }

    function finance() {
        var id = getCheckId();
        $.get("/cargo/packing/findById.do", "id=" + id, function (resp) {
            if (resp.state >= 1) {
                if(id) {
                    location.href="${ctx}/cargo/packing/finance.do?id="+id;
                }else{
                    alert("请勾选待处理的记录，且每次只能勾选一个")
                }
            } else {
                alert("装箱单状态不为submit状态,无法提交finance");

            }
        });
    }

    function cancel() {
        var id = getCheckId();
        $.get("/cargo/packing/findById.do", "id=" + id, function (resp) {
            if (resp.state == 1) {
                if(id) {
                    location.href="${ctx}/cargo/packing/cancel.do?id="+id;
                }else{
                    alert("请勾选待处理的记录，且每次只能勾选一个")
                }
            } else {
                alert("装箱单状态不为提交状态,无法取消");

            }
        });

    }

    function view() {
        var id = getCheckId();
        if(id) {
            location.href="${ctx}/cargo/packing/toView.do?id="+id;
        }else{
            alert("请勾选待处理的记录，且每次只能勾选一个")
        }
    }


</script>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
<section class="content-header">
    <h1>
        货运管理
        <small>装箱单</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="../../../../home.jsp"><i class="fa fa-dashboard"></i> 首页</a></li>
    </ol>
</section>
<!-- 内容头部 /-->

<!-- 正文区域 -->
<section class="content">

    <!-- .box-body -->
    <div class="box box-primary">
        <div class="box-header with-border">
            <h3 class="box-title">装箱单列表</h3>
        </div>

        <div class="box-body">

            <!-- 数据表格 -->
            <div class="table-box">

                <!--工具栏-->
                <div class="pull-left">
                    <div class="form-group form-inline">
                        <div class="btn-group">
                            <button type="button" class="btn btn-default" title="新建" onclick='location.href="${ctx}/cargo/packing/toAdd.do"'><i class="fa fa-file-o"></i> 新建</button>
                            <button type="button" class="btn btn-default" title="查看" onclick='view()'><i class="fa  fa-eye-slash"></i> 查看</button>
                            <button type="button" class="btn btn-default" title="删除" onclick='deleteById()'><i class="fa fa-trash-o"></i> 删除</button>
                            <button type="button" class="btn btn-default" title="刷新" onclick="window.location.reload();"><i class="fa fa-refresh"></i> 刷新</button>
                            <button type="button" class="btn btn-default" title="提交" onclick="submit()"><i class="fa fa-retweet"></i> 提交</button>
                            <button type="button" class="btn btn-default" title="Finance" onclick="finance()"><i class="fa fa-retweet"></i> Finance&Invoice </button>
                            <button type="button" class="btn btn-default" title="取消" onclick="cancel()"><i class="fa fa-remove"></i> 取消</button>
                        </div>
                    </div>
                </div>
                <div class="box-tools pull-right">
                    <div class="has-feedback">
                        <input type="text" class="form-control input-sm" placeholder="搜索">
                        <span class="glyphicon glyphicon-search form-control-feedback"></span>
                    </div>
                </div>
                <!--工具栏/-->

                <!--数据列表-->
                <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                    <thead>
                    <tr>
                        <th class="" style="padding-right:0px;">

                        </th>
                        <th class="sorting">装箱编号</th>
                        <th class="sorting">卖方</th>
                        <th class="sorting">买方</th>
                        <th class="sorting">发票号</th>
                        <th class="sorting">唛头</th>
                        <th class="sorting">描述</th>
                        <th class="sorting">状态</th>
                        <th class="sorting">创建人</th>
                        <th class="sorting">创建部门</th>
                        <th class="sorting">创建日期</th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="o" varStatus="status">
                        <tr>
                            <td><input type="checkbox" name="id" value="${o.id}"/></td>
                            <td>${o.id}</td>
                            <td>${o.seller}</td>
                            <td>${o.buyer}</td>
                            <td>
                               ${o.invoiceNo}
                            </td>
                            <td>${o.marks}</td>
                            <td>${o.descriptions}</td>
                            <td><c:if test="${o.state==0}">draft</c:if>
                                <c:if test="${o.state==1}"><font color="green">reported</font></c:if>
                                <c:if test="${o.state>=2}"><font color="red">finance&invoice</font></c:if>
                            </td>
                            <td>${o.createBy}</td>
                            <td>${o.createDept}</td>
                            <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd"/></td>
                            <td>
                                <%--<a href="${ctx }/cargo/contract/toView.do?id=${o.id}">[查看详情]</a>--%>
                                <a href="${ctx }/cargo/packing/toUpdate.do?id=${o.id}">[编辑]</a>
<%--                                    <c:if test="${o.state>=2}">--%>
<%--                                        <a href="${ctx }/cargo/packing/download.do?id=${o.id}">[下载]</a>--%>
<%--                                    </c:if>--%>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <!--
                <tfoot>
                <tr>
                <th>Rendering engine</th>
                <th>Browser</th>
                <th>Platform(s)</th>
                <th>Engine version</th>
                <th>CSS grade</th>
                </tr>
                </tfoot>-->
                </table>
                <!--数据列表/-->

                <!--工具栏/-->

            </div>
            <!-- 数据表格 /-->


        </div>
        <!-- /.box-body -->

        <!-- .box-footer-->
        <div class="box-footer">
            <jsp:include page="../../common/page.jsp">
                <jsp:param value="/cargo/contract/list.do" name="pageUrl"/>
            </jsp:include>
        </div>
        <!-- /.box-footer-->


    </div>

</section>
</div>
</body>

</html>