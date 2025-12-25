package com.example.tennisclub.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class CoursCollectif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private String niveau;

    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    private int nbPlaces;

    @ManyToOne
    private Entraineur entraineur;

    @ManyToOne
    private Terrain terrain;

    @ManyToMany
    @JoinTable(
            name = "cours_membres",
            joinColumns = @JoinColumn(name = "cours_id"),
            inverseJoinColumns = @JoinColumn(name = "membre_id")
    )
    private Set<Membre> membres = new HashSet<>();

    // ---- getters & setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }

    public LocalTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }

    public int getNbPlaces() { return nbPlaces; }
    public void setNbPlaces(int nbPlaces) { this.nbPlaces = nbPlaces; }

    public Entraineur getEntraineur() { return entraineur; }
    public void setEntraineur(Entraineur entraineur) { this.entraineur = entraineur; }

    public Terrain getTerrain() { return terrain; }
    public void setTerrain(Terrain terrain) { this.terrain = terrain; }

    public Set<Membre> getMembres() { return membres; }
    public void setMembres(Set<Membre> membres) { this.membres = membres; }
}
