package util;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum I18n {

    ROOT("root"),
    ERROR("error"),
    TABLE("table"),
    DIALOG("dialog");

        private final ResourceBundle resourceBundle;
        private static final String DEFAULT_LOCATION = "i18n.";

        I18n(String bundleFile) {
            resourceBundle = ResourceBundle.getBundle(DEFAULT_LOCATION + bundleFile);
        }

        public String getString(String key) {
            try {
                return resourceBundle.getString(key);
            } catch (MissingResourceException ex) {
                return "err#";
            }
        }

        public ResourceBundle getResourceBundle(){
            return resourceBundle;
        }

    }
