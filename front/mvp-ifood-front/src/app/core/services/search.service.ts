import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
private restaurantApiUrl = `${environment.apiBaseUrl}${environment.apiUrls.restaurant}`; 
private productApiUrl = `${environment.apiBaseUrl}${environment.apiUrls.product}`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  searchRestaurants(query: string, page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('query', query)
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<any>(this.restaurantApiUrl + '/search', { params });
  }

  searchProducts(query: string, page: number = 0, size: number = 10): Observable<any> {
    const params = new HttpParams()
      .set('query', query)
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<any>(this.productApiUrl + '/search', { params });
  }

  getRecommendedRestaurants(): Observable<any> {
    const params = new HttpParams()
      .set('page', '0')
      .set('size', '8');
      
    return this.http.get<any>(this.restaurantApiUrl + '/recommendations', { params });
  }

  getRecommendedProducts(productId?: string): Observable<any> {
    let params = new HttpParams()
      .set('page', '0')
      .set('size', '8');

    if (productId) {
      params = params.set('productId', productId);
    }
      
    return this.http.get<any>(this.productApiUrl + '/recommendations', { params});
  }

  getRestaurantById(id: string): Observable<any> {
    return this.http.get<any>(this.restaurantApiUrl + `/restaurant/${id}`, { });
  }

  getProductsByRestaurantId(id: string, productId?: string): Observable<any> {
    let params = new HttpParams();
    if (productId) {
      params = params.set('productId', productId);
    }

    return this.http.get<any>(`${this.productApiUrl}/restaurant/${id}`, { params });
  }
  
  getProductById(id: string): Observable<any> {
    return this.http.get<any>(this.productApiUrl + `/product/${id}`, { });
  }
}
