import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup } from '@angular/forms';
import { catchError, Observable, throwError } from 'rxjs';
import { PageResponse } from '../../model/page.response.model';
import { Student } from '../../model/student.model';
import { StudentsService } from '../../services/students.service';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css'],
})
export class StudentsComponent implements OnInit {
  searchFormGroup!: FormGroup;
  studentFormGroup!: FormGroup;
  pageStudents$!: Observable<PageResponse<Student>>;
  students$!: Observable<Array<Student>>;
  errorMessage!: string;
  submitted: boolean = false;
  currentPage: number = 0;
  pageSize: number = 3;
  errorStudentMessage!: string;

  constructor(
    private modalService: NgbModal,
    private fb: FormBuilder,
    private studentService: StudentsService
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    this.handleSearchStudents();
  }

  getModal(content: any) {
    this.submitted = false;

    this.modalService.open(content, { size: 'xl' });
  }

  handleSearchStudents() {
    let keyword = this.searchFormGroup.value.keyword;
    this.pageStudents$ = this.studentService
      .searchStudents(keyword, this.currentPage, this.pageSize)
      .pipe(
        catchError((err) => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchStudents();
  }

  handleDeleteStudent(s: Student) {
    let conf = confirm('Are you sure?');
    if (!conf) return;
    this.studentService.deleteStudent(s.studentId).subscribe({
      next: () => {
        this.handleSearchStudents();
      },
      error: (err) => {
        alert(err.message);
        console.log(err);
      },
    });
  }
}
