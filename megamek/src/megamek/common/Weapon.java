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
 * Created on May 10, 2004
 *
 */
package megamek.common;
import megamek.common.Compute;
import java.util.Enumeration;
import megamek.common.actions.*;
import megamek.client.FiringDisplay;
/**
 * @author Andrew Hunter
 * A class representing a weapon.
 */
public abstract class Weapon extends WeaponType {
	abstract public AttackHandler fire(WeaponAttackAction waa, Game g);
	public ToHitData toHit(WeaponAttackAction waa, Game game) {
		Targetable target = waa.getTarget(game);
		int attackerId = waa.getEntityId();
		int weaponId = waa.getWeaponId();
		final Entity ae = game.getEntity(attackerId);
		int aimingAt = waa.getAimedLocation();
		int aimingMode = waa.getAimingMode();
        Entity te = null;
        if (target.getTargetType() == Targetable.TYPE_ENTITY) {
            te = (Entity) target;
        }
        final Mounted weapon = ae.getEquipment(weaponId);
        final WeaponType wtype = (WeaponType)weapon.getType();
        boolean isAttackerInfantry = (ae instanceof Infantry);
        boolean isWeaponInfantry = wtype.hasFlag(WeaponType.F_INFANTRY);
        // 2003-01-02 BattleArmor MG and Small Lasers have unlimited ammo.
        // 2002-09-16 Infantry weapons have unlimited ammo.
        final boolean usesAmmo = wtype.getAmmoType() != AmmoType.T_NA &&
            wtype.getAmmoType() != AmmoType.T_BA_MG &&
            wtype.getAmmoType() != AmmoType.T_BA_SMALL_LASER &&
            !isWeaponInfantry && !wtype.hasFlag(WeaponType.F_ONESHOT);
        final Mounted ammo = usesAmmo ? weapon.getLinked() : null;
        final AmmoType atype = ammo == null ? null : (AmmoType)ammo.getType();
        final boolean targetInBuilding = Compute.isInBuilding( game, te );
        final boolean isOneShot = wtype.hasFlag(WeaponType.F_ONESHOT);
        boolean isIndirect = !(wtype.hasFlag(WeaponType.F_ONESHOT)) && wtype.getAmmoType() == AmmoType.T_LRM //For now, oneshot LRM launchers won't be able to indirect.  Sue me, until I can figure out a better fix.
            && weapon.curMode().equals("Indirect");
        boolean isInferno =
            ( atype != null &&
              atype.getMunitionType() == AmmoType.M_INFERNO ) ||
            ( isWeaponInfantry &&
              wtype.hasFlag(WeaponType.F_INFERNO) );
        boolean isArtilleryDirect= wtype.hasFlag(WeaponType.F_ARTILLERY) && game.getPhase() == Game.PHASE_FIRING;
        boolean isArtilleryIndirect = wtype.hasFlag(WeaponType.F_ARTILLERY) && (game.getPhase() == Game.PHASE_TARGETING || game.getPhase() == Game.PHASE_OFFBOARD);//hack, otherwise when actually resolves shot labeled impossible.
        Entity spotter = Compute.findSpotter(game, ae, target);

        
        int attEl = ae.absHeight();
        int targEl;

        if (te == null) {
            targEl = game.board.getHex(target.getPosition()).floor();
        } else {
            targEl = te.absHeight();
        }

        //TODO: mech making DFA could be higher if DFA target hex is higher
        //      BMRr pg. 43, "attacking unit is considered to be in the air
        //      above the hex, standing on an elevation 1 level higher than
        //      the target hex or the elevation of the hex the attacker is
        //      in, whichever is higher."

        

        // check LOS (indirect LOS is from the spotter)
        LosEffects los;
        ToHitData losMods;
        if (!isIndirect) {
            los = LosEffects.calculateLos(game, attackerId, target);
            losMods = los.losModifiers(game);
        } else {
            los = LosEffects.calculateLos(game, spotter.getId(), target);
            // do not count attacker partial cover in indirect fire
            los.setAttackerCover(false);
            losMods = los.losModifiers(game);
        }
        ToHitData toHit = impossibilityCheck(game, target, attackerId, weaponId, ae, te, weapon, wtype, isAttackerInfantry, usesAmmo, ammo, atype, isOneShot, isInferno, isArtilleryIndirect, isIndirect, spotter,targetInBuilding,losMods,los);
        if(toHit.getValue()==ToHitData.IMPOSSIBLE) {
        	return toHit;
        }

        

        // Leg attacks, Swarm attacks, and
        // Mine Launchers don't use gunnery.
        toHit=getBaseToHit(ae, te, wtype);
        
        

        // Is the pilot a weapon specialist?
        if (ae.crew.getOptions().stringOption("weapon_specialist").equals(wtype.getName())) {
            toHit.addModifier( -2, "weapon specialist" );
        }

        // determine some more variables
        int aElev = ae.getElevation();
        int tElev = target.getElevation();
        int distance = Compute.effectiveDistance(game, ae, target);


        // Handle direct artillery attacks.
        if(isArtilleryDirect) {
          toHit.addModifier(5, "direct artillery modifer");
          toHit.append(Compute.getAttackerMovementModifier(game, attackerId));
          toHit.append(losMods);
          toHit.append(Compute.getSecondaryTargetMod(game, ae, target));
          // actuator & sensor damage to attacker
          toHit.append(Compute.getDamageWeaponMods(ae, weapon));
          // heat
          if (ae.getHeatFiringModifier() != 0) {
            toHit.addModifier(ae.getHeatFiringModifier(), "heat");
          }
          // weapon to-hit modifier
          if (wtype.getToHitModifier() != 0) {
            toHit.addModifier(wtype.getToHitModifier(), "weapon to-hit modifier");
          }

          // ammo to-hit modifier
          if (usesAmmo && atype.getToHitModifier() != 0) {
            toHit.addModifier(atype.getToHitModifier(),
                              "ammunition to-hit modifier");
          }
          if (distance >17) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Direct artillery attack at range >17 hexes.");
          }
          return toHit;

        }
        if(isArtilleryIndirect) {
            int boardRange=(int)Math.ceil(((float)distance)/17f);
            if(boardRange>wtype.getLongRange()) {
                return new ToHitData(ToHitData.IMPOSSIBLE, "Indirect artillery attack out of range");
            }
            if(distance<=17  && !(losMods.getValue()==ToHitData.IMPOSSIBLE)) {
                return new ToHitData(ToHitData.IMPOSSIBLE, "Cannot fire indirectly at range <=17 hexes unless no LOS.");
            }
            toHit.addModifier(7, "indirect artillery modifier");
            int adjust = ae.aTracker.getModifier(weapon,target.getPosition());
            if(adjust==ToHitData.AUTOMATIC_SUCCESS) {
                return new ToHitData(ToHitData.AUTOMATIC_SUCCESS, "Artillery firing at target that's been hit before.");
            } else if(adjust!=0) {
                toHit.addModifier(adjust, "adjusted fire");
            }
            return toHit;

        }


