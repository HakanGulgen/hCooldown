package io.github.hakangulgen.hcooldown.util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;

public class ConfigurationVariables {

    private final ConfigurationUtil configurationUtil;

    private String prefix, noPermission, pluginReloaded, warningType,
            interactWarnMessage, inventoryWarnMessage;

    private boolean interactEnabled, interactItemMetaEnabled, inventoryEnabled,
            inventoryItemMetaEnabled, interactWarnEnabled, inventoryWarnEnabled;

    private int interactCooldown, inventoryCooldown;

    public ConfigurationVariables(ConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;

        reloadConfig();
    }

    public void reloadConfig() {
        final Configuration config = configurationUtil.getConfiguration("%datafolder%/config.yml");

        prefix = ChatColor.translateAlternateColorCodes('&', config.getString("messages.prefix"));
        noPermission = ChatColor.translateAlternateColorCodes('&', config.getString("messages.noPermission").replace("%prefix%", prefix));
        pluginReloaded = ChatColor.translateAlternateColorCodes('&', config.getString("messages.pluginReloaded").replace("%prefix%", prefix));
        warningType = config.getString("warningType");

        interactEnabled = config.getBoolean("interact.enabled");
        interactItemMetaEnabled = config.getBoolean("interact.checkItemMeta");
        interactCooldown = config.getInt("interact.cooldown");
        interactWarnEnabled = config.getBoolean("interact.warningMessage.enabled");
        interactWarnMessage = ChatColor.translateAlternateColorCodes('&', config.getString("interact.warningMessage.message").replace("%prefix%", prefix));

        inventoryEnabled = config.getBoolean("inventoryClick.enabled");
        inventoryItemMetaEnabled = config.getBoolean("inventoryClick.checkItemMeta");
        inventoryCooldown = config.getInt("inventoryClick.cooldown");
        inventoryWarnEnabled = config.getBoolean("inventoryClick.warningMessage.enabled");
        inventoryWarnMessage = ChatColor.translateAlternateColorCodes('&', config.getString("inventoryClick.warningMessage.message").replace("%prefix%", prefix));
    }

    public String getPrefix() { return prefix; }

    public String getNoPermission() { return noPermission; }

    public String getPluginReloaded() { return pluginReloaded; }

    public String getWarningType() { return warningType; }

    public String getInteractWarnMessage() { return interactWarnMessage; }

    public String getInventoryWarnMessage() { return inventoryWarnMessage; }

    public boolean isInteractEnabled() { return interactEnabled; }

    public boolean isInventoryEnabled() { return inventoryEnabled; }

    public boolean isInteractWarnEnabled() { return interactWarnEnabled; }

    public boolean isInventoryWarnEnabled() { return inventoryWarnEnabled; }

    public int getInteractCooldown() { return interactCooldown; }

    public int getInventoryCooldown() { return inventoryCooldown; }

    public boolean isInteractItemMetaEnabled() { return interactItemMetaEnabled; }

    public boolean isInventoryItemMetaEnabled() { return inventoryItemMetaEnabled; }

}
