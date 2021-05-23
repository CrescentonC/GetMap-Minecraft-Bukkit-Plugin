import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.Material;
import java.io.File;

public class GetMap extends JavaPlugin {
	private final PlayerListener listener = new PlayerListener(this);

	@Override
    public void onEnable() {
		this.getCommand("startRec").setExecutor(new StartRecCommand(this));
        this.getCommand("endRec").setExecutor(new EndRecCommand(this));
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(listener, this);
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
    		temp.outputMap();
    		temp.outputBlockModEvents();
    		return true;
    	}
    	return false;
    }
    
    public static final int RADIUS = 6; // seems that player cannot reach outside 6 blocks away
    public void addBlocksAround(Block b) {
    	for (int x = RADIUS; x >= -RADIUS; x--) {
            for (int y = RADIUS; y >= -RADIUS; y--) {
                for (int z = RADIUS; z >= -RADIUS; z--) {
                	Block br = b.getRelative(x, y, z);
                	this.logger.add(new Integer[] {br.getX(), br.getY(), br.getZ()}, br);
//                	this.getLogger().info(br.getType().toString() + "," + br.getX() + "," + br.getY() + "," + br.getZ());
                }
            }
    	}
    }
    
    public void addBlockModify(Block b, String modType) {
    	this.logger.addBlockModEvents(modType + "," + b.getType().toString() + "," + b.getX() + "," + b.getY() + "," + b.getZ());
    }
    
}

