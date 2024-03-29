import { Component, OnInit, ViewChild } from '@angular/core';
import { DialogService, DynamicDialogRef } from 'primeng/dynamicdialog';
import { Table } from 'primeng/table';
import { Tags } from 'src/app/models/tags';
import { ConfirmationDialogComponent } from 'src/app/modules/shared/components/confirmation-dialog/confirmation-dialog.component';
import { ResultDialogComponent } from 'src/app/modules/shared/components/result-dialog/result-dialog.component';
import { TagsService } from 'src/app/services/tags/tags.service';
import { AddTagsComponent } from '../add-tags/add-tags.component';
import { cloneDeep } from '@apollo/client/utilities';

@Component({
  selector: 'app-tags-list',
  templateUrl: './tags-list.component.html',
  styleUrls: ['./tags-list.component.css']
})
export class TagsListComponent implements OnInit {

  tags: Tags[];
  loading: boolean;
  totalRecords: number;
  ref: DynamicDialogRef;
  @ViewChild('tagTable') tagTable: Table | undefined;

  constructor(public dialogService:DialogService, private tagsService: TagsService) { }

  ngOnInit() {
    this.getAllTags();
  }

  ngOnDestroy(): void {
    if(this.ref){
      this.ref.close();
    }
  }

  public getAllTags(): void{
    this.loading = true;

    this.tagsService.getAllTags().subscribe({
      next: (tags: Tags[]) => {
        this.tags = cloneDeep(tags);
        this.loading = false;
      },
      error: (error: any) => {
        console.log(error);
      }
    });
  }

  applyFilterGlobal($event:any, stringVal:any) {
    this.tagTable.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }

  public deleteTag(tagId: string):void{
    this.tagsService.deleteTag(tagId).subscribe({
      next: (result: any) => {
        console.log(result);
        this.showResultDialog("Success","Tag has been deleted successfully");
        this.getAllTags();
      },
      error: (error: any) => {
        console.log(error);
        this.showResultDialog("Failed","There was a problem, try again later");
      }
    });
  }

  showAddTagDialog(){
    this.ref = this.dialogService.open(AddTagsComponent, {
      header: "Add Tag",
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"550px", "max-width":"700px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((success:boolean) =>{
      if (success) {
        this.getAllTags();
      } 
    });
  }

  showConfirmationDialog(title:string, message:string, action:string, tagId:string){
    this.ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });

    this.ref.onClose.subscribe((confirm:boolean) =>{
        if (confirm) {
          if(action == 'delete'){
            this.deleteTag(tagId);
          }
        }
    });
  }

  showResultDialog(title:string, message:string){
    this.ref = this.dialogService.open(ResultDialogComponent, {
      header: title,
      data: {
        message: message,
      },
      baseZIndex: 10000,
      contentStyle: {"max-height": "650px","width":"40vw", "min-width":"350px", "max-width":"500px", "overflow": "auto"},
    });
  }

}
