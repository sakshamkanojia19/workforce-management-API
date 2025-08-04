Spring Boot 3.3.1 + Java 24 – Backend Engineer Challenge
A lightweight, fully-functional REST service that lets managers create, assign, track and prioritise tasks for their staff – all in-memory, no database required.
🚀 Quick Start
bash
Copy
# 1. Clone the repo
git clone https://github.com/YOUR_USERNAME/workforcemgmt.git
cd workforcemgmt

# 2. Run (Gradle wrapper downloads everything)
./gradlew bootRun        # Windows: gradlew.bat bootRun
Server spins up at http://localhost:8080 in ~3 s.
🔍 Core Features
Table
Copy
Feature	Implemented?
Create, read, update tasks	✅
Re-assign task → auto-cancels duplicate	✅
Daily “today + backlog” view (no cancelled noise)	✅
Task priority (LOW / MEDIUM / HIGH)	✅
Comments & activity history per task	✅
In-memory storage (no external DB)	✅
📋 API Endpoints
Table
Copy
Method	Endpoint	Body / Params	Description
POST	/tasks	{ "title":"...", "description":"...", "assigneeId":1, "startDate":"2025-08-04", "dueDate":"2025-08-05" }	Create a new task
PUT	/tasks/{id}/assign/{staffId}	—	Re-assign task; old duplicate auto-cancelled
GET	/tasks/staff/{id}?from=YYYY-MM-DD&to=YYYY-MM-DD	Query params	Daily view for staff (excludes cancelled)
PUT	/tasks/{id}/priority	{ "priority": "HIGH" }	Change task priority
GET	/tasks/priority/{HIGH|MEDIUM|LOW}	—	Filter tasks by priority
POST	/tasks/{id}/comments	{ "author":"Alice", "text":"Bring scanner" }	Add human comment
GET	/tasks/{id}	—	Full detail incl. history & comments
🔧 Tech Stack
Java 24 + Spring Boot 3.3.1
Gradle Wrapper – zero local Gradle install
MapStruct (DTO mapping) + Lombok (boilerplate killer)
Embedded Tomcat – single JAR deployment
In-memory collections (Map, List) – no DB setup
🧪 Try It Yourself
Start the server (./gradlew bootRun).
Import the included Postman collection:
Postman → Import → Raw Text → paste collection.json
Run the pre-made requests in order.
