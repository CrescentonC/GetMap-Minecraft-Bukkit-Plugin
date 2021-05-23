import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndRecCommand implements CommandExecutor {
	
	private final GetMap plugin;
	
	public EndRecCommand(GetMap p) {
		this.plugin = p;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		this.plugin.endRecording(sender.getName());
		return true;
	}
}
