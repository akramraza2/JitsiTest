package com.akram.remote_pdf_viewer.subscaleview;

@SuppressWarnings("EmptyMethod")
public interface OnAnimationEventListener {

    void onComplete();

    void onInterruptedByUser();

    void onInterruptedByNewAnim();
}