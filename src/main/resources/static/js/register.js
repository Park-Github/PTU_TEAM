let emailInput;
let passwordInput;
let passwordCheck;
let nicknameInput;
let phoneInput;
let birthInput;
let privacyConsent;

document.addEventListener("DOMContentLoaded", onInit);

function onInit() {
    let form = document.querySelector("#form");
    emailInput = form.elements.namedItem("email");
    passwordInput = form.elements.namedItem("password");
    passwordCheck = form.elements.namedItem("password-check");
    nicknameInput = form.elements.namedItem("nickname");
    phoneInput = form.elements.namedItem("contact");
    birthInput = form.elements.namedItem("birth");
    privacyConsent = form.elements.namedItem("privacy-consent");
    birthInput.addEventListener("click", () => resetInput(birthInput));
    emailInput.addEventListener("input", validateForm);
    passwordInput.addEventListener("input", validateForm);
    passwordCheck.addEventListener("input", validateForm);
    nicknameInput.addEventListener("input", validateForm);
    phoneInput.addEventListener("input", validateForm);
    birthInput.addEventListener("input", validateForm);
    privacyConsent.addEventListener("input", validateForm);
    form.addEventListener("submit", e => onSubmit(e, form));
}

async function onSubmit(event, form) {
    event.preventDefault();

    if (!validateForm()) return;

    let r = await fetch("/member/register", {
        method: "POST",
        body: new FormData(form)
    });
    let json = await r.json();

    if (r.ok) {
        location.href = "/";
    } else {
        onError(json);
    }
}

function onError(response) {
    let status = parseInt(response["status"]);

    switch (status) {
        case 1:  // INCOMPLETE_FORM
            window.alert("누락된 내용이 있습니다.");
            break;
        case 2:  // SHORT_PASSWORD
            updateInput(passwordInput, false);
            window.alert("비밀번호는 최소 8글자로 구성해야 합니다.")
            break;
        case 3:  // DUPLICATE_EMAIL
            updateInput(emailInput, false);
            window.alert("본 이메일은 이미 사용중입니다.");
            break;
        case 4:  // DUPLICATE_NAME
            updateInput(nicknameInput, false);
            window.alert("본 이름은 이미 사용중입니다.");
            break;
        default:
            window.alert("서버 오류!");
    }
}

function validateForm() {
    let email = emailInput.value;
    let password1 = passwordInput.value;
    let password2 = passwordCheck.value;
    let nickname = nicknameInput.value;
    let phone = phoneInput.value;
    let birth = birthInput.value;
    let consent = privacyConsent.checked;

    return (validate(emailInput, (email && email.includes("@")))
        && validate(passwordInput, (password1 && password1.length >= 8))
        && validate(passwordCheck, (password1 === password2))
        && validate(nicknameInput, nickname)
        && validate(phoneInput, (phone && phone.match(/^[0-9]+$/) != null))
        && validate(birthInput, birth)
        && validate(privacyConsent, consent));
}

function validate(input, expr) {
    if (expr) {
        updateInput(input, true);
        return true;
    } else {
        updateInput(input, false);
        return false;
    }
}

function updateInput(input, valid) {
    input.classList.add(valid ? "is-valid" : "is-invalid");
    input.classList.remove(valid ? "is-invalid" : "is-valid");
}

function resetInput(input) {
    input.classList.remove("is-invalid", "is-valid");
}