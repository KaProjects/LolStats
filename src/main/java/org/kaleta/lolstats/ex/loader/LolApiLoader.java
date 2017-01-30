package org.kaleta.lolstats.ex.loader;

import org.eclipse.jetty.util.ajax.JSON;
import org.kaleta.lolstats.ex.entities.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Stanislav Kaleta
 * Date: 1.11.2015
 */
public class LolApiLoader {
    private final String apiUrl = "https://eune.api.pvp.net/api/lol/";
    private final String apiKey = "?api_key={KEY}";
    private final String recentMatchesUrl = "eune/v1.3/game/by-summoner/{ID}/recent";
    private final String champsInfoUrl = "static-data/eune/v1.2/champion/"; // + champId
    private final String playerByName = "eune/v1.4/summoner/by-name/{NICK}";
    //private final String matchInfoUrl = "eune/v2.2/match/"; // + matchId
    public LolApiLoader(){
        // config mnger get summ. name, api key, region
    }

    public List<GameRecord> loadDataFromLolApi(){
        String data = getData(apiUrl + recentMatchesUrl + apiKey);
        if (data == null){
            return null;
        } else {
            return parseData((HashMap) JSON.parse(data));
        }
    }

    private String getData(String stringUrl){
        try {
            URL url = new URL(stringUrl);
            URLConnection connection = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            return null;
        }
    }

    private List<GameRecord> parseData(HashMap rootData){
        Object[] games = (Object[]) rootData.get("games");

        List<GameRecord> records = new ArrayList<>();
        for(int g=0;g<games.length;g++){
            if (!(((HashMap)games[g]).get("subType")).equals("RANKED_SOLO_5x5")){
                continue;
            }
            GameRecord record = new GameRecord();

            Date d = new Date((Long) ((HashMap)games[g]).get("createDate"));
            String dateString = new SimpleDateFormat ("ddMMyyyy").format(d);
            org.kaleta.lolstats.ex.entities.Date date = new org.kaleta.lolstats.ex.entities.Date();
            date.setStringDate(dateString);
            record.setDate(date);

            Long champId = (Long) ((HashMap)games[g]).get("championId");
            String champInfo = getData(apiUrl + champsInfoUrl + champId + apiKey);
            String champName = (String) ((HashMap) JSON.parse(champInfo)).get("name");
            champName = champName.replace(" ", "");
            record.setMyChampion(Champion.valueOf(champName));

            HashMap stats = (HashMap) ((HashMap) games[g]).get("stats");

            GameLength gameLength = new GameLength();
            Long mins = ((Long)stats.get("timePlayed"))/60;
            gameLength.setStringGameLength(String.valueOf(mins),true);
            record.setGameLength(gameLength);

            boolean result = (boolean) stats.get("win");
            record.setGameResult((result) ? Result.Victory : Result.Defeat);

            Score score = new Score();
            Long kills = (Long) stats.get("championsKilled");
            score.setKills((kills == null) ? 0 : Integer.valueOf(String.valueOf(kills)));
            Long deaths = (Long) stats.get("numDeaths");
            score.setDeaths((deaths == null) ? 0 : Integer.valueOf(String.valueOf(deaths)));
            Long assists = (Long) stats.get("assists");
            score.setAssists((assists == null) ? 0 : Integer.valueOf(String.valueOf(assists)));
            record.setScore(score);

            Long cs = (Long) stats.get("minionsKilled");
            cs = (cs == null) ? 0 : cs;
            Long monsters = (Long) stats.get("neutralMinionsKilled");
            monsters = (monsters == null) ? 0 : monsters;
            record.setMinionsKilled((int) (cs + monsters));

            Long gold = (Long) stats.get("goldEarned");
            record.setGoldEarned((int) (gold/100*100));

            records.add(record);
        }
        return records;
    }
}
