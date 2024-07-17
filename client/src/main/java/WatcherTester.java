package com.softwareag.is.vault.adapter;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

class ConfigFileWatcher extends Thread {

    private final File configDir;

    private final WatchService watchService;

    private WatchKey watchKey;

    public ConfigFileWatcher(final File configDir) throws Exception {
        this.configDir = configDir;
        watchService = FileSystems.getDefault().newWatchService();
        final Path path = Paths.get(getConfigDir().getPath());
        path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
    }

    @Override
    public void run() {
        long lastRead = Long.MIN_VALUE;
        while (true) {
            try {
                watchKey = watchService.take();
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    final String fileName = event.context().toString();
                    if (fileName.equals("vaults.json")) {
                        long lastWrite = new File(configDir, fileName).lastModified();
                        if (lastWrite != lastRead) {
                            System.out.println("Changes detected in file: " + fileName + ", processing...");
                            lastRead = lastWrite;

                        }
                    }
                }
                if (!watchKey.reset()) {
                    break;
                }
            } catch (final InterruptedException e) {
                // ignore
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public File getConfigDir() {
        return configDir;
    }

    public void cancelService() {
        if (watchKey != null) {
            watchKey.cancel();
        }
        if (watchService != null) {
            try {
                watchService.close();
            } catch (final Exception e) {
                // ignore
            }
        }
    }

}

public class WatcherTester {

    public static void main(String[] args) throws Exception {
        ConfigFileWatcher watcher = new ConfigFileWatcher(new File("/temp"));
        watcher.start();
    }

}