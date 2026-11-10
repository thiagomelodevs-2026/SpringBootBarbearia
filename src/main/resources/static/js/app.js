document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll("[data-auth-form]").forEach((form) => {
        form.addEventListener("submit", (event) => {
            event.preventDefault();

            const message = form.querySelector("[data-form-message]");
            if (message) {
                message.textContent = "Esta função será ativada quando a autenticação for conectada ao sistema.";
            }
        });
    });

    document.querySelectorAll(".nav-link-placeholder").forEach((link) => {
        link.addEventListener("click", (event) => event.preventDefault());
    });
});