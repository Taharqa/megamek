/*
 * MegaMek - Copyright (C) 2000-2003 Ben Mazur (bmazur@sev.org)
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

import com.sun.java.util.collections.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import megamek.common.*;

public class BoardEditor extends Container
    implements BoardListener, ItemListener, ActionListener, TextListener,
    KeyListener, WindowListener {

    private Frame               frame = new Frame();
    
    private Game        game = new Game();
    private Board               board = game.getBoard();
    private BoardView1          bv = new BoardView1(game, frame);
    private CommonMenuBar       menuBar = new CommonMenuBar();
    private CommonAboutDialog   about  = null;
    private CommonHelpDialog    help   = null;
    private CommonSettingsDialog        setdlg = null;

    private Hex                 curHex = new Hex();

    private String              curpath, curfile;
    
    private boolean             ctrlheld, altheld;
    
    // buttons and labels and such:
    private HexCanvas           canHex;
    
    private Label               labElev;
    private TextField           texElev;
    private Button              butElevUp;
    private Button              butElevDown;
    
    private Label               labTerrain;
    private java.awt.List       lisTerrain;
    
    private Button              butDelTerrain;
    
    private Panel               panTerrainType;
    private Choice              choTerrainType;
    private TextField           texTerrainLevel;
    
    private Panel               panTerrExits;
    private Checkbox            cheTerrExitSpecified;
    private TextField           texTerrExits;
    private Button              butTerrExits;

    private Panel               panRoads;
    private Checkbox            cheRoadsAutoExit;

    private Label               labTheme;
    private TextField           texTheme;

    private Button              butAddTerrain;
    
    private Label               blankL;
    
    private Label               labBoard;
    private Panel               panButtons;
    private Button              butBoardNew, butBoardLoad;
    private Button              butBoardSave, butBoardSaveAs;
    private Button              butMiniMap;
    
    private Dialog               minimapW;
    private MiniMap              minimap;
    
    /**
     * Creates and lays out a new Board Editor frame.
     */
    public BoardEditor() {
        Settings.load();

        this.addKeyListener(bv);
        this.addKeyListener(this);
        bv.addKeyListener(this);
        board.addBoardListener(this);

        bv.setUseLOSTool(false);

        setupEditorPanel();
        setupFrame();

        frame.show();

        new AlertDialog(frame, "Please read the editor-readme.txt", "Instructions for using the editor may be found in editor-readme.txt.").show();
    }
    
    /**
     * Sets up the frame that will display the editor.
     */
    private void setupFrame() {
        frame.setTitle("MegaMek Board Editor : Unnamed");
        frame.setLayout(new BorderLayout());
        frame.add(bv, BorderLayout.CENTER);
        frame.add(this, BorderLayout.EAST);

        menuBar.addActionListener( this );
        frame.setMenuBar( menuBar );
      
        frame.setBackground(SystemColor.menu);
        frame.setForeground(SystemColor.menuText);
    
        // maybe we should have our own settings, instead of using the client's
        if (Settings.windowSizeHeight != 0) {
            frame.setLocation(Settings.windowPosX, Settings.windowPosY);
            frame.setSize(Settings.windowSizeWidth, Settings.windowSizeHeight);
        } else {
            frame.setSize(800, 600);
        }

        // when frame is closing, just hide it
        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    frame.setVisible(false);
                    setMapVisible(false);
                }
            });
    }

    /**
     * Sets up the editor panel, which goes on the right of the map and has
     * controls for editing the current square.
     */
    private void setupEditorPanel() {
        canHex = new HexCanvas();
        labElev = new Label("Elev:", Label.RIGHT);
        texElev = new TextField("0", 1);
        texElev.addActionListener(this);
        texElev.addTextListener(this);
        butElevUp = new Button("U");
        butElevUp.addActionListener(this);
        butElevDown = new Button("D");
        butElevDown.addActionListener(this);
    
        labTerrain = new Label("Terrain:", Label.LEFT);
        lisTerrain = new java.awt.List(6);
        lisTerrain.addItemListener(this);
        refreshTerrainList();
        
        butDelTerrain = new Button("Remove Terrain");
        butDelTerrain.addActionListener(this);
        
        choTerrainType = new Choice();
        for (int i = 1; i < Terrain.SIZE; i++) {
            choTerrainType.add(Terrain.getName(i));
        }
        
        texTerrainLevel = new TextField("0", 1);
        
        butAddTerrain = new Button("Add/Set Terrain");
        butAddTerrain.addActionListener(this);
        
        butMiniMap = new Button("Toggle MiniMap");
        butMiniMap.setActionCommand( "viewMiniMap" );
        butMiniMap.addActionListener(this);
        
        panTerrainType = new Panel(new BorderLayout());
        panTerrainType.add(choTerrainType, BorderLayout.WEST);
        panTerrainType.add(texTerrainLevel, BorderLayout.CENTER);
        
        cheTerrExitSpecified = new Checkbox("Set Exits : ");
        butTerrExits = new Button("A");
        texTerrExits = new TextField("0", 1);
        butTerrExits.addActionListener(this);
    
        panTerrExits = new Panel(new FlowLayout());
        panTerrExits.add(cheTerrExitSpecified);
        panTerrExits.add(butTerrExits);
        panTerrExits.add(texTerrExits);

        panRoads = new Panel(new FlowLayout());
        cheRoadsAutoExit = new Checkbox("Exit Roads to Pavement");
        cheRoadsAutoExit.addItemListener( this );
        panRoads.add(cheRoadsAutoExit);

        labTheme = new Label("Theme:", Label.LEFT);
        texTheme = new TextField("", 15);
        texTheme.addTextListener(this);

        labBoard = new Label("Board:", Label.LEFT);
        butBoardNew = new Button("New...");
        butBoardNew.setActionCommand( "fileBoardNew" );
        butBoardNew.addActionListener(this);

        butBoardLoad = new Button("Load...");
        butBoardLoad.setActionCommand( "fileBoardOpen" );
        butBoardLoad.addActionListener(this);

        butBoardSave = new Button("Save");
        butBoardSave.setActionCommand( "fileBoardSave" );
        butBoardSave.addActionListener(this);

        butBoardSaveAs = new Button("Save As...");
        butBoardSaveAs.setActionCommand( "fileBoardSaveAs" );
        butBoardSaveAs.addActionListener(this);
        
        panButtons = new Panel(new GridLayout(2, 2, 2, 2));
        panButtons.add(butBoardNew);
        panButtons.add(butBoardLoad);
        panButtons.add(butBoardSave);
        panButtons.add(butBoardSaveAs);
        
        blankL = new Label("", Label.CENTER);
        
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;    c.weighty = 0.0;
        c.insets = new Insets(4, 4, 1, 1);
        
        c.gridwidth = GridBagConstraints.REMAINDER; 
        addBag(canHex, gridbag, c);
        c.gridwidth = 1; 
        addBag(labElev, gridbag, c);
        addBag(butElevUp, gridbag, c);
        addBag(butElevDown, gridbag, c);
        c.gridwidth = GridBagConstraints.REMAINDER; 
        addBag(texElev, gridbag, c);
        
        addBag(labTerrain, gridbag, c);
        addBag(lisTerrain, gridbag, c);
        addBag(butDelTerrain, gridbag, c);
        addBag(panTerrainType, gridbag, c);
        addBag(panTerrExits, gridbag, c);
        addBag(panRoads, gridbag, c);
        addBag(labTheme, gridbag, c);
        addBag(texTheme, gridbag, c);
        addBag(butAddTerrain, gridbag, c);
        addBag(butMiniMap, gridbag, c);
    
        c.weightx = 1.0;    c.weighty = 1.0;
        addBag(blankL, gridbag, c);
        
        c.weightx = 1.0;    c.weighty = 0.0;
        addBag(labBoard, gridbag, c);
        addBag(panButtons, gridbag, c);

        minimapW = new Dialog(frame, "MiniMap", false);
        minimapW.setLocation(Settings.minimapPosX, Settings.minimapPosY);
        minimapW.setSize(Settings.minimapSizeWidth, Settings.minimapSizeHeight);
        minimap = new MiniMap(minimapW, game, bv);
        minimapW.addWindowListener(this);
        minimapW.add(minimap);

        setMapVisible(true);
    }
    
    private void addBag(Component comp, GridBagLayout gridbag, GridBagConstraints c) {
        gridbag.setConstraints(comp, c);
        add(comp);
        comp.addKeyListener(this);
    }
    
    /**
     * Apply the current Hex to the Board at the specified
     * location.
     */
    public void paintHex(Coords c) {
        board.setHex(c, (Hex)curHex.clone());
    }
    
    /**
     * Sets the current hex
     * 
     * @param hex            hex to set.
     */
    public void setCurrentHex(Hex hex) {
        curHex = (Hex)hex.clone();
        
        texElev.setText(Integer.toString(curHex.getElevation()));
            
        refreshTerrainList();
        
        if (lisTerrain.getItemCount() > 0) {
            lisTerrain.select(0);
            refreshTerrainFromList();
        }
        
        texTheme.setText(curHex.getTheme());
        repaint();
        canHex.repaint();
    }
    
    /**
     * Refreshes the terrain list to match the current hex
     */
    public void refreshTerrainList() {
        lisTerrain.removeAll();
        for (int i = 0; i < Terrain.SIZE; i++) {
            Terrain terrain = curHex.getTerrain(i);
            if (terrain != null) {
                lisTerrain.add(terrain.toString());
            }
        }
    }
    
    /**
     * Returns a new instance of the terrain that is currently entered in the
     * terrain input fields
     */
    private Terrain enteredTerrain() {
        int type = Terrain.parse(choTerrainType.getSelectedItem());
        int level = Integer.parseInt(texTerrainLevel.getText());
        boolean exitsSpecified = cheTerrExitSpecified.getState();
        int exits = Integer.parseInt(texTerrExits.getText());
        return new Terrain(type, level, exitsSpecified, exits);
    }
    
    /**
     * Add or set the terrain to the list based on the fields.
     */
    private void addSetTerrain() {
        Terrain toAdd = enteredTerrain();
        curHex.addTerrain(toAdd);
        refreshTerrainList();
        canHex.repaint();
    }
    
    /**
     * Set all the appropriate terrain field to match the currently selected
     * terrain in the list.
     */
    private void refreshTerrainFromList() {
        Terrain terrain = new Terrain(lisTerrain.getSelectedItem());
        terrain = curHex.getTerrain(terrain.getType());

        choTerrainType.select(Terrain.getName(terrain.getType()));
        texTerrainLevel.setText(Integer.toString(terrain.getLevel()));
        cheTerrExitSpecified.setState(terrain.hasExitsSpecified());
        texTerrExits.setText(Integer.toString(terrain.getExits()));
    }

    
    /**
     * Initialize a new data set in the current board.
     * 
     * If hexes are loaded, brings up a dialog box requesting
     * width and height and default hex.  If height and width
     * are valid, creates new board data and fills it with the
     * selected hex.
     */
    public void boardNew() {
        // display new board dialog
        BoardNewDialog bnd = new BoardNewDialog(frame, lisTerrain.getItems(), lisTerrain.getSelectedIndex());
        bnd.setLocation(frame.getLocation().x + 150, frame.getLocation().y + 100);
        bnd.show();
        
        if(bnd.getX() > 0 || bnd.getY() > 0) {
            Hex[] newHexes = new Hex[ bnd.getX() * bnd.getY() ]; 
            for(int i = 0; i < newHexes.length; i++) { 
                newHexes[i] = new Hex(); 
            }
            board.newData(bnd.getX(), bnd.getY(), newHexes); 
            curpath = null;
            curfile = null;
            frame.setTitle("MegaMek Board Editor : Unnamed");
            menuBar.setBoard( true );
        }
    }
    
    public void boardLoad() {
        FileDialog fd = new FileDialog(frame, "Load Board...", FileDialog.LOAD);
        fd.setDirectory("data" + File.separator + "boards");
        fd.setLocation(frame.getLocation().x + 150, frame.getLocation().y + 100);
        fd.show();
        
        if(fd.getFile() == null) {
            // I want a file, y'know!
            return;
        }
        curpath = fd.getDirectory();
        curfile = fd.getFile();
        // load!
        try {
            InputStream is = new FileInputStream(new File(curpath, curfile));
            // tell the board to load!
            board.load(is);
            // okay, done!
            is.close();
            menuBar.setBoard( true );
        } catch(IOException ex) {
            System.err.println("error opening file to save!");
            System.err.println(ex);
        }
        
        frame.setTitle("MegaMek Board Editor : " + curfile);

        cheRoadsAutoExit.setState( board.getRoadsAutoExit() );

        refreshTerrainList();
    }

    /**
     * Checks to see if there is already a path and name
     * stored; if not, calls "save as"; otherwise, saves 
     * the board to the specified file.
     */
    public void boardSave() {
        if(curfile == null) {
            boardSaveAs();
            return;
        }
        // save!
        try {
            OutputStream os = new FileOutputStream(new File(curpath, curfile));
            // tell the board to save!
            board.save(os);
            // okay, done!
            os.close();
        } catch(IOException ex) {
            System.err.println("error opening file to save!");
            System.err.println(ex);
        }
    }
    
    /**
     * Opens a file dialog box to select a file to save as;
     * saves the board to the file.
     */
    public void boardSaveAs() {
        FileDialog fd = new FileDialog(frame, "Save Board As...", FileDialog.SAVE);
        fd.setDirectory("data" + File.separator + "boards");
        fd.setLocation(frame.getLocation().x + 150, frame.getLocation().y + 100);
        fd.show();
        
        if(fd.getFile() == null) {
            // I want a file, y'know!
            return;
        }
        curpath = fd.getDirectory();
        curfile = fd.getFile();

        // make sure the file ends in board
        if (!curfile.toLowerCase().endsWith(".board")) {
            curfile += ".board";
        }
        
        frame.setTitle("MegaMek Board Editor : " + curfile);
        
        boardSave();
    }
    
    
    //
    // BoardListener
    //
    public void boardHexMoused(BoardEvent b) {
        board.cursor(b.getCoords());
        if(altheld) {
            setCurrentHex(board.getHex(b.getCoords()));
//            board.highlight(b.getCoords());
        }
        if(ctrlheld) {
            if(!board.getHex(b.getCoords()).equals(curHex)) {
                paintHex(b.getCoords());
            }
        }
    }
    public void boardHexSelected(BoardEvent b) {
        ;
    }
    public void boardHexCursor(BoardEvent b) {
        ;
    }
    public void boardHexHighlighted(BoardEvent b) {
        ;
    }
    public void boardChangedHex(BoardEvent b) {
        ;
    }
    public void boardChangedEntity(BoardEvent b) {
        ;
    }
    public void boardNewEntities(BoardEvent b) {
        ;
    }
    public void boardNewVis(BoardEvent b) {
        ;
    }
    public void boardNewBoard(BoardEvent b) {
        ;
    }
    public void boardFirstLOSHex(BoardEvent b) {
        ;
    }
    public void boardSecondLOSHex(BoardEvent b, Coords c) {
        ;
    }
    
    //
    // ItemListener
    //
    public void itemStateChanged(ItemEvent ie) {
        if (ie.getSource() == lisTerrain) {
            refreshTerrainFromList();
        }
        else if ( ie.getSource() == cheRoadsAutoExit ) {
            // Set the new value for the option, and refrest the board.
            board.setRoadsAutoExit( cheRoadsAutoExit.getState() );
            board.newData( board );
            canHex.repaint();
        }
    }
    
    //
    // TextListener
    //
    public void textValueChanged(TextEvent te) {
        if (te.getSource() == texElev) {
            int value;
            try {
                value = Integer.parseInt(texElev.getText());
            } catch (NumberFormatException ex) {
                return;
            }
            if (value != curHex.getElevation()) {
                curHex.setElevation(value);
                canHex.repaint();
            }
        } else if (te.getSource() == texTheme) {
            curHex.setTheme(texTheme.getText());
            canHex.repaint();
        }
    }
    
    //
    // KeyListener
    //
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
        case KeyEvent.VK_CONTROL : 
            paintHex(board.lastCursor);
            ctrlheld = true;
            break;
        case KeyEvent.VK_ALT : 
            setCurrentHex(board.getHex(board.lastCursor));
            altheld = true;
            break;
        }
    }
    public void keyReleased(KeyEvent ke) {
        switch(ke.getKeyCode()) {
        case KeyEvent.VK_CONTROL : 
            ctrlheld = false; 
            break;
        case KeyEvent.VK_ALT : 
            altheld = false; 
            break;
        }
    }
    public void keyTyped(KeyEvent ke) {
        ;
    }

    /**
     * Called when the user selects the "Help->About" menu item.
     */
    private void showAbout() {
        // Do we need to create the "about" dialog?
        if ( this.about == null ) {
            this.about = new CommonAboutDialog( this.frame );
        }

        // Show the about dialog.
        this.about.show();
    }

    /**
     * Called when the user selects the "Help->Contents" menu item.
     */
    private void showHelp() {
        // Do we need to create the "help" dialog?
        if ( this.help == null ) {
            File helpfile = new File( "editor-readme.txt" );
            this.help = new CommonHelpDialog( this.frame, helpfile );
        }

        // Show the help dialog.
        this.help.show();
    }

    /**
     * Called when the user selects the "View->Client Settings" menu item.
     */
    private void showSettings() {
        // Do we need to create the "settings" dialog?
        if ( this.setdlg == null ) {
            this.setdlg = new CommonSettingsDialog( this.frame );
        }

        // Show the settings dialog.
        this.setdlg.show();
    }

    //
    // ActionListener
    //
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equalsIgnoreCase("fileBoardNew")) {
            boardNew();
        } else if (ae.getActionCommand().equalsIgnoreCase("fileBoardOpen")) {
            boardLoad();                                            
        } else if (ae.getActionCommand().equalsIgnoreCase("fileBoardSave")) {
            boardSave();                                            
        } else if (ae.getActionCommand().equalsIgnoreCase("fileBoardSaveAs")) {
            boardSaveAs();
        } else if (ae.getSource() == butDelTerrain && lisTerrain.getSelectedItem() != null) {
            Terrain toRemove = new Terrain(lisTerrain.getSelectedItem());
            curHex.removeTerrain(toRemove.getType());
            refreshTerrainList();
            canHex.repaint();
        } else if (ae.getSource() == butAddTerrain) {
            addSetTerrain();
        } else if (ae.getSource() == butElevUp && curHex.getElevation() < 9) {
            curHex.setElevation(curHex.getElevation() + 1);
            texElev.setText(Integer.toString(curHex.getElevation()));
            canHex.repaint();
        } else if (ae.getSource() == butElevDown && curHex.getElevation() > -5) {
            curHex.setElevation(curHex.getElevation() - 1);
            texElev.setText(Integer.toString(curHex.getElevation()));
            canHex.repaint();
        } else if (ae.getSource() == butTerrExits) {
            ExitsDialog ed = new ExitsDialog(frame);
            cheTerrExitSpecified.setState(true);
            ed.setExits(Integer.parseInt(texTerrExits.getText()));
            ed.show();
            texTerrExits.setText(Integer.toString(ed.getExits()));
            addSetTerrain();
        } else if (ae.getActionCommand().equalsIgnoreCase("viewMiniMap")) {
            toggleMap();
        } else if (ae.getActionCommand().equalsIgnoreCase("helpAbout")) {
            showAbout();
        } else if (ae.getActionCommand().equalsIgnoreCase("helpContents")) {
            showHelp();
        } else if (ae.getActionCommand().equalsIgnoreCase("viewClientSettings")) {
            showSettings();
        }
    }

    
    
    /**
     * Displays the currently selected hex picture, in 
     * component form
     */
    private class HexCanvas extends Canvas {
        public HexCanvas() {
            super();
            setSize(72, 72);
        }
        
        public void paint(Graphics g) {
            update(g);
        }
        
        public void update(Graphics g) {
            if(curHex != null) {
                g.drawImage(bv.baseFor(curHex), 0, 0, this);
                if (bv.supersFor(curHex) != null) {
                    for (Iterator i = bv.supersFor(curHex).iterator(); i.hasNext();) {
                        g.drawImage((Image)i.next(), 0, 0, this);
                        g.drawString("SUPER", 0, 10);
                    }
                }
                g.setFont(new Font("SansSerif", Font.PLAIN, 9));
                g.drawString("LEVEL " + curHex.getElevation(), 24, 70);
            } else {
                g.clearRect(0, 0, 72, 72);
            }
        }
    }
    /**
     * @return the frame this is displayed in
     */
    public Frame getFrame() {
        return frame;
    }

    /** Toggles the minimap window
         Also, toggles the minimap enabled setting
     */
    public void toggleMap() {
        setMapVisible(!minimapW.isVisible());
    }    

    public void setMapVisible(boolean visible) {
        minimapW.setVisible(visible);
    }
    
    //
    // WindowListener
    //
    public void windowActivated(java.awt.event.WindowEvent windowEvent) {
    }    
    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
    }    
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    }    
    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {
    }    
    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {
    }    
    public void windowIconified(java.awt.event.WindowEvent windowEvent) {
    }    
    public void windowOpened(java.awt.event.WindowEvent windowEvent) {
    }
}

