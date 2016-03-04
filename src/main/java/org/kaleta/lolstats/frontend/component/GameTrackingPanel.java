package org.kaleta.lolstats.frontend.component;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class GameTrackingPanel extends JPanel{
    private JButton buttonCancel;
    public GameTrackingPanel(){
        this.setBackground(Color.BLUE);
        this.setVisible(false);

        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GameTrackingPanel.this.setVisible(false);
            }
        });
        this.add(buttonCancel);
    }

    public void addCancelAction(AbstractAction cancelAction){
        buttonCancel.addActionListener(cancelAction);
    }
}
