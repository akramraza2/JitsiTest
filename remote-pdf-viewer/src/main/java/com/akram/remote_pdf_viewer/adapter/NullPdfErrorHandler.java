package com.akram.remote_pdf_viewer.adapter;

class NullPdfErrorHandler implements PdfErrorHandler {
    @Override
    public void onPdfError(Throwable t) {
        /* Empty */
    }
}