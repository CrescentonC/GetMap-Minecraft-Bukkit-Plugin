import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.block.Block;

public class PlayerListener implements Listener {
	private final int RADIUS = 5;
    private final GetMap plugin;

    public PlayerListener(GetMap instance) {
        plugin = instance;
    }

    @EventHandler
    public synchronized void onPlayerMove(PlayerMoveEvent event) {
        if (this.plugin.isRecording()) {
            Block from = event.getFrom().getBlock();
            Block to = event.getTo().getBlock();

            this.plugin.addBlocksAround(to);
            plugin.getLogger().info(String.format("From %d,%d,%d to %d,%d,%d", from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ()));
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
    	if (this.plugin.isRecording()) {
    		this.plugin.addBlockModify(event.getBlock(), "Break");
    	}
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
    	if (this.plugin.isRecording()) {
    		this.plugin.addBlockModify(event.getBlock(), "Place");
    	}
    }
}