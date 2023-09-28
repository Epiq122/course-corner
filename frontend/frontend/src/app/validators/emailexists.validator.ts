import { UsersService } from '../services/users.service';
import {
  AbstractControl,
  AsyncValidator,
  AsyncValidatorFn,
  ValidationErrors,
} from '@angular/forms';
import { map, Observable } from 'rxjs';

export class EmailExistsValidator {
  static validate(userService: UsersService): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      return userService
        .checkIfEmailExists(control.value)
        .pipe(
          map((result: boolean) =>
            result ? { emailAlreadyExists: true } : null
          )
        );
    };
  }
}
