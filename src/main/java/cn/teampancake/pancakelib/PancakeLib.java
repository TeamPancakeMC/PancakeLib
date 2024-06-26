package cn.teampancake.pancakelib;

import com.mojang.logging.LogUtils;
import cn.teampancake.pancakelib.client.gui.hud.ModifierDeBugHudRenderer;
import cn.teampancake.pancakelib.client.tooltip.EquipmentSetTooltipComponent;
import cn.teampancake.pancakelib.common.config.ModConfig;
import cn.teampancake.pancakelib.common.init.ModEquipmentSet;
import cn.teampancake.pancakelib.common.init.ModModifiers;
import cn.teampancake.pancakelib.common.network.ModMessages;
import cn.teampancake.pancakelib.compat.curios.event.subscriber.CurioPlayerEventSubscriber;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(PancakeLib.MOD_ID)
public class PancakeLib {
    public static final String MOD_ID = "pancake_lib";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static boolean CURIOS_LOADED;


    public PancakeLib() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onFMLCommonSetup);

        if(FMLEnvironment.dist.isClient()){
            modEventBus.addListener(this::onRegisterClientTooltipComponentFactories);
        }

        ModModifiers.MODIFIER.register(modEventBus);
        ModEquipmentSet.EQUIPMENT_SET.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);



        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.SPEC);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ModifierDeBugHudRenderer::registerOverlay);
        }

        if (ModList.get().isLoaded("curios")){
            CURIOS_LOADED=true;
            MinecraftForge.EVENT_BUS.register(CurioPlayerEventSubscriber.class);
        }
    }

    @SubscribeEvent
    public void onFMLCommonSetup(FMLCommonSetupEvent event) {
        ModMessages.register();
        ModEquipmentSet.setup();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static <T> ResourceKey<Registry<T>> asResourceKey(String name) {
        return ResourceKey.createRegistryKey(asResource(name));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onRegisterClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(EquipmentSetTooltipComponent.class, equipmentSetTooltipComponent -> equipmentSetTooltipComponent);
    }
}
