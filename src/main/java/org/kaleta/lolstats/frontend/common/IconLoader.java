package org.kaleta.lolstats.frontend.common;

import org.kaleta.lolstats.backend.entity.Role;
import org.kaleta.lolstats.frontend.Initializer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Stanislav Kaleta on 04.03.2016.
 */
public class IconLoader {
    public static final String NO_ICON = "/icons/def/no_icon.png";
    public static final String LOGO = "/icons/def/logo.png";

    public static ImageIcon getChampIcon(String name) {
        String path = "/icons/champion/" + name.replace(" ","").replace("'","").replace(".","").toLowerCase() + ".png";
        return loadIcon(path,name, new Dimension(30,30));
    }

    public static ImageIcon getRoleIcon(Role role){
        String path = "/icons/role/" + role.toString().toLowerCase() + ".png";
        return loadIcon(path,role.toString(), new Dimension(30,30));
    }

    public static ImageIcon getTierIcon(String name, Dimension size) {
        String path = "/icons/tier/" + name + ".png";
        return loadIcon(path,name, size);
    }

    public static ImageIcon getTierIcon(String name) {
        String path = "/icons/tier/" + name + ".png";
        return loadIcon(path,name, null);
    }

    public static ImageIcon getIcon(String path){
        return loadIcon(path, null, null);
    }

    private static ImageIcon loadIcon(String path, String description, Dimension size) {
        try {
            Image img = ImageIO.read(IconLoader.class.getResource(path));
            if (size != null){
                img = img.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
            }
            return new ImageIcon(img, description);
        } catch (IOException | IllegalArgumentException e) {
            Initializer.LOG.warning("Cant fined icon with path " + path);
            return loadIcon(NO_ICON, "icon not found", size);
        }

    }
}