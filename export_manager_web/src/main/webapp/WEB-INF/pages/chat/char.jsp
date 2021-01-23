<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style type="text/css">
        .talk_con{
            width:600px;
            height:500px;
            border:1px solid #666;
            margin:50px auto 0;
            background:#f9f9f9;
        }
        .online{
            width:500px;
            height:30px;
            border:1px solid #666;
            margin:50px auto 0;
            background:#f9f9f9;
        }
        .talk_show{
            width:580px;
            height:420px;
            border:1px solid #666;
            background:#fff;
            margin:10px auto 0;
            overflow:auto;
        }
        .talk_input{
            width:580px;
            margin:10px auto 0;
        }
        .whotalk{
            width:80px;
            height:30px;
            float:left;
            outline:none;
        }
        .talk_word{
            width:410px;
            height:26px;
            padding:0px;
            float:left;
            margin-left:10px;
            outline:none;
            text-indent:10px;
        }
        .talk_sub{
            width:56px;
            height:30px;
            float:left;
            margin-left:10px;
        }
        .atalk{
            margin:10px;
        }
        .atalk span{
            display:inline-block;
            background:#0181cc;
            border-radius:10px;
            color:#fff;
            padding:5px 10px;
        }
        .atalk1{
            margin:10px;
        }
        .atalk1 span{
            /*display:inline-block;*/
            /*background:#0181cc;*/
            /*border-radius:10px;*/
            /*color:#fff;*/
            font-size: 3px;
            padding:5px 10px;
        }
        .btalk{
            margin:10px;
            text-align:right;
        }
        .btalk span{
            display:inline-block;
            background:#ef8201;
            border-radius:10px;
            color:#fff;
            padding:5px 10px;
        }
        .btalk1{
            margin:10px;
            text-align:right;
        }
        .btalk1 span{
            /*display:inline-block;*/
            /*background:#ef8201;*/
            /*border-radius:10px;*/
            /*color:#fff;*/
            font-size: 3px;
            padding:5px 10px;
        }
    </style>
    <script type="text/javascript">

        var host = window.location.host;
        url = "ws://"+host+"/chat/findAll.do";
        var websocket = null;
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            websocket = new WebSocket(url);
        }
        else {
            alert('Browser Do Not support websocket');
        }

        let NUMBER;
        //连接发生错误的回调方法
        websocket.onerror = function () {
            setMessageInnerHTML("WebSocket连接发生错误");
        };

        //连接成功建立的回调方法
        websocket.onopen = function (event) {
            // alert(event.data);
            // console.log('1'+ event.data);
            setMessageInnerHTML(event.data+"@,@!");
        }

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            NUMBER = event.data;
            setMessageInnerHTML(event.data);
        }

        //连接关闭的回调方法
        websocket.onclose = function (event) {
            // console.log('2'+ event.data);
            setMessageInnerHTML(event.data);
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        }

        // function setOnline(innerHTML) {
        //     var arr = innerHTML.split("@,@!");
        //    document.getElementById('online').innerHTML = innerHTML + '<br/>';
        // }

        //将消息显示在网页上
        function setMessageInnerHTML(innerHTML) {
            arr = innerHTML.split("@,@!");

            msg = "";
            for(i = 3;i < arr.length;i++){
                msg = msg + arr[i];
            }
            // alert("arr[0]"+arr[0]);
            // alert("arr[1]"+arr[1]);
            <%--alert("${sessionScope.get("loginUserId")}")--%>
            <%--alert(msg);--%>
            /**
             * yyyy-MM-dd HH:mm:ss
             */
            var date = new Date();
            // alert("date"+date);
            var seperator1 = "-";
            var seperator2 = ":";
            //外国的月份都是从0开始的，所以+1
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            //1-9月用0补位
            if(month >=1 && month <=9){
                month = "0" + month;
            }
            //1-9日用0补位
            if(strDate >=0 && strDate <= 9){
                strDate = "0" + strDate;
            }
            var min = date.getMinutes()
            if(min>=0 && min<=9){
               min = "0"+ min;
            }
            //获取当前时间 yyyy-MM-dd HH:mm:ss
            currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " " +date.getHours() + seperator2 + date.getMinutes() + seperator2 + date.getSeconds();
            // alert(arr[1]);
            if(arr[2] == "${sessionScope.get("loginUserId")}"){
                // alert("1");
                document.getElementById('words').innerHTML += '<div class="btalk1" id="btalk1"><span>'+arr[1]+"  " + currentdate +
                    '</span><br></div>';
                document.getElementById('words').innerHTML += '<div class="btalk" id="btalk"><span>'+msg+
                    '</span><br></div>';
                return;
            }else if((arr[2] != "${sessionScope.get("loginUserId")}")&&arr[2] != null){
                // alert("2");
                document.getElementById('words').innerHTML += '<div class="atalk1" id="atalk1"><span><span>'+arr[1]+"  " + currentdate +
                    '</span><br></div>';
                document.getElementById('words').innerHTML += '<div class="atalk" id="atalk"><span><span>'+msg+
                    '</span><br></div>';
                return;
            }
            // alert("3");
            // alert(innerHTML);
            document.getElementById('online').innerHTML = innerHTML + '<br/>';
        }

        //关闭WebSocket连接
        function closeWebSocket() {
            websocket.close();
        }


        //发送消息
        function send() {
            var message = document.getElementById('talkwords').value;
            var arr = message.split(" ");
            messagetest = "";
            for(var a = 0; a < arr.length;a++){
                messagetest=messagetest+arr[a];
            }
            if(messagetest == ""){
                // 消息为空时弹窗
                alert("message can't be null");
                return;
            }else{
                message = "${sessionScope.loginUserId}"+"@,@!"+ message;
                // alert("test");
                <%--alert("${sessionScope.loginUserId}");--%>
                <%--alert("${sessionScope.loginUserName}");--%>
                <%--alert("${sessionScope.loginUser.userName}");--%>
                message = "${sessionScope.loginUser.userName}"+"@,@!"+ message;
                message = "${sessionScope.loginUserCompanyId}"+"@,@!"+ message;
                websocket.send(message);
                document.getElementById('talkwords').value="";
            }
            // websocket.send(message);
        }

    </script>
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <div id="online" class="online"></div>
<div class="talk_con">
    <div class="talk_show" id="words">
