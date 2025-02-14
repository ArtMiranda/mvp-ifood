import { Component, OnChanges, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Doneness, Product } from '../../core/models/Product';
import { SearchService } from '../../core/services/search.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../../shared/components/header/header.component';
import { FooterComponent } from '../../shared/components/footer/footer.component';
import { ProductListComponent } from "../../shared/components/product-list/product-list.component";
import { ButtonComponent } from "../../shared/components/button/button.component";
import { CartService } from '../../core/services/cart.service';
import { DonenessTextPipe } from "../../core/pipes/doneness-text.pipe";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.css'],
  standalone: true,
  imports: [CommonModule, RouterModule, HeaderComponent, FooterComponent, ProductListComponent, ButtonComponent, DonenessTextPipe],
})
export class ProductPageComponent implements OnInit {
  product: Product | undefined;
  quantity: number = 1;
  restaurantProducts: Product[] = [];
  recommendedProducts: Product[] = [];
  doneness = Doneness;

  constructor(
    private route: ActivatedRoute,
    private searchService: SearchService,
    private sanitizer: DomSanitizer,
    private cartService: CartService,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const productId = params.get('id');
      if (productId) {
        this.getProductData(productId);
      }
    });
  }  

  getProductData(productId: string): void {
    this.searchService.getProductById(productId).subscribe(
      (data) => {
        this.product = data;
        this.loadRestaurantProducts();
      },
      (error) => {
        console.error('Error fetching product:', error);
      }
    );
  }  

  loadRestaurantProducts(): void {
    if (this.product) {
      this.searchService.getProductsByRestaurantId(this.product.restaurantId, this.product.id).subscribe(
        (data) => {
          this.restaurantProducts = data.content || [];
          if (this.restaurantProducts.length === 0) {
            this.loadRecommendedProducts();
          }
        },
        (error) => {
          console.error('Error fetching restaurant products:', error);
        }
      );
    }
  }

    loadRecommendedProducts(): void {
    this.searchService.getRecommendedProducts(this.product!.id).subscribe(
      (data) => {
        this.recommendedProducts = data.content || [];
      },
      (error) => {
        console.error('Error fetching recommended products:', error);
      }
    );
  }

  increaseQuantity(): void {
    this.quantity++;
  }

  addToCart(): void {
    if (this.product) {
      this.cartService.addToCart(this.product, this.quantity);
      this.toastr.info('Produto adicionado a sua sacola');
      this.router.navigate(['/cart']);
    } 
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  getSanitizedImage(base64: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(`data:image/jpeg;base64,${base64}`);
  }
}
