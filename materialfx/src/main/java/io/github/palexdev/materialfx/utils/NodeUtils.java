package io.github.palexdev.materialfx.utils;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class which provides convenience methods for working with Nodes
 */
public class NodeUtils {

    private NodeUtils() {
    }

    /**
     * Changes the background color of a {@code Region} to the desired one.
     * @param region The region to change the background color to
     * @param fill The desired color
     */
    public static void updateBackground(Region region, Paint fill) {
        final Background background = region.getBackground();
        if (background == null || background.getFills().isEmpty()) {
            return;
        }

        final List<BackgroundFill> fills = new ArrayList<>();
        for (BackgroundFill bf : background.getFills()) {
            fills.add(new BackgroundFill(fill, bf.getRadii(), bf.getInsets()));
        }

        region.setBackground(new Background(fills.toArray(BackgroundFill[]::new)));
    }

    /**
     * Changes the background color of a {@code Region} to the desired one and lets specify the background insets.
     * @param region The region to change the background color to
     * @param fill The desired color
     * @param backgroundInsets The background insets to use
     */
    public static void updateBackground(Region region, Paint fill, Insets backgroundInsets) {
        final Background background = region.getBackground();
        if (background == null || background.getFills().isEmpty()) {
            return;
        }

        final List<BackgroundFill> fills = new ArrayList<>();
        for (BackgroundFill bf : background.getFills()) {
            fills.add(new BackgroundFill(fill, bf.getRadii(), backgroundInsets));
        }

        region.setBackground(new Background(fills.toArray(BackgroundFill[]::new)));
    }

    /**
     * Centers the specified node in an {@code AnchorPane}.
     */
    public static void centerNodeInAnchorPane(Node node, double topBottom, double leftRight) {
        AnchorPane.setTopAnchor(node, topBottom);
        AnchorPane.setBottomAnchor(node, topBottom);
        AnchorPane.setLeftAnchor(node, leftRight);
        AnchorPane.setRightAnchor(node, leftRight);
    }

    /**
     * Checks if the specified element is in the hierarchy of the specified node.
     */
    public static boolean inHierarchy(Node node, Node element) {
        if (element == null) {
            return true;
        }
        while (node != null) {
            if (node == element) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }

    /**
     * Makes the given region circular.
     * <p>
     * <b>Notice: the region's pref width and height must be set and be equals</b>
     * @param region The given region
     */
    public static void makeRegionCircular(Region region) {
        Circle circle = new Circle(region.getPrefWidth() / 2.0);
        circle.centerXProperty().bind(region.widthProperty().divide(2.0));
        circle.centerYProperty().bind(region.heightProperty().divide(2.0));
        try {
            region.setClip(circle);
        } catch (IllegalArgumentException ex) {
            LoggingUtils.logException("Could not set region's clip to make it circular", ex);
        }
    }

    /**
     * Makes the given region circular with the specified radius.
     * <p>
     * <b>Notice: the region's pref width and height must be set and be equals</b>
     * @param region The given region
     * @param radius The wanted radius
     */
    public static void makeRegionCircular(Region region, double radius) {
        Circle circle = new Circle(radius);
        circle.centerXProperty().bind(region.widthProperty().divide(2.0));
        circle.centerYProperty().bind(region.heightProperty().divide(2.0));
        try {
            region.setClip(circle);
        } catch (IllegalArgumentException ex) {
            LoggingUtils.logException("Could not set region's clip to make it circular", ex);
        }
    }

    /* The next two methods are copied from com.sun.javafx.scene.control.skin.Utils class
     * It's a private module, so to avoid adding exports and opens I copied them
     */
    public static double computeXOffset(double width, double contentWidth, HPos hpos) {
        switch (hpos) {
            case LEFT:
                return 0;
            case CENTER:
                return (width - contentWidth) / 2;
            case RIGHT:
                return width - contentWidth;
        }
        return 0;
    }

    public static double computeYOffset(double height, double contentHeight, VPos vpos) {

        switch (vpos) {
            case TOP:
                return 0;
            case CENTER:
                return (height - contentHeight) / 2;
            case BOTTOM:
                return height - contentHeight;
            default:
                return 0;
        }
    }
}
