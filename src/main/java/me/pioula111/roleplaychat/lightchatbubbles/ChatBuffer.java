package me.pioula111.roleplaychat.lightchatbubbles;

import me.pioula111.roleplaychat.Roleplaychat;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ChatBuffer {
	// properties
	private Roleplaychat plugin;
	private int maxBubbleHeight;
	private int maxBubbleWidth;
	private int bubblesInterval;
	private Map<String, Queue<String>> chatQueue = new HashMap<>();
	
	// constructor
	public ChatBuffer(Roleplaychat plugin) {
		maxBubbleHeight = plugin.getConfig().getInt("maxBubbleHeight");
		maxBubbleWidth = plugin.getConfig().getInt("maxBubbleWidth");
		bubblesInterval = plugin.getConfig().getInt("bubblesInterval");
		this.plugin = plugin;
	}
	
	// wrap pre-trimmed chat and put in a player buffer
	public void receiveChat(Player player, String msg)
	{
		// most probable case, 1 line chat
		if (msg.length() <= maxBubbleWidth)
		{
			queueChat(player, msg+"\n");
			return;
		}
		
		// longer chat, prepare word wrap
		msg = msg+" ";
		StringBuilder chat = new StringBuilder();
		int delimPos;
		int lineCount = 0;
		
		// word wrap chat
		while (msg.length() > 0)
		{
			// search delimiter (space) before or after bubble width, or finishing
			delimPos = msg.lastIndexOf(' ', maxBubbleWidth);
			if (delimPos < 0) delimPos = msg.indexOf(' ', maxBubbleWidth);
			if (delimPos < 0) delimPos = msg.length();
			
			// pull sized text from chat message
			chat.append(msg, 0, delimPos);
			msg = msg.substring(delimPos+1);
			
			// line wrap chat in multiple messages if exceeds max lines
			++lineCount;
			if (lineCount % maxBubbleHeight == 0 || msg.length() == 0)
			{
				queueChat(player, chat+(msg.length() == 0 ? "\n" : "...\n"));
				chat = new StringBuilder();
			}
			else
				chat.append("\n");
		}
	}
	
	// get word wrapped chat and queues in a player buffer, creates if not exists
	private void queueChat(Player player, String chat)
	{
		// if no player buffer yet, create it and schedule this message
		String playerId = ""+player.getUniqueId();
		if (!chatQueue.containsKey(playerId))
		{
			chatQueue.put(playerId, new LinkedList<>());
			scheduleMessageUpdate(player, playerId, 0);
		}
		
		// queue this message
		chatQueue.get(playerId).add(chat);
	}
	
	// ... and this method will take previously queued chat messages by one for display
	private void scheduleMessageUpdate(Player player, String playerId, int timer)
	{
		//BukkitTask chatTimer = new BukkitRunnable()
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				// player could be not chatting or offline, check him and collect his garbage
				if (chatQueue.get(playerId).size() < 1 || !player.isOnline())
				{
					chatQueue.remove(playerId);
					return;
				}
				
				// pull queued message, send it to be displayed and get the duration, schedule the next message
				String chat = chatQueue.get(playerId).poll();
				assert chat != null;
				int bubbleDuration = plugin.getBubbles().receiveMessage(player, chat);
				scheduleMessageUpdate(player, playerId, bubbleDuration+bubblesInterval);
			}
		}.runTaskLater(plugin, timer);
	}
	
}
