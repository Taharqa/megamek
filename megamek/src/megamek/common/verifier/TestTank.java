/*
 * MegaMek -
 * Copyright (C) 2000,2001,2002,2003,2004,2005 Ben Mazur (bmazur@sev.org)
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
 * Author: Reinhard Vicinus
 */

package megamek.common.verifier;

import megamek.common.AmmoType;
import megamek.common.Entity;
import megamek.common.EntityMovementMode;
import megamek.common.EquipmentType;
import megamek.common.GunEmplacement;
import megamek.common.MiscType;
import megamek.common.Mounted;
import megamek.common.SuperHeavyTank;
import megamek.common.SupportTank;
import megamek.common.Tank;
import megamek.common.VTOL;
import megamek.common.WeaponType;
import megamek.common.util.StringUtil;
import megamek.common.weapons.CLChemicalLaserWeapon;

public class TestTank extends TestEntity {
    private Tank tank = null;

    public TestTank(Tank tank, TestEntityOption options, String fileString) {
        super(options, tank.getEngine(), TestTank.getArmor(tank), TestTank.getStructure(tank));
        this.tank = tank;
        this.fileString = fileString;
    }

    private static Structure getStructure(Tank tank) {
        int type = EquipmentType.T_STRUCTURE_STANDARD;
        int flag = 0;

        if (tank.getStructureType() == 1) {
            type = EquipmentType.T_STRUCTURE_ENDO_STEEL;
        }
        return new Structure(type, tank.isSuperHeavy(), tank.getMovementMode());
    }

    private static Armor[] getArmor(Tank tank) {
        Armor[] armor;
        if (!tank.hasPatchworkArmor()) {
            armor = new Armor[1];
            int type = tank.getArmorType(1);
            int flag = 0;
            if (tank.isClanArmor(1)) {
                flag |= Armor.CLAN_ARMOR;
            }
            armor[0] = new Armor(type, flag);
            return armor;
        } else {
            armor = new Armor[tank.locations()];
            for (int i = 0; i < tank.locations(); i++) {
                int type = tank.getArmorType(1);
                int flag = 0;
                if (tank.isClanArmor(1)) {
                    flag |= Armor.CLAN_ARMOR;
                }
                armor[i] = new Armor(type, flag);
            }
        }
        return armor;
    }

    @Override
    public Entity getEntity() {
        return tank;
    }

    @Override
    public boolean isTank() {
        return !(tank instanceof GunEmplacement);
    }

    @Override
    public boolean isMech() {
        return false;
    }

    public float getTankWeightTurret() {
        float weight = 0f;
        for (Mounted m : tank.getEquipment()) {
            if ((tank.isSuperHeavy()?m.getLocation() == SuperHeavyTank.LOC_TURRET:m.getLocation() == Tank.LOC_TURRET) && !(m.getType() instanceof AmmoType)) {
                weight += m.getType().getTonnage(tank);
            }
        }
        return TestEntity.ceilMaxHalf(weight / 10.0f, getWeightCeilingTurret());
    }

    public float getTankWeightDualTurret() {
        float weight = 0f;
        for (Mounted m : tank.getEquipment()) {
            if ((tank.isSuperHeavy()?m.getLocation() == SuperHeavyTank.LOC_TURRET_2:m.getLocation() == Tank.LOC_TURRET_2) && !(m.getType() instanceof AmmoType)) {
                weight += m.getType().getTonnage(tank);
            }
        }
        return TestEntity.ceilMaxHalf(weight / 10.0f, getWeightCeilingTurret());
    }

    public float getTankWeightLifting() {
        if (tank.getMovementMode() == EntityMovementMode.HOVER) {
            return tank.getWeight() / 10.0f;
        } else if (tank.getMovementMode() == EntityMovementMode.VTOL) {
            return tank.getWeight() / 10.0f;
        } else if (tank.getMovementMode() == EntityMovementMode.HYDROFOIL) {
            return tank.getWeight() / 10.0f;
        } else if (tank.getMovementMode() == EntityMovementMode.SUBMARINE) {
            return tank.getWeight() / 10.0f;
        }
        return 0f;
    }

    @Override
    public float getWeightMisc() {
        return getTankWeightTurret() + getTankWeightDualTurret() + getTankWeightLifting();
    }

