package net.hnt8.advancedban.bukkit.listener;

import net.hnt8.advancedban.Universal;
import net.hnt8.advancedban.bukkit.BukkitMain;
import net.hnt8.advancedban.manager.PunishmentManager;
import net.hnt8.advancedban.manager.UUIDManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Leoko @ dev.skamps.eu on 16.07.2016.
 */
public class ConnectionListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onConnect(AsyncPlayerPreLoginEvent event) {
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED){
            UUIDManager.get().supplyInternUUID(event.getName(), event.getUniqueId());
            String result = Universal.get().callConnection(event.getName(), event.getAddress().getHostAddress());
            if (result != null) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, result);
            }
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event){
        PunishmentManager.get().discard(event.getPlayer().getName());
    }

    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        Universal.get().getMethods().scheduleAsync(() -> {
            if (event.getPlayer().getName().equalsIgnoreCase("Leoko")) {
                Bukkit.getScheduler().runTaskLaterAsynchronously(BukkitMain.get(), () -> {
                    if (Universal.get().broadcastLeoko()) {
                        Bukkit.broadcastMessage("");
                        Bukkit.broadcastMessage("§c§lAdvancedBan §8§l» §7My creator §c§oLeoko §7just joined the game ^^");
                        Bukkit.broadcastMessage("");
                    } else {
                        event.getPlayer().sendMessage("§c§lAdvancedBan v2 §8§l» §cHey Leoko we are using your Plugin (NO-BC)");
                    }
                }, 20);
            }
        }, 20);
    }


}