package io.github.hakangulgen.hcooldown;

import io.github.hakangulgen.hcooldown.command.hCooldownCommand;
import io.github.hakangulgen.hcooldown.listener.InventoryClickListener;
import io.github.hakangulgen.hcooldown.listener.NPCRightClickListener;
import io.github.hakangulgen.hcooldown.listener.PlayerInteractListener;
import io.github.hakangulgen.hcooldown.listener.PlayerQuitListener;
import io.github.hakangulgen.hcooldown.util.ConfigurationUtil;
import io.github.hakangulgen.hcooldown.util.ConfigurationVariables;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class hCooldownPlugin extends JavaPlugin {

    private ConfigurationVariables configurationVariables;

    @Override
    public void onEnable() {
        final ConfigurationUtil configurationUtil = new ConfigurationUtil(this);

        configurationUtil.createConfiguration("%datafolder%/config.yml");

        configurationVariables = new ConfigurationVariables(configurationUtil);

        final PluginManager pluginManager = this.getServer().getPluginManager();

        this.registerListeners(pluginManager);

        this.getCommand("hcooldown").setExecutor(new hCooldownCommand(configurationVariables));

        if (configurationVariables.isCitizensEnabled() && pluginManager.getPlugin("Citizens") != null) {
            pluginManager.registerEvents(new NPCRightClickListener(this), this);

            this.getLogger().info("Successfully hooked with Citizens.");
        }
    }

    private void registerListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new InventoryClickListener(this), this);
        pluginManager.registerEvents(new PlayerInteractListener(this), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
    }

    public ConfigurationVariables getVariables() { return configurationVariables; }

}
