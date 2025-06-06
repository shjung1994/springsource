const fileInput = document.querySelector("[name='file']");

const showUploadImages = (arr) => {
  const output = document.querySelector(".uploadResult ul");

  console.log("----- arr");
  console.log(arr);

  let tags = "";
  arr.forEach((element, idx) => {
    tags += `<li data-name="${element.fileName}" data-path="${element.folderPath}" data-uuid="${element.uuid}">`;
    tags += `<img src="/upload/display?fileName=${element.thumbnailURL}">`;
    tags += `<a href="${element.imageURL}"><i class="fa-regular fa-circle-xmark"></i></a>`;
    tags += "</li>";
  });
  output.insertAdjacentHTML("beforeend", tags);
};

// a href(x 버튼) 클릭 시 a 기본 태그 중지
// a href 가져오기: 파일명 가져오기
// axios.post()
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  e.preventDefault();

  // 이벤트 대상
  // console.log("e.target", e.target); // i가 뜸 <i class="fa-regular fa-circle-xmark"></i>
  // 이벤트 대상의 부모
  // console.log("e.currentTarget", e.currentTarget); // 부모 전체가 뜸 <div> ~ </div>
  // 하지만 필요한건 i를 감싸고 있는 a
  // 이벤트 대상과 제일 가까운: closest(원하는 selector)
  // console.log("e.target.closest", e.target.closest("a"));

  const aTag = e.target.closest("a");
  const liTag = e.target.closest("li");

  // 속성 접근: . or getAttribute("속성명")
  // img.src: img 태그의 src 값 가져오기 or img.getAttribute("src")

  console.log(aTag.href);
  console.log(aTag.getAttribute("href"));

  const fileName = aTag.getAttribute("href");
  let form = new FormData();
  form.append("fileName", fileName);

  if (!confirm("정말로 삭제하시겠습니까?")) return;

  axios
    .post(`/upload/removeFile`, form, {
      headers: {
        "X-CSRF-TOKEN": csrf,
      },
    })
    .then((res) => {
      console.log(res.data);

      if (res.data) {
        liTag.remove();
      }
    });
});

// document.querySelector("button").addEventListener("click", () => {
//   // 버튼 클릭 시 uploadFiles 가져오기
//   const inputFile = document.querySelector('[name="uploadFiles"]');
//   console.log(inputFile);
//   const files = inputFile.files;
//   console.log(files);

//   // form 생성 후 업로드 된 파일 append
//   let form = new FormData();

//   for (let i = 0; i < files.length; i++) {
//     form.append("uploadFiles", files[i]);
//   }

//   axios
//     .post(`/upload/files`, form, {
//       headers: {
//         "X-CSRF-TOKEN": csrf,
//       },
//     })
//     .then((res) => {
//       console.log(res.data);
//       showUploadImages(res.data);
//     });
// });

fileInput.addEventListener("change", (e) => {
  // 버튼 클릭 시 uploadFiles 가져오기
  const inputFile = e.target;
  const files = inputFile.files;
  console.log(files);

  // form 생성 후 업로드 된 파일 append
  let form = new FormData();

  for (let i = 0; i < files.length; i++) {
    form.append("uploadFiles", files[i]);
  }

  axios
    .post(`/upload/files`, form, {
      headers: {
        "X-CSRF-TOKEN": csrf,
      },
    })
    .then((res) => {
      console.log(res.data);
      showUploadImages(res.data);
    });
});
