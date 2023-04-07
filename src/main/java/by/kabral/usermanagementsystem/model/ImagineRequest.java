package by.kabral.usermanagementsystem.model;

import org.springframework.stereotype.Component;

@Component
public class ImagineRequest {

    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
