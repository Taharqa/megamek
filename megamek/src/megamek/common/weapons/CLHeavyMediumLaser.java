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
 * Created on Sep 12, 2004
 *
 */
package megamek.common.weapons;

/**
 * @author Andrew Hunter
 *
 */
public class CLHeavyMediumLaser extends LaserWeapon {
	/**
	 * 
	 */
	public CLHeavyMediumLaser() {
		super();
		this.name = "Heavy Medium Laser";
        this.setInternalName("CLHeavyMediumLaser");
        this.addLookupName("Clan Medium Heavy Laser");
        this.heat = 7;
        this.damage = 10;
        this.toHitModifier = 1;
        this.shortRange = 3;
        this.mediumRange = 6;
        this.longRange = 9;
        this.waterShortRange = 2;
        this.waterMediumRange = 4;
        this.waterLongRange = 6;
        this.tonnage = 1.0f;
        this.criticals = 2;
        this.bv = 76;
	}
}
