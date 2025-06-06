// 날짜 포맷함수
const formatDate = (str) => {
  const date = new Date(str);

  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

const replyListElement = document.querySelector(".replyList");
const replyForm = document.querySelector("#replyForm");

// 댓글 리로딩
const replyList = () => {
  axios.get(`/replies/board/${bno}`).then((res) => {
    console.log(res.data);
    const data = res.data;
    console.log("댓글 수 ", data.length);

    // 댓글 갯수 리로딩
    replyListElement.previousElementSibling.querySelector("span").innerHTML = data.length;

    let result = "";
    data.forEach((reply) => {
      result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno=${reply.rno} data-email=${reply.replyerEmail}>`;
      result += `<div class="p-3">`;
      result += `<img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
      result += `<div class="flex-grow-1 align-self-center">`;
      result += `<div>${reply.replyerName}</div>`;
      result += `<div><span class="fs-5">${reply.text}</span></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;

      // 로그인 사용자 == 댓글 작성자
      if (loginUser == reply.replyerEmail) {
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      }
      result += `</div></div>`;
    });

    replyListElement.innerHTML = result;
  });
};

// 댓글 삭제
// 삭제 버튼 클릭 시 data-rno 가져오고 그걸 삭제

document.querySelector(".replyList").addEventListener("click", (e) => {
  // 어느 버튼의 이벤트인가?
  console.log(e.target);
  const btn = e.target;

  // rno 가져오기
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log(rno);
  // 댓글 작성자 가져오기
  const replyerEmail = btn.closest(".reply-row").dataset.email;
  // 삭제 버튼 or 수정 버튼
  if (btn.classList.contains("btn-outline-danger")) {
    // 삭제
    if (!confirm("정말로 삭제하시겠습니까?")) return;

    axios.delete(`/replies/${rno}`, {
      data: { replyerEmail: replyerEmail },
      headers: {
        "Content-Type": "application/json",
        "X-CSRF-TOKEN": csrf,
      },
    }).then((res) => {
      console.log(res.data);

      // 댓글 다시 불러오기
      replyList();
      // 댓글 갯수 다시 불러오기
      replyCount();
    });
  } else if (btn.classList.contains("btn-outline-success")) {
    // 수정
    // 먼저 수정한 댓글을 불러온다음
    axios.get(`/replies/${rno}`).then((res) => {
      console.log(res.data);
      const data = res.data;

      // replyForm 안에 보여주기
      replyForm.rno.value = data.rno;
      replyForm.replyerName.value = data.replyerName;
      replyForm.replyerEmail.value = data.replyerEmail;
      replyForm.text.value = data.text;
    });
  }
});
  
  // 폼 sumbit => 수정 / 삽입
  if (replyForm) {
    replyForm.addEventListener("submit", (e) => {
      e.preventDefault();
  
      const form = e.target;
      const rno = form.rno.value;
  
      if (rno) {
        // 수정
        axios
          .put(`/replies/${rno}`, form, {
            headers: {
              "Content-Type": "application/json",
              "X-CSRF-TOKEN": csrf,
            },
          })
          .then((res) => {
            console.log(res.data);
            alert("댓글 수정 완료");
  
            // form 기존 내용 지우기
            replyForm.rno.value = "";
            replyForm.replyerEmail.value = "";
            replyForm.replyerName.value = "";
            replyForm.text.value = "";
  
            // 수정 내용 반영
            replyList();
          });
      } else {
        // 삽입
        axios
          .post(`/replies/new`, form, {
            headers: {
              "Content-Type": "application/json",
              "X-CSRF-TOKEN": csrf,
            },
          })
          .then((res) => {
            console.log(res.data);
            alert(res.data + ' 댓글 등록');
  
            // form 기존 내용 지우기
            replyForm.rno.value = "";
            replyForm.replyerEmail.value = "";
            replyForm.replyerName.value = "";
            replyForm.text.value = "";
            
            // 수정 내용 반영
            replyList();
          });
      }
    });
  }

  // 페이지 로드시 호출
  replyList();

  // ReplyController.java에 있는

  // @GetMapping("/board/{bno}")
  // public List<ReplyDTO> list(@PathVariable Long bno) {
  //     log.info("bno 댓글 요청 {} ", bno);

  //     return replyService.getList(bno);
  // }

  // 에 들어가는 (path함수 또는) axios함수 사용
