package org.kaleta.lolstats.ex.graph.role;

import org.kaleta.lolstats.backend.entity.Role;
import org.kaleta.lolstats.ex.entities.Champion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * User: Stanislav Kaleta
 * Date: 25.6.2015
 */
public class ChampIcon extends JPanel {
    private BufferedImage image;

    public ChampIcon(){
        image = getIcon("no_icon.png");
    }

    public void update(Champion champion){
        image = getIcon("ch_" + champion.toString().toLowerCase() + ".png");
    }

    public void update(Role role){
        image = getIcon("r_" + role.toString().toLowerCase() + ".png");
    }

    public void update(){
        image = null;
    }

    private BufferedImage getIcon(String fileName){
        BufferedImage icon;
        try {
            URL iconUrl = this.getClass().getResource("/icons/"+fileName);
            if (iconUrl == null){
                icon = ImageIO.read(this.getClass().getResource("/icons/def/no_icon.png"));
            } else {
                icon = ImageIO.read(iconUrl);
            }
            return icon;
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponents(g);
        if (image == null){
            this.setOpaque(false);
        } else {
            g.drawImage(image, 0, 0, this);
        }
    }

    public BufferedImage getIcon(){
        return image;
    }
}
