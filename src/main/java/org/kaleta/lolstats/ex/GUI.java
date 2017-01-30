package org.kaleta.lolstats.ex;

import org.kaleta.lolstats.backend.entity.Role;
import org.kaleta.lolstats.ex.entities.*;
import org.kaleta.lolstats.ex.graph.GraphFrame;
import org.kaleta.lolstats.ex.loader.LolApiLoader;
import org.kaleta.lolstats.ex.loader.PDFResultLoader;
import org.kaleta.lolstats.ex.manager.StatsManager;
import org.kaleta.lolstats.ex.manager.StatsManagerImpl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 14.1.2015
 */
public class GUI extends JFrame {
    private final String VERSION = "1.7.1";
    private final String NAME = "LoL Stats";
    private final Dimension PACKED_SIZE = new Dimension(235, 98); /*TODO automatic resize would be great*/
    private final Dimension UNPACKED_SIZE = new Dimension(585, 300);

    private StatsManager managerSeason3 = new StatsManagerImpl("stats3.xml");
    private StatsManager managerSeason4 = new StatsManagerImpl("stats4.xml");
    private StatsManager managerSeason5 = new StatsManagerImpl("stats5.xml");
    private StatsManager managerSeason6 = new StatsManagerImpl("stats6.xml");
    private PDFResultLoader resultLoader = new PDFResultLoader("StanleyZipper"); /*TODO setable nick*/
    private JFileChooser matchHistoryFileChooser;

    private JButton buttonNewRecord;
    private JButton buttonAddRecord;
    private JLabel labelLastGameRecord;
    private JPanel panelNewRecord;

    private JButton buttonLoadData;
    private JTextField textFieldGameNumber;
    private JTextField textFieldDate;
    private JComboBox<Role> comboBoxMyRole;
    private JComboBox<Champion> comboBoxMyChamp;
    private JTextField textFieldGameLength;
    private JTextField textFieldLP;
    private JTextField textFieldWinRatio;
    private JComboBox<Result> comboBoxGameResult;
    private JCheckBox checkBoxMyTeamFB;
    private JTextField textFieldComplexScore;
    private JTextField textFieldMinionsKilled;
    private JTextField textFieldGoldEarned;

    private JMenuBar menuBar = new JMenuBar(); /*TODO refactoring -> class menu*/
    private JMenu menuStats = new JMenu();

    public GUI(){
        initComponents();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(NAME+" "+VERSION);
        setSize(PACKED_SIZE);
    }

