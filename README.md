<div align="center">
  <img src="https://cdn-icons-png.flaticon.com/512/3389/3389081.png" alt="SynthEd Logo" width="100"/>

  # SynthEd
  
  **AI-Powered Educational Platform & Smart Flashcards Generator** <br>
  *Academic Prototype / Master's Thesis Project*

  [![Vue.js](https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vue.js&logoColor=4FC08D)](https://vuejs.org/)
  [![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
  [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
  [![OpenAI](https://img.shields.io/badge/OpenAI_API-412991?style=for-the-badge&logo=openai&logoColor=white)](https://openai.com/)

</div>

---

## About The Project

**SynthEd** is a full-stack web application designed to solve the problem of information overload in education. By leveraging modern Large Language Models (LLMs), the platform allows students to upload bulky course materials (PDFs) and automatically synthesizes them into bite-sized, interactive flashcards. 

The application implements a smart **Spaced Repetition System (SRS)** logic, allowing users to focus exclusively on the concepts they find difficult, drastically improving retention rates.

## Key Features

- **Document Processing:** Securely upload and parse complex PDF documents using Apache PDFBox.
- **AI Generation:** Seamless integration with `gpt-4o-mini` using Strict JSON Mode to deterministically generate high-quality Q&A flashcards.
- **Smart Study Mode:** Interactive 3D flashcards with "Easy" / "Hard" feedback loops. Automatically isolates missed questions for focused review sessions.
- **Secure Architecture:** Stateless REST API, Environment Variable secrets management, and robust error handling.
- **Responsive UI:** Modern, glassmorphism-inspired interface built with Vue 3 and Bootstrap 5.

---

## Tech Stack

### Frontend (Client)
- **Framework:** Vue.js 3 (Composition API)
- **Styling:** Bootstrap 5 & Custom CSS (Glassmorphism)
- **Routing & HTTP:** Vue Router, Axios
- **Animations:** Canvas Confetti, CSS 3D Transforms

### Backend (Server)
- **Framework:** Java 17 + Spring Boot 3
- **Database & ORM:** PostgreSQL, Spring Data JPA / Hibernate
- **AI Integration:** OpenAI REST API (Prompt Engineering, JSON Mode)
- **Utilities:** Apache PDFBox (PDF parsing), Lombok, Jackson (JSON mapping)

---

## Getting Started

Follow these steps to set up the project locally on your machine.

### Prerequisites
- [Node.js](https://nodejs.org/) (v18+)
- [Java Development Kit (JDK)](https://adoptium.net/) (v17+)
- [PostgreSQL](https://www.postgresql.org/) (v14+)
- An active [OpenAI API Key](https://platform.openai.com/api-keys)

### 1. Database Setup
Create a new PostgreSQL database named `synthed_db`.
```sql
CREATE DATABASE synthed_db;
```

### 2. Backend Setup (Spring Boot)
1. Navigate to the backend directory.
2. Open `src/main/resources/application.properties` and configure your database credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/synthed_db
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
```
3. Set your OpenAI API key as an environment variable to keep it secure:
   - **Windows:** `setx OPENAI_API_KEY "sk-your-key-here"`
   - **Linux/Mac:** `export OPENAI_API_KEY="sk-your-key-here"`
4. Run the application (it will start on `http://localhost:8080`).

### 3. Frontend Setup (Vue.js)
1. Navigate to the frontend directory:
```bash
cd synthed-frontend
```
2. Install dependencies:
```bash
npm install
```
3. Start the development server:
```bash
npm run dev
```
4. Open `http://localhost:5173` in your browser.

---

## Screenshots

*(Add your screenshots here before the final presentation!)*

<details>
  <summary>Click to view UI Previews</summary>
  
  *Add image paths here, e.g.,*
  * `![Dashboard Preview](/assets/dashboard.png)`
  * `![Study Mode](/assets/study_mode.png)`
</details>

---

## Future Improvements

While this version serves as a fully functional Academic Proof of Concept, future iterations will include:
- **Semantic Chunking & RAG:** Implementing a Vector Database (e.g., Pinecone/Chroma) to process massive text volumes (1000+ pages) without hitting LLM context limits.
- **Advanced SRS Algorithm:** Full implementation of the SuperMemo-2 (SM2) algorithm for long-term knowledge retention.

---

<div align="center">
  <p>Built for a Master's Thesis Project - 2026</p>
</div>
