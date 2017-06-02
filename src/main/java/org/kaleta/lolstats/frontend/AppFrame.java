package org.kaleta.lolstats.frontend;

import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.frontend.action.menu.*;
import org.kaleta.lolstats.frontend.common.IconLoader;
import org.kaleta.lolstats.frontend.common.MenuItemWrapper;
import org.kaleta.lolstats.frontend.component.GameListPanel;
import org.kaleta.lolstats.frontend.component.GameTrackingPanel;
import org.kaleta.lolstats.frontend.component.stats.StatsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Stanislav Kaleta on 19.02.2016.
 */
public class AppFrame extends JFrame implements Configuration {
    private GameListPanel gameListPanel;
    private StatsPanel statsPanel;

    public AppFrame(){

        GameTrackingPanel gameTrackingPanel = new GameTrackingPanel(this);
        initMenuBar(gameTrackingPanel);
        initComponents(gameTrackingPanel);



        this.pack();
        this.setSize(new Dimension(this.getSize().width, 500));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        int centerPosX = (screenSize.width - frameSize.width) / 2;
        int centerPosY = (screenSize.height - frameSize.height) / 2;
        this.setLocation(centerPosX, centerPosY);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle(Initializer.NAME +" - "+ Initializer.VERSION+" - "+DataSourceService.getLolApiConfig().getNick());
        this.setIconImage(IconLoader.getIcon(IconLoader.LOGO).getImage());
    }

    private void initComponents(JPanel gameTrackingPanel){
        JTabbedPane pane = new JTabbedPane();

        gameListPanel = new GameListPanel();
        JScrollPane paneGames = new JScrollPane(gameListPanel);
        pane.addTab("Games", paneGames);

        pane.addTab("Live", gameTrackingPanel);

        statsPanel = new StatsPanel();
        JScrollPane paneStats = new JScrollPane(statsPanel);
        pane.addTab("Stats", paneStats);

        this.getContentPane().add(pane);
    }

    private void initMenuBar(GameTrackingPanel gameTrackingPanel) {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);
        JMenu newMenu = new JMenu("Add");
        fileMenu.add(newMenu);
        newMenu.add(new MenuItemWrapper(new OpenAddGameDialog(this)));
        fileMenu.add(new JSeparator());
        fileMenu.add(new MenuItemWrapper(new OpenSettingsDialog(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK)));
        fileMenu.add(new MenuItemWrapper(new UpdateChampionList(this)));
        fileMenu.add(new JSeparator());
        fileMenu.add(new MenuItemWrapper(new PerformExit(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)));

        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(gameMenu);
        gameMenu.add(new MenuItemWrapper(new StartTrackingGames(this, gameTrackingPanel), "Starts tracking new games from LoL API"));
        gameMenu.add(new MenuItemWrapper(new OpenRecentGameDialog(this),"Loads last 10 games from LoL API"));
    }

    @Override
    public void updateComponents() {
        gameListPanel.update();
        statsPanel.update();
    }
}
