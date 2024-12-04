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
    let message = `未知错误： ${data.message || data.error}`
    if (response.status >= 500) {
      message = `服务器内部错误：${data.message || data.error}`
    } else if (response.status === 401) {
      message = `当前未登录：${data.message || data.error}`
      void router.push('/auth')
    } else if (response.status === 403) {
      message = `当前没有权限访问：${data.message || data.error}`
    } else if (response.status === 400) {
      message = `请求参数错误：${data.message || data.error}`
    }
    void MessagePlugin.error(message);
    throw new Error(message)
  }
};

export const client = createClient<paths>({
  baseUrl: '/api',
  querySerializer: (query) => {
    const queryArray: string[] = []
    for (const [k, v] of Object.entries(query)) {
      if (Array.isArray(v)) {
        // 数组格式需要修改为 a=1&a=2 而不是 a[]=1,2
        for (const value of v) {
          queryArray.push(`${k}=${value}`)
        }
      } else if (k === 'pageable') {
        // Spring boot 接受的参数是分页的时候需要进行处理下
        const pv = v as Record<string, never>;
        for (const [k, v] of Object.entries(pv)) {
          queryArray.push(`${k}=${v}`)
        }
      } else {
        queryArray.push(`${k}=${v}`)
      }
    }
    // 可以添加打印语句看看结果 console.log(`query param ${queryArray.join("&")}`)
    return queryArray.join("&");
  }
});

client.use(authMiddleware);
client.use(errorMiddleware);
