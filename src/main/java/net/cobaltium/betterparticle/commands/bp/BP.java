package net.cobaltium.betterparticle.commands.bp;

import net.cobaltium.betterparticle.data.NoteColor;
import net.cobaltium.betterparticle.data.RangeVec;
import net.cobaltium.betterparticle.data.Range;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Redstone;

import java.util.ArrayList;
import java.util.Arrays;
/*
ARGS LAYOUT:
/bp PARTICLE COUNT posX posY posZ delX delY delZ VEL SPEED
 */

public class BP implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RangeVec loc;
        RangeVec delta = null;
        Particle.DustOptions dust = null;
        ItemStack itemType = null;
        BlockData blockType = null;
        Range speed = null;
        //REFACTOR
        //error if player doesn't supply enough args to run command
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
            sender.sendMessage("Invalid particle: " + args[0]);
            return false;
        }
        //offset the position of the rest of our arg reads in the event that we are spawning a particle with different arg requirements
        int offset = 0;
        RangeVec RGB = null;
        Range size = null;
        switch (particle.name()) {
            case "REDSTONE":
            case "SPELL_MOB":
            case "SPELL_MOB_AMBIENT":
                try {
                    //get RGB input
                    RGB = new RangeVec(sender, new String[]{args[1], args[2], args[3]});
                    size = new Range(sender, args[4], null);
                } catch (Exception e) {
                    System.out.println(e.toString());
                    sender.sendMessage("Invalid color.");
                    return false;
                }
                if (particle != Particle.REDSTONE) {
                    speed = size;
                    //SPELL particles take RBG because ???
                    delta = new RangeVec(sender, new String[]{args[1], args[3], args[2]});
                }
                offset = 4;
                break;
            case "NOTE":
                try {
                    //get note color
                    speed = new Range(sender, "1", null);
                    NoteColor note = NoteColor.valueOf(args[1].toUpperCase());
                    delta = new RangeVec(sender, new String[]{String.valueOf(note.GetValue()), "0", "0"});
                } catch(Exception e) {
                    System.out.println(e.toString());
                    sender.sendMessage("Invalid note: " + args[1]);
                    return false;
                }
                offset = 1;
                break;
            case "BLOCK_DUST":
            case "BLOCK_CRACK":
            case "FALLING_DUST":
            case "ITEM_CRACK":
                try {
                    //get blockdata / itemstack
                    Material materialType = Material.getMaterial(args[1]);
                    if (particle != Particle.ITEM_CRACK) {
                        if (!materialType.isBlock()) {
                            sender.sendMessage("Material is not a block.");
                            return false;
                        }
                        blockType = materialType.createBlockData();
                    }
                    itemType = new ItemStack(materialType);

                } catch (Exception e) {
                    System.out.println(e.toString());
                    sender.sendMessage("Invalid item name: " + args[1]);
                    return false;
                }
                offset = 1;
                break;
        }

        //get position and delta from input
        try {
            loc = new RangeVec(sender, new String[]{args[offset+2], args[offset+3], args[offset+4]});
            if (delta == null) delta = new RangeVec(sender, new String[]{args[offset+5], args[offset+6], args[offset+7]});
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
        try {
            if (speed == null) speed = new Range(sender, args[offset+9], null);
        } catch(Exception e) {
            System.out.println(e.toString());
            sender.sendMessage("Invalid speed.");
            return false;
        }

        //spawn particle(s)
        for (int i = 0; i < Math.round(count.GetVal()); i++) {
            Location particleLoc = new Location(loc.GetWorld(), loc.GetX(), loc.GetY(), loc.GetZ());
            //get randomized color each loop
            if (particle == Particle.REDSTONE) {
                Color color = Color.fromRGB(RGB.GetX().intValue(), RGB.GetY().intValue(), RGB.GetZ().intValue());
                dust = new Particle.DustOptions(color, size.GetVal().floatValue());
            }
            loc.GetWorld().spawnParticle(
                    particle,
                    particleLoc,
                    0,
                    delta.GetX(),
                    delta.GetY(),
                    delta.GetZ(),
                    speed.GetVal(),
                    (offset == 4) ? dust : (offset == 1 && particle == Particle.ITEM_CRACK) ? itemType : blockType,
                    false
            );
        }
        return true;
    }
}
