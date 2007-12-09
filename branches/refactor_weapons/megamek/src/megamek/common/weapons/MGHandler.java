/**
 * MegaMek - Copyright (C) 2004,2005 Ben Mazur (bmazur@sev.org)
 * 
 *  This program is free software; you can redistribute it and/or modify it 
 *  under the terms of the GNU General Public License as published by the Free 
 *  Software Foundation; either version 2 of the License, or (at your option) 
 *  any later version.
 * 
 *  This program is distributed in the hope that it will be useful, but 
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 *  for more details.
 */
/*
 * Created on Oct 20, 2004
 *
 */
package megamek.common.weapons;

import java.util.Vector;

import megamek.common.BattleArmor;
import megamek.common.Compute;
import megamek.common.IGame;
import megamek.common.Infantry;
import megamek.common.Report;
import megamek.common.TargetRoll;
import megamek.common.Targetable;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.server.Server;

/**
 * @author Andrew Hunter
 * 
 */
public class MGHandler extends AmmoWeaponHandler {
    int nDamPerHit = 0;

    /**
     * @param t
     * @param w
     * @param g
     * @param s
     */
    public MGHandler(ToHitData t, WeaponAttackAction w, IGame g, Server s) {
        super(t, w, g, s);
    }

    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.weapons.WeaponHandler#calcDamagePerHit()
     */
    protected int calcDamagePerHit() {
        if (weapon.isRapidfire() &&
            !(target instanceof Infantry)) {
            // Check for rapid fire Option. Only MGs can be rapidfire.
            nDamPerHit = Compute.d6();
        } else {
            if (target instanceof Infantry && !(target instanceof BattleArmor)) {
                if (ae instanceof BattleArmor) {
                    switch (wtype.getDamage()) {
                        case 1:
                            return (int)Math.ceil(Compute.d6()/2);
                        case 2:
                            return Compute.d6();
                        case 3:
                            return Compute.d6(2);                        
                    }
                }
                nDamPerHit =  Compute.d6(wtype.getDamage());
            } else {
                nDamPerHit = super.calcDamagePerHit();
            }
        }
        return nDamPerHit;
    }
    
    /*
     *  (non-Javadoc)
     * @see megamek.common.weapons.WeaponHandler#addHeat()
     */    
    protected void addHeat() {
        if (!(toHit.getValue() == TargetRoll.IMPOSSIBLE)) {
            if (weapon.isRapidfire()) {
                ae.heatBuildup += nDamPerHit;
            } else {
                ae.heatBuildup += (wtype.getHeat());
            }
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see megamek.common.weapons.WeaponHandler#reportMiss(java.util.Vector)
     */
    protected void reportMiss(Vector<Report> vPhaseReport) {
        //Report the miss
        r = new Report(3220);
        if (weapon.isRapidfire() &&
                !(target instanceof Infantry &&
                !(target instanceof BattleArmor)) ){
              r.messageId = 3225;
              r.add(nDamPerHit*3);
        }
        vPhaseReport.add(r);
    }
    
    /*
     *  (non-Javadoc)
     * @see megamek.common.weapons.WeaponHandler#useAmmo()
     */
    protected void useAmmo() {
        if (weapon.isRapidfire()) {
            checkAmmo();
            int ammoUsage = 3*nDamPerHit;
            for (int i=0; i<ammoUsage; i++) {
                if (ammo.getShotsLeft() <= 0) {
                    ae.loadWeapon(weapon);
                    ammo = weapon.getLinked();
                } 
                ammo.setShotsLeft(ammo.getShotsLeft()-1);
            }
            setDone();
        } else super.useAmmo();
    }

}
