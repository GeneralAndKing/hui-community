import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";
import Components from "unplugin-vue-components/vite";
import VueRouter from "unplugin-vue-router/vite";
import vueDevTools from "vite-plugin-vue-devtools";
import UnoCSS from "unocss/vite";
import { TDesignResolver } from "unplugin-vue-components/resolvers";

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    VueRouter({
      /* options */
    }),
    vue(),
    vueJsx(),
    vueDevTools(),
    UnoCSS(),
    Components({
      resolvers: [TDesignResolver()]
    })
  ],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url))
    }
  }
});
