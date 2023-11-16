
let commentEditBtn = document.querySelector("#submit-editing");

commentEditBtn.addEventListener("click", function () {

})

let commentWriteBtn = document.querySelector("#submit-creating");
commentWriteBtn.addEventListener("click", function () {
    let comment = {
        articleId: document.getElementById("new-comment-article-id").value,
        contents: document.getElementById("new-comment-body").value,

    }
    console.log(comment);
    let url = "/api/articles/"+ comment.articleId + "/comments";
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(comment)
    }).then(response => {
        const msg = (response.ok) ? "댓글이 등록됐습니다." : "댓글 등록에 실패하였습니다.";
        alert(msg);
        window.location.reload();
    })
});