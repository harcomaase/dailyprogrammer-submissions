
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TodoList {

  public static void main(String[] args) {
    TodoList todoList = new TodoList();
    
    todoList.addItem("Take a shower");
    todoList.addItem("Go to work");
    todoList.viewList();
    
    todoList.addItem("Buy a new phone");
    todoList.deleteItem("Go to work");
    todoList.viewList();
  }

  private final List<Todo> todos;

  public TodoList() {
    todos = new ArrayList<>();
  }

  public void addItem(final String name) {
    todos.add(new Todo(name));
  }

  public void deleteItem(final String name) {
    if (name == null) {
      return;
    }
    todos.removeAll(todos.stream().filter(todo -> name.equals(todo.toString())).collect(Collectors.toList()));
  }

  public void viewList() {
    todos.forEach(System.out::println);
  }

  private class Todo {

    private final String name;

    public Todo(final String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }
}
