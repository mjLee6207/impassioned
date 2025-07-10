<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/sidebar.css" />
    <link rel="stylesheet" href="/css/mypage.css" />
    <link rel="stylesheet" href="/css/pagination.css">
</head>
<body>
<jsp:include page="/common/header.jsp" />

<!-- 7월 9일 강승태 마이페이지 구조 통일을 위해 수정 -->
<div class="main-flex">
    <!-- 사이드바 -->
    <div class="sidebar">
        <jsp:include page="/common/sidebar.jsp" />
    </div>

    <!-- 오른쪽 컨텐츠 영역 -->
    <div class="content-area">

        <!-- ====== 탭 메뉴 ====== -->
        <div class="tab-menu">
            <div id="tab-myPosts" class="active" onclick="showSection('myPostsSection', this)">
                <i class="bi bi-pencil-square"></i>
                <span>내가 작성한 글 <span class="post-count">(${myPostsTotalCount}개)</span></span>
            </div>
            <div id="tab-likedPosts" onclick="showSection('likedPostsSection', this)">
                <i class="bi bi-heart-fill"></i>
                <span>좋아요 남긴 글 <span class="like-count">(${likedPostsTotalCount}개)</span></span>
            </div>
        </div>

        <!-- 내가 작성한 글 -->
        <div id="myPostsSection" style="display: block;">
            <div class="search-area">
                <input type="text" id="searchMyPosts" class="form-control form-control-sm search-input" placeholder="내가 작성한 글 검색" onkeyup="filterTable('myPostsTable', this.value)">
                <button type="button" class="search-btn" onclick="clickSearch('myPostsTable','searchMyPosts')">
                    <i class="bi bi-search"></i>
                </button>
            </div>
            <table id="myPostsTable" class="table table-hover post-table">
                <thead>
                    <tr>
                        <th class="text-center" style="width:55%;">제목</th>
                        <th class="text-center" style="width:25%;">작성일</th>
                        <th class="text-center" style="width:10%;">조회수</th>
                        <th class="text-center" style="width:10%;">좋아요</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="post" items="${myPosts}">
                        <tr>
                            <td class="text-start">
                                <a href="${pageContext.request.contextPath}/board/view.do?boardId=${post.boardId}" class="post-title-link">${post.title}</a>
                            </td>
                            <td class="text-center">
                                <fmt:formatDate value="${post.writeDate}" pattern="yyyy-MM-dd" />
                            </td>
                            <td class="text-center">${post.viewCount}</td>
                            <td class="text-center">${post.likeCount}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty myPosts}">
                        <tr>
                            <td colspan="4" class="text-secondary text-center">아직 작성한 게시글이 없습니다.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
             <!-- 페이지네이션 -->
	            <div class="flex-center">
	        	<ul class="pagination" id="paginationMyPosts"></ul>
	    		</div>
        </div>
        <!-- 좋아요한 글 -->
        <div id="likedPostsSection" style="display: none;">
            <div class="search-area">
                <input type="text" id="searchLikedPosts" class="form-control form-control-sm search-input" placeholder="좋아요 남긴 글 검색" onkeyup="filterTable('likedPostsTable', this.value)">
                <button type="button" class="search-btn" onclick="clickSearch('likedPostsTable','searchLikedPosts')">
                    <i class="bi bi-search"></i>
                </button>
            </div>
            <table id="likedPostsTable" class="table table-hover post-table">
                <thead>
                    <tr>
                        <th class="text-center col-title" style="width:35%;">제목</th>
                        <th class="text-center col-author"style="width:15%;">작성자</th>
                        <th class="text-center col-date"style="width:20%;">작성일</th>
                        <th class="text-center col-views"style="width:10%;">조회수</th>
                        <th class="text-center col-likes"style="width:10%;">좋아요</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="like" items="${likedPosts}">
                        <tr>
                            <td class="text-start">
                                <a href="${pageContext.request.contextPath}/board/view.do?boardId=${like.boardId}" class="post-title-link">${like.title}</a>
                            </td>
                            <td class="text-center">${like.writerName}</td>
                            <td class="text-center">
                                <fmt:formatDate value="${like.writeDate}" pattern="yyyy-MM-dd" />
                            </td>
                            <td class="text-center">${like.viewCount}</td>
                            <td class="text-center">${like.likeCount}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty likedPosts}">
                        <tr>
                            <td colspan="5" class="text-secondary text-center">좋아요를 남긴 게시글이 없습니다.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            <!-- 페이지네이션 -->
	            <div class="flex-center">
	        	<ul class="pagination" id="paginationLikedPosts"></ul>
	    		</div>
	    		</div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twbs-pagination/1.4.2/jquery.twbsPagination.min.js"></script>
