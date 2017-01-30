package org.kaleta.lolstats.backend.manager;

import org.kaleta.lolstats.backend.entity.Season;

/**
 *  Created by Stanislav Kaleta on 11.02.2016.
 *
 *  Provides basic CRUD operations for Season entity.
 */
public interface SeasonManager {

    /**
     * Creates Season in data source.
     * @throws ManagerException
     */
    void createSeason(Season season) throws ManagerException;

    /**
     * Retrieves Season from data source.
     * @throws ManagerException
     */
    Season retrieveSeason(Long id) throws ManagerException;

    /**
     * Updates Season in data source.
     * @throws ManagerException
     */
    void updateSeason(Season season) throws ManagerException;

    /**
     * Deletes Season from data source.
     * @throws ManagerException
     */
    void deleteSeason(Long id) throws ManagerException;
}