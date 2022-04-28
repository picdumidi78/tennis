package com.mycompany.mytennis.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "player_two_name")
    private String playerTwoName;

    @Column(name = "player_two_score")
    private Double playerTwoScore;

    @Column(name = "predication")
    private String predication;

    @ManyToMany
    @JoinTable(
        name = "rel_jhi_match__player",
        joinColumns = @JoinColumn(name = "jhi_match_id"),
        inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "matches" }, allowSetters = true)
    private Set<Player> players = new HashSet<>();

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

    public String getPredication() {
        return this.predication;
    }

    public Match predication(String predication) {
        this.setPredication(predication);
        return this;
    }

    public void setPredication(String predication) {
        this.predication = predication;
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Match players(Set<Player> players) {
        this.setPlayers(players);
        return this;
    }

    public Match addPlayer(Player player) {
        this.players.add(player);
        player.getMatches().add(this);
        return this;
    }

    public Match removePlayer(Player player) {
        this.players.remove(player);
        player.getMatches().remove(this);
        return this;
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
            ", playerTwoName='" + getPlayerTwoName() + "'" +
            ", playerTwoScore=" + getPlayerTwoScore() +
            ", predication='" + getPredication() + "'" +
            "}";
    }
}
