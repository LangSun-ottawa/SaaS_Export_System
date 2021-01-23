<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>user message</title>
    <link rel="stylesheet" href="../../../plugins/bootstrap/css/bootstrap.css">
    <script src="../../../plugins/jQuery/jquery-3.3.1.js"></script>
    <script src="../../../plugins/bootstrap/js/bootstrap.js"></script>
    <style>
        #main {
            margin: 30px 30px;
            /*border: 10px solid black;*/
            height: 600px;
            padding: 3px;
        }
    </style>
</head>
<body>

<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <!-- 正文区域 -->
    <section class="content">
        <div class="box box-primary">
            <div class="box-body" style="height: 800px">

                <div id="main">

                    <!--上面布局-->
                    <div style="height: 100px"></div>
                    <div class="col-xs-2"></div>
                    <div class="col-xs-8">
                        <div class="jumbotron">
                            <div class="page-header">
                                <h1>${feedback.userName} <small>give you a message</small></h1>
                            </div>
                            <div class="alert alert-info" role="alert"><h3>${feedback.content} </h3></div>
                            <div class="col-xs-8"></div>
                            <div class="col-xs-4">
                                <p><a class="btn btn-success btn-lg" href="${pageContext.request.contextPath}/feedback/toHome.do" role="button">我已了解</a></p>
                            </div>
                            <hr>
                        </div>
                    </div>
                    <div class="col-xs-2"></div>


                </div>

            </div>
        </div>
    </section>
    <!-- 正文区域 /-->

</div>



</body>
</html>
