package org.kaleta.lolstats.frontend.dialog;

import org.jdatepicker.ComponentColorDefaults;
import org.jdatepicker.ComponentFormatDefaults;
import org.jdatepicker.JDatePicker;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.frontend.common.NumberFilter;
import org.kaleta.lolstats.frontend.component.ComboBoxRenderer;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class AddGameDialog extends JDialog {
    private JTextField textFieldGameNumber;
    private JDatePicker datePicker;
    private JComboBox comboBoxTier;
    private JComboBox comboBoxDivision;
    private JTextField textFieldLp;
    private JSpinner spinnerLength;

    private JRadioButton userTeamVictory;
    private JRadioButton enemyTeamVictory;
    private JRadioButton userTeamFb;
    private JRadioButton enemyTeamFb;

    private boolean result;

    public AddGameDialog(){
        result = false;
        this.setTitle("Add Game");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        this.setModal(true);
        this.pack();
    }

    private void initComponents() {
        Font labelFont = new Font(Font.DIALOG, Font.BOLD, 15);
        Font textFieldFont = new Font(Font.DIALOG, Font.BOLD, 20);

        JLabel labelGameNumber = new JLabel("Game Number:");
        labelGameNumber.setFont(labelFont);
        textFieldGameNumber = new JTextField();
        textFieldGameNumber.setFont(textFieldFont);
        textFieldGameNumber.setHorizontalAlignment(JTextField.CENTER);
        textFieldGameNumber.setText(String.valueOf(DataSourceService.getLastInsertedGameNumber() + 1));
        ((PlainDocument) textFieldGameNumber.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel labelDate = new JLabel("Date:");
        labelDate.setFont(labelFont);
        ComponentFormatDefaults.getInstance().setFormat(ComponentFormatDefaults.Key.SELECTED_DATE_FIELD, new SimpleDateFormat("dd.MM.yyyy"));
        ComponentColorDefaults.getInstance().setColor(ComponentColorDefaults.Key.BG_MONTH_SELECTOR, Color.LIGHT_GRAY);
        datePicker = new JDatePicker(new Date(System.currentTimeMillis()));
        datePicker.getComponent(0).setFont(textFieldFont);

        JLabel labelTier = new JLabel("Tier:");
        labelTier.setFont(labelFont);
        JLabel labelDiv = new JLabel("Division:");
        labelDiv.setFont(labelFont);
        JLabel labelLp = new JLabel("LP:");
        labelLp.setFont(labelFont);
        comboBoxTier = new JComboBox<>(new String[]{"Bronze", "Silver", "Gold", "Platinum", "Diamond", "Master"});
        comboBoxTier.setRenderer(new ComboBoxRenderer(ComboBoxRenderer.TIER));
        comboBoxTier.setFont(new Font(new JList().getFont().getName(), Font.BOLD, 20));
        Season.Game.Rank lastRank = DataSourceService.getLastInsertedRank();
        if (lastRank != null) {
            String tier = lastRank.getTier();
            comboBoxTier.setSelectedItem(tier.substring(0, 1) + tier.substring(1).toLowerCase());
        } else {
            comboBoxTier.setSelectedIndex(-1);
        }
        comboBoxDivision = new JComboBox<>(new String[]{"I", "II", "III", "IV", "V"});
        comboBoxDivision.setRenderer(new ComboBoxRenderer(ComboBoxRenderer.DIVISION));
        comboBoxDivision.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        if (lastRank != null) {
            comboBoxDivision.setSelectedItem(lastRank.getDivision());
        } else {
            comboBoxDivision.setSelectedIndex(-1);
        }
        textFieldLp = new JTextField();
        textFieldLp.setFont(textFieldFont);
        textFieldLp.setHorizontalAlignment(JTextField.CENTER);
        ((PlainDocument) textFieldLp.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel labelLength = new JLabel("Length:");
        labelLength.setFont(labelFont);

        spinnerLength = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spinnerLength, "mm:ss");
        spinnerLength.setEditor(timeEditor);
        spinnerLength.setFont(textFieldFont);
        spinnerLength.setValue(new Date(0));

        JSeparator separator1 = new JSeparator();

        JLabel labelYourTeam = new JLabel("Your Team:");
        labelYourTeam.setFont(labelFont);
        JLabel labelEnemyTeam = new JLabel("Enemy Team:");
        labelEnemyTeam.setFont(labelFont);
        JLabel labelVictory = new JLabel("Victory");
        labelVictory.setFont(labelFont);
        JLabel labelFb = new JLabel("First Blood");
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

        // todo continue team stats
        // TODO: 3/10/16

        JSeparator separator2 = new JSeparator();

        // TODO: 3/10/16

        JSeparator separator3 = new JSeparator();

        JButton buttonOk = new JButton("OK");
        JButton buttonCancel = new JButton("Cancel");

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelGameNumber, 100, 100, 100)
                                                .addGap(5)
                                                .addComponent(textFieldGameNumber, 60, 60, 60))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(labelDate, 100, 100, 100)
                                                .addGap(5)
                                                .addComponent(datePicker, 140, 140, 140))
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
                                .addComponent(labelYourTeam, 100, 100, 100)
                                .addComponent(labelEnemyTeam, 100, 100, 100))
                            .addGroup(layout.createParallelGroup()
                                .addComponent(labelVictory)
                                .addComponent(userTeamVictory)
                                .addComponent(enemyTeamVictory))
                            .addGroup(layout.createParallelGroup()
                                .addComponent(labelFb)
                                .addComponent(userTeamFb)
                                .addComponent(enemyTeamFb)))
                        .addComponent(separator2)
                        .addComponent(separator3)
                        .addGroup(layout.createSequentialGroup()
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
                                        .addComponent(datePicker, 25, 25, 25))
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
                .addComponent(separator1)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(labelYourTeam)
                                .addComponent(labelEnemyTeam))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelVictory)
                                .addComponent(userTeamVictory)
                                .addComponent(enemyTeamVictory))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelFb)
                                .addComponent(userTeamFb)
                                .addComponent(enemyTeamFb)))
                .addGap(5)
                .addComponent(separator2)
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
                if (!userTeamFb.isSelected() && !enemyTeamFb.isSelected()){
                    msg = "Team first blood not set!";
                }
                if (!userTeamVictory.isSelected() && !enemyTeamVictory.isSelected()){
                    msg = "Winning team not set!";
                }
                if (comboBoxTier.getSelectedItem() != null && textFieldLp.getText().equals("")) {
                    msg = "LP not set!";
                }
                if (comboBoxTier.getSelectedItem() != null && comboBoxDivision.getSelectedItem() == null) {
                    msg = "division not selected!";
                }
                if (datePicker.getModel().getValue() == null) {
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

    public void setUpDialog(Season.Game game){
        if (!game.getNumber().equals("")){
            textFieldGameNumber.setText(game.getNumber());
        }
        if (game.getDate().equals("")){
            datePicker.getModel().setDate(0,0,0);
        } else {
            org.kaleta.lolstats.ex.entities.Date date = new org.kaleta.lolstats.ex.entities.Date();
            date.setStringDate(game.getDate());
            datePicker.getModel().setDate(date.getYear(),date.getMonth() - 1,date.getDay());
        }
        if (!game.getLength().equals("")){
            spinnerLength.getModel().setValue(new Date(Integer.parseInt(game.getLength().substring(0,2)) * 60000
                    + Integer.parseInt(game.getLength().substring(2,4)) * 1000));
        }
        if (!game.getRank().getTier().equals("undefined") && !game.getRank().getTier().equals("")){
            comboBoxTier.setSelectedItem(game.getRank().getTier().substring(0, 1) + game.getRank().getTier().substring(1).toLowerCase());
            comboBoxDivision.setSelectedItem(game.getRank().getDivision());
            textFieldLp.setText(game.getRank().getLp());
        }
        if (!game.getUserTeam().getWin().equals("") && !game.getEnemyTeam().getWin().equals("")){
            userTeamVictory.setSelected(Boolean.parseBoolean(game.getUserTeam().getWin()));
            enemyTeamVictory.setSelected(Boolean.parseBoolean(game.getEnemyTeam().getWin()));
        }
        if (!game.getUserTeam().getFirstBlood().equals("") && !game.getEnemyTeam().getFirstBlood().equals("")){
            userTeamFb.setSelected(Boolean.parseBoolean(game.getUserTeam().getFirstBlood()));
            enemyTeamFb.setSelected(Boolean.parseBoolean(game.getEnemyTeam().getFirstBlood()));
        }



        // TODO: 3/4/16  
    }

    public boolean getResult(){
        return result;
    }

    public Season.Game getGame(){
        Season.Game newGame = new Season.Game();

        newGame.setNumber(textFieldGameNumber.getText());
        newGame.setDate(new SimpleDateFormat("ddMMyyyy").format(datePicker.getModel().getValue()));
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
        newGame.getEnemyTeam().setWin(String.valueOf(enemyTeamVictory.isSelected()));
        newGame.getEnemyTeam().setFirstBlood(String.valueOf(enemyTeamFb.isSelected()));


        // TODO: 3/4/16
        return newGame;
    }
}
