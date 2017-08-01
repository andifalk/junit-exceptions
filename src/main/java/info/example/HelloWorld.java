package info.example;

import org.apache.commons.lang3.StringUtils;

public class HelloWorld {

    public String sayHello(String message) {
        if (StringUtils.isNotBlank(message)) {
            if ("world".equalsIgnoreCase(message)) {
                return "Hello " + message;
            } else {
                throw new IllegalArgumentException("Only message 'world' is acceptable");
            }
        } else {
            throw new IllegalArgumentException("Non-null and non-empty message is required");
        }
    }

}
