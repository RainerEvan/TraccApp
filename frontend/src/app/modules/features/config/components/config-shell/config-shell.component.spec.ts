import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigShellComponent } from './config-shell.component';

describe('ConfigShellComponent', () => {
  let component: ConfigShellComponent;
  let fixture: ComponentFixture<ConfigShellComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigShellComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfigShellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
