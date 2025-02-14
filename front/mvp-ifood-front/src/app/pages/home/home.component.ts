import { Component } from '@angular/core';
import { SearchService } from '../../core/services/search.service';
import { Product } from '../../core/models/Product';
import { Restaurant } from '../../core/models/Restaurant';
import { HeaderComponent } from '../../shared/components/header/header.component';
import { RestaurantListComponent } from '../../shared/components/restaurant-list/restaurant-list.component';
import { ProductListComponent } from '../../shared/components/product-list/product-list.component';
import { FooterComponent } from '../../shared/components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { CarouselComponent } from "../../shared/components/carousel/carousel.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [
    HeaderComponent,
    RestaurantListComponent,
    ProductListComponent,
    FooterComponent,
    CommonModule,
    CarouselComponent
],
})
export class HomeComponent {
  products: Product[] = [];
  restaurants: Restaurant[] = [];
  recommendedRestaurants: Restaurant[] = [];
  recommendedProducts: Product[] = [];
  searchQuery: string = '';
  totalRestaurantItems: number = 0;
  totalProductItems: number = 0;
  searched: boolean = false;

  constructor(private searchService: SearchService) {}

  ngOnInit() {
    this.searchData();
  }

  onSearchQueryChanged(query: string) {
    if (query.trim() === '') {
      this.restaurants = [];
      this.products = [];
      this.searched = false;
      return;
    }
    this.searchQuery = query;
    this.searched = true;
    this.searchData();
  }

  searchData() {
    if (this.searchQuery.trim()) {
      this.searchService.searchRestaurants(this.searchQuery).subscribe(
        (data) => {
          this.restaurants = data.content;
          this.totalRestaurantItems = data.totalElements;
        },
        (error) => {
          console.error('Error fetching restaurants:', error);
        }
      );

      this.searchService.searchProducts(this.searchQuery).subscribe(
        (data) => {
          this.products = data.content;
          this.totalProductItems = data.totalElements;
        },
        (error) => {
          console.error('Error fetching products:', error);
        }
      );
    } else {
      this.searchService.getRecommendedRestaurants().subscribe(
        (data) => {
          this.recommendedRestaurants = data.content;
        },
        (error) => {
          console.error('Error fetching recommended restaurants:', error);
        }
      );
      this.searchService.getRecommendedProducts().subscribe(
        (data) => {
          this.recommendedProducts = data.content;
        },
        (error) => {
          console.error('Error fetching recommended products:', error);
        }
      );
    }
  }
}
