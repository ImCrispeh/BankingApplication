import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-months',
    templateUrl: './months.component.html',
    styleUrls: ['./months.component.css']
})

export class MonthsComponent implements OnInit {
    monthStyles: {};
    months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    isEvenMonth = false;

    public onChange(event) {
        this.isEvenMonth = event.currentTarget.selectedIndex % 2 != 0;
        this.setMonthStyles(); 
    }

    setMonthStyles() {
        this.monthStyles = {
            'font-weight': this.isEvenMonth ? 'normal' : 'bold',
            'color': this.isEvenMonth ? 'red' : 'blue'
        };
    }

    constructor() {
        this.setMonthStyles();
    }

    ngOnInit() {
    }

}
