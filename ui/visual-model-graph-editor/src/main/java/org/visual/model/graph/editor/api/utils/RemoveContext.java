package org.visual.model.graph.editor.api.utils;

import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Collection;
import org.eclipse.emf.ecore.EObject;


/**
 * Context to keep track of the objects to be deleted in a remove operation.
 * This is necessary to prevent creation of remove commands removing the same
 * objects multiple times which will lead to errors.
 *
 * @since 15.02.2019
 */
public final class RemoveContext {

    private final Collection<EObject> objectsToDelete = new ObjectArraySet<>();

    /**
     * Constructor
     *
     * @since 15.02.2019
     */
    public RemoveContext() {
        // Auto-generated constructor stub
    }

    /**
     * @param pToCheck {@link EObject} to check
     * @return {@code true} if no other involved party has created a delete
     * command for the given object otherwise {@code false}
     * @since 15.02.2019
     */
    public boolean canRemove(final EObject pToCheck) {
        return objectsToDelete.add(pToCheck);
    }

    /**
     * @param pToCheck {@link EObject} to check
     * @return {@code true} any involved party has created a delete command for
     * the given object otherwise {@code false}
     * @since 15.02.2019
     */
    public boolean contains(final EObject pToCheck) {
        return objectsToDelete.contains(pToCheck);
    }
}
