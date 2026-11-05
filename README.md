# Java Hello World Application

A complete Hello World application featuring an **AngularJS frontend** and a **Java Spring Boot backend**, with Docker and Kubernetes Helm chart support for containerization and deployment.

## 🏗️ Architecture

- **Frontend**: AngularJS (v1.8) with Bootstrap 5
- **Backend**: Spring Boot 3.2.0 with Java 17
- **Containerization**: Docker with multi-stage builds
- **Orchestration**: Kubernetes Helm chart

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.9+
- Docker & Docker Compose
- Kubernetes cluster (optional, for Helm deployment)
- Helm 3.x (optional)

### Local Development

#### 1. Build and Run with Maven

```bash
# Build the application
mvn clean package -DskipTests

# Run the application
mvn spring-boot:run
```

The application will be available at:
- **Frontend**: http://localhost:8080
- **API Endpoints**:
  - `GET /api/hello?name=YourName` - Personalized greeting
  - `GET /api/greeting` - Default greeting

#### 2. Run with Docker Compose

```bash
# Build and start the container
docker-compose up --build

# Stop the container
docker-compose down
```

### Docker Deployment

#### Build the Docker Image

```bash
docker build -t java-helloworld:latest .
```

#### Run the Container

```bash
docker run -p 8080:8080 java-helloworld:latest
```

### Kubernetes Deployment with Helm

#### Install the Application

```bash
# Navigate to the helm-chart directory
cd helm-chart

# Install the chart
helm install java-helloworld .

# Or install with a custom release name
helm install my-release .
```

#### Configure the Installation

Edit `helm-chart/values.yaml` to customize:
- Number of replicas
- Resource limits
- Service type (ClusterIP, LoadBalancer, etc.)
- Ingress configuration
- Environment variables

#### Upgrade the Installation

```bash
helm upgrade java-helloworld .
```

#### Uninstall the Application

```bash
helm uninstall java-helloworld
```

## 📁 Project Structure

```
java-helloworld/
├── pom.xml                          # Maven build configuration
├── Dockerfile                       # Docker build configuration
├── docker-compose.yml               # Docker Compose configuration
├── README.md                        # This file
├── src/
│   └── main/
│       ├── java/org/example/
│       │   ├── HelloWorldApplication.java  # Spring Boot application entry
│       │   ├── HelloController.java        # REST API controllers
│       │   └── Greeting.java               # Greeting model
│       └── resources/
│           ├── application.properties      # Application configuration
│           └── static/
│               └── index.html             # AngularJS frontend
└── helm-chart/
    ├── Chart.yaml                         # Helm chart metadata
    ├── values.yaml                        # Default values
    └── templates/
        ├── deployment.yaml                # Kubernetes deployment
        ├── service.yaml                   # Kubernetes service
        ├── ingress.yaml                   # Kubernetes ingress
        ├── serviceaccount.yaml            # Service account
        ├── hpa.yaml                       # Horizontal pod autoscaler
        └── _helpers.tpl                   # Helm templates helpers
```

## 🎯 API Endpoints

### GET /api/hello

Returns a personalized greeting.

**Parameters:**
- `name` (optional): Name to greet (default: "World")

**Response:**
```json
{
  "message": "Hello, World!",
  "status": "success"
}
```

**Example:**
```bash
curl "http://localhost:8080/api/hello?name=John"
```

### GET /api/greeting

Returns a default greeting.

**Response:**
```json
{
  "message": "Hello from Spring Boot!",
  "success": true
}
```

**Example:**
```bash
curl http://localhost:8080/api/greeting
```

## 🛠️ Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Application port | 8080 |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `docker` |
| `JAVA_TOOL_OPTIONS` | JVM options | `-Xmx256m -Xms128m` |

### Helm Values

Key configuration options in `values.yaml`:

```yaml
replicaCount: 1
image:
  repository: java-helloworld
  tag: "latest"

resources:
  limits:
    cpu: 500m
    memory: 512Mi
  requests:
    cpu: 100m
    memory: 128Mi

service:
  type: ClusterIP
  port: 80
```

## 🔍 Health Checks

The application exposes health endpoints:

- **Liveness Probe**: `GET /actuator/health`
- **Readiness Probe**: `GET /actuator/health`

## 🧪 Testing

```bash
# Run unit tests
mvn test

# Run tests and build
mvn clean package
```

## 📝 License

This project is created for demonstration purposes.

## 👨‍💻 Author

Developed as a Hello World example for AngularJS + Spring Boot + Docker + Kubernetes.

---

**Happy Coding! 🎉**
