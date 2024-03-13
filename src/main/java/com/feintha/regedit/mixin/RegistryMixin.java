package com.feintha.regedit.mixin;

import com.feintha.regedit.RegistryEditEvent;
import com.feintha.regedit.RegistryManipulation;
import net.minecraft.registry.Registries;
import net.minecraft.registry.SimpleRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleRegistry.class)
public abstract class RegistryMixin<T>  {
    @Mixin(Registries.class)
    public static class BootstrapMixin{
        @Inject(method="bootstrap", at=@At(value = "INVOKE", target = "Lnet/minecraft/registry/Registries;freezeRegistries()V", shift = At.Shift.BEFORE))
        private static void bootstrapFreezeMixin(CallbackInfo ci){
            RegistryEditEvent.EVENT.invoker().manipulate(new RegistryManipulation.Manipulator());
        }
    }

}
