/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gl3dgame.perspect;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

/**
 *
 * @��ӭ��ҹ������IT���Ϳռ�.
 */
public class GLRender implements GLEventListener {

    float xRot = 0.0f;
    float yRot = 0.0f;
    //    GL gl;
    GL2 gl;
    GLU glu;

    @Override
    public void init(GLAutoDrawable drawable) {
        //        gl = drawable.getGL();
        gl = drawable.getGL().getGL2();
        glu = new GLU();

        float whiteLight[] = {0.45f, 0.45f, 0.45f, 1.0f};
        float sourceLight[] = {0.25f, 0.25f, 0.25f, 1.0f};
        float lightPos[] = {-50.f, 25.0f, 250.0f, 0.0f};

        gl.glEnable(GL.GL_DEPTH_TEST);	//������Ȳ��� Hidden surface removal
        gl.glFrontFace(GL.GL_CCW);		//����CCW����Ϊ�����桱��CCW��CounterClockWise����ʱ��
        gl.glEnable(GL.GL_CULL_FACE);	//��GL_CULL_FACE���ų�������glEnable������ʾ��������α����޳�����

        // Enable lighting  ���õƹ�
        gl.glEnable(GLLightingFunc.GL_LIGHTING);

        // Setup and enable light 0   ���õƹ�
        gl.glLightModelfv(GL2ES1.GL_LIGHT_MODEL_AMBIENT, whiteLight, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_AMBIENT, sourceLight, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_DIFFUSE, sourceLight, 0);
        gl.glLightfv(GLLightingFunc.GL_LIGHT0, GLLightingFunc.GL_POSITION, lightPos, 0);
        gl.glEnable(GLLightingFunc.GL_LIGHT0);

        // Enable color tracking   ���ò���
        gl.glEnable(GLLightingFunc.GL_COLOR_MATERIAL);

        // ��������������滹�Ƿ��棬�Ի����⡢����⻹������������ɫ���١�
        //��һ����������ȡGL_FRONT��GL_BACK��GL_FRONT_AND_BACK�е�����һ�֣�
        //�ڶ�����������ȡGL_AMBIENT��GL_DIFFUSE��GL_AMBIENT_AND_DIFFUSE��GL_SPECULAR�е�����һ�֡�
        gl.glColorMaterial(GL.GL_FRONT, GLLightingFunc.GL_AMBIENT_AND_DIFFUSE);

        // ��ɫ����
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        float fZ,bZ;

        // ��䱳��Ϊ������ɫ
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        fZ = 100.0f;
        bZ = -100.0f;

        // �������״̬������ת
        gl.glPushMatrix();
        gl.glTranslatef(0.0f, 0.0f, -300.0f);
        gl.glRotatef(xRot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(yRot, 0.0f, 1.0f, 0.0f);

        // ���û滭��ɫΪ��
        gl.glColor3f(1.0f, 0.0f, 0.0f);

        // Front Face ///////////////////////////////////
        gl.glBegin(GL2GL3.GL_QUADS);
        //ֱ���˵���ָ��Z��,Ҳ����ָ��ǰ��ķ���
        gl.glNormal3f(0.0f, 0.0f, 1.0f);

        // �������
        gl.glVertex3f(-50.0f, 50.0f, fZ);
        gl.glVertex3f(-50.0f, -50.0f, fZ);
        gl.glVertex3f(-35.0f, -50.0f, fZ);
        gl.glVertex3f(-35.0f,50.0f,fZ);

        //�����Һ�
        gl.glVertex3f(50.0f, 50.0f, fZ);
        gl.glVertex3f(35.0f, 50.0f, fZ);
        gl.glVertex3f(35.0f, -50.0f, fZ);
        gl.glVertex3f(50.0f,-50.0f,fZ);

        //�����Ϻ�
        gl.glVertex3f(-35.0f, 50.0f, fZ);
        gl.glVertex3f(-35.0f, 35.0f, fZ);
        gl.glVertex3f(35.0f, 35.0f, fZ);
        gl.glVertex3f(35.0f, 50.0f,fZ);

        //�����º�
        gl.glVertex3f(-35.0f, -35.0f, fZ);
        gl.glVertex3f(-35.0f, -50.0f, fZ);
        gl.glVertex3f(35.0f, -50.0f, fZ);
        gl. glVertex3f(35.0f, -35.0f,fZ);

        // ���� ////////////////////////////
        // Normal points up Y axis
        gl.glNormal3f(0.0f, 1.0f, 0.0f);
        gl.glVertex3f(-50.0f, 50.0f, fZ);
        gl.glVertex3f(50.0f, 50.0f, fZ);
        gl.glVertex3f(50.0f, 50.0f, bZ);
        gl.glVertex3f(-50.0f,50.0f,bZ);

        // ����
        gl.glNormal3f(0.0f, -1.0f, 0.0f);
        gl.glVertex3f(-50.0f, -50.0f, fZ);
        gl.glVertex3f(-50.0f, -50.0f, bZ);
        gl.glVertex3f(50.0f, -50.0f, bZ);
        gl.glVertex3f(50.0f, -50.0f, fZ);

        // ����
        gl.glNormal3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(50.0f, 50.0f, fZ);
        gl.glVertex3f(50.0f, -50.0f, fZ);
        gl.glVertex3f(50.0f, -50.0f, bZ);
        gl.glVertex3f(50.0f, 50.0f, bZ);

        // ����
        gl.glNormal3f(-1.0f, 0.0f, 0.0f);
        gl.glVertex3f(-50.0f, 50.0f, fZ);
        gl.glVertex3f(-50.0f, 50.0f, bZ);
        gl.glVertex3f(-50.0f, -50.0f, bZ);
        gl.glVertex3f(-50.0f, -50.0f, fZ);
        gl.glEnd();

        gl.glFrontFace(GL.GL_CW);		// ����CW����Ϊ�����桱��CW��ClockWise��˳ʱ��

        gl.glBegin(GL2GL3.GL_QUADS);
        // Back section
        // Pointing straight out Z
        gl.glNormal3f(0.0f, 0.0f, -1.0f);

        // �������
        gl.glVertex3f(-50.0f, 50.0f, bZ);
        gl.glVertex3f(-50.0f, -50.0f, bZ);
        gl.glVertex3f(-35.0f, -50.0f, bZ);
        gl. glVertex3f(-35.0f,50.0f,bZ);

        // �����Һ�
        gl.glVertex3f(50.0f, 50.0f, bZ);
        gl.glVertex3f(35.0f, 50.0f, bZ);
        gl.glVertex3f(35.0f, -50.0f, bZ);
        gl. glVertex3f(50.0f,-50.0f,bZ);

        // �����Ϻ�
        gl.glVertex3f(-35.0f, 50.0f, bZ);
        gl.glVertex3f(-35.0f, 35.0f, bZ);
        gl.glVertex3f(35.0f, 35.0f, bZ);
        gl. glVertex3f(35.0f, 50.0f,bZ);

        // �����º�
        gl. glVertex3f(-35.0f, -35.0f, bZ);
        gl. glVertex3f(-35.0f, -50.0f, bZ);
        gl. glVertex3f(35.0f, -50.0f, bZ);
        gl. glVertex3f(35.0f, -35.0f,bZ);

        // Insides /////////////////////////////
        //��ɫ
        gl.glColor3f(0.75f, 0.75f, 0.75f);

        // ����
        gl. glNormal3f(0.0f, 1.0f, 0.0f);
        gl. glVertex3f(-35.0f, 35.0f, fZ);
        gl. glVertex3f(35.0f, 35.0f, fZ);
        gl. glVertex3f(35.0f, 35.0f, bZ);
        gl. glVertex3f(-35.0f,35.0f,bZ);

        // ����
        gl. glNormal3f(0.0f, 1.0f, 0.0f);
        gl. glVertex3f(-35.0f, -35.0f, fZ);
        gl. glVertex3f(-35.0f, -35.0f, bZ);
        gl. glVertex3f(35.0f, -35.0f, bZ);
        gl. glVertex3f(35.0f, -35.0f, fZ);

        // ����
        gl. glNormal3f(1.0f, 0.0f, 0.0f);
        gl. glVertex3f(-35.0f, 35.0f, fZ);
        gl. glVertex3f(-35.0f, 35.0f, bZ);
        gl. glVertex3f(-35.0f, -35.0f, bZ);
        gl. glVertex3f(-35.0f, -35.0f, fZ);

        // ����
        gl. glNormal3f(-1.0f, 0.0f, 0.0f);
        gl. glVertex3f(35.0f, 35.0f, fZ);
        gl.glVertex3f(35.0f, -35.0f, fZ);
        gl. glVertex3f(35.0f, -35.0f, bZ);
        gl. glVertex3f(35.0f, 35.0f, bZ);
        gl.glEnd();

        gl.glFrontFace(GL.GL_CCW);		//����CCW����Ϊ�����桱��CCW��CounterClockWise����ʱ��

        // Restore the matrix state  ��ԭ״̬����
        gl.glPopMatrix();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        float fAspect;

        // ��ֹΪ��
        if (height == 0) {
            height = 1;
        }

        //�ӿ�����Ϊ���ڳߴ�
        gl.glViewport(0, 0, width, height);

        fAspect =(float) width / height;

        // Reset projection matrix stack
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();



        //͸��ͶӰ �۽Ƕ�,����,������,Զ����
        glu.gluPerspective(53.0f, fAspect, 1.0, 400.0);

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
/////////////////////////////////////////////////////////////////////////////////
