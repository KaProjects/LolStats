package org.kaleta.lolstats.frontend.dialog;


import org.kaleta.lolstats.backend.entity.Player;
import org.kaleta.lolstats.backend.entity.Role;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.frontend.common.NumberFilter;
import org.kaleta.lolstats.frontend.component.ComboBoxRenderer;
import org.kaleta.lolstats.frontend.component.InputNumberField;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class AddGameDialog extends JDialog {
    private JTextField textFieldGameNumber;
//    private JDatePicker datePicker; // TODO when 2.0 jdatepicker releasted
    private JTextField tfDatePicker;
    private JComboBox comboBoxTier;
    private JComboBox comboBoxDivision;
    private JTextField textFieldLp;
    private JSpinner spinnerLength;

    private JRadioButton userTeamVictory;
    private JRadioButton enemyTeamVictory;
    private JRadioButton userTeamFb;
    private JRadioButton enemyTeamFb;
    private JTextField textFieldTurretsUser;
    private JTextField textFieldTurretsEnemy;
    private JTextField textFieldInhUser;
    private JTextField textFieldInhEnemy;
    private JTextField textFieldDrakeUser;
    private JTextField textFieldDrakeEnemy;
    private JTextField textFieldNashUser;
    private JTextField textFieldNashEnemy;
    private PlayerStatsPanel userStats;
    private PlayerStatsPanel userTeammate1;
    private PlayerStatsPanel userTeammate2;
    private PlayerStatsPanel userTeammate3;
    private PlayerStatsPanel userTeammate4;
    private PlayerStatsPanel userEnemy1;
    private PlayerStatsPanel userEnemy2;
    private PlayerStatsPanel userEnemy3;
    private PlayerStatsPanel userEnemy4;
    private PlayerStatsPanel userEnemy5;

    private boolean result;

    public AddGameDialog(Component parent){
        result = false;
        this.setTitle("Add Game");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLocationRelativeTo(parent);
        initComponents();
        this.pack();
    }

    private void initComponents() {
        Font labelFont = new Font(Font.DIALOG, Font.BOLD, 15);

        JLabel labelGameNumber = new JLabel("Game Number:");
        labelGameNumber.setFont(labelFont);
        textFieldGameNumber = new InputNumberField();
        textFieldGameNumber.setText(String.valueOf(DataSourceService.getLastInsertedGameNumber() + 1));

        JLabel labelDate = new JLabel("Date:");
        labelDate.setFont(labelFont);
//        ComponentFormatDefaults.getInstance().setFormat(ComponentFormatDefaults.Key.SELECTED_DATE_FIELD, new SimpleDateFormat("dd.MM.yyyy"));
//        ComponentColorDefaults.getInstance().setColor(ComponentColorDefaults.Key.BG_MONTH_SELECTOR, Color.LIGHT_GRAY);
//        datePicker = new JDatePicker(new Date(System.currentTimeMillis()));
//        datePicker.getComponent(0).setFont(textFieldFont);
        tfDatePicker = new InputNumberField();

        JLabel labelTier = new JLabel("Tier:");
        labelTier.setFont(labelFont);
        JLabel labelDiv = new JLabel("Division:");
        labelDiv.setFont(labelFont);
        JLabel labelLp = new JLabel("LP:");
        labelLp.setFont(labelFont);
        comboBoxTier = new JComboBox<>(new String[]{"Bronze", "Silver", "Gold", "Platinum", "Diamond", "Master"});
        comboBoxTier.setRenderer(new ComboBoxRenderer(ComboBoxRenderer.ICON_AND_TEXT));
        comboBoxTier.setFont(new Font(new JList().getFont().getName(), Font.BOLD, 20));
        Season.Game.Rank lastRank = DataSourceService.getLastInsertedRank();
        if (lastRank != null) {
            String tier = lastRank.getTier();
            comboBoxTier.setSelectedItem(tier.substring(0, 1) + tier.substring(1).toLowerCase());
        } else {
            comboBoxTier.setSelectedIndex(-1);
        }
        comboBoxDivision = new JComboBox<>(new String[]{"I", "II", "III", "IV", "V"});
        comboBoxDivision.setRenderer(new ComboBoxRenderer(ComboBoxRenderer.TEXT_ONLY));
        comboBoxDivision.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        if (lastRank != null) {
            comboBoxDivision.setSelectedItem(lastRank.getDivision());
        } else {
            comboBoxDivision.setSelectedIndex(-1);
        }
        textFieldLp = new InputNumberField();

        JLabel labelLength = new JLabel("Length:");
        labelLength.setFont(labelFont);

        spinnerLength = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerLength, "mm:ss");
        spinnerLength.setEditor(timeEditor);
        spinnerLength.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        spinnerLength.setValue(new Date(0));

        JSeparator separator1 = new JSeparator();

        JLabel labelYourTeam = new JLabel("Your Team:");
        labelYourTeam.setFont(labelFont);
        JLabel labelEnemyTeam = new JLabel("Enemy Team:");
        labelEnemyTeam.setFont(labelFont);
        JLabel labelVictory = new JLabel("Victory");
        labelVictory.setFont(labelFont);
        JLabel labelFb = new JLabel("FB");
        labelFb.setFont(labelFont);
        userTeamVictory = new JRadioButton();
        enemyTeamVictory = new JRadioButton();
        ButtonGroup victoryGroup = new ButtonGroup();
        victoryGroup.add(userTeamVictory);
        victoryGroup.add(enemyTeamVictory);
        userTeamFb = new JRadioButton();
        enemyTeamFb = new JRadioButton();
        ButtonGroup fbGroup = new ButtonGroup();
        fbGroup.add(userTeamFb);
        fbGroup.add(enemyTeamFb);

        JLabel labelTurret = new JLabel("Turrets");
        labelTurret.setFont(labelFont);
        textFieldTurretsUser = new InputNumberField();
        textFieldTurretsEnemy = new InputNumberField();
        JLabel labelInhibs = new JLabel("Inhibitors");
        labelInhibs.setFont(labelFont);
        textFieldInhUser = new InputNumberField();
        textFieldInhEnemy = new InputNumberField();
        JLabel labelDrakes = new JLabel("Dragons");
        labelDrakes.setFont(labelFont);
        textFieldDrakeUser = new InputNumberField();
        textFieldDrakeEnemy = new InputNumberField();
        JLabel labelnashors = new JLabel("Nashors");
        labelnashors.setFont(labelFont);
        textFieldNashUser = new InputNumberField();
        textFieldNashEnemy = new InputNumberField();

        JSeparator separator2 = new JSeparator();

        ButtonGroup groupPlayerFb = new ButtonGroup();
        userStats = new PlayerStatsPanel(groupPlayerFb);
        userTeammate1 = new PlayerStatsPanel(groupPlayerFb);
        userTeammate2 = new PlayerStatsPanel(groupPlayerFb);
        userTeammate3 = new PlayerStatsPanel(groupPlayerFb);
        userTeammate4 = new PlayerStatsPanel(groupPlayerFb);
        userEnemy1 = new PlayerStatsPanel(groupPlayerFb);
        userEnemy2 = new PlayerStatsPanel(groupPlayerFb);
        userEnemy3 = new PlayerStatsPanel(groupPlayerFb);
        userEnemy4 = new PlayerStatsPanel(groupPlayerFb);
        userEnemy5 = new PlayerStatsPanel(groupPlayerFb);

        JPanel panelUser = new JPanel();
        panelUser.setLayout(new GridLayout(1, 1));
        panelUser.add(userStats);
        panelUser.setBorder(BorderFactory.createTitledBorder(DataSourceService.getLolApiConfig().getNick()));

        JPanel panelMates = new JPanel();
        panelMates.setLayout(new GridLayout(4, 1));
        panelMates.add(userTeammate1);
        panelMates.add(userTeammate2);
        panelMates.add(userTeammate3);
        panelMates.add(userTeammate4);
        panelMates.setBorder(BorderFactory.createTitledBorder("Teammates"));

        JPanel panelEnemies = new JPanel();
        panelEnemies.setLayout(new GridLayout(5, 1));
        panelEnemies.add(userEnemy1);
        panelEnemies.add(userEnemy2);
        panelEnemies.add(userEnemy3);
        panelEnemies.add(userEnemy4);
        panelEnemies.add(userEnemy5);
        panelEnemies.setBorder(BorderFactory.createTitledBorder("Enemies"));

        JSeparator separator3 = new JSeparator();

        JButton buttonOk = new JButton("OK");
        JButton buttonCancel = new JButton("Cancel");

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelGameNumber, 100, 100, 100)
                                                .addGap(5)
                                                .addComponent(textFieldGameNumber, 60, 60, 60))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelDate, 100, 100, 100)
                                                .addGap(5)
                                                .addComponent(tfDatePicker, 140, 140, 140))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelLength, 100, 100, 100)
                                                .addGap(5)
                                                .addComponent(spinnerLength, 75, 75, 75)))
                                .addGap(20)
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelTier, 60, 60, 60)
                                                .addGap(5)
                                                .addComponent(comboBoxTier, 140, 140, 140))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelDiv, 60, 60, 60)
                                                .addGap(5)
                                                .addComponent(comboBoxDivision, 80, 80, 80))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelLp, 60, 60, 60)
                                                .addGap(5)
                                                .addComponent(textFieldLp, 50, 50, 50))))
                        .addComponent(separator1)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(labelYourTeam, 90, 90, 90)
                                        .addComponent(labelEnemyTeam, 90, 90, 90))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(labelVictory)
                                        .addComponent(userTeamVictory, 20, 20, 20)
                                        .addComponent(enemyTeamVictory, 20, 20, 20))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(labelFb)
                                        .addComponent(userTeamFb, 20, 20, 20)
                                        .addComponent(enemyTeamFb, 20, 20, 20))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(labelTurret)
                                        .addComponent(textFieldTurretsUser, 40, 40, 40)
                                        .addComponent(textFieldTurretsEnemy, 40, 40, 40))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(labelInhibs)
                                        .addComponent(textFieldInhUser, 40, 40, 40)
                                        .addComponent(textFieldInhEnemy, 40, 40, 40))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(labelDrakes)
                                        .addComponent(textFieldDrakeUser, 40, 40, 40)
                                        .addComponent(textFieldDrakeEnemy, 40, 40, 40))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(labelnashors)
                                        .addComponent(textFieldNashUser, 40, 40, 40)
                                        .addComponent(textFieldNashEnemy, 40, 40, 40)))
                        .addComponent(separator2)
                        .addGroup(layout.createParallelGroup()
                                .addComponent(panelUser)
                                .addComponent(panelMates)
                                .addComponent(panelEnemies))
                        .addComponent(separator3)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100)
                                .addComponent(buttonCancel, 100, 100, 100)
                                .addGap(5)
                                .addComponent(buttonOk, 100, 100, 100)))
                .addGap(10));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(labelGameNumber)
                                        .addComponent(textFieldGameNumber, 25, 25, 25))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(labelDate)
                                        .addComponent(tfDatePicker, 25, 25, 25))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(labelLength)
                                        .addComponent(spinnerLength, 25, 25, 25)))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(labelTier)
                                        .addComponent(comboBoxTier, 30, 30, 30))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(labelDiv)
                                        .addComponent(comboBoxDivision, 30, 30, 30))
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(labelLp)
                                        .addComponent(textFieldLp, 25, 25, 25))))
                .addGap(5)
                .addComponent(separator1, 5, 5, 5)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(labelYourTeam, 25, 25, 25)
                                .addComponent(labelEnemyTeam, 25, 25, 25))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelVictory, 20, 20, 20)
                                .addComponent(userTeamVictory, 25, 25, 25)
                                .addComponent(enemyTeamVictory, 25, 25, 25))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelFb, 20, 20, 20)
                                .addComponent(userTeamFb, 25, 25, 25)
                                .addComponent(enemyTeamFb, 25, 25, 25))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTurret, 20, 20, 20)
                                .addComponent(textFieldTurretsUser, 25, 25, 25)
                                .addComponent(textFieldTurretsEnemy, 25, 25, 25))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelInhibs, 20, 20, 20)
                                .addComponent(textFieldInhUser, 25, 25, 25)
                                .addComponent(textFieldInhEnemy, 25, 25, 25))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelDrakes, 20, 20, 20)
                                .addComponent(textFieldDrakeUser, 25, 25, 25)
                                .addComponent(textFieldDrakeEnemy, 25, 25, 25))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelnashors, 20, 20, 20)
                                .addComponent(textFieldNashUser, 25, 25, 25)
                                .addComponent(textFieldNashEnemy, 25, 25, 25)))
                .addGap(5)
                .addComponent(separator2, 5, 5, 5)
                .addGap(5)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panelUser)
                        .addComponent(panelMates)
                        .addComponent(panelEnemies))
                .addGap(5)
                .addComponent(separator3)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonCancel, 25, 25, 25)
                        .addComponent(buttonOk, 25, 25, 25))
                .addGap(10));

        buttonOk.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String msg = null;
                if (!userEnemy1.isFilled() || !userEnemy2.isFilled() || !userEnemy3.isFilled()
                        || !userEnemy4.isFilled() || !userEnemy5.isFilled()) {
                    msg = "user opponents are not properly set!";
                }
                if (!userTeammate1.isFilled() || !userTeammate2.isFilled() || !userTeammate3.isFilled()
                        || !userTeammate4.isFilled()) {
                    msg = "user teammates are not properly set!";
                }
                if (!userStats.isFilled()) {
                    msg = "user stats are not properly set!";
                }
                if (textFieldTurretsUser.getText().equals("") || textFieldTurretsEnemy.getText().equals("")
                        || textFieldInhUser.getText().equals("") || textFieldInhEnemy.getText().equals("")
                        || textFieldDrakeUser.getText().equals("") || textFieldDrakeEnemy.getText().equals("")
                        || textFieldNashUser.getText().equals("") || textFieldNashEnemy.getText().equals("")) {
                    msg = "Team numbers not set!";
                }
                if (!userTeamFb.isSelected() && !enemyTeamFb.isSelected()) {
                    msg = "Team first blood not set!";
                }
                if (!userTeamVictory.isSelected() && !enemyTeamVictory.isSelected()) {
                    msg = "Winning team not set!";
                }
                if (comboBoxTier.getSelectedItem() != null && textFieldLp.getText().equals("")) {
                    msg = "LP not set!";
                }
                if (comboBoxTier.getSelectedItem() != null && comboBoxDivision.getSelectedItem() == null) {
                    msg = "division not selected!";
                }
