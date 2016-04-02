package org.kaleta.lolstats.feature.analytics;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 02.04.2016.
 */
public class ToggleMenuButton extends JPanel {
    private boolean horizontal;

    public ToggleMenuButton(JPanel target, boolean horizontal){
        this.horizontal = horizontal;

        this.setBackground(Color.GRAY);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                    target.setVisible(!target.isVisible());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ToggleMenuButton.this.setBackground(Color.GRAY.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ToggleMenuButton.this.setBackground(Color.GRAY);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        if (horizontal) {
            int center = this.getWidth() / 2;
            g.drawLine(center - 10, 7, center + 10, 7);
            g.drawLine(center - 10, 13, center + 10, 13);
        } else {
            int center = this.getHeight() / 2;
            g.drawLine(7, center - 10, 7, center + 10);
            g.drawLine(13, center - 10, 13, center + 10);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return (horizontal)
                ? new Dimension(super.getPreferredSize().width, 20)
                : new Dimension(20, super.getPreferredSize().height);
    }

    @Override
    public Dimension getMaximumSize() {
        return (horizontal)
                ? new Dimension(super.getMaximumSize().width, 20)
                : new Dimension(20, super.getMaximumSize().height);
    }

    @Override
    public Dimension getMinimumSize() {
        return (horizontal)
                ? new Dimension(super.getMinimumSize().width, 20)
                : new Dimension(20, super.getMinimumSize().height);
    }
}
