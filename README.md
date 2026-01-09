# 🚀 Cloud Task Manager – DevSecOps CI/CD on AWS EKS

This project demonstrates an end-to-end DevSecOps pipeline for deploying a Spring Boot microservice to AWS EKS, using:

- ✔ **Terraform** (infrastructure)
- ✔ **Docker** (image build)
- ✔ **Helm** (deployment to Kubernetes)
- ✔ **Trivy** (image security scan)
- ✔ **SonarQube** (code quality)

The application itself is a simple Task Manager API (CRUD), but the main purpose is to learn end-to-end CI/CD + DevSecOps + Kubernetes deployment.

---

## 🧠 Architecture Overview

**High-level flow:**

```
Developer
   |
   |  git push
   v
Git Repository
   |
   v
Docker Build & Push
   |
   |-- Build Docker image
   |-- Push image to DockerHub
   v
AWS EKS Cluster
   |
   v
Kubernetes Deployment (via Helm)
   |
   v
Spring Boot Application (Pods)
```

---

## 🏗 Architecture Components Explained

### 1️⃣ Source Control (Git)

Holds:
- Spring Boot backend
- Dockerfile
- Helm chart

### 2️⃣ Docker & DockerHub

- Docker builds a container image for the Spring Boot app
- Image is tagged with the build number
- Image is pushed to DockerHub
- Kubernetes pulls the image from DockerHub during deployment

### 3️⃣ Terraform (Infrastructure as Code)

Terraform is used to create AWS infrastructure:
- VPC
- Subnets
- Internet/NAT gateways
- EKS cluster
- Managed node groups

Once the infrastructure is created, you don't need to modify it unless necessary, as the focus is now on building and deploying the app.

### 4️⃣ AWS EKS (Kubernetes Cluster)

- Hosts the application
- Receives deployments via Helm
- Runs Spring Boot pods inside Kubernetes namespaces

### 5️⃣ Helm (Kubernetes Package Manager)

Helm chart defines:
- Deployment
- Service
- Environment variables
- Health probes

Helm installs and upgrades the application on Kubernetes.

---

## 📁 Repository Structure

```
AWS-CICD-DevSecOps-Project/
├── backend/
│   ├── src/
│   ├── pom.xml
│   └── Dockerfile
│
├── infra/
│   ├── terraform/eks/
│   │   ├── main.tf
│   │   ├── variables.tf
│   │   └── outputs.tf
│   │
│   └── helm/todoapp/
│       ├── Chart.yaml
│       ├── values.yaml
│       └── templates/
│           ├── deployment.yaml
│           ├── service.yaml
│           └── _helpers.tpl
```

---

## 🔄 Build & Deployment Flow

### Step 1: Build & Push Docker Image

**Build the Docker image** for the Spring Boot app (with the Dockerfile in `backend/`):

```bash
docker build -t your-dockerhub-username/todoapp:<tag> .
```

**Push the Docker image** to DockerHub:

```bash
docker push your-dockerhub-username/todoapp:<tag>
```

> **Note:** The tag could be the version or build number.

---

### Step 2: Terraform Infrastructure Setup

Make sure Terraform is set up and your EKS cluster is already created.

**To create the infrastructure with Terraform:**

Navigate to the `infra/terraform/eks/` folder and apply the Terraform configuration:

```bash
terraform init
terraform apply
```

This will create:
- VPC
- Subnets
- EKS cluster
- Managed node groups

**Once the infrastructure is created,** you can verify the cluster by running:

```bash
aws eks update-kubeconfig --region <your-region> --name <cluster-name>
kubectl get nodes
```

---

### Step 3: Deploy Application to EKS with Helm

Make sure your Helm chart is ready for deployment (it should be in `infra/helm/todoapp`).

**Set the correct values** in `values.yaml` for image repository and tag:

```yaml
image:
  repository: your-dockerhub-username/todoapp
  tag: "latest"
  pullPolicy: IfNotPresent
```

**Install the Helm chart:**

```bash
helm install todoapp infra/helm/todoapp \
  --namespace todoapp \
  --create-namespace \
  --set image.tag=<tag>
```

**Upgrade the deployment** when you push a new image (replace `<tag>` with the new tag):

```bash
helm upgrade todoapp infra/helm/todoapp \
  --namespace todoapp \
  --set image.tag=<new-tag>
```

This will update the pods with the new image.

---

## 🧪 Verifying Deployment

Once your deployment is successful, verify the following:

**Check pods:**

```bash
kubectl get pods -n todoapp
```

**Check services:**

```bash
kubectl get svc -n todoapp
```

**Get the logs for the app:**

```bash
kubectl logs -n todoapp <pod-name>
```

**If your Service type is LoadBalancer,** you can get the external IP:

```bash
kubectl get svc -n todoapp
```

And then hit the application in your browser or with curl:

```
http://<EXTERNAL-IP>/actuator/health
```

---

## 🛠 Troubleshooting

| Issue | Solution |
|-------|----------|
| Docker build fails | Check Dockerfile path in terminal or Jenkins |
| Helm install fails | Run `helm lint` on your chart or check logs |
| kubectl fails | Check your AWS credentials and kubeconfig setup |
| Trivy fails | Run trivy locally to check vulnerabilities |

---

## 🎯 What This Project Demonstrates

- ✅ Infrastructure as Code (IaC) with Terraform
- ✅ Containerization of Spring Boot app with Docker
- ✅ Kubernetes Deployment using Helm
- ✅ Security Scanning with Trivy
- ✅ Continuous Deployment without Jenkins or ArgoCD (manual steps)
- ✅ Application Monitoring with Spring Boot Actuator

---

## 📌 Next Enhancements (Optional)

- Add RDS for PostgreSQL and configure Kubernetes secrets
- Set up Ingress + AWS Load Balancer for better service exposure
- Prometheus & Grafana for monitoring the app
- Blue/Green or Canary deployments using Helm and Kubernetes
- Automate using GitHub Actions or integrate Jenkins (if needed)
- Test your Helm deployment using Helm test or Kubernetes health checks

---

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## 👤 Author

Your Name - [@yourhandle](https://github.com/yourhandle)
