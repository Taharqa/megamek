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
import java.util.Vector;
import java.util.Enumeration;
import java.io.*;

import megamek.common.*;

public class ChatLounge extends AbstractPhaseDisplay
	implements ActionListener, ItemListener, BoardListener, GameListener
{
    // parent Client
    private Client client;
    	
    // buttons & such
    private Label labColor, labTeam;			
    private Choice choColor, choTeam;
    private Panel panColor;
      
    private Label labBoardW, labBoardH, labSheetW, labSheetH;
    private TextField tfdBoardW, tfdBoardH, tfdSheetW, tfdSheetH;
    private Button butChangeBoard;
    private Panel panBoardSettings;
      
    private Button butLoad, butDelete;
    private List lisEntities;
    private int[] entityCorrespondance;
    private Panel panEntities;
    
    private Label labStarts;
    private Button[] butPos;
    private Label labMiddlePosition;
    private Panel panStartButtons;
    private List lisStarts;
    private Panel panStarts;
      
    private Label labBVs;
    private List lisBVs;
    private Panel panBVs;
      
    private Panel panMain;
    	
    private Label labStatus;
    private Button butReady;
	
	/**
	 * Creates a new chat lounge for the client.
	 */
	public ChatLounge(Client client) {
        super();
        this.client = client;
            
        client.addGameListener(this);
        client.game.board.addBoardListener(this);
        		
        ChatterBox cb = client.cb;
        		
        panColor = new Panel();
            
        labColor = new Label("Color:", Label.RIGHT);
        labTeam = new Label("Team:", Label.RIGHT);
        		
        choColor = new Choice();
        choColor.addItemListener(this);
        		
        refreshColors();
        		
        choTeam = new Choice();
        choTeam.addItem("Not Functional");
        choTeam.setEnabled(false);
            
        setupBoardSettings();
        refreshGameSettings();
            
        setupEntities();
        refreshEntities();
            
        setupBVs();
        refreshBVs();
        
        setupStarts();
        refreshStarts();
            
        panMain = new Panel();
        panMain.setLayout(new GridBagLayout());
            
        panMain.add(panBoardSettings);
        panMain.add(panStarts);
        panMain.add(panEntities);
        panMain.add(panBVs);
            
        labStatus = new Label("", Label.CENTER);
        		
        butReady = new Button("I'm Ready.");
        butReady.setActionCommand("ready");
        butReady.addActionListener(this);
        		
        // layout colors
        panColor.add(labColor);
        panColor.add(choColor);
        panColor.add(labTeam);
        panColor.add(choTeam);		
        		
        // layout main thing
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
        		
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;	c.weighty = 0.0;
        c.insets = new Insets(1, 1, 1, 1);
        c.gridwidth = GridBagConstraints.REMAINDER;
        addBag(panColor, gridbag, c);

        c.weightx = 1.0;	c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        addBag(panMain, gridbag, c);

        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        addBag(labStatus, gridbag, c);

        c.gridwidth = 1;
        c.weightx = 1.0;	c.weighty = 0.0;
        addBag(client.cb.getComponent(), gridbag, c);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0.0;	c.weighty = 0.0;
        addBag(butReady, gridbag, c);
	}
	
	private void addBag(Component comp, GridBagLayout gridbag, GridBagConstraints c) {
		gridbag.setConstraints(comp, c);
		add(comp);
	}
  
  
    /**
     * Sets up the board settings panel
     */
    private void setupBoardSettings() {
        labBoardW = new Label("Board Width (hexes):", Label.RIGHT);
        labBoardH = new Label("Board Height (hexes):", Label.RIGHT);
        labSheetW = new Label("Map Width (boards):", Label.RIGHT);
        labSheetH = new Label("Map Height (boards):", Label.RIGHT);
            
        tfdBoardW = new TextField();
        tfdBoardW.setEnabled(false);
        tfdBoardH = new TextField();
        tfdBoardH.setEnabled(false);
        tfdSheetW = new TextField();
        tfdSheetH = new TextField();
            
        butChangeBoard = new Button("Change");
        butChangeBoard.setActionCommand("change_board");
        butChangeBoard.addActionListener(this);
            
        panBoardSettings = new Panel();
            
        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panBoardSettings.setLayout(gridbag);
            
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = 1;
        gridbag.setConstraints(labBoardW, c);
        panBoardSettings.add(labBoardW);
            
        c.weightx = 0.0;	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(tfdBoardW, c);
        panBoardSettings.add(tfdBoardW);
            
        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = 1;
        gridbag.setConstraints(labBoardH, c);
        panBoardSettings.add(labBoardH);
            
        c.weightx = 0.0;	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(tfdBoardH, c);
        panBoardSettings.add(tfdBoardH);
            
        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = 1;
        gridbag.setConstraints(labSheetW, c);
        panBoardSettings.add(labSheetW);
            
        c.weightx = 0.0;	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(tfdSheetW, c);
        panBoardSettings.add(tfdSheetW);
            
        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = 1;
        gridbag.setConstraints(labSheetH, c);
        panBoardSettings.add(labSheetH);
            
        c.weightx = 0.0;	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(tfdSheetH, c);
        panBoardSettings.add(tfdSheetH);
            
        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(butChangeBoard, c);
        panBoardSettings.add(butChangeBoard);
    }
  
    /**
     * Sets up the entities panel
     */
    private void setupEntities() {
        lisEntities = new List(10);

        butLoad = new Button("Load Entity (meps only)...");
        butLoad.setActionCommand("load_mech");
        butLoad.addActionListener(this);
            
        butDelete = new Button("Delete Entity");
        butDelete.setActionCommand("delete_mech");
        butDelete.addActionListener(this);
            
        panEntities = new Panel();
            
        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panEntities.setLayout(gridbag);
            
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1.0;	c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(lisEntities, c);
        panEntities.add(lisEntities);
            
        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = 1;
        gridbag.setConstraints(butLoad, c);
        panEntities.add(butLoad);
            
        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(butDelete, c);
        panEntities.add(butDelete);
    }

    /**
     * Sets up the battle values panel
     */
        private void setupBVs() {
        labBVs = new Label("Total Battle Values", Label.CENTER);
            
        lisBVs = new List(5);
            
        panBVs = new Panel();
            
        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panBVs.setLayout(gridbag);
            
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1.0;	c.weighty = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(labBVs, c);
        panBVs.add(labBVs);
            
        c.weightx = 1.0;	c.weighty = 1.0;
        gridbag.setConstraints(lisBVs, c);
        panBVs.add(lisBVs);
    }

    /**
     * Sets up the starting positions panel
     */
    private void setupStarts() {
        labStarts = new Label("Starting Positions", Label.CENTER);
        
        butPos = new Button[8];
        for (int i = 0; i < 8; i++) {
            butPos[i] = new Button(new Integer(i).toString());
            butPos[i].setActionCommand("starting_pos_" + i);
            butPos[i].addActionListener(this);
        }
        
        labMiddlePosition = new Label("", Label.CENTER);
            
        panStartButtons = new Panel();

        lisStarts = new List(5);
            
        panStarts = new Panel();
            
        // layout
        panStartButtons.setLayout(new GridLayout(3, 3));
        panStartButtons.add(butPos[0]);
        panStartButtons.add(butPos[1]);
        panStartButtons.add(butPos[2]);
        panStartButtons.add(butPos[7]);
        panStartButtons.add(labMiddlePosition);
        panStartButtons.add(butPos[3]);
        panStartButtons.add(butPos[6]);
        panStartButtons.add(butPos[5]);
        panStartButtons.add(butPos[4]);
        
        panStarts.setLayout(new BorderLayout());
        panStarts.add(labStarts, BorderLayout.NORTH);
        panStarts.add(panStartButtons, BorderLayout.WEST);
        panStarts.add(lisStarts, BorderLayout.CENTER);
    }

    /**
     * Refreshes the game settings with new info from the client
     */
    private void refreshGameSettings() {
        tfdBoardW.setText(new Integer(client.gameSettings.boardWidth).toString());
        tfdBoardH.setText(new Integer(client.gameSettings.boardHeight).toString());
        tfdSheetW.setText(new Integer(client.gameSettings.sheetWidth).toString());
        tfdSheetH.setText(new Integer(client.gameSettings.sheetHeight).toString());
    }
  
    /**
     * Refreshes the entities from the client
     */
    private void refreshEntities() {
        lisEntities.removeAll();
        int listIndex = 0;
        entityCorrespondance = new int[client.game.getNoOfEntities()];
        for (Enumeration i = client.getEntities(); i.hasMoreElements();) {
            Entity entity = (Entity)i.nextElement();
            lisEntities.add(entity.getDisplayName() 
                            + " BV=" + entity.calculateBattleValue());
            entityCorrespondance[listIndex++] = entity.getId();
        }
  }
	
    /**
     * Refreshes the battle values from the client
     */
    private void refreshBVs() {
        lisBVs.removeAll();
        for (Enumeration i = client.getPlayers(); i.hasMoreElements();) {
            Player player = (Player)i.nextElement();
            if(player != null) {
                int playerBV = 0;
                for (Enumeration j = client.getEntities(); j.hasMoreElements();) {
                    Entity entity = (Entity)j.nextElement();
                    if (entity.getOwner().equals(player)) {
                        playerBV += entity.calculateBattleValue();
                    }
                }
                lisBVs.add(player.getName() + " BV=" + playerBV);
            }
        }
    }
    
    /**
     * Refreshes the starting positions
     */
    private void refreshStarts() {
        lisStarts.removeAll();
        for (Enumeration i = client.getPlayers(); i.hasMoreElements();) {
            Player player = (Player)i.nextElement();
            if(player != null) {
                lisStarts.add(player.getName() + " : " + player.getStartingPos());
            }
        }
    }
	
	/**
	 * Refresh the color choice box
	 */
	private void refreshColors() {
		choColor.removeAll();
		for(int i = 0; i < Player.colorNames.length; i++) {
			choColor.add(Player.colorNames[i]);
		}
		choColor.select(Player.colorNames[client.getLocalPlayer().getColorIndex()]);
	}
  
    /**
     * Refreshes the "ready" status of the ready button
     */
    private void refreshReadyButton() {
	    butReady.setLabel(client.getLocalPlayer().isReady() ? "Cancel Ready" : "I'm Ready.");
    }
	
	/**
	 * Change local player color.
	 */
	public void changeColor(int nc) {
		client.getLocalPlayer().setColorIndex(nc);
		client.sendPlayerInfo();
	}
  
    //
	// GameListener
	//
	public void gamePlayerStatusChange(GameEvent ev) {
        refreshReadyButton();
        refreshBVs();
        refreshStarts();
        refreshColors();
	}
	public void gamePhaseChange(GameEvent ev) {
	    if (client.game.phase !=  Game.PHASE_LOUNGE) {
	        // unregister stuff.
	        client.removeGameListener(this);
            client.game.board.removeBoardListener(this);
        }
	}
	public void gameNewEntities(GameEvent ev) {
        refreshEntities();
        refreshBVs();
	}
	public void gameNewSettings(GameEvent ev) {
        refreshGameSettings();
	}
	
	//
	// ItemListener
	//
	public void itemStateChanged(ItemEvent ev) {
		if (ev.getSource().equals(choColor)) {
			for (int i = 0; i < Player.colorNames.length; i++) {
				if (Player.colorNames[i].equalsIgnoreCase(choColor.getSelectedItem())) {
					changeColor(i);
				}
			}
		}
		refreshColors();
	}


	//
	// ActionListener
	//
	public void actionPerformed(ActionEvent ev) {
        if (ev.getActionCommand().equalsIgnoreCase("ready")) {
            client.sendReady(!client.getLocalPlayer().isReady());
            refreshReadyButton();
        } else if (ev.getActionCommand().equalsIgnoreCase("load_mech")) {
            // add (load) mech
            FileDialog fd = new FileDialog(client.frame, 
                                           "Select a .mep file...",
                                           FileDialog.LOAD);
                                              
            fd.setDirectory("data" + File.separator + "mep");
            fd.show();
            MepFile mf = new MepFile(fd.getDirectory() + File.separator + fd.getFile());
            if (mf == null || fd.getFile() == null) {
                // error reading mech
                //TODO: display error in a dialog
                System.err.println("chatlounge: could not read mepfile.");
            } else {
                Mech mech = mf.getMech();
                if (mech == null) {
                    // error reading mech
                    //TODO: display error in a dialog
                    System.err.println("chatlounge: could not make a mech from mepfile.  (probably not 3025)");
                } else {
                    mech.setOwner(client.getLocalPlayer());
                    client.sendAddEntity(mech);
                }
            }
        } else if (ev.getActionCommand().equalsIgnoreCase("delete_mech")) {
            // delete mech
            if (lisEntities.getSelectedIndex() != -1) {
                client.sendDeleteEntity(entityCorrespondance[lisEntities.getSelectedIndex()]);
            }
        } else if (ev.getActionCommand().equalsIgnoreCase("change_board")) {
            // board settings 
            //client.gameSettings.boardWidth = Integer.parseInt(tfdBoardW.getText());
            //client.gameSettings.boardHeight = Integer.parseInt(tfdBoardH.getText());
            client.gameSettings.sheetWidth = Integer.parseInt(tfdSheetW.getText());
            client.gameSettings.sheetHeight = Integer.parseInt(tfdSheetH.getText());
            client.sendGameSettings();
        } else if (ev.getActionCommand().startsWith("starting_pos_")) {
            // starting position
            client.getLocalPlayer().setStartingPos(Integer.parseInt(ev.getActionCommand().substring(13)));
		    client.sendPlayerInfo();
        }
	}
	
}