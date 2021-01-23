<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
            新增厂家
            <small>厂家表单</small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="all-admin-index.html"><i class="fa fa-dashboard"></i> 首页</a></li>
            <li><a href="all-order-manage-list.html">新增厂家</a></li>
            <li class="active">厂家表单</li>
        </ol>
    </section>
    <!-- 内容头部 /-->

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">厂家信息</div>
            <form id="editForm" action="${ctx}/baseinfo/factory/edit.do" method="post">
<%--                <input type="hidden" name="id" value="${coFactory.id}">--%>
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">厂家简称</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="厂家简称" name="factoryName" value="">
                    </div>

                    <div class="col-md-2 title">厂家全称</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="厂家全称" name="fullName" value="">
                    </div>

<%--                    <div class="col-md-2 title">添加日期</div>--%>
<%--                    <div class="col-md-4 data">--%>
<%--                        <div class="input-group date">--%>
<%--                            <div class="input-group-addon">--%>
<%--                                <i class="fa fa-calendar"></i>--%>
<%--                            </div>--%>
<%--                            <input type="text" placeholder="添加日期"  name="createTime" class="form-control pull-right"--%>
<%--                                   value="<fmt:formatDate value="${coFactory.createTime}" pattern="yyyy-MM-dd"/>" id="signingDate">--%>
<%--                        </div>--%>
<%--                    </div>--%>

<%--                    <div class="col-md-2 title">修改日期</div>--%>
<%--                    <div class="col-md-4 data">--%>
<%--                        <div class="input-group date">--%>
<%--                            <div class="input-group-addon">--%>
<%--                                <i class="fa fa-calendar"></i>--%>
<%--                            </div>--%>
<%--                            <input type="text" placeholder="修改日期"  name="updateTime" class="form-control pull-right"--%>
<%--                                   value="<fmt:formatDate value="${coFactory.updateTime}" pattern="yyyy-MM-dd"/>" id="signingDate1">--%>
<%--                        </div>--%>
<%--                    </div>--%>

                    <div class="col-md-2 title">联系人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="联系人" name="contacts" >
                    </div>

                    <div class="col-md-2 title">电话</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="电话" name="phone" >
                    </div>

                    <div class="col-md-2 title">手机</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="手机" name="monile" >
                    </div>

                    <div class="col-md-2 title">地址</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="地址" name="address" >
                    </div>
                    <div class="col-md-2 title">验货人</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="验货人" name="inspector">
                    </div>

                   <%-- <div class="col-md-2 title">船期</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                    <div class="input-group-addon">
                        <i class="fa fa-calendar"></i>
                    </div>
                    <input type="text" placeholder="船期"  name="shipTime" class="form-control pull-right"
                           value="<fmt:formatDate value="${contract.shipTime}" pattern="yyyy-MM-dd"/>" id="shipTime">
                </div>
                </div>--%>

                 <%--   <div class="col-md-2 title">重要程度</div>
                    <div class="col-md-4 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${contract.importNum==3?'checked':''} name="importNum" value="3">★★★</label></div>
                            <div class="radio"><label><input type="radio" ${contract.importNum==2?'checked':''} name="importNum" value="2">★★</label></div>
                            <div class="radio"><label><input type="radio" ${contract.importNum==1?'checked':''} name="importNum" value="1">★</label></div>
                        </div>
                    </div>--%>

                  <%--  <div class="col-md-2 title">交货期限</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="交货期限"  name="deliveryPeriod" class="form-control pull-right"
                                   value="<fmt:formatDate value="${contract.deliveryPeriod}" pattern="yyyy-MM-dd"/>" id="deliveryPeriod">
                        </div>
                    </div>--%>

                   <%-- <div class="col-md-2 title">贸易条款</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="贸易条款" name="tradeTerms" value="${contract.tradeTerms}">
                    </div>

                    <div class="col-md-2 title">打印版式</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="打印版式" name="printStyle" value="${contract.printStyle}">
                    </div>
                    <div class="col-md-2 title">总金额</div>
                    <div class="col-md-4 data">
                        <input type="text" disabled class="form-control" placeholder="0" name="totalAmount" value="${contract.totalAmount}">
                    </div>--%>

                    <div class="col-md-2 title">传真</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="传真" name="fax" >
                    </div>

                    <div class="col-md-2 title rowHeight2x">备注</div>
                    <div class="col-md-10 data rowHeight2x">
                        <textarea class="form-control" rows="3" name="remark"></textarea>
                    </div>
                </div>
            </form>
        </div>
        <!--订单信息/-->

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit()' class="btn bg-maroon">保存</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">返回</button>
        </div>
        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

</div>
<!-- 内容区域 /-->
</body>
<script src="../../plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="../../plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<link rel="stylesheet" href="../../css/style.css">
<script>
    $('#signingDate').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#signingDate1').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#deliveryPeriod').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#shipTime').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>