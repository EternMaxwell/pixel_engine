package com.maxwell_dev.globj;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GL46;

import java.util.LinkedList;

import static org.lwjgl.opengl.GL46.*;

/**
 * this class describes the opengl context
 * <p>it contains the bindings same as the bindings in opengl context but more intuitive</p>
 * <p>learn more about the bindings in opengl context in the <a href="https://www.khronos.org/opengl/wiki/Main_Page">opengl wiki</a></p>
 */
public class Context {
    private static Context contextCurrent;
    public static final Context NULL_CONTEXT = new Context(0);
    private final GLCapabilities capabilities;
    private final VertexArrayBinding vertexArray;
    private final BufferBinding arrayBuffer, copyReadBuffer, copyWriteBuffer, dispatchIndirectBuffer,
            drawIndirectBuffer, elementArrayBuffer, pixelPackBuffer, pixelUnpackBuffer,
            atomicCounterBuffer, transformFeedbackBuffer, uniformBuffer, shaderStorageBuffer,
            queryBuffer, textureBuffer;
    private final LinkedList<BufferBinding> atomicCounterBuffers, transformFeedbackBuffers, uniformBuffers,
            shaderStorageBuffers;
    private final LinkedList<TextureUnit> textureUnits;
    private final FramebufferBinding drawFrameBuffer, readFrameBuffer;
    private final ProgramBinding program;
    private final QueryBinding samplesPassedQuery, anySamplesPassedQuery, anySamplesPassedConservativeQuery,
            primitivesGeneratedQuery, transformFeedbackPrimitivesWrittenQuery, timeElapsedQuery,
            timestampQuery, vertexShaderInvocationsQuery, geometryShaderInvocationsQuery,
            geometryShaderPrimitivesEmittedQuery, fragmentShaderInvocationsQuery,
            computeShaderInvocationsQuery, tessControlShaderPatchesQuery,
            tessEvaluationShaderInvocationsQuery, clippingInputPrimitivesQuery, clippingOutputPrimitivesQuery;
    private final ScissorBinding scissor;
    private final StencilBinding stencil;
    private final DepthBinding depth;
    private final BlendBinding blend;
    private final LogicOpBinding logicOp;
    private final WriteMaskBinding writeMask;
    private final FaceCullBinding faceCull;
    private final ViewPortBinding viewPort;
    private final MemoryBarrierBinding memoryBarrier;
    private final ClearColor clearColor;
    private final ClearBit clearBit;

    /**
     * get the current context
     * @return the current context
     */
    public static Context current() {
        return contextCurrent;
    }

    public class ClearBit {
        private int bit;

        /**
         * set the clear bit
         * @param bit the clear bit. can be one of the following:
         * <p>{@link GL46#GL_COLOR_BUFFER_BIT}, {@link GL46#GL_DEPTH_BUFFER_BIT}, {@link GL46#GL_STENCIL_BUFFER_BIT}</p>
         * 
         */
        public ClearBit set(int bit) {
            this.bit = bit;
            return this;
        }

        /**
         * get the clear bit
         * @return the clear bit
         */
        public int get() {
            return bit;
        }

        /**
         * clear the buffer
         */
        public void clear() {
            glClear(bit);
        }
    }

    public class ClearColor {
        private final float[] color;

        public ClearColor() {
            color = new float[]{0, 0, 0, 0};
        }

        public ClearColor set(float r, float g, float b, float a) {
            color[0] = r;
            color[1] = g;
            color[2] = b;
            color[3] = a;
            return this;
        }

        public float[] get() {
            return color;
        }

        public float r() {
            return color[0];
        }

        public float g() {
            return color[1];
        }

        public float b() {
            return color[2];
        }

        public float a() {
            return color[3];
        }

        public void clear() {
            glClearColor(color[0], color[1], color[2], color[3]);
        }
    }

    public class MemoryBarrierBinding {
        private MemoryBarrier memoryBarrier;

        /**
         * set the memory barrier to this binding point
         * @param memoryBarrier the memory barrier to bind
         */
        public void set(MemoryBarrier memoryBarrier) {
            if (this.memoryBarrier == memoryBarrier)
                return;
            this.memoryBarrier = memoryBarrier;
            setContextCurrent();
            if (memoryBarrier == null) {
                glMemoryBarrier(0);
                return;
            }
            glMemoryBarrier(memoryBarrier.barrier());
        }

        /**
         * get the memory barrier currently bound to this binding point
         * @return the memory barrier
         */
        public MemoryBarrier get() {
            return memoryBarrier;
        }
    }

    /**
     * this class describes a program binding point in the opengl context
     */
    public class ProgramBinding {
        private Program program;

        /**
         * bind the program obj to this binding point in the current context
         * @param program the program obj to bind
         */
        public void bind(Program program) {
            if (this.program == program)
                return;
            this.program = program;
            setContextCurrent();
            if (program == null) {
                glUseProgram(0);
                return;
            }
            glUseProgram(program.id());
        }

        /**
         * get the program obj currently bound to this binding
         * @return the program obj
         */
        public Program get() {
            return program;
        }
    }

    /**
     * this class describes a vertex array binding point in the opengl context
     */
    public class VertexArrayBinding {
        private VertexArray vertexArray;

        /**
         * bind the vertex array obj to this binding point in the current context
         * @param vertexArray the vertex array obj to bind
         */
        public void bind(VertexArray vertexArray) {
            if (this.vertexArray == vertexArray)
                return;
            this.vertexArray = vertexArray;
            setContextCurrent();
            if (vertexArray == null) {
                glBindVertexArray(0);
                return;
            }
            glBindVertexArray(vertexArray.id());
        }

