<script setup lang="ts">
import { reactive, ref } from "vue";
import type { NewOrEditUser } from "@/types";
import { type CustomValidator, type FormInstanceFunctions, type FormProps } from "tdesign-vue-next";
import { useMutation, useQueryClient } from "@tanstack/vue-query";
import { client } from "@/request";
import type { components } from "@/types/client";
import { useRoute, useRouter } from "vue-router";

const form = ref<FormInstanceFunctions | null>(null);
const formData: FormProps["data"] = reactive({
  displayName: "",
  username: "",
  password: "",
  confirmPassword: "",
  phone: "",
  email: ""
});
const route = useRoute("/dashboard/[communityId]/user/");
const communityId = route.params.communityId;

const id = ref<string>();

const visible = ref(false);
const queryClient = useQueryClient();
const { mutate: updateMutate, isPending: updatePending } = useMutation({
  mutationFn: async (data: {
    id: string;
    body: components["schemas"]["UpdateSysUserRequest"];
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
    queryClient.invalidateQueries({ queryKey: ["communityUsers"] });
    visible.value = false;
  }
});

const { mutate: addMutate, isPending: addPending } = useMutation({
  mutationFn: async (data: {
    communityId: string;
    body: components["schemas"]["AddSysUserRequest"];
  }) => client.POST("/sys-api/community/{communityId}/sys-user", {
    params: {
      path: {
        communityId: data.communityId
      }
    },
    body: {
      displayName: data.body.displayName,
      username: data.body.username,
      phone: data.body.phone,
      email: data.body.email,
      password: data.body.password
    }
  }),
  onSuccess: () => {
    queryClient.invalidateQueries({ queryKey: ["communityUsers"] });
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
    addMutate({ communityId: communityId, body: formData });
  }
};

const handleOpen = (current: NewOrEditUser | undefined) => {
  visible.value = true;
  if (current && current.id) {
    id.value = current.id;
    formData.displayName = current.displayName;
    formData.username = current.username;
    formData.phone = current.phone;
    formData.email = current.email;
  } else {
    id.value = undefined;
    formData.displayName = "";
    formData.username = "";
    formData.phone = "";
    formData.email = "";
    formData.password = "";
    formData.confirmPassword = "";
  }
  void form.value?.clearValidate();
};

defineExpose({ open: handleOpen });

const rePassword: CustomValidator = val =>
  new Promise((resolve) => {
    const timer = setTimeout(() => {
      resolve(formData.password === val);
      clearTimeout(timer);
    });
  });
const rules: FormProps["rules"] = {
  displayName: [
    { required: true, message: "名称必填", type: "error", trigger: "blur" },
    { required: true, message: "名称必填", type: "error", trigger: "change" },
    { whitespace: true, message: "名称不能为空" }
  ],
  username: [
    { required: true, message: "用户名必填", type: "error", trigger: "blur" },
    { required: true, message: "用户名必填", type: "error", trigger: "change" },
    { whitespace: true, message: "用户名不能为空" }
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
      type: "error"
    },
    {
      min: 6,
      message: "密码最少为 6 位",
      type: "error"
    }
  ],
  confirmPassword: [
    {
      required: true,
      message: "密码必填",
      type: "error"
    },
    {
      validator: rePassword,
      message: "两次密码不一致"
    }
  ]
};
</script>

<template>
  <t-dialog :visible="visible" :close-btn="true"
            :closeOnEscKeydown="false"
            confirm-btn="保存"
            cancel-btn="取消"
            :on-confirm="handleSubmit"
            :confirm-loading="updatePending || addPending"
            :on-close="() => {
              visible = false;
              form?.clearValidate();
            }">
    <template #header>{{id ? '编辑' : '新增'}}用户</template>
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
        <t-form-item v-if="!id" label="确认密码" name="confirmPassword" :initial-data="formData.confirmPassword ?? ''">
          <t-input v-model="formData.confirmPassword" placeholder="请再次输入密码" type="password"/>
        </t-form-item>
      </t-form>
    </template>
  </t-dialog>
</template>

<style scoped>

</style>
