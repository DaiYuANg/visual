/*
 * Copyright (C) 2005 - 2014 by TESIS DYNAware GmbH
 */
package org.visual.model.graph.editor.api.utils;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.visual.model.graph.editor.api.GConnectorSkin;
import org.visual.model.graph.editor.api.GJointSkin;
import org.visual.model.graph.editor.api.GNodeSkin;
import org.visual.model.graph.editor.api.SkinLookup;
import org.visual.model.graph.editor.model.GConnection;
import org.visual.model.graph.editor.model.GConnector;
import org.visual.model.graph.editor.model.GJoint;
import org.visual.model.graph.editor.model.GNode;

/**
 * Utility class containing helper methods relating to geometry, positions, etc.
 */
public class GeometryUtils {

    private static final double HALF_A_PIXEL = 0.5;

    /**
     * Gets the position of the <b>center</b> of a connector in the coordinate system of the view.
     *
     * <p>
     * Only works for connectors that are attached to nodes.
     * <p>
     *
     * @param connector  the {@link GConnector} whose position is desired
     * @param skinLookup the {@link SkinLookup} instance for this graph editor
     * @return the x and y coordinates of the connector, or {@code null} if the connector isn't attached to a node
     */
    public static Point2D getConnectorPosition(final GConnector connector, final SkinLookup skinLookup) {

        final GConnectorSkin connectorSkin = skinLookup.lookupConnector(connector);
        final GNode parent = connector.getParent();

        final GNodeSkin nodeSkin = skinLookup.lookupNode(parent);
        if (nodeSkin == null) {
            return null;
        }

        nodeSkin.layoutConnectors();

        final double nodeX = nodeSkin.getRoot().getLayoutX();
        final double nodeY = nodeSkin.getRoot().getLayoutY();

        final Point2D connectorPosition = nodeSkin.getConnectorPosition(connectorSkin);

        final double connectorX = connectorPosition.getX();
        final double connectorY = connectorPosition.getY();

        return new Point2D(moveOnPixel(nodeX + connectorX), moveOnPixel(nodeY + connectorY));
    }

    /**
     * Gets the position of the cursor relative to some node.
     *
     * @param event a {@link MouseEvent} storing the cursor position
     * @param node  some {@link Node}
     * @return the position of the cursor relative to the node origin
     */
    public static @NotNull Point2D getCursorPosition(final @NotNull MouseEvent event, final @NotNull Node node) {
        final double sceneX = event.getSceneX();
        final double sceneY = event.getSceneY();

        final Point2D containerScene = node.localToScene(0, 0);
        return new Point2D(sceneX - containerScene.getX(), sceneY - containerScene.getY());
    }

    /**
     * Gets the layout x and y values from all joints in a list of joint skins.
     *
     * @param jointSkins a list of joint skin instances
     * @return a {@link List} of {@link Point2D} objects containing joint x and y values
     */
    public static @NotNull List<Point2D> getJointPositions(final @NotNull List<GJointSkin> jointSkins) {

        final List<Point2D> jointPositions = new ArrayList<>(jointSkins.size());

        jointSkins.forEach(jointSkin -> {
            final Region region = jointSkin.getRoot();
            final double x = region.getLayoutX() + jointSkin.getWidth() / 2;
            final double y = region.getLayoutY() + jointSkin.getHeight() / 2;
            jointPositions.add(new Point2D(x, y));
        });

        return jointPositions;
    }

    /**
     * Gets the layout x and y values from all joints within a connection.
     *
     * <p>
     * Uses the JavaFX properties of the skins, not the model values. Is
     * therefore always up-to-date, even during a drag gesture where the model
     * is not necessarily updated.
     * <p>
     *
     * @param connection the {@link GConnection} for which the positions are desired
     * @param skinLookup the {@link SkinLookup} instance for this graph editor
     * @param pTarget    the array where to write the points to
     */
    public static void fillJointPositions(final @NotNull GConnection connection, final SkinLookup skinLookup, final Point2D[] pTarget) {
        IntStream.range(0, connection.getJoints().size()).forEach(i -> {
            val joint = connection.getJoints().get(i);
            pTarget[i + 1] = getJointPosition(joint, skinLookup);
        });
    }

