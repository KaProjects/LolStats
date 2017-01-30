package org.kaleta.lolstats.ex.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 1.4.2015
 */
public class Rank {
    private Tier tier;
    private Division division;
    private int lp;

    public Rank(Tier tier, Division division, int lp) {
        this.division = division;
        this.lp = lp;
        this.tier = tier;
    }

    public Rank() {
        this.tier = Tier.UNDEFINED;
        this.division = Division.UNDEFINED;
        this.lp = -1;
    }

    /*TODO temp: till XML version not change */ private String offsetLP = "";
    public String getStringOffsetLP(){
        return offsetLP;
    }

    public void setStringOffsetLP(String offsetLP){
        this.offsetLP = offsetLP;
    }



    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rank rank1 = (Rank) o;

        if (lp != rank1.lp) return false;
        if (division != rank1.division) return false;
        if (tier != rank1.tier) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tier.hashCode();
        result = 31 * result + division.hashCode();
        result = 31 * result + lp;
        return result;
    }

    public enum Tier {
        UNDEFINED, UNRANKED, BRONZE, SILVER, GOLD, PLATINUM, DIAMOND, MASTER, CHALLENGER
    }
    public enum Division {
        UNDEFINED, I, II, III, IV, V
    }
}
