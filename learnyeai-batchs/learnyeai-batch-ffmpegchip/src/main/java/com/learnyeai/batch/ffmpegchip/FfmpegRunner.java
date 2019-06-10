package com.learnyeai.batch.ffmpegchip;

import java.io.File;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import cn.jovany.command.CommandApi;
import cn.jovany.command.CommandResultSet;

@Configuration
public class FfmpegRunner implements ApplicationRunner {

	@Autowired
	CommandApi ffmpeg;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		String result = ffmpeg.append("-ss", "00:00:00"::toString)
				.append("-i",
						new File("/Users/wangqi/ffmpegDir/47402cfd-82b4-11e9-86c3-005056b13ddf.mp4")::getAbsolutePath)
				.append("-c", "copy"::toString).append("-t", new Long(60l)::toString)
				.append(new File("/Users/wangqi/ffmpegDir/output.mp4").getAbsolutePath()).apply(t -> {
					System.out.println(t.toCommand());
					return t;
				}).execute(CommandResultSet::toString);
		System.out.println(result);
	}

	public void test(ApplicationArguments args) throws Exception {
		int durationBySecond = ffmpeg
				.append("-i",
						new File("/Users/wangqi/ffmpegDir/47402cfd-82b4-11e9-86c3-005056b13ddf.mp4")::getAbsolutePath)
				.execute(t -> t.regex("Duration: (.*?), start: (.*?), bitrate: (.*?)", 1).indexOf(0)
						.get(t1 -> t1.regex("(\\d\\d?):(\\d\\d?):(\\d\\d?)\\.(\\d\\d?)", 4).apply(re1 -> {
							BigDecimal hour = new BigDecimal(re1.group(0));
							BigDecimal minute = new BigDecimal(re1.group(1));
							BigDecimal second = new BigDecimal(re1.group(2));
							BigDecimal millisecond = new BigDecimal(re1.group(3));
							BigDecimal v60 = new BigDecimal(60);
							BigDecimal v100 = new BigDecimal(100);
							BigDecimal v1000 = new BigDecimal(1000);
							BigDecimal duration = millisecond.multiply(v100)
									.add(second.add(minute.multiply(v60)).multiply(v1000))
									.add(hour.multiply(v60).multiply(v60).multiply(v1000));
							return duration.intValue();
						})));
		System.out.println(durationBySecond);

	}

}
