<script setup>
import { ref, computed, onMounted, onUnmounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import confetti from "canvas-confetti";

const route = useRoute();
const router = useRouter();
const documentId = route.params.id;

const doc = ref(null);
const loading = ref(true);
const error = ref(null);

const allCards = ref([]);
const activeCards = ref([]);
const missedCards = ref([]);

const currentIndex = ref(0);
const isFlipped = ref(false);
const isFinished = ref(false);

const stats = ref({
  easy: 0,
  hard: 0,
});

const currentCard = computed(() => {
  if (!activeCards.value || activeCards.value.length === 0) return null;
  return activeCards.value[currentIndex.value];
});

const fetchDocumentDetails = async () => {
  loading.value = true;
  error.value = null;

  const authHeader = localStorage.getItem("auth");
  if (!authHeader) {
    router.push("/");
    return;
  }

  try {
    const response = await axios.get(
      `http://localhost:8080/api/documents/${documentId}`,
      {
        headers: { Authorization: authHeader },
      },
    );

    doc.value = response.data;

    if (doc.value.flashcards && doc.value.flashcards.length > 0) {
      allCards.value = doc.value.flashcards;
      activeCards.value = [...allCards.value];
    } else {
      error.value = "Acest curs nu are încă flashcard-uri generate.";
    }
  } catch (err) {
    console.error("Eroare la preluarea datelor:", err);
    error.value = "Nu am putut încărca flashcard-urile.";
    if (err.response && err.response.status === 401) {
      router.push("/");
    }
  } finally {
    loading.value = false;
  }
};

const flipCard = () => {
  isFlipped.value = !isFlipped.value;
};

const handleAnswer = (difficulty) => {
  if (difficulty === "easy") {
    stats.value.easy++;
  } else if (difficulty === "hard") {
    stats.value.hard++;
    missedCards.value.push(activeCards.value[currentIndex.value]);
  }

  if (currentIndex.value < activeCards.value.length - 1) {
    isFlipped.value = false;
    setTimeout(() => {
      currentIndex.value++;
    }, 150);
  } else {
    finishSession();
  }
};

const finishSession = () => {
  isFinished.value = true;
  if (missedCards.value.length === 0) {
    triggerConfetti();
  }
};

const reviewMissed = () => {
  activeCards.value = [...missedCards.value];
  missedCards.value = [];
  currentIndex.value = 0;
  isFlipped.value = false;
  isFinished.value = false;
  stats.value = { easy: 0, hard: 0 };
};

const restartSession = () => {
  activeCards.value = [...allCards.value];
  missedCards.value = [];
  currentIndex.value = 0;
  isFlipped.value = false;
  isFinished.value = false;
  stats.value = { easy: 0, hard: 0 };
};

const triggerConfetti = () => {
  const duration = 3000;
  const animationEnd = Date.now() + duration;
  const defaults = { startVelocity: 30, spread: 360, ticks: 60, zIndex: 0 };
  const randomInRange = (min, max) => Math.random() * (max - min) + min;

  const interval = setInterval(function () {
    const timeLeft = animationEnd - Date.now();
    if (timeLeft <= 0) return clearInterval(interval);

    const particleCount = 50 * (timeLeft / duration);
    confetti({
      ...defaults,
      particleCount,
      origin: { x: randomInRange(0.1, 0.3), y: Math.random() - 0.2 },
      colors: ["#4f46e5", "#818cf8", "#ffffff"],
    });
    confetti({
      ...defaults,
      particleCount,
      origin: { x: randomInRange(0.7, 0.9), y: Math.random() - 0.2 },
      colors: ["#4f46e5", "#818cf8", "#ffffff"],
    });
  }, 250);
};

const handleKeydown = (e) => {
  if (loading.value || isFinished.value || !currentCard.value) return;

  if (e.code === "Space") {
    e.preventDefault();
    flipCard();
  } else if (e.code === "ArrowRight" && isFlipped.value) {
    handleAnswer("easy");
  } else if (e.code === "ArrowLeft" && isFlipped.value) {
    handleAnswer("hard");
  }
};

onMounted(() => {
  fetchDocumentDetails();
  window.addEventListener("keydown", handleKeydown);
});

onUnmounted(() => {
  window.removeEventListener("keydown", handleKeydown);
});
</script>

<template>
  <div class="study-container min-vh-100 d-flex flex-column">
    <nav class="navbar px-4 py-3 position-absolute w-100" style="z-index: 10">
      <div class="d-flex align-items-center gap-3 w-100">
        <button
          @click="router.push('/dashboard')"
          class="btn btn-white rounded-circle shadow-sm border"
          style="width: 40px; height: 40px"
        >
          <i class="fas fa-times text-secondary"></i>
        </button>

        <div
          class="flex-grow-1 mx-3 d-none d-md-flex gap-1"
          v-if="activeCards.length > 0 && !isFinished"
        >
          <div
            v-for="(c, idx) in activeCards"
            :key="idx"
            class="progress-segment rounded-pill"
            :class="{
              active: idx <= currentIndex,
              current: idx === currentIndex,
            }"
          ></div>
        </div>

        <div
          class="badge bg-white text-dark shadow-sm border px-3 py-2 rounded-pill"
          v-if="activeCards.length > 0 && !isFinished"
        >
          {{ currentIndex + 1 }} / {{ activeCards.length }}
        </div>
      </div>
    </nav>

    <div
      class="flex-grow-1 d-flex align-items-center justify-content-center p-4 position-relative overflow-hidden"
    >
      <div class="blob blob-1"></div>
      <div class="blob blob-2"></div>

      <div
        v-if="loading"
        class="spinner-border text-primary"
        role="status"
      ></div>

      <div
        v-else-if="doc && (!doc.flashcards || doc.flashcards.length === 0)"
        class="text-center position-relative z-1 animate-pop-in"
      >
        <div
          class="bg-white p-5 rounded-5 shadow-lg border text-center mx-auto"
          style="max-width: 450px"
        >
          <div
            class="icon-circle bg-secondary bg-opacity-10 text-secondary mx-auto mb-4"
            style="width: 80px; height: 80px; font-size: 2rem"
          >
            <i class="fas fa-ghost"></i>
          </div>
          <h3 class="fw-bold mb-2">Ups! Niciun Flashcard.</h3>
          <p class="text-muted mb-4 small">
            AI-ul nu a putut extrage informații. Posibil ca PDF-ul să fie o
            imagine scanată sau să conțină prea puțin text.
          </p>
          <button
            @click="router.push('/dashboard')"
            class="btn btn-primary w-100 rounded-3 fw-bold shadow-sm"
          >
            <i class="fas fa-arrow-left me-2"></i> Înapoi la Dashboard
          </button>
        </div>
      </div>

      <div v-else-if="isFinished" class="text-center py-5">
        <div class="mb-4">
          <i
            v-if="missedCards.length === 0"
            class="fas fa-trophy text-warning"
            style="font-size: 5rem"
          ></i>
          <i
            v-else
            class="fas fa-tasks text-primary"
            style="font-size: 5rem"
          ></i>
        </div>
        <h2 class="fw-bold mb-3">
          {{
            missedCards.length === 0
              ? "Felicitări! Ai reținut tot!"
              : "Sesiune finalizată"
          }}
        </h2>
        <p class="text-secondary mb-5">
          Ai parcurs {{ activeCards.length }} carduri în această rundă.
        </p>

        <div
          class="d-flex flex-column flex-sm-row justify-content-center gap-3 max-w-400 mx-auto"
        >
          <button
            v-if="missedCards.length > 0"
            @click="reviewMissed"
            class="btn btn-warning btn-lg rounded-pill fw-bold shadow-sm px-4"
          >
            <i class="fas fa-redo me-2"></i> Repetă cardurile greșite ({{
              missedCards.length
            }})
          </button>

          <button
            @click="restartSession"
            class="btn btn-primary btn-lg rounded-pill fw-bold shadow-sm px-4"
          >
            <i class="fas fa-sync-alt me-2"></i> Începe de la zero ({{
              allCards.length
            }})
          </button>
        </div>
      </div>

      <div v-else-if="currentCard" class="scene position-relative z-1">
        <div
          class="flashcard"
          :class="{ 'is-flipped': isFlipped }"
          @click="flipCard"
        >
          <div
            class="card__face card__face--front shadow-2xl rounded-5 bg-white d-flex flex-column align-items-center justify-content-center p-5 text-center"
          >
            <div
              class="text-uppercase text-muted fw-bold small mb-3 tracking-wide"
            >
              Întrebare
            </div>
            <h3 class="fw-bold text-dark lh-base">
              {{ currentCard.question }}
            </h3>
            <div class="mt-auto text-muted small opacity-50">
              <i class="fas fa-mouse-pointer me-1"></i> Apasă sau Space pentru
              răspuns
            </div>
          </div>

          <div
            class="card__face card__face--back shadow-2xl rounded-5 bg-white d-flex flex-column align-items-center justify-content-center p-5 text-center border-top border-4 border-primary"
          >
            <div
              class="text-uppercase text-primary fw-bold small mb-3 tracking-wide"
            >
              Răspuns
            </div>
            <p class="lead text-dark mb-4">{{ currentCard.answer }}</p>

            <div class="d-flex gap-3 w-100 mt-auto" @click.stop>
              <button
                @click="handleAnswer('hard')"
                class="btn btn-outline-danger flex-grow-1 py-2 fw-bold rounded-3 hover-scale"
              >
                <i class="fas fa-times me-2"></i> Nu Știu
              </button>
              <button
                @click="handleAnswer('easy')"
                class="btn btn-success flex-grow-1 py-2 fw-bold rounded-3 shadow-sm hover-scale text-white"
              >
                <i class="fas fa-check me-2"></i> Știu
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.study-container {
  background-color: #f8fafc;
  overflow: hidden;
  position: relative;
}

