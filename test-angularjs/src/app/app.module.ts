import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { TestCompComponent } from './test-comp/test-comp.component';
import { MonthsComponent } from './months/months.component';
import { TestDirectiveDirective } from './test-directive.directive';

@NgModule({
  declarations: [
    AppComponent,
    TestCompComponent,
    MonthsComponent,
    TestDirectiveDirective
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
