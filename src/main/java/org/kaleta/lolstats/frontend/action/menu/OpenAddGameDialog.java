package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.dialog.AddGameDialog;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 05.03.2016.
 */
public class OpenAddGameDialog extends MenuAction{
    public OpenAddGameDialog(Configuration config) {
        super(config, "Game");
    }

    @Override
    protected void actionPerformed() {
        AddGameDialog dialog = new AddGameDialog((Component) getConfiguration());
        dialog.setVisible(true);
        if (dialog.getResult()){
            Season.Game newGame = dialog.getGame();
            DataSourceService.addNewGame(newGame);
        }
    }
}
