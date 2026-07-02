<script setup>
import { useRouter } from "vue-router";

const router = useRouter();
const goToRegister = () => router.push("/register");
const goToLogin = () => router.push("/login");
</script>

<template>
  <div class="landing-page">
    <nav class="navbar navbar-expand-lg fixed-top glass-nav py-3">
      <div class="container">
        <a
          class="navbar-brand fw-bold fs-4 text-dark d-flex align-items-center gap-2"
          href="#"
        >
          <img
            src="@/assets/logo.png"
            alt="SynthEd Logo"
            width="40"
            height="40"
            class="rounded-3"
          />
          <span class="tracking-tight">SynthEd</span>
        </a>

        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
        ></button>

        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav mx-auto mb-2 mb-lg-0 fw-medium small gap-lg-4">
            <li class="nav-item"></li>
            <li class="nav-item"></li>
          </ul>
          <div class="d-flex gap-2">
            <button
              class="btn btn-link text-decoration-none text-dark fw-bold px-3"
              @click="goToLogin"
            >
              Autentificare
            </button>
            <button
              class="btn btn-primary rounded-pill px-4 fw-bold shadow-sm btn-hover-lift"
              @click="goToRegister"
            >
              Accesează Platforma
            </button>
          </div>
        </div>
      </div>
    </nav>

    <header class="hero-section position-relative overflow-hidden pt-5 pb-5">
      <div class="ambient-light-1"></div>
      <div class="ambient-light-2"></div>

      <div class="container position-relative z-2 mt-5 pt-5">
        <div class="row align-items-center">
          <div class="col-lg-5 mb-5 mb-lg-0 text-center text-lg-start">
            <h1 class="display-4 fw-bolder text-dark mb-4 lh-sm tracking-tight">
              Sinteza cursurilor <br />
              asistată de
              <span class="text-gradient">Inteligența Artificială.</span>
            </h1>

            <p class="lead text-secondary mb-5">
              Platformă experimentală dezvoltată pentru procesarea, analizarea
              și transformarea automată a documentelor educaționale în
              Flashcard-uri interactive folosind modele LLM.
            </p>

            <div
              class="d-flex gap-3 justify-content-center justify-content-lg-start"
            >
              <button
                @click="goToRegister"
                class="btn btn-dark btn-lg rounded-pill px-5 fw-bold shadow-lg btn-hover-scale"
              >
                Testare Sistem
              </button>
            </div>

            <div class="mt-5 text-muted small fw-medium">
              <i></i> Procesare Securizată
              <span class="mx-2">•</span>
              <i></i> Arhitectură AI Hibridă
            </div>
          </div>

          <div class="col-lg-7">
            <div class="browser-mockup-container ms-lg-4">
              <div class="browser-window shadow-2xl">
                <div class="browser-header">
                  <div class="window-controls">
                    <span class="control red"></span
                    ><span class="control yellow"></span
                    ><span class="control green"></span>
                  </div>
                  <div class="address-bar">
                    <i class="fas fa-lock text-success me-2 small"></i
                    >synthed.app/dashboard
                  </div>
                </div>
                <div
                  class="browser-body bg-light position-relative overflow-hidden"
                >
                  <div class="fake-sidebar">
                    <div class="fake-item active"></div>
                    <div class="fake-item"></div>
                    <div class="fake-item"></div>
                  </div>
                  <div class="fake-content p-4">
                    <div class="d-flex justify-content-between mb-4">
                      <div>
                        <div class="skeleton-h1 w-50 mb-2"></div>
                        <div class="skeleton-p w-25"></div>
                      </div>
                      <div class="skeleton-btn"></div>
                    </div>
                    <div class="row g-3">
                      <div class="col-6">
                        <div class="fake-card h-100">
                          <div class="mt-3 skeleton-line w-75"></div>
                          <div class="mt-2 skeleton-line w-50"></div>
                        </div>
                      </div>
                      <div class="col-6">
                        <div class="fake-card h-100">
                          <div class="mt-3 skeleton-line w-75"></div>
                          <div class="mt-2 skeleton-line w-50"></div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="floating-toast">
                    <div class="d-flex align-items-center gap-3">
                      <div class="checkmark-circle">
                        <i class="fas fa-check"></i>
                      </div>
                      <div>
                        <div class="fw-bold text-dark small">
                          JSON Parsat cu Succes
                        </div>
                        <div class="text-muted" style="font-size: 0.7rem">
                          Tokeni procesați: 4,096
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>

    <footer class="bg-dark text-white pt-5 pb-4">
      <div class="container">
        <div class="row g-4 mb-5">
          <div class="col-lg-5">
            <h5 class="fw-bold mb-3 d-flex align-items-center gap-2">
              <i class="fas fa-graduation-cap text-primary"></i> SynthEd
            </h5>
            <p class="text-white-50 small pe-lg-5">
              Acest proiect reprezintă o aplicație practică dezvoltată pentru
              lucrarea de disertație, demonstrând integrarea arhitecturilor web
              moderne cu inteligența artificială generativă.
            </p>
          </div>
        </div>

        <div
          class="border-top border-secondary pt-4 text-center small text-white-50"
        >
          <div>
            &copy; 2026 Proiect de Disertație. Toate drepturile rezervate.
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
.landing-page {
  font-family: "Inter", sans-serif;
  background: #fff;
  scroll-behavior: smooth;
}
.tracking-tight {
  letter-spacing: -0.025em;
}
.tracking-widest {
  letter-spacing: 0.1em;
}
.text-gradient {
  background: linear-gradient(90deg, #212529 0%, #4f46e5 100%);
  background-clip: text;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
.mw-600 {
  max-width: 600px;
}
.mw-800 {
  max-width: 800px;
}

.glass-nav {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(12px);
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}
.logo-box {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.hover-dark:hover {
  color: #000 !important;
}

.browser-mockup-container {
  perspective: 1000px;
}
.browser-window {
  background: #fff;
  border-radius: 12px;
  border: 1px solid #e2e8f0;
  transform: rotateY(-5deg) rotateX(5deg);
  transition: transform 0.5s ease;
}
.browser-window:hover {
  transform: rotateY(0deg);
}
.browser-header {
  background: #f8fafc;
  padding: 10px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #e2e8f0;
}
.window-controls {
  display: flex;
  gap: 6px;
  margin-right: 15px;
}
.control {
  width: 10px;
  height: 10px;
  border-radius: 50%;
}
.red {
  background: #ff5f56;
}
.yellow {
  background: #ffbd2e;
}
.green {
  background: #27c93f;
}
.address-bar {
  flex-grow: 1;
  background: #fff;
  font-size: 11px;
  padding: 4px;
  text-align: center;
  border-radius: 4px;
  color: #64748b;
  border: 1px solid #e2e8f0;
}
.browser-body {
  height: 320px;
}

.fake-sidebar {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 50px;
  border-right: 1px solid #eee;
  background: #fff;
  padding: 10px;
}
.fake-content {
  margin-left: 50px;
}
.fake-item {
  width: 100%;
  height: 30px;
  background: #f1f5f9;
  border-radius: 6px;
  margin-bottom: 10px;
}
.fake-item.active {
  background: #e0e7ff;
}
.fake-card {
  background: white;
  border: 1px solid #eee;
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.02);
}
.skeleton-line {
  height: 8px;
  background: #f1f5f9;
  border-radius: 4px;
}
.floating-toast {
  position: absolute;
  bottom: 20px;
  right: 20px;
  background: white;
  padding: 10px 15px;
  border-radius: 10px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  border: 1px solid #eee;
  animation: float 4s infinite ease-in-out;
}
.checkmark-circle {
  width: 20px;
  height: 20px;
  background: #10b981;
  border-radius: 50%;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 10px;
}
@keyframes float {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

.icon-box {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
}
.hover-up {
  transition: transform 0.3s;
}
.hover-up:hover {
  transform: translateY(-5px);
}
.hover-scale:hover {
  transform: scale(1.02);
  transition: transform 0.2s;
}
.shadow-primary {
  box-shadow: 0 10px 20px -5px rgba(79, 70, 229, 0.4);
}

.hover-white:hover {
  color: #fff !important;
}
.accordion-button:not(.collapsed) {
  background-color: #f8f9fa;
  color: #000;
  box-shadow: none;
}
.accordion-button:focus {
  box-shadow: none;
}

.ambient-light-1 {
  position: absolute;
  top: -10%;
  left: -10%;
  width: 500px;
  height: 500px;
  background: radial-gradient(
    circle,
    rgba(79, 70, 229, 0.1) 0%,
    transparent 70%
  );
  filter: blur(80px);
}
.ambient-light-2 {
  position: absolute;
  top: 30%;
  right: -10%;
  width: 600px;
  height: 600px;
  background: radial-gradient(
    circle,
    rgba(16, 185, 129, 0.05) 0%,
    transparent 70%
  );
  filter: blur(80px);
}
</style>
