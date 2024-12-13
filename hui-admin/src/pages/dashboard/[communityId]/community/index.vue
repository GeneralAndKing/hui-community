<script setup lang="tsx">
import { useQuery } from "@tanstack/vue-query";
import { client } from "@/request";
import { type FormProps, type PrimaryTableCol, type TableProps } from "tdesign-vue-next";
import { computed, reactive, ref } from "vue";
import type { components } from "@/types/client";
import CommunityEditDialog from "@/pages/dashboard/[communityId]/community/_components/CommunityEditDialog.vue";

const condition: FormProps["data"] = reactive({
  likedName: "",
  likedCode: "",
  pageable: {
    page: 0,
    size: 10,
    sort: ["id,desc"]
  }
});
const { isPending, data, refetch } = useQuery({
  queryKey: ["community"],
  queryFn: async () => {
    const { data } = await client.GET("/sys-api/community/page", {
      params: {
        query: {
          likedName: condition.likedName,
          likedCode: condition.likedCode,
          pageable: condition.pageable
        }
      }
    });
    return data;
  },
  refetchOnMount: true
});
const editRef = ref<null | InstanceType<typeof CommunityEditDialog>>(null);

const columns = ref<PrimaryTableCol<components["schemas"]["CommunityResponse"]>[]>([
  {
    colKey: "name",
    title: "小区名称"
  },
  {
    colKey: "address",
    title: "地址"
  },
  {
    colKey: "areaId",
    title: "区域代码"
  },
  {
    colKey: "code",
    title: "小区代码"
  },
  {
    colKey: "id",
    title: "经纬度",
    cell: (_, { row }) => {
      const longitude = row.longitude as number;
      const latitude = row.latitude as number;
      return (
        <div>
          (
          {longitude}
          ,
          {latitude}
          )
        </div>
      );
    }
  },
  {
    colKey: "id",
    title: "操作",
    cell: (h, { row }) => {
      return (
        <div class="table-operations">
          <t-button
            theme="primary"
            variant="text"
            data-id={row.id}
            onClick={() => {
              editRef.value?.open(row.id, {
                ...row
              });
            }}
          >
            编辑
          </t-button>
        </div>
      );
    }
  }

]);

const pagination = computed(() => {
  return {
    defaultCurrent: 1,
    defaultPageSize: 10,
    total: data.value?.page?.totalElements ?? 0
  } as TableProps["pagination"];
});
const onSubmit: FormProps["onSubmit"] = () => {
  refetch();
};
const handleNewItem = () => {
  editRef.value?.open(undefined, undefined);
};
</script>

<template>
  <t-card bordered class="w-full flex h-full flex-col gap-4">
    <CommunityEditDialog ref="editRef" />
    <t-form
        ref="form"
        layout="inline"
        :data="condition"
        label-width="calc(2em + 24px)"
        scroll-to-first-error="smooth"
        @submit="onSubmit"
    >
      <t-form-item label="名称" name="likedDisplayName">
        <t-input v-model="condition.likedName" placeholder="请输入地区名称"/>
      </t-form-item>

      <t-form-item :status-icon="false">
        <t-space size="small">
          <t-button theme="primary" type="submit">搜索</t-button>
          <t-button theme="default" variant="base" @click="handleNewItem">新增</t-button>
        </t-space>
      </t-form-item>
    </t-form>
    <t-table
        hover
        :loading="isPending"
        row-key="index"
        :data="data?.content ?? []"
        :columns="columns"
        :pagination="pagination"
        class="flex-1 flex flex-col"
        style="margin-top: 1rem"
        :header-affixed-top="{
          container: `.main`
        }"
        :pagination-affixed-bottom="{
          container: `.main`
        }"
    ></t-table>
  </t-card>
</template>

<style>
.t-card__body {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.t-table__content {
  flex: 1;
}
</style>
