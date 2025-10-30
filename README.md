# Pesquisa de Satisfação ☁️

## 📘 Descrição Geral
Este projeto implementa uma aplicação de **pesquisa de satisfação** com o objetivo principal de **praticar contêinerização e deploy na Google Cloud Platform (GCP)**.  

A aplicação é composta por dois serviços principais:
- **Backend:** API desenvolvida em **Spring Boot**, responsável por processar e armazenar respostas em um banco **MySQL no Cloud SQL**  
- **Frontend:** site estático em **HTML, CSS e JavaScript**, servido via **Nginx**, contendo páginas de pesquisa e um **dashboard de resultados**

O foco do projeto é o **processo completo de deploy e integração entre serviços na GCP**, unindo Docker, Cloud Run e Cloud SQL.

---

## 🎯 Objetivo do Projeto
O projeto foi desenvolvido para explorar e consolidar conceitos de:
- **Criação e publicação de imagens Docker** para backend e frontend  
- **Deploy independente** dos serviços no **Google Cloud Run**  
- **Integração entre containers e banco Cloud SQL**  
- **Hospedagem de conteúdo estático via Nginx**  
- **Exportação de dados em planilhas (.xlsx)** no dashboard de resultados  

---

## 🧩 Tecnologias Utilizadas
- **Backend:** Spring Boot (Java)  
- **Banco de Dados:** MySQL (Google Cloud SQL)  
- **Frontend:** HTML, CSS e JavaScript puro  
- **Servidor Web:** Nginx  
- **Exportação de Dados:** XLSX (gerado via JavaScript)  
- **Infraestrutura e Deploy:**  
  - Docker  
  - Google Cloud Artifact Registry  
  - Google Cloud Run  
  - Google Cloud SQL  

---

## ⚙️ Arquitetura do Projeto
A arquitetura é formada por dois contêineres independentes e integrados:

1. **pesquisa_satisfacao/**  
   - Contém a API Spring Boot responsável pelo backend da aplicação  
   - Realiza operações de cadastro e consulta das respostas de pesquisa  
   - Conecta-se ao banco **MySQL hospedado no Cloud SQL**  
   - Empacotada em uma imagem Docker publicada no **Artifact Registry**
   - Implantado como serviço independente no **Cloud Run**

2. **html/**  
   - Contém o **frontend da aplicação**, servido via **Nginx**  
   - Possui:
     - `index.html`: página principal de envio de avaliações  
     - `resultados.html`: página com **dashboard interativo** exibindo estatísticas e canais de divulgação  
     - Função de **exportação dos dados para XLSX** diretamente pelo navegador  
   - Inclui o arquivo `nginx.conf` para configuração do servidor web
   - Empacotada em uma imagem Docker publicada no **Artifact Registry**
   - Implantado como serviço independente no **Cloud Run**

---

## 🧠 Aprendizados e Objetivos Técnicos
- Estruturar e empacotar aplicações frontend e backend em contêineres distintos  
- Configurar e usar o **Nginx** para servir aplicações estáticas  
- Publicar imagens personalizadas no **Artifact Registry**  
- Realizar **deploys independentes** no **Cloud Run**  
- Conectar containers ao **Cloud SQL** de forma segura e escalável  
- Criar exportação dinâmica de dados (XLSX) no frontend  
- Entender o fluxo completo de **deploy em nuvem com Docker e GCP**

---

## 🚀 Possíveis Melhorias Futuras
- Adicionar autenticação e controle de acesso para o dashboard  
- Incluir gráficos mais detalhados e filtros de pesquisa  
- Automatizar o pipeline de build e deploy (GitHub Actions + Cloud Build)  
- Migrar o frontend para um framework moderno (React, Vue ou Angular)  

---

## 📂 Estrutura do Projeto
pesquisa_satisfacao/
├── html/ # Frontend (Nginx + HTML/CSS/JS)
│ ├── img/ # Imagens utilizadas no site
│ ├── index.html # Página principal da pesquisa
│ ├── resultados.html # Dashboard de resultados e exportação XLSX
│ ├── nginx.conf # Configuração do servidor Nginx
│ ├── Dockerfile # Imagem Docker do frontend
│
├── pesquisa_satisfacao/ # API Spring Boot (Java)
│ ├── src/ # Código-fonte da aplicação
│ ├── pom.xml # Dependências Maven
│ ├── Dockerfile # Imagem Docker da API
│
└── README.md

---

## 💡 Observação
Este projeto foi desenvolvido com **foco educacional**, para consolidar conhecimentos em **Docker, GCP (Cloud Run, Cloud SQL, Artifact Registry)** e boas práticas de **deploy em nuvem**.  
A aplicação serve como base para estudos de infraestrutura moderna e integração de serviços cloud.
