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
		this.getCommand("startRecMap").setExecutor(new StartRecCommand(this));
        this.getCommand("endRecMap").setExecutor(new EndRecCommand(this));
        
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(listener, this);
	}
	
    @Override
    public void onDisable() {}
    
    private MapRecorder mapRec = null;
    public boolean isRecording() {
    	return mapRec != null ;
    }
    public boolean startRecording() {
    	if(!isRecording()) {
    		mapRec = new MapRecorder();
    	}
    	return false;
    }
    
    public boolean endRecording(String name) {
    	if (isRecording()) {
    		MapRecorder temp = this.mapRec;
    		this.mapRec = null;
    		temp.outputMap(name);
    		return true;
    	}
    	return false;
    }
    
    public static final int RADIUS = 2;
    public void addBlocksAround(Block b) {
    	for (int x = RADIUS; x >= -RADIUS; x--) {
            for (int y = RADIUS; y >= -RADIUS; y--) {
                for (int z = RADIUS; z >= -RADIUS; z--) {
                	Block br = b.getRelative(x, y, z);
                	if (br.getType() != Material.AIR) {                		
                		this.mapRec.add(new Integer[] {br.getX(), br.getY(), br.getZ()}, br);
                	}
                }
            }
    	}
    }
    
}

