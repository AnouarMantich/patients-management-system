
# 🏥 Healthcare Microservices System
<img src="./resources/Developement-architecture.png">

This project demonstrates a **microservices-based architecture** for a healthcare system using **Docker**, **gRPC**, **Kafka**, and a central **API Gateway**. It is designed to be modular, scalable, and production-ready for use cases like authentication, patient management, billing, analytics, and notifications.

---

## 📐 Architecture Overview

```plaintext
Frontend (Client)
   |
   v
API Gateway
   |
   +--> Auth Service (micro-service)
   |
   +--> Patient Service (micro-service)
             |
             +--> gRPC Client
             |     |
             |     +--> Billing Service (gRPC Server)
             |
             +--> Kafka Producer --> Kafka Topic: `patients`
                                        |
                                        +--> Analytics Service (Kafka Consumer)
                                        +--> Notification Service (Kafka Consumer)
📦 Microservices
🔐 Auth Service
Handles user authentication and issues tokens for authorized access.

🧑‍⚕️ Patient Service
Core service to manage patient records.

Acts as a gRPC client to the Billing Service.

Publishes messages to Kafka topic patients.

💳 Billing Service
Exposes a gRPC server interface.

Used by Patient Service to process billing information.

📊 Analytics Service
Kafka consumer for patients topic.

Processes event data for reporting and insights.

🔔 Notification Service
Kafka consumer for patients topic.

Sends alerts or notifications triggered by patient events.

🌐 API Gateway
Centralized gateway that routes requests to appropriate services.

🧪 Kafka
Message broker for asynchronous communication between microservices.

Hosts topic: patients.

📁 Proto File
text
Copy
Edit
proto/
└── billing_service.proto
Defines the gRPC service interface between the Patient and Billing services.

🐳 Docker Setup
All services are containerized and connected through a Docker network.

▶️ To run the system:
for each service
run docker build -t service-name .
docker-compose up --build
Make sure Docker and Docker Compose are installed on your machine.

📬 Kafka Topics
Topic Name	Producer	Consumers
patient	Patient Service	Analytics Service, Notification Service

🛠️ Technologies Used
Docker & Docker Compose

gRPC (Protocol Buffers)

Apache Kafka

REST APIs

(Optional) React/Angular for frontend

📂 Suggested Project Structure
plaintext
Copy
Edit
.
├── api-gateway/
├── auth-service/
├── patient-service/
├── billing-service/
├── analytics-service/
├── notification-service/
├── proto/
│   └── billing_service.proto
├── docker-compose.yml
└── README.md
🤝 Contributing
Pull requests are welcome! For major changes, open an issue first to discuss what you'd like to change.


👨‍💻 Author
Anouar MANTICH – https://github.com/AnouarMantich
