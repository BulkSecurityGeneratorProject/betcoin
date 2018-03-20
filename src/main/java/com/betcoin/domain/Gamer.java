package com.betcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Gamer.
 */
@Entity
@Table(name = "gamer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gamer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "email")
    private String email;

    @Column(name = "points")
    private Integer points;

    @Column(name = "lastconnexion")
    private LocalDate lastconnexion;

    @Column(name = "isadmin")
    private Boolean isadmin;

    @OneToMany(mappedBy = "gamer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pronostic> pronostics = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Gamer pseudo(String pseudo) {
        this.pseudo = pseudo;
        return this;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public Gamer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPoints() {
        return points;
    }

    public Gamer points(Integer points) {
        this.points = points;
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDate getLastconnexion() {
        return lastconnexion;
    }

    public Gamer lastconnexion(LocalDate lastconnexion) {
        this.lastconnexion = lastconnexion;
        return this;
    }

    public void setLastconnexion(LocalDate lastconnexion) {
        this.lastconnexion = lastconnexion;
    }

    public Boolean isIsadmin() {
        return isadmin;
    }

    public Gamer isadmin(Boolean isadmin) {
        this.isadmin = isadmin;
        return this;
    }

    public void setIsadmin(Boolean isadmin) {
        this.isadmin = isadmin;
    }

    public Set<Pronostic> getPronostics() {
        return pronostics;
    }

    public Gamer pronostics(Set<Pronostic> pronostics) {
        this.pronostics = pronostics;
        return this;
    }

    public Gamer addPronostics(Pronostic pronostic) {
        this.pronostics.add(pronostic);
        pronostic.setGamer(this);
        return this;
    }

    public Gamer removePronostics(Pronostic pronostic) {
        this.pronostics.remove(pronostic);
        pronostic.setGamer(null);
        return this;
    }

    public void setPronostics(Set<Pronostic> pronostics) {
        this.pronostics = pronostics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Gamer gamer = (Gamer) o;
        if (gamer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gamer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gamer{" +
            "id=" + getId() +
            ", pseudo='" + getPseudo() + "'" +
            ", email='" + getEmail() + "'" +
            ", points='" + getPoints() + "'" +
            ", lastconnexion='" + getLastconnexion() + "'" +
            ", isadmin='" + isIsadmin() + "'" +
            "}";
    }
}
