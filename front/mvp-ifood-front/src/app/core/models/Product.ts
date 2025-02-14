export enum ProductType {
    DISH = 'Prato',
    DRINK = 'Bebida',
  }
  
  export enum Doneness {
    RARE = 'Mal-passado',
    MEDIUM_RARE = 'Mal-passado para ao ponto',
    MEDIUM = 'Ao Ponto',
    MEDIUM_WELL = 'Ao ponto para bem-passado',
    WELL_DONE = 'Bem-passado',
  }  
  
  export interface Product {
    id: string; 
    restaurantId: string;
    name: string;
    description: string;
    price: number; 
    type: ProductType;
    image: string;
    allergens: string;
    available: boolean;
    alcoholic: boolean;
    vegetarian: boolean;
    vegan: boolean;
    glutenFree: boolean;
    doneness?: Doneness; 
  }
  