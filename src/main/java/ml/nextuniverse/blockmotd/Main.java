package ml.nextuniverse.blockmotd;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new JoinEvent());
    }
}
