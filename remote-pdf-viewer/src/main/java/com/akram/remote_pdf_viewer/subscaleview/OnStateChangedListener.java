package com.akram.remote_pdf_viewer.subscaleview;

import android.graphics.PointF;

@SuppressWarnings("EmptyMethod")
public interface OnStateChangedListener {

    void onScaleChanged(float newScale, int origin);

    void onCenterChanged(PointF newCenter, int origin);
}