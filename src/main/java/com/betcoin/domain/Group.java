package com.betcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Group.
 */
@Entity
@Table(name = "jhi_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Group implements Serializable {

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

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Team> teams = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Group name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getWinner() {
        return winner;
    }

    public Group winner(Team team) {
        this.winner = team;
        return this;
    }

    public void setWinner(Team team) {
        this.winner = team;
    }

    public Team getSecond() {
        return second;
    }

    public Group second(Team team) {
        this.second = team;
        return this;
    }

    public void setSecond(Team team) {
        this.second = team;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Group teams(Set<Team> teams) {
        this.teams = teams;
        return this;
    }

    public Group addTeams(Team team) {
        this.teams.add(team);
        team.setGroup(this);
        return this;
    }

    public Group removeTeams(Team team) {
        this.teams.remove(team);
        team.setGroup(null);
        return this;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Group group = (Group) o;
        if (group.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), group.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
