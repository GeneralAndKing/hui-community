<script setup lang="ts">
import { useRoute } from "vue-router";

const route = useRoute("/dashboard/[communityId]/");
const communityId = route.params.communityId;
console.log(communityId);
</script>

<template>
  <div>
    Community Manager
  </div>
</template>

<style scoped>

</style>
