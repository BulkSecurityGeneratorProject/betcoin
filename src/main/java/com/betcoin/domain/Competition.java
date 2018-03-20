package com.betcoin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Competition.
 */
@Entity
@Table(name = "competition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private Team winner;

    @OneToOne
    @JoinColumn(unique = true)
    private Team second;

    @OneToOne
    @JoinColumn(unique = true)
    private Team third;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Competition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getWinner() {
        return winner;
    }

    public Competition winner(Team team) {
        this.winner = team;
        return this;
    }

    public void setWinner(Team team) {
        this.winner = team;
    }

    public Team getSecond() {
        return second;
    }

    public Competition second(Team team) {
        this.second = team;
        return this;
    }

    public void setSecond(Team team) {
        this.second = team;
    }

    public Team getThird() {
        return third;
    }

    public Competition third(Team team) {
        this.third = team;
        return this;
    }

    public void setThird(Team team) {
        this.third = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Competition competition = (Competition) o;
        if (competition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), competition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Competition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
