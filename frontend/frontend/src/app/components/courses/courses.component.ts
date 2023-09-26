import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup} from "@angular/forms";
import {createLogErrorHandler} from "@angular/compiler-cli/ngcc/src/execution/tasks/completion";

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})

export class CoursesComponent implements OnInit {

  searchFormGroup!:FormGroup;
  constructor(private modalService: NgbModal,private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword:this.fb.control('')
    })
  }


  getModal(content: any) {
    this.modalService.open(content, {size: 'xl'})
    console.log("Hello world")
  }


  handleSearchCourses() {
    // send a http request to the backend to get the courses


  }
}
