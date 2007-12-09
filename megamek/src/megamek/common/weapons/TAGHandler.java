/**
 * MegaMek - Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
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
package megamek.common.weapons;

import java.util.Vector;

import megamek.common.Building;
import megamek.common.Entity;
import megamek.common.EquipmentMode;
import megamek.common.IGame;
import megamek.common.Report;
import megamek.common.TagInfo;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.common.weapons.WeaponHandler;
import megamek.server.Server;

public class TAGHandler extends WeaponHandler {
    /**
     * @param toHit
     * @param waa
     * @param g
     */
    public TAGHandler(ToHitData toHit, WeaponAttackAction waa, IGame g,
            Server s) {
        super(toHit, waa, g, s);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.weapons.WeaponHandler#calcDamagePerHit()
     */
    protected int calcDamagePerHit() {
        return 0;
    }
    /*
     *  (non-Javadoc)
     * @see megamek.common.weapons.WeaponHandler#handleEntityDamage(megamek.common.Entity, java.util.Vector, megamek.common.Building, int, int, int, int)
     */
    protected void handleEntityDamage(Entity entityTarget,
            Vector<Report> vPhaseReport, Building bldg, int hits, int nCluster,
            int nDamPerHit, int bldgAbsorbs) {
        if (entityTarget == null) {
            r = new Report(3187);
            r.subject = ae.getId();
            vPhaseReport.addElement(r);
        } else {
            int priority = 1;
            EquipmentMode mode = (weapon.curMode());
            if (mode != null) {
                if (mode.getName() == "1-shot") {
                    priority = 1;
                } else if (mode.getName() == "2-shot") {
                    priority = 2;
                } else if (mode.getName() == "3-shot") {
                    priority = 3;
                } else if (mode.getName() == "4-shot") {
                    priority = 4;
                }
            }
            if (priority < 1) priority = 1;
            //add even misses, as they waste homing missiles.
            //it is possible for 2 or more tags to hit the same entity, 
            //but this only matters in the offboard phase
            TagInfo info = new TagInfo(ae.getId(), entityTarget.getId(), priority, false);
            game.addTagInfo(info);
            entityTarget.setTaggedBy(ae.getId());
            r = new Report(3188);
            r.subject = ae.getId();
            vPhaseReport.addElement(r);
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see megamek.common.weapons.WeaponHandler#handleSpecialMiss(megamek.common.Entity, boolean, megamek.common.Building, java.util.Vector)
     */
    protected boolean handleSpecialMiss(Entity entityTarget,
            boolean targetInBuilding, Building bldg, Vector<Report> vPhaseReport) {
        int priority = 1;
        EquipmentMode mode = (weapon.curMode());
        if (mode != null) {
            if (mode.getName() == "1-shot") {
                priority = 1;
            } else if (mode.getName() == "2-shot") {
                priority = 2;
            } else if (mode.getName() == "3-shot") {
                priority = 3;
            } else if (mode.getName() == "4-shot") {
                priority = 4;
            }
        }
        TagInfo info = new TagInfo(ae.getId(), entityTarget.getId(), priority, true);
        game.addTagInfo(info);
        return false;
    }
    
    /*
     *  (non-Javadoc)
     * @see megamek.common.weapons.AttackHandler#cares(int)
     */
    public boolean cares(int phase) {
        if (phase == IGame.PHASE_OFFBOARD) {
            return true;
        }
        return false;
    }
}
