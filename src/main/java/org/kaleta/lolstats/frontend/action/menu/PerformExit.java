package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.frontend.Configuration;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class PerformExit extends MenuAction{

    public PerformExit(Configuration configuration) {
        super(configuration, "Exit");
    }

    @Override
    protected void actionPerformed() {
        System.exit(0);
    }
}
