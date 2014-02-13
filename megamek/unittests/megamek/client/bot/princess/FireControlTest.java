/*
 * MegaMek - Copyright (C) 2000-2011 Ben Mazur (bmazur@sev.org)
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
package megamek.client.bot.princess;

import megamek.common.AmmoType;
import megamek.common.BattleArmor;
import megamek.common.BipedMech;
import megamek.common.BuildingTarget;
import megamek.common.ConvFighter;
import megamek.common.Coords;
import megamek.common.Entity;
import megamek.common.EntityMovementType;
import megamek.common.EquipmentType;
import megamek.common.IBoard;
import megamek.common.IGame;
import megamek.common.IHex;
import megamek.common.ITerrain;
import megamek.common.Infantry;
import megamek.common.MechWarrior;
import megamek.common.Mounted;
import megamek.common.Tank;
import megamek.common.Targetable;
import megamek.common.Terrains;
import megamek.common.ToHitData;
import megamek.common.VTOL;
import megamek.common.WeaponType;
import megamek.common.options.GameOptions;
import megamek.common.weapons.ATMWeapon;
import megamek.common.weapons.MMLWeapon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @version %Id%
 * @lastEditBy Deric "Netzilla" Page (deric dot page at usa dot net)
 * @since 12/18/13 1:38 PM
 */
@RunWith(JUnit4.class)
public class FireControlTest {

    // AC5
    private WeaponType mockWeaponTypeAC5;
    private AmmoType mockAmmoTypeAC5Std;
    private Mounted mockAmmoAC5Std;
    private AmmoType mockAmmoTypeAC5Flak;
    private Mounted mockAmmoAC5Flak;
    private AmmoType mockAmmoTypeAC5Incendiary;
    private Mounted mockAmmoAc5Incendiary;
    private AmmoType mockAmmoTypeAc5Flechette;
    private Mounted mockAmmoAc5Flechette;

    // LB10X
    private WeaponType mockLB10X;
    private AmmoType mockAmmoTypeLB10XSlug;
    private Mounted mockAmmoLB10XSlug;
    private AmmoType mockAmmoTypeLB10XCluster;
    private Mounted mockAmmoLB10XCluster;

    // MML
    private WeaponType mockMML5;
    private AmmoType mockAmmoTypeSRM5;
    private Mounted mockAmmoSRM5;
    private AmmoType mockAmmoTypeLRM5;
    private Mounted mockAmmoLRM5;
    private AmmoType mockAmmoTypeInferno5;
    private Mounted mockAmmoInfero5;
    private AmmoType mockAmmoTypeLrm5Frag;
    private Mounted mockAmmoLrm5Frag;

    // ATM
    private WeaponType mockAtm5;
    private AmmoType mockAmmoTypeAtm5He;
    private Mounted mockAmmoAtm5He;
    private AmmoType mockAmmoTypeAtm5St;
    private Mounted mockAmmoAtm5St;
    private AmmoType mockAmmoTypeAtm5Er;
    private Mounted mockAmmoAtm5Er;
    private AmmoType mockAmmoTypeAtm5Inferno;
    private Mounted mockAmmoAtm5Inferno;

    EntityState mockTargetState;

    private Princess mockPrincess;


