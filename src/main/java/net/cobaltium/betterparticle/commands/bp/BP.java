package net.cobaltium.betterparticle.commands.bp;

import net.cobaltium.betterparticle.data.ParticleLoc;
import net.cobaltium.betterparticle.data.Range;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;

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

        //particle arg check
        Particle particle;
        try {
            particle = Particle.valueOf(args[0].toUpperCase());
        } catch(Exception e) {
            System.out.println(e.toString());
            sender.sendMessage("Invalid particle.");
            return false;
        }
        //offset the position of the rest of our arg reads in the event that we are spawning a particle with different arg requirements
        int offset = 0;
        switch (particle) {
            case REDSTONE:
            case SPELL_MOB:
            case SPELL_MOB_AMBIENT:
                offset = 3;
                break;
            case NOTE:
            case ITEM_CRACK:
            case BLOCK_CRACK:
            case BLOCK_DUST:
            case FALLING_DUST:
                offset = 1;
                break;
        }

        ParticleLoc loc;
        ParticleLoc delta;
        try {
            loc = new ParticleLoc(sender, new String[]{args[offset+2], args[offset+3], args[offset+4]});
            delta = new ParticleLoc(sender, new String[]{args[offset+5], args[offset+6], args[offset+7]});
        } catch (Exception e) {
            System.out.println(e.toString());
            sender.sendMessage("Invalid position / delta v.");
            return false;
        }
        //velocity / positional arg check
        boolean isVel;
        try {
            if (args[offset+8].equalsIgnoreCase("VEL")) {
                isVel = true;
            } else if (args[offset+8].equalsIgnoreCase("ABS")) {
                isVel = false;
            }
        } catch(Exception e) {
            System.out.println(e.toString());
            sender.sendMessage("Invalid Velocity / Absolute position status.");
            return false;
        }
        //count check
        Range count;
        try {
            count = new Range(sender, args[offset+1], null);
        } catch(Exception e) {
            System.out.println(e.toString());
            sender.sendMessage("Invalid particle count.");
            return false;
        }
        //speed check
        Range speed;
        try {
            speed = new Range(sender, args[offset+9], null);
        } catch(Exception e) {
            System.out.println(e.toString());
            sender.sendMessage("Invalid speed.");
            return false;
        }
        //spawn particle(s)
        for (int i = 0; i < Math.round(count.GetVal()); i++) {
            Location finalPos = new Location(loc.GetWorld(), loc.GetX(), loc.GetY(), loc.GetZ());
            loc.GetWorld().spawnParticle(particle, finalPos, 0, delta.GetX(), delta.GetY(), delta.GetZ(), speed.GetVal(), null, false);
        }

        return true;
    }
}
