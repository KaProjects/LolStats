package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.frontend.Configuration;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class OpenSettingsDialog extends MenuAction{

    public OpenSettingsDialog(Configuration config) {
        super(config, "Settings..");
    }

    @Override
    protected void actionPerformed() {
        //TODO
    }
}
