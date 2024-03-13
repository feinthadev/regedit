package com.feintha.regedit;

import com.feintha.regedit.i._IRegistrable;
import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class RegistryManipulation {
    public static class Manipulator{

        private static Unsafe unsafe;

        static{
            try{
                final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                unsafe = (Unsafe) unsafeField.get(null);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        static void setFinalStatic(Field field, Object value) throws Exception{
            Object fieldBase = unsafe.staticFieldBase(field);
            long fieldOffset = unsafe.staticFieldOffset(field);

            unsafe.putObject(fieldBase, fieldOffset, value);
        }
        static <T> Field getFieldForElement(Class parent, T element) throws IllegalAccessException {
            try {
                for (Field field : parent.getFields()) {
                    if (field.get(null) == element) {
                        return field;
                    }
                }
            } catch (Exception e) {
                return null;
            }
            return null;
        }
        static final HashMap<Class<?>, Class<?>> CLASS_TO_HOLDER_MAPPING = new HashMap<>(Map.of(
                Item.class, Items.class,
                Block.class, Blocks.class
        ));
        public final <T> void Redirect(@NotNull Class<?> HolderClass, @NotNull Registry<T> registry, @NotNull T VANILLA, @NotNull T CUSTOM) {
            ((_IRegistrable<T>)CUSTOM).setEntry(registry.createEntry(CUSTOM));
            var ref = ((SimpleRegistry<T>)registry).set(registry.getRawId(VANILLA), ((_IRegistrable<T>)VANILLA).getRegistryEntry().registryKey(), CUSTOM, Lifecycle.stable());
            try {
                var f = getFieldForElement(HolderClass, VANILLA);
                if (f == null) {return;} // ignore if not in net.minecraft.--.Items.class;
                setFinalStatic(f, CUSTOM);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        @SuppressWarnings("unchecked")
        public final <T> void Redirect(@NotNull Registry<T> registry, @NotNull T VANILLA, @NotNull T CUSTOM) {
            var cls = VANILLA.getClass();
            ((_IRegistrable<T>)CUSTOM).setEntry(registry.createEntry(CUSTOM));
            var ref = ((SimpleRegistry<T>)registry).set(registry.getRawId(VANILLA), ((_IRegistrable<T>)VANILLA).getRegistryEntry().registryKey(), CUSTOM, Lifecycle.stable());
            CLASS_TO_HOLDER_MAPPING.forEach((elTypeCls, elHolderCls) -> {
                if (elTypeCls.isAssignableFrom(VANILLA.getClass())) {
                    try {
                        var f = getFieldForElement(elHolderCls, VANILLA);
                        if (f == null) {return;} // ignore if not in net.minecraft.--.Items.class;
                        setFinalStatic(f, CUSTOM);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}
