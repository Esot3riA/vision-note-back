package org.swm.vnb;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class VnbApplication {

    public static void main(String[] args) {
        SpringApplication.run(VnbApplication.class, args);
    }

}
