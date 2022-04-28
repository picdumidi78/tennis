package com.mycompany.mytennis.service;

import com.mycompany.mytennis.domain.Match;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Match}.
 */
public interface MatchService {
    /**
     * Save a match.
     *
     * @param match the entity to save.
     * @return the persisted entity.
     */
    Match save(Match match);

    /**
     * Updates a match.
     *
     * @param match the entity to update.
     * @return the persisted entity.
     */
    Match update(Match match);

    /**
     * Partially updates a match.
     *
     * @param match the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Match> partialUpdate(Match match);

    /**
     * Get all the matches.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Match> findAll(Pageable pageable);

    /**
     * Get all the matches with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Match> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" match.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Match> findOne(Long id);

    /**
     * Delete the "id" match.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
