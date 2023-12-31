let commentEditBtn = document.querySelector("#comment-edit-form");
let commentWriteBtn = document.querySelector("#submit-creating");
let commentEditCompleteBtn = document.querySelector("#submit-editing");

const header = document.querySelector('meta[name="_csrf_header"]').content;
const token = document.querySelector('meta[name="_csrf"]').content;

// comment 등록 버튼 이벤트
commentWriteBtn.addEventListener("click", function () {
    let comment = {
        articleId: document.getElementById("new-comment-article-id").value,
        contents: document.getElementById("new-comment-body").value,

    }
    let url = "/api/articles/"+ comment.articleId + "/comments";
    fetch(url, {
        method: "POST",
        headers: {
            'header': header,
            'X-Requested-With': 'XMLHttpRequest',
            "Content-Type": "application/json",
            'X-CSRF-Token': token
        },
        body: JSON.stringify(comment)
    }).then(response => {
        const msg = (response.ok) ? "댓글이 등록됐습니다." : "댓글 등록에 실패하였습니다.";
        alert(msg);
        window.location.reload();
    })
});

// comment 수정 버튼 modal 이벤트
commentEditBtn.addEventListener("show.bs.modal", function (event) {
    const targetBtn = event.relatedTarget;
    const id = targetBtn.getAttribute("data-bs-id");
    const nickName = targetBtn.getAttribute("data-bs-nickName");
    const contents = targetBtn.getAttribute("data-bs-contents");
    const articleId = targetBtn.getAttribute("data-bs-articleId");

    document.querySelector("#edit-comment-id").value = id;
    document.querySelector("#edit-comment-nickName").value = nickName;
    document.querySelector("#edit-comment-contents").value = contents;
    document.querySelector("#edit-comment-articleId").value = articleId;

})

// comment 수정 완료 버튼 이벤트
commentEditCompleteBtn.addEventListener("click", function () {
   const comment ={
       id: document.querySelector("#edit-comment-id").value,
       contents: document.querySelector("#edit-comment-contents").value,
       nickName: document.querySelector("#edit-comment-nickName").value,
       articleId: document.querySelector("#edit-comment-articleId").value
   }
   const url = "/api/articles/comments/" + comment.id;
   fetch(url, {
       method: "PATCH",
       headers: {
           'header': header,
           'X-Requested-With': 'XMLHttpRequest',
           "Content-Type": "application/json",
           'X-CSRF-Token': token
       },
       body: JSON.stringify(comment)
   }).then(response => {
       const msg = (response.ok) ? "댓글이 수정됐습니다." : "댓글을 수정할 수 없습니다.(오류)";
       alert(msg);
       window.location.reload();
   })
});

// comment 삭제 이벤트
let deleteBtns = document.querySelectorAll(".comment-delete-btn");
deleteBtns.forEach(btn => {
    btn.addEventListener("click", (event) => {
        console.log("삭제 버튼 감지");
        const deleteBtn = event.target;
        const commentId = deleteBtn.getAttribute("data-commentId");
        console.log(`삭제 버튼 클릭 : ${commentId}번 댓글`);
        const url = `/api/comments/${commentId}`;

        fetch(url, {
            method: "DELETE",
            headers: {
                'header': header,
                'X-Requested-With': 'XMLHttpRequest',
                "Content-Type": "application/json",
                'X-CSRF-Token': token
            }
        }).then(response => {
            if (!response.ok) {
                alert("댓글을 삭제할 수 없습니다!(오류)");
                return
            }

            const target = document.querySelector(`#commentId-${commentId}`);
            target.remove();

            const msg = `댓글을 삭제했습니다.`;
            alert(msg);

            window.location.reload();
        })
    });
});