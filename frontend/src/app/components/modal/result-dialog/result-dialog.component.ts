import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-result-dialog',
  templateUrl: './result-dialog.component.html',
  styleUrls: ['./result-dialog.component.css']
})
export class ResultDialogComponent implements OnInit {

  @Input() display:boolean;
  @Input() success:boolean;
  @Input() message:string;

  constructor() { }

  ngOnInit(): void {
  }

  close(){
    this.display=false;
  }

}
