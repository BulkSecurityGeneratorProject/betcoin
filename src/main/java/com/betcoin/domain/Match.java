package com.betcoin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.betcoin.domain.enumeration.Phase;

/**
 * A Match.
 */
@Entity
@Table(name = "jhi_match")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "score_1")
    private Integer score1;

    @Column(name = "score_2")
    private Integer score2;

    @Enumerated(EnumType.STRING)
    @Column(name = "phase")
    private Phase phase;

    @ManyToOne
    private Team team1;

    @ManyToOne
    private Team team2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Match date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getScore1() {
        return score1;
    }

    public Match score1(Integer score1) {
        this.score1 = score1;
        return this;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public Match score2(Integer score2) {
        this.score2 = score2;
        return this;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Phase getPhase() {
        return phase;
    }

    public Match phase(Phase phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Team getTeam1() {
        return team1;
    }

    public Match team1(Team team) {
        this.team1 = team;
        return this;
    }

    public void setTeam1(Team team) {
        this.team1 = team;
    }

    public Team getTeam2() {
        return team2;
    }

    public Match team2(Team team) {
        this.team2 = team;
        return this;
    }

    public void setTeam2(Team team) {
        this.team2 = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Match match = (Match) o;
        if (match.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), match.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", score1='" + getScore1() + "'" +
            ", score2='" + getScore2() + "'" +
            ", phase='" + getPhase() + "'" +
            "}";
    }
}