/**
 * a quick class for the new map diaglogue box
 */
class BoardNewDialog extends Dialog implements ActionListener {
    public int            xvalue, yvalue;
    
    protected Label        labWidth, labHeight;
    protected TextField    texWidth, texHeight;
    protected Button        butOkay, butCancel;
    
    public BoardNewDialog(Frame frame, String[] hexList, int hexSelected) {
        super(frame, "Set Dimensions", true);
        
        xvalue = 0;
        yvalue = 0;
        
        labWidth = new Label("Width:", Label.RIGHT);
        labHeight = new Label("Height:", Label.RIGHT);
        
        texWidth = new TextField("16", 2);
        texHeight = new TextField("17", 2);
        
        butOkay = new Button("Okay");
        butOkay.setActionCommand("done");
        butOkay.addActionListener(this);
        butOkay.setSize(80, 24);

        butCancel = new Button("Cancel");
        butCancel.setActionCommand("cancel");
        butCancel.addActionListener(this);
        butCancel.setSize(80, 24);

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(gridbag);

        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;    c.weighty = 0.0;
        c.insets = new Insets(5, 5, 1, 1);
        
        gridbag.setConstraints(labWidth, c);
        add(labWidth);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(texWidth, c);
        add(texWidth);
        
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(labHeight, c);
        add(labHeight);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(texHeight, c);
        add(texHeight);
        
        c.ipadx = 20;    c.ipady = 5;
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(butOkay, c);
        add(butOkay);
        
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(butCancel, c);
        add(butCancel);
        
        pack();
        setResizable(false);
        setLocation(frame.getLocation().x + frame.getSize().width/2 - getSize().width/2,
                    frame.getLocation().y + frame.getSize().height/2 - getSize().height/2);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == butOkay) {
            try {
                xvalue = Integer.decode(texWidth.getText()).intValue();
                yvalue = Integer.decode(texHeight.getText()).intValue();
            } catch(NumberFormatException ex) {
                System.err.println(ex.getMessage());
            }
            setVisible(false);
        } else if (e.getSource() == butCancel) {
            setVisible(false);
        }
    }
    
    public int getX() {
        return xvalue;
    }
    
    public int getY() {
        return yvalue;
    }
}

