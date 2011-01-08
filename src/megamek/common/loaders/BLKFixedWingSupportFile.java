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

import megamek.common.Aero;
import megamek.common.Engine;
import megamek.common.Entity;
import megamek.common.EntityMovementMode;
import megamek.common.EquipmentType;
import megamek.common.FixedWingSupport;
import megamek.common.HeavyVehicleBay;
import megamek.common.LightVehicleBay;
import megamek.common.LocationFullException;
import megamek.common.MechBay;
import megamek.common.TechConstants;
import megamek.common.TroopSpace;
import megamek.common.util.BuildingBlock;

public class BLKFixedWingSupportFile extends BLKFile implements IMechLoader {

    // armor locatioms
    public static final int NOSE = 0;
    public static final int RW = 1;
    public static final int LW = 2;
    public static final int AFT = 3;

    public BLKFixedWingSupportFile(BuildingBlock bb) {
        dataFile = bb;
    }

    public Entity getEntity() throws EntityLoadingException {

        FixedWingSupport a = new FixedWingSupport();

        if (!dataFile.exists("Name")) {
            throw new EntityLoadingException("Could not find name block.");
        }
        a.setChassis(dataFile.getDataAsString("Name")[0]);
        if (dataFile.exists("Model") && (dataFile.getDataAsString("Model")[0] != null)) {
            a.setModel(dataFile.getDataAsString("Model")[0]);
        } else {
            a.setModel("");
        }

        if (!dataFile.exists("barrating")) {
            throw new EntityLoadingException("Could not find barrating block.");
        }
        a.setBARRating(dataFile.getDataAsInt("barrating")[0]);

        setTechLevel(a);
        setFluff(a);

        if (dataFile.exists("source")) {
            a.setSource(dataFile.getDataAsString("source")[0]);
        }

        if (!dataFile.exists("tonnage")) {
            throw new EntityLoadingException("Could not find weight block.");
        }
        a.setWeight(dataFile.getDataAsFloat("tonnage")[0]);

        // how many bombs can it carry
        a.autoSetMaxBombPoints();

        // get a movement mode - lets try Aerodyne
        EntityMovementMode nMotion = EntityMovementMode.AERODYNE;
        a.setMovementMode(nMotion);

        if (dataFile.exists("transporters")) {
            String[] transporters = dataFile.getDataAsString("transporters");
            // Walk the array of transporters.
            for (String transporter : transporters) {
                transporter = transporter.toLowerCase();
                // TroopSpace:
                if (transporter.startsWith("troopspace:", 0)) {
                    // Everything after the ':' should be the space's size.
                    Double fsize = new Double(transporter.substring(11));
                    int size = fsize.intValue();
                    a.addTransporter(new TroopSpace(size));
                } else if (transporter.startsWith("mechbay:", 0)) {
                    String numbers = transporter.substring(8);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new MechBay(size, doors));
                } else if (transporter.startsWith("lightvehiclebay:", 0)) {
                    String numbers = transporter.substring(16);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new LightVehicleBay(size, doors));
                } else if (transporter.startsWith("heavyvehiclebay:", 0)) {
                    String numbers = transporter.substring(16);
                    String temp[] = numbers.split(":");
                    double size = Double.parseDouble(temp[0]);
                    int doors = Integer.parseInt(temp[1]);
                    a.addTransporter(new HeavyVehicleBay(size, doors));
                }

            } // Handle the next transportation component.

        } // End has-transporters

        // figure out fuel
        if (!dataFile.exists("fuel")) {
            throw new EntityLoadingException("Could not find fuel block.");
        }
        a.setFuel(dataFile.getDataAsInt("fuel")[0]);

        // figure out engine stuff
        int engineCode = BLKFile.FUSION;
        if (dataFile.exists("engine_type")) {
            engineCode = dataFile.getDataAsInt("engine_type")[0];
        }
        int engineFlags = Engine.TANK_ENGINE;
        if (a.isClan()) {
            engineFlags |= Engine.CLAN_ENGINE;
        }
        if (!dataFile.exists("SafeThrust")) {
            throw new EntityLoadingException("Could not find SafeThrust block.");
        }
        int engineRating = (dataFile.getDataAsInt("SafeThrust")[0] - 2) * (int) a.getWeight();
        a.setEngine(new Engine(engineRating, BLKFile.translateEngineCode(engineCode), engineFlags));

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

        if (!dataFile.exists("armor")) {
            throw new EntityLoadingException("Could not find armor block.");
        }

        int[] armor = dataFile.getDataAsInt("armor");

        if (armor.length != 4) {
            throw new EntityLoadingException("Incorrect armor array length");
        }

        a.initializeArmor(armor[BLKAeroFile.NOSE], Aero.LOC_NOSE);
        a.initializeArmor(armor[BLKAeroFile.RW], Aero.LOC_RWING);
        a.initializeArmor(armor[BLKAeroFile.LW], Aero.LOC_LWING);
        a.initializeArmor(armor[BLKAeroFile.AFT], Aero.LOC_AFT);

        a.autoSetInternal();
        a.autoSetSI();
        // This is not working right for arrays for some reason
        a.autoSetThresh();

        loadEquipment(a, "Nose", Aero.LOC_NOSE);
        loadEquipment(a, "Right Wing", Aero.LOC_RWING);
        loadEquipment(a, "Left Wing", Aero.LOC_LWING);
        loadEquipment(a, "Aft", Aero.LOC_AFT);
        loadEquipment(a, "Body", FixedWingSupport.LOC_BODY);

        if (dataFile.exists("omni")) {
            a.setOmni(true);
        }

        if (a.isClan()) {
            a.addClanCase();
        }

        return a;
    }

    @Override
    protected void loadEquipment(Entity t, String sName, int nLoc) throws EntityLoadingException {
        String[] saEquip = dataFile.getDataAsString(sName + " Equipment");
        if (saEquip == null) {
            return;
        }

        // prefix is "Clan " or "IS "
        String prefix;
        if (t.getTechLevel() == TechConstants.T_CLAN_TW) {
            prefix = "Clan ";
        } else {
            prefix = "IS ";
        }

        boolean rearMount = false;

        if (saEquip[0] != null) {
            for (String element : saEquip) {
                rearMount = false;
                String equipName = element.trim();

                if (equipName.startsWith("(R) ")) {
                    rearMount = true;
                    equipName = equipName.substring(4);
                }

                EquipmentType etype = EquipmentType.get(equipName);

                if (etype == null) {
                    // try w/ prefix
                    etype = EquipmentType.get(prefix + equipName);
                }

                if (etype != null) {
                    try {
                        t.addEquipment(etype, nLoc, rearMount);
                    } catch (LocationFullException ex) {
                        throw new EntityLoadingException(ex.getMessage());
                    }
                } else if (!equipName.equals("")) {
                    t.addFailedEquipment(equipName);
                }
            }
        }
    }

}
