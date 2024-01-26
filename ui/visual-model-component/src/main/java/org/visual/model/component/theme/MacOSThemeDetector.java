/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.visual.model.component.theme;

import com.sun.jna.Callback;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.visual.model.jfa.foundation.Foundation;
import org.visual.model.jfa.foundation.ID;
import org.visual.model.jfa.foundation.NSAutoreleasePool;

/**
 * Determines the dark/light theme on a MacOS System through the <i>Apple Foundation framework</i>.
 *
 * @author Daniel Gyorffy
 */
@Slf4j
class MacOSThemeDetector extends OsThemeDetector {


    private final Set<Consumer<Boolean>> listeners = new CopyOnWriteArraySet<>();
    private final Pattern themeNamePattern = Pattern.compile(".*dark.*", Pattern.CASE_INSENSITIVE);
    private final ExecutorService callbackExecutor = Executors.newSingleThreadExecutor(DetectorThread::new);

    private final Callback themeChangedCallback = new Callback() {
        @SuppressWarnings("unused")
        public void callback() {
            callbackExecutor.execute(() -> notifyListeners(isDark()));
        }
    };

    MacOSThemeDetector() {
        initObserver();
    }

    private void initObserver() {
        final NSAutoreleasePool pool = new NSAutoreleasePool();
        try {
            final ID delegateClass = Foundation.allocateObjcClassPair(Foundation.getObjcClass("NSObject"), "NSColorChangesObserver");
            if (!ID.NIL.equals(delegateClass)) {
                if (!Foundation.addMethod(delegateClass, Foundation.createSelector("handleAppleThemeChanged:"), themeChangedCallback, "v@")) {
                    log.error("Observer method cannot be added");
                }
                Foundation.registerObjcClassPair(delegateClass);
            }

            final ID delegate = Foundation.invoke("NSColorChangesObserver", "new");
            Foundation.invoke(
                    Foundation.invoke("NSDistributedNotificationCenter", "defaultCenter"),
                    "addObserver:selector:name:object:",
                    delegate,
                    Foundation.createSelector("handleAppleThemeChanged:"),
                    Foundation.nsString("AppleInterfaceThemeChangedNotification"),
                    ID.NIL);
        } finally {
            pool.drain();
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public boolean isDark() {
        val pool = new NSAutoreleasePool();
        try {
            final ID userDefaults = Foundation.invoke("NSUserDefaults", "standardUserDefaults");
            final String appleInterfaceStyle = Foundation.toStringViaUTF8(Foundation.invoke(userDefaults, "objectForKey:", Foundation.nsString("AppleInterfaceStyle")));
            return isDarkTheme(appleInterfaceStyle);
        } catch (RuntimeException e) {
            log.error("Couldn't execute theme name query with the Os", e);
        } finally {
            pool.drain();
        }
        return false;
    }

    private boolean isDarkTheme(String themeName) {
        return themeName != null && themeNamePattern.matcher(themeName).matches();
    }

    @Override
    public void registerListener(@NotNull Consumer<Boolean> darkThemeListener) {
        listeners.add(darkThemeListener);
    }

    @Override
    public void removeListener(@Nullable Consumer<Boolean> darkThemeListener) {
        listeners.remove(darkThemeListener);
    }

    private void notifyListeners(boolean isDark) {
        listeners.forEach(listener -> listener.accept(isDark));
    }

    private static final class DetectorThread extends Thread {
        DetectorThread(@NotNull Runnable runnable) {
            super(runnable);
            setName("MacOS Theme Detector Thread");
            setDaemon(true);
        }
    }
}
