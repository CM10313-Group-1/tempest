package tempest.ui;

import tempest.ui.pages.Page;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JPanel;

public class ViewManager extends JPanel {
    private static final long serialVersionUID = -7163717383346091663L;

    private final CardLayout layout = new ScalableCardLayout();
    private final HashMap<String, Page> pages = new HashMap<>();
    private final Stack<String> history = new Stack<>();

    public ViewManager(Page[] pages, Page initialView) {
        this.setLayout(layout);

        for (Page p : pages) {
            this.pages.put(p.getName(), p);
            this.add(p, p.getName());
        }

        layout.show(this, initialView.getName());
        history.push(initialView.getName());
    }

    /**
     * Changes the visible {@link Page} to the one named.
     *
     * @param name The name of the view to be switched to.
     */
    public void changeView(String name) {
        if (viewExists(name)) {
            layout.show(this, name);
            history.push(name);
        } else {
            System.err.println("The card/page you are trying to swap to doesn't exist");
        }
    }

    /**
     * Changes the visible {@link Page} to the previous one.
     */
    public void changeToPrevious() {
        history.pop();
        String lastView = history.peek();
        if (!viewExists(lastView))
            System.err.println("Invalid State");
        layout.show(this, lastView);
    }

    /**
     * Returns the name of the currently visible view.
     *
     * @return String The name of the currently visible view.
     */
    public String getVisible() {
        return history.peek();
    }

    /**
     * Checks if a given view exists in all possible.
     *
     * @param name The name of the view.
     * @return boolean true if the view exists.
     */
    private boolean viewExists(String name) {
        return pages.containsKey(name);
    }

    /**
     * Finds the first instance of a particular class in the possible views.
     *
     * @param classObject The class to be found.
     * @return The view, if it exists; else null.
     */
    public Page getView(Class<? extends Page> classObject) {
        for (Page p : pages.values()) {
            if (p.getClass() == classObject)
                return p;
        }

        System.err.println("Couldn't find a view of this class.");
        return null;
    }

    /**
     * Finds the name of the first instance of a particular class in the possible
     * views.
     *
     * @param classObject The class to be found.
     * @return The view, if it exists; else null.
     */
    public String getViewName(Class<? extends Page> classObject) {
        return getView(classObject).getName();
    }
}
