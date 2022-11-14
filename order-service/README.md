https://challenges.synpulse8.com/backend-engineer/

Backend Engineer Hiring Challenge
Summary
For an e-Banking Portal you have been given the task to design and implement a reusable REST API for returning the paginated list of money account transactions created in an arbitrary calendar month for a given customer who is logged-on in the portal. For each transaction ‘page’ return the total credit and debit values at the current exchange rate (from the third-party provider). The list of transactions should be consumed from a Kafka topic. Build a Docker image out of the application and prepare the configuration for deploying it to Kubernetes / OpenShift.

Assumptions
Every e-banking client has one or more accounts in different currencies (e.g. GBP, EUR, CHF)
There are approximately one hundred thousand e-banking customers, each with a couple thousands of transactions per month.
The transactions cover the last ten years and are stored in Kafka with the key being the transaction ID and the value the JSON representation of the transaction
The user is already authenticated and the API client invoking the transaction API will send a JWT token containing the user’s unique identity key (e.g. P-0123456789)
The exchange rate on any given date is provided by an external API
For simplicity reasons, consider a money account transaction composed of the following attributes:

Unique identifier (e.g. 89d3o179-abcd-465b-o9ee-e2d5f6ofEld46)
Amount with currency (eg GBP 100-, CHF 75)
Account IBAN (eg. CH93-0000-0000-0000-0000-0)
Value date (e.g. 01-10-2020)
Description (e.g. Online payment CHF)
Deliverables
Design and implement the microservice using:

Java
Spring libraries: web, kafka, data security
Kubernetes
Focus on aspects such as

API modeling (Swagger/OpenAPI)
Security (authentication and authorization)
Data access (schema evolution, efficient data access)
Logging and monitoring
Unit and integration testing, functional tests, API contract tests, etc,
Documentation
Please upload the code to a Git repository together with a README file explaining how the code is organized and how the requirements were implemented, including any relevant decisions in a text document. Include any relevant diagrams describing the architecture, data modeling, C4 context as you see fit for explaining the concepts.

Link the Git repository to a service for continuous integration such as CircleCI and provide the link to the pipeline showing the successful execution of the unit and integration tests.


