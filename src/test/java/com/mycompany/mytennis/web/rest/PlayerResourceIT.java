package com.mycompany.mytennis.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.mytennis.IntegrationTest;
import com.mycompany.mytennis.domain.Player;
import com.mycompany.mytennis.repository.PlayerRepository;
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
 * Integration tests for the {@link PlayerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlayerResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Double DEFAULT_FIRST_SERVE_PERCENTAGE = 1D;
    private static final Double UPDATED_FIRST_SERVE_PERCENTAGE = 2D;

    private static final Double DEFAULT_SERVICE_POINTS_WON = 1D;
    private static final Double UPDATED_SERVICE_POINTS_WON = 2D;

    private static final Double DEFAULT_BREAK_POINTS_SAVED = 1D;
    private static final Double UPDATED_BREAK_POINTS_SAVED = 2D;

    private static final Double DEFAULT_SECOND_SERVE_RETURN_POINTS_WON = 1D;
    private static final Double UPDATED_SECOND_SERVE_RETURN_POINTS_WON = 2D;

    private static final Double DEFAULT_BREAK_POINTS_CONVERTED = 1D;
    private static final Double UPDATED_BREAK_POINTS_CONVERTED = 2D;

    private static final Double DEFAULT_SCORE = 1D;
    private static final Double UPDATED_SCORE = 2D;

    private static final String ENTITY_API_URL = "/api/players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlayerMockMvc;

    private Player player;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createEntity(EntityManager em) {
        Player player = new Player()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .country(DEFAULT_COUNTRY)
            .age(DEFAULT_AGE)
            .firstServePercentage(DEFAULT_FIRST_SERVE_PERCENTAGE)
            .servicePointsWon(DEFAULT_SERVICE_POINTS_WON)
            .breakPointsSaved(DEFAULT_BREAK_POINTS_SAVED)
            .secondServeReturnPointsWon(DEFAULT_SECOND_SERVE_RETURN_POINTS_WON)
            .breakPointsConverted(DEFAULT_BREAK_POINTS_CONVERTED)
            .score(DEFAULT_SCORE);
        return player;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Player createUpdatedEntity(EntityManager em) {
        Player player = new Player()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .country(UPDATED_COUNTRY)
            .age(UPDATED_AGE)
            .firstServePercentage(UPDATED_FIRST_SERVE_PERCENTAGE)
            .servicePointsWon(UPDATED_SERVICE_POINTS_WON)
            .breakPointsSaved(UPDATED_BREAK_POINTS_SAVED)
            .secondServeReturnPointsWon(UPDATED_SECOND_SERVE_RETURN_POINTS_WON)
            .breakPointsConverted(UPDATED_BREAK_POINTS_CONVERTED)
            .score(UPDATED_SCORE);
        return player;
    }

    @BeforeEach
    public void initTest() {
        player = createEntity(em);
    }

    @Test
    @Transactional
    void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();
        // Create the Player
        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isCreated());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate + 1);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testPlayer.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPlayer.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPlayer.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPlayer.getFirstServePercentage()).isEqualTo(DEFAULT_FIRST_SERVE_PERCENTAGE);
        assertThat(testPlayer.getServicePointsWon()).isEqualTo(DEFAULT_SERVICE_POINTS_WON);
        assertThat(testPlayer.getBreakPointsSaved()).isEqualTo(DEFAULT_BREAK_POINTS_SAVED);
        assertThat(testPlayer.getSecondServeReturnPointsWon()).isEqualTo(DEFAULT_SECOND_SERVE_RETURN_POINTS_WON);
        assertThat(testPlayer.getBreakPointsConverted()).isEqualTo(DEFAULT_BREAK_POINTS_CONVERTED);
        assertThat(testPlayer.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    @Transactional
    void createPlayerWithExistingId() throws Exception {
        // Create the Player with an existing ID
        player.setId(1L);

        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the playerList
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].firstServePercentage").value(hasItem(DEFAULT_FIRST_SERVE_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].servicePointsWon").value(hasItem(DEFAULT_SERVICE_POINTS_WON.doubleValue())))
            .andExpect(jsonPath("$.[*].breakPointsSaved").value(hasItem(DEFAULT_BREAK_POINTS_SAVED.doubleValue())))
            .andExpect(jsonPath("$.[*].secondServeReturnPointsWon").value(hasItem(DEFAULT_SECOND_SERVE_RETURN_POINTS_WON.doubleValue())))
            .andExpect(jsonPath("$.[*].breakPointsConverted").value(hasItem(DEFAULT_BREAK_POINTS_CONVERTED.doubleValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())));
    }

    @Test
    @Transactional
    void getPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get the player
        restPlayerMockMvc
            .perform(get(ENTITY_API_URL_ID, player.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(player.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.firstServePercentage").value(DEFAULT_FIRST_SERVE_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.servicePointsWon").value(DEFAULT_SERVICE_POINTS_WON.doubleValue()))
            .andExpect(jsonPath("$.breakPointsSaved").value(DEFAULT_BREAK_POINTS_SAVED.doubleValue()))
            .andExpect(jsonPath("$.secondServeReturnPointsWon").value(DEFAULT_SECOND_SERVE_RETURN_POINTS_WON.doubleValue()))
            .andExpect(jsonPath("$.breakPointsConverted").value(DEFAULT_BREAK_POINTS_CONVERTED.doubleValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingPlayer() throws Exception {
        // Get the player
        restPlayerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player
        Player updatedPlayer = playerRepository.findById(player.getId()).get();
        // Disconnect from session so that the updates on updatedPlayer are not directly saved in db
        em.detach(updatedPlayer);
        updatedPlayer
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .country(UPDATED_COUNTRY)
            .age(UPDATED_AGE)
            .firstServePercentage(UPDATED_FIRST_SERVE_PERCENTAGE)
            .servicePointsWon(UPDATED_SERVICE_POINTS_WON)
            .breakPointsSaved(UPDATED_BREAK_POINTS_SAVED)
            .secondServeReturnPointsWon(UPDATED_SECOND_SERVE_RETURN_POINTS_WON)
            .breakPointsConverted(UPDATED_BREAK_POINTS_CONVERTED)
            .score(UPDATED_SCORE);

        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlayer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPlayer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPlayer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPlayer.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPlayer.getFirstServePercentage()).isEqualTo(UPDATED_FIRST_SERVE_PERCENTAGE);
        assertThat(testPlayer.getServicePointsWon()).isEqualTo(UPDATED_SERVICE_POINTS_WON);
        assertThat(testPlayer.getBreakPointsSaved()).isEqualTo(UPDATED_BREAK_POINTS_SAVED);
        assertThat(testPlayer.getSecondServeReturnPointsWon()).isEqualTo(UPDATED_SECOND_SERVE_RETURN_POINTS_WON);
        assertThat(testPlayer.getBreakPointsConverted()).isEqualTo(UPDATED_BREAK_POINTS_CONVERTED);
        assertThat(testPlayer.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    void putNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, player.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(player))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(player))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .country(UPDATED_COUNTRY)
            .firstServePercentage(UPDATED_FIRST_SERVE_PERCENTAGE)
            .servicePointsWon(UPDATED_SERVICE_POINTS_WON)
            .breakPointsSaved(UPDATED_BREAK_POINTS_SAVED)
            .secondServeReturnPointsWon(UPDATED_SECOND_SERVE_RETURN_POINTS_WON)
            .breakPointsConverted(UPDATED_BREAK_POINTS_CONVERTED)
            .score(UPDATED_SCORE);

        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPlayer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPlayer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPlayer.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPlayer.getFirstServePercentage()).isEqualTo(UPDATED_FIRST_SERVE_PERCENTAGE);
        assertThat(testPlayer.getServicePointsWon()).isEqualTo(UPDATED_SERVICE_POINTS_WON);
        assertThat(testPlayer.getBreakPointsSaved()).isEqualTo(UPDATED_BREAK_POINTS_SAVED);
        assertThat(testPlayer.getSecondServeReturnPointsWon()).isEqualTo(UPDATED_SECOND_SERVE_RETURN_POINTS_WON);
        assertThat(testPlayer.getBreakPointsConverted()).isEqualTo(UPDATED_BREAK_POINTS_CONVERTED);
        assertThat(testPlayer.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    void fullUpdatePlayerWithPatch() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeUpdate = playerRepository.findAll().size();

        // Update the player using partial update
        Player partialUpdatedPlayer = new Player();
        partialUpdatedPlayer.setId(player.getId());

        partialUpdatedPlayer
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .country(UPDATED_COUNTRY)
            .age(UPDATED_AGE)
            .firstServePercentage(UPDATED_FIRST_SERVE_PERCENTAGE)
            .servicePointsWon(UPDATED_SERVICE_POINTS_WON)
            .breakPointsSaved(UPDATED_BREAK_POINTS_SAVED)
            .secondServeReturnPointsWon(UPDATED_SECOND_SERVE_RETURN_POINTS_WON)
            .breakPointsConverted(UPDATED_BREAK_POINTS_CONVERTED)
            .score(UPDATED_SCORE);

        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlayer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlayer))
            )
            .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
        Player testPlayer = playerList.get(playerList.size() - 1);
        assertThat(testPlayer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPlayer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPlayer.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPlayer.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPlayer.getFirstServePercentage()).isEqualTo(UPDATED_FIRST_SERVE_PERCENTAGE);
        assertThat(testPlayer.getServicePointsWon()).isEqualTo(UPDATED_SERVICE_POINTS_WON);
        assertThat(testPlayer.getBreakPointsSaved()).isEqualTo(UPDATED_BREAK_POINTS_SAVED);
        assertThat(testPlayer.getSecondServeReturnPointsWon()).isEqualTo(UPDATED_SECOND_SERVE_RETURN_POINTS_WON);
        assertThat(testPlayer.getBreakPointsConverted()).isEqualTo(UPDATED_BREAK_POINTS_CONVERTED);
        assertThat(testPlayer.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    @Transactional
    void patchNonExistingPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, player.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(player))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(player))
            )
            .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlayer() throws Exception {
        int databaseSizeBeforeUpdate = playerRepository.findAll().size();
        player.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlayerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(player)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeDelete = playerRepository.findAll().size();

        // Delete the player
        restPlayerMockMvc
            .perform(delete(ENTITY_API_URL_ID, player.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
