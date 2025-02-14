import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';
import { ToastrService } from 'ngx-toastr';
import { environment } from '../environments/environment';

interface DecodedToken {
  exp: number;
  [key: string]: any;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = `${environment.apiBaseUrl}${environment.apiUrls.auth}/login`;

  constructor(
    private http: HttpClient,
    private cookieService: CookieService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  login(email: string, password: string): Observable<{ token: string }> {
    return this.http
      .post<{ token: string }>(this.apiUrl, { email, password })
      .pipe(
        tap((response) => {
          this.cookieService.set('token', response.token, {
            path: '/',
            secure: true,
            sameSite: 'Strict',
          });
        })
      );
  }

  isTokenValid(token: string): boolean {
    try {
      const decoded: DecodedToken = jwtDecode(token);
      const currentTime = Date.now() / 1000;
      return decoded.exp > currentTime;
    } catch (error) {
      console.error('Invalid token:', error);
      return false;
    }
  }

  checkTokenAndLogoff(): void {
    console.log('Checking token validity');
    const token = this.getToken();
    if (token && !this.isTokenValid(token)) {
      this.logout(); 
      this.toastr.error('Sessão expirada', 'Faça login novamente');
      this.router.navigate(['/login']);
    } 
  }

  logout() {
    console.log('Logging out');
    this.cookieService.delete('token');
    this.router.navigate(['/login']);
  }
  

  getToken(): string {
    return this.cookieService.get('token');
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }
}
