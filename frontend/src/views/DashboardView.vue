<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();
const username = localStorage.getItem("user") || "Student";
const documents = ref([]);
const selectedFile = ref(null);
const isUploading = ref(false);

const fetchDocuments = async () => {
  const authHeader = localStorage.getItem("auth");
  if (!authHeader) {
    router.push("/");
    return;
  }
  try {
    const response = await axios.get("http://localhost:8080/api/documents", {
      headers: { Authorization: authHeader },
    });
    documents.value = response.data;
  } catch (error) {
    if (error.response && error.response.status === 401) logout();
  }
};

const logout = () => {
  localStorage.removeItem("auth");
  localStorage.removeItem("user");
  window.addToast("Te-ai delogat cu succes.", "success");
  router.push("/");
};

const handleFileSelect = (event) => {
  selectedFile.value = event.target.files[0];
};

const uploadDocument = async () => {
  if (!selectedFile.value) return;

  if (selectedFile.value.type !== "application/pdf") {
    window.addToast("Te rugăm să încarci doar fișiere PDF.", "error");
    return;
  }

  isUploading.value = true;
  const authHeader = localStorage.getItem("auth");
  const formData = new FormData();
  formData.append("file", selectedFile.value);

  try {
    await axios.post("http://localhost:8080/api/documents/upload", formData, {
      headers: {
        Authorization: authHeader,
        "Content-Type": "multipart/form-data",
      },
    });

    window.addToast("Curs procesat și generat cu succes!", "success");

    selectedFile.value = null;
    document.getElementById("fileInput").value = "";
    await fetchDocuments();
  } catch (error) {
    window.addToast("Eroare la procesarea fișierului.", "error");
  } finally {
    isUploading.value = false;
  }
};

const deleteDocument = async (id) => {
  if (!confirm("Sigur vrei să ștergi acest curs?")) return;

  const authHeader = localStorage.getItem("auth");
  try {
    await axios.delete(`http://localhost:8080/api/documents/${id}`, {
      headers: { Authorization: authHeader },
    });

    window.addToast("Curs șters definitiv.", "success");
    await fetchDocuments();
  } catch (e) {
    window.addToast("Nu s-a putut șterge cursul.", "error");
  }
};

onMounted(() => {
  fetchDocuments();
});
</script>

