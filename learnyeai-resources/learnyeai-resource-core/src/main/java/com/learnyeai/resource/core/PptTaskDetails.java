package com.learnyeai.resource.core;

import com.learnyeai.resource.core.task.DownloadableTaskDetails;
import com.learnyeai.resource.core.task.PlayableTaskDetails;
import com.learnyeai.resource.core.task.PreviewTaskDetails;

public interface PptTaskDetails
		extends PlayableTaskDetails<PptDetails>, PreviewTaskDetails<PptDetails>, DownloadableTaskDetails<PptDetails> {

}
