package io.javabrains.springsecurityjwt.models;

import javax.persistence.*;

@Entity
@Table(name = "telefonos")  // Nombre de la tabla en la BD
public class Telefono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "citycode", nullable = false)
    private String citycode;

    @Column(name = "contrycode", nullable = false)
    private String contrycode;

    // Relación ManyToOne con la entidad Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getContrycode() {
        return contrycode;
    }

    public void setContrycode(String contrycode) {
        this.contrycode = contrycode;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

