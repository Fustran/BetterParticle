package net.cobaltium.betterparticle;

import net.cobaltium.betterparticle.commands.bp.BP;
import net.cobaltium.betterparticle.commands.bp.BPComplete;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterParticle extends JavaPlugin {
    @Override
    public void onEnable() {
        PluginCommand bp = this.getCommand("bp");
        bp.setExecutor(new BP());
        bp.setTabCompleter(new BPComplete());
    }

    @Override
    public void onDisable() {
    }
}
