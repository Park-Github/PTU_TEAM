import {resetInput, validateInput} from "./common.js";


class LoginService {
    static init() {
        let service = new LoginService();
        document.addEventListener("DOMContentLoaded", () => service.onInit());
    }

    onInit() {
        this.form = document.querySelector("#form");
        this.emailInput = this.form.elements.namedItem("email");
        this.passwordInput = this.form.elements.namedItem("password");
        this.emailInput.addEventListener("input", () => this.validateForm());
        this.passwordInput.addEventListener("input", () => this.validateForm());
        this.form.addEventListener("submit", e => this.onSubmit(e));
    }

    async onSubmit(event) {
        event.preventDefault();

        if (this.validateForm()) {
            let response = await fetch("/member/login", {
                method: "POST",
                body: new FormData(this.form),
            });

            if (response.ok) {
                history.back();
            } else {
                let json = await response.json();
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
            case 2:  // CREDENTIAL_ERROR
                window.alert("비밀번호가 틀렸거나 존재하지 않는 계정입니다!");
                break;
            default:
                window.alert("서버 오류!");
        }
    }

    validateForm() {
        try {
            let email = this.emailInput.value;
            let pwd = this.passwordInput.value;

            validateInput(this.emailInput, (email && email.includes("@")))
            validateInput(this.passwordInput, pwd);
            resetInput(this.emailInput);
            resetInput(this.passwordInput);
        } catch (e) {
            return false;
        }
        return true;
    }
}

LoginService.init();
