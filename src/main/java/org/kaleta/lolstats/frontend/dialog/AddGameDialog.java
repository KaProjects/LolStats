package org.kaleta.lolstats.frontend.dialog;

import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.ex.graph.role.ChampIcon;
import org.kaleta.lolstats.frontend.common.IconLoader;
import org.kaleta.lolstats.frontend.common.NumberFilter;
import org.kaleta.lolstats.frontend.component.ComboBoxRenderer;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.security.Provider;
import java.util.Vector;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class AddGameDialog extends JDialog {
    private JTextField textFieldGameNumber;
    private JComboBox comboBoxTier;
    private JComboBox comboBoxDivision;
    private JTextField textFieldLp;

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
        //Font labelFont = ;
        Font textFieldFont = new Font(new JList().getFont().getName(), Font.BOLD, 25);

        JLabel labelGameNumber = new JLabel("Game Number:");
        //labelGameNumber.setFont();
        textFieldGameNumber = new JTextField();
        textFieldGameNumber.setFont(textFieldFont);
        textFieldGameNumber.setHorizontalAlignment(JTextField.CENTER);
        textFieldGameNumber.setText(String.valueOf(DataSourceService.getLastInsertedGameNumber() + 1));
        ((PlainDocument) textFieldGameNumber.getDocument()).setDocumentFilter(new NumberFilter());

        JLabel labelRank = new JLabel("Rank:");
        //labelRank.setFont();
        comboBoxTier = new JComboBox(new String[]{"Bronze","Silver","Gold","Platinum","Diamond","Master"});
        comboBoxTier.setRenderer(new ComboBoxRenderer(ComboBoxRenderer.TIER));
        comboBoxTier.setFont(new Font(new JList().getFont().getName(), Font.BOLD, 20));
        String tier = DataSourceService.getLastInsertedRank().getTier();
        comboBoxTier.setSelectedItem(tier.substring(0, 1) + tier.substring(1).toLowerCase());
        comboBoxDivision = new JComboBox(new String[]{"I","II","III","IV","V"});
        comboBoxDivision.setRenderer(new ComboBoxRenderer(ComboBoxRenderer.DIVISION));
        comboBoxDivision.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        comboBoxDivision.setSelectedItem(DataSourceService.getLastInsertedRank().getDivision());
        textFieldLp = new JTextField();
        textFieldLp.setFont(textFieldFont);
        textFieldLp.setHorizontalAlignment(JTextField.CENTER);
        ((PlainDocument) textFieldLp.getDocument()).setDocumentFilter(new NumberFilter());







        this.getContentPane().setLayout(new GridLayout(1,3));
        this.getContentPane().add(labelGameNumber);
        this.getContentPane().add(textFieldGameNumber);
        this.getContentPane().add(labelRank);
        this.getContentPane().add(comboBoxTier);
        this.getContentPane().add(comboBoxDivision);
        this.getContentPane().add(textFieldLp);
    }

    public void setUpDialog(Season.Game game){
        // TODO: 3/4/16  
    }

    public boolean getResult(){
        return result;
    }

    public Season.Game getGame(){
        Season.Game newGame = new Season.Game();

        newGame.setNumber(textFieldGameNumber.getText());
        // TODO: 3/4/16
        return newGame;
    }
}
