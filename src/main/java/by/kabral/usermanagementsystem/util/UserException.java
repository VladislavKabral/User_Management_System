package by.kabral.usermanagementsystem.util;

/**
 * Class which represents exception class special for user`s entities
 *
 * @author Vladislav Kabral
 */
public class UserException extends Exception {

    /**
     * Constructor
     *
     * @param message message of exception
     */
    public UserException(String message) {
        super(message);
    }
}
