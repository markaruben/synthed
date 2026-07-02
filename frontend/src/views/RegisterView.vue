<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import api from "@/services/api";

const router = useRouter();

const form = ref({
  username: "",
  email: "",
  password: "",
  confirmPassword: "",
  agreed: false,
});

const isLoading = ref(false);
const errorMessage = ref("");

const touched = ref({
  username: false,
  email: false,
  password: false,
  confirmPassword: false,
});

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

const isUsernameValid = computed(() => form.value.username.trim().length >= 3);
const isEmailValid = computed(() => emailRegex.test(form.value.email));
const isPasswordValid = computed(() => form.value.password.length >= 6);
const doPasswordsMatch = computed(
  () =>
    isPasswordValid.value && form.value.password === form.value.confirmPassword,
);

const isFormValid = computed(
  () =>
    isUsernameValid.value &&
    isEmailValid.value &&
    isPasswordValid.value &&
    doPasswordsMatch.value &&
    form.value.agreed,
);

const goHome = () => router.push("/");

const handleRegister = async () => {
  if (!isFormValid.value) return;

  isLoading.value = true;
  errorMessage.value = "";

  try {
    await api.post("/auth/register", {
      username: form.value.username,
      email: form.value.email,
      password: form.value.password,
    });

    if (window.addToast) {
      window.addToast("Cont creat cu succes! Te poți autentifica.", "success");
    }
    router.push("/login");
  } catch (error) {
    console.error(error);
    errorMessage.value =
      error.response && typeof error.response.data === "string"
        ? error.response.data
        : "Eroare la înregistrare. Încearcă alt username.";
  } finally {
    isLoading.value = false;
  }
};
</script>

<template>
  <div
    class="register-wrapper min-vh-100 d-flex align-items-center justify-content-center position-relative overflow-hidden"
  >
    <div class="blob blob-1"></div>
    <div class="blob blob-2"></div>

    <button
      type="button"
      @click="goHome"
      class="btn bg-white position-fixed top-0 start-0 m-4 rounded-circle shadow-sm border d-flex align-items-center justify-content-center back-btn"
      style="width: 45px; height: 45px; z-index: 1000"
    >
      <i class="fas fa-arrow-left text-dark"></i>
    </button>

    <div
      class="card register-card border-0 p-4 shadow-lg position-relative z-1"
    >
      <div class="card-body">
        <div class="text-center mb-4">
          <div class="logo-circle mx-auto mb-3 shadow-sm animate-float">
            <img src="@/assets/logo.png" alt="SynthEd" width="45" />
          </div>
          <h4 class="fw-bold text-dark mb-1">Creează cont</h4>
          <p class="text-muted small">Începe să înveți inteligent cu SynthEd</p>
        </div>

        <div
          v-if="errorMessage"
          class="alert alert-danger py-2 small rounded-3 border-0 bg-danger bg-opacity-10 text-danger mb-4"
        >
          <i class="fas fa-exclamation-circle me-1"></i> {{ errorMessage }}
        </div>

        <form @submit.prevent="handleRegister">
          <div class="mb-3">
            <label
              class="form-label small fw-bold text-secondary text-uppercase tracking-wide"
              >Username</label
            >
            <div
              class="input-group"
              :class="{
                'is-invalid-group': !isUsernameValid && touched.username,
              }"
            >
              <span
                class="input-group-text bg-white border-end-0 text-muted ps-3"
                ><i class="fas fa-user"></i
              ></span>
              <input
                v-model="form.username"
                @blur="touched.username = true"
                type="text"
                class="form-control bg-white border-start-0 ps-2"
                :class="{
                  'is-invalid-input': !isUsernameValid && touched.username,
                }"
                placeholder="ex: student24"
                required
              />
            </div>
            <div
              v-if="!isUsernameValid && touched.username"
              class="text-danger small mt-1"
            >
              <i class="fas fa-info-circle me-1"></i> Username-ul trebuie să
              aibă minim 3 caractere.
            </div>
          </div>

          <div class="mb-3">
            <label
              class="form-label small fw-bold text-secondary text-uppercase tracking-wide"
              >Email</label
            >
            <div
              class="input-group"
              :class="{ 'is-invalid-group': !isEmailValid && touched.email }"
            >
              <span
                class="input-group-text bg-white border-end-0 text-muted ps-3"
                ><i class="fas fa-envelope"></i
              ></span>
              <input
                v-model="form.email"
                @blur="touched.email = true"
                type="email"
                class="form-control bg-white border-start-0 ps-2"
                :class="{ 'is-invalid-input': !isEmailValid && touched.email }"
                placeholder="nume@student.ro"
                required
              />
            </div>
            <div
              v-if="!isEmailValid && touched.email"
              class="text-danger small mt-1"
            >
              <i class="fas fa-info-circle me-1"></i> Introduceți o adresă de
              email validă.
            </div>
          </div>

          <div class="row g-2 mb-3">
            <div class="col-md-6">
              <label
                class="form-label small fw-bold text-secondary text-uppercase tracking-wide"
                >Parolă</label
              >
              <div
                class="input-group"
                :class="{
                  'is-invalid-group': !isPasswordValid && touched.password,
                }"
              >
                <span
                  class="input-group-text bg-white border-end-0 text-muted ps-3"
                  ><i class="fas fa-lock"></i
                ></span>
                <input
                  v-model="form.password"
                  @blur="touched.password = true"
                  type="password"
                  class="form-control bg-white border-start-0 ps-2"
                  :class="{
                    'is-invalid-input': !isPasswordValid && touched.password,
                  }"
                  placeholder="******"
                  required
                />
              </div>
              <div
                v-if="!isPasswordValid && touched.password"
                class="text-danger small mt-1"
              >
                <i class="fas fa-info-circle me-1"></i> Minim 6 caractere.
              </div>
            </div>
            <div class="col-md-6">
              <label
                class="form-label small fw-bold text-secondary text-uppercase tracking-wide"
                >Confirmă</label
              >
              <div
                class="input-group"
                :class="{
                  'is-invalid-group':
                    !doPasswordsMatch && touched.confirmPassword,
                }"
              >
                <input
                  v-model="form.confirmPassword"
                  @blur="touched.confirmPassword = true"
                  type="password"
                  class="form-control bg-white ps-3"
                  :class="{
                    'is-invalid-input':
                      !doPasswordsMatch && touched.confirmPassword,
                  }"
                  placeholder="******"
                  required
                />
              </div>
              <div
                v-if="!doPasswordsMatch && touched.confirmPassword"
                class="text-danger small mt-1"
              >
                <i class="fas fa-info-circle me-1"></i> Parolele nu coincid.
              </div>
            </div>
          </div>

          <div class="form-check mb-4">
            <input
              v-model="form.agreed"
              class="form-check-input"
              type="checkbox"
              id="termsCheck"
            />
            <label
              class="form-check-label small"
              :class="{
                'text-danger fw-bold': !form.agreed,
                'text-muted': form.agreed,
              }"
              for="termsCheck"
            >
              Sunt de acord cu
              <a href="#" class="text-dark fw-bold text-decoration-none"
                >Termenii și Condițiile</a
              >.
            </label>
          </div>

          <button
            type="submit"
            class="btn btn-primary w-100 py-2 rounded-3 fw-bold shadow-sm btn-hover-lift"
            :disabled="isLoading || !isFormValid"
          >
            <span
              v-if="isLoading"
              class="spinner-border spinner-border-sm me-2"
            ></span>
            {{ isLoading ? "Se creează contul..." : "Înregistrează-te" }}
          </button>
        </form>

        <div class="text-center mt-4 pt-3 border-top">
          <p class="small text-muted mb-0">
            Ai deja cont?
            <a
              @click="router.push('/login')"
              href="#"
              class="text-dark fw-bold text-decoration-none"
              >Autentifică-te</a
            >
          </p>
        </div>
      </div>
    </div>

    <div class="position-absolute bottom-0 mb-3 text-muted small opacity-50">
      &copy; 2026 SynthEd Inc.
    </div>
  </div>
