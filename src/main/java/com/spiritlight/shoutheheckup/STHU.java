package com.spiritlight.shoutheheckup;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.io.IOException;
import java.util.ArrayList;

@Mod(modid = STHU.MODID, name = STHU.NAME, version = STHU.VERSION)
public class STHU
{
    public static final String MODID = "sthu";
    public static final String NAME = "Shout The Heck Up!";
    public static final String VERSION = "1.2";
    public static String prefix = "§6[§rSTHU§6]";
    public static boolean active = true;
    public static ArrayList<String> players = new ArrayList<String>();
    public static ArrayList<String> bannedWords = new ArrayList<String>();
    public static String version = "1.2";
    public static int hidelevel = 0;
    @EventHandler
    public void init(FMLInitializationEvent event) throws IOException
    {
        MinecraftForge.EVENT_BUS.register(new MsgEventSpirit());
        ClientCommandHandler.instance.registerCommand(new CmdSpirit());
        ConfigSpirit.getConfig();
    }
}
