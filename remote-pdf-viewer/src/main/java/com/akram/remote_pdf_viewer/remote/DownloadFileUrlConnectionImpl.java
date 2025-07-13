/*
 * Copyright (C) 2016 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.akram.remote_pdf_viewer.remote;

import android.content.Context;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadFileUrlConnectionImpl implements DownloadFile {
    private static final int KILOBYTE = 1024;

    private static final int BUFFER_LEN = 1 * KILOBYTE;
    private static final int NOTIFY_PERIOD = 150 * KILOBYTE;

    Context context;
    Handler uiThread;
    Listener listener = new NullListener();
    OkHttpClient okHttpClient;

    public DownloadFileUrlConnectionImpl(Context context, Handler uiThread, Listener listener, OkHttpClient okHttpClient) {
        this.context = context;
        this.uiThread = uiThread;
        this.listener = listener;
        this.okHttpClient = okHttpClient;
    }

    @Override
    public void download(final String url, final String destinationPath) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                FileOutputStream fileOutput = null;
                Response response = null;

                try {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    response = okHttpClient.newCall(request).execute();

                    if (!response.isSuccessful() || response.body() == null) {
                        throw new IOException("Failed to download file: " + response);
                    }

                    long totalSize = response.body().contentLength();
                    long downloadedSize = 0;
                    int counter = 0;

                    inputStream = response.body().byteStream();
                    fileOutput = new FileOutputStream(destinationPath);

                    byte[] buffer = new byte[BUFFER_LEN];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutput.write(buffer, 0, bytesRead);
                        downloadedSize += bytesRead;
                        counter += bytesRead;

                        if (counter >= NOTIFY_PERIOD) {
                            notifyProgressOnUiThread((int) downloadedSize, (int) totalSize);
                            counter = 0;
                        }
                    }

                    fileOutput.flush();
                    notifySuccessOnUiThread(url, destinationPath);
                } catch (Exception e) {
                    notifyFailureOnUiThread(e);
                } finally {
                    try {
                        if (inputStream != null) inputStream.close();
                        if (fileOutput != null) fileOutput.close();
                        if (response != null) response.close();
                    } catch (IOException e) {
                        // Ignored
                    }
                }
            }
        }).start();
    }

    protected void notifySuccessOnUiThread(final String url, final String destinationPath) {
        if (uiThread == null) {
            return;
        }

        uiThread.post(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(url, destinationPath);
            }
        });
    }

    protected void notifyFailureOnUiThread(final Exception e) {
        if (uiThread == null) {
            return;
        }

        uiThread.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(e);
            }
        });
    }

    private void notifyProgressOnUiThread(final int downloadedSize, final int totalSize) {
        if (uiThread == null) {
            return;
        }

        uiThread.post(new Runnable() {
            @Override
            public void run() {
                listener.onProgressUpdate(downloadedSize, totalSize);
            }
        });
    }

    protected class NullListener implements Listener {
        public void onSuccess(String url, String destinationPath) {
            /* Empty */
        }

        public void onFailure(Exception e) {
            /* Empty */
        }

        public void onProgressUpdate(int progress, int total) {
            /* Empty */
        }
    }
}
