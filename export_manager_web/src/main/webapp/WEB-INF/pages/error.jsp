<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ include file="base.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Error - AdminLTE2_error</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- Content Header (Page header) -->
    <section class="content-header">
        <h1>
            500 error page
        </h1>

        <ol class="breadcrumb">
            <li><a href="${ctx}/feedback/toMain.do"><i class="fa fa-dashboard"></i>index page</a></li>
            <li class="active">500 error</li>
        </ol>

    </section>

    <!-- Main content -->
    <section class="content">
        <div class="error-page">
            <h2 class="headline text-red"> 500</h2>

            <div class="error-content">
                <h3><i class="fa fa-warning text-red"></i> Oops! There is an error in the page.</h3>

                <p>
                    ${errorMsg}<a href="${ctx}/feedback/toMain.do">Back to the home page</a> or
                </p>
                <p><a href="#" onclick="showDetail();">Click here to view the specific error message</a>,
                    <br/>
                </p>
            </div>
            <!-- /.error-content -->
        </div>
        <div class="box-body" id="detail_system_error_msg" style="display:none;text-align:left;padding-bottom:100px;">
            <br>
            <p>
            <%
                exception.printStackTrace(new java.io.PrintWriter(out));
            %>
            </p>
        </div>
        <script>
            function showDetail()
            {
                var elm = document.getElementById('detail_system_error_msg');
                if(elm.style.display == '') {
                    elm.style.display = 'none';
                }else {
                    elm.style.display = '';
                }
            }
        </script>
        <!-- /.error-page -->
    </section>
</div>
<!-- 内容区域 /-->
</body>

</html>