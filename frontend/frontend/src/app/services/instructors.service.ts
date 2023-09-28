import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Instructor } from '../model/instructor.model';
import { environment } from '../../environments/environment';
import { PageResponse } from '../model/page.response.model';
import { Course } from '../model/course.model';

@Injectable({
  providedIn: 'root',
})
export class InstructorsService {
  constructor(private http: HttpClient) {}

  // find all instructors

  public findAllInstructors(): Observable<Array<Instructor>> {
    return this.http.get<Array<Instructor>>(
      environment.backendHost + '/instructors/all'
    );
  }

  public searchInstructors(
    keyword: string,
    currentPage: number,
    pageSize: number
  ): Observable<PageResponse<Instructor>> {
    return this.http.get<PageResponse<Instructor>>(
      environment.backendHost +
        '/instructors?keyword=' +
        keyword +
        '&page=' +
        currentPage +
        '&size=' +
        pageSize
    );
  }

  public deleteInstructor(instructorId: number) {
    return this.http.delete(
      environment.backendHost + '/instructors/' + instructorId
    );
  }

  public onSaveInstructor(instructor: Instructor): Observable<Instructor> {
    return this.http.post<Instructor>(
      environment.backendHost + '/instructors',
      instructor
    );
  }
}
