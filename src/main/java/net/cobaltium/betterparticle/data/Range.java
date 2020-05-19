package net.cobaltium.betterparticle.data;

import com.sun.istack.internal.Nullable;
import net.cobaltium.betterparticle.util.Util;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//a class that can hold either a data range (10,-10) or a single value. Optional targetAxis param in case the range contains tildes that need conversion.
public class Range {
    Double[] pos;

    private Pattern simpleRangeCheck = Pattern.compile("^[\\d\\.~-]+,[\\d\\.~-]+$");
    private Pattern deepRangeCheck = Pattern.compile("^~?-?\\d*\\.?\\d*,~?-?\\d*\\.?\\d*$");
    private Pattern singleCheck = Pattern.compile("^[-~\\d\\.]+$");

    public Range (CommandSender sender, String input, @Nullable Integer targetAxis) {
        if (IsRangePos(input)) {
            //split range values
            String[] splitPos = input.split(",");
            pos = new Double[]{GetRelativePos(sender, splitPos[0], targetAxis), GetRelativePos(sender, splitPos[1], targetAxis)};
        } else if (IsSinglePos(input)) {
            pos = new Double[]{GetRelativePos(sender, input, targetAxis)};
        }
    }

    //if the position is a range, we return a random value within its bounds
    public Double GetVal() {
        if (pos.length > 1) {
            Double range = pos[0] - pos[1];
            return (Math.random() * range) + pos[1];
        } else {
            return pos[0];
        }
    }

    //converts a relative position like '~-1' to an absolute position
    private Double GetRelativePos(CommandSender sender, String input, Integer targetAxis) {
        Location loc = new Location(Util.GetSenderWorld(sender), 0d, 0d, 0d);
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
