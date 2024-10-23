package io.javabrains.springsecurityjwt.models;



import javax.persistence.*;

@Entity
@Table(name = "telefonosnew") // Cambia el nombre de la tabla según tu esquema
public class PhoneNewDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "citycode", nullable = false)
    private String citycode;

    @Column(name = "contrycode", nullable = false)
    private String contrycode;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserNew user;

    // Getters y setters

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

    public UserNew getUserNew() {
        return user;
    }

    public void setUser(UserNew user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", citycode='" + citycode + '\'' +
                ", contrycode='" + contrycode + '\'' +
                ", user=" + user +
                '}';
    }
}

