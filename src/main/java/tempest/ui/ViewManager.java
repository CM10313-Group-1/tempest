package tempest.ui;

import tempest.ui.pages.ManageModulesPage;
import tempest.ui.pages.PageNames;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.JPanel;

public class ViewManager<T extends View> extends JPanel {
  private static final long serialVersionUID = -7163717383346091663L;

  private CardLayout layout = new CardLayout();
  private HashMap<String, T> views = new HashMap<String, T>();
  private Stack<String> history = new Stack<String>();

  public ViewManager(T[] views, T initialView) {
    super();
    this.setLayout(layout);

    for (T view : views) {
      this.views.put(view.getName(), view);
      this.add(view, view.getName());
    }

    layout.show(this, initialView.getName());
    history.push(initialView.getName());
  }

  /**
   * Changes the visible {@link View} to the one named.
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
   * Changes the visible {@link View} to the previous one.
   */
  public void changeToPrevious() {
    history.pop();
    String lastView = history.peek();
    // Updates the delete module button
    if (!viewExists(lastView))
      System.err.println("Invalid State");
    else if (lastView.equals(PageNames.MANAGE_MODULES)) {
      ManageModulesPage p = (ManageModulesPage) views.get(lastView);
      p.update();
    }
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
    return views.containsKey(name);
  }

  /**
   * Finds the first instance of a particular class in the possible views.
   * 
   * @param classObject The class to be found.
   * @return The view, if it exists; else null.
   */
  public T getView(Class<? extends T> classObject) {
    for (T view : views.values()) {
      if (view.getClass() == classObject)
        return view;
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
  public String getViewName(Class<? extends T> classObject) {
    return getView(classObject).getName();
  }
}
