const title = document.getElementById('title');
const contents = document.getElementById('contents');
const submitBtn = document.getElementById('submitBtn');

function checkInput() {
    const titleValue = title.value;
    const contentsValue = contents.value;
    if(titleValue && contentsValue) {
        submitBtn.disabled = false;
    } else {
        submitBtn.disabled = true;
    }
}

title.addEventListener('input', checkInput);
contents.addEventListener('input', checkInput);