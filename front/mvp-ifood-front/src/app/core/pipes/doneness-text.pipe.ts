import { Pipe, PipeTransform } from '@angular/core';
import { Doneness } from '../models/Product';

@Pipe({
  name: 'donenessText'
})
export class DonenessTextPipe implements PipeTransform {
  transform(doneness: Doneness): string {
    return Doneness[doneness as unknown as keyof typeof Doneness];
  }
}