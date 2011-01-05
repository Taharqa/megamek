/*
 * MegaMek - Copyright (C) 2000-2002 Ben Mazur (bmazur@sev.org)
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

/*
 * BLkFile.java
 * 
 * Created on April 6, 2002, 2:06 AM
 */

/**
 * 
 * @author taharqa
 * @version
 */
package megamek.common.loaders;

import megamek.common.ASFBay;
import megamek.common.AmmoType;
import megamek.common.BattleArmorBay;
import megamek.common.CargoBay;
import megamek.common.DockingCollar;
import megamek.common.Engine;
import megamek.common.Entity;
import megamek.common.EntityMovementMode;
import megamek.common.EquipmentType;
import megamek.common.HeavyVehicleBay;
import megamek.common.InfantryBay;
import megamek.common.LightVehicleBay;
import megamek.common.LocationFullException;
import megamek.common.MechBay;
import megamek.common.Mounted;
import megamek.common.SmallCraftBay;
import megamek.common.TechConstants;
import megamek.common.Warship;
import megamek.common.WeaponType;
import megamek.common.util.BuildingBlock;

public class BLKWarshipFile extends BLKFile implements IMechLoader {

    // armor locatioms
    public static final int NOSE = 0;
    public static final int FLS = 1;
    public static final int FRS = 2;
    public static final int ALS = 3;
    public static final int ARS = 4;
    public static final int AFT = 5;
    public static final int LBS = 6;
    public static final int RBS = 7;

    public BLKWarshipFile(BuildingBlock bb) {
        dataFile = bb;
    }

