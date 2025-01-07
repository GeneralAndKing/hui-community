<script setup lang="ts">

import { useRoute } from "vue-router";
import { computed, reactive, ref } from "vue";
import { useMutation, useQuery, useQueryClient } from "@tanstack/vue-query";
import type { FormInstanceFunctions, FormProps } from "tdesign-vue-next";
import type { components } from "@/types/client";
import { client } from "@/request";

const props = defineProps<{
  permission: components["schemas"]["PermissionResponse"][];
}>();

const options = computed(() => props.permission.map(item => ({
  label: `[${item.type}] ${item.name}`,
  value: item.id
})));

const route = useRoute("/dashboard/[communityId]/role/");
const communityId = route.params.communityId;

const id = ref<string>();
const form = ref<FormInstanceFunctions | null>(null);
const formData = reactive<components["schemas"]["AddSysUserRoleRequest"]>({
  name: "",
  description: "",
  permissionIds: []
});
const visible = ref(false);

const handleOpen = (editId: string | undefined, current: components["schemas"]["AddSysUserRoleRequest"] | undefined) => {
  visible.value = true;
  if (editId) {
    id.value = editId;
  } else {
    id.value = undefined;
  }
  void form.value?.reset();
  if (current) {
    formData.name = current.name;
    formData.description = current.description;
    formData.permissionIds = current.permissionIds;
  }
  void form.value?.clearValidate();
};
defineExpose({ open: handleOpen });
const queryClient = useQueryClient();
const { mutate: addMutate, isPending: addPending } = useMutation({
  mutationFn: async (data: components["schemas"]["AddSysUserRoleRequest"]) =>
    client.POST("/sys-api/community/{communityId}/role", {
      params: { path: { communityId } },
      body: data
    }),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: ["communityRoles"] });
    visible.value = false;
  }
});
const { mutate: updateMutate, isPending: updatePending } = useMutation({
  mutationFn: async (data: {
    sysUserRoleId: string;
    body: components["schemas"]["AddSysUserRoleRequest"];
  }) =>
    client.PUT("/sys-api/community/{communityId}/role/{sysUserRoleId}", {
      params: { path: { communityId, sysUserRoleId: data.sysUserRoleId } },
      body: data.body
    }),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: ["communityRoles"] });
    visible.value = false;
  }
});

const handleSubmit = async () => {
  const validate = await form.value?.validate();
  if (!validate) {
    return;
  }
  console.log(formData);
  if (id.value) {
    updateMutate({ sysUserRoleId: id.value, body: formData });
  } else {
    addMutate(formData);
  }
};
const rules: FormProps["rules"] = {
  name: [
    { required: true, message: "角色名必填", type: "error", trigger: "blur" },
    { required: true, message: "角色名必填", type: "error", trigger: "change" },
    { whitespace: true, message: "角色名不能为空" }
  ],
  description: [
    { required: true, message: "角色名必填", type: "error", trigger: "blur" },
    { required: true, message: "角色名必填", type: "error", trigger: "change" },
    { whitespace: true, message: "角色名不能为空" }
  ]
};
</script>

<template>
  <t-dialog
      :visible="visible" :close-btn="true"
      :closeOnEscKeydown="false"
      width="800"
      confirm-btn="保存"
      cancel-btn="取消"
      :on-confirm="handleSubmit"
      :confirm-loading="updatePending || addPending"
      :on-close="() => {
        visible = false;
        form?.clearValidate();
      }">
    <template #header>{{ id ? '编辑' : '新增' }}角色</template>
    <template #body>
      <t-form ref="form" style="margin-bottom: 5px" :data="formData" :rules="rules" requiredMark resetType="initial">
        <t-form-item label="角色名" name="name" :initial-data="formData.name ?? ''">
          <t-input v-model="formData.name" placeholder="请输入角色名"/>
        </t-form-item>
        <t-form-item label="描述" name="description" :initial-data="formData.description ?? ''">
          <t-input v-model="formData.description" placeholder="请输入描述"/>
        </t-form-item>
        <t-form-item label="权限" name="permissionIds" :initial-data="formData.permissionIds ?? []">
          <t-transfer
              v-model="formData.permissionIds"
              :data="options"
              showCheckAll
          />
        </t-form-item>
      </t-form>
    </template>
  </t-dialog>
</template>

<style scoped>

</style>