        // Attacks against adjacent buildings automatically hit.
        if ( distance == 1 &&
             ( target.getTargetType() == Targetable.TYPE_BUILDING ||
               target.getTargetType() == Targetable.TYPE_BLDG_IGNITE ) ) {
            return new ToHitData( ToHitData.AUTOMATIC_SUCCESS,
                                  "Targeting adjacent building." );
        }

        // Attacks against buildings from inside automatically hit.
        if ( null != los.getThruBldg() &&
             ( target.getTargetType() == Targetable.TYPE_BUILDING ||
               target.getTargetType() == Targetable.TYPE_BLDG_IGNITE ) ) {
            return new ToHitData( ToHitData.AUTOMATIC_SUCCESS,
                                  "Targeting building from inside (are you SURE this is a good idea?)." );
        }

        // add range mods
        toHit.append(Compute.getRangeMods(game, ae, weaponId, target));

        // Battle Armor targets are hard for Meks and Tanks to hit.
        if ( !isAttackerInfantry && te != null && te instanceof BattleArmor ) {
            toHit.addModifier( 1, "battle armor target" );
        }

        // Indirect fire has a +1 mod
        if (isIndirect) {
            toHit.addModifier( 1, "indirect fire" );
        }

        // attacker movement
        toHit.append(Compute.getAttackerMovementModifier(game, attackerId));

