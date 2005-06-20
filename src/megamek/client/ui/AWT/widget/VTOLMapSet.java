/**
 * MegaMek - Copyright (C) 2000,2001,2002,2004,2005 Ben Mazur (bmazur@sev.org)
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

package megamek.client.util.widget;

import java.awt.*;
import java.util.*;

import megamek.client.GUIPreferences;
import megamek.client.Messages;
import megamek.common.*;

/**
 * Class which keeps set of all areas required to 
 * represent Tank unit in MechDsiplay.ArmorPanel class.
 */



public class VTOLMapSet implements DisplayMapSet {
  
    private Component comp;
    private PMSimplePolygonArea[] areas = new PMSimplePolygonArea[16];
    private PMSimpleLabel[] labels = new PMSimpleLabel[22];
    private PMValueLabel[] vLabels = new PMValueLabel[16];
    private Vector  bgDrawers = new Vector();
    private PMAreasGroup content = new PMAreasGroup();
  
    private static final int INT_STR_OFFSET = 8;
    //Polygons for all areas
    private Polygon frontArmor = new Polygon(new int[]{30,60,90,120},
                                             new int[]{30,0,0,30},
                                             4);
    //front internal structure
    private Polygon frontIS = new Polygon(new int[]{30,120,90,60},
                                          new int[]{30,30,45,45},
                                          4);
    //Left armor
    private Polygon leftArmor1 = new Polygon(new int[]{30,30,60,60},
                                             new int[]{75,30,45,75},
                                             4);  
    private Polygon leftArmor2 = new Polygon(new int[]{30,30,60,60},
                                             new int[]{135,90,90,150},
                                             4);
    //Left internal structure
    private Polygon leftIS1 = new Polygon(new int[]{60,60,75,75},
                                          new int[]{75,45,45,75},
                                          4);
    private Polygon leftIS2 = new Polygon(new int[]{60,60,75,75},
                                          new int[]{150,90,90,150},
                                          4);
    //Right armor
    private Polygon rightArmor1 = new Polygon(new int[]{90,90,120,120},
                                              new int[]{75,45,30,75},
                                              4);
    private Polygon rightArmor2 = new Polygon(new int[]{90,90,120,120},
                                              new int[]{150,90,90,135},
                                              4);
    //Right internal structure
    private Polygon rightIS1 = new Polygon(new int[]{75,75,90,90},
                                           new int[]{75,45,45,75},
                                           4);
    private Polygon rightIS2 = new Polygon(new int[]{75,75,90,90},
                                           new int[]{150,90,90,150},
                                           4);
    //Rear armor
    private Polygon rearArmor = new Polygon(new int[]{67,67,83,83},
                                            new int[]{180,150,150,180},
                                            4);
    //Rear internal structure
    private Polygon rearIS = new Polygon(new int[]{67,67,83,83},
                                         new int[]{240,180,180,240},
                                         4);
    //Rotor armor
    private Polygon rotorArmor1 = new Polygon(new int[]{0,0,45,45},
                                               new int[]{90,75,75,90},
                                               4);
    private Polygon rotorArmor2 = new Polygon(new int[]{105,105,150,150},
                                               new int[]{90,75,75,90},
                                               4);
    //Rotor internal structure
    private Polygon rotorIS = new Polygon(new int[]{45,45,105,105},
                                           new int[]{90,75,75,90},
                                           4);

    
    private static final Font       FONT_LABEL = new Font("SansSerif", Font.PLAIN, GUIPreferences.getInstance().getInt("AdvancedMechDisplayArmorSmallFontSize")); //$NON-NLS-1$
    private static final Font       FONT_VALUE = new Font("SansSerif", Font.PLAIN, GUIPreferences.getInstance().getInt("AdvancedMechDisplayArmorLargeFontSize")); //$NON-NLS-1$
 
  
  
    public VTOLMapSet(Component c) {
        comp = c;
        setAreas();
        setLabels();
        setBackGround();
        translateAreas();
        setContent();
    }
  
