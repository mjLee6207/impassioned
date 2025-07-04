
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">
		<img src="<c:url value='/images/simple-coding.png'/>" width="20" height="20" />
    	simple-coding 
    </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="/">Home</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            게시판
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<c:url value='/board/board.do'/>">Board</a></li>
            <li><a class="dropdown-item" href="<c:url value='/board/addition.do'/>">게시판 글쓰기</a></li>
          </ul>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            사원(퀴즈)
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<c:url value='/emp/emp.do'/>">Emp</a></li>
            <li><a class="dropdown-item" href="<c:url value='/emp/addition.do'/>">Add Emp</a></li>
          </ul>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Faq(마무리퀴즈)
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<c:url value='/faq/faq.do'/>">Faq</a></li>
            <li><a class="dropdown-item" href="<c:url value='/faq/addition.do'/>">Add Faq</a></li>
          </ul>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            fileDb
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<c:url value='/fileDb/fileDb.do'/>">fileDb</a></li>
            <li><a class="dropdown-item" href="<c:url value='/fileDb/addition.do'/>">Add fileDb</a></li>
          </ul>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            gallery
          </a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="<c:url value='/gallery/gallery.do'/>">gallery</a></li>
            <li><a class="dropdown-item" href="<c:url value='/gallery/addition.do'/>">Add gallery</a></li>
          </ul>
        </li>
      </ul>

      <!-- 메뉴(오른쪽) -->
      <ul class="navbar-nav">
        <!-- {/* TODO: 로그인 시작 */} -->
        <!-- memberVO 가 세션에 없으면 메뉴을 보이고, 있으면 안보임 -->
        <c:if test="${sessionScope.memberVO == null}">
        	<li class="nav-item">
	          <a class="nav-link active" href="/member/register.do"> 회원가입 </a>
	        </li>
	        <li class="nav-item">
	           <a class="nav-link active" href="/member/login.do"> 로그인 </a>
	        </li>
        </c:if>

        <!-- {/* 로그인 끝 */} -->

        <!-- {/* 로그아웃 시작 */} -->
        <!-- memberVO 가 세션에 있으면 메뉴을 보이고, 없으면 안보임 -->
        <c:if test="${sessionScope.memberVO != null}">
	         <li class="nav-item">
	           <a href="/member/logout.do" class="nav-link active"> 로그아웃
	           </a>
	         </li>
	    </c:if>
        <!-- {/* 로그아웃 끝 */} -->
      </ul>
    </div>
  </div>
</nav>
