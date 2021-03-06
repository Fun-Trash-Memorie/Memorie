package application.game.rendering;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.PMVMatrix;

import de.hshl.obj.loader.OBJLoader;
import de.hshl.obj.loader.Resource;
import de.hshl.obj.loader.objects.Mesh;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Renderer extends GLCanvas implements GLEventListener {

    private static final long serialVersionUID = 1L;
    // Defining shader source code file paths and names
    final String shaderPath = ".\\graphic_resources\\";
    final String vertexShaderFileName = "Basic.vert";
    final String fragmentShaderFileName = "Basic.frag";
    private static final Path objFile = Paths.get("./graphic_resources/models/suzanne.obj");
//    final String vertexShaderFileName = "BasicMacOS.vert";
//    final String fragmentShaderFileName = "BasicMacOS.frag";

    // Object for loading shaders and creating a shader program
    private ShaderProgram shaderProgram;

    // OpenGL buffer names for data allocation and handling on GPU
    int[] vaoName;  // List of names (integer pointers) of vertex array objects
    int[] vboName;  // List of names (integer pointers) of vertex buffer objects
    int[] iboName;  // List of names (integer pointers) of index buffer objects

    // Declaration of an object for handling keyboard and mouse interactions
    InteractionHandler interactionHandler;

    // Declaration for using the projection-model-view matrix tool
    PMVMatrix pmvMatrix;

    // contains the geometry of our OBJ file
    float[] verticies;
    int[] indices;

    public Renderer(){
        super();
        this.addGLEventListener(this);
        createAndRegisterInteractionHandler();
    }

    public Renderer(GLCapabilities capabilities) {

        super(capabilities);

        this.addGLEventListener(this);
        createAndRegisterInteractionHandler();
    }

    /**
     * Helper method for creating an interaction handler object and registering it
     * for key press and mouse interaction call backs.
     */
    private void createAndRegisterInteractionHandler() {
        // The constructor call of the interaction handler generates meaningful default values.
        // The start parameters can also be set via setters
        // (see class definition of the interaction handler).
        interactionHandler = new InteractionHandler();
        this.addKeyListener(interactionHandler);
        this.addMouseListener(interactionHandler);
        this.addMouseMotionListener(interactionHandler);
        this.addMouseWheelListener(interactionHandler);
    }

    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * that is called when the OpenGL renderer is started for the first time.
     * @param drawable The OpenGL drawable
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        // Retrieve the OpenGL graphics context
        GL3 gl = drawable.getGL().getGL3();
        // Outputs information about the available and chosen profile
        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        // Loading the vertex and fragment shaders and creation of the shader program.
        shaderProgram = new ShaderProgram(gl);
        shaderProgram.loadShaderAndCreateProgram(shaderPath,
                vertexShaderFileName, fragmentShaderFileName);

        // Create object for projection-model-view matrix calculation.
        pmvMatrix = new PMVMatrix();

        // Vertices for drawing a triangle.
        // To be transferred to a vertex buffer object on the GPU.
        // Interleaved data layout: position, color
        try {
            Mesh mesh = new OBJLoader()
                    .setLoadNormals(true) // tell the loader to also load normal data
                    .setGenerateIndexedMeshes(true) // tell the loader to output separate index arrays
                    .loadMesh(Resource.file(objFile)); // actually load the file

            verticies = mesh.getVertices();
            indices = mesh.getIndices();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create and activate a vertex array object (VAO)
        // Useful for switching between data sets for object rendering.
        vaoName = new int[1];
        // Creating the buffer on GPU.
        gl.glGenVertexArrays(1, vaoName, 0);
        if (vaoName[0] < 1)
            System.err.println("Error allocating vertex array object (VAO) on GPU.");
        // Switch to this VAO.
        gl.glBindVertexArray(vaoName[0]);

        // Create, activate and initialize vertex buffer object (VBO)
        // Used to store vertex data on the GPU.
        vboName = new int[1];
        // Creating the buffer on GPU.
        gl.glGenBuffers(1, vboName, 0);
        if (vboName[0] < 1)
            System.err.println("Error allocating vertex buffer object (VBO) on GPU.");
        // Activating this buffer as vertex buffer object.
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vboName[0]);
        // Transferring the vertex data (see above) to the VBO on GPU.
        // (floats use 4 bytes in Java)
        gl.glBufferData(GL.GL_ARRAY_BUFFER, (long) verticies.length * Float.BYTES,
                FloatBuffer.wrap(verticies), GL.GL_STATIC_DRAW);


        iboName = new int[1];
        // Creating the buffer on GPU.
        gl.glGenBuffers(1, iboName, 0);
        if (iboName[0] < 1)
            System.err.println("Error allocating vertex buffer object (VBO) on GPU.");

        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, iboName[0]);
        gl.glBufferData(
                GL.GL_ELEMENT_ARRAY_BUFFER, (long) this.indices.length * Integer.BYTES,
                IntBuffer.wrap(indices), GL.GL_STATIC_DRAW
        );

        // Activate and map input for the vertex shader from VBO,
        // taking care of interleaved layout of vertex data (position and color),
        // Enable layout position 0
        gl.glEnableVertexAttribArray(0);
        // Map layout position 0 to the position information per vertex in the VBO.
        gl.glVertexAttribPointer(0, 3, GL.GL_FLOAT, false, 6* Float.BYTES, 0);
        // Enable layout position 1
        gl.glEnableVertexAttribArray(1);
        // Map layout position 1 to the color information per vertex in the VBO.
        gl.glVertexAttribPointer(1, 3, GL.GL_FLOAT, false, 6* Float.BYTES, 3* Float.BYTES);

        // Set start parameter(s) for the interaction handler.
        interactionHandler.setEyeZ(2);

        // Switch on back face culling
        // gl.glEnable(GL.GL_CULL_FACE);
        //gl.glCullFace(GL.GL_BACK);

        // Switch on depth application.test.
        gl.glEnable(GL.GL_DEPTH_TEST);

        // Set background color of the GLCanvas.
        gl.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
    }

    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * called by the OpenGL animator for every frame.
     * @param drawable The OpenGL drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        // Retrieve the OpenGL graphics context
        GL3 gl = drawable.getGL().getGL3();
        // Clear color and depth buffer
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT | GL3.GL_DEPTH_BUFFER_BIT);

        // Controlling the interaction settings
