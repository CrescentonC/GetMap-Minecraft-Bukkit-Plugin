import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.block.Block;

public class PlayerListener implements Listener {
	private final int RADIUS = 5;
    private final GetMap plugin;

    public PlayerListener(GetMap instance) {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (this.plugin.isRecording()) {
//            Location from = event.getFrom();
            Block to = event.getTo().getBlock();

            this.plugin.addBlocksAround(to);
//            plugin.getLogger().info(String.format("From %.2f,%.2f,%.2f to %.2f,%.2f,%.2f", from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ()));
        }
    }
}