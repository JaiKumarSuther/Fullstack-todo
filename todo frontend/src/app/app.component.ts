import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';
import { Component, inject, OnInit } from '@angular/core';
import { ApiService } from './service/api.service';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule, CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {

  
  apiService: ApiService = inject(ApiService);
  todoList: any;

  http = inject(HttpClient);

  ngOnInit() {
    this.getAllTodos();
    console.log("object");
    console.log(this.profileForm.value.todo);
  }
  getAllTodos() {
    this.http.get('http://localhost:8080/todo').subscribe(
      (res: any) => {
        this.todoList = res;
      }
    );
  }


  profileForm = new FormGroup({
    todo: new FormControl(''),

  });



  addTask() {
    if(this.profileForm.value.todo !== '') {
      this.apiService.addTodo({"task": this.profileForm.value.todo}).subscribe(
        (res) => {
          alert("successfully added!");
          this.getAllTodos();
          this.profileForm.value.todo = '';
        }
      )
    } else {
      alert("field is empty!!");
    }
  }

  deleteTask(id: any) {
    this.apiService.deleteTask(id).subscribe(
      (res) => {
        console.log("deleted successfully");
        this.getAllTodos();
      }
    );
  }

  toEdit: boolean =  false;
  index: any;
  editTodoTask(i: any) {
    this.index = i;
    this.toEdit = true;
  }

  saveTodo(obj: any, index: any){
    if(this.profileForm.value.todo) {
      obj.task = this.profileForm.value.todo;
      this.apiService.editTask(obj, index).subscribe(
        (res) => {
          alert('edited successfully');
          this.toEdit = false;
          this.profileForm.value.todo = '';
        }
      )
    } else {
      alert("it can't be empty")
    }
  }
}
