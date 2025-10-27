# nutriXpert-backend

API desenvolvida com **Spring Boot** para gerenciamento de informações nutricionais.  
Documentação Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🚀 Pré-requisitos

- [Java 17+](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Recomendado)

---

## 📥 Clonando o repositório

```bash
git clone https://github.com/C0demain/nutriXpert-backend.git
cd nutriXpert-backend
```

---


## ⚙️ Configurando variáveis de ambiente

O projeto utiliza um arquivo `.env` para armazenar configurações sensíveis (como credenciais de banco de dados e chave JWT).

1. Copie o arquivo de exemplo:

```bash
cp .env.example .env

2. Edite o arquivo `.env` com os valores corretos (exemplo):

```dotenv
JWT_SECRET=my_secret_key
DB_CONNECTION_STRING=jdbc:postgresql://localhost:5432/postgres
DB_USER=postgres
DB_PASSWORD=postgres
```


---

## 🛠️ Executando no IntelliJ IDEA

No IntelliJ, você pode usar uma configuração pronta para carregar automaticamente o `.env`:

> Basta abrir o projeto, selecionar a configuração **NutriXpert** no canto superior direito e clicar em **Run**.

---

## ▶️ Executando via linha de comando

Se preferir, você pode declarar as variáveis diretamente no terminal antes de rodar:

```bash
export JWT_SECRET=my_secret_key
export DB_CONNECTION_STRING=jdbc:postgresql://localhost:5432/postgres
export DB_USER=postgres
export DB_PASSWORD=postgres

./mvnw spring-boot:run
```

> Windows (PowerShell):

```powershell
$env:JWT_SECRET="my_secret_key"
$env:DB_CONNECTION_STRING="jdbc:postgresql://localhost:5432/postgres"
$env:DB_USER="postgres"
$env:DB_PASSWORD="postgres"
./mvnw spring-boot:run
```
---

## 🛠️ Configurando o IntelliJ IDEA

1. Abra o projeto no IntelliJ (`File > Open` → selecione a pasta `nutriXpert-backend`).  
2. Aguarde o IntelliJ baixar as dependências Maven.
3. Selecionar a configuração **NutriXpert** no canto superior direito e clicar em **Run**
---

## ▶️ Executando o projeto

No IntelliJ:  
- Escolha a configuração criada no canto superior direito e clique em **Run** .  

Via terminal (usando o wrapper Maven incluso):

```bash
./mvnw spring-boot:run
```

Se o Maven já estiver instalado globalmente:

```bash
mvn spring-boot:run
```

A API ficará disponível em:  
- [http://localhost:8080](http://localhost:8080)  
- Swagger: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
