# Project: Microservices - Ms-Sales

This project was developed in order to explore the communication between services with docker and messaging with RabbitMQ,
using RabbitMQ as a message broker, and the behavior of the integration with APIs built with nodejs and express, finally the behavior of the integration with the database.

<!---Esses são exemplos. Veja https://shields.io para outras pessoas ou para personalizar este conjunto de escudos. Você pode querer incluir dependências, status do projeto e informações de licença aqui--->

<p align="center">
     <img alt="GitHub language count" src="https://img.shields.io/github/commit-status/rafaelrok/rest-archetypebook_v2/main/0d4a9c360f41bd33e2970edb2c3bdbec3ada6506?logo=git">
     <img alt="Repository size" src="https://img.shields.io/github/repo-size/rafaelrok/rest-archetypebook_v2?logo=github">
     <a href="https://twitter.com/RafaelV38440615">
      <img alt="Siga no Twitter" src="https://img.shields.io/twitter/url?url=https://github.com/rafaelrok/rest-archetypebook_v2/edit/main/README.md">
     </a>  
     <a href="https://github.com/rest-archetypebook_v2/README.md/commits/master">
      <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/rafaelrok/rest-archetypebook_v2">
     </a>
     <a href="https://github.com/rafaelrok/rest-archetypebook_v2/blob/main/LICENSE">
      <img alt="License" src="https://img.shields.io/hexpm/l/apa?logo=apache">
     </a>  
     <a href="https://github.com/tgmarinho/README-ecoleta/stargazers">
      <img alt="Stargazers" src="https://img.shields.io/github/stars/rafaelrok/rest-archetypebook_v2?style=social">
     </a>
     <a href="https://medium.com/@rafael">
      <img alt="medium" src="https://img.shields.io/twitter/url?label=Medium&logo=medium&style=social&url=https%3A%2F%2Fmedium.com%2F%40rafael.">
     </a>
  </p>

## technologies

* **Java 11**
* **Spring Boot**
* **Javascript ES6**
* **Node.js 14**
* **ES6 Modules**
* **Express.js**
* **MongoDB (Container e Cloud MongoDB)**
* **API REST**
* **PostgreSQL (Container e Heroku Postgres)**
* **RabbitMQ (Container e CloudAMQP)**
* **Docker**
* **docker-compose**
* **JWT**
* **Spring Cloud OpenFeign**
* **FlyWay**
* **Axios**
* **Heroku**
* **Coralogix Logging**
* **Kibana**

## Proposed Architecture

This project was developed with the following architecture:

![Proposed Architecture](https://github.com/rafaelrok/ms-sales/blob/main/docs/Arquitetura%20ms-sales.png)

Composed of 3 APIs:

* **Auth-API**: Authentication API with Node.js 14, Express.js, Sequelize, PostgreSQL, JWT and Bcrypt.
* **Sales-API**: Sales API with Node.js 14, Express.js, MongoDB, Mongoose, JWT validation, RabbitMQ and Axios for HTTP clients.
* **Product-API**: Product API with Java 11, Spring Boot, Spring Data JPA, PostgreSQL, JWT validation, RabbitMQ and Spring Cloud OpenFeign for HTTP clients.

Project also works with apis running via docker containers via docker-compose.

### Execution flow of an order

The flow for making a request will depend on **synchronous** (HTTP calls via REST) and **asynchronous** (messaging with RabbitMQ) communications.

The flow is described below:

* 01 - The start of the flow will be by making a request to the order creation endpoint.
* 02 - The input payload (JSON) will be a list of products informing the ID and the desired quantity.
* 03 - Before creating the order, a REST call will be made to the products API to validate if there is stock for the purchase of all products.
* 04 - If any product is out of stock, the products API will return an error, and the sales API will throw an error message stating that there is no stock.
* 05 - If there is stock, then an order will be created and saved in MongoDB with pending status (PENDING).
* 06 - When saving the order, a message will be published on RabbitMQ informing the ID of the created order, and the products with their respective IDs and quantities.
* 07 - The products API will be listening to the queue, so it will receive the message.
* 08 - Upon receiving the message, the API will revalidate the stock of products, and if all are ok, it will update the stock of each product.
* 09 - If the stock is updated successfully, the products API will publish a message in the sales confirmation queue with status APPROVED.
* 10 - If there is a problem with the update, the product API will publish a message in the sales confirmation queue with status REJECTED.
* 11 - Finally, the order API will receive the confirmation message and update the order with the status returned in the message.

## API Logs and Tracing

All endpoints need a header called **transactionid**, as it will represent the ID that will go through the entire request in the service, and, if this application calls other microservices, this **transactionid** will be passed on. All input and output endpoints will log the input data (JSON or parameters) and the **transactionid**.

At each request for each microservice, we will have a **serviceid** attribute generated only for the logs of that service itself. We will then have the **transactionid** that will circulate among all microservices involved in the request, and each microservice will have its own **serviceid**.

Tracing flow in requests:

**POST** - **/api/order** with **transactionid**: ef8347eb-2207-4610-86c0-657b4e5851a3

```
service-1:
transactionid: ef8347eb-2207-4610-86c0-657b4e5851a3
serviceid    : 6116a0f4-6c9f-491f-b180-ea31bea2d9de
|
| HTTP Request
|----------------> service-2:
                   transactionid: ef8347eb-2207-4610-86c0-657b4e5851a3
                   serviceid    : 4e1261c1-9a0c-4a5d-bfc2-49744fd159c6
                   |
                   | HTTP Request
                   |----------------> service-3: /api/check-stock
                                      transactionid: ef8347eb-2207-4610-86c0-657b4e5851a3
                                      serviceid    : b4fbc082-a49a-440d-b1d6-2bd0557fd189
```

As we can see in the flow above, the **transactionid** ef8347eb-2207-4610-86c0-657b4e5851a3 remained the same in the 3 services, and each service has
your own **serviceid**.

Example of a complete flow calling 5 services and generating **transactionid** and **serviceid**:

![Tracing](https://github.com/rafaelrok/ms-sales/blob/main/docs/Tracing.png)

Example of logs in the developed APIs:

Auth-API:

```
Request to POST login with data {"email":"testeuser1@gmail.com","password":"123456"} | [transactionID: e3762030-127a-4079-9dee-ba961d7e77ce | serviceID: 6b07b6c2-009e-4799-be96-3bf972338b17]

Response to POST login with data {"status":200,"accessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdXRoVXNlciI6eyJpZCI6MSwibmFtZSI6IlVzZXIgVGVzdCAxIiwiZW1haWwiOiJ0ZXN0ZXVzZXIxQGdtYWlsLmNvbSJ9LCJpYXQiOjE2MzQwNTE4ODQsImV4cCI6MTYzNDEzODI4NH0.NJ-h2i5XPT8NwZyZ_43bif1NIS00ROfCtRecBkxy5A8"} | [transactionID: e3762030-127a-4079-9dee-ba961d7e77ce | serviceID: 6b07b6c2-009e-4799-be96-3bf972338b17]
```

Product-API:

```
Request to POST product stock with data {"products":[{"productId":1001,"quantity":1},{"productId":1002,"quantity":1},{"productId":1003,"quantity":1}]} | [transactionID: 8817508e-805c-48fb-9cb4-6a1e5a6e71e9 | serviceID: ea146e74-55cf-4a53-860e-9010d6e3f61b]

Response to POST product stock with data {"status":200,"message":"The stock is ok!"} | [transactionID: 8817508e-805c-48fb-9cb4-6a1e5a6e71e9 | serviceID: ea146e74-55cf-4a53-860e-9010d6e3f61b]
```

Sales-API:

```
Request to POST new order with data {"products":[{"productId":1001,"quantity":1},{"productId":1002,"quantity":1},{"productId":1003,"quantity":1}]} | [transactionID: 8817508e-805c-48fb-9cb4-6a1e5a6e71e9 | serviceID: 5f553f02-e830-4bed-bc04-8f71fe16cf28]

Response to POST login with data {"status":200,"createdOrder":{"products":[{"productId":1001,"quantity":1},{"productId":1002,"quantity":1},{"productId":1003,"quantity":1}],"user":{"id":1,"name":"User Test 1","email":"testeuser1@gmail.com"},"status":"PENDING","createdAt":"2021-10-12T16:34:49.778Z","updatedAt":"2021-10-12T16:34:49.778Z","transactionid":"8817508e-805c-48fb-9cb4-6a1e5a6e71e9","serviceid":"5f553f02-e830-4bed-bc04-8f71fe16cf28","_id":"6165b92addaf7fc9dd85dad0","__v":0}} | [transactionID: 8817508e-805c-48fb-9cb4-6a1e5a6e71e9 | serviceID: 5f553f02-e830-4bed-bc04-8f71fe16cf28]
```

RabbitMQ:

```
Sending message to product update stock: {"salesId":"6165b92addaf7fc9dd85dad0","products":[{"productId":1001,"quantity":1},{"productId":1002,"quantity":1},{"productId":1003,"quantity":1}],"transactionid":"8817508e-805c-48fb-9cb4-6a1e5a6e71e9"}

Recieving message with data: {"salesId":"6165b92addaf7fc9dd85dad0","products":[{"productId":1001,"quantity":1},{"productId":1002,"quantity":1},{"productId":1003,"quantity":1}],"transactionid":"8817508e-805c-48fb-9cb4-6a1e5a6e71e9"} and TransactionID: 8817508e-805c-48fb-9cb4-6a1e5a6e71e9

Sending message: {"salesId":"6165b92addaf7fc9dd85dad0","status":"APPROVED","transactionid":"8817508e-805c-48fb-9cb4-6a1e5a6e71e9"}

Recieving message from queue: {"salesId":"6165b92addaf7fc9dd85dad0","status":"APPROVED","transactionid":"8817508e-805c-48fb-9cb4-6a1e5a6e71e9"}
```

## Endpoint documentation

The API documentation is present in the file [DOCS](https://github.com/rafaelrok/ms-sales/blob/main/docs/API_DOCS.md).
API endpoints [Collection](https://github.com/rafaelrok/ms-sales/blob/main/docs/MS-Sales.postman_collection.json).

## Deploy Heroku

The 3 APIs were published on Heroku, the repository that were published are these:

* Auth-API    - https://github.com/vhnegrisoli2018/auth-api (PostgreSQL e Coralogix Logging)
* Product-API - https://github.com/vhnegrisoli2018/product-api (Coralogix Logging, Cloud MongoDB e CloudAQMP)
* Sales-API   - https://github.com/vhnegrisoli2018/sales-api (Coralogix Logging Heroku Postgres e CloudAQMP)

As URL base são:

* Auth-API    - https://microsservicos-auth-api.herokuapp.com/
* Product-API - https://microsservicos-product-api.herokuapp.com/
* Sales-API   - https://microsservicos-sales-api.herokuapp.com/

## Tracing with Coralogix Logging and Kibana

Coralogix Logging is a Heroku add-on for adding a status dashboard and viewing logs for applications.

Coralogix Logging dashboard example from Product-API application:

![Dashboard Product-API](https://github.com/rafaelrok/ms-sales/blob/main/docs/Coralogix%20Logging%20Dashboard.png)

On Heroku, we were able to trace the application through our **TransactionID** header, which is mandatory on all endpoints.

Below is an example of tracing performed with an order created for **TransactionID** with value **1c75be8c-efbe-44d7-99ea-60564465c77a**.

![Requisição](https://github.com/rafaelrok/ms-sales/blob/main/docs/Exemplo%20Rastreamento%20Requisi%C3%A7%C3%A3o.png)

After the request is made, we go to our Kibana provided by Coralogix Logging from the Sales-API application and search the logs by the value **1c75be8c-efbe-44d7-99ea-60564465c77a**:

![Kibana Sales-API](https://github.com/rafaelrok/ms-sales/blob/main/docs/Tracing%20Sales-API.png)

We can see multiple input and output logs, containing input and output JSON. We can also see that a call was made to the Product-API microservice via HTTP REST, and also a communication via Rabbit message, and we can see these logs being received there in the Product-API application:

![Kibana Product-API](https://github.com/rafaelrok/ms-sales/blob/main/docs/Tracing%20Product-API.png)

With this, we were able to track all the input and output data of the endpoints, the transaction ID that circulates between them via REST call and via message, facilitating the monitoring of logs of a specific request and, mainly, in the troubleshooting process.

## Docker Commands

Below will be listed some commands executed during the course to create containers
from the PostgreSQL, MongoDB and RabbitMQ message broker databases:

#### Container Auth-DB

`docker run --name auth-db -p 5432:5432 -e POSTGRES_DB=auth-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:11`

#### Container Product-DB

`docker run --name product-db -p 5433:5432 -e POSTGRES_DB=product-db -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=123456 postgres:11`

#### Container Sales-DB

`docker run --name sales-db -p 27017:27017 -p 28017:28017 -e MONGODB_USER="admin" -e MONGODB_DATABASE="sales" -e MONGODB_PASS="123456" -v  c:/db tutum/mongodb`

#### Conection no Mongoshell

`mongo "mongodb://admin:123456@localhost:27017/sales"`

#### Container RabbitMQ

`docker run --name sales_rabbit -p 5672:5672 -p 25676:25676 -p 15672:15672 rabbitmq:3-management`

### Execution docker-compose

`docker-compose up --build`

To ignore the logs, add the flag`-d`.


## Developer
<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/8467131?v=4" width="100px;" alt="Foto do Rafael Vieira no GitHub"/><br>
        <sub>
          <b>Rafael Vieira</b>
        </sub>
      </a>
    </td>
  </tr>
</table>
<table>
  <tr>
    <a href="https://www.linkedin.com/in/rafaelvieira-s/">
      <img alt="linkedin" src="https://img.shields.io/twitter/url?label=Linkedin&logo=linkedin&style=social&url=https%3A%2F%2Fwww.linkedin.com%2Fin%2Frafaelvieira-s%2F">
    </a>
    <a href="https://medium.com/@rafael">
      <img alt="medium" src="https://img.shields.io/twitter/url?label=Medium&logo=medium&style=social&url=https%3A%2F%2Fmedium.com%2F%40rafael.">
    </a>
    <a href = "mailto:rafaelrok25@gmail.com">
      <img alt="gmail" src="https://img.shields.io/twitter/url?label=gmail&logo=gmail&style=social&url=https%3A%2F%2Fmail.google.com%2F">
    </a>
  </tr>
</table>


## 📝 License

This project is under license. see the file [LICENSE](./LICENSE) for more details.

##  versions of README

[Português 🇧🇷](./README.md)  |  [English 🇺🇸](./README-en.md) 

[⬆ Back to the top](#ms-sales)<br>
