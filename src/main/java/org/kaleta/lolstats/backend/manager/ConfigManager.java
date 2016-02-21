package org.kaleta.lolstats.backend.manager;

import org.kaleta.lolstats.backend.entity.Config;

/**
 *  Created by Stanislav Kaleta on 11.02.2016.
 *
 *  Provides basic CRUD operations for Config entity.
 */
public interface ConfigManager {

    /**
     * Creates Config in data source.
     * @throws ManagerException
     */
    void createConfig() throws ManagerException;

    /**
     * Retrieves Config from data source.
     * @throws ManagerException
     */
    Config retrieveConfig() throws ManagerException;

    /**
     * Updates Config in data source.
     * @throws ManagerException
     */
    void updateConfig(Config config) throws ManagerException;

    /**
     * Deletes Config from data source.
     * @throws ManagerException
     */
    void deleteConfig() throws ManagerException;
}