package org.kaleta.lolstats.frontend.component;

import org.kaleta.lolstats.frontend.common.NumberFilter;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.Font;

/**
 * Created by Stanislav Kaleta on 12.03.2016.
 */
public class InputNumberField extends JTextField{

    public InputNumberField(){
        this.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        this.setHorizontalAlignment(JTextField.CENTER);
        ((PlainDocument) this.getDocument()).setDocumentFilter(new NumberFilter());
    }

    @Override
    public void repaint() {
        try{
            if (this.getText().equals("")){
                this.setBackground(Color.getHSBColor(0/360f,0.75f,1));
            } else {
                this.setBackground(Color.WHITE);
            }
        } catch (Exception e){}
        super.repaint();
    }
}
