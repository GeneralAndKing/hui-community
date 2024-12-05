<script setup lang="ts">
import { RouterView } from "vue-router";
import { VueQueryDevtools } from "@tanstack/vue-query-devtools";
</script>

<template>
  <RouterView />
  <VueQueryDevtools />
</template>

<style>
body, html {
  height: 100vh;
  width: 100vw;
}
#app {
  width: 100%;
  height: 100%;
}
</style>