<template>
  <div class="dashboard-container min-vh-100">
    <nav class="navbar navbar-expand-lg fixed-top px-4 py-3 glass-nav">
      <div class="d-flex align-items-center gap-3">
        <div class="logo-wrapper bg-primary text-white rounded-3 shadow-sm">
          <i class="fas fa-brain"></i>
        </div>
        <span class="fw-bold fs-5 tracking-tight text-dark">SynthEd</span>
      </div>

      <div class="ms-auto d-flex align-items-center gap-4">
        <div
          class="d-none d-md-flex flex-column text-end lh-1 cursor-pointer profile-section"
          @click="router.push('/profile')"
          title="Vezi Profilul"
        >
          <span class="fw-bold text-dark small transition-colors">{{
            username
          }}</span>
          <span class="text-muted" style="font-size: 0.7rem">Free Tier</span>
        </div>

        <div class="dropdown">
          <button
            @click="logout"
            class="btn btn-white rounded-circle shadow-sm border icon-btn hover-scale"
            title="Deconectare"
          >
            <i class="fas fa-sign-out-alt text-danger"></i>
          </button>
        </div>
      </div>
    </nav>

    <div class="container pt-5 mt-5 pb-5">
      <div class="row mb-5 fade-in-up">
        <div class="col-lg-8 mb-4 mb-lg-0">
          <h1 class="fw-bolder text-dark mb-2">Dashboard</h1>
          <p class="text-secondary lead fs-6">
            Bine ai venit,
            <span class="fw-bold text-primary">{{ username }}</span
            >! Pregătit să transformi PDF-urile în cunoștințe?
          </p>
        </div>

        <div class="col-lg-4">
          <div class="d-flex gap-3 h-100">
            <div
              class="stat-card bg-white p-3 rounded-4 shadow-sm border w-50 d-flex flex-column justify-content-center position-relative overflow-hidden"
            >
              <div class="stat-icon bg-primary bg-opacity-10 text-primary">
                <i class="fas fa-book"></i>
              </div>
              <div class="fs-2 fw-bolder text-dark mt-2">
                {{ documents.length }}
              </div>
              <div
                class="text-muted small fw-bold text-uppercase"
                style="font-size: 0.7rem"
              >
                Cursuri
              </div>
            </div>
            <div
              class="stat-card bg-white p-3 rounded-4 shadow-sm border w-50 d-flex flex-column justify-content-center position-relative overflow-hidden"
            >
              <div class="stat-icon bg-success bg-opacity-10 text-success">
                <i class="fas fa-layer-group"></i>
              </div>
              <div class="fs-2 fw-bolder text-dark mt-2">
                {{ documents.length * 15
                }}<span class="fs-6 text-muted">+</span>
              </div>
              <div
                class="text-muted small fw-bold text-uppercase"
                style="font-size: 0.7rem"
              >
                Flashcards
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row g-4">
        <div class="col-lg-4 fade-in-up" style="animation-delay: 0.1s">
          <div
            class="card border-0 shadow-sm rounded-4 overflow-hidden h-100 bg-white"
          >
            <div class="card-body p-4 d-flex flex-column">
              <div
                class="d-flex align-items-center justify-content-between mb-4"
              >
                <h5 class="fw-bold m-0">
                  <i class="fas fa-cloud-upload-alt text-primary me-2"></i
                  >Upload Nou
                </h5>
                <span class="badge bg-light text-muted border">PDF Only</span>
              </div>

              <div
                class="upload-zone flex-grow-1 border-2 border-dashed rounded-4 d-flex flex-column align-items-center justify-content-center p-4 text-center bg-light position-relative"
              >
                <div v-if="!selectedFile" class="transition-all">
                  <div
                    class="icon-circle bg-white shadow-sm mb-3 mx-auto text-primary fs-4"
                  >
                    <i class="fas fa-plus"></i>
                  </div>
                  <p class="small text-muted fw-bold mb-1">
                    Trage un fișier aici sau
                  </p>
                  <label
                    for="fileInput"
                    class="btn btn-sm btn-outline-primary rounded-pill fw-bold px-3"
                    >Răsfoiește</label
                  >
                </div>

                <div v-else class="w-100">
                  <div
                    class="file-preview bg-white p-3 rounded-3 shadow-sm border mb-3 d-flex align-items-center gap-3"
                  >
                    <i class="fas fa-file-pdf text-danger fs-4"></i>
                    <div class="text-start overflow-hidden">
                      <div class="text-truncate fw-bold small">
                        {{ selectedFile.name }}
                      </div>
                      <div class="text-muted" style="font-size: 0.7rem">
                        {{ (selectedFile.size / 1024 / 1024).toFixed(2) }} MB
                      </div>
                    </div>
                    <button
                      @click="selectedFile = null"
                      class="btn btn-link text-muted ms-auto p-0"
                    >
                      <i class="fas fa-times"></i>
                    </button>
                  </div>
                </div>

                <input
                  id="fileInput"
                  type="file"
                  class="position-absolute top-0 start-0 w-100 h-100 opacity-0 cursor-pointer"
                  accept=".pdf"
                  @change="handleFileSelect"
                  :disabled="selectedFile"
                />
              </div>

              <button
                @click="uploadDocument"
                class="btn btn-primary w-100 mt-4 py-2 rounded-3 fw-bold shadow-primary btn-hover-lift"
                :disabled="!selectedFile || isUploading"
              >
                <span
                  v-if="isUploading"
                  class="spinner-border spinner-border-sm me-2"
                ></span>
                {{ isUploading ? "Se procesează..." : "Generează Flashcards" }}
              </button>
            </div>
          </div>
        </div>

        <div class="col-lg-8 fade-in-up" style="animation-delay: 0.2s">
          <div class="d-flex align-items-center justify-content-between mb-3">
            <h5 class="fw-bold text-secondary m-0">Biblioteca Ta</h5>
            <div class="text-muted small">{{ documents.length }} documente</div>
          </div>

          <div
            v-if="documents.length === 0"
            class="card border-0 shadow-sm rounded-4 p-5 text-center bg-white h-100 align-items-center justify-content-center"
          >
            <div class="bg-light rounded-circle p-4 mb-3 text-muted">
              <i class="fas fa-folder-open fs-1"></i>
            </div>
            <h5 class="fw-bold">Niciun curs încărcat</h5>
            <p class="text-muted small">
              Folosește panoul din stânga pentru a începe.
            </p>
          </div>

          <div v-else class="row g-3">
            <div class="col-md-6" v-for="doc in documents" :key="doc.id">
              <div
                class="course-card card h-100 border-0 shadow-sm rounded-4 p-3 bg-white position-relative"
                @click="router.push(`/study/${doc.id}`)"
              >
                <button
                  @click.stop="deleteDocument(doc.id)"
                  class="delete-btn btn btn-white text-danger shadow-sm rounded-circle position-absolute top-0 end-0 m-2"
                  title="Șterge"
                >
                  <i class="fas fa-trash-alt small"></i>
                </button>

                <div class="d-flex align-items-start gap-3">
                  <div
                    class="icon-square bg-indigo-50 text-indigo rounded-3 flex-shrink-0"
                  >
                    <i class="fas fa-file-pdf"></i>
                  </div>
                  <div class="overflow-hidden w-100">
                    <h6
                      class="fw-bold text-dark mb-1 text-truncate"
                      :title="doc.fileName"
                    >
                      {{ doc.fileName }}
                    </h6>
                    <div
                      class="d-flex align-items-center gap-2 small text-muted"
                    >
                      <i class="far fa-calendar-alt"></i>
                      {{ new Date(doc.uploadDate).toLocaleDateString("ro-RO") }}
                    </div>
                  </div>
                </div>

                <div class="mt-4 pt-2 border-top">
                  <div
                    class="d-flex justify-content-between align-items-center mb-1"
                  >
                    <span
                      class="badge bg-success bg-opacity-10 text-success rounded-pill px-2"
                      >Ready</span
                    >
                    <small class="fw-bold text-primary hover-link"
                      >Start Studiu <i class="fas fa-arrow-right ms-1"></i
                    ></small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dashboard-container {
  background-color: #f3f4f6;
  font-family: "Inter", sans-serif;
}
.text-indigo {
  color: #4f46e5;
}
.bg-indigo-50 {
  background-color: #eef2ff;
}

