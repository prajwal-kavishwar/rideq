# 🚀 RideQ — Uber-like Ride Booking Backend System

Hi, if you are reading this is my UBER clone project RideQ created solely for experience.
---

## 🎯 Project Goal

The goal of RideQ is to build a realistic backend system that handles the complete lifecycle of a ride, along with a simple frontend interface for interaction.

It is designed as an internship-level project to demonstrate strong backend fundamentals and system design thinking.

---

## 🏁 Features

### 🚗 Trip Lifecycle

```
CREATED → ASSIGNED → ACCEPTED → STARTED → COMPLETED 
CANCELED(Not applicable on state STARTED , COMPLETED)
```

---

### ⚙️ Backend Features

* RESTful API design using Spring Boot
* Clean layered architecture:

    * Controller → Service → Repository
* Event-driven driver assignment (`TripCreatedEvent`)
* Distance-based driver matching
* Retry logic for driver rejection
* Driver accept/reject workflow
* JWT-based authentication & authorization
* Driver availability (online/offline) system
* Basic concurrency safety handling

---

### 🌐 Frontend Features
Created a simple frontend to show working for the API in order to use it go to ->

https://github.com/prajwal-kavishwar/rideq-frontend


* Multi-page UI (Login, User, Driver)
* Role-based routing (USER / DRIVER)
* Map integration using Leaflet.js
* Pickup & drop selection via map(LocationIQ)
* Trip creation and status tracking
* Polling-based updates
* Display of:

    * Trip status
    * Driver ID & User ID
    * Pickup & Drop locations
    * Distance & Fare

---

## 🧱 Project Structure

```
rideq/
├── backend/
│   └── com.rideq
│       ├── controller
│       ├── service
│       ├── repository
│       ├── entity
│       ├── dto
│       ├── event
│       ├── listener
│       └── security
│
├── frontend/
│   ├── login.html
│   ├── user.html
│   ├── driver.html
│   ├── styles.css
│   └── scripts.js
```

---

## ⚙️ Tech Stack

### Backend

* Java 21
* Spring Boot 3.x
* Spring Security (JWT)
* Spring Data JPA (Hibernate)
* MySQL
* Maven

### Frontend

* HTML
* CSS
* JavaScript
* Leaflet.js

---

## 🔄 How It Works

1. User logs in and selects pickup & drop locations
2. A trip is created via API
3. `TripCreatedEvent` is triggered
4. System finds nearest available driver
5. Driver receives request and can accept/reject
6. If rejected → next driver is assigned (retry logic)
7. Once accepted → trip progresses through lifecycle
8. Frontend polls backend for updates and displays status

---

## 🧠 Key Concepts Demonstrated

* Backend system design
* Event-driven architecture
* REST API design
* Authentication & security (JWT)
* Separation of concerns
* Retry and failure handling
* Frontend-backend integration
* Real-world system flow modeling

---


## 🚀 Getting Started

### 1. Clone Repository

```
fork repo and run in command prompt/terminal

git clone https://github.com/your-username/rideq.git
cd rideq
```

---

### 2. Backend Setup

* Configure MySQL database
* Copy application-example.yaml to application.yaml and update your credentials.

```
spring.datasource.url=jdbc:mysql://localhost:3306/rideq
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Run the backend:

```
./mvnw spring-boot:run
```

---

### 3. Frontend Setup

Simply open:

```
frontend/login.html
```

in your browser.

---

## 🔐 Authentication

* JWT-based authentication
* Separate roles:

    * USER
    * DRIVER

---

## 📌 Future Improvements 

* React-based frontend
* WebSocket for real-time updates
* Microservices architecture
* Advanced driver matching algorithm

---

## 📷 Screenshots 
![Screenshot from 2026-03-23 00-56-12.png](screenShots/Screenshot%20from%202026-03-23%2000-56-12.png)
![Screenshot from 2026-03-23 00-57-59.png](screenShots/Screenshot%20from%202026-03-23%2000-57-59.png)
![Screenshot from 2026-03-23 00-59-41.png](screenShots/Screenshot%20from%202026-03-23%2000-59-41.png)
![Screenshot from 2026-03-23 01-00-06.png](screenShots/Screenshot%20from%202026-03-23%2001-00-06.png)


---

## 🙌 Author

**Prajwal Kavishwar**
B.Tech Mathematics and Computing Engineering Student

---

## ⭐ If you like this project

Give it a star ⭐ on GitHub!
