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

import megamek.common.TechConstants;

/**
 * @author Sebastian Brocks
 *
 */
public class ISRL15 extends RLWeapon {

    /**
     * 
     */
    public ISRL15() {
        super(); 
        this.techLevel = TechConstants.T_IS_LEVEL_2;
        this.name = "RL 15";
        this.setInternalName("RL15");
        this.addLookupName("ISRocketLauncher15");
        this.addLookupName("IS RLauncher-15");
        this.heat = 5;
        this.rackSize = 15;
        this.shortRange= 5;
        this.mediumRange = 11;
        this.longRange = 18;
        this.extremeRange = 22;
        this.tonnage = 1.0f;
        this.criticals = 2;
        this.bv = 23;
        this.cost = 30000;
    }
}