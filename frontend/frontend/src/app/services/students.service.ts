import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../model/student.model';
import { Instructor } from '../model/instructor.model';
import { environment } from '../../environments/environment';
import { PageResponse } from '../model/page.response.model';

@Injectable({
  providedIn: 'root',
})
export class StudentsService {
  constructor(private http: HttpClient) {}

  public fetchAllStudents(): Observable<Array<Student>> {
    return this.http.get<Array<Student>>(
      environment.backendHost + '/students/all'
    );
  }

  public searchStudents(
    keyword: string,
    currentPage: number,
    pageSize: number
  ): Observable<PageResponse<Student>> {
    return this.http.get<PageResponse<Student>>(
      environment.backendHost +
        '/students?keyword=' +
        keyword +
        '&page=' +
        currentPage +
        '&size=' +
        pageSize
    );
  }

  public deleteStudent(studentId: number) {
    return this.http.delete(environment.backendHost + '/students/' + studentId);
  }

  public saveStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(
      environment.backendHost + '/students',
      student
    );
  }
}
