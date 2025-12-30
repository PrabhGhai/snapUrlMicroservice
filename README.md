<h1>SnapUrl – Distributed URL Shortener</h1>

<br>
<img width="1676" height="798" alt="image" src="https://github.com/user-attachments/assets/4c6bf517-0b18-4772-8054-7328602a75df" />
<p>
  <b>SnapUrl</b> is a scalable, microservices-based URL shortener designed to
  demonstrate real-world backend engineering concepts such as service discovery,
  API gateways, authentication, distributed ID generation, caching patterns,
  and asynchronous logging using Kafka.
</p>


<h2>High-Level Architecture</h2>

<p>
  The system is divided into two major layers: <b>Frontend</b> and <b>Backend</b>.
  The backend follows a microservices architecture with centralized routing,
  authentication, service discovery, and asynchronous event-based logging.
</p>

<p>
  An architecture diagram is included in this repository for a visual overview
  of service interactions.
</p>

<hr />

<h2>Tech Stack</h2>

<h3>Frontend</h3>
<ul>
  <li>React.js</li>
  <li>Context API (Global State Management)</li>
  <li>Framer Motion (UI Animations)</li>
  <li>Zod (Input Validation)</li>
</ul>

<h3>Backend</h3>
<ul>
  <li>Java</li>
  <li>Spring Boot</li>
  <li>Spring Cloud Gateway</li>
  <li>Eureka Server (Service Discovery)</li>
  <li>Apache Kafka (Event Streaming)</li>
  <li>JWT (Access & Refresh Tokens)</li>
</ul>

<h3>Other</h3>
<ul>
  <li>Snowflake ID Generation</li>
  <li>Base62 Encoding</li>
</ul>

<hr />

<h2>System Components</h2>

<h3>Frontend</h3>
<ul>
  <li>User interface built with React.js</li>
  <li>Handles authentication state using Context API</li>
  <li>Client-side validation using Zod</li>
  <li>Smooth UI transitions using Framer Motion</li>
</ul>

<h3>API Gateway</h3>
<ul>
  <li>Single entry point for all client requests</li>
  <li>JWT validation and authentication</li>
  <li>Routes requests to downstream services</li>
</ul>

<h3>Eureka Server</h3>
<ul>
  <li>Service registry for dynamic service discovery</li>
  <li>Enables loose coupling between microservices</li>
</ul>

<h3>User Service</h3>
<ul>
  <li>User signup and login</li>
  <li>JWT-based authentication and authorization</li>
  <li>Access Token + Refresh Token implementation</li>
  <li>Refresh token validity fixed at 30 days (non-rotational)</li>
</ul>

<h3>URL Service</h3>
<ul>
  <li>Creates short URLs from long URLs</li>
  <li>Uses Snowflake algorithm for unique ID generation</li>
  <li>Encodes IDs using Base62 (11-character short URLs)</li>
  <li>Supports view, share, and delete operations</li>
</ul>

<h3>Logging & Event Processing</h3>
<ul>
  <li>Each service produces structured logs</li>
  <li>Logs are published as Kafka events</li>
  <li>A Kafka consumer processes and prints logs to the terminal</li>
</ul>

<hr />

<h2>Core Features</h2>
<ul>
  <li>User-specific URL shortening</li>
  <li>Distributed unique ID generation using Snowflake</li>
  <li>Base62 encoding for compact URLs</li>
  <li>JWT authentication with access and refresh tokens</li>
  <li>Service discovery using Eureka</li>
  <li>Asynchronous centralized logging using Kafka</li>
</ul>

<hr />

<h2>URL Creation Flow</h2>
<ol>
  <li>User submits a long URL from the frontend</li>
  <li>Request passes through the API Gateway for authentication</li>
  <li>URL Service generates a Snowflake ID</li>
  <li>ID is encoded using Base62</li>
  <li>Short URL is stored in the database</li>
  <li>Logs are published asynchronously to Kafka</li>
</ol>

<hr />

<h2>Future Improvements</h2>
<ul>
  <li>Implement Redis for Refresh Token and accessing Urls</li>
  <li>Refresh token rotation strategy</li>
  <li>Rate limiting </li>
  <li>URL analytics and click tracking</li>
  <li>Production-grade log persistence (ELK/Loki)</li>
</ul>

<hr />

<h2>Project Goal</h2>
<p>
  This project was built to understand and implement real-world distributed
  systems concepts commonly used in large-scale backend applications.
</p>




