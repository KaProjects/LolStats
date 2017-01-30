package org.kaleta.lolstats.feature.analytics;

import javax.swing.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by Stanislav Kaleta on 12.03.2016.
 */
public class GraphFrame extends JFrame {
    public GraphFrame(JPanel options, JPanel legend, JPanel backround, JPanel foreground){
        JLayeredPane layeredPane = new JLayeredPane();
        this.getContentPane().setLayout(new GridLayout(1,1));
        this.getContentPane().add(layeredPane);

        layeredPane.setPreferredSize(new Dimension(500, 500));

        JPanel panelOptions = new JPanel();
        panelOptions.setOpaque(false);
        JPanel buttonToggleOptions = new ToggleMenuButton(options,true);
        JPanel glassForPanelOptions = new JPanel();
        glassForPanelOptions.setOpaque(false);

        //TODO align height to option needs / try without glass panel
        GroupLayout optionsLayout = new GroupLayout(panelOptions);
        panelOptions.setLayout(optionsLayout);
        optionsLayout.setHorizontalGroup(optionsLayout.createParallelGroup()
                .addComponent(options)
                .addComponent(buttonToggleOptions)
                .addComponent(glassForPanelOptions));
        optionsLayout.setVerticalGroup(optionsLayout.createSequentialGroup()
                .addComponent(options)
                .addComponent(buttonToggleOptions)
                .addComponent(glassForPanelOptions));

        JPanel panelLegend = new JPanel();
        panelLegend.setOpaque(false);
        JPanel buttonToggleLegend = new ToggleMenuButton(legend,false);
        JPanel glassForPanelLegend = new JPanel();
        glassForPanelLegend.setOpaque(false);

        //TODO align width to legend needs / try without glass panel
        GroupLayout legendLayout = new GroupLayout(panelLegend);
        panelLegend.setLayout(legendLayout);
        legendLayout.setHorizontalGroup(legendLayout.createSequentialGroup()
                .addComponent(glassForPanelLegend)
                .addComponent(buttonToggleLegend, 20, 20, 20)
                .addComponent(legend));
        legendLayout.setVerticalGroup(legendLayout.createParallelGroup()
                .addComponent(glassForPanelLegend)
                .addComponent(buttonToggleLegend)
                .addComponent(legend));

        layeredPane.add(backround, new Integer(1));
        layeredPane.add(foreground, new Integer(2));
        layeredPane.add(panelLegend, new Integer(3));
        layeredPane.add(panelOptions, new Integer(4));

        this.getContentPane().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Container container = (Container) e.getComponent();
                backround.setBounds(0, 0, container.getSize().width, container.getSize().height);
                foreground.setBounds(0, 0, container.getSize().width, container.getSize().height);
                panelLegend.setBounds(0, 0, container.getSize().width, container.getSize().height);
                panelOptions.setBounds(0, 0, container.getSize().width, container.getSize().height);
                container.revalidate();
            }
        });
    }
}
