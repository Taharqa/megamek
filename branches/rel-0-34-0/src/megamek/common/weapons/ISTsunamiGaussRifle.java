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

/**
 * @author Sebastian Brocks
 */
public class ISTsunamiGaussRifle extends Weapon {
    /**
     * 
     */
    private static final long serialVersionUID = -4179313979730970060L;

    /**
     * 
     */
    public ISTsunamiGaussRifle() {
        super();
        this.techLevel = TechConstants.T_IS_TW_NON_BOX;
        this.name = "Tsunami Heavy Gauss Rifle";
        this.setInternalName(this.name);
        this.addLookupName("BA-ISTsunamiHeavyGaussRifle");
        this.heat = 0;
        this.damage = 1;
        this.ammoType = AmmoType.T_NA;
        this.shortRange = 2;
        this.mediumRange = 4;
        this.longRange = 5;
        this.extremeRange = 8;
        this.tonnage = 0.0f;
        this.criticals = 0;
        this.bv = 6;
        this.flags |= F_DIRECT_FIRE | F_NO_FIRES | F_BALLISTIC;
    }
}
