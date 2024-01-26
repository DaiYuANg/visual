package org.visual.model.graph.editor.core.connections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.visual.model.graph.editor.model.GConnection;
import org.visual.model.graph.editor.model.GConnector;
import org.visual.model.graph.editor.model.GNode;


/**
 * Helper methods to copy {@link GConnection connections}
 */
public final class ConnectionCopier
{

    /**
     * Static class.
     */
    private ConnectionCopier()
    {
    }

    /**
     * Copies connection information from one set of nodes to another.
     *
     * <p>
     * Connections between nodes in the <b>keys</b> of the input map are copied
     * (including their joint information) and the new connections are set
     * inside the corresponding nodes in the <b>values</b> of the input map.
     * </p>
     *
     * <p>
     * The new connection information is set <em>directly</em>. EMF commands are
     * not used
     * </p>
     *
     * @param copies
     *            a map that links source nodes to their copies in its key-value
     *            pairs
     * @return the list of created connections
     */
    @Contract("_ -> new")
    public static @NotNull List<GConnection> copyConnections(final @NotNull Map<GNode, GNode> copies)
    {
        final Map<GConnection, GConnection> copiedConnections = new HashMap<>();

        for (final GNode node : copies.keySet())
        {
            final GNode copy = copies.get(node);

            for (final GConnector connector : node.getConnectors())
            {
                final int connectorIndex = node.getConnectors().indexOf(connector);
                final GConnector copiedConnector = copy.getConnectors().get(connectorIndex);

                copiedConnector.getConnections().clear();

                for (final GConnection connection : connector.getConnections())
                {
                    final GNode opposingNode = getOpposingNode(connector, connection);

                    final boolean opposingNodePresent = copies.containsKey(opposingNode);

                    if (opposingNodePresent)
                    {
                        final GConnection copiedConnection;
                        if (!copiedConnections.containsKey(connection))
                        {
                            copiedConnection = EcoreUtil.copy(connection);
                            copiedConnections.put(connection, copiedConnection);
                        }
                        else
                        {
                            copiedConnection = copiedConnections.get(connection);
                        }

                        if (connection.getSource().equals(connector))
                        {
                            copiedConnection.setSource(copiedConnector);
                        }
                        else
                        {
                            copiedConnection.setTarget(copiedConnector);
                        }

                        copiedConnector.getConnections().add(copiedConnection);
                    }
                }
            }
        }

        return new ArrayList<>(copiedConnections.values());
    }

    /**
     * Gets the node on the other side of the connection to the given connector.
     *
     * @param connector
     *            a {@link GConnector} instance
     * @param connection
     *            a {@link GConnection} attached to this connector
     * @return the {@link GNode} on the other side of the connection, or
     *         {@code null} if none exists
     */
    private static @Nullable GNode getOpposingNode(final GConnector connector, final @NotNull GConnection connection)
    {
        GConnector opposingConnector;
        if (connection.getSource().equals(connector))
        {
            opposingConnector = connection.getTarget();
        }
        else
        {
            opposingConnector = connection.getSource();
        }

        if (opposingConnector != null && opposingConnector.getParent() != null)
        {
            return opposingConnector.getParent();
        }
        else
        {
            return null;
        }
    }
}
