import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamShellComponent } from './team-shell.component';

describe('TeamShellComponent', () => {
  let component: TeamShellComponent;
  let fixture: ComponentFixture<TeamShellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamShellComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamShellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
