package com.learnyeai.batch.videoffmpeg.core;

import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.util.Arrays;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.learnyeai.batch.taskpool.Task;
import com.learnyeai.batch.taskpool.TaskExecutor;
import com.learnyeai.batch.taskpool.TaskSpeed;
import com.learnyeai.batch.videoffmpeg.data.ResFileRepository;

public class DirectoryTaskExecutor extends Task {

	public static final class SuffixFilter implements FilenameFilter {
		private final Set<String> suffixSupport;

		public SuffixFilter(String... suffixSupport) {
			super();
			this.suffixSupport = Arrays.asList(suffixSupport).stream().collect(Collectors.toSet());
		}

		@Override
		public boolean accept(File dir, String name) {
			if (suffixSupport.isEmpty())
				return true;
			return suffixSupport.stream().map(String::toLowerCase).filter(suffix -> name.toLowerCase().endsWith(suffix))
					.count() > 0;
		}

		public boolean containsSuffix(String suffix) {
			return suffixSupport.contains(suffix);
		}

		public Object[] getSuffixArray() {
			return suffixSupport.toArray();
		}

		public boolean addSuffix(String e) {
			return suffixSupport.add(e);
		}

		public boolean containsAllSuffix(Collection<?> c) {
			return suffixSupport.containsAll(c);
		}

		public boolean addAllSuffix(Collection<String> c) {
			return suffixSupport.addAll(c);
		}

		public boolean removeSuffix(String suffix) {
			return suffixSupport.remove(suffix);
		}

		public boolean removeAllSuffix(Collection<String> c) {
			return suffixSupport.removeAll(c);
		}

		public boolean removeIfSuffix(Predicate<String> filter) {
			return suffixSupport.removeIf(filter);
		}

	}

	private File parent;
	private SuffixFilter suffixFilter;
	private FileFilter fileFilter;
	private Queue<File> queue;
	private File file;

	private final ResFileRepository repository;
	private final String ffmpegPath;

	public DirectoryTaskExecutor(String ffmpegPath, ResFileRepository repository, File parent, String... uffix) {
		super();
		this.ffmpegPath = ffmpegPath;
		this.parent = parent;
		this.suffixFilter = new SuffixFilter(uffix);
		this.repository = repository;
	}

	public File getParent() {
		return parent;
	}

	@Override
	public void close() {
	}

	@Override
	public void run(TaskSpeed taskSpeed, Closeable closeable) {
		try {
			if (!parent.isDirectory()) {
				parent = parent.getParentFile();
			}
			if (!parent.exists()) {
				try {
					throw new DirectoryNotEmptyException(parent.getPath());
				} catch (DirectoryNotEmptyException e) {
					error(e);
				}
			}
			queue = new ConcurrentLinkedQueue<>(Arrays.asList(parent.listFiles(file -> {
				boolean suffixSupport = suffixFilter.accept(file.getParentFile(), file.getName());
				if (fileFilter == null) {
					return file.isFile() && suffixSupport;
				}
				return file.isFile() && suffixSupport && fileFilter.accept(file);
			})).stream().collect(Collectors.toSet()));
			if (queue.isEmpty()) {
				throw new EmptyStackException();
			}
			while (!queue.isEmpty()) {
				if (queue.peek() == file) {
					try {
						Thread.sleep(15l);
						continue;
					} catch (InterruptedException e) {
						error(e);
					}
				}
				TaskExecutor.step(this, taskSpeed, closeable,
						new FileTaskExecutor(ffmpegPath, repository, file = queue.peek())).close(() -> {
							queue.poll();
						}).execute();
			}
			try {
				closeable.close();
			} catch (IOException e) {
				error(e);
			}
		} catch (Exception e) {
			error(e);
		}
	}

	public boolean containsSuffix(String suffix) {
		return suffixFilter.containsSuffix(suffix);
	}

	public Object[] getSuffixArray() {
		return suffixFilter.getSuffixArray();
	}

	public boolean addSuffix(String suffix) {
		return suffixFilter.addSuffix(suffix);
	}

	public boolean containsAllSuffix(Collection<String> suffixs) {
		return suffixFilter.containsAllSuffix(suffixs);
	}

	public boolean addAllSuffix(Collection<String> suffixs) {
		return suffixFilter.addAllSuffix(suffixs);
	}

	public boolean removeSuffix(String suffix) {
		return suffixFilter.removeSuffix(suffix);
	}

	public boolean removeAllSuffix(Collection<String> suffix) {
		return suffixFilter.removeAllSuffix(suffix);
	}

	public boolean removeIfSuffix(Predicate<String> filter) {
		return suffixFilter.removeIfSuffix(filter);
	}

	public FileFilter getFileFilter() {
		return fileFilter;
	}

	public void setFileFilter(FileFilter fileFilter) {
		this.fileFilter = fileFilter;
	}

}
