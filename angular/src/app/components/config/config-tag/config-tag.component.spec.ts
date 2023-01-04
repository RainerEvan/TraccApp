import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigTagComponent } from './config-tag.component';

describe('ConfigTagComponent', () => {
  let component: ConfigTagComponent;
  let fixture: ComponentFixture<ConfigTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigTagComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
