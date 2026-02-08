# GitHub Anomaly Detection System

A Spring Boot application designed to monitor GitHub Organization events in real-time and detect security anomalies based on pre-defined policy rules.

## 🚀 Overview
The system consumes GitHub Webhooks and processes them through an extensible engine of security rules.

### Implemented Security Rules:
* **Suspicious Team Prefix:** Alerts when a team is created with names containing "hacker" or other suspicious keywords.
* **Rapid Repository Deletion:** Detects if a repository was deleted within 10 minutes of its creation.
* **Time-Window Enforcement:** Monitors for code pushes occurring during forbidden hours (14:00-16:00 UTC), which may indicate unauthorized access outside of local working hours.

---

## 🛠 Prerequisites
To build and run this project, you will need:
* **Java 21**.
* **Node.js (v18 or higher)**.

---

## ⚙ Setting Up the Environment

### 1. 🏃 Building and Running the application

1.  **Build the application:**
    ```bash
    ./gradlew build
    ```
2.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```
3.  The server will start on `http://localhost:8080`.

### 2. Webhook Tunneling (Smee.io)
Since the application runs on `localhost`, we use **Smee.io** as a payload delivery service:
1.  Go to [smee.io](https://smee.io) and start a new channel.
2.  Install the Smee client via NPM:
    ```bash
    npm install --global smee-client
    ```
3.  Start the proxy to forward GitHub events to your local server:
    ```bash
    smee --url [YOUR_SMEE_URL] --path /webhook --port 8080
    ```

### 3. GitHub Configuration
1.  Navigate to your GitHub Organization **Settings** > **Webhooks**.
2.  Add a new Webhook:
    * **Payload URL:** Your unique Smee.io URL.
    * **Content type:** `application/json`.
    * **Events:** Select "Send me everything" or specific events (Push, Repository, Team).