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

// 댓글 리로딩
const replyList = () => {
    axios.get(`/replies/board/${bno}`).then((res) => {
        console.log(res.data);
      
        const data = res.data;
      
        let result = "";
        data.forEach((reply) => {
          result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno=${reply.rno}>`;
          result += `<div class="p-3"><img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
          result += `<div class="flex-grow-1 align-self-center">`;
          result += `<div>${reply.replyer}</div>`;
          result += `<div><span class="fs-5">${reply.text}</span></div>`;
          result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div>`;
          result += `</div>`;
          result += `<div class="d-flex flex-column align-self-center">`;
          result += `<div class="mb-2">`;
          result += `<button class="btn btn-outline-danger btn-sm">삭제</button>`;
          result += `</div>`;
          result += `<div>`;
          result += `<button class="btn btn-outline-success btn-sm">수정</button>`;
          result += `</div>`;
          result += `</div>`;
          result += `</div>`;
        });
      
        document.querySelector(".replyList").innerHTML = result;
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

    // 삭제 버튼 or 수정 버튼
    if (btn.classList.contains("btn-outline-danger")) {

        // 삭제
        if(!confirm("정말로 삭제하시겠습니까?")) return;

        axios.delete(`/replies/${rno}`).then((res) => {
            console.log(res.data);
            
        // 댓글 다시 불러오기
        replyList();

        });

    } else if (btn.classList.contains("btn-outline-success")) {

        // 수정
        // if(!confirm("수정하시겠습니까?")) return;

        // axios.update(`/replies/${rno}`).then((res) => {
        //     console.log(res.data);
        // });

    }

});

// 페이지 로드시 호출
replyList();

// ReplyController.java에 있는

// @GetMapping("/board/{bno}")
// public List<ReplyDTO> list(@PathVariable Long bno) {
//     log.info("bno 댓글 요청 {} ", bno);

//     return replyService.getList(bno);
// }

// 에 들어가는 (path함수 또는) axios함수 사용



