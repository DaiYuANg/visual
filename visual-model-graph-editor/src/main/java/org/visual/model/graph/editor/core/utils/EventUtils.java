package org.visual.model.graph.editor.core.utils;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Helper methods for JavaFX event handling
 */
public class EventUtils
{

    public static <N extends Node, T extends Event> void removeEventHandlers(final @NotNull Map<N, EventHandler<T>> pEventHandlers,
            final EventType<T> pType)
    {
        for (final Iterator<Entry<N, EventHandler<T>>> iter = pEventHandlers.entrySet().iterator(); iter.hasNext();)
        {
            final Entry<N, EventHandler<T>> next = iter.next();
            final N node = next.getKey();
            final EventHandler<T> eventHandler = next.getValue();
            if (node != null && eventHandler != null)
            {
                node.removeEventHandler(pType, eventHandler);
            }
            iter.remove();
        }
    }

}
