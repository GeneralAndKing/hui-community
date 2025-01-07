<script setup lang="ts">
import { DesktopIcon, LockOnIcon } from "tdesign-icons-vue-next";
import { type FormProps, MessagePlugin } from "tdesign-vue-next";
import { reactive } from "vue";
import useAuthStore from "@/stores/auth";
const rules: FormProps["rules"] = {
  username: [
    {
      required: true,
      message: "用户名必填",
      type: "error",
      trigger: "blur"
    },
    {
      required: true,
      message: "用户名必填",
      type: "error",
      trigger: "change"
    },
    {
      whitespace: true,
      message: "用户名必填"
    }
  ],
  password: [
    {
      required: true,
      message: "密码必填",
      type: "error"
    }
  ]
};
const formData: FormProps["data"] = reactive({
  username: "",
  password: ""
});
const authStore = useAuthStore();

const onSubmit: FormProps["onSubmit"] = ({ validateResult, firstError }) => {
  if (validateResult === false) {
    console.log("Validate Errors: ", firstError, validateResult);
    void MessagePlugin.warning(`${firstError}`);
    return;
  }
  authStore.signIn({
    username: formData.username,
    password: formData.password
  });
};
</script>

<template>
  <div class="w-screen h-screen flex flex-col items-center justify-center max-h-[1048px]">
    <t-card class="w-120" shadow>
      <div class="my-8 flex flex-col gap-y-4">
        <div class="text-4xl font-bold">登录</div>
        <div class="text-md">使用您的惠小区账号</div>
        <t-form ref="form" :rules="rules" :data="formData" :colon="true" :label-width="0" @submit="onSubmit">
          <t-form-item name="username">
            <t-input v-model="formData.username" clearable placeholder="请输入账户名">
              <template #prefix-icon>
                <desktop-icon />
              </template>
            </t-input>
          </t-form-item>

          <t-form-item name="password">
            <t-input v-model="formData.password" type="password" clearable placeholder="请输入密码">
              <template #prefix-icon>
                <lock-on-icon />
              </template>
            </t-input>
          </t-form-item>

          <t-form-item>
            <t-button theme="primary" type="submit" :loading="authStore.isLoading" block>登录</t-button>
          </t-form-item>
        </t-form>
      </div>
    </t-card>
  </div>
</template>

<style scoped>

</style>
