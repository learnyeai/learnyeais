package com.learnyeai.batch.videoffmpeg.core;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;

import com.learnyeai.batch.taskpool.Task;
import com.learnyeai.batch.taskpool.TaskSpeed;
import com.learnyeai.batch.videoffmpeg.data.ResFile;
import com.learnyeai.batch.videoffmpeg.data.ResFileRepository;

import cn.jovany.ffmpeg.Ffmpeg;
import cn.jovany.ffmpeg.FfmpegBuilder;

public class FileTaskExecutor extends Task {

	private final File file;

	private final ResFileRepository repository;

	private final String ffmpegPath;

	public FileTaskExecutor(String ffmpegPath, ResFileRepository repository, File file) {
		super();
		this.file = file;
		this.repository = repository;
		this.ffmpegPath = ffmpegPath;
	}

	@Override
	public void run(TaskSpeed taskSpeed, Closeable closeable) {
		try {

			String filename = file.getName();

			ResFile resFile = repository.findTopByFileName(filename);
			if (resFile == null) {
				throw new FileNotFoundException(file.getName());
			}

			int count = 0;

			if (resFile.getFileSize() == null) {
				resFile.setFileSize(String.valueOf(file.length()));
				count++;
			}

			if (resFile.getFileTimeLen() == null) {
				FfmpegBuilder ffmpegBuilder = new Ffmpeg(new File(ffmpegPath)).build();
				int durationBySecond = ffmpegBuilder.append("-i", file::getAbsolutePath)
						.execute(re -> re.regex("Duration: (.*?), start: (.*), bitrate: (.*)", 1).first()
								.get(re2 -> re2.regex("(\\d\\d?):(\\d\\d?):(\\d\\d?)\\.(\\d\\d?)", 4).apply(res2 -> {
									BigDecimal hour = new BigDecimal(res2.group(0));
									BigDecimal minute = new BigDecimal(res2.group(1));
									BigDecimal second = new BigDecimal(res2.group(2));
									BigDecimal v60 = new BigDecimal(60);
									BigDecimal duration = second.add(minute.multiply(v60))
											.add(hour.multiply(v60).multiply(v60));
									return duration.intValue();
								})));
				if (durationBySecond > 0) {
					resFile.setFileTimeLen(String.valueOf(durationBySecond));
					count++;
				}
			}

			if (count <= 0) {
				throw new FileNotFoundException(file.getName());
			}

			if (!file.renameTo(new File(file.getParentFile(), MessageFormat.format("{0}{1}", resFile.getResFileId(),
					filename.substring(filename.lastIndexOf(".")))))) {
				throw new IOException();
			}

			System.out.println(MessageFormat.format("已完成：{0} ==> {1}", filename, file.getAbsolutePath()));
			repository.save(resFile);

			taskSpeed.progress(1);

		} catch (Exception e) {
			error(e);
		} finally {
			try {
				closeable.close();
			} catch (IOException ex) {
				error(ex);
			}
		}
	}

	@Override
	public void close() throws IOException {
	}

	public File getFile() {
		return file;
	}

}
