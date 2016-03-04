package org.kaleta.lolstats.frontend.dialog;

import org.kaleta.lolstats.backend.entity.Season;

import javax.swing.*;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class AddGameDialog extends JDialog {
    private boolean result;
//<game number="1" date="23012015" role="DUO_SUPPORT" champion="morgana">
//    <result win="false" length="3403" first-blood="false">
//          <team-diff kills-diff="-27" farm-diff="-225" gold-diff="-21431" total-dmg-diff="-198355" champ-dmg-diff="-36466" drake-diff="-3" nashor-diff="0" turret-diff="-10" inhibitor-diff="-3"/>
//          <rank tier="undefined" division="" lp=""/>
//    </result>
//    <performance first-blood="false" gold="7842" turrets="0" killing-spree="0">
//          <dmg total-dmg-absolute="31392" total-dmg-team-relative="0.067" champ-dmg-absolute="6730" champ-dmg-team-relative="0.092"/>
//          <score kills-absolute="0" kills-team-relative="0.000" deaths-absolute="12" deaths-team-relative="0.267" assists-absolute="9" assists-team-relative="0.310"/>
//          <farm farm-absolute="40" farm-team-relative="0.066"/>
//          <wards ward-placed="24" ward-destroyed="3"/>
//          <multi-kills double="0" triple="0" quadra="0" penta="0"/>
//    </performance>
//</game>
    public AddGameDialog(){
        result = false;
        initComponents();
    }

    private void initComponents() {

    }

    public void setUpDialog(Season.Game game){

    }

    public boolean getResult(){
        return result;
    }

    public Season.Game getGame(){
        //TODO
        return new Season.Game();
    }
}
