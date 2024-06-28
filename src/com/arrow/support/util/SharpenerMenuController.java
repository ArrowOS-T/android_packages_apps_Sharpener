/*
 * Copyright (C) 2024 ArrowOS-T
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arrow.support.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

import java.util.HashMap;
import java.util.Map;

public class SharpenerMenuController extends AbstractPreferenceController {

    private static final String KEY_MENU_TOP = "sharpener_menu_top";
    private static final String KEY_MENU_BOTTOM = "sharpener_menu_bottom";

    public SharpenerMenuController(Context context) {
        super(context);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);

        LayoutPreference sharpenerMenuTop = (LayoutPreference) screen.findPreference(KEY_MENU_TOP);
        if (sharpenerMenuTop != null) {
            Map<Integer, Intent> clickMap = new HashMap<>();
            clickMap.put(R.id.sharpener_quicksettings, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SharpenerQuickSettingsActivity")));
            clickMap.put(R.id.sharpener_statusbar, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SharpenerStatusBarActivity")));
            clickMap.put(R.id.sharpener_about, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SharpenerAboutActivity")));

            for (Map.Entry<Integer, Intent> entry : clickMap.entrySet()) {
                View view = sharpenerMenuTop.findViewById(entry.getKey());
                if (view != null) {
                    view.setOnClickListener(v -> mContext.startActivity(entry.getValue()));
                }
            }    
        }

        LayoutPreference sharpenerMenuBottom = (LayoutPreference) screen.findPreference(KEY_MENU_BOTTOM);
        if (sharpenerMenuBottom != null) {
            Map<Integer, Intent> clickMap = new HashMap<>();
            clickMap.put(R.id.sharpener_notifications, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SharpenerNotificationActivity")));
            clickMap.put(R.id.sharpener_lockscreen, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SharpenerLockScreenActivity")));
            clickMap.put(R.id.sharpener_gestures, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SettingsGestureActivity")));
            clickMap.put(R.id.sharpener_themes, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SharpenerThemesActivity")));
            clickMap.put(R.id.sharpener_buttons, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SharpenerButtonsActivity")));
            clickMap.put(R.id.sharpener_misc, new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$SharpenerMiscActivity")));

            for (Map.Entry<Integer, Intent> entry : clickMap.entrySet()) {
                View view = sharpenerMenuBottom.findViewById(entry.getKey());
                if (view != null) {
                    view.setOnClickListener(v -> mContext.startActivity(entry.getValue()));
                }
            }    
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_MENU_TOP;
    }
}