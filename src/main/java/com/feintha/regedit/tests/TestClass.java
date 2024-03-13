package com.feintha.regedit.tests;

import com.feintha.regedit.RegistryEditEvent;
import com.feintha.regedit.RegistryManipulation;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.BoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class TestClass implements ModInitializer {
    @Override
    public void onInitialize() {
//        RegistryEditEvent.EVENT.register(manipulator -> manipulator.Redirect(Registries.ITEM, Items.ACACIA_BOAT, new Item(new Item.Settings())));
//        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
//            dispatcher.register(CommandManager.literal("test_boat").executes(context -> {
//                context.getSource().sendMessage(Text.of(Items.ACACIA_BOAT instanceof BoatItem ? "Boat (FAILED)" : "Not boat (SUCCEEDED!)"));
//                return 0;
//            }));
//        });
    }
}
