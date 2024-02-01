# Microservices Application Skeleton

## Overview

A study project to summarize information about Spring Boot and Spring Cloud
based microservices web-application.

It represents a web-application skeleton with widespread functionality.
Consists of backend and frontend parts and contains the following subprojects:

**Frontend**

- [`client`](./frontend/client/README.md) - a single-page Angular-based client web-application

**Backend**

- `client` - a service to hold and deliver client single-page web-application.
It comprises an embedded Apache Tomcat web-server and generated static content
of the original SPA.
- `gateway` - a Spring Cloud based service to handle and route to other services
all the external HTTP requests
- `registry` - a Spring Cloud based service registry to register and share
information about running backend services. It implements the Service Discovery
pattern
- `config` - a Spring Cloud based configuration server to implement
centralized configuration across application.

A typical usage scenario of this skeleton is to develop your own microservices
applications on top of it.

## License

The application is distributed under the Creative Commons CC0 1.0 Universal License.
Bundled dependencies are distributed under its own open licenses, please explore
the corresponding original documentation.
