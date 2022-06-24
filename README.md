# Payday-Trade
Payday Trade
Background:
Payday bank is implementing brand new Trading solution. The customer’s now should be able
to purchase stocks from partner brokers through their bank account.
For the MVP you will be expected to develop the user stories provided in an agile and
incremental manner. This means that you should prioritize related user stories that can be
implemented completely within a sprint
## I Used technologies: 

* IDE - Intellij Idea; 
* JDK - 1.8 or later;
* Spring WEB
* Restful API
* Spring Data JPA
* Spring Secuirty
* OAuth2
* Mysql connector
* Spring devtools
* Lombok
* Validation API
* Hibernate Validator
* Spring email
* Json Simple
* Postman

## Development Steps
* Create an Intellij Idea Spring Web Project; 
* Add Dependencies; 
* Project Structure; 
* Application Properties Setup; 
* Config a login&register and security
  - Create entity class: User, Role, UserDetail and RoleUtil;
  - Create repository: UserRepository, RoleRepository;
  - Create service: UserService, RoleService, UserDetailService and RegistrationService;
  - Create secure config: GoogleOAuth2SuccessHandler, WebSecurityConfig and EncoderConfig;
* Create a email verification:
  - Create email classes: EmailSender, EmailValidator, EmailService;
  - Create verify token generation: ConfirmationToken, ConfirmationTokenRepository, ConfirmationTokenService
  - And other configurations are in the classes I mentioned above
* Create a List Stocks 
  - I get a stock-market json data;
  - And I create entity:  StockData;
  - And other configurations: StockDataRepository, Service etc.;
* Create a Place An Order:
  - I create Cart entity/repo/service and other operations;
* Create a DTO 
  - AddToCartDTO, CartDTO, CartItemDTO, CredentialDTO, RegistrationRequestDTO and the ResponseDTO that brings it all together;
* Create a RestController 
  - AuthenticationRestController, CartRestController, StockDataRestController;

![Screenshot (88)](https://user-images.githubusercontent.com/86052693/175530973-c4f058a6-dcff-43b4-a363-5154e2ca3cf2.png)

