package com.example.tennisclub.controller.api;

import com.example.tennisclub.entity.Entraineur;
import com.example.tennisclub.repository.EntraineurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entraineurs")
public class EntraineurRestController {

    private final EntraineurRepository repo;

    public EntraineurRestController(EntraineurRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Entraineur> all() {
        return repo.findAll();
    }

    @PostMapping
    public Entraineur create(@RequestBody Entraineur e) {
        return repo.save(e);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entraineur> update(@PathVariable Long id, @RequestBody Entraineur e) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        e.setId(id);
        return ResponseEntity.ok(repo.save(e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
