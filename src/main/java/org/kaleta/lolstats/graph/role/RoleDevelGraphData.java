package org.kaleta.lolstats.graph.role;

import org.kaleta.lolstats.entities.Champion;

/**
 * User: Stanislav Kaleta
 * Date: 5.7.2015
 */
public class RoleDevelGraphData {
    private int performancePoints;

    private int scorePoints;
    private float killsAvg;
    private float deathsAvg;
    private float assistsAvg;

    private int farmPoints;
    private float csAvg;
    private float goldAvg;

    private float experienceQuantif;
    private int wins;
    private int defs;
    private int total;

    private Champion lastChampion;

    public Champion getLastChampion() {
        return lastChampion;
    }

    public void setLastChampion(Champion lastChampion) {
        this.lastChampion = lastChampion;
    }

    public float getAssistsAvg() {
        return assistsAvg;
    }

    public void setAssistsAvg(float assistsAvg) {
        this.assistsAvg = assistsAvg;
    }

    public float getCsAvg() {
        return csAvg;
    }

    public void setCsAvg(float csAvg) {
        this.csAvg = csAvg;
    }

    public float getDeathsAvg() {
        return deathsAvg;
    }

    public void setDeathsAvg(float deathsAvg) {
        this.deathsAvg = deathsAvg;
    }

    public int getDefs() {
        return defs;
    }

    public void setDefs(int defs) {
        this.defs = defs;
    }

    public float getExperienceQuantif() {
        return experienceQuantif;
    }

    public void setExperienceQuantif(float experienceQuantif) {
        this.experienceQuantif = experienceQuantif;
    }

    public int getFarmPoints() {
        return farmPoints;
    }

    public void setFarmPoints(int farmPoints) {
        this.farmPoints = farmPoints;
    }

    public float getGoldAvg() {
        return goldAvg;
    }

    public void setGoldAvg(float goldAvg) {
        this.goldAvg = goldAvg;
    }

    public float getKillsAvg() {
        return killsAvg;
    }

    public void setKillsAvg(float killsAvg) {
        this.killsAvg = killsAvg;
    }

    public int getPerformancePoints() {
        return performancePoints;
    }

    public void setPerformancePoints(int performancePoints) {
        this.performancePoints = performancePoints;
    }

    public int getScorePoints() {
        return scorePoints;
    }

    public void setScorePoints(int scorePoints) {
        this.scorePoints = scorePoints;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
