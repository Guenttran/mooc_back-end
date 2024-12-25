package org.example.backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "huyproject",
            "api_key", "211273934684491",
            "api_secret", "TD7gfuK_67Mlc9hSl9koMUw9PPM"
        ));
    }
}