package org.kaleta.lolstats.frontend.common;

import org.kaleta.lolstats.frontend.Initializer;

import javax.swing.*;
import java.net.URL;

/**
 * Created by Stanislav Kaleta on 04.03.2016.
 */
public class IconLoader {
    public static final int ERROR_ICON = 1;


    /**
     * Returns icon according to specified constant.
     *
     * @param iconConstant specific constant
     */
    public static ImageIcon getIcon(int iconConstant) {
        switch (iconConstant) {
            case ERROR_ICON:
                return loadIcon("def/error_icon.png", null);

            default:
                throw new IllegalArgumentException("Illegal icon constant!");
        }
    }


    /**
     * todo doc
     */
    public static ImageIcon getChampIcon(String name) { // TODO: 3/12/16 update all icons
        String path = "ch/" + name.replace(" ","").replace("'","").replace(".","").toLowerCase() + ".png";
        return loadIcon(path,name);
    }

    /**
     * todo doc
     */
    public static ImageIcon getTierIcon(String name) {
        String path = "tier/" + name.replace(" ","").replace("'","").replace(".","").toLowerCase() + ".png";
        return loadIcon(path,name);
    }

    private static ImageIcon loadIcon(String path, String description) {
        URL url = IconLoader.class.getResource("/icons/" + path);
        if (url == null){
            Initializer.LOG.warning("System cant find icon \n" + path + "\". URL is null!");
            return new ImageIcon(IconLoader.class.getResource("/icons/def/no_icon.png"));
        } else {
            if (description == null){
                return new ImageIcon(url);
            } else {
                return new ImageIcon(url, description);
            }
        }
    }
}