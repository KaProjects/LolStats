package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.Initializer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stanislav Kaleta on 26.09.2016.
 */
public class UpdateChampionList extends MenuAction {

    public UpdateChampionList(Configuration config) {
        super(config, "Update List of Champions");
    }

    @Override
    protected void actionPerformed() {
        JDialog dialog = new JDialog();
        dialog.getContentPane().add(new JLabel("Updating..."));
        dialog.pack();
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setModal(false);
        dialog.setVisible(true);
        DataSourceService.updateChampionList();
        Initializer.LOG.info("List of champions updated.");
        dialog.dispose();
    }
}
