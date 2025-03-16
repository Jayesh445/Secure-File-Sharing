package com.secure.FileShareApp.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    private final String cloudName = Dotenv.load().get("CLOUDINARY_CLOUD_NAME");
    private final String apiKey = Dotenv.load().get("CLOUDINARY_API_KEY");
    private final String apiSecret = Dotenv.load().get("CLOUDINARY_API_SECRET");

    @Bean
    public Cloudinary cloudinary() {

    return new Cloudinary(ObjectUtils.asMap(
       "cloud_name",cloudName,
            "api_key",apiKey,
            "api_secret",apiSecret,
            "secure",true
    ));
    }
}
