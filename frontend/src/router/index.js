import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "../views/LoginView.vue";
import RegisterView from "../views/RegisterView.vue";
import DashboardView from "../views/DashboardView.vue";
import StudyView from "../views/StudyView.vue";
import ProfileView from "../views/ProfileView.vue";
import NotFoundView from "../views/NotFoundView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", name: "home", component: HomeView },
    { path: "/login", name: "login", component: LoginView },
    { path: "/register", name: "register", component: RegisterView },
    { path: "/dashboard", name: "dashboard", component: DashboardView },
    { path: "/study/:id", name: "study", component: StudyView },
    { path: "/profile", name: "profile", component: ProfileView },
    { path: "/:pathMatch(.*)*", name: "not-found", component: NotFoundView },
  ],
});

export default router;