        /**
         * get the vertex array obj currently bound to this binding point
         * @return the vertex array obj
         */
        public VertexArray get() {
            return vertexArray;
        }
    }

    /**
     * this class describes a frame buffer binding point in the opengl context
     */
    public class FramebufferBinding {
        private final int target;
        private Framebuffer framebuffer;

        public FramebufferBinding(int target) {
            this.target = target;
        }

        /**
         * bind the frame buffer to this binding point in the current context
         * @param framebuffer the frame buffer obj to bind
         */
        public void bind(Framebuffer framebuffer) {
            if (this.framebuffer == framebuffer)
                return;
            this.framebuffer = framebuffer;
            setContextCurrent();
            if (framebuffer == null) {
                glBindFramebuffer(target, 0);
                return;
            }
            glBindFramebuffer(target, framebuffer.id());
        }

        /**
         * get the frame buffer currently bound to this binding point
         * @return the frame buffer
         */
        public Framebuffer get() {
            return framebuffer;
        }
    }

    /**
     * this class describes a texture unit binding point for any texture accepted
     * <p>note that one texture unit can only bind a single texture</p>
     */
    public class TextureUnit {
        private final int index;
        private Texture texture;
        private Sampler sampler;

        public TextureUnit(int index) {
            this.index = index;
        }

        /**
         * bind the texture to this texture unit 
         * @param texture the texture to bind 
         */
        public void bindTexture(Texture texture) {
            if(this.texture == texture)
                return;
            this.texture = texture;
            setContextCurrent();
            if (texture == null) {
                glBindTextureUnit(GL_TEXTURE0 + index, 0);
                return;
            }
            glBindTextureUnit(GL_TEXTURE0 + index, texture.id());
        }

        /**
         * bind the sampler to this texture unit
         * @param sampler the sampler to bind
         */
        public void bindSampler(Sampler sampler) {
            if(this.sampler == sampler)
                return;
            this.sampler = sampler;
            setContextCurrent();
            if (sampler == null) {
                glBindSampler(index, 0);
                return;
            }
            glBindSampler(index, sampler.id());
        }

        /**
         * get the texture currently bound to this texture unit 
         * <p>note that the returned type is {@link Texture}</p>
         * <p>please transform the type before use the returned obj</p>
         * @return the texture 
         */
        public Texture getTexture() {
            return texture;
        }

        /**
         * get the sampler currently bound to this texture unit 
         * @return the sampler
         */
        public Sampler getSampler() {
            return sampler;
        }
    }

    /**
     * this class described a buffer binding point
     */
    public class BufferBinding {
        private final int target;
        private final int index;
        private Buffer buffer;

        public BufferBinding(int target, int index) {
            this.target = target;
            this.index = index;
        }

        public BufferBinding(int target) {
            this(target, -1);
        }

        /**
         * bind the buffer to this binding point
         * <p> this will use bind buffer base if the index is valid</p>
         * @param buffer the buffer to bind 
         */
        public void bind(Buffer buffer) {
            if (this.buffer == buffer)
                return;
            this.buffer = buffer;
            setContextCurrent();
            if (buffer == null) {
                if (index < 0) {
                    glBindBuffer(target, 0);
                } else {
                    glBindBufferBase(target, index, 0);
                }
            } else {
                if (index < 0) {
                    if (buffer.isSub()) {
                        glBindBufferRange(target, 0, buffer.id(), buffer.offset(), buffer.size());
                    } else {
                        glBindBuffer(target, buffer.id());
                    }
                } else {
                    if (buffer.isSub()) {
                        glBindBufferRange(target, index, buffer.id(), buffer.offset(), buffer.size());
                    } else {
                        glBindBufferBase(target, index, buffer.id());
                    }
                }
            }
        }

        /**
         * get the currently bound buffer for this binding point
         * @return the currently bound buffer
         */
        public Buffer get() {
            return buffer;
        }
    }

    public class QueryBinding {
        private final int target;
        private Query query;

        public QueryBinding(int target) {
            this.target = target;
        }

        /**
         * bind the query to this binding point
         * @param query the query to bind
         */
        public void bind(Query query) {
            if (this.query == query)
                return;
            if (this.query != null) {
                this.query.setTarget(0);
            }
            this.query = query;
            setContextCurrent();
            if (query == null) {
                return;
            }
            query.setTarget(target);
        }

        /**
         * get the query currently bound to this binding point
         * @return the query
         */
        public Query get() {
            return query;
        }
    }

    public class BlendBinding {
        private Blend blend;

        public void set(Blend blend) {
            if (this.blend == blend)
                return;
            this.blend = blend;
            setContextCurrent();
            if (blend == null) {
                glBlendColor(0, 0, 0, 0);
                glBlendEquation(GL_FUNC_ADD);
                glBlendFunc(GL_ONE, GL_ZERO);
                glBlendFuncSeparate(GL_ONE, GL_ZERO, GL_ONE, GL_ZERO);
                glDisable(GL_BLEND);
                return;
            }
            glEnable(GL_BLEND);
            if (blend.color().get() != null)
                glBlendColor(blend.color().get()[0], blend.color().get()[1], blend.color().get()[2],
                        blend.color().get()[3]);
            else
                glBlendColor(0, 0, 0, 0);
            if (blend.equation().get() != null)
                glBlendEquation(blend.equation().get().mode());
            else
                glBlendEquation(GL_FUNC_ADD);
            if (blend.func() != null)
                glBlendFunc(blend.func().get().src(), blend.func().get().dst());
            else
                glBlendFunc(GL_ONE, GL_ZERO);
            if (blend.funcSeperate().get() != null)
                glBlendFuncSeparate(blend.funcSeperate().get().srcRGB(), blend.funcSeperate().get().dstRGB(),
                        blend.funcSeperate().get().srcAlpha(), blend.funcSeperate().get().dstAlpha());
            else
                glBlendFuncSeparate(GL_ONE, GL_ZERO, GL_ONE, GL_ZERO);
        }