    public void setRest() {
    }
  
    public PMAreasGroup getContentGroup() {
        return content;
    }
    
    public Vector getBackgroundDrawers() {
        return bgDrawers;
    }
    
    public void setEntity(Entity e) {
        VTOL t = (VTOL) e;
        int a = 1;
        int a0 = 1;
        int x = 0;
        for(int i = 1; i <= 8; i++) {
            switch (i) {
                case 1: x = 1; break;
                case 2: x = 2; break;
                case 3: x = 2; break;
                case 4: x = 3; break;
                case 5: x = 3; break;
                case 6: x = 4; break;
                case 7: x = 5; break;
                case 8: x = 5; break;
            }
            a = t.getArmor(x);
            a0 = t.getOArmor(x);
            vLabels[i].setValue(t.getArmorString(x));
            setAreaColor(areas[i], vLabels[i], (double)a/(double)a0);
        }
        for(int i = 9; i <= 15; i++) {
            switch (i) {
                case 9: x = 1; break;
                case 10: x = 2; break;
                case 11: x = 2; break;
                case 12: x = 3; break;
                case 13: x = 3; break;
                case 14: x = 4; break;
                case 15: x = 5; break;
            }
            a = t.getInternal(x);
            a0 = t.getOInternal(x);
            vLabels[i].setValue(t.getInternalString(x));
            setAreaColor(areas[i], vLabels[i], (double)a/(double)a0);
        }
    }
    
    private void setContent() {
        for(int i = 1; i <= 15; i++) {
            content.addArea(areas[i]);
            content.addArea(vLabels[i]);
        }
        for(int i = 1; i <= 21; i++) {
            content.addArea(labels[i]);
        }
    }
  
    private void setAreas() {
        areas[1] = new PMSimplePolygonArea(frontArmor);
        areas[2] = new PMSimplePolygonArea(rightArmor1);
        areas[3] = new PMSimplePolygonArea(rightArmor2);
        areas[4] = new PMSimplePolygonArea(leftArmor1);
        areas[5] = new PMSimplePolygonArea(leftArmor2);
        areas[6] = new PMSimplePolygonArea(rearArmor);
        areas[7] = new PMSimplePolygonArea(rotorArmor1);
        areas[8] = new PMSimplePolygonArea(rotorArmor2);
        areas[9] = new PMSimplePolygonArea(frontIS);
        areas[10] = new PMSimplePolygonArea(rightIS1);
        areas[11] = new PMSimplePolygonArea(rightIS2);
        areas[12] = new PMSimplePolygonArea(leftIS1);
        areas[13] = new PMSimplePolygonArea(leftIS2);
        areas[14] = new PMSimplePolygonArea(rearIS);
        areas[15] = new PMSimplePolygonArea(rotorIS);
    }
  