        // target movement
        if (te != null) {
            ToHitData thTemp = Compute.getTargetMovementModifier(game, target.getTargetId());
            toHit.append(thTemp);

            // precision ammo reduces this modifier
            if (atype != null && atype.getAmmoType() == AmmoType.T_AC &&
                atype.getMunitionType() == AmmoType.M_PRECISION) {
                int nAdjust = Math.min(2, thTemp.getValue());
                if (nAdjust > 0) {
                    toHit.append(new ToHitData(-nAdjust, "Precision Ammo"));
                }
            }
        }

        // Armor Piercing ammo is a flat +1
        if ( (atype != null) &&
             (atype.getMunitionType() == AmmoType.M_ARMOR_PIERCING) ) {
            toHit.addModifier( 1, "Armor-Piercing Ammo" );
        }

        // spotter movement, if applicable
        if (isIndirect) {
            toHit.append(Compute.getSpotterMovementModifier(game, spotter.getId()));
        }

        // attacker terrain
        toHit.append(Compute.getAttackerTerrainModifier(game, attackerId));

        // target terrain, not applicable when delivering minefields
        if (target.getTargetType() != Targetable.TYPE_MINEFIELD_DELIVER) {
            toHit.append(Compute.getTargetTerrainModifier(game, target));
        }

        // target in water?
        Hex attHex = game.board.getHex(ae.getPosition());
        Hex targHex = game.board.getHex(target.getPosition());
        if (targHex.contains(Terrain.WATER) && targHex.surface() == targEl && te.height() > 0) { //target in partial water
            los.targetCover = true;
            losMods = los.losModifiers(game);
        }

        // add in LOS mods that we've been keeping
        toHit.append(losMods);

        // secondary targets modifier...
        toHit.append(Compute.getSecondaryTargetMod(game, ae, target));

        // heat
        if (ae.getHeatFiringModifier() != 0) {
            toHit.addModifier(ae.getHeatFiringModifier(), "heat");
        }

        // actuator & sensor damage to attacker
        toHit.append(Compute.getDamageWeaponMods(ae, weapon));

        // target immobile
        toHit.append(Compute.getImmobileMod(target, aimingAt, aimingMode));

        // attacker prone
        toHit.append(Compute.getProneMods(game, ae, weaponId));

        // target prone
        if (te != null && te.isProne()) {
            // easier when point-blank
            if (distance <= 1) {
                // BMRr, pg. 72: Swarm Mek attacks get "an additional -4
                // if the BattleMech is prone or immoble."  I interpret
                // this to mean that the bonus gets applied *ONCE*.
                if ( Infantry.SWARM_MEK.equals( wtype.getInternalName() ) ) {
                    // If the target is immoble, don't give a bonus for prone.
                    if ( !te.isImmobile() ) {
                        toHit.addModifier(-4, "swarm prone target");
                    }
                } else {
                    toHit.addModifier(-2, "target prone and adjacent");
                }
            }
            // harder at range
            else {
                toHit.addModifier(1, "target prone and at range");
            }
        }

        // weapon to-hit modifier
        if (wtype.getToHitModifier() != 0) {
            toHit.addModifier(wtype.getToHitModifier(), "weapon to-hit modifier");
        }

        // ammo to-hit modifier
        if (usesAmmo && atype.getToHitModifier() != 0) {
            toHit.addModifier(atype.getToHitModifier(), "ammunition to-hit modifier");
        }

        // add targeting computer (except with LBX cluster ammo)
        if (aimingMode == FiringDisplay.AIM_MODE_TARG_COMP &&
          aimingAt != Mech.LOC_NONE) {
          toHit.addModifier(3, "aiming with targeting computer");
        } else {
          if ( ae.hasTargComp() && wtype.hasFlag(WeaponType.F_DIRECT_FIRE) &&
               (!usesAmmo || atype.getMunitionType() != AmmoType.M_CLUSTER) ) {
              toHit.addModifier(-1, "targeting computer");
          }
      }