    @Before
    public void setUp() {
        mockPrincess = Mockito.mock(Princess.class);

        mockTargetState = Mockito.mock(EntityState.class);
        Mockito.when(mockTargetState.isBuilding()).thenReturn(false);
        Mockito.when(mockTargetState.getHeat()).thenReturn(0);

        // AC5
        mockWeaponTypeAC5 = Mockito.mock(WeaponType.class);
        Mockito.when(mockWeaponTypeAC5.getAmmoType()).thenReturn(AmmoType.T_AC);
        mockAmmoTypeAC5Std = Mockito.mock(AmmoType.class);
        Mockito.when(mockAmmoTypeAC5Std.getAmmoType()).thenReturn(AmmoType.T_AC);
        Mockito.when(mockAmmoTypeAC5Std.getMunitionType()).thenReturn(AmmoType.M_STANDARD);
        mockAmmoAC5Std = Mockito.mock(Mounted.class);
        Mockito.when(mockAmmoAC5Std.getType()).thenReturn(mockAmmoTypeAC5Std);
        Mockito.when(mockAmmoAC5Std.isAmmoUsable()).thenReturn(true);
        mockAmmoTypeAC5Flak = Mockito.mock(AmmoType.class);
        Mockito.when(mockAmmoTypeAC5Flak.getAmmoType()).thenReturn(AmmoType.T_AC);
        Mockito.when(mockAmmoTypeAC5Flak.getMunitionType()).thenReturn(AmmoType.M_FLAK);
        mockAmmoAC5Flak = Mockito.mock(Mounted.class);
        Mockito.when(mockAmmoAC5Flak.getType()).thenReturn(mockAmmoTypeAC5Flak);
        Mockito.when(mockAmmoAC5Flak.isAmmoUsable()).thenReturn(true);
        mockAmmoTypeAC5Incendiary = Mockito.mock(AmmoType.class);
        Mockito.when(mockAmmoTypeAC5Incendiary.getMunitionType()).thenReturn(AmmoType.M_INCENDIARY_AC);
        Mockito.when(mockAmmoTypeAC5Incendiary.getAmmoType()).thenReturn(AmmoType.T_AC);
        mockAmmoAc5Incendiary = Mockito.mock(Mounted.class);
        Mockito.when(mockAmmoAc5Incendiary.getType()).thenReturn(mockAmmoTypeAC5Incendiary);
        Mockito.when(mockAmmoAc5Incendiary.isAmmoUsable()).thenReturn(true);
        mockAmmoTypeAc5Flechette = Mockito.mock(AmmoType.class);
        Mockito.when(mockAmmoTypeAc5Flechette.getAmmoType()).thenReturn(AmmoType.T_AC);
        Mockito.when(mockAmmoTypeAc5Flechette.getMunitionType()).thenReturn(AmmoType.M_FLECHETTE);
        mockAmmoAc5Flechette = Mockito.mock(Mounted.class);
        Mockito.when(mockAmmoAc5Flechette.getType()).thenReturn(mockAmmoTypeAc5Flechette);
        Mockito.when(mockAmmoAc5Flechette.isAmmoUsable()).thenReturn(true);

        // LB10X
        mockLB10X = Mockito.mock(WeaponType.class);
        mockAmmoTypeLB10XSlug = Mockito.mock(AmmoType.class);
        mockAmmoLB10XSlug = Mockito.mock(Mounted.class);
        mockAmmoTypeLB10XCluster = Mockito.mock(AmmoType.class);
        mockAmmoLB10XCluster = Mockito.mock(Mounted.class);
        Mockito.when(mockLB10X.getAmmoType()).thenReturn(AmmoType.T_AC_LBX);
        Mockito.when(mockAmmoTypeLB10XSlug.getAmmoType()).thenReturn(AmmoType.T_AC_LBX);
        Mockito.when(mockAmmoTypeLB10XSlug.getMunitionType()).thenReturn(AmmoType.M_STANDARD);
        Mockito.when(mockAmmoLB10XSlug.getType()).thenReturn(mockAmmoTypeLB10XSlug);
        Mockito.when(mockAmmoLB10XSlug.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoTypeLB10XCluster.getAmmoType()).thenReturn(AmmoType.T_AC_LBX);
        Mockito.when(mockAmmoTypeLB10XCluster.getMunitionType()).thenReturn(AmmoType.M_CLUSTER);
        Mockito.when(mockAmmoLB10XCluster.getType()).thenReturn(mockAmmoTypeLB10XCluster);
        Mockito.when(mockAmmoLB10XCluster.isAmmoUsable()).thenReturn(true);

        // MML
        mockMML5 = Mockito.mock(MMLWeapon.class);
        mockAmmoTypeSRM5 = Mockito.mock(AmmoType.class);
        mockAmmoSRM5 = Mockito.mock(Mounted.class);
        mockAmmoTypeLRM5 = Mockito.mock(AmmoType.class);
        mockAmmoLRM5 = Mockito.mock(Mounted.class);
        mockAmmoTypeInferno5 = Mockito.mock(AmmoType.class);
        mockAmmoInfero5 = Mockito.mock(Mounted.class);
        mockAmmoTypeLrm5Frag = Mockito.mock(AmmoType.class);
        mockAmmoLrm5Frag = Mockito.mock(Mounted.class);
        Mockito.when(mockMML5.getAmmoType()).thenReturn(AmmoType.T_MML);
        Mockito.when(mockAmmoTypeSRM5.getMunitionType()).thenReturn(AmmoType.M_STANDARD);
        Mockito.when(mockAmmoTypeSRM5.getAmmoType()).thenReturn(AmmoType.T_MML);
        Mockito.when(mockAmmoSRM5.getType()).thenReturn(mockAmmoTypeSRM5);
        Mockito.when(mockAmmoSRM5.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoTypeLRM5.getMunitionType()).thenReturn(AmmoType.M_STANDARD);
        Mockito.when(mockAmmoTypeLRM5.hasFlag(Mockito.any(BigInteger.class))).thenReturn(false);
        Mockito.when(mockAmmoTypeLRM5.hasFlag(Mockito.eq(AmmoType.F_MML_LRM))).thenReturn(true);
        Mockito.when(mockAmmoTypeLRM5.getAmmoType()).thenReturn(AmmoType.T_MML);
        Mockito.when(mockAmmoLRM5.getType()).thenReturn(mockAmmoTypeLRM5);
        Mockito.when(mockAmmoLRM5.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoTypeInferno5.getMunitionType()).thenReturn(AmmoType.M_INFERNO);
        Mockito.when(mockAmmoTypeInferno5.getAmmoType()).thenReturn(AmmoType.T_MML);
        Mockito.when(mockAmmoInfero5.getType()).thenReturn(mockAmmoTypeInferno5);
        Mockito.when(mockAmmoInfero5.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoTypeLrm5Frag.getMunitionType()).thenReturn(AmmoType.M_FRAGMENTATION);
        Mockito.when(mockAmmoTypeLrm5Frag.hasFlag(Mockito.eq(AmmoType.F_MML_LRM))).thenReturn(true);
        Mockito.when(mockAmmoTypeLrm5Frag.getAmmoType()).thenReturn(AmmoType.T_MML);
        Mockito.when(mockAmmoLrm5Frag.getType()).thenReturn(mockAmmoTypeLrm5Frag);
        Mockito.when(mockAmmoLrm5Frag.isAmmoUsable()).thenReturn(true);

        // ATM
        mockAtm5 = Mockito.mock(ATMWeapon.class);
        mockAmmoTypeAtm5He = Mockito.mock(AmmoType.class);
        mockAmmoAtm5He = Mockito.mock(Mounted.class);
        mockAmmoTypeAtm5St = Mockito.mock(AmmoType.class);
        mockAmmoAtm5St = Mockito.mock(Mounted.class);
        mockAmmoTypeAtm5Er = Mockito.mock(AmmoType.class);
        mockAmmoAtm5Er = Mockito.mock(Mounted.class);
        mockAmmoTypeAtm5Inferno = Mockito.mock(AmmoType.class);
        mockAmmoAtm5Inferno = Mockito.mock(Mounted.class);
        Mockito.when(mockAtm5.getAmmoType()).thenReturn(AmmoType.T_ATM);
        Mockito.when(mockAtm5.getRackSize()).thenReturn(5);
        Mockito.when(mockAmmoTypeAtm5He.getAmmoType()).thenReturn(AmmoType.T_ATM);
        Mockito.when(mockAmmoTypeAtm5He.getMunitionType()).thenReturn(AmmoType.M_HIGH_EXPLOSIVE);
        Mockito.when(mockAmmoTypeAtm5He.getRackSize()).thenReturn(5);
        Mockito.when(mockAmmoAtm5He.getType()).thenReturn(mockAmmoTypeAtm5He);
        Mockito.when(mockAmmoAtm5He.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoTypeAtm5St.getMunitionType()).thenReturn(AmmoType.M_STANDARD);
        Mockito.when(mockAmmoTypeAtm5St.getAmmoType()).thenReturn(AmmoType.T_ATM);
        Mockito.when(mockAmmoTypeAtm5St.getRackSize()).thenReturn(5);
        Mockito.when(mockAmmoAtm5St.getType()).thenReturn(mockAmmoTypeAtm5St);
        Mockito.when(mockAmmoAtm5St.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoTypeAtm5Er.getMunitionType()).thenReturn(AmmoType.M_EXTENDED_RANGE);
        Mockito.when(mockAmmoTypeAtm5Er.getAmmoType()).thenReturn(AmmoType.T_ATM);
        Mockito.when(mockAmmoTypeAtm5Er.getRackSize()).thenReturn(5);
        Mockito.when(mockAmmoAtm5Er.getType()).thenReturn(mockAmmoTypeAtm5Er);
        Mockito.when(mockAmmoAtm5Er.isAmmoUsable()).thenReturn(true);
        Mockito.when(mockAmmoTypeAtm5Inferno.getMunitionType()).thenReturn(AmmoType.M_IATM_IIW);
        Mockito.when(mockAmmoTypeAtm5Inferno.getAmmoType()).thenReturn(AmmoType.T_ATM);
        Mockito.when(mockAmmoTypeAtm5Inferno.getRackSize()).thenReturn(5);
        Mockito.when(mockAmmoAtm5Inferno.getType()).thenReturn(mockAmmoTypeAtm5Inferno);
        Mockito.when(mockAmmoAtm5Inferno.isAmmoUsable()).thenReturn(true);
    }


