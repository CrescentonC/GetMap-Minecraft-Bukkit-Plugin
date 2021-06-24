# GetMap

A bukkit Minecraft server plugin developed to record the map around and the block place/break operations done by the player during a period specified by the player. To be used by the BEE synthesizer.

How to setup a eclipse project and import the project to modify and build: https://www.justdave.net/dave/2015/05/04/how-to-write-a-minecraftbukkit-plugin-for-spigot-1-8/

There is a usable `.jar` file in the `target` folder for Minecraft server version 1.12.2

## Usage

After placing the plugin `.jar` file to the `plugin` folder of a bukkit server, launch the server once and a file name `synth_config.txt` will show up in the directory where you ran the server (also the root directory where all the server related files are generated and located). And add two lines without any blank line in between to the file `synth_config.txt`. 

Where the first line is the script to run the python script provided by the BEE framework, with the path to the files to be used as examples replaced by two `%s`; The second line is the root folder to your minecraft server.

**Example** (I use WSL so the first line might look a little bit wired):

```
bash -c "python3 /root/code/AutoProgramSynth/ENTER/interfaces/python/minecraft/minecraft_call_c.py %s %s"
/mnt/c/Users/Cresc/Software/minecraftServer_1.12.2/
```

---

## Minecraft related

### Get the game

1. Setup java 1.8, this version is recommended though some other version might work

2. By using some third-party launcher. 
   
   This project is tested on the game launched by [HMCL launcher](https://hmcl.huangyuhui.net/), but other launchers should be fine.

### Set up the Minecraft Server with Plugins Enabled

1. Get the Minecraft server itself. The official page seems only provide the latest version, but some websites like [this](https://mcversions.net/) provide the `server.jar` of all the history versions.

2. Get the `craftbukkit.jar` file which enables the plugins to be run on the server. There is no official release of this file, there are some third-party websites providing the file like [this](https://getbukkit.org/download/craftbukkit), but it can also be compiled [from the source](https://www.spigotmc.org/wiki/buildtools/) (in fact, if you want to build this plugin your self, the build tools you will need will include this `craftbukkit.jar` file. See the above [tutorial](https://www.justdave.net/dave/2015/05/04/how-to-write-a-minecraftbukkit-plugin-for-spigot-1-8/) on how to set up the build tools and start the plugin develop project)

3. Put the `server.jar` and the `craftbukkit.jar` into one folder, and inside that folder, run `java -jar ./craftbukkit.jar`. In some newer version of Minecraft, you need to deal with the EULA when first launch the server, follow the prompt to proceed.

4. To enable localhost to get running smoothly, find file `server.properties` in the root directory of the game and find the entry `online-mode`, make sure the value is set to `false`, e.g. `online-mode=false`.

5. Enter into the Minecraft game, selet Multiplayer. If the server did not show up automatically, click Add Server, give the server an arbitrary name and the fill in the server address to be `localhost:<port>`, the port is usually `25565` and you can find it in the startup log information when you start the server.



