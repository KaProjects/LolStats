package org.kaleta.lolstats.ex.graph.line.data;

import org.kaleta.lolstats.ex.entities.GameRecord;
import org.kaleta.lolstats.ex.entities.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Stanislav Kaleta
 * Date: 12.6.2015
 */
public class LpPerGameDataModel implements LineDataModel{
    private Integer[] ker;
    private Integer[] im;
    private Integer[][] func;

    public LpPerGameDataModel(){
        ker = new Integer[]{};
        im = new Integer[]{};
        func = new Integer[][]{};
    }

    @Override
    public void initData(List<GameRecord> records) {
        List<Integer> xValues = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();
        List<Result> rValues = new ArrayList<>();
        int itr = 0;
        for (GameRecord record : records) {
            xValues.add(itr, record.getGameNumber());
            String yValue = record.getRank().getStringOffsetLP();
            if (yValue == null){
                yValues.add(itr, null);
            } else {
                yValues.add(itr, Integer.valueOf(yValue));
            }

            rValues.add(itr, record.getGameResult());
            itr++;
        }

        int yMin = Short.MAX_VALUE;
        int yMax = 0;
        for (Integer yValue : yValues){
            if (yValue == null){
                continue;
            }
            yMin = (yValue < yMin) ? yValue : yMin;
            yMax = (yValue > yMax) ? yValue : yMax;
        }
        yMin = (yMin/100);
        int tempMaxRemainder = yMax % 100;
        yMax = yMax/100;
        if (tempMaxRemainder != 0){
            yMax++;
        }
        int imLength = (yMax - yMin)*100 + 1;
        im = new Integer[imLength];
        for (int i = 0;i<imLength;i++){
            im[i] = yMin*100 + i;
        }

        int xMin = Short.MAX_VALUE;
        int xMax = 0;
        for (Integer xValue : xValues){
            xMin = (xValue < xMin) ? xValue : xMin;
            xMax = (xValue > xMax) ? xValue : xMax;
        }
        int kerLength = xMax - xMin + 1;
        ker = new Integer[kerLength];
        for (int i = 0;i<kerLength;i++){
            ker[i] = xMin + i;
        }

        func = new Integer[3][kerLength];
        for (int i = 0;i<kerLength;i++){
            if (yValues.get(i) == null){
                func[0][i] = null;
            } else {
                func[0][i] = yValues.get(i) - yMin*100;
            }


            switch (rValues.get(i)){
                case Victory:
                    func[1][i] = 0;
                    break;
                case Defeat:
                    func[1][i] = 1;
                    break;
                case LossPrevented:
                    func[1][i] = 2;
                    break;
                default:
                    func[1][i] = null;
            }
        }

        setUpModel(ker, im, func);
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
            if (func[0][i] != null && (func[0][i] < 0 || func[0][i] >= im.length)){
                throw new IllegalArgumentException("func not match im indexes!");
            }
            if ( func[1][i] == null || !(func[1][i] == 0 || func[1][i] == 1 || func[1][i] == 2)){
                throw new IllegalArgumentException("func not match defined values!");
            }
        }

        this.ker = (Integer[]) ker;
        this.im = (Integer[]) im;
        this.func = func;
    }

    @Override
    public Integer getXSize() {
        return ker.length;
    }

    @Override
    public String getXMark(int i) {
        String mark  = String.valueOf(ker[i]);
        return (i % 20 == 0) ? mark : null;/*TODO via base*/
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
        return func[var][x];
    }

    public int getYminimum(){
        return im[0];
    }
}
