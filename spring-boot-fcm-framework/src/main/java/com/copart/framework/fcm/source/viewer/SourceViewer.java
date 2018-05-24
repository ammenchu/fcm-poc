package com.copart.framework.fcm.source.viewer;

public interface SourceViewer<Source> {

	void setSource(Source source);

	Source viewSource();
}
