package org.kaleta.lolstats.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 1.4.2015
 */
public class Score {
    private int kills;
    private int assists;
    private int deaths;

    public Score(int kills, int assists, int deaths) {
        this.kills = kills;
        this.assists = assists;
        this.deaths = deaths;
    }

    public Score() {
        this.kills = -1;
        this.assists = -1;
        this.deaths = -1;
    }

    public String getStringScore(){
        if (kills < 0 || deaths < 0 || assists < 0) {
            return "-";
        } else {
            return String.format("%02d", kills) + String.format("%02d", deaths) + String.format("%02d", assists);
        }
    }

    public void setStringScore(String score){
        if (score.length() == 6) {
            kills = Integer.parseInt(score.substring(0, 2));
            deaths = Integer.parseInt(score.substring(2, 4));
            assists = Integer.parseInt(score.substring(4));
        }
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score = (Score) o;

        if (assists != score.assists) return false;
        if (deaths != score.deaths) return false;
        if (kills != score.kills) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = kills;
        result = 31 * result + assists;
        result = 31 * result + deaths;
        return result;
    }
}

