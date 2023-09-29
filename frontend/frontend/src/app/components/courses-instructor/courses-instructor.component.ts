import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Instructor } from '../../model/instructor.model';
import { catchError, Observable, throwError } from 'rxjs';
import { PageResponse } from '../../model/page.response.model';
import { Course } from '../../model/course.model';
import { CoursesService } from '../../services/courses.service';

@Component({
  selector: 'app-courses-instructor',
  templateUrl: './courses-instructor.component.html',
  styleUrls: ['./courses-instructor.component.css'],
})
export class CoursesInstructorComponent implements OnInit {
  instructorId!: number;
  currentInstructor!: Instructor;
  pageCourses!: Observable<PageResponse<Course>>;
  currentPage: number = 0;
  pageSize: number = 5;
  errorMessage!: string;

  constructor(
    private route: ActivatedRoute,
    private courseService: CoursesService
  ) {}

  ngOnInit(): void {
    this.instructorId = this.route.snapshot.params['id'];
    this.fillCurrentInstructor();
    this.handleSearchInstructorCourses();
  }

  private fillCurrentInstructor() {
    this.currentInstructor = {
      instructorId: this.instructorId,
      firstName: '',
      lastName: '',
      summary: '',
      user: {
        email: '',
        password: '',
      },
    };
  }

  private handleSearchInstructorCourses() {
    this.pageCourses = this.courseService
      .getCoursesByInstructor(
        this.instructorId,
        this.currentPage,
        this.pageSize
      )
      .pipe(
        catchError((err) => {
          this.errorMessage = err.message;
          return throwError(err);
        })
      );
  }

  gotoPage(page: number) {
    this.currentPage = page;
    this.handleSearchInstructorCourses();
  }
}