<script>
function initPagination(selector, totalPages, startPage, visiblePages, tabName, pageParamName) {
    if ($(selector).data('twbs-pagination')) {
        $(selector).twbsPagination('destroy');
    }

    $(selector).twbsPagination({
        totalPages: Number(totalPages) || 1,
        startPage: Number(startPage) || 1,
        visiblePages: Number(visiblePages) || 5,
        initiateStartPageClick: false,
        first: '&laquo;',
        prev: '&lt;',
        next: '&gt;',
        last: '&raquo;',
        onPageClick: function(event, page) {
            var params = new URLSearchParams(window.location.search);
            params.set('tab', tabName);
            params.set(pageParamName, page);
            window.location.search = params.toString();
        }
    });
}

function showSection(sectionId, tabElem) {
    document.getElementById("myPostsSection").style.display = "none";
    document.getElementById("likedPostsSection").style.display = "none";
    document.getElementById(sectionId).style.display = "block";

    document.getElementById('tab-myPosts').classList.remove('active');
    document.getElementById('tab-likedPosts').classList.remove('active');
    if (tabElem) tabElem.classList.add('active');

    document.getElementById("searchMyPosts").value = "";
    document.getElementById("searchLikedPosts").value = "";

    filterTable('myPostsTable', '');
    filterTable('likedPostsTable', '');

    var params = new URLSearchParams(window.location.search);

    if (sectionId === 'myPostsSection') {
        params.set('tab', 'myboard');
        params.set('myPostsPage', 1);
        params.delete('likedPostsPage');

        // 내가 쓴 글 페이지네이션 재초기화
        initPagination(
            '#paginationMyPosts',
            parseInt('${myPostsPaginationInfo.totalPageCount}'),
            parseInt('${myPostsPaginationInfo.currentPageNo}'),
            parseInt('${myPostsPaginationInfo.recordCountPerPage}'),
            'myboard',
            'myPostsPage'
        );

        // 좋아요 페이지네이션 제거
        if ($('#paginationLikedPosts').data('twbs-pagination')) {
            $('#paginationLikedPosts').twbsPagination('destroy');
        }
    } else if (sectionId === 'likedPostsSection') {
        params.set('tab', 'mylike');
        params.set('likedPostsPage', 1);
        params.delete('myPostsPage');

        // 좋아요 페이지네이션 재초기화
        initPagination(
            '#paginationLikedPosts',
            parseInt('${likedPostsPaginationInfo.totalPageCount}'),
            parseInt('${likedPostsPaginationInfo.currentPageNo}'),
            parseInt('${likedPostsPaginationInfo.recordCountPerPage}'),
            'mylike',
            'likedPostsPage'
        );

        // 내가 쓴 글 페이지네이션 제거
        if ($('#paginationMyPosts').data('twbs-pagination')) {
            $('#paginationMyPosts').twbsPagination('destroy');
        }
    }

    window.history.replaceState({}, '', `${location.pathname}?${params.toString()}`);
}

// 필터 함수 (기존 유지)
function filterTable(tableId, keyword) {
    keyword = keyword.toLowerCase();
    var table = document.getElementById(tableId);
    if (!table) return;
    var trs = table.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    for(var i=0; i<trs.length; i++) {
        var rowText = trs[i].innerText.toLowerCase();
        trs[i].style.display = (rowText.indexOf(keyword) > -1) ? "" : "none";
    }
}

// 검색 함수 (기존 유지)
function clickSearch(tableId, inputId) {
    var keyword = document.getElementById(inputId).value;
    filterTable(tableId, keyword);
}

$(function() {
    // 페이지 로딩 시 현재 탭에 따라 페이징 초기화 및 탭 표시
    var params = new URLSearchParams(window.location.search);
    var tab = params.get('tab') || 'myboard';

    if (tab === 'myboard') {
        showSection('myPostsSection', document.getElementById('tab-myPosts'));
    } else if (tab === 'mylike') {
        showSection('likedPostsSection', document.getElementById('tab-likedPosts'));
    }

    // 초기 페이지네이션 두 개 다 설정 (destroy 호출이 있으므로 문제 없음)
    initPagination(
        '#paginationMyPosts',
        parseInt('${myPostsPaginationInfo.totalPageCount}'),
        parseInt('${myPostsPaginationInfo.currentPageNo}'),
        parseInt('${myPostsPaginationInfo.recordCountPerPage}'),
        'myboard',
        'myPostsPage'
    );

    initPagination(
        '#paginationLikedPosts',
        parseInt('${likedPostsPaginationInfo.totalPageCount}'),
        parseInt('${likedPostsPaginationInfo.currentPageNo}'),
        parseInt('${likedPostsPaginationInfo.recordCountPerPage}'),
        'mylike',
        'likedPostsPage'
    );
});
</script>
<script>
$(function() {
    $('#paginationLikedPosts').twbsPagination({
        totalPages: 3,       // 임의로 3페이지 설정
        startPage: 1,
        visiblePages: 5,
        initiateStartPageClick: true,
        onPageClick: function (event, page) {
            console.log('Liked posts page:', page);
        }
    });
});
</script>
</body>
</html>
