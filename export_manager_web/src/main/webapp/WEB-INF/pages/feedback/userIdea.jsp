<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Personal Feedback</title>
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

        #titleFont {
            /*font-family: "黑体";*/
            font-size: 25px;
            font-weight: bolder;

        }

        #br {
            height: 3px;
            background-color: #5a595f;
        }

        #bodyAndFoot1 {
            border: 2px solid #5e5e5e;
            border-radius: 20px;
            /*高度*/
            height: 180px;
            margin: 3px;
            padding: 22px;
        }

        #bodyAndFoot2 {
            border: 2px solid #5e5e5e;
            border-radius: 20px;
            /*高度*/
            height: 400px;
            margin: 3px;
            padding: 22px;

        }

        .bodyFont {
            font-size: 20px;
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

                    <!--上-->
                    <div>
                        <div id="titleFont">
                            <span class="glyphicon glyphicon-envelope"></span> Personal Feedback
                        </div>
                        <div id="br">
                        </div>
                    </div>

                    <!--中-->
                    <div id="bodyAndFoot1">
                        <div class="alert alert-success" role="alert">
                            Hello!

                            If you have any ideas, please give me your feedback.
                        </div>
                    </div>

                    <!--下-->
                    <div id="bodyAndFoot2">
        <span class="bodyFont">
            <span class="glyphicon glyphicon-pencil"></span>反馈内容
        </span>
                        <div>
                            <textarea id="inputText" name = "idea"  class="form-control" rows="12" placeholder="请输入您的宝贵意见"></textarea>
                        </div>

                        <br>
                        <br>
                        <button type="button" class="btn btn-danger btn-lg" onclick="buttonSubmit()">
                            <span class="glyphicon glyphicon-hand-right"></span>
                            submit
                            <span class="glyphicon glyphicon-hand-left"></span>
                        </button>

                    </div>

                </div>

            </div>
        </div>
    </section>
    <!-- 正文区域 /-->
</div>

</body>
<script>

    function buttonSubmit() {
        var inputText = document.getElementById("inputText");
        // alert(inputText.val());
        alert(inputText.value);

        if (inputText.value == null || inputText.value == "") {
            alert("please enter your feedback!!");
        } else {
            location.href = "${pageContext.request.contextPath}/feedback/update1.do?idea=" + inputText.value;
        }
    }

</script>
</html>