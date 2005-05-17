/*
 * MegaMek - Copyright (C) 2000,2001,2002,2003,2004 Ben Mazur (bmazur@sev.org)
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

package megamek.common;

import java.util.Enumeration;
import java.util.Vector;
import megamek.common.equip.AmmoState;
import megamek.common.equip.EquipmentState;
import megamek.common.equip.UsesAmmoType;

public class AmmoType extends EquipmentType {
    // ammo types
    public static final int     T_BA_SMALL_LASER    = -3; // !usesAmmo(), 3 damage per hit
    public static final int     T_BA_MG             = -2; // !usesAmmo(), 2 damage per hit
    public static final int     T_NA                = -1;
    public static final int     T_AC                = 1;
    public static final int     T_VEHICLE_FLAMER    = 2;
    public static final int     T_MG                = 3;
    public static final int     T_MG_HEAVY          = 4;
    public static final int     T_MG_LIGHT          = 5;
    public static final int     T_GAUSS             = 6;
    public static final int     T_LRM               = 7;
    public static final int     T_LRM_TORPEDO       = 8;
    public static final int     T_SRM               = 9;
    public static final int     T_SRM_TORPEDO       = 10;
    public static final int     T_SRM_STREAK        = 11;
    public static final int     T_MRM               = 12;
    public static final int     T_NARC              = 13;
    public static final int     T_AMS               = 14;
    public static final int     T_ARROW_IV          = 15;
    public static final int     T_LONG_TOM          = 16;
    public static final int     T_SNIPER            = 17;
    public static final int     T_THUMPER           = 18;
    public static final int     T_AC_LBX            = 19;
    public static final int     T_AC_ULTRA          = 20;
    public static final int     T_GAUSS_LIGHT       = 21;
    public static final int     T_GAUSS_HEAVY       = 22;
    public static final int     T_AC_ROTARY         = 23;
    public static final int     T_SRM_ADVANCED      = 24;
    public static final int     T_BA_INFERNO        = 25;
    public static final int     T_BA_MICRO_BOMB     = 26;
    public static final int     T_LRM_TORPEDO_COMBO = 27;
    public static final int     T_MINE              = 28;
    public static final int     T_ATM               = 29; // Clan ATM missile systems
    public static final int     T_ROCKET_LAUNCHER   = 30;
    public static final int     T_INARC             = 31;
    public static final int     NUM_TYPES           = 32;

    // ammo flags
    public static final int     F_MG                = 0x0001;
    public static final int     F_BATTLEARMOR       = 0x1000; // only used by BA squads
    public static final int     F_PROTOMECH         = 0x0040; // only used by Protomechs

    // ammo munitions, used for custom loadouts
    // N.B. we play bit-shifting games to allow "incendiary"
    //      to be combined to othter munition types.
    public static final int     M_STANDARD          = 0;
    public static final int     M_CLUSTER           = 1;
    public static final int     M_ARMOR_PIERCING    = 1 << 1;
    public static final int     M_FLECHETTE         = 1 << 2;
    public static final int     M_INCENDIARY        = 1 << 3;
    public static final int     M_PRECISION         = 1 << 4;
    public static final int     M_EXTENDED_RANGE    = 1 << 5;
    public static final int     M_HIGH_EXPLOSIVE    = 1 << 6;
    public static final int     M_FLARE             = 1 << 7;
    public static final int     M_FRAGMENTATION     = 1 << 8;
    public static final int     M_INFERNO           = 1 << 9;
    public static final int     M_SEMIGUIDED        = 1 << 10;
    public static final int     M_SWARM             = 1 << 11;
    public static final int     M_SWARM_I           = 1 << 12;
    public static final int     M_THUNDER           = 1 << 13;
    public static final int     M_THUNDER_AUGMENTED = 1 << 14;
    public static final int     M_THUNDER_INFERNO   = 1 << 15;
    public static final int     M_THUNDER_VIBRABOMB = 1 << 16;
    public static final int     M_THUNDER_ACTIVE    = 1 << 17;
    // iNarc
    public static final int     M_EXPLOSIVE         = 1 << 18;
    public static final int     M_ECM               = 1 << 19;
    public static final int     M_HAYWIRE           = 1 << 20;
    public static final int     M_NEMESIS           = 1 << 21;
    // Narc
    public static final int     M_NARC_EX           = 1 << 22;
    // Arrow IV
    public static final int     M_HOMING            = 1 << 23;
    public static final int     M_FASCAM            = 1 << 24;
    public static final int     M_INFERNO_IV        = 1 << 25;
    public static final int     M_VIBRABOMB_IV      = 1 << 26;
    public static final int     M_SMOKE             = 1 << 27;
    // Narc capable missiles
    public static final int     M_NARC_CAPABLE      = 1 << 28;
    

    /*public static final String[] MUNITION_NAMES = { "Standard",
        "Cluster", "Armor Piercing", "Flechette", "Incendiary", "Precision",
        "Extended Range", "High Explosive", "Flare", "Fragmentation", "Inferno",
        "Semiguided", "Swarm", "SwarmI", "Thunder", "Thunder/Augmented",
        "Thunder/Inferno", "Thunder/Vibrabomb", "Thunder/Active", "Explosive",
        "ECM", "Haywire", "Nemesis" };
        */
    private static Vector[] m_vaMunitions = new Vector[NUM_TYPES];

    public static Vector getMunitionsFor(int nAmmoType) {
        return m_vaMunitions[nAmmoType];
    }

    protected int damagePerShot;
    protected int rackSize;
    private int ammoType;
    private int munitionType;
    protected int shots;

    public AmmoType() {
        criticals = 1;
        tonnage = 1.0f;
        explosive = true;
    }

    /**
     * When comparing <code>AmmoType</code>s, look at the ammoType and rackSize.
     *
     * @param   other the <code>Object</code> to compare to this one.
     * @return  <code>true</code> if the other is an <code>AmmoType</code>
     *          object of the same <code>ammoType</code> as this object.
     *          N.B. different munition types are still equal.
     */
    public boolean equals( Object other ) {
        if ( !(other instanceof AmmoType) ) {
            return false;
        }
        return (this.getAmmoType() == ( (AmmoType) other ).getAmmoType() && this.getRackSize() == ((AmmoType)other).getRackSize());
    }

    public int getAmmoType() {
        return ammoType;
    }

    public int getMunitionType() {
        return munitionType;
    }

    protected int heat;
    protected RangeType range;
    protected int tech;

    public int getHeat() {
        return heat;
    }
    
    public int getShotDamage(Entity en, Targetable targ) {
        return damagePerShot;
    }

    public RangeType getRange() {
        return range;
    }

    // By default, all ballistic type weapons are 9.  If some are impossible
    // (i.e. Gauss Rifle or SRM-2) they will override
    public int getFireTN() {
        return 9;
    }
    
    public void resolveAttack(IGame game, WeaponResult wr, UsesAmmoType weap,
                              EquipmentState weap_state) {}

    // By default, adds no new modifiers (these are for ammo based modifiers)
    public TargetRoll getModifiersFor(IGame game, Entity en, Targetable targ) {
        return new TargetRoll();
    }

    // Created using the base type, using the default number of shots
    public EquipmentState getNewState(Mounted location) {
        return new AmmoState(location, this, shots);
    }

    // Be default, all ammo explodes, with shots remaining * damagePerShot
    public void doCriticalDamage(EquipmentState state) {
        if (isExplosive()) {
            AmmoState as = (AmmoState)state;
            // Get the amount of damage.
            int damage = this.getDamagePerShot() * as.shotsLeft();
/* TODO : implement me
            super.doCriticalDamage(state); // Set it as destroyed
*/
            // Do weapon explosion damage
        }
    }

    public int getDamagePerShot() {
        return damagePerShot;
    }

    public int getRackSize() {
        return rackSize;
    }

    public int getShots() {
        return shots;
    }

    // Returns the first usable ammo type for the given oneshot launcher
    public static AmmoType getOneshotAmmo(Mounted mounted) {
        WeaponType wt = (WeaponType)mounted.getType();
        Vector Vammo = AmmoType.getMunitionsFor(wt.getAmmoType());
        AmmoType at = null;
        for (int i = 0; i < Vammo.size(); i++) {
            at = (AmmoType)Vammo.elementAt(i);
            if (at.getRackSize() == wt.getRackSize()) {
                return at;
            }
        }
        return null; //couldn't find any ammo for this weapon type
    }

    public static void initializeTypes() {
        // Save copies of the SRM and LRM ammos to use to create munitions.
        Vector srmAmmos = new Vector(9);
        Vector lrmAmmos = new Vector(24);
        Vector acAmmos  = new Vector(4);
        Vector arrowAmmos = new Vector(4);
        Vector artyAmmos = new Vector(6);
        Vector munitions = new Vector();

        Enumeration baseTypes = null;
        Enumeration mutators = null;
        AmmoType base = null;
        MunitionMutator mutator = null;

        // all level 1 ammo
        EquipmentType.addType(createISVehicleFlamerAmmo());
        EquipmentType.addType(createISMGAmmo());
        EquipmentType.addType(createISMGAmmoHalf());
        base = createISAC2Ammo();
        acAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISAC5Ammo();
        acAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISAC10Ammo();
        acAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISAC20Ammo();
        acAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISLRM5Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISLRM10Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISLRM15Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISLRM20Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISSRM2Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISSRM4Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISSRM6Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );

        // Start of Level2 Ammo
        EquipmentType.addType(createISLB2XAmmo());
        EquipmentType.addType(createISLB5XAmmo());
        EquipmentType.addType(createISLB10XAmmo());
        EquipmentType.addType(createISLB20XAmmo());
        EquipmentType.addType(createISLB2XClusterAmmo());
        EquipmentType.addType(createISLB5XClusterAmmo());
        EquipmentType.addType(createISLB10XClusterAmmo());
        EquipmentType.addType(createISLB20XClusterAmmo());
        EquipmentType.addType(createISUltra2Ammo());
        EquipmentType.addType(createISUltra5Ammo());
        EquipmentType.addType(createISUltra10Ammo());
        EquipmentType.addType(createISUltra20Ammo());
        EquipmentType.addType(createISRotary2Ammo());
        EquipmentType.addType(createISRotary5Ammo());
        EquipmentType.addType(createISGaussAmmo());
        EquipmentType.addType(createISLTGaussAmmo());
        EquipmentType.addType(createISHVGaussAmmo());
        EquipmentType.addType(createISStreakSRM2Ammo());
        EquipmentType.addType(createISStreakSRM4Ammo());
        EquipmentType.addType(createISStreakSRM6Ammo());
        EquipmentType.addType(createISMRM10Ammo());
        EquipmentType.addType(createISMRM20Ammo());
        EquipmentType.addType(createISMRM30Ammo());
        EquipmentType.addType(createISMRM40Ammo());
        EquipmentType.addType(createISRL10Ammo());
        EquipmentType.addType(createISRL15Ammo());
        EquipmentType.addType(createISRL20Ammo());
        EquipmentType.addType(createISAMSAmmo());
        EquipmentType.addType(createISNarcAmmo());
        EquipmentType.addType(createISNarcExplosiveAmmo());
        EquipmentType.addType(createISiNarcAmmo());
        EquipmentType.addType(createISiNarcECMAmmo());
        EquipmentType.addType(createISiNarcExplosiveAmmo());
        EquipmentType.addType(createISiNarcHaywireAmmo());
        EquipmentType.addType(createISiNarcNemesisAmmo());
        base = createISLongTomAmmo();
        artyAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISSniperAmmo();
        artyAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISThumperAmmo();
        artyAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createISArrowIVAmmo();
        arrowAmmos.addElement( base );
        EquipmentType.addType( base );

        EquipmentType.addType(createCLLB2XAmmo());
        EquipmentType.addType(createCLLB5XAmmo());
        EquipmentType.addType(createCLLB10XAmmo());
        EquipmentType.addType(createCLLB20XAmmo());
        EquipmentType.addType(createCLLB2XClusterAmmo());
        EquipmentType.addType(createCLLB5XClusterAmmo());
        EquipmentType.addType(createCLLB10XClusterAmmo());
        EquipmentType.addType(createCLLB20XClusterAmmo());
        EquipmentType.addType(createCLUltra2Ammo());
        EquipmentType.addType(createCLUltra5Ammo());
        EquipmentType.addType(createCLUltra10Ammo());
        EquipmentType.addType(createCLUltra20Ammo());
        EquipmentType.addType(createCLGaussAmmo());
        EquipmentType.addType(createCLStreakSRM1Ammo());
        EquipmentType.addType(createCLStreakSRM2Ammo());
        EquipmentType.addType(createCLStreakSRM3Ammo());
        EquipmentType.addType(createCLStreakSRM4Ammo());
        EquipmentType.addType(createCLStreakSRM5Ammo());
        EquipmentType.addType(createCLStreakSRM6Ammo());
        EquipmentType.addType(createCLVehicleFlamerAmmo());
        EquipmentType.addType(createCLMGAmmo());
        EquipmentType.addType(createCLMGAmmoHalf());
        EquipmentType.addType(createCLHeavyMGAmmo());
        EquipmentType.addType(createCLHeavyMGAmmoHalf());
        EquipmentType.addType(createCLLightMGAmmo());
        EquipmentType.addType(createCLLightMGAmmoHalf());
        EquipmentType.addType(createCLAMSAmmo());
        EquipmentType.addType(createCLNarcAmmo());
        EquipmentType.addType(createCLNarcExplosiveAmmo());
        EquipmentType.addType(createCLATM3Ammo());
        EquipmentType.addType(createCLATM3ERAmmo());
        EquipmentType.addType(createCLATM3HEAmmo());
        EquipmentType.addType(createCLATM6Ammo());
        EquipmentType.addType(createCLATM6ERAmmo());
        EquipmentType.addType(createCLATM6HEAmmo());
        EquipmentType.addType(createCLATM9Ammo());
        EquipmentType.addType(createCLATM9ERAmmo());
        EquipmentType.addType(createCLATM9HEAmmo());
        EquipmentType.addType(createCLATM12Ammo());
        EquipmentType.addType(createCLATM12ERAmmo());
        EquipmentType.addType(createCLATM12HEAmmo());
        base = createCLLongTomAmmo();
        artyAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLSniperAmmo();
        artyAmmos.addElement( base );
        base = createCLThumperAmmo();
        EquipmentType.addType( base );
        artyAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLArrowIVAmmo();
        arrowAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLSRM1Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLSRM2Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLSRM3Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLSRM4Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLSRM5Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLSRM6Ammo();
        srmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM1Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM2Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM3Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM4Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM5Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM6Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM7Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM8Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM9Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM10Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM11Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM12Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM13Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM14Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM15Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM16Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM17Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM18Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM19Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );
        base = createCLLRM20Ammo();
        lrmAmmos.addElement( base );
        EquipmentType.addType( base );

        // Start of BattleArmor ammo
        EquipmentType.addType( createBASRM2Ammo() );
        EquipmentType.addType( createBASRM2OSAmmo() );
        EquipmentType.addType( createBAInfernoSRMAmmo() );
        EquipmentType.addType( createBAAdvancedSRM2Ammo() );
        EquipmentType.addType( createBAAdvancedSRM5Ammo() );
        EquipmentType.addType( createBAMicroBombAmmo() );
        EquipmentType.addType( createCLTorpedoLRM5Ammo() );
        EquipmentType.addType( createFenrirSRM4Ammo() );
        EquipmentType.addType( createBACompactNarcAmmo() );
        EquipmentType.addType( createBAMineLauncherAmmo() );
        EquipmentType.addType( createBALRM5Ammo() );
        EquipmentType.addType( createPhalanxSRM4Ammo() );
        EquipmentType.addType( createGrenadierSRM4Ammo() );

        // Protomech-specific ammo
        EquipmentType.addType(createCLPROHeavyMGAmmo());
        EquipmentType.addType(createCLPROMGAmmo());
        EquipmentType.addType(createCLPROLightMGAmmo());

        // Create the munition types for SRM launchers.
        munitions.removeAllElements();
        munitions.addElement( new MunitionMutator( "Inferno",
                                                   1, M_INFERNO ) );
        munitions.addElement( new MunitionMutator( "Fragmentation",
                                                   1, M_FRAGMENTATION ) );
        munitions.addElement( new MunitionMutator( "Narc capable",
                                                   1, M_NARC_CAPABLE ) );

        // Walk through both the base types and the
        // mutators, and create munition types.
        baseTypes = srmAmmos.elements();
        while ( baseTypes.hasMoreElements() ) {
            base = (AmmoType) baseTypes.nextElement();
            mutators = munitions.elements();
            while ( mutators.hasMoreElements() ) {
                mutator =  (MunitionMutator) mutators.nextElement();
                EquipmentType.addType( mutator.createMunitionType( base ) );
            }
        }

        // Create the munition types for LRM launchers.
        munitions.removeAllElements();
        munitions.addElement( new MunitionMutator( "Fragmentation",
                                                   1, M_FRAGMENTATION ) );
        munitions.addElement( new MunitionMutator( "Thunder",
                                                   1, M_THUNDER ) );
        munitions.addElement( new MunitionMutator( "Thunder-Augmented",
                                                   2, M_THUNDER_AUGMENTED ) );
        munitions.addElement( new MunitionMutator( "Thunder-Inferno",
                                                   2, M_THUNDER_INFERNO ) );
        munitions.addElement( new MunitionMutator( "Thunder-Active",
                                                   2, M_THUNDER_ACTIVE ) );
        munitions.addElement( new MunitionMutator( "Thunder-Vibrabomb",
                                                   2, M_THUNDER_VIBRABOMB ) );
        munitions.addElement( new MunitionMutator( "Narc capable",
                                                   1, M_NARC_CAPABLE ) );

        // Walk through both the base types and the
        // mutators, and create munition types.
        baseTypes = lrmAmmos.elements();
        while ( baseTypes.hasMoreElements() ) {
            base = (AmmoType) baseTypes.nextElement();
            mutators = munitions.elements();
            while ( mutators.hasMoreElements() ) {
                mutator =  (MunitionMutator) mutators.nextElement();
                EquipmentType.addType( mutator.createMunitionType( base ) );
            }
        }

        // Create the munition types for AC rounds.
        munitions.removeAllElements();
        munitions.addElement( new MunitionMutator( "Precision",
                                                   2, M_PRECISION ) );
        munitions.addElement( new MunitionMutator( "Armor-Piercing",
                                                   2, M_ARMOR_PIERCING ) );
        munitions.addElement( new MunitionMutator( "Flechette",
                                                   1, M_FLECHETTE ) );

        // Walk through both the base types and the
        // mutators, and create munition types.
        baseTypes = acAmmos.elements();
        while ( baseTypes.hasMoreElements() ) {
            base = (AmmoType) baseTypes.nextElement();
            mutators = munitions.elements();
            while ( mutators.hasMoreElements() ) {
                mutator =  (MunitionMutator) mutators.nextElement();
                EquipmentType.addType( mutator.createMunitionType( base ) );
            }
        }

        // Create the munition types for Arrow IV launchers.
        munitions.removeAllElements();
        munitions.addElement( new MunitionMutator( "Homing",
                                                   1, M_HOMING ) );
        munitions.addElement( new MunitionMutator( "FASCAM",
                                                   1, M_FASCAM ) );
        munitions.addElement( new MunitionMutator( "Inferno-IV",
                                                   1, M_INFERNO_IV ) );
        munitions.addElement( new MunitionMutator( "Vibrabomb-IV",
                                                   1, M_VIBRABOMB_IV ) );

        // Walk through both the base types and the
        // mutators, and create munition types.
        baseTypes = arrowAmmos.elements();
        while ( baseTypes.hasMoreElements() ) {
            base = (AmmoType) baseTypes.nextElement();
            mutators = munitions.elements();
            while ( mutators.hasMoreElements() ) {
                mutator =  (MunitionMutator) mutators.nextElement();
                EquipmentType.addType( mutator.createMunitionType( base ) );
            }
        }

        // Create the munition types for Artillery launchers.
        munitions.removeAllElements();
        munitions.addElement( new MunitionMutator( "Smoke",
                                                   1, M_SMOKE ) );

        // Walk through both the base types and the
        // mutators, and create munition types.
        baseTypes = artyAmmos.elements();
        while ( baseTypes.hasMoreElements() ) {
            base = (AmmoType) baseTypes.nextElement();
            mutators = munitions.elements();
            while ( mutators.hasMoreElements() ) {
                mutator =  (MunitionMutator) mutators.nextElement();
                EquipmentType.addType( mutator.createMunitionType( base ) );
            }
        }

        // cache types that share a launcher for loadout purposes
        for (Enumeration e = EquipmentType.getAllTypes(); e.hasMoreElements(); ) {
            EquipmentType et = (EquipmentType)e.nextElement();
            if (! (et instanceof AmmoType)) {
                continue;
            }
            AmmoType at = (AmmoType)et;
            int nType = at.getAmmoType();
            if (m_vaMunitions[nType] == null) {
                m_vaMunitions[nType] = new Vector();
            }

            m_vaMunitions[nType].addElement(at);
        }
    }

    public static AmmoType createISAC2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "AC/2 Ammo";
        ammo.setInternalName("IS Ammo AC/2");
        ammo.addLookupName("ISAC2 Ammo");
        ammo.addLookupName("IS Autocannon/2 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_AC;
        ammo.shots = 45;
        ammo.bv = 5;

        return ammo;
    }

    public static AmmoType createISAC5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "AC/5 Ammo";
        ammo.setInternalName("IS Ammo AC/5");
        ammo.addLookupName("ISAC5 Ammo");
        ammo.addLookupName("IS Autocannon/5 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_AC;
        ammo.shots = 20;
        ammo.bv = 9;

        return ammo;
    }

    public static AmmoType createISAC10Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "AC/10 Ammo";
        ammo.setInternalName("IS Ammo AC/10");
        ammo.addLookupName("ISAC10 Ammo");
        ammo.addLookupName("IS Autocannon/10 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_AC;
        ammo.shots = 10;
        ammo.bv = 15;

        return ammo;
    }

    public static AmmoType createISAC20Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "AC/20 Ammo";
        ammo.setInternalName("IS Ammo AC/20");
        ammo.addLookupName("ISAC20 Ammo");
        ammo.addLookupName("IS Autocannon/20 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_AC;
        ammo.shots = 5;
        ammo.bv = 20;

        return ammo;
    }

    public static AmmoType createISVehicleFlamerAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Vehicle Flamer Ammo";
        ammo.setInternalName("IS Vehicle Flamer Ammo");
        ammo.addLookupName("IS Ammo Vehicle Flamer");
        ammo.addLookupName("ISVehicleFlamer Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_VEHICLE_FLAMER;
        ammo.shots = 20;
        ammo.bv = 1;

        return ammo;
    }

    public static AmmoType createISMGAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Machine Gun Ammo";
        ammo.setInternalName("IS Ammo MG - Full");
        ammo.addLookupName("ISMG Ammo (200)");
        ammo.addLookupName("IS Machine Gun Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_MG;
        ammo.flags |= F_MG;
        ammo.shots = 200;
        ammo.bv = 1;

        return ammo;
    }

    public static AmmoType createISMGAmmoHalf() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Half Machine Gun Ammo";
        ammo.setInternalName("IS Machine Gun Ammo - Half");
        ammo.addLookupName("IS Ammo MG - Half");
        ammo.addLookupName("ISMG Ammo (100)");
        ammo.addLookupName("IS Machine Gun Ammo (1/2 ton)");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_MG;
        ammo.flags |= F_MG;
        ammo.shots = 100;
        ammo.bv = 0.5f;
        ammo.tonnage = 0.5f;

        return ammo;
    }

    public static AmmoType createISLRM5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 5 Ammo";
        ammo.setInternalName("IS Ammo LRM-5");
        ammo.addLookupName("ISLRM5 Ammo");
        ammo.addLookupName("IS LRM 5 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 24;
        ammo.bv = 6;

        return ammo;
    }

    public static AmmoType createISLRM10Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 10 Ammo";
        ammo.setInternalName("IS Ammo LRM-10");
        ammo.addLookupName("ISLRM10 Ammo");
        ammo.addLookupName("IS LRM 10 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 12;
        ammo.bv = 11;

        return ammo;
    }

    public static AmmoType createISLRM15Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 15 Ammo";
        ammo.setInternalName("IS Ammo LRM-15");
        ammo.addLookupName("ISLRM15 Ammo");
        ammo.addLookupName("IS LRM 15 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 15;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 8;
        ammo.bv = 17;

        return ammo;
    }

    public static AmmoType createISLRM20Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 20 Ammo";
        ammo.setInternalName("IS Ammo LRM-20");
        ammo.addLookupName("ISLRM20 Ammo");
        ammo.addLookupName("IS LRM 20 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 6;
        ammo.bv = 23;

        return ammo;
    }

    public static AmmoType createISSRM2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 2 Ammo";
        ammo.setInternalName("IS Ammo SRM-2");
        ammo.addLookupName("ISSRM2 Ammo");
        ammo.addLookupName("IS SRM 2 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 50;
        ammo.bv = 3;

        return ammo;
    }

    public static AmmoType createISSRM4Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 4 Ammo";
        ammo.setInternalName("IS Ammo SRM-4");
        ammo.addLookupName("ISSRM4 Ammo");
        ammo.addLookupName("IS SRM 4 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 4;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 25;
        ammo.bv = 5;

        return ammo;
    }

    public static AmmoType createISSRM6Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 6 Ammo";
        ammo.setInternalName("IS Ammo SRM-6");
        ammo.addLookupName("ISSRM6 Ammo");
        ammo.addLookupName("IS SRM 6 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 6;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 15;
        ammo.bv = 7;

        return ammo;
    }
    
    public static AmmoType createISLongTomAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Long Tom Ammo";
        ammo.setInternalName("ISLongTomAmmo");
        ammo.addLookupName("ISLongTom Ammo");
        ammo.addLookupName("ISLongTomArtillery Ammo");
        ammo.addLookupName("IS Ammo Long Tom");
        ammo.addLookupName("IS Long Tom Ammo");
        ammo.damagePerShot=1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_LONG_TOM;
        ammo.shots = 5;
        ammo.bv = 11;

        return ammo;
    }
    
    public static AmmoType createISSniperAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Sniper Ammo";
        ammo.setInternalName("ISSniperAmmo");
        ammo.addLookupName("ISSniper Ammo");
        ammo.addLookupName("ISSniperArtillery Ammo");
        ammo.addLookupName("IS Ammo Sniper");
        ammo.addLookupName("IS Sniper Ammo");
        ammo.damagePerShot=1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_SNIPER;
        ammo.shots = 10;
        ammo.bv = 5;

        return ammo;
    }
    
    public static AmmoType createISThumperAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Thumper Ammo";
        ammo.setInternalName("ISThumperAmmo");
        ammo.addLookupName("ISThumper Ammo");
        ammo.addLookupName("ISThumperArtillery Ammo");
        ammo.addLookupName("IS Ammo Thumper");
        ammo.addLookupName("IS Thumper Ammo");
        ammo.damagePerShot=1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_THUMPER;
        ammo.shots = 20;
        ammo.bv = 3;

        return ammo;
    }

    // Start of Level2 Ammo

    public static AmmoType createISLB2XAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 2-X AC Ammo";
        ammo.setInternalName("IS LB 2-X AC Ammo");
        ammo.addLookupName("IS Ammo 2-X");
        ammo.addLookupName("ISLBXAC2 Ammo");
        ammo.addLookupName("IS LB 2-X AC Ammo - Slug");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.shots = 45;
        ammo.bv = 5;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISLB5XAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 5-X AC Ammo";
        ammo.setInternalName("IS LB 5-X AC Ammo");
        ammo.addLookupName("IS Ammo 5-X");
        ammo.addLookupName("ISLBXAC5 Ammo");
        ammo.addLookupName("IS LB 5-X AC Ammo - Slug");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.shots = 20;
        ammo.bv = 10;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISLB10XAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 10-X AC Ammo";
        ammo.setInternalName("IS LB 10-X AC Ammo");
        ammo.addLookupName("IS Ammo 10-X");
        ammo.addLookupName("ISLBXAC10 Ammo");
        ammo.addLookupName("IS LB 10-X AC Ammo - Slug");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.shots = 10;
        ammo.bv = 19;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISLB20XAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 20-X AC Ammo";
        ammo.setInternalName("IS LB 20-X AC Ammo");
        ammo.addLookupName("IS Ammo 20-X");
        ammo.addLookupName("ISLBXAC20 Ammo");
        ammo.addLookupName("IS LB 20-X AC Ammo - Slug");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.shots = 5;
        ammo.bv = 27;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISLB2XClusterAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 2-X Cluster Ammo";
        ammo.setInternalName("IS LB 2-X Cluster Ammo");
        ammo.addLookupName("IS Ammo 2-X (CL)");
        // this isn't a true mtf code
        ammo.addLookupName("ISLBXAC2 CL Ammo");
        ammo.addLookupName("IS LB 2-X AC Ammo - Cluster");
        ammo.damagePerShot = 1;
        ammo.toHitModifier = -1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.munitionType = M_CLUSTER;
        ammo.shots = 45;
        ammo.bv = 5;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISLB5XClusterAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 5-X Cluster Ammo";
        ammo.setInternalName("IS LB 5-X Cluster Ammo");
        ammo.addLookupName("IS Ammo 5-X (CL)");
        // this isn't a true mtf code
        ammo.addLookupName("ISLBXAC5 CL Ammo");
        ammo.addLookupName("IS LB 5-X AC Ammo - Cluster");
        ammo.damagePerShot = 1;
        ammo.toHitModifier = -1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.munitionType = M_CLUSTER;
        ammo.shots = 20;
        ammo.bv = 10;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISLB10XClusterAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 10-X Cluster Ammo";
        ammo.setInternalName("IS LB 10-X Cluster Ammo");
        ammo.addLookupName("IS Ammo 10-X (CL)");
        // this isn't a true mtf code
        ammo.addLookupName("ISLBXAC10 CL Ammo");
        ammo.addLookupName("IS LB 10-X AC Ammo - Cluster");
        ammo.damagePerShot = 1;
        ammo.toHitModifier = -1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.munitionType = M_CLUSTER;
        ammo.shots = 10;
        ammo.bv = 19;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISLB20XClusterAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 20-X Cluster Ammo";
        ammo.setInternalName("IS LB 20-X Cluster Ammo");
        ammo.addLookupName("IS Ammo 20-X (CL)");
        // this isn't a true mtf code
        ammo.addLookupName("ISLBXAC20 CL Ammo");
        ammo.addLookupName("IS LB 20-X AC Ammo - Cluster");
        ammo.damagePerShot = 1;
        ammo.toHitModifier = -1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.munitionType = M_CLUSTER;
        ammo.shots = 5;
        ammo.bv = 27;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISUltra2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Ultra AC/2 Ammo";
        ammo.setInternalName("IS Ultra AC/2 Ammo");
        ammo.addLookupName("IS Ammo Ultra AC/2");
        ammo.addLookupName("ISUltraAC2 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_AC_ULTRA;
        ammo.shots = 45;
        ammo.bv = 7;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISUltra5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Ultra AC/5 Ammo";
        ammo.setInternalName("IS Ultra AC/5 Ammo");
        ammo.addLookupName("IS Ammo Ultra AC/5");
        ammo.addLookupName("ISUltraAC5 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_AC_ULTRA;
        ammo.shots = 20;
        ammo.bv = 14;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISUltra10Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Ultra AC/10 Ammo";
        ammo.setInternalName("IS Ultra AC/10 Ammo");
        ammo.addLookupName("IS Ammo Ultra AC/10");
        ammo.addLookupName("ISUltraAC10 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_AC_ULTRA;
        ammo.shots = 10;
        ammo.bv = 29;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISUltra20Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Ultra AC/20 Ammo";
        ammo.setInternalName("IS Ultra AC/20 Ammo");
        ammo.addLookupName("IS Ammo Ultra AC/20");
        ammo.addLookupName("ISUltraAC20 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_AC_ULTRA;
        ammo.shots = 5;
        ammo.bv = 32;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISRotary2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Rotary AC/2 Ammo";
        ammo.setInternalName("ISRotaryAC2 Ammo");
        ammo.addLookupName("IS Rotary AC/2 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_AC_ROTARY;
        ammo.shots = 45;
        ammo.bv = 15;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISRotary5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Rotary AC/5 Ammo";
        ammo.setInternalName("ISRotaryAC5 Ammo");
        ammo.addLookupName("IS Rotary AC/5 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_AC_ROTARY;
        ammo.shots = 20;
        ammo.bv = 31;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISGaussAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Gauss Ammo";
        ammo.setInternalName("IS Gauss Ammo");
        ammo.addLookupName("IS Ammo Gauss");
        ammo.addLookupName("ISGauss Ammo");
        ammo.addLookupName("IS Gauss Rifle Ammo");
        ammo.damagePerShot = 15;
        ammo.explosive = false;
        ammo.ammoType = AmmoType.T_GAUSS;
        ammo.shots = 8;
        ammo.bv = 37;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISLTGaussAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Light Gauss Ammo";
        ammo.setInternalName("IS Light Gauss Ammo");
        ammo.addLookupName("ISLightGauss Ammo");
        ammo.addLookupName("IS Light Gauss Rifle Ammo");
        ammo.damagePerShot = 8;
        ammo.explosive = false;
        ammo.ammoType = AmmoType.T_GAUSS_LIGHT;
        ammo.shots = 16;
        ammo.bv = 20;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISHVGaussAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Heavy Gauss Ammo";
        ammo.setInternalName("ISHeavyGauss Ammo");
        ammo.addLookupName("IS Heavy Gauss Rifle Ammo");
        ammo.damagePerShot = 25;  // actually variable
        ammo.explosive = false;
        ammo.ammoType = AmmoType.T_GAUSS_HEAVY;
        ammo.shots = 4;
        ammo.bv = 43;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISStreakSRM2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Streak SRM 2 Ammo";
        ammo.setInternalName("IS Streak SRM 2 Ammo");
        ammo.addLookupName("IS Ammo Streak-2");
        ammo.addLookupName("ISStreakSRM2 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 50;
        ammo.bv = 4;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISStreakSRM4Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Streak SRM 4 Ammo";
        ammo.setInternalName("IS Streak SRM 4 Ammo");
        ammo.addLookupName("IS Ammo Streak-4");
        ammo.addLookupName("ISStreakSRM4 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 4;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 25;
        ammo.bv = 7;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISStreakSRM6Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Streak SRM 6 Ammo";
        ammo.setInternalName("IS Streak SRM 6 Ammo");
        ammo.addLookupName("IS Ammo Streak-6");
        ammo.addLookupName("ISStreakSRM6 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 6;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 15;
        ammo.bv = 11;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISMRM10Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "MRM 10 Ammo";
        ammo.setInternalName("IS MRM 10 Ammo");
        ammo.addLookupName("ISMRM10 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_MRM;
        ammo.shots = 24;
        ammo.bv = 7;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISMRM20Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "MRM 20 Ammo";
        ammo.setInternalName("IS MRM 20 Ammo");
        ammo.addLookupName("ISMRM20 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_MRM;
        ammo.shots = 12;
        ammo.bv = 14;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISMRM30Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "MRM 30 Ammo";
        ammo.setInternalName("IS MRM 30 Ammo");
        ammo.addLookupName("ISMRM30 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 30;
        ammo.ammoType = AmmoType.T_MRM;
        ammo.shots = 8;
        ammo.bv = 21;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISMRM40Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "MRM 40 Ammo";
        ammo.setInternalName("IS MRM 40 Ammo");
        ammo.addLookupName("ISMRM40 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 40;
        ammo.ammoType = AmmoType.T_MRM;
        ammo.shots = 6;
        ammo.bv = 28;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISRL10Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "RL 10 Ammo";
        ammo.setInternalName("IS Ammo RL-10");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_ROCKET_LAUNCHER;
        ammo.shots = 1;
        ammo.bv = 0;

        return ammo;
    }

    public static AmmoType createISRL15Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "RL 15 Ammo";
        ammo.setInternalName("IS Ammo RL-15");
        ammo.damagePerShot = 1;
        ammo.rackSize = 15;
        ammo.ammoType = AmmoType.T_ROCKET_LAUNCHER;
        ammo.shots = 1;
        ammo.bv = 0;

        return ammo;
    }

    public static AmmoType createISRL20Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "RL 20 Ammo";
        ammo.setInternalName("IS Ammo RL-20");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_ROCKET_LAUNCHER;
        ammo.shots = 1;
        ammo.bv = 0;

        return ammo;
    }

    public static AmmoType createISAMSAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "AMS Ammo";
        ammo.setInternalName("ISAMS Ammo");
        ammo.addLookupName("IS Ammo AMS");
        ammo.addLookupName("IS AMS Ammo");
        ammo.damagePerShot = 1; // only used for ammo crits
        ammo.rackSize = 2; // only used for ammo crits
        ammo.ammoType = AmmoType.T_AMS;
        ammo.shots = 12;
        ammo.bv = 11;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISNarcAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "Narc Pods";
        ammo.setInternalName("ISNarc Pods");
        ammo.addLookupName("IS Ammo Narc");
        ammo.addLookupName("IS Narc Missile Beacon Ammo");
        ammo.damagePerShot = 2; // only used for ammo crits
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_NARC;
        ammo.shots = 6;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISNarcExplosiveAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "Narc Explosive Pods";
        ammo.setInternalName("ISNarc ExplosivePods");
        ammo.damagePerShot = 4;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_NARC;
        ammo.munitionType = M_NARC_EX;
        ammo.shots = 6;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISiNarcAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "iNarc Pods";
        ammo.setInternalName("ISiNarc Pods");
        ammo.addLookupName("IS Ammo iNarc");
        ammo.addLookupName("IS iNarc Missile Beacon Ammo");
        ammo.damagePerShot = 3; // only used for ammo crits
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_INARC;
        ammo.shots = 4;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISiNarcExplosiveAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "iNarc Explosive Pods";
        ammo.setInternalName("ISiNarc Explosive Pods");
        ammo.damagePerShot = 6; // only used for ammo crits
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_INARC;
        ammo.munitionType = AmmoType.M_EXPLOSIVE;
        ammo.shots = 4;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }
    
    public static AmmoType createISiNarcECMAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "iNarc ECM Pods";
        ammo.setInternalName("ISiNarc ECM Pods");
        ammo.damagePerShot = 3; // only used for ammo crits
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_INARC;
        ammo.munitionType = AmmoType.M_ECM;
        ammo.shots = 4;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }
    
    public static AmmoType createISiNarcHaywireAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "iNarc Haywire Pods";
        ammo.setInternalName("ISiNarc Haywire Pods");
        ammo.damagePerShot = 3; // only used for ammo crits
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_INARC;
        ammo.munitionType = AmmoType.M_HAYWIRE;
        ammo.shots = 4;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }
    
    public static AmmoType createISiNarcNemesisAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "iNarc Nemesis Pods";
        ammo.setInternalName("ISiNarc Nemesis Pods");
        ammo.damagePerShot = 3; // only used for ammo crits
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_INARC;
        ammo.munitionType = AmmoType.M_NEMESIS;
        ammo.shots = 4;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLGaussAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Gauss Ammo";
        ammo.setInternalName("Clan Gauss Ammo");
        ammo.addLookupName("Clan Ammo Gauss");
        ammo.addLookupName("CLGauss Ammo");
        ammo.addLookupName("Clan Gauss Rifle Ammo");
        ammo.damagePerShot = 15;
        ammo.explosive = false;
        ammo.ammoType = AmmoType.T_GAUSS;
        ammo.shots = 8;
        ammo.bv = 33;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLB2XAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 2-X AC Ammo";
        ammo.setInternalName("Clan LB 2-X AC Ammo");
        ammo.addLookupName("Clan Ammo 2-X");
        ammo.addLookupName("CLLBXAC2 Ammo");
        ammo.addLookupName("Clan LB 2-X AC Ammo - Slug");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.shots = 45;
        ammo.bv = 6;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLB5XAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 5-X AC Ammo";
        ammo.setInternalName("Clan LB 5-X AC Ammo");
        ammo.addLookupName("Clan Ammo 5-X");
        ammo.addLookupName("CLLBXAC5 Ammo");
        ammo.addLookupName("Clan LB 5-X AC Ammo - Slug");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.shots = 20;
        ammo.bv = 12;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLB10XAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 10-X AC Ammo";
        ammo.setInternalName("Clan LB 10-X AC Ammo");
        ammo.addLookupName("Clan Ammo 10-X");
        ammo.addLookupName("CLLBXAC10 Ammo");
        ammo.addLookupName("Clan LB 10-X AC Ammo - Slug");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.shots = 10;
        ammo.bv = 19;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLB20XAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 20-X AC Ammo";
        ammo.setInternalName("Clan LB 20-X AC Ammo");
        ammo.addLookupName("Clan Ammo 20-X");
        ammo.addLookupName("CLLBXAC20 Ammo");
        ammo.addLookupName("Clan LB 20-X AC Ammo - Slug");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.shots = 5;
        ammo.bv = 33;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLB2XClusterAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 2-X Cluster Ammo";
        ammo.setInternalName("Clan LB 2-X Cluster Ammo");
        ammo.addLookupName("Clan Ammo 2-X (CL)");
        // this isn't a true mtf code
        ammo.addLookupName("CLLBXAC2 CL Ammo");
        ammo.addLookupName("Clan LB 2-X AC Ammo - Cluster");
        ammo.damagePerShot = 1;
        ammo.toHitModifier = -1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.munitionType = M_CLUSTER;
        ammo.shots = 45;
        ammo.bv = 6;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLB5XClusterAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 5-X Cluster Ammo";
        ammo.setInternalName("Clan LB 5-X Cluster Ammo");
        ammo.addLookupName("Clan Ammo 5-X (CL)");
        // this isn't a true mtf code
        ammo.addLookupName("CLLBXAC5 CL Ammo");
        ammo.addLookupName("Clan LB 5-X AC Ammo - Cluster");
        ammo.damagePerShot = 1;
        ammo.toHitModifier = -1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.munitionType = M_CLUSTER;
        ammo.shots = 20;
        ammo.bv = 12;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLB10XClusterAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 10-X Cluster Ammo";
        ammo.setInternalName("Clan LB 10-X Cluster Ammo");
        ammo.addLookupName("Clan Ammo 10-X (CL)");
        // this isn't a true mtf code
        ammo.addLookupName("CLLBXAC10 CL Ammo");
        ammo.addLookupName("Clan LB 10-X AC Ammo - Cluster");
        ammo.damagePerShot = 1;
        ammo.toHitModifier = -1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.munitionType = M_CLUSTER;
        ammo.shots = 10;
        ammo.bv = 19;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLB20XClusterAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LB 20-X Cluster Ammo";
        ammo.setInternalName("Clan LB 20-X Cluster Ammo");
        ammo.addLookupName("Clan Ammo 20-X (CL)");
        // this isn't a true mtf code
        ammo.addLookupName("CLLBXAC20 CL Ammo");
        ammo.addLookupName("Clan LB 20-X AC Ammo - Cluster");
        ammo.damagePerShot = 1;
        ammo.toHitModifier = -1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_AC_LBX;
        ammo.munitionType = M_CLUSTER;
        ammo.shots = 5;
        ammo.bv = 33;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLVehicleFlamerAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Vehicle Flamer Ammo";
        ammo.setInternalName("Clan Vehicle Flamer Ammo");
        ammo.addLookupName("Clan Ammo Vehicle Flamer");
        ammo.addLookupName("CLVehicleFlamer Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_VEHICLE_FLAMER;
        ammo.shots = 20;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLHeavyMGAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Heavy Machine Gun Ammo";
        ammo.setInternalName("Clan Heavy Machine Gun Ammo - Full");
        ammo.addLookupName("CLHeavyMG Ammo (100)");
        ammo.addLookupName("Clan Heavy Machine Gun Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_MG_HEAVY;
        ammo.flags |= F_MG;
        ammo.shots = 100;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLHeavyMGAmmoHalf() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Half Heavy Machine Gun Ammo";
        ammo.setInternalName("Clan Heavy Machine Gun Ammo - Half");
        ammo.addLookupName("CLHeavyMG Ammo (50)");
        ammo.addLookupName("Clan Heavy Machine Gun Ammo (1/2 ton)");
        ammo.damagePerShot = 1;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_MG_HEAVY;
        ammo.flags |= F_MG;
        ammo.shots = 50;
        ammo.tonnage = 0.5f;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLMGAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Machine Gun Ammo";
        ammo.setInternalName("Clan Machine Gun Ammo - Full");
        ammo.addLookupName("Clan Ammo MG - Full");
        ammo.addLookupName("CLMG Ammo (200)");
        ammo.addLookupName("Clan Machine Gun Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_MG;
        ammo.flags |= F_MG;
        ammo.shots = 200;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLMGAmmoHalf() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Half Machine Gun Ammo";
        ammo.setInternalName("Clan Machine Gun Ammo - Half");
        ammo.addLookupName("Clan Ammo MG - Half");
        ammo.addLookupName("CLMG Ammo (100)");
        ammo.addLookupName("Clan Machine Gun Ammo (1/2 ton)");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_MG;
        ammo.flags |= F_MG;
        ammo.shots = 100;
        ammo.tonnage = 0.5f;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLightMGAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Light Machine Gun Ammo";
        ammo.setInternalName("Clan Light Machine Gun Ammo - Full");
        ammo.addLookupName("CLLightMG Ammo (200)");
        ammo.addLookupName("Clan Light Machine Gun Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_MG_LIGHT;
        ammo.flags |= F_MG;
        ammo.shots = 200;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLightMGAmmoHalf() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Half Light Machine Gun Ammo";
        ammo.setInternalName("Clan Light Machine Gun Ammo - Half");
        ammo.addLookupName("CLLightMG Ammo (100)");
        ammo.addLookupName("Clan Light Machine Gun Ammo (1/2 ton)");
        ammo.damagePerShot = 1;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_MG_LIGHT;
        ammo.flags |= F_MG;
        ammo.shots = 100;
        ammo.tonnage = 0.5f;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLUltra2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Ultra AC/2 Ammo";
        ammo.setInternalName("Clan Ultra AC/2 Ammo");
        ammo.addLookupName("Clan Ammo Ultra AC/2");
        ammo.addLookupName("CLUltraAC2 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_AC_ULTRA;
        ammo.shots = 45;
        ammo.bv = 6;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLUltra5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Ultra AC/5 Ammo";
        ammo.setInternalName("Clan Ultra AC/5 Ammo");
        ammo.addLookupName("Clan Ammo Ultra AC/5");
        ammo.addLookupName("CLUltraAC5 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_AC_ULTRA;
        ammo.shots = 20;
        ammo.bv = 15;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLUltra10Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Ultra AC/10 Ammo";
        ammo.setInternalName("Clan Ultra AC/10 Ammo");
        ammo.addLookupName("Clan Ammo Ultra AC/10");
        ammo.addLookupName("CLUltraAC10 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_AC_ULTRA;
        ammo.shots = 10;
        ammo.bv = 26;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLUltra20Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Ultra AC/20 Ammo";
        ammo.setInternalName("Clan Ultra AC/20 Ammo");
        ammo.addLookupName("Clan Ammo Ultra AC/20");
        ammo.addLookupName("CLUltraAC20 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_AC_ULTRA;
        ammo.shots = 5;
        ammo.bv = 35;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLRM1Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 1 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-1");
        ammo.addLookupName("Clan Ammo LRM-1");
        ammo.addLookupName("CLLRM1 Ammo");
        ammo.addLookupName("Clan LRM 1 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 2;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM2Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 2 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-2");
        ammo.addLookupName("Clan Ammo LRM-2");
        ammo.addLookupName("CLLRM2 Ammo");
        ammo.addLookupName("Clan LRM 2 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 3;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM3Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 3 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-3");
        ammo.addLookupName("Clan Ammo LRM-3");
        ammo.addLookupName("CLLRM3 Ammo");
        ammo.addLookupName("Clan LRM 3 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 5;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM4Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 4 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-4");
        ammo.addLookupName("Clan Ammo LRM-4");
        ammo.addLookupName("CLLRM4 Ammo");
        ammo.addLookupName("Clan LRM 4 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 4;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 6;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 5 Ammo";
        ammo.setInternalName("Clan Ammo LRM-5");
        ammo.addLookupName("CLLRM5 Ammo");
        ammo.addLookupName("Clan LRM 5 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 24;
        ammo.bv = 7;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLRM6Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 6 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-6");
        ammo.addLookupName("Clan Ammo LRM-6");
        ammo.addLookupName("CLLRM6 Ammo");
        ammo.addLookupName("Clan LRM 6 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 6;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 9;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM7Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 7 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-7");
        ammo.addLookupName("Clan Ammo LRM-7");
        ammo.addLookupName("CLLRM7 Ammo");
        ammo.addLookupName("Clan LRM 7 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 7;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 12;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM8Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 8 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-8");
        ammo.addLookupName("Clan Ammo LRM-8");
        ammo.addLookupName("CLLRM8 Ammo");
        ammo.addLookupName("Clan LRM 8 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 8;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 12;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM9Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 9 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-9");
        ammo.addLookupName("Clan Ammo LRM-9");
        ammo.addLookupName("CLLRM9 Ammo");
        ammo.addLookupName("Clan LRM 9 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 9;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 12;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM10Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 10 Ammo";
        ammo.setInternalName("Clan Ammo LRM-10");
        ammo.addLookupName("CLLRM10 Ammo");
        ammo.addLookupName("Clan LRM 10 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 12;
        ammo.bv = 14;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLRM11Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 11 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-11");
        ammo.addLookupName("Clan Ammo LRM-11");
        ammo.addLookupName("CLLRM11 Ammo");
        ammo.addLookupName("Clan LRM 11 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 11;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 18;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM12Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 12 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-12");
        ammo.addLookupName("Clan Ammo LRM-12");
        ammo.addLookupName("CLLRM12 Ammo");
        ammo.addLookupName("Clan LRM 12 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 12;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 18;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM13Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 13 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-13");
        ammo.addLookupName("Clan Ammo LRM-13");
        ammo.addLookupName("CLLRM13 Ammo");
        ammo.addLookupName("Clan LRM 13 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 13;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 20;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM14Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 14 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-14");
        ammo.addLookupName("Clan Ammo LRM-14");
        ammo.addLookupName("CLLRM14 Ammo");
        ammo.addLookupName("Clan LRM 14 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 14;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 21;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM15Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 15 Ammo";
        ammo.setInternalName("Clan Ammo LRM-15");
        ammo.addLookupName("CLLRM15 Ammo");
        ammo.addLookupName("Clan LRM 15 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 15;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 8;
        ammo.bv = 21;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLLRM16Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 16 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-16");
        ammo.addLookupName("Clan Ammo LRM-16");
        ammo.addLookupName("CLLRM16 Ammo");
        ammo.addLookupName("Clan LRM 16 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 16;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 27;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM17Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 17 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-17");
        ammo.addLookupName("Clan Ammo LRM-17");
        ammo.addLookupName("CLLRM17 Ammo");
        ammo.addLookupName("Clan LRM 17 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 17;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 27;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM18Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 18 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-18");
        ammo.addLookupName("Clan Ammo LRM-18");
        ammo.addLookupName("CLLRM18 Ammo");
        ammo.addLookupName("Clan LRM 18 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 18;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 27;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM19Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "LRM 19 Ammo";
        ammo.setInternalName("Clan Ammo Protomech LRM-19");
        ammo.addLookupName("Clan Ammo LRM-19");
        ammo.addLookupName("CLLRM19 Ammo");
        ammo.addLookupName("Clan LRM 19 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 19;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 100;
        ammo.bv = 28;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;
        return ammo;

    }

    public static AmmoType createCLLRM20Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 20 Ammo";
        ammo.setInternalName("Clan Ammo LRM-20");
        ammo.addLookupName("CLLRM20 Ammo");
        ammo.addLookupName("Clan LRM 20 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.shots = 6;
        ammo.bv = 27;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLSRM1Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 1 Ammo";
        ammo.setInternalName("Clan Ammo SRM-1");
        ammo.addLookupName("CLSRM1 Ammo");
        ammo.addLookupName("Clan SRM 1 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 100;
        ammo.bv = 2;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLSRM2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 2 Ammo";
        ammo.setInternalName("Clan Ammo SRM-2");
        ammo.addLookupName("CLSRM2 Ammo");
        ammo.addLookupName("Clan SRM 2 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 50;
        ammo.bv = 3;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLSRM3Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 3 Ammo";
        ammo.setInternalName("Clan Ammo SRM-3");
        ammo.addLookupName("CLSRM3 Ammo");
        ammo.addLookupName("Clan SRM 3 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 100;
        ammo.bv = 4;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLSRM4Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 4 Ammo";
        ammo.setInternalName("Clan Ammo SRM-4");
        ammo.addLookupName("CLSRM4 Ammo");
        ammo.addLookupName("Clan SRM 4 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 4;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 25;
        ammo.bv = 5;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLSRM5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 5 Ammo";
        ammo.setInternalName("Clan Ammo SRM-5");
        ammo.addLookupName("CLSRM5 Ammo");
        ammo.addLookupName("Clan SRM 5 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 100;
        ammo.bv = 5;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLSRM6Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 6 Ammo";
        ammo.setInternalName("Clan Ammo SRM-6");
        ammo.addLookupName("CLSRM6 Ammo");
        ammo.addLookupName("Clan SRM 6 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 6;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.shots = 15;
        ammo.bv = 7;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLStreakSRM1Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "Streak SRM 1 Ammo";
        ammo.setInternalName("Clan Streak SRM 1 Ammo");
        ammo.addLookupName("Clan Ammo Streak-1");
        ammo.addLookupName("CLStreakSRM1 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 100;
        ammo.bv = 3;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLStreakSRM2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Streak SRM 2 Ammo";
        ammo.setInternalName("Clan Streak SRM 2 Ammo");
        ammo.addLookupName("Clan Ammo Streak-2");
        ammo.addLookupName("CLStreakSRM2 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 50;
        ammo.bv = 5;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLStreakSRM3Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "Streak SRM 3 Ammo";
        ammo.setInternalName("Clan Streak SRM 3 Ammo");
        ammo.addLookupName("Clan Ammo Streak-3");
        ammo.addLookupName("CLStreakSRM3 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 100;
        ammo.bv = 8;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLStreakSRM4Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Streak SRM 4 Ammo";
        ammo.setInternalName("Clan Streak SRM 4 Ammo");
        ammo.addLookupName("Clan Ammo Streak-4");
        ammo.addLookupName("CLStreakSRM4 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 4;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 25;
        ammo.bv = 10;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLStreakSRM5Ammo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "Streak SRM 5 Ammo";
        ammo.setInternalName("Clan Streak SRM 5 Ammo");
        ammo.addLookupName("Clan Ammo Streak-5");
        ammo.addLookupName("CLStreakSRM5 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 100;
        ammo.bv = 13;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLStreakSRM6Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Streak SRM 6 Ammo";
        ammo.setInternalName("Clan Streak SRM 6 Ammo");
        ammo.addLookupName("Clan Ammo Streak-6");
        ammo.addLookupName("CLStreakSRM6 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 6;
        ammo.ammoType = AmmoType.T_SRM_STREAK;
        ammo.shots = 15;
        ammo.bv = 15;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLAMSAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "AMS Ammo";
        ammo.setInternalName("CLAMS Ammo");
        ammo.addLookupName("Clan Ammo AMS");
        ammo.addLookupName("Clan AMS Ammo");
        ammo.damagePerShot = 1; // only used for ammo crits
        ammo.rackSize = 2; // only used for ammo crits
        ammo.ammoType = AmmoType.T_AMS;
        ammo.shots = 24;
        ammo.bv = 21;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLNarcAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "Narc Pods";
        ammo.setInternalName("CLNarc Pods");
        ammo.addLookupName("Clan Ammo Narc");
        ammo.addLookupName("Clan Narc Missile Beacon Ammo");
        ammo.damagePerShot = 2; // only used for ammo crits
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_NARC;
        ammo.shots = 6;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLNarcExplosiveAmmo() {
        AmmoType ammo = new AmmoType();
        ammo.name = "Narc Explosive Pods";
        ammo.setInternalName("CLNarc Explosive Pods");
        ammo.damagePerShot = 4;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_NARC;
        ammo.munitionType = AmmoType.M_NARC_EX;
        ammo.shots = 6;
        ammo.bv = 0;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM3Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 3 Ammo";
        ammo.setInternalName("Clan Ammo ATM-3");
        ammo.addLookupName("CLATM3 Ammo");
        ammo.addLookupName("Clan ATM-3 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.shots = 20;
        ammo.bv = 14;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM3ERAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 3 ER Ammo";
        ammo.setInternalName("Clan Ammo ATM-3 ER");
        ammo.addLookupName("CLATM3 ER Ammo");
        ammo.addLookupName("Clan ATM-3 ER Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.munitionType = M_EXTENDED_RANGE;
        ammo.shots = 20;
        ammo.bv = 14;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM3HEAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 3 HE Ammo";
        ammo.setInternalName("Clan Ammo ATM-3 HE");
        ammo.addLookupName("CLATM3 HE Ammo");
        ammo.addLookupName("Clan ATM-3 HE Ammo");
        ammo.damagePerShot = 3;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.munitionType = M_HIGH_EXPLOSIVE;
        ammo.shots = 20;
        ammo.bv = 14;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM6Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 6 Ammo";
        ammo.setInternalName("Clan Ammo ATM-6");
        ammo.addLookupName("CLATM6 Ammo");
        ammo.addLookupName("Clan ATM-6 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 6;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.shots = 10;
        ammo.bv = 26;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM6ERAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 6 ER Ammo";
        ammo.setInternalName("Clan Ammo ATM-6 ER");
        ammo.addLookupName("CLATM6 ER Ammo");
        ammo.addLookupName("Clan ATM-6 ER Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 6;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.munitionType = M_EXTENDED_RANGE;
        ammo.shots = 10;
        ammo.bv = 26;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM6HEAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 6 HE Ammo";
        ammo.setInternalName("Clan Ammo ATM-6 HE");
        ammo.addLookupName("CLATM6 HE Ammo");
        ammo.addLookupName("Clan ATM-6 HE Ammo");
        ammo.damagePerShot = 3;
        ammo.rackSize = 6;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.munitionType = M_HIGH_EXPLOSIVE;
        ammo.shots = 10;
        ammo.bv = 26;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM9Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 9 Ammo";
        ammo.setInternalName("Clan Ammo ATM-9");
        ammo.addLookupName("CLATM9 Ammo");
        ammo.addLookupName("Clan ATM-9 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 9;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.shots = 7;
        ammo.bv = 36;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM9ERAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 9 ER Ammo";
        ammo.setInternalName("Clan Ammo ATM-9 ER");
        ammo.addLookupName("CLATM9 ER Ammo");
        ammo.addLookupName("Clan ATM-9 ER Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 9;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.munitionType = M_EXTENDED_RANGE;
        ammo.shots = 7;
        ammo.bv = 36;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM9HEAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 9 HE Ammo";
        ammo.setInternalName("Clan Ammo ATM-9 HE");
        ammo.addLookupName("CLATM9 HE Ammo");
        ammo.addLookupName("Clan ATM-9 HE Ammo");
        ammo.damagePerShot = 3;
        ammo.rackSize = 9;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.munitionType = M_HIGH_EXPLOSIVE;
        ammo.shots = 7;
        ammo.bv = 36;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM12Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 12 Ammo";
        ammo.setInternalName("Clan Ammo ATM-12");
        ammo.addLookupName("CLATM12 Ammo");
        ammo.addLookupName("Clan ATM-12 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 12;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.shots = 5;
        ammo.bv = 52;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM12ERAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 12 ER Ammo";
        ammo.setInternalName("Clan Ammo ATM-12 ER");
        ammo.addLookupName("CLATM12 ER Ammo");
        ammo.addLookupName("Clan ATM-12 ER Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 12;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.munitionType = M_EXTENDED_RANGE;
        ammo.shots = 5;
        ammo.bv = 52;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLATM12HEAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "ATM 12 HE Ammo";
        ammo.setInternalName("Clan Ammo ATM-12 HE");
        ammo.addLookupName("CLATM12 HE Ammo");
        ammo.addLookupName("Clan ATM-12 HE Ammo");
        ammo.damagePerShot = 3;
        ammo.rackSize = 12;
        ammo.ammoType = AmmoType.T_ATM;
        ammo.munitionType = M_HIGH_EXPLOSIVE;
        ammo.shots = 5;
        ammo.bv = 52;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    // Start BattleArmor ammo
    public static AmmoType createBASRM2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 2 Ammo";
        ammo.setInternalName("BA-SRM2 Ammo");
        ammo.addLookupName("BASRM2 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 2;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createBASRM2OSAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 2 Ammo";
        ammo.setInternalName(BattleArmor.IS_DISPOSABLE_SRM2_AMMO);
        ammo.addLookupName("BASRM2OS Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 1;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createBAInfernoSRMAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Inferno SRM Ammo";
        ammo.setInternalName("BA-Inferno SRM Ammo");
        ammo.addLookupName("BAInfernoSRM Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_BA_INFERNO;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 1;
        ammo.hittable = false;
        ammo.bv = 0;
        ammo.munitionType = M_INFERNO;

        return ammo;
    }
    public static AmmoType createBAAdvancedSRM2Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Advanced SRM 2 Ammo";
        ammo.setInternalName("BA-Advanced SRM2 Ammo");
        ammo.addLookupName("BAAdvancedSRM2 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM_ADVANCED;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 2;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createBAAdvancedSRM5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Advanced SRM 5 Ammo";
        ammo.setInternalName("BA-Advanced SRM-5 Ammo");
        ammo.addLookupName("BA-Advanced SRM-5 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_SRM_ADVANCED;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 2;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createBAMicroBombAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Micro Bomb Ammo";
        ammo.setInternalName("BA-Micro Bomb Ammo");
        ammo.addLookupName("BAMicroBomb Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_BA_MICRO_BOMB;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 1;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createCLTorpedoLRM5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Torpedo/LRM 5 Ammo";
        ammo.setInternalName("Clan Torpedo/LRM5 Ammo");
        ammo.addLookupName("CLTorpedoLRM5 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_LRM_TORPEDO_COMBO;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 1;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createFenrirSRM4Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 4 Ammo";
        ammo.setInternalName("Fenrir SRM-4 Ammo");
        ammo.addLookupName("FenrirSRM4 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 4;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createPhalanxSRM4Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 4 Ammo";
        ammo.setInternalName("PhalanxSRM4Ammo");
        ammo.addLookupName("Phalanx SRM4 Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 2;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createGrenadierSRM4Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "SRM 4 Ammo";
        ammo.setInternalName("BA-SRM4 Grenadier Ammo");
        ammo.addLookupName("BA-SRM4 Grenadier Ammo");
        ammo.damagePerShot = 2;
        ammo.rackSize = 4;
        ammo.ammoType = AmmoType.T_SRM;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 7;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createBACompactNarcAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Compact Narc Ammo";
        ammo.setInternalName(BattleArmor.IS_DISPOSABLE_NARC_AMMO);
        ammo.addLookupName("BACompactNarc Ammo");
        ammo.damagePerShot = 2; // only used for ammo crits
        ammo.rackSize = 4;
        ammo.ammoType = AmmoType.T_NARC;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 2;
        ammo.hittable = false;
        ammo.bv = 0;

        return ammo;
    }
    public static AmmoType createBAMineLauncherAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Mine Launcher Ammo";
        ammo.setInternalName("BA-Mine Launcher Ammo");
        ammo.addLookupName("BAMineLauncher Ammo");
        ammo.damagePerShot = 4;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_MINE;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 1;
        ammo.bv = 0;

        return ammo;
    }

    public static AmmoType createBALRM5Ammo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "LRM 5 Ammo";
        ammo.setInternalName("BA Ammo LRM-5");
        ammo.addLookupName("BALRM5 Ammo");
        ammo.addLookupName("BA LRM 5 Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_LRM;
        ammo.flags |= F_BATTLEARMOR;
        ammo.shots = 6;
        ammo.bv = 0;

        return ammo;
    }

    //Proto Ammos
    public static AmmoType createCLPROHeavyMGAmmo() {
        // Need special processing to allow non-standard ammo loads.
        AmmoType ammo = new AmmoType();

        ammo.name = "Heavy Machine Gun Ammo";
        ammo.setInternalName("Clan Heavy Machine Gun Ammo - Proto");
        ammo.addLookupName("CLHeavyMG Ammo");
        ammo.addLookupName("Clan Heavy Machine Gun Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 3;
        ammo.ammoType = AmmoType.T_MG_HEAVY;
        ammo.flags |= F_MG | F_PROTOMECH;
        ammo.shots = 100;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLPROMGAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Machine Gun Ammo";
        ammo.setInternalName("Clan Machine Gun Ammo - Proto");
        ammo.addLookupName("CLMG Ammo");
        ammo.addLookupName("Clan Machine Gun Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 2;
        ammo.ammoType = AmmoType.T_MG;
        ammo.flags |= F_MG | F_PROTOMECH;
        ammo.shots = 200;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLPROLightMGAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Light Machine Gun Ammo";
        ammo.setInternalName("Clan Light Machine Gun Ammo - Proto");
        ammo.addLookupName("CLLightMG Ammo");
        ammo.addLookupName("Clan Light Machine Gun Ammo");
        ammo.damagePerShot = 1;
        ammo.rackSize = 1;
        ammo.ammoType = AmmoType.T_MG_LIGHT;
        ammo.flags |= F_MG | F_PROTOMECH;
        ammo.shots = 200;
        ammo.bv = 1;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
    }

    public static AmmoType createISArrowIVAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Arrow IV Ammo";
        ammo.setInternalName("ISArrowIVAmmo");
        ammo.addLookupName("ISArrowIV Ammo");
        ammo.addLookupName("IS Ammo Arrow");
        ammo.addLookupName("IS Arrow IV Ammo");
        ammo.damagePerShot=1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_ARROW_IV;
        ammo.shots = 5;
        ammo.bv = 11;
        ammo.techType = TechConstants.T_IS_LEVEL_2;

        return ammo;
    }

    public static AmmoType createCLArrowIVAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Arrow IV Ammo";
        ammo.setInternalName("CLArrowIVAmmo");
        ammo.addLookupName("CLArrowIV Ammo");
        ammo.addLookupName("Clan Ammo Arrow");
        ammo.addLookupName("Clan Arrow IV Ammo");
        ammo.damagePerShot=1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_ARROW_IV;
        ammo.shots = 5;
        ammo.bv = 11;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
      }

    public static AmmoType createCLLongTomAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Long Tom Ammo";
        ammo.setInternalName("CLLongTomAmmo");
        ammo.addLookupName("CLLongTom Ammo");
        ammo.addLookupName("CLLongTomArtillery Ammo");
        ammo.addLookupName("Clan Ammo Long Tom");
        ammo.addLookupName("Clan Long Tom Ammo");
        ammo.damagePerShot=1;
        ammo.rackSize = 20;
        ammo.ammoType = AmmoType.T_LONG_TOM;
        ammo.shots = 5;
        ammo.bv = 11;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
      }

    public static AmmoType createCLSniperAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Sniper Ammo";
        ammo.setInternalName("CLSniperAmmo");
        ammo.addLookupName("CLSniper Ammo");
        ammo.addLookupName("CLSniperArtillery Ammo");
        ammo.addLookupName("Clan Ammo Sniper");
        ammo.addLookupName("Clan Sniper Ammo");
        ammo.damagePerShot=1;
        ammo.rackSize = 10;
        ammo.ammoType = AmmoType.T_SNIPER;
        ammo.shots = 10;
        ammo.bv = 5;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
      }

    public static AmmoType createCLThumperAmmo() {
        AmmoType ammo = new AmmoType();

        ammo.name = "Thumper Ammo";
        ammo.setInternalName("CLThumperAmmo");
        ammo.addLookupName("CLThumper Ammo");
        ammo.addLookupName("CLThumperArtillery Ammo");
        ammo.addLookupName("Clan Ammo Thumper");
        ammo.addLookupName("Clan Thumper Ammo");
        ammo.damagePerShot=1;
        ammo.rackSize = 5;
        ammo.ammoType = AmmoType.T_THUMPER;
        ammo.shots = 20;
        ammo.bv = 3;
        ammo.techType = TechConstants.T_CLAN_LEVEL_2;

        return ammo;
      }

    public String toString() {
        return "Ammo: " + name;
    }

    public static boolean canClearMinefield(AmmoType at) {

        if (at != null &&
            (at.getAmmoType() == T_LRM ||
             at.getAmmoType() == T_MRM) &&
            at.getRackSize() >= 20 &&
            at.getMunitionType() == M_STANDARD) {
            return true;
        }

        return false;
    }

    public static boolean canDeliverMinefield(AmmoType at) {

        if (at != null &&
            at.getAmmoType() == T_LRM &&
            ( (at.getMunitionType() == M_THUNDER)
              || (at.getMunitionType() == M_THUNDER_INFERNO)
              || (at.getMunitionType() == M_THUNDER_AUGMENTED)
              || (at.getMunitionType() == M_THUNDER_VIBRABOMB)
              || (at.getMunitionType() == M_THUNDER_ACTIVE) ) ) {
            return true;
        }

        return false;
    }

    private void addToBegining() {

    }

    private void addToEnd(AmmoType base, String modifier) {
        Enumeration n = base.getNames();
        while (n.hasMoreElements()) {
            String s = (String)n.nextElement();
            addLookupName(s + modifier);
        }
    }

    private void addBeforeString(AmmoType base, String keyWord, String modifier) {
        Enumeration n = base.getNames();
        while (n.hasMoreElements()) {
            String s = (String)n.nextElement();
            StringBuffer sb = new StringBuffer(s);
            sb.insert(s.lastIndexOf(keyWord), modifier);
            addLookupName(sb.toString());
        }
    }

    /**
     * Helper class for creating munition types.
     */
    static private class MunitionMutator {
        /** The name of this munition type.
         */
        private String name;

        /** The weight ratio of a round of this munition to a standard round.
         */
        private int weight;

        /** The munition flag(s) for this type.
         */
        private int type;

        /**
         * Create a mutator that will transform the <code>AmmoType</code> of
         * a base round into one of its muntions.
         *
         * @param   munitionName - the <code>String</code> name of this
         *          munition type.
         * @param   weightRation - the <code>int</code> ratio of a round
         *          of this munition to a round of the standard type.
         * @param   munitionType - the <code>int</code> munition flag(s)
         *          of this type.
         */
        public MunitionMutator( String munitionName, int weightRatio,
                                int munitionType ) {
            name = munitionName;
            weight = weightRatio;
            type = munitionType;
        }

        /**
         * Create the <code>AmmoType</code> for this munition type for
         * the given rack size.
         * @param   base - the <code>AmmoType</code> of the base round.
         * @return  this munition's <code>AmmotType</code>.
         */
        public AmmoType createMunitionType( AmmoType base ) {
            StringBuffer nameBuf;
            String temp;
            int index;

            // Create an uninitialized munition object.
            AmmoType munition = new AmmoType();

            // Manipulate the base round's names, depending on ammoType.
            switch ( base.ammoType ) {
            case AmmoType.T_AC:
                // Add the munition name to the beginning of the display name.
                nameBuf = new StringBuffer( this.name );
                nameBuf.append( " " );
                nameBuf.append( base.name );
                munition.name = nameBuf.toString();

                // Add the munition name to the end of the TDB ammo name.
                nameBuf = new StringBuffer( " - " );
                nameBuf.append( this.name );
                munition.addToEnd(base, " - " + this.name);
                
                // The munition name appears in the middle of the other names.
                nameBuf = new StringBuffer( base.internalName );
                index = base.internalName.lastIndexOf( "Ammo" );
                nameBuf.insert( index, ' ' );
                nameBuf.insert( index, this.name );
                munition.setInternalName(nameBuf.toString());
                munition.addBeforeString(base, "Ammo", this.name + " ");
                nameBuf = null;
                break;
            case AmmoType.T_ARROW_IV:
                // The munition name appears in the middle of all names.
                nameBuf = new StringBuffer( base.name );
                index = base.name.lastIndexOf( "Ammo" );
                nameBuf.insert( index, ' ' );
                // Do special processing for munition names ending in "IV".
                //  Note: this does not work for The Drawing Board
                if ( this.name.endsWith("-IV") ) {
                    StringBuffer tempName = new StringBuffer(this.name);
                    tempName.setLength(tempName.length() - 3);
                    nameBuf.insert( index, tempName.toString() );
                } else {
                    nameBuf.insert( index, this.name );
                }
                munition.name = nameBuf.toString();

                nameBuf = new StringBuffer( base.internalName );
                index = base.internalName.lastIndexOf( "Ammo" );
                nameBuf.insert( index, this.name );
                munition.setInternalName(nameBuf.toString());

                munition.addBeforeString(base, "Ammo", this.name + " ");
                munition.addToEnd(base, " - " + this.name);
                if (this.name.equals("Homing"))
                    munition.addToEnd(base, " (HO)"); //mep
                nameBuf = null;

                break;
            case AmmoType.T_SRM:
                // Add the munition name to the end of some of the ammo names.
                nameBuf = new StringBuffer( " " );
                nameBuf.append( this.name );
                munition.setInternalName(base.internalName + nameBuf.toString());
                munition.addToEnd(base, nameBuf.toString());
                nameBuf.insert( 0, " -" );
                munition.addToEnd(base, nameBuf.toString());

                // The munition name appears in the middle of the other names.
                nameBuf = new StringBuffer( base.name );
                index = base.name.lastIndexOf( "Ammo" );
                nameBuf.insert( index, ' ' );
                nameBuf.insert( index, this.name );
                munition.name = nameBuf.toString();
                nameBuf = null;
                munition.addBeforeString(base, "Ammo", this.name + " ");
                break;
            case AmmoType.T_LRM:
                // Add the munition name to the beginning of the display name.
                nameBuf = new StringBuffer( this.name );
                nameBuf.append( " " );
                nameBuf.append( base.name );
                munition.name = nameBuf.toString();

                // Add the munition name to the end of some of the ammo names.
                nameBuf = new StringBuffer( " " );
                nameBuf.append( this.name );
                munition.setInternalName(base.internalName + nameBuf.toString());
                munition.addToEnd(base, nameBuf.toString());
                nameBuf.insert( 0, " -" );
                munition.addToEnd(base, nameBuf.toString());
                break;
            case AmmoType.T_LONG_TOM:
            case AmmoType.T_SNIPER:
            case AmmoType.T_THUMPER:
                // Add the munition name to the beginning of the display name.
                nameBuf = new StringBuffer( this.name );
                nameBuf.append( " " );
                nameBuf.append( base.name );
                munition.name = nameBuf.toString();
                munition.setInternalName( munition.name );
                munition.addToEnd( base, munition.name );

                // The munition name appears in the middle of the other names.
                munition.addBeforeString(base, "Ammo", this.name + " ");
                break;
            default:
                throw new IllegalArgumentException
                    ( "Don't know how to create munitions for " +
                      base.ammoType );
            }

            // Assign our munition type.
            munition.munitionType = this.type;

            // Reduce base number of shots to reflect the munition's weight.
            munition.shots = base.shots / this.weight;

            // Assign techlevel of the munition type.
            // TODO : find a non-hardcoded way of doing this.
            if ( TechConstants.T_CLAN_LEVEL_2 == base.techType ) {
                munition.techType = TechConstants.T_CLAN_LEVEL_2;
            } else {
                munition.techType = TechConstants.T_IS_LEVEL_2;
            }

            // Copy over all other values.
            munition.damagePerShot = base.damagePerShot;
            munition.rackSize = base.rackSize;
            munition.ammoType = base.ammoType;
            munition.bv = base.bv;
            munition.flags = base.flags;
            munition.hittable = base.hittable;
            munition.explosive = base.explosive;
            munition.toHitModifier = base.toHitModifier;

            // Return the new munition.
            return munition;
        }
    } // End private class MunitionMutator

}
