package com.example.tennisclub.controller.web;

import com.example.tennisclub.entity.CoursCollectif;
import com.example.tennisclub.repository.CoursCollectifRepository;
import com.example.tennisclub.repository.EntraineurRepository;
import com.example.tennisclub.repository.TerrainRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CoursWebController {

    private final CoursCollectifRepository coursRepo;
    private final EntraineurRepository entraineurRepo;
    private final TerrainRepository terrainRepo;

    public CoursWebController(CoursCollectifRepository coursRepo,
                              EntraineurRepository entraineurRepo,
                              TerrainRepository terrainRepo) {
        this.coursRepo = coursRepo;
        this.entraineurRepo = entraineurRepo;
        this.terrainRepo = terrainRepo;
    }

    // ✅ LISTE UNIQUE /cours
    @GetMapping("/cours")
    public String list(Model model, Authentication auth) {
        model.addAttribute("coursList", coursRepo.findAll());

        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        return "cours/liste";
    }

    // ✅ FORM AJOUT /cours/new (accessible ADMIN)
    @GetMapping("/cours/new")
    public String newForm(Model model) {
        model.addAttribute("cours", new CoursCollectif());
        model.addAttribute("entraineurs", entraineurRepo.findAll());
        model.addAttribute("terrains", terrainRepo.findAll());
        return "cours/form";
    }

    // ✅ FORM EDIT /cours/edit/{id} (accessible ADMIN)
    @GetMapping("/cours/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        CoursCollectif cours = coursRepo.findById(id).orElse(null);
        if (cours == null) return "redirect:/cours";

        model.addAttribute("cours", cours);
        model.addAttribute("entraineurs", entraineurRepo.findAll());
        model.addAttribute("terrains", terrainRepo.findAll());
        return "cours/form";
    }

    // ✅ SAVE (AJOUT + MODIF) (accessible ADMIN)
    @PostMapping("/cours/save")
    public String save(@ModelAttribute("cours") CoursCollectif cours) {

        // recharger entraineur / terrain depuis la BD (important)
        if (cours.getEntraineur() != null && cours.getEntraineur().getId() != null) {
            cours.setEntraineur(
                    entraineurRepo.findById(cours.getEntraineur().getId()).orElse(null)
            );
        }

        if (cours.getTerrain() != null && cours.getTerrain().getId() != null) {
            cours.setTerrain(
                    terrainRepo.findById(cours.getTerrain().getId()).orElse(null)
            );
        }

        coursRepo.save(cours);
        return "redirect:/cours";
    }


    // ✅ DELETE (accessible ADMIN)
    @GetMapping("/cours/delete/{id}")
    public String delete(@PathVariable Long id) {
        coursRepo.deleteById(id);
        return "redirect:/cours";
    }
}
