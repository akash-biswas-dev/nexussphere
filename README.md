# 🚀 Nexusphere

<div align="center">

### 🤖 A Multi-Agent AI Data Analytics Platform

_Automating data analysis through intelligent AI agent orchestration._

</div>

Nexusphere is a prototype platform that aims to automate the work traditionally performed by data analysts through a team of specialised AI agents. Instead of relying on a single large agent, Nexusphere orchestrates multiple AI agents that collaborate to understand data, clean datasets, build machine learning models, generate business insights, and create interactive dashboards.

The long-term vision is to evolve Nexusphere into a collaborative, multi-user analytics workspace—similar to the collaborative experience of Figma—where teams can analyse data together with AI-powered assistants.

---

# 🌟 Vision

Modern data analytics often requires expertise in data engineering, statistics, machine learning, and visualisation. Nexusphere brings these capabilities together through AI agent orchestration, allowing users to focus on asking questions while autonomous agents handle the technical workflow.

---

# ✨ Features

- 🤖 Multi-agent AI orchestration
- 📂 Load data from multiple data sources
- 🧹 Intelligent data cleaning and preprocessing
- 📊 Automated exploratory data analysis
- 🧠 Machine learning model generation
- 📈 AI-generated business insights
- 📉 Interactive dashboard generation
- 🔐 Secure API access through a dedicated Gateway
- ⚡ High-performance execution engine written in Rust
- 🚀 One-command local development environment

---

# 🏗️ Architecture

```text
                          ┌─────────────────────┐
                          │     Next.js UI      │
                          └──────────┬──────────┘
                                     │
                                     ▼
                         ┌────────────────────────┐
                         │      API Gateway       │
                         │ Authentication         │
                         │ Authorization          │
                         │ Request Routing        │
                         └──────────┬─────────────┘
                                    │
          ┌─────────────────────────┼────────────────────────┐
          │                         │                        │
          ▼                         ▼                        ▼
 ┌───────────────────┐    ┌──────────────────┐    ┌─────────────────┐
 │ FastAPI Agents    │    │ Spring Boot API  │    │ Future Services │
 │                   │    │                  │    │                 │
 │ Planner           │    │ Users            │    │                 │
 │ Analytics Agent   │    │ Workspaces       │    │                 │
 │ ML Agent          │    │ Pages            │    │                 │
 │ Dashboard Agent   │    │ Projects         │    │                 │
 └─────────┬─────────┘    └──────────────────┘
           │
           │ gRPC
           ▼
 ┌────────────────────────────────────────────┐
 │        Rust Execution Engine               │
 │--------------------------------------------│
 │ Dataset Loading                            │
 │ Data Cleaning                              │
 │ Statistical Analysis                       │
 │ Machine Learning                           │
 │ Visualisation                              │
 │ Dashboard Generation                       │
 └────────────────────────────────────────────┘
```

---

# 🛠️ Technology Stack

| Layer                | Technology              |
| -------------------- | ----------------------- |
| 🎨 Frontend          | Next.js                 |
| 🤖 Agent Runtime     | FastAPI                 |
| 🏢 Business Services | Spring Boot             |
| ⚡ Execution Engine  | Rust                    |
| 🔗 Communication     | gRPC + Protocol Buffers |
| 🔒 API Security      | Gateway                 |
| 📦 Containerisation  | Docker & Docker Compose |

---

# 🧩 Components

## 🎨 Next.js Client

The frontend provides the user interface for interacting with AI agents, managing workspaces, viewing dashboards, and exploring generated insights.

**Responsibilities**

- 🖥️ User interface
- 📊 Dashboard rendering
- 🗂️ Workspace management
- 💬 Chat interface
- 📈 Data visualisation

---

## 🔒 API Gateway

The gateway acts as the single entry point into the platform.

**Responsibilities**

- 🔑 Authentication
- 🛡️ Authorization
- 🔀 Request routing
- 🚦 Rate limiting
- 🔐 Security policies
- 🌐 Service discovery _(future)_

---

## 🤖 FastAPI Agent Service