    private void setLabels() {
        FontMetrics fm = comp.getFontMetrics(FONT_LABEL);
    
        //Labels for Front view
        labels[1] = createLabel(Messages.getString("VTOLMapSet.FrontArmor"), fm, Color.black,68,20); //$NON-NLS-1$
        labels[2] = createLabel(Messages.getString("VTOLMapSet.LS"), fm, Color.black,44,50); //$NON-NLS-1$
        labels[3] = createLabel(Messages.getString("VTOLMapSet.LS"), fm, Color.black,44,100); //$NON-NLS-1$
        labels[4] = createLabel(Messages.getString("VTOLMapSet.RS"), fm, Color.black,104,50); //$NON-NLS-1$
        labels[5] = createLabel(Messages.getString("VTOLMapSet.RS"), fm, Color.black,104,100); //$NON-NLS-1$
        labels[6] = createLabel(Messages.getString("VTOLMapSet.RearArmor1"), fm, Color.black,76,185); //$NON-NLS-1$
        labels[7] = createLabel(Messages.getString("VTOLMapSet.RearArmor2"), fm, Color.black,76,195); //$NON-NLS-1$
        labels[8] = createLabel(Messages.getString("VTOLMapSet.RotorArmor"), fm, Color.black,18,82); //$NON-NLS-1$
        labels[9] = createLabel(Messages.getString("VTOLMapSet.RotorArmor"), fm, Color.black,123,82); //$NON-NLS-1$
        labels[10] = createLabel(Messages.getString("VTOLMapSet.FrontIS"), fm, Color.black,68,35); //$NON-NLS-1$
        labels[11] = createLabel(Messages.getString("VTOLMapSet.LIS1"), fm, Color.black,68,48); //$NON-NLS-1$
        labels[12] = createLabel(Messages.getString("VTOLMapSet.LIS2"), fm, Color.black,68,57); //$NON-NLS-1$
        labels[13] = createLabel(Messages.getString("VTOLMapSet.LIS1"), fm, Color.black,68,100); //$NON-NLS-1$
        labels[14] = createLabel(Messages.getString("VTOLMapSet.LIS2"), fm, Color.black,68,110); //$NON-NLS-1$
        labels[15] = createLabel(Messages.getString("VTOLMapSet.RIS1"), fm, Color.black,84,48); //$NON-NLS-1$
        labels[16] = createLabel(Messages.getString("VTOLMapSet.RIS2"), fm, Color.black,84,57); //$NON-NLS-1$
        labels[17] = createLabel(Messages.getString("VTOLMapSet.RIS1"), fm, Color.black,84,100); //$NON-NLS-1$
        labels[18] = createLabel(Messages.getString("VTOLMapSet.RIS2"), fm, Color.black,84,110); //$NON-NLS-1$
        labels[19] = createLabel(Messages.getString("VTOLMapSet.RearIS1"), fm, Color.black,76,152); //$NON-NLS-1$
        labels[20] = createLabel(Messages.getString("VTOLMapSet.RearIS2"), fm, Color.black,76,161); //$NON-NLS-1$
        labels[21] = createLabel(Messages.getString("VTOLMapSet.RotorIS"), fm, Color.black,73,82); //$NON-NLS-1$
    
        //Value labels for all parts of mek
        //front 
        fm = comp.getFontMetrics(FONT_VALUE);   
        vLabels[1] = createValueLabel(101, 22, "", fm); //$NON-NLS-1$ Front
        vLabels[2] = createValueLabel(44, 65, "", fm); //$NON-NLS-1$ LS
        vLabels[3] = createValueLabel(44, 115, "", fm); //$NON-NLS-1$ LS
        vLabels[4] = createValueLabel(105, 65, "", fm); //$NON-NLS-1$ RS
        vLabels[5] = createValueLabel(105, 115, "", fm); //$NON-NLS-1$ RS
        vLabels[6] = createValueLabel(76, 207, "", fm); //$NON-NLS-1$ Rear
        vLabels[7] = createValueLabel(38, 83, "", fm); //$NON-NLS-1$ Rotor
        vLabels[8] = createValueLabel(143, 83, "", fm); //$NON-NLS-1$ Rotor
        vLabels[9] = createValueLabel(94, 37, "", fm); //$NON-NLS-1$ Front
        vLabels[10] = createValueLabel(68, 68, "", fm); //$NON-NLS-1$ LS
        vLabels[11] = createValueLabel(68, 122, "", fm); //$NON-NLS-1$ LS
        vLabels[12] = createValueLabel(84, 68, "", fm); //$NON-NLS-1$ RS
        vLabels[13] = createValueLabel(84, 122, "", fm); //$NON-NLS-1$ RS
        vLabels[14] = createValueLabel(76, 172, "", fm); //$NON-NLS-1$ Rear
        vLabels[15] = createValueLabel(98, 83, "", fm); //$NON-NLS-1$ Rotor
    }
    
