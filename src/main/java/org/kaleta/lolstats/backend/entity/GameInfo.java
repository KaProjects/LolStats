package org.kaleta.lolstats.backend.entity;

/**
 * Created by Stanislav Kaleta on 04.03.2016.
 */
public class GameInfo {
    private String id;
    private String dateInMillis;
    private String win;
    private String k;
    private String d;
    private String a;

    public GameInfo() {
        this.id = null;
        this.dateInMillis = null;
        this.win = null;
        this.k = null;
        this.d = null;
        this.a = null;
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

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameInfo gameInfo = (GameInfo) o;

        if (id != null ? !id.equals(gameInfo.id) : gameInfo.id != null) return false;
        if (dateInMillis != null ? !dateInMillis.equals(gameInfo.dateInMillis) : gameInfo.dateInMillis != null)
            return false;
        if (win != null ? !win.equals(gameInfo.win) : gameInfo.win != null) return false;
        if (k != null ? !k.equals(gameInfo.k) : gameInfo.k != null) return false;
        if (d != null ? !d.equals(gameInfo.d) : gameInfo.d != null) return false;
        return a != null ? a.equals(gameInfo.a) : gameInfo.a == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateInMillis != null ? dateInMillis.hashCode() : 0);
        result = 31 * result + (win != null ? win.hashCode() : 0);
        result = 31 * result + (k != null ? k.hashCode() : 0);
        result = 31 * result + (d != null ? d.hashCode() : 0);
        result = 31 * result + (a != null ? a.hashCode() : 0);
        return result;
    }
}