.blob {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.6;
}
.blob-1 {
  top: -10%;
  left: -10%;
  width: 600px;
  height: 600px;
  background: #e0e7ff;
}
.blob-2 {
  bottom: -10%;
  right: -10%;
  width: 500px;
  height: 500px;
  background: #d1fae5;
}

.progress-segment {
  height: 6px;
  background-color: #e2e8f0;
  flex-grow: 1;
  transition: background-color 0.3s ease;
}
.progress-segment.active {
  background-color: #4f46e5;
}
.progress-segment.current {
  background-color: #818cf8;
}

.scene {
  width: 100%;
  max-width: 600px;
  height: 400px;
  perspective: 1000px;
}
.flashcard {
  width: 100%;
  height: 100%;
  position: relative;
  transition: transform 0.6s cubic-bezier(0.4, 0.2, 0.2, 1);
  transform-style: preserve-3d;
  cursor: pointer;
}
.flashcard.is-flipped {
  transform: rotateY(180deg);
}

.card__face {
  position: absolute;
  width: 100%;
  height: 100%;
  backface-visibility: hidden;
  -webkit-backface-visibility: hidden;
  border: 1px solid rgba(255, 255, 255, 0.6);
}
.card__face--front {
  background: white;
}
.card__face--back {
  background: white;
  transform: rotateY(180deg);
}

.shadow-2xl {
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.15);
}
.tracking-wide {
  letter-spacing: 0.1em;
}
.hover-scale {
  transition: transform 0.1s;
}
.hover-scale:active {
  transform: scale(0.95);
}

.animate-pop-in {
  animation: popIn 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}
@keyframes popIn {
  from {
    opacity: 0;
    transform: scale(0.8);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.icon-circle {
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
