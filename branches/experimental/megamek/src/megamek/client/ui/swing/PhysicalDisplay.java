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

package megamek.client.ui.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;


import megamek.client.event.BoardViewEvent;
import megamek.client.ui.Messages;
import megamek.client.ui.SharedUtility;
import megamek.client.ui.swing.widget.IndexedRadioButton;
import megamek.client.ui.swing.widget.MegamekButton;
import megamek.common.BattleArmor;
import megamek.common.Building;
import megamek.common.BuildingTarget;
import megamek.common.Compute;
import megamek.common.Coords;
import megamek.common.Entity;
import megamek.common.GameTurn;
import megamek.common.IAimingModes;
import megamek.common.IGame;
import megamek.common.INarcPod;
import megamek.common.Infantry;
import megamek.common.Mech;
import megamek.common.MiscType;
import megamek.common.Mounted;
import megamek.common.QuadMech;
import megamek.common.TargetRoll;
import megamek.common.Targetable;
import megamek.common.ToHitData;
import megamek.common.actions.BAVibroClawAttackAction;
import megamek.common.actions.BreakGrappleAttackAction;
import megamek.common.actions.BrushOffAttackAction;
import megamek.common.actions.ClubAttackAction;
import megamek.common.actions.DodgeAction;
import megamek.common.actions.EntityAction;
import megamek.common.actions.GrappleAttackAction;
import megamek.common.actions.JumpJetAttackAction;
import megamek.common.actions.KickAttackAction;
import megamek.common.actions.LayExplosivesAttackAction;
import megamek.common.actions.ProtomechPhysicalAttackAction;
import megamek.common.actions.PunchAttackAction;
import megamek.common.actions.PushAttackAction;
import megamek.common.actions.SearchlightAttackAction;
import megamek.common.actions.ThrashAttackAction;
import megamek.common.actions.TripAttackAction;
import megamek.common.event.GamePhaseChangeEvent;
import megamek.common.event.GameTurnChangeEvent;

public class PhysicalDisplay extends StatusBarPhaseDisplay {

    /**
     *
     */
    private static final long serialVersionUID = -3274750006768636001L;
    
    /**
     * This enumeration lists all of the possible ActionCommands that can be
     * carried out during the physical phase.  Each command has a string for the
     * command plus a flag that determines what unit type it is appropriate for.
     * @author walczak
     *
     */
    public static enum Command {
    	PHYSICAL_NEXT("next"),
    	PHYSICAL_PUNCH("punch"),
    	PHYSICAL_KICK("kick"),    
    	PHYSICAL_CLUB("club"),
    	PHYSICAL_BRUSH_OFF("brushOff"),
    	PHYSICAL_THRASH("thrash"),
    	PHYSICAL_DODGE("dodge"),
    	PHYSICAL_PUSH("push"),
    	PHYSICAL_TRIP("trip"),
    	PHYSICAL_GRAPPLE("grapple"),
    	PHYSICAL_JUMPJET("jumpjet"),    	
    	PHYSICAL_PROTO("protoPhysical"),
    	PHYSICAL_SEARCHLIGHT("fireSearchlight"),
    	PHYSICAL_EXPLOSIVES("explosives"),
    	PHYSICAL_VIBRO("vibro"),
    	PHYSICAL_MORE("more");	    	
    
	    String cmd;
	    private Command(String c){
	    	cmd = c;
	    }
	    
	    public String getCmd(){
	    	return cmd;
	    }
	    
	    public String toString(){
	    	return cmd;
	    }
    }

    // buttons
    protected Hashtable<Command,MegamekButton> buttons;

    // let's keep track of what we're shooting and at what, too
    private int cen = Entity.NONE; // current entity number
    Targetable target; // target

    // stuff we want to do
    private Vector<EntityAction> attacks;

    private AimedShotHandler ash = new AimedShotHandler();

    /**
     * Creates and lays out a new movement phase display for the specified
     * clientgui.getClient().
     */
    public PhysicalDisplay(ClientGUI clientgui) {
    	super();
    	
        this.clientgui = clientgui;
        clientgui.getClient().game.addGameListener(this);

        clientgui.getBoardView().addBoardViewListener(this);
        setupStatusBar(Messages
                .getString("PhysicalDisplay.waitingForPhysicalAttackPhase")); //$NON-NLS-1$
               
        attacks = new Vector<EntityAction>();

        buttons = new Hashtable<Command, MegamekButton>(
				(int) (Command.values().length * 1.25 + 0.5));
		for (Command cmd : Command.values()) {
			String title = Messages.getString("PhysicalDisplay."
					+ cmd.getCmd());
			MegamekButton newButton = new MegamekButton(title);
			newButton.addActionListener(this);
			newButton.setActionCommand(cmd.getCmd());
			newButton.setEnabled(false);
			buttons.put(cmd, newButton);
		}  		
		numButtonGroups = 
        		(int)Math.ceil((buttons.size()+0.0) / buttonsPerGroup);

        butDone.setText("<html><b>"+Messages.getString(
        		"PhysicalDisplay.Done")+"</b></html>"); //$NON-NLS-1$
        butDone.setEnabled(false);
        
        setupButtonPanel();

        // layout screen
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(1, 1, 1, 1);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0.0;
        c.weighty = 0.0;
        addBag(panButtons, gridbag, c);

        c.weightx = 1.0;
        c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        addBag(panStatus, gridbag, c);
    }

