package com.elastic.pipeline;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.elastic.exceptions.TaskExecutionException;
import com.elastic.exceptions.UnknownTaskException;
import com.elastic.properties.ContinuousDeliveryProperties;
import com.elastic.tasks.AbstractTask;
import com.elastic.tasks.TaskBuilder;

public class Pipeline {

    private final static String PIPELINE_TOKENIZER_DELIMITATOR = ";";

    private static Logger logger = LoggerFactory.getLogger(Pipeline.class);

    private ContinuousDeliveryProperties properties;

    private Pipeline previousPipeline;

    private Pipeline nextPipeline;

    private AbstractTask task;

    private Pipeline(Pipeline previousPipeline) {
	this.previousPipeline = previousPipeline;
    }

    public Pipeline(ContinuousDeliveryProperties properties) throws UnknownTaskException {
	this.properties = properties;

	this.init();
    }

    private void init() throws UnknownTaskException {
	String pipelines = this.properties.getPipeline();
	StringTokenizer tokenizer = new StringTokenizer(pipelines, Pipeline.PIPELINE_TOKENIZER_DELIMITATOR);

	while (tokenizer.hasMoreTokens()) {
	    AbstractTask task = TaskBuilder.createTask(tokenizer.nextToken(), this.properties);

	    this.addTask(task);
	}
    }

    public void addTask(AbstractTask task) {
	if (this.nextPipeline == null) {
	    this.task = task;
	    this.nextPipeline = new Pipeline(this);
	} else {
	    this.nextPipeline.addTask(task);
	}
    }

    public void run() {
	this.executeCurrentTask();

	this.runNextPipeline();
    }

    private void runNextPipeline() {
	if (this.nextPipeline != null) {
	    this.nextPipeline.run();
	}
    }

    private void executeCurrentTask() {
	if (this.task != null) {
	    try {
		this.task.execute();
	    } catch (TaskExecutionException e) {
		Pipeline.logger.error(e.getMessage(), e);

		this.rollback();
	    }
	}
    }

    private void rollback() {
	// TODO Auto-generated method stub

    }
}