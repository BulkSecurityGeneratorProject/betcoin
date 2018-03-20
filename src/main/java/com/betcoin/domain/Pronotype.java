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
 * A Pronotype.
 */
@Entity
@Table(name = "pronotype")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pronotype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_label")
    private String label;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "points")
    private Integer points;

    @OneToMany(mappedBy = "pronotype")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pronostic> pronostics = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Pronotype label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Pronotype expirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getPoints() {
        return points;
    }

    public Pronotype points(Integer points) {
        this.points = points;
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Set<Pronostic> getPronostics() {
        return pronostics;
    }

    public Pronotype pronostics(Set<Pronostic> pronostics) {
        this.pronostics = pronostics;
        return this;
    }

    public Pronotype addPronostic(Pronostic pronostic) {
        this.pronostics.add(pronostic);
        pronostic.setPronotype(this);
        return this;
    }

    public Pronotype removePronostic(Pronostic pronostic) {
        this.pronostics.remove(pronostic);
        pronostic.setPronotype(null);
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
        Pronotype pronotype = (Pronotype) o;
        if (pronotype.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pronotype.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pronotype{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", points='" + getPoints() + "'" +
            "}";
    }
}
