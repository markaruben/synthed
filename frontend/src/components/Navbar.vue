<template>
  <nav class="navbar navbar-expand-lg fixed-top glass-nav py-3">
    <div class="container">
      <a
        class="navbar-brand fw-bold fs-4 text-dark d-flex align-items-center gap-2"
        href="#"
        @click.prevent="goToHome"
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
        <ul
          class="navbar-nav mx-auto mb-2 mb-lg-0 fw-medium small gap-lg-4"
        ></ul>

        <div class="d-flex align-items-center gap-3">
          <template v-if="!isLoggedIn">
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
          </template>

          <template v-else>
            <div
              class="d-none d-md-flex flex-column text-end lh-1 profile-section"
            >
              <span class="fw-bold text-dark small transition-colors">{{
                username
              }}</span>
            </div>

            <button
              @click="handleLogout"
              class="btn btn-white rounded-circle shadow-sm border icon-btn hover-scale d-flex align-items-center justify-content-center"
              style="width: 40px; height: 40px"
              title="Deconectare"
            >
              <i class="fas fa-sign-out-alt text-danger"></i>
            </button>
          </template>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();

const isLoggedIn = ref(false);
const username = ref("");

const checkAuthStatus = () => {
  const token = localStorage.getItem("auth");
  if (token) {
    isLoggedIn.value = true;
    username.value = localStorage.getItem("user") || "Student";
  } else {
    isLoggedIn.value = false;
    username.value = "";
  }
};

onMounted(() => {
  checkAuthStatus();
});

watch(
  () => route.path,
  () => {
    checkAuthStatus();
  },
);

const goToHome = () => router.push("/");
const goToRegister = () => router.push("/register");
const goToLogin = () => router.push("/login");

const handleLogout = () => {
  localStorage.removeItem("auth");
  localStorage.removeItem("username");

  isLoggedIn.value = false;

  router.push("/login");
};
</script>

<style scoped>
.glass-nav {
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(12px);
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}
.tracking-tight {
  letter-spacing: -0.025em;
}
.btn-hover-lift {
  transition:
    transform 0.2s ease,
    box-shadow 0.2s ease;
}
.btn-hover-lift:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
.cursor-pointer {
  cursor: pointer;
}
.hover-scale {
  transition: transform 0.2s ease;
}
.hover-scale:hover {
  transform: scale(1.05);
}
</style>