    private void setBackGround() {
        Image tile = comp.getToolkit().getImage("data/widgets/tile.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        int b = BackGroundDrawer.TILING_BOTH;
        bgDrawers.addElement(new BackGroundDrawer (tile,b));
        
        b = BackGroundDrawer.TILING_HORIZONTAL | 
            BackGroundDrawer.VALIGN_TOP;
        tile = comp.getToolkit().getImage("data/widgets/h_line.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        bgDrawers.addElement(new BackGroundDrawer (tile,b));                
        
        b = BackGroundDrawer.TILING_HORIZONTAL | 
            BackGroundDrawer.VALIGN_BOTTOM;
        tile = comp.getToolkit().getImage("data/widgets/h_line.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        bgDrawers.addElement(new BackGroundDrawer (tile,b));
        
        b = BackGroundDrawer.TILING_VERTICAL | 
            BackGroundDrawer.HALIGN_LEFT;
        tile = comp.getToolkit().getImage("data/widgets/v_line.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        bgDrawers.addElement(new BackGroundDrawer (tile,b));
        
        b = BackGroundDrawer.TILING_VERTICAL | 
            BackGroundDrawer.HALIGN_RIGHT;
        tile = comp.getToolkit().getImage("data/widgets/v_line.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        bgDrawers.addElement(new BackGroundDrawer (tile,b));
        
        b = BackGroundDrawer.NO_TILING | 
            BackGroundDrawer.VALIGN_TOP |
            BackGroundDrawer.HALIGN_LEFT;
        tile = comp.getToolkit().getImage("data/widgets/tl_corner.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        bgDrawers.addElement(new BackGroundDrawer (tile,b));
        
        b = BackGroundDrawer.NO_TILING | 
            BackGroundDrawer.VALIGN_BOTTOM |
            BackGroundDrawer.HALIGN_LEFT;
        tile = comp.getToolkit().getImage("data/widgets/bl_corner.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        bgDrawers.addElement(new BackGroundDrawer (tile,b));
        
        b = BackGroundDrawer.NO_TILING | 
            BackGroundDrawer.VALIGN_TOP |
            BackGroundDrawer.HALIGN_RIGHT;
        tile = comp.getToolkit().getImage("data/widgets/tr_corner.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        bgDrawers.addElement(new BackGroundDrawer (tile,b));
        
        b = BackGroundDrawer.NO_TILING | 
            BackGroundDrawer.VALIGN_BOTTOM |
            BackGroundDrawer.HALIGN_RIGHT;
        tile = comp.getToolkit().getImage("data/widgets/br_corner.gif"); //$NON-NLS-1$
        PMUtil.setImage(tile,comp);
        bgDrawers.addElement(new BackGroundDrawer (tile,b));
    }
    
    private void translateAreas() {
    }
    
    private PMSimpleLabel createLabel(String s, FontMetrics fm,Color color, int x, int y) {
        PMSimpleLabel l = new PMSimpleLabel(s, fm, color);
        centerLabelAt(l,x,y);
        return l; 
    }
    
    private PMValueLabel createValueLabel(int x, int y, String v, FontMetrics fm) {
        PMValueLabel l = new PMValueLabel(fm, Color.red);
        centerLabelAt(l, x, y);
        l.setValue(v);
        return l;
    }
    
    private void centerLabelAt(PMSimpleLabel l, int x, int y) {
        if (l == null) return;
        Dimension d = l.getSize();
        l.moveTo( x - d.width/2, y + d.height/2); 
    }
    
    private void setAreaColor(PMSimplePolygonArea ha, PMValueLabel l, double percentRemaining) {
        if ( percentRemaining <= 0 ){
            ha.backColor = Color.darkGray.brighter();
            l.setColor(Color.blue);
            ha.highlightBorderColor = Color.green;          
        } else if ( percentRemaining <= .25 ){
            ha.backColor = Color.red.brighter();
            l.setColor(Color.blue);
            ha.highlightBorderColor = Color.green;          
        } else if ( percentRemaining <= .75 ){
            ha.backColor = Color.yellow;
            l.setColor(Color.blue);
            ha.highlightBorderColor = Color.green;
        } else {
            ha.backColor = Color.gray.brighter();
            l.setColor(Color.red);
            ha.highlightBorderColor = Color.red;
        }
    }
    
}
