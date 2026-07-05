<script setup>
import { RouterView } from "vue-router";
import ToastContainer from "@/components/ToastContainer.vue";
import { computed } from "vue";
import { useRoute } from "vue-router";
import Navbar from "@/components/Navbar.vue";
import Footer from "@/components/Footer.vue";

const route = useRoute();

const showGlobalLayout = computed(() => {
  const hiddenRoutes = ["login", "register"];
  return !hiddenRoutes.includes(route.name);
});
</script>

<template>
  <div class="app-wrapper d-flex flex-column min-vh-100">
    <Navbar v-if="showGlobalLayout" />
    <ToastContainer />

    <main class="flex-grow-1" :class="{ 'with-navbar': showGlobalLayout }">
      <RouterView />
    </main>

    <Footer v-if="showGlobalLayout" />
  </div>
</template>

<style>
.app-wrapper {
  background-color: #f8f9fa;
}

.with-navbar {
  padding-top: 85px;
}
</style>
