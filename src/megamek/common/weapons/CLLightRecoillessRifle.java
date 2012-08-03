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
 * Created on Sep 24, 2004
 *
 */
package megamek.common.weapons;

import megamek.common.AmmoType;
import megamek.common.TechConstants;
import megamek.common.WeaponType;

/**
 * @author Andrew Hunter
 */
public class CLLightRecoillessRifle extends Weapon {
    /**
     *
     */
    private static final long serialVersionUID = 1463215714799059271L;

    /**
     *
     */
    public CLLightRecoillessRifle() {
        super();
        techLevel = TechConstants.T_CLAN_TW;
        name = "Light Recoilless Rifle";
        setInternalName(name);
        addLookupName("CLLight Recoilless Rifle");
        addLookupName("CLLightRecoillessRifle");
        damage = 2;
        infDamageClass = WeaponType.WEAPON_BURST_1D6;
        ammoType = AmmoType.T_NA;
        shortRange = 2;
        mediumRange = 4;
        longRange = 6;
        extremeRange = 8;
        bv = 12;
        flags = flags.or(F_NO_FIRES).or(F_DIRECT_FIRE).or(F_BALLISTIC).or(F_BA_WEAPON);
        cost = 1000;
    }

}
