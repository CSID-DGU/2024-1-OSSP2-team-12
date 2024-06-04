package com.oos12.scansavvy.config;

import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;

public class Storage {
    @Bean
    public Storage storage() {
        return (Storage) StorageOptions.getDefaultInstance().getService();
    }
}
