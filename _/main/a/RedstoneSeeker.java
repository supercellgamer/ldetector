package main.a;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RedstoneSeeker extends JavaPlugin {
   private HashMap<UUID, Boolean> alertsToggled;
   public static final String prefix;

   static {
      prefix = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "LagDetector" + ChatColor.DARK_GRAY + "] ";
   }

   public boolean hasAlertsDisabled(Player player) {
      return this.alertsToggled.containsKey(player.getUniqueId()) && !(Boolean)this.alertsToggled.get(player.getUniqueId());
   }

   public boolean hasAlertsEnabled(Player player) {
      return !this.alertsToggled.containsKey(player.getUniqueId()) || (Boolean)this.alertsToggled.get(player.getUniqueId());
   }

   public void disableAlerts(Player player) {
      if (!this.hasAlertsDisabled(player)) {
         this.alertsToggled.put(player.getUniqueId(), false);
      }

   }

   public void enableAlerts(Player player) {
      if (!this.hasAlertsEnabled(player)) {
         this.alertsToggled.put(player.getUniqueId(), true);
      }

   }

   public boolean inAlertsMap(Player player) {
      return this.alertsToggled.containsKey(player.getUniqueId());
   }

   public void saveMemoryAlerts(Player player) {
      if (this.inAlertsMap(player)) {
         this.alertsToggled.remove(player.getUniqueId());
      }

   }

   public void onEnable() {
      this.saveDefaultConfig();
      this.alertsToggled = new HashMap();
      new AlertUtils(this);
      new DetectionUtils(this);
   }

   public void onDisable() {
      this.alertsToggled.clear();
   }
}
