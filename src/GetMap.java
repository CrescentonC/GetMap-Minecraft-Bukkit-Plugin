import org.bukkit.plugin.java.JavaPlugin;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
//import org.bukkit.Location;
import org.bukkit.block.Block;
//import org.bukkit.Material;
import java.io.File;

import java.lang.Runtime;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Scanner;

public class GetMap extends JavaPlugin {
	private final PlayerListener listener = new PlayerListener(this);

	
	@Override
    public void onEnable() {
		this.getCommand("startRec").setExecutor(new StartRecCommand(this));
        this.getCommand("endRec").setExecutor(new EndRecCommand(this));
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(listener, this);
        
        
        try {
        	File f = new File("synth_config.txt");
            if (!f.exists()) {
           	this.getLogger().info("config file does not exist, creating one");
           	f.createNewFile();
            } else {}
        } catch (Exception e) {
        	this.getLogger().info("Something wrong creating config file");
        	e.printStackTrace();
        }
	}
	
    @Override
    public void onDisable() {}
    
    private Logger logger = null;
    public boolean isRecording() {
    	return logger != null ;
    }
    public boolean startRecording(String name) {
    	if(!isRecording()) {
    		logger = new Logger(name);
    	}
    	return false;
    }
    
    public boolean endRecording() {
    	if (isRecording()) {
    		Logger temp = this.logger;
    		this.logger = null;
    		String prevMapFileName = temp.outputMap();
    		String opFileName = temp.outputBlockModEvents();
    		this.runSynthesizer(prevMapFileName, opFileName);
    		return true;
    	}
    	return false;
    }
    
    private static String pythonScriptPreFormatted = "";
    private static String logFileOutputPath = ""; // NOTE should end with '/'
    public void runSynthesizer(String prevMapFileName, String opFileName) {
    	// read the config file
    	try {
    		 File f = new File("synth_config.txt");
             if (!f.exists()) {
            	 this.getLogger().info("config file does not exist, creating one");
            	 f.createNewFile();
             } else {}
             Scanner scanner = new Scanner(f);
             
             pythonScriptPreFormatted = scanner.nextLine();
             logFileOutputPath = scanner.nextLine();
             scanner.close();
    	} catch (Exception e) {
    		this.getLogger().info("Config File error:\n First line: python script preformatted with %s \n Second line: the directory in your machine where the log file is output");
    		e.printStackTrace();
    	}
    	
    	// composing the command and run
    	try {
    		String finalCommand = String.format(pythonScriptPreFormatted,
    				"'" + logFileOutputPath + prevMapFileName + "'", 
    				"'" + logFileOutputPath + opFileName + "'");
    		this.getLogger().info(finalCommand);
    		Process p = Runtime.getRuntime().exec(finalCommand);
    		
    		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String s;
    		while ((s = br.readLine()) != null)
    			this.getLogger().info("line: " + s);
            p.waitFor();
            this.getLogger().info("exit: " + p.exitValue());
            p.destroy();
            
    	} catch (Exception e) {
    		this.getLogger().info("command malfomated, please check config");
    		e.printStackTrace();
    	}
    }
    
    public static final int RADIUS = 6; // seems that player cannot reach outside 6 blocks away
    public void addBlocksAround(Block b) {
    	for (int x = RADIUS; x >= -RADIUS; x--) {
            for (int y = RADIUS; y >= -RADIUS; y--) {
                for (int z = RADIUS; z >= -RADIUS; z--) {
                	Block br = b.getRelative(x, y, z);
                	this.logger.add(new Integer[] {br.getX(), br.getY(), br.getZ()}, br);
                	// this.getLogger().info(br.getType().toString() + "," + br.getX() + "," + br.getY() + "," + br.getZ());
                }
            }
    	}
    }
    
    public void addBlockModify(Block b, String modType) {
    	this.logger.addBlockModEvents(modType + "," + b.getType().toString() + "," + b.getX() + "," + b.getY() + "," + b.getZ());
    }
    
}

