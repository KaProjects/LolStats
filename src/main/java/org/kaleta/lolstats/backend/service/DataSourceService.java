package org.kaleta.lolstats.backend.service;

import org.kaleta.lolstats.backend.entity.Config;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.manager.ConfigManager;
import org.kaleta.lolstats.backend.manager.JaxbConfigManager;
import org.kaleta.lolstats.backend.manager.JaxbSeasonManager;
import org.kaleta.lolstats.backend.manager.ManagerException;
import org.kaleta.lolstats.frontend.ErrorDialog;
import org.kaleta.lolstats.frontend.Initializer;

/**
 * Created by Stanislav Kaleta on 11.02.2016.
 *
 * TODo doc.
 */
public class DataSourceService {

    /**
     * TODo doc.
     */
    public Config.LolApi getLolApiConfig(){
        try {
            ConfigManager manager = new JaxbConfigManager();
            return manager.retrieveConfig().getLolApi();
        } catch (ManagerException e){
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODo doc.
     */
    public void createSeason(String name){
        try {
            ConfigManager configManager = new JaxbConfigManager();
            Config config = configManager.retrieveConfig();
            int lastId = 0;
            for (Config.Seasons.Season s : config.getSeasons().getSeason()){
                int id = Integer.parseInt(s.getId());
                if (id > lastId){
                    lastId = id;
                }
            }

            Season season = new Season();
            season.setName(name);
            season.setId(String.valueOf(lastId + 1));
            new JaxbSeasonManager().createSeason(season);

            Config.Seasons.Season s = new Config.Seasons.Season();
            s.setId(String.valueOf(lastId + 1));
            s.setOrder(String.valueOf(lastId + 1));
            config.getSeasons().getSeason().add(s);
            configManager.updateConfig(config);
        } catch (ManagerException e){
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODo doc.
     */
    public static void addNewGame(int seasonNumber, Season.Game game){
        // TODO: 3/5/16 impl.
    }

    /**
     * TODo doc.
     */
    public static Season.Game.Rank getLastInsertedRank(){
        // TODO: 3/5/16 impl.
        Season.Game.Rank rank = new Season.Game.Rank();
        rank.setTier("PLATINUM");
        rank.setDivision("III");
        rank.setLp("90");
        return null;
    }

    /**
     * TODo doc.
     */
    public static Integer getLastInsertedGameNumber(){
        // TODO: 3/5/16 impl.
        return 0;
    }
}
