<script setup lang="ts">
import { reactive, ref } from "vue";
import type { NewOrEditUser } from "@/types";
import { type FormInstanceFunctions, type FormProps } from "tdesign-vue-next";
import { useMutation, useQueryClient } from "@tanstack/vue-query";
import { client } from "@/request";
import type { components } from "@/types/client";

const form = ref<FormInstanceFunctions | null>(null);
const formData: FormProps["data"] = reactive({
  displayName: "",
  username: "",
  password: "",
  phone: "",
  email: ""
});

const id = ref<string>();

const visible = ref(false);
const queryClient = useQueryClient();
const { mutate: updateMutate, isPending: updatePending } = useMutation({
  mutationFn: async (data: {
    id: string;
    body: components["schemas"]["UpdateSysUserRequest"]
  }) => client.PUT("/sys-api/sys-user/{sysUserId}", {
    params: {
      path: {
        sysUserId: data.id
      }
    },
    body: {
      displayName: data.body.displayName,
      username: data.body.username,
      phone: data.body.phone,
      email: data.body.email
    }
  }),
  onSuccess: () => {
    queryClient.invalidateQueries({queryKey: ["communityUsers"]});
    visible.value = false;
  }
});

const handleSubmit = async () => {
  const validate = await form.value?.validate();
  if (validate) {
    updateMutate({ id: id.value!, body: formData });
  }
};

const handleOpen = (current: NewOrEditUser) => {
  visible.value = true;
  if (current.id) {
    id.value = current.id;
    formData.displayName = current.displayName;
    formData.username = current.username;
    formData.phone = current.phone;
    formData.email = current.email;
  }
};

defineExpose({open: handleOpen});

const rules: FormProps["rules"] = {
  displayName: [
    {required: true, message: "名称必填", type: "error", trigger: "blur"},
    {required: true, message: "名称必填", type: "error", trigger: "change"},
    {whitespace: true, message: "名称不能为空"}
  ],
  username: [
    {required: true, message: "用户名必填", type: "error", trigger: "blur"},
    {required: true, message: "用户名必填", type: "error", trigger: "change"},
    {whitespace: true, message: "用户名不能为空"}
  ],
  email: [
    {
      required: true,
      message: "邮箱不能为空",
      type: "error"
    },
    {
      email: true,
      message: "邮箱格式不合法",
      type: "error"
    }
  ],
  phone: [
    {
      required: true,
      message: "手机号不能为空",
      type: "error"
    },
    {
      telnumber: true,
      message: "手机号格式不合法",
      type: "error"
    }
  ],
  password: [
    {
      required: true,
      message: "密码不能为空",
      type: "error",
      trigger: "change"
    },
    {
      min: 6,
      message: "密码最少为 6 位",
      type: "error",
      trigger: "change"
    }
  ]
};
</script>

<template>
  <t-dialog :visible="visible" :close-btn="true"
            confirm-btn="保存"
            cancel-btn="取消"
            :on-confirm="handleSubmit"
            :confirm-loading="updatePending"
            :on-close="() => {
              visible = false;
              form?.clearValidate();
            }">
    <template #header>编辑用户</template>
    <template #body>
      <t-form ref="form" style="margin-bottom: 5px" :data="formData" :rules="rules" requiredMark resetType="initial">
        <t-form-item label="名称" name="displayName" :initial-data="formData.displayName ?? ''">
          <t-input v-model="formData.displayName" placeholder="请输入名称"/>
        </t-form-item>
        <t-form-item label="用户名" name="username" :initial-data="formData.username ?? ''">
          <t-input v-model="formData.username" placeholder="请输入用户名"/>
        </t-form-item>
        <t-form-item label="手机号码" name="phone" :initial-data="formData.phone ?? ''">
          <t-input v-model="formData.phone" placeholder="请输入 11 位手机号" type="tel"/>
        </t-form-item>
        <t-form-item label="电子邮箱" name="email" :initial-data="formData.email ?? ''">
          <t-input v-model="formData.email" placeholder="请输入邮箱号"/>
        </t-form-item>
        <t-form-item v-if="!id" label="密码" name="password" :initial-data="formData.password ?? ''">
          <t-input v-model="formData.password" placeholder="请输入密码" type="password"/>
        </t-form-item>
      </t-form>
    </template>
  </t-dialog>
</template>

<style scoped>

</style>
