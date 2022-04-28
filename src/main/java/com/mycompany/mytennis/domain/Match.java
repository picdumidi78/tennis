package com.mycompany.mytennis.domain;

import com.mycompany.mytennis.domain.enumeration.Resultat;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Match.
 */
@Entity
@Table(name = "jhi_match")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "player_one_name")
    private String playerOneName;

    @Column(name = "player_one_score")
    private Double playerOneScore;

    @Column(name = "player_one_odd")
    private Double playerOneOdd;

    @Column(name = "player_two_name")
    private String playerTwoName;

    @Column(name = "player_two_score")
    private Double playerTwoScore;

    @Column(name = "player_two_odd")
    private Double playerTwoOdd;

    @Enumerated(EnumType.STRING)
    @Column(name = "prediction")
    private Resultat prediction;

    @Enumerated(EnumType.STRING)
    @Column(name = "actual_result")
    private Resultat actualResult;

    @Column(name = "bet_amount")
    private Double betAmount;

    @Column(name = "potential_gain")
    private Double potentialGain;

    @Column(name = "gain")
    private Double gain;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Match id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerOneName() {
        return this.playerOneName;
    }

    public Match playerOneName(String playerOneName) {
        this.setPlayerOneName(playerOneName);
        return this;
    }

    public void setPlayerOneName(String playerOneName) {
        this.playerOneName = playerOneName;
    }

    public Double getPlayerOneScore() {
        return this.playerOneScore;
    }

    public Match playerOneScore(Double playerOneScore) {
        this.setPlayerOneScore(playerOneScore);
        return this;
    }

    public void setPlayerOneScore(Double playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public Double getPlayerOneOdd() {
        return this.playerOneOdd;
    }

    public Match playerOneOdd(Double playerOneOdd) {
        this.setPlayerOneOdd(playerOneOdd);
        return this;
    }

    public void setPlayerOneOdd(Double playerOneOdd) {
        this.playerOneOdd = playerOneOdd;
    }

    public String getPlayerTwoName() {
        return this.playerTwoName;
    }

    public Match playerTwoName(String playerTwoName) {
        this.setPlayerTwoName(playerTwoName);
        return this;
    }

    public void setPlayerTwoName(String playerTwoName) {
        this.playerTwoName = playerTwoName;
    }

    public Double getPlayerTwoScore() {
        return this.playerTwoScore;
    }

    public Match playerTwoScore(Double playerTwoScore) {
        this.setPlayerTwoScore(playerTwoScore);
        return this;
    }

    public void setPlayerTwoScore(Double playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }

    public Double getPlayerTwoOdd() {
        return this.playerTwoOdd;
    }

    public Match playerTwoOdd(Double playerTwoOdd) {
        this.setPlayerTwoOdd(playerTwoOdd);
        return this;
    }

    public void setPlayerTwoOdd(Double playerTwoOdd) {
        this.playerTwoOdd = playerTwoOdd;
    }

    public Resultat getPrediction() {
        return this.prediction;
    }

    public Match prediction(Resultat prediction) {
        this.setPrediction(prediction);
        return this;
    }

    public void setPrediction(Resultat prediction) {
        this.prediction = prediction;
    }

    public Resultat getActualResult() {
        return this.actualResult;
    }

    public Match actualResult(Resultat actualResult) {
        this.setActualResult(actualResult);
        return this;
    }

    public void setActualResult(Resultat actualResult) {
        this.actualResult = actualResult;
    }

    public Double getBetAmount() {
        return this.betAmount;
    }

    public Match betAmount(Double betAmount) {
        this.setBetAmount(betAmount);
        return this;
    }

    public void setBetAmount(Double betAmount) {
        this.betAmount = betAmount;
    }

    public Double getPotentialGain() {
        return this.potentialGain;
    }

    public Match potentialGain(Double potentialGain) {
        this.setPotentialGain(potentialGain);
        return this;
    }

    public void setPotentialGain(Double potentialGain) {
        this.potentialGain = potentialGain;
    }

    public Double getGain() {
        return this.gain;
    }

    public Match gain(Double gain) {
        this.setGain(gain);
        return this;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Match)) {
            return false;
        }
        return id != null && id.equals(((Match) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Match{" +
            "id=" + getId() +
            ", playerOneName='" + getPlayerOneName() + "'" +
            ", playerOneScore=" + getPlayerOneScore() +
            ", playerOneOdd=" + getPlayerOneOdd() +
            ", playerTwoName='" + getPlayerTwoName() + "'" +
            ", playerTwoScore=" + getPlayerTwoScore() +
            ", playerTwoOdd=" + getPlayerTwoOdd() +
            ", prediction='" + getPrediction() + "'" +
            ", actualResult='" + getActualResult() + "'" +
            ", betAmount=" + getBetAmount() +
            ", potentialGain=" + getPotentialGain() +
            ", gain=" + getGain() +
            "}";
    }
}
