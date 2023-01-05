import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolveTicketComponent } from './solve-ticket.component';

describe('SolveTicketComponent', () => {
  let component: SolveTicketComponent;
  let fixture: ComponentFixture<SolveTicketComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SolveTicketComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SolveTicketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
