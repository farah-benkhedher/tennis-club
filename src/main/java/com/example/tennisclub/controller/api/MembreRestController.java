package com.example.tennisclub.controller.api;

import com.example.tennisclub.entity.Membre;
import com.example.tennisclub.repository.MembreRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membres")
public class MembreRestController {

    private final MembreRepository repo;

    public MembreRestController(MembreRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Membre> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Membre> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Membre create(@RequestBody Membre m) {
        return repo.save(m);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Membre> update(@PathVariable Long id, @RequestBody Membre m) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        m.setId(id);
        return ResponseEntity.ok(repo.save(m));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
