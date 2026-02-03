<script setup>
import { useRouter } from "vue-router";
const router = useRouter();
</script>

<template>
  <div
    class="not-found-container min-vh-100 d-flex flex-column align-items-center justify-content-center text-center position-relative overflow-hidden"
  >
    <div class="blob blob-1"></div>
    <div class="blob blob-2"></div>

    <div class="content position-relative z-1 p-4">
      <div
        class="display-1 fw-bold text-indigo mb-3 animate-float"
        style="font-size: 8rem; opacity: 0.2"
      >
        404
      </div>

      <div
        class="icon-box mx-auto mb-4 bg-white shadow-sm rounded-circle d-flex align-items-center justify-content-center text-primary"
        style="width: 100px; height: 100px; font-size: 3rem"
      >
        <i class="fas fa-compass"></i>
      </div>

      <h2 class="fw-bold text-dark mb-3">Te-ai rătăcit?</h2>
      <p class="text-muted lead mb-5" style="max-width: 400px; margin: 0 auto">
        Pagina pe care o cauți nu există sau a fost mutată. Nu-ți face griji, se
        întâmplă și celor mai buni studenți.
      </p>

      <button
        @click="router.push('/dashboard')"
        class="btn btn-primary btn-lg rounded-pill px-5 fw-bold shadow-lg btn-hover-lift"
      >
        <i class="fas fa-home me-2"></i> Înapoi la Dashboard
      </button>
    </div>

    <div class="position-absolute bottom-0 mb-4 text-muted small opacity-50">
      SynthEd 404 Error
    </div>
  </div>
</template>

<style scoped>
.not-found-container {
  background-color: #f8fafc;
}

.blob {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.6;
}
.blob-1 {
  top: -10%;
  left: 30%;
  width: 500px;
  height: 500px;
  background: #e0e7ff;
}
.blob-2 {
  bottom: -10%;
  right: 20%;
  width: 400px;
  height: 400px;
  background: #ddd6fe;
}

.text-indigo {
  color: #4338ca;
}

.animate-float {
  animation: float 6s ease-in-out infinite;
}
@keyframes float {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20px);
  }
}

.btn-hover-lift {
  transition: transform 0.2s;
}
.btn-hover-lift:hover {
  transform: translateY(-3px);
}
</style>
