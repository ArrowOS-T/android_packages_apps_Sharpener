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

package com.arrow.support;

import com.android.internal.logging.nano.MetricsProto;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Surface;
import android.preference.Preference;
import com.android.settings.R;

import com.android.settings.SettingsPreferenceFragment;

public class Sharpener extends SettingsPreferenceFragment {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        addPreferencesFromResource(R.xml.sharpener_menu);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.ARROW;
    }

}
