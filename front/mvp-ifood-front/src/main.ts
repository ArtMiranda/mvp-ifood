import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { register as registerSwiperElements } from 'swiper/element/bundle';
import { provideToastr } from 'ngx-toastr';
import { provideAnimations } from '@angular/platform-browser/animations';

registerSwiperElements();

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    provideAnimations(),
    provideToastr({
      timeOut: 1500, 
      positionClass: 'toast-top-right', 
      progressAnimation: 'decreasing',
      progressBar: true,
    }),
    ...(appConfig.providers || []),
  ],
}).catch((err) => console.error(err));
