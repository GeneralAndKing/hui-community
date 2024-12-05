import globals from "globals";
import tseslint from "typescript-eslint";
import pluginVue from "eslint-plugin-vue";


/** @type {import('eslint').Linter.Config[]} */
export default [
  {files: ["**/*.{js,mjs,cjs,ts,vue}"]},
  {languageOptions: {globals: globals.browser}},
  ...tseslint.configs.recommended,
  ...pluginVue.configs["flat/essential"],
  {
    files: ["**/*.{js,mjs,cjs,ts,vue}"], languageOptions: {
      parserOptions: {
        parser: tseslint.parser, ecmaFeatures: {
          jsx: true
        }
      }
    },
    rules: {
      "vue/multi-word-component-names": "off",
      indent: ["error", 2], // 缩进使用 2 个空格
      quotes: ["error", "double"], // 使用单引号
      semi: ["error", "always"], // 语句末尾不加分号
      "no-console": process.env.NODE_ENV === "production" ? "warn" : "off", // 生产环境中警告 console 使用，开发环境中关闭规则
      "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off", // 生产环境中警告 debugger 使用，开发环境中关闭规则
    }
  }
];