        public Blend get() {
            return blend;
        }
    }

    public class ScissorBinding {
        private Scissor scissor;

        /**
         * bind the scissor to this binding point
         * @param scissor the scissor to bind
         */
        public void set(Scissor scissor) {
            if (this.scissor == scissor)
                return;
            this.scissor = scissor;
            setContextCurrent();
            glDisable(GL_SCISSOR_TEST);
            if (scissor == null) {
                return;
            } else if (scissor.indexed()) {
                for (int i = 0; i < scissor.scissorIndexed().size(); i++) {
                    Scissor.Rectangle rectangle = scissor.scissor(i).get();
                    if (rectangle == null)
                        continue;
                    glEnablei(GL_SCISSOR_TEST, i);
                    glScissorIndexed(i, rectangle.x(), rectangle.y(), rectangle.width(), rectangle.height());
                }
            } else {
                Scissor.Rectangle rectangle = scissor.scissor().get();
                if (rectangle == null)
                    return;
                glEnable(GL_SCISSOR_TEST);
                glScissor(rectangle.x(), rectangle.y(), rectangle.width(), rectangle.height());
            }
        }

        /**
         * get the scissor currently bound to this binding point
         * @return the scissor
         */
        public Scissor get() {
            return scissor;
        }
    }
    
    public class StencilBinding {
        private Stencil stencil;

        /**
         * bind the stencil to this binding point
         * @param stencil the stencil to bind
         */
        public void set(Stencil stencil) {
            if (this.stencil == stencil)
                return;
            this.stencil = stencil;
            setContextCurrent();
            if (stencil == null) {
                glDisable(GL_STENCIL_TEST);
                return;
            }
            glEnable(GL_STENCIL_TEST);
            Stencil.Face front = stencil.front();
            Stencil.Face back = stencil.back();
            if (front != null) {
                glStencilFuncSeparate(GL_FRONT, front.func().func(), front.func().ref(),
                        front.func().mask());
                glStencilOpSeparate(GL_FRONT, front.op().sfail(), front.op().dpfail(),
                        front.op().dppass());
            }
            if (back != null) {
                glStencilFuncSeparate(GL_BACK, back.func().func(), back.func().ref(),
                        back.func().mask());
                glStencilOpSeparate(GL_BACK, back.op().sfail(), back.op().dpfail(),
                        back.op().dppass());
            }
        }

        /**
         * get the stencil currently bound to this binding point
         * @return the stencil
         */
        public Stencil get() {
            return stencil;
        }
    }

    public class DepthBinding {
        private Depth depth;

        /**
         * bind the depth to this binding point
         * @param depth the depth to bind
         */
        public void set(Depth depth) {
            if (this.depth == depth)
                return;
            this.depth = depth;
            setContextCurrent();
            if (depth == null) {
                glDisable(GL_DEPTH_TEST);
                return;
            }
            glEnable(GL_DEPTH_TEST);
            glDepthFunc(depth.func());
        }

        /**
         * get the depth currently bound to this binding point
         * @return the depth
         */
        public Depth get() {
            return depth;
        }
    }

    public class LogicOpBinding {
        private LogicOp logicOp;

        /**
         * bind the logic operation to this binding point
         * @param logicOp the logic operation to bind
         */
        public void set(LogicOp logicOp) {
            if (this.logicOp == logicOp)
                return;
            this.logicOp = logicOp;
            setContextCurrent();
            if (logicOp == null) {
                glDisable(GL_COLOR_LOGIC_OP);
                return;
            }
            glEnable(GL_COLOR_LOGIC_OP);
            glLogicOp(logicOp.op());
        }

        /**
         * get the logic operation currently bound to this binding point
         * @return the logic operation
         */
        public LogicOp get() {
            return logicOp;
        }
    }

    public class WriteMaskBinding {
        private WriteMask writeMask;

        /**
         * bind the write mask to this binding point
         * @param writeMask the write mask to bind
         */
        public void set(WriteMask writeMask) {
            if (this.writeMask == writeMask)
                return;
            this.writeMask = writeMask;
            setContextCurrent();
            if (writeMask == null) {
                glColorMask(true, true, true, true);
                glDepthMask(true);
                return;
            }
            if (writeMask.indexed()) {
                for (int i = 0; i < writeMask.colorMasks().size(); i++) {
                    WriteMask.ColorMask colorMask = writeMask.colorMask(i);
                    if (colorMask == null)
                        continue;
                    glColorMaski(i, colorMask.red(), colorMask.green(), colorMask.blue(), colorMask.alpha());
                }
            } else {
                WriteMask.ColorMask colorMask = writeMask.colorMask();
                if (colorMask != null)
                    glColorMask(colorMask.red(), colorMask.green(), colorMask.blue(), colorMask.alpha());
                else
                    glColorMask(true, true, true, true);
            }
            if (writeMask.depthMask() != null)
                glDepthMask(writeMask.depthMask().depth());
            else
                glDepthMask(true);
        }

        /**
         * get the write mask currently bound to this binding point
         * @return the write mask
         */
        public WriteMask get() {
            return writeMask;
        }
    }

