import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotificationShellComponent } from './notification-shell.component';

describe('NotificationShellComponent', () => {
  let component: NotificationShellComponent;
  let fixture: ComponentFixture<NotificationShellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotificationShellComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotificationShellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
