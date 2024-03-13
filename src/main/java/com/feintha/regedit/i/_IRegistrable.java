package com.feintha.regedit.i;

import net.minecraft.registry.entry.RegistryEntry;

public interface  _IRegistrable<T> {
    void setEntry(RegistryEntry.Reference<T> reference);
    RegistryEntry.Reference<T> getRegistryEntry();
}
