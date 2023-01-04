import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { DynamicDialogConfig } from 'primeng/dynamicdialog';

@Component({
  selector: 'app-image-dialog',
  templateUrl: './image-dialog.component.html',
  styleUrls: ['./image-dialog.component.css']
})
export class ImageDialogComponent implements OnInit {

  imageUrl: any;

  constructor(public config: DynamicDialogConfig, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.imageUrl = this.sanitizer.bypassSecurityTrustResourceUrl('data:image/png;base64,'+this.config.data.attachment);
  }

}
