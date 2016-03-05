package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.dialog.AddGameDialog;

import javax.swing.*;
import java.awt.Color;

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
        dialog.setVisible(true);
        if (true/*dialog.getResult()*/){
            Season.Game newGame = dialog.getGame();


            //todo  + update data


        }
    }
}
