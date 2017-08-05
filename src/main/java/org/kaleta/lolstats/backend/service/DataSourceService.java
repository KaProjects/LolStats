package org.kaleta.lolstats.backend.service;

import org.kaleta.lolstats.backend.entity.Config;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.manager.*;
import org.kaleta.lolstats.frontend.ErrorDialog;
import org.kaleta.lolstats.frontend.Initializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 11.02.2016.
 *
 * TODo doc.
 */
public class DataSourceService {

    /**
     * TODo doc.
     */
    public static Config.LolApi getLolApiConfig(){
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
    public static void updateChampionList(){
        try {
            ConfigManager manager = new JaxbConfigManager();
            Config config =  manager.retrieveConfig();
            config.getChamps().getChamp().clear();
            for (String champName : new LolApiService().getChampionMap().values()){
                Config.Champs.Champ champ = new Config.Champs.Champ();
                champ.setName(champName);
                config.getChamps().getChamp().add(champ);
            }
            manager.updateConfig(config);
        } catch (ManagerException e){
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODo doc.
     */
    public static List<String> getChampList(){
        try {
            ConfigManager manager = new JaxbConfigManager();
            List<String> champs = new ArrayList<>();
            for(Config.Champs.Champ champ : manager.retrieveConfig().getChamps().getChamp()){
                champs.add(champ.getName());
            }
            return champs;
        } catch (ManagerException e){
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODo doc.
     */
    public static void createSeason(String name, final Long number){
        try {
            ConfigManager configManager = new JaxbConfigManager();
            Config config = configManager.retrieveConfig();
            if (config.getSeasons().getSeason().stream().anyMatch(s -> Long.parseLong(s.getId()) == number)){
                throw new IllegalArgumentException("Id already exist!");
            }

            Season season = new Season();
            season.setName(name);
            season.setId(String.valueOf(number));
            new JaxbSeasonManager().createSeason(season);

            Config.Seasons.Season s = new Config.Seasons.Season();
            s.setId(String.valueOf(number));
            s.setOrder(String.valueOf(number));
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
    public static void addNewGame(Season.Game game){
        try {
            String actualSeason = new JaxbConfigManager().retrieveConfig().getSeasons().getActualSeason();
            SeasonManager manager = new JaxbSeasonManager();
            Season season = manager.retrieveSeason(Long.parseLong(actualSeason));
            season.getGame().add(game);
            manager.updateSeason(season);
        } catch (ManagerException e){
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODo doc.
     */
    public static Season.Game.Rank getLastInsertedRank(){
        try {
            String actualSeasonId = new JaxbConfigManager().retrieveConfig().getSeasons().getActualSeason();
            Season actualSeason = new JaxbSeasonManager().retrieveSeason(Long.parseLong(actualSeasonId));
            if (actualSeason.getGame().size() > 0){
                Season.Game.Rank lastRank = actualSeason.getGame().get(actualSeason.getGame().size() - 1).getRank();
                if (!lastRank.getTier().equals("") && !lastRank.getDivision().equals("") && !lastRank.getLp().equals("")){
                    return lastRank;
                }
            }
            return null;
        } catch (ManagerException e){
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODo doc.
     */
    public static Integer getLastInsertedGameNumber(){
        try {
            String actualSeasonId = new JaxbConfigManager().retrieveConfig().getSeasons().getActualSeason();
            return new JaxbSeasonManager().retrieveSeason(Long.parseLong(actualSeasonId)).getGame().size();
        } catch (ManagerException e){
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODo doc.
     */
    public static List<Season.Game> getAllGames(){
        try {
            String actualSeasonId = new JaxbConfigManager().retrieveConfig().getSeasons().getActualSeason();
            return new JaxbSeasonManager().retrieveSeason(Long.parseLong(actualSeasonId)).getGame();
        } catch (ManagerException e){
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }
}