/*        System.out.println("Camera: z = " + interactionHandler.getEyeZ() + ", " +
                "x-Rot: " + interactionHandler.getAngleXaxis() +
                ", y-Rot: " + interactionHandler.getAngleYaxis() +
                ", x-Translation: " + interactionHandler.getxPosition()+
                ", y-Translation: " + interactionHandler.getyPosition());// definition of translation of model (Model/Object Coordinates --> World Coordinates)
*/

        // Apply view transform using the PMV-Tool
        // Camera positioning is steered by the interaction handler
        pmvMatrix.glLoadIdentity();
        pmvMatrix.gluLookAt(0f, 0f, interactionHandler.getEyeZ(),
                0f, 0f, 0f,
                0f, 1.0f, 0f);
        pmvMatrix.glTranslatef(interactionHandler.getxPosition(), interactionHandler.getyPosition(), 0f);
        pmvMatrix.glRotatef(interactionHandler.getAngleXaxis(), 1f, 0f, 0f);
        pmvMatrix.glRotatef(interactionHandler.getAngleYaxis(), 0f, 1f, 0f);

        // Switch to this vertex buffer array for drawing.
        gl.glBindVertexArray(vaoName[0]);
        // Activating the compiled shader program.
        // Could be placed into the init-method for this simple example.
        gl.glUseProgram(shaderProgram.getShaderProgramID());

        // Transfer the PVM-Matrix (model-view and projection matrix) to the GPU
        // via uniforms
        // Transfer projection matrix via uniform layout position 0
        gl.glUniformMatrix4fv(0, 1, false, pmvMatrix.glGetPMatrixf());
        // Transfer model-view matrix via layout position 1
        gl.glUniformMatrix4fv(1, 1, false, pmvMatrix.glGetMvMatrixf());

        // Draw the triangles using the indices array
        gl.glDrawElements(
                GL.GL_TRIANGLES,            // mode
                indices.length,             // count
                GL.GL_UNSIGNED_INT,         // type
                0                         // element array buffer offset
        );
    }


    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * called when the OpenGL window is resized.
     * @param drawable The OpenGL drawable
     * @param x x-coordinate of the viewport
     * @param y y-coordinate of the viewport
     * @param width width of the viewport
     * @param height height of the viewport
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Retrieve the OpenGL graphics context
        GL3 gl = drawable.getGL().getGL3();

        // Set the viewport to the entire window
        gl.glViewport(0, 0, width, height);
        // Switch the pmv-tool to perspective projection
        pmvMatrix.glMatrixMode(PMVMatrix.GL_PROJECTION);
        // Reset projection matrix to identity
        pmvMatrix.glLoadIdentity();
        // Calculate projection matrix
        //      Parameters:
        //          fovy (field of view), aspect ratio,
        //          zNear (near clipping plane), zFar (far clipping plane)
        pmvMatrix.gluPerspective(45f, (float) width/ (float) height, 0.1f, 10000f);
        // Switch to model-view transform
        pmvMatrix.glMatrixMode(PMVMatrix.GL_MODELVIEW);
    }

    /**
     * Implementation of the OpenGL EventListener (GLEventListener) method
     * called when OpenGL canvas ist destroyed.
     * @param drawable the drawable
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Retrieve the OpenGL graphics context
        GL3 gl = drawable.getGL().getGL3();
        System.out.println("Deleting allocated objects, incl. the shader program.");

        // Detach and delete shader program
        gl.glUseProgram(0);
        shaderProgram.deleteShaderProgram();

        // deactivate VAO and VBO
        gl.glBindVertexArray(0);
        gl.glDisableVertexAttribArray(0);
        gl.glDisableVertexAttribArray(1);
        gl.glDeleteVertexArrays(1, vaoName,0);
        gl.glDeleteBuffers(1, vboName, 0);

        System.exit(0);
    }
}
