// 삭제버튼 클릭 시 removeForm submit

document.querySelector(".btn-danger").addEventListener("click", (e) => {
    document.querySelector("#removeForm").submit();
});