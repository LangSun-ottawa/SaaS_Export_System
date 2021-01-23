<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
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
                    <div class="col-xs-1"></div>
                    <div class="col-xs-10">
                        <div class="alert alert-success" role="alert">
                            <div class="page-header">
                                <h1>${user.companyName}
                                    <small> ${user.userName},您好</small>
                                </h1>
                            </div>
                        </div>
                        <div class="alert alert-info" role="alert">
                            <table class="table table-hover ">
                                <tr>
                                    <th><h3>序号</h3></th>
                                    <th><h3>发件人</h3></th>
                                    <th><h3>反馈内容</h3></th>
                                    <th><h3>时间</h3></th>
                                    <th><h3>操作</h3></th>
                                </tr>

                                <% int count = 1;%>
                                <c:forEach items="${list}" var="Opinion">
                                    <tr>
                                        <td><%=count++%>
                                        </td>
                                        <td>${Opinion.userName}</td>
                                        <td>${Opinion.content}</td>
                                        <td>${Opinion.date}</td>
                                        <td>
                                            <button class="btn btn-sm btn-danger" onclick="deleteIdea('${Opinion.feedbackId}')">
                                                删除
                                            </button>
                                            <input type="checkbox" value="${Opinion.feedbackId}" class="ideas">
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div class="col-xs-8"></div>
                            <div class="col-xs-2">
                                <p><a class="btn btn-success btn-lg"
                                      href="${pageContext.request.contextPath}/feedback/toHome.do"
                                      role="button">我已了解</a></p>
                            </div>
                            <div class="col-xs-2">
                                <button onclick="deleteIdeas()">批量删除</button>
                            </div>

                            <br>
                            <br>
                            <br>
                        </div>
                    </div>

                </div>

            </div>
        </div>
    </section>
    <!-- 正文区域 /-->

</div>


</body>
<script>
    /*获取多个id*/
    function deleteIdeas() {
        var $ids = document.getElementsByClassName("ideas");
        var ids = "";
        for (var i = 0; i < $ids.length; i++) {
            if ($ids[i].checked) {
                ids += $ids[i].value + ","
            }
        }

        ids = ids.substring(0, ids.length - 1);
        if (confirm("delete these feedback？"))
            location.href = "${pageContext.request.contextPath}/feedback/deleteIdeas.do?ids=" + ids;
    }


    /*获取单个id*/
    function deleteIdea(id) {
        if (confirm("delete this feedback？")) {
            location.href = "${pageContext.request.contextPath}/feedback/deleteIdea.do?id=" + id;
        }

    }

</script>

</html>
