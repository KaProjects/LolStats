package org.kaleta.lolstats.frontend;

import org.kaleta.lolstats.frontend.action.menu.*;
import org.kaleta.lolstats.frontend.common.MenuItemWrapper;
import org.kaleta.lolstats.frontend.component.GameTrackingPanel;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Stanislav Kaleta on 19.02.2016.
 */
public class AppFrame extends JFrame implements Configuration {

    public AppFrame(){

        GameTrackingPanel gameTrackingPanel = new GameTrackingPanel();
        initMenuBar(gameTrackingPanel);
        initComponents(gameTrackingPanel);




        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        int centerPosX = (screenSize.width - frameSize.width) / 2;
        int centerPosY = (screenSize.height - frameSize.height) / 2;
        this.setLocation(centerPosX, centerPosY);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponents(JPanel gameTrackingPanel){





        this.getContentPane().add(gameTrackingPanel);
    }

    private void initMenuBar(GameTrackingPanel gameTrackingPanel) {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        JMenu newMenu = new JMenu("Add");
        fileMenu.add(newMenu);
        newMenu.add(new MenuItemWrapper(new OpenAddGameDialog(this), "NOT_IMPLEMENTED"));//todo tooltip
        fileMenu.add(new JSeparator());
        fileMenu.add(new MenuItemWrapper(new OpenSettingsDialog(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK)));
        fileMenu.add(new JSeparator());
        fileMenu.add(new MenuItemWrapper(new PerformExit(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)));

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(gameMenu);
        gameMenu.add(new MenuItemWrapper(new StartTrackingGames(this, gameTrackingPanel),"NOT_IMPLEMENTED"));//TODO tooltip
        gameMenu.add(new MenuItemWrapper(new AddRecentGame(this),"NOT_IMPLEMENTED"));//TODO tooltip
    }
}
;