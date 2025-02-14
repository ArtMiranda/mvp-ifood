import { Component, Input } from '@angular/core';
import { Restaurant } from '../../../core/models/Restaurant';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-restaurant-list',
  templateUrl: './restaurant-list.component.html',
  styleUrls: ['./restaurant-list.component.css'],
  imports: [CommonModule, RouterModule],
})
export class RestaurantListComponent {
  @Input() restaurants: Restaurant[] = [];
  @Input() totalItems: number = 0;
  @Input() label = 'Restaurantes';
  @Input() subtitle = '';

  constructor(private sanitizer: DomSanitizer, private router: Router) {}

  getSanitizedImage(base64: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(
      `data:image/jpeg;base64,${base64}`
    );
  }

  getStars(rating: number): string[] {
    const stars: string[] = [];
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 !== 0;
    const emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);

    for (let i = 0; i < fullStars; i++) {
      stars.push('full');
    }
    if (hasHalfStar) {
      stars.push('half');
    }
    for (let i = 0; i < emptyStars; i++) {
      stars.push('empty');
    }

    return stars;
  }

  goToRestaurantPage(id: string) {
    this.router.navigate(['/restaurant', id]);
  }
}
