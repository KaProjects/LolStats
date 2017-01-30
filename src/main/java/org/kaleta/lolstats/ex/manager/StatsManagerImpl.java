package org.kaleta.lolstats.ex.manager;

import org.kaleta.lolstats.backend.entity.Role;
import org.kaleta.lolstats.ex.ServiceFailureException;
import org.kaleta.lolstats.ex.entities.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 14.1.2015
 */
public class StatsManagerImpl implements StatsManager {
    private String statsFileURI;

    public StatsManagerImpl(String fileName) {
        //File databaseDir = new File(Initializer.DATA_SOURCE);
        File databaseDir = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"database/");

        if (!databaseDir.exists()){
            databaseDir.mkdir();/*TODO result maybe*/
            System.out.println("dir database created"); /*TODO log*/
        }

        statsFileURI = databaseDir.getPath()+File.separator+fileName;

        boolean fileExists = false;
        for (File f : databaseDir.listFiles()){   /*TODO solve null*/
            if(f.getName().equals(fileName)){
                fileExists = true;
            }
        }
        if (!fileExists){
            int result = JOptionPane.showConfirmDialog(null, fileName+" do not exist. Create new file?","Error",JOptionPane.YES_NO_OPTION);
            if (result == 0){
                try {
                    DocumentBuilderFactory bFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = bFactory.newDocumentBuilder();
                    Document document = builder.newDocument();

                    Element rootE = document.createElement("stats");
                    document.appendChild(rootE);

                    Element recordsE = document.createElement("records");
                    rootE.appendChild(recordsE);

                    TransformerFactory tFactory = TransformerFactory.newInstance();
                    Transformer transformer = tFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult streamResult = new StreamResult(statsFileURI);
                    transformer.transform(source, streamResult);
                    System.out.println("INFO: "+statsFileURI+" successfully created");/*TODO log*/

                } catch (ParserConfigurationException e) {
                    System.out.println("Error creating " + fileName + " - "+e); /*TODO log*/
                } catch (TransformerConfigurationException e) {
                    System.out.println("Error creating " + fileName + " - "+e); /*TODO log*/
                } catch (TransformerException e) {
                    System.out.println("Error creating " + fileName + " - "+e); /*TODO log*/
                }

            }
        }
    }

    @Override
    public List<GameRecord> retrieveStats() throws ServiceFailureException {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            Document document = builder.parse(new File(statsFileURI));
            NodeList records =  document.getDocumentElement().getElementsByTagName("records").item(0).getChildNodes();

            List<GameRecord> output = new ArrayList<>();
            for (int i=0;i<records.getLength();i++){
                NamedNodeMap recordAttrs = records.item(i).getAttributes();

                GameRecord record = new GameRecord();
                try {
                    record.setGameNumber(Integer.parseInt(recordAttrs.getNamedItem("game").getTextContent()));
                } catch (NumberFormatException ignore){
                    // value remains null
                }

                Date date = new Date();
                date.setStringDate(recordAttrs.getNamedItem("date").getTextContent());
                record.setDate(date);

                try {
                    record.setMyRole(Role.valueOf(recordAttrs.getNamedItem("role").getTextContent()));
                } catch (IllegalArgumentException ignore){
                    // value remains UNDEFINED
                }

                try {
                    record.setMyChampion(Champion.valueOf(recordAttrs.getNamedItem("champ").getTextContent()));
                } catch (IllegalArgumentException ignore){
                    // value remains UNDEFINED
                }

                try {
                    GameLength length = new GameLength();
                    length.setStringGameLength(recordAttrs.getNamedItem("length").getTextContent(), true);
                    record.setGameLength(length);
                } catch (NumberFormatException ignore){
                    // value remains null
                }

                try {
                    Rank rank = new Rank();
                    String rankValue = recordAttrs.getNamedItem("lp").getTextContent();

                    rank.setStringOffsetLP((rankValue.equals("null")) ? null : rankValue);
                    record.setRank(rank);
                } catch (NumberFormatException ignore){
                    // value remains null
                }

                try {
                    record.setWinRatio(Integer.parseInt(recordAttrs.getNamedItem("ratio").getTextContent()));
                } catch (NumberFormatException ignore){
                    // value remains null
                }

                try {
                    record.setGameResult(Result.valueOf(recordAttrs.getNamedItem("result").getTextContent()));
                } catch (IllegalArgumentException ignore){
                    // value remains UNDEFINED
                }

                record.setMyTeamFB(Boolean.parseBoolean(recordAttrs.getNamedItem("teamfb").getTextContent()));/*TODO try,catch if cant parse*/

                Score score = new Score();
                score.setStringScore(recordAttrs.getNamedItem("score").getTextContent());
                record.setScore(score);

                try {
                    record.setMinionsKilled(Integer.parseInt(recordAttrs.getNamedItem("cs").getTextContent()));
                } catch (NumberFormatException ignore){
                    // value remains null
                }

                try {
                    record.setGoldEarned(Integer.parseInt(recordAttrs.getNamedItem("mygold").getTextContent()));
                } catch (NumberFormatException ignore){
                    // value remains null
                }

                output.add(record);
            }

            return output;
        } catch (ParserConfigurationException e) {
            /*TODO log*/
            throw new ServiceFailureException("ERROR: retrieving stats! (ParserConfigurationException): ",e);
        } catch (SAXException e){
           /*TODO log*/
            throw new ServiceFailureException("ERROR: retrieving stats! (SAXException): ",e);
        } catch (IOException e){
            /*TODO log*/
            throw new ServiceFailureException("ERROR: retrieving stats! (IOException): ",e);
        } catch (NullPointerException e){
            /*TODO log*/
            throw new ServiceFailureException("ERROR: retrieving stats! (NullPointerException): ",e);
        }
    }

    @Override
    public void updateStats(List<GameRecord> gameRecords) throws ServiceFailureException {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(statsFileURI));

            Element rootE = document.getDocumentElement();

            Element recordsE = (Element)rootE.getElementsByTagName("records").item(0);

            NodeList recordNodes = recordsE.getChildNodes();

            /*TODO double for and if not equals update that element*/
            List<Integer> gamesSaved = new ArrayList<>();
            for (int i=0;i<recordNodes.getLength();i++){
                NamedNodeMap recordAttrs = recordNodes.item(i).getAttributes();
                gamesSaved.add(Integer.parseInt(recordAttrs.getNamedItem("game").getTextContent()));
            }
            for (GameRecord gameRecord : gameRecords){
                boolean gameNotSaved = true;
                for (Integer gameNumber : gamesSaved){
                    if(gameNumber.equals(gameRecord.getGameNumber())){
                        gameNotSaved = false;
                    }
                }
                if (gameNotSaved){
                    Element recordE = document.createElement("record");

                    Attr gameA = document.createAttribute("game");
                    gameA.setValue(String.valueOf(gameRecord.getGameNumber()));
                    recordE.setAttributeNode(gameA);

                    Attr dateA = document.createAttribute("date");
                    dateA.setValue(gameRecord.getDate().getStringDate());
                    recordE.setAttributeNode(dateA);

                    Attr roleA = document.createAttribute("role");
                    roleA.setValue(String.valueOf(gameRecord.getMyRole()));
                    recordE.setAttributeNode(roleA);

                    Attr champA = document.createAttribute("champ");
                    champA.setValue(String.valueOf(gameRecord.getMyChampion()));
                    recordE.setAttributeNode(champA);

                    Attr lengthA = document.createAttribute("length");
                    lengthA.setValue(gameRecord.getGameLength().getStringGameLength(true));
                    recordE.setAttributeNode(lengthA);

                    Attr lpA = document.createAttribute("lp");
                    String lpValue = gameRecord.getRank().getStringOffsetLP();
                    lpA.setValue((lpValue == null) ? "null" : lpValue);
                    recordE.setAttributeNode(lpA);

                    Attr ratioA = document.createAttribute("ratio");
                    ratioA.setValue(String.valueOf(gameRecord.getWinRatio()));
                    recordE.setAttributeNode(ratioA);

                    Attr resultA = document.createAttribute("result");
                    resultA.setValue(String.valueOf(gameRecord.getGameResult()));
                    recordE.setAttributeNode(resultA);

                    Attr teamFBA = document.createAttribute("teamfb");
                    teamFBA.setValue(String.valueOf(gameRecord.getMyTeamFB()));
                    recordE.setAttributeNode(teamFBA);

                    Attr scoreA = document.createAttribute("score");
                    scoreA.setValue(gameRecord.getScore().getStringScore());
                    recordE.setAttributeNode(scoreA);

                    Attr csA = document.createAttribute("cs");
                    csA.setValue(String.valueOf(gameRecord.getMinionsKilled()));
                    recordE.setAttributeNode(csA);

                    Attr goldEarnedA = document.createAttribute("mygold");
                    goldEarnedA.setValue(String.valueOf(gameRecord.getGoldEarned()));
                    recordE.setAttributeNode(goldEarnedA);

                    recordsE.appendChild(recordE);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(statsFileURI);
            transformer.transform(source, result);
            System.out.println("INFO: States successfully updated");/*TODO log*/

        } catch (ParserConfigurationException e) {
            /*TODO log*/
            throw new ServiceFailureException("ERROR: updating stats! (ParserConfigurationException): ",e);
        } catch (SAXException e) {
            /*TODO log*/
            throw new ServiceFailureException("ERROR: updating stats! (SAXException): ",e);
        } catch (IOException e) {
            /*TODO log*/
            throw new ServiceFailureException("ERROR: updating stats! (IOException): ",e);
        } catch (TransformerConfigurationException e) {
            /*TODO log*/
            throw new ServiceFailureException("ERROR: updating stats! (TransformerConfigurationException): ",e);
        } catch (TransformerException e) {
            /*TODO log*/
            throw new ServiceFailureException("ERROR: updating stats! (TransformerException): ",e);
        }
    }

}
