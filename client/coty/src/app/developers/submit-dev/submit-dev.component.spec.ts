import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitDevComponent } from './submit-dev.component';

describe('SubmitDevComponent', () => {
  let component: SubmitDevComponent;
  let fixture: ComponentFixture<SubmitDevComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitDevComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitDevComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
