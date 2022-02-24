package main.a;

import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DetectionUtils implements Listener {
   private RedstoneSeeker rs;

   public DetectionUtils(RedstoneSeeker rs) {
      this.rs = rs;
      rs.getServer().getPluginManager().registerEvents(this, rs);
   }

   public int count(Chunk c, Material m) {
      int count = 0;

      for(int x = 0; x < 16; ++x) {
         for(int z = 0; z < 16; ++z) {
            for(int y = 0; y < 256; ++y) {
               if (c.getBlock(x, y, z).getType() == m) {
                  ++count;
               }
            }
         }
      }

      return count;
   }

   @EventHandler
   public void onBlockPlace(BlockPlaceEvent event) {
      if (event.getBlock().getType() == Material.REPEATER) {
         Chunk chunk = event.getBlock().getChunk();
         int count = this.count(chunk, Material.REPEATER);
         if (count >= this.rs.getConfig().getInt("repeater-limit")) {
            Iterator var5 = Bukkit.getOnlinePlayers().iterator();

            while(var5.hasNext()) {
               Player player = (Player)var5.next();
               if (player.hasPermission("ldetector.alerts") && this.rs.hasAlertsEnabled(player)) {
                  player.sendMessage(RedstoneSeeker.prefix + ChatColor.AQUA + event.getPlayer().getName() + ChatColor.RED + " has just placed a repeater in a chunk already containing " + ChatColor.AQUA + count + ChatColor.RED + " repeaters!");
               }
            }
         }
      }

   }

   @EventHandler
   public void onDisconnect(PlayerQuitEvent event) {
      if (this.rs.inAlertsMap(event.getPlayer())) {
         this.rs.saveMemoryAlerts(event.getPlayer());
      }

   }
}
