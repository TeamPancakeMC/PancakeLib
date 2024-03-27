package cn.teampancake.pancakelib.common.init;

import cn.teampancake.pancakelib.PancakeLib;
import cn.teampancake.pancakelib.common.capability.LastInventoryCap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModCapability {
    public static final Capability<LastInventoryCap> LAST_INVENTORY = CapabilityManager.get(new CapabilityToken<>() {});
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(LastInventoryCap.class);
    }
    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof Player player) {
            event.addCapability(PancakeLib.asResource("last_inventory"), new LastInventoryCap.Provider(player));
        }
    }
}
