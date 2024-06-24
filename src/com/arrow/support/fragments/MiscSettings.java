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
import com.android.settings.Utils;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import com.android.internal.util.arrow.SystemRestartUtils;

public class MiscSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {
    
    private static final String SYS_GMS_SPOOF = "persist.sys.pixelprops.gms";
    private static final String SYS_GOOGLE_SPOOF = "persist.sys.pixelprops.google";
    private static final String SYS_PROP_OPTIONS = "persist.sys.pixelprops.all";
    private static final String SYS_NETFLIX_SPOOF = "persist.sys.pixelprops.netflix";
    private static final String SYS_GPHOTOS_SPOOF = "persist.sys.pixelprops.gphotos";
    
    private boolean isPixelDevice;

    private Preference mGmsSpoof;
    private Preference mGoogleSpoof;
    private Preference mGphotosSpoof;
    private Preference mNetflixSpoof;
    private Preference mPropOptions;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.sharpener_misc);
        final PreferenceScreen prefScreen = getPreferenceScreen();

        ContentResolver resolver = getActivity().getContentResolver();

        mNetflixSpoof = (Preference) findPreference(SYS_NETFLIX_SPOOF);
        mGphotosSpoof = (Preference) findPreference(SYS_GPHOTOS_SPOOF);
        mGmsSpoof = (Preference) findPreference(SYS_GMS_SPOOF);
        mGoogleSpoof = (Preference) findPreference(SYS_GOOGLE_SPOOF);
        mPropOptions = (Preference) findPreference(SYS_PROP_OPTIONS);
        isPixelDevice = SystemProperties.get("ro.soc.manufacturer").equals("Google");
        if (!isPixelDevice) {
            mPropOptions.setEnabled(false);
            mPropOptions.setSummary(R.string.spoof_option_disabled);
        } else {
            mGmsSpoof.setDependency(SYS_PROP_OPTIONS);
            mGphotosSpoof.setDependency(SYS_PROP_OPTIONS);
            mNetflixSpoof.setDependency(SYS_PROP_OPTIONS);
            mGoogleSpoof.setEnabled(false);
            mGoogleSpoof.setSummary(R.string.google_spoof_option_disabled);
        }
        mGmsSpoof.setOnPreferenceChangeListener(this);
        mPropOptions.setOnPreferenceChangeListener(this);
        mGoogleSpoof.setOnPreferenceChangeListener(this);
        mGphotosSpoof.setOnPreferenceChangeListener(this);
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
        if (preference == mGmsSpoof || preference == mPropOptions || preference == mGoogleSpoof || preference == mGphotosSpoof) {
            SystemRestartUtils.showSystemRestartDialog(getContext());
            return true;
        }
        return false;
    }
}