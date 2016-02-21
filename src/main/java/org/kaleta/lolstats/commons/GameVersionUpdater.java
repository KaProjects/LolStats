package org.kaleta.lolstats.commons;

import org.kaleta.lolstats.backend.entity.Config;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.manager.JaxbSeasonManager;
import org.kaleta.lolstats.backend.manager.ManagerException;
import org.kaleta.lolstats.backend.manager.SeasonManager;
import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.backend.service.LolApiService;
import org.kaleta.lolstats.ex.entities.GameRecord;
import org.kaleta.lolstats.ex.entities.Result;
import org.kaleta.lolstats.ex.entities.Score;
import org.kaleta.lolstats.ex.manager.StatsManagerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 18.02.2016.
 */
public class GameVersionUpdater {
    private final SeasonManager manager2x;
    private Season season_2x;
    private List<GameRecord> season_1x;

    public GameVersionUpdater(int seasonNumber) throws ManagerException {
        manager2x = new JaxbSeasonManager();
        season_2x = manager2x.retrieveSeason((long) seasonNumber);
        season_1x = new StatsManagerImpl("stats"+seasonNumber+".xml").retrieveStats();
    }
    /**
     * TODO
     * @return
     * @throws ManagerException
     */
    public String updateGame_1x_t0_2x() throws ManagerException {
        season_2x.getGame().clear();
        Config.LolApi apiConfig = new DataSourceService().getLolApiConfig();
        LolApiService apiService = new LolApiService(apiConfig.getRegion(), apiConfig.getAccessKey(), apiConfig.getNick());

        int gameCounter = 0;
        int foundCounter = 0;
        int okMatchCounter = 0;
        int badMatchCounter = 0;
        List<GameRecord> tempGameList = new ArrayList<>();
        String date = "";
        for (int g = 0;g<season_1x.size() + 1;g++){
            String gDate = "";
            if (g != season_1x.size()){
                gDate  = season_1x.get(g).getDate().getStringDate();
            }
            if (date.equals(gDate)){

            } else {
                List<Season.Game> foundGames = apiService.tryFindGame(5,date,"RANKED_SOLO_5x5");
                for (GameRecord dbGame : tempGameList){
                    int MatchedGameCounter = 0;
                    Season.Game matchedGame = null;
                    for (Season.Game apiGame : foundGames){
                        if (dbGame.getMyChampion().toString().toLowerCase().equals(apiGame.getChampion())){
                            Season.Game.Performance.Score apiScore = apiGame.getPerformance().getScore();
                            Score transformedScore = new Score(Integer.parseInt(apiScore.getKillsAbsolute()),
                                    Integer.parseInt(apiScore.getDeathsAbsolute()),
                                    Integer.parseInt(apiScore.getAssistsAbsolute()));
                            if (dbGame.getScore().getStringScore().equals(transformedScore.getStringScore())){
                                matchedGame = apiGame;
                                MatchedGameCounter++;
                            }
                        }
                    }
                    if (MatchedGameCounter == 1){
                        okMatchCounter++;
                        matchedGame.setNumber(String.valueOf(dbGame.getGameNumber()));
                        season_2x.getGame().add(matchedGame);
                    } else {
                        badMatchCounter++;
                        Season.Game errorGame = new Season.Game();
                        errorGame.setNumber(String.valueOf(dbGame.getGameNumber()));
                        System.out.println("Game was not found for db game with number \'"+errorGame.getNumber()+"\'");
                        season_2x.getGame().add(errorGame);
                    }
                }
                gameCounter += tempGameList.size();
                foundCounter += foundGames.size();
                tempGameList.clear();
                date = gDate;
            }
            if (g != season_1x.size()){
                tempGameList.add(season_1x.get(g));
            }
        }

        manager2x.updateSeason(season_2x);

        return "Updating finished:\n"
                + "    database games:" + gameCounter + "\n"
                + "    api games found:" + foundCounter + "\n"
                + "    games matched:" + okMatchCounter + "\n"
                + "    games NOT matched:" + badMatchCounter + "\n";
    }

