import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css']
})
export class ConfirmationDialogComponent implements OnInit {

  @Input() display:boolean;
  @Input() title:string;
  @Input() message:string;
  @Output() confirm = new EventEmitter();
  displayResultDialog

  constructor() { }

  ngOnInit(): void {
  }

  confirmAction(){
    this.close();
    this.confirm.emit();
  }

  close(){
    this.display= false;
  }

}