</template>

<style scoped>
.register-wrapper {
  background-color: #f8fafc;
}
.blob {
  position: absolute;
  border-radius: 50%;
  filter: blur(90px);
  opacity: 0.5;
  z-index: 0;
}
.blob-1 {
  top: -10%;
  left: -10%;
  width: 600px;
  height: 600px;
  background: #c7d2fe;
}
.blob-2 {
  bottom: -10%;
  right: -10%;
  width: 500px;
  height: 500px;
  background: #bbf7d0;
}
.register-card {
  width: 100%;
  max-width: 460px;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(12px);
  border-radius: 20px;
  border: 1px solid rgba(255, 255, 255, 0.5);
}
.logo-circle {
  width: 70px;
  height: 70px;
  background: white;
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.animate-float {
  animation: float 4s ease-in-out infinite;
}
@keyframes float {
  0% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-8px);
  }
  100% {
    transform: translateY(0px);
  }
}
.input-group-text {
  border-color: #e2e8f0;
}
.form-control {
  border-color: #e2e8f0;
  padding: 10px 0;
  color: #1e293b;
}
.form-control:not(.border-start-0) {
  padding-left: 12px;
}
.form-control:focus {
  box-shadow: none;
  border-color: #4f46e5;
}
.input-group:focus-within .input-group-text {
  border-color: #4f46e5;
}
.input-group:focus-within {
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
  border-radius: 0.375rem;
}
.form-check-input:checked {
  background-color: #4f46e5;
  border-color: #4f46e5;
}
.tracking-wide {
  letter-spacing: 0.05em;
}
.btn-primary {
  background-color: #4f46e5;
  border: none;
  transition: all 0.2s;
}
.btn-hover-lift:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 15px -3px rgba(79, 70, 229, 0.3);
}
.back-btn {
  transition: all 0.2s ease;
  cursor: pointer;
}
.back-btn:hover {
  background-color: #f1f5f9 !important;
  transform: scale(1.1);
}

/* Stiluri noi adăugate pentru erorile de validare */
.is-invalid-group {
  box-shadow: 0 0 0 3px rgba(220, 53, 69, 0.1) !important;
  border-radius: 0.375rem;
}
.is-invalid-group .input-group-text,
.is-invalid-input {
  border-color: #dc3545 !important;
}
.btn-primary:disabled {
  background-color: #94a3b8;
  cursor: not-allowed;
}
</style>
