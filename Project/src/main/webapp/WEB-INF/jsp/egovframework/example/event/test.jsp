<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>레시피추천</title>
    <link rel="stylesheet" href="/css/test.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div id="page">
        <!-- 시작 화면 -->
        <div id="s-screen">
            <h1>나랑 어울리는 요리는?</h1>
            <button type="button" class="btn btn-success" onclick="start()">테스트시작</button>
        </div>     

        <!-- 질문 화면 -->
        <div id="q-screen" style="display: none;">
            <h2 id="q-no"></h2>
            <h2 id="q-text"></h2>
            <div id="a-buttons"></div>
        </div>

        <!-- 결과 화면 -->
        <div id="r-screen" style="display: none;">
            <h1>나랑 어울리는 요리는</h1>
            <h3 id="r-text"></h3>
            <a id="r-link" class="btn btn-primary mt-3" href="#">레시피 보러가기</a>
            <button type="button" class="btn btn-success" onclick="location.reload()">다시하기</button>
        </div>
    </div>
    
    <body data-context-path="<%= request.getContextPath() %>">

    <script>
    
    const questions = [
        {
            qno: "Q1. ",
            question: "나는 요리",
            answers: [
                { text: "초보다", type: "양식"},
                { text: "고수다", type: "일식"}
            ]
        },
        {
            qno: "Q2. ",
            question: "요리할 때에 나는",
            answers: [
                { text: "무조건 레시피 대로", type: "한식"},
                { text: "오늘은 내가 셰프", type: "중식"}
            ]
        },
        {
            qno: "Q3. ",
            question: "나는",
            answers: [
                { text: "채식 주의자", type: "양식"},
                { text: "채식 '주의'자", type: "한식"}
            ]
        },
        {
            qno: "Q4. ",
            question: "식사후에 설거지는",
            answers: [
                { text: "바로 해야지!", type: "중식"},
                { text: "나중에 할래...", type: "일식"}
            ]
        }
    ];

    let currentQuestion = 0;
    let score = {
        한식: 0,
        양식: 0,
        중식: 0,
        일식: 0
    };

    const ctx = "<%= request.getContextPath() %>";

    const typeToDbCategory = {
        "한식": "한식",
        "중식": "중식",
        "일식": "일식",
        "양식": "양식"
    };


    // 테스트 시작
    function start() {
        document.getElementById("s-screen").style.display = "none";
        document.getElementById("q-screen").style.display = "block";
        showQuestion();
    }

    // 질문 표시
    function showQuestion() {
        const q = questions[currentQuestion];
        document.getElementById("q-no").innerText = q.qno;
        document.getElementById("q-text").innerText = q.question;

        const answersDiv = document.getElementById("a-buttons");
        answersDiv.innerHTML = "";

        q.answers.forEach(answer => {
            const btn = document.createElement("button");
            btn.innerText = answer.text;
            btn.className = "btn btn-success m-2";
            btn.onclick = () => {
                score[answer.type]++;
                currentQuestion++;
                if (currentQuestion < questions.length) {
                    showQuestion();
                } else {
                    showResult();
                }
            };
            answersDiv.appendChild(btn);
        });
    }

    // 테스트 결과
    function showResult() {
        document.getElementById("q-screen").style.display = "none";
        document.getElementById("r-screen").style.display = "block";

        const maxScore = Math.max(...Object.values(score));
        const bestTypes = Object.keys(score).filter(type => score[type] === maxScore);
        const bestCategory = bestTypes[0];


        document.getElementById("r-text").textContent = bestCategory + "!";

        const categoryParam = typeToDbCategory[bestCategory] || "";
        const url = ctx + "/recipe/recipe.do?categoryKr=" + encodeURIComponent(categoryParam);
        document.getElementById("r-link").href = url;
    }
    </script>
</body>
</html>
