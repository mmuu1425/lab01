FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY . .

# 调试：显示当前目录结构
RUN echo "=== Current directory structure ==="
RUN ls -la

# 调试：显示 target 目录（如果存在）
RUN echo "=== Target directory ==="
RUN ls -la target/ || echo "Target directory does not exist yet"

# 构建应用
RUN mvn clean package -DskipTests

# 调试：显示构建后的文件
RUN echo "=== After Maven build ==="
RUN ls -la target/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/lab01-1.0-SNAPSHOT.jar"]