        // Change hit table for elevation differences inside building.
        if ( null != los.getThruBldg() && aElev != tElev ) {

            // Tanks get hit in a random side.
            if ( target instanceof Tank ) {
                toHit.setSideTable( ToHitData.SIDE_RANDOM );
            }

            // Meks have special tables for shots from above and below.
            else if ( target instanceof Mech ) {
                if ( aElev > tElev ) {
                    toHit.setHitTable( ToHitData.HIT_ABOVE );
                } else {
                    toHit.setHitTable( ToHitData.HIT_BELOW );
                }
            }

        }

        // Change hit table for partial cover, accomodate for partial underwater(legs)
        if (los.targetCover) {
            if ( ae.getLocationStatus(weapon.getLocation()) == Entity.LOC_WET && (targHex.contains(Terrain.WATER) && targHex.surface() == targEl && te.height() > 0) ) {
                //weapon underwater, target in partial water
                toHit.setHitTable(ToHitData.HIT_KICK);
            } else {
                toHit.setHitTable(ToHitData.HIT_PUNCH);
            }
        }

        // factor in target side
        if ( isAttackerInfantry && 0 == distance ) {
            // Infantry attacks from the same hex are resolved against the front.
            toHit.setSideTable( ToHitData.SIDE_FRONT );
        } else {
            toHit.setSideTable( Compute.targetSideTable(ae, target) );
        }

