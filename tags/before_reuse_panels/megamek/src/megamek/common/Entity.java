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

import java.io.Serializable;
import java.util.*;
import java.awt.*;

import megamek.common.actions.*;
import megamek.common.util.StringUtil;

/**
 * Entity is a master class for basically anything on the board except
 * terrain.
 */
public abstract class Entity
    implements Serializable, Transporter, Targetable, RoundUpdated
{
    public interface MovementType {
      public static final int NONE    = 0; //Future expansion. Turrets?
      public static final int BIPED   = 1;
      public static final int QUAD    = 2;
      public static final int TRACKED = 3;
      public static final int WHEELED = 4;
      public static final int HOVER   = 5;
    }

    public static final int REMOVE_UNKNOWN        = 0x0000;
    public static final int REMOVE_IN_RETREAT     = 0x0100;
    public static final int REMOVE_PUSHED         = 0x0110;
    public static final int REMOVE_SALVAGEABLE    = 0x0200;
    public static final int REMOVE_EJECTED        = 0x0210;
    public static final int REMOVE_DEVASTATED     = 0x0400;
    public static final int REMOVE_NEVER_JOINED   = 0x0800;

    // weight class limits
    public static final int        WEIGHT_LIGHT        = 35;
    public static final int        WEIGHT_MEDIUM       = 55;
    public static final int        WEIGHT_HEAVY        = 75;
    public static final int        WEIGHT_ASSAULT      = 100;

    public static final int        NONE                = -1;

    public static final int        MOVE_SKID           = -2;
    public static final int        MOVE_ILLEGAL        = -1;
    public static final int        MOVE_NONE           = 0;
    public static final int        MOVE_WALK           = 1;
    public static final int        MOVE_RUN            = 2;
    public static final int        MOVE_JUMP           = 3;

    public static final int        ARMOR_NA            = -1;
    public static final int        ARMOR_DOOMED        = -2;
    public static final int        ARMOR_DESTROYED     = -3;

    public static final int        LOC_NONE            = -1;
    public static final int        LOC_DESTROYED       = -2;

    public static final int        LOC_BREACHED        = -1;
    public static final int        LOC_NORMAL          = 0;
    public static final int        LOC_VACUUM          = 1;
    public static final int        LOC_WET             = 2;

    public static final int        MAX_C3_NODES        = 12;

    // The maximum length of the icon name.
    public static final int        ICON_NAME_MAX_LENGTH = 52;

    protected transient Game    game;

    protected int               id = Entity.NONE;

    // ID settable by external sources (such as mm.net)
    protected int               externalId = Entity.NONE;

    protected float             weight;
    protected boolean           omni = false;
    protected String            chassis;
    protected String            model;
    protected int               year;
    protected int   techLevel;

    protected String            displayName = null;
    protected String            shortName = null;
    protected String            iconName = null;

    protected transient Player  owner;
    protected int               ownerId;

    public Pilot                crew = new Pilot();

    protected boolean           shutDown = false;
    protected boolean           doomed = false;
    protected boolean           destroyed = false;

    private Coords              position = null;

    protected int               facing = 0;
    protected int               sec_facing = 0;

    protected int               walkMP = 0;
    protected int               jumpMP = 0;

    protected boolean           done = false;

    protected boolean           prone = false;
    protected boolean           findingClub = false;
    protected boolean           armsFlipped = false;
    protected boolean           unjammingRAC = false;

    protected DisplacementAttackAction displacementAttack = null;

    public int                  heat = 0;
    public int                  heatBuildup = 0;
    public int                  delta_distance = 0;
    public int                  mpUsed = 0;
    public int                  moved = MOVE_NONE;

    private int[]               exposure;
    private int[]               armor;
    private int[]               internal;
    private int[]               orig_armor;
    private int[]               orig_internal;
    public int                  damageThisPhase;
    public int                  engineHitsThisRound;
    public boolean              rolledForEngineExplosion = false; //So that we don't roll twice in one round
    public boolean              dodging;

    public boolean        spotting;
    private boolean				clearingMinefield = false;
    private boolean 			selected = false;

    /**
     * The object that tracks this unit's Inferno round hits.
     */
    public InfernoTracker       infernos = new InfernoTracker();

    protected String            C3NetIdString = null;
    protected int               C3Master = NONE;
    protected int               C3CompanyMasterIndex = LOC_DESTROYED;

    protected Vector            equipmentList = new Vector();
    protected Vector            weaponList = new Vector();
    protected Vector            ammoList = new Vector();
    protected Vector            miscList = new Vector();

    // which teams have NARCd us?  a long allows for 64 teams.
    protected long              m_lNarcedBy = 0;
    protected long              m_lPendingNarc = 0;

    protected CriticalSlot[][]  crits; // [loc][slot]

    protected int               movementType  = MovementType.NONE;

    /**
     * The components of this entity that can transport other entities.
     */
    private Vector                transports = new Vector();

    /**
     * The ID of the <code>Entity</code> that has loaded this unit.
     */
    private int                 conveyance = Entity.NONE;

    /**
     * Set to <code>true</code> if this unit was unloaded this turn.
     */
    private boolean             unloadedThisTurn = false;

    /**
     * The id of the <code>Entity</code> that is the current target of a
     * swarm attack by this unit.
     */
    private int                 swarmTargetId = Entity.NONE;

    /**
     * The id of the <code>Entity</code> that is attacking this unit with
     * a swarm attack.
     */
    private int                 swarmAttackerId = Entity.NONE;

    /**
     * Flag that indicates that the unit can still
     * be salvaged (given enough time and parts).
     */
    private boolean             salvageable = true;

    /** The removal condition is set when the entitiy is removed from the game.
     */
    private int removalCondition = REMOVE_UNKNOWN;

    /**
     * The round this unit will be deployed
     */
    private int deployRound = 0;

    /**
     * Marks an entity as having been deployed
     */
    private boolean deployed = false;

    /**
     * Generates a new, blank, entity.
     */
    public Entity() {
        this.armor = new int[locations()];
        this.internal = new int[locations()];
        this.orig_armor = new int[locations()];
        this.orig_internal = new int[locations()];
        this.crits = new CriticalSlot[locations()][];
        this.exposure = new int[locations()];
        for (int i = 0; i < locations(); i++) {
            this.crits[i] = new CriticalSlot[getNumberOfCriticals(i)];
        }
        setC3NetId(this);
    }

    /**
     * Restores the entity after serialization
     */
    public void restore() {
        // restore all mounted equipments
        for (Enumeration i = equipmentList.elements(); i.hasMoreElements();) {
            Mounted mounted = (Mounted)i.nextElement();
            mounted.restore();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExternalId() {
        return externalId;
    }

    public void setExternalId(int externalId) {
        this.externalId = externalId;
    }

    public void setGame(Game game) {
        this.game = game;
        this.restore();
        // Make sure the owner is set.
        if ( null == owner ) {
            if ( Entity.NONE == ownerId ) {
                throw new IllegalStateException
                    ( "Entity doesn't know its owner's ID." );
            } else {
                setOwner( game.getPlayer(ownerId) );
            }
        }

        // Also set game for each entity "loaded" in this entity.
        Vector v = this.getLoadedUnits();
        Enumeration iter = v.elements();
        while (iter.hasMoreElements())
        {
           ((Entity)iter.nextElement()).setGame(game);
        }
    }

    /**
     * Returns the unit code for this entity.
     */
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the chassis name for this entity.
     */
    public String getChassis() {
        return chassis;
    }

    public void setChassis(String chassis) {
        this.chassis = chassis;
    }

    /**
     * Returns the unit tech for this entity.
     */
    public int getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(int techLevel) {
        this.techLevel = techLevel;
    }

    public boolean isClan() {
        return techLevel == TechConstants.T_CLAN_LEVEL_2;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getWeight() {
        return weight;
    }

    public int getWeightClass() {
        int nWeight = (int)getWeight();
         if (nWeight <= WEIGHT_LIGHT) {
            return WEIGHT_LIGHT;
         } else if (nWeight <= WEIGHT_MEDIUM) {
             return WEIGHT_MEDIUM;
         } else if (nWeight <= WEIGHT_HEAVY) {
             return WEIGHT_HEAVY;
         } else {
             return WEIGHT_ASSAULT;
         }
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isOmni() {
        return omni;
    }

    public void setOmni(boolean omni) {
        this.omni = omni;
    }

    /**
     * Returns the number of locations in the entity
     */
    public abstract int locations();

    /**
     * Returns the player that "owns" this entity.
     */
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        this.owner = player;
        this.ownerId = player.getId();

        generateDisplayName();
    }

    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Returns true if the other entity is an enemy of this entity.  This is
     * more reliable than Player.isEnemyOf since it knows that an entity will
     * never be an enemy of itself.
     */
    public boolean isEnemyOf(Entity other) {
        if ( null == owner ) {
            return ( id != other.getId() && ownerId != other.ownerId );
        }
        return id != other.getId() && owner.isEnemyOf(other.getOwner());
    }

    public Pilot getCrew() {
        return crew;
    }

    public void setCrew(Pilot crew) {
        this.crew = crew;
    }

    public boolean isShutDown() {
        return shutDown;
    }

    public void setShutDown(boolean shutDown) {
        this.shutDown = shutDown;
    }

    public boolean isDoomed() {
        return doomed;
    }

    public void setDoomed(boolean doomed) {
        // Doomed entities aren't in retreat.
        if ( doomed ) {
            this.setRemovalCondition( Entity.REMOVE_SALVAGEABLE );
        }
        this.doomed = doomed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    // Targetable interface
    public int getTargetType() { return Targetable.TYPE_ENTITY; }
    public int getTargetId() { return getId(); }
    public int getHeight() { return height(); }
    // End Targetable interface

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Determine if this entity participate in the current game phase.
     *
     * @return  <code>true</code> if this entity is not shut down, is
     *          not destroyed, has an active crew, and was not unloaded
     *          from a transport this turn.  <code>false</code> otherwise.
     */
    public boolean isActive() {
      return this.isActive(-1);
    }

    public boolean isActive(int turn) {
      boolean isActive = !shutDown && !destroyed && getCrew().isActive() && !this.unloadedThisTurn;

      if ( (turn > -1) && isActive ) {
        isActive = !deployed && shouldDeploy(turn);
      } else {
        isActive = isActive && deployed;
      }

      return isActive;
    }

    /**
     * Returns true if this entity is selectable for action.  Transported
     * entities can not be selected.
     */
    public boolean isSelectableThisTurn(Game game) {
        return !done && (conveyance == Entity.NONE) &&
            !this.unloadedThisTurn && !isClearingMinefield();
        //&& isActive(game.getPhase() == Game.PHASE_DEPLOYMENT ? game.getRoundCount() : -1)
    }

    /**
     * Determine if this <code>Entity</code> was unloaded previously this turn.
     *
     * @return  <code>true</code> if this entity was unloaded for any reason
     *          during this turn.
     */
    public boolean isUnloadedThisTurn() {
        return this.unloadedThisTurn;
    }

    /**
     * Returns true if this entity is targetable for attacks
     */
    public boolean isTargetable() {
        return !destroyed && !doomed && !crew.isDead() && deployed;
    }

    public boolean isProne() {
        return prone;
    }

    public void setProne(boolean prone) {
        this.prone = prone;
    }

    /**
     * Is this entity shut down or is the crew unconcious?
     */
    public boolean isImmobile() {
        return shutDown || crew.isUnconcious();
    }

    public boolean isCharging() {
        return displacementAttack instanceof ChargeAttackAction;
    }

    public boolean isPushing() {
        return displacementAttack instanceof PushAttackAction;
    }

    public boolean isMakingDfa() {
        return displacementAttack instanceof DfaAttackAction;
    }

    public boolean hasDisplacementAttack() {
        return displacementAttack != null;
    }

    public DisplacementAttackAction getDisplacementAttack() {
        return displacementAttack;
    }

    public void setDisplacementAttack(DisplacementAttackAction displacementAttack) {
        this.displacementAttack = displacementAttack;
    }

    /**
     * Returns true if any other entities this entity knows of are making a
     * displacement attack on this entity.
     */
    public boolean isTargetOfDisplacementAttack() {
        return findTargetedDisplacement() != null;
    }

    /**
     * Returns any known displacement attacks (should only be one) that this
     * entity is a target of.
     */
    public DisplacementAttackAction findTargetedDisplacement() {
        for (Enumeration i = game.getEntities(); i.hasMoreElements();) {
            Entity other = (Entity)i.nextElement();
            if (other.hasDisplacementAttack()
            && other.getDisplacementAttack().getTargetId() == id) {
                return other.getDisplacementAttack();
            }
        }
        return null;
    }

    public boolean isUnjammingRAC() {
        return unjammingRAC;
    }

    public void setUnjammingRAC(boolean u) {
        unjammingRAC = u;
    }

    public boolean isFindingClub() {
        return findingClub;
    }

    public void setFindingClub(boolean findingClub) {
        this.findingClub = findingClub;
    }

    /**
     * Set whether or not the mech's arms are flipped to the rear
     */
      public void setArmsFlipped(boolean armsFlipped) {
        this.armsFlipped = armsFlipped;
      }

    /**
     * Returns true if the mech's arms are flipped to the rear
     */
      public boolean getArmsFlipped() {
        return this.armsFlipped;
      }

    /**
     * Returns the current position of this entity on
     * the board.
     *
     * This is not named getLocation(), since I want the word location to
     * refer to hit locations on a mech or vehicle.
     */
    public Coords getPosition() {
        return position;
    }

    /**
     * Sets the current position of this entity on the board.
     *
     * @param position the new position.
     */
    public void setPosition(Coords position) {
        this.position = position;
    }

    /**
     * Returns the elevation of this entity.
     */
    public int getElevation() {
        Coords  pos = getPosition();

        if ( Entity.NONE != this.getTransportId() ) {
            pos = game.getEntity( this.getTransportId() ).getPosition();
        }

        if ( null == pos ) {
            throw new IllegalStateException
                ("Entity #" + this.getId() + " does not know its position.");
        }
        else if ( !game.board.contains(pos) ) {
            throw new IllegalStateException
                ("Board does not contain the Coords: " + pos + ".");
        }
        return elevationOccupied(game.board.getHex(pos));
    }

    /**
     * Returns the height of the unit, that is, how many levels above
     * it's elevation is it for LOS purposes.
     *
     * Default is 0.
     */
    public int height() {
        return 0;
    }

    /**
     * Returns the absolute height of the entity
     */
    public int absHeight() {
        return getElevation() + height();
    }

    /**
    * Returns the display name for this entity.
    */
    public String getDisplayName() {
        if (displayName == null) {
            generateDisplayName();
        }
        return displayName;
    }

    /**
     * Generates the display name for this entity.
     * <p/>
     * Sub-classes are allowed to override this method.
     *
     * The display name is in the format [Chassis] [Model] ([Player Name]).
     */
    protected void generateDisplayName() {
        StringBuffer nbuf = new StringBuffer();
        nbuf.append(chassis);
        if (model != null && model.length() > 0) {
            nbuf.append(" ").append(model);
        }

        if (getOwner() != null) {
            nbuf.append(" (").append(getOwner().getName()).append(")");
        }

        this.displayName = nbuf.toString();
    }

    /**
     * A short name, suitable for displaying above a unit icon.  The short name
     * is basically the same as the display name, minus the player name.
     */
    public String getShortName() {
        if (shortName == null) {
            generateShortName();
        }
        return shortName;
    }

    /**
     * Generate the short name for a unit
     * <p/>
     * Sub-classes are allowed to override this method.
     *
     * The display name is in the format [Chassis] [Model].
     */
    protected void generateShortName() {
        StringBuffer nbuf = new StringBuffer();
        nbuf.append(chassis);
        if (model != null && model.length() > 0) {
            nbuf.append(" ").append(model);
        }
        this.shortName = nbuf.toString();
    }

    // TODO : I really REALLY hate having an AWT class in the megamek.common
    //        package, but I'm not smart enough to figure out how to remove it
    public String getIconName(FontMetrics fm) {
        if (iconName == null) {
            generateIconName(fm);
        }
        return iconName;
    }

    public abstract void generateIconName(FontMetrics fm);

    /**
     * Returns the primary facing, or -1 if n/a
     */
    public int getFacing() {
        return facing;
    }

    /**
     * Sets the primary facing.
     */
    public void setFacing(int facing) {
        this.facing = facing;
    }

    /**
     * Returns the secondary facing, or -1 if n/a
     */
    public int getSecondaryFacing() {
        return sec_facing;
    }

    /**
     * Sets the secondary facing.
     */
    public void setSecondaryFacing(int sec_facing) {
        this.sec_facing = sec_facing;
    }

    /**
     * Can this entity change secondary facing at all?
     */
    public abstract boolean canChangeSecondaryFacing();

    /**
     * Can this entity torso/turret twist the given direction?
     */
    public abstract boolean isValidSecondaryFacing(int dir);

    /**
     * Returns the closest valid secondary facing to the given direction.
     *
     * @returns the the closest valid secondary facing.
     */
    public abstract int clipSecondaryFacing(int dir);

    /**
     * Returns true if the entity has an RAC
     */
      public boolean hasRAC() {
          for (Enumeration i = weaponList.elements(); i.hasMoreElements();) {
              Mounted mounted = (Mounted)i.nextElement();
              WeaponType wtype = (WeaponType)mounted.getType();
              if (wtype.getAmmoType() == AmmoType.T_AC_ROTARY) {
                  return true;
              }
          }
          return false;
      }

    /**
     * Returns true if the entity has an RAC which is jammed
     */
      public boolean canUnjamRAC() {
          for (Enumeration i = weaponList.elements(); i.hasMoreElements();) {
              Mounted mounted = (Mounted)i.nextElement();
              WeaponType wtype = (WeaponType)mounted.getType();
              if (wtype.getAmmoType() == AmmoType.T_AC_ROTARY && mounted.isJammed()) {
                  return true;
              }
          }
          return false;
      }

    /**
     * Returns true if the entity can flip its arms
     */
      public boolean canFlipArms() {
        return false;
      }

    /**
     * Returns this entity's original walking movement points
     */
    public int getOriginalWalkMP() {
        return walkMP;
    }

    /**
     * Sets this entity's original walking movement points
     */
    public void setOriginalWalkMP(int walkMP) {
        this.walkMP = walkMP;
    }

    /**
     * Returns this entity's walking/cruising mp, factored
     * for heat.
     */
    public int getWalkMP() {
        return Math.max(walkMP - (int)(heat / 5), 0);
    }

    /**
     * Returns this entity's unmodified running/flank mp.
     */
    protected int getOriginalRunMP() {
        return (int)Math.ceil(getOriginalWalkMP() * 1.5);
    }

    /**
     * Returns this entity's running/flank mp modified for heat.
     */
    public int getRunMP() {
        return (int)Math.ceil(getWalkMP() * 1.5);
    }

    public abstract int getRunMPwithoutMASC();

    /**
     * Returns this entity's running/flank mp as a string.
     */
    public String getRunMPasString() {
        return Integer.toString(getRunMP());
    }

    /**
     * Returns this entity's original jumping mp.
     */
    public int getOriginalJumpMP() {
        return jumpMP;
    }

    /**
     * Sets this entity's original jump movement points
     */
    public void setOriginalJumpMP(int jumpMP) {
        this.jumpMP = jumpMP;
    }

    /**
     * Returns this entity's current jumping MP, not effected by terrain.
     */
    public int getJumpMP() {
        return jumpMP;
    }

    /**
     * Returns this entity's current jumping MP, effected by terrain (like
     * water.)
     */
    public int getJumpMPWithTerrain() {
        return jumpMP;
    }

    /**
     * Returns the elevation that this entity would be on if it were placed
     * into the specified hex.
     */
    public int elevationOccupied(Hex hex) {
        return hex.floor();
    }

    /**
     * Returns the elevation that this entity would be on if it moved from an
     * adjacent hex, at the specified elevation, into the specified hex.
     *
     * Mechs might move into upper building levels, subs/vtols might stay at
     * their present elevation, etc.
     */
//    public int elevationOccupied(Hex hex, int elevation) {
//        return hex.floor();
//    }

    /**
     * Returns true if the specified hex contains some sort of prohibited
     * terrain.
     */
    public boolean isHexProhibited(Hex hex) {
        return false;
    }

    /**
     * Returns the name of the type of movement used.
     */
    public abstract String getMovementString(int mtype);

    /**
     * Returns the abbreviation of the name of the type of movement used.
     */
    public abstract String getMovementAbbr(int mtype);

    /**
     * Returns the name of the location specified.
     */
    public String getLocationName(HitData hit) {
      return getLocationName(hit.getLocation());
    }

    protected abstract String[] getLocationNames();

    /**
     * Returns the name of the location specified.
     */
    public String getLocationName(int loc) {
      String[] locationNames = getLocationNames();

      if ( (null == locationNames) || (loc >= locationNames.length) )
        return "";

      return locationNames[loc];
    }

    protected abstract String[] getLocationAbbrs();

    /**
     * Returns the abbreviated name of the location specified.
     */
    public String getLocationAbbr(HitData hit) {
      return getLocationAbbr(hit.getLocation()) + (hit.isRear() && hasRearArmor(hit.getLocation()) ? "R" : "") + (hit.getEffect() == HitData.EFFECT_CRITICAL ? " (critical)" : "");
    }

    /**
     * Returns the abbreviated name of the location specified.
     */
    public String getLocationAbbr(int loc) {
      String[] locationAbbrs = getLocationAbbrs();

      if ( (null == locationAbbrs) || (loc >= locationAbbrs.length) )
        return "";

      return locationAbbrs[loc];
    }

    /**
     * Returns the location that the specified abbreviation indicates
     */
    public int getLocationFromAbbr(String abbr) {
        for (int i = 0; i < locations(); i++) {
            if (getLocationAbbr(i).equalsIgnoreCase(abbr)) {
                return i;
            }
        }
      return Entity.LOC_NONE;
    }

    /**
     * Rolls the to-hit number
     */
    public abstract HitData rollHitLocation(int table, int side, int aimedLocation, int aimingMode);

    public abstract HitData rollHitLocation(int table, int side);

    /**
     * Gets the location that excess damage transfers to.  That is, one
     * location inwards.
     */
    public abstract HitData getTransferLocation(HitData hit);

    /** int version */
    public int getTransferLocation(int loc) {
        return getTransferLocation(new HitData(loc)).getLocation();
    }

    /**
     * Gets the location that is destroyed recursively.  That is, one location
     * outwards.
     */
    public abstract int getDependentLocation(int loc);

    /**
     * Does this location have rear armor?
     */
    public abstract boolean hasRearArmor(int loc);

    /**
     * Returns the amount of armor in the location specified,
     * or ARMOR_NA, or ARMOR_DESTROYED.  Only works on front locations.
     */
    public int getArmor(int loc) {
        return getArmor(loc, false);
    }

    /**
     * Returns the amount of armor in the location hit.
     */
    public int getArmor(HitData hit) {
        return getArmor(hit.getLocation(), hit.isRear());
    }

    /**
     * Returns the amount of armor in the location specified,
     * or ARMOR_NA, or ARMOR_DESTROYED.
     */
    public int getArmor(int loc, boolean rear) {
        return armor[loc];
    }

    /**
     * Returns the original amount of armor in the location specified,
     * or ARMOR_NA, or ARMOR_DESTROYED.  Only works on front locations.
     */
    public int getOArmor(int loc) {
        return getOArmor(loc, false);
    }

    /**
     * Returns the original amount of armor in the location hit.
     */
    public int getOArmor(HitData hit) {
        return getOArmor(hit.getLocation(), hit.isRear());
    }

    /**
     * Returns the original amount of armor in the location specified,
     * or ARMOR_NA, or ARMOR_DESTROYED.
     */
    public int getOArmor(int loc, boolean rear) {
        return orig_armor[loc];
    }

    /**
     * Sets the amount of armor in the location specified.
     */
    public void setArmor(int val, HitData hit) {
        setArmor(val, hit.getLocation(), hit.isRear());
    }

    /**
     * Sets the amount of armor in the front location specified.
     */
    public void setArmor(int val, int loc) {
        setArmor(val, loc, false);
    }

    /**
     * Sets the amount of armor in the location specified.
     */
    public void setArmor(int val, int loc, boolean rear) {
        armor[loc] = val;
    }

    /**
     * Initializes the armor on the unit. Sets the original and starting point
     * of the armor to the same number.
     */
      public void initializeArmor(int val, int loc) {
        orig_armor[loc] = val;
        setArmor(val, loc);
      }

    /**
    * Returns the total amount of armor on the entity.
    */
    public int getTotalArmor() {
        int totalArmor = 0;
        for (int i = 0; i < locations(); i++) {
            if (getArmor(i) > 0) {
                totalArmor += getArmor(i);
            }
            if (hasRearArmor(i) && getArmor(i, true) > 0) {
                totalArmor += getArmor(i, true);
            }
        }
        return totalArmor;
    }

    /**
    * Returns the total amount of armor on the entity.
    */
    public int getTotalOArmor() {
        int totalArmor = 0;
        for (int i = 0; i < locations(); i++) {
            if (getOArmor(i) > 0) {
                totalArmor += getOArmor(i);
            }
            if (hasRearArmor(i) && getOArmor(i, true) > 0) {
                totalArmor += getOArmor(i, true);
            }
        }
        return totalArmor;
    }

    /**
     * Returns the percent of the armor remaining
     */
      public double getArmorRemainingPercent() {
        return ((double)getTotalArmor() / (double)getTotalOArmor());
      }

    /**
     * Returns the amount of internal structure in the location hit.
     */
    public int getInternal(HitData hit) {
        return getInternal(hit.getLocation());
    }

    /**
     * Returns the amount of internal structure in the
     * location specified, or ARMOR_NA, or ARMOR_DESTROYED.
     */
    public int getInternal(int loc) {
        return internal[loc];
    }

    /**
     * Returns the original amount of internal structure in the location hit.
     */
    public int getOInternal(HitData hit) {
        return getOInternal(hit.getLocation());
    }

    /**
     * Returns the original amount of internal structure in the
     * location specified, or ARMOR_NA, or ARMOR_DESTROYED.
     */
    public int getOInternal(int loc) {
        return orig_internal[loc];
    }

    /**
     * Sets the amount of armor in the location specified.
     */
    public void setInternal(int val, HitData hit) {
        setInternal(val, hit.getLocation());
    }

    /**
     * Sets the amount of armor in the location specified.
     */
    public void setInternal(int val, int loc) {
        internal[loc] = val;
    }

    /**
     * Initializes the internal structure on the unit. Sets the original and starting point
     * of the internal structure to the same number.
     */
      public void initializeInternal(int val, int loc) {
        orig_internal[loc] = val;
        setInternal(val, loc);
      }

    /**
     * Set the internal structure to the appropriate value for the mech's
     * weight class
     */
    public abstract void autoSetInternal();

    /**
     * Returns the total amount of internal structure on the entity.
     */
    public int getTotalInternal() {
        int totalInternal = 0;
        for (int i = 0; i < locations(); i++) {
            if (getInternal(i) > 0) {
                totalInternal += getInternal(i);
            }
        }
        return totalInternal;
    }

    /**
     * Returns the total original amount of internal structure on the entity.
     */
    public int getTotalOInternal() {
        int totalInternal = 0;
        for (int i = 0; i < locations(); i++) {
            if (getOInternal(i) > 0) {
                totalInternal += getOInternal(i);
            }
        }
        return totalInternal;
    }

    /**
     * Returns the percent of the armor remaining
     */
      public double getInternalRemainingPercent() {
        return ((double)getTotalInternal() / (double)getTotalOInternal());
      }

    /**
    * Is this location destroyed?
    */
    public boolean isLocationDestroyed(int loc) {
        return getInternal(loc) == ARMOR_DESTROYED;
    }

    //returns exposure or breached flag for location
    public int getLocationStatus(int loc) {
        return exposure[loc];
    }

    //sets location exposure
    public void setLocationStatus(int loc, int status) {
        if (exposure[loc] > LOC_BREACHED) { //can't change BREACHED status
            exposure[loc] = status;
        }
    }

    /**
     * Returns true is the location is a leg
     */
    public boolean locationIsLeg(int loc) {
      return false;
    }

    /**
     * Returns a string representing the armor in the location
     */
    public String getArmorString(int loc) {
        return getArmorString(loc, false);
    }

    /**
     * Returns a string representing the armor in the location
     */
    public String getArmorString(int loc, boolean rear) {
        return armorStringFor(getArmor(loc, rear));
    }

    /**
     * Returns a string representing the internal structure in the location
     */
    public String getInternalString(int loc) {
        return armorStringFor(getInternal(loc));
    }

    /**
     * Parses the game's internal armor representation into a human-readable
     * string.
     */
    public static String armorStringFor(int value) {
        if (value == ARMOR_NA) {
            return "N/A";
        } else if (value == ARMOR_DOOMED || value == ARMOR_DESTROYED) {
            return "***";
        } else {
            return Integer.toString(value);
        }
    }

    /**
     * Returns the modifier to weapons fire due to heat.
     */
    public int getHeatFiringModifier() {
        int mod = 0;
        if (heat >= 8) {
            mod++;
        }
        if (heat >= 13) {
            mod++;
        }
        if (heat >= 17) {
            mod++;
        }
        if (heat >= 24) {
            mod++;
        }
        return mod;
    }

    /**
     * Creates a new mount for this equipment and adds it in.
     */
    public Mounted addEquipment(EquipmentType etype, int loc)
        throws LocationFullException
    {
        return addEquipment(etype, loc, false);
    }

    /**
     * Creates a new mount for this equipment and adds it in.
     */
    public Mounted addEquipment(EquipmentType etype, int loc, boolean rearMounted)
        throws LocationFullException
    {
        Mounted mounted = new Mounted(this, etype);
        addEquipment(mounted, loc, rearMounted);
        return mounted;
    }

    protected void addEquipment(Mounted mounted, int loc, boolean rearMounted)
        throws LocationFullException
    {
        mounted.setLocation(loc, rearMounted);
        equipmentList.addElement(mounted);

        // add it to the proper sub-list
        if (mounted.getType() instanceof WeaponType) {
            weaponList.addElement(mounted);
        }
        if (mounted.getType() instanceof AmmoType) {
            ammoList.addElement(mounted);
        }
        if (mounted.getType() instanceof MiscType) {
            miscList.addElement(mounted);
        }
    }

    /**
     * Returns the equipment number of the specified equipment, or
     * -1 if equipment is not present.
     */
    public int getEquipmentNum(Mounted mounted) {
        if (mounted != null) {
            return equipmentList.indexOf(mounted);
        } else {
            return -1;
        }
    }

    /**
     * Returns an enumeration of all equipment
     */
    public Enumeration getEquipment() {
        return equipmentList.elements();
    }

    /**
     * Returns the equipment, specified by number
     */
    public Mounted getEquipment(int index) {
        try {
            return (Mounted)equipmentList.elementAt(index);
        } catch (ArrayIndexOutOfBoundsException ex) {
            return null;
        }
    }

    public int getTotalAmmoOfType(EquipmentType et) {
        int totalShotsLeft = 0;
        for (Enumeration j = getAmmo(); j.hasMoreElements();) {
            Mounted amounted = (Mounted)j.nextElement();
            if (amounted.getType() == et && !amounted.isDumping()) {
                totalShotsLeft += amounted.getShotsLeft();
            }
        }
        return totalShotsLeft;
    }

    /**
     * Returns the Rules.ARC that the weapon, specified by
     * number, fires into.
     */
    public abstract int getWeaponArc(int wn);

    /**
     * Returns true if this weapon fires into the secondary facing arc.  If
     * false, assume it fires into the primary.
     */
    public abstract boolean isSecondaryArcWeapon(int weaponId);


    public Enumeration getWeapons() {
        return weaponList.elements();
    }

    public Vector getWeaponList() {
        return weaponList;
    }

    /**
     * Returns the first ready weapon
     *
     * @return the index number of the first available weapon, or -1 if none are ready.
     */
    public int getFirstWeapon() {
        for (Enumeration i = weaponList.elements(); i.hasMoreElements();) {
            Mounted mounted = (Mounted)i.nextElement();
            if (mounted.isReady()) {
                return getEquipmentNum(mounted);
            }
        }
        return -1;
    }

    /**
     * Returns the next ready weapon, starting at the specified index
     */
    public int getNextWeapon(int start) {
        boolean past = false;
        for (Enumeration i = weaponList.elements(); i.hasMoreElements();) {
            Mounted mounted = (Mounted)i.nextElement();
            if (past && mounted.isReady()) {
                return getEquipmentNum(mounted);
            }
            if (getEquipmentNum(mounted) == start) {
                past = true;
                continue;
            }
        }
        return getFirstWeapon();
    }

    /**
     * Loads all weapons with ammo
     */
    public void loadAllWeapons() {
        for (Enumeration i = weaponList.elements(); i.hasMoreElements();) {
            Mounted mounted = (Mounted)i.nextElement();
            WeaponType wtype = (WeaponType)mounted.getType();
            if (wtype.getAmmoType() != AmmoType.T_NA) {
                loadWeapon(mounted);
            }
        }
    }

    /**
     * Tries to load the specified weapon with the first available ammo
     */
    public void loadWeapon(Mounted mounted) {
        WeaponType wtype = (WeaponType)mounted.getType();
        for (Enumeration i = getAmmo(); i.hasMoreElements();) {
            Mounted mountedAmmo = (Mounted)i.nextElement();
            AmmoType atype = (AmmoType)mountedAmmo.getType();
            if (mountedAmmo.isDestroyed() || mountedAmmo.getShotsLeft() <= 0 || mountedAmmo.isDumping() || mountedAmmo.isBreached()) {
                continue;
            }
            if (atype.getAmmoType() == wtype.getAmmoType() && atype.getRackSize() == wtype.getRackSize()) {
                mounted.setLinked(mountedAmmo);
                break;
            }
        }
    }

    /**
     * Sets the ammo load to a specific ton
     */
    public void loadWeapon(Mounted mounted, Mounted mountedAmmo)
    {
        if (mountedAmmo.isDestroyed() || mountedAmmo.getShotsLeft() <= 0 || mountedAmmo.isDumping() || mountedAmmo.isBreached()) {
            return;
        }

        WeaponType wtype = (WeaponType)mounted.getType();
        AmmoType atype = (AmmoType)mountedAmmo.getType();
        if (atype.getAmmoType() == wtype.getAmmoType() && atype.getRackSize() == wtype.getRackSize()) {
            mounted.setLinked(mountedAmmo);
        }
    }


    /**
     * Checks whether a weapon has been fired from the specified location this
     * turn
     */
    public boolean weaponFiredFrom(int loc) {
        // check critical slots for used weapons
        for (int i = 0; i < this.getNumberOfCriticals(loc); i++) {
            CriticalSlot slot = getCritical(loc, i);
            // ignore empty & system slots
            if (slot == null || slot.getType() != CriticalSlot.TYPE_EQUIPMENT) {
                continue;
            }
            Mounted mounted = getEquipment(slot.getIndex());
            if (mounted.getType() instanceof WeaponType && mounted.isUsedThisRound()) {
                return true;
            }
        }
        return false;
    }

    public Enumeration getAmmo() {
        return ammoList.elements();
    }

    public Enumeration getMisc() {
        return miscList.elements();
    }

    /**
     * Removes the first misc eq. whose name equals the specified string.  Used
     * for removing broken tree clubs.
     */
    public void removeMisc(String toRemove) {
        for (Enumeration i = miscList.elements(); i.hasMoreElements();) {
            Mounted mounted = (Mounted)i.nextElement();
            if (mounted.getName().equals(toRemove)) {
                miscList.removeElement(mounted);
                equipmentList.removeElement(mounted);
                break;
            }
        }
    }


    /**`
     * Returns the about of heat that the entity can sink each
     * turn.
     */
    public abstract int getHeatCapacity();

    /**
     * Returns the about of heat that the entity can sink each
     * turn, factoring in whether the entity is standing in water.
     */
    public abstract int getHeatCapacityWithWater();

    /**
     * Returns extra heat generated by engine crits
     */
    public abstract int getEngineCritHeat();

    /**
     * Returns a critical hit slot
     */
    public CriticalSlot getCritical(int loc, int slot) {
        return crits[loc][slot];
    }

    /**
     * Sets a critical hit slot
     */
    public void setCritical(int loc, int slot, CriticalSlot cs) {
        crits[loc][slot] = cs;
    }

    /**
     * Removes all matching critical slots from the location
     */
    public void removeCriticals(int loc, CriticalSlot cs) {
        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            if (getCritical(loc, i) != null && getCritical(loc, i).equals(cs)) {
                setCritical(loc, i, null);
            }
        }
    }

    /**
     * Returns the number of empty critical slots in a location
     */
    public int getEmptyCriticals(int loc) {
        int empty = 0;

        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            if (getCritical(loc, i) == null) {
                empty++;
            }
        }

        return empty;
    }

    /**
     * Returns the number of operational critical slots remaining in a location
     */
    public int getHittableCriticals(int loc) {
        int empty = 0;

        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            if (getCritical(loc, i) != null && getCritical(loc, i).isHittable()) {
                empty++;
            }
        }

        return empty;
    }

    /**
     * Returns true if this location should transfer criticals to the next
     * location inwards.  Checks to see that every critical in this location
     * is either already totally destroyed (not just hit) or was never
     * hittable to begin with.
     */
    public boolean canTransferCriticals(int loc) {
        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            CriticalSlot crit = getCritical(loc, i);
            if (crit != null && !crit.isDestroyed() && crit.isEverHittable()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of operational critical slots of the specified type
     * in the location
     */
    public int getGoodCriticals(CriticalSlot cs, int loc) {
        return getGoodCriticals(cs.getType(), cs.getIndex(), loc);
    }

    /**
     * Returns the number of operational critical slots of the specified type
     * in the location
     */
    public int getGoodCriticals(int type, int index, int loc) {
        int operational = 0;

        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            CriticalSlot ccs = getCritical(loc, i);

            if (ccs != null && ccs.getType() == type && ccs.getIndex() == index
                && !ccs.isDestroyed()) {
                operational++;
            }

        }

        return operational;
    }

    /**
     * The number of critical slots that are destroyed in the component.
     */
    public int getDestroyedCriticals(int type, int index, int loc) {
        int hits = 0;

        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            CriticalSlot ccs = getCritical(loc, i);

            if (ccs != null && ccs.getType() == type && ccs.getIndex() == index) {
                if (ccs.isDestroyed() || ccs.isBreached()) {
                    hits++;
                }
            }

        }

        return hits;
    }

    /**
     * Number of slots doomed, missing or destroyed
     */
    public int getHitCriticals(int type, int index, int loc) {
        int hits = 0;

        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            CriticalSlot ccs = getCritical(loc, i);

            if (ccs != null && ccs.getType() == type && ccs.getIndex() == index) {
                if (ccs.isDamaged() || ccs.isBreached()) {
                    hits++;
                }
            }

        }

        return hits;
    }

    protected abstract int[] getNoOfSlots();
  /**
   * Returns the number of total critical slots in a location
   */
    public int getNumberOfCriticals(int loc) {
      int[] noOfSlots = getNoOfSlots();

      if ( (null == noOfSlots) || (loc >= noOfSlots.length) || loc == LOC_NONE ) {
        return 0;
      }

      return noOfSlots[loc];
    }

    /**
     * Returns the number of critical slots present in the section, destroyed
     * or not.
     */
    public int getNumberOfCriticals(int type, int index, int loc) {
        int num = 0;

        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            CriticalSlot ccs = getCritical(loc, i);

            if (ccs != null && ccs.getType() == type && ccs.getIndex() == index) {
                num++;
            }

        }

        return num;
    }

    /**
     * Returns true if the entity has a hip crit.
     * Overridden by sub-classes.
     */
    public boolean hasHipCrit() {
        return false;
    }

    /**
     * Returns true if the entity has a leg actuator crit
     */
      public boolean hasLegActuatorCrit() {
        boolean hasCrit = false;

        for ( int i = 0; i < locations(); i++ ) {
          if ( locationIsLeg(i) ) {
            if ( (getDestroyedCriticals(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_HIP, i) > 0) ||
                  (getDestroyedCriticals(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_UPPER_LEG, i) > 0) ||
                  (getDestroyedCriticals(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_LOWER_LEG, i) > 0) ||
                  (getDestroyedCriticals(CriticalSlot.TYPE_SYSTEM, Mech.ACTUATOR_FOOT, i) > 0) ) {
              hasCrit = true;
              break;
            }
          }
        }

        return hasCrit;
      }

    /**
     * Returns true if there is at least 1 functional system of the type
     * specified in the location
     */
    public boolean hasWorkingSystem(int system, int loc) {
        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            CriticalSlot ccs = getCritical(loc, i);
            if (ccs != null && ccs.getType() == CriticalSlot.TYPE_SYSTEM
            && ccs.getIndex() == system && !ccs.isDestroyed() && !ccs.isBreached()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the the location has a system of the type,
     * whether is destroyed or not
     */
    public boolean hasSystem(int system, int loc) {
        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            CriticalSlot ccs = getCritical(loc, i);
            if (ccs != null && ccs.getType() == CriticalSlot.TYPE_SYSTEM
            && ccs.getIndex() == system) {
                return true;
            }
        }
        return false;
    }

    /**
     * Does the mech have a functioning ECM unit?
     */
    public boolean hasActiveECM() {
        for (Enumeration e = getMisc(); e.hasMoreElements(); ) {
            Mounted m = (Mounted)e.nextElement();
            EquipmentType type = m.getType();
            if (type instanceof MiscType && type.hasFlag(MiscType.F_ECM)) {
                return !(m.isDestroyed() || m.isMissing() || m.isBreached());
            }
        }
        return false;
    }

    /**
     * What's the range of the ECM equipment?  Infantry can have ECM that
     * just covers their own hex.
     *
     * @return  the <code>int</code> range of this unit's ECM.  This value
     *          will be <code>Entity.NONE</code> if no ECM is active.
     */
    public int getECMRange() {
        for (Enumeration e = getMisc(); e.hasMoreElements(); ) {
            Mounted m = (Mounted)e.nextElement();
            EquipmentType type = m.getType();
            if ( type instanceof MiscType && type.hasFlag(MiscType.F_ECM) &&
                 !m.isDestroyed() && !m.isMissing() ) {
                if ( BattleArmor.SINGLE_HEX_ECM
                     .equals(type.getInternalName()) ) {
                    return 0;
                }
                else {
                    return 6;
                }
            }
        }
        return Entity.NONE;
    }

    public boolean hasTargComp() {
        for (Enumeration e = getMisc(); e.hasMoreElements(); ) {
            Mounted m = (Mounted)e.nextElement();
            if (m.getType() instanceof MiscType && m.getType().hasFlag(MiscType.F_TARGCOMP)) {
                return !(m.isDestroyed() || m.isMissing() || m.isBreached());
            }
        }
        return false;
    }

    public boolean hasAimModeTargComp() {
        for (Enumeration e = getMisc(); e.hasMoreElements(); ) {
            Mounted m = (Mounted)e.nextElement();
            if (m.getType() instanceof MiscType && m.getType().hasFlag(MiscType.F_TARGCOMP) && m.curMode().equals("Aimed shot")) {
                return !(m.isDestroyed() || m.isMissing() || m.isBreached());
            }
        }
        return false;
    }
    /**
     * Returns whether this 'mech is part of a C3 network.
     */

    public boolean hasC3S() {
        if (isShutDown()) return false;
        for (Enumeration e = getEquipment(); e.hasMoreElements(); ) {
            Mounted m = (Mounted)e.nextElement();
            if (m.getType() instanceof MiscType && m.getType().hasFlag(MiscType.F_C3S)
                    && !m.isDestroyed() && !m.isBreached()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasC3M() {
        if (isShutDown()) return false;
        for (Enumeration e = getEquipment(); e.hasMoreElements(); ) {
            Mounted m = (Mounted)e.nextElement();
            if (m.getType() instanceof MiscType && m.getType().hasFlag(MiscType.F_C3M)
                    && !m.isDestroyed() && !m.isBreached()) {
                // If this unit is configured as a company commander,
                // and if this computer is the company master, then
                // this unit does not have a lance master computer.
                if ( this.C3MasterIs(this) &&
                     this.C3CompanyMasterIndex == getEquipmentNum( m ) ) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean hasC3MM() {
        if (isShutDown()) return false;

        // Have we already determined that there's no company command master?
        if ( C3CompanyMasterIndex == LOC_NONE ) return false;

        // Do we need to determine that there's no company command master?
        if ( C3CompanyMasterIndex == LOC_DESTROYED ) {
            Enumeration e = getEquipment();
            while ( C3CompanyMasterIndex == LOC_DESTROYED &&
                    e.hasMoreElements() ) {
                Mounted m = (Mounted)e.nextElement();
                if ( m.getType() instanceof MiscType &&
                     m.getType().hasFlag(MiscType.F_C3M) &&
                     !m.isDestroyed() && !m.isBreached() ) {
                    // Now look for the company command master.
                    while ( C3CompanyMasterIndex == LOC_DESTROYED &&
                            e.hasMoreElements() ) {
                        m = (Mounted)e.nextElement();
                        if ( m.getType() instanceof MiscType &&
                             m.getType().hasFlag(MiscType.F_C3M) &&
                             !m.isDestroyed() && !m.isBreached() ) {
                            // Found the comany command master
                            this.C3CompanyMasterIndex = getEquipmentNum( m );
                        }
                    }
                }
            }
            // If we haven't found the company command master, there is none.
            if ( C3CompanyMasterIndex == LOC_DESTROYED ) {
                this.C3CompanyMasterIndex = LOC_NONE;
                return false;
            }
        }

        Mounted m = getEquipment( this.C3CompanyMasterIndex );
        if ( !m.isDestroyed() && !m.isBreached() ) {
            return true;
        }
        return false;
    }

    public boolean hasC3() {
      return hasC3S() | hasC3M() | hasC3MM();
    }

    public boolean hasC3i() {
        if (isShutDown()) return false;
        for (Enumeration e = getEquipment(); e.hasMoreElements(); ) {
            Mounted m = (Mounted)e.nextElement();
            if (m.getType() instanceof MiscType && m.getType().hasFlag(MiscType.F_C3I)
                    && !m.isDestroyed() && !m.isBreached() ) {
                return true;
            }
        }
        return false;
    }

    public String getC3NetId() {
        if (C3NetIdString == null) {
            if ( hasC3() ) {
                C3NetIdString = "C3." + getId();
            }
            else if ( hasC3i() ) {
                C3NetIdString = "C3i." + getId();
            }
        }
        return C3NetIdString;
    }

    public void setC3NetId(Entity e) {
        if (isEnemyOf(e)) return;
        C3NetIdString = e.C3NetIdString;
    }

    /**
     * Determine the remaining number of other C3 Master computers that
     * can connect to this <code>Entity</code>.
     *<p>Please note, if this <code>Entity</code> does not have two C3
     * Master computers, then it must first be identified as a company
     * commander; otherwise the number of free nodes will be zero.
     *
     * @return  a non-negative <code>int</code> value.
     */
    public int calculateFreeC3MNodes() {
        int nodes = 0;
        if ( hasC3MM() ) {
            nodes = 2;
            if (game != null) {
                for (java.util.Enumeration i = game.getEntities(); i.hasMoreElements();) {
                    final Entity e = (Entity)i.nextElement();
                    if (e.hasC3M() && e != this ) {
                        final Entity m = e.getC3Master();
                        if (equals(m)) nodes--;
                        if(nodes <= 0) return 0;
                    }
                }
            }
        }
        else if ( hasC3M() && this.C3MasterIs(this) )  {
            nodes = 3;
            if (game != null) {
                for (java.util.Enumeration i = game.getEntities(); i.hasMoreElements();) {
                    final Entity e = (Entity)i.nextElement();
                    if (e.hasC3() && e != this ) {
                        final Entity m = e.getC3Master();
                        if (equals(m)) nodes--;
                        if(nodes <= 0) return 0;
                    }
                }
            }
        }
        return nodes;
    }

    /**
     * Determine the remaining number of other C3 computers that can
     * connect to this <code>Entity</code>.
     *<p>Please note, if this <code>Entity</code> has two C3 Master
     * computers, then this function only returns the remaining number
     * of <b>C3 Slave</b> computers that can connect.
     *
     * @return  a non-negative <code>int</code> value.
     */
    public int calculateFreeC3Nodes() {
        int nodes = 0;
        if (hasC3i())  {
            nodes = 5;
            if (game != null) {
                for (java.util.Enumeration i = game.getEntities(); i.hasMoreElements();) {
                    final Entity e = (Entity)i.nextElement();
                    if (!equals(e) && onSameC3NetworkAs(e)) {
                        nodes--;
                        if(nodes <= 0) return 0;
                    }
                }
            }
        }
        else if (hasC3M())  {
            nodes = 3;
            if (game != null) {
                for (java.util.Enumeration i = game.getEntities(); i.hasMoreElements();) {
                    final Entity e = (Entity)i.nextElement();
                    if (e.hasC3() && !equals(e) ) {
                        final Entity m = e.getC3Master();
                        if (equals(m)) {
                            // If this unit is a company commander, and has two
                            // C3 Master computers, only count C3 Slaves here.
                            if ( !this.C3MasterIs(this) ||
                                 !this.hasC3MM() || e.hasC3S() )
                                nodes--;
                        }
                        if(nodes <= 0) return 0;
                    }
                }
            }
        }
        return nodes;
    }

    public Entity getC3Top() {
      Entity m = this;
      while(m.getC3Master() != null && !m.getC3Master().equals(m) && m.getC3Master().hasC3() &&
            !(Compute.isAffectedByECM(m, m.getPosition(), m.getC3Master().getPosition()))) {
        m = m.getC3Master();
      }
      return m;
    }

    /**
     * Return the unit that is current master of this unit's C3 network.  If
     * the master unit has been destroyed or had it's C3 master computer
     * damaged, then this unit is out of the C3 network for the rest of the
     * game.  If the master unit has shut down, then this unit may return to
     * the C3 network at a later time.
     *
     * @return  the <code>Entity</code> that is the master of this unit's C3
     *          network.  This value may be <code>null</code>.  If the value
     *          master unit has shut down, then the value will be
     *          non-<code>null</code> after the master unit restarts.
     */
    public Entity getC3Master() {
      if(C3Master == NONE) return null;
      if(hasC3S() && C3Master > NONE) {
          // since we can't seem to get the check working in setC3Master(), I'll just do it here, every time. This sucks.
          Entity eMaster = game.getEntity(C3Master);
          // Have we lost our C3Master?
          if (eMaster == null ) {
              C3Master = NONE;
          }
          // If our master is shut down, don't clear this slave's setting.
          else if ( eMaster.isShutDown() ) {
              return null;
          }
          // Slave computers can't connect to single-computer company masters.
          else if ( eMaster.C3MasterIs(eMaster) && !eMaster.hasC3MM() ) {
              C3Master = NONE;
          }
          // Has our lance master lost its computer?
          else if ( !eMaster.hasC3M() ) {
              C3Master = NONE;
          }
      }
      else if(hasC3M() && C3Master > NONE) {
          Entity eMaster = game.getEntity(C3Master);
          // Have we lost our C3Master?
          if (eMaster == null ) {
              C3Master = NONE;
          }
          // If our master is shut down, don't clear this slave's setting.
          else if ( eMaster.isShutDown() ) {
              return null;
          }
          // Lance commanders can't connect to non-company commanders.
          else if (!eMaster.C3MasterIs(eMaster)) {
              C3Master = NONE;
          }
          // Has our company commander lost his company command computer?
          else if ( ( eMaster.C3CompanyMasterIndex > LOC_NONE &&
                      !eMaster.hasC3MM() ) ||
                    ( eMaster.C3CompanyMasterIndex <= LOC_NONE &&
                      !eMaster.hasC3M() ) ) {
              C3Master = NONE;
          }
      }
      // If we aren't shut down, and if we don't have a company master
      // computer, but have a C3Master, then we must have lost our network.
      else if ( !isShutDown() && !hasC3MM() && C3Master > NONE ) {
          C3Master = NONE;
      }
      if (C3Master == NONE) {
          return null;
      } else {
          return game.getEntity(C3Master);
      }
    }

    /**
     * Get the ID of the master unit in this unit's C3 network.  If the
     * master unit has dhut down, then the ID will still be returned.  The
     * only times when the value, <code>Entity.NONE</code> is returned is when
     * this unit is permanently out of the C3 network, or when it was never
     * in a C3 network.
     *
     * @return  the <code>int</code> ID of the unit that is the master of
     *          this unit's C3 network, or <code>Entity.NONE</code>.
     */
    public int getC3MasterId() {
        // Make sure that this unit is still on a C3 network.
        // N.B. this call may set this.C3Master to NONE.
        this.getC3Master();
        return this.C3Master;
    }

    /**
     * Determines if the passed <code>Entity</code> is the C3 Master of this
     * unit.
     * <p>Please note, that when an <code>Entity</code> is it's own C3 Master,
     * then it is a company commander.
     * <p>Also note that when <code>null</code> is the master for this
     * <code>Entity</code>, then it is an independant master.
     *
     * @param   e - the <code>Entity</code> that may be this unit's C3 Master.
     * @return  a <code>boolean</code> that is <code>true</code> when the
     *          passed <code>Entity</code> is this unit's commander.  If
     *          the passed unit isn't this unit's commander, this routine
     *          returns <code>false</code>.
     */
    public boolean C3MasterIs(Entity e)
    {
        if(e == null && C3Master == NONE) return true;
        return (e.id == C3Master);
    }

    public void setC3Master(Entity e)
    {
        if(e == null) {
            setC3Master(NONE);
        }
        else {
            if (isEnemyOf(e)) return;
            setC3Master(e.id);
        }
    }

    public void setC3Master(int entityId)
    {
        if((id == entityId) != (id == C3Master))
        {   // this just changed from a company-level to lance-level (or vice versa); have to disconnect all slaved units to maintain integrity.
            for (java.util.Enumeration i = game.getEntities(); i.hasMoreElements();) {
                final Entity e = (Entity)i.nextElement();
                if(e.C3MasterIs(this) && !equals(e)) {
                   e.setC3Master(NONE);
                }
            }
        }
        if(hasC3()) C3Master = entityId;
        if(hasC3() && entityId == NONE) {
            C3NetIdString = "C3." + id;

        }
        else if(hasC3i() && entityId == NONE) {
            C3NetIdString = "C3i." + id;
        }
        else if ( hasC3() || hasC3i() ) {
            C3NetIdString = game.getEntity(entityId).getC3NetId();
        }

        for (java.util.Enumeration i = game.getEntities(); i.hasMoreElements();) {
            final Entity e = (Entity)i.nextElement();
            if ( e.C3MasterIs(this) && !equals(e) ) {
                e.C3NetIdString = C3NetIdString;
            }

        }

    }

    public boolean onSameC3NetworkAs(Entity e) {
      if(isEnemyOf(e)) return false;
      if (isShutDown() || e.isShutDown()) return false;

      // Active Mek Stealth prevents entity from participating in C3.
      // Turn off the stealth, and your back in the network.
      if ( this instanceof Mech && this.isStealthActive() ) return false;
      if ( e instanceof Mech && e.isStealthActive() ) return false;


      // C3i is easy - if they both have C3i, and their net ID's match, they're on the same network!
      if(hasC3i() && e.hasC3i() && getC3NetId().equals(e.getC3NetId())) {
        // check for ECM interference
        return !(Compute.isAffectedByECM(e, e.getPosition(), getPosition()));
      }

      // simple sanity check - do they both have C3, and are they both on the same network?
      if (!hasC3() || !e.hasC3()) return false;
      if (getC3Top() == null || e.getC3Top() == null) return false;
      // got the easy part out of the way, now we need to verify that the network isn't down
      return (getC3Top().equals(e.getC3Top()));
    }

    /**
     * Returns whether there is CASE protecting the location.
     */
    public boolean locationHasCase(int loc) {
        for (Enumeration i = miscList.elements(); i.hasMoreElements();) {
            Mounted mounted = (Mounted)i.nextElement();
            if (mounted.getLocation() == loc
            && mounted.getType().hasFlag(MiscType.F_CASE)
            && !mounted.isDestroyed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a CriticalSlot into the first empty slot
     *
     * TODO: throw exception if full, maybe?
     */
    public void addCritical(int loc, CriticalSlot cs) {
        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            if (getCritical(loc, i) == null) {
                setCritical(loc, i, cs);
                return;
            }
        }
    }

    /**
     * Hits all criticals of the system occupying the specified critical
     * slot.  Used, for example, in a gauss rifle capacitor discharge.
     * Does not apply any special effect of hitting the criticals, like ammo
     * explosion.
     */
    public void hitAllCriticals(int loc, int slot) {
        CriticalSlot orig = getCritical(loc, slot);
        for (int i = 0; i < getNumberOfCriticals(loc); i++) {
            CriticalSlot cs = getCritical(loc, slot);
            if (cs.getType() == orig.getType() && cs.getIndex() == orig.getIndex()) {
                cs.setHit(true);
            }
        }
    }

    public void newRound(int roundNumber)
    {
        unloadedThisTurn = false;
        done = false;
        delta_distance = 0;
        mpUsed = 0;
        moved = Entity.MOVE_NONE;

        setArmsFlipped(false);
        setDisplacementAttack(null);
        setFindingClub(false);
        setSpotting(false);
        setClearingMinefield(false);
        setUnjammingRAC(false);
        crew.setKoThisRound(false);
        m_lNarcedBy |= m_lPendingNarc;

        for (Enumeration i = getEquipment(); i.hasMoreElements();) {
            ((Mounted)i.nextElement()).newRound(roundNumber);
        }

        // Update the inferno tracker.
        this.infernos.newRound(roundNumber);
    }

    /**
     * Applies any damage that the entity has suffered.  When anything gets hit
     * it is simply marked as "hit" but does not stop working until this
     * is called.
     */
    public void applyDamage() {
        // mark all damaged equipment destroyed and empty
        for (Enumeration i = getEquipment(); i.hasMoreElements();) {
            Mounted mounted = (Mounted)i.nextElement();
            if (mounted.isHit() || mounted.isMissing()) {
                mounted.setShotsLeft(0);
                mounted.setDestroyed(true);
            }
        }

        // destroy criticals that were hit last phase
        for (int i = 0; i < locations(); i++) {
            for (int j = 0; j < getNumberOfCriticals(i); j++) {
                final CriticalSlot cs = getCritical(i, j);
                if (cs != null) {
                    cs.setDestroyed(cs.isDamaged());
                }
            }
        }

        // destroy armor/internals if the section was removed
        for (int i = 0; i < locations(); i++) {
            if (getInternal(i) == Entity.ARMOR_DOOMED) {
                setArmor(Entity.ARMOR_DESTROYED, i);
                setArmor(Entity.ARMOR_DESTROYED, i, true);
                setInternal(Entity.ARMOR_DESTROYED, i);
            }
        }
    }

    /**
     * Attempts to reload any empty weapons with the first ammo found
     */
    public void reloadEmptyWeapons() {
        // try to reload weapons
        for (Enumeration i = getWeapons(); i.hasMoreElements();) {
            Mounted mounted = (Mounted) i.nextElement();
            WeaponType wtype = (WeaponType) mounted.getType();

            if (wtype.getAmmoType() != AmmoType.T_NA
                && (wtype.getFlags() & WeaponType.F_INFANTRY)
                    != WeaponType.F_INFANTRY) {
                if (mounted.getLinked() == null
                    || mounted.getLinked().getShotsLeft() <= 0
                    || mounted.getLinked().isDumping()) {
                    loadWeapon(mounted);
                }
            }
        }
    }

    /**
     * Assign AMS systems to the most dangerous incoming missile attacks.
     * This should only be called once per turn, or AMS will get extra attacks
     */
    public void assignAMS(Vector vAttacks) {

        for (Enumeration e = getWeapons(); e.hasMoreElements(); ) {
            Mounted weapon = (Mounted)e.nextElement();
            if (((WeaponType)weapon.getType()).getAmmoType() == AmmoType.T_AMS) {
                if (!weapon.isReady() || weapon.isMissing()) {
                    continue;
                }
                // don't try if it's turned off
                if (weapon.curMode().equals("Off")) {
                    continue;
                }

                // make sure ammo is loaded
                Mounted ammo = weapon.getLinked();
                if ( ammo == null || ammo.getShotsLeft() == 0 ||
                     ammo.isDumping() ) {
                    loadWeapon(weapon);
                    ammo = weapon.getLinked();
                }

                // try again
                if ( ammo == null || ammo.getShotsLeft() == 0 ||
                     ammo.isDumping() ) {
                    // No ammo for this AMS.
                    continue;
                }

                // make a new vector of only incoming attacks in arc
                Vector vAttacksInArc = new Vector(vAttacks.size());
                for (Enumeration i = vAttacks.elements(); i.hasMoreElements();) {
                    WeaponAttackAction waa = (WeaponAttackAction)i.nextElement();
                    if (Compute.isInArc(game, this.getId(), getEquipmentNum(weapon),
                            game.getEntity(waa.getEntityId()))) {
                        vAttacksInArc.addElement(waa);
                    }
                }
                // find the most dangerous salvo by expected damage
                WeaponAttackAction waa = Compute.getHighestExpectedDamage(game, vAttacksInArc);
                if (waa != null) {
                    waa.addCounterEquipment(weapon);
                }
            }
        }
    }

    // add a narc pod from this team to the mech.  Unremovable
    public void setNarcedBy(int nTeamID) {
        // avoid overflow in ridiculous battles
        if (nTeamID > (1 << Player.MAX_TEAMS)) {
            System.out.println("Narc system can't handle team IDs this high!");
            return;
        }
        long teamMask = 1;
        if ( nTeamID > Player.TEAM_NONE ) {
            teamMask = 1 << nTeamID;
        }
        m_lPendingNarc |= teamMask;
    }

    // has the team attached a narc pod to me?
    public boolean isNarcedBy(int nTeamID) {
        long teamMask = 1;
        if ( nTeamID > Player.TEAM_NONE ) {
            teamMask = 1 << nTeamID;
        }
        return (m_lNarcedBy & teamMask) > 0;
    }


    /**
     * Calculates the battle value of this entity
     */
    public abstract int calculateBattleValue();

    /**
     * Calculates the battle value of this mech.
     *  If the parameter is true, then the battle value for
     *  c3 will be added whether the mech is currently part of
     *  a network or not.
     *
     * This should be overwritten if necessary
     */
    public int calculateBattleValue(boolean assumeLinkedC3){
    	return calculateBattleValue();
    }

    /**
     * Generates a string containing a report on all useful information about
     * this entity.
     */
    public abstract String victoryReport();

    /**
     * Two entities are equal if their ids are equal
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Entity other = (Entity)object;
        return other.getId() == this.id;
    }

    /**
     * Get the movement type of the entity
     */
      public  int getMovementType() {
        return movementType;
      }

    public String getMovementTypeAsString() {
        switch (getMovementType()) {
        case Entity.MovementType.NONE:
            return "None";
        case Entity.MovementType.BIPED:
            return "Biped";
        case Entity.MovementType.QUAD:
            return "Quad";
        case Entity.MovementType.TRACKED:
            return "Tracked";
        case Entity.MovementType.WHEELED:
            return "Wheeled";
        case Entity.MovementType.HOVER:
            return "Hover";
        default:
            return "ERROR";
        }
    }

    /**
     * Set the movement type of the entity
     */
      public void setMovementType(int movementType) {
        this.movementType = movementType;
      }

    /**
     * Helper function to determine if a entity is a biped
     */
      public boolean entityIsBiped() { return (getMovementType() == Entity.MovementType.BIPED); }

    /**
     * Helper function to determine if a entity is a quad
     */
      public boolean entityIsQuad() { return (getMovementType() == Entity.MovementType.QUAD); }

    /**
     * Returns true is the entity needs a roll to stand up
     */
      public boolean needsRollToStand() {
        return true;
      }

    /**
     * Returns an entity's base piloting skill roll needed
     */
    public PilotingRollData getBasePilotingRoll() {
        final int entityId = getId();

        PilotingRollData roll;

        // Pilot dead?
        if ( getCrew().isDead() || getCrew().isDoomed()
             || getCrew().getHits() >= 6 ) {
            return new PilotingRollData(entityId, PilotingRollData.IMPOSSIBLE, "Pilot dead");
        }
        // pilot awake?
        else if (!getCrew().isActive()) {
            return new PilotingRollData(entityId, PilotingRollData.IMPOSSIBLE, "Pilot unconcious");
        }
        // gyro operational?
        if (getDestroyedCriticals(CriticalSlot.TYPE_SYSTEM, Mech.SYSTEM_GYRO, Mech.LOC_CT) > 1) {
            return new PilotingRollData(entityId, PilotingRollData.AUTOMATIC_FAIL, 3, "Gyro destroyed");
        }
        // both legs present?
        if ( this instanceof BipedMech ) {
          if ( ((BipedMech)this).countDestroyedLegs() == 2 )
            return new PilotingRollData(entityId, PilotingRollData.AUTOMATIC_FAIL, 10, "Both legs destroyed");
        } else if ( this instanceof QuadMech ) {
          if ( ((QuadMech)this).countDestroyedLegs() >= 3 )
            return new PilotingRollData(entityId, PilotingRollData.AUTOMATIC_FAIL, 10, ((Mech)this).countDestroyedLegs() + " legs destroyed");
        }
        // entity shut down?
        if (isShutDown()) {
            return new PilotingRollData(entityId, PilotingRollData.AUTOMATIC_FAIL, 3, "Reactor shut down");
        }

        // okay, let's figure out the stuff then
        roll = new PilotingRollData(entityId, getCrew().getPiloting(), "Base piloting skill");

        //Let's see if we have a modifier to our piloting skill roll. We'll pass in the roll
        //object and adjust as necessary
          roll = addEntityBonuses(roll);

        return roll;
    }

    /**
     * Add in any piloting skill mods
     */
      public abstract PilotingRollData addEntityBonuses(PilotingRollData roll);

    /**
     * Checks if the entity is getting up.  If so, returns the
     *  target roll for the piloting skill check.
     */
    public PilotingRollData checkGetUp(MoveStep step) {
        PilotingRollData roll = getBasePilotingRoll();

        if (step.getType() != MovePath.STEP_GET_UP) {
            roll.addModifier(TargetRoll.CHECK_FALSE,"Check false");
            return roll;
        }

        if (!needsRollToStand() && (getDestroyedCriticals(CriticalSlot.TYPE_SYSTEM, Mech.SYSTEM_GYRO,Mech.LOC_CT) < 2)) {
            roll.addModifier(TargetRoll.AUTOMATIC_SUCCESS,"\n" + getDisplayName() + " does not need to make a piloting skill check to stand up because it has all four of its legs.");
            return roll;
        }

        // append the reason modifier
        roll.append(new PilotingRollData(getId(), 0, "getting up"));

        return roll;
    }

    /**
     * Checks if the entity is attempting to run with damage that would
     *  force a PSR.  If so, returns the target roll for the piloting
     *  skill check.
     */
    public PilotingRollData checkRunningWithDamage(int overallMoveType) {
        PilotingRollData roll = getBasePilotingRoll();

        if (overallMoveType == Entity.MOVE_RUN
            && !isProne()
            && (getDestroyedCriticals(CriticalSlot.TYPE_SYSTEM,
                                             Mech.SYSTEM_GYRO,
                                             Mech.LOC_CT) > 0
                || hasHipCrit())
            ) {
            // append the reason modifier
            roll.append(new PilotingRollData(getId(), 0, "running with damaged hip actuator or gyro"));
        } else {
            roll.addModifier(TargetRoll.CHECK_FALSE,"Check false");
        }

        return roll;
    }

    /**
     * Checks if the entity is landing (from a jump) with damage that would
     *  force a PSR.  If so, returns the target roll for the piloting
     *  skill check.
     */
    public PilotingRollData checkLandingWithDamage() {
        PilotingRollData roll = getBasePilotingRoll();

        if (getDestroyedCriticals(CriticalSlot.TYPE_SYSTEM, Mech.SYSTEM_GYRO,
                                  Mech.LOC_CT) > 0
            || hasLegActuatorCrit()) {
            // append the reason modifier
            roll.append(new PilotingRollData(getId(), 0, "landing with damaged leg actuator or gyro"));
        } else {
            roll.addModifier(TargetRoll.CHECK_FALSE,"Check false");
        }

        return roll;
    }

    /**
     * Checks if the entity might skid on pavement.  If so, returns
     *  the target roll for the piloting skill check.
     */
    public PilotingRollData checkSkid(int moveType, Hex prevHex,
                                      int overallMoveType, MoveStep prevStep,
                                      int prevFacing, int curFacing,
                                      Coords lastPos, Coords curPos,
                                      boolean isInfantry, int distance) {
        PilotingRollData roll = getBasePilotingRoll();

        // TODO: add check for elevation of pavement, road,
        //       or bridge matches entity elevation.
        if (moveType != Entity.MOVE_JUMP
            && prevHex != null
            /* Bug 754610: Revert fix for bug 702735.
               && ( prevHex.contains(Terrain.PAVEMENT) ||
               prevHex.contains(Terrain.ROAD) ||
               prevHex.contains(Terrain.BRIDGE) )
            */
            && prevStep.isPavementStep()
            && overallMoveType == Entity.MOVE_RUN
            && prevFacing != curFacing
            && !lastPos.equals(curPos)
            && !isInfantry) {
            // append the reason modifier
            if ( this instanceof Mech ) {
                roll.append(new PilotingRollData(getId(),
                                      getMovementBeforeSkidPSRModifier(distance),
                                      "running & turning on pavement"));
            } else {
                roll.append(new PilotingRollData(getId(),
                                      getMovementBeforeSkidPSRModifier(distance),
                                      "reckless driving on pavement"));
            }
        } else {
            roll.addModifier(TargetRoll.CHECK_FALSE,"Check false");
        }

        return roll;
    }

    /**
     * Checks if the entity is moving into rubble.  If so, returns
     *  the target roll for the piloting skill check.
     */
    public PilotingRollData checkRubbleMove(MoveStep step, Hex curHex,
                                            Coords lastPos, Coords curPos) {
        PilotingRollData roll = getBasePilotingRoll();

        if (!lastPos.equals(curPos)
            && step.getMovementType() != Entity.MOVE_JUMP
            && curHex.levelOf(Terrain.RUBBLE) > 0
            && this instanceof Mech) {
            // append the reason modifier
            roll.append(new PilotingRollData(getId(), 0, "entering Rubble"));
        } else {
            roll.addModifier(TargetRoll.CHECK_FALSE,"Check false");
        }

        return roll;
    }

    /**
     * Checks if the entity is moving into depth 1+ water.  If so,
     *  returns the target roll for the piloting skill check.
     */
    public PilotingRollData checkWaterMove(MoveStep step, Hex curHex,
                                           Coords lastPos, Coords curPos,
                                           boolean isPavementStep) {
        if (curHex.levelOf(Terrain.WATER) > 0
            && !lastPos.equals(curPos)
            && step.getMovementType() != Entity.MOVE_JUMP
            && getMovementType() != Entity.MovementType.HOVER
            && !isPavementStep) {
            return checkWaterMove(curHex.levelOf(Terrain.WATER));
        } else {
            return checkWaterMove(0);
        }
    }

    /**
     * Checks if the entity is moving into depth 1+ water.  If so,
     *  returns the target roll for the piloting skill check.
     */
    public PilotingRollData checkWaterMove(int waterLevel) {
        PilotingRollData roll = getBasePilotingRoll();

        int mod;
        if (waterLevel == 1) {
            mod = -1;
        } else if (waterLevel == 2) {
            mod = 0;
        } else {
            mod = 1;
        }

        if (waterLevel > 0) {
            // append the reason modifier
            roll.append(new PilotingRollData(getId(), mod, "entering Depth "
                                             + waterLevel + " Water"));
        } else {
            roll.addModifier(TargetRoll.CHECK_FALSE,"Check false");
        }

        return roll;
    }

    /**
     * Checks if the entity is being swarmed.  If so, returns the
     *  target roll for the piloting skill check to dislodge them.
     */
    public PilotingRollData checkDislodgeSwarmers() {

        // If we're not being swarmed, return CHECK_FALSE
        if (Entity.NONE == getSwarmAttackerId()) {
            return new PilotingRollData(getId(), TargetRoll.CHECK_FALSE,"Check false");
        }

        // append the reason modifier
        PilotingRollData roll = getBasePilotingRoll();
        roll.append(new PilotingRollData(getId(), 0, "attempting to dislodge swarmers by dropping prone"));

        return roll;
    }

    /**
     * Checks to see if an entity is moving through buildings.
     *  Note: this method returns true/false, unlike the other
     *  checkStuff() methods above.
     */
    public boolean checkMovementInBuilding(Coords lastPos, Coords curPos,
                                           MoveStep step, Hex curHex,
                                           Hex prevHex) {

        if ( !lastPos.equals(curPos) &&
             step.getMovementType() != Entity.MOVE_JUMP &&
             ( curHex.contains(Terrain.BUILDING) ||
               (prevHex != null && prevHex.contains(Terrain.BUILDING)) ) &&
             !(this instanceof Infantry) ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculates and returns the roll for an entity moving in buildings.
     */
    public PilotingRollData rollMovementInBuilding(Building bldg,
                                                  int distance, String why) {
        PilotingRollData roll = getBasePilotingRoll();

        int mod = 0;
        String desc;

        if (why == "") {
            desc = "moving through ";
        } else {
            desc = why + " ";
        }

        switch ( bldg.getType() ) {
        case Building.LIGHT:
            desc = "Light";
            break;
        case Building.MEDIUM:
            mod = 1;
            desc = "Medium";
            break;
        case Building.HEAVY:
            mod = 2;
            desc = "Heavy";
            break;
        case Building.HARDENED:
            mod = 5;
            desc = "Hardened";
            break;
        }

        // append the reason modifier
        roll.append(new PilotingRollData(getId(), mod, "moving through "
                                         + desc + " " + bldg.getName()));

        // Modify the roll by the distance moved so far.
        if (distance >= 3 && distance <= 4) {
            roll.addModifier(1, "moved 3-4 hexes");
        } else if (distance >= 5 && distance <= 6) {
            roll.addModifier(2, "moved 5-6 hexes");
        } else if (distance >= 7 && distance <= 9) {
            roll.addModifier(3, "moved 7-9 hexes");
        } else if (distance >= 10) {
            roll.addModifier(4, "moved 10+ hexes");
        }

        return roll;
    }

    /**
     * Calculate the piloting skill roll modifier, based upon the number
     * of hexes moved this phase.  Used for skidding.
     */
    public int getMovementBeforeSkidPSRModifier( int distance ) {
        int mod = -1;

        if ( distance > 10 ) // 11+ hexes
            mod = 4;
        else if ( distance > 7 ) // 8-10 hexes
            mod = 2;
        else if ( distance > 4 ) // 5-7 hexes
            mod = 1;
        else if ( distance > 2 ) // 3-4 hexes
            mod = 0;
        else // 0-2 hexes
            mod = -1;

        if ( getCrew().getOptions().booleanOption("maneuvering_ace") )
          mod--;

        return mod;
    }

    /**
     * The maximum elevation change the entity can cross
     */
    public abstract int getMaxElevationChange();

    /**
     * Get a list of the class names of the <code>Transporter</code>s
     * for the given <code>Entity</code>.
     * <p/>
     * This method should <strong>only</strong> be used when serializing
     * the <code>Entity</code>.
     *
     * @param   entity - the <code>Entity</code> being serialized.
     * @return  a <code>String</code> listing the <code>Transporter</code>
     *          class names of the <code>Entity</code>.  This value will
     *          be empty ("") if the entity has no <code>Transporter</code>s.
     */
    public static String encodeTransporters( Entity entity ) {
        StringBuffer buffer = new StringBuffer();
        Enumeration iter = entity.transports.elements();
        boolean isFirst = true;
        while ( iter.hasMoreElements() ) {

            // Every entry after the first gets a leading comma.
            if ( isFirst ) {
                isFirst = false;
            } else {
                buffer.append( ',' );
            }

            // Add the next Transporter's class name.
            Transporter transporter = (Transporter) iter.nextElement();
            buffer.append( transporter.getClass().getName() );

            // If this is a TroopSpace transporter, get it's capacity.
            if ( transporter instanceof TroopSpace ) {
                buffer.append( "=" )
                    .append( ((TroopSpace)transporter).totalSpace );
            }
        }
        return buffer.toString();
    }

    /**
     * Set the <code>Transporter</code>s for the given <code>Entity</code>.
     * <p/>
     * This method should <strong>only</strong> be used when deserializing
     * the <code>Entity</code>.
     *
     * @param   entity - the <code>Entity</code> being deserialized.
     * @param   transporters - the <code>String</code> listing the class
     *          names of the <code>Transporter</code>s to be set for the
     *          <code>Entity</code>, separated by commas.  This value can
     *          be <code>null</code> or empty ("").
     * @throws  <code>IllegalStateException</code> if any error occurs.
     */
    public static void decodeTransporters( Entity entity,
                                           String transporters )
        throws IllegalStateException {

        // Split the string on the commas, and add transporters to the Entity.
        Enumeration iter = StringUtil.splitString( transporters,
                                                   "," ).elements();
        while ( iter.hasMoreElements() ) {
            try {
                String name = (String) iter.nextElement();
                Class transporter = Class.forName( name );
                Object object = null;
                if ( TroopSpace.class.getName().equals( name ) ) {
                    // Get the tonnage of space.
                    int tonnage = Integer.parseInt
                        ( name.substring(name.lastIndexOf("=")) );
                    object = new TroopSpace( tonnage );
                } else {
                    object = transporter.newInstance();
                }
                entity.addTransporter( (Transporter) object );
            }
            catch ( Exception err ) {
                err.printStackTrace();
                throw new IllegalStateException( err.getMessage() );
            }

        } // Handle the next transporter
            
    }

    /**
     * Add a transportation component to this Entity. Please note, this
     * method should only be called during this entity's construction.
     *
     * @param component - One of this new entity's <code>Transporter</code>s.
     */
    /* package */ void addTransporter( Transporter component ) {
        transports.addElement( component );
    }

    /**
     * Determines if this object can accept the given unit.  The unit may
     * not be of the appropriate type or there may be no room for the unit.
     *
     * @param   unit - the <code>Entity</code> to be loaded.
     * @return  <code>true</code> if the unit can be loaded,
     *          <code>false</code> otherwise.
     */
    public boolean canLoad( Entity unit ) {
        // Walk through this entity's transport components;
        // if one of them can load the unit, we can.
        Enumeration iter = this.transports.elements();
        while ( iter.hasMoreElements() ) {
            Transporter next = (Transporter)iter.nextElement();
            if ( next.canLoad( unit ) ) {
                return true;
            }
        }

        // If we got here, none of our transports can carry the unit.
        return false;
    }

    /**
     * Load the given unit.
     *
     * @param   unit - the <code>Entity</code> to be loaded.
     * @exception - If the unit can't be loaded, an
     *          <code>IllegalArgumentException</code> exception will be thrown.
     */
    public void load( Entity unit ) throws IllegalArgumentException {
        // Walk through this entity's transport components;
        // find the one that can load the unit.
        // Stop looking after the first match.
        Enumeration iter = this.transports.elements();
        while ( iter.hasMoreElements() ) {
            Transporter next = (Transporter)iter.nextElement();
            if ( next.canLoad( unit ) ) {
                next.load( unit );
                return;
            }
        }

        // If we got to this point, then we can't load the unit.
        throw new IllegalArgumentException( this.getShortName() +
                                            " can not load " +
                                            unit.getShortName() );
    }

    /**
     * Get a <code>List</code> of the units currently loaded into this payload.
     *
     * @return  A <code>List</code> of loaded <code>Entity</code> units.
     *          This list will never be <code>null</code>, but it may be empty.
     *          The returned <code>List</code> is independant from the under-
     *          lying data structure; modifying one does not affect the other.
     *
     */
    public Vector getLoadedUnits() {
        Vector result = new Vector();

        // Walk through this entity's transport components;
        // add all of their lists to ours.
        Enumeration iter = this.transports.elements();
        while ( iter.hasMoreElements() ) {
            Transporter next = (Transporter)iter.nextElement();
            for (Enumeration i = next.getLoadedUnits().elements(); i.hasMoreElements();) {
                result.addElement(i.nextElement());
            }
        }

        // Return the list.
        return result;
    }

    /**
     * Unload the given unit.
     *
     * @param   unit - the <code>Entity</code> to be unloaded.
     * @return  <code>true</code> if the unit was contained in this space,
     *          <code>false</code> otherwise.
     */
    public boolean unload( Entity unit ) {
        // Walk through this entity's transport components;
        // try to remove the unit from each in turn.
        // Stop after the first match.
        Enumeration iter = this.transports.elements();
        while ( iter.hasMoreElements() ) {
            Transporter next = (Transporter)iter.nextElement();
            if ( next.unload( unit ) ) {
                return true;
            }
        }

        // If we got here, none of our transports currently carry the unit.
        return false;
    }

    /**
     * Return a string that identifies the unused capacity of this transporter.
     *
     * @return A <code>String</code> meant for a human.
     */
    public String getUnusedString() {
        StringBuffer result = new StringBuffer();

        // Walk through this entity's transport components;
        // add all of their string to ours.
        Enumeration iter = this.transports.elements();
        while ( iter.hasMoreElements() ) {
            Transporter next = (Transporter)iter.nextElement();
            result.append( next.getUnusedString() );
            // Add a newline character between strings.
            if ( iter.hasMoreElements() ) {
                result.append( '\n' );
            }
        }

        // Return the String.
        return result.toString();
    }

    /**
     * Determine if transported units prevent a weapon in the given location
     * from firing.
     *
     * @param   loc - the <code>int</code> location attempting to fire.
     * @param   isRear - a <code>boolean</code> value stating if the given
     *          location is rear facing; if <code>false</code>, the location
     *          is front facing.
     * @return  <code>true</code> if a transported unit is in the way,
     *          <code>false</code> if the weapon can fire.
     */
    public boolean isWeaponBlockedAt( int loc, boolean isRear ) {
        // Walk through this entity's transport components;
        // check each for blockage in turn.
        // Stop after the first match.
        Enumeration iter = this.transports.elements();
        while ( iter.hasMoreElements() ) {
            Transporter next = (Transporter)iter.nextElement();
            if ( next.isWeaponBlockedAt( loc, isRear ) ) {
                return true;
            }
        }

        // If we got here, none of our transports
        // carry a blocking unit at that location.
        return false;
    }

    /**
     * If a unit is being transported on the outside of the transporter, it
     * can suffer damage when the transporter is hit by an attack.  Currently,
     * no more than one unit can be at any single location; that same unit
     * can be "spread" over multiple locations.
     *
     * @param   loc - the <code>int</code> location hit by attack.
     * @param   isRear - a <code>boolean</code> value stating if the given
     *          location is rear facing; if <code>false</code>, the location
     *          is front facing.
     * @return  The <code>Entity</code> being transported on the outside
     *          at that location.  This value will be <code>null</code>
     *          if no unit is transported on the outside at that location.
     */
    public Entity getExteriorUnitAt( int loc, boolean isRear ) {
        // Walk through this entity's transport components;
        // check each for an exterior unit in turn.
        // Stop after the first match.
        Enumeration iter = this.transports.elements();
        while ( iter.hasMoreElements() ) {
            Transporter next = (Transporter)iter.nextElement();
            Entity exterior = next.getExteriorUnitAt( loc, isRear );
            if ( null != exterior ) {
                return exterior;
            }
        }

        // If we got here, none of our transports
        // carry an exterior unit at that location.
        return null;
    }

    /**
     * Record the ID of the <code>Entity</code> that has loaded this unit.
     * A unit that is unloaded can neither move nor attack for the rest of
     * the turn.
     *
     * @param   transportId - the <code>int</code> ID of our transport.
     *          The ID is <b>not</b> validated.  This value should be
     *          <code>Entity.NONE</code> if this unit has been unloaded.
     */
    public void setTransportId( int transportId ) {
        this.conveyance = transportId;
        // If we were unloaded, set the appropriate flags.
        if ( transportId == Entity.NONE ) {
            this.unloadedThisTurn = true;
            this.done = true;
        }
    }

    /**
     * Get the ID <code>Entity</code> that has loaded this one.
     *
     * @return  the <code>int</code> ID of our transport.
     *          The ID may be invalid.  This value should be
     *          <code>Entity.NONE</code> if this unit has not been loaded.
     */
    public int getTransportId() {
        return this.conveyance;
    }

    /**
     * Determine if this unit has an active stealth system.
     * <p/>
     * Sub-classes are encouraged to override this method.
     *
     * @return  <code>true</code> if this unit has a stealth system that
     *          is currently active, <code>false</code> if there is no
     *          stealth system or if it is inactive.
     */
    public boolean isStealthActive() { return false; }

    /**
     * Determine the stealth modifier for firing at this unit from the
     * given range.  If the value supplied for <code>range</code> is not
     * one of the <code>Entity</code> class range constants, an
     * <code>IllegalArgumentException</code> will be thrown.
     * <p/>
     * Sub-classes are encouraged to override this method.
     *
     * @param   range - an <code>int</code> value that must match one
     *          of the <code>Compute</code> class range constants.
     * @return  a <code>TargetRoll</code> value that contains the stealth
     *          modifier for the given range.
     */
    public TargetRoll getStealthModifier( int range ) {
        TargetRoll result = null;

        // Stealth must be active.
        if ( !isStealthActive() ) {
            result = new TargetRoll( 0, "stealth not active"  );
        }

        // Get the range modifier.
        switch ( range ) {
        case Compute.RANGE_SHORT:
        case Compute.RANGE_MEDIUM:
        case Compute.RANGE_LONG:
            result = new TargetRoll( 0, "stealth not installed" );
            break;
        default:
            throw new IllegalArgumentException
                ( "Unknown range constant: " + range );
        }

        // Return the result.
        return result;
    }

    /**
     * Record the ID of the <code>Entity</code> that is the current target
     * of a swarm attack by this unit. A unit that stops swarming can neither
     * move nor attack for the rest of the turn.
     *
     * @param   id - the <code>int</code> ID of the swarm attack's target.
     *          The ID is <b>not</b> validated.  This value should be
     *          <code>Entity.NONE</code> if this unit has stopped swarming.
     */
    public void setSwarmTargetId( int id ) {
        this.swarmTargetId = id;
        // This entity can neither move nor attack for the rest of this turn.
        if ( id == Entity.NONE ) {
            this.unloadedThisTurn = true;
            this.done = true;
        }
    }

    /**
     * Get the ID of the <code>Entity</code> that is the current target
     * of a swarm attack by this unit.
     *
     * @param   id - the <code>int</code> ID of the swarm attack's target
     *          The ID may be invalid.  This value should be
     *          <code>Entity.NONE</code> if this unit is not swarming.
     */
    public int getSwarmTargetId() {
        return this.swarmTargetId;
    }

    /**
     * Record the ID of the <code>Entity</code> that is attacking this unit
     * with a swarm attack.
     *
     * @param   id - the <code>int</code> ID of the swarm attack's attacker.
     *          The ID is <b>not</b> validated.  This value should be
     *          <code>Entity.NONE</code> if the swarm attack has ended.
     */
    public void setSwarmAttackerId( int id ) {
        this.swarmAttackerId = id;
    }

    /**
     * Get the ID of the <code>Entity</code> that is attacking this unit with
     * a swarm attack.
     *
     * @param   id - the <code>int</code> ID of the swarm attack's attacker
     *          The ID may be invalid.  This value should be
     *          <code>Entity.NONE</code> if this unit is not being swarmed.
     */
    public int getSwarmAttackerId() {
        return this.swarmAttackerId;
    }

    /**
     * Scans through the ammo on the unit for any inferno rounds.
     *
     * @return  <code>true</code> if the unit is still loaded with Inferno
     *          rounds.  <code>false</code> if no rounds were ever loaded
     *          or if they have all been fired.
     */
    public boolean hasInfernoAmmo() {
        boolean found = false;

        // Walk through the unit's ammo, stop when we find a match.
        for (Enumeration j = getAmmo(); j.hasMoreElements() && !found;) {
            Mounted amounted = (Mounted)j.nextElement();
            AmmoType atype = (AmmoType)amounted.getType();
            if ( atype.getMunitionType() == AmmoType.M_INFERNO &&
                 amounted.getShotsLeft() > 0 ) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Record if the unit is just combat-lossed or if it has been utterly
     * destroyed.
     *
     * @param   canSalvage - a <code>boolean</code> that is <code>true</code>
     *          if the unit can be repaired (given time and parts); if this
     *          value is <code>false</code>, the unit is utterly destroyed.
     */
    public void setSalvage( boolean canSalvage ) {
        // Unsalvageable entities aren't in retreat or salvageable.
        if ( !canSalvage ) {
            this.setRemovalCondition( Entity.REMOVE_DEVASTATED );
        }
        this.salvageable = canSalvage;
    }

    /**
     * Determine if the unit is just combat-lossed or if it has been utterly
     * destroyed.
     *
     * @return  A <code>boolean</code> that is <code>true</code> if the unit
     *          has salvageable components; if this value is <code>false</code>
     *          the unit is utterly destroyed.
     * @see     #isRepairable()
     */
    public boolean isSalvage() {
        return this.salvageable;
    }

    /**
     * Determine if the unit can be repaired, or only harvested for spares.
     *
     * @return  A <code>boolean</code> that is <code>true</code> if the unit
     *          can be repaired (given enough time and parts); if this value
     *          is <code>false</code>, the unit is only a source of spares.
     * @see     #isSalvage()
     */
    public boolean isRepairable() {
        return isSalvage();
    }

    /** Getter for property removalCondition.
     * @return Value of property removalCondition.
     */
    public int getRemovalCondition() {
        return removalCondition;
    }

    /** Setter for property removalCondition.
     * @param removalCondition New value of property removalCondition.
     */
    public void setRemovalCondition(int removalCondition) {
        // Don't replace a removal condition with a lesser condition.
        if ( this.removalCondition < removalCondition ) {
            this.removalCondition = removalCondition;
        }
    }

    /**
     * @return whether this entity is clearing a minefield.
     */
    public boolean isClearingMinefield() {
        return clearingMinefield;
    }

    /**
     * @param clearingMinefield
     */
    public void setClearingMinefield(boolean clearingMinefield) {
        this.clearingMinefield = clearingMinefield;
    }

    /**
     * @return whether this entity is spotting this round.
     */
    public boolean isSpotting() {
        return spotting;
    }

    /**
     * @param spotting
     */
    public void setSpotting(boolean spotting) {
        this.spotting = spotting;
    }

    /**
     * Um, basically everything can spot for LRM indirect fire.
     * @return true, if the entity is active
     */
    public boolean canSpot() {
        return isActive();
    }

    public String toString() {
        return "Entity [" + getDisplayName() + ", " + getId() + "]";
    }

    /**
     * The round the unit will be deployed. We will deploy at the end of a
     * round. So if depoyRound is set to 5, we will deploy when round 5 is
     * over.  Any value of zero or less is automatically set to 1
     *
     * @param    deployRound         an int
     *
     */
    public void setDeployRound(int deployRound) {
        this.deployRound = deployRound;
    }

    /**
     * The round the unit will be deployed
     *
     * @return   an int
     *
     */
    public int getDeployRound() {
        return deployRound;
    }

    /**
     * Toggles if an entity has been deployed
     */
    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    /**
     * Checks to see if an entity has been deployed
     */
    public boolean isDeployed() {
        return deployed;
    }

    /**
     * Returns true if the entity should be deployed
     */
    public boolean shouldDeploy(int round) {
        return ( !deployed && (getDeployRound() <= round) );
    }

    public void setSelected(boolean selected) {
    	this.selected = selected;
    }

    public boolean isSelected() {
    	return selected;
    }
}