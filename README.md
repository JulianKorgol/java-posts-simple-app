# Java Posts Simple App
Simple app to handle posts in Java Spring Boot.


## Features
- ServiceHandler: Signleton class that will perform like DB.
- Endpoints:
  - GET -> /user/sign/in: Log in user. Returns: token.
  - POST -> /user/sign/up: Sign up user. Returns: token.
  - GET -> /post/get/my: Get all posts from user. Returns: posts in JSON.
  - POST -> /post/create: Create a post. Returns: status in JSON.
  - GET -> /post/get/{id}: Get a post by id. Returns: post in JSON if public.
