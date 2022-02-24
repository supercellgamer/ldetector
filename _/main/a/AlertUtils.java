package main.a;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertUtils implements CommandExecutor {
   private RedstoneSeeker rs;

   public AlertUtils(RedstoneSeeker rs) {
      this.rs = rs;
      rs.getCommand("ldetector").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
      if (!(sender instanceof Player)) {
         return true;
      } else {
         Player player = (Player)sender;
         if (cmd.getName().equalsIgnoreCase("ldetector")) {
            if (args.length == 0) {
               if (!player.hasPermission("ldetector.*") && !player.hasPermission("ldetector.alerts") && !player.hasPermission("ldetector.reload")) {
                  player.sendMessage(ChatColor.RED + "You do not have access to this command.");
                  return true;
               }

               player.sendMessage(RedstoneSeeker.prefix + ChatColor.RED + "Please enter one of the following arguments:");
               player.sendMessage(ChatColor.RED + " - /ldetector toggle - Toggles alerts");
               player.sendMessage(ChatColor.RED + " - /ldetector reload - Reload the config");
               player.sendMessage(ChatColor.RED + " - /ldetector about - Learn about this plugins terms of use");
               player.sendMessage(ChatColor.RED + " - /ldetector - Display this page again");
               return true;
            }

            if (args.length == 1) {
               if (args[0].equalsIgnoreCase("toggle")) {
                  if (!player.hasPermission("ldetector.alerts") && !player.hasPermission("ldetector.*")) {
                     player.sendMessage(ChatColor.RED + "You do not have access to this command.");
                     return true;
                  }

                  if (this.rs.hasAlertsEnabled(player)) {
                     this.rs.disableAlerts(player);
                     player.sendMessage(RedstoneSeeker.prefix + ChatColor.RED + "Alerts are now disabled.");
                  } else if (this.rs.hasAlertsDisabled(player)) {
                     this.rs.enableAlerts(player);
                     player.sendMessage(RedstoneSeeker.prefix + ChatColor.RED + "Alerts are now enabled.");
                  }

                  return true;
               }

               if (args[0].equalsIgnoreCase("reload")) {
                  if (!player.hasPermission("ldetector.reload") && !player.hasPermission("ldetector.*")) {
                     player.sendMessage(ChatColor.RED + "You do not have access to this command.");
                     return true;
                  }

                  player.sendMessage(RedstoneSeeker.prefix + ChatColor.RED + "Config reloaded!");
                  this.rs.reloadConfig();
                  return true;
               }

               if (!player.hasPermission("ldetector.*") && !player.hasPermission("ldetector.alerts") && !player.hasPermission("ldetector.reload")) {
                  player.sendMessage(ChatColor.RED + "You do not have access to this command.");
                  return true;
               }

               player.sendMessage(RedstoneSeeker.prefix + ChatColor.RED + "Please enter one of the following arguments:");
               player.sendMessage(ChatColor.RED + " - /ldetector toggle - Toggles alerts");
               player.sendMessage(ChatColor.RED + " - /ldetector reload - Reload the config");
               player.sendMessage(ChatColor.RED + " - /ldetector - Display this page again");
               return true;
            }
         }

         return true;
      }
   }
}
