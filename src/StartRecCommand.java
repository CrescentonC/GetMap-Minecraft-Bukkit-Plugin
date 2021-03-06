//import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;

public class StartRecCommand implements CommandExecutor {
	
	private final GetMap plugin;
	
	public StartRecCommand(GetMap p) {
		this.plugin = p;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] split) {
		return this.plugin.startRecording(sender.getName());
	}
}
