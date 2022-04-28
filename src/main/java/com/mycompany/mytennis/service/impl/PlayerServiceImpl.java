package com.mycompany.mytennis.service.impl;

import com.mycompany.mytennis.domain.Player;
import com.mycompany.mytennis.repository.PlayerRepository;
import com.mycompany.mytennis.service.PlayerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Player}.
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player save(Player player) {
        log.debug("Request to save Player : {}", player);
        return playerRepository.save(player);
    }

    @Override
    public Player update(Player player) {
        log.debug("Request to save Player : {}", player);
        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> partialUpdate(Player player) {
        log.debug("Request to partially update Player : {}", player);

        return playerRepository
            .findById(player.getId())
            .map(existingPlayer -> {
                if (player.getFirstname() != null) {
                    existingPlayer.setFirstname(player.getFirstname());
                }
                if (player.getLastname() != null) {
                    existingPlayer.setLastname(player.getLastname());
                }
                if (player.getCountry() != null) {
                    existingPlayer.setCountry(player.getCountry());
                }
                if (player.getAge() != null) {
                    existingPlayer.setAge(player.getAge());
                }
                if (player.getFirstServePercentage() != null) {
                    existingPlayer.setFirstServePercentage(player.getFirstServePercentage());
                }
                if (player.getServicePointsWon() != null) {
                    existingPlayer.setServicePointsWon(player.getServicePointsWon());
                }
                if (player.getBreakPointsSaved() != null) {
                    existingPlayer.setBreakPointsSaved(player.getBreakPointsSaved());
                }
                if (player.getSecondServeReturnPointsWon() != null) {
                    existingPlayer.setSecondServeReturnPointsWon(player.getSecondServeReturnPointsWon());
                }
                if (player.getBreakPointsConverted() != null) {
                    existingPlayer.setBreakPointsConverted(player.getBreakPointsConverted());
                }
                if (player.getScore() != null) {
                    existingPlayer.setScore(player.getScore());
                }

                return existingPlayer;
            })
            .map(playerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Player> findAll(Pageable pageable) {
        log.debug("Request to get all Players");
        return playerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Player> findOne(Long id) {
        log.debug("Request to get Player : {}", id);
        return playerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Player : {}", id);
        playerRepository.deleteById(id);
    }
}
