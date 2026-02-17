package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "razas")
public class Raza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(length = 150)
    private String habilidadEspecial;

    @Column(length = 100)
    private String reinoOrigen;

    private Integer esperanzaVida;

    // Una raza agrupa a varios personajes
    @OneToMany(mappedBy = "raza", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Personaje> personajes = new ArrayList<>();

    public Raza() {}

    public Raza(String nombre, String habilidadEspecial, String reinoOrigen, Integer esperanzaVida) {
        this.nombre = nombre;
        this.habilidadEspecial = habilidadEspecial;
        this.reinoOrigen = reinoOrigen;
        this.esperanzaVida = esperanzaVida;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getHabilidadEspecial() { return habilidadEspecial; }
    public void setHabilidadEspecial(String habilidadEspecial) { this.habilidadEspecial = habilidadEspecial; }

    public String getReinoOrigen() { return reinoOrigen; }
    public void setReinoOrigen(String reinoOrigen) { this.reinoOrigen = reinoOrigen; }

    public Integer getEsperanzaVida() { return esperanzaVida; }
    public void setEsperanzaVida(Integer esperanzaVida) { this.esperanzaVida = esperanzaVida; }

    public List<Personaje> getPersonajes() { return personajes; }
    public void setPersonajes(List<Personaje> personajes) { this.personajes = personajes; }

    // Vincula un personaje a esta raza manteniendo la relacion bidireccional
    public void addPersonaje(Personaje personaje) {
        personajes.add(personaje);
        personaje.setRaza(this);
    }

    @Override
    public String toString() {
        return nombre + " (Origen: " + reinoOrigen + ", Esperanza de vida: " + esperanzaVida + ")";
    }
}