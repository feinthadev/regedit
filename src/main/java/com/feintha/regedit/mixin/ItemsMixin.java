package com.feintha.regedit.mixin;


import com.feintha.regedit.RedirectedItem;
import com.feintha.regedit.i._IRegistrable;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public class ItemsMixin {
    @Mixin(Item.class)
    public static class ItemMixin implements _IRegistrable<Item> {
        @Mutable
        @Shadow @Final private RegistryEntry.Reference<Item> registryEntry;

        @Redirect(method="<init>", at=@At(value = "INVOKE", target = "Lnet/minecraft/registry/DefaultedRegistry;createEntry(Ljava/lang/Object;)Lnet/minecraft/registry/entry/RegistryEntry$Reference;"))
        RegistryEntry.Reference<Item> redirectEntryCreation(DefaultedRegistry<Item> instance, Object o){
            if (o instanceof RedirectedItem) { return null; }
            return instance.createEntry((Item) o);
        }
        @Override
        public void setEntry(RegistryEntry.Reference<Item> reference) {
            registryEntry = reference;
        }

        @Override
        public RegistryEntry.Reference<Item> getRegistryEntry() {
            return registryEntry;
        }
    }
}