/**
 * A dialog of which exits are connected for terrain.
 */
class ExitsDialog extends Dialog implements ActionListener
{
    private Checkbox    cheExit0 = new Checkbox("0");
    private Checkbox    cheExit1 = new Checkbox("1");
    private Checkbox    cheExit2 = new Checkbox("2");
    private Checkbox    cheExit3 = new Checkbox("3");
    private Checkbox    cheExit4 = new Checkbox("4");
    private Checkbox    cheExit5 = new Checkbox("5");
    
    private Label       labBlank = new Label("");
    
    private Panel       panNorth = new Panel(new GridBagLayout());
    private Panel       panSouth = new Panel(new GridBagLayout());
    private Panel       panWest = new Panel(new BorderLayout());
    private Panel       panEast = new Panel(new BorderLayout());
    
    private Panel       panExits = new Panel(new BorderLayout());
    
    private Button      butDone = new Button("Done");
    
    public ExitsDialog(Frame frame) {
        super(frame, "Set Exits", true);
        setResizable(false);
        
        butDone.addActionListener(this);
        
        panNorth.add(cheExit0);
        panSouth.add(cheExit3);
        
        panWest.add(cheExit5, BorderLayout.NORTH);
        panWest.add(cheExit4, BorderLayout.SOUTH);
        
        panEast.add(cheExit1, BorderLayout.NORTH);
        panEast.add(cheExit2, BorderLayout.SOUTH);
        
        panExits.add(panNorth, BorderLayout.NORTH);
        panExits.add(panWest, BorderLayout.WEST);
        panExits.add(labBlank, BorderLayout.CENTER);
        panExits.add(panEast, BorderLayout.EAST);
        panExits.add(panSouth, BorderLayout.SOUTH);
        
        setLayout(new BorderLayout());
        
        add(panExits, BorderLayout.CENTER);
        add(butDone, BorderLayout.SOUTH);
        
        pack();
        setLocation(frame.getLocation().x + frame.getSize().width/2 - getSize().width/2,
                    frame.getLocation().y + frame.getSize().height/2 - getSize().height/2);
    }
    
    public void setExits(int exits) {
        cheExit0.setState((exits & 1) != 0); 
        cheExit1.setState((exits & 2) != 0); 
        cheExit2.setState((exits & 4) != 0); 
        cheExit3.setState((exits & 8) != 0); 
        cheExit4.setState((exits & 16) != 0); 
        cheExit5.setState((exits & 32) != 0); 
    }
    
    public int getExits() {
        int exits = 0;
        exits |= cheExit0.getState() ? 1 : 0;
        exits |= cheExit1.getState() ? 2 : 0;
        exits |= cheExit2.getState() ? 4 : 0;
        exits |= cheExit3.getState() ? 8 : 0;
        exits |= cheExit4.getState() ? 16 : 0;
        exits |= cheExit5.getState() ? 32 : 0;
        return exits;
    }
    
    public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
        setVisible(false);
    }    
}