    @Test
    public void testGetHardTargetAmmo() {

        // Test an ammo list with only 1 bin of standard ammo.
        List<Mounted> testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAC5Std);
        FireControl testFireControl = new FireControl(mockPrincess);
        Assert.assertEquals(mockAmmoAC5Std, testFireControl.getHardTargetAmmo(testAmmoList, mockWeaponTypeAC5, 5));

        // Test an ammo list with only 1 bin of flak ammo.
        testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAC5Flak);
        Assert.assertNull(testFireControl.getHardTargetAmmo(testAmmoList, mockWeaponTypeAC5, 5));

        // Test an ammo list with 1 each of standard and flak.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoAC5Flak);
        testAmmoList.add(mockAmmoAC5Std);
        Assert.assertEquals(mockAmmoAC5Std, testFireControl.getHardTargetAmmo(testAmmoList, mockWeaponTypeAC5, 5));

        // Test LBX weaponry.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLB10XCluster);
        testAmmoList.add(mockAmmoLB10XSlug);
        Assert.assertEquals(mockAmmoLB10XSlug, testFireControl.getHardTargetAmmo(testAmmoList, mockLB10X, 5));

        // Test MMLs
        testAmmoList = new ArrayList<Mounted>(3);
        testAmmoList.add(mockAmmoLRM5);
        testAmmoList.add(mockAmmoSRM5);
        testAmmoList.add(mockAmmoInfero5);
        Assert.assertEquals(mockAmmoSRM5, testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 4));
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 8));
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 10));

        // Test MMLs without LRMs.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoSRM5);
        testAmmoList.add(mockAmmoInfero5);
        Assert.assertEquals(mockAmmoSRM5, testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 4));
        Assert.assertEquals(mockAmmoSRM5, testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 8));
        Assert.assertNull(testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 10));

        // Test MMLs without SRMs.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLRM5);
        testAmmoList.add(mockAmmoInfero5);
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 4));
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 8));
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getHardTargetAmmo(testAmmoList, mockMML5, 10));
    }

    @Test
    public void testGetAntiAirAmmo() {

        // Test an ammo list with only 1 bin.
        List<Mounted> testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoAC5Std);
        FireControl testFireControl = new FireControl(mockPrincess);
        Assert.assertNull(testFireControl.getAntiAirAmmo(testAmmoList, mockWeaponTypeAC5, 5));

        // Add the flak ammo.
        testAmmoList.add(mockAmmoAC5Flak);
        Assert.assertEquals(mockAmmoAC5Flak, testFireControl.getAntiAirAmmo(testAmmoList, mockWeaponTypeAC5, 5));

        // Test a list with 2 bins of standard and 0 flak ammo.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoAC5Std);
        testAmmoList.add(mockAmmoAC5Std);
        Assert.assertNull(testFireControl.getAntiAirAmmo(testAmmoList, mockWeaponTypeAC5, 5));

        // Test LBX weaponry.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLB10XCluster);
        testAmmoList.add(mockAmmoLB10XSlug);
        Assert.assertEquals(mockAmmoLB10XCluster, testFireControl.getAntiAirAmmo(testAmmoList, mockLB10X, 5));
    }

    @Test
    public void testGetClusterAmmo() {

        // Test an ammo list with only 1 bin of cluster ammo.
        List<Mounted> testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLB10XCluster);
        FireControl testFireControl = new FireControl(mockPrincess);
        Assert.assertEquals(mockAmmoLB10XCluster, testFireControl.getClusterAmmo(testAmmoList, mockLB10X, 5));

        // Test an ammo list with only 1 bin of slug ammo.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLB10XSlug);
        testFireControl = new FireControl(mockPrincess);
        Assert.assertNull(testFireControl.getClusterAmmo(testAmmoList, mockLB10X, 5));

        // Test with both loaded
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLB10XCluster);
        testAmmoList.add(mockAmmoLB10XSlug);
        Assert.assertEquals(mockAmmoLB10XCluster, testFireControl.getClusterAmmo(testAmmoList, mockLB10X, 5));
    }

    @Test
    public void testGetHeatAmmo() {

        // Test an ammo list with only 1 bin of incendiary ammo.
        List<Mounted> testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAc5Incendiary);
        FireControl testFireControl = new FireControl(mockPrincess);
        Assert.assertEquals(mockAmmoAc5Incendiary, testFireControl.getIncendiaryAmmo(testAmmoList, mockWeaponTypeAC5,
                                                                                     5));

        // Test an ammo list with only 1 bin of standard ammo.
        testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAC5Std);
        Assert.assertNull(testFireControl.getIncendiaryAmmo(testAmmoList, mockWeaponTypeAC5, 5));

        // Test a list with multiple types of ammo.
        testAmmoList = new ArrayList<Mounted>(3);
        testAmmoList.add(mockAmmoAC5Std);
        testAmmoList.add(mockAmmoAc5Incendiary);
        testAmmoList.add(mockAmmoAC5Flak);
        Assert.assertEquals(mockAmmoAc5Incendiary, testFireControl.getIncendiaryAmmo(testAmmoList, mockWeaponTypeAC5,
                                                                                     5));

        // Test LBX
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLB10XCluster);
        testAmmoList.add(mockAmmoLB10XSlug);
        Assert.assertNull(testFireControl.getIncendiaryAmmo(testAmmoList, mockLB10X, 5));

        // Test MMLs
        testAmmoList = new ArrayList<Mounted>(3);
        testAmmoList.add(mockAmmoLRM5);
        testAmmoList.add(mockAmmoSRM5);
        testAmmoList.add(mockAmmoInfero5);
        Assert.assertEquals(mockAmmoInfero5, testFireControl.getIncendiaryAmmo(testAmmoList, mockMML5, 4));
        Assert.assertEquals(mockAmmoInfero5, testFireControl.getIncendiaryAmmo(testAmmoList, mockMML5, 8));
        Assert.assertNull(testFireControl.getIncendiaryAmmo(testAmmoList, mockMML5, 10));
    }

    @Test
    public void testGetAntiInfantryAmmo() {

        // Test an ammo list with only 1 bin of flechette ammo.
        List<Mounted> testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAc5Flechette);
        FireControl testFireControl = new FireControl(mockPrincess);
        Assert.assertEquals(mockAmmoAc5Flechette, testFireControl.getAntiInfantryAmmo(testAmmoList,
                                                                                      mockWeaponTypeAC5, 5));

        // Test an ammo list with only 1 bin of standard ammo.
        testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAC5Std);
        Assert.assertNull(testFireControl.getAntiInfantryAmmo(testAmmoList, mockWeaponTypeAC5, 5));

        // Test a list with multiple types of ammo.
        testAmmoList = new ArrayList<Mounted>(3);
        testAmmoList.add(mockAmmoAC5Std);
        testAmmoList.add(mockAmmoAC5Flak);
        testAmmoList.add(mockAmmoAc5Flechette);
        Assert.assertEquals(mockAmmoAc5Flechette, testFireControl.getAntiInfantryAmmo(testAmmoList,
                                                                                      mockWeaponTypeAC5, 5));

        // Test LBX
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLB10XCluster);
        testAmmoList.add(mockAmmoLB10XSlug);
        Assert.assertEquals(mockAmmoLB10XCluster, testFireControl.getAntiInfantryAmmo(testAmmoList, mockLB10X, 5));

        // Test MMLs
        testAmmoList = new ArrayList<Mounted>(4);
        testAmmoList.add(mockAmmoLRM5);
        testAmmoList.add(mockAmmoSRM5);
        testAmmoList.add(mockAmmoInfero5);
        testAmmoList.add(mockAmmoLrm5Frag);
        Assert.assertEquals(mockAmmoInfero5, testFireControl.getAntiInfantryAmmo(testAmmoList, mockMML5, 4));
        Assert.assertEquals(mockAmmoLrm5Frag, testFireControl.getAntiInfantryAmmo(testAmmoList, mockMML5, 8));
        Assert.assertEquals(mockAmmoLrm5Frag, testFireControl.getAntiInfantryAmmo(testAmmoList, mockMML5, 10));
    }

    @Test
    public void testGetAntiVeeAmmo() {

        // Test an ammo list with only 1 bin of standard ammo.
        List<Mounted> testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAC5Std);
        FireControl testFireControl = new FireControl(mockPrincess);
        Assert.assertNull(testFireControl.getAntiVeeAmmo(testAmmoList, mockWeaponTypeAC5, 5, false));

        // Test an ammo list with only 1 bin of incendiary ammo.
        testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAc5Incendiary);
        testFireControl = new FireControl(mockPrincess);
        Assert.assertNull(testFireControl.getAntiVeeAmmo(testAmmoList, mockWeaponTypeAC5, 5, false));

        // Test a list with multiple types of ammo.
        testAmmoList = new ArrayList<Mounted>(3);
        testAmmoList.add(mockAmmoAC5Std);
        testAmmoList.add(mockAmmoAc5Incendiary);
        testAmmoList.add(mockAmmoAC5Flak);
        Assert.assertNull(testFireControl.getAntiVeeAmmo(testAmmoList, mockWeaponTypeAC5, 5, true));
        Assert.assertNull(testFireControl.getAntiVeeAmmo(testAmmoList, mockWeaponTypeAC5, 5, false));

        // Test LBX
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLB10XCluster);
        testAmmoList.add(mockAmmoLB10XSlug);
        Assert.assertEquals(mockAmmoLB10XCluster, testFireControl.getAntiVeeAmmo(testAmmoList, mockLB10X, 5, false));

        // Test MMLs
        testAmmoList = new ArrayList<Mounted>(4);
        testAmmoList.add(mockAmmoLRM5);
        testAmmoList.add(mockAmmoSRM5);
        testAmmoList.add(mockAmmoInfero5);
        testAmmoList.add(mockAmmoLrm5Frag);
        Assert.assertEquals(mockAmmoInfero5, testFireControl.getAntiVeeAmmo(testAmmoList, mockMML5, 4, false));
        Assert.assertEquals(mockAmmoInfero5, testFireControl.getAntiVeeAmmo(testAmmoList, mockMML5, 8, false));
        Assert.assertNull(testFireControl.getAntiVeeAmmo(testAmmoList, mockMML5, 4, true));
        Assert.assertNull(testFireControl.getAntiVeeAmmo(testAmmoList, mockMML5, 8, true));
        Assert.assertNull(testFireControl.getAntiVeeAmmo(testAmmoList, mockMML5, 10, false));
    }

    @Test
    public void testGetAtmAmmo() {

        // Test a list with just HE ammo.
        List<Mounted> testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAtm5He);
        FireControl testFireControl = new FireControl(mockPrincess);
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getAtmAmmo(testAmmoList, 5, mockTargetState, false));
        Assert.assertNull(testFireControl.getAtmAmmo(testAmmoList, 15, mockTargetState, false));

        // Test a list with just Standard ammo.
        testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAtm5St);
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 5, mockTargetState, false));
        Assert.assertNull(testFireControl.getAtmAmmo(testAmmoList, 20, mockTargetState, false));

        // Test a list with just ER ammo.
        testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoAtm5Er);
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getAtmAmmo(testAmmoList, 5, mockTargetState, false));

        // Test a list with all 3 ammo types
        testAmmoList = new ArrayList<Mounted>(3);
        testAmmoList.add(mockAmmoAtm5He);
        testAmmoList.add(mockAmmoAtm5Er);
        testAmmoList.add(mockAmmoAtm5St);
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getAtmAmmo(testAmmoList, 20, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getAtmAmmo(testAmmoList, 12, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 8, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getAtmAmmo(testAmmoList, 6, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 5, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getAtmAmmo(testAmmoList, 3, mockTargetState, false));

        // Test a list with just HE and Standard ammo types.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoAtm5He);
        testAmmoList.add(mockAmmoAtm5St);
        Assert.assertNull(testFireControl.getAtmAmmo(testAmmoList, 20, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 12, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 8, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getAtmAmmo(testAmmoList, 6, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 5, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getAtmAmmo(testAmmoList, 3, mockTargetState, false));

        // Test a list with just HE and ER ammo types.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoAtm5He);
        testAmmoList.add(mockAmmoAtm5Er);
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getAtmAmmo(testAmmoList, 20, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getAtmAmmo(testAmmoList, 12, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getAtmAmmo(testAmmoList, 8, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getAtmAmmo(testAmmoList, 6, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getAtmAmmo(testAmmoList, 5, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getAtmAmmo(testAmmoList, 3, mockTargetState, false));

        // Test a list with just Standard and ER ammo types.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoAtm5St);
        testAmmoList.add(mockAmmoAtm5Er);
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getAtmAmmo(testAmmoList, 20, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getAtmAmmo(testAmmoList, 12, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 8, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 6, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 5, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 3, mockTargetState, false));

        // Test targets that should be hit with infernos.
        Mockito.when(mockTargetState.isBuilding()).thenReturn(true);
        testAmmoList.add(mockAmmoAtm5He);
        testAmmoList.add(mockAmmoAtm5Er);
        testAmmoList.add(mockAmmoAtm5St);
        testAmmoList.add(mockAmmoAtm5Inferno);
        Assert.assertEquals(mockAmmoAtm5Inferno, testFireControl.getAtmAmmo(testAmmoList, 8, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 8, mockTargetState, true));
        Mockito.when(mockTargetState.isBuilding()).thenReturn(false);
        Mockito.when(mockTargetState.getHeat()).thenReturn(9);
        Assert.assertEquals(mockAmmoAtm5Inferno, testFireControl.getAtmAmmo(testAmmoList, 8, mockTargetState, false));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getAtmAmmo(testAmmoList, 8, mockTargetState, true));
        Mockito.when(mockTargetState.getHeat()).thenReturn(0);
    }

    @Test
    public void testGetGeneralMmlAmmo() {

        // Test a list with just SRM ammo.
        List<Mounted> testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoSRM5);
        FireControl testFireControl = new FireControl(mockPrincess);
        Assert.assertEquals(mockAmmoSRM5, testFireControl.getGeneralMmlAmmo(testAmmoList, 6));
        Assert.assertNull(testFireControl.getGeneralMmlAmmo(testAmmoList, 10));

        // Test a list with just LRM ammo.
        testAmmoList = new ArrayList<Mounted>(1);
        testAmmoList.add(mockAmmoLRM5);
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getGeneralMmlAmmo(testAmmoList, 10));
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getGeneralMmlAmmo(testAmmoList, 3));

        // Test a list with both types of ammo.
        testAmmoList = new ArrayList<Mounted>(2);
        testAmmoList.add(mockAmmoLRM5);
        testAmmoList.add(mockAmmoSRM5);
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getGeneralMmlAmmo(testAmmoList, 10));
        Assert.assertEquals(mockAmmoLRM5, testFireControl.getGeneralMmlAmmo(testAmmoList, 6));
        Assert.assertEquals(mockAmmoSRM5, testFireControl.getGeneralMmlAmmo(testAmmoList, 4));
    }

    @Test
    public void testGetPreferredAmmo() {
        Entity mockShooter = Mockito.mock(BipedMech.class);
        Targetable mockTarget = Mockito.mock(BipedMech.class);
        Mockito.when(((Entity) mockTarget).getArmorType(Mockito.anyInt())).thenReturn(EquipmentType.T_ARMOR_STANDARD);
        FireControl testFireControl = new FireControl(mockPrincess);

        ArrayList<Mounted> testAmmoList = new ArrayList<Mounted>(5);
        testAmmoList.add(mockAmmoAtm5He);
        testAmmoList.add(mockAmmoAtm5Er);
        testAmmoList.add(mockAmmoAtm5St);
        testAmmoList.add(mockAmmoAC5Std);
        testAmmoList.add(mockAmmoAC5Flak);
        testAmmoList.add(mockAmmoAc5Flechette);
        testAmmoList.add(mockAmmoAc5Incendiary);
        testAmmoList.add(mockAmmoLB10XCluster);
        testAmmoList.add(mockAmmoLB10XSlug);
        testAmmoList.add(mockAmmoSRM5);
        testAmmoList.add(mockAmmoInfero5);
        testAmmoList.add(mockAmmoLRM5);
        Mockito.when(mockShooter.getAmmo()).thenReturn(testAmmoList);
        Mockito.when(mockShooter.getPosition()).thenReturn(new Coords(10, 10));

        // Test ATMs
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 30));
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockAtm5));
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 22));
        Assert.assertEquals(mockAmmoAtm5Er, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockAtm5));
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 18));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockAtm5));
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 16));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockAtm5));
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 15));
        Assert.assertEquals(mockAmmoAtm5St, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockAtm5));
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 13));
        Assert.assertEquals(mockAmmoAtm5He, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockAtm5));

        // Test shooting an AC5 at a building.
        mockTarget = Mockito.mock(BuildingTarget.class);
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 15));
        Assert.assertEquals(mockAmmoAc5Incendiary, testFireControl.getPreferredAmmo(mockShooter, mockTarget,
                                                                                    mockWeaponTypeAC5));

        // Test shooting an LBX at an airborne target.
        mockTarget = Mockito.mock(VTOL.class);
        Mockito.when(((Entity) mockTarget).getArmorType(Mockito.anyInt())).thenReturn(EquipmentType.T_ARMOR_STANDARD);
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 15));
        Mockito.when(mockTarget.isAirborne()).thenReturn(true);
        Assert.assertEquals(mockAmmoLB10XCluster, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockLB10X));

        // Test shooting an LBX at a tank.
        mockTarget = Mockito.mock(Tank.class);
        Mockito.when(((Entity) mockTarget).getArmorType(Mockito.anyInt())).thenReturn(EquipmentType.T_ARMOR_STANDARD);
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 15));
        Assert.assertEquals(mockAmmoLB10XCluster, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockLB10X));

        // Test shooting an AC at infantry.
        mockTarget = Mockito.mock(Infantry.class);
        Mockito.when(((Entity) mockTarget).getArmorType(Mockito.anyInt())).thenReturn(EquipmentType.T_ARMOR_STANDARD);
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 15));
        Assert.assertTrue(
                mockAmmoAc5Flechette.equals(testFireControl.getPreferredAmmo(mockShooter, mockTarget,
                                                                             mockWeaponTypeAC5))
                        || mockAmmoAc5Incendiary.equals(testFireControl.getPreferredAmmo(mockShooter, mockTarget,
                                                                                         mockWeaponTypeAC5)));

        // Test a LBX at a heavily damaged target.
        mockTarget = Mockito.mock(BipedMech.class);
        Mockito.when(((Entity) mockTarget).getArmorType(Mockito.anyInt())).thenReturn(EquipmentType.T_ARMOR_STANDARD);
        Mockito.when(mockTarget.getPosition()).thenReturn(new Coords(10, 15));
        Mockito.when(((Entity) mockTarget).getDamageLevel()).thenReturn(Entity.DMG_HEAVY);
        Assert.assertEquals(mockAmmoLB10XCluster, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockLB10X));

        // Test a hot target.
        Mockito.when(((Entity) mockTarget).getDamageLevel()).thenReturn(Entity.DMG_LIGHT);
        Mockito.when(((Entity) mockTarget).getHeat()).thenReturn(12);
        Assert.assertEquals(mockAmmoInfero5, testFireControl.getPreferredAmmo(mockShooter, mockTarget,
                                                                              mockMML5));
        Mockito.when(((Entity) mockTarget).getArmorType(Mockito.anyInt()))
               .thenReturn(EquipmentType.T_ARMOR_HEAT_DISSIPATING);
        Assert.assertEquals(mockAmmoSRM5, testFireControl.getPreferredAmmo(mockShooter, mockTarget,
                                                                           mockMML5));
        Mockito.when(((Entity) mockTarget).getArmorType(Mockito.anyInt())).thenReturn(EquipmentType.T_ARMOR_STANDARD);

        // Test a normal target.
        Mockito.when(((Entity) mockTarget).getHeat()).thenReturn(4);
        Assert.assertEquals(mockAmmoAC5Std, testFireControl.getPreferredAmmo(mockShooter, mockTarget,
                                                                             mockWeaponTypeAC5));
        Assert.assertEquals(mockAmmoSRM5, testFireControl.getPreferredAmmo(mockShooter, mockTarget, mockMML5));
    }

    @Test
    public void testGuessToHitModifierHelperForAnyAttack() {
        FireControl testFireControl = Mockito.spy(new FireControl(mockPrincess));

        Entity mockShooter = Mockito.mock(BipedMech.class);
        Coords mockShooterCoords = new Coords(0, 0);
        EntityState mockShooterState = Mockito.mock(EntityState.class);
        Mockito.when(mockShooterState.getPosition()).thenReturn(mockShooterCoords);
        GameOptions mockGameOptions = Mockito.mock(GameOptions.class);
        IHex mockHex = Mockito.mock(IHex.class);
        IBoard mockBoard = Mockito.mock(IBoard.class);
        Mockito.when(mockBoard.getHex(Mockito.any(Coords.class))).thenReturn(mockHex);
        IGame mockGame = Mockito.mock(IGame.class);
        Mockito.when(mockGame.getOptions()).thenReturn(mockGameOptions);
        Mockito.when(mockGame.getBoard()).thenReturn(mockBoard);
        Entity mockTarget = Mockito.mock(BipedMech.class);
        ToHitData mockAttackerMoveMod = new ToHitData();
        Mockito.doReturn(mockAttackerMoveMod)
               .when(testFireControl)
               .getAttackerMovementModifier(Mockito.any(IGame.class), Mockito.anyInt(),
                                            Mockito.any(EntityMovementType.class));
        ToHitData mockTargetMoveMod = new ToHitData();
        Mockito.doReturn(mockTargetMoveMod)
               .when(testFireControl)
               .getTargetMovementModifier(Mockito.anyInt(), Mockito.anyBoolean(), Mockito.anyBoolean(),
                                          Mockito.any(IGame.class));

        // Test the most vanilla case we can.
        Mockito.when(mockShooterState.isProne()).thenReturn(false);
        Mockito.when(mockShooter.hasQuirk(Mockito.eq("anti_air"))).thenReturn(false);
        Mockito.when(mockTargetState.isImmobile()).thenReturn(false);
        Mockito.when(mockTargetState.getMovementType()).thenReturn(EntityMovementType.MOVE_NONE);
        Mockito.when(mockTargetState.getPosition()).thenReturn(new Coords(10, 0));
        Mockito.when(mockTargetState.isProne()).thenReturn(false);
        Mockito.when(mockTarget.isAirborne()).thenReturn(false);
        Mockito.when(mockTarget.isAirborneVTOLorWIGE()).thenReturn(false);
        Mockito.when(mockTarget.hasQuirk(Mockito.eq("low_profile"))).thenReturn(false);
        Mockito.when(mockGameOptions.booleanOption(Mockito.eq("tacops_standing_still"))).thenReturn(false);
        Mockito.when(mockHex.terrainLevel(Terrains.WOODS)).thenReturn(ITerrain.LEVEL_NONE);
        Mockito.when(mockHex.terrainLevel(Terrains.JUNGLE)).thenReturn(ITerrain.LEVEL_NONE);
        ToHitData expected = new ToHitData();
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));

        // Make the shooter prone.
        Mockito.when(mockShooterState.isProne()).thenReturn(true);
        expected = new ToHitData();
        expected.addModifier(2, FireControl.TH_ATT_PRONE);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockShooterState.isProne()).thenReturn(false);

        // Make the target immobile.
        Mockito.when(mockTargetState.isImmobile()).thenReturn(true);
        expected = new ToHitData();
        expected.addModifier(-4, FireControl.TH_TAR_IMMOBILE);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockTargetState.isImmobile()).thenReturn(false);

        // Have the target fall prone adjacent.
        Mockito.when(mockTargetState.isProne()).thenReturn(true);
        Mockito.when(mockTargetState.getPosition()).thenReturn(new Coords(0, 1));
        expected = new ToHitData();
        expected.addModifier(-2, FireControl.TH_TAR_PRONE_ADJ);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockTargetState.getPosition()).thenReturn(new Coords(10, 0)); // Move the target away.
        expected = new ToHitData();
        expected.addModifier(1, FireControl.TH_TAR_PRONE_RANGE);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockTargetState.getMovementType()).thenReturn(EntityMovementType.MOVE_SKID); // Have the target
        // skid.
        expected.addModifier(2, FireControl.TH_TAR_SKID);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockTargetState.isProne()).thenReturn(false);
        Mockito.when(mockTargetState.getMovementType()).thenReturn(EntityMovementType.MOVE_NONE);

        // Turn on Tac-Ops Standing Still rules.
        Mockito.when(mockGameOptions.booleanOption(Mockito.eq("tacops_standing_still"))).thenReturn(true);
        expected = new ToHitData();
        expected.addModifier(-1, FireControl.TH_TAR_NO_MOVE);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockTargetState.getMovementType()).thenReturn(EntityMovementType.MOVE_WALK); // Walking target.
        expected = new ToHitData();
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockGameOptions.booleanOption(Mockito.eq("tacops_standing_still"))).thenReturn(false);
        Mockito.when(mockTargetState.getMovementType()).thenReturn(EntityMovementType.MOVE_NONE);

        // Have the target sprint.
        Mockito.when(mockTargetState.getMovementType()).thenReturn(EntityMovementType.MOVE_SPRINT);
        expected = new ToHitData();
        expected.addModifier(-1, FireControl.TH_TAR_SPRINT);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockTargetState.getMovementType()).thenReturn(EntityMovementType.MOVE_NONE);

        // Stand the target in light woods.
        Mockito.when(mockHex.terrainLevel(Terrains.WOODS)).thenReturn(1);
        expected = new ToHitData();
        expected.addModifier(1, FireControl.TH_WOODS);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockHex.terrainLevel(Terrains.WOODS)).thenReturn(ITerrain.LEVEL_NONE);

        // Stand the target in heavy woods.
        Mockito.when(mockHex.terrainLevel(Terrains.WOODS)).thenReturn(2);
        expected = new ToHitData();
        expected.addModifier(2, FireControl.TH_WOODS);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockHex.terrainLevel(Terrains.WOODS)).thenReturn(ITerrain.LEVEL_NONE);

        // Stand the target in super heavy woods.
        Mockito.when(mockHex.terrainLevel(Terrains.WOODS)).thenReturn(3);
        expected = new ToHitData();
        expected.addModifier(3, FireControl.TH_WOODS);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockHex.terrainLevel(Terrains.WOODS)).thenReturn(ITerrain.LEVEL_NONE);

        // Stand the target in jungle.
        Mockito.when(mockHex.terrainLevel(Terrains.JUNGLE)).thenReturn(2);
        expected = new ToHitData();
        expected.addModifier(2, FireControl.TH_WOODS);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockHex.terrainLevel(Terrains.JUNGLE)).thenReturn(ITerrain.LEVEL_NONE);

        // Give the shooter the anti-air quirk but fire on a ground target.
        Mockito.when(mockShooter.hasQuirk(Mockito.eq("anti_air"))).thenReturn(true);
        expected = new ToHitData();
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockShooter.hasQuirk(Mockito.eq("anti_air"))).thenReturn(false);

        // Give the shooter the anti-air quirk, and fire on an airborne target.
        Mockito.when(mockShooter.hasQuirk(Mockito.eq("anti_air"))).thenReturn(true);
        mockTarget = Mockito.mock(ConvFighter.class);
        Mockito.when(mockTarget.isAirborne()).thenReturn(true);
        Mockito.when(mockTarget.isAirborneVTOLorWIGE()).thenReturn(false);
        expected = new ToHitData();
        expected.addModifier(-2, FireControl.TH_ANTI_AIR);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockShooter.hasQuirk(Mockito.eq("anti_air"))).thenReturn(false);
        mockTarget = Mockito.mock(BipedMech.class);
        Mockito.when(mockTarget.isAirborne()).thenReturn(false);
        Mockito.when(mockTarget.isAirborneVTOLorWIGE()).thenReturn(false);

        // Firing at Battle Armor
        mockTarget = Mockito.mock(BattleArmor.class);
        expected = new ToHitData();
        expected.addModifier(1, FireControl.TH_TAR_BA);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        mockTarget = Mockito.mock(BipedMech.class);

        // Firing at an ejected mechwarrior.
        mockTarget = Mockito.mock(MechWarrior.class);
        expected = new ToHitData();
        expected.addModifier(2, FireControl.TH_TAR_MW);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        mockTarget = Mockito.mock(BipedMech.class);

        // Firing at infantry
        mockTarget = Mockito.mock(Infantry.class);
        expected = new ToHitData();
        expected.addModifier(1, FireControl.TH_TAR_INF);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        mockTarget = Mockito.mock(BipedMech.class);

        // Target is low-profile.
        Mockito.when(mockTarget.hasQuirk(Mockito.eq("low_profile"))).thenReturn(true);
        expected = new ToHitData();
        expected.addModifier(1, FireControl.TH_TAR_LOW_PROFILE);
        assertToHitDataEqual(expected, testFireControl.guessToHitModifierHelperForAnyAttack(mockShooter,
                                                                                            mockShooterState,
                                                                                            mockTarget,
                                                                                            mockTargetState,
                                                                                            mockGame));
        Mockito.when(mockTarget.hasQuirk(Mockito.eq("low_profile"))).thenReturn(false);
    }

    private void assertToHitDataEqual(ToHitData expected, Object actual) {
        Assert.assertNotNull(actual);
        Assert.assertTrue("actual: " + actual.getClass().getName(), actual instanceof ToHitData);
        ToHitData actualTHD = (ToHitData) actual;
        Assert.assertEquals(expected.getValue(), actualTHD.getValue());
        Assert.assertEquals(expected.getDesc(), actualTHD.getDesc());
    }
}
