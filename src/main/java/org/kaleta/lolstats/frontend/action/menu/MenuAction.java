package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.common.SwingWorkerHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 *
 * Basic class for every menu action.
 */
abstract public class MenuAction extends AbstractAction {
    private Configuration config;

    public MenuAction(Configuration config, String name, Icon icon) {
        super(name, icon);
        this.config = config;
    }

    public MenuAction(Configuration config, String name) {
        super(name);
        this.config = config;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new SwingWorkerHandler() {
            @Override
            protected void runInBackground() {
                actionPerformed();
            }
        }.execute();
    }

    protected abstract void actionPerformed();

    protected Configuration getConfiguration(){
        return config;
    }
}
