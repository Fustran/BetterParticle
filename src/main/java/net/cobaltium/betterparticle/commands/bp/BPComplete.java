package net.cobaltium.betterparticle.commands.bp;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

public class BPComplete implements TabCompleter {
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        String[] res;
        switch (args.length) {
            case 1:
                //particle type
                res = new String[]{"BARRIER", "BLOCK_CRACK", "BLOCK_DUST", "BUBBLE_COLUMN_UP", "BUBBLE_POP", "CAMPFIRE_COZY_SMOKE", "CAMPFIRE_SIGNAL_SMOKE",
                "CLOUD", "COMPOSTER", "CRIT", "CRIT_MAGIC", "CURRENT_DOWN", "DAMAGE_INDICATOR", "DOLPHIN", "DRAGON_BREATH", "DRIP_LAVA", "DRIP_WATER",
                "DRIPPING_HONEY", "ENCHANTMENT_TABLE", "END_ROD", "EXPLOSION_HUGE", "EXPLOSION_LARGE", "EXPLOSION_NORMAL", "FALLING_DUST", "FALLING_HONEY",
                "FALLING_LAVA", "FALLING_NECTAR", "FALLING_WATER", "FIREWORKS_SPARK", "FLAME", "FLASH", "HEART", "ITEM_CRACK", "LANDING_HONEY", "LANDING_LAVA",
                "LAVA", "LEGACY_BLOCK_CRACK", "LEGACY_BLOCK_DUST", "LEGACY_FALLING_DUST", "MOB_APPEARANCE", "NAUTILUS", "NOTE", "PORTAL", "REDSTONE", "SLIME",
                "SMOKE_LARGE", "SNEEZE", "SNOW_SHOVEL", "SNOWBALL", "SPELL", "SPELL_INSTANT", "SPELL_MOB", "SPELL_MOB_AMBIENT", "SPELL_WITCH", "SPIT",
                "SQUID_INK", "SUSPENDED", "SUSPENDED_DEPTH", "SWEEP_ATTACK", "TOTEM", "TOWN_AURA", "VILLAGER_ANGRY", "VILLAGER_HAPPY", "WATER_BUBBLE",
                "WATER_DROP", "WATER_SPLASH", "WATER_WAKE"};
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
                res = new String[]{"???"};
        }
        return Arrays.asList(res);
    }
}
