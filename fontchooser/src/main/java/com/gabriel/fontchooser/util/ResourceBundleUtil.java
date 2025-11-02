<<<<<<< HEAD
package com.gabriel.fontchooser.util;

import java.util.ResourceBundle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceBundleUtil {
    private final ResourceBundle resourceBundle;

    public char getFirstChar(String key) {
        String bundleString = resourceBundle.getString(key);
        return bundleString.charAt(0);
    }
}
=======
package com.gabriel.fontchooser.util;

import java.util.ResourceBundle;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceBundleUtil {

    private final ResourceBundle resourceBundle;

    public char getFirstChar(String key) {
        String bundleString = resourceBundle.getString(key);
        return bundleString.charAt(0);
    }

}
>>>>>>> 157268aeee6107c107861b0fb636b27bf93cba4b
