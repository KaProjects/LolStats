package org.kaleta.lolstats.backend.entity;

/**
 * Created by Stanislav Kaleta on 03.03.2016.
 */
public enum Role {
    adc, support, mid, top, jungle, UNDEFINED;

    public static Role getRoleByApi(String line, String role){
        if (line.equals("MIDDLE") && role.equals("SOLO")){
            return mid;
        }
        if (line.equals("BOTTOM") && role.equals("DUO_CARRY")){
            return adc;
        }
        if (line.equals("BOTTOM") && role.equals("DUO_SUPPORT")){
            return support;
        }
        if (line.equals("JUNGLE") && role.equals("NONE")){
            return jungle;
        }
        if (line.equals("TOP") && role.equals("SOLO")){
            return top;
        }
        return UNDEFINED;
    }

    public static boolean isValue(String value){
        try {
            Role.valueOf(value);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

    public static String[] stringValues(){
        return new String[]{adc.toString(), support.toString(), mid.toString(), top.toString(), jungle.toString(), UNDEFINED.toString()};
    }
}
