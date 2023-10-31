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
    form.addEventListener("submit", e => submitForm(e));
}

function submitForm(event) {
    if (!validateForm()) {
        event.preventDefault();
    }
    // todo compute response

}

function validateForm() {
    let email = emailInput.value;
    let password1 = passwordInput.value;
    let password2 = passwordCheck.value;
    let nickname = nicknameInput.value;
    let phone = phoneInput.value;
    let birth = birthInput.value;
    let consent = privacyConsent.checked;

    return (validate(emailInput, email && email.includes("@"))
        && validate(passwordInput, (password1 && password1.length >= 8))
        && validate(passwordCheck, (password1 === password2))
        && validate(nicknameInput, nickname)
        && validate(phoneInput, phone && phone.match(/^[0-9]+$/) != null)
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