/*
 * Copyright (C) 2021 The Android Open Source Project
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

package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.NonNull;

/**
 * Draws udfps fingerprint if sensor isn't illuminating.
 */
public class UdfpsFpDrawable extends UdfpsDrawable {

    public UdfpsFpDrawable(@NonNull Context context) {
        super(context);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (isDisplayConfigured()) {
            return;
        }

        if (getUdfpsDrawable() != null) {
            getUdfpsDrawable().draw(canvas);
            return;
        }

        mFingerprintDrawable.draw(canvas);
    }

    @Override
    public void onSensorRectUpdated(@NonNull RectF sensorRect) {
        super.onSensorRectUpdated(sensorRect);
        final int margin =  (int) sensorRect.height() / 16;

        final Rect bounds = new Rect((int) sensorRect.left + margin,
                (int) sensorRect.top + margin,
                (int) sensorRect.right - margin,
                (int) sensorRect.bottom - margin);
        updateFingerprintIconBounds(bounds);
    }

    @Override
    protected void updateFingerprintIconBounds(@NonNull Rect bounds) {
        super.updateFingerprintIconBounds(bounds);
        if (getUdfpsDrawable() != null) {
            getUdfpsDrawable().setBounds(bounds);
            invalidateSelf();
        }
    }
}