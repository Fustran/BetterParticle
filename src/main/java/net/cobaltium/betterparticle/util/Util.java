package net.cobaltium.betterparticle.util;

import com.sun.istack.internal.Nullable;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Util {
    //turns a list of enums into a string[]
    public static String[] GetEnumNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
    //gets the sender's world
    @Nullable
    public static World GetSenderWorld(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            return player.getWorld();
        } else if (sender instanceof BlockCommandSender) {
            BlockCommandSender cmdBlock = (BlockCommandSender)sender;
            return cmdBlock.getBlock().getWorld();
        }
        return null;
    }
}
