package org.example.lab_1.fcm;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ConfigurationProperties(prefix = "fcm")
@Component
public class FcmSettings {
    private String serviceAccountFile;
}
