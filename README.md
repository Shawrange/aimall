# AiMall

AiMall 是一个基于 Spring Boot 的 AI 电商系统，包含用户端、管理端、公共业务模块和 MCP 服务模块。项目集成了商品、订单、购物车、用户、支付、RAG 数据处理、AI 对话和 MCP 服务能力。

## 项目结构

```text
aimall
├── aimall-admin   # 管理端服务
├── aimall-web     # 用户端服务
├── aimall-common  # 公共实体、Mapper、Service、工具类
├── aimall-mcp     # MCP 服务模块
└── pom.xml        # Maven 父工程

技术栈
Java 21
Spring Boot 3.5.x
Spring AI
MyBatis
MySQL
Redis / Redisson
Elasticsearch
Netty WebSocket
Alipay SDK
Maven
环境要求
JDK 21+
Maven 3.6.3+
MySQL 8+
Redis
Elasticsearch