The FastAPI service hosts the AI agents exposed to users.

**Responsibilities**

- 🧠 Planner Agent
- 🛠️ Tool selection
- 🤝 Multi-agent orchestration
- 🔄 Workflow execution
- 📝 Context management
- 💭 Conversation memory
- ⚡ Calling the Rust execution engine via gRPC

---

## ☕ Spring Boot Service

The Spring Boot application manages the platform itself rather than analytics execution.

**Responsibilities**

- 👤 Users
- 🔑 Authentication
- 🗂️ Workspaces
- 📄 Pages
- 📁 Projects
- 👥 Collaboration _(future)_
- 💾 Persistence layer
- 🌐 Business APIs

---

## 🦀 Rust Execution Engine

The execution engine performs all deterministic data operations.

**Responsibilities**

- 📂 Load datasets
- 🧹 Clean datasets
- 🏗️ Feature engineering
- 📊 Statistical analysis
- 🧠 Machine learning
- 📉 Dashboard generation
- ⚡ High-performance computation

> **The AI agents decide _what_ should happen.**
> **The Rust execution engine decides _how_ it happens.**

---

# 🔄 Communication

```text
User
   │
   ▼
Next.js
   │
   ▼
Gateway
   │
   ▼
FastAPI Agent
   │
 gRPC
   │
   ▼
Rust Execution Engine
```

The AI agents never manipulate datasets directly. Instead, they orchestrate workflows by invoking strongly typed gRPC services exposed by the execution engine.

---

# 🚀 Local Development

## 📋 Prerequisites

- Docker
- Docker Compose

## ▶️ Start the platform

```bash
docker compose up -d
```

Once the containers are running, every service will start automatically.

---

# 🎯 Project Goals

The current version is a prototype focused on validating the architecture and AI workflow.

Future releases aim to introduce:

- 👥 Multi-user collaboration
- 🗂️ Shared workspaces
- ⚡ Real-time collaborative editing
- 🔌 Plugin ecosystem
- 🌍 External data source integrations
- 🖥️ Distributed agent execution
- 🛡️ Enterprise authentication
- ☁️ Cloud deployment
- 🧠 Model fine-tuning
- 📊 Advanced analytics workflows

---

# 📁 Repository Structure

```text
nexusphere/
│
├── client/                      # Next.js application
│
├── services/
│   ├── gateway/                 # API Gateway
│   ├── agents/                  # FastAPI Agent Service
│   ├── backend/                 # Spring Boot Services
│   └── execution-engine/        # Rust Execution Engine
│
├── proto/                       # Protocol Buffers
├── docker/                      # Docker resources
├── docker-compose.yml
└── README.md
```

---

# 📐 Design Principles

- 🤖 AI agents orchestrate workflows instead of performing computation.
- ⚡ Heavy computation belongs in the Rust execution engine.
- ☕ Business logic remains isolated in Spring Boot services.
- 🎨 The frontend is responsible only for presentation.
- 📦 Every component is independently deployable.
- 🔗 Communication between services uses strongly typed gRPC APIs.
- 🔒 Security is enforced centrally through the API Gateway.

---

# 🗺️ Roadmap

### ✅ Completed

- [x] 🤖 Multi-agent orchestration
- [x] ⚡ Rust execution engine
- [x] 🔗 gRPC communication
- [x] ☕ Spring Boot workspace service
- [x] 🎨 Next.js client
- [x] 🐳 Docker Compose development environment

### 🚧 Planned

- [ ] 👥 Multi-user collaboration
- [ ] ⚡ Real-time collaboration
- [ ] 🛒 Agent marketplace
- [ ] 🔌 Plugin architecture
- [ ] 🌍 External data source connectors
- [ ] ☁️ Cloud-native deployment
- [ ] ☸️ Kubernetes support
- [ ] 🖥️ Distributed execution engine
- [ ] 🔐 Enterprise authentication
- [ ] 📈 Production observability

---

# 📄 License

🚧 **Nexusphere is currently under active development as a prototype.**

Licensing information will be added before the first public release.