    public class FaceCullBinding {
        private FaceCull faceCull;

        /**
         * bind the face culling to this binding point
         * @param faceCull the face culling to bind
         */
        public void set(FaceCull faceCull) {
            if (this.faceCull == faceCull)
                return;
            this.faceCull = faceCull;
            setContextCurrent();
            if (faceCull == null || !faceCull.cullFace()) {
                glDisable(GL_CULL_FACE);
                return;
            }
            glEnable(GL_CULL_FACE);
            glFrontFace(faceCull.frontFace());
            glCullFace(faceCull.mode());
        }

        /**
         * get the face culling currently bound to this binding point
         * @return the face culling
         */
        public FaceCull get() {
            return faceCull;
        }
    }
    
    public class ViewPortBinding {
        private ViewPort viewPort;
        
        public void set(ViewPort viewPort) {
            if (this.viewPort == viewPort)
                return;
            this.viewPort = viewPort;
            setContextCurrent();
            if (viewPort == null) {
                glViewport(0, 0, 0, 0);
                glDepthRange(0, 1);
                return;
            }
            int[] viewport = viewPort.get();
            float[] depthRange = viewPort.getDepth();
            glViewport(viewport[0], viewport[1], viewport[2], viewport[3]);
            glDepthRange(depthRange[0], depthRange[1]);
        }
        
        public ViewPort get() {
            return viewPort;
        }
    }

    /**
     * create a empty context
     * <p>only used for {@link Context#NULL_CONTEXT}</p>
     * @param o placeholder to avoid collision with {@link Context#Context()}
     */
    private Context(int o) {
        capabilities = null;

        vertexArray = null;

        arrayBuffer = null;
        copyReadBuffer = null;
        copyWriteBuffer = null;
        atomicCounterBuffer = null;
        dispatchIndirectBuffer = null;
        drawIndirectBuffer = null;
        elementArrayBuffer = null;
        pixelPackBuffer = null;
        pixelUnpackBuffer = null;
        queryBuffer = null;
        shaderStorageBuffer = null;
        textureBuffer = null;
        transformFeedbackBuffer = null;
        uniformBuffer = null;

        uniformBuffers = null;
        atomicCounterBuffers = null;
        transformFeedbackBuffers = null;
        shaderStorageBuffers = null;

        textureUnits = null;

        drawFrameBuffer = null;
        readFrameBuffer = null;

        program = null;

        samplesPassedQuery = null;
        anySamplesPassedQuery = null;
        anySamplesPassedConservativeQuery = null;   
        primitivesGeneratedQuery = null;
        transformFeedbackPrimitivesWrittenQuery = null;
        timeElapsedQuery = null;
        timestampQuery = null;
        vertexShaderInvocationsQuery = null;
        geometryShaderInvocationsQuery = null;
        geometryShaderPrimitivesEmittedQuery = null;
        fragmentShaderInvocationsQuery = null;
        computeShaderInvocationsQuery = null;
        tessControlShaderPatchesQuery = null;
        tessEvaluationShaderInvocationsQuery = null;
        clippingInputPrimitivesQuery = null;
        clippingOutputPrimitivesQuery = null;

        scissor = null;
        stencil = null;
        depth = null;
        blend = null;
        logicOp = null;
        writeMask = null;
        faceCull = null;

        viewPort = null;

        memoryBarrier = null;

        clearColor = null;
        clearBit = null;
    }

