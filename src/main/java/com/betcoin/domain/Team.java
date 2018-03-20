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
 * A Team.
 */
@Entity
@Table(name = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "team1")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Match> matchshomes = new HashSet<>();

    @OneToMany(mappedBy = "team2")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Match> matchsaways = new HashSet<>();

    @ManyToOne
    private Group group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public Team color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<Match> getMatchshomes() {
        return matchshomes;
    }

    public Team matchshomes(Set<Match> matches) {
        this.matchshomes = matches;
        return this;
    }

    public Team addMatchshome(Match match) {
        this.matchshomes.add(match);
        match.setTeam1(this);
        return this;
    }

    public Team removeMatchshome(Match match) {
        this.matchshomes.remove(match);
        match.setTeam1(null);
        return this;
    }

    public void setMatchshomes(Set<Match> matches) {
        this.matchshomes = matches;
    }

    public Set<Match> getMatchsaways() {
        return matchsaways;
    }

    public Team matchsaways(Set<Match> matches) {
        this.matchsaways = matches;
        return this;
    }

    public Team addMatchsaway(Match match) {
        this.matchsaways.add(match);
        match.setTeam2(this);
        return this;
    }

    public Team removeMatchsaway(Match match) {
        this.matchsaways.remove(match);
        match.setTeam2(null);
        return this;
    }

    public void setMatchsaways(Set<Match> matches) {
        this.matchsaways = matches;
    }

    public Group getGroup() {
        return group;
    }

    public Team group(Group group) {
        this.group = group;
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }
}