    public Entity getEntity() throws EntityLoadingException {

        Warship a = new Warship();

        if (!dataFile.exists("Name")) {
            throw new EntityLoadingException("Could not find name block.");
        }
        a.setChassis(dataFile.getDataAsString("Name")[0]);
        if (dataFile.exists("Model") && (dataFile.getDataAsString("Model")[0] != null)) {
            a.setModel(dataFile.getDataAsString("Model")[0]);
        } else {
            a.setModel("");
        }

        if (dataFile.exists("source")) {
            a.setSource(dataFile.getDataAsString("source")[0]);
        }

        if (!dataFile.exists("year")) {
            throw new EntityLoadingException("Could not find year block.");
        }
        a.setYear(dataFile.getDataAsInt("year")[0]);

        setTechLevel(a);
        setFluff(a);

        if (!dataFile.exists("tonnage")) {
            throw new EntityLoadingException("Could not find weight block.");
        }
        a.setWeight(dataFile.getDataAsFloat("tonnage")[0]);

        if (!dataFile.exists("crew")) {
            throw new EntityLoadingException("Could not find crew block.");
        }
        a.setNCrew(dataFile.getDataAsInt("crew")[0]);

        if (!dataFile.exists("passengers")) {
            throw new EntityLoadingException("Could not find passenger block.");
        }
        a.setNPassenger(dataFile.getDataAsInt("passengers")[0]);

        if (!dataFile.exists("life_boat")) {
            throw new EntityLoadingException("Could not find life boat block.");
        }
        a.setLifeBoats(dataFile.getDataAsInt("life_boat")[0]);

        if (!dataFile.exists("escape_pod")) {
            throw new EntityLoadingException("Could not find escape pod block.");
        }
        a.setEscapePods(dataFile.getDataAsInt("escape_pod")[0]);

        // get a movement mode - lets try Aerodyne
        EntityMovementMode nMotion = EntityMovementMode.AERODYNE;
        a.setMovementMode(nMotion);

        // figure out structural integrity
        if (!dataFile.exists("structural_integrity")) {
            throw new EntityLoadingException("Could not find structual integrity block.");
        }
        a.set0SI(dataFile.getDataAsInt("structural_integrity")[0]);

        // figure out heat
        if (!dataFile.exists("heatsinks")) {
            throw new EntityLoadingException("Could not find heatsinks block.");
        }
        a.setHeatSinks(dataFile.getDataAsInt("heatsinks")[0]);
        if (!dataFile.exists("sink_type")) {
            throw new EntityLoadingException("Could not find sink_type block.");
        }
        a.setHeatType(dataFile.getDataAsInt("sink_type")[0]);

        // figure out fuel
        if (!dataFile.exists("fuel")) {
            throw new EntityLoadingException("Could not find fuel block.");
        }
        a.setFuel(dataFile.getDataAsInt("fuel")[0]);

        if (!dataFile.exists("SafeThrust")) {
            throw new EntityLoadingException("Could not find Safe Thrust block.");
        }
        a.setOriginalWalkMP(dataFile.getDataAsInt("SafeThrust")[0]);

        a.setEngine(new Engine(400, 0, 0));

        if (dataFile.exists("lithium-fusion")) {
            a.setLF(true);
        }

        if (dataFile.exists("hpg")) {
            a.setHPG(true);
        }

        // grav decks
        if (dataFile.exists("grav_deck")) {
            a.setGravDeck(dataFile.getDataAsInt("grav_deck")[0]);
        }
        if (dataFile.exists("grav_deck_large")) {
            a.setGravDeckLarge(dataFile.getDataAsInt("grav_deck_large")[0]);
        }
        if (dataFile.exists("grav_deck_huge")) {
            a.setGravDeckHuge(dataFile.getDataAsInt("grav_deck_huge")[0]);
        }

        if (dataFile.exists("armor_type")) {
            a.setArmorType(dataFile.getDataAsInt("armor_type")[0]);
        } else {
            a.setArmorType(EquipmentType.T_ARMOR_STANDARD);
        }
        if (dataFile.exists("armor_tech")) {
            a.setArmorTechLevel(dataFile.getDataAsInt("armor_tech")[0]);
        }
        if (dataFile.exists("internal_type")) {
            a.setStructureType(dataFile.getDataAsInt("internal_type")[0]);
        } else {
            a.setStructureType(EquipmentType.T_STRUCTURE_STANDARD);
        }

        if (dataFile.exists("history")) {
            a.getFluff().setHistory(dataFile.getDataAsString("history").toString());
        }

        if (dataFile.exists("imagepath")) {
            a.getFluff().setMMLImagePath(dataFile.getDataAsString("imagepath").toString());
        }

        if (!dataFile.exists("armor")) {
            throw new EntityLoadingException("Could not find armor block.");
        }

        int[] armor = dataFile.getDataAsInt("armor");

        if (armor.length != 6) {
            throw new EntityLoadingException("Incorrect armor array length");
        }

        a.initializeArmor(armor[BLKWarshipFile.NOSE], Warship.LOC_NOSE);
        a.initializeArmor(armor[BLKWarshipFile.FLS], Warship.LOC_FLS);
        a.initializeArmor(armor[BLKWarshipFile.FRS], Warship.LOC_FRS);
        a.initializeArmor(armor[BLKWarshipFile.ALS], Warship.LOC_ALS);
        a.initializeArmor(armor[BLKWarshipFile.ARS], Warship.LOC_ARS);
        a.initializeArmor(armor[BLKWarshipFile.AFT], Warship.LOC_AFT);
        a.initializeArmor(0, Warship.LOC_LBS);
        a.initializeArmor(0, Warship.LOC_RBS);

        a.autoSetInternal();
        a.autoSetThresh();
        a.initializeKFIntegrity();
        a.initializeSailIntegrity();

        loadEquipment(a, "Nose", Warship.LOC_NOSE);
        loadEquipment(a, "Front Right Side", Warship.LOC_FRS);
        loadEquipment(a, "Front Left Side", Warship.LOC_FLS);
        loadEquipment(a, "Aft Left Side", Warship.LOC_ALS);
        loadEquipment(a, "Aft Right Side", Warship.LOC_ARS);
        loadEquipment(a, "Aft", Warship.LOC_AFT);
        // for now put all broadside mounted weapons in the front location
        // TODO: turn broadsides into a real unhittable location
        loadEquipment(a, "Left Broadside", Warship.LOC_LBS);
        loadEquipment(a, "Right Broadside", Warship.LOC_RBS);

        // get the bays on this jumpship
        if (dataFile.exists("transporters")) {
            String[] transporters = dataFile.getDataAsString("transporters");
            // Walk the array of transporters.
            for (String transporter : transporters) {
                if (transporter.startsWith("SmallCraftBay:", 0)) {
                    String numbers = transporter.substring(14);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new SmallCraftBay(size, doors));
                } else if (transporter.startsWith("ASFBay:", 0)) {
                    String numbers = transporter.substring(7);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new ASFBay(size, doors));
                } else if (transporter.startsWith("MechBay:", 0)) {
                    String numbers = transporter.substring(8);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new MechBay(size, doors));
                } else if (transporter.startsWith("LightVehicleBay:", 0)) {
                    String numbers = transporter.substring(16);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new LightVehicleBay(size, doors));
                } else if (transporter.startsWith("HeavyVehicleBay:", 0)) {
                    String numbers = transporter.substring(16);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new HeavyVehicleBay(size, doors));
                } else if (transporter.startsWith("InfantryBay:", 0)) {
                    String numbers = transporter.substring(12);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new InfantryBay(size, doors));
                } else if (transporter.startsWith("BattleArmorBay:", 0)) {
                    String numbers = transporter.substring(15);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new BattleArmorBay(size, doors));
                } else if (transporter.startsWith("CargoBay:", 0)) {
                    String numbers = transporter.substring(9);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new CargoBay(size, doors));
                }
            }
        }

        // get docking collars
        if (!dataFile.exists("docking_collar")) {
            throw new EntityLoadingException("Could not find docking collar block.");
        }
        int docks = dataFile.getDataAsInt("docking_collar")[0];
        while (docks > 0) {
            a.addTransporter(new DockingCollar(1));
            docks--;
        }

        return a;
    }

    @Override
    protected void loadEquipment(Entity en, String sName, int nLoc) throws EntityLoadingException {
        Warship a = (Warship) en;
        String[] saEquip = dataFile.getDataAsString(sName + " Equipment");
        if (saEquip == null) {
            return;
        }

        // prefix is "Clan " or "IS "
        String prefix;
        if (a.getTechLevel() == TechConstants.T_CLAN_TW) {
            prefix = "Clan ";
        } else {
            prefix = "IS ";
        }

        boolean rearMount = false;
        int nAmmo = 1;
        // set up a new weapons bay mount
        Mounted bayMount = null;
        // set up a new bay type
        boolean newBay = false;
        double bayDamage = 0;
        int nBay = 1;
        if (saEquip[0] != null) {
            for (String element : saEquip) {
                rearMount = false;
                nAmmo = 1;
                newBay = false;
                String equipName = element.trim();

                // I will need to deal with rear-mounted bays on Dropships
                if (equipName.startsWith("(R) ")) {
                    rearMount = true;
                    equipName = equipName.substring(4);
                }

                if (equipName.startsWith("(B) ")) {
                    newBay = true;
                    equipName = equipName.substring(4);
                }

                // check for ammo loadouts
                if (equipName.contains(":") && equipName.contains("Ammo")) {
                    // then split by the :
                    String[] temp;
                    temp = equipName.split(":");
                    equipName = temp[0];
                    if (temp[1] != null) {
                        nAmmo = Integer.parseInt(temp[1]);
                    }
                }

                EquipmentType etype = EquipmentType.get(equipName);

                if (etype == null) {
                    // try w/ prefix
                    etype = EquipmentType.get(prefix + equipName);
                }

                if (etype != null) {
                    // first load the equipment
                    Mounted newmount;
                    try {
                        if (nAmmo == 1) {
                            newmount = a.addEquipment(etype, nLoc, rearMount);
                        } else {
                            newmount = a.addEquipment(etype, nLoc, rearMount, nAmmo);
                        }
                    } catch (LocationFullException ex) {
                        throw new EntityLoadingException(ex.getMessage());
                    }

                    // this is where weapon bays go
                    // first, lets see if it is a weapon
                    if (newmount.getType() instanceof WeaponType) {
                        // if so then I need to find out if it is the same class
                        // as the current weapon bay
                        // If the current bay is null, then it needs to be
                        // initialized
                        WeaponType weap = (WeaponType) newmount.getType();
                        if (bayMount == null) {
                            try {
                                bayMount = a.addEquipment(weap.getBayType(), nLoc, rearMount);
                                newBay = false;
                            } catch (LocationFullException ex) {
                                throw new EntityLoadingException(ex.getMessage());
                            }
                        }
                        double damage = weap.getShortAV();
                        if (weap.isCapital()) {
                            damage *= 10;
                        }
                        if (!newBay && ((bayDamage + damage) <= 700) && (bayMount.isRearMounted() == rearMount) && (weap.getAtClass() == ((WeaponType) bayMount.getType()).getAtClass()) && !(((WeaponType) bayMount.getType()).isSubCapital() && !weap.isSubCapital())) {
                            // then we should add this weapon to the current bay
                            bayMount.addWeaponToBay(a.getEquipmentNum(newmount));
                            bayDamage += damage;
                        } else {
                            try {
                                bayMount = a.addEquipment(weap.getBayType(), nLoc, rearMount);
                            } catch (LocationFullException ex) {
                                throw new EntityLoadingException(ex.getMessage());
                            }
                            bayMount.addWeaponToBay(a.getEquipmentNum(newmount));
                            // reset bay damage
                            bayDamage = damage;
                            nBay++;
                        }
                    }
                    // ammo should also get loaded into the bay
                    if (newmount.getType() instanceof AmmoType) {
                        bayMount.addAmmoToBay(a.getEquipmentNum(newmount));
                    }
                } else if (!equipName.equals("")) {
                    a.addFailedEquipment(equipName);
                }
            }
        }
    }

}
