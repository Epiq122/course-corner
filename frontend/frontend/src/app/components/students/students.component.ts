import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError, Observable, throwError } from 'rxjs';
import { PageResponse } from '../../model/page.response.model';
import { Student } from '../../model/student.model';
import { StudentsService } from '../../services/students.service';
import { EmailExistsValidator } from '../../validators/emailexists.validator';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css'],
})
export class StudentsComponent implements OnInit {
  searchFormGroup!: FormGroup;
  studentFormGroup!: FormGroup;
  pageStudents$!: Observable<PageResponse<Student>>;
  errorMessage!: string;
  submitted: boolean = false;
  currentPage: number = 0;
  pageSize: number = 5;

  constructor(
    private modalService: NgbModal,
    private fb: FormBuilder,
    private studentService: StudentsService,
    private userService: UsersService
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    this.studentFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      level: ['', Validators.required], // this is because its technically creating a new user
      user: this.fb.group({
        email: [
          '',
          [
            Validators.required,
            Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$'),
          ],
          [EmailExistsValidator.validate(this.userService)],
        ],
        password: ['', Validators.required],
      }),
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

  onSaveStudent(modal: any) {
    this.submitted = true;
    console.log(this.studentFormGroup);
    if (this.studentFormGroup.invalid) return;
    this.studentService.saveStudent(this.studentFormGroup.value).subscribe({
      next: () => {
        alert('success creating Student');
        this.handleSearchStudents();
        this.studentFormGroup.reset();
        this.submitted = false;
        modal.close();
      },
      error: (err) => {
        alert(err.message);
      },
    });
  }

  onCloseModal(modal: any) {
    modal.close();
    this.studentFormGroup.reset();
  }
}
