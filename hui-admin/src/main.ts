import "virtual:uno.css";
import "@/styles/app.css";

import { createApp } from "vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import TDesign from "tdesign-vue-next";
import "tdesign-vue-next/es/style/index.css";
import App from "./App.vue";
import router from "./router";

const app = createApp(App);

const plugin = createPinia();
plugin.use(piniaPluginPersistedstate);

app.use(plugin);
app.use(router);
app.use(TDesign);

app.mount("#app");