    private void initComponents() {
        buttonLoadData = new JButton();
        textFieldGameNumber = new JTextField();
        textFieldDate = new JTextField();
        comboBoxMyRole = new JComboBox<>(Role.values());
        comboBoxMyRole.setSelectedIndex(-1);
        comboBoxMyChamp = new JComboBox<>(Champion.values());
        comboBoxMyChamp.setSelectedIndex(-1);
        textFieldGameLength = new JTextField();
        textFieldLP = new JTextField();
        textFieldWinRatio = new JTextField();
        comboBoxGameResult = new JComboBox<>(Result.values());
        comboBoxGameResult.setSelectedIndex(-1);
        checkBoxMyTeamFB = new JCheckBox();
        textFieldComplexScore = new JTextField();
        textFieldMinionsKilled = new JTextField();
        textFieldGoldEarned = new JTextField();

        buttonLoadData.setText("Load Data");
        buttonLoadData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO pdf (maybe deprecated)
//                int returnVal = matchHistoryFileChooser.showOpenDialog(GUI.this);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    try {
//                        GameRecord record = resultLoader.loadDataFromPDF(matchHistoryFileChooser.getSelectedFile());
//                        setUpGUI(record);
//                    } catch (ServiceFailureException ex) {
//                        System.err.println(ex.getMessage());
//                        JOptionPane.showMessageDialog(GUI.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
                JPanel panelMatches = new JPanel();
                panelMatches.setLayout(new BoxLayout(panelMatches, BoxLayout.Y_AXIS));

                final JDialog dialog = new JDialog(GUI.this,"Recent Ranked Games");
                final List<GameRecord> records = new LolApiLoader().loadDataFromLolApi();
                for(int i=0;i<records.size();i++){
                    GameRecord record = records.get(i);
                    JLabel matchDef = new JLabel("Game: " + record.getDate().getDay()+"."+
                            record.getDate().getMonth()+"."+record.getDate().getYear()+" "+record.getMyChampion()+" "+
                            record.getScore().getKills()+":"+record.getScore().getDeaths()+":"+
                            record.getScore().getAssists(), JLabel.CENTER);
                    JPanel bgForMatchDef = new JPanel();
                    bgForMatchDef.add(matchDef);
                    final int finalI = i;
                    bgForMatchDef.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            dialog.dispose();
                            setUpGUI(records.get(finalI));
                        }
                    });
                    bgForMatchDef.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelMatches.add(bgForMatchDef);
                }
                dialog.getContentPane().add(panelMatches);
                dialog.setLocationRelativeTo(GUI.this);
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        matchHistoryFileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Match History", "pdf");
        matchHistoryFileChooser.setFileFilter(filter);

        final JLabel labelGameNumber = new JLabel("Game number: ");
        JLabel labelRole = new JLabel("Role: ");
        JLabel labelDate = new JLabel("Date: ");
        JLabel labelChampion = new JLabel("Champion: ");
        JLabel labelGameLength = new JLabel("Game length: ");
        JLabel labelLP = new JLabel("LP: ");
        JLabel labelWinRatio = new JLabel("W-L ratio: ");
        JLabel labelResult = new JLabel("Result: ");
        JLabel labelTeamFB = new JLabel("Team FB: ");
        JLabel labelScore = new JLabel("Score: ");
        JLabel labelCS = new JLabel("Minions killed: ");
        JLabel labelGoldEarned = new JLabel("Gold earned: ");

        Font groupLabelFont = new Font(new JLabel().getFont().getName(),Font.BOLD,20);
        Color groupLabelColor = Color.getHSBColor(120/360f,0.8f,0.6f);
        JLabel labelGroupGameDefinition = new JLabel("Game definition");
        labelGroupGameDefinition.setFont(groupLabelFont);
        labelGroupGameDefinition.setForeground(groupLabelColor);
        JLabel labelGroupGameResults = new JLabel("Game results");
        labelGroupGameResults.setFont(groupLabelFont);
        labelGroupGameResults.setForeground(groupLabelColor);
        JLabel labelGroupPersonalPerform = new JLabel("Personal performance");
        labelGroupPersonalPerform.setFont(groupLabelFont);
        labelGroupPersonalPerform.setForeground(groupLabelColor);

        panelNewRecord = new JPanel();
        panelNewRecord.setVisible(false);
        GroupLayout layoutNR = new GroupLayout(panelNewRecord);
        panelNewRecord.setLayout(layoutNR);
        layoutNR.setHorizontalGroup(layoutNR.createParallelGroup()
                .addComponent(buttonLoadData)
                .addComponent(labelGroupGameDefinition)
                .addGroup(layoutNR.createSequentialGroup()
                        .addComponent(labelGameNumber)
                        .addComponent(textFieldGameNumber, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(labelDate)
                        .addComponent(textFieldDate, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(labelRole)
                        .addComponent(comboBoxMyRole)
                        .addGap(5)
                        .addComponent(labelChampion)
                        .addComponent(comboBoxMyChamp))
                .addComponent(labelGroupGameResults)
                .addGroup(layoutNR.createSequentialGroup()
                        .addComponent(labelGameLength)
                        .addComponent(textFieldGameLength, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(labelLP)
                        .addComponent(textFieldLP, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(labelWinRatio)
                        .addComponent(textFieldWinRatio, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(labelResult)
                        .addComponent(comboBoxGameResult)
                        .addGap(5)
                        .addComponent(labelTeamFB)
                        .addComponent(checkBoxMyTeamFB))
                .addComponent(labelGroupPersonalPerform)
                .addGroup(layoutNR.createSequentialGroup()
                        .addComponent(labelScore)
                        .addComponent(textFieldComplexScore, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(labelCS)
                        .addComponent(textFieldMinionsKilled, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(5)
                        .addComponent(labelGoldEarned)
                        .addComponent(textFieldGoldEarned, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)));
        layoutNR.setVerticalGroup(layoutNR.createSequentialGroup()
                .addComponent(buttonLoadData)
                .addComponent(labelGroupGameDefinition)
                .addGap(5)
                .addGroup(layoutNR.createParallelGroup()
                        .addComponent(labelGameNumber, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldGameNumber, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldDate, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelRole, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxMyRole, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelChampion, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxMyChamp, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .addGap(5)
                .addComponent(labelGroupGameResults)
                .addGap(5)
                .addGroup(layoutNR.createParallelGroup()
                        .addComponent(labelGameLength, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldGameLength, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelLP, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldLP, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelWinRatio, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldWinRatio, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelResult, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxGameResult, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelTeamFB, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkBoxMyTeamFB, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                .addGap(5)
                .addComponent(labelGroupPersonalPerform)
                .addGap(5)
                .addGroup(layoutNR.createParallelGroup()
                        .addComponent(labelScore, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldComplexScore, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelCS, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldMinionsKilled, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelGoldEarned, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldGoldEarned, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)));

        buttonNewRecord = new JButton("New Record");
        buttonNewRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonNewRecord.setEnabled(false);
                buttonAddRecord.setEnabled(true);
                setUpGUI(null);
                Integer lastGameNumber = -1;
                GameRecord lastGame = null;
                for (GameRecord record :  managerSeason6.retrieveStats()){
                    if (record.getGameNumber() > lastGameNumber){
                        lastGameNumber = record.getGameNumber();
                        lastGame = record;
                    }
                }
                if (lastGame != null){
                    textFieldGameNumber.setText(String.valueOf(lastGame.getGameNumber()+1));
                }
                panelNewRecord.setVisible(true);
            }
        });
        buttonAddRecord = new JButton("Add Record");
        buttonAddRecord.setEnabled(false);
        buttonAddRecord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonNewRecord.setEnabled(true);
                buttonAddRecord.setEnabled(false);
                updateStats();
                panelNewRecord.setVisible(false);
            }
        });
        labelLastGameRecord = new JLabel("Last Game: {not found}");
        labelLastGameRecord.setForeground(Color.getHSBColor(0f,0f,0.5f));
        Integer lastGameNumber = -1;
        GameRecord lastGame = null;
        for (GameRecord record :  managerSeason6.retrieveStats()){
            if (record.getGameNumber() > lastGameNumber){
                lastGameNumber = record.getGameNumber();
                lastGame = record;
            }
        }
        if (lastGame != null){
            labelLastGameRecord.setText("Last Game: number="+lastGame.getGameNumber()+" W-R ratio="+lastGame.getWinRatio());
        }


        SpringLayout layoutBG = new SpringLayout();
        this.setLayout(layoutBG);
        this.add(panelNewRecord);
        this.add(buttonNewRecord);
        this.add(buttonAddRecord);
        this.add(labelLastGameRecord);
        layoutBG.putConstraint(SpringLayout.WEST, buttonNewRecord, 5, SpringLayout.WEST, this);
        layoutBG.putConstraint(SpringLayout.NORTH,buttonNewRecord,5,SpringLayout.NORTH,this);
        layoutBG.putConstraint(SpringLayout.WEST,buttonAddRecord,5,SpringLayout.EAST,buttonNewRecord);
        layoutBG.putConstraint(SpringLayout.NORTH,buttonAddRecord,5,SpringLayout.NORTH,this);
        layoutBG.putConstraint(SpringLayout.WEST,labelLastGameRecord,5,SpringLayout.EAST,buttonAddRecord);
        layoutBG.putConstraint(SpringLayout.NORTH,labelLastGameRecord,5,SpringLayout.NORTH,this);
        layoutBG.putConstraint(SpringLayout.WEST,panelNewRecord,5,SpringLayout.WEST,this);
        layoutBG.putConstraint(SpringLayout.NORTH,panelNewRecord,5,SpringLayout.SOUTH,buttonNewRecord);

        JMenuItem menuItemSeason3 = new JMenuItem();
        menuItemSeason3.setText("Season 3");
        menuItemSeason3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        new GraphFrame(managerSeason3.retrieveStats(),"Season 3").setVisible(true);
                        return null;
                    }
                    @Override
                    public void done() {
                        try {
                            get();
                        } catch (InterruptedException ignore) {
                        } catch (java.util.concurrent.ExecutionException e) {
                            throw new ServiceFailureException("ERROR: retrieving stats! (ExecutionException): ",e);
                        }
                    }
                };
                worker.execute();
            }
        });
        JMenuItem menuItemSeason4 = new JMenuItem();
        menuItemSeason4.setText("Season 4");
        menuItemSeason4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        new GraphFrame(managerSeason4.retrieveStats(),"Season 4").setVisible(true);
                        return null;
                    }
                    @Override
                    public void done() {
                        try {
                            get();
                        } catch (InterruptedException ignore) {
                        } catch (java.util.concurrent.ExecutionException e) {
                            throw new ServiceFailureException("ERROR: retrieving stats! (ExecutionException): ",e);
                        }
                    }
                };
                worker.execute();
            }
        });
        JMenuItem menuItemSeason5 = new JMenuItem();
        menuItemSeason5.setText("Season 5");
        menuItemSeason5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        GraphFrame graphFrame = new GraphFrame(managerSeason5.retrieveStats(),"Season 5");
                        graphFrame.setLocationRelativeTo(GUI.this);
                        graphFrame.setVisible(true);
                        return null;
                    }
                    @Override
                    public void done() {
                        try {
                            get();
                        } catch (InterruptedException ignore) {
                        } catch (java.util.concurrent.ExecutionException e) {
                            throw new ServiceFailureException("ERROR: retrieving stats! (ExecutionException): ",e);
                        }
                    }
                };
                worker.execute();
            }
        });

        JMenuItem menuItemSeason6 = new JMenuItem();
        menuItemSeason6.setText("Season 6");
        menuItemSeason6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    public Void doInBackground() {
                        GraphFrame graphFrame = new GraphFrame(managerSeason6.retrieveStats(),"Season 6");
                        graphFrame.setLocationRelativeTo(GUI.this);
                        graphFrame.setVisible(true);
                        return null;
                    }
                    @Override
                    public void done() {
                        try {
                            get();
                        } catch (InterruptedException ignore) {
                        } catch (java.util.concurrent.ExecutionException e) {
                            throw new ServiceFailureException("ERROR: retrieving stats! (ExecutionException): ",e);
                        }
                    }
                };
                worker.execute();
            }
        });


        menuStats.add(menuItemSeason3);
        menuStats.add(menuItemSeason4);
        menuStats.add(menuItemSeason5);
        menuStats.add(menuItemSeason6);

        menuStats.setText("Stats");
        menuBar.add(menuStats);

        this.setJMenuBar(menuBar);
    }

    private void setUpGUI(GameRecord record){
        if (record == null){
            textFieldGameNumber.setText("");
            comboBoxMyRole.setSelectedIndex(-1);
            comboBoxMyChamp.setSelectedIndex(-1);
            textFieldLP.setText("");
            textFieldWinRatio.setText("");
            textFieldDate.setText("");
            textFieldGameLength.setText("");
            comboBoxGameResult.setSelectedIndex(-1);
            checkBoxMyTeamFB.setSelected(false);
            textFieldComplexScore.setText("");
            textFieldMinionsKilled.setText("");
            textFieldGoldEarned.setText("");
        } else {
            textFieldDate.setText(record.getDate().getStringDate());
            comboBoxMyChamp.setSelectedItem(record.getMyChampion());
            textFieldGameLength.setText(String.valueOf(record.getGameLength().getMinutes()));
            comboBoxGameResult.setSelectedItem(record.getGameResult());
            textFieldComplexScore.setText(record.getScore().getStringScore());
            textFieldMinionsKilled.setText(String.valueOf(record.getMinionsKilled()));
            textFieldGoldEarned.setText(String.valueOf(record.getGoldEarned()));
        }

        setSize(UNPACKED_SIZE);
    }
    private void updateStats(){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                GameRecord record = new GameRecord();

                record.setGameNumber(Integer.parseInt(textFieldGameNumber.getText()));

                if (!textFieldDate.getText().trim().isEmpty()){
                    Date date = new Date();
                    date.setStringDate(textFieldDate.getText());
                    record.setDate(date);
                }
                if (comboBoxMyRole.getSelectedIndex() != -1){
                    record.setMyRole((Role)comboBoxMyRole.getSelectedItem());
                }
                if (comboBoxMyChamp.getSelectedIndex() != -1){
                    record.setMyChampion((Champion)comboBoxMyChamp.getSelectedItem());
                }
                if (!textFieldGameLength.getText().trim().isEmpty()){
                    GameLength length = new GameLength();
                    length.setStringGameLength(textFieldGameLength.getText(), true);
                    record.setGameLength(length);
                }
                if (!textFieldLP.getText().trim().isEmpty()){
                    Rank rank = new Rank();
                    rank.setStringOffsetLP(textFieldLP.getText());
                    record.setRank(rank);
                }
                if (!textFieldWinRatio.getText().trim().isEmpty()){
                    record.setWinRatio(Integer.parseInt(textFieldWinRatio.getText()));
                }
                if (comboBoxGameResult.getSelectedIndex() != -1){
                    record.setGameResult((Result)comboBoxGameResult.getSelectedItem());
                }
                record.setMyTeamFB(checkBoxMyTeamFB.isSelected());
                if (!textFieldComplexScore.getText().trim().isEmpty()){
                    Score score = new Score();
                    score.setStringScore(textFieldComplexScore.getText());
                    record.setScore(score);
                }
                if (!textFieldMinionsKilled.getText().trim().isEmpty()){
                    record.setMinionsKilled(Integer.parseInt(textFieldMinionsKilled.getText()));
                }
                if (!textFieldGoldEarned.getText().trim().isEmpty()){
                    record.setGoldEarned(Integer.parseInt(textFieldGoldEarned.getText()));
                }

                List<GameRecord> recordList = managerSeason6.retrieveStats();
                recordList.add(record);
                managerSeason6.updateStats(recordList);

                labelLastGameRecord.setText("Last Game: number="+record.getGameNumber()+" W-R ratio="+record.getWinRatio());
                return null;
            }

            @Override
            public void done() {
                try {
                    get();
                } catch (InterruptedException ignore) {
                } catch (java.util.concurrent.ExecutionException e) {
                    throw new ServiceFailureException("ERROR: updating stats! (ExecutionException): ",e);
                }
            }
        };
        worker.execute();

        setSize(PACKED_SIZE);
    }

    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });

        new LolApiLoader();


    }
}
