package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.manager.JaxbSeasonManager;
import org.kaleta.lolstats.backend.manager.ManagerException;
import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.dialog.AddGameDialog;

/**
 * Created by Stanislav Kaleta on 05.03.2016.
 */
public class OpenAddGameDialog extends MenuAction{
    public OpenAddGameDialog(Configuration config) {
        super(config, "Game");
    }

    @Override
    protected void actionPerformed() {
        AddGameDialog dialog = new AddGameDialog();

        /*todo temp*/
        try {
            Season.Game game = new JaxbSeasonManager().retrieveSeason(5L).getGame().get(54);
            dialog.setUpDialog(game);
        } catch (ManagerException e) {
            e.printStackTrace();
        }

        dialog.setVisible(true);
        if (dialog.getResult()){
            Season.Game newGame = dialog.getGame();


            //todo  + update data


        }
    }
}
