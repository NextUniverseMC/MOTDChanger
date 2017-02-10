package ml.nextuniverse.blockmotd;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class JoinEvent implements Listener {
    // Stores the hostname for use in the PostLoginEvent
    HashMap<String, String> hostname = new HashMap<>();

    // Kicks a user when using the wrong ip
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin(PreLoginEvent e) {
        hostname.put(e.getConnection().getName(), e.getConnection().getVirtualHost().getHostName());
        if (e.getConnection().getVirtualHost().getHostName().equalsIgnoreCase("jerrysnetwork.com"))  {
            e.setCancelled(true);
            e.setCancelReason("java.net.ConnectException: Connection refused: no further information:");
        }
    }
    // Sends the deprecated message when using a different ip
    @EventHandler
    public void onLogin(PostLoginEvent e) {
        System.out.println(hostname.get(e.getPlayer().getName()));
        if (hostname.get(e.getPlayer().getName()).contains("server.thediamondpicks.tk")) {
            e.getPlayer().sendMessage(ChatColor.YELLOW  + "The IP used is deprecated. Please update your IP to " + ChatColor.GOLD + "play.nextuniverse.ml");
        }
        if (hostname.containsKey(e.getPlayer().getName())) {
            hostname.remove(e.getPlayer().getName());
        }
    }
    // Allows for a custom version when using an outdated version & fakes a non-existent server
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ProxyPingEvent e) {
        ServerPing serverPing = e.getResponse();

        ServerPing.Protocol version = new ServerPing.Protocol(ChatColor.AQUA + "Supports from Minecraft 1.8 - 1.11.2", serverPing.getVersion().getProtocol());
        serverPing.setVersion(version);
        e.setResponse(serverPing);
        if (e.getConnection().getVirtualHost().getHostName().equalsIgnoreCase("jerrysnetwork.com")) {
            serverPing = e.getResponse();
            serverPing.setVersion(new ServerPing.Protocol("", serverPing.getVersion().getProtocol() - 1));
            serverPing.setDescription(ChatColor.DARK_RED + "Can't connect to server.");
            BufferedImage img = null;
            try{
                img = ImageIO.read(new File("unknown_server.png"));
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
            Favicon f = Favicon.create(img);
            serverPing.setFavicon(f);
        }
    }
}
