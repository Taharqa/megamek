package megamek.common;

import java.io.*;
import com.sun.java.util.collections.*;

/*
 * Setting this up as static so a client and server running in the same
 * process can share it
 */

public class MechSummaryCache
{
    private static MechSummaryCache m_instance = null;
    
    public static synchronized MechSummaryCache getInstance()
    {
        if (m_instance == null) {
            m_instance = new MechSummaryCache();
        }
        return m_instance;
    }
    
    private MechSummary[] m_data;
    private Map m_refMap;
    private static final char SEPARATOR = '|';
    private static final File ROOT = new File(Settings.mechDirectory);
    private static final File CACHE = new File(ROOT, "mechcache.txt");
    
    private MechSummaryCache()
    {
        m_refMap = new Hashtable();
        loadMechData();
    }
    
    public MechSummary[] getAllMechs() { return m_data; }
    
    public MechSummary getMech(String sRef)
    {
        return (MechSummary)m_refMap.get(sRef);
    }
    
    private void loadMechData()
    {
        Vector vMechs = new Vector();
        Set sKnownFiles = new HashSet();
        long lLastCheck = 0;
        long lStart = System.currentTimeMillis();
        
        // check the cache
        try {
            if (CACHE.exists()) {
                System.out.println("Reading from mechcache file");
                lLastCheck = CACHE.lastModified();
                DataInputStream dis = new DataInputStream(new FileInputStream(CACHE));
                String s;
                while ((s = dis.readLine()) != null) {
                    MechSummary ms = new MechSummary();
                    // manually do a string tokenizer.  Much faster
                    int nIndex1 = s.indexOf(SEPARATOR);
                    ms.setRef(s.substring(0, nIndex1));
                    int nIndex2 = s.indexOf(SEPARATOR, nIndex1 + 1);
                    ms.setName(s.substring(nIndex1 + 1, nIndex2));
                    nIndex1 = nIndex2;
                    nIndex2 = s.indexOf(SEPARATOR, nIndex1 + 1);
                    ms.setSourceFile(new File(s.substring(nIndex1 + 1, nIndex2)));
                    nIndex1 = nIndex2;
                    nIndex2 = s.indexOf(SEPARATOR, nIndex1 + 1);
                    ms.setYear(Integer.parseInt(s.substring(nIndex1 + 1, nIndex2)));
                    nIndex1 = nIndex2;
                    nIndex2 = s.indexOf(SEPARATOR, nIndex1 + 1);
                    ms.setType(Integer.parseInt(s.substring(nIndex1 + 1, nIndex2)));
                    nIndex1 = nIndex2;
                    nIndex2 = s.indexOf(SEPARATOR, nIndex1 + 1);
                    ms.setTons(Integer.parseInt(s.substring(nIndex1 + 1, nIndex2)));
                    ms.setBV(Integer.parseInt(s.substring(nIndex2 + 1)));
                    vMechs.addElement(ms);
                    sKnownFiles.add(ms.getSourceFile().toString());
                }
            }
        }
        catch (Exception e) {
            System.out.println("Unable to load mechcache: " + e.getMessage());
            e.printStackTrace();
        }
        
        // load any changes since the last check time
        boolean bNeedsUpdate = loadMechsFromDirectory(vMechs, sKnownFiles, lLastCheck, ROOT);
        
        // convert to array
        m_data = new MechSummary[vMechs.size()];
        vMechs.copyInto(m_data);
        
        // store map references
        for (int x = 0; x < m_data.length; x++) {
            m_refMap.put(m_data[x].getRef(), m_data[x]);
        }
        
        // save updated cache back to disk
        if (bNeedsUpdate) {
            try {
                saveCache(lStart);
            } catch (Exception e) {
                System.out.println("Unable to save mech cache");
            }
        }
        
        System.out.println(m_data.length + " mechs loaded.");
    }
    
    private void saveCache(long lStart)
        throws Exception
    {
        System.out.println("Saving mechcache");
        FileWriter wr = new FileWriter(CACHE);
        for (int x = 0; x < m_data.length; x++) {
            wr.write(m_data[x].getRef() + SEPARATOR + 
                    m_data[x].getName() + SEPARATOR + 
                    m_data[x].getSourceFile().getPath() + SEPARATOR + 
                    m_data[x].getYear() + SEPARATOR +
                    m_data[x].getType() + SEPARATOR + 
                    m_data[x].getTons() + SEPARATOR + 
                    m_data[x].getBV() + "\r\n");
        }
        wr.flush();
        wr.close();
    }
    
    // Loading a complete mech object for each summary is a bear and should be 
    // changed, but it lets me use the existing parsers
    private boolean loadMechsFromDirectory(Vector vMechs, Set sKnownFiles, long lLastCheck, File fDir)
    {
        boolean bNeedsUpdate = false;
        System.out.println("Looking in " + fDir.getPath());
        String[] sa = fDir.list();
        for (int x = 0; x < sa.length; x++) {
            File f = new File(fDir, sa[x]);
            if (f.equals(CACHE)) { continue; }
            else if (f.isDirectory()) {
                 // recursion is fun
                if (loadMechsFromDirectory(vMechs, sKnownFiles, lLastCheck, f)) {
                    bNeedsUpdate = true;
                }
            }
            else {
                if (f.lastModified() > lLastCheck || !sKnownFiles.contains(f.toString())) {
                   try {
                       System.out.println("Loading from " + f);
                       MechFileParser mfp = new MechFileParser(f);
                       Entity m = mfp.getEntity();
                       MechSummary ms = new MechSummary();
                       ms.setName(m.getName());
                       ms.setRef(m.getModel());
                       ms.setSourceFile(f);
                       ms.setYear(m.getYear());
                       ms.setType(m.getTechLevel());
                       ms.setTons((int)m.getWeight());
                       ms.setBV(m.calculateBattleValue());
                       vMechs.addElement(ms);
                       sKnownFiles.add(f.toString());
                       bNeedsUpdate = true;
                   } catch (EntityLoadingException ex) {
                       System.err.println("couldn't load file " + f.getName() + " : " + ex.getMessage());
                       continue;
                   }
                }
            } 
        }
        
        return bNeedsUpdate;
    }
    
}