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
            width:420px;
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
    <%--    <script type="text/javascript">--%>
    <%--        //--%>
    <%--        window.onload = function(){--%>
    <%--            var Words = document.getElementById("words");--%>
    <%--            var Who = document.getElementById("who");--%>
    <%--            var TalkWords = document.getElementById("talkwords");--%>
    <%--            var TalkSub = document.getElementById("talksub");--%>
    <%--            TalkSub.onclick = function(){--%>
    <%--                //定义空字符串--%>
    <%--                var str = "";--%>
    <%--                if(TalkWords.value == ""){--%>
    <%--                    // 消息为空时弹窗--%>
    <%--                    alert("消息不能为空");--%>
    <%--                    return;--%>
    <%--                }else{--%>
    <%--                    document.getElementById("editForm").submit();--%>
    <%--                }--%>
    <%--                // if(Who.value == 0){--%>
    <%--                //     //如果Who.value为0n那么是 A说--%>
    <%--                //     str = '<div class="atalk"><span>A说 :' + TalkWords.value +'</span></div>';--%>
    <%--                // }--%>
    <%--                // else{--%>
    <%--                &lt;%&ndash;    str = '<div class="btalk"><span>${name} :' + TalkWords.value +'</span></div>' ;&ndash;%&gt;--%>
    <%--                //}--%>
    <%--                //Words.innerHTML = Words.innerHTML + str;--%>
    <%--            }--%>
    <%--            $("#words").scrollTop($("#words")[0].scrollHeight);--%>
    <%--        }--%>


    <%--    </script>--%>
</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <div class="talk_con">
        <%--    <div class="talk_show" id="words">--%>
        <%--        <div class="atalk"><span id="asay">A说：吃饭了吗？</span></div>--%>
        <%--        <div class="btalk"><span id="bsay">B说：还没呢，你呢？</span></div>--%>
        <%--    </div>--%>
        <%--    <div class="talk_input">--%>
        <%--        <select class="whotalk" id="who">--%>
        <%--            <option value="0">A说：</option>--%>
        <%--            <option value="1">B说：</option>--%>
        <%--        </select>--%>
        <div class="talk_show" id="words">
            <c:forEach items="${page.list}" var="item">
                <%--        <tr>--%>
                <%--            <td><input name="ids" value="${item.id}" type="checkbox"></td>--%>
                <%--            <td>--%>
                <%--                    ${item.name}--%>
                <%--            </td>--%>
                <%--            <td>${item.city}</td>--%>
                <%--            <td class="text-center">--%>
                <%--                <button type="button" class="btn bg-olive btn-xs" onclick='location.href="/company/toUpdate.do?id=${item.id}"'>编辑</button>--%>
                <%--            </td>--%>
                <%--        </tr>--%>
                <c:if test="${item.userId == loginUserId}">
                    <div class="btalk1">
                        <span>${loginUsername} ${item.date}</span><br>
                    </div>
                    <div class="btalk">
                        <span>${item.content}</span>
                    </div>
                </c:if>
                <c:if test="${item.userId != loginUserId}">
                    <div class="atalk1">
                        <span>${item.userName} ${item.date}</span><br>
                    </div>
                    <div class="atalk">
                <span>${item.content}
                </span></div>
                </c:if>
            </c:forEach>
        </div>
        <%--        <div class="talk_show" id="words">--%>
        <%--            <div class="atalk"><span id="asay">A说：吃饭了吗？</span></div>--%>
        <%--            <div class="btalk"><span id="bsay">${name}：还没呢，你呢？</span></div>--%>
        <%--        </div>--%>
        <%--    <div class="talk_input">--%>
        <%--            <select class="whotalk" id="who">--%>
        <%--                <option value="0">A说：</option>--%>
        <%--                <option value="1">${loginUsername}：</option>--%>
        <%--            </select>--%>
        <%--        <form id="editForm" action="/chat/toAdd.do" method="post">--%>
        <%--            <input type="text" class="talk_word" id="talkwords" name="content">--%>

        <%--        </form>--%>
        <%--            <button class="talk_sub" id="talksub" >发送</button>--%>
        <%--    </div>--%>
    </div>
</div>
</body>
</html>