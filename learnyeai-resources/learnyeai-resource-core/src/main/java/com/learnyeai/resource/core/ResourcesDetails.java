package com.learnyeai.resource.core;

import java.util.Iterator;

import com.learnyeai.resource.core.details.OnlineDetails;
import com.learnyeai.resource.core.details.ResourceableDetails;

public interface ResourcesDetails extends Iterator<ResourceableDetails>, OnlineDetails, ResourceableDetails {
	
}
