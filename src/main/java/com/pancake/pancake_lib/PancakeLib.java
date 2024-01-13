package com.pancake.pancake_lib;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PancakeLib.MOD_ID)
public class PancakeLib {
    public static final String MOD_ID = "pancake_lib";
    public static final Logger LOGGER = LogUtils.getLogger();


    public PancakeLib() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
