package com.aurilux.xar.handler;

import com.aurilux.xar.lib.XARModInfo;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.util.ChatComponentText;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class UpdateHandler {
    private final String MESSAGE_PREFACE = "[\u00A75" + XARModInfo.MOD_NAME + "\u00A7r] A new version of " + XARModInfo.MOD_NAME + " is available.";
    private String updateThread = null;

    @SubscribeEvent
	public void playerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (isUpdateAvailable()) {
			event.player.addChatMessage(new ChatComponentText(MESSAGE_PREFACE + " Please go to " + updateThread + " to download the update."));
		}
	}
	
	private boolean isUpdateAvailable() {
		try {
			HttpsURLConnection conn = (HttpsURLConnection) new URL(XARModInfo.VERSION_FILE).openConnection();
			InputStream versionFile = conn.getInputStream();
			Properties versionProperties = new Properties();
			versionProperties.loadFromXML(versionFile);
			String currentVersion = versionProperties.getProperty(Loader.instance().getMCVersionString());
			updateThread = versionProperties.getProperty("thread");
			return (currentVersion != null && updateThread != null && !currentVersion.equals(XARModInfo.MOD_VERSION));
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}