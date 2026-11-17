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

    document.querySelectorAll("[data-booking-form]").forEach((form) => {
        form.addEventListener("submit", (event) => {
            event.preventDefault();

            const message = form.querySelector("[data-form-message]");
            if (message) {
                message.textContent = "O formulário está pronto, mas o salvamento de agendamentos ainda não foi conectado ao banco.";
            }
        });
    });

    document.querySelectorAll(".nav-link-placeholder").forEach((link) => {
        link.addEventListener("click", (event) => event.preventDefault());
    });
});