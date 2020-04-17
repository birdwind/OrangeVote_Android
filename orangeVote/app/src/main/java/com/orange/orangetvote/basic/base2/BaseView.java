package com.orange.orangetvote.basic.base2;

public interface BaseView {
    /**
     * 顯示dialog
     */
    void showLoading();

    /**
     * 顯示下載文件dialog
     */

    void showLoadingFileDialog();

    /**
     * 隱藏下載文件dialog
     */

    void hideLoadingFileDialog();

    /**
     * 下載進度
     *
     * @param totalSize
     * @param downSize
     */

    void onProgress(long totalSize, long downSize);

    /**
     * 隱藏 dialog
     */

    void hideLoading();

    /**
     * 顯示錯誤訊息
     *
     * @param msg
     */
    void showError(String msg);

    /**
     * 錯誤碼
     */
    void onErrorCode(int code, String msg);


}
