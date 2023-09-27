import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { createLogErrorHandler } from '@angular/compiler-cli/ngcc/src/execution/tasks/completion';
import { CoursesService } from '../../services/courses.service';
import { catchError, Observable, throwError } from 'rxjs';
import { PageResponse } from '../../model/page.response.model';
import { Course } from '../../model/course.model';
import { InstructorsService } from '../../services/instructors.service';
import { Instructor } from '../../model/instructor.model';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css'],
})
export class CoursesComponent implements OnInit {
  searchFormGroup!: FormGroup;
  courseFromGroup!: FormGroup;
  // observable to contain the result of the query
  pageCourses$!: Observable<PageResponse<Course>>;
  instructors$!: Observable<Array<Instructor>>;
  currentPage: number = 0;
  pageSize: number = 5;
  errorMessage!: string;
  errorInstructorMessage!: string;

  constructor(
    private modalService: NgbModal,
    private fb: FormBuilder,
    private courseService: CoursesService,
    private instructorService: InstructorsService
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    this.courseFromGroup = this.fb.group({
      courseName: ['', Validators.required],
      courseDuration: ['', Validators.required],
      courseDescription: ['', Validators.required],
      instructor: [null, Validators.required],
    });
    this.handleSearchCourses();
  }

  getModal(content: any) {
    this.fetchInstructors();
    this.modalService.open(content, { size: 'xl' });
  }

  handleSearchCourses() {
    let keyword = this.searchFormGroup.value.keyword;
    this.pageCourses$ = this.courseService
      .searchCourses(keyword, this.currentPage, this.pageSize)
      .pipe(
        catchError((err) => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchCourses();
  }

  handleDeleteCourse(courses: Course) {
    let confirmation = confirm('Are you sure?');
    if (!confirmation) return;
    this.courseService.deleteCourse(courses.courseId).subscribe({
      next: () => {
        this.handleSearchCourses();
      },
      error: (err) => {
        alert(err.message);
        console.log(err);
      },
    });
  }

  onCloseModal(modal: any) {}

  onSaveModal(modal: any) {}

  fetchInstructors() {
    this.instructors$ = this.instructorService.findAllInstructors().pipe(
      catchError((err) => {
        this.errorInstructorMessage = err.message;
        return throwError(err);
      })
    );
  }
}
