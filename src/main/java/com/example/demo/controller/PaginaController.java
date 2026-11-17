package com.example.demo.controller;

import com.example.demo.service.BarbeiroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
public class PaginaController {

    private final BarbeiroService barbeiroService;

    public PaginaController(BarbeiroService barbeiroService) {
        this.barbeiroService = barbeiroService;
    }

    @GetMapping({"/", "/agendar"})
    public String paginaInicial() {
        return "agendar/index";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cliente/cadastro";
    }

    @GetMapping("/agendamento")
    public String agendamento(Model model) {
        model.addAttribute("barbeiros", barbeiroService.listar());
        return "cliente/agendamento";
    }

    @GetMapping("/servicos")
    public String servicos() {
        return "servicos/index";
    }

    @GetMapping("/equipe")
    public String equipe(Model model) {
        model.addAttribute("barbeiros", barbeiroService.listar());
        return "equipe/index";
    }

    @GetMapping("/trabalhe-conosco")
    public String trabalheConosco() {
        return "vagas/index";
    }

    @GetMapping("/admin/login")
    public String loginAdmin() {
        return "admin/login";
    }

    @PostMapping("/admin/login")
    public String autenticarAdmin(@RequestParam String username,
                                  @RequestParam String password,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        if ("admin".equals(username) && "admin".equals(password)) {
            session.setAttribute("adminLogado", true);
            return "redirect:/admin/painel";
        }
        redirectAttributes.addFlashAttribute("erroLogin", "Usuário ou senha inválidos.");
        return "redirect:/admin/login";
    }

    @GetMapping({"/admin/painel", "/admin/painel-agenda"})
    public String painelAdmin(HttpSession session, Model model) {
        if (!Boolean.TRUE.equals(session.getAttribute("adminLogado"))) {
            return "redirect:/admin/login";
        }
        model.addAttribute("barbeiros", barbeiroService.listar());
        model.addAttribute("agendamentos", Collections.emptyList());
        return "admin/painel-agenda";
    }

    @GetMapping("/admin/logout")
    public String logoutAdmin(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/cliente/status")
    public String statusCliente(@RequestParam(required = false) String codigo, Model model) {
        model.addAttribute("codigo", codigo);
        model.addAttribute("agendamento", null);
        return "cliente/status";
    }

    @GetMapping("/cliente/cancelar")
    public String cancelarCliente(@RequestParam(required = false) Long agendamentoId, Model model) {
        model.addAttribute("agendamentoId", agendamentoId);
        return "cliente/cancelar";
    }

    @PostMapping("/cliente/cancelar")
    public String confirmarCancelamento(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagem",
                "A solicitação de cancelamento foi registrada. O agendamento ainda não possui persistência criada.");
        return "redirect:/cliente/status";
    }

    @GetMapping("/cliente/remarcar")
    public String remarcarCliente(@RequestParam(required = false) Long agendamentoId, Model model) {
        model.addAttribute("agendamentoId", agendamentoId);
        return "cliente/remarcar";
    }

    @PostMapping("/cliente/remarcar")
    public String confirmarRemarcacao(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("mensagem",
                "A solicitação de remarcação foi registrada. O agendamento ainda não possui persistência criada.");
        return "redirect:/cliente/status";
    }
}
