package com.mycompany.mytennis.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.mytennis.IntegrationTest;
import com.mycompany.mytennis.domain.Match;
import com.mycompany.mytennis.domain.enumeration.Resultat;
import com.mycompany.mytennis.domain.enumeration.Resultat;
import com.mycompany.mytennis.repository.MatchRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MatchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MatchResourceIT {

    private static final String DEFAULT_PLAYER_ONE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_ONE_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PLAYER_ONE_SCORE = 1D;
    private static final Double UPDATED_PLAYER_ONE_SCORE = 2D;

    private static final Double DEFAULT_PLAYER_ONE_ODD = 1D;
    private static final Double UPDATED_PLAYER_ONE_ODD = 2D;

    private static final String DEFAULT_PLAYER_TWO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLAYER_TWO_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PLAYER_TWO_SCORE = 1D;
    private static final Double UPDATED_PLAYER_TWO_SCORE = 2D;

    private static final Double DEFAULT_PLAYER_TWO_ODD = 1D;
    private static final Double UPDATED_PLAYER_TWO_ODD = 2D;

    private static final Resultat DEFAULT_PREDICTION = Resultat.PLAYERONE;
    private static final Resultat UPDATED_PREDICTION = Resultat.PLAYERTWO;

    private static final Resultat DEFAULT_ACTUAL_RESULT = Resultat.PLAYERONE;
    private static final Resultat UPDATED_ACTUAL_RESULT = Resultat.PLAYERTWO;

    private static final Double DEFAULT_BET_AMOUNT = 1D;
    private static final Double UPDATED_BET_AMOUNT = 2D;

    private static final Double DEFAULT_POTENTIAL_GAIN = 1D;
    private static final Double UPDATED_POTENTIAL_GAIN = 2D;

    private static final Double DEFAULT_GAIN = 1D;
    private static final Double UPDATED_GAIN = 2D;

    private static final String ENTITY_API_URL = "/api/matches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMatchMockMvc;

    private Match match;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createEntity(EntityManager em) {
        Match match = new Match()
            .playerOneName(DEFAULT_PLAYER_ONE_NAME)
            .playerOneScore(DEFAULT_PLAYER_ONE_SCORE)
            .playerOneOdd(DEFAULT_PLAYER_ONE_ODD)
            .playerTwoName(DEFAULT_PLAYER_TWO_NAME)
            .playerTwoScore(DEFAULT_PLAYER_TWO_SCORE)
            .playerTwoOdd(DEFAULT_PLAYER_TWO_ODD)
            .prediction(DEFAULT_PREDICTION)
            .actualResult(DEFAULT_ACTUAL_RESULT)
            .betAmount(DEFAULT_BET_AMOUNT)
            .potentialGain(DEFAULT_POTENTIAL_GAIN)
            .gain(DEFAULT_GAIN);
        return match;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Match createUpdatedEntity(EntityManager em) {
        Match match = new Match()
            .playerOneName(UPDATED_PLAYER_ONE_NAME)
            .playerOneScore(UPDATED_PLAYER_ONE_SCORE)
            .playerOneOdd(UPDATED_PLAYER_ONE_ODD)
            .playerTwoName(UPDATED_PLAYER_TWO_NAME)
            .playerTwoScore(UPDATED_PLAYER_TWO_SCORE)
            .playerTwoOdd(UPDATED_PLAYER_TWO_ODD)
            .prediction(UPDATED_PREDICTION)
            .actualResult(UPDATED_ACTUAL_RESULT)
            .betAmount(UPDATED_BET_AMOUNT)
            .potentialGain(UPDATED_POTENTIAL_GAIN)
            .gain(UPDATED_GAIN);
        return match;
    }

    @BeforeEach
    public void initTest() {
        match = createEntity(em);
    }

    @Test
    @Transactional
    void createMatch() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();
        // Create the Match
        restMatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate + 1);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getPlayerOneName()).isEqualTo(DEFAULT_PLAYER_ONE_NAME);
        assertThat(testMatch.getPlayerOneScore()).isEqualTo(DEFAULT_PLAYER_ONE_SCORE);
        assertThat(testMatch.getPlayerOneOdd()).isEqualTo(DEFAULT_PLAYER_ONE_ODD);
        assertThat(testMatch.getPlayerTwoName()).isEqualTo(DEFAULT_PLAYER_TWO_NAME);
        assertThat(testMatch.getPlayerTwoScore()).isEqualTo(DEFAULT_PLAYER_TWO_SCORE);
        assertThat(testMatch.getPlayerTwoOdd()).isEqualTo(DEFAULT_PLAYER_TWO_ODD);
        assertThat(testMatch.getPrediction()).isEqualTo(DEFAULT_PREDICTION);
        assertThat(testMatch.getActualResult()).isEqualTo(DEFAULT_ACTUAL_RESULT);
        assertThat(testMatch.getBetAmount()).isEqualTo(DEFAULT_BET_AMOUNT);
        assertThat(testMatch.getPotentialGain()).isEqualTo(DEFAULT_POTENTIAL_GAIN);
        assertThat(testMatch.getGain()).isEqualTo(DEFAULT_GAIN);
    }

    @Test
    @Transactional
    void createMatchWithExistingId() throws Exception {
        // Create the Match with an existing ID
        match.setId(1L);

        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMatchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMatches() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get all the matchList
        restMatchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId().intValue())))
            .andExpect(jsonPath("$.[*].playerOneName").value(hasItem(DEFAULT_PLAYER_ONE_NAME)))
            .andExpect(jsonPath("$.[*].playerOneScore").value(hasItem(DEFAULT_PLAYER_ONE_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].playerOneOdd").value(hasItem(DEFAULT_PLAYER_ONE_ODD.doubleValue())))
            .andExpect(jsonPath("$.[*].playerTwoName").value(hasItem(DEFAULT_PLAYER_TWO_NAME)))
            .andExpect(jsonPath("$.[*].playerTwoScore").value(hasItem(DEFAULT_PLAYER_TWO_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].playerTwoOdd").value(hasItem(DEFAULT_PLAYER_TWO_ODD.doubleValue())))
            .andExpect(jsonPath("$.[*].prediction").value(hasItem(DEFAULT_PREDICTION.toString())))
            .andExpect(jsonPath("$.[*].actualResult").value(hasItem(DEFAULT_ACTUAL_RESULT.toString())))
            .andExpect(jsonPath("$.[*].betAmount").value(hasItem(DEFAULT_BET_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].potentialGain").value(hasItem(DEFAULT_POTENTIAL_GAIN.doubleValue())))
            .andExpect(jsonPath("$.[*].gain").value(hasItem(DEFAULT_GAIN.doubleValue())));
    }

    @Test
    @Transactional
    void getMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        // Get the match
        restMatchMockMvc
            .perform(get(ENTITY_API_URL_ID, match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(match.getId().intValue()))
            .andExpect(jsonPath("$.playerOneName").value(DEFAULT_PLAYER_ONE_NAME))
            .andExpect(jsonPath("$.playerOneScore").value(DEFAULT_PLAYER_ONE_SCORE.doubleValue()))
            .andExpect(jsonPath("$.playerOneOdd").value(DEFAULT_PLAYER_ONE_ODD.doubleValue()))
            .andExpect(jsonPath("$.playerTwoName").value(DEFAULT_PLAYER_TWO_NAME))
            .andExpect(jsonPath("$.playerTwoScore").value(DEFAULT_PLAYER_TWO_SCORE.doubleValue()))
            .andExpect(jsonPath("$.playerTwoOdd").value(DEFAULT_PLAYER_TWO_ODD.doubleValue()))
            .andExpect(jsonPath("$.prediction").value(DEFAULT_PREDICTION.toString()))
            .andExpect(jsonPath("$.actualResult").value(DEFAULT_ACTUAL_RESULT.toString()))
            .andExpect(jsonPath("$.betAmount").value(DEFAULT_BET_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.potentialGain").value(DEFAULT_POTENTIAL_GAIN.doubleValue()))
            .andExpect(jsonPath("$.gain").value(DEFAULT_GAIN.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingMatch() throws Exception {
        // Get the match
        restMatchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Update the match
        Match updatedMatch = matchRepository.findById(match.getId()).get();
        // Disconnect from session so that the updates on updatedMatch are not directly saved in db
        em.detach(updatedMatch);
        updatedMatch
            .playerOneName(UPDATED_PLAYER_ONE_NAME)
            .playerOneScore(UPDATED_PLAYER_ONE_SCORE)
            .playerOneOdd(UPDATED_PLAYER_ONE_ODD)
            .playerTwoName(UPDATED_PLAYER_TWO_NAME)
            .playerTwoScore(UPDATED_PLAYER_TWO_SCORE)
            .playerTwoOdd(UPDATED_PLAYER_TWO_ODD)
            .prediction(UPDATED_PREDICTION)
            .actualResult(UPDATED_ACTUAL_RESULT)
            .betAmount(UPDATED_BET_AMOUNT)
            .potentialGain(UPDATED_POTENTIAL_GAIN)
            .gain(UPDATED_GAIN);

        restMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMatch.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMatch))
            )
            .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getPlayerOneName()).isEqualTo(UPDATED_PLAYER_ONE_NAME);
        assertThat(testMatch.getPlayerOneScore()).isEqualTo(UPDATED_PLAYER_ONE_SCORE);
        assertThat(testMatch.getPlayerOneOdd()).isEqualTo(UPDATED_PLAYER_ONE_ODD);
        assertThat(testMatch.getPlayerTwoName()).isEqualTo(UPDATED_PLAYER_TWO_NAME);
        assertThat(testMatch.getPlayerTwoScore()).isEqualTo(UPDATED_PLAYER_TWO_SCORE);
        assertThat(testMatch.getPlayerTwoOdd()).isEqualTo(UPDATED_PLAYER_TWO_ODD);
        assertThat(testMatch.getPrediction()).isEqualTo(UPDATED_PREDICTION);
        assertThat(testMatch.getActualResult()).isEqualTo(UPDATED_ACTUAL_RESULT);
        assertThat(testMatch.getBetAmount()).isEqualTo(UPDATED_BET_AMOUNT);
        assertThat(testMatch.getPotentialGain()).isEqualTo(UPDATED_POTENTIAL_GAIN);
        assertThat(testMatch.getGain()).isEqualTo(UPDATED_GAIN);
    }

    @Test
    @Transactional
    void putNonExistingMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();
        match.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, match.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(match))
            )
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();
        match.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(match))
            )
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();
        match.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMatchWithPatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Update the match using partial update
        Match partialUpdatedMatch = new Match();
        partialUpdatedMatch.setId(match.getId());

        partialUpdatedMatch
            .playerOneScore(UPDATED_PLAYER_ONE_SCORE)
            .playerOneOdd(UPDATED_PLAYER_ONE_ODD)
            .playerTwoName(UPDATED_PLAYER_TWO_NAME)
            .playerTwoScore(UPDATED_PLAYER_TWO_SCORE)
            .prediction(UPDATED_PREDICTION)
            .betAmount(UPDATED_BET_AMOUNT)
            .potentialGain(UPDATED_POTENTIAL_GAIN);

        restMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatch))
            )
            .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getPlayerOneName()).isEqualTo(DEFAULT_PLAYER_ONE_NAME);
        assertThat(testMatch.getPlayerOneScore()).isEqualTo(UPDATED_PLAYER_ONE_SCORE);
        assertThat(testMatch.getPlayerOneOdd()).isEqualTo(UPDATED_PLAYER_ONE_ODD);
        assertThat(testMatch.getPlayerTwoName()).isEqualTo(UPDATED_PLAYER_TWO_NAME);
        assertThat(testMatch.getPlayerTwoScore()).isEqualTo(UPDATED_PLAYER_TWO_SCORE);
        assertThat(testMatch.getPlayerTwoOdd()).isEqualTo(DEFAULT_PLAYER_TWO_ODD);
        assertThat(testMatch.getPrediction()).isEqualTo(UPDATED_PREDICTION);
        assertThat(testMatch.getActualResult()).isEqualTo(DEFAULT_ACTUAL_RESULT);
        assertThat(testMatch.getBetAmount()).isEqualTo(UPDATED_BET_AMOUNT);
        assertThat(testMatch.getPotentialGain()).isEqualTo(UPDATED_POTENTIAL_GAIN);
        assertThat(testMatch.getGain()).isEqualTo(DEFAULT_GAIN);
    }

    @Test
    @Transactional
    void fullUpdateMatchWithPatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Update the match using partial update
        Match partialUpdatedMatch = new Match();
        partialUpdatedMatch.setId(match.getId());

        partialUpdatedMatch
            .playerOneName(UPDATED_PLAYER_ONE_NAME)
            .playerOneScore(UPDATED_PLAYER_ONE_SCORE)
            .playerOneOdd(UPDATED_PLAYER_ONE_ODD)
            .playerTwoName(UPDATED_PLAYER_TWO_NAME)
            .playerTwoScore(UPDATED_PLAYER_TWO_SCORE)
            .playerTwoOdd(UPDATED_PLAYER_TWO_ODD)
            .prediction(UPDATED_PREDICTION)
            .actualResult(UPDATED_ACTUAL_RESULT)
            .betAmount(UPDATED_BET_AMOUNT)
            .potentialGain(UPDATED_POTENTIAL_GAIN)
            .gain(UPDATED_GAIN);

        restMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMatch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMatch))
            )
            .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
        Match testMatch = matchList.get(matchList.size() - 1);
        assertThat(testMatch.getPlayerOneName()).isEqualTo(UPDATED_PLAYER_ONE_NAME);
        assertThat(testMatch.getPlayerOneScore()).isEqualTo(UPDATED_PLAYER_ONE_SCORE);
        assertThat(testMatch.getPlayerOneOdd()).isEqualTo(UPDATED_PLAYER_ONE_ODD);
        assertThat(testMatch.getPlayerTwoName()).isEqualTo(UPDATED_PLAYER_TWO_NAME);
        assertThat(testMatch.getPlayerTwoScore()).isEqualTo(UPDATED_PLAYER_TWO_SCORE);
        assertThat(testMatch.getPlayerTwoOdd()).isEqualTo(UPDATED_PLAYER_TWO_ODD);
        assertThat(testMatch.getPrediction()).isEqualTo(UPDATED_PREDICTION);
        assertThat(testMatch.getActualResult()).isEqualTo(UPDATED_ACTUAL_RESULT);
        assertThat(testMatch.getBetAmount()).isEqualTo(UPDATED_BET_AMOUNT);
        assertThat(testMatch.getPotentialGain()).isEqualTo(UPDATED_POTENTIAL_GAIN);
        assertThat(testMatch.getGain()).isEqualTo(UPDATED_GAIN);
    }

    @Test
    @Transactional
    void patchNonExistingMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();
        match.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, match.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(match))
            )
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();
        match.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(match))
            )
            .andExpect(status().isBadRequest());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMatch() throws Exception {
        int databaseSizeBeforeUpdate = matchRepository.findAll().size();
        match.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMatchMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(match)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Match in the database
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMatch() throws Exception {
        // Initialize the database
        matchRepository.saveAndFlush(match);

        int databaseSizeBeforeDelete = matchRepository.findAll().size();

        // Delete the match
        restMatchMockMvc
            .perform(delete(ENTITY_API_URL_ID, match.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Match> matchList = matchRepository.findAll();
        assertThat(matchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
