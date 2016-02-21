package org.kaleta.lolstats.graph.line.data;

import org.kaleta.lolstats.entities.GameRecord;

import java.util.List;

/**
 * User: Stanislav Kaleta
 * Date: 12.6.2015
 */
public class LpPerDateDataModel implements LineDataModel {
    private Object[] ker;
    private Integer[] im;
    private Integer[][] func;

    public LpPerDateDataModel(){
        ker = new Object[]{};
        im = new Integer[]{};
        func = new Integer[][]{};
    }

    @Override
    public void initData(List<GameRecord> records) {

    }

    @Override
    public void setUpModel(Object[] ker, Object[] im, Integer[][] func) {
        if (ker == null || im == null || func == null){
            throw new IllegalArgumentException("Some input argument is null!");
        }
        if (func[0].length != ker.length){
            throw new IllegalArgumentException("length of func and ker must be even!");
        }
        for (int i=0;i<func.length;i++){
            if ( func[0][i] == null || (func[0][i] < 0 || func[0][i] >= im.length)){
                throw new IllegalArgumentException("func not match im indexes!");
            }
            if ( func[1][i] == null || !(func[1][i] == 0 || func[1][i] == 1 || func[1][i] == 2)){
                throw new IllegalArgumentException("func not match defined values!");
            }
        }

        this.ker = ker;
        this.im = (Integer[]) im;
        this.func = func;
    }

    @Override
    public Integer getXSize() {
        return ker.length;
    }

    @Override
    public String getXMark(int i) {
        /*TODO*/
        return null;
    }

    @Override
    public Integer getYSize() {
        return im.length;
    }

    @Override
    public String getYMark(int i) {
        String mark  = String.valueOf(im[i]);
        return (i % 100 == 0) ? mark : null;
    }

    @Override
    public Integer getFunctionValue(int x, int var) {
        return null;
    }

    public int getYminimum(){
        return im[0];
    }
}