.glass-nav {
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(12px);
  border-bottom: 1px solid rgba(0, 0, 0, 0.05);
}
.logo-wrapper {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.icon-btn {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}
.icon-btn:hover {
  background-color: #f8fafc;
  transform: translateY(-2px);
}

.cursor-pointer {
  cursor: pointer;
}
.profile-section:hover .transition-colors {
  color: #4f46e5 !important;
  transition: color 0.2s ease;
}

.stat-card {
  transition: transform 0.2s ease;
}
.stat-card:hover {
  transform: translateY(-3px);
}
.stat-icon {
  position: absolute;
  top: -10px;
  right: -10px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  opacity: 0.5;
}

.upload-zone {
  border-style: dashed;
  border-color: #cbd5e1;
  transition: all 0.2s ease;
}
.upload-zone:hover {
  background-color: #f1f5f9 !important;
  border-color: #4f46e5;
}
.icon-circle {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.btn-hover-lift {
  transition: transform 0.2s;
}
.btn-hover-lift:active {
  transform: scale(0.98);
}
.shadow-primary {
  box-shadow: 0 4px 14px 0 rgba(79, 70, 229, 0.39);
}
.cursor-pointer {
  cursor: pointer;
}
.course-card {
  transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
}
.course-card:hover {
  transform: translateY(-5px);
  box-shadow:
    0 20px 25px -5px rgba(0, 0, 0, 0.1),
    0 10px 10px -5px rgba(0, 0, 0, 0.04) !important;
  border-color: rgba(79, 70, 229, 0.3) !important;
}
.icon-square {
  width: 44px;
  height: 44px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.25rem;
}
.delete-btn {
  opacity: 0;
  transition:
    opacity 0.2s,
    transform 0.2s;
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
}
.course-card:hover .delete-btn {
  opacity: 1;
}
.delete-btn:hover {
  background-color: #fee2e2 !important;
  transform: scale(1.1);
}
.hover-link {
  transition: color 0.2s;
}
.course-card:hover .hover-link {
  color: #4338ca !important;
}

.fade-in-up {
  animation: fadeInUp 0.5s ease-out forwards;
  opacity: 0;
  transform: translateY(20px);
}
@keyframes fadeInUp {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
