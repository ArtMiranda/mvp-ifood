import { Component, OnInit } from '@angular/core';
import { CartService } from '../../core/services/cart.service';
import { CartItem } from '../../core/models/CartItem';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { HeaderComponent } from "../../shared/components/header/header.component";
import { FooterComponent } from "../../shared/components/footer/footer.component";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
  imports: [FormsModule, CommonModule, HeaderComponent, FooterComponent],
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  total: number = 0;

  constructor(private cartService: CartService, private sanitizer: DomSanitizer, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.updateCart();
  }

  getSanitizedImage(base64: string): SafeUrl {
    return this.sanitizer.bypassSecurityTrustUrl(
      `data:image/jpeg;base64,${base64}`
    );
  }

  updateCart(): void {
    this.cartItems = this.cartService.getCartItems();
    this.total = this.cartService.getTotal();
  }

  removeFromCart(productId: string): void {
    this.toastr.info('Produto removido de sua sacola');
    this.cartService.removeFromCart(productId);
    this.updateCart();
  }

  updateQuantity(productId: string, quantity: number): void {
    this.cartService.updateQuantity(productId, quantity);
    this.updateCart();
  }

  clearCart(): void {
    this.toastr.info('Todos os produtos foram removidos de sua sacola');
    this.cartService.clearCart();
    this.updateCart();
  }
}
