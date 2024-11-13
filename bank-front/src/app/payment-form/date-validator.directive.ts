import { Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appDateValidator]',
  providers: [{ provide: NG_VALIDATORS, useExisting: DateValidatorDirective, multi: true }]
})
export class DateValidatorDirective implements Validator {
  validate(control: AbstractControl): ValidationErrors | null {
    const value = control.value;
    const dateRegex = /^\d{2}\/(0[1-9]|1[0-2])$/;

    if (!value) {
      return null;
    }

    const valid = dateRegex.test(value);
    return valid ? null : { dateInvalid: true };
  }
}
