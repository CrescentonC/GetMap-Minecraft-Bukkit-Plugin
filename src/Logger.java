import java.util.Map;
import java.util.HashMap;
import org.bukkit.block.Block;
//import org.bukkit.Location;
//import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class Logger {
	private Map<ArrayList<Integer>, String> prev_blocks = new HashMap<ArrayList<Integer>, String>();
	private Map<ArrayList<Integer>, String> post_blocks = new HashMap<ArrayList<Integer>, String>();
	private String startTime = null;
	private String endTime = null;
	private String player = null;
	
	private static SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmss");
	public Logger(String name) {
		startTime = f.format(new Date());
		this.player = name;
	}
	
	public void add(Integer[] cord, Block b) {
		ArrayList<Integer> t = new ArrayList<Integer>(Arrays.asList(cord));
		if (!this.prev_blocks.containsKey(t)) {			
			this.prev_blocks.put(t, b.getType().toString());
		}
		this.post_blocks.put(t, b.getType().toString());
	}
	
	public void updateEndTime() {
		this.endTime = (this.endTime == null) ? f.format(new Date()) : this.endTime;
	}
	
	public String outputMap() {
		this.updateEndTime();
		try {
			String prevFileName = player + "__" + this.startTime + " to " + endTime + "__Previous Map Records.txt";
			FileWriter fw = new FileWriter(prevFileName);
			for (ArrayList<Integer> b : prev_blocks.keySet()) {
				if (this.prev_blocks.get(b).equals("AIR")) continue;
				fw.write(this.prev_blocks.get(b) + "," + b.get(0) + "," + b.get(1) + "," + b.get(2) + "\n");
			}
//			fw.write(this.prev_blocks.size());
			fw.close();
			
			fw = new FileWriter(player + "__" + this.startTime + " to " + endTime + "__Post Map Records.txt");
			for (ArrayList<Integer> b : post_blocks.keySet()) {
				if (this.prev_blocks.get(b).equals("AIR")) continue;
				fw.write(this.post_blocks.get(b) + "," + b.get(0) + "," + b.get(1) + "," + b.get(2) + "\n");
			}
			fw.close();
			
			return prevFileName;
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
		return null;
	}
	
	public static String blockInfo(Block b) {
		if (b == null) return null;
		return b.getType().toString() + "," + b.getX() + "," + b.getY() + "," + b.getZ() + "\n";
	}
	
	LinkedList<String> blockModEvents = new LinkedList<String>();
	public void addBlockModEvents(String s) {
		this.blockModEvents.addLast(s);
	}
	
	public String outputBlockModEvents() {
		this.updateEndTime();
		try {
			String fileName = player + "__" + this.startTime + " to " + endTime + "__Block Mod Events Records.txt";
			FileWriter fw = new FileWriter(fileName);
			for (String s : this.blockModEvents) {
				fw.write(s + "\n");
			}
			fw.close();
			
			return fileName;
		} catch (IOException e) {
			System.out.println("An error occurred.");
		    e.printStackTrace();
		}
		
		return null;
	}

}
