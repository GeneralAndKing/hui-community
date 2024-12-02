<script setup lang="ts">
import { RouterView } from "vue-router";
import useAuthStore from "@/stores/auth";
import { onMounted } from "vue";

const authStore = useAuthStore();

onMounted(authStore.getUserInfo);
</script>

<template>
  <t-layout class="w-full h-full">
    <t-header class="border-t-0 border-x-0 border-b border-solid border-b-gray-200 border-b-2">
      <t-head-menu height="120px">
        <template #logo>
          <div class="font-semibold  text-lg  tracking-widest  text-gray-700  hover:text-blue-600  transition-all  duration-300  py-4  cursor-pointer  uppercase ">
            Hui 社区
          </div>
        </template>
        <template #operations>
          <div>{{authStore.username}}</div>
        </template>
      </t-head-menu>
    </t-header>
    <t-layout>
      <t-aside>
        <t-menu theme="light" value="dashboard" style="margin-right: 50px" height="550px">
          <t-menu-item value="dashboard">
            <template #icon>
              <t-icon name="dashboard" />
            </template>
            社区管理
          </t-menu-item>
        </t-menu>
      </t-aside>
      <t-layout>
        <t-content>
          <div class="w-full h-full flex flex-col gap-y-4 p-4">
            <RouterView />
          </div>
        </t-content>
        <t-footer>Copyright @ 2019-{{ new Date().getFullYear() }} Tencent. All Rights Reserved</t-footer>
      </t-layout>
    </t-layout>
  </t-layout>
</template>

<style scoped>

</style>
