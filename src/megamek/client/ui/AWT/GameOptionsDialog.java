/*
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

/*
 * GameOptionsDialog.java
 *
 * Created on April 26, 2002, 2:14 PM
 */

package megamek.client;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import megamek.common.*;
import megamek.common.options.*;

/**
 * Responsible for displaying the current game options and allowing the user to
 * change them.
 *
 * @author  Ben
 * @version 
 */
public class GameOptionsDialog extends Dialog implements ActionListener, DialogOptionListener {
    
    private Client client;
    private GameOptions options;

    private boolean editable = true;

    private Vector optionComps = new Vector();
    
    private AlertDialog savedAlert = null;
    private Panel panOptions = new Panel();
    private ScrollPane scrOptions = new ScrollPane();
    
    private TextArea texDesc = new TextArea("Mouse over an option to see a description.", 3, 35, TextArea.SCROLLBARS_VERTICAL_ONLY);
    
    private Panel panPassword = new Panel();
    private Label labPass = new Label("Password:");
    private TextField texPass = new TextField(15);
    
    private Panel panButtons = new Panel();
    private Button butSave = new Button("Save");
    private Button butOkay = new Button("Okay");
    private Button butCancel = new Button("Cancel");

    /**
     * Initialize this dialog.
     *
     * @param   frame - the <code>Frame</code> parent of this dialog.
     * @param   options - the <code>GameOptions</code> to be displayed.
     */
    private void init( Frame frame, GameOptions options ) {
        this.options = options;
        
        scrOptions.add(panOptions);
        
        texDesc.setEditable(false);
        
        setupButtons();
        setupPassword();
        
        
        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
            
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1.0;    c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(scrOptions, c);
        add(scrOptions);
            
        c.weightx = 1.0;    c.weighty = 0.0;
        gridbag.setConstraints(texDesc, c);
        add(texDesc);
        
        gridbag.setConstraints(panPassword, c);
        add(panPassword);
        
        gridbag.setConstraints(panButtons, c);
        add(panButtons);
        
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) { setVisible(false); }
            });
        
        pack();
        setSize(getSize().width, Math.max(getSize().height, 400));
        setResizable(false);
        setLocation(frame.getLocation().x + frame.getSize().width/2 - getSize().width/2,
                    frame.getLocation().y + frame.getSize().height/2 - getSize().height/2);

        savedAlert = new AlertDialog(frame, "Options saved", "Default games options saved. Next time you host a game these options will be automatically loaded and set.");

    }

    /**
     * Creates new <code>GameOptionsDialog</code> for a <code>Client</code>
     *
     * @param   client - the <code>Client</code> parent of this dialog.
     */
    public GameOptionsDialog(Client client) {
        super(client.frame, "View/Edit Game Options...", true);
        this.client = client;
        this.init( client.frame, client.game.getOptions() );
    }

    /**
     * Creates new <code>GameOptionsDialog</code> for a given 
     * <code>Frame</code>, with given set of options.
     *
     * @param   frame - the <code>Frame</code> parent of this dialog.
     * @param   options - the <code>GameOptions</code> to be displayed.
     */
    public GameOptionsDialog( Frame frame, GameOptions options ) {
        super(frame, "View/Edit Game Options...", true);
        this.client = null;
        this.init( frame, options );
        butOkay.setEnabled( false );
    }

    public void update(GameOptions options) {
        this.options = options;
        refreshOptions();
    }
    
    public void send() {
        Vector changed = new Vector();
        
        for (Enumeration i = optionComps.elements(); i.hasMoreElements();) {
            DialogOptionComponent comp = (DialogOptionComponent)i.nextElement();
            
            if (comp.hasChanged()) {
                changed.addElement(comp.changedOption());
            }
        }
        
        if (client != null && changed.size() > 0) {
            client.sendGameOptions(texPass.getText(), changed);
        }
    }
    
    public void doSave() {
      Vector options = new Vector();
      
      for ( Enumeration i = optionComps.elements(); i.hasMoreElements(); ) {
        DialogOptionComponent comp = (DialogOptionComponent)i.nextElement();
        
        GameOption option = comp.getOption().deepCopy();
        option.setValue(comp.getValue());
        
        options.addElement(option);
      }
      
      GameOptions.saveOptions(options);
      
      savedAlert.show();
    }
    
    private void refreshOptions() {
        panOptions.removeAll();
        optionComps = new Vector();
        
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panOptions.setLayout(gridbag);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(1, 1, 0, 0);
        c.ipadx = 0;    c.ipady = 0;
        
        for (Enumeration i = options.groups(); i.hasMoreElements();) {
            OptionGroup group = (OptionGroup)i.nextElement();
            
            addGroup(group, gridbag, c);
            
            for (Enumeration j = group.options(); j.hasMoreElements();) {
                GameOption option = (GameOption)j.nextElement();
                
                addOption(option, gridbag, c);
            }
        }
        
        validate();
    }
    
    private void addGroup(OptionGroup group, GridBagLayout gridbag, GridBagConstraints c) {
        Label groupLabel = new Label(group.getName());
        
        gridbag.setConstraints(groupLabel, c);
        panOptions.add(groupLabel);
    }
    
    private void addOption(GameOption option, GridBagLayout gridbag, GridBagConstraints c) {
        DialogOptionComponent optionComp = new DialogOptionComponent(this, option);
        
        gridbag.setConstraints(optionComp, c);
        panOptions.add(optionComp);
        optionComp.setEditable( editable );
        
        optionComps.addElement(optionComp);
    }
    
    
    public void showDescFor(GameOption option) {
        texDesc.setText(option.getDesc());
    }
    
    
    private void setupButtons() {
        butSave.addActionListener(this);
        butOkay.addActionListener(this);
        butCancel.addActionListener(this);
        
        // layout
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        panButtons.setLayout(gridbag);
            
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 1.0;    c.weighty = 1.0;
        c.fill = GridBagConstraints.VERTICAL;
        c.ipadx = 20;    c.ipady = 5;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        gridbag.setConstraints(butOkay, c);
        panButtons.add(butOkay);
            
        c.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(butSave, c);
        panButtons.add(butSave);
            
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.anchor = GridBagConstraints.CENTER;
        gridbag.setConstraints(butCancel, c);
        panButtons.add(butCancel);
    }
    
    private void setupPassword() {
        panPassword.setLayout(new BorderLayout());
            
        panPassword.add(labPass, BorderLayout.WEST);
        panPassword.add(texPass, BorderLayout.CENTER);
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == butOkay) {
            send();
        } else if (e.getSource() == butSave) {
          doSave();
          
          return;
        }
        
        this.setVisible(false);
    }

    /**
     * Update the dialog so that it is editable or view-only.
     *
     * @param   editable - <code>true</code> if the contents of the dialog
     *          are editable, <code>false</code> if they are view-only.
     */
    public void setEditable( boolean editable ) {

        // Set enabled state of all of the option components in the dialog.
        for ( Enumeration i = optionComps.elements(); i.hasMoreElements(); ) {
            DialogOptionComponent comp = 
                (DialogOptionComponent) i.nextElement();
            comp.setEditable( editable );
        }

        // If the panel is editable, the player can commit and save.
        texPass.setEnabled( editable );
        butOkay.setEnabled( editable && client != null );
        butSave.setEnabled( editable );

        // Update our data element.
        this.editable = editable;
    }

    /**
     * Determine whether the dialog is editable or view-only.
     *
     * @return  <code>true</code> if the contents of the dialog are
     *          editable, <code>false</code> if they are view-only.
     */
    public boolean isEditable() {
        return this.editable;
    }
        
}