//                if (datePicker.getModel().getValue() == null) {
//                    msg = "date not set!";
//                }
                if (tfDatePicker.getText().equals("")){
                    msg = "date not set!";
                }
                if (textFieldGameNumber.getText().equals("")) {
                    msg = "game number not set!";
                }
                if (msg == null) {
                    result = true;
                    AddGameDialog.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(AddGameDialog.this, msg, "Something missing...", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddGameDialog.this.dispose();
            }
        });
    }

    public void setUpDialog(Season.Game game) {
        if (!game.getNumber().equals("")) {
            textFieldGameNumber.setText(game.getNumber());
        }
//        if (game.getDate().equals("")) {
//            datePicker.getModel().setDate(0, 0, 0);
//        } else {
//            org.kaleta.lolstats.ex.entities.Date date = new org.kaleta.lolstats.ex.entities.Date();
//            date.setStringDate(game.getDate());
//            datePicker.getModel().setDate(date.getYear(), date.getMonth() - 1, date.getDay());
//        }
        tfDatePicker.setText(game.getDate());
        if (!game.getLength().equals("")) {
            spinnerLength.getModel().setValue(new Date(Integer.parseInt(game.getLength().substring(0, 2)) * 60000
                    + Integer.parseInt(game.getLength().substring(2, 4)) * 1000));
        }
        if (!game.getRank().getTier().equals("undefined") && !game.getRank().getTier().equals("")) {
            comboBoxTier.setSelectedItem(game.getRank().getTier().substring(0, 1) + game.getRank().getTier().substring(1).toLowerCase());
            comboBoxDivision.setSelectedItem(game.getRank().getDivision());
            textFieldLp.setText(game.getRank().getLp());
        }
        if (!game.getUserTeam().getWin().equals("") && !game.getEnemyTeam().getWin().equals("")) {
            userTeamVictory.setSelected(Boolean.parseBoolean(game.getUserTeam().getWin()));
            enemyTeamVictory.setSelected(Boolean.parseBoolean(game.getEnemyTeam().getWin()));
        }
        if (!game.getUserTeam().getFirstBlood().equals("") && !game.getEnemyTeam().getFirstBlood().equals("")) {
            userTeamFb.setSelected(Boolean.parseBoolean(game.getUserTeam().getFirstBlood()));
            enemyTeamFb.setSelected(Boolean.parseBoolean(game.getEnemyTeam().getFirstBlood()));
        }
        textFieldTurretsUser.setText(game.getUserTeam().getTurrets());
        textFieldInhUser.setText(game.getUserTeam().getInhibitors());
        textFieldDrakeUser.setText(game.getUserTeam().getDragon());
        textFieldNashUser.setText(game.getUserTeam().getBaron());
        textFieldTurretsEnemy.setText(game.getEnemyTeam().getTurrets());
        textFieldInhEnemy.setText(game.getEnemyTeam().getInhibitors());
        textFieldDrakeEnemy.setText(game.getEnemyTeam().getDragon());
        textFieldNashEnemy.setText(game.getEnemyTeam().getBaron());

        userStats.setPlayer(game.getUser());
        userTeammate1.setPlayer(game.getUserMate().get(0));
        userTeammate2.setPlayer(game.getUserMate().get(1));
        userTeammate3.setPlayer(game.getUserMate().get(2));
        userTeammate4.setPlayer(game.getUserMate().get(3));
        userEnemy1.setPlayer(game.getOpponent().get(0));
        userEnemy2.setPlayer(game.getOpponent().get(1));
        userEnemy3.setPlayer(game.getOpponent().get(2));
        userEnemy4.setPlayer(game.getOpponent().get(3));
        userEnemy5.setPlayer(game.getOpponent().get(4));
    }

    public boolean getResult(){
        return result;
    }

    public Season.Game getGame(){
        Season.Game newGame = new Season.Game();

        newGame.setNumber(textFieldGameNumber.getText());
        //newGame.setDate(new SimpleDateFormat("ddMMyyyy").format(datePicker.getModel().getValue()));
        newGame.setDate(tfDatePicker.getText());
        if (comboBoxTier.getSelectedItem() != null){
            newGame.getRank().setTier(((String)comboBoxTier.getSelectedItem()).toUpperCase());
            newGame.getRank().setDivision((String) comboBoxDivision.getSelectedItem());
            newGame.getRank().setLp(textFieldLp.getText());
        } else {
            newGame.getRank().setTier("undefined");
            newGame.getRank().setDivision("");
            newGame.getRank().setLp("");
        }
        newGame.setLength(new SimpleDateFormat("mmss").format(spinnerLength.getModel().getValue()));

        newGame.getUserTeam().setWin(String.valueOf(userTeamVictory.isSelected()));
        newGame.getUserTeam().setFirstBlood(String.valueOf(userTeamFb.isSelected()));
        newGame.getUserTeam().setTurrets(textFieldTurretsUser.getText());
        newGame.getUserTeam().setInhibitors(textFieldInhUser.getText());
        newGame.getUserTeam().setDragon(textFieldDrakeUser.getText());
        newGame.getUserTeam().setBaron(textFieldNashUser.getText());

        newGame.getEnemyTeam().setWin(String.valueOf(enemyTeamVictory.isSelected()));
        newGame.getEnemyTeam().setFirstBlood(String.valueOf(enemyTeamFb.isSelected()));
        newGame.getEnemyTeam().setTurrets(textFieldTurretsEnemy.getText());
        newGame.getEnemyTeam().setInhibitors(textFieldInhEnemy.getText());
        newGame.getEnemyTeam().setDragon(textFieldDrakeEnemy.getText());
        newGame.getEnemyTeam().setBaron(textFieldNashEnemy.getText());

        newGame.setUser(userStats.getPlayer());
        newGame.getUserMate().add(userTeammate1.getPlayer());
        newGame.getUserMate().add(userTeammate2.getPlayer());
        newGame.getUserMate().add(userTeammate3.getPlayer());
        newGame.getUserMate().add(userTeammate4.getPlayer());
        newGame.getOpponent().add(userEnemy1.getPlayer());
        newGame.getOpponent().add(userEnemy2.getPlayer());
        newGame.getOpponent().add(userEnemy3.getPlayer());
        newGame.getOpponent().add(userEnemy4.getPlayer());
        newGame.getOpponent().add(userEnemy5.getPlayer());

        return newGame;
    }

    public class PlayerStatsPanel extends JPanel{
        private JComboBox<String> cbChamp;
        private JComboBox<String> cbRole;
        private JTextField tfFarm;
        private JTextField tfGold;
        private JTextField tfTurrets;
        private JRadioButton rbFb;
        private JTextField tfK;
        private JTextField tfD;
        private JTextField tfA;
        private JTextField tfS;
        private JTextField tfDouble;
        private JTextField tfTriple;
        private JTextField tfQuadra;
        private JTextField tfPenta;
        private JTextField tfDmgT;
        private JTextField tfDmgCh;
        private JTextField tfWardP;
        private JTextField tfWardD;

        public PlayerStatsPanel(ButtonGroup fbGroup){
            this.initComponents();
            fbGroup.add(rbFb);
        }

        private void initComponents() {
            Font labelFont = new Font(Font.DIALOG, Font.BOLD, 15);
            Font textFieldFont = new Font(Font.DIALOG, Font.BOLD, 20);

            JLabel labelChamp = new JLabel("Champion:");
            labelChamp.setFont(labelFont);
            String[] champs = new String[1];
            champs = DataSourceService.getChampList().toArray(champs);
            cbChamp = new JComboBox<String>(champs){
                @Override
                public void repaint() {
                    try {
                        if (this.getSelectedIndex() == -1){
                            this.setBackground(Color.getHSBColor(0/360f,0.75f,1));
                        } else {
                            this.setBackground(new JComboBox<>().getBackground());
                        }
                    } catch (Exception e){}
                    super.repaint();
                }
            };
            cbChamp.setRenderer(new ComboBoxRenderer(ComboBoxRenderer.TEXT_ONLY));
            cbChamp.setSelectedIndex(-1);

            JLabel labelRole = new JLabel("Role:");
            labelRole.setFont(labelFont);
            cbRole = new JComboBox<String>(Role.stringValues()){
                @Override
                public void repaint() {
                    try {
                        if (this.getSelectedIndex() == -1 || this.getSelectedItem().equals(Role.UNDEFINED.toString())){
                            this.setBackground(Color.getHSBColor(0/360f,0.75f,1));
                        } else {
                            this.setBackground(new JComboBox<>().getBackground());
                        }
                    } catch (Exception e){}
                    super.repaint();
                }
            };
            cbRole.setRenderer(new ComboBoxRenderer(ComboBoxRenderer.TEXT_ONLY));
            cbRole.setSelectedIndex(-1);

            JLabel labelFarm = new JLabel("Farm:");
            labelFarm.setFont(labelFont);
            tfFarm = new InputNumberField();
            JLabel labelGold = new JLabel("Gold:");
            labelGold.setFont(labelFont);
            tfGold = new InputNumberField();
            JLabel labelTurrets = new JLabel("Turrets:");
            labelTurrets.setFont(labelFont);
            tfTurrets = new InputNumberField();
            JLabel labelFb = new JLabel("FB:");
            labelFb.setFont(labelFont);
            rbFb = new JRadioButton();

            JLabel labelKills = new JLabel("K:");
            labelKills.setFont(labelFont);
            tfK = new InputNumberField();
            JLabel labelDeaths = new JLabel("D:");
            labelDeaths.setFont(labelFont);
            tfD = new InputNumberField();
            JLabel labelAssists = new JLabel("A:");
            labelAssists.setFont(labelFont);
            tfA = new InputNumberField();
            JLabel labelSpree = new JLabel("Spree:");
            labelSpree.setFont(labelFont);
            tfS = new InputNumberField();

            JLabel labelDouble = new JLabel("Double:");
            labelDouble.setFont(labelFont);
            tfDouble = new InputNumberField();
            JLabel labelTriple = new JLabel("Triple:");
            labelTriple.setFont(labelFont);
            tfTriple = new InputNumberField();
            JLabel labelQuadra = new JLabel("Quadra:");
            labelQuadra.setFont(labelFont);
            tfQuadra = new InputNumberField();
            JLabel labelPenta = new JLabel("Penta:");
            labelPenta.setFont(labelFont);
            tfPenta = new InputNumberField();

            JLabel labelDmgT = new JLabel("Dmg Total:");
            labelDmgT.setFont(labelFont);
            tfDmgT = new InputNumberField();
            JLabel labelDmgCh = new JLabel("Dmg to Champs:");
            labelDmgCh.setFont(labelFont);
            tfDmgCh = new InputNumberField();

            JLabel labelWardP = new JLabel("Wards Placed:");
            labelWardP.setFont(labelFont);
            tfWardP = new InputNumberField();
            JLabel labelWArdD = new JLabel("Wards Destroyed:");
            labelWArdD.setFont(labelFont);
            tfWardD = new InputNumberField();

            GroupLayout layout = new GroupLayout(this);
            this.setLayout(layout);
            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                            .addComponent(labelChamp, 20, 20, 20)
                            .addComponent(cbChamp, 25, 25, 25)
                            .addComponent(labelKills, 20, 20, 20)
                            .addComponent(tfK, 25, 25, 25)
                            .addComponent(labelDeaths, 20, 20, 20)
                            .addComponent(tfD, 25, 25, 25)
                            .addComponent(labelAssists, 20, 20, 20)
                            .addComponent(tfA, 25, 25, 25)
                            .addComponent(labelSpree, 20, 20, 20)
                            .addComponent(tfS, 25, 25, 25)
                            .addComponent(labelDouble, 20, 20, 20)
                            .addComponent(tfDouble, 25, 25, 25)
                            .addComponent(labelTriple, 20, 20, 20)
                            .addComponent(tfTriple, 25, 25, 25)
                            .addComponent(labelQuadra, 20, 20, 20)
                            .addComponent(tfQuadra, 25, 25, 25)
                            .addComponent(labelPenta, 20, 20, 20)
                            .addComponent(tfPenta, 25, 25, 25)
                            .addComponent(labelWArdD, 20, 20, 20)
                            .addComponent(tfWardD, 25, 25, 25))
                    .addGroup(layout.createParallelGroup()
                            .addComponent(labelRole, 20, 20, 20)
                            .addComponent(cbRole, 25, 25, 25)
                            .addComponent(labelFb, 20, 20, 20)
                            .addComponent(rbFb, 25, 25, 25)
                            .addComponent(labelFarm, 20, 20, 20)
                            .addComponent(tfFarm, 25, 25, 25)
                            .addComponent(labelGold, 20, 20, 20)
                            .addComponent(tfGold, 25, 25, 25)
                            .addComponent(labelTurrets, 20, 20, 20)
                            .addComponent(tfTurrets, 25, 25, 25)
                            .addComponent(labelDmgT, 20, 20, 20)
                            .addComponent(tfDmgT, 25, 25, 25)
                            .addComponent(labelDmgCh, 20, 20, 20)
                            .addComponent(tfDmgCh, 25, 25, 25)
                            .addComponent(labelWardP, 20, 20, 20)
                            .addComponent(tfWardP, 25, 25, 25))
                    .addGap(5));
            layout.setHorizontalGroup(layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(labelChamp).addGap(5)
                            .addComponent(cbChamp, 100, 100, 100)
                            .addGap(5)
                            .addComponent(labelKills).addGap(5)
                            .addComponent(tfK, 35, 35, 35)
                            .addGap(5)
                            .addComponent(labelDeaths).addGap(5)
                            .addComponent(tfD, 35, 35, 35)
                            .addGap(5)
                            .addComponent(labelAssists).addGap(5)
                            .addComponent(tfA, 35, 35, 35)
                            .addGap(5)
                            .addComponent(labelSpree).addGap(5)
                            .addComponent(tfS, 35, 35, 35)
                            .addGap(5)
                            .addComponent(labelDouble).addGap(5)
                            .addComponent(tfDouble, 25, 25, 25)
                            .addGap(5)
                            .addComponent(labelTriple).addGap(5)
                            .addComponent(tfTriple, 25, 25, 25)
                            .addGap(5)
                            .addComponent(labelQuadra).addGap(5)
                            .addComponent(tfQuadra, 25, 25, 25)
                            .addGap(5)
                            .addComponent(labelPenta).addGap(5)
                            .addComponent(tfPenta, 25, 25, 25)
                            .addGap(5)
                            .addComponent(labelWArdD).addGap(5)
                            .addComponent(tfWardD, 25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                            .addComponent(labelRole).addGap(5)
                            .addComponent(cbRole, 100, 100, 100)
                            .addGap(5)
                            .addComponent(labelFb).addGap(5)
                            .addComponent(rbFb, 20, 20, 20)
                            .addGap(5)
                            .addComponent(labelFarm).addGap(5)
                            .addComponent(tfFarm, 50, 50, 50)
                            .addGap(5)
                            .addComponent(labelGold).addGap(5)
                            .addComponent(tfGold, 65, 65, 65)
                            .addGap(5)
                            .addComponent(labelTurrets).addGap(5)
                            .addComponent(tfTurrets, 25, 25, 25)
                            .addGap(5)
                            .addComponent(labelDmgT).addGap(5)
                            .addComponent(tfDmgT, 70, 70, 70)
                            .addGap(5)
                            .addComponent(labelDmgCh).addGap(5)
                            .addComponent(tfDmgCh, 60, 60, 60)
                            .addGap(5)
                            .addComponent(labelWardP).addGap(5)
                            .addComponent(tfWardP, 30, 30, 30)));
        }

        public void setPlayer(Player player){
            cbChamp.setSelectedItem(player.getChamp());
            if (!player.getRole().equals("")){
                if (Role.isValue(player.getRole())){
                    cbRole.setSelectedItem(player.getRole());
                } else {
                    String[] apiRoles = player.getRole().split(" ");
                    cbRole.setSelectedItem(Role.getRoleByApi(apiRoles[0],apiRoles[1]).toString());
                }
            }
            tfFarm.setText(player.getFarm());
            tfGold.setText(player.getGold());
            tfTurrets.setText(player.getTurrets());
            if (!player.getFb().equals("")){
                rbFb.setSelected(Boolean.parseBoolean(player.getFb()));
            }
            tfK.setText(player.getScore().getKills());
            tfD.setText(player.getScore().getDeaths());
            tfA.setText(player.getScore().getAssists());
            tfS.setText(player.getScore().getMaxKillingSpree());
            tfDouble.setText(player.getScore().getMulti().getDouble());
            tfTriple.setText(player.getScore().getMulti().getTriple());
            tfQuadra.setText(player.getScore().getMulti().getQuadra());
            tfPenta.setText(player.getScore().getMulti().getPenta());
            tfDmgT.setText(player.getDmg().getTotal());
            tfDmgCh.setText(player.getDmg().getToChamps());
            tfWardP.setText(player.getWard().getPlaced());
            tfWardD.setText(player.getWard().getDestroyed());
        }

        public Player getPlayer(){
            Player newPlayer = new Player();
            newPlayer.setChamp((String) cbChamp.getSelectedItem());
            newPlayer.setRole(String.valueOf(cbRole.getSelectedItem()));
            newPlayer.setFarm(tfFarm.getText());
            newPlayer.setGold(tfGold.getText());
            newPlayer.setTurrets(tfTurrets.getText());
            newPlayer.setFb(String.valueOf(rbFb.isSelected()));
            newPlayer.getScore().setKills(tfK.getText());
            newPlayer.getScore().setDeaths(tfD.getText());
            newPlayer.getScore().setAssists(tfA.getText());
            newPlayer.getScore().setMaxKillingSpree(tfS.getText());
            newPlayer.getScore().getMulti().setDouble(tfDouble.getText());
            newPlayer.getScore().getMulti().setTriple(tfTriple.getText());
            newPlayer.getScore().getMulti().setQuadra(tfQuadra.getText());
            newPlayer.getScore().getMulti().setPenta(tfPenta.getText());
            newPlayer.getDmg().setTotal(tfDmgT.getText());
            newPlayer.getDmg().setToChamps(tfDmgCh.getText());
            newPlayer.getWard().setPlaced(tfWardP.getText());
            newPlayer.getWard().setDestroyed(tfWardD.getText());
            return newPlayer;
        }

        public boolean isFilled(){
            return (cbChamp.getSelectedIndex() != -1) && (cbRole.getSelectedIndex() != -1)
                    && !tfFarm.getText().equals("") && !tfGold.getText().equals("") && !tfTurrets.getText().equals("")
                    && !tfK.getText().equals("") && !tfD.getText().equals("") && !tfA.getText().equals("") && !tfS.getText().equals("")
                    && !tfDouble.getText().equals("") && !tfTriple.getText().equals("") && !tfQuadra.getText().equals("") && !tfPenta.getText().equals("")
                    && !tfDmgT.getText().equals("") && !tfDmgCh.getText().equals("")
                    && !tfWardP.getText().equals("") && !tfWardD.getText().equals("");
        }
    }
}
