/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tk.jogl;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;

/**
 *
 * @author Administrator
 */
public class GLRender implements GLEventListener {

    GL gl;
    // ��ת�Ƕ�
    static float xRot = 0.0f;
    static float yRot = 0.0f;


    boolean iCull = false;
    boolean iOutline = false;
    boolean iDepth = true;
    float x, y, angle;  // �洢Ϊ����ͽǶ�

    float r,g,b;

    @Override
    public void init(GLAutoDrawable drawable) {
        //        gl = drawable.getGL();
        final GL2 gl = drawable.getGL().getGL2();

        // ��ɫ����
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // ���û�ͼ��ɫΪ��ɫ
        gl.glColor3f(0.0f, 1.0f, 0.0f);

        // ������ɫ��ɫģ��ƽ��
        gl.glShadeModel(GLLightingFunc.GL_FLAT);

        // Clock wise wound polygons are front facing, this is reversed
        // because we are using triangle fans
        // ����CW����Ϊ�����桱��CW��ClockWise��˳ʱ��
        gl.glFrontFace(GL.GL_CW);


    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        int iPivot = 1;		// Used to flag alternating colors

        // Clear the window and the depth buffer
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Turn culling on if flag is set
        if (iCull) {
            gl.glEnable(GL.GL_CULL_FACE);  //�޳�����
        } else {
            gl.glDisable(GL.GL_CULL_FACE);
        }

        // Enable depth testing if flag is set
        if (iDepth) {
            gl.glEnable(GL.GL_DEPTH_TEST);
        } else {
            gl.glDisable(GL.GL_DEPTH_TEST);
        }

        // Draw back side as a polygon only, if flag is set
        //������ƶ����ֻ��һ���������־����
        if (iOutline) {
            gl.glPolygonMode(GL.GL_BACK, GL2GL3.GL_LINE);
        } else {
            gl.glPolygonMode(GL.GL_BACK, GL2GL3.GL_FILL);
        }


        // Save matrix state and do the rotation
        //�������״̬������ת
        gl.glPushMatrix();
        gl.glRotatef(GLRender.xRot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(GLRender.yRot, 0.0f, 1.0f, 0.0f);


        // ��ʼ��������
        gl.glBegin(GL.GL_TRIANGLE_FAN);

        // Pinnacle of cone is shared vertex for fan, moved up Z axis
        // to produce a cone instead of a circle
        gl.glVertex3f(0.0f, 0.0f, 75.0f);

        // Loop around in a circle and specify even points along the circle
        // as the vertices of the triangle fan
        //����Χ��һ��ԲȦ����ָ����������Ϊ�����εĶ���Բ��
        for (angle = 0.0f; angle <= 2.0f * Math.PI+1; angle += Math.PI / 8.0f) {
            // ������һ�������x��yλ��
            x = (float) (50.0f * Math.sin(angle));
            y = (float) (50.0f * Math.cos(angle));

            //������ɫ��ɫ����ɫ
            if (iPivot % 2 == 0) {
                gl.glColor3f(0.0f, 1.0f, 0.0f);
            } else {
                gl.glColor3f(1.0f, 0.0f, 0.0f);
            }

            //��ȫ�����ɫ,����Ч�����ÿ�.
            //gl.glColor3f((float)Math.random(), (float)Math.random(), (float)Math.random());



            // Increment pivot to change color next time
            iPivot++;

            // Specify the next vertex for the triangle fan
            gl.glVertex2f(x, y);
        }

        // Done drawing fan for cone
        gl.glEnd();


        // �ڵ��滭��������
        gl.glBegin(GL.GL_TRIANGLE_FAN);

        // Center of fan is at the origin
        gl.glVertex2f(0.0f, 0.0f);
        for (angle = 0.0f; angle <= 2.0f * Math.PI+1; angle += Math.PI / 8.0f) {
            // Calculate x and y position of the next vertex
            x = (float) (50.0f * Math.sin(angle));
            y = (float) (50.0f * Math.cos(angle));

            // Alternate color between red and green
            if (iPivot % 2 == 0) {
                gl.glColor3f(0.0f, 1.0f, 0.0f);
            } else {
                gl.glColor3f(1.0f, 0.0f, 0.0f);
            }
            //gl.glColor3f((float)Math.random(), (float)Math.random(), (float)Math.random());

            // Increment pivot to change color next time
            //����iPivot����һ�θı���ɫ
            iPivot++;

            // Specify the next vertex for the triangle fan
            gl.glVertex2f(x, y);
        }

        // Done drawing the fan that covers the bottom
        //���ͼֽ�ķ��ȣ������ײ�
        gl.glEnd();

        // Restore transformations
        gl.glPopMatrix();



    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        float nRange = 100.0f;

        // ��ֹΪ��
        if (height == 0) {
            height = 1;
        }

        //�ӿ�����Ϊ���ڳߴ�
        gl.glViewport(0, 0, width, height);

        // Reset projection matrix stack
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();

        //����������ͼ,��ͶӰ�����ң��ײ�������������Զ��
        if (width <= height) {
            gl.glOrtho(-nRange, nRange, -nRange * height / width, nRange * height / width, -nRange, nRange);
        } else {
            gl.glOrtho(-nRange * width / height, nRange * width / height, -nRange, nRange, -nRange, nRange);
        }

        // ����ģ�͹۲�����ջ
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    @Override
    public void dispose(GLAutoDrawable arg0)
    {
        // TODO Auto-generated method stub

    }
}