    @Override
    public float getWeightControls() {
        return TestEntity.ceilMaxHalf(tank.getWeight() / 20.0f,
                getWeightCeilingControls());
    }

    private int getTankCountHeatLaserWeapons() {
        int heat = 0;
        for (Mounted m : tank.getWeaponList()) {
            WeaponType wt = (WeaponType) m.getType();
            if ((wt.hasFlag(WeaponType.F_LASER) && (wt.getAmmoType() == AmmoType.T_NA))
                    || wt.hasFlag(WeaponType.F_PPC)
                    || wt.hasFlag(WeaponType.F_PLASMA)
                    || wt.hasFlag(WeaponType.F_PLASMA_MFUK)
                    || (wt.hasFlag(WeaponType.F_FLAMER) && (wt.getAmmoType() == AmmoType.T_NA))) {
                heat += wt.getHeat();
            }
            // laser insulator reduce heat by 1, to a minimum of 1
            if (wt.hasFlag(WeaponType.F_LASER) && (m.getLinkedBy() != null)
                    && !m.getLinkedBy().isInoperable()
                    && m.getLinkedBy().getType().hasFlag(MiscType.F_LASER_INSULATOR)) {
                heat -= 1;
                if (heat == 0) {
                    heat++;
                }
            }

            if ((m.getLinkedBy() != null) && (m.getLinkedBy().getType() instanceof
                    MiscType) && m.getLinkedBy().getType().
                    hasFlag(MiscType.F_PPC_CAPACITOR)) {
                heat += 5;
            }
        }
        for (Mounted m : tank.getMisc()) {
            MiscType mtype = (MiscType)m.getType();
            // mobile HPGs count as energy weapons for construction purposes
            if (mtype.hasFlag(MiscType.F_MOBILE_HPG)) {
                heat += 20;
            }
        }
        return heat;
    }

    @Override
    public boolean hasDoubleHeatSinks() {
        // tanks can't have DHS
        return false;
    }

    @Override
    public int getCountHeatSinks() {
        float heat = getTankCountHeatLaserWeapons();
        return Math.round(heat);
    }

    @Override
    public float getWeightHeatSinks() {
        int heat = getCountHeatSinks();
        heat -= engine.getWeightFreeEngineHeatSinks();
        if (heat < 0) {
            heat = 0;
        }
        return heat;
    }

    @Override
    public String printWeightMisc() {
        String turretString = !tank.hasNoTurret() ? StringUtil.makeLength("Turret:",
                getPrintSize() - 5)
                + TestEntity.makeWeightString(getTankWeightTurret()) + "\n" : "";
        String dualTurretString = !tank.hasNoDualTurret() ? StringUtil.makeLength("Front Turret:",
                getPrintSize() - 5)
                + TestEntity.makeWeightString(getTankWeightDualTurret()) + "\n" : "";
        return turretString + dualTurretString
                + (getTankWeightLifting() != 0 ? StringUtil.makeLength(
                        "Lifting Equip:", getPrintSize() - 5)
                        + TestEntity.makeWeightString(getTankWeightLifting()) + "\n" : "")
                + (getWeightPowerAmp() != 0 ? StringUtil.makeLength(
                        "Power Amp:", getPrintSize() - 5)
                        + TestEntity.makeWeightString(getWeightPowerAmp()) + "\n" : "");
    }

    @Override
    public String printWeightControls() {
        return StringUtil.makeLength("Controls:", getPrintSize() - 5)
                + TestEntity.makeWeightString(getWeightControls()) + "\n";
    }

    public Tank getTank() {
        return tank;
    }

    @Override
    public boolean correctEntity(StringBuffer buff) {
        return correctEntity(buff, true);
    }

