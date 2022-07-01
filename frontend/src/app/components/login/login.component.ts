import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  error: string;

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService, private formBuilder: FormBuilder) {
    if(this.authService.accountValue){
      this.router.navigate(['/']);
    }
  }

  ngOnInit(): void {
    this.generateLoginForm();
  }

  generateLoginForm(){
    this.loginForm = this.formBuilder.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  }

  get username(){
    return this.loginForm.get('username');
  }

  get password(){
    return this.loginForm.get('password');
  }

  login():void{
    if(this.loginForm.valid){
      this.authService.login(this.username.value,this.password.value).subscribe({
        next: () => {
          const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
          this.router.navigateByUrl(returnUrl);
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }
}
