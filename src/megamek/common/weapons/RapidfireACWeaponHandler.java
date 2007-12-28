/**
 * MegaMek - Copyright (C) 2004 Ben Mazur (bmazur@sev.org)
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
 * Created on Sep 29, 2004
 *
 */
package megamek.common.weapons;

import java.util.Vector;

import megamek.common.BattleArmor;
import megamek.common.Compute;
import megamek.common.CriticalSlot;
import megamek.common.HitData;
import megamek.common.IGame;
import megamek.common.Infantry;
import megamek.common.Mounted;
import megamek.common.Report;
import megamek.common.ToHitData;
import megamek.common.actions.WeaponAttackAction;
import megamek.server.Server;
import megamek.server.Server.DamageType;

/**
 * @author Andrew Hunter
 * 
 */
public class RapidfireACWeaponHandler extends UltraWeaponHandler {
    int howManyShots;

    /**
     * @param t
     * @param w
     * @param g
     */
    public RapidfireACWeaponHandler(ToHitData t, WeaponAttackAction w, IGame g,
            Server s) {
        super(t, w, g, s);
    }

    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.weapons.WeaponHandler#addHeatUseAmmo()
     */
    protected void useAmmo() {
        setDone();
        checkAmmo();
        int total = ae.getTotalAmmoOfType(ammo.getType());
        if (total > 1) {
            howManyShots = 2;
        }
        if (total == 1) {
            howManyShots = 1;
        }
        if (total == 0) {
            // can't happen?

        }
        if (ammo.getShotsLeft() == 0) {
            ae.loadWeapon(weapon);
            ammo = weapon.getLinked();
            // there will be some ammo somewhere, otherwise shot will not have
            // been fired.
        }
        if (ammo.getShotsLeft() == 1) {
            ammo.setShotsLeft(0);
            ae.loadWeapon(weapon);
            ammo = weapon.getLinked();
            // that fired one, do we need to fire another?
            ammo.setShotsLeft(ammo.getShotsLeft()
                    - ((howManyShots == 2) ? 1 : 0));
        } else {
            ammo.setShotsLeft(ammo.getShotsLeft() - howManyShots);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.weapons.WeaponHandler#calcHits(java.util.Vector)
     */
    protected int calcHits(Vector<Report> vPhaseReport) {
        // conventional infantry gets hit in one lump
        // BAs do one lump of damage per BA suit
        if (target instanceof Infantry && !(target instanceof BattleArmor)) {
            if (ae instanceof BattleArmor) {
                bSalvo = true;
                return ((BattleArmor)ae).getShootingStrength();
            }
            return 1;
        }
        int shotsHit;
        switch (howManyShots) {
            case 1:
                shotsHit = 1;
                break;
            default:
                shotsHit = allShotsHit() ? howManyShots : Compute
                        .missilesHit(howManyShots);
                // report number of shots that hit only when weapon doesn't jam
                if (!weapon.isJammed()) {
                    r = new Report(3325);
                    r.subject = subjectId;
                    r.add(shotsHit);
                    r.add(" shot(s) ");
                    r.add(toHit.getTableDesc());
                    r.newlines = 0;
                    vPhaseReport.addElement(r);
                    r = new Report(3345);
                    r.newlines = 0;
                    vPhaseReport.addElement(r);
                }
                break;
        }
        bSalvo = true;
        return shotsHit;
    }

    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.weapons.WeaponHandler#addHeat()
     */
    protected void addHeat() {// silly hack
        for (int x = 0; x < howManyShots; x++) {
            super.addHeat();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see megamek.common.weapons.WeaponHandler#doChecks()
     */
    protected boolean doChecks(Vector<Report> vPhaseReport) {
        if (roll <= 4 && howManyShots == 2) {
            if (roll > 2) {
                r = new Report(3161);
                r.subject = subjectId;
                r.newlines = 0;
                vPhaseReport.addElement(r);
                weapon.setJammed(true);
                weapon.setHit(true);
            } else {
                r = new Report(3162);
                r.subject = subjectId;
                weapon.setJammed(true);
                weapon.setHit(true);
                int wlocation = weapon.getLocation();
                weapon.setDestroyed(true);
                for (int i = 0; i < ae.getNumberOfCriticals(wlocation); i++) {
                    CriticalSlot slot1 = ae.getCritical(wlocation, i);
                    if (slot1 == null
                            || slot1.getType() != CriticalSlot.TYPE_SYSTEM) {
                        continue;
                    }
                    Mounted mounted = ae.getEquipment(slot1.getIndex());
                    if (mounted.equals(weapon)) {
                        ae.hitAllCriticals(wlocation, i);
                        break;
                    }
                }
                r.choose(false);
                vPhaseReport.addElement(r);
                vPhaseReport.addAll(server.damageEntity(ae, new HitData(wlocation),
                        wtype.getDamage(), false, DamageType.NONE, true));
            }
            return true;
        }
        return false;
    }
}
