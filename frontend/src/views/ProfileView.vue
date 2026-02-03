<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const username = ref(localStorage.getItem("user") || "Student");
const email = ref("student@email.com");
const isLoading = ref(false);

const passwordForm = ref({
  current: "",
  new: "",
  confirm: "",
});

const updatePassword = () => {
  if (passwordForm.value.new !== passwordForm.value.confirm) {
    window.addToast("Parolele noi nu coincid!", "error");
    return;
  }

  isLoading.value = true;
  setTimeout(() => {
    isLoading.value = false;
    window.addToast("Parola a fost actualizată.", "success");
    passwordForm.value = { current: "", new: "", confirm: "" };
  }, 1000);
};

const deleteAccount = () => {
  if (confirm("Ești absolut sigur? Această acțiune este ireversibilă.")) {
    localStorage.clear();
    window.addToast("Contul a fost șters.", "success");
    router.push("/");
  }
};
</script>

<template>
  <div class="profile-container min-vh-100 position-relative overflow-hidden">
    <div class="blob blob-1"></div>
    <div class="blob blob-2"></div>

    <nav class="navbar px-4 py-3 position-absolute w-100" style="z-index: 10">
      <button
        @click="router.push('/dashboard')"
        class="btn btn-white rounded-circle shadow-sm border back-btn"
      >
        <i class="fas fa-arrow-left text-dark"></i>
      </button>
    </nav>

    <div class="container py-5 mt-5 position-relative z-1">
      <div class="row justify-content-center">
        <div class="col-lg-8">
          <h2 class="fw-bold text-dark mb-4">Setări Profil</h2>

          <div
            class="card border-0 shadow-sm rounded-4 mb-4 bg-white overflow-hidden"
          >
            <div class="card-body p-4 d-flex align-items-center gap-4">
              <div
                class="avatar-circle bg-primary text-white fs-2 fw-bold shadow-sm"
              >
                {{ username.charAt(0).toUpperCase() }}
              </div>
              <div class="flex-grow-1">
                <h5 class="fw-bold mb-0">{{ username }}</h5>
                <p class="text-muted small mb-2">{{ email }}</p>
                <span
                  class="badge bg-indigo-50 text-indigo border border-indigo-100 px-3 py-2 rounded-pill"
                >
                  <i class="fas fa-bolt me-1"></i> Free Tier
                </span>
              </div>
              <button
                class="btn btn-outline-primary btn-sm rounded-pill px-3 fw-bold"
              >
                Upgrade PRO
              </button>
            </div>
          </div>

          <div class="card border-0 shadow-sm rounded-4 mb-4 bg-white">
            <div class="card-body p-4">
              <h6 class="fw-bold mb-4">
                <i class="fas fa-lock me-2 text-muted"></i>Schimbă Parola
              </h6>

              <form @submit.prevent="updatePassword">
                <div class="mb-3">
                  <label class="form-label small text-muted fw-bold"
                    >Parola Curentă</label
                  >
                  <input
                    v-model="passwordForm.current"
                    type="password"
                    class="form-control bg-light border-0"
                    required
                  />
                </div>
                <div class="row g-3 mb-4">
                  <div class="col-md-6">
                    <label class="form-label small text-muted fw-bold"
                      >Parola Nouă</label
                    >
                    <input
                      v-model="passwordForm.new"
                      type="password"
                      class="form-control bg-light border-0"
                      required
                    />
                  </div>
                  <div class="col-md-6">
                    <label class="form-label small text-muted fw-bold"
                      >Confirmă Parola</label
                    >
                    <input
                      v-model="passwordForm.confirm"
                      type="password"
                      class="form-control bg-light border-0"
                      required
                    />
                  </div>
                </div>
                <div class="text-end">
                  <button
                    type="submit"
                    class="btn btn-dark px-4 rounded-3 fw-bold"
                    :disabled="isLoading"
                  >
                    <span
                      v-if="isLoading"
                      class="spinner-border spinner-border-sm me-2"
                    ></span>
                    Salvează
                  </button>
                </div>
              </form>
            </div>
          </div>

          <div
            class="card border-danger border-opacity-25 shadow-sm rounded-4 bg-white"
          >
            <div class="card-body p-4">
              <h6 class="fw-bold text-danger mb-3">
                <i class="fas fa-exclamation-triangle me-2"></i>Zona Periculoasă
              </h6>
              <div class="d-flex justify-content-between align-items-center">
                <div>
                  <p class="mb-0 fw-bold text-dark">Șterge contul</p>
                  <p class="small text-muted mb-0">
                    Această acțiune va șterge permanent toate cursurile tale.
                  </p>
                </div>
                <button
                  @click="deleteAccount"
                  class="btn btn-outline-danger btn-sm rounded-3 fw-bold px-3"
                >
                  Șterge Cont
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-container {
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
  right: -10%;
  width: 400px;
  height: 400px;
  background: #c7d2fe;
}
.blob-2 {
  bottom: 20%;
  left: -10%;
  width: 300px;
  height: 300px;
  background: #fecaca;
}

.avatar-circle {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #4f46e5 0%, #818cf8 100%);
}

.text-indigo {
  color: #4f46e5;
}
.bg-indigo-50 {
  background-color: #eef2ff;
}
.border-indigo-100 {
  border-color: #e0e7ff !important;
}

.form-control:focus {
  background-color: #fff !important;
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
}

.back-btn {
  width: 45px;
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.back-btn:hover {
  transform: scale(1.1);
  transition: transform 0.2s;
}
</style>
