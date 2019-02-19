import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[appTestDirective]'
})
export class TestDirectiveDirective {

  constructor(element : ElementRef) {
      element.nativeElement.innerText = 'Generated through custom directive';
  }
}
