package com.elastic.pipeline;

import java.util.StringTokenizer;

import com.elastic.exceptions.UnknownTaskException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.AbstractTask;
import com.elastic.tasks.TaskBuilder;

public class Pipeline {

    private final static String PIPELINE_TOKENIZER_DELIMITATOR = ";";

    private ContinuousDeliveryProperties properties;

    private Pipeline nextPipeline;

    private AbstractTask task;

    private Pipeline() {

    }

    public Pipeline(ContinuousDeliveryProperties properties) throws UnknownTaskException {
	this.properties = properties;

	init();
    }

    private void init() throws UnknownTaskException {
	String pipelines = this.properties.getPipeline();
	StringTokenizer tokenizer = new StringTokenizer(pipelines, PIPELINE_TOKENIZER_DELIMITATOR);

	while (tokenizer.hasMoreTokens()) {
	    AbstractTask task = TaskBuilder.createTask(tokenizer.nextToken(), properties);

	    this.addTask(task);
	}
    }

    public void addTask(AbstractTask task) {
	if (this.nextPipeline == null) {
	    this.task = task;
	    this.nextPipeline = new Pipeline();
	} else {
	    this.nextPipeline.addTask(task);
	}
    }

    public void run() {
	if (this.task != null) {
	    this.task.execute();
	}

	if (this.nextPipeline != null) {
	    this.nextPipeline.run();
	}
    }
}