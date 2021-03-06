package org.kaleta.lolstats.ex.manager;

import org.kaleta.lolstats.ex.ServiceFailureException;
import org.kaleta.lolstats.ex.entities.GameRecord;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 14.1.2015
 */
public interface StatsManager {

    /*TODO CRUD + this is Season class manager */
    public List<GameRecord> retrieveStats() throws ServiceFailureException;
    public void updateStats(List<GameRecord> gameRecords) throws ServiceFailureException;

}