    /**
     * Gets the layout x and y values from all joints within a connection.
     *
     * <p>
     * Uses the JavaFX properties of the skins, not the model values. Is
     * therefore always up-to-date, even during a drag gesture where the model
     * is not necessarily updated.
     * <p>
     *
     * @param connection the {@link GConnection} for which the positions are desired
     * @param skinLookup the {@link SkinLookup} instance for this graph editor
     * @return a {@link List} of {@link Point2D} objects containing joint x and
     * y values
     */
    public static @NotNull List<Point2D> getJointPositions(final @NotNull GConnection connection, final SkinLookup skinLookup) {
        return connection.getJoints()
                .stream()
                .map(joint -> getJointPosition(joint, skinLookup))
                .collect(Collectors.toCollection(() -> new ObjectArrayList<>(connection.getJoints().size())));
    }

    /**
     * Gets the layout x and y values from all joints within a connection.
     *
     * <p>
     * Uses the JavaFX properties of the skins, not the model values. Is
     * therefore always up-to-date, even during a drag gesture where the model
     * is not necessarily updated.
     * <p>
     *
     * @param joint      the {@link GJoint} for which the position is desired
     * @param skinLookup the {@link SkinLookup} instance for this graph editor
     * @return {@link Point2D} object containing joint x and y values
     */
    public static @NotNull Point2D getJointPosition(final GJoint joint, final @NotNull SkinLookup skinLookup) {
        final GJointSkin jointSkin = skinLookup.lookupJoint(joint);
        final Region region = jointSkin.getRoot();

        final double x = region.getLayoutX() + jointSkin.getWidth() / 2;
        final double y = region.getLayoutY() + jointSkin.getHeight() / 2;

        return new Point2D(x, y);
    }

    /**
     * Gets the x and y values from all joints within a connection.
     *
     * @param connection a {@link GConnection} instance
     * @return a {@link List} of {@link Point2D} objects containing joint x and y values
     */
    public static @NotNull List<Point2D> getJointPositions(final @NotNull GConnection connection) {

        return connection.getJoints().stream()
                .map(joint -> new Point2D(joint.getX(), joint.getY()))
                .collect(Collectors.toCollection(() -> new ObjectArrayList<>(connection.getJoints().size())));
    }

    /**
     * Moves an x or y position value on-pixel.
     *
     * <p>
     * Lines drawn off-pixel look blurry. They should therefore have integer x and y values.
     * </p>
     *
     * @param position the position to move on-pixel
     * @return the position rounded to the nearest integer
     */
    public static double moveOnPixel(final double position) {
        return Math.ceil(position);
    }

    /**
     * Moves an x or y position value off-pixel.
     *
     * <p>
     * This is for example useful for a 1-pixel-wide stroke with a stroke-type of centered. The x and y positions need
     * to be off-pixel so that the stroke is on-pixel.
     * </p>
     *
     * @param position the position to move off-pixel
     * @return the position moved to the nearest value halfway between two integers
     */
    public static double moveOffPixel(final double position) {
        return Math.ceil(position) - HALF_A_PIXEL;
    }

    /**
     * Checks if the given position is between two values.
     *
     * <p>
     * Also returns true if the given position is equal to either of the values.
     * </p>
     *
     * @param firstValue  an x or y position value
     * @param secondValue another x or y position value
     * @param position    the cursor's position value
     * @return {@code true} if the cursor position is between the two points
     */
    public static boolean checkInRange(final double firstValue, final double secondValue, final double position) {

        if (secondValue >= firstValue) {
            return firstValue <= position && position <= secondValue;
        }
        // ELSE:
        return secondValue <= position && position <= firstValue;
    }

    /**
     * Checks if a horizontal line segment AB intersects with a vertical line segment CD.
     *
     * @param a start of line segment AB
     * @param b end of line segment AB
     * @param c start of line segment CD
     * @param d end of line segment CD
     * @return {@code true} if AB and CD intersect, {@code false} otherwise
     */
    public static boolean checkIntersection(final @NotNull Point2D a, final Point2D b, final Point2D c, final Point2D d) {

        if (!(c.getX() > a.getX() && c.getX() < b.getX()) && !(c.getX() > b.getX() && c.getX() < a.getX())) {
            return false;
        }

        return a.getY() > c.getY() && a.getY() < d.getY() || a.getY() > d.getY() && a.getY() < c.getY();
    }

    /**
     * Rounds some value to the nearest multiple of the grid spacing.
     *
     * @param pProperties {@link GraphEditorProperties} or {@code null}
     * @param pValue      a double value
     * @return the input value rounded to the nearest multiple of the grid
     * spacing
     */
    public static double roundToGridSpacing(final GraphEditorProperties pProperties, final double pValue) {
        if (pProperties == null || !pProperties.isSnapToGridOn()) {
            return pValue;
        }
        val spacing = pProperties.getGridSpacing();
        return spacing * Math.round(pValue / spacing);
    }
}
