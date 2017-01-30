package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.component.GameTrackingPanel;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class StartTrackingGames extends MenuAction {
    private GameTrackingPanel panel;

    public StartTrackingGames(Configuration config, GameTrackingPanel panel) {
        super(config, "Start Tracking");
        this.panel = panel;
    }

    @Override
    protected void actionPerformed() {
        this.setEnabled(false);
        panel.startTracking();
        this.setEnabled(true);
    }
}
