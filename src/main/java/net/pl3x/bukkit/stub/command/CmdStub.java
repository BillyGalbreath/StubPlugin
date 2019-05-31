package net.pl3x.bukkit.stub.command;

import net.pl3x.bukkit.stub.StubPlugin;
import net.pl3x.bukkit.stub.configuration.Config;
import net.pl3x.bukkit.stub.configuration.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;

public class CmdStub implements TabExecutor {
    private final StubPlugin plugin;

    public CmdStub(StubPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && "reload".startsWith(args[0].toLowerCase()) && sender.hasPermission("command.stub")) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("command.stub")) {
            Lang.send(sender, Lang.COMMAND_NO_PERMISSION);
            return true;
        }

        String response = "&d" + plugin.getName() + " v" + plugin.getDescription().getVersion();

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            Config.reload(plugin);
            Lang.reload(plugin);

            response += " reloaded";
        }

        Lang.send(sender, response);
        return true;
    }
}
