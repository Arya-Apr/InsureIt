This document outlines the architecture of the Insurance Aggregator App, a sophisticated application designed to streamline insurance-related processes.

Technology Stack

GraphQL APIs (primary)
REST APIs (specific tasks, e.g., image uploads)
Tesseract OCR integration (KYC processes)
Spring Framework
Kafka message broker
Kubernetes (container orchestration)
MongoDB database
JWT tokens and Spring Security (authentication and authorization)

Role-based authentication and authorization
JWT tokens and Spring Security
Configuration

application.properties: Stores configurations for GraphiQL, GraphQL schema path, MongoDB Atlas cluster URL, Kafka bootstrap servers, and upload location.
Dedicated folders for Kafka producers/consumers, security configurations, and exception handling.
Key Folders

security: JWT Auth filter and JWT helper beans
serialization: Object ID serialization for MongoDB responses
services: Custom user service implementation fetching user roles and credentials from MongoDB
resources/graphql: schema.graphqls file defining data types, queries, and mutations for the GraphQL API
Additional Features

Exception handling for GraphQL errors
User repository for managing user data in MongoDB
User model definition matching database structure
Input types defining accepted mutation data
GraphQL subscriptions for real-time notifications
This architecture provides a solid foundation for the Insurance Aggregator App, and the planned enhancements will further improve its scalability and maintainability.