import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Product } from '../../../core/models/Product';
import { CartService } from '../../../core/services/cart.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-product-list',
  imports: [CommonModule, RouterModule],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css',
})
export class ProductListComponent {
  @Input() products: Product[] = [];
  @Input() totalItems: number = 0;
  @Input() label = 'Produtos';
  @Input() subtitle = '';

  constructor(private sanitizer: DomSanitizer, private cartService: CartService, 
    private router: Router) {}

  getSanitizedImage(base64: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(
      `data:image/jpeg;base64,${base64}`
    );
  }

  goToProductPage(id: string) {
    this.router.navigate(['/product', id]);
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
}