<%--    <c:forEach items="${page.list}" var="item">--%>
<%--        <c:if test="${item.userId == loginUserId}">--%>
<%--       <div class="btalk1" id="btalk1">--%>
<%--         --%>
<%--&lt;%&ndash;            <span>${loginUsername} ${item.date}</span><br>&ndash;%&gt;--%>
<%--        </div>--%>
<%--            <div class="btalk" id="btalk">--%>
<%--                <span>${item.content}</span>--%>
<%--          </div>--%>
<%--        </c:if>--%>
<%--        <c:if test="${item.userId != loginUserId}">--%>
<%--        <div class="atalk1" id="atalk1">--%>
<%--            <span>${item.userName} ${item.date}</span><br>--%>
<%--        </div>--%>
<%--            <div class="atalk" id="atalk">--%>
<%--                <span>${item.content}--%>
<%--               </span>--%>
<%--</div>--%>
<%--        </c:if>--%>
<%--    </c:forEach>--%>
    </div>
    <div class="talk_input">
        <button class="button" onclick="window.location.href='/chat/history.do'">chat history</button>
<%--        <form id="editForm" action="/chat/toAdd.do" method="post">--%>
            <input type="text" class="talk_word" id="talkwords" name="content">

<%--        </form>--%>
            <button  class="talksub" onclick="send()">发送</button>

    </div>
</div>
</div>
</body>
</html>