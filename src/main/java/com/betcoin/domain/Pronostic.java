package com.betcoin.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.betcoin.domain.enumeration.Status;

/**
 * A Pronostic.
 */
@Entity
@Table(name = "pronostic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pronostic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "resourceid")
    private Long resourceid;

    @Column(name = "score_1")
    private Integer score1;

    @Column(name = "score_2")
    private Integer score2;

    @Column(name = "winner")
    private Long winner;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne
    private Gamer gamer;

    @ManyToOne
    private Pronotype pronotype;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Pronostic date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getResourceid() {
        return resourceid;
    }

    public Pronostic resourceid(Long resourceid) {
        this.resourceid = resourceid;
        return this;
    }

    public void setResourceid(Long resourceid) {
        this.resourceid = resourceid;
    }

    public Integer getScore1() {
        return score1;
    }

    public Pronostic score1(Integer score1) {
        this.score1 = score1;
        return this;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public Pronostic score2(Integer score2) {
        this.score2 = score2;
        return this;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Long getWinner() {
        return winner;
    }

    public Pronostic winner(Long winner) {
        this.winner = winner;
        return this;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }

    public Status getStatus() {
        return status;
    }

    public Pronostic status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Gamer getGamer() {
        return gamer;
    }

    public Pronostic gamer(Gamer gamer) {
        this.gamer = gamer;
        return this;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }

    public Pronotype getPronotype() {
        return pronotype;
    }

    public Pronostic pronotype(Pronotype pronotype) {
        this.pronotype = pronotype;
        return this;
    }

    public void setPronotype(Pronotype pronotype) {
        this.pronotype = pronotype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pronostic pronostic = (Pronostic) o;
        if (pronostic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pronostic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pronostic{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", resourceid='" + getResourceid() + "'" +
            ", score1='" + getScore1() + "'" +
            ", score2='" + getScore2() + "'" +
            ", winner='" + getWinner() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
