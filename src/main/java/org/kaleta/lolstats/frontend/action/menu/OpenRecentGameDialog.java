package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.dialog.AddRecentGameDialog;

import java.awt.*;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class OpenRecentGameDialog extends MenuAction{

    public OpenRecentGameDialog(Configuration config) {
        super(config, "Add Recent");
    }

    @Override
    protected void actionPerformed() {
        new AddRecentGameDialog((Component) getConfiguration()).setVisible(true);
    }
}
