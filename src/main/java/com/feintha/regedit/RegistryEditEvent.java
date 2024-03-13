package com.feintha.regedit;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface RegistryEditEvent {
    Event<RegistryEditEvent> EVENT = EventFactory.createArrayBacked(RegistryEditEvent.class,
            (listeners) ->
                    (manipulator) -> {
                        for (RegistryEditEvent listener : listeners) {
                            listener.manipulate(manipulator);
                        }
                    }
    );
    void manipulate(RegistryManipulation.Manipulator manipulator);
}
