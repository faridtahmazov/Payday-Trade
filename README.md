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
  
# Post Register (Generate verify token)
![Screenshot (84)](https://user-images.githubusercontent.com/86052693/175531256-d5d8925f-487e-46ab-92ff-2e11f8f81fc3.png)
# Get Register verify account
![Screenshot (81)](https://user-images.githubusercontent.com/86052693/175531249-fa97f1c7-7842-4922-be61-d9b7c09fd1f6.png)
![Screenshot (90)](https://user-images.githubusercontent.com/86052693/175531261-7ae75ad5-260b-4db3-986e-7cab4740f0a9.png)
# Email verify
![Screenshot (82)](https://user-images.githubusercontent.com/86052693/175531252-a8b1bbb7-5a50-4af7-8a99-d6224ad49a57.png)

# Post Login
![Screenshot (80)](https://user-images.githubusercontent.com/86052693/175531242-2532b0ce-1a5a-480e-b5ae-be5345eb411e.png)

# List Stocks
![Screenshot (86)](https://user-images.githubusercontent.com/86052693/175531259-ad2022fa-62d3-404c-aeb3-ce0b24f388c4.png)

# Place An Order
![Screenshot (91)](https://user-images.githubusercontent.com/86052693/175538157-1d6b30bb-ac4d-4b8c-9a7b-635970a69c92.png)

# Order notifications
![Screenshot (92)](https://user-images.githubusercontent.com/86052693/175538287-61be5318-0ff0-4799-a9c2-763d658b662e.png)