    @Override
    public boolean correctEntity(StringBuffer buff, boolean ignoreAmmo) {
        if ((tank instanceof SupportTank)) {
            return true;
        } // don't bother checking, won't work. Needs fixing (new class
            // needed.)
        boolean correct = true;
        if (skip()) {
            return true;
        }
        if (!correctWeight(buff)) {
            buff.insert(0, printTechLevel() + printShortMovement());
            buff.append(printWeightCalculation()).append("\n");
            correct = false;
        }
        if (!engine.engineValid) {
            buff.append(engine.problem.toString()).append("\n\n");
            correct = false;
        }
        if (tank.hasWorkingMisc(MiscType.F_ARMORED_MOTIVE_SYSTEM) && !((tank.getMovementMode() == EntityMovementMode.WHEELED) || (tank.getMovementMode() == EntityMovementMode.TRACKED) || (tank.getMovementMode() == EntityMovementMode.HOVER) || (tank.getMovementMode() == EntityMovementMode.HYDROFOIL) || (tank.getMovementMode() == EntityMovementMode.NAVAL) || (tank.getMovementMode() == EntityMovementMode.SUBMARINE) || (tank.getMovementMode() == EntityMovementMode.WIGE))) {
            buff.append("Armored Motive system and incompatible movemement mode!\n\n");
            correct = false;
        }
        if (tank.getFreeSlots() < 0) {
            buff.append("Not enough itemslots available! Using "+Math.abs(tank.getFreeSlots())+" slots too much\n\n");
            correct = false;
        }
        for (Mounted m : tank.getMisc()) {
            if (m.getType().hasFlag(MiscType.F_COMBAT_VEHICLE_ESCAPE_POD)) {
                if (m.getLocation() != Tank.LOC_REAR) {
                    buff.append("combat vehicle escape pod must be placed in rear");
                    correct = false;
                }
            }
        }
        for (int loc = 0; loc < tank.locations(); loc++) {
            int count = 0;
            for (Mounted misc : tank.getMisc()) {
                if ((misc.getLocation() == loc) && misc.getType().hasFlag(MiscType.F_MANIPULATOR)) {
                    count++;
                }
            }
            if (count > 2) {
                buff.append("max of 2 manipulators per location");
                return false;
            }
        }
        if (tank instanceof VTOL) {
            for (Mounted m : tank.getEquipment()) {
                if (m.getLocation() == VTOL.LOC_ROTOR) {
                    if (!((m.getType() instanceof MiscType) && m.getType().hasFlag(MiscType.F_MAST_MOUNT))) {
                        buff.append("only mast mount can be mounted in rotor");
                        correct = false;
                    }
                }
            }
        }

        if (showFailedEquip() && hasFailedEquipment(buff)) {
            correct = false;
        }
        if (hasIllegalTechLevels(buff, ignoreAmmo)) {
            correct = false;
        }
        if (hasIllegalEquipmentCombinations(buff)) {
            correct = false;
        }
        // only tanks with fusion engine can be vacuum protected
        if (!tank.getEngine().isFusion() && !tank.doomedInVacuum()) {
            buff.append("Vacuum protection requires fusion engine.\n");
            correct = false;
        }
        return correct;
    }

    @Override
    public StringBuffer printEntity() {
        StringBuffer buff = new StringBuffer();
        buff.append("Tank: ").append(tank.getDisplayName()).append("\n");
        buff.append("Found in: ").append(fileString).append("\n");
        buff.append(printTechLevel());
        buff.append(printShortMovement());
        if (correctWeight(buff, true, true)) {
            buff.append("Weight: ").append(getWeight()).append(" (").append(
                    calculateWeight()).append(")\n");
        }

        buff.append(printWeightCalculation()).append("\n");
        printFailedEquipment(buff);
        return buff;
    }

    @Override
    public String getName() {
        return "Tank: " + tank.getDisplayName();
    }

    @Override
    public float getWeightPowerAmp() {
        if (!engine.isFusion()) {
            int weight = 0;
            for (Mounted m : tank.getWeaponList()) {
                WeaponType wt = (WeaponType) m.getType();
                if (wt.hasFlag(WeaponType.F_ENERGY) && !(wt instanceof CLChemicalLaserWeapon)) {
                    weight += wt.getTonnage(tank);
                }
                if ((m.getLinkedBy() != null) && (m.getLinkedBy().getType() instanceof
                        MiscType) && m.getLinkedBy().getType().
                        hasFlag(MiscType.F_PPC_CAPACITOR)) {
                    weight += ((MiscType)m.getLinkedBy().getType()).getTonnage(tank);
                }
            }
            return TestEntity.ceil(weight / 10f, getWeightCeilingPowerAmp());
        }
        return 0;
    }
}
