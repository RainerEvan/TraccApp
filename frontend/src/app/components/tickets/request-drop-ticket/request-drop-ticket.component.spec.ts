import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RequestDropTicketComponent } from './request-drop-ticket.component';

describe('RequestDropTicketComponent', () => {
  let component: RequestDropTicketComponent;
  let fixture: ComponentFixture<RequestDropTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RequestDropTicketComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RequestDropTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
