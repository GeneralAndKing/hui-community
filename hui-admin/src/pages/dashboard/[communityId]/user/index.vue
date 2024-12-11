<script setup lang="tsx">
import { useRoute } from "vue-router";
import { useMutation, useQuery, useQueryClient } from "@tanstack/vue-query";
import { client } from "@/request";
import {
  computed,
  reactive,
  ref
} from "vue";
import { type FormProps, type PrimaryTableCol, type TableProps } from "tdesign-vue-next";
import type { components } from "@/types/client";
import UserEditDialog from "./_components/UserEditDialog.vue";

const route = useRoute("/dashboard/[communityId]/");
const communityId = route.params.communityId;
const condition: FormProps["data"] = reactive({
  likedUsername: "",
  likedDisplayName: "",
  pageable: {
    page: 0,
    size: 10,
    sort: ["id,desc"]
  }
});
const { isPending, data, refetch } = useQuery({
  queryKey: ["communityUsers"],
  queryFn: async () => {
    const { data } = await client.GET("/sys-api/community/{communityId}/sys-user/page", {
      params: {
        path: { communityId },
        query: {
          likedUsername: condition.likedUsername,
          likedDisplayName: condition.likedDisplayName,
          pageable: condition.pageable
        }
      }
    });
    return data;
  },
  refetchOnMount: true
});
const queryClient = useQueryClient();
const { mutate: disableMutate, isPending: disablePending } = useMutation({
  mutationFn: async (data: {
    id: string;
  }) => client.POST("/sys-api/sys-user/{sysUserId}/lock", {
    params: {
      path: { sysUserId: data.id }
    }
  }),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: ["communityUsers"] });
  }
});
const { mutate: enableMutate, isPending: enablePending } = useMutation({
  mutationFn: async (data: {
    id: string;
  }) => client.DELETE("/sys-api/sys-user/{sysUserId}/lock", {
    params: {
      path: { sysUserId: data.id }
    }
  }),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: ["communityUsers"] });
  }
});

const onSubmit: FormProps["onSubmit"] = () => {
  refetch();
};

const editRef = ref<null | InstanceType<typeof UserEditDialog>>(null);

const columns = ref<PrimaryTableCol<components["schemas"]["SysUserPageResponse"]>[]>([
  {
    colKey: "displayName",
    title: "用户名称"
  },
  {
    colKey: "username",
    title: "账号"
  },
  {
    colKey: "phone",
    title: "手机号"
  },
  {
    colKey: "email",
    title: "邮箱"
  },
  {
    colKey: "lockedTime",
    title: "锁定时间",
    cell: (_, { row }) => {
      const lockedTime = row.lockedTime;
      return lockedTime ? <div>{new Date(lockedTime).toLocaleString()}</div> : <div>-</div>;
    }
  },
  {
    colKey: "roles",
    title: "角色"
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
              console.log(editRef.value);
              editRef.value?.open({
                id: row.id,
                displayName: row.displayName,
                username: row.username,
                phone: row.phone,
                email: row.email
              });
            }}
          >
            编辑
          </t-button>
          <t-popconfirm
            theme="danger"
            content={`确认${row.lockedTime ? "解除禁用" : "禁用"}吗`}
            onConfirm={() => {
              if (!row.id) {
                return;
              }
              if (row.lockedTime) {
                enableMutate({ id: row.id });
              } else {
                disableMutate({ id: row.id });
              }
            }}
          >
            <t-button
              theme="danger"
              variant="text"
              disabled={row.lockedTime ? enablePending : disablePending}
              data-id={row.id}
              content={row.lockedTime ? "解禁" : "禁用"}
            />
          </t-popconfirm>
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
  editRef.value?.open(undefined);
};
</script>

<template>
  <t-card bordered class="w-full flex h-full flex-col gap-4">
    <UserEditDialog ref="editRef"/>
    <t-form
        ref="form"
        layout="inline"
        :data="condition"
        label-width="calc(2em + 24px)"
        scroll-to-first-error="smooth"
        @submit="onSubmit"
    >
      <t-form-item label="账号" name="likedDisplayName">
        <t-input v-model="condition.likedUsername" placeholder="请输入账号"/>
      </t-form-item>
      <t-form-item label="用户名" name="likedDisplayName">
        <t-input v-model="condition.likedDisplayName" placeholder="请输入用户名"/>
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
