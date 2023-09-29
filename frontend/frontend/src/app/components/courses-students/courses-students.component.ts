import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-courses-students',
  templateUrl: './courses-students.component.html',
  styleUrls: ['./courses-students.component.css'],
})
export class CoursesStudentsComponent implements OnInit {
  studentId!: number;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.studentId = this.route.snapshot.params['id'];
  }
}
