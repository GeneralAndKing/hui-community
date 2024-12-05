import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import VueRouter from "unplugin-vue-router/vite";
import vueDevTools from "vite-plugin-vue-devtools";
import UnoCSS from "unocss/vite";

// https://vite.dev/config/
export default defineConfig(() => {
  return {
    plugins: [
      VueRouter({
        /* options */
      }),
      vue(),
      vueJsx(),
      vueDevTools(),
      UnoCSS()
    ],
    resolve: {
      alias: {
        "@": fileURLToPath(new URL("./src", import.meta.url))
      }
    },
    server: {
      proxy: {
        "/api": {
          target: "http://139.155.2.12:8080",
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, "")
        }
      }
    }
  };
});