    /**
     * create a new opengl context on the current thread
     */
    public Context() {
        capabilities = GL.createCapabilities();

        contextCurrent = this;

        vertexArray = new VertexArrayBinding();

        arrayBuffer = new BufferBinding(GL_ARRAY_BUFFER);
        atomicCounterBuffer = new BufferBinding(GL_ATOMIC_COUNTER_BUFFER);
        copyReadBuffer = new BufferBinding(GL_COPY_READ_BUFFER);
        copyWriteBuffer = new BufferBinding(GL_COPY_WRITE_BUFFER);
        dispatchIndirectBuffer = new BufferBinding(GL_DISPATCH_INDIRECT_BUFFER);
        drawIndirectBuffer = new BufferBinding(GL_DRAW_INDIRECT_BUFFER);
        elementArrayBuffer = new BufferBinding(GL_ELEMENT_ARRAY_BUFFER);
        pixelPackBuffer = new BufferBinding(GL_PIXEL_PACK_BUFFER);
        pixelUnpackBuffer = new BufferBinding(GL_PIXEL_UNPACK_BUFFER);
        queryBuffer = new BufferBinding(GL_QUERY_BUFFER);
        shaderStorageBuffer = new BufferBinding(GL_SHADER_STORAGE_BUFFER);
        textureBuffer = new BufferBinding(GL_TEXTURE_BUFFER);
        transformFeedbackBuffer = new BufferBinding(GL_TRANSFORM_FEEDBACK_BUFFER);
        uniformBuffer = new BufferBinding(GL_UNIFORM_BUFFER);

        uniformBuffers = new LinkedList<>();
        atomicCounterBuffers = new LinkedList<>();
        transformFeedbackBuffers = new LinkedList<>();
        shaderStorageBuffers = new LinkedList<>();

        textureUnits = new LinkedList<>();

        drawFrameBuffer = new FramebufferBinding(GL_DRAW_FRAMEBUFFER);
        readFrameBuffer = new FramebufferBinding(GL_READ_FRAMEBUFFER);

        program = new ProgramBinding();

        samplesPassedQuery = new QueryBinding(GL_SAMPLES_PASSED);
        anySamplesPassedQuery = new QueryBinding(GL_ANY_SAMPLES_PASSED);
        anySamplesPassedConservativeQuery = new QueryBinding(GL_ANY_SAMPLES_PASSED_CONSERVATIVE);
        primitivesGeneratedQuery = new QueryBinding(GL_PRIMITIVES_GENERATED);
        transformFeedbackPrimitivesWrittenQuery = new QueryBinding(GL_TRANSFORM_FEEDBACK_PRIMITIVES_WRITTEN);
        timeElapsedQuery = new QueryBinding(GL_TIME_ELAPSED);
        timestampQuery = new QueryBinding(GL_TIMESTAMP);
        vertexShaderInvocationsQuery = new QueryBinding(GL_VERTICES_SUBMITTED);
        geometryShaderInvocationsQuery = new QueryBinding(GL_GEOMETRY_SHADER_INVOCATIONS);
        geometryShaderPrimitivesEmittedQuery = new QueryBinding(GL_GEOMETRY_SHADER_PRIMITIVES_EMITTED);
        fragmentShaderInvocationsQuery = new QueryBinding(GL_FRAGMENT_SHADER_INVOCATIONS);
        computeShaderInvocationsQuery = new QueryBinding(GL_COMPUTE_SHADER_INVOCATIONS);
        tessControlShaderPatchesQuery = new QueryBinding(GL_TESS_CONTROL_SHADER_PATCHES);
        tessEvaluationShaderInvocationsQuery = new QueryBinding(GL_TESS_EVALUATION_SHADER_INVOCATIONS);
        clippingInputPrimitivesQuery = new QueryBinding(GL_CLIPPING_INPUT_PRIMITIVES);
        clippingOutputPrimitivesQuery = new QueryBinding(GL_CLIPPING_OUTPUT_PRIMITIVES);

        scissor = new ScissorBinding();
        stencil = new StencilBinding();
        depth = new DepthBinding();
        blend = new BlendBinding();
        logicOp = new LogicOpBinding();
        writeMask = new WriteMaskBinding();
        faceCull = new FaceCullBinding();

        viewPort = new ViewPortBinding();

        memoryBarrier = new MemoryBarrierBinding();

        clearColor = new ClearColor();
        clearBit = new ClearBit();
    }

    //====CONTEXT OPERATION====//
    //FLAG context operation part begin

    /**
     * set this context as the current context
     */
    public void setContextCurrent() {
        if (contextCurrent != this)
            GL.setCapabilities(capabilities);
    }

    /**
     * set this context as the current opengl context in the current thread.
     * @param context the context to set as the current context.
     */
    public static void setCurrentContext(Context context) {
        context.setContextCurrent();
    }

    /**
     * get the current context of the current thread
     * @return the current context
     */
    public static Context currentContext() {
        return contextCurrent;
    }
    //FLAG context operation part end

    //====VERTEX ARRAY BINDING====//
    //FLAG vertex array binding part begin
    /**
     * get the vertex array binding point 
     * @return the vertex array binding point 
     */
    public VertexArrayBinding vertexArray() {
        return vertexArray;
    }
    //FLAG vertex array binding part end

    //====BUFFER BINDING====//
    //FLAG buffer binding part begin

    /**
     * get the array buffer binding
     * @return the array buffer binding
     */
    public BufferBinding arrayBuffer() {
        return arrayBuffer;
    }

    /**
     * get the copy read buffer binding
     * @return the copy read buffer binding
     */
    public BufferBinding copyReadBuffer() {
        return copyReadBuffer;
    }

    /**
     * get the copy write buffer binding
     * @return the copy write buffer binding
     */
    public BufferBinding copyWriteBuffer() {
        return copyWriteBuffer;
    }

    /**
     * get the dispatch indirect buffer binding
     * @return the dispatch indirect buffer binding
     */
    public BufferBinding dispatchIndirectBuffer() {
        return dispatchIndirectBuffer;
    }

    /**
     * get the draw indirect buffer binding
     * @return the draw indirect buffer binding
     */
    public BufferBinding drawIndirectBuffer() {
        return drawIndirectBuffer;
    }

    /**
     * get the element buffer binding
     * @return the element buffer binding
     */
    public BufferBinding elementArrayBuffer() {
        return elementArrayBuffer;
    }

    /**
     * get the pixel pack buffer binding
     * @return the pixel pack buffer binding
     */
    public BufferBinding pixelPackBuffer() {
        return pixelPackBuffer;
    }

    /**
     * get the pixel unpack buffer binding
     * @return the pixel unpack buffer binding
     */
    public BufferBinding pixelUnpackBuffer() {
        return pixelUnpackBuffer;
    }

    /**
     * get the atomic counter buffer binding
     * @return the atomic counter buffer binding
     */
    public BufferBinding atomicCounterBuffer() {
        return atomicCounterBuffer;
    }

    /**
     * get the transform feedback buffer binding
     * @return the transform feedback buffer binding
     */
    public BufferBinding transformFeedbackBuffer() {
        return transformFeedbackBuffer;
    }

    /**
     * get the uniform buffer binding
     * @return the uniform buffer binding
     */
    public BufferBinding uniformBuffer() {
        return uniformBuffer;
    }

    /**
     * get the shader storage buffer binding
     * @return the shader storage buffer binding
     */
    public BufferBinding shaderStorageBuffer() {
        return shaderStorageBuffer;
    }

    /**
     * get the query buffer binding
     * @return the query buffer binding
     */
    public BufferBinding queryBuffer() {
        return queryBuffer;
    }

