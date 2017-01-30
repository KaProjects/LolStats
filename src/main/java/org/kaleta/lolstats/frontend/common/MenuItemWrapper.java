package org.kaleta.lolstats.frontend.common;

import javax.swing.*;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class MenuItemWrapper extends JMenuItem {

    public MenuItemWrapper(Action action, KeyStroke keyStroke, String tipText) {
        super(action);
        this.setAccelerator(keyStroke);
        this.setToolTipText(tipText);
    }

    public MenuItemWrapper(Action action, KeyStroke keyStroke) {
        super(action);
        this.setAccelerator(keyStroke);
    }

    public MenuItemWrapper(Action action, String tipText) {
        super(action);
        this.setToolTipText(tipText);
    }

    public MenuItemWrapper(Action action) {
        super(action);
    }
}
