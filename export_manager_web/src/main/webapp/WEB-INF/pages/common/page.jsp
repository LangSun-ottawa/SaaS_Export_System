<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<div class="pull-left">
    <div class="form-group form-inline">
        Total ${page.pages} Pages，Total ${page.total} Data。
    </div>
</div>

<div class="box-tools pull-right">
    <ul class="pagination" style="margin: 0px;">
        <li >
            <a href="javascript:goPage(1)" aria-label="Previous">First Page</a>
        </li>
        <li><a href="javascript:goPage(${page.prePage})"> << </a></li>
        <c:forEach begin="${page.navigateFirstPage}" end="${page.navigateLastPage}" var="i">
            <li class="paginate_button ${page.pageNum==i ? 'active':''}"><a href="javascript:goPage(${i})">${i}</a></li>
        </c:forEach>
        <li><a href="javascript:goPage(${page.nextPage})"> >> </a></li>
        <li>
            <a href="javascript:goPage(${page.pages})" aria-label="Next">Last Page</a>
        </li>
    </ul>
</div>
<form id="pageForm" action="${param.pageUrl}" method="post">
    <input type="hidden" name="page" id="pageNum">
</form>
<script>
    function goPage(page) {
        document.getElementById("pageNum").value = page;
        document.getElementById("pageForm").submit();
    }
</script>
</body>
</html>
