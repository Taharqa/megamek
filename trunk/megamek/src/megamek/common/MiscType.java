/**
 * * MegaMek - Copyright (C) 2000,2001,2002,2003,2004,2005 Ben Mazur
 * (bmazur@sev.org)
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */

/**
 * MiscType.java
 *
 * Created on April 2, 2002, 12:15 PM
 */

package megamek.common;

import java.math.BigInteger;

import megamek.common.weapons.ISERPPC;
import megamek.common.weapons.ISHeavyPPC;
import megamek.common.weapons.ISLightPPC;
import megamek.common.weapons.ISPPC;
import megamek.common.weapons.ISSnubNosePPC;

/**
 * @author Ben
 * @version
 */
public class MiscType extends EquipmentType {
    // equipment flags (okay, like every type of equipment has its own flag)
    public static final BigInteger F_HEAT_SINK = BigInteger.valueOf(1).shiftLeft(0);
    public static final BigInteger F_DOUBLE_HEAT_SINK = BigInteger.valueOf(1).shiftLeft(1);
    public static final BigInteger F_JUMP_JET = BigInteger.valueOf(1).shiftLeft(2);
    public static final BigInteger F_CASE = BigInteger.valueOf(1).shiftLeft(3);
    public static final BigInteger F_MASC = BigInteger.valueOf(1).shiftLeft(4);
    public static final BigInteger F_TSM = BigInteger.valueOf(1).shiftLeft(5);
    public static final BigInteger F_LASER_HEAT_SINK = BigInteger.valueOf(1).shiftLeft(6);
    public static final BigInteger F_C3S = BigInteger.valueOf(1).shiftLeft(7);
    public static final BigInteger F_C3I = BigInteger.valueOf(1).shiftLeft(8);
    public static final BigInteger F_ARTEMIS = BigInteger.valueOf(1).shiftLeft(9);
    public static final BigInteger F_TARGCOMP = BigInteger.valueOf(1).shiftLeft(10);
    public static final BigInteger F_ANGEL_ECM = BigInteger.valueOf(1).shiftLeft(11);
    public static final BigInteger F_BOARDING_CLAW = BigInteger.valueOf(1).shiftLeft(12);
    public static final BigInteger F_VACUUM_PROTECTION = BigInteger.valueOf(1).shiftLeft(13);
    public static final BigInteger F_MAGNET_CLAW = BigInteger.valueOf(1).shiftLeft(14);
    public static final BigInteger F_FIRE_RESISTANT = BigInteger.valueOf(1).shiftLeft(15);
    public static final BigInteger F_STEALTH = BigInteger.valueOf(1).shiftLeft(16);
    public static final BigInteger F_MINE = BigInteger.valueOf(1).shiftLeft(17);
    public static final BigInteger F_TOOLS = BigInteger.valueOf(1).shiftLeft(18);
    public static final BigInteger F_MAGNETIC_CLAMP = BigInteger.valueOf(1).shiftLeft(19);
    public static final BigInteger F_PARAFOIL = BigInteger.valueOf(1).shiftLeft(20);
    public static final BigInteger F_FERRO_FIBROUS = BigInteger.valueOf(1).shiftLeft(21);
    public static final BigInteger F_ENDO_STEEL = BigInteger.valueOf(1).shiftLeft(22);
    public static final BigInteger F_AP_POD = BigInteger.valueOf(1).shiftLeft(23);
    public static final BigInteger F_SEARCHLIGHT = BigInteger.valueOf(1).shiftLeft(24);
    public static final BigInteger F_CLUB = BigInteger.valueOf(1).shiftLeft(25);
    public static final BigInteger F_HAND_WEAPON = BigInteger.valueOf(1).shiftLeft(26);
    public static final BigInteger F_COWL = BigInteger.valueOf(1).shiftLeft(27);
    public static final BigInteger F_JUMP_BOOSTER = BigInteger.valueOf(1).shiftLeft(28);
    public static final BigInteger F_HARJEL = BigInteger.valueOf(1).shiftLeft(29);
    public static final BigInteger F_UMU = BigInteger.valueOf(1).shiftLeft(30);
    public static final BigInteger F_COOLANT_SYSTEM = BigInteger.valueOf(1).shiftLeft(31);
    public static final BigInteger F_SPIKES = BigInteger.valueOf(1).shiftLeft(32);
    public static final BigInteger F_COMMUNICATIONS = BigInteger.valueOf(1).shiftLeft(33);
    public static final BigInteger F_PPC_CAPACITOR = BigInteger.valueOf(1).shiftLeft(34);
    public static final BigInteger F_REFLECTIVE = BigInteger.valueOf(1).shiftLeft(35);
    public static final BigInteger F_REACTIVE = BigInteger.valueOf(1).shiftLeft(36);
    public static final BigInteger F_CASEII = BigInteger.valueOf(1).shiftLeft(37);
    public static final BigInteger F_LIFTHOIST = BigInteger.valueOf(1).shiftLeft(38);
    public static final BigInteger F_ENVIRONMENTAL_SEALING = BigInteger.valueOf(1).shiftLeft(39);
    public static final BigInteger F_ARMORED_CHASSIS = BigInteger.valueOf(1).shiftLeft(40);
    public static final BigInteger F_TRACTOR_MODIFICATION = BigInteger.valueOf(1).shiftLeft(41);
    public static final BigInteger F_ACTUATOR_ENHANCEMENT_SYSTEM = BigInteger.valueOf(1).shiftLeft(42);
    public static final BigInteger F_ECM = BigInteger.valueOf(1).shiftLeft(43);
    public static final BigInteger F_BAP = BigInteger.valueOf(1).shiftLeft(44);
    public static final BigInteger F_MODULAR_ARMOR = BigInteger.valueOf(1).shiftLeft(45);
    public static final BigInteger F_TALON = BigInteger.valueOf(1).shiftLeft(46);
    public static final BigInteger F_VISUAL_CAMO = BigInteger.valueOf(1).shiftLeft(47);
    public static final BigInteger F_APOLLO = BigInteger.valueOf(1).shiftLeft(48);
    public static final BigInteger F_INDUSTRIAL_TSM = BigInteger.valueOf(1).shiftLeft(49);
    public static final BigInteger F_NULLSIG = BigInteger.valueOf(1).shiftLeft(50);
    public static final BigInteger F_VOIDSIG = BigInteger.valueOf(1).shiftLeft(51);
    public static final BigInteger F_CHAMELEON_SHIELD = BigInteger.valueOf(1).shiftLeft(52);
    public static final BigInteger F_VIBROCLAW = BigInteger.valueOf(1).shiftLeft(53);
    public static final BigInteger F_SINGLE_HEX_ECM = BigInteger.valueOf(1).shiftLeft(54);
    public static final BigInteger F_EJECTION_SEAT = BigInteger.valueOf(1).shiftLeft(55);
    public static final BigInteger F_SALVAGE_ARM = BigInteger.valueOf(1).shiftLeft(56);
    public static final BigInteger F_PARTIAL_WING = BigInteger.valueOf(1).shiftLeft(57);
    public static final BigInteger F_FERRO_LAMELLOR = BigInteger.valueOf(1).shiftLeft(58);
    public static final BigInteger F_ARTEMIS_V = BigInteger.valueOf(1).shiftLeft(59);
    // TODO: Implement me, so far only construction data
    public static final BigInteger F_TRACKS = BigInteger.valueOf(1).shiftLeft(60);
    // TODO: Implement me, so far only construction data
    public static final BigInteger F_MASS = BigInteger.valueOf(1).shiftLeft(61);
    // TODO: Implement me, so far only construction data
    public static final BigInteger F_CARGO = BigInteger.valueOf(1).shiftLeft(62);
    // TODO: Implement me, so far only construction data
    public static final BigInteger F_DUMPER = BigInteger.valueOf(1).shiftLeft(63);
    // TODO: Implement me, so far only construction data
    public static final BigInteger F_MASH = BigInteger.valueOf(1).shiftLeft(64);
    public static final BigInteger F_BA_EQUIPMENT = BigInteger.valueOf(1).shiftLeft(65);
    public static final BigInteger F_MECH_EQUIPMENT = BigInteger.valueOf(1).shiftLeft(66);
    public static final BigInteger F_TANK_EQUIPMENT = BigInteger.valueOf(1).shiftLeft(67);
    public static final BigInteger F_AERO_EQUIPMENT = BigInteger.valueOf(1).shiftLeft(68);
    public static final BigInteger F_SUPPORT_TANK_EQUIPMENT = BigInteger.valueOf(1).shiftLeft(69);
    public static final BigInteger F_PROTOMECH_EQUIPMENT = BigInteger.valueOf(1).shiftLeft(70);
    public static final BigInteger F_ARMORED_GLOVE = BigInteger.valueOf(1).shiftLeft(71);
    public static final BigInteger F_BASIC_MANIPULATOR = BigInteger.valueOf(1).shiftLeft(72);
    public static final BigInteger F_BATTLE_CLAW = BigInteger.valueOf(1).shiftLeft(73);
    public static final BigInteger F_AP_MOUNT = BigInteger.valueOf(1).shiftLeft(74);
    public static final BigInteger F_MAST_MOUNT = BigInteger.valueOf(1).shiftLeft(75);
    public static final BigInteger F_FUEL = BigInteger.valueOf(1).shiftLeft(76);
    public static final BigInteger F_BLUE_SHIELD = BigInteger.valueOf(1).shiftLeft(77);
    public static final BigInteger F_BASIC_FIRECONTROL = BigInteger.valueOf(1).shiftLeft(78);
    public static final BigInteger F_ADVANCED_FIRECONTROL = BigInteger.valueOf(1).shiftLeft(79);
    public static final BigInteger F_ENDO_COMPOSITE = BigInteger.valueOf(1).shiftLeft(80);
    public static final BigInteger F_LASER_INSULATOR = BigInteger.valueOf(1).shiftLeft(81);
    public static final BigInteger F_LIQUID_CARGO = BigInteger.valueOf(1).shiftLeft(82);
    public static final BigInteger F_WATCHDOG = BigInteger.valueOf(1).shiftLeft(83);
    public static final BigInteger F_EW_EQUIPMENT = BigInteger.valueOf(1).shiftLeft(84);
    public static final BigInteger F_CCM = BigInteger.valueOf(1).shiftLeft(85);
    public static final BigInteger F_HITCH = BigInteger.valueOf(1).shiftLeft(86);
    public static final BigInteger F_FLOTATION_HULL = BigInteger.valueOf(1).shiftLeft(87);
    public static final BigInteger F_LIMITED_AMPHIBIOUS = BigInteger.valueOf(1).shiftLeft(88);
    public static final BigInteger F_FULLY_AMPHIBIOUS = BigInteger.valueOf(1).shiftLeft(89);
    public static final BigInteger F_DUNE_BUGGY = BigInteger.valueOf(1).shiftLeft(90);
    public static final BigInteger F_SHOULDER_TURRET = BigInteger.valueOf(1).shiftLeft(91);
    public static final BigInteger F_HEAD_TURRET = BigInteger.valueOf(1).shiftLeft(92);
    public static final BigInteger F_QUAD_TURRET = BigInteger.valueOf(1).shiftLeft(93);
    public static final BigInteger F_SPACE_ADAPTATION = BigInteger.valueOf(1).shiftLeft(94);
    public static final BigInteger F_CUTTING_TORCH = BigInteger.valueOf(1).shiftLeft(95);
    public static final BigInteger F_OFF_ROAD = BigInteger.valueOf(1).shiftLeft(96);
    public static final BigInteger F_C3SBS = BigInteger.valueOf(1).shiftLeft(97);
    public static final BigInteger F_VTOL_EQUIPMENT = BigInteger.valueOf(1).shiftLeft(98);
    public static final BigInteger F_NAVAL_C3 = BigInteger.valueOf(1).shiftLeft(99);
    public static final BigInteger F_MINESWEEPER = BigInteger.valueOf(1).shiftLeft(100);
    public static final BigInteger F_MOBILE_HPG = BigInteger.valueOf(1).shiftLeft(101);
    public static final BigInteger F_FIELD_KITCHEN = BigInteger.valueOf(1).shiftLeft(102);
    public static final BigInteger F_MOBILE_FIELD_BASE = BigInteger.valueOf(1).shiftLeft(103);
    // TODO: add game rules for the following imagers/radars, construction data
    // only
    public static final BigInteger F_HIRES_IMAGER = BigInteger.valueOf(1).shiftLeft(104);
    public static final BigInteger F_HYPERSPECTRAL_IMAGER = BigInteger.valueOf(1).shiftLeft(105);
    public static final BigInteger F_INFRARED_IMAGER = BigInteger.valueOf(1).shiftLeft(106);
    public static final BigInteger F_LOOKDOWN_RADAR = BigInteger.valueOf(1).shiftLeft(107);

    public static final BigInteger F_COMMAND_CONSOLE = BigInteger.valueOf(1).shiftLeft(108);
    public static final BigInteger F_VSTOL_CHASSIS = BigInteger.valueOf(1).shiftLeft(109);
    public static final BigInteger F_STOL_CHASSIS = BigInteger.valueOf(1).shiftLeft(110);
    public static final BigInteger F_SPONSON_TURRET = BigInteger.valueOf(1).shiftLeft(111);
    public static final BigInteger F_ARMORED_MOTIVE_SYSTEM = BigInteger.valueOf(1).shiftLeft(112);
    public static final BigInteger F_CHASSIS_MODIFICATION = BigInteger.valueOf(1).shiftLeft(113);
    public static final BigInteger F_CHAFF_POD = BigInteger.valueOf(1).shiftLeft(114);
    public static final BigInteger F_DRONE_CARRIER_CONTROL = BigInteger.valueOf(1).shiftLeft(115);
    public static final BigInteger F_DRONE_EXTRA = BigInteger.valueOf(1).shiftLeft(116);
    public static final BigInteger F_MASH_EXTRA = BigInteger.valueOf(1).shiftLeft(117);
    public static final BigInteger F_JET_BOOSTER = BigInteger.valueOf(1).shiftLeft(118);
    public static final BigInteger F_SENSOR_DISPENSER = BigInteger.valueOf(1).shiftLeft(119);
    public static final BigInteger F_DRONE_OPERATING_SYSTEM = BigInteger.valueOf(1).shiftLeft(120);
    public static final BigInteger F_RECON_CAMERA = BigInteger.valueOf(1).shiftLeft(121);
    public static final BigInteger F_COMBAT_VEHICLE_ESCAPE_POD = BigInteger.valueOf(1).shiftLeft(122);
    public static final BigInteger F_DETACHABLE_WEAPON_PACK = BigInteger.valueOf(1).shiftLeft(123);
    public static final BigInteger F_HEAT_SENSOR = BigInteger.valueOf(1).shiftLeft(124);
    public static final BigInteger F_EXTENDED_LIFESUPPORT = BigInteger.valueOf(1).shiftLeft(125);
    public static final BigInteger F_SPRAYER = BigInteger.valueOf(1).shiftLeft(126);
    public static final BigInteger F_ELECTRIC_DISCHARGE_ARMOR = BigInteger.valueOf(1).shiftLeft(127);
    public static final BigInteger F_MECHANICAL_JUMP_BOOSTER = BigInteger.valueOf(1).shiftLeft(128);
    public static final BigInteger F_TRAILER_MODIFICATION = BigInteger.valueOf(1).shiftLeft(129);
    public static final BigInteger F_LARGE_COMM_SCANNER_SUITE = BigInteger.valueOf(1).shiftLeft(130);
    public static final BigInteger F_SMALL_COMM_SCANNER_SUITE = BigInteger.valueOf(1).shiftLeft(131);
    public static final BigInteger F_LIGHT_BRIDGE_LAYER = BigInteger.valueOf(1).shiftLeft(132);
    public static final BigInteger F_MEDIUM_BRIDGE_LAYER = BigInteger.valueOf(1).shiftLeft(133);
    public static final BigInteger F_HEAVY_BRIDGE_LAYER = BigInteger.valueOf(1).shiftLeft(134);
    public static final BigInteger F_BA_SEARCHLIGHT = BigInteger.valueOf(1).shiftLeft(135);
    public static final BigInteger F_BOOBY_TRAP = BigInteger.valueOf(1).shiftLeft(136);
    public static final BigInteger F_SPLITABLE = BigInteger.valueOf(1).shiftLeft(137);
    public static final BigInteger F_REFUELING_DROGUE = BigInteger.valueOf(1).shiftLeft(138);
    public static final BigInteger F_BULLDOZER = BigInteger.valueOf(1).shiftLeft(139);
    public static final BigInteger F_EXTERNAL_STORES_HARDPOINT = BigInteger.valueOf(1).shiftLeft(140);
    public static final BigInteger F_COMPACT_HEAT_SINK = BigInteger.valueOf(1).shiftLeft(141);
    public static final BigInteger F_MANIPULATOR = BigInteger.valueOf(1).shiftLeft(142);
    public static final BigInteger F_CARGOLIFTER = BigInteger.valueOf(1).shiftLeft(143);
    public static final BigInteger F_PINTLE_TURRET = BigInteger.valueOf(1).shiftLeft(144);
    public static final BigInteger F_IS_DOUBLE_HEAT_SINK_PROTOTYPE = BigInteger.valueOf(1).shiftLeft(145);
    public static final BigInteger F_NAVAL_TUG_ADAPTOR = BigInteger.valueOf(1).shiftLeft(146);
    public static final BigInteger F_AMPHIBIOUS = BigInteger.valueOf(1).shiftLeft(147);
    public static final BigInteger F_PROP = BigInteger.valueOf(1).shiftLeft(148);
    public static final BigInteger F_ULTRA_LIGHT = BigInteger.valueOf(1).shiftLeft(149);


    // Secondary Flags for Physical Weapons
    public static final long S_CLUB = 1L << 0; // BMR
    public static final long S_TREE_CLUB = 1L << 1;// BMR
    public static final long S_HATCHET = 1L << 2; // BMR
    public static final long S_SWORD = 1L << 3; // BMR
    public static final long S_MACE_THB = 1L << 4;// Tac Handbook version
    public static final long S_CLAW_THB = 1L << 5; // Not used yet, but...
    // Hey, it's all for
    // fun.
    public static final long S_MACE = 1L << 6;
    public static final long S_DUAL_SAW = 1L << 7;
    public static final long S_FLAIL = 1L << 8;
    public static final long S_PILE_DRIVER = 1L << 9;
    public static final long S_SHIELD_SMALL = 1L << 10;
    public static final long S_SHIELD_MEDIUM = 1L << 11;
    public static final long S_SHIELD_LARGE = 1L << 12;
    public static final long S_LANCE = 1L << 13;
    public static final long S_VIBRO_SMALL = 1L << 14;
    public static final long S_VIBRO_MEDIUM = 1L << 15;
    public static final long S_VIBRO_LARGE = 1L << 16;
    public static final long S_WRECKING_BALL = 1L << 17;
    public static final long S_BACKHOE = 1L << 18;
    public static final long S_COMBINE = 1L << 19; // TODO
    public static final long S_CHAINSAW = 1L << 20;
    public static final long S_ROCK_CUTTER = 1L << 21;
    // TODO
    public static final long S_BUZZSAW = 1L << 22; // Unbound;
    public static final long S_RETRACTABLE_BLADE = 1L << 23;
    public static final long S_CHAIN_WHIP = 1L << 24;
    public static final long S_SPOT_WELDER = 1L << 25; // TODO: add game rules
    public static final long S_MINING_DRILL = 1L << 26; // Miniatures

    public static final String S_ACTIVE_SHIELD = "Active";
    public static final String S_PASSIVE_SHIELD = "Passive";
    public static final String S_NO_SHIELD = "None";

    // Secondary damage for hand weapons.
    // These are differentiated from Physical Weapons using the F_CLUB flag
    // because the following weapons are treated as a punch attack, while
    // the above weapons are treated as club or hatchet attacks.
    // these are subtypes of F_HAND_WEAPON
    public static final long S_CLAW = 1L << 0; // Solaris 7
    // Rulebook; TODO

    // Secondary flags for infantry tools
    public static final long S_VIBROSHOVEL = 1L << 0; // can fortify hexes
    public static final long S_DEMOLITION_CHARGE = 1L << 1; // can demolish
    // buildings
    public static final long S_BRIDGE_KIT = 1L << 2; // can build a bridge
    public static final long S_MINESWEEPER = 1L << 3; // can clear mines
    public static final long S_HEAVY_ARMOR = 1L << 4;

    // Secondary flags for MASC
    public static final long S_SUPERCHARGER = 1L << 0;
    // this kind of works like MASC for the double cruise MP, so we will make it
    // a subtype
    public static final long S_JETBOOSTER = 1L << 1;

    // Secondary flags for Jump Jets
    public static final long S_STANDARD = 1L << 0;
    public static final long S_IMPROVED = 1L << 1;
    public static final long S_PROTOTYPE = 1L << 2;

    // New stuff for shields
    protected int baseDamageAbsorptionRate = 0;
    protected int baseDamageCapacity = 0;
    protected int damageTaken = 0;

    private boolean industrial = false;

    /** Creates new MiscType */
    public MiscType() {
    }

    public boolean isShield() {
        if (hasFlag(MiscType.F_CLUB) && (hasSubType(MiscType.S_SHIELD_LARGE) || hasSubType((MiscType.S_SHIELD_MEDIUM)) || hasSubType(MiscType.S_SHIELD_SMALL))) {
            return true;
        }
        // else
        return false;
    }

    public boolean isVibroblade() {
        if (hasFlag(MiscType.F_CLUB) && (hasSubType(MiscType.S_VIBRO_LARGE) || hasSubType((MiscType.S_VIBRO_MEDIUM)) || hasSubType(MiscType.S_VIBRO_SMALL))) {
            return true;
        }
        // else
        return false;
    }