        // okay!
        return toHit;
	}
	/**
	 * @param ae
	 * @param te
	 * @param wtype
	 * @return
	 */
	private ToHitData getBaseToHit(final Entity ae, Entity te, final WeaponType wtype) {
		ToHitData toHit;
        if ( Infantry.LEG_ATTACK.equals( wtype.getInternalName() ) ) {
             toHit = Compute.getLegAttackBaseToHit( ae, te );

            // Return if the attack is impossible.
            if ( ToHitData.IMPOSSIBLE == toHit.getValue() ) {
                return toHit;
            }

            // If the attacker has Assault claws, give a -1 modifier.
            // We can stop looking when we find our first match.
            for ( Enumeration iter = ae.getMisc(); iter.hasMoreElements(); ) {
                Mounted mount = (Mounted) iter.nextElement();
                EquipmentType equip = mount.getType();
                if ( BattleArmor.ASSAULT_CLAW.equals
                     (equip.getInternalName()) ) {
                    toHit.addModifier( -1, "attacker has assault claws" );
                    break;
                }
            }
        }
        else if ( Infantry.SWARM_MEK.equals( wtype.getInternalName() ) ) {
            toHit = Compute.getSwarmMekBaseToHit( ae, te );

            // Return if the attack is impossible.
            if ( ToHitData.IMPOSSIBLE == toHit.getValue() ) {
                return toHit;
            }

            // If the attacker has Assault claws, give a -1 modifier.
            // We can stop looking when we find our first match.
            for ( Enumeration iter = ae.getMisc(); iter.hasMoreElements(); ) {
                Mounted mount = (Mounted) iter.nextElement();
                EquipmentType equip = mount.getType();
                if ( BattleArmor.ASSAULT_CLAW.equals
                     (equip.getInternalName()) ) {
                    toHit.addModifier( -1, "attacker has assault claws" );
                    break;
                }
            }
        }
        else if ( Infantry.STOP_SWARM.equals( wtype.getInternalName() ) ) {
            // Can't stop if we're not swarming, otherwise automatic.
            if ( Entity.NONE == ae.getSwarmTargetId() ) {
                return new ToHitData( ToHitData.IMPOSSIBLE,
                                      "Not swarming a Mek." );
            } else {
                return new ToHitData( ToHitData.AUTOMATIC_SUCCESS,
                                      "End swarm attack." );
            }
        }
        else if ( BattleArmor.MINE_LAUNCHER.equals(wtype.getInternalName()) ) {
            // Mine launchers can not hit infantry.
            if ( te instanceof Infantry ) {
                return new ToHitData( ToHitData.IMPOSSIBLE,
                                      "Can not attack infantry." );
            } else {
                return new ToHitData(8, "magnetic mine attack");
            }
        }
        // Swarming infantry always hit their target, but
        // they can only target the Mek they're swarming.
        else if ( te != null && ae.getSwarmTargetId() == te.getId() ) {
            // Only certain weapons can be used in a swarm attack.
            if ( wtype.getDamage() == 0 ) {
                return new ToHitData( ToHitData.IMPOSSIBLE,
                                      "Weapon causes no damage." );
            } else {
                return new ToHitData( ToHitData.AUTOMATIC_SUCCESS,
                                      "Attack during swarm.",
                                      ToHitData.HIT_SWARM,
                                      ToHitData.SIDE_FRONT );
            }
        }
        else if ( Entity.NONE != ae.getSwarmTargetId() ) {
            return new ToHitData( ToHitData.IMPOSSIBLE,
                                  "Must target the Mek being swarmed." );
        }
        else {
            return new ToHitData(ae.crew.getGunnery(), "gunnery skill");
            
        }
        return toHit;
	}
	/**
	 * @param game
	 * @param target
	 * @param attackerId
	 * @param weaponId
	 * @param ae
	 * @param te
	 * @param weapon
	 * @param wtype
	 * @param isAttackerInfantry
	 * @param usesAmmo
	 * @param ammo
	 * @param atype
	 * @param isOneShot
	 * @param isInferno
	 * @param isArtilleryIndirect
	 * @param isIndirect
	 * @param spotter
	 * @return
	 */
	private ToHitData impossibilityCheck(Game game, Targetable target, int attackerId, int weaponId, final Entity ae, Entity te, final Mounted weapon, final WeaponType wtype, boolean isAttackerInfantry, final boolean usesAmmo, final Mounted ammo, final AmmoType atype, final boolean isOneShot, boolean isInferno, boolean isArtilleryIndirect, boolean isIndirect, Entity spotter, boolean targetInBuilding, ToHitData losMods, LosEffects los) {
		// make sure weapon can deliver minefield
        if (target.getTargetType() == Targetable.TYPE_MINEFIELD_DELIVER &&
        	!AmmoType.canDeliverMinefield(atype)) {
			return new ToHitData(ToHitData.IMPOSSIBLE, "Weapon can't deliver minefields");
        }
        if((game.getPhase() == Game.PHASE_TARGETING) && !isArtilleryIndirect) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Only indirect artillery can be fired in the targeting phase");
        }


        if ( atype != null &&
             atype.getAmmoType() == AmmoType.T_LRM &&
             ( atype.getMunitionType() == AmmoType.M_THUNDER ||
               atype.getMunitionType() == AmmoType.M_THUNDER_ACTIVE ||
               atype.getMunitionType() == AmmoType.M_THUNDER_INFERNO ||
               atype.getMunitionType() == AmmoType.M_THUNDER_VIBRABOMB||
               atype.getMunitionType() == AmmoType.M_THUNDER_AUGMENTED ) &&
             target.getTargetType() != Targetable.TYPE_MINEFIELD_DELIVER) {
            return new ToHitData( ToHitData.IMPOSSIBLE,
                                  "Weapon can only deliver minefields" );
        }

        // make sure weapon can clear minefield
		if (target instanceof MinefieldTarget &&
			!AmmoType.canClearMinefield(atype)) {
			return new ToHitData(ToHitData.IMPOSSIBLE, "Weapon can't clear minefields");
		}
        // Arty shots have to be with arty, non arty shots with non arty.
               if (target.getTargetType() == Targetable.TYPE_HEX_ARTILLERY && !wtype.hasFlag(WeaponType.F_ARTILLERY)) {
               return new ToHitData(ToHitData.IMPOSSIBLE, "Weapon can't make artillery attacks.");
               }
               if (!(target.getTargetType() == Targetable.TYPE_HEX_ARTILLERY) && wtype.hasFlag(WeaponType.F_ARTILLERY)) {
               return new ToHitData(ToHitData.IMPOSSIBLE, "Weapon must make artillery attacks.");
               }


        // can't target yourself
        if (ae.equals(te)) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "You can't target yourself");
        }

        // weapon operational?
        if (weapon.isDestroyed() || weapon.isBreached()) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Weapon not operational.");
        }

        // got ammo?
        if ( usesAmmo && (ammo == null || ammo.getShotsLeft() == 0) ) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Weapon out of ammo.");
        }
        //Oneshot fired?
        if(isOneShot && weapon.isFired()) {
          return new ToHitData(ToHitData.IMPOSSIBLE, "One-shot weapon already fired.");
        }

        // Are we dumping that ammo?
        if ( usesAmmo && ammo.isDumping() ) {
            ae.loadWeapon( weapon );
            if ( ammo.getShotsLeft() == 0 || ammo.isDumping() ) {
                return new ToHitData( ToHitData.IMPOSSIBLE,
                                      "Dumping remaining ammo." );
            }
        }

        // is the attacker even active?
        if (ae.isShutDown() || !ae.getCrew().isActive()) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Attacker is in no condition to fire weapons.");
        }

        // sensors operational?
        final int sensorHits = ae.getBadCriticals(CriticalSlot.TYPE_SYSTEM, Mech.SYSTEM_SENSORS, Mech.LOC_HEAD);
        if (sensorHits > 1) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Attacker sensors destroyed.");
        }

        // Is the weapon blocked by a passenger?
        if ( ae.isWeaponBlockedAt(weapon.getLocation(), weapon.isRearMounted()) ) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Weapon blocked by passenger.");
        }

        // Can't target a entity conducting a swarm attack.
        if ( te != null && Entity.NONE != te.getSwarmTargetId() ) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Target is swarming a Mek.");
        }

        // Infantry can't clear woods.
        if ( isAttackerInfantry &&
             Targetable.TYPE_HEX_CLEAR == target.getTargetType() ) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Infantry can not clear woods.");
        }

        // Some weapons can't cause fires, but Infernos always can.
        if ( wtype.hasFlag(WeaponType.F_NO_FIRES) && !isInferno &&
             Targetable.TYPE_HEX_IGNITE == target.getTargetType() ) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Weapon can not cause fires.");
        }

        // Can't target infantry with Inferno rounds (BMRr, pg. 141).
        if ( te instanceof Infantry && isInferno ) {
            return new ToHitData( ToHitData.IMPOSSIBLE,
                  "Can not target infantry with Inferno rounds." );
        }

        // Can't raise the heat of infantry or tanks.
        if ( wtype.hasFlag(WeaponType.F_FLAMER) &&
             wtype.hasModes() &&
             weapon.curMode().equals("Heat") &&
             !(te instanceof Mech) ) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Can only raise the heat level of Meks.");
        }

        // Handle solo attack weapons.
        if ( wtype.hasFlag(WeaponType.F_SOLO_ATTACK) ) {
            for ( Enumeration i = game.getActions();
                  i.hasMoreElements(); ) {
                Object o = i.nextElement();
                if (!(o instanceof WeaponAttackAction)) {
                    continue;
                }
                WeaponAttackAction prevAttack = (WeaponAttackAction)o;
                if (prevAttack.getEntityId() == attackerId) {

                    // If the attacker fires another weapon, this attack fails.
                    if ( weaponId != prevAttack.getWeaponId() ) {
                        return new ToHitData(ToHitData.IMPOSSIBLE,
                                             "Other weapon attacks declared.");
                    }
                }
            }
        } // End current-weapon-is-solo
        
        //      check if indirect fire is valid
        if (isIndirect && !game.getOptions().booleanOption("indirect_fire")) {
            return new ToHitData(ToHitData.IMPOSSIBLE, "Indirect fire option not enabled");
              }

        // if we're doing indirect fire, find a spotter
         if (isIndirect) {
            
            if (spotter == null) {
                return new ToHitData( ToHitData.IMPOSSIBLE, "No available spotter");
            }
        }
//       if LOS is blocked, block the shot
         if (losMods.getValue() == ToHitData.IMPOSSIBLE && !isArtilleryIndirect) {
             return losMods;
         }

         // Must target infantry in buildings from the inside.
         if ( targetInBuilding &&
              te instanceof Infantry &&
              null == los.getThruBldg() ) {
             return new ToHitData(ToHitData.IMPOSSIBLE, "Attack on infantry crosses building exterior wall.");
         }

         // attacker partial cover means no leg weapons
         if (los.attackerCover && ae.locationIsLeg(weapon.getLocation())) {
             return new ToHitData(ToHitData.IMPOSSIBLE,
                                  "Nearby terrain blocks leg weapons.");
         }

         // Weapon in arc?
         if (!Compute.isInArc(game, attackerId, weaponId, target)) {
             return new ToHitData(ToHitData.IMPOSSIBLE, "Target not in arc.");
         }
        return new ToHitData();
	}
}
