package tempest.ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;

import tempest.ui.pages.PageNames;

//Coded with help of: https://stackoverflow.com/questions/8277834/how-to-set-a-jframe-size-to-fit-the-cardlayout-displayed-jpanel

public class ScalableCardLayout extends CardLayout {
    private static final long serialVersionUID = 7572233533967024176L;

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Component current = findCurrentComponent(parent);

        if (current.getName() != null) {
            String name = current.getName();

            for (String s : PageNames.getCharts()) {
                if (name.equals(s)) {
                    return dynamicGUI(parent, current);
                }
            }

            if (name.equals(PageNames.DELETE_SESSION)) {
                return dynamicGUI(parent, current);
            }
        }

        // The default frame size for panels
        return new Dimension(500, 150);
    }

    private Dimension dynamicGUI(Container parent, Component current) {
        Insets insets = parent.getInsets();
        Dimension pref = current.getPreferredSize();
        pref.width += insets.left + insets.right;
        pref.height += insets.top + insets.bottom;
        return pref;
    }

    public Component findCurrentComponent(Container parent) {
        for (Component comp : parent.getComponents()) {
            if (comp.isVisible()) {
                return comp;
            }
        }
        return null;
    }
}
