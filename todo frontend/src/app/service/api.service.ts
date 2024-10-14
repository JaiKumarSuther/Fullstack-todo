import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  getTodos(): any {
    return this.http.get('http://localhost:8080/todo');
  }

  addTodo(obj: any) {
    return this.http.post('http://localhost:8080/todo', obj);
  }

  deleteTask(id: any) {
    return this.http.delete('http://localhost:8080/todo/'+id)
  }

  editTask(obj: any, id: any) {
    return this.http.put('http://localhost:8080/todo/'+id, obj, id);
  }
}
