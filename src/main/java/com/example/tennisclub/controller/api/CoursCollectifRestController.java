package com.example.tennisclub.controller.api;

import com.example.tennisclub.entity.CoursCollectif;
import com.example.tennisclub.entity.Membre;
import com.example.tennisclub.repository.CoursCollectifRepository;
import com.example.tennisclub.repository.MembreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cours")
public class CoursCollectifRestController {

    private final CoursCollectifRepository coursRepo;
    private final MembreRepository membreRepo;

    public CoursCollectifRestController(CoursCollectifRepository coursRepo, MembreRepository membreRepo) {
        this.coursRepo = coursRepo;
        this.membreRepo = membreRepo;
    }

    @GetMapping
    public List<CoursCollectif> all() { return coursRepo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<CoursCollectif> one(@PathVariable Long id) {
        return coursRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CoursCollectif create(@RequestBody CoursCollectif c) {
        return coursRepo.save(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoursCollectif> update(@PathVariable Long id, @RequestBody CoursCollectif c) {
        if (!coursRepo.existsById(id)) return ResponseEntity.notFound().build();
        c.setId(id);
        return ResponseEntity.ok(coursRepo.save(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!coursRepo.existsById(id)) return ResponseEntity.notFound().build();
        coursRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Inscription: /api/cours/{idCours}/inscrire/{idMembre}
    @PostMapping("/{idCours}/inscrire/{idMembre}")
    public ResponseEntity<String> inscrire(@PathVariable Long idCours, @PathVariable Long idMembre) {

        CoursCollectif cours = coursRepo.findById(idCours).orElse(null);
        Membre membre = membreRepo.findById(idMembre).orElse(null);

        if (cours == null || membre == null) return ResponseEntity.notFound().build();

        // Vérif simple capacité
        if (cours.getMembres().size() >= cours.getNbPlaces()) {
            return ResponseEntity.badRequest().body("Cours complet");
        }

        cours.getMembres().add(membre);
        coursRepo.save(cours);

        return ResponseEntity.ok("Inscription OK");
    }
}