    /**
     * get the texture buffer binding
     * @return the texture buffer binding
     */
    public BufferBinding textureBuffer() {
        return textureBuffer;
    }

    /**
     * get the atomic counter buffer binding at index binding point
     * @param index the binding point index
     * @return the binding point
     */
    public BufferBinding atomicCounterBuffer(int index) {
        if (index >= atomicCounterBuffers.size()) {
            for (int binding = atomicCounterBuffers.size(); binding <= index; binding++)
                atomicCounterBuffers.add(new BufferBinding(GL_ATOMIC_COUNTER_BUFFER, binding));
        }
        return atomicCounterBuffers.get(index);
    }

    /**
     * get the transform feedback buffer binding at index binding point
     * @param index the binding point index
     * @return the binding point
     */
    public BufferBinding transformFeedbackBuffer(int index) {
        if (index >= transformFeedbackBuffers.size()) {
            for (int binding = transformFeedbackBuffers.size(); binding <= index; binding++)
                transformFeedbackBuffers.add(new BufferBinding(GL_TRANSFORM_FEEDBACK_BUFFER, binding));
        }
        return transformFeedbackBuffers.get(index);
    }

    /**
     * get the uniform buffer binding at index binding point
     * @param index the binding point index
     * @return the binding point
     */
    public BufferBinding uniformBuffer(int index) {
        if (index >= uniformBuffers.size()) {
            for (int binding = uniformBuffers.size(); binding <= index; binding++)
                uniformBuffers.add(new BufferBinding(GL_UNIFORM_BUFFER, binding));
        }
        return uniformBuffers.get(index);
    }

    /**
     * get the shader storage buffer binding at index binding point
     * @param index the binding point index
     * @return the binding point
     */
    public BufferBinding shaderStorageBuffer(int index) {
        if (index >= shaderStorageBuffers.size()) {
            for (int binding = shaderStorageBuffers.size(); binding <= index; binding++)
                shaderStorageBuffers.add(new BufferBinding(GL_SHADER_STORAGE_BUFFER, binding));
        }
        return shaderStorageBuffers.get(index);
    }
    //FLAG buffer binding part end

    //====TEXTURE UNIT FUNC====//
    //FLAG texture unit part begin
    /**
     * get the texture unit at binding point index
     * @param index the index of the texture unit
     * @return the texture unit at index
     */
    public TextureUnit textureUnit(int index) {
        if (index >= textureUnits.size()) {
            for (int binding = textureUnits.size(); binding <= index; binding++)
                textureUnits.add(new TextureUnit(binding));
        }
        return textureUnits.get(index);
    }
    //FLAG texture unit part end

    //====FRAME BUFFER BIND====//
    //FLAG frame buffer bind part begin
    /**
     * get the read frame buffer binding 
     * @return the read frame buffer binding 
     */
    public FramebufferBinding readFrameBuffer() {
        return readFrameBuffer;
    }

    /**
     * get the draw frame buffer binding
     * @return the draw frame buffer binding
     */
    public FramebufferBinding drawFrameBuffer() {
        return drawFrameBuffer;
    }
    //FLAG frame buffer bind part end

    //====PROGRAM BIND====//
    //FLAG program bind part begin
    /**
     * get the program binding
     * @return the program binding
     */
    public ProgramBinding program() {
        return program;
    }
    //FLAG program bind part end

    //====QUERY BIND====//
    //FLAG query bind part begin
    /**
     * get the samples passed query binding
     * @return the samples passed query binding
     */
    public QueryBinding samplesPassedQuery() {
        return samplesPassedQuery;
    }

    /**
     * get the any samples passed query binding
     * @return the any samples passed query binding
     */
    public QueryBinding anySamplesPassedQuery() {
        return anySamplesPassedQuery;
    }

    /**
     * get the any samples passed conservative query binding
     * @return the any samples passed conservative query binding
     */
    public QueryBinding anySamplesPassedConservativeQuery() {
        return anySamplesPassedConservativeQuery;
    }
    
    /**
     * get the primitives generated query binding
     * @return the primitives generated query binding
     */
    public QueryBinding primitivesGeneratedQuery() {
        return primitivesGeneratedQuery;
    }

    /**
     * get the transform feedback primitives written query binding
     * @return the transform feedback primitives written query binding
     */
    public QueryBinding transformFeedbackPrimitivesWrittenQuery() {
        return transformFeedbackPrimitivesWrittenQuery;
    }

    /**
     * get the time elapsed query binding
     * @return the time elapsed query binding
     */
    public QueryBinding timeElapsedQuery() {
        return timeElapsedQuery;
    }

    /**
     * get the timestamp query binding
     * @return the timestamp query binding
     */
    public QueryBinding timestampQuery() {
        return timestampQuery;
    }

    /**
     * get the vertex shader invocations query binding
     * @return the vertex shader invocations query binding
     */
    public QueryBinding vertexShaderInvocationsQuery() {
        return vertexShaderInvocationsQuery;
    }

    /**
     * get the geometry shader invocations query binding
     * @return the geometry shader invocations query binding
     */
    public QueryBinding geometryShaderInvocationsQuery() {
        return geometryShaderInvocationsQuery;
    }

    /**
     * get the geometry shader primitives emitted query binding
     * @return the geometry shader primitives emitted query binding
     */
    public QueryBinding geometryShaderPrimitivesEmittedQuery() {
        return geometryShaderPrimitivesEmittedQuery;
    }

    /**
     * get the fragment shader invocations query binding
     * @return the fragment shader invocations query binding
     */
    public QueryBinding fragmentShaderInvocationsQuery() {
        return fragmentShaderInvocationsQuery;
    }

