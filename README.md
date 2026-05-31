SynthEd
AI-Powered Educational Platform & Smart Flashcards Generator

Academic Prototype / Master's Thesis Project

About The Project
SynthEd is a full-stack web application designed to solve the problem of information overload in education. By leveraging modern Large Language Models (LLMs), the platform allows students to upload bulky course materials (PDFs) and automatically synthesizes them into bite-sized, interactive flashcards.

The application implements a smart Spaced Repetition System (SRS) logic, allowing users to focus exclusively on the concepts they find difficult, drastically improving retention rates.

Key Features
Document Processing: Securely upload and parse complex PDF documents using Apache PDFBox.

AI Generation: Seamless integration with gpt-4o-mini using Strict JSON Mode to deterministically generate high-quality Q&A flashcards.

Smart Study Mode: Interactive 3D flashcards with "Easy" / "Hard" feedback loops. Automatically isolates missed questions for focused review sessions.

Secure Architecture: Stateless REST API, Environment Variable secrets management, and robust error handling.

Responsive UI: Modern, glassmorphism-inspired interface built with Vue 3 and Bootstrap 5.

Tech Stack
Frontend (Client)
Framework: Vue.js 3 (Composition API)

Styling: Bootstrap 5 & Custom CSS (Glassmorphism)

Routing & HTTP: Vue Router, Axios

Animations: Canvas Confetti, CSS 3D Transforms

Backend (Server)
Framework: Java 17 + Spring Boot 3

Database & ORM: PostgreSQL, Spring Data JPA / Hibernate

AI Integration: OpenAI REST API (Prompt Engineering, JSON Mode)

Utilities: Apache PDFBox (PDF parsing), Lombok, Jackson (JSON mapping)

Getting Started
Follow these steps to set up the project locally on your machine.

Prerequisites
Node.js (v18+)

Java Development Kit (JDK) (v17+)

PostgreSQL (v14+)

An active OpenAI API Key

1. Database Setup
Create a new PostgreSQL database named synthed_db.

2. Backend Setup (Spring Boot)
Navigate to the backend directory.

Open src/main/resources/application.properties and configure your database credentials.

Set your OpenAI API key as an environment variable to keep it secure:

Windows: setx OPENAI_API_KEY "sk-your-key-here"

Linux/Mac: export OPENAI_API_KEY="sk-your-key-here"

Run the application (it will start on http://localhost:8080).

3. Frontend Setup (Vue.js)
Navigate to the frontend directory.

Install dependencies by running npm install.

Start the development server by running npm run dev.

Open http://localhost:5173 in your browser.

//TODO
Screenshots
e.g.
![Dashboard Preview](/assets/dashboard.png)

![Study Mode](/assets/study_mode.png)

Future Improvements
While this version serves as a fully functional Academic Proof of Concept, future iterations will include:

Semantic Chunking & RAG: Implementing a Vector Database (e.g., Pinecone/Chroma) to process massive text volumes (1000+ pages) without hitting LLM context limits.

Advanced SRS Algorithm: Full implementation of the SuperMemo-2 (SM2) algorithm for long-term knowledge retention.
