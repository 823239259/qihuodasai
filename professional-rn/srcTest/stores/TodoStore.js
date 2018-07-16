import { observable, action, computed } from 'mobx';

class Todo {
  @observable value;
  @observable id;
  @observable complete;

  constructor(value) {
    this.value = value;
    this.id = Date.now();
    this.complete = false;
  }
}
export default class TodoStore {
  @observable todos = [new Todo('buy eggs')];
  @observable filter = '';

  @computed get filteredTodos() {
    const matchedTodo = new RegExp(this.filter, 'i');
    return this.todos.filter((todo) => !this.filter || matchedTodo.test(todo.value));    
  }
  
  @action addTodo(value) {
    this.todos.push(new Todo(value));
  }

  @action toggleTodo(id) {
    const todos = this.todos;
    for (let i = 0; i < todos.length; i++) {
      if (todos[i].id === id) {
        todos[i].complete = !todos[i].complete;
        break;
      }
    }
  }
  @action clearComplete() {
    const TodosNotComplete = this.todos.filter((todo) => !todo.complete);
    this.todos.replace(TodosNotComplete);    
  }
}

