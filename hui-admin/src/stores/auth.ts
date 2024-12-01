import { ref, computed, reactive } from "vue";
import { defineStore } from "pinia";
import type { paths } from "@/types/client";
import { client } from "@/request";
import { MessagePlugin } from "tdesign-vue-next";
import type { AuthToken, ResponseError } from '@/types'
import { useRouter } from "vue-router";

const useAuthStore = defineStore(
  "auth",
  () => {
    const auth = reactive<AuthToken>({});
    const signInLoading = ref<boolean>(false);
    const isAuth = computed(() => auth.accessToken !== undefined);
    const router = useRouter();

    const signIn = (formData: { username: string; password: string }) => {
      signInLoading.value = true;
      client
        .POST("/sys-api/login", {
          body: formData
        })
        .then(({ data, error }) => {
          if (error) {
            console.error(error);
            void MessagePlugin.error(`登录失败: ${(error as ResponseError).message}`);
            return;
          }
          auth.id = data.subject;
          auth.subject = data.subject;
          auth.username = data.username;
          auth.accessToken = data.accessToken;
          auth.refreshToken = data.refreshToken;
          void MessagePlugin.success("登录成功");
          void router.push("/dashboard");
        })
        .finally(() => {
          signInLoading.value = false;
        });
    };

    return {
      signIn,
      auth,
      isAuth,
      isLoading: signInLoading
    };
  },
  {
    persist: true
  }
);

export default useAuthStore;
