package me.pioula111.roleplaychat.chatColors;

import org.bukkit.ChatColor;

public abstract class ChatFormating {
    public static String formatMessage(String message) {
        StringBuilder newMessage = new StringBuilder();
        newMessage.append(ChatColor.GRAY);

        boolean sayMe = true;
        boolean sayDo = true;
        boolean sayOoc = true;

        for (int i = 0; i < message.length(); i++) {
            switch (message.charAt(i)) {
                case '*': {
                    if (sayMe) {
                        newMessage.append(ChatColor.LIGHT_PURPLE);
                        newMessage.append(message.charAt(i));
                    }
                    else {
                        newMessage.append(message.charAt(i));
                        newMessage.append(ChatColor.GRAY);
                    }
                    sayMe ^= true;
                    break;
                }
                case '#': {
                    if (sayDo) {
                        newMessage.append(ChatColor.DARK_GREEN);
                        newMessage.append(message.charAt(i));
                    }
                    else {
                        newMessage.append(message.charAt(i));
                        newMessage.append(ChatColor.GRAY);
                    }
                    sayDo ^= true;
                    break;
                }
                case '^': {
                    if (sayOoc) {
                        newMessage.append(ChatColor.GOLD);
                        newMessage.append(message.charAt(i));
                    }
                    else {
                        newMessage.append(message.charAt(i));
                        newMessage.append(ChatColor.GRAY);
                    }

                    sayOoc ^= true;
                    break;
                }
                default:
                    newMessage.append(message.charAt(i));
                    break;
            }

        }

        return newMessage.toString();
    }
}
