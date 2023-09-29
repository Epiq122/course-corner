import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Instructor } from '../../model/instructor.model';

@Component({
  selector: 'app-courses-instructor',
  templateUrl: './courses-instructor.component.html',
  styleUrls: ['./courses-instructor.component.css'],
})
export class CoursesInstructorComponent implements OnInit {
  // get the route
  instructorId!: number;
  currentInstructor!: Instructor;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.instructorId = this.route.snapshot.params['id'];
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
}
