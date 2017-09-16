package ml.nextuniverse.blockmotd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.HashMap;

public class JoinEvent implements Listener {
    // Stores the hostname for use in the PostLoginEvent
    HashMap<String, String> hostname = new HashMap<>();

    // Could potentially be used if the domain ever changes

//    // Sends the deprecated message when using a different ip
//    @EventHandler
//    public void onLogin(PostLoginEvent e) {
//        System.out.println(hostname.get(e.getPlayer().getName()).equalsIgnoreCase("play.nextuniverse.ml"));
//        if (hostname.get(e.getPlayer().getName()).equalsIgnoreCase("play.nextuniverse.ml")) {
//            e.getPlayer().sendMessage(ChatColor.YELLOW  + "The IP used is deprecated. Please update your IP to " + ChatColor.GOLD + "???");
//        }
//        if (hostname.containsKey(e.getPlayer().getName())) {
//            hostname.remove(e.getPlayer().getName());
//        }
//    }

    @EventHandler
    public void onJoin(PostLoginEvent e) {
        e.getPlayer().setTabHeader(new ComponentBuilder("NextUniverse").color(ChatColor.LIGHT_PURPLE).bold(true).append(" | ")
        .color(ChatColor.DARK_GRAY).bold(false).append(" nextuniverse.ml").color(ChatColor.AQUA).create(), new ComponentBuilder("Support the server: ").color(ChatColor.GRAY).append("nextuniverse.buycraft.net").color(ChatColor.AQUA).create());
    }

    // Allows for a custom version when using an outdated version & fakes a non-existent server
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ProxyPingEvent e) {
        ServerPing serverPing = e.getResponse();

        ServerPing.Protocol version = new ServerPing.Protocol(ChatColor.AQUA + "Supports Minecraft 1.8 - 1.12", serverPing.getVersion().getProtocol());
        serverPing.setVersion(version);
        e.setResponse(serverPing);
    }
}
