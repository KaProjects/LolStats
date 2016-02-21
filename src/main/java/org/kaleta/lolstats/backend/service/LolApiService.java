package org.kaleta.lolstats.backend.service;

import org.eclipse.jetty.util.ajax.JSON;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.frontend.ErrorDialog;
import org.kaleta.lolstats.frontend.Initializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 11.02.2016.
 *
 * TODO doc.
 */
public class LolApiService {
    private final String recentMatchUrl;
    private final String champByIdUrl;
    private final String matchByIdUrl;
    private final String matchListUrl;

    private final String playerId;
    private Object[] allPlayerMatches;

    public LolApiService(String region, String apiKey, String playerNick) {
        String protocol = "https://";
        String domain = region + ".api.pvp.net";
        String keyParameter = "?api_key=" + apiKey;
        String sumByNamePath = "/api/lol/"+region+"/v1.4/summoner/by-name/";
        HashMap players = loadData(protocol + domain + sumByNamePath + playerNick + keyParameter);
        playerId = String.valueOf(((HashMap)players.get(playerNick.toLowerCase().replace(" ",""))).get("id"));

        recentMatchUrl = protocol + domain + "/api/lol/" + region + "/v1.3/game/by-summoner/" + playerId + "/recent" + keyParameter;
        champByIdUrl = protocol + domain + "/api/lol/static-data/" + region + "/v1.2/champion/" + "{ID}" + keyParameter;
        matchByIdUrl = protocol + domain + "/api/lol/" + region + "/v2.2/match/{ID}" + keyParameter;
        matchListUrl = protocol + domain + "/api/lol/" + region + "/v2.2/matchlist/by-summoner/" + playerId + keyParameter;
    }

    private HashMap loadData(String url){
        int retryCounter = 0;
        while (retryCounter < 60){
            try {
                URLConnection connection = new URL(url).openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                return (HashMap) JSON.parse(sb.toString());
            } catch (IOException e) {
                if (e.getMessage().contains("Server returned HTTP response code:")){
                    try {
                        if (retryCounter < 10){
                            Thread.sleep(1000);
                        } else {
                            Thread.sleep(2000);
                        }
                    } catch (InterruptedException e1) {}
                    retryCounter++;
                } else {
                    Initializer.LOG.warning(ErrorDialog.getExceptionStackTrace(e));
                    throw new ServiceFailureException(e);
                }
            }
        }
        String msg = "time-out longer than 60 seconds!";
        Initializer.LOG.warning(msg);
        throw new ServiceFailureException(msg);
    }

    /**
     * TODo
     * @return
     */
    public List<Season.Game> getRecentGames() {
        Object[] recentGames = (Object[]) loadData(recentMatchUrl).get("games");
        List<Season.Game> outputGames = new ArrayList<>();
        for (int i = 0; i < recentGames.length; i++) {
            if (!(((HashMap) recentGames[i]).get("subType")).equals("RANKED_SOLO_5x5")) {
                continue;
            }
            String gameId = String.valueOf(((HashMap)recentGames[i]).get("gameId"));
            outputGames.add(getGameById(gameId));
        }
        return outputGames;
    }