    public boolean isIndustrial() {
        return industrial;
    }

    @Override
    public float getTonnage(Entity entity) {
        return getTonnage(entity, Entity.LOC_NONE);
    }

    public float getTonnage(Entity entity, int location) {

        if (tonnage != TONNAGE_VARIABLE) {
            return tonnage;
        }
        // check for known formulas
        if (hasFlag(F_JUMP_JET)) {
            if (hasSubType(S_IMPROVED)) {
                if (entity.getWeight() <= 55.0) {
                    return 1.0f;
                } else if (entity.getWeight() <= 85.0) {
                    return 2.0f;
                } else {
                    return 4.0f;
                }
            }
            if (entity.getWeight() <= 55.0) {
                return 0.5f;
            } else if (entity.getWeight() <= 85.0) {
                return 1.0f;
            } else {
                return 2.0f;
            }
        } else if (hasFlag(F_UMU)) {
            if (entity.getWeight() <= 55.0) {
                return 0.5f;
            } else if (entity.getWeight() <= 85.0) {
                return 1.0f;
            } else {
                return 2.0f;
            }
        } else if (hasFlag(F_PARTIAL_WING) && hasFlag(F_MECH_EQUIPMENT)) {
            if (getTechLevel() == TechConstants.T_CLAN_EXPERIMENTAL) {
                return (float) (Math.ceil((entity.getWeight() / 20.0) * 2.0) / 2.0);
            } else if (getTechLevel() == TechConstants.T_IS_EXPERIMENTAL) {
                return (float) (Math.ceil(entity.getWeight() * 0.14) / 2.0);
            }
        } else if (hasFlag(F_PARTIAL_WING) && hasFlag(F_PROTOMECH_EQUIPMENT)) {
            return (float) (Math.ceil((entity.getWeight() / 5.0) * 2.0) / 2.0);
        } else if (hasFlag(F_CLUB) && (hasSubType(S_HATCHET) || hasSubType(S_MACE_THB))) {
            return (float) Math.ceil(entity.getWeight() / 15.0);
        } else if (hasFlag(F_CLUB) && hasSubType(S_LANCE)) {
            return (float) Math.ceil(entity.getWeight() / 20.0);
        } else if (hasFlag(F_CLUB) && hasSubType(S_SWORD)) {
            return (float) (Math.ceil((entity.getWeight() / 20.0) * 2.0) / 2.0);
        } else if (hasFlag(F_CLUB) && hasSubType(S_MACE)) {
            return (float) (Math.ceil(entity.getWeight() / 10.0));
        } else if (hasFlag(F_CLUB) && hasSubType(S_RETRACTABLE_BLADE)) {
            return 0.5f + ((float) Math.ceil(entity.getWeight() / 10.0) / 2);
        } else if (hasFlag(F_MASC)) {
            if (entity instanceof Protomech) {
                return entity.getWeight() * 0.025f;
            } else {
                if (hasSubType(S_JETBOOSTER)) {
                    return entity.getEngine().getWeightEngine(entity) / 10;
                }
                if (hasSubType(S_SUPERCHARGER)) {
                    Engine e = entity.getEngine();
                    if (e == null) {
                        return 0.0f;
                    }
                    return (float) (Math.ceil((e.getWeightEngine(entity) / 10.0) * 2.0) / 2.0);
                }
                if (entity.isClan()) {
                    return Math.round(entity.getWeight() / 25.0f);
                }
                return Math.round(entity.getWeight() / 20.0f);
            }
        } else if (hasFlag(F_QUAD_TURRET) || hasFlag(F_SHOULDER_TURRET) || hasFlag(F_HEAD_TURRET)) {
            int locationToCheck = location;
            if (hasFlag(F_HEAD_TURRET)) {
                locationToCheck = Mech.LOC_HEAD;
            }
            // 10% of linked weapons' weight
            float weaponWeight = 0;
            for (Mounted m : entity.getWeaponList()) {
                if ((m.getLocation() == locationToCheck) && m.isMechTurretMounted()) {
                    weaponWeight += m.getType().getTonnage(entity);
                }
            }
            // round to half ton
            weaponWeight /= 10;
            return (float) (Math.ceil(weaponWeight * 2.0)) / 2.0f;
        } else if (hasFlag(F_SPONSON_TURRET)) {
            float weaponWeight = 0;
            // 10% of linked weapons' weight
            for (Mounted m : entity.getWeaponList()) {
                if ((m.isSponsonTurretMounted() && ((m.getLocation() == Tank.LOC_LEFT) || (m.getLocation() == Tank.LOC_RIGHT)))) {
                    weaponWeight += m.getType().getTonnage(entity);
                }
            }
            // round to half ton
            weaponWeight /= 10;
            return (float) (Math.ceil(weaponWeight * 2.0)) / 2.0f;
        } else if (hasFlag(F_PINTLE_TURRET)) {
            float weaponWeight = 0;
            // 5% of linked weapons' weight
            for (Mounted m : entity.getWeaponList()) {
                if (m.isSponsonTurretMounted() && (m.getLocation() == location)) {
                    weaponWeight += m.getType().getTonnage(entity);
                }
            }
            // TODO: round to kilogram
            weaponWeight /= 20;
            return weaponWeight;
        }
        else if (hasFlag(F_ARMORED_MOTIVE_SYSTEM)) {
            if (TechConstants.isClan(getTechLevel())) {
                return (float) (entity.getWeight() * 0.1);
            } else {
                return (float) (entity.getWeight() * 0.15);
            }
        } else if (hasFlag(F_TARGCOMP)) {
            // based on tonnage of direct_fire weaponry
            double fTons = 0.0;
            for (Mounted m : entity.getWeaponList()) {
                WeaponType wt = (WeaponType) m.getType();
                if (wt.hasFlag(WeaponType.F_DIRECT_FIRE)) {
                    fTons += wt.getTonnage(entity);
                }
            }
            if (TechConstants.isClan(getTechLevel())) {
                return (float) Math.ceil(fTons / 5.0f);
            }
            return (float) Math.ceil(fTons / 4.0f);
        } else if (EquipmentType.getArmorTypeName(T_ARMOR_FERRO_FIBROUS).equals(internalName)) {
            double tons = 0.0;
            if (!entity.hasPatchworkArmor()) {
                if (entity.isClanArmor(1)) {
                    tons = entity.getTotalOArmor() / (16 * 1.2);
                } else {
                    tons = entity.getTotalOArmor() / (16 * 1.12);
                }
                tons = Math.ceil(tons * 2.0) / 2.0;
            } else {
                // TODO
            }
            return (float) tons;
        } else if (EquipmentType.getArmorTypeName(T_ARMOR_LIGHT_FERRO).equals(internalName)) {
            double tons = 0;
            if (!entity.hasPatchworkArmor()) {
                tons = entity.getTotalOArmor() / (16 * 1.06);
                tons = Math.ceil(tons * 2.0) / 2.0;
            } else {
                // TODO
            }
            return (float) tons;
        } else if (EquipmentType.getArmorTypeName(T_ARMOR_HEAVY_FERRO).equals(internalName)) {
            double tons = 0;
            if (!entity.hasPatchworkArmor()) {
                tons = entity.getTotalOArmor() / (16 * 1.24);
                tons = Math.ceil(tons * 2.0) / 2.0;
            } else {
                // TODO
            }
            return (float) tons;
        } else if (EquipmentType.getArmorTypeName(T_ARMOR_FERRO_LAMELLOR).equals(internalName)) {
            double tons = 0;
            if (!entity.hasPatchworkArmor()) {
                tons = entity.getTotalOArmor() / (16 * 0.875);
                tons = Math.ceil(tons * 2.0) / 2.0;
            } else {
                // TODO
            }
            return (float) tons;
        } else if (EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_STEEL).equals(internalName) || hasFlag(F_ENDO_STEEL)) {
            double tons = 0.0;
            tons = Math.ceil(entity.getWeight() / 10.0) / 2.0;
            return (float) tons;
        } else if (EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_PROTOTYPE).equals(internalName)) {
            double tons = 0.0;
            tons = Math.ceil(entity.getWeight() / 10.0) / 2.0;
            return (float) tons;
        } else if (EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_COMPOSITE).equals(internalName) || hasFlag(F_ENDO_COMPOSITE)) {
            double tons = 0.0;
            tons = entity.getWeight() / 10.0;
            tons = Math.ceil(tons * 1.5) / 2.0;
            return (float) tons;
        } else if (EquipmentType.getStructureTypeName(T_STRUCTURE_REINFORCED).equals(internalName)) {
            double tons = 0.0;
            tons = Math.ceil(entity.getWeight() / 10.0) * 2.0;
            return (float) tons;
        } else if (EquipmentType.getStructureTypeName(T_STRUCTURE_COMPOSITE).equals(internalName)) {
            double tons = 0.0;
            tons = Math.ceil(entity.getWeight() / 10.0) / 2.0;
            return (float) tons;
        } else if (EquipmentType.getStructureTypeName(T_STRUCTURE_INDUSTRIAL).equals(internalName)) {
            double tons = 0.0;
            tons = Math.ceil(entity.getWeight() / 10.0) * 2.0;
            return (float) tons;
        } else if (hasFlag(F_VACUUM_PROTECTION)) {
            return (float) Math.ceil(entity.getWeight() / 10.0);
        } else if (hasFlag(F_ENVIRONMENTAL_SEALING) || hasFlag(F_DUNE_BUGGY)) {
            return entity.getWeight() / 10.0f;
        } else if (hasFlag(F_JUMP_BOOSTER)) {
            return (float) (Math.ceil((entity.getWeight() * entity.getOriginalJumpMP()) / 10.0) / 2.0);
        } else if ((hasFlag(F_HAND_WEAPON) && hasSubType(S_CLAW)) || hasFlag(F_TALON)) {
            return (int) Math.ceil(entity.getWeight() / 15);
        } else if (hasFlag(F_ACTUATOR_ENHANCEMENT_SYSTEM)) {

            float tonnage = 0;
            if (entity instanceof BipedMech) {
                tonnage = entity.getWeight() / 35;
            } else {
                tonnage = entity.getWeight() / 50;
            }

            if (tonnage == Math.round(tonnage)) {
                return tonnage;
            }

            if (Math.floor(tonnage) < Math.round(tonnage)) {
                return Math.round(tonnage);
            }

            return (float) (Math.floor(tonnage) + 0.5);
        } else if (hasFlag(F_TRACKS)) {
            return entity.getWeight() / 10;
        } else if (hasFlag(F_LIMITED_AMPHIBIOUS)) {
            return (float) (Math.ceil((entity.getWeight() / 25) * 2) / 2.0);
        } else if (hasFlag(F_FULLY_AMPHIBIOUS)) {
            return (float) (Math.ceil((entity.getWeight() / 10) * 2) / 2.0);
        } else if (hasFlag(F_DUMPER)) {
            // 5% of cargo
            float cargoTonnage = 0;
            for (Mounted mount : entity.getMisc()) {
                if (mount.getType().hasFlag(F_CARGO)) {
                    cargoTonnage += mount.getType().getTonnage(entity);
                }
            }
            // round to half ton TODO: round to kilograms for small support
            // vees, but we don't support them yet
            return (float) (Math.ceil(cargoTonnage / 40) * 2.0);
        } else if (hasFlag(F_BASIC_FIRECONTROL)) {
            // 5% of weapon weight
            float weaponWeight = 0;
            for (Mounted mount : entity.getWeaponList()) {
                weaponWeight += mount.getType().getTonnage(entity);
            }
            // round to half ton TODO: round to kilograms for small support
            // vees, but we don't support them yet
            return (float) (Math.ceil(weaponWeight / 40) * 2.0);
        } else if (hasFlag(F_ADVANCED_FIRECONTROL)) {
            // 10% of weapon weight
            float weaponWeight = 0;
            for (Mounted mount : entity.getWeaponList()) {
                weaponWeight += mount.getType().getTonnage(entity);
            }
            // round to half ton TODO: round to kilograms for small support
            // vees, but we don't support them yet
            return (float) (Math.ceil(weaponWeight / 20) * 2.0);
        } else if (hasFlag(F_BOOBY_TRAP)) {
            // 10% of unit weight
            // round to half ton TODO: round to kilograms for small support
            // vees, but we don't support them yet
            float weight = entity.getWeight() / 10;
            return (float) (Math.ceil(weight * 2.0)) / 2.0f;
        } else if (hasFlag(F_DRONE_CARRIER_CONTROL)) {
            float weight = 2;
            for (Mounted mount : entity.getMisc()) {
                if (mount.getType().hasFlag(MiscType.F_DRONE_EXTRA)) {
                    weight += 0.5;
                }
            }
            return weight;
        } else if (hasFlag(MiscType.F_DRONE_OPERATING_SYSTEM)) {
            // 10% of the weight, plus 0.5 tons for the extra sensors
            return (entity.getWeight() / 10) + 0.5f;
        } else if (hasFlag(MiscType.F_NAVAL_TUG_ADAPTOR)) {
            return (100 + (entity.getWeight() / 10.0f));
        }
        // okay, I'm out of ideas
        return 1.0f;
    }

    @Override
    public double getCost(Entity entity, boolean isArmored, int loc) {
        double costValue = cost;
        if (costValue == EquipmentType.COST_VARIABLE) {
            if (hasFlag(F_DRONE_CARRIER_CONTROL)) {
                costValue = getTonnage(entity, loc) * 10000;
            } else if (hasFlag(F_FLOTATION_HULL) || hasFlag(F_VACUUM_PROTECTION) || hasFlag(F_ENVIRONMENTAL_SEALING) || hasFlag(F_OFF_ROAD)) {
                costValue = 0;
            } else if (hasFlag(F_LIMITED_AMPHIBIOUS) || hasFlag((F_FULLY_AMPHIBIOUS))) {
                costValue = getTonnage(entity, loc) * 10000;
            } else if (hasFlag(F_DUNE_BUGGY)) {
                float totalTons = getTonnage(entity, loc);
                costValue = 10 * totalTons * totalTons;
            } else if (hasFlag(F_MASC) && hasFlag(F_BA_EQUIPMENT)) {
                costValue = entity.getRunMP() * 75000;
            } else if (hasFlag(F_HEAD_TURRET) || hasFlag(F_SHOULDER_TURRET) || hasFlag(F_QUAD_TURRET)) {
                costValue = getTonnage(entity, loc) * 10000;
            } else if (hasFlag(F_SPONSON_TURRET)) {
                costValue = getTonnage(entity, loc) * 4000;
            } else if (hasFlag(F_PINTLE_TURRET)) {
                costValue = getTonnage(entity, loc) * 1000;
            } else if (hasFlag(F_ARMORED_MOTIVE_SYSTEM)) {
                costValue = getTonnage(entity, loc) * 100000;
            } else if (hasFlag(F_JET_BOOSTER)) {
                costValue = entity.getEngine().getRating() * 10000;
            } else if (hasFlag(F_DRONE_OPERATING_SYSTEM)) {
                costValue = (getTonnage(entity, loc) * 10000) + 5000;
            } else if (hasFlag(MiscType.F_MASC)) {
                if (entity instanceof Protomech) {
                    costValue = Math.round(entity.getEngine().getRating() * 1000 * entity.getWeight() * 0.025f);
                } else if (hasSubType(MiscType.S_SUPERCHARGER)) {
                    Engine e = entity.getEngine();
                    if (e == null) {
                        costValue = 0;
                    } else {
                        costValue = e.getRating() * 10000;
                    }
                } else {
                    int mascTonnage = 0;
                    if (getInternalName().equals("ISMASC")) {
                        mascTonnage = Math.round(entity.getWeight() / 20.0f);
                    } else if (getInternalName().equals("CLMASC")) {
                        mascTonnage = Math.round(entity.getWeight() / 25.0f);
                    }
                    costValue = entity.getEngine().getRating() * mascTonnage * 1000;
                }
            } else if (hasFlag(MiscType.F_TARGCOMP)) {
                int tCompTons = 0;
                float fTons = 0.0f;
                for (Mounted mo : entity.getWeaponList()) {
                    WeaponType wt = (WeaponType) mo.getType();
                    if (wt.hasFlag(WeaponType.F_DIRECT_FIRE)) {
                        fTons += wt.getTonnage(entity);
                    }
                }
                if (getInternalName().equals("ISTargeting Computer")) {
                    tCompTons = (int) Math.ceil(fTons / 4.0f);
                } else if (getInternalName().equals("CLTargeting Computer")) {
                    tCompTons = (int) Math.ceil(fTons / 5.0f);
                }
                costValue = tCompTons * 10000;
            } else if (hasFlag(MiscType.F_CLUB) && (hasSubType(MiscType.S_HATCHET) || hasSubType(MiscType.S_MACE_THB))) {
                int hatchetTons = (int) Math.ceil(entity.getWeight() / 15.0);
                costValue = hatchetTons * 5000;
            } else if (hasFlag(MiscType.F_CLUB) && hasSubType(MiscType.S_SWORD)) {
                float swordTons = (float) (Math.ceil((entity.getWeight() / 20.0) * 2.0) / 2.0);
                costValue = swordTons * 10000;
            } else if (hasFlag(MiscType.F_CLUB) && hasSubType(MiscType.S_RETRACTABLE_BLADE)) {
                int bladeTons = (int) Math.ceil(0.5f + Math.ceil(entity.getWeight() / 20.0));
                costValue = (1 + bladeTons) * 10000;
            } else if (hasFlag(MiscType.F_TRACKS)) {
                costValue = (int) Math.ceil((500 * entity.getEngine().getRating() * entity.getWeight()) / 75);
            } else if (hasFlag(MiscType.F_TALON)) {
                costValue = (int) Math.ceil(getTonnage(entity, loc) * 300);
            } else if (hasFlag(MiscType.F_SPIKES)) {
                costValue = (int) Math.ceil(entity.getWeight() * 50);
            } else if (hasFlag(MiscType.F_PARTIAL_WING)) {
                costValue = (int) Math.ceil(getTonnage(entity, loc) * 50000);
            } else if (hasFlag(MiscType.F_ACTUATOR_ENHANCEMENT_SYSTEM)) {
                int multiplier = entity.locationIsLeg(loc) ? 700 : 500;
                costValue = (int) Math.ceil(entity.getWeight() * multiplier);
            } else if (hasFlag(MiscType.F_HAND_WEAPON) && (hasSubType(MiscType.S_CLAW))) {
                costValue = (int) Math.ceil(entity.getWeight() * 200);
            } else if (hasFlag(MiscType.F_CLUB) && (hasSubType(MiscType.S_LANCE))) {
                costValue = (int) Math.ceil(entity.getWeight() * 150);
            }
            if (isArmored) {
                double armoredCost = costValue;

                armoredCost += 150000 * getCriticals(entity);

                return armoredCost;
            }
        }
        return costValue;
    }

    @Override
    public int getCriticals(Entity entity) {
        if (criticals != CRITICALS_VARIABLE) {
            return criticals;
        }
        // check for known formulas
        if (hasFlag(F_CLUB) && (hasSubType(S_HATCHET) || hasSubType(S_SWORD) || hasSubType(S_MACE_THB))) {
            return (int) Math.ceil(entity.getWeight() / 15.0);
        } else if (hasFlag(F_CLUB) && hasSubType(S_LANCE)) {
            return (int) Math.ceil(entity.getWeight() / 20.0);
        } else if (hasFlag(F_CLUB) && hasSubType(S_MACE)) {
            return (int) Math.ceil(entity.getWeight() / 10.0);
        } else if (hasFlag(F_CLUB) && hasSubType(S_RETRACTABLE_BLADE)) {
            return 1 + (int) Math.ceil(entity.getWeight() / 20.0);
        } else if (hasFlag(F_MASC)) {
            if (entity.isClan()) {
                return (int) Math.round(entity.getWeight() / 25.0);
            }
            return (int) Math.round(entity.getWeight() / 20.0);
        } else if (hasFlag(F_TARGCOMP)) {
            // based on tonnage of direct_fire weaponry
            double fTons = 0.0;
            for (Mounted m : entity.getWeaponList()) {
                WeaponType wt = (WeaponType) m.getType();
                if (wt.hasFlag(WeaponType.F_DIRECT_FIRE)) {
                    fTons += wt.getTonnage(entity);
                }
            }
            if (TechConstants.isClan(getTechLevel())) {
                return (int) Math.ceil(fTons / 5.0f);
            }
            return (int) Math.ceil(fTons / 4.0f);
        } else if (EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_FIBROUS).equals(internalName) || EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_REACTIVE).equals(internalName)) {
            if (entity.isClanArmor(1) && !entity.hasPatchworkArmor()) {
                return 7;
            } else if (entity.hasPatchworkArmor()) {
                int slots = 0;
                for (int i = 0; i < entity.locations(); i++) {
                    if ((entity.getArmorType(i) == EquipmentType.T_ARMOR_FERRO_FIBROUS) || (entity.getArmorType(i) == EquipmentType.T_ARMOR_REACTIVE)) {
                        if (TechConstants.isClan(entity.getArmorTechLevel(i))) {
                            slots++;
                        } else {
                            slots += 2;
                        }
                    }
                }
                return slots;
            } else {
                return 14;
            }
        } else if (EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_REFLECTIVE).equals(internalName)) {
            if (entity.isClanArmor(1) && !entity.hasPatchworkArmor()) {
                return 5;
            } else if (entity.hasPatchworkArmor()) {
                int slots = 0;
                for (int i = 0; i < entity.locations(); i++) {
                    if (entity.getArmorType(i) == EquipmentType.T_ARMOR_REFLECTIVE) {
                        if (TechConstants.isClan(entity.getArmorTechLevel(i))) {
                            slots++;
                        } else {
                            slots += 2;
                        }
                    }
                }
                return slots;
            }
            return 10;
        } else if (EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_LIGHT_FERRO).equals(internalName)) {
            if (!entity.hasPatchworkArmor()) {
                return 7;
            } else {
                int slots = 0;
                for (int i = 0; i < entity.locations(); i++) {
                    if (entity.getArmorType(i) == EquipmentType.T_ARMOR_LIGHT_FERRO) {
                        slots++;
                    }
                }
                return slots;
            }
        } else if (EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_HEAVY_FERRO).equals(internalName)) {
            if (!entity.hasPatchworkArmor()) {
                return 21;
            } else {
                int slots = 0;
                for (int i = 0; i < entity.locations(); i++) {
                    if (entity.getArmorType(i) == EquipmentType.T_ARMOR_HEAVY_FERRO) {
                        slots += 3;
                    }
                }
                return slots;
            }
        } else if (EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_LAMELLOR).equals(internalName)) {
            if (!entity.hasPatchworkArmor()) {
                return 12;
            } else {
                int slots = 0;
                for (int i = 0; i < entity.locations(); i++) {
                    if (entity.getArmorType(i) == EquipmentType.T_ARMOR_FERRO_LAMELLOR) {
                        slots += 2;
                    }
                }
                return slots;
            }
        } else if (EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_FIBROUS_PROTO).equals(internalName)) {
            if (!entity.hasPatchworkArmor()) {
                return 16;
            } else {
                int slots = 0;
                for (int i = 0; i < entity.locations(); i++) {
                    if (entity.getArmorType(i) == EquipmentType.T_ARMOR_FERRO_FIBROUS_PROTO) {
                        slots += 2;
                    }
                }
                return slots;
            }
        } else if (EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_STEEL).equals(internalName)) {
            if (entity.isClan()) {
                return 7;
            }
            return 14;
        } else if (EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_COMPOSITE).equals(internalName)) {
            if (entity.isClan()) {
                return 4;
            }
            return 7;
        } else if (hasFlag(F_JUMP_BOOSTER) || hasFlag(F_TALON)) {
            return (entity instanceof QuadMech) ? 8 : 4; // all slots in all
            // legs
        } else if (hasFlag(F_HAND_WEAPON) && hasSubType(S_CLAW)) {
            return (int) Math.ceil(entity.getWeight() / 15);
        } else if (hasFlag(F_ACTUATOR_ENHANCEMENT_SYSTEM)) {
            switch (entity.getWeightClass()) {
                case EntityWeightClass.WEIGHT_LIGHT:
                    return 1;
                case EntityWeightClass.WEIGHT_MEDIUM:
                    return 2;
                case EntityWeightClass.WEIGHT_HEAVY:
                    return 3;
                case EntityWeightClass.WEIGHT_ASSAULT:
                    return 4;
            }
            return entity.getWeightClass();
        } else if (hasFlag(F_TRACKS)) {
            if (entity instanceof QuadMech) {
                return 4;
            }
            if (entity instanceof BipedMech) {
                return 2;
            }
        }
        // right, well I'll just guess then
        return 1;
    }

    public double getBV(Entity entity, Mounted linkedTo) {

        if (hasFlag(F_PPC_CAPACITOR) && (linkedTo != null) && (linkedTo.getLinkedBy() != null)) {

            if (linkedTo.getType() instanceof ISLightPPC) {
                return 44;
            }

            if (linkedTo.getType() instanceof ISPPC) {
                return 88;
            }

            if (linkedTo.getType() instanceof ISHeavyPPC) {
                return 53;
            }

            if (linkedTo.getType() instanceof ISSnubNosePPC) {
                return 87;
            }

            if (linkedTo.getType() instanceof ISERPPC) {
                return 114;
            }
        }

        if (linkedTo != null) {
            return this.getBV(entity, linkedTo.getLocation());
        } else {
            return this.getBV(entity);
        }
    }

    @Override
    public double getBV(Entity entity) {
        return getBV(entity, Entity.LOC_NONE);
    }

    public double getBV(Entity entity, int location) {
        double returnBV = 0.0;
        if (bv != BV_VARIABLE) {
            returnBV = bv;
            // Mast Mounts give extra BV to equipment mounted in the mast
            if ((entity instanceof VTOL) && entity.hasWorkingMisc(MiscType.F_MAST_MOUNT, -1, VTOL.LOC_ROTOR) && (location == VTOL.LOC_ROTOR) && (hasFlag(MiscType.F_ECM) || hasFlag(MiscType.F_BAP) || hasFlag(MiscType.F_C3S) || hasFlag(MiscType.F_C3SBS) || hasFlag(MiscType.F_C3I))) {
                returnBV += 10;
            }
            return returnBV;
        }
        // check for known formulas
        if (hasFlag(F_CLUB) && hasSubType(S_HATCHET)) {
            returnBV = Math.ceil(entity.getWeight() / 5.0) * 1.5;
            if (entity.hasWorkingMisc(F_TSM)) {
                returnBV *= 2;
            }
        } else if (hasFlag(F_CLUB) && hasSubType(S_MACE_THB)) {
            returnBV = Math.ceil(entity.getWeight() / 5.0) * 1.5;
            if (entity.hasWorkingMisc(F_TSM)) {
                returnBV *= 2;
            }
        } else if (hasFlag(F_CLUB) && hasSubType(S_LANCE)) {
            returnBV = Math.ceil(entity.getWeight() / 5.0) * 1.0;
            if (entity.hasWorkingMisc(F_TSM)) {
                returnBV *= 2;
            }
        } else if (hasFlag(F_CLUB) && hasSubType(S_MACE)) {
            returnBV = Math.ceil(entity.getWeight() / 4.0);
            if (entity.hasWorkingMisc(F_TSM)) {
                returnBV *= 2;
            }
        } else if (hasFlag(F_CLUB) && hasSubType(S_RETRACTABLE_BLADE)) {
            returnBV = Math.ceil(entity.getWeight() / 10.0) * 1.725;
            if (entity.hasWorkingMisc(F_TSM)) {
                returnBV *= 2;
            }
        } else if (hasFlag(F_TARGCOMP)) {
            // 20% of direct_fire weaponry BV (half for rear-facing)
            double fFrontBV = 0.0, fRearBV = 0.0;
            for (Mounted m : entity.getWeaponList()) {
                WeaponType wt = (WeaponType) m.getType();
                if (wt.hasFlag(WeaponType.F_DIRECT_FIRE)) {
                    if (m.isRearMounted()) {
                        fRearBV += wt.getBV(entity);
                    } else {
                        fFrontBV += wt.getBV(entity);
                    }
                }
            }
            if (fFrontBV > fRearBV) {
                returnBV = (fFrontBV * 0.2) + (fRearBV * 0.1);
            }
            returnBV = (fRearBV * 0.2) + (fFrontBV * 0.1);
        } else if (hasFlag(F_HAND_WEAPON) && hasSubType(S_CLAW)) {
            returnBV = (Math.ceil(entity.getWeight() / 7.0)) * 1.275;
        } else if (hasFlag(F_TALON)) {
            // according to an email from TPTB, Talon BV is the extra damage
            // they
            // do for kicks, so 50% of normal kick damage
            returnBV = Math.round(Math.floor(entity.getWeight() / 5.0) * 0.5);
            if (entity.hasWorkingMisc(MiscType.F_TSM)) {
                returnBV *= 2;
            }
        }

        return returnBV;
    }

    /**
     * Add all the types of misc eq we can create to the list
     */
    public static void initializeTypes() {
        // all tech level 1 stuff
        EquipmentType.addType(MiscType.createHeatSink());
        EquipmentType.addType(MiscType.createJumpJet());
        EquipmentType.addType(MiscType.createTreeClub());
        EquipmentType.addType(MiscType.createGirderClub());
        EquipmentType.addType(MiscType.createLimbClub());
        EquipmentType.addType(MiscType.createHatchet());
        EquipmentType.addType(MiscType.createVacuumProtection());
        EquipmentType.addType(MiscType.createStandard());

        // Start of Level2 stuff
        EquipmentType.addType(MiscType.createISDoubleHeatSink());
        EquipmentType.addType(MiscType.createISDoubleHeatSinkPrototype());
        EquipmentType.addType(MiscType.createCLDoubleHeatSink());
        EquipmentType.addType(MiscType.createISCASE());
        EquipmentType.addType(MiscType.createCLCASE());
        EquipmentType.addType(MiscType.createISMASC());
        EquipmentType.addType(MiscType.createCLMASC());
        EquipmentType.addType(MiscType.createTSM());
        EquipmentType.addType(MiscType.createC3S());
        EquipmentType.addType(MiscType.createC3SBS());
        EquipmentType.addType(MiscType.createC3I());
        EquipmentType.addType(MiscType.createNC3());
        EquipmentType.addType(MiscType.createISArtemis());
        EquipmentType.addType(MiscType.createCLArtemis());
        EquipmentType.addType(MiscType.createGECM());
        EquipmentType.addType(MiscType.createCLECM());
        EquipmentType.addType(MiscType.createISTargComp());
        EquipmentType.addType(MiscType.createCLTargComp());
        EquipmentType.addType(MiscType.createMekStealth());
        EquipmentType.addType(MiscType.createFerroFibrous());
        EquipmentType.addType(MiscType.createEndoSteel());
        EquipmentType.addType(MiscType.createCLEndoSteel());
        EquipmentType.addType(MiscType.createBeagleActiveProbe());
        EquipmentType.addType(MiscType.createBloodhoundActiveProbe());
        EquipmentType.addType(MiscType.createTHBBloodhoundActiveProbe());
        EquipmentType.addType(MiscType.createCLActiveProbe());
        EquipmentType.addType(MiscType.createCLLightActiveProbe());
        EquipmentType.addType(MiscType.createISAPPod());
        EquipmentType.addType(MiscType.createCLAPPod());
        EquipmentType.addType(MiscType.createSword());
        EquipmentType.addType(MiscType.createISPPCCapacitor());
        EquipmentType.addType(MiscType.createRetractableBlade());
        EquipmentType.addType(MiscType.createChainWhip());
        EquipmentType.addType(MiscType.createISApolloFCS());
        EquipmentType.addType(MiscType.createEjectionSeat());
        EquipmentType.addType(MiscType.createIndustrialTSM());
        EquipmentType.addType(MiscType.createSalvageArm());
        EquipmentType.addType(MiscType.createSpotWelder());
        EquipmentType.addType(MiscType.createLiftHoist());
        EquipmentType.addType(MiscType.createTracks());
        EquipmentType.addType(MiscType.createISMASS());
        EquipmentType.addType(MiscType.createCLMASS());
        EquipmentType.addType(MiscType.createLightBridgeLayer());
        EquipmentType.addType(MiscType.createMediumBridgeLayer());
        EquipmentType.addType(MiscType.createHeavyBridgeLayer());
        // For industrials and tanks
        EquipmentType.addType(MiscType.createEnvironmentalSealing());

        EquipmentType.addType(MiscType.createFieldKitchen());
        EquipmentType.addType(MiscType.createAmphibiousChassis());

        EquipmentType.addType(MiscType.createImprovedJumpJet());
        EquipmentType.addType(MiscType.createCLImprovedJumpJet());
        EquipmentType.addType(MiscType.createJumpBooster());
        EquipmentType.addType(MiscType.createFerroFibrousPrototype());
        EquipmentType.addType(MiscType.createLightFerroFibrous());
        EquipmentType.addType(MiscType.createHeavyFerroFibrous());
        EquipmentType.addType(MiscType.createHardenedArmor());
        EquipmentType.addType(MiscType.createIndustrialArmor());
        EquipmentType.addType(MiscType.createPrimitiveArmor());
        EquipmentType.addType(MiscType.createHeavyIndustrialArmor());
        EquipmentType.addType(MiscType.createCommercialArmor());
        EquipmentType.addType(MiscType.createFerroLamellorArmor());
        EquipmentType.addType(MiscType.createEndoSteelPrototype());
        EquipmentType.addType(MiscType.createReinforcedStructure());
        EquipmentType.addType(MiscType.createCompositeStructure());
        EquipmentType.addType(MiscType.createIndustrialStructure());
        EquipmentType.addType(MiscType.createIS1CompactHeatSink());
        EquipmentType.addType(MiscType.createIS2CompactHeatSinks());
        EquipmentType.addType(MiscType.createCLLaserHeatSink());
        EquipmentType.addType(MiscType.createArtemisV());
        EquipmentType.addType(MiscType.createISAngelECM());
        EquipmentType.addType(MiscType.createISTHBAngelECM());
        EquipmentType.addType(MiscType.createCLgelECM());
        EquipmentType.addType(MiscType.createWatchdogECM());
        EquipmentType.addType(MiscType.createTHBMace());
        EquipmentType.addType(MiscType.createMace());
        EquipmentType.addType(MiscType.createDualSaw());
        EquipmentType.addType(MiscType.createChainsaw());
        EquipmentType.addType(MiscType.createRockCutter());
        EquipmentType.addType(MiscType.createCombine());
        EquipmentType.addType(MiscType.createBackhoe());
        EquipmentType.addType(MiscType.createPileDriver());
        EquipmentType.addType(MiscType.createArmoredCowl());
        EquipmentType.addType(MiscType.createNullSignatureSystem());
        EquipmentType.addType(MiscType.createVoidSignatureSystem());
        EquipmentType.addType(MiscType.createChameleonLightPolarizationShield());
        EquipmentType.addType(MiscType.createLightMinesweeper());
        EquipmentType.addType(MiscType.createBridgeKit());
        EquipmentType.addType(MiscType.createVibroShovel());
        EquipmentType.addType(MiscType.createDemolitionCharge());
        EquipmentType.addType(MiscType.createISSuperCharger());
        EquipmentType.addType(MiscType.createCLSuperCharger());
        EquipmentType.addType(MiscType.createISMediumShield());
        EquipmentType.addType(MiscType.createISSmallShield());
        EquipmentType.addType(MiscType.createISLargeShield());
        EquipmentType.addType(MiscType.createISClaw());
        EquipmentType.addType(MiscType.createCLHarJel());
        EquipmentType.addType(MiscType.createISHarJel());
        EquipmentType.addType(MiscType.createISUMU());
        EquipmentType.addType(MiscType.createCLUMU());
        EquipmentType.addType(MiscType.createISLance());
        EquipmentType.addType(MiscType.createISWreckingBall());
        EquipmentType.addType(MiscType.createCLWreckingBall());
        EquipmentType.addType(MiscType.createISFlail());
        EquipmentType.addType(MiscType.createISMediumVibroblade());
        EquipmentType.addType(MiscType.createISSmallVibroblade());
        EquipmentType.addType(MiscType.createISLargeVibroblade());
        EquipmentType.addType(MiscType.createISBuzzsaw());
        EquipmentType.addType(MiscType.createCLBuzzsaw());
        EquipmentType.addType(MiscType.createCoolantSystem());
        EquipmentType.addType(MiscType.createHeavyArmor());
        EquipmentType.addType(MiscType.createSpikes());
        EquipmentType.addType(MiscType.createTalons());
        EquipmentType.addType(MiscType.createReactive());
        EquipmentType.addType(MiscType.createReflective());
        EquipmentType.addType(MiscType.createISCASEII());
        EquipmentType.addType(MiscType.createCLCASEII());
        EquipmentType.addType(MiscType.createISAES());
        EquipmentType.addType(MiscType.createCLAES());
        EquipmentType.addType(MiscType.createISModularArmor());
        EquipmentType.addType(MiscType.createCLModularArmor());
        EquipmentType.addType(MiscType.createCommsGear1());
        EquipmentType.addType(MiscType.createCommsGear2());
        EquipmentType.addType(MiscType.createCommsGear3());
        EquipmentType.addType(MiscType.createCommsGear4());
        EquipmentType.addType(MiscType.createCommsGear5());
        EquipmentType.addType(MiscType.createCommsGear6());
        EquipmentType.addType(MiscType.createCommsGear7());
        EquipmentType.addType(MiscType.createCommsGear8());
        EquipmentType.addType(MiscType.createCommsGear9());
        EquipmentType.addType(MiscType.createCommsGear10());
        EquipmentType.addType(MiscType.createCommsGear11());
        EquipmentType.addType(MiscType.createCommsGear12());
        EquipmentType.addType(MiscType.createCommsGear13());
        EquipmentType.addType(MiscType.createCommsGear14());
        EquipmentType.addType(MiscType.createCommsGear15());
        EquipmentType.addType(MiscType.createISGroundMobileHPG());
        EquipmentType.addType(MiscType.createISMobileHPG());
        EquipmentType.addType(MiscType.createClanGroundMobileHPG());
        EquipmentType.addType(MiscType.createClanMobileHPG());
        EquipmentType.addType(MiscType.createCLPartialWing());
        EquipmentType.addType(MiscType.createISPartialWing());
        EquipmentType.addType(MiscType.createCargo1());
        EquipmentType.addType(MiscType.createHalfCargo());
        EquipmentType.addType(MiscType.createLiquidCargo1());
        EquipmentType.addType(MiscType.createHalfLiquidCargo());
        EquipmentType.addType(MiscType.createCargoContainer());
        EquipmentType.addType(MiscType.createMechSprayer());
        EquipmentType.addType(MiscType.createTankSprayer());
        EquipmentType.addType(MiscType.createFrontDumper());
        EquipmentType.addType(MiscType.createRearDumper());
        EquipmentType.addType(MiscType.createLeftDumper());
        EquipmentType.addType(MiscType.createRightDumper());
        EquipmentType.addType(MiscType.createMASH());
        EquipmentType.addType(MiscType.createMASHExtraTheater());
        EquipmentType.addType(MiscType.createParamedicEquipment());
        EquipmentType.addType(MiscType.createCLProtoMyomerBooster());
        EquipmentType.addType(MiscType.createProtoPartialWing());
        EquipmentType.addType(MiscType.createCLMastMount());
        EquipmentType.addType(MiscType.createISMastMount());
        EquipmentType.addType(MiscType.createFuel1());
        EquipmentType.addType(MiscType.createFuelHalf());
        EquipmentType.addType(MiscType.createBlueShield());
        EquipmentType.addType(MiscType.createEndoComposite());
        EquipmentType.addType(MiscType.createCLEndoComposite());
        EquipmentType.addType(MiscType.createCLLaserInsulator());
        EquipmentType.addType(MiscType.createISLaserInsulator());
        EquipmentType.addType(MiscType.createISEWEquipment());
        EquipmentType.addType(MiscType.createISCollapsibleCommandModule());
        EquipmentType.addType(MiscType.createHitch());
        EquipmentType.addType(MiscType.createISFlotationHull());
        EquipmentType.addType(MiscType.createISLimitedAmphibiousChassis());
        EquipmentType.addType(MiscType.createISFullyAmphibiousChassis());
        EquipmentType.addType(MiscType.createISDuneBuggyChassis());
        EquipmentType.addType(MiscType.createISOffRoadChassis());
        EquipmentType.addType(MiscType.createCLFlotationHull());
        EquipmentType.addType(MiscType.createCLLimitedAmphibiousChassis());
        EquipmentType.addType(MiscType.createCLFullyAmphibiousChassis());
        EquipmentType.addType(MiscType.createCLDuneBuggyChassis());
        EquipmentType.addType(MiscType.createCLOffRoadChassis());
        EquipmentType.addType(MiscType.createISShoulderTurret());
        EquipmentType.addType(MiscType.createCLShoulderTurret());
        EquipmentType.addType(MiscType.createISHeadTurret());
        EquipmentType.addType(MiscType.createCLHeadTurret());
        EquipmentType.addType(MiscType.createISQuadTurret());
        EquipmentType.addType(MiscType.createCLQuadTurret());
        EquipmentType.addType(MiscType.createCLTankCommandConsole());
        EquipmentType.addType(MiscType.createISTankCommandConsole());
        EquipmentType.addType(MiscType.createISSponsonTurret());
        EquipmentType.addType(MiscType.createCLSponsonTurret());
        EquipmentType.addType(MiscType.createPintleTurret());
        EquipmentType.addType(MiscType.createISArmoredMotiveSystem());
        EquipmentType.addType(MiscType.createCLArmoredMotiveSystem());
        EquipmentType.addType(MiscType.createISChaffPod());
        EquipmentType.addType(MiscType.createISDroneCarrierControlSystem());
        EquipmentType.addType(MiscType.createCLDroneCarrierControlSystem());
        EquipmentType.addType(MiscType.createISDroneExtra());
        EquipmentType.addType(MiscType.createCLDroneExtra());
        EquipmentType.addType(MiscType.createBC3());
        EquipmentType.addType(MiscType.createBC3i());
        EquipmentType.addType(MiscType.createISHIResImager());
        EquipmentType.addType(MiscType.createCLHIResImager());
        EquipmentType.addType(MiscType.createISHyperspectralImager());
        EquipmentType.addType(MiscType.createISInfraredImager());
        EquipmentType.addType(MiscType.createCLInfraredImager());
        EquipmentType.addType(MiscType.createISLookDownRadar());
        EquipmentType.addType(MiscType.createCLLookDownRadar());
        EquipmentType.addType(MiscType.createISVTOLJetBooster());
        EquipmentType.addType(MiscType.createCLVTOLJetBooster());
        EquipmentType.addType(MiscType.createRemoteSensorDispenser());
        EquipmentType.addType(MiscType.createCLDroneOperatingSystem());
        EquipmentType.addType(MiscType.createISDroneOperatingSystem());
        EquipmentType.addType(MiscType.createISVehicularMineDispenser());
        EquipmentType.addType(MiscType.createCLVehicularMineDispenser());
        EquipmentType.addType(MiscType.createMiningDrill());
        EquipmentType.addType(MiscType.createCLReconCamera());
        EquipmentType.addType(MiscType.createISReconCamera());
        EquipmentType.addType(MiscType.createISCombatVehicleEscapePod());
        EquipmentType.addType(MiscType.createISSmallNavalCommScannerSuite());
        EquipmentType.addType(MiscType.createCLSmallNavalCommScannerSuite());
        EquipmentType.addType(MiscType.createISLargeNavalCommScannerSuite());
        EquipmentType.addType(MiscType.createCLLargeNavalCommScannerSuite());
        EquipmentType.addType(MiscType.createCLNavalTugAdaptor());
        EquipmentType.addType(MiscType.createISNavalTugAdaptor());

        // Start BattleArmor equipment
        EquipmentType.addType(MiscType.createBAFireResistantArmor());
        EquipmentType.addType(MiscType.createBasicStealth());
        EquipmentType.addType(MiscType.createStandardStealth());
        EquipmentType.addType(MiscType.createImprovedStealth());
        EquipmentType.addType(MiscType.createMine());
        EquipmentType.addType(MiscType.createBABasicManipulator());
        EquipmentType.addType(MiscType.createBABasicManipulatorMineClearance());
        EquipmentType.addType(MiscType.createBABattleClaw());
        EquipmentType.addType(MiscType.createBABattleClawMagnets());
        EquipmentType.addType(MiscType.createBABattleClawVibro());
        EquipmentType.addType(MiscType.createBACargoLifter());
        EquipmentType.addType(MiscType.createBAHeavyBattleClaw());
        EquipmentType.addType(MiscType.createBAHeavyBattleClawMagnet());
        EquipmentType.addType(MiscType.createBAHeavyBattleClawVibro());
        EquipmentType.addType(MiscType.createBAIndustrialDrill());
        EquipmentType.addType(MiscType.createBASalvageArm());
        EquipmentType.addType(MiscType.createBAArmoredGlove());
        EquipmentType.addType(MiscType.createBAMagneticClamp());
        EquipmentType.addType(MiscType.createBAAPMount());
        EquipmentType.addType(MiscType.createCLBAMyomerBooster());
        EquipmentType.addType(MiscType.createSingleHexECM());
        EquipmentType.addType(MiscType.createMimeticCamo());
        EquipmentType.addType(MiscType.createSimpleCamo());
        EquipmentType.addType(MiscType.createParafoil());
        EquipmentType.addType(MiscType.createSearchlight());
        EquipmentType.addType(MiscType.createBASearchlight());
        EquipmentType.addType(MiscType.createISImprovedSensors());
        EquipmentType.addType(MiscType.createCLImprovedSensors());
        EquipmentType.addType(MiscType.createCLBALightActiveProbe());
        EquipmentType.addType(MiscType.createISBALightActiveProbe());
        EquipmentType.addType(MiscType.createBARemoteSensorDispenser());
        EquipmentType.addType(MiscType.createBACuttingTorch());
        EquipmentType.addType(MiscType.createBASpaceOperationsAdaptation());
        EquipmentType.addType(MiscType.createISDetachableWeaponPack());
        EquipmentType.addType(MiscType.createCLDetachableWeaponPack());
        EquipmentType.addType(MiscType.createBAHeatSensor());
        EquipmentType.addType(MiscType.createBAExtendedLifeSupport());
        EquipmentType.addType(MiscType.createBAPartialWing());
        EquipmentType.addType(MiscType.createISBAJumpBooster());
        EquipmentType.addType(MiscType.createCLBAJumpBooster());
        EquipmentType.addType(MiscType.createISBAMechanicalJumpBooster());
        // support vee stuff
        EquipmentType.addType(MiscType.createTractorModification());
        EquipmentType.addType(MiscType.createTrailerModification());
        EquipmentType.addType(MiscType.createArmoredChassis());
        EquipmentType.addType(MiscType.createBasicFireControl());
        EquipmentType.addType(MiscType.createAdvancedFireControl());
        EquipmentType.addType(MiscType.createISMineSweeper());
        EquipmentType.addType(MiscType.createClanMineSweeper());
        EquipmentType.addType(MiscType.createISMobileFieldBase());
        EquipmentType.addType(MiscType.createCLMobileFieldBase());
        EquipmentType.addType(MiscType.createSTOLChassisMod());
        EquipmentType.addType(MiscType.createVSTOLChassisMod());
        EquipmentType.addType(MiscType.createElectricDischargeArmor());
        EquipmentType.addType(MiscType.createISPrototypeJumpJet());
        EquipmentType.addType(MiscType.createBoobyTrap());
        EquipmentType.addType(MiscType.createRefuelingDrogue());
        EquipmentType.addType(MiscType.createBulldozer());
        EquipmentType.addType(MiscType.createExternalStoresHardpoint());
        EquipmentType.addType(MiscType.createManipulator());
        EquipmentType.addType(MiscType.createPropChassisModification());
        EquipmentType.addType(MiscType.createUltraLightChassisModification());
    }

    public static MiscType createHeatSink() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Heat Sink";
        misc.setInternalName(misc.name);
        misc.tonnage = 1.0f;
        misc.criticals = 1;
        misc.tankslots = 0;
        misc.flags = misc.flags.or(F_HEAT_SINK);
        misc.bv = 0;
        misc.introDate = 2022;
        misc.availRating = new int[]{EquipmentType.RATING_B, EquipmentType.RATING_B, EquipmentType.RATING_B};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createJumpJet() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Jump Jet";
        misc.setInternalName(misc.name);
        misc.addLookupName("JumpJet");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.tankslots = 0;
        misc.flags = misc.flags.or(F_JUMP_JET).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_STANDARD;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_C, EquipmentType.RATING_C};
        misc.introDate = 2471;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createISPrototypeJumpJet() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Prototype Improved Jump Jet";
        misc.setInternalName("ISPrototypeJumpJet");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.tankslots = 0;
        misc.flags = misc.flags.or(F_JUMP_JET).or(F_MECH_EQUIPMENT);
        misc.subType |= S_PROTOTYPE;
        misc.bv = 0;
        misc.introDate = 2464;
        misc.availRating = new int[]{EquipmentType.RATING_E, EquipmentType.RATING_X, EquipmentType.RATING_X};
        misc.techRating = RATING_D;
        return misc;
    }

    public static MiscType createImprovedJumpJet() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Improved Jump Jet";
        misc.setInternalName("IS Improved Jump Jet");
        misc.addLookupName("ISImprovedJump Jet");
        misc.addLookupName("ImprovedJump Jet");
        misc.addLookupName("Improved Jump Jet");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 2;
        misc.flags = misc.flags.or(F_JUMP_JET).or(F_MECH_EQUIPMENT);
        misc.subType |= S_IMPROVED;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};
        misc.introDate = 3069;
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createCLImprovedJumpJet() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Improved Jump Jet";
        misc.setInternalName("Clan Improved Jump Jet");
        misc.addLookupName("Clan Improved Jump Jet");
        misc.addLookupName("CLImprovedJump Jet");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 2;
        misc.flags = misc.flags.or(F_JUMP_JET).or(F_MECH_EQUIPMENT);
        misc.subType |= S_IMPROVED;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};
        misc.techRating = RATING_F;
        misc.introDate = 3069;

        return misc;
    }

    public static MiscType createTractorModification() {
        MiscType misc = new MiscType();

        misc.name = "Tractor";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_TRACTOR_MODIFICATION).or(F_SUPPORT_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;
        return misc;
    }

    public static MiscType createTrailerModification() {
        MiscType misc = new MiscType();

        misc.name = "Trailer";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_TRAILER_MODIFICATION).or(F_SUPPORT_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createTreeClub() {
        MiscType misc = new MiscType();

        misc.name = "Tree Club";
        misc.setInternalName(misc.name);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_TREE_CLUB | S_CLUB;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createGirderClub() {
        MiscType misc = new MiscType();

        misc.name = "Girder Club";
        misc.setInternalName(misc.name);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_CLUB;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;
        return misc;
    }

    public static MiscType createLimbClub() {
        MiscType misc = new MiscType();

        misc.name = "Limb Club";
        misc.setInternalName(misc.name);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.flags = misc.flags.or(F_CLUB);
        misc.subType |= S_CLUB;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;
        return misc;
    }

    public static MiscType createHatchet() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_INTRO_BOXSET;
        misc.name = "Hatchet";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_HATCHET;
        misc.bv = BV_VARIABLE;
        misc.introDate = 3022;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_F, EquipmentType.RATING_D};
        misc.techRating = RATING_B;

        return misc;
    }

    // Start of Level2 stuff

    public static MiscType createISDoubleHeatSink() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Double Heat Sink";
        misc.setInternalName("ISDoubleHeatSink");
        misc.addLookupName("IS Double Heat Sink");
        misc.addLookupName("ISDouble Heat Sink");
        misc.tonnage = 1.0f;
        misc.criticals = 3;
        misc.flags = misc.flags.or(F_DOUBLE_HEAT_SINK);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_E, EquipmentType.RATING_D};
        misc.introDate = 2567;
        misc.extinctDate = 2865;
        misc.reintroDate = 3040;
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createISDoubleHeatSinkPrototype() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "IS Double Heat Sink Prototype";
        misc.setInternalName("ISDoubleHeatSinkPrototype");
        misc.addLookupName("IS Double Heat Sink Prototype");
        misc.addLookupName("ISDouble Heat Sink Prototype");
        misc.tonnage = 1.0f;
        misc.criticals = 3;
        misc.flags = misc.flags.or(F_IS_DOUBLE_HEAT_SINK_PROTOTYPE);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_F, EquipmentType.RATING_F, EquipmentType.RATING_F};
        misc.introDate = 2559;
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createCLDoubleHeatSink() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Double Heat Sink";
        misc.setInternalName("CLDoubleHeatSink");
        misc.addLookupName("Clan Double Heat Sink");
        misc.addLookupName("CLDouble Heat Sink");
        misc.tonnage = 1.0f;
        misc.criticals = 2;
        misc.flags = misc.flags.or(F_DOUBLE_HEAT_SINK);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_B, EquipmentType.RATING_B};
        misc.introDate = 2595;
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createISCASE() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "CASE";
        misc.setInternalName("ISCASE");
        misc.addLookupName("IS CASE");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_CASE).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.cost = 50000;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_F, EquipmentType.RATING_D};
        misc.introDate = 2476;
        misc.extinctDate = 2840;
        misc.reintroDate = 3036;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCLCASE() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "CASE";
        misc.setInternalName("CLCASE");
        misc.addLookupName("Clan CASE");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_CASE).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.cost = 50000;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_B, EquipmentType.RATING_B};
        misc.introDate = 2595;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createISCASEII() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "CASE II";
        misc.setInternalName("ISCASEII");
        misc.addLookupName("IS CASE II");
        misc.tonnage = 1.0f;
        misc.criticals = 1;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_CASEII).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.cost = 175000;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};

        return misc;
    }

    public static MiscType createCLCASEII() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "CASE II";
        misc.setInternalName("CLCASEII");
        misc.addLookupName("Clan CASE II");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_CASEII).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.cost = 175000;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};

        return misc;
    }

    public static MiscType createISMASC() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "MASC";
        misc.setInternalName("ISMASC");
        misc.addLookupName("IS MASC");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_MASC).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_D, EquipmentType.RATING_F, EquipmentType.RATING_E};

        String[] saModes =
            { "Armed", "Off" };
        misc.setModes(saModes);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_C, EquipmentType.RATING_C};
        misc.introDate = 2740;
        misc.extinctDate = 2795;
        misc.reintroDate = 3035;

        return misc;
    }

    public static MiscType createCLMASC() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "MASC";
        misc.setInternalName("CLMASC");
        misc.addLookupName("Clan MASC");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_MASC).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        String[] saModes =
            { "Armed", "Off" };
        misc.setModes(saModes);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_C, EquipmentType.RATING_C};
        misc.introDate = 2740;
        misc.techRating = RATING_F;

        return misc;
    }

    public static MiscType createCLProtoMyomerBooster() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Protomech Myomer Booster";
        misc.setInternalName("CLMyomerBooster");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.cost = COST_VARIABLE;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_MASC).or(F_PROTOMECH_EQUIPMENT);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};
        misc.introDate = 3068;
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createCLBAMyomerBooster() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "BA Myomer Booster";
        misc.setInternalName("CLBAMyomerBooster");
        misc.addLookupName("CLBAMB");
        misc.addLookupName("BAMyomerBooster");
        misc.tonnage = .250f;
        misc.criticals = 0;
        misc.cost = COST_VARIABLE;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_MASC).or(F_BA_EQUIPMENT);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};

        return misc;
    }

    public static MiscType createISSuperCharger() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Supercharger";
        misc.setInternalName(misc.name);
        misc.addLookupName("IS Super Charger");
        misc.addLookupName("ISSuperCharger");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_MASC).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_SUPERCHARGER;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_F, EquipmentType.RATING_F, EquipmentType.RATING_F};

        String[] saModes =
            { "Armed", "Off" };
        misc.setModes(saModes);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_C, EquipmentType.RATING_C};

        return misc;
    }

    public static MiscType createCLSuperCharger() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Supercharger";
        misc.setInternalName("CL Super Charger");
        misc.addLookupName("CLSuperCharger");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_MASC).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_SUPERCHARGER;
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_F, EquipmentType.RATING_F, EquipmentType.RATING_F};

        String[] saModes =
            { "Armed", "Off" };
        misc.setModes(saModes);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_C, EquipmentType.RATING_C};

        return misc;
    }

    public static MiscType createTSM() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "TSM";
        misc.setInternalName(misc.name);
        misc.addLookupName("IS TSM");
        misc.addLookupName("Triple Strength Myomer");
        misc.tonnage = 0;
        misc.criticals = 6;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_TSM).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_D};
        misc.introDate = 3050;
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createIndustrialTSM() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Industrial TSM";
        misc.setInternalName(misc.name);
        misc.addLookupName("IS Industrial TSM");
        misc.addLookupName("Industrial Triple Strength Myomer");
        misc.tonnage = 0;
        misc.criticals = 12;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_INDUSTRIAL_TSM).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};
        misc.introDate = 3045;
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createC3S() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "C3 Slave";
        misc.setInternalName("ISC3SlaveUnit");
        misc.addLookupName("IS C3 Slave");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 250000;
        misc.flags = misc.flags.or(F_C3S).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};
        misc.introDate = 3050;
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createC3SBS() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "C3 Slave Boosted";
        misc.setInternalName("ISC3BoostedSystemSlaveUnit");
        misc.addLookupName("IS C3 Boosted System Slave");
        misc.tonnage = 3;
        misc.criticals = 2;
        misc.cost = 500000;
        misc.flags = misc.flags.or(F_C3SBS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};

        return misc;
    }

    public static MiscType createC3I() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "C3i Computer";
        misc.setInternalName("ISC3iUnit");
        misc.addLookupName("ISImprovedC3CPU");
        misc.addLookupName("IS C3i Computer");
        misc.tonnage = 2.5f;
        misc.criticals = 2;
        misc.cost = 750000;
        misc.flags = misc.flags.or(F_C3I).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};
        misc.introDate = 3062;

        return misc;
    }

    public static MiscType createNC3() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Naval C3";
        misc.setInternalName("ISNC3");
        misc.setInternalName("NC3");
        misc.setInternalName("NC3Unit");
        misc.setInternalName("ISNC3Unit");
        misc.addLookupName("IS Navel C3");
        misc.tonnage = 6;
        misc.criticals = 1;
        misc.cost = 250000;
        misc.flags = misc.flags.or(F_C3I).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};

        return misc;
    }

    public static MiscType createBC3() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "BC3";
        misc.setInternalName("BattleArmorC3");
        misc.addLookupName("IS BattleArmor C3");
        misc.tonnage = .250f;
        misc.criticals = 1;
        misc.cost = 62500;
        misc.flags = misc.flags.or(F_C3S).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};

        return misc;
    }

    public static MiscType createBC3i() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "BC3i";
        misc.setInternalName("ISBC3i");
        misc.addLookupName("IS BC3i");
        misc.addLookupName("IS BattleArmor C3i");
        misc.tonnage = .350f;
        misc.criticals = 1;
        misc.cost = 125000;
        misc.flags = misc.flags.or(F_C3I).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};

        return misc;
    }

    public static MiscType createISArtemis() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Artemis IV FCS";
        misc.setInternalName("ISArtemisIV");
        misc.addLookupName("IS Artemis IV FCS");
        misc.tonnage = 1.0f;
        misc.criticals = 1;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_ARTEMIS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.availRating = new int[]{EquipmentType.RATING_E, EquipmentType.RATING_F, EquipmentType.RATING_D};
        misc.introDate = 2598;
        misc.extinctDate = 2855;
        misc.reintroDate = 3035;
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createCLArtemis() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Artemis IV FCS";
        misc.setInternalName("CLArtemisIV");
        misc.addLookupName("Clan Artemis IV FCS");
        misc.tonnage = 1.0f;
        misc.cost = 100000;
        misc.criticals = 1;
        misc.flags = misc.flags.or(F_ARTEMIS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 2598;
        misc.techRating = RATING_F;

        return misc;
    }

    public static MiscType createArtemisV() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Artemis V FCS";
        misc.setInternalName("CLArtemisV");
        misc.addLookupName("Clan Artemis V");
        misc.addLookupName("Artemis V");
        misc.tonnage = 1.5f;
        misc.cost = 250000;
        misc.criticals = 2;
        misc.flags = misc.flags.or(F_ARTEMIS_V).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};
        return misc;
    }

    public static MiscType createISApolloFCS() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "MRM Apollo FCS";
        misc.setInternalName("ISApollo");
        misc.addLookupName("IS MRM Apollo Fire Control System");
        misc.addLookupName("IS MRM Apollo FCS");
        misc.tonnage = 1.0f;
        misc.criticals = 1;
        misc.cost = 125000;
        misc.flags = misc.flags.or(F_APOLLO).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};

        return misc;
    }

    public static MiscType createGECM() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Guardian ECM Suite";
        misc.setInternalName("ISGuardianECMSuite");
        misc.addLookupName("IS Guardian ECM");
        misc.addLookupName("ISGuardianECM");
        misc.addLookupName("IS Guardian ECM Suite");
        misc.tonnage = 1.5f;
        misc.criticals = 2;
        misc.cost = 200000;
        misc.flags = misc.flags.or(F_ECM).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 61;
        misc.setModes(new String[]
            { "ECM" });
        misc.setInstantModeSwitch(false);
        misc.availRating = new int[]{EquipmentType.RATING_E, EquipmentType.RATING_F, EquipmentType.RATING_D};
        misc.introDate = 2597;
        misc.extinctDate = 2845;
        misc.reintroDate = 3045;

        return misc;
    }

    public static MiscType createCLECM() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "ECM Suite";
        misc.setInternalName("CLECMSuite");
        misc.addLookupName("Clan ECM Suite");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 200000;
        misc.flags = misc.flags.or(F_ECM).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 61;
        misc.setModes(new String[]
            { "ECM" });
        misc.setInstantModeSwitch(false);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 2597;

        return misc;
    }

    public static MiscType createISAngelECM() {
        MiscType misc = new MiscType();

        // Don't forget, this will eventually count double for ECCM.
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Angel ECM Suite";
        misc.setInternalName("ISAngelECMSuite");
        misc.addLookupName("IS Angel ECM Suite");
        misc.addLookupName("ISAngelECM");
        misc.tonnage = 2;
        misc.criticals = 2;
        misc.cost = 750000;
        misc.flags = misc.flags.or(F_ECM).or(F_ANGEL_ECM).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 100;
        misc.setModes(new String[]
            { "ECM" });
        misc.setInstantModeSwitch(false);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};
        misc.introDate = 3057;

        return misc;
    }

    public static MiscType createISTHBAngelECM() {
        MiscType misc = new MiscType();

        // Don't forget, this will eventually count double for ECCM.
        misc.techLevel = TechConstants.T_IS_UNOFFICIAL;
        misc.name = "THB Angel ECM Suite";
        misc.setInternalName("ISTHBAngelECMSuite");
        misc.addLookupName("IS THB Angel ECM Suite");
        misc.addLookupName("ISTHBAngelECM");
        misc.tonnage = 1.5f;
        misc.criticals = 2;
        misc.cost = 1000000;
        misc.flags = misc.flags.or(F_ECM).or(F_ANGEL_ECM).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 100;
        misc.setModes(new String[]
            { "ECM" });
        misc.setInstantModeSwitch(false);
        misc.availRating = new int[]{EquipmentType.RATING_E, EquipmentType.RATING_F, EquipmentType.RATING_D};
        misc.introDate = 2597;
        misc.extinctDate = 2845;
        misc.reintroDate = 3045;

        return misc;
    }

    public static MiscType createCLgelECM() {
        MiscType misc = new MiscType();

        // Don't forget, this will eventually count double for ECCM.
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Angel ECM Suite";
        misc.setInternalName("CLAngelECMSuite");
        misc.addLookupName("Clan Angel ECM Suite");
        misc.addLookupName("CLAngelECM");
        misc.addLookupName("Clan Angel ECM Suite");
        misc.tonnage = 2;
        misc.criticals = 2;
        misc.cost = 750000;
        misc.flags = misc.flags.or(F_ECM).or(F_ANGEL_ECM).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 100;
        misc.setModes(new String[]
            { "ECM" });
        misc.setInstantModeSwitch(false);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 2597;

        return misc;
    }

    public static MiscType createWatchdogECM() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Watchdog CEWS";
        misc.setInternalName(Sensor.WATCHDOG);
        misc.addLookupName("Watchdog ECM Suite");
        misc.addLookupName("WatchdogECM");
        misc.addLookupName("CLWatchdogECM");
        misc.tonnage = 1.5f;
        misc.criticals = 2;
        misc.cost = 500000;
        misc.flags = misc.flags.or(F_WATCHDOG).or(F_ECM).or(F_BAP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 68;
        misc.setModes(new String[]
            { "ECM" });
        misc.setInstantModeSwitch(false);
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};
        misc.introDate = 3059;

        return misc;
    }

    public static MiscType createSword() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Sword";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_SWORD;
        misc.bv = BV_VARIABLE;
        misc.introDate = 3058;
        misc.availRating = new int[]{RATING_X,RATING_F,RATING_D};
        misc.techRating = RATING_B;
        return misc;
    }

    public static MiscType createChainWhip() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Chain Whip";
        misc.setInternalName(misc.name);
        misc.tonnage = 3;
        misc.criticals = 2;
        misc.cost = 120000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_CHAIN_WHIP;
        misc.bv = 5.175;

        return misc;
    }

    public static MiscType createRetractableBlade() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Retractable Blade";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_RETRACTABLE_BLADE;
        misc.bv = BV_VARIABLE;
        misc.setInstantModeSwitch(true);
        String[] modes =
            { "retracted", "extended" };
        misc.setModes(modes);
        misc.introDate = 2420;
        misc.availRating = new int[]{RATING_F,RATING_D,RATING_D};
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createSpotWelder() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Spot Welder";
        misc.setInternalName(misc.name);
        misc.tonnage = 2;
        misc.criticals = 1;
        misc.cost = 75000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_SPOT_WELDER;
        misc.bv = 5;
        misc.industrial = true;
        misc.introDate = 2320;
        misc.availRating = new int[]{RATING_C,RATING_D,RATING_C};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createTHBMace() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_UNOFFICIAL;
        misc.name = "Mace (THB)";
        misc.setInternalName(misc.name);
        misc.addLookupName("THB Mace");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_MACE_THB;
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createMace() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Mace";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = 130000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_MACE;
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createBackhoe() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Backhoe";
        misc.setInternalName(misc.name);
        misc.tonnage = 5;
        misc.criticals = 6;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_BACKHOE;
        misc.bv = 8;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_B,RATING_B,RATING_B};
        misc.techRating = RATING_B;
        return misc;
    }

    public static MiscType createLiftHoist() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Lift Hoist";
        misc.setInternalName(misc.name);
        misc.tonnage = 3;
        misc.criticals = 3;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_LIFTHOIST).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;
        return misc;
    }

    public static MiscType createDualSaw() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Dual Saw";
        misc.setInternalName(misc.name);
        misc.tonnage = 7;
        misc.criticals = 7;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_DUAL_SAW;
        misc.bv = 9;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_D,RATING_D};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createPileDriver() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Pile Driver";
        misc.setInternalName(misc.name);
        misc.addLookupName("PileDriver");
        misc.tonnage = 10;
        misc.criticals = 8;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_PILE_DRIVER;
        misc.bv = 5;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_E,RATING_D};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createChainsaw() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Chainsaw";
        misc.setInternalName(misc.name);
        misc.tonnage = 5;
        misc.criticals = 5;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_CHAINSAW;
        misc.bv = 7;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_D,RATING_D};
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createRockCutter() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Rock Cutter";
        misc.setInternalName(misc.name);
        misc.tonnage = 5;
        misc.criticals = 5;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_ROCK_CUTTER;
        misc.bv = 6;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_D,RATING_D};
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createCombine() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Combine";
        misc.setInternalName(misc.name);
        misc.tonnage = 2.5f;
        misc.criticals = 4;
        misc.cost = 75000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_COMBINE;
        misc.bv = 5;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_C,RATING_C,RATING_C};
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createEjectionSeat() {
        MiscType misc = new MiscType();

        misc.name = "Ejection Seat";
        misc.setInternalName(misc.name);
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_EJECTION_SEAT).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_D,RATING_D};
        misc.techRating = RATING_B;
        return misc;
    }

    public static MiscType createArmoredChassis() {
        MiscType misc = new MiscType();

        misc.name = "Armored Chassis";
        misc.setInternalName("Armored Chassis");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.cost = 0;
        misc.tankslots = 0;
        misc.flags = misc.flags.or(F_ARMORED_CHASSIS).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_C,RATING_E,RATING_D};
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createArmoredCowl() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_UNOFFICIAL;
        misc.name = "Armored Cowl";
        misc.setInternalName(misc.name);
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 10000;
        misc.flags = misc.flags.or(F_COWL).or(F_MECH_EQUIPMENT);
        misc.bv = 10;

        return misc;
    }

    /**
     * Targeting comps should NOT be spreadable. However, I've set them such as
     * a temp measure to overcome the following bug: TC space allocation is
     * calculated based on tonnage of direct-fire weaponry. However, since meks
     * are loaded location-by-location, when the TC is loaded it's very unlikely
     * that all of the weaponry will be attached, resulting in undersized comps.
     * Any remaining TC crits after the last expected one are being handled as a
     * 2nd TC, causing LocationFullExceptions.
     */
    public static MiscType createISTargComp() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Targeting Computer";
        misc.setInternalName("ISTargeting Computer");
        misc.addLookupName("IS Targeting Computer");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.bv = BV_VARIABLE;
        misc.flags = misc.flags.or(F_TARGCOMP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        // see note above
        misc.spreadable = true;
        String[] modes =
            { "Normal", "Aimed shot" };
        misc.setModes(modes);
        misc.introDate = 3062;
        misc.techRating = RATING_E;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};

        return misc;
    }

    public static MiscType createCLTargComp() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Targeting Computer";
        misc.setInternalName("CLTargeting Computer");
        misc.addLookupName("Clan Targeting Computer");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.bv = BV_VARIABLE;
        misc.flags = misc.flags.or(F_TARGCOMP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        // see note above
        misc.spreadable = true;
        String[] modes =
            { "Normal", "Aimed shot" };
        misc.setModes(modes);
        misc.introDate = 2860;
        misc.techRating = RATING_F;
        misc.availRating = new int[]{RATING_X,RATING_D,RATING_C};

        return misc;
    }

    // Start BattleArmor equipment

    public static MiscType createBAFireResistantArmor() {
        MiscType misc = new MiscType();

        misc.name = "Fire Resistant Armor";
        misc.setInternalName("BA-Fire Resistant Armor");
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_FIRE_RESISTANT).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 3058;
        misc.techRating = RATING_E;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_F};

        return misc;
    }

    public static MiscType createBasicStealth() {
        MiscType misc = new MiscType();

        misc.name = BattleArmor.BASIC_STEALTH_ARMOR;
        misc.setInternalName(BattleArmor.BASIC_STEALTH_ARMOR);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_STEALTH).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 2710;
        misc.extinctDate = 2770;
        misc.reintroDate = 3052;
        misc.techRating = RATING_E;
        misc.availRating = new int[]{RATING_F,RATING_F,RATING_E};

        return misc;
    }

    public static MiscType createStandardStealth() {
        MiscType misc = new MiscType();

        misc.name = BattleArmor.STANDARD_STEALTH_ARMOR;
        misc.setInternalName(BattleArmor.STANDARD_STEALTH_ARMOR);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_STEALTH).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 2720;
        misc.extinctDate = 2770;
        misc.reintroDate = 3052;
        misc.techRating = RATING_E;
        misc.availRating = new int[]{RATING_X,RATING_F,RATING_E};

        return misc;
    }

    public static MiscType createImprovedStealth() {
        MiscType misc = new MiscType();

        misc.name = BattleArmor.IMPROVED_STEALTH_ARMOR;
        misc.setInternalName(BattleArmor.IMPROVED_STEALTH_ARMOR);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_STEALTH).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 3057;
        misc.techRating = RATING_E;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_F};

        return misc;
    }

    public static MiscType createMine() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Mine";
        misc.setInternalName("Mine");
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.flags = misc.flags.or(F_MINE).or(F_BA_EQUIPMENT);
        misc.bv = 4;
        misc.introDate = 1950;
        misc.techRating = RATING_C;
        misc.availRating = new int[]{RATING_E,RATING_E,RATING_D};


        return misc;
    }

    public static MiscType createISVehicularMineDispenser() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Mine Dispenser";
        misc.setInternalName("ISVehicularMineDispenser");
        misc.cost = 20000;
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        // TODO: implement game rules for this, analog to the mine for BAs
        misc.flags = misc.flags.or(F_MINE).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 8; // because it includes 2 mines
        misc.introDate = 3062;
        misc.techRating = RATING_B;
        misc.availRating = new int[]{ RATING_E, RATING_E, RATING_F };
        return misc;
    }

    public static MiscType createCLVehicularMineDispenser() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Mine Dispenser";
        misc.setInternalName("CLVehicularMineDispenser");
        misc.cost = 20000;
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.techRating = RATING_B;
        misc.availRating = new int[]{ RATING_E, RATING_E, RATING_F };
        // TODO: implement game rules for this, analog to the mine for BAs
        misc.flags = misc.flags.or(F_MINE).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 8; // because it includes 2 mines

        return misc;
    }

    public static MiscType createMiningDrill() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Mining Drill";
        misc.setInternalName("MiningDrill");
        misc.cost = 10000;
        misc.tonnage = 3.0f;
        misc.criticals = 4;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_MINING_DRILL;
        misc.bv = 6;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.techRating = RATING_B;
        misc.availRating = new int[]{ RATING_C, RATING_D, RATING_D };

        return misc;
    }

    public static MiscType createLightMinesweeper() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Light Minesweeper";
        misc.setInternalName("Light Minesweeper");
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_TOOLS).or(F_BA_EQUIPMENT);
        misc.subType |= S_MINESWEEPER;
        misc.toHitModifier = 1;
        misc.bv = 0;

        return misc;
    }

    public static MiscType createBAMagneticClamp() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Magnetic Clamps";
        misc.setInternalName("BA-Magnetic Clamp");
        misc.addLookupName("Magnetic Clamp");
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_MAGNETIC_CLAMP).or(F_BA_EQUIPMENT);
        String[] saModes =
            { "On", "Off" };
        misc.setModes(saModes);
        misc.setInstantModeSwitch(true);
        misc.bv = 1;
        misc.introDate = 3062;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_C;

        return misc;
    }

    public static MiscType createSingleHexECM() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = BattleArmor.SINGLE_HEX_ECM;
        misc.setInternalName(BattleArmor.SINGLE_HEX_ECM);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_ECM).or(F_SINGLE_HEX_ECM).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.setModes(new String[]
            { "ECM" });
        misc.setInstantModeSwitch(false);
        misc.introDate = 2720;
        misc.extinctDate = 2788;
        misc.reintroDate = 3057;
        misc.availRating = new int[]{RATING_E,RATING_F,RATING_F};
        misc.introDate = 2720;
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createMimeticCamo() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = BattleArmor.MIMETIC_ARMOR;
        misc.setInternalName(BattleArmor.MIMETIC_ARMOR);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_STEALTH).or(F_VISUAL_CAMO).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 2800;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_F};
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createSimpleCamo() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = BattleArmor.CAMO_SYSTEM;
        misc.setInternalName(BattleArmor.CAMO_SYSTEM);
        misc.addLookupName("Simple Camo");
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_STEALTH).or(F_VISUAL_CAMO).or(F_BA_EQUIPMENT);
        misc.introDate = 2800;
        misc.availRating = new int[]{RATING_X,RATING_F,RATING_F};
        misc.techRating = RATING_E;
        misc.bv = 0;

        return misc;
    }

    public static MiscType createParafoil() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Parafoil";
        misc.setInternalName("Parafoil");
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_PARAFOIL).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_B,RATING_B,RATING_C};
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createMekStealth() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_STEALTH);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_STEALTH));
        misc.addLookupName("Stealth Armor");
        misc.tonnage = 0; // ???
        misc.criticals = 12;
        misc.tankslots = 0;
        misc.hittable = false;
        misc.spreadable = true;
        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.flags = misc.flags.or(F_STEALTH).or(F_MECH_EQUIPMENT);
        String[] saModes =
            { "Off", "On" };
        misc.setModes(saModes);
        misc.setInstantModeSwitch(false);
        misc.bv = 0;
        misc.introDate = 3063;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createNullSignatureSystem() {
        MiscType misc = new MiscType();

        misc.name = "Null Signature System";
        misc.setInternalName("Mek Null Signature System");
        misc.addLookupName("Null Signature System");
        misc.addLookupName("NullSignatureSystem");
        misc.tonnage = 0;
        misc.criticals = 7;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_NULLSIG).or(F_MECH_EQUIPMENT);
        String[] saModes =
            { "Off", "On" };
        misc.setModes(saModes);
        misc.setInstantModeSwitch(false);
        misc.bv = 0;
        misc.cost = 1400000;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;

        return misc;
    }

    public static MiscType createVoidSignatureSystem() {
        MiscType misc = new MiscType();

        misc.name = "Void Signature System";
        misc.setInternalName("Mek Void Signature System");
        misc.addLookupName("Void Signature System");
        misc.addLookupName("VoidSignatureSystem");
        misc.tonnage = 0;
        misc.criticals = 7;
        misc.spreadable = true;
        String[] saModes =
            { "Off", "On" };
        misc.setModes(saModes);
        misc.setInstantModeSwitch(false);
        misc.flags = misc.flags.or(F_VOIDSIG).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        misc.cost = 2000000;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;

        return misc;
    }

    public static MiscType createChameleonLightPolarizationShield() {
        MiscType misc = new MiscType();

        misc.name = "Chameleon Light Polarization Shield";
        misc.setInternalName("Chameleon Light Polarization Shield");
        misc.addLookupName("Chameleon Light Polarization Field");
        misc.addLookupName("ChameleonLightPolarizationShield");
        misc.addLookupName("ChameleonLightPolarizationField");
        misc.tonnage = 0;
        misc.criticals = 6;
        misc.spreadable = true;
        String[] saModes =
            { "Off", "On" };
        misc.setModes(saModes);
        misc.setInstantModeSwitch(false);
        misc.flags = misc.flags.or(F_CHAMELEON_SHIELD).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        misc.cost = 600000;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;

        return misc;
    }

    public static MiscType createFerroFibrous() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_FIBROUS);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_FIBROUS));
        misc.addLookupName("Ferro-Fibrous Armor");
        misc.addLookupName("Ferro Fibre");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_FERRO_FIBROUS);
        misc.bv = 0;
        misc.introDate = 2571;
        misc.extinctDate = 2810;
        misc.reintroDate = 3040;
        misc.availRating = new int[]{RATING_D,RATING_F,RATING_D};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createFerroFibrousPrototype() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_FIBROUS_PROTO);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_FIBROUS_PROTO));
        misc.addLookupName("Ferro-Fibrous Armor Prototype");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_FERRO_FIBROUS);
        misc.bv = 0;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.introDate = 2557;
        misc.availRating = new int[]{RATING_D,RATING_F,RATING_D};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createLightFerroFibrous() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_LIGHT_FERRO);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_LIGHT_FERRO));
        misc.addLookupName("Light Ferro-Fibrous Armor");
        misc.addLookupName("LightFerro");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_FERRO_FIBROUS);
        misc.bv = 0;
        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.introDate = 3067;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createHeavyFerroFibrous() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_HEAVY_FERRO);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_HEAVY_FERRO));
        misc.addLookupName("Heavy Ferro-Fibrous Armor");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_FERRO_FIBROUS);
        misc.bv = 0;
        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.introDate = 3069;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createHardenedArmor() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_HARDENED);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_HARDENED));
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.hittable = false;
        misc.bv = 0;

        return misc;
    }

    public static MiscType createCommercialArmor() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_COMMERCIAL);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_COMMERCIAL));
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.hittable = false;
        misc.bv = 0;
        misc.industrial = true;
        misc.introDate = 2400;
        misc.availRating = new int[]{RATING_B,RATING_B,RATING_A};
        misc.techRating = RATING_B;
        return misc;
    }

    public static MiscType createFerroLamellorArmor() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_LAMELLOR);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_FERRO_LAMELLOR));
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.hittable = false;
        misc.spreadable = true;
        misc.bv = 0;
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.flags = misc.flags.or(F_FERRO_LAMELLOR);
        return misc;
    }

    public static MiscType createIndustrialArmor() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_INDUSTRIAL);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_INDUSTRIAL));
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.hittable = false;
        misc.bv = 0;
        misc.industrial = true;
        misc.introDate = 2439;
        misc.availRating = new int[]{RATING_B,RATING_C,RATING_B};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createPrimitiveArmor() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_PRIMITIVE);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_PRIMITIVE));
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.hittable = false;
        misc.bv = 0;
        misc.industrial = true;
        return misc;
    }

    public static MiscType createHeavyIndustrialArmor() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_HEAVY_INDUSTRIAL);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_HEAVY_INDUSTRIAL));
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.hittable = false;
        misc.bv = 0;
        misc.industrial = true;
        misc.introDate = 3040;
        misc.availRating = new int[]{RATING_C,RATING_C,RATING_C};
        misc.techRating = RATING_D;
        return misc;
    }

    public static MiscType createEndoSteel() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_STEEL);
        misc.setInternalName(EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_STEEL));
        misc.addLookupName("Endo-Steel");
        misc.addLookupName("EndoSteel");
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_ENDO_STEEL);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_D, EquipmentType.RATING_F, EquipmentType.RATING_E};
        misc.introDate = 2487;
        misc.extinctDate = 2850;
        misc.reintroDate = 3035;
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createEndoComposite() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_COMPOSITE);
        misc.setInternalName(EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_COMPOSITE));
        misc.addLookupName("Endo-Composite");
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_ENDO_COMPOSITE);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createEndoSteelPrototype() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_PROTOTYPE);
        misc.setInternalName(EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_PROTOTYPE));
        misc.addLookupName("Endo-Steel Prototype");
        misc.addLookupName("EndoSteelPrototype");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 16;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_ENDO_STEEL);
        misc.bv = 0;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.availRating = new int[]{EquipmentType.RATING_D, EquipmentType.RATING_F, EquipmentType.RATING_E};
        misc.introDate = 2471;
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createCLEndoSteel() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_STEEL);
        misc.setInternalName("Clan " + EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_STEEL));
        misc.addLookupName("ClanEndo-Steel");
        misc.addLookupName("ClanEndoSteel");
        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 7;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_ENDO_STEEL);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 2487;
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createCLEndoComposite() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_COMPOSITE);
        misc.setInternalName("Clan " + EquipmentType.getStructureTypeName(T_STRUCTURE_ENDO_COMPOSITE));
        misc.addLookupName("ClanEndo-Composite");
        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 4;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_ENDO_COMPOSITE);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createReinforcedStructure() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_REINFORCED);
        misc.setInternalName(EquipmentType.getStructureTypeName(T_STRUCTURE_REINFORCED));
        misc.addLookupName("Reinforced");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.hittable = false;
        misc.spreadable = true;
        misc.bv = 0;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        return misc;
    }

    public static MiscType createCommsGear1() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (1 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:1");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear2() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (2 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:2");
        misc.tonnage = 2;
        misc.criticals = 2;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear3() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (3 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:3");
        misc.tonnage = 3;
        misc.criticals = 3;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear4() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (4 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:4");
        misc.tonnage = 4;
        misc.criticals = 4;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear5() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (5 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:51");
        misc.tonnage = 5;
        misc.criticals = 5;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear6() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (6 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:6");
        misc.tonnage = 6;
        misc.criticals = 6;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear7() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (7 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:7");
        misc.tonnage = 7;
        misc.criticals = 7;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear8() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (8 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:8");
        misc.tonnage = 8;
        misc.criticals = 8;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear9() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (9 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:9");
        misc.tonnage = 9;
        misc.criticals = 9;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear10() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (10 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:10");
        misc.tonnage = 10;
        misc.criticals = 10;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear11() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (11 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:11");
        misc.tonnage = 11;
        misc.criticals = 11;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear12() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (12 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:12");
        misc.tonnage = 12;
        misc.criticals = 12;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear13() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (13 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:13");
        misc.tonnage = 13;
        misc.criticals = 13;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear14() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (14 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:14");
        misc.tonnage = 14;
        misc.criticals = 14;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCommsGear15() {
        MiscType misc = new MiscType();

        misc.name = "Communications Equipment (15 ton)";
        misc.setInternalName(misc.name);
        misc.addLookupName("CommsGear:15");
        misc.tonnage = 15;
        misc.criticals = 15;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_COMMUNICATIONS).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        String[] modes =
            { "Default", "ECCM", "Ghost Targets" };
        misc.setModes(modes);
        misc.setInstantModeSwitch(false);
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createCompositeStructure() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_COMPOSITE);
        misc.setInternalName(EquipmentType.getStructureTypeName(T_STRUCTURE_COMPOSITE));
        misc.addLookupName("Composite");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.hittable = false;
        misc.bv = 0;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;

        return misc;
    }

    public static MiscType createIndustrialStructure() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_INDUSTRIAL);
        misc.setInternalName(EquipmentType.getStructureTypeName(T_STRUCTURE_INDUSTRIAL));
        misc.addLookupName("Industrial");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.bv = 0;
        misc.techLevel = TechConstants.T_TW_ALL;
        misc.industrial = true;
        misc.introDate = 2350;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_C, EquipmentType.RATING_C};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createCLLaserHeatSink() {
        MiscType misc = new MiscType();

        misc.name = "Laser Heat Sink";
        misc.setInternalName(misc.name);
        misc.addLookupName("CLLaser Heat Sink");
        misc.tonnage = 1.0f;
        misc.criticals = 2;
        misc.flags = misc.flags.or(F_DOUBLE_HEAT_SINK).or(F_LASER_HEAT_SINK).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;

        return misc;
    }

    // It is possible to have 1 or 2 compact heat sinks
    // in a single critical slot - this is addressed by
    // creating 1 slot single and 1 slot double heat sinks
    // with the weight of 1 or 2 compact heat sinks
    public static MiscType createIS1CompactHeatSink() {
        MiscType misc = new MiscType();

        misc.name = "1 Compact Heat Sink";
        misc.setInternalName(misc.name);
        misc.addLookupName("IS1 Compact Heat Sink");
        misc.tonnage = 1.5f;
        misc.criticals = 1;
        misc.flags = misc.flags.or(F_HEAT_SINK).or(F_COMPACT_HEAT_SINK);
        misc.bv = 0;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;

        return misc;
    }

    public static MiscType createIS2CompactHeatSinks() {
        MiscType misc = new MiscType();

        misc.name = "2 Compact Heat Sinks";
        misc.setInternalName(misc.name);
        misc.addLookupName("IS2 Compact Heat Sinks");
        misc.tonnage = 3.0f;
        misc.criticals = 1;
        misc.flags = misc.flags.or(F_DOUBLE_HEAT_SINK).or(F_COMPACT_HEAT_SINK);
        misc.bv = 0;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;

        return misc;
    }

    public static MiscType createISImprovedSensors() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Improved Sensors";
        misc.setInternalName(Sensor.ISIMPROVED);
        misc.tonnage = 0.0f;
        misc.cost = 200000;
        misc.flags = misc.flags.or(F_BAP).or(F_BA_EQUIPMENT);
        misc.introDate = 3051;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createCLImprovedSensors() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Improved Sensors";
        misc.setInternalName(Sensor.CLIMPROVED);
        misc.tonnage = 0.0f;
        misc.cost = 200000;
        misc.flags = misc.flags.or(F_BAP).or(F_BA_EQUIPMENT);
        misc.introDate = 2890;
        misc.availRating = new int[]{RATING_X,RATING_D,RATING_C};
        misc.techRating = RATING_F;

        return misc;
    }

    public static MiscType createBeagleActiveProbe() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Beagle Active Probe";
        misc.setInternalName(Sensor.BAP);
        misc.addLookupName("Beagle Active Probe");
        misc.addLookupName("ISBeagleActiveProbe");
        misc.addLookupName("IS Beagle Active Probe");
        misc.tonnage = 1.5f;
        misc.criticals = 2;
        misc.cost = 200000;
        misc.flags = misc.flags.or(F_BAP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 10;
        misc.availRating = new int[]{EquipmentType.RATING_E, EquipmentType.RATING_F, EquipmentType.RATING_D};
        misc.introDate = 2575;
        misc.extinctDate = 2835;
        misc.reintroDate = 3045;
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createBloodhoundActiveProbe() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Bloodhound Active Probe";
        misc.setInternalName(Sensor.BLOODHOUND);
        misc.addLookupName("Bloodhound Active Probe");
        misc.addLookupName("ISBloodhoundActiveProbe");
        misc.addLookupName("IS Bloodhound Active Probe");
        misc.tonnage = 2;
        misc.criticals = 3;
        misc.cost = 500000;
        misc.flags = misc.flags.or(F_BAP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 25;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_F};
        misc.introDate = 3058;

        return misc;
    }

    public static MiscType createTHBBloodhoundActiveProbe() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_UNOFFICIAL;
        misc.name = "Bloodhound Active Probe (THB)";
        misc.setInternalName("THBBloodhoundActiveProbe");
        misc.addLookupName("THB Bloodhound Active Probe");
        misc.addLookupName("ISTHBBloodhoundActiveProbe");
        misc.addLookupName("IS THB Bloodhound Active Probe");
        misc.tonnage = 5;
        misc.criticals = 2;
        misc.cost = 750000;
        misc.flags = misc.flags.or(F_BAP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 25;

        return misc;
    }

    public static MiscType createCLActiveProbe() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Active Probe";
        misc.setInternalName(Sensor.CLAN_AP);
        misc.addLookupName("Active Probe");
        misc.addLookupName("Clan Active Probe");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 200000;
        misc.flags = misc.flags.or(F_BAP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 12;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 2576;
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createCLLightActiveProbe() {
        MiscType misc = new MiscType();

        misc.name = "Light Active Probe";
        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.setInternalName(Sensor.LIGHT_AP);
        misc.addLookupName("CL Light Active Probe");
        misc.addLookupName("Light Active Probe");
        misc.addLookupName("Clan Light Active Probe");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_BAP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 7;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_D};
        misc.introDate = 3059;
        misc.techRating = RATING_F;
        return misc;
    }

    public static MiscType createCLBALightActiveProbe() {
        MiscType misc = new MiscType();

        misc.name = "Light Active Probe";
        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.setInternalName(Sensor.CLBALIGHT_AP);
        misc.tonnage = 0.15f;
        misc.criticals = 1;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_BAP).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 2900;
        misc.techRating = RATING_F;
        return misc;
    }

    public static MiscType createISBALightActiveProbe() {
        MiscType misc = new MiscType();

        misc.name = "Light Active Probe";
        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.setInternalName(Sensor.ISBALIGHT_AP);
        misc.tonnage = 0.25f;
        misc.criticals = 1;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_BAP).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 3050;
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createISAPPod() {
        MiscType misc = new MiscType();

        misc.name = "A-Pod";
        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.setInternalName("ISAntiPersonnelPod");
        misc.addLookupName("ISAPod");
        misc.addLookupName("IS A-Pod");
        misc.addLookupName("IS AP Pod");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 1500;
        misc.flags = misc.flags.or(F_AP_POD).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 1;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_D};
        misc.introDate = 3055;
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createCLAPPod() {
        MiscType misc = new MiscType();

        misc.name = "A-Pod";
        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.setInternalName("CLAntiPersonnelPod");
        misc.addLookupName("Clan A-Pod");
        misc.addLookupName("CL AP Pod");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 1500;
        misc.flags = misc.flags.or(F_AP_POD).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 1;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_D, EquipmentType.RATING_C};
        misc.introDate = 2850;
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createSearchlight() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Mounted Searchlight";
        misc.setInternalName("Searchlight");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_B};
        misc.techRating = RATING_A;
        misc.flags = misc.flags.or(F_SEARCHLIGHT).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.cost = 2000;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createBASearchlight() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Handheld Searchlight";
        misc.setInternalName("BASearchlight");
        misc.tonnage = 0.005f;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_A};
        misc.techRating = RATING_A;
        misc.introDate = 1950;
        misc.flags = misc.flags.or(F_BA_SEARCHLIGHT).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.cost = 500;


        return misc;
    }

    public static MiscType createBAArmoredGlove() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Armored Glove";
        misc.setInternalName("BAArmoredGlove");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_ARMORED_GLOVE).or(F_AP_MOUNT);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_D,RATING_D};
        misc.techRating = RATING_C;

        return misc;
    }

    public static MiscType createBABasicManipulator() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Basic Manipulator";
        misc.setInternalName("BABasicManipulator");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_BASIC_MANIPULATOR);
        misc.bv = 0;
        misc.introDate = 2110;
        misc.availRating = new int[]{RATING_C,RATING_D,RATING_C};
        misc.techRating = RATING_C;

        return misc;
    }

    public static MiscType createBABasicManipulatorMineClearance() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Basic Manipulator (w/ Mine Clearance)";
        misc.setInternalName("BABasicManipulatorMineClearance");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_TOOLS).or(F_BASIC_MANIPULATOR);
        misc.subType |= S_MINESWEEPER;
        misc.bv = 0;
        misc.introDate = 3057;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createBABattleClaw() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Battle Claw";
        misc.setInternalName("BABattleClaw");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_BATTLE_CLAW);
        misc.bv = 1;
        misc.introDate = 2868;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createBABattleClawMagnets() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Battle Magnetic Claw";
        misc.setInternalName("BABattleClawMagnets");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_MAGNET_CLAW).or(F_BA_EQUIPMENT).or(F_BATTLE_CLAW);
        misc.bv = 1.5;
        misc.introDate = 3055;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createBABattleClawVibro() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Battle Vibro Claw";
        misc.setInternalName("BABattleClawVibro");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_VIBROCLAW).or(F_BA_EQUIPMENT).or(F_BATTLE_CLAW);
        misc.bv = 1;
        misc.introDate = 3054;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createBACargoLifter() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Cargo Lifter";
        misc.setInternalName("BACargoLifter");
        misc.tonnage = 0.03f;
        misc.criticals = 0;
        misc.cost = 250;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_CARGOLIFTER);
        misc.bv = 0;
        misc.introDate = 2110;
        misc.availRating = new int[]{RATING_D,RATING_D,RATING_D};
        misc.techRating = RATING_C;

        return misc;
    }

    public static MiscType createBAHeavyBattleClaw() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Heavy Battle Claw";
        misc.setInternalName("BAHeavyBattleClaw");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_BATTLE_CLAW);
        misc.bv = 0;
        misc.introDate = 2868;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createBAHeavyBattleClawMagnet() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Heavy Battle Magnetic Claw";
        misc.addLookupName("Heavy Battle Claw (w/ Magnets)");
        misc.setInternalName("BAHeavyBattleClawMagnets");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_MAGNET_CLAW).or(F_BA_EQUIPMENT).or(F_BATTLE_CLAW);
        misc.bv = 1.5;
        misc.introDate = 3055;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createBAHeavyBattleClawVibro() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Heavy Battle Vibro Claw";
        misc.addLookupName("Heavy Battle Claw (w/ Vibro-Claws)");
        misc.setInternalName("BAHeavyBattleClawVibro");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_VIBROCLAW).or(F_BA_EQUIPMENT).or(F_BATTLE_CLAW);
        misc.bv = 1;
        misc.introDate = 3054;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createBAIndustrialDrill() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Industrial Drill";
        misc.setInternalName("BAIndustrialDrill");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_D,RATING_D};
        misc.techRating = RATING_C;

        return misc;
    }

    public static MiscType createBASalvageArm() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Salvage Arm";
        misc.setInternalName("BASalvageArm");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 2415;
        misc.availRating = new int[]{RATING_E,RATING_E,RATING_E};
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createBAAPMount() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Anti Personnel Weapon Mount";
        misc.setInternalName("BAAPMount");
        misc.tonnage = 0.0f;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_AP_MOUNT);
        misc.bv = 0;
        misc.introDate = 2870;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_C};
        misc.techRating = RATING_E;

        return misc;
    }

    public static MiscType createVacuumProtection() {
        MiscType misc = new MiscType();

        misc.name = "Vacuum Protection";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.cost = 0;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.flags = misc.flags.or(F_VACUUM_PROTECTION).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.techRating = RATING_C;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_A};

        return misc;
    }

    public static MiscType createEnvironmentalSealing() {
        MiscType misc = new MiscType();

        misc.name = "Environmental Sealing";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 8;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.spreadable = true;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.flags = misc.flags.or(F_ENVIRONMENTAL_SEALING).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.techRating = RATING_C;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_A};

        return misc;
    }

    public static MiscType createJumpBooster() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Jump Booster";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.bv = 0;
        misc.flags = misc.flags.or(F_JUMP_BOOSTER).or(F_MECH_EQUIPMENT);
        misc.spreadable = true;

        return misc;
    }

    public static MiscType createDemolitionCharge() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Demolition Charge";
        misc.setInternalName(misc.name);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_TOOLS).or(F_BA_EQUIPMENT);
        misc.subType |= S_DEMOLITION_CHARGE;
        misc.toHitModifier = 1;
        misc.bv = 0;
        misc.industrial = true;

        return misc;
    }

    public static MiscType createVibroShovel() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Vibro-Shovel";
        misc.setInternalName(misc.name);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_TOOLS).or(F_BA_EQUIPMENT);
        misc.subType |= S_VIBROSHOVEL;
        misc.toHitModifier = 1;
        misc.bv = 0;
        misc.industrial = true;

        return misc;
    }

    public static MiscType createBridgeKit() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Bridge Kit";
        misc.setInternalName(misc.name);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_TOOLS).or(F_BA_EQUIPMENT);
        misc.subType |= S_BRIDGE_KIT;
        misc.toHitModifier = 1;
        misc.bv = 0;
        misc.industrial = true;

        return misc;
    }

    public static MiscType createBARemoteSensorDispenser() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Remote Sensor Dispenser";
        misc.setInternalName("BARemoteSensorDispenser");
        misc.addLookupName("BA Remote Sensor Dispenser");
        misc.tonnage = 0.004f;
        misc.criticals = 1;
        misc.hittable = true;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_SENSOR_DISPENSER);
        misc.bv = 0;
        misc.techRating = RATING_D;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_D,RATING_D};
        misc.cost = 28500;

        return misc;
    }

    public static MiscType createRemoteSensorDispenser() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Remote Sensor Dispenser";
        misc.setInternalName("RemoteSensorDispenser");
        misc.addLookupName("Remote Sensor Dispenser");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.hittable = true;
        misc.flags = misc.flags.or(F_MECH_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_SUPPORT_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT).or(F_SENSOR_DISPENSER);
        misc.bv = 0;
        misc.cost = 51000;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.techRating = RATING_C;
        misc.availRating = new int[]{ RATING_F, RATING_F, RATING_D };

        return misc;
    }

    public static MiscType createISSmallShield() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Small Shield";
        misc.setInternalName("ISSmallShield");
        misc.addLookupName("Small Shield");
        misc.tonnage = 2;
        misc.criticals = 3;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_SHIELD_SMALL;
        misc.bv = 50;
        misc.setInstantModeSwitch(true);
        String[] modes =
            { S_NO_SHIELD, S_ACTIVE_SHIELD, S_PASSIVE_SHIELD };
        misc.setModes(modes);
        misc.damageTaken = 0;
        misc.baseDamageAbsorptionRate = 3;
        misc.baseDamageCapacity = 11;

        return misc;
    }

    /**
     * Creates a claw MiscType Object
     *
     * @return MiscType
     */
    public static MiscType createISClaw() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Claw";
        misc.setInternalName("ISClaw");
        misc.addLookupName("Claw");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_HAND_WEAPON).or(F_MECH_EQUIPMENT);
        misc.subType |= S_CLAW;
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createISMediumShield() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Medium Shield";
        misc.setInternalName("ISMediumShield");
        misc.addLookupName("Medium Shield");
        misc.tonnage = 4;
        misc.criticals = 5;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_SHIELD_MEDIUM;
        misc.bv = 135;
        misc.setInstantModeSwitch(true);
        String[] modes =
            { S_NO_SHIELD, S_ACTIVE_SHIELD, S_PASSIVE_SHIELD };
        misc.setModes(modes);
        misc.damageTaken = 0;
        misc.baseDamageAbsorptionRate = 5;
        misc.baseDamageCapacity = 18;

        return misc;
    }

    public static MiscType createISLargeShield() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Large Shield";
        misc.setInternalName("ISLargeShield");
        misc.addLookupName("Large Shield");
        misc.tonnage = 6;
        misc.criticals = 7;
        misc.cost = 300000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_SHIELD_LARGE;
        misc.bv = 263;
        misc.setInstantModeSwitch(true);
        String[] modes =
            { S_NO_SHIELD, S_ACTIVE_SHIELD, S_PASSIVE_SHIELD };
        misc.setModes(modes);
        misc.damageTaken = 0;
        misc.baseDamageAbsorptionRate = 7;
        misc.baseDamageCapacity = 25;

        return misc;
    }

    public static MiscType createCLHarJel() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "HarJel";
        misc.setInternalName("Clan HarJel");
        misc.addLookupName("Clan HarJel");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 120000;
        misc.flags = misc.flags.or(F_HARJEL).or(F_MECH_EQUIPMENT).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};
        misc.introDate = 2840;
        misc.techRating = RATING_F;

        return misc;
    }

    public static MiscType createISHarJel() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "HarJel";
        misc.setInternalName("IS HarJel");
        misc.addLookupName("IS HarJel");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 120000;
        misc.flags = misc.flags.or(F_HARJEL).or(F_MECH_EQUIPMENT).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};
        misc.introDate = 3067;
        misc.techRating = RATING_F;

        return misc;
    }

    public static MiscType createISAES() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "AES";
        misc.setInternalName("ISAES");
        misc.addLookupName("IS Actuator Enhancement System");
        misc.addLookupName("ISActuatorEnhancementSystem");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_ACTUATOR_ENHANCEMENT_SYSTEM).or(F_MECH_EQUIPMENT);
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createCLAES() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "AES";
        misc.setInternalName("CLAES");
        misc.addLookupName("CL Actuator Enhancement System");
        misc.addLookupName("CLActuatorEnhancementSystem");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_ACTUATOR_ENHANCEMENT_SYSTEM).or(F_MECH_EQUIPMENT);
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createISUMU() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "UMU";
        misc.setInternalName("ISUMU");
        misc.addLookupName("IS Underwater Maneuvering Unit");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.flags = misc.flags.or(F_UMU).or(F_MECH_EQUIPMENT);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createCLUMU() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "UMU";
        misc.setInternalName("CLUMU");
        misc.addLookupName("Clan Underwater Maneuvering Unit");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.flags = misc.flags.or(F_UMU).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 3059;

        return misc;
    }

    public static MiscType createISLance() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Lance";
        misc.setInternalName("IS Lance");
        misc.addLookupName("ISLance");
        misc.addLookupName("Lance");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_LANCE;
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createISFlail() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Flail";
        misc.setInternalName("IS Flail");
        misc.addLookupName("Flail");
        misc.tonnage = 5;
        misc.criticals = 4;
        misc.cost = 110000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_FLAIL;
        misc.bv = 11;

        return misc;
    }

    public static MiscType createISWreckingBall() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Wrecking Ball";
        misc.setInternalName("IS Wrecking Ball");
        misc.addLookupName("WreckingBall");
        misc.tonnage = 4;
        misc.criticals = 5;
        misc.cost = 110000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_WRECKING_BALL;
        misc.bv = 8;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_C,RATING_C,RATING_C};
        misc.techRating = RATING_A;
        return misc;
    }

    public static MiscType createCLWreckingBall() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Wrecking Ball";
        misc.setInternalName("Clan Wrecking Ball");
        misc.addLookupName("CLWrecking Ball");
        misc.tonnage = 4;
        misc.criticals = 5;
        misc.cost = 110000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.subType |= S_WRECKING_BALL;
        misc.bv = 8;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_X,RATING_C,RATING_C};
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createISSmallVibroblade() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Small Vibroblade";
        misc.setInternalName("ISSmallVibroblade");
        misc.addLookupName("Small Vibroblade");
        misc.tonnage = 3;
        misc.criticals = 1;
        misc.cost = 150000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_VIBRO_SMALL;
        misc.bv = 12;
        misc.setInstantModeSwitch(true);
        String[] modes =
            { "Inactive", "Active" };
        misc.setModes(modes);

        return misc;
    }

    public static MiscType createISMediumVibroblade() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Medium Vibroblade";
        misc.setInternalName("ISMediumVibroblade");
        misc.addLookupName("Medium Vibroblade");
        misc.tonnage = 5;
        misc.criticals = 2;
        misc.cost = 400000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_VIBRO_MEDIUM;
        misc.bv = 17;
        misc.setInstantModeSwitch(true);
        String[] modes =
            { "Inactive", "Active" };
        misc.setModes(modes);

        return misc;
    }

    public static MiscType createISLargeVibroblade() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Large Vibroblade";
        misc.setInternalName("ISLargeVibroblade");
        misc.addLookupName("Large Vibroblade");
        misc.tonnage = 7;
        misc.criticals = 4;
        misc.cost = 750000;
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_VIBRO_LARGE;
        misc.bv = 24;
        misc.setInstantModeSwitch(true);
        String[] modes =
            { "Inactive", "Active" };
        misc.setModes(modes);

        return misc;
    }

    public static MiscType createISBuzzsaw() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Buzzsaw";
        misc.setInternalName(misc.name);
        misc.addLookupName("IS Buzzsaw");
        misc.tonnage = 4;
        misc.criticals = 2;
        misc.cost = 100000;// From the Ask the Writer Forum
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_BUZZSAW;
        misc.bv = 67;// From the Ask the Writer Forum

        return misc;
    }

    public static MiscType createCLBuzzsaw() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Buzzsaw";
        misc.setInternalName("CLBuzzsaw");
        misc.addLookupName("Clan Buzzsaw");
        misc.tonnage = 4;
        misc.criticals = 2;
        misc.cost = 100000;// From the Ask the Writer Forum
        misc.flags = misc.flags.or(F_CLUB).or(F_MECH_EQUIPMENT);
        misc.subType |= S_BUZZSAW;
        misc.bv = 6;// From the Ask the Writer Forum

        return misc;
    }

    public static MiscType createCoolantSystem() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Coolant System";
        misc.setInternalName(misc.name);
        misc.tonnage = 9;
        misc.criticals = 2;
        misc.cost = 90000;
        misc.flags = misc.flags.or(F_COOLANT_SYSTEM).or(F_MECH_EQUIPMENT);
        misc.bv = 15;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};
        misc.introDate = 3049;

        return misc;
    }

    public static MiscType createSpikes() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Spikes";
        misc.setInternalName(misc.name);
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_SPIKES).or(F_MECH_EQUIPMENT);
        misc.bv = 4;

        return misc;
    }

    public static MiscType createTalons() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Talons";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.spreadable = true;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_TALON).or(F_MECH_EQUIPMENT);
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createHeavyArmor() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Heavy Armor";
        misc.setInternalName(misc.name);
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_TOOLS);
        misc.subType = S_HEAVY_ARMOR;
        misc.bv = 15;

        return misc;
    }

    public static MiscType createStandard() {
        // This is not really a single piece of equipment, it is used to
        // identify "standard" internal structure, armor, whatever.
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getStructureTypeName(T_STRUCTURE_STANDARD);
        misc.setInternalName(EquipmentType.getStructureTypeName(T_STRUCTURE_STANDARD));
        misc.addLookupName("Regular");
        misc.addLookupName("Standard Armor");

        return misc;
    }

    public static MiscType createISPPCCapacitor() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "PPC Capacitor";
        misc.setInternalName(misc.name);
        misc.addLookupName("ISPPCCapacitor");
        misc.addLookupName("LPPC Capacitor");
        misc.addLookupName("ISLightPPCCapacitor");
        misc.addLookupName("SNPPC Capacitor");
        misc.addLookupName("ISSNPPCCapacitor");
        misc.addLookupName("ERPPC Capacitor");
        misc.addLookupName("ISERPPCCapacitor");
        misc.addLookupName("HPPC Capacitor");
        misc.addLookupName("ISHeavyPPCCapacitor");
        misc.tonnage = 1.0f;
        misc.criticals = 1;
        misc.tankslots = 0;
        misc.cost = 150000;
        misc.setModes(new String[]
            { "Off", "Charge" });
        misc.flags = misc.flags.or(F_PPC_CAPACITOR).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.setInstantModeSwitch(false);
        misc.explosive = true;
        // misc.bv = 88;
        misc.bv = 0;
        return misc;
    }

    public static MiscType createReflective() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_REFLECTIVE);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_REFLECTIVE));
        misc.addLookupName("Reflective Armor");
        misc.addLookupName("Reflective");
        misc.tonnage = 0;
        misc.criticals = CRITICALS_VARIABLE;
        misc.hittable = false;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_REFLECTIVE);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createReactive() {
        MiscType misc = new MiscType();

        misc.name = EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_REACTIVE);
        misc.setInternalName(EquipmentType.getArmorTypeName(EquipmentType.T_ARMOR_REACTIVE));
        misc.addLookupName("Reactive Armor");
        misc.addLookupName("Reactive");
        misc.tonnage = 0;
        misc.criticals = CRITICALS_VARIABLE;
        misc.spreadable = true;
        misc.flags = misc.flags.or(F_REACTIVE);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createISModularArmor() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Modular Armor";
        misc.setInternalName("ISModularArmor");
        misc.setInternalName("IS Modular Armor");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_MODULAR_ARMOR).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = BV_VARIABLE;
        misc.damageTaken = 0;
        misc.baseDamageAbsorptionRate = 10;
        misc.baseDamageCapacity = 10;

        return misc;
    }

    public static MiscType createCLModularArmor() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Modular Armor";
        misc.setInternalName("CLModularArmor");
        misc.addLookupName("Clan Modular Armor");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 100000;
        misc.flags = misc.flags.or(F_MODULAR_ARMOR).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.bv = BV_VARIABLE;
        misc.damageTaken = 0;
        misc.baseDamageAbsorptionRate = 10;
        misc.baseDamageCapacity = 10;

        return misc;
    }

    public static MiscType createSalvageArm() {
        MiscType misc = new MiscType();

        misc.name = "Salvage Arm";
        misc.setInternalName(misc.name);
        misc.addLookupName("SalvageArm");
        misc.tonnage = 3;
        misc.criticals = 2;
        misc.bv = 0;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_SALVAGE_ARM).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{RATING_E,RATING_F,RATING_E};
        misc.introDate = 2000;
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createTracks() {
        MiscType misc = new MiscType();

        misc.name = "Tracks";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = CRITICALS_VARIABLE;
        misc.spreadable = true;
        misc.bv = 0;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_TRACKS).or(F_MECH_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.introDate = 2440;
        misc.availRating = new int[]{RATING_D,RATING_E,RATING_E};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createISMASS() {
        MiscType misc = new MiscType();

        misc.name = "MASS";
        misc.setInternalName("ISMASS");
        misc.addLookupName("IS Mass");
        misc.tonnage = 1.5f;
        misc.criticals = 1;
        misc.bv = 9;
        misc.cost = 4000;
        misc.flags = misc.flags.or(F_MASS).or(F_MECH_EQUIPMENT);
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_D};
        misc.introDate = 3048;

        return misc;
    }

    public static MiscType createCLMASS() {
        MiscType misc = new MiscType();

        misc.name = "MASS";
        misc.setInternalName("CLMASS");
        misc.addLookupName("Clan Mass");
        misc.tonnage = 1.5f;
        misc.criticals = 1;
        misc.bv = 9;
        misc.cost = 4000;
        misc.flags = misc.flags.or(F_MASS).or(F_MECH_EQUIPMENT);
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_D};
        misc.introDate = 3062;

        return misc;
    }

    public static MiscType createCLPartialWing() {
        MiscType misc = new MiscType();

        misc.name = "Partial Wing";
        misc.setInternalName("CLPartialWing");
        misc.addLookupName("PartialWing");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 6;
        misc.spreadable = true;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_PARTIAL_WING).or(F_MECH_EQUIPMENT);
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;


        return misc;
    }

    public static MiscType createISPartialWing() {
        MiscType misc = new MiscType();

        misc.name = "Partial Wing";
        misc.setInternalName("ISPartialWing");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 8;
        misc.spreadable = true;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_PARTIAL_WING).or(F_MECH_EQUIPMENT);
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_F};

        return misc;
    }

    public static MiscType createCargo1() {
        MiscType misc = new MiscType();

        misc.name = "Cargo (1 ton)";
        misc.setInternalName(misc.name);
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_CARGO).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createHalfCargo() {
        MiscType misc = new MiscType();

        misc.name = "Cargo (0.5 tons)";
        misc.setInternalName(misc.name);
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_CARGO).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createLiquidCargo1() {
        MiscType misc = new MiscType();

        misc.name = "Liquid Storage (1 ton)";
        misc.setInternalName(misc.name);
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_LIQUID_CARGO).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createHalfLiquidCargo() {
        MiscType misc = new MiscType();

        misc.name = "Liquid Storage (0.5 tons)";
        misc.setInternalName(misc.name);
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_LIQUID_CARGO).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createCargoContainer() {
        MiscType misc = new MiscType();

        misc.name = "Cargo Container (10 tons)";
        misc.setInternalName(misc.name);
        misc.tonnage = 10;
        misc.criticals = 1;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_CARGO).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createMechSprayer() {
        MiscType misc = new MiscType();

        misc.name = "Sprayer";
        misc.setInternalName("MechSprayer");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 1000;
        misc.flags = misc.flags.or(F_MECH_EQUIPMENT).or(F_SPRAYER);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_B, EquipmentType.RATING_B, EquipmentType.RATING_B};
        misc.introDate = 1950;
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createTankSprayer() {
        MiscType misc = new MiscType();

        misc.name = "Sprayer";
        misc.setInternalName("Tank Sprayer");
        misc.tonnage = 0.015f;
        misc.criticals = 0;
        misc.cost = 1000;
        misc.flags = misc.flags.or(F_TANK_EQUIPMENT).or(F_SPRAYER);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_B, EquipmentType.RATING_B, EquipmentType.RATING_B};
        misc.introDate = 1950;
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createFrontDumper() {
        MiscType misc = new MiscType();

        misc.name = "Dumper (Front)";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = 5000;
        misc.flags = misc.flags.or(F_DUMPER).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_A};
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createRearDumper() {
        MiscType misc = new MiscType();

        misc.name = "Dumper (Rear)";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = 5000;
        misc.flags = misc.flags.or(F_DUMPER).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_A};
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createRightDumper() {
        MiscType misc = new MiscType();

        misc.name = "Dumper (Right)";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = 5000;
        misc.flags = misc.flags.or(F_DUMPER).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_A};
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createLeftDumper() {
        MiscType misc = new MiscType();

        misc.name = "Dumper (Left)";
        misc.setInternalName(misc.name);
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = 5000;
        misc.flags = misc.flags.or(F_DUMPER).or(F_TANK_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_A};
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createMASH() {
        MiscType misc = new MiscType();

        misc.name = "MASH core component";
        misc.setInternalName(misc.name);
        misc.tonnage = 3.5f;
        misc.criticals = 1;
        misc.cost = 35000;
        misc.flags = misc.flags.or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_MASH);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_E, EquipmentType.RATING_D};
        misc.introDate = 1950;
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createMASHExtraTheater() {
        MiscType misc = new MiscType();

        misc.name = "MASH Operation Theater";
        misc.setInternalName(misc.name);
        misc.tonnage = 1f;
        misc.criticals = 1;
        misc.tankslots = 0;
        misc.cost = 10000;
        misc.flags = misc.flags.or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_MASH_EXTRA);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_E, EquipmentType.RATING_D};
        misc.introDate = 1950;
        misc.industrial = true;
        misc.techRating = RATING_B;

        return misc;
    }

    public static MiscType createParamedicEquipment() {
        MiscType misc = new MiscType();

        misc.name = "Paramedic Equipment";
        misc.setInternalName(misc.name);
        misc.tonnage = 0.25f;
        misc.criticals = 1;
        misc.cost = 7500;
        misc.flags = misc.flags.or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_MECH_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_C, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_C;

        return misc;
    }

    public static MiscType createCLMastMount() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Mast Mount";
        misc.setInternalName("CLMastMount");
        misc.tonnage = 0.5f;
        misc.tankslots = 0;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_MAST_MOUNT).or(F_VTOL_EQUIPMENT);
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createISMastMount() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Mast Mount";
        misc.setInternalName("ISMastMount");
        misc.tonnage = 0.5f;
        misc.tankslots = 0;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_MAST_MOUNT).or(F_VTOL_EQUIPMENT);
        misc.bv = BV_VARIABLE;

        return misc;
    }

    public static MiscType createFuel1() {
        MiscType misc = new MiscType();

        misc.name = "Extended Fuel Tank (1 ton)";
        misc.setInternalName(misc.name);
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 500;
        misc.flags = misc.flags.or(F_FUEL).or(F_MECH_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.explosive = true;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_C,RATING_D,RATING_D};
        misc.techRating = RATING_C;

        return misc;
    }

    public static MiscType createFuelHalf() {
        MiscType misc = new MiscType();

        misc.name = "Extended Fuel Tank (0.5 tons)";
        misc.setInternalName(misc.name);
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 500;
        misc.flags = misc.flags.or(F_FUEL).or(F_MECH_EQUIPMENT);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.explosive = true;
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_C,RATING_D,RATING_D};
        misc.techRating = RATING_C;

        return misc;
    }

    public static MiscType createBlueShield() {
        MiscType misc = new MiscType();
        misc.name = "Blue Shield Particle Field Damper";
        misc.setInternalName(misc.name);
        misc.setModes(new String[]
            { "Off", "On" });
        misc.instantModeSwitch = false;
        misc.explosive = true;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.tonnage = 3;
        misc.criticals = 7;
        misc.spreadable = true;
        misc.cost = 1000000;
        misc.flags = misc.flags.or(F_BLUE_SHIELD).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);

        return misc;
    }

    public static MiscType createBasicFireControl() {
        MiscType misc = new MiscType();
        misc.name = "Basic Fire Control";
        misc.setInternalName(misc.name);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.flags = misc.flags.or(MiscType.F_BASIC_FIRECONTROL).or(MiscType.F_SUPPORT_TANK_EQUIPMENT);
        misc.industrial = true;
        misc.introDate = 2400;
        misc.availRating = new int[]{RATING_B,RATING_C,RATING_C};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createAdvancedFireControl() {
        MiscType misc = new MiscType();
        misc.name = "Advanced Fire Control";
        misc.setInternalName(misc.name);
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.flags = misc.flags.or(MiscType.F_ADVANCED_FIRECONTROL).or(MiscType.F_SUPPORT_TANK_EQUIPMENT);
        misc.introDate = 2439;
        misc.availRating = new int[]{RATING_C,RATING_D,RATING_D};
        misc.techRating = RATING_D;

        return misc;
    }

    public static MiscType createISLaserInsulator() {
        MiscType misc = new MiscType();
        misc.name = "Laser Insulator";
        misc.setInternalName("ISLaserInsulator");
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 3500;
        misc.flags = misc.flags.or(MiscType.F_LASER_INSULATOR).or(MiscType.F_SUPPORT_TANK_EQUIPMENT).or(MiscType.F_MECH_EQUIPMENT).or(MiscType.F_AERO_EQUIPMENT).or(MiscType.F_TANK_EQUIPMENT);

        return misc;
    }

    public static MiscType createCLLaserInsulator() {
        MiscType misc = new MiscType();
        misc.name = "Laser Insulator";
        misc.setInternalName("CLLaserInsulator");
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 3500;
        misc.flags = misc.flags.or(MiscType.F_LASER_INSULATOR).or(MiscType.F_SUPPORT_TANK_EQUIPMENT).or(MiscType.F_MECH_EQUIPMENT).or(MiscType.F_AERO_EQUIPMENT).or(MiscType.F_TANK_EQUIPMENT);

        return misc;
    }

    public static MiscType createISEWEquipment() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Electronic Warfare Equipment";
        misc.setInternalName(Sensor.EW_EQUIPMENT);
        misc.tonnage = 7.5f;
        misc.criticals = 4;
        misc.cost = 500000;
        misc.flags = misc.flags.or(F_EW_EQUIPMENT).or(F_BAP).or(F_ECM).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 39;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_F, EquipmentType.RATING_E};
        misc.introDate = 3025;

        return misc;
    }

    public static MiscType createISCollapsibleCommandModule() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Collapsible Command Module";
        misc.setInternalName("ISCollapsibleCommandModule");
        misc.addLookupName("ISCCM");
        misc.addLookupName("CollapsibleCommandModule");
        misc.tonnage = 16f;
        misc.criticals = 12;
        misc.cost = 500000;
        misc.flags = misc.flags.or(F_CCM).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_E, EquipmentType.RATING_F, EquipmentType.RATING_E};
        misc.introDate = 2710;

        return misc;
    }

    public static MiscType createHitch() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Trailer Hitch";
        misc.setInternalName("Hitch");
        misc.tonnage = 0f;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_HITCH).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_A, EquipmentType.RATING_A, EquipmentType.RATING_A};
        misc.introDate = 1950;
        misc.techRating = RATING_A;

        return misc;
    }

    public static MiscType createISFlotationHull() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Flotation Hull";
        misc.setInternalName("ISFlotationHull");
        misc.tonnage = 0f;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_FLOTATION_HULL).or(F_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createISLimitedAmphibiousChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Limited Amphibious";
        misc.setInternalName("ISLimitedAmphibiousChassis");
        misc.addLookupName("ISLimitedAmphibious");
        misc.tonnage = EquipmentType.TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_LIMITED_AMPHIBIOUS).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createISFullyAmphibiousChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Fully Amphibious";
        misc.setInternalName("ISFullyAmphibiousChassis");
        misc.addLookupName("ISFullyAmphibious");
        misc.tonnage = EquipmentType.TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_FULLY_AMPHIBIOUS).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createAmphibiousChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Amphibious";
        misc.setInternalName("AmphibiousChassis");
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_AMPHIBIOUS).or(F_SUPPORT_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createISDuneBuggyChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Dune Buggy";
        misc.setInternalName("ISDuneBuggyChassis");
        misc.addLookupName("ISDuneBuggy");
        misc.tonnage = 0f;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_DUNE_BUGGY).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createISOffRoadChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Off-Road";
        misc.setInternalName("ISOffRoadChassis");
        misc.addLookupName("ISOffRoad");
        misc.tonnage = 0f;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_OFF_ROAD).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createCLOffRoadChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Off-Road";
        misc.setInternalName("ClanOffRoadChassis");
        misc.addLookupName("CLOffRoad");
        misc.tonnage = 0f;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_OFF_ROAD).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createCLFlotationHull() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Flotation Hull";
        misc.setInternalName("ClanFlotationHull");
        misc.tonnage = 0f;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_FLOTATION_HULL).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createCLLimitedAmphibiousChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Limited Amphibious";
        misc.setInternalName("ClanLimitedAmphibiousChassis");
        misc.addLookupName("ClanLimitedAmphibious");
        misc.tonnage = EquipmentType.TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_LIMITED_AMPHIBIOUS).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createCLFullyAmphibiousChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Fully Amphibious";
        misc.setInternalName("ClanFullyAmphibiousChassis");
        misc.addLookupName("ClanFullyAmphibious");
        misc.tonnage = EquipmentType.TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_FULLY_AMPHIBIOUS).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createCLDuneBuggyChassis() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Dune Buggy";
        misc.setInternalName("ClanDuneBuggyChassis");
        misc.addLookupName("ClanDuneBuggy");
        misc.tonnage = 0f;
        misc.criticals = 0;
        misc.cost = EquipmentType.COST_VARIABLE;
        misc.flags = misc.flags.or(F_DUNE_BUGGY).or(F_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;

        return misc;
    }

    public static MiscType createCLShoulderTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Shoulder Turret";
        misc.setInternalName("CLShoulderTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_SHOULDER_TURRET).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISShoulderTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Shoulder Turret";
        misc.setInternalName("ISShoulderTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_SHOULDER_TURRET).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createCLHeadTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Head Turret";
        misc.setInternalName("CLHeadTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_HEAD_TURRET).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISHeadTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Head Turret";
        misc.setInternalName("ISHeadTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_HEAD_TURRET).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createCLQuadTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Quad Turret";
        misc.setInternalName("CLQuadTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_QUAD_TURRET).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISQuadTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Quad Turret";
        misc.setInternalName("ISQuadTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 1;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_QUAD_TURRET).or(F_MECH_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createBASpaceOperationsAdaptation() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Space Operations Adaptation";
        misc.setInternalName("BASpaceOperationsAdaptation");
        misc.tonnage = 0.1f;
        misc.criticals = 1;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_SPACE_ADAPTATION).or(F_BA_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 3011;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_F;
        return misc;
    }

    public static MiscType createBACuttingTorch() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Cutting Torch";
        misc.setInternalName("BACuttingTorch");
        misc.tonnage = 0.005f;
        misc.criticals = 1;
        misc.cost = 1000;
        misc.flags = misc.flags.or(F_CUTTING_TORCH).or(F_BA_EQUIPMENT);
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_B,RATING_B,RATING_B};
        misc.techRating = RATING_C;
        misc.bv = 0;

        return misc;
    }

    public static MiscType createISMineSweeper() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Mine Sweeper";
        misc.setInternalName("ISMineSweeper");
        misc.tonnage = 3f;
        misc.criticals = 1;
        misc.cost = 40000;
        misc.flags = misc.flags.or(F_MINESWEEPER).or(F_TANK_EQUIPMENT);
        misc.bv = 30;
        return misc;
    }

    public static MiscType createClanMineSweeper() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Mine Sweeper";
        misc.setInternalName("ClanMineSweeper");
        misc.tonnage = 3f;
        misc.criticals = 1;
        misc.cost = 40000;
        misc.flags = misc.flags.or(F_MINESWEEPER).or(F_TANK_EQUIPMENT);
        misc.bv = 30;
        return misc;
    }

    public static MiscType createISMobileHPG() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Mobile HPG";
        misc.setInternalName("ISMobileHPG");
        misc.tonnage = 50f;
        misc.criticals = 50;
        misc.cost = 1000000000;
        misc.flags = misc.flags.or(F_MOBILE_HPG).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createClanMobileHPG() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Mobile HPG";
        misc.setInternalName("ClanMobileHPG");
        misc.tonnage = 50f;
        misc.criticals = 50;
        misc.cost = 1000000000;
        misc.flags = misc.flags.or(F_MOBILE_HPG).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISGroundMobileHPG() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Ground-Mobile HPG";
        misc.setInternalName("ISGroundMobileHPG");
        misc.tonnage = 12f;
        misc.criticals = 12;
        misc.cost = 4000000000f;
        misc.flags = misc.flags.or(F_MOBILE_HPG).or(F_TANK_EQUIPMENT).or(F_MECH_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_SPLITABLE);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_F, EquipmentType.RATING_X, EquipmentType.RATING_F};
        misc.introDate = 2751;
        return misc;
    }

    public static MiscType createClanGroundMobileHPG() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Ground-Mobile HPG";
        misc.setInternalName("ClanGroundMobileHPG");
        misc.tonnage = 12f;
        misc.criticals = 12;
        misc.cost = 4000000000f;
        misc.flags = misc.flags.or(F_MOBILE_HPG).or(F_TANK_EQUIPMENT).or(F_MECH_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_SPLITABLE);
        misc.bv = 0;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_F, EquipmentType.RATING_F};
        misc.introDate = 2751;
        return misc;
    }

    public static MiscType createFieldKitchen() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Field Kitchen";
        misc.setInternalName("FieldKitchen");
        misc.tonnage = 3f;
        misc.cost = 25000;
        misc.flags = misc.flags.or(F_FIELD_KITCHEN).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.industrial = true;
        misc.availRating = new int[]{EquipmentType.RATING_C, EquipmentType.RATING_C, EquipmentType.RATING_C};
        misc.introDate = 1950;
        misc.techRating = RATING_A;
        return misc;
    }

    public static MiscType createISMobileFieldBase() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Mobile Field Base";
        misc.setInternalName("ISMobileFieldBase");
        misc.tonnage = 20f;
        misc.cost = 150000;
        misc.flags = misc.flags.or(F_MOBILE_FIELD_BASE).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createCLMobileFieldBase() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Mobile Field Base";
        misc.setInternalName("CLMobileFieldBase");
        misc.tonnage = 20f;
        misc.cost = 150000;
        misc.flags = misc.flags.or(F_MOBILE_FIELD_BASE).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createCLHIResImager() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "High-Resolution Imager";
        misc.setInternalName("CLHighResImager");
        misc.tonnage = 2.5f;
        misc.cost = 150000;
        misc.flags = misc.flags.or(F_HIRES_IMAGER).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISHIResImager() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "High-Resolution Imager";
        misc.setInternalName("ISHighResImager");
        misc.tonnage = 2.5f;
        misc.cost = 150000;
        misc.flags = misc.flags.or(F_HIRES_IMAGER).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISHyperspectralImager() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Hyperspectral Imager";
        misc.setInternalName("ISHypersprectralImager");
        misc.addLookupName("ISHyperspectralImager");
        misc.tonnage = 7.5f;
        misc.cost = 550000;
        misc.flags = misc.flags.or(F_HIRES_IMAGER).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createCLInfraredImager() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Infrared Imager";
        misc.setInternalName("CLInfraredImager");
        misc.tonnage = 5f;
        misc.cost = 250000;
        misc.flags = misc.flags.or(F_INFRARED_IMAGER).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISInfraredImager() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Infrared Imager";
        misc.setInternalName("ISInfraredImager");
        misc.tonnage = 5f;
        misc.cost = 250000;
        misc.flags = misc.flags.or(F_INFRARED_IMAGER).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createCLLookDownRadar() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Look-Down Rader";
        misc.setInternalName("CLLookDownRadar");
        misc.tonnage = 5f;
        misc.cost = 400000;
        misc.flags = misc.flags.or(F_LOOKDOWN_RADAR).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_X,RATING_D,RATING_D};
        return misc;
    }

    public static MiscType createISLookDownRadar() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Look-Down Rader";
        misc.setInternalName("ISLookDownRadar");
        misc.tonnage = 5f;
        misc.cost = 400000;
        misc.flags = misc.flags.or(F_LOOKDOWN_RADAR).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_X,RATING_E,RATING_D};
        return misc;
    }

    public static MiscType createSTOLChassisMod() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "STOL";
        misc.setInternalName("STOLChassisMod");
        misc.tonnage = 0f;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_STOL_CHASSIS).or(F_SUPPORT_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_C,RATING_D,RATING_C};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createVSTOLChassisMod() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "VSTOL";
        misc.setInternalName("VSTOLChassisMod");
        misc.tonnage = 0f;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_VSTOL_CHASSIS).or(F_SUPPORT_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_C,RATING_D,RATING_C};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createCLTankCommandConsole() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Cockpit Command Console";
        misc.setInternalName("CLTankCockpitCommandConsole");
        misc.tonnage = 3f;
        misc.cost = 500000;
        misc.flags = misc.flags.or(F_COMMAND_CONSOLE).or(F_TANK_EQUIPMENT).or(F_SUPPORT_TANK_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISTankCommandConsole() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Cockpit Command Console";
        misc.setInternalName("ISTankCockpitCommandConsole");
        misc.tonnage = 3f;
        misc.cost = 500000;
        misc.flags = misc.flags.or(F_COMMAND_CONSOLE).or(F_TANK_EQUIPMENT).or(F_SUPPORT_TANK_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISSponsonTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Sponson Turret";
        misc.setInternalName("ISSponsonTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.hittable = false;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_SPONSON_TURRET).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createCLSponsonTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Sponson Turret";
        misc.setInternalName("CLSponsonTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.hittable = false;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_SPONSON_TURRET).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createPintleTurret() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Pintle Turret";
        misc.setInternalName("PintleTurret");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.hittable = false;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_PINTLE_TURRET).or(F_SUPPORT_TANK_EQUIPMENT);
        misc.bv = 0;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_A,RATING_A,RATING_A};
        misc.techRating = RATING_A;
        return misc;
    }

    public static MiscType createISArmoredMotiveSystem() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Armored Motive System";
        misc.setInternalName("ISArmoredMotiveSystem");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_ARMORED_MOTIVE_SYSTEM).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createCLArmoredMotiveSystem() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "Armored Motive System";
        misc.setInternalName("CLArmoredMotiveSystem");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.criticals = 0;
        misc.tankslots = 0;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_ARMORED_MOTIVE_SYSTEM).or(F_TANK_EQUIPMENT);
        misc.bv = 0;
        return misc;
    }

    public static MiscType createISChaffPod() {
        // TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Chaff Pod";
        misc.setInternalName("ISChaffPod");
        misc.tonnage = 1;
        misc.criticals = 1;
        misc.cost = 2000;
        misc.flags = misc.flags.or(F_CHAFF_POD).or(F_TANK_EQUIPMENT).or(F_MECH_EQUIPMENT).or(F_AERO_EQUIPMENT);
        misc.bv = 19;
        misc.availRating = new int[]{EquipmentType.RATING_X, EquipmentType.RATING_X, EquipmentType.RATING_E};
        misc.introDate = 3069;
        return misc;
    }

    public static MiscType createCLDroneCarrierControlSystem() {
        // TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Drone Carrier Control System";
        misc.setInternalName("CLDroneCarrierControlSystem");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_DRONE_CARRIER_CONTROL).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createISDroneCarrierControlSystem() {
        // TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Drone Carrier Control System";
        misc.setInternalName("ISDroneCarrierControlSystem");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_DRONE_CARRIER_CONTROL).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createISDroneExtra() {
        // TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Drone Extra Equipment";
        misc.setInternalName("ISDroneExtra");
        misc.tonnage = 0;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_DRONE_EXTRA).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createCLDroneExtra() {
        // TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Drone Extra Equipment";
        misc.setInternalName("CLDroneExtra");
        misc.tonnage = 0;
        misc.cost = 0;
        misc.flags = misc.flags.or(F_DRONE_EXTRA).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createCLDroneOperatingSystem() {
        // TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Drone (Remote) Operating System";
        misc.setInternalName("CLDroneOperatingSystem");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.criticals = 1;
        misc.flags = misc.flags.or(F_DRONE_OPERATING_SYSTEM).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_MECH_EQUIPMENT);
        misc.availRating = new int[]
            { RATING_E, RATING_F, RATING_F };
        return misc;
    }

    public static MiscType createISDroneOperatingSystem() {
        // TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Drone (Remote) Operating System";
        misc.setInternalName("ISDroneOperatingSystem");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.criticals = 1;
        misc.availRating = new int[]
            { RATING_E, RATING_F, RATING_F };
        misc.flags = misc.flags.or(F_DRONE_OPERATING_SYSTEM).or(F_TANK_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_MECH_EQUIPMENT);
        return misc;
    }

    public static MiscType createISVTOLJetBooster() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "VTOL Jet Booster";
        misc.setInternalName("ISVTOLJetBooster");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_JET_BOOSTER).or(F_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT).or(F_MASC);
        misc.subType |= S_JETBOOSTER;
        return misc;
    }

    public static MiscType createCLVTOLJetBooster() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name = "VTOL Jet Booster";
        misc.setInternalName("CLVTOLJetBooster");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_JET_BOOSTER).or(F_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT).or(F_MASC);
        misc.subType |= S_JETBOOSTER;
        return misc;
    }

    public static MiscType createCLReconCamera() {
        // TODO: implement game rules
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Recon Camera";
        misc.setInternalName("CLReconCamera");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 10000;
        misc.flags = misc.flags.or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_RECON_CAMERA);
        return misc;
    }

    public static MiscType createISReconCamera() {
        //TODO: implement game rules
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Recon Camera";
        misc.setInternalName("ISReconCamera");
        misc.tonnage = 0.5f;
        misc.criticals = 1;
        misc.cost = 10000;
        misc.flags = misc.flags.or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT).or(F_RECON_CAMERA);
        return misc;
    }

    public static MiscType createISCombatVehicleEscapePod() {
        //TODO: implement game rules
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Combat Vehicle Escape Pod";
        misc.setInternalName("ISCombatVehicleEscapePod");
        misc.tonnage = 4f;
        misc.criticals = 0;
        misc.cost = 10000;
        misc.flags = misc.flags.or(F_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT).or(F_COMBAT_VEHICLE_ESCAPE_POD);
        misc.availRating = new int[]{RATING_X, RATING_X, RATING_E};
        misc.techRating = RATING_D;
        return misc;
    }

    public static MiscType createISDetachableWeaponPack() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Detachable Weapon Pack";
        misc.setInternalName("ISDetachableWeaponPack");
        // FIXME: should be 0.75 x weapon/ammo
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.cost = 18000;
        misc.techRating = RATING_E;
        misc.availRating = new int[]{RATING_X, RATING_X, RATING_F};
        misc.flags = misc.flags.or(F_DETACHABLE_WEAPON_PACK).or(F_BA_EQUIPMENT);
        return misc;
    }

    public static MiscType createCLDetachableWeaponPack() {
        MiscType misc = new MiscType();

        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Detachable Weapon Pack";
        misc.setInternalName("CLDetachableWeaponPack");
        // FIXME: should be 0.75 x weapon/ammo
        misc.tonnage = 0;
        misc.criticals = 0;
        misc.cost = 18000;
        misc.techRating = RATING_E;
        misc.availRating = new int[]{RATING_X, RATING_X, RATING_F};
        misc.flags = misc.flags.or(F_DETACHABLE_WEAPON_PACK).or(F_BA_EQUIPMENT);
        return misc;
    }

    public static MiscType createBAHeatSensor() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Heat Sensor";
        misc.setInternalName("BAHeatSensor");
        misc.cost = 15000;
        misc.availRating = new int[]{RATING_X, RATING_X, RATING_F};
        misc.techRating = RATING_D;
        misc.tonnage = 0.02f;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_HEAT_SENSOR);
        misc.introDate = 3050;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_F};
        return misc;
    }

    public static MiscType createBAExtendedLifeSupport() {
        //TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Extended Life Support";
        misc.setInternalName("BAExtendedLifeSupport");
        misc.cost = 10000;
        misc.techRating = RATING_E;
        misc.tonnage = 0.025f;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_EXTENDED_LIFESUPPORT);
        misc.introDate = 2715;
        misc.availRating = new int[]{RATING_E,RATING_F,RATING_E};
        return misc;
    }

    public static MiscType createElectricDischargeArmor() {
        //TODO: add game rules for this
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name ="Electric Discharge ProtoMech Armor";
        misc.setInternalName("CLEDPArmor");
        misc.techRating = RATING_F;
        misc.availRating = new int[]{RATING_X, RATING_X, RATING_F};
        misc.tonnage = TONNAGE_VARIABLE;
        misc.hittable = false;
        misc.flags = misc.flags.or(F_PROTOMECH_EQUIPMENT).or(F_ELECTRIC_DISCHARGE_ARMOR);
        misc.bv = 32;
        String[] modes = {"not charging", "charging"};
        misc.setModes(modes);
        return misc;
    }

    public static MiscType createBAPartialWing() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name ="Partial Wing";
        misc.setInternalName("BAPartialWing");
        misc.tonnage = 0.2f;
        misc.cost = 50000;
        misc.flags = misc.flags.or(F_BA_EQUIPMENT).or(F_PARTIAL_WING);
        misc.techRating = RATING_D;
        misc.availRating = new int[]{RATING_X, RATING_X, RATING_F};
        misc.introDate = 3053;

        return misc;
    }

    public static MiscType createProtoPartialWing() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_EXPERIMENTAL;
        misc.name ="Partial Wing";
        misc.setInternalName("ProtoMechPartialWing");
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = COST_VARIABLE;
        misc.flags = misc.flags.or(F_PROTOMECH_EQUIPMENT).or(F_PARTIAL_WING);
        misc.techRating = RATING_F;
        misc.availRating = new int[]{RATING_X, RATING_X, RATING_E};
        misc.introDate = 3070;

        return misc;
    }

    public static MiscType createISBAJumpBooster() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_TW_NON_BOX;
        misc.name = "Jump Booster";
        misc.setInternalName("ISBAJumpBooster");
        misc.tonnage = 0.125f;
        misc.cost = 75000;
        misc.flags = misc.flags.or(F_JUMP_BOOSTER).or(F_BA_EQUIPMENT);
        misc.introDate = 3051;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_E};
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createCLBAJumpBooster() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_CLAN_TW;
        misc.name = "Jump Booster";
        misc.setInternalName("CLBAJumpBooster");
        misc.tonnage = 0.125f;
        misc.cost = 75000;
        misc.flags = misc.flags.or(F_JUMP_BOOSTER).or(F_BA_EQUIPMENT);
        misc.introDate = 3062;
        misc.availRating = new int[]{RATING_X,RATING_X,RATING_D};
        misc.techRating = RATING_E;
        return misc;
    }

    public static MiscType createISBAMechanicalJumpBooster() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Mechanical Jump Booster";
        misc.setInternalName("ISMechanicalJumpBooster");
        misc.techRating = RATING_E;
        misc.availRating = new int[]{RATING_X, RATING_X, RATING_F};
        misc.flags = misc.flags.or(F_MECHANICAL_JUMP_BOOSTER).or(F_BA_EQUIPMENT);
        return misc;
    }

    public static MiscType createISSmallNavalCommScannerSuite() {
        MiscType misc = new MiscType();
        misc.tonnage = 100;
        misc.cost = 50000000;
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Small Naval Comm-Scanner Suite";
        misc.setInternalName("ISSmallNavalCommScannerSuite");
        misc.techRating = RATING_D;
        misc.availRating = new int[]{RATING_D, RATING_E, RATING_E};
        misc.flags = misc.flags.or(F_SMALL_COMM_SCANNER_SUITE).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createCLSmallNavalCommScannerSuite() {
        MiscType misc = new MiscType();
        misc.tonnage = 100;
        misc.cost = 50000000;
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Small Naval Comm-Scanner Suite";
        misc.setInternalName("CLSmallNavalCommScannerSuite");
        misc.techRating = RATING_D;
        misc.availRating = new int[]{RATING_D, RATING_E, RATING_E};
        misc.flags = misc.flags.or(F_SMALL_COMM_SCANNER_SUITE).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createISLargeNavalCommScannerSuite() {
        MiscType misc = new MiscType();
        misc.tonnage = 500;
        misc.cost = 250000000;
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Large Naval Comm-Scanner Suite";
        misc.setInternalName("ISLargeNavalCommScannerSuite");
        misc.techRating = RATING_D;
        misc.availRating = new int[]{RATING_D, RATING_E, RATING_E};
        misc.flags = misc.flags.or(F_LARGE_COMM_SCANNER_SUITE).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createCLLargeNavalCommScannerSuite() {
        MiscType misc = new MiscType();
        misc.tonnage = 500;
        misc.cost = 250000000;
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Large Naval Comm-Scanner Suite";
        misc.setInternalName("CLLargeNavalCommScannerSuite");
        misc.techRating = RATING_D;
        misc.availRating = new int[]{RATING_D, RATING_E, RATING_E};
        misc.flags = misc.flags.or(F_LARGE_COMM_SCANNER_SUITE).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createLightBridgeLayer() {
        MiscType misc = new MiscType();
        misc.tonnage = 1;
        misc.cost = 40000;
        misc.criticals = 2;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Light Bridge Layer";
        misc.setInternalName("LightBridgeLayer");
        misc.flags = misc.flags.or(F_LIGHT_BRIDGE_LAYER).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_SUPPORT_TANK_EQUIPMENT);
        misc.industrial = true;
        misc.introDate = 1950;
        misc.techRating = RATING_B;
        misc.availRating = new int[]{RATING_D, RATING_E, RATING_D};
        return misc;
    }

    public static MiscType createMediumBridgeLayer() {
        MiscType misc = new MiscType();
        misc.tonnage = 2;
        misc.cost = 75000;
        misc.criticals = 4;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Medium Bridge Layer";
        misc.setInternalName("MediumBridgeLayer");
        misc.flags = misc.flags.or(F_MEDIUM_BRIDGE_LAYER).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_SUPPORT_TANK_EQUIPMENT);
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D, RATING_E, RATING_D};
        misc.techRating = RATING_C;
        return misc;
    }

    public static MiscType createHeavyBridgeLayer() {
        MiscType misc = new MiscType();
        misc.tonnage = 6;
        misc.cost = 100000;
        misc.criticals = 12;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Heavy Bridge Layer";
        misc.setInternalName("HeavyBridgeLayer");
        misc.flags = misc.flags.or(F_HEAVY_BRIDGE_LAYER).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_SUPPORT_TANK_EQUIPMENT);
        misc.industrial = true;
        misc.introDate = 1950;
        misc.techRating = RATING_D;
        misc.availRating = new int[]{RATING_E, RATING_E, RATING_E};
        return misc;
    }

    public static MiscType createBoobyTrap() {
        MiscType misc = new MiscType();
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = 100000;
        misc.criticals = 1;
        misc.techLevel = TechConstants.T_IS_EXPERIMENTAL;
        misc.name = "Booby Trap";
        misc.setInternalName("ISBoobyTrap");
        misc.techRating = RATING_B;
        misc.availRating = new int[]{RATING_D, RATING_F, RATING_D};
        misc.flags = misc.flags.or(F_BOOBY_TRAP).or(F_MECH_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_SUPPORT_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT).or(F_AERO_EQUIPMENT);
        return misc;
    }

    public static MiscType createRefuelingDrogue() {
        MiscType misc = new MiscType();
        misc.tonnage = 1;
        misc.cost = 25000;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Refueling Drogue";
        misc.setInternalName("RefuelingDrogue");
        misc.flags = misc.flags.or(F_REFUELING_DROGUE).or(F_AERO_EQUIPMENT).or(F_VTOL_EQUIPMENT);
        misc.industrial = true;
        misc.introDate = 1950;
        misc.techRating = RATING_C;
        misc.availRating = new int[]{RATING_B, RATING_B, RATING_B};
        return misc;
    }

    public static MiscType createBulldozer() {
        MiscType misc = new MiscType();
        misc.tonnage = 2;
        misc.cost = 50000;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Bulldozer";
        misc.setInternalName(misc.name);
        misc.bv = 10;
        misc.flags = misc.flags.or(F_BULLDOZER).or(F_TANK_EQUIPMENT).or(F_SUPPORT_TANK_EQUIPMENT);
        misc.industrial = true;
        misc.introDate = 1950;
        misc.techRating = RATING_B;
        misc.availRating = new int[]{RATING_C, RATING_C, RATING_C};
        return misc;
    }

    public static MiscType createExternalStoresHardpoint() {
        MiscType misc = new MiscType();
        misc.tonnage = 0.2f;
        misc.cost = 5000;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "External Stores Hardpoint";
        misc.setInternalName(misc.name);
        misc.flags = misc.flags.or(F_EXTERNAL_STORES_HARDPOINT).or(F_SUPPORT_TANK_EQUIPMENT);
        misc.industrial = true;
        misc.introDate = 1950;
        misc.availRating = new int[]{RATING_D,RATING_E,RATING_D};
        misc.techRating = RATING_B;
        return misc;
    }

    public static MiscType createManipulator() {
        MiscType misc = new MiscType();
        misc.tonnage = 0.01f;
        misc.cost = 7500;
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Manipulator";
        misc.setInternalName(misc.name);
        misc.flags = misc.flags.or(F_MANIPULATOR).or(F_SUPPORT_TANK_EQUIPMENT).or(F_TANK_EQUIPMENT).or(F_VTOL_EQUIPMENT);
        misc.industrial = true;
        misc.techRating = RATING_C;
        misc.availRating = new int[]{RATING_C, RATING_D, RATING_C};
        misc.introDate = 2000;
        return misc;
    }

    public static MiscType createISNavalTugAdaptor() {
        MiscType misc = new MiscType();
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = 100000;
        misc.techLevel = TechConstants.T_IS_ADVANCED;
        misc.name = "Naval Tug Adaptor";
        misc.setInternalName("ISNavalTugAdaptor");
        misc.techRating = RATING_C;
        misc.availRating = new int[]{RATING_C, RATING_C, RATING_C};
        misc.flags = misc.flags.or(F_AERO_EQUIPMENT).or(F_NAVAL_TUG_ADAPTOR);
        return misc;
    }

    public static MiscType createCLNavalTugAdaptor() {
        MiscType misc = new MiscType();
        misc.tonnage = TONNAGE_VARIABLE;
        misc.cost = 100000;
        misc.techLevel = TechConstants.T_CLAN_ADVANCED;
        misc.name = "Naval Tug Adaptor";
        misc.setInternalName("CLNavalTugAdaptor");
        misc.techRating = RATING_C;
        misc.availRating = new int[]{RATING_C, RATING_C, RATING_C};
        misc.flags = misc.flags.or(F_AERO_EQUIPMENT).or(F_NAVAL_TUG_ADAPTOR);
        return misc;
    }

    public static MiscType createPropChassisModification() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Prop";
        misc.setInternalName("PropChassisMod");
        misc.flags = misc.flags.or(F_AERO_EQUIPMENT).or(F_CHASSIS_MODIFICATION).or(F_PROP);
        return misc;
    }

    public static MiscType createUltraLightChassisModification() {
        MiscType misc = new MiscType();
        misc.techLevel = TechConstants.T_ALLOWED_ALL;
        misc.name = "Ultra-Light";
        misc.setInternalName("UltraLightChassisMod");
        misc.flags = misc.flags.or(F_SUPPORT_TANK_EQUIPMENT).or(F_CHASSIS_MODIFICATION).or(F_ULTRA_LIGHT);
        return misc;
    }

    @Override
    public String toString() {
        return "MiscType: " + name;
    }
}