    /**
     * get the compute shader invocations query binding
     * @return the compute shader invocations query binding
     */
    public QueryBinding computeShaderInvocationsQuery() {
        return computeShaderInvocationsQuery;
    }

    /**
     * get the tessellation control shader patches query binding
     * @return the tessellation control shader patches query binding
     */
    public QueryBinding tessControlShaderPatchesQuery() {
        return tessControlShaderPatchesQuery;
    }

    /**
     * get the tessellation evaluation shader invocations query binding
     * @return the tessellation evaluation shader invocations query binding
     */
    public QueryBinding tessEvaluationShaderInvocationsQuery() {
        return tessEvaluationShaderInvocationsQuery;
    }

    /**
     * get the clipping input primitives query binding
     * @return the clipping input primitives query binding
     */
    public QueryBinding clippingInputPrimitivesQuery() {
        return clippingInputPrimitivesQuery;
    }

    /**
     * get the clipping output primitives query binding
     * @return the clipping output primitives query binding
     */
    public QueryBinding clippingOutputPrimitivesQuery() {
        return clippingOutputPrimitivesQuery;
    }
    //FLAG query bind part end

    /**
     * get the scissor binding
     * @return the scissor binding
     */
    public ScissorBinding scissor() {
        return scissor;
    }

    /**
     * get the stencil binding
     * @return the stencil binding
     */
    public StencilBinding stencil() {
        return stencil;
    }

    /**
     * get the depth binding
     * @return the depth binding
     */
    public DepthBinding depth() {
        return depth;
    }

    /**
     * get the blend binding
     * @return the blend binding
     */
    public BlendBinding blend() {
        return blend;
    }

    /**
     * get the logic operation binding
     * @return the logic operation binding
     */
    public LogicOpBinding logicOp() {
        return logicOp;
    }

    /**
     * get the write mask binding
     * @return the write mask binding
     */
    public WriteMaskBinding writeMask() {
        return writeMask;
    }

    /**
     * get the face cull binding
     * @return the face cull binding
     */
    public FaceCullBinding faceCull() {
        return faceCull;
    }

    /**
     * get the view port binding
     * @return the view port binding
     */
    public ViewPortBinding viewPort() {
        return viewPort;
    }

    /**
     * get the memory barrier binding
     * @return the memory barrier binding
     */
    public MemoryBarrierBinding memoryBarrier() {
        return memoryBarrier;
    }

    /**
     * get the clear color binding
     * @return the clear color binding
     */
    public ClearColor clearColor() {
        return clearColor;
    }

    /**
     * get the clear bit binding
     * @return the clear bit binding
     */
    public ClearBit clearBit() {
        return clearBit;
    }

