package com.mycompany.mytennis.repository;

import com.mycompany.mytennis.domain.Player;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PlayerRepositoryWithBagRelationshipsImpl implements PlayerRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Player> fetchBagRelationships(Optional<Player> player) {
        return player.map(this::fetchMatches);
    }

    @Override
    public Page<Player> fetchBagRelationships(Page<Player> players) {
        return new PageImpl<>(fetchBagRelationships(players.getContent()), players.getPageable(), players.getTotalElements());
    }

    @Override
    public List<Player> fetchBagRelationships(List<Player> players) {
        return Optional.of(players).map(this::fetchMatches).orElse(Collections.emptyList());
    }

    Player fetchMatches(Player result) {
        return entityManager
            .createQuery("select player from Player player left join fetch player.matches where player is :player", Player.class)
            .setParameter("player", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Player> fetchMatches(List<Player> players) {
        return entityManager
            .createQuery("select distinct player from Player player left join fetch player.matches where player in :players", Player.class)
            .setParameter("players", players)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
