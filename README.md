## **Desafio Técnico - Grão Direto**  
**Autor:** Artur Campos Miranda  

## 📌 Sobre o Projeto  
Este projeto é um MVP (Minimum Viable Product) inspirado no iFood, desenvolvido como parte do desafio técnico da Grão Direto. Ele permite que usuários explorem restaurantes, visualizem produtos (pratos e bebidas), adicionem itens a um carrinho de compras e utilizem autenticação JWT para segurança.  

> ⚠️ **Aviso Legal**  
> Este projeto utiliza ilustrações, bordões e outros aspectos de marketing originalmente criados pelo iFood. Todos esses elementos são propriedade do iFood e foram utilizados **exclusivamente para fins educacionais** no contexto deste desafio técnico. Este MVP não tem nenhuma afiliação ou vínculo com a empresa iFood.  
---
## 🚀 Tecnologias Utilizadas  

### 🔹 Frontend  
- **Angular 19**  
- **RxJS** (Gerenciamento de Estado e Requisições Assíncronas)  
- **Tailwind CSS** (Estilização)  

### 🔹 Backend  
- **Java 23 + Spring Boot 3**  
- **Spring Security** (Autenticação e Autorização)  
- **Spring Data JPA** (Persistência de Dados)  
- **Liquibase** (Versionamento do Banco de Dados)  
- **OpenAPI + Swagger UI** (Documentação da API)  

### 🔹 Banco de Dados  
- **PostgreSQL**  

---

## 🎯 Features Implementadas  

✅ **Listagem de Restaurantes**  
✅ **Listagem de Produtos (Pratos e Bebidas)**  
✅ **Pesquisa Paginada**  
✅ **Carrinho de Compras (Sacola)**  
✅ **Autenticação JWT**  
✅ **Documentação com OpenAPI (Swagger UI)**  

---

### 🖼️ Imagens do Projeto  

_Tela de Login._  
<div align="center">
  <img src="https://github.com/user-attachments/assets/98cef9ba-d16c-4106-b495-78f17a3a6d18" alt="Tela de Login"/>
</div>

---

_Tela Inicial._  
<div align="center">
  <img src="https://github.com/user-attachments/assets/efaca236-051b-4fee-923e-d84aea116539" alt="Tela Inicial"/>
</div>

---

_Carrinho (sacola)._  
<div align="center">
  <img src="https://github.com/user-attachments/assets/6a022041-9f75-49ea-9701-b26950e8e8f7" alt="Carrinho (sacola)"/>
</div>

---


## 🔐 Autenticação JWT  

A autenticação na aplicação é baseada em **JWT (JSON Web Token)**, garantindo segurança nas requisições entre o frontend (Angular) e o backend (Spring Boot).  

### 🔄 Fluxo de Autenticação  

1. **Login e Geração do Token**  
   - O usuário faz login enviando suas credenciais (e-mail e senha) para o backend.  
   - Se as credenciais forem válidas, o backend gera um **JWT** e o armazena nos **cookies HTTP-only** para maior segurança.  

2. **Armazenamento do Token**  
   - O token JWT não é armazenado no `localStorage` ou `sessionStorage` para evitar ataques XSS.  
   - Ele é salvo nos **cookies** e enviado automaticamente nas requisições para endpoints protegidos.  

3. **Interceptor no Angular**  
   - No frontend, um **Interceptor** (um middleware no Angular) intercepta todas as requisições HTTP.  
   - Ele verifica se o token JWT é válido antes de permitir o acesso a rotas protegidas.  
   - Caso o token seja inválido ou expirado, o usuário é redirecionado para a página de login.  

---

### 📌 O que é um Interceptor?  
Um **Interceptor** no Angular é um middleware que intercepta todas as requisições HTTP antes de serem enviadas para o servidor. Ele permite modificar a requisição, como adicionar cabeçalhos de autenticação ou redirecionar o usuário caso o token tenha expirado.  

---

### 🖼️ Fluxo da Autenticação JWT  

_Ilustração representando o fluxo de autenticação com o Interceptor Angular._  

<div align="center">
  <img src="https://github.com/user-attachments/assets/77f8f5a4-3b0a-4c4b-942f-5a4f6d2ba007" alt="Fluxo de Autenticação JWT"/>
</div>

---

## 📡 Endpoints Principais  

### 🏠 **Autenticação**  

#### 🔹 Login  
`POST /auth/login`  
**Descrição:** Realiza a autenticação do usuário e retorna um JWT.  

```json:  
{  
  "email": "usuario@email.com",  
  "password": "senha123"  
}  
```
**Output:**  
```json:  
{  
  "token": "eyJhbGciOiJIUzI1..."  
}  
```
---

🛠️ Como Rodar o Projeto
------------------------

### 🔹 Pré-requisitos

Certifique-se de ter os seguintes softwares instalados:

*   **Node.js** (Versão 18 ou superior)
    
*   **Angular CLI** (npm install -g @angular/cli)
    
*   **Java 23**
    
*   **Maven**
    
*   **PostgreSQL**
    
Primeiro, clone o repositório

1.  git clone https://github.com/ArtMiranda/mvp-ifood


### 🔹 Backend (Spring Boot)

1. cd back/mvp-ifood-back
    
2.  **Configure o banco de dados**
    
    *   Crie um banco de dados PostgreSQL.
        
    *   Atualize as credenciais no application.properties.
        
3.  mvn spring-boot:run
    
### 🔹 Frontend (Angular)

1.  cd front/mvp-ifood-front
    
2.  npm install
    
3.  ng serve
    
4.  http://localhost:4200
    
