import createClient, { type Middleware } from "openapi-fetch";
import type { paths } from "./types/client";
import useAuthStore from "@/stores/auth";
import router, { NO_AUTH_PATH } from "@/router";
import type { ResponseError } from "@/types";
import { MessagePlugin } from "tdesign-vue-next";

const authMiddleware: Middleware = {
  async onRequest({ request, schemaPath }) {
    const authStore = useAuthStore();
    if (NO_AUTH_PATH.includes(schemaPath)) {
      return undefined;
    }
    if (authStore.isAuth) {
      request.headers.set("Authorization", `Bearer ${authStore.auth.accessToken}`);
    }
    return request;
  }
};

const errorMiddleware: Middleware = {
  onResponse: async ({ response }) => {
    if (response.ok) {
      return;
    }
    const data: ResponseError = await response.clone().json();
    let message = `未知错误： ${data.message}`
    if (response.status >= 500) {
      message = `服务器内部错误：${data.message}`
    } else if (response.status === 401) {
      message = `当前未登录：${data.message}`
      void router.push('/auth')
    } else if (response.status === 403) {
      message = `当前没有权限访问：${data.message}`
    }
    void MessagePlugin.error(message);
    throw new Error(message)
  }
};

export const client = createClient<paths>({
  baseUrl: import.meta.env.VITE_SERVICE_BASE_URL
});

client.use(authMiddleware);
client.use(errorMiddleware);