    protected ArrayList<MegamekButton> getButtonList(){                
        ArrayList<MegamekButton> buttonList = new ArrayList<MegamekButton>(); 
        int i = 0;
        for (Command cmd : Command.values()){
        	if (cmd == Command.PHYSICAL_NEXT || cmd == Command.PHYSICAL_MORE){
        		continue;
        	}
        	if (i % buttonsPerGroup == 0){
        		buttonList.add(buttons.get(Command.PHYSICAL_NEXT));
        		i++;
        	}
        	
            buttonList.add(buttons.get(cmd));
            i++;
            
            if ((i+1) % buttonsPerGroup == 0){
        		buttonList.add(buttons.get(Command.PHYSICAL_MORE));
        		i++;
        	}
        }
        if (!buttonList.get(i-1).getActionCommand().
        		equals(Command.PHYSICAL_MORE.getCmd())){
	        while ((i+1) % buttonsPerGroup != 0){
	        	buttonList.add(null);
	        	i++;	        	
	        }
	        buttonList.add(buttons.get(Command.PHYSICAL_MORE));
        }
        return buttonList;
    }

    
    /**
     * Selects an entity, by number, for movement.
     */
    public void selectEntity(int en) {
        if (clientgui.getClient().game.getEntity(en) == null) {
            System.err
                    .println("PhysicalDisplay: tried to select non-existant entity: " + en); //$NON-NLS-1$
            return;
        }

        cen = en;
        clientgui.setSelectedEntityNum(en);

        Entity entity = ce();

        target(null);
        if (entity instanceof Mech) {
            int grapple = ((Mech) entity).getGrappled();
            if (grapple != Entity.NONE) {
                Entity t = clientgui.getClient().game.getEntity(grapple);
                if (t != null) {
                    target(t);
                }
            }
        }
        clientgui.getBoardView().highlight(ce().getPosition());
        clientgui.getBoardView().select(null);
        clientgui.getBoardView().cursor(null);

        clientgui.mechD.displayEntity(entity);
        clientgui.mechD.showPanel("movement"); //$NON-NLS-1$

        clientgui.bv.centerOnHex(entity.getPosition());

        // Update the menu bar.
        clientgui.getMenuBar().setEntity(ce());

        // does it have a club?
        String clubLabel = null;
        for (Mounted club : entity.getClubs()) {
            String thisLab;
            if (club.getName().endsWith("Club")) { //$NON-NLS-1$
                thisLab = Messages.getString("PhysicalDisplay.Club"); //$NON-NLS-1$
            } else {
                thisLab = club.getName();
            }
            if (clubLabel == null) {
                clubLabel = thisLab;
            } else {
                clubLabel = clubLabel + "/" + thisLab;
            }
        }
        if (clubLabel == null) {
            clubLabel = Messages.getString("PhysicalDisplay.Club"); //$NON-NLS-1$
        }
        buttons.get(Command.PHYSICAL_CLUB).setText(clubLabel);

        if ((entity instanceof Mech)
                && !entity.isProne()
                && entity.getCrew().getOptions()
                        .booleanOption("dodge_maneuver")) { //$NON-NLS-1$
            setDodgeEnabled(true);
        }
    }

    /**
     * Does turn start stuff
     */
    private void beginMyTurn() {
        clientgui.setDisplayVisible(true);
        GameTurn turn = clientgui.getClient().getMyTurn();
        // There's special processing for countering break grapple.
        if (turn instanceof GameTurn.CounterGrappleTurn) {
            disableButtons();
            selectEntity(((GameTurn.CounterGrappleTurn) turn).getEntityNum());
            grapple(true);
            ready();
        } else {
            target(null);
            selectEntity(clientgui.getClient().getFirstEntityNum());
            setNextEnabled(true);
            butDone.setEnabled(true);
            buttons.get(Command.PHYSICAL_MORE).setEnabled(true);
        }
        clientgui.getBoardView().select(null);
    }

    /**
     * Does end turn stuff.
     */
    private void endMyTurn() {
        // end my turn, then.
        Entity next = clientgui.getClient().game.getNextEntity(clientgui
                .getClient().game.getTurnIndex());
        if ((IGame.Phase.PHASE_PHYSICAL == clientgui.getClient().game
                .getPhase())
                && (null != next)
                && (null != ce())
                && (next.getOwnerId() != ce().getOwnerId())) {
            clientgui.setDisplayVisible(false);
        }
        cen = Entity.NONE;
        target(null);
        clientgui.getBoardView().select(null);
        clientgui.getBoardView().highlight(null);
        clientgui.getBoardView().cursor(null);
        clientgui.bv.clearMovementData();
        disableButtons();
    }

    /**
     * Disables all buttons in the interface
     */
    private void disableButtons() {
        setKickEnabled(false);
        setPunchEnabled(false);
        setPushEnabled(false);
        setTripEnabled(false);
        setGrappleEnabled(false);
        setJumpJetEnabled(false);
        setClubEnabled(false);
        setBrushOffEnabled(false);
        setThrashEnabled(false);
        setDodgeEnabled(false);
        setProtoEnabled(false);
        setVibroEnabled(false);
        setExplosivesEnabled(false);
        butDone.setEnabled(false);
        setNextEnabled(false);
    }

    /**
     * Called when the current entity is done with physical attacks.
     */
    @Override
    public void ready() {
        if (attacks.isEmpty()
                && GUIPreferences.getInstance().getNagForNoAction()) {
            // comfirm this action
            ConfirmDialog response = clientgui
                    .doYesNoBotherDialog(
                            Messages
                                    .getString("PhysicalDisplay.DontPhysicalAttackDialog.title") //$NON-NLS-1$
                            ,
                            Messages
                                    .getString("PhysicalDisplay.DontPhysicalAttackDialog.message")); //$NON-NLS-1$
            if (!response.getShowAgain()) {
                GUIPreferences.getInstance().setNagForNoAction(false);
            }
            if (!response.getAnswer()) {
                return;
            }
        }

        disableButtons();
        clientgui.getClient().sendAttackData(cen, attacks);
        attacks.removeAllElements();
        // close aimed shot display, if any
        ash.closeDialog();
        endMyTurn();
    }

    /**
     * Clears all current actions
     */
    @Override
    public void clear() {
        if (attacks.size() > 0) {
            attacks.removeAllElements();
        }
        clientgui.mechD.wPan.displayMech(ce());
        updateTarget();

        Entity entity = clientgui.getClient().game.getEntity(cen);
        entity.dodging = true;
    }

