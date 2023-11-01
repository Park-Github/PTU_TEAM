import {resetInput, updateInput, validateInput} from "./common.js";


class RegisterService {
    static init() {
        let service = new RegisterService();
        document.addEventListener("DOMContentLoaded", () => service.onInit());
    }

    onInit() {
        this.form = document.querySelector("#form");
        this.emailInput = this.form.elements.namedItem("email");
        this.passwordInput = this.form.elements.namedItem("password");
        this.passwordCheck = this.form.elements.namedItem("password-check");
        this.nicknameInput = this.form.elements.namedItem("nickname");
        this.phoneInput = this.form.elements.namedItem("contact");
        this.birthInput = this.form.elements.namedItem("birth");
        this.privacyConsent = this.form.elements.namedItem("privacy-consent");
        this.birthInput.addEventListener("click", () => resetInput(this.birthInput));
        this.emailInput.addEventListener("input", () => this.validateForm());
        this.passwordInput.addEventListener("input", () => this.validateForm());
        this.passwordCheck.addEventListener("input", () => this.validateForm());
        this.nicknameInput.addEventListener("input", () => this.validateForm());
        this.phoneInput.addEventListener("input", () => this.validateForm());
        this.birthInput.addEventListener("input", () => this.validateForm());
        this.privacyConsent.addEventListener("input", () => this.validateForm());
        this.form.addEventListener("submit", e => this.onSubmit(e));
    }

    async onSubmit(event) {
        event.preventDefault();

        if (this.validateForm()) {
            let r = await fetch("/member/register", {
                method: "POST",
                body: new FormData(this.form)
            });
            let json = await r.json();

            if (r.ok) {
                location.href = "/";
            } else {
                this.onError(json);
            }
        }
    }

    onError(data) {
        let status = parseInt(data["status"]);

        switch (status) {
            case 1:  // INCOMPLETE_FORM
                window.alert("누락된 내용이 있습니다.");
                break;
            case 2:  // SHORT_PASSWORD
                updateInput(this.passwordInput, false);
                window.alert("비밀번호는 최소 8글자로 구성해야 합니다.")
                break;
            case 3:  // DUPLICATE_EMAIL
                updateInput(this.emailInput, false);
                window.alert("본 이메일은 이미 사용중입니다.");
                break;
            case 4:  // DUPLICATE_NAME
                updateInput(this.nicknameInput, false);
                window.alert("본 이름은 이미 사용중입니다.");
                break;
            default:
                window.alert("서버 오류!");
        }
    }

    validateForm() {
        try {
            let email = this.emailInput.value;
            let password1 = this.passwordInput.value;
            let password2 = this.passwordCheck.value;
            let nickname = this.nicknameInput.value;
            let phone = this.phoneInput.value;
            let birth = this.birthInput.value;
            let consent = this.privacyConsent.checked;

            validateInput(this.emailInput, (email && email.includes("@")))
            validateInput(this.passwordInput, (password1 && password1.length >= 8))
            validateInput(this.passwordCheck, (password1 === password2))
            validateInput(this.nicknameInput, nickname)
            validateInput(this.phoneInput, (phone && phone.match(/^[0-9]+$/)))
            validateInput(this.birthInput, birth)
            validateInput(this.privacyConsent, consent);
        } catch (e) {
            return false;
        }
        return true;
    }
}

RegisterService.init();
