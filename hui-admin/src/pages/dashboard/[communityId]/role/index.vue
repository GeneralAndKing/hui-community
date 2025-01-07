<script setup lang="tsx">
import { useRoute } from "vue-router";
import type { FormProps, PrimaryTableCol, TableProps } from "tdesign-vue-next";
import { computed, reactive, ref } from "vue";
import { useQuery } from "@tanstack/vue-query";
import { client } from "@/request";
import type { components } from "@/types/client";
import RoleEditDialog from "@/pages/dashboard/[communityId]/role/_components/RoleEditDialog.vue";

const route = useRoute("/dashboard/[communityId]/");
const communityId = route.params.communityId;
const condition: FormProps["data"] = reactive({
  likedName: "",
  pageable: {
    page: 0,
    size: 10,
    sort: ["id,desc"]
  }
});
const editRef = ref<null | InstanceType<typeof RoleEditDialog>>(null);

const { isPending, data, refetch } = useQuery({
  queryKey: ["communityRoles"],
  queryFn: async () => {
    const { data } = await client.GET("/sys-api/community/{communityId}/role/page", {
      params: {
        path: { communityId },
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

const { data: permission } = useQuery({
  queryKey: ["permission"],
  queryFn: async () => {
    const { data } = await client.GET("/sys-api/permission");
    return data;
  },
  refetchOnMount: false
});

const onSubmit: FormProps["onSubmit"] = () => {
  refetch();
};

const columns = ref<PrimaryTableCol<components["schemas"]["SysUserRolePageResponse"]>[]>([
  {
    colKey: "name",
    title: "角色名称"
  },
  {
    colKey: "description",
    title: "描述信息"
  },
  {
    colKey: "updateTime",
    title: "更新时间",
    cell: (_, { row }) => {
      const updateTime = row.updateTime;
      return updateTime ? <div>{new Date(updateTime).toLocaleString()}</div> : <div>-</div>;
    }
  },
  {
    colKey: "createTime",
    title: "创建时间",
    cell: (_, { row }) => {
      const createTime = row.createTime;
      return createTime ? <div>{new Date(createTime).toLocaleString()}</div> : <div>-</div>;
    }
  },
  {
    colKey: "permissions",
    title: "权限",
    cell: (_, { row }) => {
      const permissions = row.permissions;
      return (
        <div>
          {(!permissions || permissions?.length === 0)
            ? "-"
            : (
                <div>
                  {permissions.map(permission => (
                    <t-tag key={permission.id} theme="primary" variant="light-outline">
                      {permission.type}
                      -
                      {permission.name}
                    </t-tag>
                  ))}
                </div>
              )}
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
                name: row.name ?? "",
                description: row.description ?? "",
                permissionIds: (row.permissions ?? []).filter(item => item !== undefined).map(permission => permission.id as string)
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
const handleNewItem = () => {
  editRef.value?.open(undefined, undefined);
};
</script>

<template>
  <t-card bordered class="w-full flex h-full flex-col gap-4">
    <RoleEditDialog ref="editRef" :permission="permission" />
    <t-form
        ref="form"
        layout="inline"
        :data="condition"
        label-width="calc(2em + 24px)"
        scroll-to-first-error="smooth"
        @submit="onSubmit"
    >
      <t-form-item label="账号" name="likedDisplayName">
        <t-input v-model="condition.likedName" placeholder="请输入角色名"/>
      </t-form-item>

      <t-form-item :status-icon="false">
        <t-space size="small">
          <t-button theme="primary" :loading="isPending" type="submit">搜索</t-button>
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
