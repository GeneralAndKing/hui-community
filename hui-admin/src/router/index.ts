import { createRouter, createWebHistory } from "vue-router";
import { routes, handleHotUpdate } from "vue-router/auto-routes";
import useAuthStore from '@/stores/auth'
import { MessagePlugin } from 'tdesign-vue-next'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});
if (import.meta.hot) {
  handleHotUpdate(router);
}

export const NO_AUTH_PATH = [
  '/auth', '/', '/sys-api/login'
]

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  if (NO_AUTH_PATH.includes(to.path)) {
    next()
    return
  }
  if (authStore.isAuth) {
    next()
    return
  }
  void MessagePlugin.warning("你需要登录才能够访问")
  next('/auth')
})

export default router;
