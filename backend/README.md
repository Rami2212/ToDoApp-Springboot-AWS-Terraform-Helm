Cloud Task Manager – Backend

A simple Spring Boot CRUD microservice designed to demonstrate a complete DevSecOps pipeline, cloud deployment, and Kubernetes automation.
The focus is not the business logic, but the end-to-end workflow:

✅ CI/CD
✅ Security Scanning
✅ Infrastructure as Code (Terraform)
✅ Docker/Kubernetes/Helm
✅ GitOps (ArgoCD)
✅ Observability (Prometheus/Grafana)

📌 Features

CRUD operations on Tasks

REST API with request validations

PostgreSQL persistence (Testcontainers for tests)

Dockerized microservice

Prometheus metrics via Spring Boot Actuator

Swagger/OpenAPI documentation

Global exception handling

Ready for AWS EKS deployment via Helm

Fully compatible with DevSecOps tools:

SonarQube (code quality)

Trivy (image vulnerability scanning)

Veracode (SAST)

Jenkins / GitHub CI

🛠 Tech Stack
Backend

Java 21

Spring Boot 3.4.12

Maven

PostgreSQL

Testcontainers

DevOps / CI-CD

Docker

DockerHub

Jenkins

GitHub

SonarQube

Trivy

Veracode

Cloud & Infra

AWS EKS

AWS EC2 / S3 / CloudWatch

Terraform

Kubernetes

Helm

Monitoring

ArgoCD

Prometheus

Grafana

📁 Project Structure
cloud-task-manager/
├── pom.xml
├── Dockerfile
├── src/main/java/com/cloud/taskmanager
│   ├── CloudTaskManagerApplication.java
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── exception/
│   ├── repository/
│   └── service/
└── src/main/resources/application.yml

🚀 Running the Application Locally
1️⃣ Prerequisites

Java 21

Maven 3+

PostgreSQL running locally OR inside Docker

Example local DB:

docker run -d \
--name taskdb \
-e POSTGRES_DB=cloud_task_manager \
-e POSTGRES_USER=task_user \
-e POSTGRES_PASSWORD=task_password \
-p 5432:5432 postgres:16-alpine

2️⃣ Build the project
mvn clean install

3️⃣ Run the application
mvn spring-boot:run


Server will start at:
👉 http://localhost:8080

📘 API Documentation (Swagger)

Available at:

👉 http://localhost:8080/swagger-ui/index.html

OpenAPI JSON:

👉 http://localhost:8080/v3/api-docs

📡 REST API Endpoints
Create Task

POST /api/v1/tasks

Get All Tasks

GET /api/v1/tasks

Get by ID

GET /api/v1/tasks/{id}

Update

PUT /api/v1/tasks/{id}

Delete

DELETE /api/v1/tasks/{id}

📊 Observability & Monitoring
Actuator Endpoints
Endpoint	Purpose
/actuator/health	Liveness / Readiness for Kubernetes
/actuator/prometheus	Prometheus metrics
/actuator/info	App metadata

Prometheus scrapes from → /actuator/prometheus

🐳 Docker Support
Build the Docker image
docker build -t cloud-task-manager .

Run container
docker run -p 8080:8080 \
-e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/cloud_task_manager \
-e SPRING_DATASOURCE_USERNAME=task_user \
-e SPRING_DATASOURCE_PASSWORD=task_password \
cloud-task-manager

☸️ Kubernetes (Helm-ready)

This backend is designed to work with:

Helm charts (values.yaml for DB URL, env vars, HPA configs)

ArgoCD auto-syncing the deployments

AWS EKS cluster created via Terraform

Your deployment will include:

Deployment

Service

Horizontal Pod Autoscaler

ConfigMap + Secrets

Ingress (AWS Load Balancer)

🧪 Testing

Uses JUnit 5 + Testcontainers + MockMvc:

Run tests:

mvn test


PostgreSQL Testcontainer automatically spins up for integration tests.

🔐 DevSecOps Pipeline (Recommended Flow)
Stage	Tools
Code Quality	SonarQube
Dependency Scan	Maven + OWASP
Build	Maven
Container Build	Docker
Image Scan	Trivy
SAST	Veracode
Deploy	Helm → ArgoCD → EKS

Each Git push triggers:

Jenkins builds + tests

Scans for vulnerabilities

Builds Docker image

Pushes to DockerHub

ArgoCD auto-deploys to Kubernetes

📦 Environment Variables

K8s + Terraform + Jenkins-friendly:

Variable	Default
SPRING_DATASOURCE_URL	jdbc:postgresql://localhost:5432/cloud_task_manager
SPRING_DATASOURCE_USERNAME	task_user
SPRING_DATASOURCE_PASSWORD	task_password
SERVER_PORT	8080
📄 License

MIT License (or any license you prefer)

🎯 Next Steps

After backend:

✔ Create Terraform infrastructure (VPC, EKS, RDS, IAM)
✔ Write Helm chart (Deployment, Service, HPA, ConfigMap, Ingress)
✔ Set up Jenkins CI/CD pipeline
✔ Configure ArgoCD GitOps sync
✔ Set Prometheus + Grafana dashboards