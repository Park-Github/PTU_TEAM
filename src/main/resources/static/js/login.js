import {resetInput, validateInput, ResponseError} from "./common.js";


class LoginService {
    static init() {
        let service = new LoginService();
        document.addEventListener("DOMContentLoaded", () => service.onInit());
    }

    onInit() {
        this.form = document.querySelector("#form");
        this.emailInput = this.form.elements.namedItem("username");
        this.passwordInput = this.form.elements.namedItem("password");
        this.emailInput.addEventListener("input", () => this.validateForm());
        this.passwordInput.addEventListener("input", () => this.validateForm());
        this.form.addEventListener("submit", e => this.onSubmit(e));
    }

    async onSubmit(event) {
        event.preventDefault();

        if (this.validateForm()) {
            let response = await fetch("/login", {
                method: "POST",
                body: new FormData(this.form),
            });

            try {
                this.validateResponse(response);
                location.href = response.url;
            } catch (e) {
                window.alert(e.message);
            }
        }
    }

    validateResponse(response) {
        let url = response.url;
        let t = url.indexOf('?') + 1;
        let param = url.slice(t);

        if (!response.ok && !response.redirected) {
            throw new ResponseError("서버 오류가 발생했습니다!");
        } else if (param === "error") {
            throw new ResponseError("비밀번호가 틀렸거나 존재하지 않는 계정입니다!");
        } else if (param === "logout") {
            throw new ResponseError("성공적으로 로그아웃 되었습니다.");
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