    private Season.Game getGameById(String gameId){
        Season.Game gameOutput = new Season.Game();
        HashMap gameData = loadData(matchByIdUrl.replace("{ID}",gameId));
        long playerMatchId = -1;
        int playerIndex = -1;
        HashMap player = null;
        long teamId = -1;
        int teamIndex = -1;
        Object[] participants = (Object[]) gameData.get("participantIdentities");
        for (int i=0;i<participants.length;i++){
            String sId = String.valueOf(((HashMap)((HashMap)participants[i]).get("player")).get("summonerId"));
            if (sId.equals(playerId)){
                playerMatchId = (long) ((HashMap)participants[i]).get("participantId");
                playerIndex = i;
            }
        }
        if (((HashMap)((Object[])gameData.get("participants"))[playerIndex]).get("participantId").equals(playerMatchId)){
            player = (HashMap)((Object[])gameData.get("participants"))[playerIndex];
            teamId = (long) player.get("teamId");
            Object[] teams = (Object[]) gameData.get("teams");
            for (int i=0;i<teams.length;i++){
                if (((HashMap)teams[i]).get("teamId").equals(teamId)){
                    teamIndex = i;
                }
            }
        } else {
            return null;
        }

        String date = new SimpleDateFormat("ddMMyyyy").format(gameData.get("matchCreation"));
        gameOutput.setDate(date);

        String champ = String.valueOf(loadData(champByIdUrl.replace("{ID}",String.valueOf(player.get("championId")))).get("key")).toLowerCase();
        gameOutput.setChampion(champ); //TODO champs are by key to lower case -> add method to get actual name from api for GUI purposes

        String line = (String) ((HashMap)player.get("timeline")).get("lane");
        if (line.equals("BOTTOM")){
            String role = (String) ((HashMap)player.get("timeline")).get("role");
            gameOutput.setRole(role);
        } else {
            gameOutput.setRole(line);
        }

        String duration = new SimpleDateFormat("mmss").format(((Long)gameData.get("matchDuration"))*1000);
        gameOutput.getResult().setLength(duration);

        String win = String.valueOf(((HashMap)((Object[])gameData.get("teams"))[teamIndex]).get("winner"));
        gameOutput.getResult().setWin(win);

        String teamFb = String.valueOf(((HashMap)((Object[])gameData.get("teams"))[teamIndex]).get("firstBlood"));
        gameOutput.getResult().setFirstBlood(teamFb);

        int enemyTeamIndex = (teamIndex + 1) % 2;
        Object[] teams = (Object[]) gameData.get("teams");
        int turretDif = (int) (((long)((HashMap)teams[teamIndex]).get("towerKills")) - ((long)((HashMap)teams[enemyTeamIndex]).get("towerKills")));
        gameOutput.getResult().getTeamDiff().setTurretDiff(String.valueOf(turretDif));
        int inhibDiff = (int) (((long)((HashMap)teams[teamIndex]).get("inhibitorKills")) - ((long)((HashMap)teams[enemyTeamIndex]).get("inhibitorKills")));
        gameOutput.getResult().getTeamDiff().setInhibitorDiff(String.valueOf(inhibDiff));
        int drakeDiff = (int) (((long)((HashMap)teams[teamIndex]).get("dragonKills")) - ((long)((HashMap)teams[enemyTeamIndex]).get("dragonKills")));
        gameOutput.getResult().getTeamDiff().setDrakeDiff(String.valueOf(drakeDiff));
        int nashorDiff = (int) (((long)((HashMap)teams[teamIndex]).get("baronKills")) - ((long)((HashMap)teams[enemyTeamIndex]).get("baronKills")));
        gameOutput.getResult().getTeamDiff().setNashorDiff(String.valueOf(nashorDiff));

        long totalDmgDiff = 0;
        long champDmgDiff = 0;
        long farmDiff = 0;
        long goldDiff = 0;
        long killsDiff = 0;
        for (int i=0;i<((Object[]) gameData.get("participants")).length;i++){
            HashMap p = (HashMap) ((Object[])gameData.get("participants"))[i];
            HashMap stats = (HashMap) p.get("stats");
            if ((p.get("teamId")).equals(teamId)){
                totalDmgDiff += (long) stats.get("totalDamageDealt");
                champDmgDiff += (long) stats.get("totalDamageDealtToChampions");
                farmDiff += ((long) stats.get("minionsKilled") + (long) stats.get("neutralMinionsKilled"));
                goldDiff += (long) stats.get("goldEarned");
                killsDiff += (long) stats.get("kills");
            } else {
                totalDmgDiff -= (long) stats.get("totalDamageDealt");
                champDmgDiff -= (long) stats.get("totalDamageDealtToChampions");
                farmDiff -= ((long) stats.get("minionsKilled") + (long) stats.get("neutralMinionsKilled"));
                goldDiff -= (long) stats.get("goldEarned");
                killsDiff -= (long) stats.get("kills");
            }
        }
        gameOutput.getResult().getTeamDiff().setTotalDmgDiff(String.valueOf(totalDmgDiff));
        gameOutput.getResult().getTeamDiff().setChampDmgDiff(String.valueOf(champDmgDiff));
        gameOutput.getResult().getTeamDiff().setFarmDiff(String.valueOf(farmDiff));
        gameOutput.getResult().getTeamDiff().setGoldDiff(String.valueOf(goldDiff));
        gameOutput.getResult().getTeamDiff().setKillsDiff(String.valueOf(killsDiff));

        String fb = String.valueOf(((HashMap)player.get("stats")).get("firstBloodKill"));
        gameOutput.getPerformance().setFirstBlood(fb);
        String gold = String.valueOf(((HashMap)player.get("stats")).get("goldEarned"));
        gameOutput.getPerformance().setGold(gold);
        String spree = String.valueOf(((HashMap)player.get("stats")).get("largestKillingSpree"));
        gameOutput.getPerformance().setKillingSpree(spree);
        String turrets = String.valueOf(((HashMap)player.get("stats")).get("towerKills"));
        gameOutput.getPerformance().setTurrets(turrets);

        String d = String.valueOf(((HashMap)player.get("stats")).get("doubleKills"));
        gameOutput.getPerformance().getMultiKills().setDouble(d);
        String t = String.valueOf(((HashMap)player.get("stats")).get("tripleKills"));
        gameOutput.getPerformance().getMultiKills().setTriple(t);
        String q = String.valueOf(((HashMap)player.get("stats")).get("quadraKills"));
        gameOutput.getPerformance().getMultiKills().setQuadra(q);
        String penta = String.valueOf(((HashMap)player.get("stats")).get("pentaKills"));
        gameOutput.getPerformance().getMultiKills().setPenta(penta);

        String w = String.valueOf(((HashMap)player.get("stats")).get("wardsPlaced"));
        gameOutput.getPerformance().getWards().setWardPlaced(w);
        String wDestroyed = String.valueOf(((HashMap)player.get("stats")).get("wardsKilled"));
        gameOutput.getPerformance().getWards().setWardDestroyed(wDestroyed);

        long teamKills = 0;
        long teamDeaths = 0;
        long teamAssists = 0;
        long teamTotalDmg = 0;
        long teamChampDmg = 0;
        long teamFarm = 0;
        long myKills = 0;
        long myDeaths = 0;
        long myAssists = 0;
        long myTotalDmg = 0;
        long myChampDmg = 0;
        long myFarm = 0;
        for (int i=0;i<((Object[]) gameData.get("participants")).length;i++){
            HashMap p = (HashMap) ((Object[])gameData.get("participants"))[i];
            HashMap stats = (HashMap) p.get("stats");
            if ((p.get("teamId")).equals(teamId)){
                if ((p.get("participantId")).equals(playerMatchId)){
                    myKills = (long) stats.get("kills");
                    myDeaths = (long) stats.get("deaths");
                    myAssists = (long) stats.get("assists");
                    myTotalDmg = (long) stats.get("totalDamageDealt");
                    myChampDmg = (long) stats.get("totalDamageDealtToChampions");
                    myFarm = (long) stats.get("minionsKilled") + (long) stats.get("neutralMinionsKilled");
                }
                teamKills += (long) stats.get("kills");
                teamDeaths += (long) stats.get("deaths");
                teamAssists += (long) stats.get("assists");
                teamTotalDmg += (long) stats.get("totalDamageDealt");
                teamChampDmg += (long) stats.get("totalDamageDealtToChampions");
                teamFarm += (long) stats.get("minionsKilled") + (long) stats.get("neutralMinionsKilled");
            }
        }
        gameOutput.getPerformance().getScore().setKillsAbsolute(String.valueOf(myKills));
        gameOutput.getPerformance().getScore().setDeathsAbsolute(String.valueOf(myDeaths));
        gameOutput.getPerformance().getScore().setAssistsAbsolute(String.valueOf(myAssists));
        gameOutput.getPerformance().getScore().setKillsTeamRelative(String.format("%.3f",(double) myKills/teamKills));
        gameOutput.getPerformance().getScore().setDeathsTeamRelative(String.format("%.3f",(double) myDeaths/teamDeaths));
        gameOutput.getPerformance().getScore().setAssistsTeamRelative(String.format("%.3f",(double) myAssists/teamAssists));
        gameOutput.getPerformance().getDmg().setChampDmgAbsolute(String.valueOf(myChampDmg));
        gameOutput.getPerformance().getDmg().setTotalDmgAbsolute(String.valueOf(myTotalDmg));
        gameOutput.getPerformance().getDmg().setChampDmgTeamRelative(String.format("%.3f",(double) myChampDmg/teamChampDmg));
        gameOutput.getPerformance().getDmg().setTotalDmgTeamRelative(String.format("%.3f",(double) myTotalDmg/teamTotalDmg));
        gameOutput.getPerformance().getFarm().setFarmAbsolute(String.valueOf(myFarm));
        gameOutput.getPerformance().getFarm().setFarmTeamRelative(String.format("%.3f",(double) myFarm/teamFarm));

        return gameOutput;
    }

    /**
     * TODO
     * @param seasonNumber
     * @param date
     * @param queue
     * @return
     */
    public List<Season.Game> tryFindGame(int seasonNumber, String date, String queue) {
        if (allPlayerMatches == null) {
            allPlayerMatches = (Object[]) loadData(matchListUrl).get("matches");
        }
        List<Season.Game> outputGames = new ArrayList<>();
        for (int g = allPlayerMatches.length - 1; g >= 0; g--) {
            String season = "SEASON201" + seasonNumber;
            String preSeason = "PRESEASON201" + (seasonNumber+1);
            if (season.equals(((HashMap) allPlayerMatches[g]).get("season"))
                    || preSeason.equals(((HashMap) allPlayerMatches[g]).get("season"))) {
                if (queue.equals(((HashMap) allPlayerMatches[g]).get("queue"))){
                    if (date.equals(new SimpleDateFormat("ddMMyyyy").format(((HashMap) allPlayerMatches[g]).get("timestamp")))) {
                        long matchId = (long) ((HashMap) allPlayerMatches[g]).get("matchId");
                        Season.Game game = getGameById(String.valueOf(matchId));
                        outputGames.add(game);
                    }
                }
            }
        }
        return outputGames;
    }
}
