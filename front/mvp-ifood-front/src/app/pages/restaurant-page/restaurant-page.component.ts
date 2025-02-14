import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Restaurant } from '../../core/models/Restaurant';
import { SearchService } from '../../core/services/search.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { Product } from '../../core/models/Product';
import { ProductListComponent } from "../../shared/components/product-list/product-list.component";
import { HeaderComponent } from "../../shared/components/header/header.component";
import { FooterComponent } from "../../shared/components/footer/footer.component";

@Component({
  selector: 'app-restaurant-page',
  templateUrl: './restaurant-page.component.html',
  styleUrls: ['./restaurant-page.component.css'],
  imports: [CommonModule, ProductListComponent, HeaderComponent, FooterComponent],
})
export class RestaurantPageComponent implements OnInit {
  restaurant: Restaurant | undefined;
  restaurantProducts: Product[] = [];

  constructor(
    private route: ActivatedRoute,
    private searchService: SearchService,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    const restaurantId = this.route.snapshot.paramMap.get('id');

    if (restaurantId) {
      this.searchService.getRestaurantById(restaurantId).subscribe(
        (data) => {
          this.restaurant = data;
        },
        (error) => {
          console.error('Error fetching restaurant:', error);
        }
      );
    }

    if(restaurantId) {
      this.searchService.getProductsByRestaurantId(restaurantId).subscribe(
        (data) => {
          this.restaurantProducts = data.content;
        },
        (error) => {
          console.error('Error fetching products:', error);
        }
      );
    }
  }

  getSanitizedImage(base64: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(`data:image/jpeg;base64,${base64}`);
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
