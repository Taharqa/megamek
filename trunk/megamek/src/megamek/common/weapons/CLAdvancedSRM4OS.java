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
public class CLAdvancedSRM4OS extends AdvancedSRMWeapon {

    /**
     *
     */
    private static final long serialVersionUID = 1382352551382640865L;

    /**
     *
     */
    public CLAdvancedSRM4OS() {
        super();
        techLevel.put(3071,TechConstants.T_CLAN_TW);
        name = "Advanced SRM 4 (OS)";
        setInternalName("CLAdvancedSRM4OS");
        rackSize = 4;
        shortRange = 4;
        mediumRange = 8;
        longRange = 12;
        extremeRange = 16;
        bv = 12;
        flags = flags.or(F_NO_FIRES).or(F_ONESHOT);
        cost = 30000;
        introDate = 3058;
        techLevel.put(3058,techLevel.get(3071));
        availRating = new int[]{RATING_X,RATING_X,RATING_F};
        techRating = RATING_F;
    }
}
