package net.cobaltium.betterparticle.commands.bp;

import net.cobaltium.betterparticle.data.ParticleLoc;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
/*
ARGS LAYOUT:
/bp PARTICLE COUNT posX posY posZ delX delY delZ VEL SPEED
 */

public class BP implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length < 10) {
            //to tell the player what args they are missing, we make a full list of required args, then remove the ones they've already added.
            ArrayList<String> missingArgs = new ArrayList<>(Arrays.asList("PARTICLE", "COUNT", "posX", "posY", "posZ", "delX", "delY", "delZ", "REL", "SPEED"));
            missingArgs.subList(0, args.length).clear();
            sender.sendMessage(ChatColor.RED + "Missing Arguments:\n" + ChatColor.GRAY +
                    "/bp " + String.join(" ", args) + ChatColor.RED + " " + String.join(" ", missingArgs));
            return false;
        }

        ParticleLoc loc;
        ParticleLoc delta;
        try {
            loc = new ParticleLoc(sender, new String[]{args[2], args[3], args[4]});
            delta = new ParticleLoc(sender, new String[]{args[5], args[6], args[7]});
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        Particle a;
        try {
            a = Particle.valueOf(args[0].toUpperCase());
        } catch(Exception e) {
            sender.sendMessage("lol wrong name dumdum, \"" + args[0] + "\" is hella " + e.getMessage());
            return false;
        }
        return true;
    }
}
