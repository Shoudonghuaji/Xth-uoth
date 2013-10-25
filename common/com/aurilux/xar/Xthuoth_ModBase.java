package com.aurilux.xar;

import java.util.logging.Level;

import net.minecraftforge.common.Configuration;

import com.aurilux.xar.handlers.LocalizationHandler;
import com.aurilux.xar.handlers.XARUpdateHandler;
import com.aurilux.xar.lib.Blocks;
import com.aurilux.xar.lib.Entities;
import com.aurilux.xar.lib.Items;
import com.aurilux.xar.lib.Recipes;
import com.aurilux.xar.lib.WorldGen;
import com.aurilux.xar.lib.XAR_Ref;
import com.aurilux.xar.proxy.CommonXARProxy;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = XAR_Ref.MOD_ID, name = XAR_Ref.MOD_NAME, version = XAR_Ref.MOD_VERSION)
@NetworkMod(channels = {XAR_Ref.MOD_ID}, clientSideRequired = true, serverSideRequired = false)
public class Xthuoth_ModBase {
	
	@Instance(XAR_Ref.MOD_ID)
    public static Xthuoth_ModBase instance;

    @SidedProxy(clientSide = "com.aurilux.xar.proxy.ClientXARProxy", serverSide = "com.aurilux.xar.proxy.CommonXARProxy")
    public static CommonXARProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		//initialize enum additions, localization, and load configuration
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		try {
			config.load();
			
			//register blocks, items
			Blocks.init(config);
			Items.init(config);
			
			//register biomes, dimensions, and other world-gen
			WorldGen.init();
		}
        catch (Exception ex) {
            FMLLog.log(Level.SEVERE, ex, XAR_Ref.MOD_NAME + " has had a problem loading its configuration");
        }
        finally {
            config.save();
        }
		
		//language initialization is down here to make sure all blocks, items, etc are initialized first
		LocalizationHandler.load();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		//register event handlers
		//MinecraftForge.TERRAIN_GEN_BUS.register(new TerrainGenEventHandler());
		
		Recipes.init();
		Entities.init();
		
		//register tile entities and other rendering
		proxy.initRenderers();
        NetworkRegistry.instance().registerConnectionHandler(new XARUpdateHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		//used for working with other mods
	}
}