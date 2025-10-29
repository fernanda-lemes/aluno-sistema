package com.exemplo.alunos.controller;

import com.exemplo.alunos.model.Aluno;
import com.exemplo.alunos.service.AlunoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import java.util.Optional;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public String listar(Model model,
                         @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Aluno> page = alunoService.listarPagina(pageable);
        model.addAttribute("page", page);
        model.addAttribute("alunos", page.getContent());
        return "listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "cadastrar";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Aluno aluno, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cadastrar";
        }
        if (alunoService.existePorMatricula(aluno.getMatricula())) {
            result.rejectValue("matricula", "duplicate", "Já existe um aluno com esta matrícula.");
            return "cadastrar";
        }
        alunoService.salvar(aluno);
        redirectAttributes.addFlashAttribute("success", "Aluno cadastrado com sucesso.");
        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Aluno> aluno = alunoService.buscarPorId(id);
        if (aluno.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Aluno não encontrado.");
            return "redirect:/alunos";
        }
        model.addAttribute("aluno", aluno.get());
        return "editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute Aluno aluno, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "editar"; 
        }
        Optional<Aluno> existente = alunoService.buscarPorMatricula(aluno.getMatricula());
        if (existente.isPresent() && !existente.get().getId().equals(id)) {
            result.rejectValue("matricula", "duplicate", "Já existe um aluno com esta matrícula.");
            return "editar";
        }
        aluno.setId(id);
        alunoService.salvar(aluno);
        redirectAttributes.addFlashAttribute("success", "Aluno atualizado com sucesso.");
        return "redirect:/alunos";
    }

    @GetMapping("/visualizar/{id}")
    public String visualizar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Aluno> aluno = alunoService.buscarPorId(id);
        if (aluno.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Aluno não encontrado.");
            return "redirect:/alunos";
        }
        model.addAttribute("aluno", aluno.get());
        return "visualizar";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Aluno> aluno = alunoService.buscarPorId(id);
        if (aluno.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Aluno não encontrado.");
            return "redirect:/alunos";
        }
        alunoService.excluir(id);
        redirectAttributes.addFlashAttribute("success", "Aluno excluído com sucesso.");
        return "redirect:/alunos";
    }
}
