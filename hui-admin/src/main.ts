import "virtual:uno.css";
import "@/styles/app.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import TDesign from "tdesign-vue-next";
import "tdesign-vue-next/es/style/index.css";
import App from "./App.vue";
import router from "./router";
import { VueQueryPlugin } from "@tanstack/vue-query";

const app = createApp(App);

app.use(router);
app.use(TDesign);
app.use(VueQueryPlugin);

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);

app.mount("#app");
