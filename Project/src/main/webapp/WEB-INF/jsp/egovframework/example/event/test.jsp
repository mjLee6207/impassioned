<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<html>
<head>
    <title>레시피추천</title>
    <link rel="stylesheet" href="/css/test.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
    <div id="page">

        <div id="s-screen">
            <h1>나랑 어울리는 요리는?</h1>
            <button type="button"
                    class="btn btn-success"
                    onclick="start()"
                    >테스트시작</button>
        </div>

        <div id="q-screen" style="display: none;">
            <h2 id="q-no"></h2>
            <h2 id="q-text"></h2>
            <div id="a-buttons"></div>
        </div>

        <div id="r-screen" style="display: none;">
            <h1>나랑 어울리는 요리는</h1>
            <h3 id="r-text"></h3>
            <button type="button"
                    class="btn btn-success"
                    onclick="location.reload()"
                    >다시하기</button>
                    
         <a href="http://localhost:8080/recipe/recipe.do">
         	 /이엘표현식/ 레시피 바로가기    
         </a>
        </div>

    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    
    <script src="/js/test.js"></script>
</html>