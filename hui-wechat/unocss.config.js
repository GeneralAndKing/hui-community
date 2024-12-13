import {defineConfig} from "unocss";
import presetWeapp from 'unocss-preset-weapp'

const include = [/\.wxml$/]

export default defineConfig({
  content: {
    pipeline: {
      include
    }
  },
  presets: [presetWeapp(),],
  rules: [['card-data', {"box-shadow": "var(--td-tab-bar-round-shadow, var(--td-shadow-3, 0 6px 30px 5px rgba(0, 0, 0, 0.05), 0 16px 24px 2px rgba(0, 0, 0, 0.04), 0 8px 10px -5px rgba(0, 0, 0, 0.08)))"}]],
  separators: '__'
})
