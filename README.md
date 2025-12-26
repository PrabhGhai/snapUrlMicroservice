<h1>Distributed Url Shortner Microservice App - SnapUrl</h1>
<br>
<img width="1676" height="798" alt="image" src="https://github.com/user-attachments/assets/4c6bf517-0b18-4772-8054-7328602a75df" />

<h2>1. SnapURL – User Service (Microservice)</h2>
<p>
  User Authentication & Authorization Microservice for SnapURL System
</p>

<h3>📌 About</h3>
<p>
  This repository contains the <b>User Service</b> of the <b>SnapURL Microservices Architecture</b>.
  It handles <b>user authentication</b>, <b>role-based authorization</b>, and
  <b>JWT token management</b>.
</p>

<h3>✨ Features</h3>
<ul>
  <li>Role-Based Authentication (<b>USER / ADMIN</b>)</li>
  <li>JWT Authentication</li>
  <li>Access Token & Refresh Token implementation</li>
  <li>Stateless authentication (no server-side sessions)</li>
  <li>Microservice-ready design</li>
</ul>

<h3>🔐 Authentication & Authorization</h3>
<p>
  The system supports two roles:
</p>

<ul>
  <li><b>USER</b> – Regular application user</li>
  <li><b>ADMIN</b> – Administrative access</li>
</ul>

<p>
  Authorization is enforced using <b>JWT claims</b> and <b>Spring Security</b>.
</p>

<h3>JWT Authentication</h3>
<p>
  Authentication is implemented using <b>JSON Web Tokens (JWT)</b>.
</p>

<p><b>Token Types:</b></p>
<ul>
  <li>
    <b>Access Token</b>
    <ul>
      <li>Short-lived</li>
      <li>Sent with every API request</li>
      <li>Used for authentication and authorization</li>
    </ul>
  </li>
  <li>
    <b>Refresh Token</b>
    <ul>
      <li>Long-lived</li>
      <li>Used to generate a new access token</li>
      <li>Improves user experience by avoiding frequent login</li>
    </ul>
  </li>
</ul>

<h3>Access & Refresh Token Flow</h3>
<ol>
  <li>User logs in with valid credentials</li>
  <li>Server issues an Access Token and Refresh Token</li>
  <li>Client sends Access Token with every request</li>
  <li>If Access Token expires, Refresh Token is used to generate a new one</li>
  <li>User continues without re-authentication</li>
</ol>

<p>
  ✅ Stateless<br/>
  ✅ Scalable<br/>
  ✅ Secure
</p>

<hr/>

<h2>2. SnapURL – URL Service (Microservice)</h2>
<p>
  URL Shortening Microservice for SnapURL System
</p>

<h3>📌 About</h3>
<p>
  The <b>URL Service</b> handles creating, storing, and managing short URLs. 
  Each short URL is generated using the <b>Twitter Snowflake</b> ID generation algorithm and converted to <b>Base62</b>. 
  Each long URL is unique per user.
</p>


<h3>✨ Features</h3>
<ul>
  <li>Generate short URLs from long URLs</li>
  <li>Ensure uniqueness of short URL per user</li>
  <li>Efficient ID generation using <b>Twitter Snowflake</b></li>
  <li>Short URL conversion using <b>Base62 encoding</b></li>
  <li>Microservice-ready, scalable, and fast</li>
</ul>

<h3>🛠️ How Short URLs Are Generated</h3>
<p>
  To ensure uniqueness and efficiency, the service uses the following approach:
</p>

<ul>
  <li><b>Twitter Snowflake ID</b> – Generates a globally unique numeric ID for every URL</li>
  <li><b>Base62 Encoding</b> – Converts the numeric ID into a short, URL-friendly string</li>
  <li>Check per-user uniqueness – No user can create the same short URL for the same long URL twice</li>
</ul>

<h3>🔒 Security & Constraints</h3>
<ul>
  <li>Short URL is tied to the user who created it</li>
  <li>Duplicate long URLs for the same user are prevented</li>
  <li>All operations are secured via <b>User Service JWT authentication</b></li>
</ul>




