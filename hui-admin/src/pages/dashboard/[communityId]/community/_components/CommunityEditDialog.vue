<script setup lang="ts">
import { reactive, ref } from "vue";
import type { NewOrEditUser } from "@/types";
import type { FormInstanceFunctions, FormProps } from "tdesign-vue-next";

const visible = ref(false);
const id = ref<string>();
const form = ref<FormInstanceFunctions | null>(null);
const formData: FormProps["data"] = reactive({

});
const handleOpen = (current: NewOrEditUser | undefined) => {
  visible.value = true;
  if (current && current.id) {
    id.value = current.id;
  } else {
    id.value = undefined;
  }
  void form.value?.clearValidate();
};
defineExpose({ open: handleOpen });
const handleSubmit = async () => {
  const validate = await form.value?.validate();
  if (!validate) {
    return;
  }
  if (id.value) {
  } else {
  }
};

</script>

<template>
  <t-dialog
      :visible="visible" :close-btn="true"
      :closeOnEscKeydown="false"
      confirm-btn="保存"
      cancel-btn="取消"
      :on-confirm="handleSubmit"
      :on-close="() => {
        visible = false;
        form?.clearValidate();
      }">
    <template #header>{{ id ? '编辑' : '新增' }}社区</template>
  </t-dialog>
</template>

<style scoped>

</style>
