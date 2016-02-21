package org.kaleta.lolstats.graph.role;

/**
 * User: Stanislav Kaleta
 * Date: 4.7.2015
 */
public class RoleUtils {

    public static int getPerformenceFor(int total, int wins, int defs, int kills, int deaths, int assists, int cs, int gold){

        int scoreDiff = getScorePoints(total, kills, deaths, assists);

        int farmDiff = getFarmPoints(cs, gold, total);

        float experienceDiff = getExperienceQuantif(total, wins, defs);

        // perf. funkcia
        int performance = (int) ((scoreDiff + farmDiff)* experienceDiff);
        // normovanie do intervalu <0,100>
        performance = (performance > 0) ? performance : 0 ;
        performance = (performance > 100) ? 100 : performance;

        return performance;
    }
    // TODO same as all yet - possible: higher assist ratio, lower farm expectations, etc.
    public static int getSupportPerformenceFor(int total, int wins, int defs, int kills, int deaths, int assists, int cs, int gold){

        int scoreDiff = getSupportScorePoints(total, kills, deaths, assists);

        int farmDiff = getSupportFarmPoints(cs, gold, total);

        float experienceDiff = getExperienceQuantif(total, wins, defs);

        // perf. funkcia
        int performance = (int) ((scoreDiff + farmDiff)* experienceDiff);
        // normovanie do intervalu <0,100>
        performance = (performance > 0) ? performance : 0 ;
        performance = (performance > 100) ? 100 : performance;

        return performance;
    }

    public static int getScorePoints(int total, int kills, int deaths, int assists){
        // K = +1, D = -1, A = +0.5
        return (int) ((kills - deaths + 0.5*assists)/total);
    }

    public static int getSupportScorePoints(int total, int kills, int deaths, int assists){
        // K = +1, D = -1, A = +0.5
        return (int) ((kills - deaths + 0.5*assists)/total);
    }

    public static int getFarmPoints(int cs, int gold, int total){
        // 10000g. je zaklad -> kazda +/-1000 = +/-1 a este kazdych 50cs->+1
        return (cs/total)/50 + (gold/total - 10000)/1000;
    }

    public static int getSupportFarmPoints(int cs, int gold, int total){
        // 10000g. je zaklad -> kazda +/-1000 = +/-1 a este kazdych 50cs->+1
        return (cs/total)/50 + (gold/total - 10000)/1000;
    }

    public static float getExperienceQuantif(int total, int wins, int defs){
        // W/L ratio of<0,2> krat log poctu hier(od 0 do cca 4; 5) a +1 pretoze chcem vacsie ako 1
        return 1 +  (((wins > defs) ? 2 - ((float) defs/wins) : 0 + (float) wins/defs) * (float) Math.log(total));
    }
}
