FROM arm64v8/alpine
FROM openjdk:17-oracle

RUN apk add --no-cache qemu-arm-static

# Cài đặt các công cụ cần thiết (curl, unzip)
RUN apk update && apk add --no-cache curl unzip

# Cài đặt Java (ví dụ: OpenJDK 17)
RUN apk add --no-cache openjdk17

# Đặt biến môi trường JAVA_HOME và cập nhật biến PATH
ENV JAVA_HOME /usr/lib/jvm/default-jvm
ENV PATH $PATH:$JAVA_HOME/bin

# Kiểm tra phiên bản Java đã cài đặt
RUN java -version

COPY . /app
WORKDIR /app
EXPOSE 8080
RUN chmod +x /app/gradlew
CMD ["sh", "./run.sh"]

#docker run -p 8080:8080 individual:1.0
#docker build -t individual:1.0 .
# test