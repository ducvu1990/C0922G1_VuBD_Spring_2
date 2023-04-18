import {Component, OnInit, ViewChild, Renderer2} from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @ViewChild('signupButton') signupButton: any;
  @ViewChild('loginButton') loginButton: any;
  @ViewChild('userForms') userForms: any;

  constructor(private renderer: Renderer2) {}
  ngOnInit() {
  }

  signUpClick() {
    this.renderer.removeClass(this.userForms.nativeElement, 'bounceRight');
    this.renderer.addClass(this.userForms.nativeElement, 'bounceLeft');
  }

  loginClick() {
    this.renderer.removeClass(this.userForms.nativeElement, 'bounceLeft');
    this.renderer.addClass(this.userForms.nativeElement, 'bounceRight');
  }

}
