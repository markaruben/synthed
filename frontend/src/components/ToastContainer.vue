<script setup>
import { ref, onMounted } from "vue";

const toasts = ref([]);
const addToast = (message, type = "success") => {
  const id = Date.now();
  toasts.value.push({ id, message, type });

  setTimeout(() => {
    removeToast(id);
  }, 3000);
};

const removeToast = (id) => {
  toasts.value = toasts.value.filter((t) => t.id !== id);
};

onMounted(() => {
  window.addToast = addToast;
});
</script>

<template>
  <div
    class="toast-wrapper position-fixed bottom-0 end-0 p-3"
    style="z-index: 9999"
  >
    <TransitionGroup name="list">
      <div
        v-for="toast in toasts"
        :key="toast.id"
        class="toast show align-items-center border-0 mb-2 shadow-lg"
        :class="
          toast.type === 'error'
            ? 'bg-danger text-white'
            : 'bg-success text-white'
        "
        role="alert"
        style="min-width: 300px"
      >
        <div class="d-flex">
          <div class="toast-body d-flex align-items-center gap-2">
            <i
              :class="
                toast.type === 'error'
                  ? 'fas fa-exclamation-circle'
                  : 'fas fa-check-circle'
              "
            ></i>
            <span class="fw-medium">{{ toast.message }}</span>
          </div>
          <button
            @click="removeToast(toast.id)"
            type="button"
            class="btn-close btn-close-white me-2 m-auto"
          ></button>
        </div>
      </div>
    </TransitionGroup>
  </div>
</template>

<style scoped>
.list-enter-active,
.list-leave-active {
  transition: all 0.3s ease;
}
.list-enter-from,
.list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
.toast {
  border-radius: 12px;
  backdrop-filter: blur(10px);
}
</style>
