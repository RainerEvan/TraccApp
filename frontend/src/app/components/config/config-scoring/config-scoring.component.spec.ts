import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigScoringComponent } from './config-scoring.component';

describe('ConfigScoringComponent', () => {
  let component: ConfigScoringComponent;
  let fixture: ComponentFixture<ConfigScoringComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigScoringComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigScoringComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
