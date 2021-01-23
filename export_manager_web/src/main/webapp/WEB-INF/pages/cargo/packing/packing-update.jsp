    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>AdminLTE2 Edit packing list</title>
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
        <h1>Edit packing list</h1>
    </section>
    <!-- 内容头部 /-->
    <form id="editForm" class="form" action="${ctx}/cargo/packing/edit.do" method="post">
    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">
            <div class="panel-heading">Edit packing list</div>

                <input type="hidden" name="id" value="${packingList.id}">
            <input type="hidden" name="exportIds" value="${packingList.exportIds}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-2 title">Seller</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="Seller" name="seller" value="${packingList.seller}">
                    </div>

                    <div class="col-md-2 title">Buyer</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="Buyer" name="buyer" value="${packingList.buyer}">
                    </div>
                    <div class="col-md-2 title">Receipt Number</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" name="invoiceNo" disabled="disabled" value="${packingList.invoiceNo}">
                    </div>

                    <div class="col-md-2 title">Invoice Date</div>
                    <div class="col-md-4 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="Invoice date"  name="invoiceDate" class="form-control pull-right"
                                   value="<fmt:formatDate value="${packingList.invoiceDate}" pattern="yyyy-MM-dd"/>" id="signingDate">
                        </div>
                    </div>

                    <div class="col-md-2 title">Mark</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="Mark" name="marks" value="${packingList.marks}">
                    </div>

                    <div class="col-md-2 title">descriptions</div>
                    <div class="col-md-4 data">
                        <input type="text" class="form-control" placeholder="descriptions" name="descriptions" value="${packingList.descriptions}">
                    </div>

                </div>

        </div>
        <!--订单信息/-->


        <!--工具栏/-->

    </section>
    <!-- 正文区域 /-->

    <%--报运单列表--%>
    <section class="content">

        <!-- .box-body -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">Packing List</h3>
            </div>

            <div class="box-body">

                <!-- 数据表格 -->
                <div class="table-box">

                    <!--工具栏-->

<%--                    <div class="box-tools pull-right">--%>
<%--                        <div class="has-feedback">--%>
<%--                            <input type="text" class="form-control input-sm" placeholder="Search">--%>
<%--                            <span class="glyphicon glyphicon-search form-control-feedback"></span>--%>
<%--                        </div>--%>
<%--                    </div>--%>
                    <!--工具栏/-->

                    <!--数据列表-->
                    <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                        <thead>
                        <tr>
                            <td><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                            <th class="sorting">报运号</th>
                            <th class="sorting">货物/附件</th>
                            <th class="sorting">信用证号</th>
                            <th class="sorting">收货地址</th>
                            <th class="sorting">装运港</th>
                            <th class="sorting">目的港</th>
                            <th class="sorting">运输方式</th>
                            <th class="sorting">价格条件</th>
                            <th class="sorting">状态</th>
                        </tr>
                        </thead>
                        <tbody>
                        <form id="editForm1" action="/cargo/packingList/edit.do" method="post">
                            <c:forEach items="${page.list}" var="o" varStatus="status">
                                <c:set var="flag" value="0"></c:set>
                                <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                                    <c:forEach items="${exports}" var="e" varStatus="eStatus">
                                        <c:if test="${e.id == o.id}">
                                            <c:set var="flag" value="1"></c:set>
                                        </c:if>
                                    </c:forEach>
                                    <c:if test="${flag==1}">
                                        <td><input type="checkbox" checked="checked" id="${o.id}" name="afterExportIds" value="${o.id}"/></td>
                                    </c:if>
                                    <c:if test="${flag==0}">
                                        <td><input type="checkbox" id="${o.id}" name="afterExportIds" value="${o.id}"/></td>
                                    </c:if>
                                    <td>${o.id}</td>
                                    <td align="center">
                                            ${o.proNum}/${o.extNum}
                                    </td>
                                    <td>${o.lcno}</td>
                                    <td>${o.consignee}</td>
                                    <td>${o.shipmentPort}</td>
                                    <td>${o.destinationPort}</td>
                                    <td>${o.transportMode}</td>
                                    <td>${o.priceCondition}</td>
                                    <td>
                                        <c:if test="${o.state==0}">草稿</c:if>
                                        <c:if test="${o.state==1}"><font color="green">Reported</font></c:if>
                                        <c:if test="${o.state==2}"><font color="red">Packing</font></c:if>
                                        <c:if test="${o.state==3}"><font color="red">Packed</font></c:if>
                                        <c:if test="${o.state==4}"><font color="red">Finance&Invoice</font></c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </form>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- /.box-body -->

            <!-- .box-footer-->
            <div class="box-footer">
                <jsp:include page="../../common/page.jsp">
                    <jsp:param value="cargo/packing/list.do" name="pageUrl"/>
                </jsp:include>
            </div>
            <!-- /.box-footer-->

        </div>

        <!--工具栏-->
        <div class="box-tools text-center">
            <button type="button" onclick='document.getElementById("editForm").submit(),document.getElementById("editForm1").submit()' class="btn bg-maroon">Save</button>
            <button type="button" class="btn bg-default" onclick="history.back(-1);">Back</button>
        </div>
    </section>
    </form>
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