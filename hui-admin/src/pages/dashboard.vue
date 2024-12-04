<script setup lang="ts">
import { RouterView, useRoute, useRouter } from "vue-router";
import { computed, onMounted } from "vue";
import useApplicationStore from "@/stores/application";
import useAuthStore from "@/stores/auth";
import type { DropdownOption } from "tdesign-vue-next/es/dropdown/type";
import { type DropdownProps, MessagePlugin } from "tdesign-vue-next";
import { asideMenu } from "@/router";
const applicationStore = useApplicationStore();
const authStore = useAuthStore();
const router = useRouter();
const route = useRoute("/dashboard/[communityId]/");
const communityId = route.params.communityId;

console.log(route.name)

onMounted(() => {
  void applicationStore.getUserInfo();
  if (!applicationStore.currentCommunity && applicationStore.communityList.length === 0) {
    MessagePlugin.error({ content: "请先选择或创建社区" });
    return;
  }
  console.log(route.name);
  if (applicationStore.currentCommunity && !communityId) {
    router.push(`/dashboard/${applicationStore.currentCommunity.id}`);
    return;
  }
  if (!applicationStore.currentCommunity && applicationStore.communityList.length > 0) {
    const firstElement = applicationStore.communityList[0];
    applicationStore.setCurrentCommunity(firstElement);
    router.push(`/dashboard/${firstElement.id}`);
    return;
  }
});

const communityMenu = computed(() =>
  applicationStore.communityList.map(
    (item) =>
      ({
        content: item.name,
        value: item.id
      }) as DropdownOption
  )
);

const handleSelectCommunity: DropdownProps["onClick"] = (data) => {
  if (data.value && data.content) {
    applicationStore.setCurrentCommunity({
      id: data.value as string,
      name: data.content as string
    });
    router.push(`/dashboard/${data.value}`);
  }
};
</script>

<template>
  <t-layout class="w-full h-full">
    <t-header class="border-t-0 border-x-0 border-b border-solid border-b-gray-200 border-b-2">
      <t-head-menu height="120px">
        <template #logo>
          <div class="font-semibold  text-lg  tracking-widest  text-gray-700  hover:text-blue-600  transition-all  duration-300  py-4  cursor-pointer  uppercase ">
            Hui 社区
          </div>
            <t-dropdown :options="communityMenu" trigger="click" @click="handleSelectCommunity">
              <t-space>
                <t-button size="extra-small" variant="text">{{applicationStore.currentCommunity?.name}}</t-button>
              </t-space>
            </t-dropdown>
        </template>
        <template #operations>
          <div>{{authStore.username}}</div>
        </template>
      </t-head-menu>
    </t-header>
    <t-layout>
      <t-aside>
        <t-menu theme="light" :defaultValue="route.name" style="margin-right: 50px" height="550px">
          <template v-for="item in asideMenu" :key="item.name">
            <t-menu-item :to="{ name: item.name, params: { communityId: communityId } }" :value="item.name">
              <template #icon>
                <t-icon :name="item.icon" />
              </template>
              {{ item.title }}
            </t-menu-item>
          </template>
        </t-menu>
      </t-aside>
      <t-layout>
        <t-content>
          <div class="p-4 h-full main">
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
