package com.minoon.disco;

import android.util.Log;

/**
 * Created by a13587 on 15/08/23.
 */
public class Logger {
    private static final String TAG = Logger.createTag(Logger.class.getSimpleName());

    // ログ出力レベル
    private static final int VERBOSE = Log.VERBOSE;
    private static final int DEBUG = Log.DEBUG;
    private static final int INFO = Log.INFO;
    private static final int WARN = Log.WARN;
    private static final int ERROR = Log.ERROR;
    private static final int STACKTRACE_LAYER = 5;

    private static final String TAG_PREFIX = "disco_";
    private static final int TAG_PREFIX_LENGTH = TAG_PREFIX.length();
    private static final int TAG_MAX_LENGTH = 23;

    private static boolean sLoggable = false;

    private Logger() {
    }

    /**
     * create a tag with prefix.
     *
     * @param str
     * @return
     */
    public static String createTag(String str) {
        if(str.length() > TAG_MAX_LENGTH - TAG_PREFIX_LENGTH) {
            return TAG_PREFIX + str.substring(0, TAG_MAX_LENGTH - TAG_PREFIX_LENGTH - 1);
        }
        return TAG_PREFIX + str;
    }

    public static void setLoggable(boolean loggable) {
        sLoggable = loggable;
    }

    public static boolean isLoggable() {
        return sLoggable;
    }

    private static void log(final int priority, final String tag, final String msg) {
        final StringBuilder sb = makeHeader();
        sb.append(msg);
        Log.println(priority, tag, sb.toString());
    }

    private static void log(final int priority, final String tag, final String msg, final Object... args) {
        final StringBuilder sb = makeHeader();
        sb.append(String.format(msg, args));
        Log.println(priority, tag, sb.toString());
    }

    private static void log(final int priority, final String tag, final String msg, final Throwable th) {
        final StringBuilder sb = makeHeader();
        sb.append(msg)
                .append('\n')
                .append(Log.getStackTraceString(th));
        Log.println(priority, tag, sb.toString());
    }

    private static void log(final int priority, final String tag, final String msg, final Throwable th, Object... args) {
        final StringBuilder sb = makeHeader();
        sb.append(String.format(msg, args))
                .append('\n')
                .append(Log.getStackTraceString(th));
        Log.println(priority, tag, sb.toString());
    }

    public static void v(final String tag, final String msg) {
        if (!isLoggable()) {
            return;
        }
        log(VERBOSE, tag, msg);
    }

    public static void v(final String tag, final String msg, final Object... args) {
        if (!isLoggable()) {
            return;
        }
        log(VERBOSE, tag, msg, args);
    }

    public static void v(final String tag, final String msg, final Throwable th) {
        if (!isLoggable()) {
            return;
        }
        log(VERBOSE, tag, msg, th);
    }

    public static void d(final String tag, final String msg) {
        if (!isLoggable()) {
            return;
        }
        log(DEBUG, tag, msg);
    }

    public static void d(final String tag, final String msg, final Object... args) {
        if (!isLoggable()) {
            return;
        }
        log(DEBUG, tag, msg, args);
    }

    public static void d(final String tag, final String msg, final Throwable th) {
        if (!isLoggable()) {
            return;
        }
        log(DEBUG, tag, msg, th);
    }

    public static void i(final String tag, final String msg) {
        if (!isLoggable()) {
            return;
        }
        log(INFO, tag, msg);
    }

    public static void i(final String tag, final String msg, final Object... args) {
        if (!isLoggable()) {
            return;
        }
        log(INFO, tag, msg, args);
    }

    public static void i(final String tag, final String msg, final Throwable th) {
        if (!isLoggable()) {
            return;
        }
        log(INFO, tag, msg, th);
    }

    public static void w(final String tag, final String msg) {
        if (!isLoggable()) {
            return;
        }
        log(WARN, tag, msg);
    }

    public static void w(final String tag, final String msg, final Object... args) {
        if (!isLoggable()) {
            return;
        }
        log(WARN, tag, msg, args);
    }

    public static void w(final String tag, final String msg, final Throwable th) {
        if (!isLoggable()) {
            return;
        }
        log(WARN, tag, msg, th);
    }

    public static void e(final String tag, final String msg) {
        if (!isLoggable()) {
            return;
        }
        log(ERROR, tag, msg);
    }

    public static void e(final String tag, final String msg, final Object... args) {
        if (!isLoggable()) {
            return;
        }
        log(ERROR, tag, msg, args);
    }

    public static void e(final String tag, final String msg, final Throwable th) {
        if (!isLoggable()) {
            return;
        }
        log(ERROR, tag, msg, th);
    }

    public static void e(final String tag, final String msg, final Throwable th, final Object... args) {
        if (!isLoggable()) {
            return;
        }
        log(ERROR, tag, msg, th, args);
    }

    private static StringBuilder makeHeader() {
        final StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        final StackTraceElement ste = stes[STACKTRACE_LAYER];
        StringBuilder sb = new StringBuilder();
        sb.append('[')
                .append(ste.getFileName().replaceAll(".java", ""))
                .append('.')
                .append(ste.getMethodName())
                .append(':')
                .append(ste.getLineNumber())
                .append(']');
        return sb;
    }
}
