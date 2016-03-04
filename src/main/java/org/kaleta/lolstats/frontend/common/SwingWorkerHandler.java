package org.kaleta.lolstats.frontend.common;

import org.kaleta.lolstats.backend.service.ServiceFailureException;
import org.kaleta.lolstats.frontend.ErrorDialog;
import org.kaleta.lolstats.frontend.Initializer;

import javax.swing.*;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 *
 * Handles executing in Swing Worker thread. Every GUI action should extend this class.
 */
public abstract class SwingWorkerHandler {

    public void execute() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    runInBackground();
                } catch (Exception e) {
                    ErrorDialog errorDialog = new ErrorDialog(e);
                    if (!(e instanceof ServiceFailureException)) {
                        Initializer.LOG.severe(errorDialog.getExceptionStackTrace());
                    }
                    // No need to log SFEx. cause its (should be) always logged before its thrown.
                    errorDialog.setVisible(true);
                }
                return null;
            }
        }.execute();
    }

    protected abstract void runInBackground();
}