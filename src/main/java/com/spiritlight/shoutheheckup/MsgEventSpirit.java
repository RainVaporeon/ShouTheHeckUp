package com.spiritlight.shoutheheckup;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MsgEventSpirit {
    @SubscribeEvent
    public void onMessage(ClientChatReceivedEvent event) {
        // Split messages
        String[] message = event.getMessage().getUnformattedText().split(" ");
        if (message.length >= 3)
            if (message[2].contains("shouts:"))
                if ((STHU.players.contains(message[0])) || STHU.hidelevel == 3 || STHU.bannedWords.stream().anyMatch(event.getMessage().getUnformattedText()::contains)) {
                    // Declare initial types
                    TextComponentString text;
                    Style style;
                    // Behavior
                    switch (STHU.hidelevel) {
                        case 3:
                        case 2:
                            event.setCanceled(true);
                            break;
                        case 1:
                            text = new TextComponentString("§7§o[Blocked shout message]");
                            style = text.getStyle();
                            style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, event.getMessage()));
                            text.setStyle(style);
                            event.setMessage(text);
                            break;
                        case 0:
                            text = new TextComponentString("§7§o[Blocked shout message by " + message[0] + "]");
                            style = text.getStyle();
                            style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, event.getMessage()));
                            text.setStyle(style);
                            event.setMessage(text);
                            break;
                    }
                }
    }

    @SubscribeEvent
    public void onClientReceivedMessage(ClientChatReceivedEvent event) {
        if (!STHU.filterChat) return;
        final String message = event.getMessage().getUnformattedText();
        if (STHU.bannedWords.stream().anyMatch(message::contains)) {
            TextComponentString text;
            Style style;
            // Behavior
            switch (STHU.hidelevel) {
                case 3:
                case 2:
                    event.setCanceled(true);
                    break;
                case 1:
                case 0:
                    text = new TextComponentString("§7§o[Blocked message]");
                    style = text.getStyle();
                    style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, event.getMessage()));
                    text.setStyle(style);
                    event.setMessage(text);
                    break;
            }
        }
    }
}
