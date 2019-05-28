package com.learnyeai.resource.core.plan;

import java.util.Iterator;

import com.learnyeai.resource.core.details.TeachableDetails;

public interface TeachingPlanPool<TD extends TeachableDetails> extends Iterator<TeachingPlan<TD>> {

	String getName();

}
