import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigApplicationComponent } from './config-application.component';

describe('ConfigApplicationComponent', () => {
  let component: ConfigApplicationComponent;
  let fixture: ComponentFixture<ConfigApplicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigApplicationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
