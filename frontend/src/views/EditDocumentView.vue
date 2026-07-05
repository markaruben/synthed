<template>
  <div class="min-vh-100 bg-light py-5">
    <div class="container max-w-800 mx-auto">
      <div class="d-flex align-items-center mb-4">
        <button
          @click="router.push('/dashboard')"
          class="btn btn-white rounded-circle shadow-sm border me-3"
          style="width: 45px; height: 45px"
        >
          <i class="fas fa-arrow-left text-secondary"></i>
        </button>
        <div>
          <h2 class="fw-bold mb-0 text-dark">Editare Flashcard-uri</h2>
          <p class="text-muted mb-0" v-if="document">
            Document: <strong>{{ document.title || document.fileName }}</strong>
          </p>
        </div>
      </div>

      <div v-if="loading" class="text-center py-5">
        <div class="spinner-border text-primary" role="status"></div>
      </div>

      <div
        v-else-if="document && document.flashcards"
        class="d-flex flex-column gap-3"
      >
        <div
          v-for="(card, index) in document.flashcards"
          :key="card.id"
          class="card border-0 shadow-sm rounded-4 overflow-hidden transition-all"
          :class="{ 'border border-primary shadow-lg': editingId === card.id }"
        >
          <div class="card-body p-4 position-relative">
            <div
              class="position-absolute top-0 end-0 bg-primary text-white px-3 py-1 rounded-bottom-start fw-bold small z-1"
            >
              #{{ index + 1 }}
            </div>

            <div v-if="editingId !== card.id">
              <div class="mb-3 mt-2">
                <span
                  class="text-uppercase text-muted fw-bold small tracking-wide"
                  ><i class="fas fa-question-circle text-primary me-1"></i>
                  Întrebare:</span
                >
                <p class="fw-bold text-dark mt-1 mb-0">{{ card.question }}</p>
              </div>

              <div class="mb-3">
                <span
                  class="text-uppercase text-muted fw-bold small tracking-wide"
                  ><i class="fas fa-check-circle text-success me-1"></i>
                  Răspuns:</span
                >
                <p class="text-secondary mt-1 mb-0">{{ card.answer }}</p>
              </div>

              <div
                class="d-flex justify-content-end gap-2 mt-3 pt-3 border-top"
              >
                <button
                  @click="startEdit(card)"
                  class="btn btn-sm btn-outline-primary rounded-pill px-3"
                >
                  <i class="fas fa-pencil-alt me-1"></i> Modifică
                </button>

                <button
                  @click="promptDeleteCard(card.id)"
                  class="btn btn-sm btn-outline-danger rounded-pill px-3"
                >
                  <i class="fas fa-trash-alt me-1"></i> Șterge
                </button>
              </div>
            </div>

            <div v-else class="mt-2">
              <div class="mb-3">
                <label
                  class="text-uppercase text-primary fw-bold small tracking-wide mb-2"
                  ><i class="fas fa-pencil-alt me-1"></i> Editează
                  Întrebarea</label
                >
                <textarea
                  v-model="editForm.question"
                  class="form-control bg-light"
                  rows="2"
                ></textarea>
              </div>
              <div class="mb-3">
                <label
                  class="text-uppercase text-success fw-bold small tracking-wide mb-2"
                  ><i class="fas fa-pencil-alt me-1"></i> Editează
                  Răspunsul</label
                >
                <textarea
                  v-model="editForm.answer"
                  class="form-control bg-light"
                  rows="3"
                ></textarea>
              </div>

              <div
                class="d-flex justify-content-end gap-2 mt-3 pt-3 border-top"
              >
                <button
                  @click="cancelEdit"
                  class="btn btn-sm btn-light rounded-pill px-3 fw-bold text-secondary"
                >
                  Anulează
                </button>
                <button
                  @click="saveEdit(card.id)"
                  class="btn btn-sm btn-success rounded-pill px-4 fw-bold shadow-sm"
                  :disabled="isSaving"
                >
                  <span
                    v-if="isSaving"
                    class="spinner-border spinner-border-sm me-1"
                  ></span>
                  <i v-else class="fas fa-save me-1"></i> Salvează
                </button>
              </div>
            </div>
          </div>
        </div>

        <div
          v-if="document.flashcards.length === 0"
          class="text-center py-5 text-muted"
        >
          <i class="fas fa-ghost fs-1 mb-3 opacity-50"></i>
          <p>Ai șters toate flashcard-urile din acest document.</p>
        </div>
      </div>
    </div>

    <ConfirmationModal
      :isVisible="showDeleteCardModal"
      :isLoading="isDeletingCard"
      title="Șterge Flashcard"
      message="Ești sigur că vrei să elimini această întrebare din set?"
      @confirm="executeDeleteCard"
      @cancel="closeDeleteCardModal"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import api from "@/services/api";
