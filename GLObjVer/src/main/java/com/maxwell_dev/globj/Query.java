package com.maxwell_dev.globj;

import static org.lwjgl.opengl.GL46.*;
import org.lwjgl.opengl.GL46;

public class Query implements glInterface {
    private final int id;
    private int target;

    /**
     * create a query object
     *
     * @param target the query target
     */
    public Query() {
        id = glGenQueries();
    }

    /**
     * get the query id
     * @return the id
     */
    public int id() {
        return id;
    }

    /**
     * set the query target
     * @param target the query target. One of:
     * <p>{@link GL46#GL_SAMPLES_PASSED}, {@link GL46#GL_ANY_SAMPLES_PASSED}, {@link GL46#GL_ANY_SAMPLES_PASSED_CONSERVATIVE},
     * {@link GL46#GL_PRIMITIVES_GENERATED}, {@link GL46#GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN},
     * {@link GL46#GL_TIME_ELAPSED}, {@link GL46#GL_TIMESTAMP}, {@link GL46#GL_VERTICES_SUBMITTED},
     * {@link GL46#GL_PRIMITIVES_SUBMITTED}, {@link GL46#GL_VERTEX_SHADER_INVOCATIONS},
     * {@link GL46#GL_TESS_CONTROL_SHADER_PATCHES}, {@link GL46#GL_TESS_EVALUATION_SHADER_INVOCATIONS},
     * {@link GL46#GL_GEOMETRY_SHADER_INVOCATIONS}, {@link GL46#GL_GEOMETRY_SHADER_PRIMITIVES_EMITTED},
     * {@link GL46#GL_FRAGMENT_SHADER_INVOCATIONS}, {@link GL46#GL_COMPUTE_SHADER_INVOCATIONS},
     * {@link GL46#GL_CLIPPING_INPUT_PRIMITIVES}, {@link GL46#GL_CLIPPING_OUTPUT_PRIMITIVES}</p>
     */
    public void setTarget(int target) {
        this.target = target;
    }
    
    /**
     * begin the query
     */
    public void begin() {
        glBeginQuery(target, id);
    }

    /**
     * end the query
     */
    public void end() {
        glEndQuery(target);
    }

    /**
     * get the query result
     * @return the query result
     */
    public int result() {
        return glGetQueryObjecti(id, GL_QUERY_RESULT);
    }

    /**
     * begin the indexed query
     * @param index the index
     */
    public void beginIndexed(int index) {
        glBeginQueryIndexed(target, index, id);
    }

    /**
     * end the indexed query
     * @param index the index
     */
    public void endIndexed(int index) {
        glEndQueryIndexed(target, index);
    }

    /**
     * query the counter
     * @param counter the counter. One of:
     * <p>{@link GL46#GL_TIMESTAMP} {@link GL46#GL_TIME_ELAPSED}
     */
    public void counter(int counter) {
        glQueryCounter(id, counter);
    }

    /**
     * get the counter result
     * @return the counter result
     */
    public long counterResult() {
        return glGetQueryObjectui64(id, GL_QUERY_RESULT);
    }

    /**
     * get the counter result available
     * @return the counter result available
     */
    public long counterResultAvailable() {
        return glGetQueryObjectui64(id, GL_QUERY_RESULT_AVAILABLE);
    }

    /**
     * delete the query object
     */
    public void delete() {
        glDeleteQueries(id);
    }
}
