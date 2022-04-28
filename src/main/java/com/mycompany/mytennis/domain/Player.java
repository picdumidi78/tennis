package com.mycompany.mytennis.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "country")
    private String country;

    @Column(name = "age")
    private Integer age;

    @Column(name = "first_serve_percentage")
    private Double firstServePercentage;

    @Column(name = "service_points_won")
    private Double servicePointsWon;

    @Column(name = "break_points_saved")
    private Double breakPointsSaved;

    @Column(name = "second_serve_return_points_won")
    private Double secondServeReturnPointsWon;

    @Column(name = "break_points_converted")
    private Double breakPointsConverted;

    @Column(name = "score")
    private Double score;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Player id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Player firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Player lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return this.country;
    }

    public Player country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getAge() {
        return this.age;
    }

    public Player age(Integer age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getFirstServePercentage() {
        return this.firstServePercentage;
    }

    public Player firstServePercentage(Double firstServePercentage) {
        this.setFirstServePercentage(firstServePercentage);
        return this;
    }

    public void setFirstServePercentage(Double firstServePercentage) {
        this.firstServePercentage = firstServePercentage;
    }

    public Double getServicePointsWon() {
        return this.servicePointsWon;
    }

    public Player servicePointsWon(Double servicePointsWon) {
        this.setServicePointsWon(servicePointsWon);
        return this;
    }

    public void setServicePointsWon(Double servicePointsWon) {
        this.servicePointsWon = servicePointsWon;
    }

    public Double getBreakPointsSaved() {
        return this.breakPointsSaved;
    }

    public Player breakPointsSaved(Double breakPointsSaved) {
        this.setBreakPointsSaved(breakPointsSaved);
        return this;
    }

    public void setBreakPointsSaved(Double breakPointsSaved) {
        this.breakPointsSaved = breakPointsSaved;
    }

    public Double getSecondServeReturnPointsWon() {
        return this.secondServeReturnPointsWon;
    }

    public Player secondServeReturnPointsWon(Double secondServeReturnPointsWon) {
        this.setSecondServeReturnPointsWon(secondServeReturnPointsWon);
        return this;
    }

    public void setSecondServeReturnPointsWon(Double secondServeReturnPointsWon) {
        this.secondServeReturnPointsWon = secondServeReturnPointsWon;
    }

    public Double getBreakPointsConverted() {
        return this.breakPointsConverted;
    }

    public Player breakPointsConverted(Double breakPointsConverted) {
        this.setBreakPointsConverted(breakPointsConverted);
        return this;
    }

    public void setBreakPointsConverted(Double breakPointsConverted) {
        this.breakPointsConverted = breakPointsConverted;
    }

    public Double getScore() {
        return this.score;
    }

    public Player score(Double score) {
        this.setScore(score);
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", country='" + getCountry() + "'" +
            ", age=" + getAge() +
            ", firstServePercentage=" + getFirstServePercentage() +
            ", servicePointsWon=" + getServicePointsWon() +
            ", breakPointsSaved=" + getBreakPointsSaved() +
            ", secondServeReturnPointsWon=" + getSecondServeReturnPointsWon() +
            ", breakPointsConverted=" + getBreakPointsConverted() +
            ", score=" + getScore() +
            "}";
    }
}
