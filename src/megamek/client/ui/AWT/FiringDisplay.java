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

package megamek.client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import megamek.common.*;
import megamek.common.actions.*;

public class FiringDisplay 
    extends AbstractPhaseDisplay
    implements BoardListener, GameListener, ActionListener,
    KeyListener, ComponentListener, MouseListener,
    ItemListener
{
    private static final int    NUM_BUTTON_LAYOUTS = 2;
    
    // parent game
    public Client client;
    
    // displays
    private Label              labStatus;
    
    // buttons
    private Container        panButtons;
    
    private Button            butFire;
    private Button            butTwist;
    private Button            butSkip;
    
    private Button            butAim;
    private Button            butFindClub;
    
    private Button            butSpace;
    private Button            butSpace1;
    private Button            butSpace2;
    
    private Button            butNext;
    private Button            butDone;
    private Button            butMore;
    
    private int               buttonLayout;

    // let's keep track of what we're shooting and at what, too
    private int                cen;        // current entity number
    private int                ten;        // target entity number
    private int       selectedWeapon;
  
    // shots we have so far.
    private Vector attacks;  
  
    // is the shift key held?
    private boolean            shiftheld;
    private boolean            twisting;

    
    /**
     * Creates and lays out a new movement phase display 
     * for the specified client.
     */
    public FiringDisplay(Client client) {
        this.client = client;
        client.addGameListener(this);
        
        client.game.board.addBoardListener(this);
        
        shiftheld = false;
    
        // fire
        attacks = new Vector();

        labStatus = new Label("Waiting to begin Weapon Attack phase...", Label.CENTER);
        
        butFire = new Button("Fire");
        butFire.addActionListener(this);
        butFire.setEnabled(false);
        
        butSkip = new Button("Skip");
        butSkip.addActionListener(this);
        butSkip.setEnabled(false);
        
        butTwist = new Button("Twist");
        butTwist.addActionListener(this);
        butTwist.setEnabled(false);
        

        butFindClub = new Button("Find Club");
        butFindClub.addActionListener(this);
        butFindClub.setEnabled(false);
        
        butAim = new Button("Aim");
        butAim.addActionListener(this);
        butAim.setEnabled(false);
        
        
        butSpace = new Button(".");
        butSpace.setEnabled(false);

        butSpace1 = new Button(".");
        butSpace1.setEnabled(false);

        butSpace2 = new Button(".");
        butSpace2.setEnabled(false);

        butDone = new Button("Done");
        butDone.addActionListener(this);
        butDone.setEnabled(false);
        
        butNext = new Button(" Next Unit ");
        butNext.addActionListener(this);
        butNext.setEnabled(false);
        
        butMore = new Button("More...");
        butMore.addActionListener(this);
        butMore.setEnabled(false);
        
        // layout button grid
        panButtons = new Panel();
        buttonLayout = 0;
        setupButtonPanel();
        
        // layout screen
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
        
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;    c.weighty = 1.0;
        c.insets = new Insets(1, 1, 1, 1);
        c.gridwidth = GridBagConstraints.REMAINDER;
        addBag(client.bv, gridbag, c);

        c.weightx = 1.0;    c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        addBag(labStatus, gridbag, c);

        c.gridwidth = 1;
        c.weightx = 1.0;    c.weighty = 0.0;
        addBag(client.cb.getComponent(), gridbag, c);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0.0;    c.weighty = 0.0;
        addBag(panButtons, gridbag, c);
        
        addKeyListener(this);
        
        // mech display.
        client.mechD.addMouseListener(this);
        client.mechD.wPan.weaponList.addItemListener(this);
        client.mechD.wPan.weaponList.addKeyListener(this);
        client.frame.addComponentListener(this);
    
    }
    
    private void addBag(Component comp, GridBagLayout gridbag, GridBagConstraints c) {
        gridbag.setConstraints(comp, c);
        add(comp);
        comp.addKeyListener(this);
    }
    
    private void setupButtonPanel() {
        panButtons.removeAll();
        panButtons.setLayout(new GridLayout(2, 4));
        
        switch (buttonLayout) {
        case 0 :
            panButtons.add(butFire);
            panButtons.add(butSkip);
            panButtons.add(butSpace);
            panButtons.add(butNext);
            panButtons.add(butTwist);
            panButtons.add(butSpace1);
            panButtons.add(butMore);
            panButtons.add(butDone);
            break;
        case 1 :
            panButtons.add(butAim);
            panButtons.add(butFindClub);
            panButtons.add(butSpace);
            panButtons.add(butNext);
            panButtons.add(butSpace1);
            panButtons.add(butSpace2);
            panButtons.add(butMore);
            panButtons.add(butDone);
            break;
        }
        
        validate();
    }
    /**
     * Selects an entity, by number, for movement.
     */
    public void selectEntity(int en) {
        if (client.game.getEntity(en) != null) {
            this.cen = en;
            target(Entity.NONE);
            client.game.board.highlight(ce().getPosition());
            client.game.board.select(null);
            client.game.board.cursor(null);

            refreshAll();

            client.bv.centerOnHex(ce().getPosition());
            
            butTwist.setEnabled(ce().canChangeSecondaryFacing());
            butFindClub.setEnabled(Compute.canMechFindClub(client.game, en));
        } else {
            System.err.println("FiringDisplay: tried to select non-existant entity: " + en);
            System.err.println("FiringDisplay: sending ready signal...");
            client.sendReady(true);
        }
    }
    
    /**
     * Does turn start stuff
     */
    private void beginMyTurn() {
        ten = Entity.NONE;
        butNext.setEnabled(true);
        butDone.setEnabled(true);
        butMore.setEnabled(true);
        client.mechW.setVisible(true);
        moveMechDisplay();
        client.game.board.select(null);
        client.game.board.highlight(null);
        selectEntity(client.game.getFirstEntityNum(client.getLocalPlayer()));
    }
    
    /**
     * Does end turn stuff.
     */
    private void endMyTurn() {
        // end my turn, then.
        cen = Entity.NONE;
        ten = Entity.NONE;
        target(Entity.NONE);
        client.game.board.select(null);
        client.game.board.highlight(null);
        client.game.board.cursor(null);
        client.mechW.setVisible(false);
        client.bv.clearMovementData();
        disableButtons();
    }
    
    /**
     * Disables all buttons in the interface
     */
    private void disableButtons() {
        butFire.setEnabled(false);
        butSkip.setEnabled(false);
        butTwist.setEnabled(false);
        butAim.setEnabled(false);
        butFindClub.setEnabled(false);
        butMore.setEnabled(false);
        butNext.setEnabled(false);
        butDone.setEnabled(false);
    }
    
    /**
     * Called when the current entity is done firing.
     */
    private void ready() {
        if (cen == Entity.NONE) {
            return;
        }
        disableButtons();
        client.sendAttackData(cen, attacks);
        attacks.removeAllElements();
        client.sendEntityReady(cen);
        client.sendReady(true);
    }
    
    /**
     * Sends a data packet indicating the chosen weapon and 
     * target.
     */
    private void fire() {
        int wn = client.mechD.wPan.getSelectedWeaponNum();
        if (ce() == null || te() == null || ce().getEquipment(wn) == null 
        || !(ce().getEquipment(wn).getType() instanceof WeaponType)) {
            throw new IllegalArgumentException("current fire parameters are invalid");
        }
    
        attacks.addElement(new WeaponAttackAction(cen, ten, wn));
    
        ce().getEquipment(wn).setUsedThisRound(true);
        selectedWeapon = ce().getNextWeapon(wn);
        // check; if there are no ready weapons, you're done.
        if(selectedWeapon == -1) {
            ready();
            return;
        }
        client.mechD.wPan.displayMech(ce());
        client.mechD.wPan.selectWeapon(selectedWeapon);
        updateTarget();
        butNext.setEnabled(false);
    }
    
    /**
     * Skips to the next weapon
     */
    private void nextWeapon() {
        selectedWeapon = ce().getNextWeapon(client.mechD.wPan.getSelectedWeaponNum());
        // check; if there are no ready weapons, you're done.
        if(selectedWeapon == -1) {
            return;
        }
        client.mechD.wPan.displayMech(ce());
        client.mechD.wPan.selectWeapon(selectedWeapon);
        updateTarget();
    }
    
    /**
     * The entity spends the rest of its turn finding a club
     */
    private void findClub() {
        if (ce() == null) {
            return;
        }
        
        attacks.removeAllElements();
        attacks.addElement(new FindClubAction(cen));
        
        ready();
    }
  
    /**
     * Removes all current fire
     */
    private void clearAttacks() {
        if (attacks.size() > 0) {
            for (Enumeration i = attacks.elements(); i.hasMoreElements();) {
                Object o = i.nextElement();
                if (o instanceof WeaponAttackAction) {
                    WeaponAttackAction waa = (WeaponAttackAction)o;
                    ce().getEquipment(waa.getWeaponId()).setUsedThisRound(false);
                }
            }
            attacks.removeAllElements();
        }
        ce().setSecondaryFacing(ce().getFacing());
    }
    
    /**
     * Refeshes all displays.
     */
    private void refreshAll() {
        client.bv.redrawEntity(ce());
        client.mechD.wPan.displayMech(ce());
        client.mechD.showPanel("weapons");
        selectedWeapon = ce().getFirstWeapon();
        client.mechD.wPan.selectWeapon(selectedWeapon);
        updateTarget();
    }
    
    /**
     * Targets an entity
     */
    private void target(int en) {
        this.ten = en;
        updateTarget();
    }
    
    /**
     * Targets an entity
     */
    private void updateTarget() {
        butFire.setEnabled(false);
        // update target panel
        final int weaponId = client.mechD.wPan.getSelectedWeaponNum();
        if (ten != Entity.NONE && weaponId != -1) {
            ToHitData toHit = Compute.toHitWeapon(client.game, cen, ten, weaponId, attacks);
            client.mechD.wPan.wTargetR.setText(te().getDisplayName());
            client.mechD.wPan.wRangeR.setText("" + ce().getPosition().distance(te().getPosition()));
            if (toHit.getValue() <= 12) {
                client.mechD.wPan.wToHitR.setText(toHit.getValue() + " (" + Compute.oddsAbove(toHit.getValue()) + "%)");
                butFire.setEnabled(true);
            } else {
                client.mechD.wPan.wToHitR.setText("Impossible");
                butFire.setEnabled(false);
            }
            client.mechD.wPan.toHitText.setText(toHit.getDesc());
            butSkip.setEnabled(true);
        } else {
            client.mechD.wPan.wTargetR.setText("---");
            client.mechD.wPan.wRangeR.setText("---");
            client.mechD.wPan.wToHitR.setText("---");
            client.mechD.wPan.toHitText.setText("");
        }
    }
  
    /**
     * Torso twist in the proper direction.
     */
    private void torsoTwist(Coords target) {
        int direction = ce().clipSecondaryFacing(ce().getPosition().direction(target));
        if (direction != ce().getSecondaryFacing()) {
            clearAttacks();
            attacks.addElement(new TorsoTwistAction(cen, direction));
            ce().setSecondaryFacing(direction);
            refreshAll();
        }
    }
    
    /**
     * Returns the current entity.
     */
    private Entity ce() {
        return client.game.getEntity(cen);
    }
    
    /**
     * Returns the target entity.
     */
    private Entity te() {
        return client.game.getEntity(ten);
    }
    
    /**
     * Moves the mech display window to the proper position.
     */
    private void moveMechDisplay() {
        if(client.bv.isShowing()) {
            client.mechW.setLocation(client.bv.getLocationOnScreen().x 
                                     + client.bv.getSize().width 
                                     - client.mechD.getSize().width - 20, 
                                     client.bv.getLocationOnScreen().y + 20);
        }
    }
    
    //
    // BoardListener
    //
    public void boardHexMoused(BoardEvent b) {
        // ignore buttons other than 1
        if (!client.isMyTurn() || (b.getModifiers() & MouseEvent.BUTTON1_MASK) == 0) {
            return;
        }
        // check for shifty goodness
        if (shiftheld != ((b.getModifiers() & MouseEvent.SHIFT_MASK) != 0)) {
            shiftheld = (b.getModifiers() & MouseEvent.SHIFT_MASK) != 0;
        }
        
        if (b.getType() == b.BOARD_HEX_DRAGGED) {
            if (shiftheld || twisting) {
                torsoTwist(b.getCoords());
            }
            client.game.board.cursor(b.getCoords());
        } else if (b.getType() == b.BOARD_HEX_CLICKED) {
            twisting = false;
            client.game.board.select(b.getCoords());
        }
    }
    public void boardHexSelected(BoardEvent b) {
        if (client.isMyTurn() && b.getCoords() != null && ce() != null && !b.getCoords().equals(ce().getPosition())) {
            if (shiftheld) {
                torsoTwist(b.getCoords());
            } else if (client.game.getEntity(b.getCoords()) != null && client.game.getEntity(b.getCoords()).isTargetable()) {
                target(client.game.getEntity(b.getCoords()).getId());
            }
        }
    }
    
    //
    // GameListener
    //
    public void gameTurnChange(GameEvent ev) {
        if(client.game.phase == Game.PHASE_FIRING) {
            endMyTurn();
            
            if(client.isMyTurn()) {
                beginMyTurn();
                labStatus.setText("It's your turn to fire.");
            } else {
                labStatus.setText("It's " + ev.getPlayer().getName() + "'s turn to fire.");
            }
        }
    }
    public void gamePhaseChange(GameEvent ev) {
        if(client.isMyTurn() && client.game.phase != Game.PHASE_FIRING) {
            endMyTurn();
        }
        // if we're ending the firing phase, unregister stuff.
        if(client.game.phase !=  Game.PHASE_FIRING) {
            client.bv.clearAllAttacks();
            
            client.removeGameListener(this);
            client.game.board.removeBoardListener(this);
            client.mechD.removeMouseListener(this);
            client.mechD.wPan.weaponList.removeItemListener(this);
            client.mechD.wPan.weaponList.removeKeyListener(this);
            client.frame.removeComponentListener(this);
            client.bv.removeKeyListener(this);
            client.cb.getComponent().removeKeyListener(this);
        }
    }

    //
    // ActionListener
    //
    public void actionPerformed(ActionEvent ev) {
        if (!client.isMyTurn()) {
            return;
        }
        
        if (ev.getSource() == butDone) {
            ready();
        } else if (ev.getSource() == butFire) {
            fire();
        } else if (ev.getSource() == butSkip) {
            nextWeapon();
        } else if (ev.getSource() == butTwist) {
            twisting = true;
        } else if (ev.getSource() == butNext) {
            clearAttacks();
            refreshAll();
            selectEntity(client.game.getNextEntityNum(client.getLocalPlayer(), cen));
        } else if (ev.getSource() == butMore) {
            buttonLayout++;
            buttonLayout %= NUM_BUTTON_LAYOUTS;
            setupButtonPanel();
        } else if (ev.getSource() == butFindClub) {
            findClub();
        }
    }
    

    //
    // KeyListener
    //
    public void keyPressed(KeyEvent ev) {
        if (ev.getKeyCode() == ev.VK_ESCAPE) {
            clearAttacks();
            client.game.board.select(null);
            client.game.board.cursor(null);
            refreshAll();
        }
        if (ev.getKeyCode() == ev.VK_ENTER && ev.isControlDown()) {
            if (client.isMyTurn()) {
                //
            }
        }
        if (ev.getKeyCode() == KeyEvent.VK_SHIFT && !shiftheld) {
            shiftheld = true;
            if (client.isMyTurn() && client.game.board.lastCursor != null) {
                torsoTwist(client.game.board.lastCursor);
            }
        }
    }
    public void keyReleased(KeyEvent ev) {
        if (ev.getKeyCode() == KeyEvent.VK_SHIFT && shiftheld) {
            shiftheld = false;
        }
    }
    public void keyTyped(KeyEvent ev) {
        ;
    }
    
    //
    // ComponentListener
    //
    public void componentHidden(ComponentEvent ev) {
        client.mechW.setVisible(false);
    }
    public void componentMoved(ComponentEvent ev) {
        moveMechDisplay();
    }
    public void componentResized(ComponentEvent ev) {
        moveMechDisplay();
    }
    public void componentShown(ComponentEvent ev) {
        client.mechW.setVisible(false);
        moveMechDisplay();
    }
    
    //
    // MouseListener
    //
    public void mouseEntered(MouseEvent ev) {
        ;
    }
    public void mouseExited(MouseEvent ev) {
        ;
    }
    public void mousePressed(MouseEvent ev) {
        ;
    }
    public void mouseReleased(MouseEvent ev) {
        ;
    }
    public void mouseClicked(MouseEvent ev) {
        ;
    }
    
    //
    // ItemListener
    //
    public void itemStateChanged(ItemEvent ev) {
        if(ev.getItemSelectable() == client.mechD.wPan.weaponList) {
            // update target data in weapon display
            updateTarget();
        }
    }
}
