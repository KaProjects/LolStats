package org.kaleta.lolstats.ex.loader;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.kaleta.lolstats.ex.ServiceFailureException;
import org.kaleta.lolstats.ex.entities.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 1.4.2015
 */
public class PDFResultLoader {
    private String player;

    public PDFResultLoader(String player){
        this.player = player;
    }

    public GameRecord loadDataFromPDF(File file) throws ServiceFailureException {
        GameRecord record = new GameRecord();

        String text = loadText(file);
        System.out.print(text);
        List<String> lines = loadLines(text);

        record.setDate(retrieveDate(lines));
        record.setGameLength(retrieveGameLength(lines));
        record.setScore(retrieveScore(lines));
        record.setMinionsKilled(retrieveMinionsKilled(lines));
        record.setGoldEarned(retrieveGoldEarned(lines));
        record.setGameResult(retrieveResult(player,lines));
        record.setMyTeamFB(retrieveTeamFB(lines));

        return record;
    }

    private List<String> loadLines(String text) {
        List<String> lines = new ArrayList<>();
        if (text != null){
            BufferedReader reader = new BufferedReader(new StringReader(text));
            try {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                throw new ServiceFailureException(e.getMessage());
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new ServiceFailureException(e.getMessage());
                }
            }
        }
        return lines;
    }

    private String loadText(File file) {
        PDDocument document = null;
        String output = null;
        try {
            document = PDDocument.load(file);
            if (document.isEncrypted()) {
                document.decrypt("");
            }
            PDFTextStripper textStripper = new PDFTextStripper();
            output = textStripper.getText(document);
        } catch (IOException e) {
            throw new ServiceFailureException(e.getMessage());
        } catch (CryptographyException e) {
            throw new ServiceFailureException("Error: Document is encrypted with a password.");
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
            } catch (IOException e) {
                throw new ServiceFailureException(e.getMessage());
            }
        }
        return output;
    }

    private int countPositionInPDF(String player, List<String> lines){
        int defeatLineNum = -1;
        int victoryLineNum = -1;
        for (String line : lines){
            if (line.contains("DEFEAT")){
                defeatLineNum = lines.indexOf(line);
            }
            if (line.contains("VICTORY")){
                victoryLineNum = lines.indexOf(line);
            }
        }
        if ( defeatLineNum == -1 || victoryLineNum == -1){
            throw new ServiceFailureException("Cant find victory or defeat data (Probably loss prevented game)");
        }

        int position = -1;
        for (int i=1;i<=5;i++){
            String defPlayerLine = lines.get(defeatLineNum + 3*i);
            if (defPlayerLine.contains(player)) {
                position = (defeatLineNum > victoryLineNum) ? i + 5 : i;
            }
            String winPlayerLine = lines.get(victoryLineNum + 3*i);
            if (winPlayerLine.contains(player)) {
                position = (victoryLineNum > defeatLineNum) ? i + 5 : i;
            }
        }
        return position;
    }

    private Date retrieveDate(List<String> lines){
        Date date = null;
        for (String line : lines){
            if (line.contains("Ranked (Draft Mode)")){
                String tempDate = line.split(" ")[4];

                date = new Date();
                if (tempDate.contains(".")){
                    tempDate = tempDate.replace(".","-");
                    String[] tempDateDivided = tempDate.split("-");
                    date.setDay(Integer.parseInt(tempDateDivided[0]));
                    date.setMonth(Integer.parseInt(tempDateDivided[1]));
                    date.setYear(Integer.parseInt(tempDateDivided[2]));
                }
                if (tempDate.contains("/")){
                    String[] tempDateDivided = tempDate.split("/");
                    date.setDay(Integer.parseInt(tempDateDivided[1]));
                    date.setMonth(Integer.parseInt(tempDateDivided[0]));
                    date.setYear(Integer.parseInt(tempDateDivided[2]));
                }
            }
        }
        return date;
    }

    private GameLength retrieveGameLength(List<String> lines){
        GameLength gameLength = null;
        for (String line : lines){
            if (line.contains("Ranked (Draft Mode)")) {
                String tempLength = line.split(" ")[3];
                String[] tempLengthDivided = tempLength.split(":");
                gameLength = new GameLength();
                gameLength.setMinutes(Integer.parseInt(tempLengthDivided[0]));
                gameLength.setSeconds(Integer.parseInt(tempLengthDivided[1]));
            }
        }
        return gameLength;
    }

    private Score retrieveScore(List<String> lines){
        Score score = null;
        int position = countPositionInPDF(player, lines);
        for (String line : lines){
            if (line.contains("KDA")){
                String tempScore = line.split(" ")[position];
                String[] tempScoreDivided = tempScore.split("/");
                score = new Score();
                score.setKills(Integer.parseInt(tempScoreDivided[0]));
                score.setDeaths(Integer.parseInt(tempScoreDivided[1]));
                score.setAssists(Integer.parseInt(tempScoreDivided[2]));
            }
        }
        return score;
    }

    private int retrieveMinionsKilled(List<String> lines){
        int minions = -10000;
        int monsters = -10000;
        int position = countPositionInPDF(player, lines);
        for (String line : lines){
            if (line.contains("Minions Killed") && !line.contains("Neutral")){
                String tempMinions = line.split(" ")[1 + position];
                minions = Integer.parseInt(tempMinions);
            }
            if (line.contains("Neutral Minions Killed") && !line.contains("Jungle")){
                String tempMonsters = line.split(" ")[2 + position];
                try {
                    monsters = Integer.parseInt(tempMonsters);
                } catch (NumberFormatException e){
                    monsters = 0; /*TODO neviem preco "-" z pdf sa nechce rovnat mojmu "-"*/
                }

            }
        }
        return minions + monsters;
    }

    private int retrieveGoldEarned(List<String> lines){
        int gold = -1;
        int position = countPositionInPDF(player, lines);
        for (String line : lines){
            if (line.contains("Gold Earned")){
                String tempGold = line.split(" ")[1 + position];
                tempGold = tempGold.replace(".","-");
                String[] tempGoldDivided = tempGold.split("-");
                int thousand = Integer.parseInt(tempGoldDivided[0]);
                int hundred = Integer.parseInt(tempGoldDivided[1].substring(0,1));
                gold = thousand*1000 + hundred*100;
            }
        }
        return gold;
    }

    private Result retrieveResult(String player, List<String> lines){
        Result result = null;
        int defLineNum = -1;
        int winLineNum = -1;
        for (String line : lines){
            if (line.contains("DEFEAT")){
                defLineNum = lines.indexOf(line);
            }
            if (line.contains("VICTORY")){
                winLineNum = lines.indexOf(line);
            }
        }
        if (defLineNum > 0 && winLineNum > 0){
            int position = countPositionInPDF(player, lines);
            result = (defLineNum > winLineNum) ? ((position > 5) ? Result.Defeat : Result.Victory)
                    : ((position > 5) ? Result.Victory : Result.Defeat);
        }
        /*TODO loss prevented ignored for now (not sure if pdf has such information)*/
        return result;
    }

    private Boolean retrieveTeamFB(List<String> lines){
        Boolean fb = null;
        int position = countPositionInPDF(player, lines);
        for (String line : lines){
            if (line.contains("First Blood")){
                String[] tempFBDivided = line.split(" ");
                int forInit = (position > 5) ? 6 : 1;
                fb = Boolean.FALSE;
                for (int i=forInit;i<=forInit+4;i++){
                    if (tempFBDivided[1 + i].contains("●")){
                        fb = Boolean.TRUE;
                    }
                }
            }
        }
        return fb;
    }




}
