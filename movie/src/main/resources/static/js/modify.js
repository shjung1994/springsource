// 삭제 클릭시 removeForm 전송

document.querySelector(".move").addEventListener("click", (e) => {
  // a 태그 기능 중지시켜야 함 (a 태그는 움직이기 때문)
  e.preventDefault();

  document.querySelector("#removeForm").submit();
});
