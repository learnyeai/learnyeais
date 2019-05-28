package com.learnyeai.resource.core.task;

import com.learnyeai.resource.core.details.ResourceableDetails;

public interface ResourceableTaskDetails<T extends ResourceableDetails>
		extends OnlineTaskDetails<T>, DownloadableTaskDetails<T>, PreviewTaskDetails<T> {

}
