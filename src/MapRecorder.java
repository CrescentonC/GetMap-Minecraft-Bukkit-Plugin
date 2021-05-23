import java.util.Map;
import java.util.HashMap;
import org.bukkit.block.Block;
import org.bukkit.Location;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class MapRecorder {
	private Map<Integer[], Block> blocks = new HashMap<Integer[], Block>();
	private String startTime;
	private String player;
	
	public MapRecorder() {
		startTime = (new Date()).toString();
	}
	
	public void add(Integer[] cord, Block b) {
		if (blocks.containsKey(cord)) return;
		blocks.put(cord, b);
	}
	
	public void outputMap(String player) {
		String endTime = (new Date()).toString();
		try {
			FileWriter fw = new FileWriter(player + "__" + this.startTime + "__" + endTime + " Map Records");
			for (Block b : blocks.values()) {
				fw.write(blockInfo(b));
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
	}
	
	public static String blockInfo(Block b) {
		if (b == null) return null;
		return "[" + b.getX() + ", " + b.getY() + ", " + b.getZ() + "]: " + b.getType().toString() + "\n";
	}

}
