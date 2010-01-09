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
 */
public class ISSRM6IOS extends SRMWeapon {

    /**
     *
     */
    private static final long serialVersionUID = -1926715836221080572L;

    /**
     *
     */
    public ISSRM6IOS() {
        super();
        techLevel = TechConstants.T_INTRO_BOXSET;
        name = "SRM 6 (IOS)";
        setInternalName("ISSRM6IOS");
        addLookupName("ISSRM6 (IOS)"); // mtf
        addLookupName("IS SRM 6 (IOS)"); // tdb
        addLookupName("IOS SRM-6"); // mep
        heat = 4;
        rackSize = 6;
        shortRange = 3;
        mediumRange = 6;
        longRange = 9;
        extremeRange = 12;
        tonnage = 3.0f;
        criticals = 2;
        bv = 12;
        flags = flags.or(F_NO_FIRES).or(F_ONESHOT);
        cost = 80000;
        shortAV = 8;
        maxRange = RANGE_SHORT;
    }
}
