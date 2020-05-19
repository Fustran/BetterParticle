package net.cobaltium.betterparticle.data;

import net.cobaltium.betterparticle.util.Util;

import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class ParticleLoc {
    private ArrayList<Range> loc;
    private World world;

    //basically the same as Bukkit.Location, but with support for value ranges instead of just a single position.
    public ParticleLoc(CommandSender sender, String[] pos) {
        world = Util.GetSenderWorld(sender);
        loc = new ArrayList<>();
        //add a range for X, Y, and Z
        for (int i = 0; i < 3; i++) {
            loc.add(new Range(sender, pos[i], i));
        }
    }
    public Double GetX() {
       return loc.get(0).GetVal();
    }

    public Double GetY() {
        return loc.get(1).GetVal();
    }

    public Double GetZ() {
        return loc.get(2).GetVal();
    }

    public World GetWorld() {
        return world;
    }
}