// Importăm componenta modal
import ConfirmationModal from "@/components/ConfirmationModal.vue";

const route = useRoute();
const router = useRouter();

const document = ref(null);
const loading = ref(true);

const editingId = ref(null);
const editForm = ref({ question: "", answer: "" });
const isSaving = ref(false);

// Variabile noi pentru modalul de ștergere flashcard
const showDeleteCardModal = ref(false);
const cardToDeleteId = ref(null);
const isDeletingCard = ref(false);

const loadDocument = async () => {
  const documentId = route.params.id;
  try {
    const rawToken = localStorage.getItem("auth") || "";
    const cleanToken = rawToken
      .replace(/^Bearer\s*/i, "")
      .replace(/[\r\n\s]+/g, "")
      .trim();

    const response = await api.get(`/documents/${documentId}`, {
      headers: { Authorization: `Bearer ${cleanToken}` },
    });
    document.value = response.data;
  } catch (error) {
    console.error("Eroare la încărcarea documentului:", error);
  } finally {
    loading.value = false;
  }
};

onMounted(() => {
  loadDocument();
});

const startEdit = (card) => {
  editingId.value = card.id;
  editForm.value = { question: card.question, answer: card.answer };
};

const cancelEdit = () => {
  editingId.value = null;
  editForm.value = { question: "", answer: "" };
};

const saveEdit = async (cardId) => {
  if (!editForm.value.question.trim() || !editForm.value.answer.trim()) return;

  isSaving.value = true;
  try {
    const rawToken = localStorage.getItem("auth") || "";
    const cleanToken = rawToken
      .replace(/^Bearer\s*/i, "")
      .replace(/[\r\n\s]+/g, "")
      .trim();

    await api.put(
      `/flashcards/${cardId}`,
      {
        question: editForm.value.question,
        answer: editForm.value.answer,
      },
      {
        headers: { Authorization: `Bearer ${cleanToken}` },
      },
    );

    const cardIndex = document.value.flashcards.findIndex(
      (c) => c.id === cardId,
    );
    if (cardIndex !== -1) {
      document.value.flashcards[cardIndex].question = editForm.value.question;
      document.value.flashcards[cardIndex].answer = editForm.value.answer;
    }

    cancelEdit();
  } catch (error) {
    console.error("Eroare la salvarea flashcard-ului:", error);
    if (window.addToast) {
      window.addToast("Nu s-a putut salva modificarea.", "error");
    } else {
      alert("Nu s-a putut salva modificarea.");
    }
  } finally {
    isSaving.value = false;
  }
};

// Functii noi pentru gestionarea modalului de ștergere
const promptDeleteCard = (cardId) => {
  cardToDeleteId.value = cardId;
  showDeleteCardModal.value = true;
};

const closeDeleteCardModal = () => {
  showDeleteCardModal.value = false;
  cardToDeleteId.value = null;
};

const executeDeleteCard = async () => {
  if (!cardToDeleteId.value) return;

  isDeletingCard.value = true;
  const cardId = cardToDeleteId.value;

  try {
    const rawToken = localStorage.getItem("auth") || "";
    const cleanToken = rawToken
      .replace(/^Bearer\s*/i, "")
      .replace(/[\r\n\s]+/g, "")
      .trim();

    await api.delete(`/flashcards/${cardId}`, {
      headers: { Authorization: `Bearer ${cleanToken}` },
    });

    document.value.flashcards = document.value.flashcards.filter(
      (c) => c.id !== cardId,
    );

    closeDeleteCardModal(); // Închidem modalul după succes

    if (window.addToast) {
      window.addToast("Flashcard șters.", "success");
    }
  } catch (error) {
    console.error("Eroare la ștergerea flashcard-ului:", error);
    if (window.addToast) {
      window.addToast("Nu s-a putut șterge flashcard-ul.", "error");
    }
  } finally {
    isDeletingCard.value = false;
  }
};
</script>
