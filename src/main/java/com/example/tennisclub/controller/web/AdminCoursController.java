package com.example.tennisclub.controller.web;

import com.example.tennisclub.entity.CoursCollectif;
import com.example.tennisclub.repository.CoursCollectifRepository;
import com.example.tennisclub.repository.EntraineurRepository;
import com.example.tennisclub.repository.TerrainRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/cours")
public class AdminCoursController {

    private final CoursCollectifRepository coursRepo;
    private final EntraineurRepository entraineurRepo;
    private final TerrainRepository terrainRepo;

    public AdminCoursController(CoursCollectifRepository coursRepo,
                                EntraineurRepository entraineurRepo,
                                TerrainRepository terrainRepo) {
        this.coursRepo = coursRepo;
        this.entraineurRepo = entraineurRepo;
        this.terrainRepo = terrainRepo;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("coursList", coursRepo.findAll());
        return "admin/cours/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("cours", new CoursCollectif());
        model.addAttribute("entraineurs", entraineurRepo.findAll());
        model.addAttribute("terrains", terrainRepo.findAll());
        return "admin/cours/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        CoursCollectif cours = coursRepo.findById(id).orElseThrow();
        model.addAttribute("cours", cours);
        model.addAttribute("entraineurs", entraineurRepo.findAll());
        model.addAttribute("terrains", terrainRepo.findAll());
        return "admin/cours/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("cours") CoursCollectif cours) {
        coursRepo.save(cours);
        return "redirect:/admin/cours";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        coursRepo.deleteById(id);
        return "redirect:/admin/cours";
    }
}