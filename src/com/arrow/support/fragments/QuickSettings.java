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

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.Context;
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

import com.android.internal.util.arrow.SystemRestartUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class QuickSettings extends SettingsPreferenceFragment implements OnPreferenceChangeListener {

    private static final String KEY_QS_WIDGETS_ENABLED  = "qs_widgets_enabled";
    private static final String[] qsCustPreferences = { "qs_tile_shape",
            "qqs_num_columns", "qqs_num_columns_landscape",
            "qs_num_columns", "qs_num_columns_landscape" };

    private Preference mQsWidgetsPref;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.sharpener_quicksettings);
        PreferenceScreen prefScreen = getPreferenceScreen();
        mQsWidgetsPref = findPreference(KEY_QS_WIDGETS_ENABLED);
        mQsWidgetsPref.setOnPreferenceChangeListener(this);

        boolean qsStyleRound = Settings.Secure.getIntForUser(getContext().getContentResolver(),
                Settings.Secure.QS_STYLE_ROUND, 1, UserHandle.USER_CURRENT) == 1;
                
        if (!qsStyleRound) {
            for (String key : qsCustPreferences) {
                Preference preference = prefScreen.findPreference(key);
                if (preference != null) {
                    preference.setEnabled(false);
                }
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Context mContext = getActivity().getApplicationContext();
        ContentResolver resolver = mContext.getContentResolver();
        
        if (preference == mQsWidgetsPref) {
            Settings.Secure.putIntForUser(resolver,
                Settings.Secure.QS_SHOW_BRIGHTNESS_SLIDER, 0, UserHandle.USER_CURRENT);
            SystemRestartUtils.showSystemUIRestartDialog(getActivity());
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ARROW;
    }

}