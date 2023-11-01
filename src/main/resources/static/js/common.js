export function updateInput(input, valid) {
    input.classList.add(valid ? "is-valid" : "is-invalid");
    input.classList.remove(valid ? "is-invalid" : "is-valid");
}

export function resetInput(input) {
    input.classList.remove("is-invalid", "is-valid");
}

export class InputError extends Error {
    constructor(message) {
        super(message);
    }
}

export function validateInput(input, expr) {
    if (expr) {
        updateInput(input, true);
    } else {
        updateInput(input, false);
        throw new InputError();
    }
}