    /**
     * Punch the target!
     */
    void punch() {
        final ToHitData leftArm = PunchAttackAction
                .toHit(clientgui.getClient().game, cen, target,
                        PunchAttackAction.LEFT);
        final ToHitData rightArm = PunchAttackAction.toHit(clientgui
                .getClient().game, cen, target, PunchAttackAction.RIGHT);
        String title = Messages
                .getString(
                        "PhysicalDisplay.PunchDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.PunchDialog.message", new Object[] {//$NON-NLS-1$
                        rightArm.getValueAsString(),
                        new Double(Compute.oddsAbove(rightArm.getValue())),
                        rightArm.getDesc(),
                        new Integer(PunchAttackAction.getDamageFor(ce(),
                                PunchAttackAction.RIGHT,
                                (target instanceof Infantry)
                                        && !(target instanceof BattleArmor))),
                        rightArm.getTableDesc(),
                        leftArm.getValueAsString(),
                        new Double(Compute.oddsAbove(leftArm.getValue())),
                        leftArm.getDesc(),
                        new Integer(PunchAttackAction.getDamageFor(ce(),
                                PunchAttackAction.LEFT,
                                (target instanceof Infantry)
                                        && !(target instanceof BattleArmor))),
                        leftArm.getTableDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            // check for retractable blade that can be extended in each arm
            boolean leftBladeExtend = false;
            boolean rightBladeExtend = false;
            if ((ce() instanceof Mech)
                    && (target instanceof Entity)
                    && clientgui.getClient().game.getOptions().booleanOption(
                            "tacops_retractable_blades")
                    && (leftArm.getValue() != TargetRoll.IMPOSSIBLE)
                    && ((Mech) ce()).hasRetractedBlade(Mech.LOC_LARM)) {
                leftBladeExtend = clientgui.doYesNoDialog(Messages
                        .getString("PhysicalDisplay.ExtendBladeDialog.title"),
                        Messages.getString(
                                "PhysicalDisplay.ExtendBladeDialog.message",
                                new Object[] { ce().getLocationName(
                                        Mech.LOC_LARM) }));
            }
            if ((ce() instanceof Mech)
                    && (target instanceof Entity)
                    && (rightArm.getValue() != TargetRoll.IMPOSSIBLE)
                    && clientgui.getClient().game.getOptions().booleanOption(
                            "tacops_retractable_blades")
                    && ((Mech) ce()).hasRetractedBlade(Mech.LOC_RARM)) {
                rightBladeExtend = clientgui.doYesNoDialog(Messages
                        .getString("PhysicalDisplay.ExtendBladeDialog.title"),
                        Messages.getString(
                                "PhysicalDisplay.ExtendBladeDialog.message",
                                new Object[] { ce().getLocationName(
                                        Mech.LOC_RARM) }));
            }
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            if ((leftArm.getValue() != TargetRoll.IMPOSSIBLE)
                    && (rightArm.getValue() != TargetRoll.IMPOSSIBLE)) {
                attacks.addElement(new PunchAttackAction(cen, target
                        .getTargetType(), target.getTargetId(),
                        PunchAttackAction.BOTH, leftBladeExtend,
                        rightBladeExtend));
            } else if (leftArm.getValue() < rightArm.getValue()) {
                attacks.addElement(new PunchAttackAction(cen, target
                        .getTargetType(), target.getTargetId(),
                        PunchAttackAction.LEFT, leftBladeExtend,
                        rightBladeExtend));
            } else {
                attacks.addElement(new PunchAttackAction(cen, target
                        .getTargetType(), target.getTargetId(),
                        PunchAttackAction.RIGHT, leftBladeExtend,
                        rightBladeExtend));
            }
            ready();
        }
    }

    private void doSearchlight() {
        // validate
        if ((ce() == null) || (target == null)) {
            throw new IllegalArgumentException(
                    "current searchlight parameters are invalid"); //$NON-NLS-1$
        }

        if (!SearchlightAttackAction.isPossible(clientgui.getClient().game,
                cen, target, null)) {
            return;
        }

        // create and queue a searchlight action
        SearchlightAttackAction saa = new SearchlightAttackAction(cen, target
                .getTargetType(), target.getTargetId());
        attacks.addElement(saa);

        // and add it into the game, temporarily
        clientgui.getClient().game.addAction(saa);
        clientgui.bv.addAttack(saa);
        clientgui.minimap.drawMap();

        // and prevent duplicates
        setSearchlightEnabled(false);

        // refresh weapon panel, as bth will have changed
        updateTarget();
    }

    /**
     * Kick the target!
     */
    void kick() {
        ToHitData leftLeg = KickAttackAction.toHit(clientgui.getClient().game,
                cen, target, KickAttackAction.LEFT);
        ToHitData rightLeg = KickAttackAction.toHit(clientgui.getClient().game,
                cen, target, KickAttackAction.RIGHT);
        ToHitData rightRearLeg = null;
        ToHitData leftRearLeg = null;

        ToHitData attackLeg;
        int attackSide = KickAttackAction.LEFT;
        int value = leftLeg.getValue();
        attackLeg = leftLeg;

        if (value > rightLeg.getValue()) {
            value = rightLeg.getValue();
            attackSide = KickAttackAction.RIGHT;
            attackLeg = rightLeg;
        }
        if (clientgui.getClient().game.getEntity(cen) instanceof QuadMech) {
            rightRearLeg = KickAttackAction.toHit(clientgui.getClient().game,
                    cen, target, KickAttackAction.RIGHTMULE);
            leftRearLeg = KickAttackAction.toHit(clientgui.getClient().game,
                    cen, target, KickAttackAction.LEFTMULE);
            if (value > rightRearLeg.getValue()) {
                value = rightRearLeg.getValue();
                attackSide = KickAttackAction.RIGHTMULE;
                attackLeg = rightRearLeg;
            }
            if (value > leftRearLeg.getValue()) {
                value = leftRearLeg.getValue();
                attackSide = KickAttackAction.LEFTMULE;
                attackLeg = leftRearLeg;
            }
        }
        String title = Messages
                .getString(
                        "PhysicalDisplay.KickDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.KickDialog.message", new Object[] {//$NON-NLS-1$
                        attackLeg.getValueAsString(),
                        new Double(Compute.oddsAbove(attackLeg.getValue())),
                        attackLeg.getDesc(),
                        KickAttackAction.getDamageFor(ce(), attackSide,
                                (target instanceof Infantry)
                                        && !(target instanceof BattleArmor))
                                + attackLeg.getTableDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new KickAttackAction(cen,
                    target.getTargetType(), target.getTargetId(), attackSide));
            ready();
        }
    }

    /**
     * Push that target!
     */
    void push() {
        ToHitData toHit = PushAttackAction.toHit(clientgui.getClient().game,
                cen, target);
        String title = Messages
                .getString(
                        "PhysicalDisplay.PushDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.PushDialog.message", new Object[] {//$NON-NLS-1$
                toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new PushAttackAction(cen,
                    target.getTargetType(), target.getTargetId(), target
                            .getPosition()));
            ready();
        }
    }

    /**
     * Trip that target!
     */
    void trip() {
        ToHitData toHit = TripAttackAction.toHit(clientgui.getClient().game,
                cen, target);
        String title = Messages
                .getString(
                        "PhysicalDisplay.TripDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.TripDialog.message", new Object[] { //$NON-NLS-1$
                toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new TripAttackAction(cen,
                    target.getTargetType(), target.getTargetId()));
            ready();
        }
    }

    /**
     * Grapple that target!
     */
    void doGrapple() {
        if (((Mech) ce()).getGrappled() == Entity.NONE) {
            grapple(false);
        } else {
            breakGrapple();
        }
    }

    private void grapple(boolean counter) {
        ToHitData toHit = GrappleAttackAction.toHit(clientgui.getClient().game,
                cen, target);
        String title = Messages
                .getString(
                        "PhysicalDisplay.GrappleDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.GrappleDialog.message", new Object[] { //$NON-NLS-1$
                toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc() });
        if (counter) {
            message = Messages
                    .getString(
                            "PhysicalDisplay.CounterGrappleDialog.message", new Object[] { //$NON-NLS-1$
                                    target.getDisplayName(),
                                    toHit.getValueAsString(),
                                    new Double(Compute.oddsAbove(toHit
                                            .getValue())), toHit.getDesc() });
        }
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new GrappleAttackAction(cen, target
                    .getTargetType(), target.getTargetId()));
            ready();
        }
    }

    private void breakGrapple() {
        ToHitData toHit = BreakGrappleAttackAction.toHit(
                clientgui.getClient().game, cen, target);
        String title = Messages
                .getString(
                        "PhysicalDisplay.BreakGrappleDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.BreakGrappleDialog.message", new Object[] { //$NON-NLS-1$
                toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new BreakGrappleAttackAction(cen, target
                    .getTargetType(), target.getTargetId()));
            ready();
        }
    }

    /**
     * slice 'em up with your vibroclaws
     */
    public void vibroclawatt() {
        BAVibroClawAttackAction act = new BAVibroClawAttackAction(cen, target
                .getTargetType(), target.getTargetId());
        ToHitData toHit = act.toHit(clientgui.getClient().game);

        String title = Messages
                .getString(
                        "PhysicalDisplay.BAVibroClawDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.BAVibroClawDialog.message", new Object[] {//$NON-NLS-1$
                toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc(),
                        ce().getVibroClaws() + toHit.getTableDesc() });

        // Give the user to cancel the attack.
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            attacks.addElement(act);
            ready();
        }
    }

    void jumpjetatt() {
        ToHitData toHit;
        int leg;
        int damage;
        if (ce().isProne()) {
            toHit = JumpJetAttackAction.toHit(clientgui.getClient().game, cen,
                    target, JumpJetAttackAction.BOTH);
            leg = JumpJetAttackAction.BOTH;
            damage = JumpJetAttackAction.getDamageFor(ce(),
                    JumpJetAttackAction.BOTH);
        } else {
            ToHitData left = JumpJetAttackAction.toHit(
                    clientgui.getClient().game, cen, target,
                    JumpJetAttackAction.LEFT);
            ToHitData right = JumpJetAttackAction.toHit(
                    clientgui.getClient().game, cen, target,
                    JumpJetAttackAction.RIGHT);
            int d_left = JumpJetAttackAction.getDamageFor(ce(),
                    JumpJetAttackAction.LEFT);
            int d_right = JumpJetAttackAction.getDamageFor(ce(),
                    JumpJetAttackAction.RIGHT);
            if ((d_left * Compute.oddsAbove(left.getValue())) > (d_right
                    * Compute.oddsAbove(right.getValue()))) {
                toHit = left;
                leg = JumpJetAttackAction.LEFT;
                damage = d_left;
            } else {
                toHit = right;
                leg = JumpJetAttackAction.RIGHT;
                damage = d_right;
            }
        }

        String title = Messages
                .getString(
                        "PhysicalDisplay.JumpJetDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.JumpJetDialog.message", new Object[] { //$NON-NLS-1$
                toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc(), damage });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new JumpJetAttackAction(cen, target
                    .getTargetType(), target.getTargetId(), leg));
            ready();
        }
    }

    private Mounted chooseClub() {
        java.util.List<Mounted> clubs = ce().getClubs();
        if (clubs.size() == 1) {
            return clubs.get(0);
        } else if (clubs.size() > 1) {
            String[] names = new String[clubs.size()];
            for (int loop = 0; loop < names.length; loop++) {
                Mounted club = clubs.get(loop);
                names[loop] = Messages
                        .getString(
                                "PhysicalDisplay.ChooseClubDialog.line",
                                new Object[] {
                                        club.getName(),
                                        ClubAttackAction.toHit(
                                                clientgui.getClient().game,
                                                cen, target, club,
                                                ash.getAimTable())
                                                .getValueAsString(),
                                        ClubAttackAction
                                                .getDamageFor(
                                                        ce(),
                                                        club,
                                                        (target instanceof Infantry)
                                                                && !(target instanceof BattleArmor)) });
            }

            String input = (String) JOptionPane
                    .showInputDialog(
                            clientgui,
                            Messages
                                    .getString("PhysicalDisplay.ChooseClubDialog.message"), //$NON-NLS-1$
                            Messages
                                    .getString("PhysicalDisplay.ChooseClubDialog.title"), //$NON-NLS-1$
                            JOptionPane.QUESTION_MESSAGE, null, names, null);
            if (input != null) {
                for (int i = 0; i < clubs.size(); i++) {
                    if (input.equals(names[i])) {
                        return clubs.get(i);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Club that target!
     */
    void club() {
        Mounted club = chooseClub();
        if (null == club) {
            return;
        }
        ToHitData toHit = ClubAttackAction.toHit(clientgui.getClient().game,
                cen, target, club, ash.getAimTable());
        String title = Messages
                .getString(
                        "PhysicalDisplay.ClubDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.ClubDialog.message", new Object[] {//$NON-NLS-1$
                        toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc(),
                        ClubAttackAction.getDamageFor(ce(), club,
                                (target instanceof Infantry)
                                        && !(target instanceof BattleArmor))
                                + toHit.getTableDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new ClubAttackAction(cen,
                    target.getTargetType(), target.getTargetId(), club, ash
                            .getAimTable()));
            ready();
        }
    }

    /**
     * Club that target!
     */
    void club(Mounted club) {
        if (null == club) {
            return;
        }
        ToHitData toHit = ClubAttackAction.toHit(clientgui.getClient().game,
                cen, target, club, ash.getAimTable());
        String title = Messages
                .getString(
                        "PhysicalDisplay.ClubDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.ClubDialog.message", new Object[] { //$NON-NLS-1$
                        toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc(),
                        ClubAttackAction.getDamageFor(ce(), club,
                                (target instanceof Infantry)
                                        && !(target instanceof BattleArmor))
                                + toHit.getTableDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new ClubAttackAction(cen,
                    target.getTargetType(), target.getTargetId(), club, ash
                            .getAimTable()));
            ready();
        }
    }

    /**
     * Make a protomech physical attack on the target.
     */
    private void proto() {
        ToHitData proto = ProtomechPhysicalAttackAction.toHit(clientgui
                .getClient().game, cen, target);
        String title = Messages
                .getString(
                        "PhysicalDisplay.ProtoMechAttackDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.ProtoMechAttackDialog.message", new Object[] {//$NON-NLS-1$
                        proto.getValueAsString(),
                        new Double(Compute.oddsAbove(proto.getValue())),
                        proto.getDesc(),
                        ProtomechPhysicalAttackAction.getDamageFor(ce(), target)
                                + proto.getTableDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            // declare searchlight, if possible
            if (GUIPreferences.getInstance().getAutoDeclareSearchlight()) {
                doSearchlight();
            }

            attacks.addElement(new ProtomechPhysicalAttackAction(cen, target
                    .getTargetType(), target.getTargetId()));
            ready();
        }
    }

    private void explosives() {
        ToHitData explo = LayExplosivesAttackAction.toHit(
                clientgui.getClient().game, cen, target);
        String title = Messages
                .getString(
                        "PhysicalDisplay.LayExplosivesAttackDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages
                .getString(
                        "PhysicalDisplay.LayExplosivesAttackDialog.message", new Object[] {//$NON-NLS-1$
                                explo.getValueAsString(),
                                new Double(Compute.oddsAbove(explo.getValue())),
                                explo.getDesc() });
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            attacks.addElement(new LayExplosivesAttackAction(cen, target
                    .getTargetType(), target.getTargetId()));
            ready();
        }
    }

    /**
     * Sweep off the target with the arms that the player selects.
     */
    private void brush() {
        ToHitData toHitLeft = BrushOffAttackAction.toHit(
                clientgui.getClient().game, cen, target,
                BrushOffAttackAction.LEFT);
        ToHitData toHitRight = BrushOffAttackAction.toHit(
                clientgui.getClient().game, cen, target,
                BrushOffAttackAction.RIGHT);
        boolean canHitLeft = (TargetRoll.IMPOSSIBLE != toHitLeft.getValue());
        boolean canHitRight = (TargetRoll.IMPOSSIBLE != toHitRight.getValue());
        int damageLeft = 0;
        int damageRight = 0;
        String title = null;
        StringBuffer warn = null;
        String left = null;
        String right = null;
        String both = null;
        String[] choices = null;

        // If the entity can't brush off, display an error message and abort.
        if (!canHitLeft && !canHitRight) {
            clientgui.doAlertDialog(Messages
                    .getString("PhysicalDisplay.AlertDialog.title"), //$NON-NLS-1$
                    Messages.getString("PhysicalDisplay.AlertDialog.message")); //$NON-NLS-1$
            return;
        }

        // If we can hit with both arms, the player will have to make a choice.
        // Otherwise, the player is just confirming the arm in the attack.
        if (canHitLeft && canHitRight) {
            both = Messages.getString("PhysicalDisplay.bothArms"); //$NON-NLS-1$
            warn = new StringBuffer(Messages
                    .getString("PhysicalDisplay.whichArm")); //$NON-NLS-1$
            title = Messages.getString("PhysicalDisplay.chooseBrushOff"); //$NON-NLS-1$
        } else {
            warn = new StringBuffer(Messages
                    .getString("PhysicalDisplay.confirmArm")); //$NON-NLS-1$
            title = Messages.getString("PhysicalDisplay.confirmBrushOff"); //$NON-NLS-1$
        }

        // Build the rest of the warning string.
        // Use correct text when the target is an iNarc pod.
        if (Targetable.TYPE_INARC_POD == target.getTargetType()) {
            warn.append(Messages.getString(
                    "PhysicalDisplay.brushOff1", new Object[] { target })); //$NON-NLS-1$
        } else {
            warn.append(Messages.getString("PhysicalDisplay.brushOff2")); //$NON-NLS-1$
        }

        // If we can hit with the left arm, get
        // the damage and construct the string.
        if (canHitLeft) {
            damageLeft = BrushOffAttackAction.getDamageFor(ce(),
                    BrushOffAttackAction.LEFT);
            left = Messages
                    .getString("PhysicalDisplay.LAHit", new Object[] {//$NON-NLS-1$
                                    toHitLeft.getValueAsString(),
                                    new Double(Compute.oddsAbove(toHitLeft
                                            .getValue())),
                                    new Integer(damageLeft) });
        }

        // If we can hit with the right arm, get
        // the damage and construct the string.
        if (canHitRight) {
            damageRight = BrushOffAttackAction.getDamageFor(ce(),
                    BrushOffAttackAction.RIGHT);
            right = Messages
                    .getString("PhysicalDisplay.RAHit", new Object[] {//$NON-NLS-1$
                                    toHitRight.getValueAsString(),
                                    new Double(Compute.oddsAbove(toHitRight
                                            .getValue())),
                                    new Integer(damageRight) });
        }

        // Allow the player to cancel or choose which arm(s) to use.
        if (canHitLeft && canHitRight) {
            choices = new String[3];
            choices[0] = left;
            choices[1] = right;
            choices[2] = both;

            String input = (String) JOptionPane.showInputDialog(clientgui, warn
                    .toString(), title, JOptionPane.WARNING_MESSAGE, null,
                    choices, null);
            int index = -1;
            if (input != null) {
                for (int i = 0; i < choices.length; i++) {
                    if (input.equals(choices[i])) {
                        index = i;
                        break;
                    }
                }
            }
            if (index != -1) {
                disableButtons();
                switch (index) {
                case 0:
                    attacks.addElement(new BrushOffAttackAction(cen, target
                            .getTargetType(), target.getTargetId(),
                            BrushOffAttackAction.LEFT));
                    break;
                case 1:
                    attacks.addElement(new BrushOffAttackAction(cen, target
                            .getTargetType(), target.getTargetId(),
                            BrushOffAttackAction.RIGHT));
                    break;
                case 2:
                    attacks.addElement(new BrushOffAttackAction(cen, target
                            .getTargetType(), target.getTargetId(),
                            BrushOffAttackAction.BOTH));
                    break;
                }
                ready();

            } // End not-cancel

        } // End choose-attack(s)

        // If only the left arm is available, confirm that choice.
        else if (canHitLeft) {
            choices = new String[1];
            choices[0] = left;
            String input = (String) JOptionPane.showInputDialog(clientgui, warn
                    .toString(), title, JOptionPane.WARNING_MESSAGE, null,
                    choices, null);
            if (input != null) {
                disableButtons();
                attacks.addElement(new BrushOffAttackAction(cen, target
                        .getTargetType(), target.getTargetId(),
                        BrushOffAttackAction.LEFT));
                ready();

            } // End not-cancel

        } // End confirm-left

        // If only the right arm is available, confirm that choice.
        else if (canHitRight) {
            choices = new String[1];
            choices[0] = right;
            String input = (String) JOptionPane.showInputDialog(clientgui, warn
                    .toString(), title, JOptionPane.WARNING_MESSAGE, null,
                    choices, null);
            if (input != null) {
                disableButtons();
                attacks.addElement(new BrushOffAttackAction(cen, target
                        .getTargetType(), target.getTargetId(),
                        BrushOffAttackAction.RIGHT));
                ready();

            } // End not-cancel

        } // End confirm-right

    } // End private void brush()

    /**
     * Thrash at the target, unless the player cancels the action.
     */
    void thrash() {
        ThrashAttackAction act = new ThrashAttackAction(cen, target
                .getTargetType(), target.getTargetId());
        ToHitData toHit = act.toHit(clientgui.getClient().game);

        String title = Messages
                .getString(
                        "PhysicalDisplay.TrashDialog.title", new Object[] { target.getDisplayName() }); //$NON-NLS-1$
        String message = Messages.getString(
                "PhysicalDisplay.TrashDialog.message", new Object[] {//$NON-NLS-1$
                        toHit.getValueAsString(),
                        new Double(Compute.oddsAbove(toHit.getValue())),
                        toHit.getDesc(),
                        ThrashAttackAction.getDamageFor(ce())
                                + toHit.getTableDesc() });

        // Give the user to cancel the attack.
        if (clientgui.doYesNoDialog(title, message)) {
            disableButtons();
            attacks.addElement(act);
            ready();
        }
    }

    /**
     * Dodge like that guy in that movie that I won't name for copywrite
     * reasons!
     */
    void dodge() {
        if (clientgui
                .doYesNoDialog(
                        Messages.getString("PhysicalDisplay.DodgeDialog.title"), Messages.getString("PhysicalDisplay.DodgeDialog.message"))) { //$NON-NLS-1$ //$NON-NLS-2$
            disableButtons();

            Entity entity = clientgui.getClient().game.getEntity(cen);
            entity.dodging = true;

            DodgeAction act = new DodgeAction(cen);
            attacks.addElement(act);

            ready();
        }
    }

    /**
     * Targets something
     */
    void target(Targetable t) {
        target = t;
        updateTarget();
        ash.showDialog();
    }

    /**
     * Targets an entity
     */
    void updateTarget() {
        // dis/enable physical attach buttons
        if ((cen != Entity.NONE) && (target != null)) {
            if (target.getTargetType() != Targetable.TYPE_INARC_POD) {
                // punch?
                final ToHitData leftArm = PunchAttackAction.toHit(clientgui
                        .getClient().game, cen, target, PunchAttackAction.LEFT);
                final ToHitData rightArm = PunchAttackAction
                        .toHit(clientgui.getClient().game, cen, target,
                                PunchAttackAction.RIGHT);
                boolean canPunch = (leftArm.getValue() != TargetRoll.IMPOSSIBLE)
                        || (rightArm.getValue() != TargetRoll.IMPOSSIBLE);
                setPunchEnabled(canPunch);

                // kick?
                ToHitData leftLeg = KickAttackAction.toHit(clientgui
                        .getClient().game, cen, target, KickAttackAction.LEFT);
                ToHitData rightLeg = KickAttackAction.toHit(clientgui
                        .getClient().game, cen, target, KickAttackAction.RIGHT);
                boolean canKick = (leftLeg.getValue() != TargetRoll.IMPOSSIBLE)
                        || (rightLeg.getValue() != TargetRoll.IMPOSSIBLE);
                ToHitData rightRearLeg = KickAttackAction.toHit(clientgui
                        .getClient().game, cen, target,
                        KickAttackAction.RIGHTMULE);
                ToHitData leftRearLeg = KickAttackAction.toHit(clientgui
                        .getClient().game, cen, target,
                        KickAttackAction.LEFTMULE);
                canKick |= (leftRearLeg.getValue() != TargetRoll.IMPOSSIBLE)
                        || (rightRearLeg.getValue() != TargetRoll.IMPOSSIBLE);

                setKickEnabled(canKick);

                // how about push?
                ToHitData push = PushAttackAction.toHit(
                        clientgui.getClient().game, cen, target);
                setPushEnabled(push.getValue() != TargetRoll.IMPOSSIBLE);

                // how about trip?
                ToHitData trip = TripAttackAction.toHit(
                        clientgui.getClient().game, cen, target);
                setTripEnabled(trip.getValue() != TargetRoll.IMPOSSIBLE);

                // how about grapple?
                ToHitData grap = GrappleAttackAction.toHit(clientgui
                        .getClient().game, cen, target);
                ToHitData bgrap = BreakGrappleAttackAction.toHit(clientgui
                        .getClient().game, cen, target);
                setGrappleEnabled((grap.getValue() != TargetRoll.IMPOSSIBLE)
                        || (bgrap.getValue() != TargetRoll.IMPOSSIBLE));

                // how about JJ?
                ToHitData jjl = JumpJetAttackAction.toHit(
                        clientgui.getClient().game, cen, target,
                        JumpJetAttackAction.LEFT);
                ToHitData jjr = JumpJetAttackAction.toHit(
                        clientgui.getClient().game, cen, target,
                        JumpJetAttackAction.RIGHT);
                ToHitData jjb = JumpJetAttackAction.toHit(
                        clientgui.getClient().game, cen, target,
                        JumpJetAttackAction.BOTH);
                setJumpJetEnabled(!((jjl.getValue() == TargetRoll.IMPOSSIBLE)
                        && (jjr.getValue() == TargetRoll.IMPOSSIBLE) && (jjb
                        .getValue() == TargetRoll.IMPOSSIBLE)));

                // clubbing?
                boolean canClub = false;
                boolean canAim = false;
                for (Mounted club : ce().getClubs()) {
                    if (club != null) {
                        ToHitData clubToHit = ClubAttackAction.toHit(clientgui
                                .getClient().game, cen, target, club, ash
                                .getAimTable());
                        canClub |= (clubToHit.getValue() != TargetRoll.IMPOSSIBLE);
                        // assuming S7 vibroswords count as swords and maces
                        // count as hatchets
                        if (club.getType().hasSubType(MiscType.S_SWORD)
                                || club.getType()
                                        .hasSubType(MiscType.S_HATCHET)
                                || club.getType().hasSubType(
                                        MiscType.S_VIBRO_SMALL)
                                || club.getType().hasSubType(
                                        MiscType.S_VIBRO_MEDIUM)
                                || club.getType().hasSubType(
                                        MiscType.S_VIBRO_LARGE)
                                || club.getType().hasSubType(MiscType.S_MACE)
                                || club.getType().hasSubType(
                                        MiscType.S_MACE_THB)
                                || club.getType().hasSubType(MiscType.S_LANCE)
                                || club.getType().hasSubType(
                                        MiscType.S_CHAIN_WHIP)
                                || club.getType().hasSubType(
                                        MiscType.S_RETRACTABLE_BLADE)) {
                            canAim = true;
                        }
                    }
                }
                setClubEnabled(canClub);
                ash.setCanAim(canAim);

                // Thrash at infantry?
                ToHitData thrash = new ThrashAttackAction(cen, target)
                        .toHit(clientgui.getClient().game);
                setThrashEnabled(thrash.getValue() != TargetRoll.IMPOSSIBLE);

                // make a Protomech physical attack?
                ToHitData proto = ProtomechPhysicalAttackAction.toHit(clientgui
                        .getClient().game, cen, target);
                setProtoEnabled(proto.getValue() != TargetRoll.IMPOSSIBLE);

                ToHitData explo = LayExplosivesAttackAction.toHit(clientgui
                        .getClient().game, cen, target);
                setExplosivesEnabled(explo.getValue() != TargetRoll.IMPOSSIBLE);

                // vibro attack?
                ToHitData vibro = BAVibroClawAttackAction.toHit(clientgui
                        .getClient().game, cen, target);
                setVibroEnabled(vibro.getValue() != TargetRoll.IMPOSSIBLE);
            }
            // Brush off swarming infantry or iNarcPods?
            ToHitData brushRight = BrushOffAttackAction.toHit(clientgui
                    .getClient().game, cen, target, BrushOffAttackAction.RIGHT);
            ToHitData brushLeft = BrushOffAttackAction.toHit(clientgui
                    .getClient().game, cen, target, BrushOffAttackAction.LEFT);
            boolean canBrush = ((brushRight.getValue() != TargetRoll.IMPOSSIBLE) || (brushLeft
                    .getValue() != TargetRoll.IMPOSSIBLE));
            setBrushOffEnabled(canBrush);
        } else {
            setPunchEnabled(false);
            setPushEnabled(false);
            setTripEnabled(false);
            setGrappleEnabled(false);
            setJumpJetEnabled(false);
            setKickEnabled(false);
            setClubEnabled(false);
            setBrushOffEnabled(false);
            setThrashEnabled(false);
            setProtoEnabled(false);
            setVibroEnabled(false);
        }
        setSearchlightEnabled((ce() != null) && (target != null)
                && ce().isUsingSpotlight());
    }

    /**
     * Returns the current entity.
     */
    Entity ce() {
        return clientgui.getClient().game.getEntity(cen);
    }

    //
    // BoardListener
    //
    @Override
    public void hexMoused(BoardViewEvent b) {

        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }

        // control pressed means a line of sight check.
        if ((b.getModifiers() & InputEvent.CTRL_MASK) != 0) {
            return;
        }
        if (clientgui.getClient().isMyTurn()
                && ((b.getModifiers() & InputEvent.BUTTON1_MASK) != 0)) {
            if (b.getType() == BoardViewEvent.BOARD_HEX_DRAGGED) {
                if (!b.getCoords().equals(
                        clientgui.getBoardView().getLastCursor())) {
                    clientgui.getBoardView().cursor(b.getCoords());
                }
            } else if (b.getType() == BoardViewEvent.BOARD_HEX_CLICKED) {
                clientgui.getBoardView().select(b.getCoords());
            }
        }
    }

    @Override
    public void hexSelected(BoardViewEvent b) {
        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }

        if (clientgui.getClient().isMyTurn() && (b.getCoords() != null)
                && (ce() != null)) {
            final Targetable targ = chooseTarget(b.getCoords());
            if (targ != null) {
                target(targ);
            } else {
                target(null);
            }
        }
    }

    /**
     * Have the player select a target from the entities at the given coords.
     *
     * @param pos
     *            - the <code>Coords</code> containing targets.
     */
    private Targetable chooseTarget(Coords pos) {

        // Assume that we have *no* choice.
        Targetable choice = null;

        // Get the available choices.
        Enumeration<Entity> choices = clientgui.getClient().game
                .getEntities(pos);

        // Convert the choices into a List of targets.
        List<Targetable> targets = new ArrayList<Targetable>();
        while (choices.hasMoreElements()) {
            choice = choices.nextElement();
            if (!ce().equals(choice)) {
                targets.add(choice);
            }
        }

        // Is there a building in the hex?
        Building bldg = clientgui.getClient().game.getBoard()
                .getBuildingAt(pos);
        if (bldg != null) {
            targets.add(new BuildingTarget(pos, clientgui.getClient().game
                    .getBoard(), false));
        }

        // Is the attacker targeting its own hex?
        if (ce().getPosition().equals(pos)) {
            // Add any iNarc pods attached to the entity.
            Iterator<INarcPod> pods = ce().getINarcPodsAttached();
            while (pods.hasNext()) {
                choice = pods.next();
                targets.add(choice);
            }
        }

        // Do we have a single choice?
        if (targets.size() == 1) {

            // Return that choice.
            choice = targets.get(0);

        }

        // If we have multiple choices, display a selection dialog.
        else if (targets.size() > 1) {
            String input = (String) JOptionPane
                    .showInputDialog(
                            clientgui,
                            Messages
                                    .getString(
                                            "PhysicalDisplay.ChooseTargetDialog.message", new Object[] { pos.getBoardNum() }), //$NON-NLS-1$
                            Messages
                                    .getString("PhysicalDisplay.ChooseTargetDialog.title"), //$NON-NLS-1$
                            JOptionPane.QUESTION_MESSAGE, null, SharedUtility
                                    .getDisplayArray(targets), null);
            choice = SharedUtility.getTargetPicked(targets, input);
        } // End have-choices

        // Return the chosen unit.
        return choice;

    } // End private Targetable chooseTarget( Coords )

    //
    // GameListener
    //
    @Override
    public void gameTurnChange(GameTurnChangeEvent e) {

        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }

        if (clientgui.getClient().game.getPhase() == IGame.Phase.PHASE_PHYSICAL) {

            if (clientgui.getClient().isMyTurn()) {
                if (cen == Entity.NONE) {
                    beginMyTurn();
                }
                setStatusBarText(Messages
                        .getString("PhysicalDisplay.its_your_turn")); //$NON-NLS-1$
            } else {
                endMyTurn();
                setStatusBarText(Messages
                        .getString(
                                "PhysicalDisplay.its_others_turn", new Object[] { e.getPlayer().getName() })); //$NON-NLS-1$
            }
        } else {
            System.err
                    .println("PhysicalDisplay: got turnchange event when it's not the physical attacks phase"); //$NON-NLS-1$
        }
    }

    @Override
    public void gamePhaseChange(GamePhaseChangeEvent e) {

        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }

        if (clientgui.getClient().isMyTurn()
                && (clientgui.getClient().game.getPhase() != IGame.Phase.PHASE_PHYSICAL)) {
            endMyTurn();
        }
        // if we're ending the firing phase, unregister stuff.
        if (clientgui.getClient().game.getPhase() == IGame.Phase.PHASE_PHYSICAL) {
            setStatusBarText(Messages
                    .getString("PhysicalDisplay.waitingForPhysicalAttackPhase")); //$NON-NLS-1$
        }
    }

    //
    // ActionListener
    //
    public void actionPerformed(ActionEvent ev) {

        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }

        if (statusBarActionPerformed(ev, clientgui.getClient())) {
            return;
        }

        if (!clientgui.getClient().isMyTurn()) {
            // odd...
            return;
        }
        if (ev.getActionCommand().equals(Command.PHYSICAL_PUNCH.getCmd())) {
            punch();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_KICK.getCmd())) {
            kick();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_PUSH.getCmd())) {
            push();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_TRIP.getCmd())) {
            trip();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_GRAPPLE.getCmd())) {
            doGrapple();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_JUMPJET.getCmd())) {
            jumpjetatt();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_CLUB.getCmd())) {
            club();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_BRUSH_OFF.getCmd())) {
            brush();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_THRASH.getCmd())) {
            thrash();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_DODGE.getCmd())) {
            dodge();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_PROTO.getCmd())) {
            proto();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_EXPLOSIVES.getCmd())) {
            explosives();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_VIBRO.getCmd())) {
            vibroclawatt();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_NEXT.getCmd())) {
            selectEntity(clientgui.getClient().getNextEntityNum(cen));
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_SEARCHLIGHT.getCmd())) {
            doSearchlight();
        } else if (ev.getActionCommand().equals(Command.PHYSICAL_MORE.getCmd())) {
        	currentButtonGroup++;
        	currentButtonGroup %= numButtonGroups;
            setupButtonPanel();
        }
    }

    //
    // BoardViewListener
    //
    @Override
    public void finishedMovingUnits(BoardViewEvent b) {
        // no action
    }

    @Override
    public void unitSelected(BoardViewEvent b) {

        // Are we ignoring events?
        if (isIgnoringEvents()) {
            return;
        }

        Entity e = clientgui.getClient().game.getEntity(b.getEntityId());
        if (clientgui.getClient().isMyTurn()) {
            if (clientgui.getClient().getMyTurn().isValidEntity(e,
                    clientgui.getClient().game)) {
                selectEntity(e.getId());
            }
        } else {
            clientgui.setDisplayVisible(true);
            clientgui.mechD.displayEntity(e);
            if (e.isDeployed()) {
                clientgui.bv.centerOnHex(e.getPosition());
            }
        }
    }

    public void setThrashEnabled(boolean enabled) {
        buttons.get(Command.PHYSICAL_THRASH).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalThrashEnabled(enabled);
    }

    public void setPunchEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_PUNCH).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalPunchEnabled(enabled);
    }

    public void setKickEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_KICK).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalKickEnabled(enabled);
    }

    public void setPushEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_PUSH).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalPushEnabled(enabled);
    }

    public void setTripEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_TRIP).setEnabled(enabled);
    }

    public void setGrappleEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_GRAPPLE).setEnabled(enabled);
    }

    public void setJumpJetEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_JUMPJET).setEnabled(enabled);
    }

    public void setClubEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_CLUB).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalClubEnabled(enabled);
    }

    public void setBrushOffEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_BRUSH_OFF).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalBrushOffEnabled(enabled);
    }

    public void setDodgeEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_DODGE).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalDodgeEnabled(enabled);
    }

    public void setProtoEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_PROTO).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalProtoEnabled(enabled);
    }

    public void setVibroEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_VIBRO).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalVibroEnabled(enabled);
    }

    public void setExplosivesEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_EXPLOSIVES).setEnabled(enabled);
        // clientgui.getMenuBar().setExplosivesEnabled(enabled);
    }

    public void setNextEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_NEXT).setEnabled(enabled);
        clientgui.getMenuBar().setPhysicalNextEnabled(enabled);
    }

    private void setSearchlightEnabled(boolean enabled) {
    	buttons.get(Command.PHYSICAL_SEARCHLIGHT).setEnabled(enabled);
        clientgui.getMenuBar().setFireSearchlightEnabled(enabled);
    }

    /**
     * Stop just ignoring events and actually stop listening to them.
     */
    public void removeAllListeners() {
        clientgui.getClient().game.removeGameListener(this);
        clientgui.getBoardView().removeBoardViewListener(this);
    }

    private class AimedShotHandler implements ActionListener, ItemListener {
        private int aimingAt = -1;

        private int aimingMode = IAimingModes.AIM_MODE_NONE;

        private AimedShotDialog asd;

        private boolean canAim;

        public AimedShotHandler() {
            // no action
        }

        public int getAimTable() {
            switch (aimingAt) {
            case 0:
                return ToHitData.HIT_PUNCH;
            case 1:
                return ToHitData.HIT_KICK;
            default:
                return ToHitData.HIT_NORMAL;
            }
        }

        public void setCanAim(boolean v) {
            canAim = v;
        }

        public void showDialog() {

            if ((ce() == null) || (target == null)) {
                return;
            }

            if (asd != null) {
                int oldAimingMode = aimingMode;
                closeDialog();
                aimingMode = oldAimingMode;
            }

            if (canAim) {

                final int attackerElevation = ce().getElevation()
                        + ce().getGame().getBoard().getHex(ce().getPosition())
                                .getElevation();
                final int targetElevation = target.getElevation()
                        + ce().getGame().getBoard()
                                .getHex(target.getPosition()).getElevation();

                if ((target instanceof Mech) && (ce() instanceof Mech)
                        && (attackerElevation == targetElevation)) {
                    String[] options = { "punch", "kick" };
                    boolean[] enabled = { true, true };

                    asd = new AimedShotDialog(
                            clientgui.frame,
                            Messages
                                    .getString("PhysicalDisplay.AimedShotDialog.title"), //$NON-NLS-1$
                            Messages
                                    .getString("PhysicalDisplay.AimedShotDialog.message"), //$NON-NLS-1$
                            options, enabled, aimingAt, this, this);

                    asd.setVisible(true);
                    updateTarget();
                }
            }
        }

        public void closeDialog() {
            if (asd != null) {
                aimingAt = Entity.LOC_NONE;
                aimingMode = IAimingModes.AIM_MODE_NONE;
                asd.setVisible(false);
                asd = null;
                updateTarget();
            }
        }

        // ActionListener, listens to the button in the dialog.
        public void actionPerformed(ActionEvent ev) {
            closeDialog();
        }

        // ItemListener, listens to the radiobuttons in the dialog.
        public void itemStateChanged(ItemEvent ev) {
            IndexedRadioButton icb = (IndexedRadioButton) ev.getSource();
            aimingAt = icb.getIndex();
            updateTarget();
        }

    }
}