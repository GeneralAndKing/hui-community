<script setup lang="ts">
import type { FormProps, PrimaryTableCol } from "tdesign-vue-next";
import { reactive, ref } from "vue";
import { useRoute } from "vue-router";
import { useQuery } from "@tanstack/vue-query";
import { client } from "@/request";
import type { components } from "@/types/client";

const condition: FormProps["data"] = reactive({
  likedName: "",
  pageable: {
    page: 0,
    size: 10,
    sort: ["id,desc"]
  }
});

import { type ListItemMetaProps } from "tdesign-vue-next";
import { CalendarIcon } from "tdesign-icons-vue-next";

const imageUrl: ListItemMetaProps["image"] = "https://tdesign.gtimg.com/site/avatar.jpg";

const { isPending, data, refetch } = useQuery({
  queryKey: ["shopPage"],
  queryFn: async () => {
    const { data } = await client.GET("/sys-api/shop/page", {
      params: {
        query: {
          likedName: condition.likedName,
          pageable: condition.pageable
        }
      }
    });
    return data;
  },
  refetchOnMount: true
});

</script>

<template>
  <t-card bordered class="w-full flex h-full flex-row w-full gap-4">
    <div class="w-64 px-4 border-r-[#d7d7d77e] border-r-1 border-r-solid flex flex-col gap-2 h-full">
      <t-input class="mt-4" v-model="condition.likedName" placeholder="商家名称"/>
      <div class="flex-1 flex flex-col gap-2">
        <template v-for="item in (data?.content ?? [])" :key="`${item.id}`">
          <div class="flex flex-row gap-2 items-center py-2 px-1 rounded-md cursor-pointer hover:bg-blue-1 transition-all">
            <t-avatar size="large" :image="item.facadeImg" :hide-on-load-failed="false" />
            <div class="flex flex-col">
              <div class="text-md truncate">{{item.name}}</div>
              <div class="text-sm text-gray-400 truncate">{{item.notice}}</div>
            </div>
          </div>
        </template>
      </div>
      <div class="mb-4 flex flex-col items-center">
        <div class="text-gray-400 text-xs">共 12 页 22 条数据</div>
        <div class="text-gray-400 text-xs">
          <t-button shape="circle" variant="text">
            <template v-slot:icon>
              <calendar-icon  />
            </template>
          </t-button>
        </div>
      </div>
    </div>
    <div class="flex-1 p-4">
      test
    </div>
  </t-card>
</template>

<style>
.t-card__body {
  display: flex;
  width: 100%;
  padding: 0;
  flex-direction: row;
}

.t-table__content {
  flex: 1;
}
</style>
