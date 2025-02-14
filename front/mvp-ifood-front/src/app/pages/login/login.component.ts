import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { InputComponent } from '../../shared/components/input/input.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [ButtonComponent, InputComponent, CommonModule, FormsModule],
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) {}

  login() {
    this.isLoading = true;
    if (!this.email || !this.password) {
      this.errorMessage = 'Preencha todos os campos';
      this.isLoading = false;
      return;
    }

    this.authService.login(this.email, this.password).subscribe({
      next: () => {
        this.errorMessage = '';
        this.isLoading = false;
        this.toastr.success('Login efetuado com sucesso');
        this.router.navigate(['/home']);
      },
      error: () => {
        this.errorMessage = 'Credenciais inválidas';
        this.isLoading = false;
      },
    });
  }
}
