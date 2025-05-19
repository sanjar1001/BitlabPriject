    package com.example.bitlabproject.config;

    import io.minio.MinioClient;
    import org.hibernate.annotations.ConcreteProxy;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class MinioConfig {

        @Value("http://localhost:9000")
        private String url;

        @Value("rootadmin")
        private String user;

        @Value("minioadmin")
        private String password;

        @Bean
        public MinioClient minioClient() {

            return MinioClient
                    .builder()
                    .endpoint(url)
                    .credentials(user, password)
                    .build();

        }


    }
