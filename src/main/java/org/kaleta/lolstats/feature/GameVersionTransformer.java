package org.kaleta.lolstats.feature;

import org.kaleta.lolstats.backend.entity.Player;
import org.kaleta.lolstats.backend.entity.Role;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.manager.JaxbSeasonManager;
import org.kaleta.lolstats.backend.manager.ManagerException;
import org.kaleta.lolstats.backend.manager.SeasonManager;
import org.kaleta.lolstats.backend.service.LolApiService;
import org.kaleta.lolstats.ex.entities.GameRecord;
import org.kaleta.lolstats.ex.entities.Result;
import org.kaleta.lolstats.ex.entities.Score;
import org.kaleta.lolstats.ex.manager.StatsManagerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 18.02.2016.
 */
public class GameVersionTransformer {
    private final SeasonManager manager2x;
    private Season season_2x;
    private List<GameRecord> season_1x;
    private int seasonNumber;

    public GameVersionTransformer(int seasonNumber) throws ManagerException {
        this.seasonNumber = seasonNumber;
        manager2x = new JaxbSeasonManager();
        season_2x = manager2x.retrieveSeason((long) seasonNumber);
        season_1x = new StatsManagerImpl("stats"+seasonNumber+".xml").retrieveStats();
    }
    /**
     * TODO doc
     */
    public String transformGame_1x_to_2x() throws ManagerException {
        season_2x.getGame().clear();
        LolApiService apiService = new LolApiService();

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
                List<Season.Game> foundGames = apiService.tryFindGames(seasonNumber,date,new String[]{"RANKED_SOLO_5x5","TEAM_BUILDER_DRAFT_RANKED_5x5"});
                for (GameRecord dbGame : tempGameList){
                    int MatchedGameCounter = 0;
                    Season.Game matchedGame = null;
                    for (Season.Game apiGame : foundGames){
                        String apiChamp = apiGame.getUser().getChamp().replace("'","").replace(" ","").toLowerCase();
                        if (dbGame.getMyChampion().toString().toLowerCase().equals(apiChamp)){
                            Player.Score apiScore = apiGame.getUser().getScore();
                            Score transformedScore = new Score(Integer.parseInt(apiScore.getKills()),
                                    Integer.parseInt(apiScore.getDeaths()),
                                    Integer.parseInt(apiScore.getAssists()));
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
                        errorGame.getUserMate().addAll(Arrays.asList(new Player(), new Player(),new Player(), new Player()));
                        errorGame.getOpponent().addAll(Arrays.asList(new Player(), new Player(),new Player(), new Player(), new Player()));
                        errorGame.setNumber(String.valueOf(dbGame.getGameNumber()));
                        if (dbGame.getGameResult().equals(Result.Victory)) {
                            errorGame.getUserTeam().setWin(String.valueOf(true));
                            errorGame.getEnemyTeam().setWin(String.valueOf(false));
                        } else {
                            errorGame.getUserTeam().setWin(String.valueOf(false));
                            errorGame.getEnemyTeam().setWin(String.valueOf(true));
                        }
                        errorGame.setDate(dbGame.getDate().getStringDate());
                        errorGame.getUser().setRole(String.valueOf(dbGame.getMyRole()));
                        errorGame.setLength(String.valueOf(dbGame.getGameLength())+"00");
                        errorGame.getUser().setGold(String.valueOf(dbGame.getGoldEarned()));
                        errorGame.getUser().setFarm(String.valueOf(dbGame.getMinionsKilled()));
                        errorGame.getUser().setChamp(String.valueOf(dbGame.getMyChampion()));
                        errorGame.getUserTeam().setFirstBlood(String.valueOf(dbGame.getMyTeamFB()));
                        errorGame.getEnemyTeam().setFirstBlood(String.valueOf(!dbGame.getMyTeamFB()));
                        errorGame.getUser().getScore().setKills(String.valueOf(dbGame.getScore().getKills()));
                        errorGame.getUser().getScore().setDeaths(String.valueOf(dbGame.getScore().getDeaths()));
                        errorGame.getUser().getScore().setAssists(String.valueOf(dbGame.getScore().getAssists()));
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
     * TODO doc
     */
    public String transformRank_1x_to_2x() throws ManagerException {
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
                Season.Game.Rank rank2x = new Season.Game.Rank();
                rank2x.setTier("undefined");
                rank2x.setDivision("");
                rank2x.setLp("");

                System.out.println(game1x.getGameNumber() +" "+ game1x.getRank().getStringOffsetLP() + " <=> " + rank2x.getTier() + " "
                        + rank2x.getDivision() + " "+rank2x.getLp());
                okTransformation++;
                season_2x.getGame().get(g).setRank(rank2x);
                continue;
            }
            if (rank1x.equals("null")){
                Season.Game.Rank rank2x = new Season.Game.Rank();
                rank2x.setTier("unranked");
                rank2x.setDivision("");
                rank2x.setLp("");

                System.out.println(game1x.getGameNumber() +" "+ game1x.getRank().getStringOffsetLP() + " <=> " + rank2x.getTier() + " "
                        + rank2x.getDivision() + " "+rank2x.getLp());
                okTransformation++;
                season_2x.getGame().get(g).setRank(rank2x);
                continue;
            }
            Season.Game.Rank rank2x = new Season.Game.Rank();

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
            season_2x.getGame().get(g).setRank(rank2x);
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

    /**
     * TODO doc
     */
    public String transformRole_1x_to_2x() throws ManagerException {

        int roleAlreadySet = 0;
        int roleNok = 0;
        int roleOk = 0;
        int roleOkError = 0;
        for (int g=0;g<season_1x.size();g++){
            GameRecord game1x = season_1x.get(g);
            Season.Game game2x = season_2x.getGame().get(g);
            if (Role.isValue(game2x.getUser().getRole()) /*game1x.getMyRole().toString().equals(game2x.getUser().getRole())*/){
                roleAlreadySet++;
                continue;
            }
            int[] rolesCheck = new int[]{0,0,0,0,0};
            List<Player> players = new ArrayList<>();
            players.add(game2x.getUser());
            players.addAll(game2x.getUserMate());
            players.addAll(game2x.getOpponent());
            Player tempEnemyDuo = null;
            Player tempUserDuo = null;
            for (Player player : players){
                if (player.getRole().equals("") || player.getRole().split(" ").length !=2){
                    continue;
                }
                String line = player.getRole().split(" ")[0];
                String role = player.getRole().split(" ")[1];
                if (line.equals("MIDDLE") && role.equals("SOLO")){
                    rolesCheck[0] += 1;
                }
                if (line.equals("MIDDLE") && role.equals("DUO_CARRY")){
                    player.setRole("MIDDLE SOLO");
                    rolesCheck[0] += 1;
                }
                if (line.equals("BOTTOM") && role.equals("DUO_CARRY")){
                    rolesCheck[1] += 1;
                }
                if (line.equals("BOTTOM") && role.equals("DUO_SUPPORT")){
                    rolesCheck[2] += 1;
                }
                if (line.equals("JUNGLE") && role.equals("NONE")){
                    rolesCheck[3] += 1;
                }
                if (line.equals("MIDDLE") && role.equals("DUO_SUPPORT")){
                    player.setRole("JUNGLE NONE");
                    rolesCheck[3] += 1;
                }
                if (line.equals("TOP") && role.equals("SOLO")){
                    rolesCheck[4] += 1;
                }
                if (line.equals("BOTTOM") && role.equals("DUO")){
                    if (game2x.getUser().equals(player) || game2x.getUserMate().contains(player)){
                        if (tempUserDuo == null){
                            tempUserDuo = player;
                        } else {
                            if (Integer.parseInt(player.getFarm()) > Integer.parseInt(tempUserDuo.getFarm())){
                                player.setRole("BOTTOM DUO_CARRY");
                                tempUserDuo.setRole("BOTTOM DUO_SUPPORT");
                            } else {
                                player.setRole("BOTTOM DUO_SUPPORT");
                                tempUserDuo.setRole("BOTTOM DUO_CARRY");
                            }
                            rolesCheck[1] += 1;
                            rolesCheck[2] += 1;
                        }
                    }
                    if (game2x.getOpponent().contains(player)){
                        if (tempEnemyDuo == null){
                            tempEnemyDuo = player;
                        } else {
                            if (Integer.parseInt(player.getFarm()) > Integer.parseInt(tempEnemyDuo.getFarm())){
                                player.setRole("BOTTOM DUO_CARRY");
                                tempEnemyDuo.setRole("BOTTOM DUO_SUPPORT");
                            } else {
                                player.setRole("BOTTOM DUO_SUPPORT");
                                tempEnemyDuo.setRole("BOTTOM DUO_CARRY");
                            }
                            rolesCheck[1] += 1;
                            rolesCheck[2] += 1;
                        }
                    }
                }
            }
            if (rolesCheck[0] == 2 && rolesCheck[1] == 2 && rolesCheck[2] == 2 && rolesCheck[3] == 2 && rolesCheck[4] == 2){
                String userLine = game2x.getUser().getRole().split(" ")[0];
                String userRole = game2x.getUser().getRole().split(" ")[1];
                if (true/*game1x.getMyRole().equals(Role.getRoleByApi(userLine,userRole))*/){
                    for (Player player : players) {
                        String line = player.getRole().split(" ")[0];
                        String role = player.getRole().split(" ")[1];
                        player.setRole(String.valueOf(Role.getRoleByApi(line,role)));
                    }
                    roleOk++;
                } else {
                    String log = "##### " + game1x.getGameNumber() + " ";
                    log += game1x.getMyRole() + " =?= " + game2x.getUser().getRole() + " | [";
                    for (Player player : game2x.getUserMate()){
                        log += player.getRole() + ",";
                    }
                    log += "] | [";
                    for (Player player : game2x.getOpponent()){
                        log += player.getRole() + ",";
                    }
                    log += "]";
                    System.out.println(log);
                    roleOkError++;
                }
            } else {
                String log = Arrays.toString(rolesCheck) +" "+ game1x.getGameNumber() + " ";
                log += game1x.getMyRole() + " =?= " + game2x.getUser().getRole() + " | [";
                for (Player player : game2x.getUserMate()){
                    log += player.getRole() + ",";
                }
                log += "] | [";
                for (Player player : game2x.getOpponent()){
                    log += player.getRole() + ",";
                }
                log += "]";
                System.out.println(log);
                roleNok++;
            }
        }
        manager2x.updateSeason(season_2x);

        return "Updating finished:\n"
                + "    role already set: " + roleAlreadySet + "\n"
                + "    role can't be decided: " + roleNok + " <-update manually\n"
                + "    role decided: " + (roleOk+roleOkError) + "\n"
                + "        role updated: " + roleOk + "\n"
                + "        ERROR: roles NOT equal (not updated): " + roleOkError + " <-update manually\n";
    }
}
