package org.kaleta.lolstats.ex.graph.line.data;

import org.kaleta.lolstats.ex.entities.GameRecord;

import java.util.List;

/**
 * User: Stanislav Kaleta
 * Date: 12.6.2015
 */
public interface LineDataModel {

    /**
     *
     * @param records
     */
    public void initData(List<GameRecord> records);

    /**
     *
     * @param ker
     * @param im
     * @param func
     */
    public void setUpModel(Object[] ker, Object[] im, Integer[][] func);

    /**
     *
     * @return
     */
    public Integer getXSize();

    /**
     *
     * @param i
     * @return
     */
    public String getXMark(int i);

    /**
     *
     * @return
     */
    public Integer getYSize();

    /**
     *
     * @param i
     * @return
     */
    public String getYMark(int i);

    /**
     *
     * @param x
     * @param var
     * @return
     */
    public Integer getFunctionValue(int x, int var);
}
