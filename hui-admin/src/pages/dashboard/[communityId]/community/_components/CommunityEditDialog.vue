<script setup lang="ts">
import { reactive, ref } from "vue";
import type { FormInstanceFunctions, FormProps } from "tdesign-vue-next";
import type { components } from "@/types/client";
import { useMutation, useQueryClient } from "@tanstack/vue-query";
import { client } from "@/request";

const visible = ref(false);
const id = ref<string>();
const form = ref<FormInstanceFunctions | null>(null);
const formData = reactive<components["schemas"]["UpdateCommunityRequest"]>({});
const handleOpen = (editId: string | undefined, current: components["schemas"]["UpdateCommunityRequest"] | undefined) => {
  visible.value = true;
  if (editId) {
    id.value = editId;
  } else {
    id.value = undefined;
  }
  void form.value?.reset();
  if (current) {
    formData.code = current.code;
    formData.name = current.name;
    formData.address = current.address;
    formData.areaId = current.areaId;
    formData.longitude = current.longitude;
    formData.latitude = current.latitude;
  }
  void form.value?.clearValidate();
};
defineExpose({ open: handleOpen });
const queryClient = useQueryClient();
const { mutate: updateMutate, isPending: updatePending } = useMutation({
  mutationFn: async (data: {
    id: string;
    body: components["schemas"]["UpdateCommunityRequest"];
  }) => client.PUT("/sys-api/community/{communityId}", {
    params: {
      path: {
        communityId: data.id
      }
    },
    body: data.body
  }),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: ["community"] });
    visible.value = false;
  }
});

const { mutate: addMutate, isPending: addPending } = useMutation({
  mutationFn: async (data: components["schemas"]["AddCommunityRequest"]) => client.POST("/sys-api/community", {
    body: data
  }),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: ["community"] });
    visible.value = false;
  }
});

const handleSubmit = async () => {
  const validate = await form.value?.validate();
  if (!validate) {
    return;
  }
  if (id.value) {
    updateMutate({ id: id.value, body: formData });
  } else {
    addMutate(formData);
  }
};
const rules: FormProps["rules"] = {
  code: [
    { required: true, message: "小区代码不必填", type: "error", trigger: "blur" },
    { required: true, message: "小区代码不必填", type: "error", trigger: "change" },
    { whitespace: true, message: "小区代码不能为空" }
  ],
  name: [
    { required: true, message: "名称必填", type: "error", trigger: "blur" },
    { required: true, message: "名称必填", type: "error", trigger: "change" },
    { whitespace: true, message: "名称不能为空" }
  ],
  address: [
    { required: true, message: "地址必填", type: "error", trigger: "blur" },
    { required: true, message: "地址必填", type: "error", trigger: "change" },
    { whitespace: true, message: "地址不能为空" }
  ],
  areaId: [
    { required: true, message: "区域 ID 必填", type: "error", trigger: "blur" },
    { required: true, message: "区域 ID 必填", type: "error", trigger: "change" },
    { whitespace: true, message: "区域 ID 不能为空" }
  ],
  longitude: [
    { required: true, message: "经度必填", type: "error", trigger: "blur" },
    { required: true, message: "经度必填", type: "error", trigger: "change" },
    { whitespace: true, message: "经度不能为空" }
  ],
  latitude: [
    { required: true, message: "纬度必填", type: "error", trigger: "blur" },
    { required: true, message: "纬度必填", type: "error", trigger: "change" },
    { whitespace: true, message: "纬度不能为空" }
  ]
};
</script>

<template>
  <t-dialog
      :visible="visible" :close-btn="true"
      :closeOnEscKeydown="false"
      confirm-btn="保存"
      cancel-btn="取消"
      :on-confirm="handleSubmit"
      :confirm-loading="updatePending || addPending"
      :on-close="() => {
        visible = false;
        form?.clearValidate();
      }">
    <template #header>{{ id ? '编辑' : '新增' }}社区</template>
    <template #body>
      <t-form ref="form" style="margin-bottom: 5px" :data="formData" :rules="rules" requiredMark resetType="initial">
        <t-form-item label="城市代码" name="code" :initial-data="formData.code ?? ''">
          <t-input v-model="formData.code" placeholder="请输入城市代码"/>
        </t-form-item>
        <t-form-item label="小区名称" name="name" :initial-data="formData.name ?? ''">
          <t-input v-model="formData.name" placeholder="请输入小区名称"/>
        </t-form-item>
        <t-form-item label="小区地址" name="address" :initial-data="formData.address ?? ''">
          <t-input v-model="formData.address" placeholder="请输入小区地址"/>
        </t-form-item>
        <t-form-item label="区域" name="areaId" :initial-data="formData.areaId ?? ''">
          <t-input v-model="formData.areaId" placeholder="请输入区域 ID"/>
        </t-form-item>
        <t-form-item label="经度" name="longitude" :initial-data="formData.longitude ?? 0">
          <t-input v-model="formData.longitude" type="number" placeholder="请输入经度"/>
        </t-form-item>
        <t-form-item label="纬度" name="latitude" :initial-data="formData.latitude ?? 0">
          <t-input v-model="formData.latitude" type="number" placeholder="请输入纬度"/>
        </t-form-item>
      </t-form>
    </template>
  </t-dialog>
</template>

<style scoped>

</style>
