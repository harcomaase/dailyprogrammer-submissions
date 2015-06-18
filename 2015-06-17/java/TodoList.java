
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TodoList {

  public static void main(String[] args) {
    TodoList todoList = new TodoList();

    todoList.addItem("A pixel is not a pixel is not a pixel", "Programming");
    todoList.addItem("The Scheme Programming Language", "Programming");
    todoList.addItem("Memory in C", "Programming");
    todoList.addItem("Haskell's School of Music", "Programming");
    todoList.addItem("Algorithmic Symphonies from one line of code", "Programming");

    todoList.addItem("Modes in Folk Music", "Music");
    todoList.addItem("The use of the Melodic Minor Scale", "Music");
    todoList.addItem("Haskell's School of Music", "Music");
    todoList.addItem("Algorithmic Symphonies from one line of code", "Music");

    todoList.addItem("Haskell's School of Music", "Music", "Programming");
    todoList.addItem("Algorithmic Symphonies from one line of code", "Music", "Programming");
    todoList.addItem("Create Sine Waves in C", "Music", "Programming");

    todoList.viewList();
    
    todoList.updateItem("Create Sine Waves in C", "Create Sine Waves in Python");

    System.out.println(" ======= NEW LIST =======");
    todoList.viewList();
  }

  private final List<Todo> todos;

  public TodoList() {
    todos = new ArrayList<>();
  }

  public void addItem(final String name, final String... categories) {
    if (invalidString(name)) {
      return;
    }
    todos.add(new Todo(name, categories));
  }

  public void updateItem(final String oldName, final String newName) {
    if (invalidString(oldName, newName)) {
      return;
    }
    todos.stream().filter(todo -> oldName.equals(todo.getName())).limit(1L).forEach((Todo todo) -> todo.setName(newName));
  }

  public void deleteItem(final String name) {
    if (invalidString(name)) {
      return;
    }
    todos.removeAll(todos.stream().filter(todo -> name.equals(todo.getName())).collect(Collectors.toList()));
  }

  public void viewList() {
    todos.stream().map(todo -> todo.getCategories().toString()).distinct().forEach(category -> {
      System.out.println("----- " + category + " -----");
      todos.stream().filter(todo -> todo.getCategories().toString().equals(category)).forEach(System.out::println);
      System.out.println();
    });
  }

  private boolean invalidString(String... strings) {
    for (String string : strings) {
      if (string == null || string.isEmpty()) {
        return true;
      }
    }
    return false;
  }

  private class Todo {

    private String name;
    private final List<String> categories;

    public Todo(final String name, final String... categories) {
      this.name = name;
      this.categories = Arrays.asList(categories);
      if (this.categories.isEmpty()) {
        this.categories.add("DEFAULT");
      }
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public List<String> getCategories() {
      return categories;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
