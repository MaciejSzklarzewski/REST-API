## **Message App**

Simple REST API with Cassandra

  <h3>Endpoints:<h3>
  
  1) **POST** /api/message
  2) **GET** /api/{emailValue} <- Pagination
  3) **POST** /api/send
  
  <h3>Usage examples:<h3>
  
  1) Storing message into Cassandra:
     <br>POST /api/message -d {"email": "john123@example.com", "title": "No title", "content":"Simple text", "magic_number":130}
  2) Getting all messages with email specified in the {emailValue} path variable: 
  <br>GET /api/messages/john123@example.com?page=2;size=3
      <p>Note: 'page' and 'size' params are optional parameters for pagination. If not provided,
      default values are used (page=0, size=5).</p>
  3) Sending messages with magic number specified in the request body:
    <br>POST /api/send -d {"magic_number":130}
