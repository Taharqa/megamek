/**
 * MegaMek - Copyright (C) 2000-2002 Ben Mazur (bmazur@sev.org)
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

import java.io.*;

public class MepFile
{
    String version;
    String name;
      
    String mechYear;
    String innerSphere;
    String techYear;
    String chassisType;
    String tonnage;
      
    String engineType;
    String heatSinkType;
    String armorType;
    String internalType;
      
    String walkMP;
    String jumpMP;
    String heatSinks;
      
    String armorPoints;
    String armorPoints1;
    String armorPoints2;
      
    String headArmor;
    String larmArmor;
    String ltArmor;
    String ltrArmor;
    String ctArmor;
    String ctrArmor;
    String rtArmor;
    String rtrArmor;
    String rarmArmor;
    String llegArmor;
    String rlegArmor;
      
    String eqCount;
    String[] equipData;
      
    String eqWeight;
    String eqSlots;
      
    String[] critData;
  
  
    public MepFile(String filename) {
        try {
            BufferedReader r = new BufferedReader(new FileReader(filename));
      
            version = r.readLine();
            name = r.readLine();
      
            r.readLine(); // don't know what these are
            r.readLine();
            r.readLine();
            r.readLine();
            r.readLine();
      
            mechYear = r.readLine();
            innerSphere = r.readLine();
            techYear = r.readLine();
            chassisType = r.readLine();
            tonnage = r.readLine();
      
            engineType = r.readLine();
            heatSinkType = r.readLine();
            armorType = r.readLine();
            internalType = r.readLine();
      
            walkMP = r.readLine();
            jumpMP = r.readLine();
            heatSinks = r.readLine();
      
            r.readLine(); // weapons table -- useless?

            armorPoints = r.readLine();
            armorPoints1 = r.readLine(); // what are these two?
            armorPoints2 = r.readLine();
      
            headArmor = r.readLine();
            larmArmor = r.readLine();
            ltArmor = r.readLine();
            ltrArmor = r.readLine();
            ctArmor = r.readLine();
            ctrArmor = r.readLine();
            rtArmor = r.readLine();
            rtrArmor = r.readLine();
            rarmArmor = r.readLine();
            llegArmor = r.readLine();
            rlegArmor = r.readLine();
      
            eqCount = r.readLine();
      
            int eqs = Integer.parseInt(eqCount.substring(1));
            equipData = new String[eqs];
            for(int i = 0; i < eqs; i++) {
                equipData[i] = r.readLine();
            }
      
            eqWeight = r.readLine();
            eqSlots = r.readLine();
      
            r.readLine(); // mystery number
      
            int crits = 78;
            critData = new String[crits];
            for(int i = 0; i < crits; i++) {
                critData[i] = r.readLine();
            }
      
            r.close();
        } catch (IOException ex) {
            //arg!
            System.err.println("MepFile: error reading file");
        }
    }
  
    public Mech getMech() {
        Mech mech = new Mech();
    
        if(!this.techYear.equalsIgnoreCase("3025")) {
            return null;
        }
    
        int firstSpace = this.name.indexOf(" ");
        if(firstSpace != -1) {
            mech.setName(this.name.substring(firstSpace).trim());
            mech.setModel(this.name.substring(5, firstSpace).trim());
        } else {
            mech.setName(this.name.substring(5).trim());
            mech.setModel(this.name.substring(5).trim());
        }
    
        mech.weight = (float)Integer.decode(this.tonnage.trim()).intValue();
    
		mech.setOriginalWalkMP(Integer.parseInt(this.walkMP.trim()));
		mech.setOriginalJumpMP(Integer.parseInt(this.jumpMP.trim()));
		mech.heatSinks = Integer.parseInt(this.heatSinks.trim()) - 10;

        decodeArmorAndInternals(mech, Mech.LOC_HEAD, headArmor);
        decodeArmorAndInternals(mech, Mech.LOC_LARM, larmArmor);
        decodeArmorAndInternals(mech, Mech.LOC_LT, ltArmor);
        decodeRearArmor(mech, Mech.LOC_LTR, ltrArmor);
        decodeArmorAndInternals(mech, Mech.LOC_CT, ctArmor);
        decodeRearArmor(mech, Mech.LOC_CTR, ctrArmor);
        decodeArmorAndInternals(mech, Mech.LOC_RT, rtArmor);
        decodeRearArmor(mech, Mech.LOC_RTR, rtrArmor);
        decodeArmorAndInternals(mech, Mech.LOC_RARM, rarmArmor);
        decodeArmorAndInternals(mech, Mech.LOC_RLEG, rlegArmor);
        decodeArmorAndInternals(mech, Mech.LOC_LLEG, llegArmor);
    
        // remove arm actuators
        for (int i = 0; i < equipData.length; i++) {
            String eqName = new String(equipData[i]);
            eqName = eqName.substring(5, 28).trim();
            
            if (eqName.equals("No Lower Right Arm")) {
                mech.removeCriticals(Mech.LOC_RARM, new CriticalSlot(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_LOWER_ARM));
                mech.removeCriticals(Mech.LOC_RARM, new CriticalSlot(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_HAND));
            } else if (eqName.equals("No Lower Left Arm")) {
                mech.removeCriticals(Mech.LOC_LARM, new CriticalSlot(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_LOWER_ARM));
                mech.removeCriticals(Mech.LOC_LARM, new CriticalSlot(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_HAND));
            } else if (eqName.equals("No Right Hand")) {
                mech.removeCriticals(Mech.LOC_RARM, new CriticalSlot(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_HAND));
            } else if (eqName.equals("No Left Hand")) {
                mech.removeCriticals(Mech.LOC_LARM, new CriticalSlot(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_HAND));
            }
        }
        
        // hmm, what to do with the rest of equipment list... I dunno.
    
        // parse the critical hit slots
        for (int i = 0; i < critData.length; i++) {
            int loc = mech.getLocationFromAbbr(critData[i].substring(3, 5));
            int slot = Integer.parseInt(critData[i].substring(5, 7));
            boolean rearMounted = false;
            String critName = critData[i].substring(7).trim();
      
            // is it a weapon?
            if (critName.startsWith("(R) ")) {
                rearMounted = true;
                critName = critName.substring(4);
            }
            Weapon weaponType = Weapon.getWeaponByMepName(critName);
            if (weaponType != null) {
                if (rearMounted) {
                    mech.addWeapon(new MountedWeapon(weaponType), Mech.getRearLocation(loc));
                } else {
                    mech.addWeapon(new MountedWeapon(weaponType), loc);
                }
            }
      
            // ammo?
            Ammo ammo = Ammo.makeAmmo(critName);
            if (ammo != null) {
                mech.addAmmo(ammo, loc);
            }
      
            // heat sink?
            if (critName.equals("Heat Sink")) {
                mech.addCritical(loc, new CriticalSlot(CriticalSlot.TYPE_SYSTEM,
                                                       Mech.SYSTEM_HEAT_SINK));
            }
            // jump jet?
            if (critName.equals("Jump Jet")) {
                mech.addCritical(loc, new CriticalSlot(CriticalSlot.TYPE_SYSTEM,
                                                       Mech.SYSTEM_JUMP_JET));
            }
        }
    
    return mech;
  }
  
  /**
   * Decodes and sets the mech's armor and internal structure values
   */
  private void decodeArmorAndInternals(Mech mech, int loc, String s) {
    mech.setArmor(Integer.parseInt(s.substring(2, 4)), loc);
    mech.setInternal(Integer.parseInt(s.substring(12)), loc);
  }
  
  /**
   * Decodes and sets the mech's rear armor values
   */
  private void decodeRearArmor(Mech mech, int loc, String string) {
    mech.setArmor(Integer.parseInt(string.substring(2, 4)), loc);
  }
  
}