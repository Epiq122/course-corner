import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { PageResponse } from '../../model/page.response.model';
import { Course } from '../../model/course.model';
import { CoursesService } from '../../services/courses.service';

@Component({
  selector: 'app-courses-students',
  templateUrl: './courses-students.component.html',
  styleUrls: ['./courses-students.component.css'],
})
export class CoursesStudentsComponent implements OnInit {
  studentId!: number;
  pageCourses!: Observable<PageResponse<Course>>;
  pageOtherCourses!: Observable<PageResponse<Course>>;
  otherCoursesCurrentPage: number = 0;
  otherCoursesPageSize: number = 5;
  currentPage: number = 0;
  pageSize: number = 5;
  errorMessage!: string;
  otherCoursesErrorMessage!: string;

  constructor(
    private route: ActivatedRoute,
    private courseService: CoursesService
  ) {}

  ngOnInit(): void {
    this.studentId = this.route.snapshot.params['id'];
    this.handleSearchNonEnrolledInCourses();
  }

  handleSearchStudentCourses() {
    this.pageCourses = this.courseService
      .getCoursesByStudent(this.studentId, this.currentPage, this.pageSize)
      .pipe(
        catchError((err) => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchStudentCourses();
  }

  handleSearchNonEnrolledInCourses() {
    this.pageOtherCourses = this.courseService
      .getNotEnrolledInCoursesByStudent(
        this.studentId,
        this.otherCoursesCurrentPage,
        this.otherCoursesPageSize
      )
      .pipe(
        catchError((err) => {
          this.otherCoursesErrorMessage = err.message;
          return throwError(err);
        })
      );
  }
}
