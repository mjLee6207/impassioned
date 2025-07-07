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
</head>
<body>
<jsp:include page="/common/header.jsp" />

<div class="container container-box" style="margin-top:32px;">
    <div style="display: flex; gap: 32px; align-items: flex-start;">
        <!-- 1. 회원정보 영역(사이드바) -->
        <div>
    <jsp:include page="/common/sidebar.jsp" />
        </div>
        <!-- 2. 마이페이지 본문 전체(오른쪽 박스) -->
        <div style="flex: 1;">
            <div class="right-box">
                <!-- ====== 탭 메뉴 ====== -->
                <div class="tab-menu">
                    <div id="tab-myPosts" class="active" onclick="showSection('myPostsSection', this)">
                        <i class="bi bi-pencil-square"></i>
                        <span>내가 작성한 글 <span class="post-count">(${myPosts.size()}개)</span></span>
                    </div>
                    <div id="tab-likedPosts" onclick="showSection('likedPostsSection', this)">
                        <i class="bi bi-heart-fill"></i>
                        <span>좋아요한 글 <span class="like-count">(${likedPosts.size()}개)</span></span>
                    </div>
                </div>

                <!-- ====== 내가 작성한 글 (기본 노출) ====== -->
                <div id="myPostsSection" style="display: block;">
                    <div class="search-area">
                        <input type="text" id="searchMyPosts" class="form-control form-control-sm search-input" placeholder="작성한 글 검색..." onkeyup="filterTable('myPostsTable', this.value)">
                        <button type="button" class="search-btn" onclick="clickSearch('myPostsTable','searchMyPosts')">
                            <i class="bi bi-search"></i>
                        </button>
                    </div>
                    <table id="myPostsTable" class="table table-hover text-center">
                        <thead>
                            <tr>
                                <th>제목</th>
                                <th>작성일</th>
                                <!-- <th>조회수</th> -->
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="post" items="${myPosts}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/board/view.do?boardId=${post.boardId}" class="text-decoration-none">${post.title}</a>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${post.writeDate}" pattern="yyyy-MM-dd" />
                                    </td>
                                    <!-- 조회수 컬럼이 VO/쿼리에 있으면 아래 주석 해제
                                    <td>${post.viewCount}</td>
                                    -->
                                </tr>
                            </c:forEach>
                            <c:if test="${empty myPosts}">
                                <tr>
                                    <td colspan="2" class="text-secondary">아직 작성한 게시글이 없습니다.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <!-- ====== 좋아요한 글 (탭 전환시 보임) ====== -->
                <div id="likedPostsSection" style="display: none;">
                    <div class="search-area">
                        <input type="text" id="searchLikedPosts" class="form-control form-control-sm search-input" placeholder="좋아요한 글 검색..." onkeyup="filterTable('likedPostsTable', this.value)">
                        <button type="button" class="search-btn" onclick="clickSearch('likedPostsTable','searchLikedPosts')">
                            <i class="bi bi-search"></i>
                        </button>
                    </div>
                    <table id="likedPostsTable" class="table table-hover text-center">
                        <thead>
                            <tr>
                                <th>제목</th>
                                <th>작성일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="like" items="${likedPosts}">
                                <tr>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/board/view.do?boardId=${like.boardId}" class="text-decoration-none">${like.title}</a>


                                    </td>
                                    <td>
                                        <fmt:formatDate value="${like.writeDate}" pattern="yyyy-MM-dd" />
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty likedPosts}">
                                <tr>
                                    <td colspan="2" class="text-secondary">아직 좋아요한 게시글이 없습니다.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div> <!-- /.right-box -->
        </div> <!-- /flex:1 -->
    </div> <!-- /flex -->
</div> <!-- /container -->

<!-- 탭 전환 & 검색 스크립트 -->
<script>
    function showSection(sectionId, tabElem) {
        document.getElementById("myPostsSection").style.display = "none";
        document.getElementById("likedPostsSection").style.display = "none";
        document.getElementById(sectionId).style.display = "block";
        document.getElementById('tab-myPosts').classList.remove('active');
        document.getElementById('tab-likedPosts').classList.remove('active');
        if(tabElem) tabElem.classList.add('active');
        // 검색창 초기화
        document.getElementById("searchMyPosts").value = "";
        document.getElementById("searchLikedPosts").value = "";
        filterTable('myPostsTable', '');
        filterTable('likedPostsTable', '');
    }
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
    function clickSearch(tableId, inputId) {
        var keyword = document.getElementById(inputId).value;
        filterTable(tableId, keyword);
    }
</script>
</body>
</html>
