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
        <h1 align="center">
            个人信息
        </h1>

    </section>

    <!-- 正文区域 -->
    <section class="content">

        <!--订单信息-->
        <div class="panel panel-default">


            <form id="editForm" action="/system/user/personalUpdate.do" method="post" enctype="multipart/form-data">
                <input type="hidden" id="id" name="id" value="${personal.id}">
                <input type="hidden" id="deptName" name="deptName" value="${personal.deptName}">
                <div class="row data-type" style="margin: 0px">
                    <div class="col-md-4 title">用户名称</div>
                    <div class="col-md-8 data">
                        <input type="text" class="form-control" placeholder="用户名称" name="userName" value="${personal.userName}">
                    </div>

                    <div class="col-md-4 title">照片</div>
                    <div class="col-md-8 data">
                        <input type="file" class="form-control" placeholder="请选择" name="photo" >
                    </div>
                    <c:if test="${not empty personal.img && !(personal.img eq null) }">
                        <div class="col-md-4 title">照片</div>
                        <div class="col-md-8 data">
                            <c:if test="${not empty personal.img && !(personal.img eq null) }">
                                <img src="${personal.img}" width="300px" height="180px">
                                <input type="hidden" name="img" value="${personal.img}">
                            </c:if>

                        </div>
                    </c:if>
                    <c:if test="${empty personal.img || (personal.img eq null) }">
                        <div class="col-md-4 title">照片</div>
                        <div class="col-md-8 data">
                            还没有照片
                        </div>
                    </c:if>

                    <div class="col-md-4 title">邮箱</div>
                    <div class="col-md-8 data">
                        <input type="text" class="form-control" placeholder="邮箱" name="email" value="${personal.email}">
                    </div>



                    <div class="col-md-4 title">password</div>
                    <div class="col-md-8 data">
                        <input type="text" class="form-control" placeholder="Change Password" name="password" value="">
                    </div>


                    <div class="col-md-4 title">性别</div>
                    <div class="col-md-8 data">
                        <div class="form-group form-inline">
                            <div class="radio"><label><input type="radio" ${personal.gender==0?'checked':''} name="gender" value="0">男</label></div>
                            <div class="radio"><label><input type="radio" ${personal.gender==1?'checked':''} name="gender" value="1">女</label></div>
                        </div>
                    </div>


                    <div class="col-md-4 title">电话</div>
                    <div class="col-md-8 data">
                        <input type="text" class="form-control" placeholder="电话" name="telephone" value="${personal.telephone}">
                    </div>

                    <div class="col-md-4 title">出生年月</div>
                    <div class="col-md-8 data">
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" placeholder="出生年月" class="form-control pull-right" name="birthday"
                                   value="${personal.birthday}" id="datepicker1">
                        </div>
                    </div>

                    <div class="col-md-4 title">说明</div>
                    <div class="col-md-8 data">
                        <input type="text" class="form-control" placeholder="说明" name="remark" value="${personal.remark}">
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
    $('#datepicker').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
    $('#datepicker1').datepicker({
        autoclose: true,
        format: 'yyyy-mm-dd'
    });
</script>
</html>