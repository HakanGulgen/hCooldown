package io.github.hakangulgen.hcooldown.util;

import org.bukkit.configuration.Configuration;

public class ConfigurationVariables {

    private final ConfigurationUtil configurationUtil;

    private String prefix, noPermission, pluginReloaded,
            interactWarnMessage, inventoryWarnMessage, citizensWarnMessage;

    private boolean useHamsterAPIEnabled, interactEnabled, interactItemMetaEnabled, inventoryEnabled,
            inventoryItemMetaEnabled, interactWarnEnabled, inventoryWarnEnabled,
            citizensEnabled, citizensWarnEnabled;

    private int interactCooldown, inventoryCooldown, citizensCooldown, warningType;

    public ConfigurationVariables(ConfigurationUtil configurationUtil) {
        this.configurationUtil = configurationUtil;

        reloadConfig();
    }

    public void reloadConfig() {
        final Configuration config = configurationUtil.getConfiguration("%datafolder%/config.yml");

        prefix = config.getString("messages.prefix").replace("&", "§");
        noPermission = config.getString("messages.no-permission").replace("&", "§").replace("%prefix%", prefix);
        pluginReloaded = config.getString("messages.plugin-reloaded").replace("&", "§").replace("%prefix%", prefix);

        useHamsterAPIEnabled = config.getBoolean("useHamsterAPI");

        warningType = config.getInt("warn-type");

        interactEnabled = config.getBoolean("interact.enabled");
        interactItemMetaEnabled = config.getBoolean("interact.check.item-meta");
        interactCooldown = config.getInt("interact.cooldown");
        interactWarnEnabled = config.getBoolean("interact.warn.enabled");
        interactWarnMessage = config.getString("interact.warn.message").replace("&", "§").replace("%prefix%", prefix);

        inventoryEnabled = config.getBoolean("inventory-click.enabled");
        inventoryItemMetaEnabled = config.getBoolean("inventory-click.check.item-meta");
        inventoryCooldown = config.getInt("inventory-click.cooldown");
        inventoryWarnEnabled = config.getBoolean("inventory-click.warn.enabled");
        inventoryWarnMessage = config.getString("inventory-click.warn.message").replace("&", "§").replace("%prefix%", prefix);

        citizensEnabled = config.getBoolean("citizens-click.enabled");
        citizensCooldown = config.getInt("citizens-click.cooldown");
        citizensWarnEnabled = config.getBoolean("citizens-click.warn.enabled");
        citizensWarnMessage = config.getString("citizens-click.warn.message").replace("&", "§").replace("%prefix%", prefix);
    }

    public String getPrefix() { return prefix; }

    public String getNoPermission() { return noPermission; }

    public String getPluginReloaded() { return pluginReloaded; }

    public int getWarningType() { return warningType; }

    public String getInteractWarnMessage() { return interactWarnMessage; }

    public String getInventoryWarnMessage() { return inventoryWarnMessage; }

    public String getCitizensWarnMessage() { return citizensWarnMessage; }

    public boolean isInteractEnabled() { return interactEnabled; }

    public boolean isInventoryEnabled() { return inventoryEnabled; }

    public boolean isInteractWarnEnabled() { return interactWarnEnabled; }

    public boolean isInventoryWarnEnabled() { return inventoryWarnEnabled; }

    public boolean isInteractItemMetaEnabled() { return interactItemMetaEnabled; }

    public boolean isInventoryItemMetaEnabled() { return inventoryItemMetaEnabled; }

    public boolean isCitizensEnabled() { return citizensEnabled; }

    public boolean isCitizensWarnEnabled() { return citizensWarnEnabled; }

    public int getCitizensCooldown() { return citizensCooldown; }

    public int getInteractCooldown() { return interactCooldown; }

    public int getInventoryCooldown() { return inventoryCooldown; }

    public void setUseHamsterAPIEnabled(boolean useHamsterAPIEnabled) { this.useHamsterAPIEnabled = useHamsterAPIEnabled;
    }
    public boolean isUseHamsterAPIEnabled() { return useHamsterAPIEnabled; }

}
