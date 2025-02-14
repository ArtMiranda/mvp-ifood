import { Component, Output, EventEmitter, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { ButtonComponent } from "../button/button.component";
import { AuthService } from '../../../core/services/auth.service';
import { Router, RouterModule } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  imports: [FormsModule, CommonModule, ButtonComponent, RouterModule]
})
export class HeaderComponent {
  @Input() showSearch: boolean = true;
  @Output() searchQueryChanged = new EventEmitter<string>(); 
  @Input() cartSelected: boolean = false; 

  input: string = '';  
  private searchSubject = new Subject<string>();

  constructor( private authService: AuthService, private router: Router, private toastr: ToastrService ) {
    this.searchSubject.pipe(
      debounceTime(500), 
      distinctUntilChanged(), 
    ).subscribe(query => {
      this.searchQueryChanged.emit(query); 
    });
  }

  goToCart() {
    this.router.navigate(['/cart']);
  }

  logout() {
    this.authService.logout();
    this.toastr.success('Logout efetuado com sucesso');
  }

  onInputChange() {
    this.searchSubject.next(this.input);  
  }
}
