package org.kaleta.lolstats.graph.role;

import java.util.Comparator;

/**
 * User: Stanislav Kaleta
 * Date: 25.6.2015
 */
public class RoleRowComparator implements Comparator<RoleRowPanel> {
    @Override
    public int compare(RoleRowPanel o1, RoleRowPanel o2) {
        return o2.getCompareValue() - o1.getCompareValue();
    }
}
