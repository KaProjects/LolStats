package org.kaleta.lolstats.frontend.component;

import org.kaleta.lolstats.frontend.common.IconLoader;

import javax.swing.*;
import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 05.03.2016.
 */
public class ComboBoxRenderer extends JLabel implements ListCellRenderer{
    public static final int TIER = 0;
    public static final int DIVISION = 1;
    private int type;

    public ComboBoxRenderer(int type){
        this.type = type;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon icon = null;
        String text = null;
        if (value != null){
            switch (type) {
                case 0:
                    icon = IconLoader.getTierIcon(((String) value).toLowerCase());
                    text = String.valueOf(value);
                    break;
                case 1:
                    text = String.valueOf(value);
                    break;

            }
            setIcon(icon);
            setText(text);
        } else {
            setIcon(null);
            setText(null);
        }

        return this;
    }
}
