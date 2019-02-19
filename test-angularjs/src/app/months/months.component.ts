import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-months',
    templateUrl: './months.component.html',
    styleUrls: ['./months.component.css']
})

export class MonthsComponent implements OnInit {
    months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    isEvenMonth = false;

    public onChange(event) {
        this.isEvenMonth = event.currentTarget.selectedIndex % 2 != 0;    
    }

    constructor() { }

    ngOnInit() {
    }

}
