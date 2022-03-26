package com.spiritlight.shoutheheckup;


import net.minecraft.command.CommandBase;
// import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import java.io.IOException;

public class CmdSpirit extends CommandBase {
    @Override
    public String getName() {
        return "sthu";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender iCommandSender) {
        return "/sthu (add/remove/list)";
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender sender, String[] args) { // yeet CommandException
        if (args.length == 0) {
            sender.sendMessage(new TextComponentString(STHU.prefix + " --------------- Help Page ---------------"));
            sender.sendMessage(new TextComponentString(STHU.prefix + " /sthu add - Adds a player to ignore shout"));
            sender.sendMessage(new TextComponentString(STHU.prefix + " /sthu remove - Removes a player from list"));
            sender.sendMessage(new TextComponentString(STHU.prefix + " /sthu list - Lists players ignored so far"));
            sender.sendMessage(new TextComponentString(STHU.prefix + " /sthu toggle - Toggles on/off mod feature"));
            sender.sendMessage(new TextComponentString(STHU.prefix + " /sthu sethidelevel - Set hide shout level"));
            sender.sendMessage(new TextComponentString(STHU.prefix + " /sthu hidelevel - Shows hide lv behaviors"));
            sender.sendMessage(new TextComponentString(STHU.prefix + " Current Hide Level: " + (STHU.hidelevel == 3 ? "§4HIGHEST" : (STHU.hidelevel == 2 ? "§cHIGH" : (STHU.hidelevel == 1 ? "§eMedium" : (STHU.hidelevel == 0 ? "§aLow" : "§7Failed to get"))))));
            sender.sendMessage(new TextComponentString(STHU.prefix + " -----------------------------------------"));
        }
        else {
            switch(args[0]) {
                case "add":
                    add(args[1], sender);
                    break;
                case "remove":
                    remove(args[1], sender);
                    break;
                case "list":
                    sender.sendMessage(new TextComponentString(STHU.prefix + " Current ignored players: " + STHU.players)); // Add something
                    break;
                case "sethidelevel":
                    setHidelevel(args[1], sender);
                    break;
                case "hidelevel":
                    sender.sendMessage(new TextComponentString(STHU.prefix + " high - Suppresses shout message"));
                    sender.sendMessage(new TextComponentString(STHU.prefix + " medium - Replaces shout message"));
                    sender.sendMessage(new TextComponentString(STHU.prefix + " low - Replaces shout message with sender name"));
                    break;
                case "toggle":
                    toggle(sender);
                    break;
            }
        }
    }
    // cc §
    private void add(String s, ICommandSender sender) {
        // add player to list
        if(s == null) {
            sender.sendMessage(new TextComponentString(STHU.prefix + " §cYou should supply a player."));
        } else if (STHU.players.contains(s)) {
            sender.sendMessage(new TextComponentString(STHU.prefix + " §cThis player already exists."));
        } else
            STHU.players.add(s);
            refreshConfig();
    }

    private void remove(String s, ICommandSender sender) {
        // remove player from list
        if(s == null) {
            sender.sendMessage(new TextComponentString(STHU.prefix + " §cYou should supply a player."));
        } else if (STHU.players.contains(s)) {
            STHU.players.remove(s);
        } else
            sender.sendMessage(new TextComponentString(STHU.prefix + " §cThe player does not exist from the list."));
        refreshConfig();
    }
    private void toggle(ICommandSender sender) {
        STHU.active = !STHU.active;
        sender.sendMessage(new TextComponentString(STHU.prefix + " Mod is now " + (STHU.active ? "§aenabled" : "§cdisabled") + "§r."));
    }
    private void setHidelevel(String level, ICommandSender sender) {
        switch(level) {
            case "all":
                STHU.hidelevel = 3;
                break;
            case "high":
                STHU.hidelevel = 2; // Entirely hide
                break;
            case "medium":
                STHU.hidelevel = 1; // Shows [Blocked Shout Message]
                break;
            case "low":
                STHU.hidelevel = 0; // Shows [Blocked Shout Message by player]
                break;
        }
        sender.sendMessage(new TextComponentString(STHU.prefix + " HideLevel has been changed."));
        refreshConfig();
    }
    private void refreshConfig() {
        try {
            ConfigSpirit.writeConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
