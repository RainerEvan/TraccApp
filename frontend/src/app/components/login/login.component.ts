import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loading: boolean = false;
  error: string;
  showPass: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService, private messageService: MessageService, private formBuilder: FormBuilder) {
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
      password: [null, [Validators.required, Validators.minLength(6)]],
    });
  }

  get username(){
    return this.loginForm.get('username');
  }

  get password(){
    return this.loginForm.get('password');
  }

  login():void{
    this.loading = true;

    if(this.loginForm.valid){
      this.authService.login(this.username.value,this.password.value).subscribe({
        next: () => {
          this.loading = false;
          this.messageService.add({key:'login', severity:'success', summary: 'Login Success', detail: 'Welcome to Tracc App'});
          const returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
          this.router.navigateByUrl(returnUrl);
        },
        error: (error: any) => {
          this.loading = false;
          this.messageService.add({key:'login', severity:'error', summary: 'Login Failed', detail: error});
          console.log(error);
        }
      });
    }
  }

  togglePass(){
    this.showPass = !this.showPass;
  }

}
