// 등록버튼을 누르면 폼 submit을 중지
// li 태그 정보 수집 후 폼에 추가
// 폼 전송
document.querySelector("form").addEventListener('submit', (e) => {
    e.preventDefault();
    
    // li 정보 수집을 위해 li 영역을 찾아옴(업로드 파일 정보)
    const output = document.querySelectorAll(".uploadResult li");

    // 속성: . or getAttribute()
    // data-: dataset

    let result = "";
    output.forEach((obj, idx) => {
        console.log(obj.dataset.uuid);
        result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}"/>`
        result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.dataset.uuid}"/>`
        result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}"/>`
    });

    // 폼에 추가
    e.target.insertAdjacentHTML("beforeend", result);
    e.target.submit();
});