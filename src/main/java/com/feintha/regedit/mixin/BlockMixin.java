package com.feintha.regedit.mixin;

import com.feintha.regedit.i._IRegistrable;
import net.minecraft.block.Block;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public class BlockMixin implements _IRegistrable<Block> {
    @Mutable
    @Shadow @Final private RegistryEntry.Reference<Block> registryEntry;

    @Override
    public void setEntry(RegistryEntry.Reference<Block> reference) {
        registryEntry = reference;
    }

    @Override
    public RegistryEntry.Reference<Block> getRegistryEntry() {
        return registryEntry;
    }
}