    /**
     * clear all the binding 
     */
    public void clear() {
        //clear vertex array binding if the value is not null
        if (vertexArray.get() != null)
            vertexArray.bind(null);
        //clear buffer binding if the value is not null
        if (arrayBuffer.get() != null)
            arrayBuffer.bind(null);
        if (copyReadBuffer.get() != null)
            copyReadBuffer.bind(null);
        if (copyWriteBuffer.get() != null)
            copyWriteBuffer.bind(null);
        if (dispatchIndirectBuffer.get() != null)
            dispatchIndirectBuffer.bind(null);
        if (drawIndirectBuffer.get() != null)
            drawIndirectBuffer.bind(null);
        if (elementArrayBuffer.get() != null)
            elementArrayBuffer.bind(null);
        if (pixelPackBuffer.get() != null)
            pixelPackBuffer.bind(null);
        if (pixelUnpackBuffer.get() != null)
            pixelUnpackBuffer.bind(null);
        if (atomicCounterBuffer.get() != null)
            atomicCounterBuffer.bind(null);
        if (transformFeedbackBuffer.get() != null)
            transformFeedbackBuffer.bind(null);
        if (uniformBuffer.get() != null)
            uniformBuffer.bind(null);
        if (shaderStorageBuffer.get() != null)
            shaderStorageBuffer.bind(null);
        if (queryBuffer.get() != null)
            queryBuffer.bind(null);
        if (textureBuffer.get() != null)
            textureBuffer.bind(null);
        //clear indexed buffer binding if the value is not null
        for (int i = 0; i < atomicCounterBuffers.size(); i++) {
            if (atomicCounterBuffer(i).get() != null)
                atomicCounterBuffer(i).bind(null);
        }
        for (int i = 0; i < transformFeedbackBuffers.size(); i++) {
            if (transformFeedbackBuffer(i).get() != null)
                transformFeedbackBuffer(i).bind(null);
        }
        for (int i = 0; i < uniformBuffers.size(); i++) {
            if (uniformBuffer(i).get() != null)
                uniformBuffer(i).bind(null);
        }
        for (int i = 0; i < shaderStorageBuffers.size(); i++) {
            if (shaderStorageBuffer(i).get() != null)
                shaderStorageBuffer(i).bind(null);
        }
        //clear texture unit binding if the value is not null
        for (int i = 0; i < textureUnits.size(); i++) {
            if (textureUnit(i).getTexture() != null)
                textureUnit(i).bindTexture(null);
            if (textureUnit(i).getSampler() != null)
                textureUnit(i).bindSampler(null);
        }
        //clear frame buffer binding if the value is not null
        if (readFrameBuffer.get() != null)
            readFrameBuffer.bind(null);
        if (drawFrameBuffer.get() != null)
            drawFrameBuffer.bind(null);
        //clear program binding if the value is not null
        if (program.get() != null)
            program.bind(null);
        //clear query binding if the value is not null
        if (samplesPassedQuery.get() != null)
            samplesPassedQuery.bind(null);
        if (anySamplesPassedQuery.get() != null)
            anySamplesPassedQuery.bind(null);
        if (anySamplesPassedConservativeQuery.get() != null)
            anySamplesPassedConservativeQuery.bind(null);
        if (primitivesGeneratedQuery.get() != null)
            primitivesGeneratedQuery.bind(null);
        if (transformFeedbackPrimitivesWrittenQuery.get() != null)
            transformFeedbackPrimitivesWrittenQuery.bind(null);
        if (timeElapsedQuery.get() != null)
            timeElapsedQuery.bind(null);
        if (timestampQuery.get() != null)
            timestampQuery.bind(null);
        if (vertexShaderInvocationsQuery.get() != null)
            vertexShaderInvocationsQuery.bind(null);
        if (geometryShaderInvocationsQuery.get() != null)
            geometryShaderInvocationsQuery.bind(null);
        if (geometryShaderPrimitivesEmittedQuery.get() != null)
            geometryShaderPrimitivesEmittedQuery.bind(null);
        if (fragmentShaderInvocationsQuery.get() != null)
            fragmentShaderInvocationsQuery.bind(null);
        if (computeShaderInvocationsQuery.get() != null)
            computeShaderInvocationsQuery.bind(null);
        if (tessControlShaderPatchesQuery.get() != null)
            tessControlShaderPatchesQuery.bind(null);
        if (tessEvaluationShaderInvocationsQuery.get() != null)
            tessEvaluationShaderInvocationsQuery.bind(null);
        if (clippingInputPrimitivesQuery.get() != null)
            clippingInputPrimitivesQuery.bind(null);
        if (clippingOutputPrimitivesQuery.get() != null)
            clippingOutputPrimitivesQuery.bind(null);
        //clear scissor binding if the value is not null
        if (scissor.get() != null)
            scissor.set(null);
        //clear stencil binding if the value is not null
        if (stencil.get() != null)
            stencil.set(null);
        //clear depth binding if the value is not null
        if (depth.get() != null)
            depth.set(null);
        //clear blend binding if the value is not null
        if (blend.get() != null)
            blend.set(null);
        //clear logic operation binding if the value is not null
        if (logicOp.get() != null)
            logicOp.set(null);
        //clear write mask binding if the value is not null
        if (writeMask.get() != null)
            writeMask.set(null);
        //clear face cull binding if the value is not null
        if (faceCull.get() != null)
            faceCull.set(null);
        //clear view port binding if the value is not null
        if (viewPort.get() != null)
            viewPort.set(null);
        //clear memory barrier binding if the value is not null
        if (memoryBarrier.get() != null)
            memoryBarrier.set(null);
        //clear clear color binding
        clearColor.set(0, 0, 0, 0);
        //clear clear bit binding
        clearBit.set(0);
    }

    //====DRAW FUNC====//

    /**
     * draw the vertex array
     * @param mode the draw mode
     * @param first the first vertex to draw
     * @param count the number of vertex to draw
     */
    public void drawArrays(int mode, int first, int count) {
        glDrawArrays(mode, first, count);
    }

    /**
     * draw the vertex array with instance
     * @param mode the draw mode
     * @param first the first vertex to draw
     * @param count the number of vertex to draw
     * @param instanceCount the number of instance to draw
     */
    public void drawArraysInstanced(int mode, int first, int count, int instanceCount) {
        glDrawArraysInstanced(mode, first, count, instanceCount);
    }

    public void drawArraysIndirect(int mode, long indirect) {
        glDrawArraysIndirect(mode, indirect);
    }

    public void drawElements(int mode, int count, int type, long indices) {
        glDrawElements(mode, count, type, indices);
    }

    /**
     * multi draw elements
     * @param mode the draw mode
     * @param type the type of the indices
     * @param indirect the indirect buffer
     * @param drawcount the number of draw
     * @param stride the stride of the indirect buffer
     */
    public void multiDrawElementsIndirect(int mode, int type, long indirect, int drawcount, int stride) {
        glMultiDrawElementsIndirect(mode, type, indirect, drawcount, stride);
    }

    public void drawElementsInstanced(int mode, int count, int type, long indices, int instanceCount) {
        glDrawElementsInstanced(mode, count, type, indices, instanceCount);
    }

    public void drawElementsBaseVertex(int mode, int count, int type, long indices, int baseVertex) {
        glDrawElementsBaseVertex(mode, count, type, indices, baseVertex);
    }

    public void drawElementsInstancedBaseVertex(int mode, int count, int type, long indices, int instanceCount, int baseVertex) {
        glDrawElementsInstancedBaseVertex(mode, count, type, indices, instanceCount, baseVertex);
    }

    public void drawElementsIndirect(int mode, int type, long indirect) {
        glDrawElementsIndirect(mode, type, indirect);
    }

    public void drawRangeElements(int mode, int start, int end, int count, int type, long indices) {
        glDrawRangeElements(mode, start, end, count, type, indices);
    }

    public void drawRangeElementsBaseVertex(int mode, int start, int end, int count, int type, long indices, int baseVertex) {
        glDrawRangeElementsBaseVertex(mode, start, end, count, type, indices, baseVertex);
    }


    public void dispatchCompute(int num_groups_x, int num_groups_y, int num_groups_z) {
        glDispatchCompute(num_groups_x, num_groups_y, num_groups_z);
    }

    public void dispatchComputeIndirect(long indirect) {
        glDispatchComputeIndirect(indirect);
    }
}
