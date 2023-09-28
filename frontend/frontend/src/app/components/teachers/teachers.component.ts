import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InstructorsService } from '../../services/instructors.service';
import { catchError, Observable, throwError } from 'rxjs';
import { Instructor } from '../../model/instructor.model';
import { PageResponse } from '../../model/page.response.model';
import { Course } from '../../model/course.model';
import { EmailExistsValidator } from '../../validators/emailexists.validator';
import { UsersService } from '../../services/users.service';
import { CoursesService } from '../../services/courses.service';

@Component({
  selector: 'app-teachers',
  templateUrl: './teachers.component.html',
  styleUrls: ['./teachers.component.css'],
})
export class TeachersComponent implements OnInit {
  searchFormGroup!: FormGroup;
  instructorFormGroup!: FormGroup;
  pageInstructors$!: Observable<PageResponse<Instructor>>;
  modalInstructor!: Instructor;
  currentPage: number = 0;
  pageSize: number = 3;

  coursesCurrentPage: number = 0;
  coursesPageSize: number = 3;
  coursesErrorMessage!: string;

  errorMessage!: string;
  submitted: boolean = false;
  pageCourses$!: Observable<PageResponse<Course>>;

  constructor(
    private modalService: NgbModal,
    private fb: FormBuilder,
    private instructorsService: InstructorsService,
    private userService: UsersService,
    private courseService: CoursesService
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    this.instructorFormGroup = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      summary: ['', Validators.required], // this is because its technically creating a new user
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
    this.handleSearchInstructors();
  }

  getModal(content: any) {
    this.submitted = false;
    this.modalService.open(content, { size: 'xl' });
  }

  handleSearchInstructors() {
    let keyword = this.searchFormGroup.value.keyword;
    this.pageInstructors$ = this.instructorsService
      .searchInstructors(keyword, this.currentPage, this.pageSize)
      .pipe(
        catchError((err) => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchInstructors();
  }

  handleDeleteInstructor(ins: Instructor) {
    let conf = confirm('Are you sure?');
    if (!conf) return;
    this.instructorsService.deleteInstructor(ins.instructorId).subscribe({
      next: () => {
        this.handleSearchInstructors();
      },
      error: (err) => {
        alert(err.message);
        console.log(err);
      },
    });
  }

  onCloseModal(modal: any) {
    modal.close();
    this.instructorFormGroup.reset();
  }

  onSaveInstructor(modal: any) {
    this.submitted = true;
    console.log(this.instructorFormGroup);
    if (this.instructorFormGroup.invalid) return;
    this.instructorsService
      .onSaveInstructor(this.instructorFormGroup.value)
      .subscribe({
        next: () => {
          alert('success creating Instructor');
          this.handleSearchInstructors();
          this.instructorFormGroup.reset();
          this.submitted = false;
          modal.close();
        },
        error: (err) => {
          alert(err.message);
        },
      });
  }

  getCoursesModal(ins: Instructor, coursesContent: any) {
    this.coursesCurrentPage = 0;
    this.modalInstructor = ins;
    this.handleSearchCourses(ins);
    this.modalService.open(coursesContent, { size: 'xl' });
  }

  handleSearchCourses(ins: Instructor) {
    this.pageCourses$ = this.courseService
      .getCoursesByInstructor(
        ins.instructorId,
        this.coursesCurrentPage,
        this.coursesPageSize
      )
      .pipe(
        catchError((err) => {
          this.coursesErrorMessage = err.message;
          return throwError(err);
        })
      );
  }

  gotoCoursesPage(page: number) {
    this.coursesCurrentPage = page;
    this.handleSearchCourses(this.modalInstructor);
  }
}
