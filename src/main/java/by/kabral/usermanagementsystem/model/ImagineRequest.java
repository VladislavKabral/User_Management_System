package by.kabral.usermanagementsystem.model;

import org.springframework.stereotype.Component;

/**
 * Class which represents imagine delay for request to database
 *
 * @author Vladislav Kabral
 */
@Component
public class ImagineRequest {

    /**
     * Method which creates delay
     *
     * @param ms count of milliseconds for creation delay
     */
    public void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
