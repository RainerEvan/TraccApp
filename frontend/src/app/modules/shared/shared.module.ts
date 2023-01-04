import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogService, DynamicDialogModule } from 'primeng/dynamicdialog';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    DynamicDialogModule,
  ],
  exports: [
  ],
  providers: [
    DialogService,
  ]
})
export class SharedModule { }
