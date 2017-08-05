package org.kaleta.lolstats.backend.entity;

/**
 * Created by Stanislav Kaleta on 04.03.2016.
 */
public class GameIdentifier {
    private String id;
    private String dateInMillis;
    private String champion;

    public GameIdentifier(String id, String dateInMillis) {
        this.id = id;
        this.dateInMillis = dateInMillis;
    }

    public GameIdentifier() {
        this.id = null;
        this.dateInMillis = null;
        this.champion = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateInMillis() {
        return dateInMillis;
    }

    public void setDateInMillis(String dateInMillis) {
        this.dateInMillis = dateInMillis;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }
}
