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
            { text: "채식 \'주의\'자", type: "한식"}
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

// TODO: 현재 질문
let currentQuestion = 0;
// TODO: 채점
let score = {
    한식: 0,
    양식: 0,
    중식: 0,
    일식: 0
};
// TODO: 테스트시작 함수
function start() {
    document.getElementById("s-screen").style.display = "none";
    document.getElementById("q-screen").style.display = "block";
    showQuestion();
}
// TODO: 질문 출력 함수
function showQuestion() {
    const q = questions[currentQuestion];

    document.getElementById("q-no").innerText = q.qno;
    document.getElementById("q-text").innerText = q.question;

    // 기존 버튼 삭제
    const answersDiv = document.getElementById("a-buttons");
    answersDiv.innerHTML = "";

    // 버튼 생성
    q.answers.forEach(answer => {
        const btn = document.createElement("button");
        btn.innerText = answer.text;
        btn.className = "btn btn-success m-2";
        // 버튼 클릭 시
        btn.onclick = () => {
            score[answer.type]++;
            currentQuestion++;
            if(currentQuestion < questions.length){
                showQuestion();
            } else {
                showResult();
            }
        };
        answersDiv.appendChild(btn);
    });
}
// TODO: 결과 출력 함수
function showResult() {
    document.getElementById("q-screen").style.display = "none";
    document.getElementById("r-screen").style.display = "block";

    let result = Object.keys(score).reduce((a,b) => score[a] > score[b] ? a:b);

    document.getElementById("r-text").innerText = `${result}!`;
}