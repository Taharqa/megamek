/*
 * MegaMek -
 * Copyright (C) 2007 Ben Mazur (bmazur@sev.org)
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
package megamek.client.bot;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;

import megamek.common.Coords;
import megamek.common.Entity;
import megamek.common.Player;
import megamek.common.event.GameEvent;
import megamek.common.event.GamePlayerChatEvent;

public class ChatProcessor {

    private boolean shouldBotAcknowledgeDefeat(String message, BotClient bot) {
        boolean result = false;
        if (message
                .contains("declares individual victory at the end of the turn.")
                || message
                        .contains("declares team victory at the end of the turn.")) {
            String[] splitMessage = message.split(" ");
            int i = 1;
            String name = splitMessage[i];
            while (!splitMessage[i + 1].equals("declares")) {
                name += " " + splitMessage[i + 1];
                i++;
            }
            for (Player p : bot.game.getPlayersVector()) {
                if (p.getName().equals(name)) {
                    if (p.isEnemyOf(bot.getLocalPlayer())) {
                        bot.sendChat("/defeat");
                        result = true;
                    }
                    break;
                }
            }
        }
        return result;
    }

    private boolean shouldBotAcknowledgeVictory(String message, BotClient bot) {
        boolean result = false;

        if (message.contains("type /victory to accept the surrender")) {
            String[] splitMessage = message.split(" ");
            int i = 1;
            String name = splitMessage[i];
            while (!splitMessage[i + 1].equals("wants")
                    && !splitMessage[i + 1].equals("admits")) {
                name += " " + splitMessage[i + 1];
                i++;
            }
            for (Player p : bot.game.getPlayersVector()) {
                if (p.getName().equals(name)) {
                    if (p.isEnemyOf(bot.getLocalPlayer())) {
                        bot.sendChat("/victory");
                        result = true;
                    }
                    break;
                }
            }
        }

        return result;
    }

    public void processChat(GamePlayerChatEvent ge, BotClient bot) {
        if (ge.getType() != GameEvent.GAME_PLAYER_CHAT) {
            return;
        }
        if (bot.getLocalPlayer() == null) {
            return;
        }

        String message = ge.getMessage();
        if (shouldBotAcknowledgeDefeat(message, bot)) {
            return;
        }
        if (shouldBotAcknowledgeVictory(message, bot)) {
            return;
        }

        // Check for end of message.
        StringTokenizer st = new StringTokenizer(ge.getMessage(), ":"); //$NON-NLS-1$
        if (!st.hasMoreTokens()) {
            return;
        }
        String name = st.nextToken().trim();
        // who is the message from?
        Enumeration<Player> e = bot.game.getPlayers();
        Player p = null;
        while (e.hasMoreElements()) {
            p = e.nextElement();
            if (name.equalsIgnoreCase(p.getName())) {
                break;
            }
        }
        if (p == null) {
            return;
        }
        if (bot instanceof TestBot) {
            additionalTestBotCommands(st, (TestBot) bot, p);
        }
    }

    private void additionalTestBotCommands(StringTokenizer st, TestBot tb,
            Player p) {
        try {
            if (st.hasMoreTokens()
                    && st.nextToken().trim()
                            .equalsIgnoreCase(tb.getLocalPlayer().getName())) {
                if (!p.isEnemyOf(tb.getLocalPlayer())) {
                    if (st.hasMoreTokens()) {
                        String command = st.nextToken().trim();
                        boolean understood = false;
                        // should create a command factory and a
                        // command object...
                        if (command.equalsIgnoreCase("echo")) { //$NON-NLS-1$
                            understood = true;
                        }
                        if (command.equalsIgnoreCase("calm down")) { //$NON-NLS-1$
                            Iterator<Entity> i = tb.getEntitiesOwned()
                                    .iterator();
                            while (i.hasNext()) {
                                CEntity cen = tb.centities.get(i.next());
                                if (cen.strategy.attack > 1) {
                                    cen.strategy.attack = 1;
                                }
                            }
                            understood = true;
                        } else if (command.equalsIgnoreCase("be aggressive")) { //$NON-NLS-1$
                            Iterator<Entity> i = tb.getEntitiesOwned()
                                    .iterator();
                            while (i.hasNext()) {
                                CEntity cen = tb.centities.get(i.next());
                                cen.strategy.attack = Math.min(
                                        cen.strategy.attack * 1.2, 1.5);
                            }
                            understood = true;
                        } else if (command.equalsIgnoreCase("attack")) { //$NON-NLS-1$
                            int x = Integer.parseInt(st.nextToken().trim());
                            int y = Integer.parseInt(st.nextToken().trim());
                            Entity en = tb.game.getFirstEntity(new Coords(
                                    x - 1, y - 1));
                            if (en != null) {
                                if (en.isEnemyOf(tb.getEntitiesOwned().get(0))) {
                                    CEntity cen = tb.centities.get(en);
                                    cen.strategy.target += 3;
                                    System.out.println(cen.entity
                                            .getShortName()
                                            + " " + cen.strategy.target); //$NON-NLS-1$
                                    understood = true;
                                }
                            }
                        }
                        if (understood) {
                            tb.sendChat("Understood " + p.getName());
                        }
                    }
                } else {
                    tb.sendChat("I can't do that, " + p.getName());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