    /**
     * TODO
     * @return
     */
    public String transformRank1xTo2x() throws ManagerException {
        if (season_1x.size() != season_2x.getGame().size()){
            return "Error: different number of games in each season!";
        }
        int okTransformation = 0;
        int problemTransf = 0;
        int ableToDecideBy1previousG = 0;
        int ableToDecideBy2previousG = 0;
        int ableToDecideBy1nextG = 0;
        int notDecided = 0;
        for (int g=0;g<season_1x.size();g++){
            GameRecord game1x = season_1x.get(g);
            String rank1x = game1x.getRank().getStringOffsetLP();
            if (rank1x == null || rank1x.equals("") || rank1x.equals("UNDEFINED") || rank1x.length() != 4){
                Season.Game.Result.Rank rank2x = new Season.Game.Result.Rank();
                rank2x.setTier("undefined");
                rank2x.setDivision("");
                rank2x.setLp("");

                System.out.println(game1x.getGameNumber() +" "+ game1x.getRank().getStringOffsetLP() + " <=> " + rank2x.getTier() + " "
                        + rank2x.getDivision() + " "+rank2x.getLp());
                okTransformation++;
                season_2x.getGame().get(g).getResult().setRank(rank2x);
                continue;
            }
            if (rank1x.equals("null")){
                Season.Game.Result.Rank rank2x = new Season.Game.Result.Rank();
                rank2x.setTier("unranked");
                rank2x.setDivision("");
                rank2x.setLp("");

                System.out.println(game1x.getGameNumber() +" "+ game1x.getRank().getStringOffsetLP() + " <=> " + rank2x.getTier() + " "
                        + rank2x.getDivision() + " "+rank2x.getLp());
                okTransformation++;
                season_2x.getGame().get(g).getResult().setRank(rank2x);
                continue;
            }
            Season.Game.Result.Rank rank2x = new Season.Game.Result.Rank();

            int lp = Integer.parseInt(rank1x.substring(rank1x.length()-2,rank1x.length()));
            int division = Integer.parseInt(rank1x.substring(0,2)) % 5;
            int tier = Integer.parseInt(rank1x.substring(0,2)) / 5;

            String info = " ";
            if (lp == 0){
                boolean decided = false;
                try {
                    GameRecord beforeGame = season_1x.get(game1x.getGameNumber() - 1 - 1);
                    String beforeGameRank = beforeGame.getRank().getStringOffsetLP();
                    int beforeGameLp = Integer.parseInt(beforeGameRank.substring(beforeGameRank.length()-2,beforeGameRank.length()));
                    if (beforeGameLp != 0){
                        if (game1x.getGameResult().equals(Result.Victory)){
                            info += "SERIES REACHED = 100lp & -1 tierDivisionOffset";
                            lp = 100;
                            int tdOffset = Integer.parseInt(rank1x.substring(0,2)) - 1;
                            division = tdOffset % 5;
                            tier = tdOffset / 5;
                        } else {
                            info += " FIRST GAME ON THE BOTTOM OF DIVISION = rank is OK";
                        }
                        decided = true;
                        ableToDecideBy1previousG++;
                    } else {
                        String beforeBeforeGameRank = season_1x.get(game1x.getGameNumber() - 1 - 2).getRank().getStringOffsetLP();
                        int beforeBeforeGameLp = Integer.parseInt(beforeBeforeGameRank.substring(beforeBeforeGameRank.length()-2,beforeBeforeGameRank.length()));
                        if (beforeBeforeGameLp != 0){
                            if (beforeGame.getGameResult().equals(Result.Victory)){
                                info += " FIRST GAME OF SERIES = 100lp & -1 tierDivisionOffset - " + game1x.getGameResult().toString();
                                lp = 100;
                                int tdOffset = Integer.parseInt(rank1x.substring(0,2)) - 1;
                                division = tdOffset % 5;
                                tier = tdOffset / 5;
                            } else {
                                info += " STILL BOTTOM OF DIVISION = rank is OK";
                            }
                            decided = true;
                            ableToDecideBy2previousG++;
                        }
                    }
                    if (info.equals(" ")){
                        GameRecord afterGame = season_1x.get(game1x.getGameNumber() - 1 + 1);
                        String afterGameRank = afterGame.getRank().getStringOffsetLP();
                        int afterGameLp = Integer.parseInt(afterGameRank.substring(afterGameRank.length()-2,afterGameRank.length()));
                        if (afterGameLp != 0){
                            if (game1x.getGameResult().equals(Result.Victory)){
                                if (afterGame.getGameResult().equals(Result.Victory)){
                                    info += " SERIES WON = rank is OK";
                                } else {
                                    info += " IN SERIES = 100lp & -1 tierDivisionOffset - " + game1x.getGameResult().toString();
                                    lp = 100;
                                    int tdOffset = Integer.parseInt(rank1x.substring(0,2)) - 1;
                                    division = tdOffset % 5;
                                    tier = tdOffset / 5;
                                }
                                decided = true;
                                ableToDecideBy1nextG++;
                            } else {
                                if (afterGame.getGameResult().equals(Result.Victory)){
                                    info += " LAST GAME ON THE BOTTOM OF DIVISION = rank is OK";
                                    decided = true;
                                    ableToDecideBy1nextG++;
                                }
                            }
                        }
                    }
                } catch (Exception e){
                    info += "EXCEPTION " + e.getClass().getName();
                    decided = false;
                }
                problemTransf++;
                if (!decided){
                    System.out.println(game1x.getGameNumber() +" "+ game1x.getRank().getStringOffsetLP()
                            + " NOT DECIDED => NEED TO BE MANUALLY UPDATED (" + info +")");
                    notDecided++;
                    continue;
                }
            } else {
                okTransformation++;
            }

            switch (division){
                case 0: rank2x.setDivision("V");break;
                case 1: rank2x.setDivision("IV");break;
                case 2: rank2x.setDivision("III");break;
                case 3: rank2x.setDivision("II");break;
                case 4: rank2x.setDivision("I");break;
            }
            switch (tier){
                case 1: rank2x.setTier("BRONZE");break;
                case 2: rank2x.setTier("SILVER");break;
                case 3: rank2x.setTier("GOLD");break;
                case 4: rank2x.setTier("PLATINUM");break;
                case 5: rank2x.setTier("DIAMOND");break;
                default: rank2x.setTier("undefined");
            }
            rank2x.setLp(String.valueOf(lp));

            System.out.println(game1x.getGameNumber() +" "+ game1x.getRank().getStringOffsetLP() + " <=> " + rank2x.getTier() + " "
                    + rank2x.getDivision() + " "+rank2x.getLp() + info);
            season_2x.getGame().get(g).getResult().setRank(rank2x);
        }

        manager2x.updateSeason(season_2x);

        return "Transforming finished:\n"
                + "  database games: " + season_1x.size() + "\n"
                + "    OK transformations: " + okTransformation + "\n"
                + "    problem transformations: " + problemTransf + "\n"
                + "      able to decide using 1 previous game: " + ableToDecideBy1previousG + "\n"
                + "      able to decide using 2 previous game: " + ableToDecideBy2previousG + "\n"
                + "      able to decide using 1 next game: " + ableToDecideBy1nextG + "\n"
                + "      not decided games: " + notDecided + " <- update manually! \n";
    }
}
