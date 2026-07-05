<template>
  <div
    v-if="isVisible"
    class="position-fixed top-0 start-0 w-100 h-100 d-flex align-items-center justify-content-center"
    style="
      background: rgba(0, 0, 0, 0.4);
      backdrop-filter: blur(4px);
      z-index: 2000;
    "
  >
    <div
      class="bg-white rounded-5 shadow-lg p-4 p-md-5 animate-pop-in"
      style="max-width: 400px; width: 90%"
    >
      <div class="text-center mb-4">
        <div
          class="icon-circle mx-auto mb-3 d-flex align-items-center justify-content-center rounded-circle"
          :class="iconBgClass"
          style="width: 70px; height: 70px; font-size: 2rem"
        >
          <i :class="iconClass"></i>
        </div>
        <h4 class="fw-bold text-dark mb-2">{{ title }}</h4>
        <p class="text-secondary small mb-0">{{ message }}</p>
      </div>

      <div class="d-flex gap-3 mt-4">
        <button
          @click="$emit('cancel')"
          class="btn btn-light flex-grow-1 rounded-pill fw-bold text-secondary py-2"
          :disabled="isLoading"
        >
          {{ cancelText }}
        </button>
        <button
          @click="$emit('confirm')"
          class="btn flex-grow-1 rounded-pill fw-bold shadow-sm py-2"
          :class="confirmButtonClass"
          :disabled="isLoading"
        >
          <span
            v-if="isLoading"
            class="spinner-border spinner-border-sm me-1"
          ></span>
          <i v-else :class="confirmIconClass"></i> {{ confirmText }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  isVisible: Boolean,
  isLoading: Boolean,
  title: { type: String, default: "Confirmare" },
  message: { type: String, default: "Ești sigur că vrei să continui?" },
  cancelText: { type: String, default: "Anulează" },
  confirmText: { type: String, default: "Confirmă" },
  confirmButtonClass: { type: String, default: "btn-danger" },
  iconClass: { type: String, default: "fas fa-exclamation-triangle" },
  iconBgClass: { type: String, default: "bg-danger bg-opacity-10 text-danger" },
  confirmIconClass: { type: String, default: "fas fa-trash-alt me-1" },
});

defineEmits(["confirm", "cancel"]);
</script>
