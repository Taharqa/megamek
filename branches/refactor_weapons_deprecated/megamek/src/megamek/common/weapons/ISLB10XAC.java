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
 * Created on Oct 15, 2004
 *
 */
package megamek.common.weapons;

import megamek.common.TechConstants;

/**
 * @author Andrew Hunter
 * 
 */
public class ISLB10XAC extends LBXACWeapon {
    /**
     * 
     */
    public ISLB10XAC() {
        super();
        this.techLevel = TechConstants.T_IS_LEVEL_2;
        this.name = "LB 10-X AC";
        this.setInternalName("ISLBXAC10");
        this.addLookupName("IS LB 10-X AC");
        this.heat = 2;
        this.damage = 10;
        this.rackSize = 10;
        this.shortRange = 6;
        this.mediumRange = 12;
        this.longRange = 18;
        this.extremeRange = 24;
        this.tonnage = 11.0f;
        this.criticals = 6;
        this.bv = 148;
        this.cost = 400000;
    }
}