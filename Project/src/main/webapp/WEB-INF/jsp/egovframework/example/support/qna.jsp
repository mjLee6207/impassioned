<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>자주 묻는 질문(FAQ) / 문의하기</title>
    <link rel="stylesheet" href="/css/qna.css">
    <link rel="stylesheet" href="/css/sidebar.css">
    <link rel="stylesheet" href="/css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css">
    <jsp:include page="/common/header.jsp" />
</head>
<body>


<div class="main-wrap">
    <!-- 사이드바 -->
    <div class="sidebar-wrap">
        <jsp:include page="/common/sidebar.jsp" />
    </div>
    <div class="board-wrap">

        <div class="faq-header-row">
            <h2 class="faq-main-title">자주 묻는 질문 (FAQ) & Q&amp;A</h2>
            <c:if test="${not empty sessionScope.loginUser}">
                <button type="button" class="ask-btn" id="show-question-form">
                    <i class="bi bi-question-circle"></i> 문의하기
                </button>
            </c:if>
        </div>

        <!-- 검색창 -->
        <div class="faq-search-area">
            <input type="text" id="faq-search-input" class="search-input" placeholder="궁금한 내용을 검색하세요. 예) 레시피 등록, 사진 업로드">
            <button type="button" class="search-btn" id="faq-search-btn">
                <i class="bi bi-search"></i>
            </button>
        </div>

        <!-- 문의 폼: 기본은 숨김, 버튼 클릭시 노출 -->
        <c:if test="${not empty sessionScope.loginUser}">
            <div class="faq-question-form" id="faq-question-form" style="display:none;">
                <form id="questionForm" action="/faq/ask.do" method="post">
                    <div class="qform-row">
                        <label>제목</label>
                        <input type="text" name="title" required class="qform-input">
                    </div>
                    <div class="qform-row">
                        <label>내용</label>
                        <textarea name="content" rows="4" required class="qform-textarea"></textarea>
                    </div>
                    <div class="qform-row flex-between">
                        <div>
                            <input type="checkbox" id="isPrivate" name="isPrivate" value="Y" style="margin-right:5px;">
                            <label for="isPrivate" style="font-size: 1rem; font-weight: 500;">비공개 문의</label>
                        </div>
                        <button type="submit" class="ask-submit-btn">등록</button>
                    </div>
                </form>
            </div>
        </c:if>

        <!-- FAQ 리스트 + 회원 Q&A (공개) -->
        <div class="faq-list-section" id="faq-list-section">
            <!-- FAQ 예시: 서버/JS로 반복 -->
            <div class="faq-list">
                <!-- FAQ 기본 항목 -->
                <div class="faq-item">
                    <button class="faq-question">Q. 레시피 사진이 안 올라가요!</button>
                    <div class="faq-answer">사진 파일은 JPG, PNG, GIF만 지원하며, 최대 5MB 이하로 업로드해 주세요.</div>
                </div>
                <div class="faq-item">
                    <button class="faq-question">Q. 내가 쓴 글/댓글/좋아요 내역은 어디서 보나요?</button>
                    <div class="faq-answer">상단 마이페이지에서 “내가 쓴 글”, “좋아요한 글”을 확인하실 수 있습니다.</div>
                </div>
                <div class="faq-item">
                    <button class="faq-question">Q. 게시글을 비공개로 등록할 수 있나요?</button>
                    <div class="faq-answer">현재는 모든 게시글이 공개됩니다. 향후 비공개 기능도 준비 중입니다.</div>
                </div>
                <div class="faq-item">
                    <button class="faq-question">Q. 사이트 회원 탈퇴는 어떻게 하나요?</button>
                    <div class="faq-answer">마이페이지 &gt; 회원정보수정 &gt; 하단 “회원 탈퇴” 버튼을 이용해 주세요.</div>
                </div>
                <div class="faq-item">
                    <button class="faq-question">Q. 사이트 이용 중 불편/오류가 있어요!</button>
                    <div class="faq-answer">문의하기를 통해 자세한 상황을 알려주시면 빠르게 조치하겠습니다.</div>
                </div>
                <div class="faq-item">
                    <button class="faq-question">Q. 문의 답변은 어디서 확인할 수 있나요?</button>
                    <div class="faq-answer">답변이 등록되면 알림 또는 마이페이지에서 확인하실 수 있습니다.</div>
                </div>
                <!-- 회원이 등록한 공개 문의(샘플) -->
                <c:forEach var="qna" items="${publicQuestions}">
                    <div class="faq-item">
                        <button class="faq-question">Q. ${qna.title} <span class="q-user">(${qna.writerName})</span></button>
                        <div class="faq-answer">${qna.content}
                            <c:if test="${not empty qna.reply}">
                                <div class="faq-reply">운영자 답변: ${qna.reply}</div>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<script>
/* FAQ 드롭다운 (아코디언) 기능 */
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.faq-question').forEach(btn => {
        btn.onclick = function() {
            let ans = this.nextElementSibling;
            ans.style.display = (ans.style.display === "block") ? "none" : "block";
            // 닫을 때 다른 답변은 접기
            document.querySelectorAll('.faq-answer').forEach(a => {
                if(a !== ans) a.style.display = "none";
            });
        }
    });
    // 문의 폼 show/hide
    const showBtn = document.getElementById('show-question-form');
    const formBox = document.getElementById('faq-question-form');
    if(showBtn && formBox){
        showBtn.onclick = () => formBox.style.display = (formBox.style.display === "block" ? "none" : "block");
    }
    // FAQ 검색(질문/답변 내 텍스트 포함만)
    const searchInput = document.getElementById('faq-search-input');
    if(searchInput) {
        searchInput.addEventListener('keyup', function() {
            let value = this.value.trim().toLowerCase();
            document.querySelectorAll('.faq-item').forEach(item => {
                let q = item.querySelector('.faq-question').innerText.toLowerCase();
                let a = item.querySelector('.faq-answer').innerText.toLowerCase();
                item.style.display = (q.includes(value) || a.includes(value)) ? "" : "none";
            });
        });
    }
});
</script>
</body>
</html>
