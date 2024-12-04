# 第一阶段：使用 Node.js 进行构建
FROM node:22-alpine AS builder

# 安装 pnpm
RUN corepack enable && corepack prepare pnpm@latest --activate

# 设置工作目录
WORKDIR /app

# 复制 package.json 和 pnpm-lock.yaml (如果存在)
COPY package.json pnpm-lock.yaml* ./

# 安装依赖
RUN pnpm install

# 复制源代码
COPY . .

# 构建项目
RUN pnpm build

# 第二阶段：使用最新版本的 Caddy 作为生产环境服务器
FROM caddy:2.8.4-alpine

# 复制 Caddy 配置文件
COPY Caddyfile /etc/caddy/Caddyfile

# 从构建阶段复制构建产物
COPY --from=builder /app/dist /usr/share/caddy

# 暴露端口
EXPOSE 3000
