package com.example.tennisclub.controller.api;

import com.example.tennisclub.entity.Terrain;
import com.example.tennisclub.repository.TerrainRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/terrains")
public class TerrainRestController {

    private final TerrainRepository repo;

    public TerrainRestController(TerrainRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Terrain> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Terrain> one(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Terrain create(@RequestBody Terrain t) {
        return repo.save(t);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Terrain> update(@PathVariable Long id, @RequestBody Terrain t) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        t.setId(id);
        return ResponseEntity.ok(repo.save(t));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
