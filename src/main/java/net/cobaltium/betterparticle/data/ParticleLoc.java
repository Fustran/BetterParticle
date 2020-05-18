package net.cobaltium.betterparticle.data;


import com.sun.istack.internal.Nullable;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParticleLoc {
    private ArrayList<Double[]> loc;
    private World world;

    private Pattern simpleRangeCheck = Pattern.compile("^[\\d\\.~-]+,[\\d\\.~-]+$");
    private Pattern deepRangeCheck = Pattern.compile("^~?-?\\d*\\.?\\d*,~?-?\\d*\\.?\\d*$");
    private Pattern singleCheck = Pattern.compile("^[-~\\d\\.]+$");

    public ParticleLoc(CommandSender sender, String[] pos) {
        world = GetWorld(sender);
        loc = new ArrayList<>();
        //loop through provided X, Y, and Z and convert ranges to actual values
        for (int i = 0; i < 3; i++) {
            if (IsRangePos(pos[i])) {
                //split range values
                String[] splitPos = pos[i].split(",");
                loc.add(new Double[]{GetRelativePos(sender, splitPos[0], i), GetRelativePos(sender, splitPos[1], i)});
            } else if (IsSinglePos(pos[i])) {
                loc.add(new Double[]{GetRelativePos(sender, pos[i], i)});
            }
        }
    }

    //if the position is a range, we return a random value within its bounds
    public Double GetX() {
        Double[] pos = loc.get(0);
        if (pos.length > 1) {
            Double range = pos[0] - pos[1];
            return (Math.random() * range) + pos[1];
        } else {
            return pos[0];
        }
    }

    public Double GetY() {
        Double[] pos = loc.get(1);
        if (pos.length > 1) {
            Double range = pos[0] - pos[1];
            return (Math.random() * range) + pos[1];
        } else {
            return pos[0];
        }
    }

    public Double GetZ() {
        Double[] pos = loc.get(2);
        if (pos.length > 1) {
            Double range = pos[0] - pos[1];
            return (Math.random() * range) + pos[1];
        } else {
            return pos[0];
        }
    }

    //not sure if I need these yet
    public boolean IsXRange() {
        return loc.get(0).length > 1;
    }

    public boolean IsYRange() {
        return loc.get(1).length > 1;
    }

    public boolean IsZRange() {
        return loc.get(2).length > 1;
    }

    //converts a relative position like '~-1' to an absolute position
    private Double GetRelativePos(CommandSender sender, String input, int targetAxis) {
        Location loc = new Location(GetWorld(sender), 0d, 0d, 0d);
        if (sender instanceof Player) {
            Player player = (Player)sender;
            loc = player.getLocation();
        } else if (sender instanceof BlockCommandSender) {
            BlockCommandSender cmdBlock = (BlockCommandSender)sender;
            loc = cmdBlock.getBlock().getLocation();
        }
        Double[] coords = {loc.getX(), loc.getY(), loc.getZ()};
        if (input.contains("~")) {
            if (input.length() > 1) {
                Double modifier = Double.parseDouble(input.substring(1));
                return coords[targetAxis] + modifier;
            } else {
                return coords[targetAxis];
            }
        }
        return Double.parseDouble(input);
    }

    //gets the sender's world
    @Nullable
    private World GetWorld(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            return player.getWorld();
        } else if (sender instanceof BlockCommandSender) {
            BlockCommandSender cmdBlock = (BlockCommandSender)sender;
            cmdBlock.getBlock().getWorld();
        }
        return null;
    }

    //checks if supplied value matches a value range eg. '1,-1'
    private boolean IsRangePos(String input) {
        /*we have two checks because of the nature of ranges. due to MC coordinates using odd characters like tilde or minus that are optional,
        we have to do a basic syntax check (matches 'val,val') before a more comprehensive check, as regex can't contain conditional logic. */
        Matcher simpleMatch = simpleRangeCheck.matcher(input);
        if (simpleMatch.find()) {
            Matcher deepMatch = deepRangeCheck.matcher(input);
            if (deepMatch.find()) {
                return true;
            }
        }
        return false;
    }

    //checks if supplied value matches a single position eg. '100'
    private boolean IsSinglePos(String input) {
        Matcher singleMatch = singleCheck.matcher(input);
        if (singleMatch.find()) {
            return true;
        }
        return false;
    }
}
