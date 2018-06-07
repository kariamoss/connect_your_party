import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthenticationProcessComponent } from './authentication-process.component';

describe('AuthenticationProcessComponent', () => {
  let component: AuthenticationProcessComponent;
  let fixture: ComponentFixture<AuthenticationProcessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthenticationProcessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthenticationProcessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
