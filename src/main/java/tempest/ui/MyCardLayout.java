package tempest.ui;

import tempest.ui.pages.PageNames;

import java.awt.*;

//Coded with help of: https://stackoverflow.com/questions/8277834/how-to-set-a-jframe-size-to-fit-the-cardlayout-displayed-jpanel

public class MyCardLayout extends CardLayout {

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Component current = findCurrentComponent(parent);

        if (current.getName() != null) {
            String name = current.getName();

            System.out.println("Name : " + name);

            // Doesn't resize when line chart pressed

//            if (name.equals(PageNames.CHART_VIEW) || name.equals(PageNames.DELETE_SESSION)) {
//                Insets insets = parent.getInsets();
//                Dimension pref = current.getPreferredSize();
//                pref.width += insets.left + insets.right;
//                pref.height += insets.top + insets.bottom;
//                return pref;
//            }

            // Resizes correctly with graphs - but it means that when in Chart_View the box is very packed & tiny

            if (name.equals(PageNames.CHART_VIEW) || name.equals(PageNames.DELETE_SESSION) || name.equals("PieChart") || name.equals("LineChart") || name.equals("BarChart")) {
                Insets insets = parent.getInsets();
                Dimension pref = current.getPreferredSize();
                pref.width += insets.left + insets.right;
                pref.height += insets.top + insets.bottom;
                return pref;
            }
        }

        System.out.println(":(");

        return new Dimension(500, 150);
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
