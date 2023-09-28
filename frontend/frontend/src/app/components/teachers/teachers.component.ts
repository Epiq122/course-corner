import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InstructorsService } from '../../services/instructors.service';
import { catchError, Observable, throwError } from 'rxjs';
import { Instructor } from '../../model/instructor.model';
import { PageResponse } from '../../model/page.response.model';
import { Course } from '../../model/course.model';

@Component({
  selector: 'app-teachers',
  templateUrl: './teachers.component.html',
  styleUrls: ['./teachers.component.css'],
})
export class TeachersComponent implements OnInit {
  searchFormGroup!: FormGroup;
  // instructors$!: Observable<Array<Instructor>>;
  pageInstructors$!: Observable<PageResponse<Instructor>>;
  currentPage: number = 0;
  pageSize: number = 2;
  errorMessage!: string;

  constructor(
    private modalService: NgbModal,
    private fb: FormBuilder,
    private instructorsService: InstructorsService
  ) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: this.fb.control(''),
    });
    this.handleSearchInstructors();
  }

  getModal(content: any) {
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
}
