/*
 * MegaMek - Copyright (C) 2000,2001,2002,2003,2004,2005
 * Ben Mazur (bmazur@sev.org)
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

import megamek.client.Client;
import megamek.common.Entity;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class displays a window that displays the forces currently selected
 * in the lounge.
 *
 * @author Ryan McConnell (oscarmm)
 * @version $Revision$
 * @since 0.31
 */
public class MechGroupView
        extends JDialog
        implements ActionListener, ItemListener {

    List entities = new List(20);
    JButton closeButton = new JButton(Messages.getString("Close"));
    JTextArea ta = new JTextArea();

    Client client;
    int[] entityArray;

    MechGroupView(JFrame frame, Client c, int[] eA) {
        super(frame, Messages.getString("MechGroupView.title"));
        client = c;
        entityArray = eA;

        for (int i = 0; i < entityArray.length; i++) {
            Entity entity = client.game.getEntity(entityArray[i]);
            // Handle the "Blind Drop" option.
            if (entity == null)
                continue;
            if (!entity.getOwner().equals(client.getLocalPlayer())
                    && client.game.getOptions().booleanOption("blind_drop")
                    && !client.game.getOptions().booleanOption("real_blind_drop")) {
                entities.add(ChatLounge.formatUnit(entity, true));
            } else if (entity.getOwner().equals(client.getLocalPlayer())
                    || (!client.game.getOptions().booleanOption("blind_drop")
                    && !client.game.getOptions().booleanOption("real_blind_drop"))) {
                entities.add(ChatLounge.formatUnit(entity, false));
            }
        }
        setLayout(new BorderLayout());
        add(entities, BorderLayout.WEST);
        add(closeButton, BorderLayout.SOUTH);

        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(ta, BorderLayout.CENTER);

        entities.addItemListener(this);
        closeButton.addActionListener(this);

        setSize(550, 600);
        setLocation(frame.getLocation().x + frame.getSize().width / 2 - getSize().width / 2,
                frame.getLocation().y + frame.getSize().height / 10);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
            }
        });
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(closeButton)) {
            setVisible(false);
        }
    }

    public void itemStateChanged(ItemEvent ie) {
        if (ie.getSource().equals(entities)) {
            int selected = entities.getSelectedIndex();
            if (selected == -1) {
                ta.setText("");
                return;
            } else if (!client.game.getEntity(entityArray[selected]).getOwner().equals(client.getLocalPlayer())) {
                ta.setText("(enemy unit)");
            } else {
                Entity entity = client.game.getEntity(entityArray[selected]);
                MechView mechView = new MechView(entity);
                ta.setText(mechView.getMechReadout());
            }
        }
    }

}
