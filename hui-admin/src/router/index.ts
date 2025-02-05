import { createRouter, createWebHistory } from "vue-router";
import { routes, handleHotUpdate } from "vue-router/auto-routes";
import useAuthStore from "@/stores/auth";
import { MessagePlugin } from "tdesign-vue-next";
import type { AsideMenu } from "@/types";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});
if (import.meta.hot) {
  handleHotUpdate(router);
}

export const asideMenu: AsideMenu[] = [
  {
    name: "/dashboard/[communityId]/",
    title: "仪表盘",
    icon: "dashboard"
  },
  {
    name: "/dashboard/[communityId]/community/",
    title: "社区管理",
    icon: "city-1"
  },
  {
    name: "/dashboard/[communityId]/user/",
    title: "员工管理",
    icon: "user-1"
  },
  {
    name: "/dashboard/[communityId]/role/",
    title: "角色管理",
    icon: "user-list"
  },
  {
    name: "/dashboard/[communityId]/shop/",
    title: "商家管理",
    icon: "shop-2"
  }
];

export const NO_AUTH_PATH = [
  "/auth", "/", "/sys-api/login"
];

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  if (NO_AUTH_PATH.includes(to.path)) {
    next();
    return;
  }
  if (authStore.isAuth) {
    next();
    return;
  }
  void MessagePlugin.warning("你需要登录才能够访问");
  next("/auth");
});

export default router;
