import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DynamicDialogModule } from 'primeng/dynamicdialog';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from './components/result-dialog/result-dialog.component';
import { ImageDialogComponent } from './components/image-dialog/image-dialog.component';
import { HasRoleDirective } from './directives/has-role.directive';

@NgModule({
  declarations: [
    ConfirmationDialogComponent,
    ResultDialogComponent,
    ImageDialogComponent,
    HasRoleDirective
  ],
  imports: [
    CommonModule,
    DynamicDialogModule,
  ],
  exports: [
    ConfirmationDialogComponent,
    ResultDialogComponent,
    ImageDialogComponent,
    HasRoleDirective
  ],
  providers: []
})
export class SharedModule { }
