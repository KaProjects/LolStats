package org.kaleta.lolstats.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 1.4.2015
 */
public class GameRecord implements Comparable<GameRecord>{
    /*user-input data*/
    private Integer gameNumber; // this is also ID for now
    private Role myRole;
    private Boolean myTeamFB; // my team first blood => true // just for fun to analyze that
    private Rank rank;
    private Integer winRatio; // W-R


    /*api-input data*/
    private Date date;
    private Champion myChampion;
    private GameLength gameLength;
    private Result gameResult;
    private Score score;
    private Integer minionsKilled;
    private Integer goldEarned;

    /*added*/
    /*TODO*/

    public GameRecord(){
        gameNumber = null;
        date = new Date();
        myRole = Role.UNDEFINED;
        myChampion = Champion.UNDEFINED;
        gameLength = null;
        rank = new Rank();
        winRatio = null;
        gameResult = Result.UNDEFINED;
        myTeamFB = null;
        score = new Score();
        minionsKilled = null;
        goldEarned = null;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GameLength getGameLength() {
        return gameLength;
    }

    public void setGameLength(GameLength gameLength) {
        this.gameLength = gameLength;
    }

    public Integer getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(Integer gameNumber) {
        this.gameNumber = gameNumber;
    }

    public Result getGameResult() {
        return gameResult;
    }

    public void setGameResult(Result gameResult) {
        this.gameResult = gameResult;
    }

    public Integer getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(Integer goldEarned) {
        this.goldEarned = goldEarned;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public Integer getMinionsKilled() {
        return minionsKilled;
    }

    public void setMinionsKilled(Integer minionsKilled) {
        this.minionsKilled = minionsKilled;
    }

    public Champion getMyChampion() {
        return myChampion;
    }

    public void setMyChampion(Champion myChampion) {
        this.myChampion = myChampion;
    }

    public Role getMyRole() {
        return myRole;
    }

    public void setMyRole(Role myRole) {
        this.myRole = myRole;
    }

    public Boolean getMyTeamFB() {
        return myTeamFB;
    }

    public void setMyTeamFB(Boolean myTeamFB) {
        this.myTeamFB = myTeamFB;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Integer getWinRatio() {
        return winRatio;
    }

    public void setWinRatio(Integer winRatio) {
        this.winRatio = winRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameRecord record = (GameRecord) o;

        if (date != null ? !date.equals(record.date) : record.date != null) return false;
        if (gameLength != null ? !gameLength.equals(record.gameLength) : record.gameLength != null) return false;
        if (gameNumber != null ? !gameNumber.equals(record.gameNumber) : record.gameNumber != null) return false;
        if (gameResult != record.gameResult) return false;
        if (goldEarned != null ? !goldEarned.equals(record.goldEarned) : record.goldEarned != null) return false;
        if (minionsKilled != null ? !minionsKilled.equals(record.minionsKilled) : record.minionsKilled != null)
            return false;
        if (myChampion != record.myChampion) return false;
        if (myRole != record.myRole) return false;
        if (myTeamFB != null ? !myTeamFB.equals(record.myTeamFB) : record.myTeamFB != null) return false;
        if (rank != null ? !rank.equals(record.rank) : record.rank != null) return false;
        if (score != null ? !score.equals(record.score) : record.score != null) return false;
        if (winRatio != null ? !winRatio.equals(record.winRatio) : record.winRatio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = gameNumber != null ? gameNumber.hashCode() : 0;
        result = 31 * result + (myRole != null ? myRole.hashCode() : 0);
        result = 31 * result + (myChampion != null ? myChampion.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (winRatio != null ? winRatio.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (gameLength != null ? gameLength.hashCode() : 0);
        result = 31 * result + (gameResult != null ? gameResult.hashCode() : 0);
        result = 31 * result + (myTeamFB != null ? myTeamFB.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (minionsKilled != null ? minionsKilled.hashCode() : 0);
        result = 31 * result + (goldEarned != null ? goldEarned.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(GameRecord o) {
        return this.getGameNumber() - o.getGameNumber();
    }


}
