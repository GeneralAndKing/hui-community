<script setup lang="ts">
import useAuthStore from '@/stores/auth'
import { useQuery } from '@tanstack/vue-query'
import { client } from '@/request'
import { onMounted } from 'vue'

const authStore = useAuthStore()

useQuery({
  queryKey: ['menu'],
  queryFn: async () => {
    const { data, error } = await client.GET('/sys-api/permission')
    console.log(data)
    return data
  }
})

onMounted(authStore.getUserInfo)

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
            仪表盘
          </t-menu-item>
          <t-menu-item value="resource">
            <template #icon>
              <t-icon name="server" />
            </template>
            资源列表
          </t-menu-item>
          <t-menu-item value="root">
            <template #icon>
              <t-icon name="root-list" />
            </template>
            根目录
          </t-menu-item>
          <t-menu-item value="control-platform">
            <template #icon>
              <t-icon name="control-platform" />
            </template>
            调度平台
          </t-menu-item>
          <t-menu-item value="precise-monitor">
            <template #icon>
              <t-icon name="precise-monitor" />
            </template>
            调度平台
          </t-menu-item>
          <t-menu-item value="mail">
            <template #icon>
              <t-icon name="mail" />
            </template>
            消息区
          </t-menu-item>
          <t-menu-item value="user-circle">
            <template #icon>
              <t-icon name="user-circle" />
            </template>
            个人中心
          </t-menu-item>
          <t-menu-item value="play-circle">
            <template #icon>
              <t-icon name="play-circle" />
            </template>
            视频区
          </t-menu-item>
          <t-menu-item value="edit1">
            <template #icon>
              <t-icon name="edit-1" />
            </template>
            资源编辑
          </t-menu-item>
        </t-menu>
      </t-aside>
      <t-layout>
        <t-content>
          <div>Content</div>
        </t-content>
        <t-footer>Copyright @ 2019-{{ new Date().getFullYear() }} Tencent. All Rights Reserved</t-footer>
      </t-layout>
    </t-layout>
  </t-layout>
</template>

<style scoped>

</style>
