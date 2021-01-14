package com.zh.android.todolist.server;

import android.content.Context;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;

import java.util.concurrent.TimeUnit;

/**
 * @author wally
 * @date 2021/01/04
 * AndServer
 */
public class ServerManager {
    private static ServerManager instance;
    private final Server mServer;

    public static void init(Context context) {
        if (instance == null) {
            synchronized (ServerManager.class) {
                if (instance == null) {
                    instance = new ServerManager(context.getApplicationContext());
                }
            }
        }
    }

    public static ServerManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("必须先调用init()，进行初始化");
        }
        return instance;
    }

    /**
     * Create server.
     */
    private ServerManager(Context context) {
        mServer = AndServer.webServer(context)
                .port(8080)
                .timeout(10, TimeUnit.SECONDS)
                .listener(new Server.ServerListener() {
                    @Override
                    public void onStarted() {
                        LogUtils.d("反向代理服务器启动成功");
                    }

                    @Override
                    public void onStopped() {
                        LogUtils.d("反向代理服务器启动失败");
                    }

                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                        LogUtils.d("反向代理服务器启动异常");
                    }
                })
                .build();
    }

    /**
     * Start server.
     */
    public void startServer() {
        if (mServer.isRunning()) {
            return;
        }
        mServer.startup();
    }

    /**
     * Stop server.
     */
    public void stopServer() {
        if (mServer.isRunning()) {
            mServer.shutdown();
        } else {
            LogUtils.d("AndServer", "The server has not started yet.");
        }
    }
}