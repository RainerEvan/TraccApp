import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigDivisionComponent } from './config-division.component';

describe('ConfigDivisionComponent', () => {
  let component: ConfigDivisionComponent;
  let fixture: ComponentFixture<ConfigDivisionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigDivisionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigDivisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
