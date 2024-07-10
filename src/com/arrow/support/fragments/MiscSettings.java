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

package com.arrow.support.fragments;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import android.os.SystemProperties;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.res.Resources;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;

import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.development.SystemPropPoker;
import com.android.settings.Utils;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class MiscSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {
    
    private static final String KEY_GAMES_SPOOF = "use_games_spoof";
    private static final String KEY_PHOTOS_SPOOF = "use_photos_spoof";
    private static final String KEY_NETFLIX_SPOOF = "use_netflix_spoof";

    private static final String SYS_GAMES_SPOOF = "persist.sys.pixelprops.games";
    private static final String SYS_PHOTOS_SPOOF = "persist.sys.pixelprops.gphotos";
    private static final String SYS_NETFLIX_SPOOF = "persist.sys.spoof_netflix";

    private SwitchPreference mGamesSpoof;
    private SwitchPreference mPhotosSpoof;
    private SwitchPreference mNetFlixSpoof;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.sharpener_misc);
        PreferenceScreen prefScreen = getPreferenceScreen();

        ContentResolver resolver = getActivity().getContentResolver();

        mGamesSpoof = (SwitchPreference) prefScreen.findPreference(KEY_GAMES_SPOOF);
        mGamesSpoof.setChecked(SystemProperties.getBoolean(SYS_GAMES_SPOOF, false));
        mGamesSpoof.setOnPreferenceChangeListener(this);

        mPhotosSpoof = (SwitchPreference) prefScreen.findPreference(KEY_PHOTOS_SPOOF);
        mPhotosSpoof.setChecked(SystemProperties.getBoolean(SYS_PHOTOS_SPOOF, true));
        mPhotosSpoof.setOnPreferenceChangeListener(this);

        mNetFlixSpoof = (SwitchPreference) findPreference(KEY_NETFLIX_SPOOF);
        mNetFlixSpoof.setChecked(SystemProperties.getBoolean(SYS_NETFLIX_SPOOF, false));
        mNetFlixSpoof.setOnPreferenceChangeListener(this);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ARROW;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        final String key = preference.getKey();
        if (preference == mGamesSpoof) {
            boolean value = (Boolean) objValue;
            SystemProperties.set(SYS_GAMES_SPOOF, value ? "true" : "false");
            SystemPropPoker.getInstance().poke();
            return true;
        } else if (preference == mPhotosSpoof) {
            boolean value = (Boolean) objValue;
            SystemProperties.set(SYS_PHOTOS_SPOOF, value ? "true" : "false");
            SystemPropPoker.getInstance().poke();
            return true;
        } else if (preference == mNetFlixSpoof) {
            boolean value = (Boolean) objValue;
            SystemProperties.set(SYS_NETFLIX_SPOOF, value ? "true" : "false");
            SystemPropPoker.getInstance().poke();
            return true;
        }
        return true;
    }
}