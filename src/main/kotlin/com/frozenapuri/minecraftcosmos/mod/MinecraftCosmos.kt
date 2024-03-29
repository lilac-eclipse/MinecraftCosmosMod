package com.frozenapuri.minecraftcosmos.mod

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent
import net.minecraftforge.fml.event.server.FMLServerStartingEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager

// The value here should match an entry in the META-INF/mods.toml file
@Mod("minecraftcosmos")
class MinecraftCosmos {
    init {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListener<FMLCommonSetupEvent> { this.setup(it) }
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListener<InterModEnqueueEvent> { this.enqueueIMC(it) }
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListener<InterModProcessEvent> { this.processIMC(it) }
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().modEventBus.addListener<FMLClientSetupEvent> { this.doClientStuff(it) }

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this)
    }

    private fun setup(event: FMLCommonSetupEvent) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT")
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.registryName)
    }

    private fun doClientStuff(event: FMLClientSetupEvent) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.minecraftSupplier.get().gameSettings)
    }

    private fun enqueueIMC(event: InterModEnqueueEvent) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("minecraftcosmos", "helloworld") {
            LOGGER.info("Hello world from the MDK")
            "Hello world"
        }
    }

    private fun processIMC(event: InterModProcessEvent) {
        // some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.imcStream
//                .map { it.getMessageSupplier<Any>().get() }
//                .collect<List<Any>, Any>(Collectors.toList()))
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    fun onServerStarting(event: FMLServerStartingEvent) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting")
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    object RegistryEvents {
        @SubscribeEvent
        fun onBlocksRegistry(blockRegistryEvent: RegistryEvent.Register<Block>) {
            // register a new block here
            LOGGER.info("HELLO from Register Block")
        }
    }

    companion object {
        // Directly reference a log4j logger.
        private val LOGGER = LogManager.getLogger()
    }
}
