// checkbox 클릭이 되면
// checkbox value, data-id 가져오기
// checkbox를 감싸

// 이벤트버블링(자식의 이벤트가 부모한테 전달)
document.querySelector(".list-group").addEventListener("click", (e) => {
    // 어떤 label안 checkbox에서 이벤트가 발생했는지 확인
    const chk = e.target;
    console.log(chk);
    // checkbox 체크, 해제 여부확인
    console.log(chk.checked);

    // id 가져오기
    // closest("선택자"): 부모에서 제일 가까운 요소 찾기
    // data- 속성 가져오기: dataset
    const id = chk.closest("label").dataset.id;
    console.log(id);

    // actionForm 찾은 후 값 변경하기
    const actionForm = document.querySelector("#actionForm");
    actionForm.id.value = id;
    actionForm.completed.value = chk.checked;
    
    actionForm.submit();
    

    //   const value = target.value;
    //   const dataId = target.dataset.id;
  
    //   // 체크박스를 감싸는 부모 요소 찾기 (li 또는 필요에 따라 조정)
    //   const parentElement = target.closest('li');
  
    //   // 콘솔로 값 출력
    //   console.log('Checkbox Value:', value);
    //   console.log('Data ID:', dataId);
    //   console.log('Parent Element:', parentElement);
  
    //   // 예시: 체크되었을 때 스타일 변경
    //   if (target.checked) {
    //     parentElement.classList.add('checked');
    //   } else {
    //     parentElement.classList.remove('checked');
    //   }
    });
  