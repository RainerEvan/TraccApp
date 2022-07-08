import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-config-page',
  templateUrl: './config-page.component.html',
  styleUrls: ['./config-page.component.css']
})
export class ConfigPageComponent implements OnInit {
  // @ViewChild('ticketTable') ticketTable: Table | undefined;

  constructor() { }

  ngOnInit(): void {
  }

   // applyFilterGlobal($event:any, stringVal:any) {
  //   this.ticketTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  // }

}
