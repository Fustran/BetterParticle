package net.cobaltium.betterparticle.commands.bp;
import net.cobaltium.betterparticle.data.NoteColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import net.cobaltium.betterparticle.util.Util;

import java.util.Arrays;
import java.util.List;

public class BPComplete implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        String[] res = {};
        int extraVarsState = 0;
        int length = args.length;
        if (length == 1) {
            return Arrays.asList(Util.GetEnumNames(Particle.class));
        } else {
            //here we go through edge case particles that need extra arguments and push the normal args forward to accommodate
            switch (args[0]) {
                case "REDSTONE":
                    extraVarsState = 1;
                    length -= 4;
                    break;
                case "SPELL_MOB":
                case "SPELL_MOB_AMBIENT":
                    extraVarsState = 2;
                    length -= 4;
                    break;
                case "NOTE":
                    extraVarsState = 3;
                    length -= 1;
                    break;
                case "ITEM_CRACK":
                case "BLOCK_CRACK":
                case "BLOCK_DUST":
                case "FALLING_DUST":
                    extraVarsState = 4;
                    length -= 1;
                    break;
            }
        }
        switch (length) {
            case -2:
                res = new String[]{"0", "255", "0 0", "255 255", "0 0 0", "255 255 255"};
                break;
            case -1:
                res = new String[]{"0", "255", "0 0", "255 255"};
                break;
            case 0:
                res = new String[]{"0", "255"};
                break;
            case 1:
                //extra variable / note color / block type
                switch (extraVarsState) {
                    case 1:
                        res = new String[]{"1", "0"};
                        break;
                    case 2:
                        res = new String[]{"2", "1", "0"};
                        break;
                    case 3:
                        res = Util.GetEnumNames(NoteColor.class);
                        break;
                    case 4:
                        res = Util.GetEnumNames(Material.class);
                        break;
                }
                break;
            case 2:
                //Count
                res = new String[]{"1"};
                break;
            case 3:
                //X Pos, also add options for auto fill X Y Z
                res = new String[]{"~", "~ ~", "~ ~ ~"};
                break;
            case 4:
                //Y Pos
                res = new String[]{"~", "~ ~"};
                break;
            case 5:
                //Z Pos
                res = new String[]{"~"};
                break;
            case 6:
                res = new String[]{"0", "0 0", "0 0 0"};
                break;
            case 7:
                res = new String[]{"0", "0 0"};
                break;
            case 8:
                res = new String[]{"0"};
                break;
            case 9:
                res = new String[]{"POS", "VEL"};
                break;
            case 10:
                res = new String[]{"1"};
                break;
            default:
                res = new String[]{""};
        }
        return Arrays.asList(res);
    }
}
