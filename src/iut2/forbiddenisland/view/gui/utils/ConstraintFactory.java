package iut2.forbiddenisland.view.gui.utils;

import java.awt.GridBagConstraints;

public class ConstraintFactory {

    public static GridBagConstraints create(final int gridX, final int gridY) {
        final GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridX;
        constraints.gridy = gridY;

        return constraints;
    }

    public static GridBagConstraints create(final int gridX, final int gridY, final int width, final int height) {
        final GridBagConstraints constraints = create(gridX, gridY);
        constraints.weightx = width;
        constraints.weighty = height;

        return constraints;
    }

    public static GridBagConstraints create(final int gridX, final int gridY, final int fill) {
        final GridBagConstraints constraints = create(gridX, gridY);
        constraints.fill = fill;

        return constraints;
    }

    public static GridBagConstraints create(final int gridX, final int gridY, final int width, final int height, final int fill) {
        final GridBagConstraints constraints = create(gridX, gridY, width, height);
        constraints.fill = fill;

        return constraints;
    }

    public static GridBagConstraints fillBoth(final int gridX, final int gridY, final int width, final int height) {
        return create(gridX, gridY, width, height, GridBagConstraints.BOTH);
    }

    public static GridBagConstraints fillVertical(final int gridX, final int gridY, final int with, final int height) {
        return create(gridX, gridY, with, height, GridBagConstraints.VERTICAL);
    }

    public static GridBagConstraints fillHorizontal(final int gridX, final int gridY, final int width, final int height) {
        return create(gridX, gridY, width, height, GridBagConstraints.HORIZONTAL);
    }
}
